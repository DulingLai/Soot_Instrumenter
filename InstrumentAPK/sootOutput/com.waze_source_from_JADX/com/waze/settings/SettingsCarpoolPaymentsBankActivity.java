package com.waze.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.PaymentFieldValidators;
import com.waze.carpool.CarpoolUserData;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.PointsView;
import com.waze.reports.PointsViewTextWatcher;
import com.waze.reports.PointsViewTextWatcher.HasContentValidator;
import com.waze.reports.PointsViewTextWatcher.PaternValidator;
import com.waze.reports.PointsViewTextWatcher.TextValidator;
import com.waze.reports.PointsViewTextWatcher.ValidatedPointsViewsMgr;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;
import com.waze.widget.rt.RealTimeManager;
import java.util.ArrayList;
import java.util.Iterator;

public class SettingsCarpoolPaymentsBankActivity extends ActivityBase implements ValidatedPointsViewsMgr {
    private static final String CARPOOL_MANDATORY_INDICATOR = " <font color=#FF7878>*</font>";
    public static final String DETAILS = "DETAILS";
    public static final int RESULT_SAVED = 7732;
    private boolean closeEnabled = false;
    Handler f98h = new Handler();
    private String mBankCode;
    private String mBankName;
    private String mId = null;
    private NativeManager mNtMgr;
    private WazeSettingsView mSlBank;
    private WazeSettingsView mSvAccount;
    private WazeSettingsView mSvBranch;
    private WazeSettingsView mSvNameFirst;
    private WazeSettingsView mSvNameLast;
    private WazeSettingsView mSvNameMiddle;
    private String mToken = null;
    private ArrayList<PointsView> mValidatedPointsViews = new ArrayList(5);
    private boolean unsavedChanges = false;

    class C26471 implements OnClickListener {
        C26471() {
        }

        public void onClick(View v) {
            SettingsCarpoolPaymentsBankActivity.this.getApplicationContext();
            ((InputMethodManager) SettingsCarpoolPaymentsBankActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(SettingsCarpoolPaymentsBankActivity.this.mSvBranch.getWindowToken(), 0);
            if (!SettingsCarpoolPaymentsBankActivity.this.checkIfAccountIsGood()) {
                return;
            }
            if (((CheckBox) SettingsCarpoolPaymentsBankActivity.this.findViewById(C1283R.id.carpoolPaymentsCheckBox)).isChecked()) {
                SettingsCarpoolPaymentsBankActivity.this.mNtMgr.OpenProgressPopup(SettingsCarpoolPaymentsBankActivity.this.mNtMgr.getLanguageString(290));
                SettingsCarpoolPaymentsBankActivity.this.sendUpdate();
                return;
            }
            MsgBox.openMessageBoxFull(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_ERROR), NativeManager.getInstance().getLanguageString(334), -1, null);
        }
    }

    class C26482 implements OnClickListener {
        C26482() {
        }

        public void onClick(View v) {
            if (SettingsCarpoolPaymentsBankActivity.this.checkIfAccountIsGood()) {
                SettingsCarpoolPaymentsBankActivity.this.sendUpdate();
            }
        }
    }

