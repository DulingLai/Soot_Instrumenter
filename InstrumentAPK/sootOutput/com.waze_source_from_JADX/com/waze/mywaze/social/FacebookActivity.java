package com.waze.mywaze.social;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.navigate.social.AddFromActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class FacebookActivity extends ActivityBase implements FacebookCallback {
    public static String INTENT_FROM_WHERE = "INTENT_FROM_WHERE";
    private String[] buttonTitles;
    private Button connectButton;
    private boolean connected;
    private View drivingMenu;
    private WazeSettingsView drivingOption;
    private CheckedTextView[] drivingStyle = new CheckedTextView[2];
    private String[] drivingStyleTexts;
    private boolean mForRideWith = false;
    private FacebookSettings mSettings;
    private boolean modified;
    private WazeSettingsView munchingOption;
    private MyWazeNativeManager mywazeManager;
    private NativeManager nativeManager;
    private WazeSettingsView reportsOption;

    class C20051 implements OnClickListener {
        C20051() throws  {
        }

        public void onClick(View v) throws  {
            FacebookActivity.this.onLogin();
        }
    }

    class C20062 implements OnClickListener {
        C20062() throws  {
        }

        public void onClick(View v) throws  {
            if (FacebookActivity.this.connected) {
                Intent $r2 = new Intent(FacebookActivity.this, AddFromActivity.class);
                $r2.putExtra(FacebookActivity.INTENT_FROM_WHERE, AddFromActivity.INTENT_FROM_FB);
                FacebookActivity.this.startActivityForResult($r2, 1003);
            }
        }
    }

    protected void onDestroy() throws  {
        if (this.modified) {
            this.mywazeManager.facebookPostConnect();
        }
        MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, this.mHandler);
        super.onDestroy();
    }

    public void refreshStatus(boolean $z0) throws  {
        if (isRunning()) {
            boolean $z1 = false;
            if ($z0 != this.connected) {
                $z1 = true;
            }
            this.connected = $z0;
            updateStatus();
            if ($z1) {
                this.nativeManager.CloseProgressPopup();
            }
        }
        UpdateCheckBox();
    }

    public void UpdateCheckBox() throws  {
        MyWazeNativeManager.getInstance().getFacebookSettings(this);
    }

    public void onFacebookSettings(FacebookSettings $r1) throws  {
        this.mSettings = $r1;
    }

    protected void onCreate(Bundle $r1) throws  {
        boolean $z0 = false;
        super.onCreate($r1);
        setContentView(C1283R.layout.facebook);
        this.nativeManager = AppService.getNativeManager();
        this.mywazeManager = MyWazeNativeManager.getInstance();
        this.modified = false;
        this.buttonTitles = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_button_title));
        this.drivingStyleTexts = this.nativeManager.getLanguageStrings(getResources().getStringArray(C1283R.array.social_driving_styles));
        ((TitleBar) findViewById(C1283R.id.facebookTitle)).init(this, 396);
        this.connectButton = (Button) findViewById(C1283R.id.connect);
        Intent $r9 = getIntent();
        if ($r9 != null) {
            this.mForRideWith = $r9.getBooleanExtra("RideWith", false);
            $r1 = $r9.getExtras();
            if ($r1 != null) {
                FacebookSettings $r11 = (FacebookSettings) $r1.getSerializable("com.waze.mywaze.facebooksettings");
                this.mSettings = $r11;
                this.connected = $r11.loggedIn;
            }
        }
        updateStatus();
        this.connectButton.setOnClickListener(new C20051());
        ((TextView) findViewById(C1283R.id.facebookDontWorryTitle)).setVisibility(8);
        ((TextView) findViewById(C1283R.id.facebookDontWorryText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_NO_ONE_CAN_ACTIVELY));
        short $s0 = (short) 168;
        if (CarpoolNativeManager.getInstance().configIsOnNTV() && CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
            $z0 = true;
        }
        if (this.mForRideWith || $z0) {
            $s0 = (short) 169;
        }
        ((TextView) findViewById(C1283R.id.facebookConnectText)).setText(this.nativeManager.getLanguageString((int) $s0));
        ((SettingsTitleText) findViewById(C1283R.id.facebookBlockedTitle)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_MANAGE));
        ((WazeSettingsView) findViewById(C1283R.id.facebookBlockedFriends)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_BLOCKED_FRIENDS));
        findViewById(C1283R.id.facebookBlockedFriends).setOnClickListener(new C20062());
    }

    private void updateStatus() throws  {
        if (this.connected) {
            this.connectButton.setText(this.buttonTitles[1]);
            Logger.m41i("Facebook session is currently connected");
            return;
        }
        this.connectButton.setText(this.buttonTitles[0]);
        Logger.m41i("Facebook session is currently disconnected");
    }

    private void onLogin() throws  {
        if (this.connected) {
            handleFacebookLogout();
            return;
        }
        Logger.m41i("Requesting Facebook connect");
        this.nativeManager.OpenProgressPopup("");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FACEBOOK_CONNECT_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_SCREEN);
        MyWazeNativeManager.getInstance().setUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, this.mHandler);
        FacebookManager.getInstance().loginWithFacebook(this, FacebookLoginType.SetToken, true);
    }

    private void handleFacebookLogout() throws  {
        Logger.m41i("Requesting Facebook disconnect");
        this.mywazeManager.facebookDisconnect();
        FacebookManager.getInstance().logoutFromFacebook();
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        Intent $r2;
        if ($r1 == null) {
            $r2 = new Intent();
        } else {
            $r2 = $r1;
        }
        super.onActivityResult($i0, $i1, $r2);
        if (!$r2.getBooleanExtra(FacebookManager.DATA_FACEBOOK_FLAG, false)) {
            if ($i1 == -1) {
                setResult(-1);
                finish();
            }
            super.onActivityResult($i0, $i1, $r1);
        } else if ($i1 != -1) {
            refreshStatus(false);
        }
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what != MyWazeNativeManager.UH_FACEBOOK_CONNECT) {
            return super.myHandleMessage($r1);
        }
        MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, this.mHandler);
        Logger.m36d("FacebookActivity: received FB connect from server");
        Bundle $r4 = $r1.getData();
        boolean $z0 = $r4.getBoolean(MyWazeNativeManager.FB_CONNECTED);
        boolean $z1 = MyWazeNativeManager.getInstance().getFacebookLoggedInNTV();
        if ($z0 && $z1) {
            Logger.m36d("CarpoolGoogleSignInActivity:  FB connected successfully to server");
            refreshStatus(true);
        } else {
            String $r6;
            int $i0 = -1;
            if ($r4.containsKey(MyWazeNativeManager.FB_CONNECT_ERROR_REASON)) {
                $i0 = $r4.getInt(MyWazeNativeManager.FB_CONNECT_ERROR_REASON);
            }
            Logger.m38e("CarpoolGoogleSignInActivity: FB Failed to connect to server, result=" + $z0 + "; connected=" + $z1 + "; reason=" + $i0);
            if ($i0 == 6) {
                $r6 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_FB_CONNECT_USER_ALREADY_IN_USE);
            } else {
                $r6 = DisplayStrings.displayString(DisplayStrings.DS_COULD_NOT_CONNECT_WITH_FACEBOOK__TRY_AGAIN_LATER_);
            }
            refreshStatus(false);
            NativeManager $r7 = this.nativeManager;
            $r7.CloseProgressPopup();
            Logger.m43w("CarpoolGoogleSignInActivity: FB UH_FACEBOOK_CONNECT:User already connected, disconnecting from FB");
            FacebookManager.getInstance().logoutFromFacebook();
            MsgBox.getInstance().OpenMessageBoxTimeoutJavaCb(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_FB_CONNECT_USER_ALREADY_IN_USE_TITLE), $r6, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_FB_CONNECT_USER_ALREADY_IN_USE_BUTTON), -1, null);
        }
        return true;
    }
}
