package com.waze.phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.settings.SettingsDialogListener;
import com.waze.settings.SettingsUtils;
import com.waze.strings.DisplayStrings;

public class PhoneVerifyYourNumbersActivity extends ActivityBase implements AuthenticateCallbackActivity {
    private static final int RQ_CODE_ALREADY_A_WAZER = 3333;
    private static final int RQ_CODE_GIVE_UP = 3334;
    public static boolean bIsForCarpool = false;
    private boolean bIsResend = false;
    private int mAuthNumberOfRetries = 0;
    private LinearLayout mContinueButton;
    private String[] mDisplayOptions = new String[3];
    private String mHash = null;
    private EditText mVerificationCodeEditText;
    private TextView mVerifyByPhoneCallText;

    class C23011 implements OnClickListener {
        C23011() {
        }

        public void onClick(View v) {
            NativeManager nm = NativeManager.getInstance();
            nm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_CODE_CONTINUE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries, true);
            nm.OpenProgressPopup(nm.getLanguageString(290));
            nm.AuthPin(PhoneVerifyYourNumbersActivity.this.getVerificationCode());
        }
    }

    class C23032 implements OnClickListener {

        class C23021 implements SettingsDialogListener {
            C23021() {
            }

            public void onComplete(int position) {
                int nType = 0;
                if (position == 0) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_RESEND_VIA_TEXT, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries, true);
                } else if (position == 1) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_RESEND_VIA_VOICE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries, true);
                    nType = 1;
                } else {
                    return;
                }
                if (PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries == 3) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_VERIFICATION_GIVE_UP, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries, true);
                    Intent i = new Intent(PhoneVerifyYourNumbersActivity.this, PhoneVerifyNumberFailure.class);
                    i.putExtra("AuthNumberOfRetries", PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries);
                    PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries = 0;
                    PhoneVerifyYourNumbersActivity.this.startActivityForResult(i, 3334);
                    return;
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ENTER_VERIFICATION_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries, true);
                NativeManager.getInstance().AuthPhoneNumber(null, PhoneVerifyYourNumbersActivity.this.mHash, nType, null);
                PhoneVerifyYourNumbersActivity.this.bIsResend = true;
                PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries = PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries + 1;
            }
        }

        C23032() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_RESEND_CODE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyYourNumbersActivity.this.mAuthNumberOfRetries, true);
            SettingsUtils.showSubmenu(PhoneVerifyYourNumbersActivity.this, NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CHOOSE_METHOD), PhoneVerifyYourNumbersActivity.this.mDisplayOptions, -1, new C23021());
        }
    }

    class C23043 implements OnEditorActionListener {
        C23043() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 4) {
                return false;
            }
            PhoneVerifyYourNumbersActivity.this.mContinueButton.performClick();
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        setContentView(C1283R.layout.phone_verify_your_numbers);
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ENTER_VERIFICATION_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + this.mAuthNumberOfRetries, true);
        this.mHash = getIntent().getStringExtra("Hash");
        bIsForCarpool = getIntent().getBooleanExtra("bIsForCarpool", false);
        initMembers();
        setListeners();
        setOnClickListeners();
        initFieldsTexts();
    }

    private void initFieldsTexts() {
        ((TextView) findViewById(C1283R.id.verifyHeaderText)).setText(NativeManager.getInstance().getLanguageString(323).toUpperCase());
        ((TextView) findViewById(C1283R.id.verifyBodyText)).setText(NativeManager.getInstance().getLanguageString(324));
        ((TextView) findViewById(C1283R.id.verifyByPhoneCallText)).setText(NativeManager.getInstance().getLanguageString(326));
        ((TextView) findViewById(C1283R.id.continueText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NEXT));
    }

    private String getVerificationCode() {
        return this.mVerificationCodeEditText.getText().toString();
    }

    public void AuthenticateCallback(int nType) {
        if (nType == 0) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
            if (!bIsForCarpool) {
                MainActivity.bToOpenAccountPopup = true;
            }
            setResult(-1);
            finish();
        } else if (nType == 6) {
            MyWazeNativeManager.getInstance().setContactsSignIn(false, PhoneNumberSignInActivity.bIsUpgrade, null, null);
            setResult(-1);
            finish();
        } else if (nType == 2) {
            NativeManager.getInstance().CloseProgressPopup();
            if (this.bIsResend) {
                this.bIsResend = false;
                return;
            }
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_INVALID_CODE_TRY_AGAIN), true);
        } else if (nType == 3) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_EXISTED, null, null, true);
            NativeManager.getInstance().CloseProgressPopup();
            if (PhoneNumberSignInActivity.bIsUpgrade) {
                MyWazeNativeManager.getInstance().setContactsSignIn(true, true, null, null);
            } else {
                startActivityForResult(new Intent(this, PhoneAlreadyAWazerActivity.class), 3333);
            }
        } else {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_SEND_FAILED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + this.mAuthNumberOfRetries, true);
            NativeManager.getInstance().CloseProgressPopup();
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
        }
    }

    public void onPinTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        if (!bIsForCarpool) {
            MainActivity.bToOpenAccountPopup = true;
        }
        setResult(-1);
        finish();
    }

    private void setOnClickListeners() {
        this.mContinueButton.setOnClickListener(new C23011());
        this.mVerifyByPhoneCallText.setOnClickListener(new C23032());
    }

    private void initMembers() {
        this.mVerificationCodeEditText = (EditText) findViewById(C1283R.id.verificationCodeEditText);
        this.mVerificationCodeEditText.setSelection(0);
        this.mVerificationCodeEditText.setHint(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ENTER_CODE));
        this.mDisplayOptions[0] = NativeManager.getInstance().getLanguageString(43);
        this.mDisplayOptions[1] = NativeManager.getInstance().getLanguageString(DisplayStrings.DS_RESEND_BY_VOICE);
        this.mDisplayOptions[2] = NativeManager.getInstance().getLanguageString(344);
        this.mContinueButton = (LinearLayout) findViewById(C1283R.id.continueButton);
        this.mVerifyByPhoneCallText = (TextView) findViewById(C1283R.id.verifyByPhoneCallText);
        this.mVerifyByPhoneCallText.setPaintFlags(this.mVerifyByPhoneCallText.getPaintFlags() | 8);
    }

    private void setListeners() {
        this.mVerificationCodeEditText.setOnEditorActionListener(new C23043());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Logger.TAG, "phoneVerifyYourAccount onActRes requestCode=" + requestCode + " resultCode=" + resultCode + " Intent=" + data);
        if (resultCode == -1) {
            setResult(-1);
            finish();
        } else {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ENTER_VERIFICATION_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + this.mAuthNumberOfRetries, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_CODE_BACK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + this.mAuthNumberOfRetries, true);
        this.mAuthNumberOfRetries = 0;
        super.onBackPressed();
    }
}