    private WazeSettingsView setEditTextAndPoints(int editTextId, int hintDS, String initialValue, TextValidator validator) {
        WazeSettingsView et = (WazeSettingsView) findViewById(editTextId);
        PointsView pv = et.getValidation();
        et.setValueText(initialValue);
        et.addTextChangedListener(new PointsViewTextWatcher(this, pv, 0, validator, initialValue));
        String hint = this.mNtMgr.getLanguageString(hintDS);
        pv.setValid(false);
        pv.setIsOn(true, true, false);
        if (validator == null) {
            pv.setVisibility(4);
        } else {
            pv.setVisibility(0);
            pv.setIgnoreFirst(true);
        }
        et.setKeyText(Html.fromHtml(hint));
        return et;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PAYMENT_SHOWN);
        this.mNtMgr = AppService.getNativeManager();
        this.mValidatedPointsViews.clear();
        setContentView(C1283R.layout.settings_carpool_payments_bank);
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.init((Activity) this, DisplayStrings.DS_CARPOOL_PAYMENTS_TITLE);
        titleBar.setCloseText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_SEND));
        titleBar.setOnClickCloseListener(new C26471());
        CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
        CarpoolUserData profile = cpnm.getCarpoolProfileNTV();
        String PrivacyNoticeUrl = cpnm.configGetPrivacyNoticeUrlNTV();
        String TOSUrl = cpnm.configGetPaymentTermsUrlNTV();
        TextView tvLegalAccept = (TextView) findViewById(C1283R.id.setCarPayLegalAccept);
        tvLegalAccept.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textView = tvLegalAccept;
        textView.setText(Html.fromHtml(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_LEGAL_ACCEPT), new Object[]{TOSUrl, PrivacyNoticeUrl})));
        tvLegalAccept.setLinkTextColor(tvLegalAccept.getTextColors());
        TextView tvSave = (TextView) findViewById(C1283R.id.saveButton);
        tvSave.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_SEND));
        tvSave.setOnClickListener(new C26482());
        tvSave.setEnabled(false);
        CheckBox carpoolPaymentsCheckBox = (CheckBox) findViewById(C1283R.id.carpoolPaymentsCheckBox);
        carpoolPaymentsCheckBox.setChecked(false);
        final TextView textView2 = tvSave;
        carpoolPaymentsCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textView2.setEnabled(isChecked);
            }
        });
        String[] configGetBankNames = cpnm.configGetBankNamesNTV();
        final String[] bankNames = new String[(configGetBankNames.length / 2)];
        final String[] bankCodes = new String[(configGetBankNames.length / 2)];
        for (int i = 1; i < configGetBankNames.length; i += 2) {
            bankNames[i / 2] = configGetBankNames[i - 1];
            bankCodes[i / 2] = configGetBankNames[i];
        }
        this.mSlBank = (WazeSettingsView) findViewById(C1283R.id.setCarPayBank);
        this.mSlBank.setOnClickListener(new OnClickListener() {

            class C26501 implements SettingsDialogListener {
                C26501() {
                }

                public void onComplete(int position) {
                    SettingsCarpoolPaymentsBankActivity.this.mBankName = bankNames[position];
                    SettingsCarpoolPaymentsBankActivity.this.mBankCode = bankCodes[position];
                    SettingsCarpoolPaymentsBankActivity.this.mSlBank.setValueText(SettingsCarpoolPaymentsBankActivity.this.mBankName);
                    SettingsCarpoolPaymentsBankActivity.this.mSlBank.setKeyText(SettingsCarpoolPaymentsBankActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_BANK));
                    PointsView bankPv = SettingsCarpoolPaymentsBankActivity.this.mSlBank.getValidation();
                    bankPv.setValid(true);
                    bankPv.isValid();
                    bankPv.setIsOn(true, true, true);
                    SettingsCarpoolPaymentsBankActivity.this.unsavedChanges = true;
                }
            }

            public void onClick(View v) {
                SettingsUtilsMultipleConfig.showSubmenu(SettingsCarpoolPaymentsBankActivity.this, SettingsCarpoolPaymentsBankActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_BANK), bankNames, new C26501());
            }
        });
        setAccountFields();
        enableTitlebarAction(this.closeEnabled);
        getWindow().setSoftInputMode(3);
    }

    private void enableTitlebarAction(boolean enabled) {
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.setCloseEnabled(enabled);
        titleBar.setCloseTextColor(getResources().getColor(enabled ? C1283R.color.titlebar_button_text_enabled : C1283R.color.titlebar_button_text_disabled));
        titleBar.setCloseVisibility(enabled);
        this.closeEnabled = enabled;
    }

    private void setAccountFields() {
        CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
        this.mSlBank.setValueText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_NOT_SET));
        this.mSlBank.setKeyText(Html.fromHtml(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_BANK) + CARPOOL_MANDATORY_INDICATOR));
        PointsView bankPv = this.mSlBank.getValidation();
        bankPv.setValid(false);
        bankPv.isValid();
        bankPv.setIsOn(true, true, true);
        bankPv.setVisibility(0);
        this.mBankName = null;
        this.mBankCode = null;
        PaymentFieldValidators validators = cpnm.configGetPaymentFieldValidatorsNTV();
        this.mSvBranch = setEditTextAndPoints(C1283R.id.setCarPayBranch, DisplayStrings.DS_CARPOOL_PAYMENTS_BRANCH, "", new PaternValidator(validators.branch, false));
        ((EditText) this.mSvBranch.getValue()).setInputType(2);
        this.mSvAccount = setEditTextAndPoints(C1283R.id.setCarPayAccount, DisplayStrings.DS_CARPOOL_PAYMENTS_ACCOUNT, "", new PaternValidator(validators.account, false));
        ((EditText) this.mSvAccount.getValue()).setInputType(2);
        this.mSvNameFirst = setEditTextAndPoints(C1283R.id.setCarPayName, DisplayStrings.DS_CARPOOL_PAYMENTS_NAME_FIRST, "", new HasContentValidator());
        this.mSvNameMiddle = setEditTextAndPoints(C1283R.id.setCarPayNameMiddle, DisplayStrings.DS_CARPOOL_PAYMENTS_NAME_MIDDLE, "", null);
        this.mSvNameLast = setEditTextAndPoints(C1283R.id.setCarPayNameLast, DisplayStrings.DS_CARPOOL_PAYMENTS_NAME_LAST, "", new HasContentValidator());
        ((CheckBox) findViewById(C1283R.id.carpoolPaymentsCheckBox)).setChecked(false);
    }

    public boolean checkIfAccountIsGood() {
        boolean isGood = true;
        ScrollView sv = (ScrollView) findViewById(C1283R.id.theScrollView);
        if (this.mBankName == null || this.mBankName.isEmpty()) {
            if (1 != null) {
                AnimationUtils.focusOnView(sv, findViewById(C1283R.id.setCarPayBank));
            }
            isGood = false;
        }
        Iterator it = this.mValidatedPointsViews.iterator();
        while (it.hasNext()) {
            PointsView pv = (PointsView) it.next();
            if (!pv.isValid()) {
                if (isGood) {
                    AnimationUtils.focusOnView(sv, pv);
                }
                pv.setVisibility(0);
                AnimationUtils.flashView(pv);
                isGood = false;
            }
        }
        return isGood;
    }

    private void validateOnExit(final int resultCode) {
        if (this.unsavedChanges) {
            Dialog d = MsgBox.openConfirmDialogJavaCallback(this.mNtMgr.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_INCOMPLETE), true, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 1) {
                        SettingsCarpoolPaymentsBankActivity.this.setResult(resultCode);
                        SettingsCarpoolPaymentsBankActivity.this.finish();
                    }
                }
            }, this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_PAYMENTS_LEAVE), this.mNtMgr.getLanguageString(344), -1);
            return;
        }
        setResult(resultCode);
        finish();
    }

    protected void onDestroy() {
        CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
        cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_BANK_ACCOUNT_SENT, this.mHandler);
        cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_TOKEN, this.mHandler);
        super.onDestroy();
    }

    private void sendUpdate() {
        CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
        if (this.mToken == null || this.mToken.isEmpty()) {
            cpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_TOKEN, this.mHandler);
            cpnm.GetCommunityToken();
            return;
        }
        String accountId = this.mSvAccount.getValueText().toString();
        this.mNtMgr.OpenProgressPopup(this.mNtMgr.getLanguageString(290));
        cpnm.setUpdateHandler(CarpoolNativeManager.UH_BANK_ACCOUNT_SENT, this.mHandler);
        RealTimeManager.getInstance().SendBankAccount(AppService.getAppContext(), this.mToken, this.mId, this.mSvNameFirst.getValueText().toString(), this.mSvNameLast.getValueText().toString(), this.mSvNameMiddle.getValueText().toString(), this.mSvBranch.getValueText().toString(), this.mBankCode, accountId);
    }

    protected boolean myHandleMessage(Message msg) {
        CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
        if (msg.what == CarpoolNativeManager.UH_BANK_ACCOUNT_SENT) {
            cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_BANK_ACCOUNT_SENT, this.mHandler);
            this.mNtMgr.CloseProgressPopup();
            if (msg.arg1 == 1) {
                this.unsavedChanges = false;
                setResult(RESULT_SAVED);
                finish();
            } else {
                MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
            }
            return true;
        } else if (msg.what != CarpoolNativeManager.UH_CARPOOL_TOKEN) {
            return super.myHandleMessage(msg);
        } else {
            String token = msg.getData().getString("token");
            String id = msg.getData().getString("id");
            if (token == null || id == null) {
                this.mNtMgr.CloseProgressPopup();
                MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
            } else {
                this.mToken = token;
                this.mId = id;
                this.mNtMgr.OpenProgressPopup(this.mNtMgr.getLanguageString(290));
                cpnm.setUpdateHandler(CarpoolNativeManager.UH_BANK_ACCOUNT_SENT, this.mHandler);
                cpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_TOKEN, this.mHandler);
                RealTimeManager.getInstance().SendBankAccount(AppService.getAppContext(), this.mToken, id, this.mSvNameFirst.getValueText().toString(), this.mSvNameLast.getValueText().toString(), this.mSvNameMiddle.getValueText().toString(), this.mSvBranch.getValueText().toString(), this.mBankCode, this.mSvAccount.getValueText().toString());
            }
            return true;
        }
    }

    public void onBackPressed() {
        validateOnExit(0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addValidatedPointsView(PointsView pv) {
        this.mValidatedPointsViews.add(pv);
    }

    public void updatePoints(int ptsMod) {
    }

    public void onEdit() {
        this.unsavedChanges = true;
    }
}
