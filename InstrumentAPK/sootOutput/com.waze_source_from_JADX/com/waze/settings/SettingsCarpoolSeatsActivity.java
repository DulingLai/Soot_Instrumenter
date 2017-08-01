package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class SettingsCarpoolSeatsActivity extends ActivityBase {
    public static final int MAX_SEATS = 4;
    private static final long NETWORK_TIMEOUT = 10000;
    private boolean bSentRequest;
    private int mSelected;
    private Runnable mTimeoutRunnable;

    class C26552 implements OnClickListener {
        C26552() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SettingsCarpoolSeatsActivity.this.setResult(-1);
            SettingsCarpoolSeatsActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_RW_SETTINGS_AVAILABLE_SEATS_TITLE);
        this.mSelected = CarpoolNativeManager.getInstance().getCarpoolProfileNTV().available_seats;
        final SettingValueAdapter adapter = new SettingValueAdapter(this);
        final SettingsValue[] values = new SettingsValue[6];
        View space = new View(this);
        space.setMinimumHeight(PixelMeasure.dp(40));
        space.setBackgroundColor(getResources().getColor(C1283R.color.blue_bg));
        values[0] = new SettingsValue(space);
        for (int i = 1; i <= 4; i++) {
            String display;
            boolean z;
            if (i == 1) {
                display = DisplayStrings.displayString(DisplayStrings.DS_RW_SETTINGS_1_SEAT);
            } else {
                display = DisplayStrings.displayStringF(DisplayStrings.DS_RW_SETTINGS_SEATS_PD, Integer.valueOf(i));
            }
            String str = "" + i;
            if (i == this.mSelected) {
                z = true;
            } else {
                z = false;
            }
            values[i] = new SettingsValue(str, display, z);
        }
        View note = getLayoutInflater().inflate(C1283R.layout.settings_note, null);
        ((TextView) note.findViewById(C1283R.id.settingsNoteText)).setText(DisplayStrings.displayString(DisplayStrings.DS_RW_SETTINGS_AVAILABLE_SEATS_NOTE));
        values[5] = new SettingsValue(note);
        adapter.setValues(values);
        ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

            class C26531 implements Runnable {
                C26531() {
                }

                public void run() {
                    SettingsCarpoolSeatsActivity.this.showError();
                }
            }

            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                if (position != 0 && position != 5) {
                    values[SettingsCarpoolSeatsActivity.this.mSelected].isSelected = false;
                    values[position].isSelected = true;
                    SettingsCarpoolSeatsActivity.this.mSelected = position;
                    adapter.notifyDataSetChanged();
                    NativeManager.getInstance().OpenProgressPopup(DisplayStrings.displayString(290));
                    CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, SettingsCarpoolSeatsActivity.this.mHandler);
                    CarpoolNativeManager.getInstance().setAvaliableSeats(position);
                    SettingsCarpoolSeatsActivity.this.bSentRequest = true;
                    SettingsCarpoolSeatsActivity.this.mTimeoutRunnable = new C26531();
                    SettingsCarpoolSeatsActivity.this.postDelayed(SettingsCarpoolSeatsActivity.this.mTimeoutRunnable, SettingsCarpoolSeatsActivity.NETWORK_TIMEOUT);
                }
            }
        });
        findViewById(C1283R.id.settingsValueListShadow).setVisibility(8);
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
    }

    private void showError() {
        NativeManager nativeManager = NativeManager.getInstance();
        nativeManager.CloseProgressPopup();
        MsgBox.openMessageBoxTimeout(nativeManager.getLanguageString(DisplayStrings.DS_UHHOHE), nativeManager.getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C26552());
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what != CarpoolNativeManager.UH_CARPOOL_USER) {
            return super.myHandleMessage(msg);
        }
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        if (this.bSentRequest) {
            this.bSentRequest = false;
            Bundle b = msg.getData();
            cancel(this.mTimeoutRunnable);
            if (b.getBoolean("success")) {
                final NativeManager nativeManager = NativeManager.getInstance();
                nativeManager.CloseProgressPopup();
                nativeManager.OpenProgressIconPopup(DisplayStrings.displayString(DisplayStrings.DS_RW_SETTINGS_SEATS_SET), "bigblue_v_icon");
                postDelayed(new Runnable() {
                    public void run() {
                        nativeManager.CloseProgressPopup();
                        SettingsCarpoolSeatsActivity.this.setResult(SettingsCarpoolWorkActivity.RESULT_EMAIL_CHANGED);
                        SettingsCarpoolSeatsActivity.this.finish();
                    }
                }, 1000);
            } else {
                showError();
            }
        }
        return true;
    }
}
