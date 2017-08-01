package com.waze.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.profile.ForgotPasswordActivity;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;

public class PhoneLoginActivity extends ActivityBase {
    private static boolean bIsBackFromFBScreen = false;
    private boolean bIsVerifyMode = false;
    private TextView mForgotYourText;
    private LinearLayout mLoginButton;
    private WazeTextView mLoginText;
    private LinearLayout mLoginWithFacebookButton;
    private WazeTextView mLoginWithFacebookText;
    private PhoneLoginFillInField mPasswordField;
    private PhoneLoginFillInField mPhoneNumberField;
    private PhoneLoginFillInField mUserNameField;

    class C22611 implements OnClickListener {

        class C22601 implements FacebookLoginListener {
            C22601() {
            }

            public void onFacebookLoginResult(boolean success) {
                if (success) {
                    NativeManager nm = NativeManager.getInstance();
                    nm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_FB_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
                    nm.SetSocialIsFirstTime(false);
                    nm.OpenProgressPopup(nm.getLanguageString(290));
                    return;
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_FB_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
            }
        }

        C22611() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FB_SIGN_IN, null, null, true);
            if (PhoneLoginActivity.bIsBackFromFBScreen) {
                FacebookManager.getInstance().loginWithFacebook(PhoneLoginActivity.this, FacebookLoginType.SetToken, false);
            } else {
                FacebookManager.getInstance().loginWithFacebook(PhoneLoginActivity.this, FacebookLoginType.SignIn, true, new C22601());
            }
        }
    }

    class C22622 implements OnClickListener {
        C22622() {
        }

        public void onClick(View v) {
            PhoneLoginActivity.this.onSignClicked();
        }
    }

    class C22633 implements OnClickListener {
        C22633() {
        }

        public void onClick(View v) {
            PhoneLoginActivity.this.onRemindClicked();
        }
    }

    class C22644 implements OnTouchListener {
        C22644() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            PhoneLoginActivity.this.setResult(0);
            PhoneLoginActivity.this.finish();
            return true;
        }
    }

    class C22666 extends LoginCallback {
        C22666() {
        }

        public void onLogin(boolean result) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_WAZER_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, result ? AnalyticsEvents.ANALYTICS_EVENT_SUCCESS : AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
            if (result) {
                NativeManager.getInstance().SetSocialIsFirstTime(false);
                if (PhoneLoginActivity.this.bIsVerifyMode) {
                    PhoneNumberSignInActivity.bIsUpgrade = true;
                } else {
                    Intent intent = new Intent(PhoneLoginActivity.this, PhoneRegisterActivity.class);
                    intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
                    intent.putExtra(PhoneNumberSignInActivity.REPORT_START, false);
                    intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_UPGRADE);
                    PhoneLoginActivity.this.startActivityForResult(intent, 5000);
                }
                PhoneLoginActivity.this.setResult(-1);
                PhoneLoginActivity.this.finish();
                return;
            }
            PhoneLoginActivity.this.mLoginButton.setEnabled(true);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        setContentView(C1283R.layout.phone_login);
        initMembers();
        this.mForgotYourText.setPaintFlags(this.mForgotYourText.getPaintFlags() | 8);
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_SHOWN, null, null, true);
        if (getIntent().getExtras() != null && getIntent().getExtras().getInt("verifyMode") > 0) {
            this.bIsVerifyMode = true;
        }
    }

    protected void onResume() {
        super.onResume();
        setOnClickListeners();
        initFieldsTexts();
        setKeyboardVisibilityListener();
    }

    private void initFieldsTexts() {
        ((TextView) findViewById(C1283R.id.loginHeaderText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ALREADY_A_WAZER).toUpperCase());
        ((TextView) findViewById(C1283R.id.loginBodyText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SIGN_IN_TO_YOUR_ACCOUNT));
        initUserNameField();
        initPasswordField();
        initPhoneNumberField();
        this.mLoginWithFacebookText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CONNECT_WITH_FACEBOOK));
        this.mLoginText.setText(NativeManager.getInstance().getLanguageString(93));
        this.mForgotYourText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FORGOT_YOUR_PASSWORD));
    }

    private void initPhoneNumberField() {
        this.mPhoneNumberField.setLabaleText(NativeManager.getInstance().getLanguageString(308));
        this.mPhoneNumberField.setInputHintText(NativeManager.getInstance().getLanguageString(307));
    }

    private void initUserNameField() {
        this.mUserNameField.setLabaleText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_USERNAME));
        this.mUserNameField.setInputHintText(NativeManager.getInstance().getLanguageString(305));
        this.mUserNameField.setEditTextDpMarginBottom(3.0f);
        this.mUserNameField.setImeOptions(5);
        this.mUserNameField.setTextGravity(19);
    }

    private void initPasswordField() {
        this.mPasswordField.setLabaleText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PASSWORD));
        this.mPasswordField.setInputHintText(NativeManager.getInstance().getLanguageString(306));
        this.mPasswordField.setEditTextDpMarginTop(3.0f);
        this.mPasswordField.setFocusableInTouchMode(false);
        this.mPasswordField.setPasswordTextType();
        this.mUserNameField.setTextGravity(19);
    }

    private void setOnClickListeners() {
        this.mLoginWithFacebookButton.setOnClickListener(new C22611());
        this.mLoginButton.setOnClickListener(new C22622());
        this.mForgotYourText.setOnClickListener(new C22633());
        this.mPhoneNumberField.setInputTextOnTouchListener(new C22644());
    }

    public void onFacebookTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        if (this.bIsVerifyMode) {
            PhoneNumberSignInActivity.bIsUpgrade = true;
        } else {
            Intent intent = new Intent(this, PhoneRegisterActivity.class);
            intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
            intent.putExtra(PhoneNumberSignInActivity.REPORT_START, false);
            intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_UPGRADE);
            startActivityForResult(intent, 5000);
        }
        setResult(-1);
        finish();
    }

    private void setKeyboardVisibilityListener() {
        final View root = findViewById(C1283R.id.rootLayout);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (root.getRootView().getHeight() - root.getHeight() <= 100) {
                }
            }
        });
    }

    private void initMembers() {
        this.mLoginButton = (LinearLayout) findViewById(C1283R.id.loginButton);
        this.mForgotYourText = (TextView) findViewById(C1283R.id.forgotText);
        this.mUserNameField = (PhoneLoginFillInField) findViewById(C1283R.id.userNameField);
        this.mPasswordField = (PhoneLoginFillInField) findViewById(C1283R.id.passwordField);
        this.mPhoneNumberField = (PhoneLoginFillInField) findViewById(C1283R.id.phoneNumberField);
        this.mLoginWithFacebookText = (WazeTextView) findViewById(C1283R.id.loginWithFacebookText);
        this.mLoginWithFacebookButton = (LinearLayout) findViewById(C1283R.id.loginWithFacebookButton);
        this.mLoginText = (WazeTextView) findViewById(C1283R.id.loginText);
    }

    private void onRemindClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FORGOT_PASSWORD, null, null, true);
        startActivityForResult(new Intent(this, ForgotPasswordActivity.class), 0);
    }

    private void onSignClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_WAZER, null, null, true);
        if (!String.valueOf(this.mUserNameField.getText()).equals("")) {
            this.mLoginButton.setEnabled(false);
        }
        MyWazeNativeManager.getInstance().doLogin(String.valueOf(this.mUserNameField.getText()), String.valueOf(this.mPasswordField.getText()), String.valueOf(this.mUserNameField.getText()), new C22666());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            NativeManager.getInstance().CloseProgressPopup();
        } else if (requestCode == 5000 && resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static float convertDpToPixels(float dp, Context context) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    public void onBackPressed() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_BACK, null, null, true);
        super.onBackPressed();
    }
}
