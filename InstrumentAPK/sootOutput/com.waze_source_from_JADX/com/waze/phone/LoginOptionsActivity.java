package com.waze.phone;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.install.SmartLockManager;
import com.waze.install.SmartLockManager.SmartLockEventListener;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.phone.PhoneInputView.PhoneInputViewListener;
import com.waze.profile.ForgotPasswordActivity;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.PixelMeasure;
import java.util.Locale;

public class LoginOptionsActivity extends ActivityBase implements SmartLockEventListener, AuthenticateCallbackActivity {
    public static final String EXTRA_IS_INSTALL_FLOW = "is_install_flow";
    public static final long OPEN_VALIDATION_SCREEN_DELAY = 15000;
    public static final int PHONE_VALIDATION_FAILED = 5;
    public static final int PHONE_VALIDATION_NEED_CONTACTS = 3;
    public static final int PHONE_VALIDATION_NEED_PIN = 2;
    public static final int PHONE_VALIDATION_SUCCESS = 0;
    public static final int PHONE_VALIDATION_SUCCESS_TYRING_RECOVERY = 6;
    public static final int RQ_VERIFY = 1111;
    private FrameLayout mFacebookLoginButton;
    private TextView mFacebookLoginLabel;
    private TextView mForgotYourPasswordLabel;
    private TextView mGooglePrivacyLabel;
    private Handler mHandler = new Handler();
    private String mHashNumber;
    private boolean mIsInstallFlow;
    private TextView mLoginTitleLabel;
    private LinearLayout mLoginWithUsernameContainer;
    private TextView mLoginWithUsernameLabel;
    private FrameLayout mLoginWithUsernameLinkContainer;
    private TextView mLoginWithUsernameLinkLabel;
    private Runnable mOpenValidationCodeScreenRunnable = new C22421();
    private TextView mOrLabel;
    private EditText mPasswordEditText;
    private TextView mPhoneDetailsLabel;
    private PhoneInputView mPhoneInputView;
    private FrameLayout mPhoneLoginButton;
    private TextView mPhoneLoginLabel;
    private ScrollView mScrollContainer;
    private RelativeLayout mTitleBarContainer;
    private EditText mUsernameEditText;
    private FrameLayout mUsernameLoginButton;
    private TextView mUsernameLoginLabel;
    private boolean mUsernameScrollOccurred;

    class C22421 implements Runnable {
        C22421() {
        }

        public void run() {
            LoginOptionsActivity.this.openValidationScreen();
        }
    }

    class C22432 implements Runnable {
        C22432() {
        }

        public void run() {
            int facebookButtonBottom = LoginOptionsActivity.this.mFacebookLoginButton.getBottom() + ((LayoutParams) LoginOptionsActivity.this.mFacebookLoginButton.getLayoutParams()).bottomMargin;
            int scrollViewHeight = LoginOptionsActivity.this.mScrollContainer.getMeasuredHeight();
            LayoutParams linkContainerParams = (LayoutParams) LoginOptionsActivity.this.mLoginWithUsernameLinkContainer.getLayoutParams();
            linkContainerParams.height = Math.max(LoginOptionsActivity.this.mLoginWithUsernameLinkContainer.getMeasuredHeight(), scrollViewHeight - facebookButtonBottom);
            LoginOptionsActivity.this.mLoginWithUsernameLinkContainer.setLayoutParams(linkContainerParams);
        }
    }

    class C22464 implements OnClickListener {

        class C22451 implements Runnable {
            C22451() {
            }

            public void run() {
                LoginOptionsActivity.this.mScrollContainer.fullScroll(130);
            }
        }

        C22464() {
        }

        public void onClick(View v) {
            LoginOptionsActivity.this.mLoginWithUsernameLinkContainer.setVisibility(8);
            LoginOptionsActivity.this.mLoginWithUsernameContainer.setVisibility(0);
            LoginOptionsActivity.this.mScrollContainer.post(new C22451());
        }
    }

