package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolPayee;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CarpoolUtils;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class SettingsCarpoolPaymentsActivity extends ActivityBase {
    private static final int ADDED_HOME_RC = 2487;
    public static final String DETAILS = "DETAILS";
    private static final int PAYMENT_CAR_POOL_RESULT_RC = 2488;
    private static final int PAYMENT_PAYONEER_RESULT_RC = 2486;
    public static final int err_payout_provider_unavailable = 1101;
    public static final int err_payout_user_country_unavailable = 1102;
    public static final int err_payout_user_country_unsupported = 1103;
    CarpoolNativeManager cpnm;
    private boolean mAccountIsSet = false;
    private TextView mAddBankAccountView;
    private WazeTextView mBankAccountSetView;
    private TextView mBankAccountView;
    private View mBankAvailView;
    boolean mConfigPayoneerIsOn = true;
    boolean mExit = false;
    private WazeTextView mLegalView;
    private View mNoBankView;
    private NativeManager mNtMgr;
    private WazeTextView mPaidBalanceView;
    CarpoolPayee mPayee = null;
    CarpoolUserData mProfile;
    private ProgressAnimation mProgressView;
    String mUrl = null;
    int mUrlRc;
    boolean mUrlReceived = false;
    int mUrlRetries = 0;

    class C26381 implements OnClickListener {
        C26381() {
        }

        public void onClick(View v) {
            SettingsCarpoolPaymentsActivity.this.finishResultOk();
        }
    }

    class C26392 implements OnClickListener {
        C26392() {
        }

        public void onClick(View v) {
            SettingsCarpoolPaymentsActivity.this.startActivityForResult(new Intent(SettingsCarpoolPaymentsActivity.this, SettingsCarpoolPaymentsBankActivity.class), 2488);
        }
    }

    class C26403 implements OnClickListener {
        C26403() {
        }

        public void onClick(View v) {
            if (SettingsCarpoolPaymentsActivity.this.mProfile != null && (SettingsCarpoolPaymentsActivity.this.mProfile.home_missing || SettingsCarpoolPaymentsActivity.this.mProfile.inferredHomeConflict())) {
                Logger.w("SettingsCarpoolPaymentsActivity: home_missing=" + SettingsCarpoolPaymentsActivity.this.mProfile.home_missing + "; inferredHomeConflict=" + SettingsCarpoolPaymentsActivity.this.mProfile.inferredHomeConflict() + "; Requesting home address update");
                SettingsCarpoolPaymentsActivity.this.requestHomeAddress();
            } else if (SettingsCarpoolPaymentsActivity.this.mUrlRc == 1102 || SettingsCarpoolPaymentsActivity.this.mUrlRc == 1103) {
                Logger.w("SettingsCarpoolPaymentsActivity: mUrlRc=" + SettingsCarpoolPaymentsActivity.this.mUrlRc + "; Requesting home address update");
                SettingsCarpoolPaymentsActivity.this.requestHomeAddress();
            } else {
                Logger.d("SettingsCarpoolPaymentsActivity: Home address seems to be ok: home_missing=" + SettingsCarpoolPaymentsActivity.this.mProfile.home_missing + "; inferredHomeConflict=" + SettingsCarpoolPaymentsActivity.this.mProfile.inferredHomeConflict() + "; URL RC: " + SettingsCarpoolPaymentsActivity.this.mUrlRc);
                CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_URL, SettingsCarpoolPaymentsActivity.this.mHandler);
                CarpoolNativeManager.getInstance().getPayeeForm();
                SettingsCarpoolPaymentsActivity.this.openPayeeUrl(true);
            }
        }
    }

    class C26414 implements DialogInterface.OnClickListener {
        C26414() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 1) {
                Intent intent = new Intent(SettingsCarpoolPaymentsActivity.this, AddHomeWorkActivity.class);
                intent.putExtra("carpool", false);
                intent.putExtra("indicate_home", true);
                SettingsCarpoolPaymentsActivity.this.startActivityForResult(intent, 2487);
            }
        }
    }

    class C26425 implements Runnable {
        C26425() {
        }

        public void run() {
            if (!SettingsCarpoolPaymentsActivity.this.mExit) {
                SettingsCarpoolPaymentsActivity.this.openPayeeUrl(false);
            }
        }
    }

    class C26436 implements Runnable {
        C26436() {
        }

        public void run() {
            if (!SettingsCarpoolPaymentsActivity.this.mExit) {
                SettingsCarpoolPaymentsActivity.this.cpnm.getPayee();
                SettingsCarpoolPaymentsActivity.this.cpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE, SettingsCarpoolPaymentsActivity.this.mHandler);
            }
        }
    }

    class C26447 implements Runnable {
        C26447() {
        }

        public void run() {
            SettingsCarpoolPaymentsActivity.this.mNtMgr.CloseProgressPopup();
        }
    }

    class C26458 implements Runnable {
        C26458() {
        }

        public void run() {
            SettingsCarpoolPaymentsActivity.this.mNtMgr.CloseProgressPopup();
        }
    }

    class C26469 implements Runnable {
        C26469() {
        }

        public void run() {
            SettingsCarpoolPaymentsActivity.this.mNtMgr.CloseProgressPopup();
        }
    }

    private class ForDisplay {
        String account;
        String currency;
        String paid;
        String unpaid;

        private ForDisplay() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mExit = false;
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PAYMENT_SHOWN);
        this.mNtMgr = AppService.getNativeManager();
        setContentView(C1283R.layout.settings_carpool_payments);
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.init((Activity) this, (int) DisplayStrings.DS_CARPOOL_PAYMENTS_TITLE);
        titleBar.setOnClickCloseListener(new C26381());
        this.cpnm = CarpoolNativeManager.getInstance();
        this.mProfile = this.cpnm.getCarpoolProfileNTV();
        this.mPayee = this.cpnm.getCachedPayeeNTV();
        this.mPaidBalanceView = (WazeTextView) findViewById(C1283R.id.setCarPaidBalanceText);
        this.mBankAvailView = findViewById(C1283R.id.bankButtonLayout);
        this.mNoBankView = findViewById(C1283R.id.noBankButtonLayout);
        this.mBankAccountView = (TextView) findViewById(C1283R.id.bankButtonLayoutDetails);
        this.mAddBankAccountView = (TextView) findViewById(C1283R.id.bankAddDetails);
        this.mProgressView = (ProgressAnimation) findViewById(C1283R.id.bankButtonProg);
        this.mLegalView = (WazeTextView) findViewById(C1283R.id.setCarPayLegalText);
        this.mConfigPayoneerIsOn = ConfigManager.getInstance().getConfigValueBool(15);
        this.mBankAccountSetView = (WazeTextView) findViewById(C1283R.id.bankButtonSet);
        this.mBankAccountSetView.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_MASKED_DEFAULT));
        ForDisplay disp = setBankDetailsAvailable();
        int payDay = ConfigManager.getInstance().getConfigValueInt(74);
        ((TextView) findViewById(C1283R.id.setCarPayGetPaidText)).setText(String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_PAYMENTS_GET_PAID_PS), new Object[]{String.valueOf(payDay)}));
        if (this.mConfigPayoneerIsOn) {
            Logger.i("SettingsCarpoolPaymentsActivity: Payoneer is enabled by config");
            findViewById(C1283R.id.noBankButtonLayout).setOnClickListener(openPayeeForm());
            findViewById(C1283R.id.bankButtonLayout).setOnClickListener(openPayeeForm());
            this.mLegalView.setMovementMethod(LinkMovementMethod.getInstance());
            this.mLegalView.setText(CarpoolUtils.getSpannedLegal(disp.currency));
            this.mLegalView.setLinkTextColor(this.mLegalView.getTextColors());
            this.mLegalView.setVisibility(0);
            return;
        }
        Logger.i("SettingsCarpoolPaymentsActivity: Payoneer is disabled by config");
        findViewById(C1283R.id.noBankButtonLayout).setOnClickListener(openBankForm());
        findViewById(C1283R.id.bankButtonLayout).setOnClickListener(openBankForm());
        this.mLegalView.setVisibility(8);
    }

    private void finishResultOk() {
        Intent i = new Intent();
        i.putExtra("account_is_set", this.mAccountIsSet);
        setResult(-1, i);
        finish();
    }

    private OnClickListener openBankForm() {
        return new C26392();
    }

    @NonNull
    private OnClickListener openPayeeForm() {
        return new C26403();
    }

    private void requestHomeAddress() {
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNtMgr.getLanguageString(DisplayStrings.DS_HOME_ADDRESS_REQUIRED_TITLE), this.mNtMgr.getLanguageString(DisplayStrings.DS_HOME_ADDRESS_REQUIRED_FOR_BANK), true, new C26414(), this.mNtMgr.getLanguageString(DisplayStrings.DS_ADD_NOW), this.mNtMgr.getLanguageString(344), -1);
    }

    private void openPayeeUrl(boolean fromButton) {
        if (fromButton) {
            this.mUrlReceived = false;
            this.mUrl = null;
        }
        if (this.mUrlReceived) {
            NativeManager.getInstance().CloseProgressPopup();
            this.mUrlRetries = 0;
            if (this.mUrl != null && !this.mUrl.isEmpty() && this.mUrlRc == 0) {
                Intent intent = new Intent(this, SettingsPaymentActivity.class);
                Logger.d("SettingsCarpoolPaymentsActivity: bank form URL received, rc valid");
                intent.putExtra("URL", this.mUrl);
                startActivityForResult(intent, 2486);
                return;
            } else if (this.mUrlRc == 1101) {
                Logger.e("SettingsCarpoolPaymentsActivity: bank form URL received, rc error - provider unreachable");
                MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_UNREACHABLE), false);
                return;
            } else if (this.mUrlRc == 1102) {
                Logger.e("SettingsCarpoolPaymentsActivity: bank form URL received, rc error - user country unavailable");
                MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_FAILED), false);
                return;
            } else if (this.mUrlRc == 1103) {
                Logger.e("SettingsCarpoolPaymentsActivity: bank form URL received, rc error - user country unsupported");
                MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_FAILED), false);
                return;
            } else {
                Logger.e("SettingsCarpoolPaymentsActivity: bank form URL is null or empty and unrecognized RC: " + this.mUrlRc + ", giving up");
                this.mUrlRetries = 100;
            }
        }
        if (this.mUrlRetries == 0) {
            NativeManager.getInstance().OpenProgressPopup("");
        }
        if (this.mUrlRetries < 15) {
            this.mUrlRetries++;
            Logger.w("SettingsCarpoolPaymentsActivity: bank form URL not recevied yet, retry " + this.mUrlRetries);
            postDelayed(new C26425(), 200);
            return;
        }
        Logger.w("SettingsCarpoolPaymentsActivity: bank form URL not recevied after " + this.mUrlRetries + " retries; giving up");
        NativeManager.getInstance().CloseProgressPopup();
        MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_UNREACHABLE), false);
        this.mUrlRetries = 0;
    }

    protected boolean myHandleMessage(Message msg) {
        Bundle b;
        if (msg.what == CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE) {
            b = msg.getData();
            this.cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE, this.mHandler);
            Logger.d("SettingsCarpoolPaymentsActivity: payee bundle received");
            this.mProfile = this.cpnm.getCarpoolProfileNTV();
            this.mPayee = (CarpoolPayee) b.getParcelable("payee");
            if (this.mPayee == null || this.mPayee.payout_account_name == null) {
                Logger.e("SettingsCarpoolPaymentsActivity: received null payee/account. Cannot fill details, requesting again");
                postDelayed(new C26436(), 1000);
                return false;
            }
            Logger.d("SettingsCarpoolPaymentsActivity: setting bank details");
            setBankDetailsAvailable();
        } else if (msg.what == CarpoolNativeManager.UH_CARPOOL_PAYMENT_URL) {
            b = msg.getData();
            this.cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_URL, this.mHandler);
            this.mUrl = b.getString("URL");
            this.mUrlRc = b.getInt("RC");
            this.mUrlReceived = true;
            Logger.d("SettingsCarpoolPaymentsActivity: recevied url");
        }
        return true;
    }

    private ForDisplay getDisplayStrings() {
        ForDisplay disp = new ForDisplay();
        int paid = 0;
        int unpaid = 0;
        String country = getCountry();
        String currency = null;
        if (this.mConfigPayoneerIsOn && this.mPayee != null) {
            currency = this.mPayee.currency_code;
            paid = this.mPayee.paid_balance;
            unpaid = this.mPayee.unpaid_balance;
            disp.account = this.mPayee.payout_account_name;
            Logger.d("SettingsCarpoolPaymentsActivity: Received details from payee");
        }
        if (paid > 0) {
            disp.paid = this.cpnm.centsToString(this, paid, country, currency);
        } else {
            disp.paid = null;
        }
        disp.unpaid = this.cpnm.centsToString(this, unpaid, country, currency);
        disp.currency = currency;
        return disp;
    }

    private String getCountry() {
        if (this.mPayee == null || this.mPayee.address_country == null || this.mPayee.address_country.isEmpty()) {
            return null;
        }
        return this.mPayee.address_country;
    }

    private ForDisplay setBankDetailsAvailable() {
        this.mBankAccountView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_MASKED_DEFAULT));
        this.mProgressView.stop();
        this.mProgressView.setVisibility(8);
        ForDisplay disp = getDisplayStrings();
        setBalance(disp);
        if (this.mProfile == null || !CarpoolUtils.hasPaymentMeans(this.mProfile, this.mAccountIsSet, this.mPayee)) {
            bankDetailsNotSet();
        } else {
            bankDetailsSet(disp);
        }
        return disp;
    }

    private void bankDetailsNotSet() {
        this.mBankAvailView.setVisibility(8);
        this.mNoBankView.setVisibility(0);
        this.mAddBankAccountView.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_SUBTITLE_NO_DETAILLS));
    }

    private ForDisplay bankDetailsSet(ForDisplay disp) {
        this.mBankAvailView.setVisibility(0);
        this.mNoBankView.setVisibility(8);
        if (disp.account == null || disp.account.isEmpty()) {
            Logger.e("SettingsCarpoolPaymentsActivity: masked bank account field empty");
            this.mBankAccountView.setVisibility(8);
            this.mBankAccountSetView.setPadding(0, 0, 0, 0);
        } else {
            this.mBankAccountView.setVisibility(0);
            this.mBankAccountView.setText(disp.account);
            this.mBankAccountSetView.setPadding(0, PixelMeasure.dp(10), 0, 0);
        }
        return disp;
    }

    private void setBalance(ForDisplay disp) {
        if (disp.paid == null) {
            this.mPaidBalanceView.setVisibility(8);
        } else {
            this.mPaidBalanceView.setVisibility(0);
            this.mPaidBalanceView.setText(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_TOTAL_PAID_PS), new Object[]{disp.paid}));
        }
        ((TextView) findViewById(C1283R.id.setCarToPayBalanceText)).setText(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_BALANCE_PS), new Object[]{disp.unpaid}));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2486) {
            handleUrlResponse(resultCode);
            if (resultCode == -1) {
                finishResultOk();
            }
        } else if (requestCode == 2487) {
            if (resultCode == 5) {
                CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_URL, this.mHandler);
                CarpoolNativeManager.getInstance().getPayeeForm();
                this.mNtMgr.OpenProgressIconPopup(this.mNtMgr.getLanguageString(DisplayStrings.DS_HOME_ADDRESS_ADDED_FOR_BANK), "sign_up_big_v");
                postDelayed(new C26447(), 1000);
            }
            this.mProfile = this.cpnm.getCarpoolProfileNTV();
        } else {
            if (requestCode == 2488) {
                if (resultCode == SettingsCarpoolPaymentsBankActivity.RESULT_SAVED) {
                    this.mNtMgr.OpenProgressIconPopup(this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_SUCCESS), "sign_up_big_v");
                    postDelayed(new C26458(), 1000);
                    Logger.d("SettingsCarpoolPaymentsActivity: bank details updated, requesting profile");
                    this.mProfile = this.cpnm.getCarpoolProfileNTV();
                    setBankDetailsAvailable();
                }
                if (resultCode == -1) {
                    finishResultOk();
                }
            }
            if (resultCode == -1) {
                finishResultOk();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleUrlResponse(int rc) {
        Logger.d("SettingsCarpoolPaymentsActivity: received RC from Payoneer URL: " + rc);
        if (rc == 99) {
            this.mNtMgr.OpenProgressIconPopup(this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_SUCCESS), "sign_up_big_v");
            postDelayed(new C26469(), 1000);
            Logger.d("SettingsCarpoolPaymentsActivity: bank details updated, requesting payee");
            this.mAccountIsSet = true;
            bankDetailsSet(getDisplayStrings());
            this.cpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE, this.mHandler);
            this.cpnm.getPayee();
        } else if (rc == 1) {
            Logger.e("SettingsCarpoolPaymentsActivity: response from Payoneer URL - registration failed");
            MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_TITLE_FAILED), this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_FAILED), false);
            this.mAccountIsSet = false;
        } else if (rc == 2) {
            Logger.e("SettingsCarpoolPaymentsActivity: response from Payoneer URL - already registered");
            MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_TITLE_FAILED), this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_FAILED), false);
            this.mAccountIsSet = false;
        } else if (rc != 0) {
            Logger.e("SettingsCarpoolPaymentsActivity: response from Payoneer URL - unknown; rc: " + rc);
            MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNtMgr.getLanguageString(DisplayStrings.DS_PAYMENT_STATUS_BODY_FAILED), false);
            this.mAccountIsSet = false;
        }
    }

    protected void onDestroy() {
        this.mExit = true;
        this.mNtMgr.CloseProgressPopup();
        this.cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE, this.mHandler);
        this.cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_URL, this.mHandler);
        super.onDestroy();
    }
}
