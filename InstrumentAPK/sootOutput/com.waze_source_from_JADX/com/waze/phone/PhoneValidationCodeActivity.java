package com.waze.phone;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.settings.SettingsDialogListener;
import com.waze.settings.SettingsUtils;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.CharacterPlaceholderEditText;
import java.util.Locale;

public class PhoneValidationCodeActivity extends ActivityBase implements AuthenticateCallbackActivity {
    public static final String EXTRA_FORCE_CONTACTS_LOGIN = "force_contacts_login";
    public static final String EXTRA_VALIDATE_ONLY = "validate_only";
    public static final int RQ_CODE_ALREADY_A_WAZER = 3333;
    public static final int RQ_CODE_GIVE_UP = 3334;
    private int mAuthNumberOfRetries = 0;
    private ImageView mBackButton;
    private TextView mCodeWasSentLabel;
    private String[] mDisplayOptions;
    private TextView mEnterValidationLabel;
    private boolean mForceContactsLogin;
    private String mHash;
    private TextView mSendItAgainLabel;
    private boolean mValidateOnly;
    private CharacterPlaceholderEditText mValidationEditText;

    class C22861 implements OnClickListener {
        C22861() {
        }

        public void onClick(View v) {
            PhoneValidationCodeActivity.this.finish();
        }
    }

    class C22872 implements OnEditorActionListener {
        C22872() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 4) {
                return false;
            }
            PhoneValidationCodeActivity.this.onFinishEnteringPinCode();
            return true;
        }
    }

    class C22893 implements OnClickListener {

        class C22881 implements SettingsDialogListener {
            C22881() {
            }

            public void onComplete(int position) {
                int nType = 0;
                if (position == 0) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_RESEND_VIA_TEXT, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneValidationCodeActivity.this.mAuthNumberOfRetries, true);
                } else if (position == 1) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_RESEND_VIA_VOICE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneValidationCodeActivity.this.mAuthNumberOfRetries, true);
                    nType = 1;
                } else {
                    return;
                }
                if (PhoneValidationCodeActivity.this.mAuthNumberOfRetries == 3) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_VERIFICATION_GIVE_UP, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneValidationCodeActivity.this.mAuthNumberOfRetries, true);
                    Intent i = new Intent(PhoneValidationCodeActivity.this, PhoneVerifyNumberFailure.class);
                    i.putExtra("AuthNumberOfRetries", PhoneValidationCodeActivity.this.mAuthNumberOfRetries);
                    PhoneValidationCodeActivity.this.mAuthNumberOfRetries = 0;
                    PhoneValidationCodeActivity.this.startActivityForResult(i, 3334);
                    return;
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ENTER_VERIFICATION_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneValidationCodeActivity.this.mAuthNumberOfRetries, true);
                NativeManager.getInstance().AuthPhoneNumber(null, PhoneValidationCodeActivity.this.mHash, nType, null);
                PhoneValidationCodeActivity.this.mAuthNumberOfRetries = PhoneValidationCodeActivity.this.mAuthNumberOfRetries + 1;
            }
        }

        C22893() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_RESEND_CODE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneValidationCodeActivity.this.mAuthNumberOfRetries, true);
            SettingsUtils.showSubmenu(PhoneValidationCodeActivity.this, NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CHOOSE_METHOD), PhoneValidationCodeActivity.this.mDisplayOptions, -1, new C22881());
        }
    }

    class C22904 implements Runnable {
        C22904() {
        }

        public void run() {
            NativeManager.getInstance().CloseProgressPopup();
        }
    }

    class C22915 implements Runnable {
        C22915() {
        }

        public void run() {
            ((InputMethodManager) PhoneValidationCodeActivity.this.getSystemService("input_method")).showSoftInput(PhoneValidationCodeActivity.this.mValidationEditText, 0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.phone_validation_layout);
        this.mHash = getIntent().getStringExtra("Hash");
        this.mValidateOnly = getIntent().getBooleanExtra(EXTRA_VALIDATE_ONLY, false);
        this.mForceContactsLogin = getIntent().getBooleanExtra(EXTRA_FORCE_CONTACTS_LOGIN, false);
        this.mBackButton = (ImageView) findViewById(C1283R.id.btnClose);
        this.mEnterValidationLabel = (TextView) findViewById(C1283R.id.lblEnterValidationCode);
        this.mCodeWasSentLabel = (TextView) findViewById(C1283R.id.lblCodeWasSent);
        this.mSendItAgainLabel = (TextView) findViewById(C1283R.id.lblSendItAgain);
        this.mValidationEditText = (CharacterPlaceholderEditText) findViewById(C1283R.id.validationEditText);
        this.mValidationEditText.setCharacterLimit(4);
        if (VERSION.SDK_INT < 21) {
            this.mValidationEditText.setGravity(1);
            this.mValidationEditText.setHint("_ _ _ _");
        }
        this.mBackButton.setOnClickListener(new C22861());
        this.mValidationEditText.setTypeface(Typeface.MONOSPACE);
        this.mValidationEditText.setOnEditorActionListener(new C22872());
        this.mValidationEditText.requestFocus();
        this.mSendItAgainLabel.setOnClickListener(new C22893());
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.BlueDeep));
        }
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ENTER_VERIFICATION_SHOWN, null, null, true);
        initDisplayStrings();
    }

    private void initDisplayStrings() {
        this.mEnterValidationLabel.setText(DisplayStrings.displayString(323));
        this.mCodeWasSentLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_PIN_TEXT));
        this.mSendItAgainLabel.setText(Html.fromHtml(String.format(Locale.US, "<u>%s</u>", new Object[]{DisplayStrings.displayString(326)})));
        this.mDisplayOptions = new String[]{DisplayStrings.displayString(43), DisplayStrings.displayString(DisplayStrings.DS_RESEND_BY_VOICE), DisplayStrings.displayString(344)};
    }

    private void onFinishEnteringPinCode() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_CODE_CONTINUE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + this.mAuthNumberOfRetries, true);
        NativeManager.getInstance().OpenProgressPopup(DisplayStrings.displayString(290));
        NativeManager.getInstance().AuthPin(this.mValidationEditText.getText().toString());
    }

    public void AuthenticateCallback(int type) {
        NativeManager.getInstance().CloseProgressPopup();
        Logger.i("GilTestSignup: Got auth callback with type = " + type);
        if (type == 0) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
            setResult(-1);
            finish();
        } else if (type == 6) {
            MyWazeNativeManager.getInstance().setContactsSignIn(false, false, null, null);
            setResult(-1);
            finish();
        } else if (type == 3) {
            if (this.mValidateOnly || this.mForceContactsLogin) {
                setResult(-1);
                finish();
                return;
            }
            startActivityForResult(new Intent(this, PhoneAlreadyAWazerActivity.class), 3333);
        } else if (type == 2) {
            NativeManager.getInstance().OpenProgressIconPopup(DisplayStrings.displayString(325), "bigblue_x_icon");
            postDelayed(new C22904(), 3000);
        }
    }

    protected void onResume() {
        super.onResume();
        postDelayed(new C22915(), 500);
    }

    public void onFacebookTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        setResult(-1);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
    }
}
