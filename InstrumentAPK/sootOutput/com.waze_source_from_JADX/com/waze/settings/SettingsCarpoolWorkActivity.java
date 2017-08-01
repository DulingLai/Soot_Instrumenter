package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.facebook.share.internal.ShareConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolUserData;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.PointsView;
import com.waze.reports.PointsViewTextWatcher;
import com.waze.reports.PointsViewTextWatcher.EmailValidator;
import com.waze.reports.PointsViewTextWatcher.ValidatedPointsViewsMgr;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class SettingsCarpoolWorkActivity extends ActivityBase implements ValidatedPointsViewsMgr {
    private static final long NETWORK_TIMEOUT = 10000;
    public static final int RESULT_EMAIL_CHANGED = 5012;
    private boolean bSentRequest = false;
    private final CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
    private WazeSettingsView mEtWork;
    private NativeManager mNtMgr;
    private PointsView mPvCheck;
    private OnClickListener mRemoveListener;
    private OnClickListener mSendEmailListener;
    private Runnable mTimeoutRunnable;
    private TextView mTvExplain;
    private TextView mTvRemove;
    private TextView mTvStatus;
    private TextView mTvSubtitle;
    private TextView mTvTitle;
    private TitleBar mtitleBar;
    private boolean sendEnabled;

    class C26571 implements TextWatcher {
        C26571() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            SettingsCarpoolWorkActivity.this.enableSendButton(s.length() > 0);
        }
    }

    class C26582 implements OnEditorActionListener {
        C26582() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 4) {
                return false;
            }
            if (SettingsCarpoolWorkActivity.this.mEtWork.getValueText().length() > 0) {
                SettingsCarpoolWorkActivity.this.mSendEmailListener.onClick(null);
            } else {
                SettingsCarpoolWorkActivity.this.checkIfDataIsGood();
            }
            return true;
        }
    }

    class C26593 implements OnFocusChangeListener {
        C26593() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                AnimationUtils.focusOnView((ScrollView) SettingsCarpoolWorkActivity.this.findViewById(C1283R.id.theScrollView), v);
            }
        }
    }

    class C26624 implements OnClickListener {

        class C26611 implements Runnable {

            class C26601 implements DialogInterface.OnClickListener {
                C26601() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    SettingsCarpoolWorkActivity.this.setResult(-1);
                    SettingsCarpoolWorkActivity.this.finish();
                }
            }

            C26611() {
            }

            public void run() {
                SettingsCarpoolWorkActivity.this.mNtMgr.CloseProgressPopup();
                MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C26601());
            }
        }

        C26624() {
        }

        public void onClick(View v) {
            if (SettingsCarpoolWorkActivity.this.sendEnabled) {
                String email = SettingsCarpoolWorkActivity.this.mEtWork.getValueText().toString();
                if (SettingsCarpoolWorkActivity.this.checkIfDataIsGood()) {
                    SettingsCarpoolWorkActivity.this.bSentRequest = true;
                    SettingsCarpoolWorkActivity.this.mNtMgr.OpenProgressPopup(SettingsCarpoolWorkActivity.this.mNtMgr.getLanguageString(290));
                    CarpoolUserData profile = SettingsCarpoolWorkActivity.this.cpnm.getCarpoolProfileNTV();
                    if (TextUtils.isEmpty(email) || SettingsCarpoolWorkActivity.this.cpnm.getCarpoolProfileNTV().isWorkEmailVerified() || !email.equals(profile.getWorkEmail())) {
                        SettingsCarpoolWorkActivity.this.cpnm.setWorkEmail(email);
                    } else {
                        SettingsCarpoolWorkActivity.this.cpnm.resendWorkEmail();
                    }
                    SettingsCarpoolWorkActivity.this.mTimeoutRunnable = new C26611();
                    SettingsCarpoolWorkActivity.this.postDelayed(SettingsCarpoolWorkActivity.this.mTimeoutRunnable, SettingsCarpoolWorkActivity.NETWORK_TIMEOUT);
                }
            }
        }
    }

    class C26635 implements OnClickListener {
        C26635() {
        }

        public void onClick(View v) {
            SettingsCarpoolWorkActivity.this.mEtWork.setValueText("");
            SettingsCarpoolWorkActivity.this.sendEnabled = true;
            SettingsCarpoolWorkActivity.this.mSendEmailListener.onClick(v);
        }
    }

    class C26646 implements Runnable {
        C26646() {
        }

        public void run() {
            SettingsCarpoolWorkActivity.this.mNtMgr.CloseProgressPopup();
            SettingsCarpoolWorkActivity.this.setResult(SettingsCarpoolWorkActivity.RESULT_EMAIL_CHANGED);
        }
    }

    private void setupEmpty() {
        this.mTvTitle.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_TITLE_TEXT_EMPTY));
        this.mtitleBar.setCloseText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_TITLE_BUTTON));
        this.mTvSubtitle.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_SUBTITLE_TEXT_EMPTY));
        this.mEtWork.setValueText("");
        this.mTvStatus.setVisibility(8);
        this.mTvExplain.setVisibility(0);
        this.mTvExplain.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_EXPLAIN_EMPTY));
        this.mTvRemove.setVisibility(8);
        enableSendButton(false);
    }

    private void setupPendingVerification(String email) {
        this.mTvTitle.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_TITLE_TEXT_PENDING));
        this.mtitleBar.setCloseText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_TITLE_BUTTON_RESEND));
        this.mTvSubtitle.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_SUBTITLE_TEXT_PENDING));
        this.mEtWork.setValueText(email);
        this.mTvStatus.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_PENDING));
        this.mTvStatus.setTextColor(getResources().getColor(C1283R.color.pastrami_pink));
        this.mTvStatus.setVisibility(0);
        this.mTvExplain.setVisibility(0);
        this.mTvExplain.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_EXPLAIN_PENDING));
        this.mTvRemove.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_REMOVE));
        this.mTvRemove.setOnClickListener(this.mRemoveListener);
        this.mTvRemove.setVisibility(0);
        enableSendButton(true);
    }

    private void setupVerified(String workplace, String email) {
        if (workplace.isEmpty()) {
            String emailSuffix = email.substring(email.lastIndexOf("@") + 1);
            this.mTvTitle.setText(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_TITLE_TEXT_VERIFIED_PS), new Object[]{emailSuffix}));
            this.mTvSubtitle.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_SUBTITLE_TEXT_VERIFIED));
        } else {
            this.mTvTitle.setText(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_TITLE_TEXT_VERIFIED_COMPANY_PS), new Object[]{workplace}));
            this.mTvSubtitle.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_SUBTITLE_TEXT_VERIFIED_COMPANY));
        }
        this.mEtWork.setValueText(email);
        this.mTvStatus.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_VERIFIED));
        this.mTvStatus.setTextColor(getResources().getColor(C1283R.color.light_green));
        this.mTvExplain.setVisibility(8);
        this.mTvRemove.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_REMOVE));
        this.mTvRemove.setOnClickListener(this.mRemoveListener);
        enableSendButton(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListeners();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_WORK_SCHOOL_SHOWN);
        this.mNtMgr = AppService.getNativeManager();
        setContentView(C1283R.layout.settings_carpool_work);
        this.mtitleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        this.mtitleBar.init((Activity) this, (int) DisplayStrings.DS_CARPOOL_WORK_TITLE, (int) DisplayStrings.DS_CARPOOL_WORK_TITLE_BUTTON);
        this.mtitleBar.setOnClickCloseListener(this.mSendEmailListener);
        CarpoolUserData profile = this.cpnm.getCarpoolProfileNTV();
        String email = profile.getWorkEmail();
        this.mTvTitle = (TextView) findViewById(C1283R.id.setEmailTitle);
        this.mTvSubtitle = (TextView) findViewById(C1283R.id.setEmailSubtitle);
        this.mEtWork = (WazeSettingsView) findViewById(C1283R.id.setCarWorkMail);
        this.mEtWork.getEdit().setInputType(32);
        this.mEtWork.addTextChangedListener(new C26571());
        this.mEtWork.setOnEditorActionListener(new C26582());
        this.mEtWork.setOnFocusChangeListener(new C26593());
        this.mPvCheck = this.mEtWork.getValidation();
        this.mTvStatus = (TextView) findViewById(C1283R.id.setCarWorkStatus);
        this.mTvRemove = (TextView) findViewById(C1283R.id.setCarWorkRemove);
        this.mTvRemove.setPaintFlags(this.mTvRemove.getPaintFlags() | 8);
        this.mTvExplain = (TextView) findViewById(C1283R.id.setCarWorkExplain);
        this.mEtWork.setKeyText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_HINT));
        boolean mUserWorkEmailMandatory = this.cpnm.isUserWorkEmailMandatory();
        this.mEtWork.addTextChangedListener(new PointsViewTextWatcher(this, this.mPvCheck, 0, new EmailValidator(!mUserWorkEmailMandatory), email));
        if (TextUtils.isEmpty(email)) {
            setupEmpty();
        } else if (profile.isWorkEmailVerified()) {
            setupVerified(profile.getWorkplace(), email);
        } else {
            setupPendingVerification(email);
        }
        this.cpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        this.cpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
    }

    private void initListeners() {
        this.mSendEmailListener = new C26624();
        this.mRemoveListener = new C26635();
    }

    private void enableSendButton(boolean enabled) {
        this.sendEnabled = enabled;
        this.mtitleBar.setCloseEnabled(enabled);
        this.mtitleBar.setCloseTextColor(getResources().getColor(enabled ? C1283R.color.titlebar_button_text_enabled : C1283R.color.titlebar_button_text_disabled));
    }

    public boolean checkIfDataIsGood() {
        this.mEtWork.setValueText(this.mEtWork.getValueText().toString());
        if (this.mPvCheck.isValid()) {
            return true;
        }
        this.mPvCheck.setIsOn(true, false, false);
        AnimationUtils.flashView(this.mPvCheck);
        ((EditText) this.mEtWork.getValue()).setSelection(0, this.mEtWork.getValueText().length());
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ERROR_SHOWN, "ERROR", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MALFORMED_EMAIL);
        MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_BAD_EMAIL_PS), new Object[]{this.mEtWork.getValueText()}), DisplayStrings.displayString(334), -1, -1);
        return false;
    }

    protected boolean myHandleMessage(Message msg) {
        Bundle b;
        if (msg.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            if (this.bSentRequest) {
                this.bSentRequest = false;
                b = msg.getData();
                cancel(this.mTimeoutRunnable);
                this.mNtMgr.CloseProgressPopup();
                if (b.getBoolean("success")) {
                    int ds = DisplayStrings.DS_CARPOOL_WORK_SENT;
                    if (this.mEtWork.getValueText().toString().isEmpty()) {
                        ds = DisplayStrings.DS_CARPOOL_WORK_REMOVED;
                        setupEmpty();
                    }
                    this.mNtMgr.OpenProgressIconPopup(this.mNtMgr.getLanguageString(ds), "popup_mail_icon");
                    postDelayed(new C26646(), 2000);
                } else {
                    showError();
                }
            } else {
                CarpoolUserData profile = this.cpnm.getCarpoolProfileNTV();
                String email = profile.getWorkEmail();
                if (TextUtils.isEmpty(email)) {
                    setupEmpty();
                } else if (profile.isWorkEmailVerified()) {
                    setupVerified(profile.getWorkplace(), email);
                } else {
                    setupPendingVerification(email);
                }
            }
            return true;
        }
        if (msg.what == CarpoolNativeManager.UH_CARPOOL_ERROR) {
            cancel(this.mTimeoutRunnable);
            this.cpnm.clearWorkEmail();
            this.mNtMgr.CloseProgressPopup();
            b = msg.getData();
            int type = b.getInt("type");
            String message = b.getString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
            if (type == 6) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ERROR_SHOWN, "ERROR", AnalyticsEvents.ANALYTICS_EVENT_VALUE_PERSONAL_EMAIL);
                MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_TITLE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_WORK_ILLEGAL), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_WORK_GOT_IT), -1, -1);
            } else if (type == 5) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ERROR_SHOWN, "ERROR", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MALFORMED_EMAIL);
                MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_BAD_EMAIL_PS), new Object[]{this.mEtWork.getValueText()}), DisplayStrings.displayString(334), -1, -1);
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ERROR_SHOWN, "ERROR", AnalyticsEvents.ANALYTICS_EVENT_VALUE_OTHER);
                MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), this.mNtMgr.getLanguageString(message), DisplayStrings.displayString(334), -1, -1);
            }
        }
        return super.myHandleMessage(msg);
    }

    private void showError() {
        MsgBox.openMessageBox(this.mNtMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNtMgr.getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
    }

    protected void onDestroy() {
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
        super.onDestroy();
    }

    public void addValidatedPointsView(PointsView pv) {
    }

    public void updatePoints(int ptsMod) {
    }

    public void onEdit() {
    }
}
