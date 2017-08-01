package com.waze.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.install.InstallFacebookPrivacyActivity;
import com.waze.install.InstallNativeManager;
import com.waze.install.InstallNickNameActivity;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class MinimalSignInActivity extends ActivityBase {
    private static boolean active = false;
    private static boolean bIsBackFromFBScreen = false;
    private static boolean bIsFacebookClicked = false;
    private static boolean bIsNew = false;
    private TextView facebookButton;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private TextView passView;
    private TextView signButton;
    private TextView userView;

    class C23661 implements OnClickListener {
        C23661() {
        }

        public void onClick(View v) {
            MinimalSignInActivity.this.onRemindClicked();
        }
    }

    class C23672 implements OnClickListener {
        C23672() {
        }

        public void onClick(View v) {
            MinimalSignInActivity.this.onSignClicked();
        }
    }

    class C23693 implements OnClickListener {

        class C23681 implements FacebookLoginListener {
            C23681() {
            }

            public void onFacebookLoginResult(boolean success) {
                if (success) {
                    NativeManager nm = NativeManager.getInstance();
                    nm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_FB_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
                    nm.OpenProgressPopup(nm.getLanguageString(290));
                    return;
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_FB_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FACEBOOK_DECLINE, null, null, true);
            }
        }

        C23693() {
        }

        public void onClick(View v) {
            MinimalSignInActivity.bIsFacebookClicked = true;
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FB_SIGN_IN, null, null, true);
            if (MinimalSignInActivity.bIsBackFromFBScreen) {
                FacebookManager.getInstance().loginWithFacebook(MinimalSignInActivity.this, FacebookLoginType.SetToken, false);
            } else {
                FacebookManager.getInstance().loginWithFacebook(MinimalSignInActivity.this, FacebookLoginType.SignIn, true, new C23681());
            }
        }
    }

    class C23704 extends LoginCallback {
        C23704() {
        }

        public void onLogin(boolean result) {
            if (result) {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_WAZE_SIGN_IN, null, null, false);
                MinimalSignInActivity.this.startActivityForResult(new Intent(MinimalSignInActivity.this, InstallNickNameActivity.class), 0);
                return;
            }
            MinimalSignInActivity.this.signButton.setEnabled(true);
        }
    }

    public MinimalSignInActivity() {
        active = true;
    }

    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    public static boolean isActive() {
        return active;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.minimal_signin);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_LOGIN, false);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).setUpButtonDisabled();
        ((TextView) findViewById(C1283R.id.text_title2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_EXISTING_USERS_SIGN_IN));
        ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USERNAME));
        ((TextView) findViewById(C1283R.id.passwordTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_PASSWORD));
        TextView remindButton = (TextView) findViewById(C1283R.id.text_title3);
        remindButton.setPaintFlags(8);
        remindButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_I_FORGOT_MY_PASSWORD));
        remindButton.setOnClickListener(new C23661());
        this.signButton = (TextView) findViewById(C1283R.id.signin);
        this.signButton.setText(this.nativeManager.getLanguageString(93));
        this.signButton.setOnClickListener(new C23672());
        this.facebookButton = (TextView) findViewById(C1283R.id.signin_facebook);
        this.facebookButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SIGN_UP_WITH_FACEBOOK));
        this.facebookButton.setOnClickListener(new C23693());
        this.userView = (TextView) findViewById(C1283R.id.userName);
        this.passView = (TextView) findViewById(C1283R.id.password);
    }

    public void onFacebookTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        Intent intent;
        if (!InstallNativeManager.IsCleanInstallation()) {
            intent = new Intent(this, InstallFacebookPrivacyActivity.class);
            intent.putExtra("isNew", false);
            bIsNew = false;
            startActivityForResult(intent, 3000);
        } else if (MyWazeNativeManager.getInstance().getUserTypeNTV() == 1) {
            intent = new Intent(this, InstallFacebookPrivacyActivity.class);
            intent.putExtra("isNew", false);
            bIsNew = false;
            startActivityForResult(intent, 3000);
        } else {
            intent = new Intent(this, InstallFacebookPrivacyActivity.class);
            intent.putExtra("isNew", true);
            bIsNew = true;
            startActivityForResult(intent, 3000);
        }
    }

    private void onRemindClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FORGOT_PASSWORD, null, null, true);
        startActivityForResult(new Intent(this, ForgotPasswordActivity.class), 0);
    }

    private void onSignClicked() {
        if (!String.valueOf(this.userView.getText()).equals("")) {
            this.signButton.setEnabled(false);
        }
        this.mywazeManager.doLogin(String.valueOf(this.userView.getText()), String.valueOf(this.passView.getText()), String.valueOf(this.userView.getText()), new C23704());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bIsFacebookClicked = false;
        if (resultCode == 0) {
            NativeManager.getInstance().CloseProgressPopup();
        }
        if (requestCode != 3000) {
            return;
        }
        if (resultCode == 0) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FACEBOOK_BACK, null, null, true);
            bIsBackFromFBScreen = true;
            return;
        }
        setResult(-1);
        finish();
    }
}
