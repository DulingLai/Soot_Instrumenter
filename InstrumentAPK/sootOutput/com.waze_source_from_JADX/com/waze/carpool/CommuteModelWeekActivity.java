package com.waze.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.Utils;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CommuteModelWeekActivity extends ActivityBase {
    private CarpoolNativeManager mCpnm;
    private CarpoolCommuteDay[] mDays;
    private int mFirstDay = 2;
    private String[] mNamesOfDay;
    private NativeManager mNm;
    private CarpoolUserData mUserData;

    class C16351 implements OnClickListener {
        C16351() throws  {
        }

        public void onClick(View v) throws  {
            Intent $r2 = new Intent(CommuteModelWeekActivity.this, AddHomeWorkActivity.class);
            $r2.putExtra("carpool", true);
            $r2.putExtra("edit_home", true);
            CommuteModelWeekActivity.this.startActivityForResult($r2, 0);
        }
    }

    class C16362 implements OnClickListener {
        C16362() throws  {
        }

        public void onClick(View v) throws  {
            Intent $r2 = new Intent(CommuteModelWeekActivity.this, AddHomeWorkActivity.class);
            $r2.putExtra("carpool", true);
            $r2.putExtra("edit_work", true);
            CommuteModelWeekActivity.this.startActivityForResult($r2, 0);
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mUserData = this.mCpnm.getCarpoolProfileNTV();
        setContentView(C1283R.layout.commute_model_week);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, DisplayStrings.DS_COMMUTE_MODEL_WEEK_TITLE);
        ((SettingsTitleText) findViewById(C1283R.id.commuteModelWeekHomeWorkTitle)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_HOME_WORK_TITLE));
        WazeSettingsView $r11 = (WazeSettingsView) findViewById(C1283R.id.commuteModelWeekHome);
        $r11.setKeyText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_HOME_ITEM));
        $r11.setSelectorImage(C1283R.drawable.list_edit_icon);
        $r11.setIcon(C1283R.drawable.list_icon_home);
        $r11.setOnClickListener(new C16351());
        $r11.setValueText(this.mUserData.inferred_home.address);
        $r11 = (WazeSettingsView) findViewById(C1283R.id.commuteModelWeekWork);
        $r11.setKeyText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_WORK_ITEM));
        $r11.setSelectorImage(C1283R.drawable.list_edit_icon);
        $r11.setIcon(C1283R.drawable.list_icon_work);
        $r11.setOnClickListener(new C16362());
        $r11.setValueText(this.mUserData.inferred_work.address);
        ((SettingsTitleText) findViewById(C1283R.id.commuteModelWeekDaysTitle)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_HOME_DAYS_TITLE));
        ((TextView) findViewById(C1283R.id.commuteModelWeekDaysComment)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_COMMENT));
        ((TextView) findViewById(C1283R.id.commuteModelWeekDaysLink)).setVisibility(8);
        Locale $r16 = Locale.getDefault();
        String $r10 = this.mCpnm.getLocaleNTV();
        if (!($r10 == null || $r10.isEmpty())) {
            try {
                $r16 = new Locale("", $r10);
            } catch (Exception e) {
                Logger.m38e("CommuteModelWeekActivity: failed to create locale for country " + $r10);
            }
        }
        this.mFirstDay = Calendar.getInstance($r16).getFirstDayOfWeek();
        this.mNamesOfDay = DateFormatSymbols.getInstance($r16).getWeekdays();
        populateDays();
        StringBuilder stringBuilder = new StringBuilder();
        $r10 = (this.mUserData.inferred_home.address == null || this.mUserData.inferred_home.address.isEmpty()) ? "F|" : "T|";
        StringBuilder $r20 = stringBuilder.append($r10);
        $r10 = (this.mUserData.inferred_work.address == null || this.mUserData.inferred_work.address.isEmpty()) ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_F : AnalyticsEvents.ANALYTICS_EVENT_VALUE_T;
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_SHOWN, "HOME|WORK", $r20.append($r10).toString());
        CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
    }

    protected void onDestroy() throws  {
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        super.onDestroy();
    }

    String formatRange(int $i0, int $i1) throws  {
        return this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_TIME_FORMAT).replace("<FROM>", Utils.formatTime(this, $i0)).replace("<TO>", Utils.formatTime(this, $i1));
    }

    void populateDays() throws  {
        int[] $r2 = new int[]{C1283R.id.commuteModelWeekDays1, C1283R.id.commuteModelWeekDays2, C1283R.id.commuteModelWeekDays3, C1283R.id.commuteModelWeekDays4, C1283R.id.commuteModelWeekDays5, C1283R.id.commuteModelWeekDays6, C1283R.id.commuteModelWeekDays7};
        this.mDays = CarpoolNativeManager.getInstance().getCarpoolProfileNTV().commute_days;
        for (int $i1 = 0; $i1 < 7; $i1++) {
            boolean $z0 = true;
            WazeSettingsView $r7 = (WazeSettingsView) findViewById($r2[$i1]);
            final int $i0 = ((this.mFirstDay + $i1) - 1) % 7;
            $r7.setText(this.mNamesOfDay[$i0 + 1]);
            CarpoolCommuteDay $r1 = this.mCpnm.getCarpoolProfileNTV().commute_days[$i0];
            $r7.setVisiblyEnabled(true);
            $r7.setIconVisibility(0);
            if ($r1.toWorkValid()) {
                $r7.setLine1(formatRange($r1.toWorkStart, $r1.toWorkEnd));
            } else {
                $r7.setLine1(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_UNAVAILABLE));
                $z0 = false;
            }
            if ($r1.toHomeValid()) {
                $r7.setLine2(formatRange($r1.toHomeStart, $r1.toHomeEnd));
            } else if ($r1.toWorkValid()) {
                $r7.setLine2(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_WEEK_UNAVAILABLE));
            } else {
                $r7.setLine2(null);
                $r7.setVisiblyEnabled($z0);
            }
            $r7.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    Intent $r2 = new Intent(CommuteModelWeekActivity.this, CommuteModelDayActivity.class);
                    $r2.putExtra("dayName", CommuteModelWeekActivity.this.mNamesOfDay[$i0 + 1]);
                    $r2.putExtra("dayNumber", $i0);
                    CommuteModelWeekActivity.this.startActivityForResult($r2, 0);
                }
            });
        }
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            this.mUserData = this.mCpnm.getCarpoolProfileNTV();
            ((WazeSettingsView) findViewById(C1283R.id.commuteModelWeekHome)).setValueText(this.mUserData.inferred_home.address);
            ((WazeSettingsView) findViewById(C1283R.id.commuteModelWeekWork)).setValueText(this.mUserData.inferred_work.address);
            populateDays();
        }
        return super.myHandleMessage($r1);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            populateDays();
        }
        super.onActivityResult($i0, $i1, $r1);
    }
}
