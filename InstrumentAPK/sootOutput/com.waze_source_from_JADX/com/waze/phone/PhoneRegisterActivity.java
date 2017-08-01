package com.waze.phone;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.phone.PhoneInputView.PhoneInputViewListener;
import com.waze.strings.DisplayStrings;

public class PhoneRegisterActivity extends ActivityBase implements PhoneInputViewListener, AuthenticateCallbackActivity {
    public static final String EXTRA_TYPE = "type_of_register";
    public static final int TYPE_FEATURE_REQUIRES_PHONE = 0;
    public static final int TYPE_ONBOARDING_WITH_PHONE = 4;
    public static final int TYPE_REGISTER_WITH_PHONE = 1;
    public static final int TYPE_UPDATE_PHONE = 2;
    private String mHashNumber;
    private ImageView mHeaderImage;
    private RelativeLayout mLoadingContainer;
    private ImageView mLoadingIcon;
    private INextActionCallback mManagerAnswerCb = new C22761();
    private Runnable mOpenValidationCodeScreenRunnable = new C22772();
    private PhoneInputView mPhoneInputView;
    private int mType;

    class C22761 implements INextActionCallback {
        C22761() {
        }

        public void setNextIntent(Intent intent) {
            if (intent != null) {
                PhoneRegisterActivity.this.startActivityForResult(intent, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
            } else {
                Logger.e("PhoneRegisterActivity: received null intent");
            }
        }

        public void setNextFragment(Fragment fragment) {
            Logger.e("PhoneRegisterActivity: received unexpected setNextFragment");
        }

        public void setNextResult(int result) {
            if (result == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                PhoneRegisterActivity.this.setResult(0);
                PhoneRegisterActivity.this.finish();
            } else if (result == -1) {
                PhoneRegisterActivity.this.setResult(-1);
                PhoneRegisterActivity.this.finish();
            } else {
                Logger.e("PhoneRegisterActivity: received unexpected result:" + result);
            }
        }

        public Context getContext() {
            return PhoneRegisterActivity.this;
        }
    }

    class C22772 implements Runnable {
        C22772() {
        }

        public void run() {
            PhoneRegisterActivity.this.openValidationScreen();
        }
    }

    class C22783 implements OnClickListener {
        C22783() {
        }

        public void onClick(View v) {
            if (PhoneRegisterActivity.this.mType == 4) {
                CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, PhoneRegisterActivity.this.mManagerAnswerCb);
            }
            PhoneRegisterActivity.this.finish();
        }
    }

    class C22794 implements Runnable {
        C22794() {
        }

        public void run() {
            ((InputMethodManager) PhoneRegisterActivity.this.getSystemService("input_method")).showSoftInput(PhoneRegisterActivity.this.mPhoneInputView.getEditText(), 0);
        }
    }

    class C22815 implements Runnable {

        class C22801 implements AnimatorListener {
            C22801() {
            }

            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                PhoneRegisterActivity.this.mLoadingIcon.setRotation(0.0f);
                PhoneRegisterActivity.this.mLoadingIcon.animate().setDuration(50000).setInterpolator(new LinearInterpolator()).rotation(36000.0f);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        }

        C22815() {
        }

        public void run() {
            PhoneRegisterActivity.this.mHeaderImage.setVisibility(8);
            PhoneRegisterActivity.this.mLoadingContainer.setVisibility(0);
            PhoneRegisterActivity.this.mLoadingIcon.animate().setInterpolator(new LinearInterpolator()).rotation(36000.0f).setDuration(50000).setListener(new C22801());
        }
    }

    class C22826 implements Runnable {
        C22826() {
        }

        public void run() {
            PhoneRegisterActivity.this.mLoadingIcon.animate().cancel();
            PhoneRegisterActivity.this.mHeaderImage.setVisibility(0);
            PhoneRegisterActivity.this.mLoadingContainer.setVisibility(8);
        }
    }

