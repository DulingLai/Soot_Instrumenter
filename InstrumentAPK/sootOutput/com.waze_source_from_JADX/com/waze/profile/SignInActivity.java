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
import com.waze.install.GuidedTourActivity;
import com.waze.install.InstallNickNameActivity;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SignInActivity extends ActivityBase {
    private static boolean active = false;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private TextView passView;
    private TextView signButton;
    private TextView userView;

    class C23941 implements OnClickListener {
        C23941() {
        }

        public void onClick(View v) {
            SignInActivity.this.onSkipClicked();
        }
    }

    class C23952 implements OnClickListener {
        C23952() {
        }

        public void onClick(View v) {
            SignInActivity.this.onJoinClicked();
        }
    }

    class C23963 implements OnClickListener {
        C23963() {
        }

        public void onClick(View v) {
            SignInActivity.this.onRemindClicked();
        }
    }

    class C23974 implements OnClickListener {
        C23974() {
        }

        public void onClick(View v) {
            SignInActivity.this.onSignClicked();
        }
    }

    class C23985 extends LoginCallback {
        C23985() {
        }

        public void onLogin(boolean result) {
            if (result) {
                Intent intent = new Intent(SignInActivity.this, InstallNickNameActivity.class);
                intent.putExtra("IsInstallation", true);
                SignInActivity.this.startActivityForResult(intent, 0);
                return;
            }
            SignInActivity.this.signButton.setEnabled(true);
        }
    }

    public SignInActivity() {
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
        setContentView(C1283R.layout.signin);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 93, false);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).setUpButtonDisabled();
        ((TextView) findViewById(C1283R.id.text_title1)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_NEW_TO_WAZEQ));
        ((TextView) findViewById(C1283R.id.text_title2)).setText(this.nativeManager.getLanguageString(311));
        ((TextView) findViewById(C1283R.id.text1)).setText(this.nativeManager.getLanguageString(DisplayStrings.f125x998f9b23));
        ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USERNAME));
        ((TextView) findViewById(C1283R.id.passwordTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_PASSWORD));
        if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr().isSplashVisible()) {
            AppService.getMainActivity().getLayoutMgr().cancelSplash();
        }
        TextView skipButton = (TextView) findViewById(C1283R.id.skip);
        skipButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SKIP));
        if (!this.mywazeManager.isRandomUserNTV() || GuidedTourActivity.IsAlreadyCreactedUser()) {
            skipButton.setVisibility(0);
        } else {
            skipButton.setVisibility(8);
        }
        skipButton.setOnClickListener(new C23941());
        TextView joinButton = (TextView) findViewById(C1283R.id.join);
        joinButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_JOIN));
        joinButton.setOnClickListener(new C23952());
        TextView remindButton = (TextView) findViewById(C1283R.id.remindme);
        remindButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_REMIND_ME));
        remindButton.setOnClickListener(new C23963());
        this.signButton = (TextView) findViewById(C1283R.id.signin);
        this.signButton.setText(this.nativeManager.getLanguageString(93));
        this.signButton.setOnClickListener(new C23974());
        this.userView = (TextView) findViewById(C1283R.id.userName);
        this.passView = (TextView) findViewById(C1283R.id.password);
    }

    private void onSkipClicked() {
        this.mywazeManager.skipSignup();
    }

    private void onJoinClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_NEW_EXISTING_JOIN, null, null, true);
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    private void onRemindClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FORGOT_PASSWORD, null, null, true);
        startActivityForResult(new Intent(this, ForgotPasswordActivity.class), 0);
    }

    private void onSignClicked() {
        if (!String.valueOf(this.userView.getText()).equals("")) {
            this.signButton.setEnabled(false);
        }
        this.mywazeManager.doLogin(String.valueOf(this.userView.getText()), String.valueOf(this.passView.getText()), String.valueOf(this.userView.getText()), new C23985());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
