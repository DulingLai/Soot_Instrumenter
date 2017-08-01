package com.waze.mywaze.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.TwitterSettings;
import com.waze.profile.TwitterConnectActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class TwitterActivity extends ActivityBase {
    private static final String LOG_TAG = "Waze";
    private static final int SOCIAL_DESTINATION_MODE_CITY = 1;
    private static final int SOCIAL_DESTINATION_MODE_DISABLED = 0;
    private static final int SOCIAL_DESTINATION_MODE_FULL = 3;
    private String[] buttonTitles;
    private TextView connectButton;
    private TextView connectStatus;
    private boolean connected;
    private View drivingMenu;
    private WazeSettingsView drivingOption;
    private CheckedTextView[] drivingStyle = new CheckedTextView[2];
    private String[] drivingStyleTexts;
    private WazeSettingsView munchingOption;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private WazeSettingsView reportsOption;
    private String[] statusTexts;

    class C20281 implements OnClickListener {
        C20281() throws  {
        }

        public void onClick(View v) throws  {
            TwitterActivity.this.onLogin();
        }
    }

    class C20292 implements SettingsToggleCallback {
        C20292() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            TwitterActivity.this.onOptionReports($z0);
        }
    }

    class C20303 implements SettingsToggleCallback {
        C20303() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            TwitterActivity.this.onOptionDriving($z0);
        }
    }

    class C20325 implements SettingsToggleCallback {
        C20325() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            TwitterActivity.this.onOptionMunching($z0);
        }
    }

    class C20336 implements DialogInterface.OnClickListener {
        C20336() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
        }
    }

    protected void onDestroy() throws  {
        super.onDestroy();
    }

    public void refreshStatus(boolean $z0) throws  {
        if (isRunning()) {
            this.connected = $z0;
            updateStatus();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.twitter);
        ((TitleBar) findViewById(C1283R.id.twitterTitle)).init(this, 159);
        this.buttonTitles = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_button_title));
        this.statusTexts = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_status_short));
        this.drivingStyleTexts = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_driving_styles));
        this.connectButton = (TextView) findViewById(C1283R.id.connectButton);
        this.connectStatus = (TextView) findViewById(C1283R.id.connectStatus);
        this.reportsOption = (WazeSettingsView) findViewById(C1283R.id.optionReports);
        this.drivingOption = (WazeSettingsView) findViewById(C1283R.id.optionDriving);
        this.drivingMenu = findViewById(C1283R.id.drivingStyles);
        this.drivingStyle[0] = (CheckedTextView) findViewById(C1283R.id.selectionDriving1);
        this.drivingStyle[1] = (CheckedTextView) findViewById(C1283R.id.selectionDriving2);
        this.munchingOption = (WazeSettingsView) findViewById(C1283R.id.optionMunching);
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            TwitterSettings $r13 = (TwitterSettings) $r1.getSerializable("com.waze.mywaze.twittersettings");
            boolean z = $r13.loggedIn;
            boolean $z0 = z;
            this.connected = z;
            WazeSettingsView $r8 = this.reportsOption;
            z = $r13.postReports;
            $r8.setValue(z);
            $r8 = this.munchingOption;
            z = $r13.postMunching;
            $r8.setValue(z);
            updateDriving($r13.postDriving);
            findViewById(C1283R.id.sectionMunching).setVisibility($r13.showMunching ? (byte) 0 : (byte) 8);
        }
        updateStatus();
        findViewById(C1283R.id.connect).setOnClickListener(new C20281());
        ((SettingsTitleText) findViewById(C1283R.id.textTitle)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_AUTOMATICALLY_TWEET_TO_MY_FOLLOWERSC));
        this.reportsOption.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_MY_ROAD_REPORTS));
        this.reportsOption.setOnChecked(new C20292());
        ((TextView) findViewById(C1283R.id.textReports)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.f106x6e38a7a9));
        this.drivingOption.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_MY_DESTINATION_AND_ETA));
        this.drivingOption.setOnChecked(new C20303());
        int $i0 = 0;
        while (true) {
            int $i2 = this.drivingStyle;
            CheckedTextView[] $r9 = $i2;
            if ($i0 < $i2.length) {
                this.drivingStyle[$i0].setText(this.drivingStyleTexts[$i0]);
                final int i = $i0;
                this.drivingStyle[$i0].setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        TwitterActivity.this.onDrivingStyle(i);
                    }
                });
                $i0++;
            } else {
                ((TextView) findViewById(C1283R.id.textDriving)).setText(this.nativeManager.getLanguageString(172));
                this.munchingOption.setText(this.nativeManager.getLanguageString(176));
                this.munchingOption.setOnChecked(new C20325());
                ((TextView) findViewById(C1283R.id.textMunching)).setText(this.nativeManager.getLanguageString(157));
                return;
            }
        }
    }

    private void updateStatus() throws  {
        if (this.connected) {
            this.connectButton.setText(this.buttonTitles[1]);
            this.connectStatus.setText(this.statusTexts[1]);
            return;
        }
        this.connectButton.setText(this.buttonTitles[0]);
        this.connectStatus.setText(this.statusTexts[0]);
    }

    private void updateDriving(int $i0) throws  {
        if ($i0 > 0) {
            this.drivingOption.setValue(true);
            showOptionDriving(true);
            if ($i0 == 3) {
                showDrivingStyle(1);
                return;
            } else {
                showDrivingStyle(0);
                return;
            }
        }
        this.drivingOption.setValue(false);
        showOptionDriving(false);
        showDrivingStyle(0);
    }

    private void onLogin() throws  {
        if (this.connected) {
            this.mywazeManager.twitterDisconnect();
        } else if (AppService.isNetworkAvailable()) {
            Intent $r1 = new Intent(this, TwitterConnectActivity.class);
            $r1.putExtra(TwitterConnectActivity.FROM_MENU_KEY, true);
            startActivityForResult($r1, 0);
        } else {
            Log.e(LOG_TAG, "TwitterActivity:: onLogin, no network connection");
            MsgBox.openMessageBoxWithCallback(this.nativeManager.getLanguageString((int) DisplayStrings.DS_NO_NETWORK_CONNECTION), this.nativeManager.getLanguageString(252), false, new C20336());
        }
    }

    private void onOptionReports(boolean $z0) throws  {
        this.mywazeManager.twitterSetReportMode($z0);
    }

    private void onOptionDriving(boolean $z0) throws  {
        byte $b0 = (byte) 1;
        showOptionDriving($z0);
        MyWazeNativeManager $r1 = this.mywazeManager;
        if (!$z0) {
            $b0 = (byte) 0;
        } else if (this.drivingStyle[1].isChecked()) {
            $b0 = (byte) 3;
        }
        $r1.twitterSetDrivingMode($b0);
    }

    private void onOptionMunching(boolean $z0) throws  {
        this.mywazeManager.twitterSetMunchingMode($z0);
    }

    private void onDrivingStyle(int $i0) throws  {
        showDrivingStyle($i0);
        this.mywazeManager.twitterSetDrivingMode($i0 > 0 ? (byte) 3 : (byte) 1);
    }

    private void showDrivingStyle(int $i0) throws  {
        int $i1 = 0;
        while ($i1 < this.drivingStyle.length) {
            this.drivingStyle[$i1].setChecked($i0 == $i1);
            $i1++;
        }
    }

    private void showOptionDriving(boolean $z0) throws  {
        this.drivingMenu.setVisibility($z0 ? (byte) 0 : (byte) 8);
    }
}