    public void onBackPressed() {
        if (this.mType == 4) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.mManagerAnswerCb);
        }
        setResult(0);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.phone_register_layout);
        this.mType = getIntent().getIntExtra(EXTRA_TYPE, 1);
        this.mPhoneInputView = (PhoneInputView) findViewById(C1283R.id.phoneInputView);
        this.mLoadingContainer = (RelativeLayout) findViewById(C1283R.id.loadingContainer);
        this.mLoadingIcon = (ImageView) findViewById(C1283R.id.imgLoader);
        this.mHeaderImage = (ImageView) findViewById(C1283R.id.imgHeader);
        findViewById(C1283R.id.btnClose).setOnClickListener(new C22783());
        this.mPhoneInputView.setListener(this);
        this.mPhoneInputView.getFocus();
        this.mPhoneInputView.setKeyboardPerformsNext();
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(C1283R.color.BlueDeep));
        }
        setDisplayStrings();
    }

    private void setDisplayStrings() {
        TextView titleLabel = (TextView) findViewById(C1283R.id.lblRegisterTitle);
        TextView detailsLabel = (TextView) findViewById(C1283R.id.lblRegisterDetails);
        TextView loadingLabel = (TextView) findViewById(C1283R.id.lblVerifying);
        String title = null;
        String description = null;
        switch (this.mType) {
            case 0:
                title = DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_PHONE_TITLE_FEATURE_REQUIRED);
                description = DisplayStrings.displayString(DisplayStrings.DS_HTML_PHONE_PRIVACY_POLICY);
                break;
            case 1:
                title = DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_PHONE_TITLE_SETTINGS);
                description = DisplayStrings.displayString(DisplayStrings.DS_HTML_PHONE_PRIVACY_POLICY);
                break;
            case 2:
                title = DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_PHONE_TITLE_UPDATE);
                description = DisplayStrings.displayString(DisplayStrings.DS_HTML_PHONE_PRIVACY_POLICY);
                break;
            case 4:
                title = DisplayStrings.displayString(DisplayStrings.DS_SIGNUP_PHONE_TITLE_CARPOOL_ONBOARDING_WITH_PHONE);
                description = DisplayStrings.displayString(DisplayStrings.DS_REGISTER_TO_USE_THIS_CARPOOL_ONBOARDING_WITH_PHONE);
                break;
        }
        titleLabel.setText(title);
        detailsLabel.setText(Html.fromHtml(description));
        detailsLabel.setMovementMethod(new LinkMovementMethod());
        loadingLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_WAITING_FOR_SMS));
    }

    protected void onResume() {
        super.onResume();
        hideVerifying();
        postDelayed(new C22794(), 500);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && data != null && data.hasExtra("index")) {
            this.mPhoneInputView.setCountryCode(data.getIntExtra("index", 0));
        } else if ((requestCode == 1111 || requestCode == 3333) && resultCode == -1) {
            completeLoginProcess();
        } else if (requestCode == CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY && resultCode == -1) {
            if (CarpoolOnboardingManager.inWazeRegister()) {
                CarpoolOnboardingManager.getInstance().getNext(-1, this.mManagerAnswerCb);
            } else {
                Logger.w("PhoneNumberActivitySignIn: onActivityResult: RESULT_OK Onboarding manager no longer in waze register");
            }
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onPhoneValidChanged(boolean isValid) {
    }

    public void onSendClick() {
        String phoneNumber = this.mPhoneInputView.getPhoneNumber();
        String countryCode = this.mPhoneInputView.getCountryCode();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_SEND, null, null, true);
        AddressBookImpl.getInstance().SetCountryID(countryCode);
        SmsReceiver.Register(AppService.getAppContext());
        this.mHashNumber = NativeManager.getInstance().SHA256(phoneNumber);
        NativeManager.getInstance().AuthPhoneNumber(phoneNumber, this.mHashNumber, 0, countryCode);
        showVerifying();
        this.mHandler.postDelayed(this.mOpenValidationCodeScreenRunnable, LoginOptionsActivity.OPEN_VALIDATION_SCREEN_DELAY);
    }

    private void showVerifying() {
        runOnUiThread(new C22815());
    }

    private void hideVerifying() {
        runOnUiThread(new C22826());
    }

    private void openValidationScreen() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FAIL_READ_SMS, null, null, true);
        SmsReceiver.UnRegister(AppService.getAppContext());
        Intent validationIntent = new Intent(this, PhoneValidationCodeActivity.class);
        validationIntent.putExtra("Hash", this.mHashNumber);
        if (this.mType == 2) {
            validationIntent.putExtra(PhoneValidationCodeActivity.EXTRA_VALIDATE_ONLY, true);
        } else if (this.mType == 0 || this.mType == 4) {
            validationIntent.putExtra(PhoneValidationCodeActivity.EXTRA_FORCE_CONTACTS_LOGIN, true);
        }
        startActivityForResult(validationIntent, 1111);
    }

    private void completeLoginProcess() {
        if (this.mType == 1) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_PHONE, null, null, true);
            MyWazeNativeManager.getInstance().setGuestUserNTV(false);
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().promptForSmartLockWhenResumed("PHONE");
            }
        } else {
            if (this.mType == 0 || this.mType == 4) {
                MyWazeNativeManager.getInstance().setGuestUserNTV(false);
            }
            if (this.mType == 2) {
                NativeManager.getInstance().setContactsAccess(false);
            }
            MyWazeNativeManager.getInstance().setContactsSignIn(true, true, null, null);
        }
        NativeManager.getInstance().signup_finished();
        if (this.mType == 4) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, this.mManagerAnswerCb);
            return;
        }
        setResult(-1);
        finish();
    }

    public void AuthenticateCallback(int type) {
        if (type != 2) {
            hideVerifying();
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
            if (this.mType == 1) {
                startActivityForResult(new Intent(this, PhoneAlreadyAWazerActivity.class), 3333);
            } else {
                completeLoginProcess();
            }
        }
    }
}
