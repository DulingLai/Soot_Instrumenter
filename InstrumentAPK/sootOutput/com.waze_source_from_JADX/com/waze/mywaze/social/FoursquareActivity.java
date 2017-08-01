package com.waze.mywaze.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FoursquareSettings;
import com.waze.profile.FoursquareConnectActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class FoursquareActivity extends ActivityBase {
    private static final String LOG_TAG = "Waze";
    private WazeSettingsView badgeOption;
    private boolean beforeCheckIn;
    private String[] buttonTitles;
    private TextView connectButton;
    private TextView connectStatus;
    private boolean connected;
    private WazeSettingsView loginOption;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private String[] statusTexts;

    class C20121 implements OnClickListener {
        C20121() throws  {
        }

        public void onClick(View v) throws  {
            FoursquareActivity.this.onLogin();
        }
    }

    class C20132 implements SettingsToggleCallback {
        C20132() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            FoursquareActivity.this.onOptionLogin($z0);
        }
    }

    class C20143 implements SettingsToggleCallback {
        C20143() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            FoursquareActivity.this.onOptionBadge($z0);
        }
    }

    class C20154 implements DialogInterface.OnClickListener {
        C20154() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
        }
    }

    protected void onDestroy() throws  {
        super.onDestroy();
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.foursquare);
        this.buttonTitles = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_button_title));
        this.statusTexts = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_status_short));
        ((TitleBar) findViewById(C1283R.id.foursquareTitle)).init(this, 162);
        this.connectButton = (TextView) findViewById(C1283R.id.connectButton);
        this.connectStatus = (TextView) findViewById(C1283R.id.connectStatus);
        this.loginOption = (WazeSettingsView) findViewById(C1283R.id.optionChecking);
        this.badgeOption = (WazeSettingsView) findViewById(C1283R.id.optionBadge);
        findViewById(C1283R.id.connect).setOnClickListener(new C20121());
        ((SettingsTitleText) findViewById(C1283R.id.text1)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_AUTOMATICALLY_TWEET_TO_MY_FOLLOWERS_THAT_IC));
        ((TextView) findViewById(C1283R.id.text2)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.f146xb877abb7) + "\n\n" + this.nativeManager.getLanguageString((int) DisplayStrings.DS_WHAT_IS_FOURSQUAREQ) + "\n" + this.nativeManager.getLanguageString((int) DisplayStrings.f114xfd0132ae) + "\n" + this.nativeManager.getLanguageString((int) DisplayStrings.DS_DONST_HAVE_AN_ACCOUNTQ_SIGN_UP_ONC) + " " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_WWW_FOURSQUARE_COM));
        this.loginOption.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_AM_CHECKING_OUT_THIS_INTEGRATION));
        this.loginOption.setOnChecked(new C20132());
        this.badgeOption.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_HAVE_UNLOCKED_THE_ROADWARRIOR_BADGE));
        this.badgeOption.setOnChecked(new C20143());
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            FoursquareSettings $r17 = (FoursquareSettings) $r1.getSerializable("com.waze.mywaze.foursquaresettings");
            boolean z = $r17.loggedIn;
            boolean $z0 = z;
            this.connected = z;
            WazeSettingsView $r8 = this.loginOption;
            z = $r17.enableTweetLogin;
            $r8.setValue(z);
            $r8 = this.badgeOption;
            z = $r17.enableTweetBadge;
            $r8.setValue(z);
            z = $r17.checkInAfterLogin;
            $z0 = z;
            this.beforeCheckIn = z;
        }
        updateStatus();
    }

    private void updateStatus() throws  {
        if (this.connected) {
            this.connectButton.setText(this.buttonTitles[1]);
            this.connectStatus.setText(this.statusTexts[1]);
            if (this.beforeCheckIn) {
                this.mywazeManager.foursquareCheckin();
                setResult(-1);
                finish();
                return;
            }
            return;
        }
        this.connectButton.setText(this.buttonTitles[0]);
        this.connectStatus.setText(this.statusTexts[0]);
    }

    private void onLogin() throws  {
        if (this.connected) {
            this.mywazeManager.foursquareDisconnect();
        } else if (AppService.isNetworkAvailable()) {
            startActivityForResult(new Intent(this, FoursquareConnectActivity.class), 0);
        } else {
            Log.e(LOG_TAG, "FourSquareActivity:: onLogin, no network connection");
            MsgBox.openMessageBoxWithCallback(this.nativeManager.getLanguageString((int) DisplayStrings.DS_NO_NETWORK_CONNECTION), this.nativeManager.getLanguageString(252), false, new C20154());
        }
    }

    private void onOptionLogin(boolean $z0) throws  {
        this.mywazeManager.foursquareSetTweetLogin($z0);
    }

    private void onOptionBadge(boolean $z0) throws  {
        this.mywazeManager.foursquareSetTweetBadge($z0);
    }

    public void refreshStatus(boolean $z0) throws  {
        if (isRunning()) {
            this.connected = $z0;
            updateStatus();
        }
    }
}
