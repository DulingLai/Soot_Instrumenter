package com.waze.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class RegisterActivity extends ActivityBase {
    private TextView EmailBody;
    private TextView EmailTitile;
    private TextView emailView;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private TextView nextButton;
    private TextView nickView;
    private TextView passView;
    private TextView userView;

    class C23921 implements OnClickListener {
        C23921() {
        }

        public void onClick(View v) {
            String username = String.valueOf(RegisterActivity.this.userView.getText());
            String password = String.valueOf(RegisterActivity.this.passView.getText());
            if (RegisterActivity.this.mywazeManager.registerUser(username, password, password, String.valueOf(RegisterActivity.this.nickView.getText()), String.valueOf(RegisterActivity.this.emailView.getText()), false, RegisterActivity.this)) {
                RegisterActivity.this.nextButton.setEnabled(false);
            }
        }
    }

    class C23932 implements OnFocusChangeListener {
        C23932() {
        }

        public void onFocusChange(View v, boolean focused) {
            if (!focused) {
                String nickname = String.valueOf(RegisterActivity.this.nickView.getText());
                if (nickname == null || nickname.length() == 0) {
                    RegisterActivity.this.nickView.setText(String.valueOf(RegisterActivity.this.userView.getText()));
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.register);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_PROFILE, false);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).setUpButtonDisabled();
        ((TextView) findViewById(C1283R.id.yourDetails)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_DETAILS));
        ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USERNAME));
        ((TextView) findViewById(C1283R.id.passwordTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_PASSWORD));
        ((TextView) findViewById(C1283R.id.nicknameTitle)).setText(this.nativeManager.getLanguageString(88));
        ((TextView) findViewById(C1283R.id.emailTitle)).setText(this.nativeManager.getLanguageString(383));
        this.nextButton = (TextView) findViewById(C1283R.id.next);
        this.nextButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_NEXT));
        this.userView = (TextView) findViewById(C1283R.id.userName);
        this.passView = (TextView) findViewById(C1283R.id.password);
        this.EmailTitile = (TextView) findViewById(C1283R.id.RecoveryEmailText);
        this.EmailTitile.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SIGN_UP_EMAIL_ADDRESS));
        this.nickView = (TextView) findViewById(C1283R.id.nickName);
        this.emailView = (TextView) findViewById(C1283R.id.email);
        this.EmailBody = (TextView) findViewById(C1283R.id.RecoveryEmailBodyText);
        this.EmailBody.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SIGN_UP_EMAIL_BODY1) + "\n" + this.nativeManager.getLanguageString(DisplayStrings.DS_SIGN_UP_EMAIL_BODY2));
        this.nextButton.setOnClickListener(new C23921());
        this.userView.setOnFocusChangeListener(new C23932());
    }

    public void openWelcome() {
        AppService.showActivity(new Intent(AppService.getAppContext(), WelcomeActivity.class));
        finish();
    }

    public void OnResponse(boolean bIsSuccess) {
        if (bIsSuccess) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_JOIN_NEXT, null, null, true);
            setResult(3);
            return;
        }
        this.nextButton.setEnabled(true);
    }
}