    class C22475 implements OnClickListener {
        C22475() {
        }

        public void onClick(View v) {
            LoginOptionsActivity.this.onFacebookLoginClick();
        }
    }

    class C22486 implements OnClickListener {
        C22486() {
        }

        public void onClick(View v) {
            LoginOptionsActivity.this.onUsernameLoginClick();
        }
    }

    class C22497 implements PhoneInputViewListener {
        C22497() {
        }

        public void onPhoneValidChanged(boolean isValid) {
            LoginOptionsActivity.this.setPhoneLoginButtonEnabled(isValid);
        }

        public void onSendClick() {
        }
    }

    class C22508 implements OnClickListener {
        C22508() {
        }

        public void onClick(View v) {
            LoginOptionsActivity.this.onPhoneLoginClick();
        }
    }

    class C22519 implements OnClickListener {
        C22519() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FORGOT_PASSWORD, null, null, true);
            LoginOptionsActivity.this.startActivity(new Intent(LoginOptionsActivity.this, ForgotPasswordActivity.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean z;
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.login_options_layout);
        this.mIsInstallFlow = getIntent().getBooleanExtra(EXTRA_IS_INSTALL_FLOW, false);
        this.mTitleBarContainer = (RelativeLayout) findViewById(C1283R.id.titleBar);
        this.mScrollContainer = (ScrollView) findViewById(C1283R.id.contentScrollView);
        this.mLoginTitleLabel = (TextView) findViewById(C1283R.id.lblLoginTitle);
        this.mPhoneDetailsLabel = (TextView) findViewById(C1283R.id.lblPhoneLoginDetails);
        this.mPhoneInputView = (PhoneInputView) findViewById(C1283R.id.phoneInputView);
        this.mPhoneLoginButton = (FrameLayout) findViewById(C1283R.id.btnPhoneLogin);
        this.mPhoneLoginLabel = (TextView) findViewById(C1283R.id.lblPhoneLogin);
        this.mOrLabel = (TextView) findViewById(C1283R.id.lblOr);
        this.mFacebookLoginButton = (FrameLayout) findViewById(C1283R.id.btnFacebookLogin);
        this.mFacebookLoginLabel = (TextView) findViewById(C1283R.id.lblFacebookLogin);
        this.mLoginWithUsernameLinkContainer = (FrameLayout) findViewById(C1283R.id.loginWithUsernameLinkContainer);
        this.mLoginWithUsernameLinkLabel = (TextView) findViewById(C1283R.id.lblLoginWithUsernameLink);
        this.mLoginWithUsernameContainer = (LinearLayout) findViewById(C1283R.id.usernameLoginContainer);
        this.mLoginWithUsernameLabel = (TextView) findViewById(C1283R.id.lblLoginWithUsername);
        this.mUsernameEditText = (EditText) findViewById(C1283R.id.usernameEditText);
        this.mPasswordEditText = (EditText) findViewById(C1283R.id.passwordEditText);
        this.mForgotYourPasswordLabel = (TextView) findViewById(C1283R.id.lblForgotYourPassword);
        this.mUsernameLoginButton = (FrameLayout) findViewById(C1283R.id.btnUsernameLogin);
        this.mUsernameLoginLabel = (TextView) findViewById(C1283R.id.lblUsernameLogin);
        this.mGooglePrivacyLabel = (TextView) findViewById(C1283R.id.lblGooglePrivacyPolicy);
        DisplayUtils.runOnLayout(this.mScrollContainer, new C22432());
        final View btnBack = findViewById(C1283R.id.btnBack);
        View btnClose = findViewById(C1283R.id.btnClose);
        OnClickListener closeListener = new OnClickListener() {
            public void onClick(View v) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LOGIN_SCREEN_EXIT_CLICKED).addParam("INFO", v == btnBack ? "BACK" : "CLOSE").send();
                LoginOptionsActivity.this.createRandomUser();
                LoginOptionsActivity.this.finish();
            }
        };
        btnClose.setOnClickListener(closeListener);
        btnBack.setOnClickListener(closeListener);
        this.mLoginWithUsernameLinkLabel.setOnClickListener(new C22464());
        this.mFacebookLoginButton.setOnClickListener(new C22475());
        this.mUsernameLoginButton.setOnClickListener(new C22486());
        this.mPhoneInputView.setListener(new C22497());
        setPhoneLoginButtonEnabled(false);
        this.mPhoneLoginButton.setOnClickListener(new C22508());
        this.mForgotYourPasswordLabel.setOnClickListener(new C22519());
        TextWatcher usernamePasswordWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                boolean isEnabled = (TextUtils.isEmpty(LoginOptionsActivity.this.mUsernameEditText.getText().toString()) || TextUtils.isEmpty(LoginOptionsActivity.this.mPasswordEditText.getText().toString())) ? false : true;
                LoginOptionsActivity.this.setUsernameLoginButtonEnabled(isEnabled);
            }
        };
        OnEditorActionListener editorActionListener = new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 5) {
                    return false;
                }
                boolean isEnabled;
                String username = LoginOptionsActivity.this.mUsernameEditText.getText().toString();
                String password = LoginOptionsActivity.this.mPasswordEditText.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    isEnabled = false;
                } else {
                    isEnabled = true;
                }
                if (isEnabled) {
                    LoginOptionsActivity.this.mUsernameEditText.clearFocus();
                    LoginOptionsActivity.this.mPasswordEditText.clearFocus();
                    ((InputMethodManager) LoginOptionsActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(LoginOptionsActivity.this.mUsernameEditText.getWindowToken(), 0);
                    LoginOptionsActivity.this.onUsernameLoginClick();
                    return true;
                } else if (TextUtils.isEmpty(password) && LoginOptionsActivity.this.mUsernameEditText.hasFocus()) {
                    LoginOptionsActivity.this.mPasswordEditText.requestFocus();
                    return true;
                } else if (!TextUtils.isEmpty(username) || !LoginOptionsActivity.this.mPasswordEditText.hasFocus()) {
                    return true;
                } else {
                    LoginOptionsActivity.this.mUsernameEditText.requestFocus();
                    return true;
                }
            }
        };
        OnFocusChangeListener usernamePasswordFocusListener = new OnFocusChangeListener() {

            class C22411 implements Runnable {
                C22411() {
                }

                public void run() {
                    LoginOptionsActivity.this.mScrollContainer.fullScroll(130);
                }
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !LoginOptionsActivity.this.mUsernameScrollOccurred) {
                    LoginOptionsActivity.this.mUsernameScrollOccurred = true;
                    LoginOptionsActivity.this.mScrollContainer.postDelayed(new C22411(), 300);
                }
            }
        };
        this.mUsernameEditText.setOnFocusChangeListener(usernamePasswordFocusListener);
        this.mPasswordEditText.setOnFocusChangeListener(usernamePasswordFocusListener);
        this.mUsernameEditText.addTextChangedListener(usernamePasswordWatcher);
        this.mPasswordEditText.addTextChangedListener(usernamePasswordWatcher);
        this.mUsernameEditText.setOnEditorActionListener(editorActionListener);
        this.mPasswordEditText.setOnEditorActionListener(editorActionListener);
        setUsernameLoginButtonEnabled(false);
        if (VERSION.SDK_INT >= 23) {
            this.mTitleBarContainer.setElevation((float) PixelMeasure.dp(8));
        }
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.BlueDeep));
        }
        initDisplayStrings();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_WIZARD_SHOWN, null, null, true);
        SmartLockManager instance = SmartLockManager.getInstance();
        if (this.mIsInstallFlow) {
            z = false;
        } else {
            z = true;
        }
        instance.signIn(this, this, z);
    }

    private void initDisplayStrings() {
        this.mLoginTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_LOGIN_OPTIONS_TITLE));
        this.mUsernameLoginLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_LOGIN_OPTIONS_BUTTON));
        this.mPhoneLoginLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_LOGIN_OPTIONS_BUTTON));
        this.mFacebookLoginLabel.setText(DisplayStrings.displayString(309));
        this.mPhoneDetailsLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_LOGIN_OPTIONS_PHONE_DESCRIPTION));
        this.mUsernameEditText.setHint(DisplayStrings.displayString(DisplayStrings.DS_USERNAME));
        this.mPasswordEditText.setHint(DisplayStrings.displayString(DisplayStrings.DS_PASSWORD));
        this.mLoginWithUsernameLinkLabel.setText(Html.fromHtml(String.format(Locale.US, "<u>%s</u>", new Object[]{DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_LOG_IN_WITH_USERNAME)})));
        this.mForgotYourPasswordLabel.setText(Html.fromHtml(String.format(Locale.US, "<u>%s</u>", new Object[]{DisplayStrings.displayString(DisplayStrings.DS_FORGOT_YOUR_PASSWORD)})));
        this.mOrLabel.setText(Html.fromHtml(String.format(Locale.US, "<i> %s </i>", new Object[]{DisplayStrings.displayString(DisplayStrings.DS_OR)})));
        this.mLoginWithUsernameLabel.setText(Html.fromHtml(String.format(Locale.US, "<i> %s </i>", new Object[]{DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_LOG_IN_WITH_USERNAME)})));
        this.mGooglePrivacyLabel.setText(Html.fromHtml(DisplayStrings.displayString(DisplayStrings.DS_HTML_PHONE_PRIVACY_POLICY)));
        this.mGooglePrivacyLabel.setMovementMethod(new LinkMovementMethod());
    }

    private void onUsernameLoginClick() {
        if (this.mUsernameLoginButton.isEnabled()) {
            setUsernameLoginButtonEnabled(false);
            this.mUsernameEditText.setEnabled(false);
            this.mPasswordEditText.setEnabled(false);
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_WAZER, null, null, true);
            String username = String.valueOf(this.mUsernameEditText.getText());
            MyWazeNativeManager.getInstance().doLogin(username, String.valueOf(this.mPasswordEditText.getText()), username, new LoginCallback() {
                public void onLogin(boolean result) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_WAZER_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, result ? AnalyticsEvents.ANALYTICS_EVENT_SUCCESS : AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
                    if (result) {
                        NativeManager.getInstance().SetSocialIsFirstTime(false);
                        NativeManager.getInstance().signup_finished();
                        if (AppService.getMainActivity() != null) {
                            AppService.getMainActivity().promptForSmartLockWhenResumed(Logger.TAG);
                        }
                        LoginOptionsActivity.this.setResult(-1);
                        LoginOptionsActivity.this.finish();
                        return;
                    }
                    LoginOptionsActivity.this.setUsernameLoginButtonEnabled(true);
                    LoginOptionsActivity.this.mUsernameEditText.setEnabled(true);
                    LoginOptionsActivity.this.mPasswordEditText.setEnabled(true);
                }
            });
        }
    }

    private void onPhoneLoginClick() {
        if (this.mPhoneLoginButton.isEnabled()) {
            setPhoneLoginButtonEnabled(false);
            String phoneNumber = this.mPhoneInputView.getPhoneNumber();
            String countryCode = this.mPhoneInputView.getCountryCode();
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_SEND, null, null, true);
                AddressBookImpl.getInstance().SetCountryID(countryCode);
                this.mHashNumber = NativeManager.getInstance().SHA256(phoneNumber);
                NativeManager.getInstance().AuthPhoneNumber(phoneNumber, this.mHashNumber, 0, countryCode);
                NativeManager.getInstance().OpenProgressPopup(DisplayStrings.displayString(DisplayStrings.DS_WAITING_FOR_SMS));
                SmsReceiver.Register(AppService.getAppContext());
                this.mHandler.postDelayed(this.mOpenValidationCodeScreenRunnable, OPEN_VALIDATION_SCREEN_DELAY);
            }
        }
    }

    private void onFacebookLoginClick() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FB_SIGN_IN, null, null, true);
        FacebookManager.getInstance().loginWithFacebook((ActivityBase) this, FacebookLoginType.SignIn, true, new FacebookLoginListener() {
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
        });
    }

    public void onFacebookTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        NativeManager.getInstance().signup_finished();
        if (AppService.getMainActivity() != null) {
            AppService.getMainActivity().promptForSmartLockWhenResumed("FB");
        }
        setResult(-1);
        finish();
    }

    private void setUsernameLoginButtonEnabled(boolean isEnabled) {
        this.mUsernameLoginButton.setAlpha(isEnabled ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mUsernameLoginButton.setEnabled(isEnabled);
    }

    private void setPhoneLoginButtonEnabled(boolean isEnabled) {
        this.mPhoneLoginButton.setAlpha(isEnabled ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mPhoneLoginButton.setEnabled(isEnabled);
    }

    public void onBackPressed() {
        createRandomUser();
        super.onBackPressed();
    }

    private void createRandomUser() {
        NativeManager.getInstance().SignUplogAnalytics("SKIP", null, null, true);
        MyWazeNativeManager.getInstance().skipSignup();
        NativeManager.getInstance().signup_finished();
        setResult(-1);
    }

    public void AuthenticateCallback(int type) {
        if (type != 2) {
            NativeManager.getInstance().CloseProgressPopup();
        }
        Logger.i("GilTestSignup: Got auth callback with type = " + type);
        if (type == 0) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
            this.mHandler.removeCallbacks(this.mOpenValidationCodeScreenRunnable);
            completeLoginProcess();
        } else if (type == 5) {
            this.mHandler.removeCallbacks(this.mOpenValidationCodeScreenRunnable);
            MsgBox.openMessageBox(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
        } else if (type == 3) {
            this.mHandler.removeCallbacks(this.mOpenValidationCodeScreenRunnable);
            startActivityForResult(new Intent(this, PhoneAlreadyAWazerActivity.class), 3333);
        }
    }

    private void completeLoginProcess() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_PHONE, null, null, true);
        NativeManager.getInstance().signup_finished();
        MyWazeNativeManager.getInstance().setContactsSignIn(false, false, null, null);
        MyWazeNativeManager.getInstance().setGuestUserNTV(false);
        if (AppService.getMainActivity() != null) {
            AppService.getMainActivity().promptForSmartLockWhenResumed("PHONE");
        }
        setResult(-1);
        finish();
    }

    private void openValidationScreen() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FAIL_READ_SMS, null, null, true);
        NativeManager.getInstance().CloseProgressPopup();
        SmsReceiver.UnRegister(AppService.getAppContext());
        Intent validationIntent = new Intent(this, PhoneValidationCodeActivity.class);
        validationIntent.putExtra("Hash", this.mHashNumber);
        startActivityForResult(validationIntent, 1111);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean z = false;
        if (requestCode == 1000 && data != null && data.hasExtra("index")) {
            this.mPhoneInputView.setCountryCode(data.getIntExtra("index", 0));
        } else if ((requestCode == 1111 || requestCode == 3333) && resultCode == -1) {
            completeLoginProcess();
        }
        SmartLockManager.getInstance().credentialsResolutionActivityResult(requestCode, resultCode, data, this);
        if (this.mPhoneInputView.getPhoneNumber() != null) {
            z = true;
        }
        setPhoneLoginButtonEnabled(z);
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onDestroy() {
        SmsReceiver.UnRegister(AppService.getAppContext());
        this.mHandler.removeCallbacks(this.mOpenValidationCodeScreenRunnable);
        super.onDestroy();
    }

    public void onSignInSuccess() {
        NativeManager.getInstance().signup_finished();
        setResult(-1);
        finish();
    }

    public void onSignInFailure() {
    }
}
