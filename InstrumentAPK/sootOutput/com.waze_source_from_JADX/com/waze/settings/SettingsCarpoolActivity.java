package com.waze.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.IntentManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDriverProfileActivity;
import com.waze.carpool.CarpoolFAQActivity;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolRidesActivity;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CarpoolUtils;
import com.waze.carpool.EditCarActivity;
import com.waze.carpool.GoogleSignInActivity;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.mywaze.social.FacebookActivity;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsCarpoolActivity extends ActivityBase implements FacebookCallback {
    private static final int INDEX_RECEIVE_REQUESTS = 0;
    public static final int PUSH_FREQ_ALL_OFFERS = 2;
    public static final int PUSH_FREQ_BEST_IND = 0;
    public static final int PUSH_FREQ_ONLY_BEST_OFFERS = 1;
    private static final int RQ_HISTORY_CODE = 1002;
    protected static final int RQ_PAYMENTS_CODE = 1000;
    private static final int RQ_SEATS_CODE = 1003;
    private static final int RQ_WORKPLACE_CODE = 1001;
    private CarpoolNativeManager mCpnm;
    private WazeSettingsView mDdSeats;
    private NativeManager mNtMgr = AppService.getNativeManager();
    private CarpoolUserData mProfile;
    private WazeSettingsView mSlWorkplace;

    class C26291 implements OnClickListener {
        C26291() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "RIDE_HISTORY");
            Intent intent = new Intent(SettingsCarpoolActivity.this, CarpoolRidesActivity.class);
            intent.putExtra("INT_VIEW_MODE", 2);
            SettingsCarpoolActivity.this.startActivityForResult(intent, 1002);
        }
    }

    class C26302 implements OnClickListener {
        C26302() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "LINKEDIN");
            MyWazeNativeManager.getInstance().openLinkedinDialog();
        }
    }

    class C26313 implements OnClickListener {
        C26313() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_FACEBOOK);
            MyWazeNativeManager.getInstance().getFacebookSettings(SettingsCarpoolActivity.this);
        }
    }

    class C26324 implements OnClickListener {
        C26324() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "WORK_SCHOOL");
            SettingsCarpoolActivity.this.startActivityForResult(new Intent(SettingsCarpoolActivity.this, SettingsCarpoolWorkActivity.class), 1001);
        }
    }

    class C26335 implements OnClickListener {
        C26335() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_CAR_DETAILS);
            SettingsCarpoolActivity.this.startActivityForResult(new Intent(SettingsCarpoolActivity.this, EditCarActivity.class), 0);
        }
    }

    class C26346 implements OnClickListener {
        C26346() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "DRIVER_PROFILE");
            SettingsCarpoolActivity.this.startActivityForResult(new Intent(SettingsCarpoolActivity.this, CarpoolDriverProfileActivity.class), 0);
        }
    }

    class C26357 implements OnClickListener {
        C26357() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_COMMUTE_AVAILABILITY);
            IntentManager.OpenCommuteModelActivity(SettingsCarpoolActivity.this);
        }
    }

    class C26368 implements OnClickListener {
        C26368() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "AVAILABLE_SEATS");
            SettingsCarpoolActivity.this.startActivityForResult(new Intent(SettingsCarpoolActivity.this, SettingsCarpoolSeatsActivity.class), 1003);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean bGetRidesRequestsEnabled;
        super.onCreate(savedInstanceState);
        Log.i("waze", "start SettingsCarpoolActivity");
        setContentView(C1283R.layout.settings_carpool);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_CARPOOL_SETTINGS_TITLE);
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mProfile = this.mCpnm.getCarpoolProfileNTV();
        ((SettingsTitleText) findViewById(C1283R.id.settingsCarpoolHeaderVerification)).setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_RW_SETTINGS_VERIFICATIONS));
        ((SettingsTitleText) findViewById(C1283R.id.settingsCarpoolHeaderCommute)).setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_RW_SETTINGS_COMMUTE));
        ((SettingsTitleText) findViewById(C1283R.id.settingsCarpoolHeaderAvailability)).setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_RW_SETTINGS_AVAILABILITY));
        ((SettingsTitleText) findViewById(C1283R.id.settingsCarpoolHeaderMore)).setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_HELP));
        ((SettingsTitleText) findViewById(C1283R.id.settingsCarpoolHeaderPayments)).setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_RW_SETTINGS_PAYMENTS));
        ((WazeSettingsView) findViewById(C1283R.id.settingsCarpoolPayments)).initDrillDownAnalytics(this, DisplayStrings.DS_CARPOOL_SETTINGS_BANK_DETAILS, SettingsCarpoolPaymentsActivity.class, 1000, AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "PAYMENT_ACCOUNT");
        WazeSettingsView ddHistory = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolHistory);
        if (this.mProfile == null || !this.mProfile.hadPrevRides()) {
            ddHistory.setVisibility(8);
        } else {
            ddHistory.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_HISTORY));
            ddHistory.setOnClickListener(new C26291());
        }
        WazeSettingsView ddLinkedin = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolLinkedin);
        ddLinkedin.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_LINKEDIN_CONNECT_TITLE));
        ddLinkedin.setOnClickListener(new C26302());
        WazeSettingsView ddFacebook = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolFacebook);
        ddFacebook.setText(this.mNtMgr.getLanguageString(396));
        ddFacebook.setOnClickListener(new C26313());
        this.mSlWorkplace = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolWork);
        this.mSlWorkplace.setKeyText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_WORKPLACE));
        this.mSlWorkplace.setOnClickListener(new C26324());
        setupWorkEmail();
        WazeSettingsView ddCar = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolCar);
        ddCar.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_MY_WAZE_EDIT_CAR));
        ddCar.setOnClickListener(new C26335());
        WazeSettingsView slProfile = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolProfile);
        slProfile.setKeyText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_PROFILE));
        slProfile.setOnClickListener(new C26346());
        slProfile.setValueText(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_PROFILE_COMPLETE_PS), new Object[]{Integer.valueOf(CarpoolDriverProfileActivity.getPercentCompleteProfile())}));
        WazeSettingsView ddCommute = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolCommute);
        ddCommute.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_COMMUTE_MODEL_SETTINGS_ENTRY));
        ddCommute.setOnClickListener(new C26357());
        this.mDdSeats = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolSeats);
        if (this.mProfile == null || !ConfigValues.getBoolValue(73)) {
            this.mDdSeats.setVisibility(8);
            ddCommute.setPosition(3);
        } else {
            setSeatsText();
            this.mDdSeats.setOnClickListener(new C26368());
        }
        WazeSettingsView swReceiveRequests = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolGetRequests);
        final WazeSettingsView swNotFreq = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolNotFreq);
        swReceiveRequests.setText(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_RECEIVE_REQUESTS));
        if (this.mProfile == null) {
            bGetRidesRequestsEnabled = false;
        } else {
            bGetRidesRequestsEnabled = this.mProfile.isGetRidesRequestsEnabled();
        }
        swReceiveRequests.setValue(bGetRidesRequestsEnabled);
        swReceiveRequests.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean isChecked) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GET_REQUESTS, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATUS, isChecked ? AnalyticsEvents.ANALYTICS_EVENT_ON : AnalyticsEvents.ANALYTICS_EVENT_OFF);
                if (isChecked) {
                    SettingsCarpoolActivity.this.mCpnm.updateGetRidesRequests(true);
                    Analytics.log(AnalyticsEvents.ANALYTICS_PUSH_RIDE_RQS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_ON);
                } else {
                    Analytics.log(AnalyticsEvents.ANALYTICS_PUSH_RIDE_RQS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_OFF);
                    SettingsCarpoolActivity.this.mCpnm.updateGetRidesRequests(false);
                }
                swNotFreq.setEnabled(isChecked);
            }
        });
        if (ConfigValues.getBoolValue(67)) {
            final String[] freqLabels = new String[]{DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_SETTINGS_NF_BEST), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_SETTINGS_NF_MANY)};
            int[] iArr = new int[2];
            iArr = new int[]{1, 2};
            final int[] iArr2 = iArr;
            GetIndex anonymousClass10 = new GetIndex() {
                public int fromConfig() {
                    if (SettingsCarpoolActivity.this.mProfile == null) {
                        return 0;
                    }
                    int nf = SettingsCarpoolActivity.this.mProfile.notification_frequency;
                    for (int i = 0; i < iArr2.length; i++) {
                        if (iArr2[i] == nf) {
                            return i;
                        }
                    }
                    return 0;
                }
            };
            iArr2 = iArr;
            swNotFreq.initSelection(this, anonymousClass10, DisplayStrings.DS_CARPOOL_SETTINGS_NOTIF_FREQ_DRILL, freqLabels, freqLabels, new SettingsDialogListener() {
                public void onComplete(int position) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NOTIFICATION_FREQUENCY, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, iArr2[position]);
                    SettingsCarpoolActivity.this.mProfile.notification_frequency = iArr2[position];
                    SettingsCarpoolActivity.this.mCpnm.updateNotificationFrequency(iArr2[position]);
                    swNotFreq.setValueText(freqLabels[position]);
                }
            });
            swNotFreq.setEnabled(bGetRidesRequestsEnabled);
        } else {
            swNotFreq.setVisibility(8);
            swReceiveRequests.setPosition(3);
        }
        ((WazeSettingsView) findViewById(C1283R.id.settingsCarpoolFAQ)).initDrillDownAnalytics(DisplayStrings.DS_CARPOOL_SETTINGS_FAQ, new Runnable() {
            public void run() {
                SettingsCarpoolActivity.this.startActivityForResult(new Intent(SettingsCarpoolActivity.this, CarpoolFAQActivity.class), 0);
            }
        }, AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FAQ);
        ((WazeSettingsView) findViewById(C1283R.id.settingsCarpoolHelp)).initDrillDownAnalytics(DisplayStrings.DS_CARPOOL_SETTINGS_HELP_FEEDBACK, new Runnable() {
            public void run() {
                SettingsCarpoolActivity.this.mCpnm.settingsHelpClicked();
            }
        }, AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_HELP);
        ((WazeSettingsView) findViewById(C1283R.id.settingsCarpoolRider)).initDrillDownAnalytics(DisplayStrings.DS_CARPOOL_SETTINGS_RIDER, new Runnable() {
            public void run() {
                CarpoolUtils.openRiderApp(SettingsCarpoolActivity.this);
            }
        }, AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_VALUE_RIDER);
        ((WazeSettingsView) findViewById(C1283R.id.settingsCarpoolProblem)).initDrillDownAnalytics(DisplayStrings.DS_CARPOOL_SETTINGS_PROBLM, new Runnable() {
            public void run() {
                SettingsCarpoolActivity.this.mCpnm.settingsProblemClicked();
            }
        }, AnalyticsEvents.ANALYTICS_EVENT_RW_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "PROBLEM");
        WazeSettingsView ddQuit = (WazeSettingsView) findViewById(C1283R.id.settingsCarpoolQuit);
        ddQuit.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_QUIT));
        ddQuit.setOnClickListener(new OnClickListener() {

            class C26271 implements DialogInterface.OnClickListener {
                C26271() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (which == 1) {
                        SettingsCarpoolActivity.this.quitRideWith();
                    }
                }
            }

            public void onClick(View v) {
                Dialog d = MsgBox.openConfirmDialogJavaCallback(SettingsCarpoolActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), SettingsCarpoolActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_QUIT_DIALOG), true, new C26271(), SettingsCarpoolActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_QUIT_YES), SettingsCarpoolActivity.this.mNtMgr.getLanguageString(344), -1);
            }
        });
        TextView textView = (TextView) findViewById(C1283R.id.settingsCarpoolBottomText);
        String languageString = this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_CONNECTED_ACCOUNT_PS);
        Object[] objArr = new Object[1];
        objArr[0] = this.mProfile != null ? this.mProfile.getEmail() : "";
        textView.setText(String.format(languageString, objArr));
        CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
    }

    private void setSeatsText() {
        String display;
        if (this.mProfile.available_seats == 1) {
            display = DisplayStrings.displayString(DisplayStrings.DS_RW_SETTINGS_AVAILABLE_1_SEAT);
        } else {
            display = DisplayStrings.displayStringF(DisplayStrings.DS_RW_SETTINGS_AVAILABLE_SEATS_PD, Integer.valueOf(this.mProfile.available_seats));
        }
        this.mDdSeats.setKeyText(DisplayStrings.displayString(DisplayStrings.DS_RW_SETTINGS_AVAILABLE_SEATS_ITEM));
        this.mDdSeats.setValueText(display);
    }

    public void onFacebookSettings(FacebookSettings settings) {
        Intent intent = new Intent(this, FacebookActivity.class);
        intent.putExtra("com.waze.mywaze.facebooksettings", settings);
        startActivityForResult(intent, 0);
    }

    protected void onDestroy() {
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        super.onDestroy();
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            this.mProfile = this.mCpnm.getCarpoolProfileNTV();
            setupWorkEmail();
        }
        return false;
    }

    private void setupWorkEmail() {
        if (this.mProfile != null) {
            String value;
            boolean verified = this.mProfile.isWorkEmailVerified();
            String workEmail = this.mProfile.getWorkEmail();
            String workplace = this.mProfile.getWorkplace();
            boolean hasWorkEmail;
            if (workEmail == null || workEmail.isEmpty()) {
                hasWorkEmail = false;
            } else {
                hasWorkEmail = true;
            }
            if (verified) {
                if (workplace == null || workplace.isEmpty()) {
                    String emailSuffix = workEmail.substring(workEmail.lastIndexOf("@") + 1);
                    value = String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_WORKPLACE_VERIFIED_PS), new Object[]{emailSuffix});
                } else {
                    value = workplace;
                }
            } else if (verified || !hasWorkEmail) {
                value = this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_WORKPLACE_NOT_SET);
            } else {
                value = this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_WORK_PENDING);
            }
            this.mSlWorkplace.setValueText(value);
        }
    }

    private void quitRideWith() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_QUIT);
        final int idProvider = this.mCpnm.getIdProviderType();
        this.mCpnm.quitCarpool(new IResultOk() {

            class C26281 implements Runnable {
                C26281() {
                }

                public void run() {
                    SettingsCarpoolActivity.this.mNtMgr.CloseProgressPopup();
                    SettingsCarpoolActivity.this.setResult(7);
                    SettingsCarpoolActivity.this.finish();
                }
            }

            public void onResult(boolean isOk) {
                if (isOk) {
                    Logger.i("quitRideWith: Carpool user deleted successfully in RT");
                    if (idProvider == 1 || idProvider == 0) {
                        Logger.i("quitRideWith: Logging out of Google service, provider=" + idProvider);
                        Intent sign = new Intent(SettingsCarpoolActivity.this, GoogleSignInActivity.class);
                        sign.putExtra(GoogleSignInActivity.GOOGLE_CONNECT_ACTION, 2);
                        SettingsCarpoolActivity.this.startActivity(sign);
                    } else {
                        Logger.i("quitRideWith: Not logging out of provider service, provider=" + idProvider);
                    }
                    SettingsCarpoolActivity.this.mNtMgr.CloseProgressPopup();
                    SettingsCarpoolActivity.this.mNtMgr.OpenProgressIconPopup(SettingsCarpoolActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_QUIT_DONE), "sign_up_big_v");
                    SettingsCarpoolActivity.this.postDelayed(new C26281(), 1000);
                    return;
                }
                Logger.e("quitRideWith: Error - Carpool user not deleted in RT");
                MsgBox.openMessageBox(SettingsCarpoolActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_UHHOHE), SettingsCarpoolActivity.this.mNtMgr.getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 444) {
            ConfigManager.getInstance().sendLogsClick();
        }
        if (requestCode == 1001) {
            this.mProfile = this.mCpnm.getCarpoolProfileNTV();
            setupWorkEmail();
        }
        if (requestCode == 1003) {
            this.mProfile = this.mCpnm.getCarpoolProfileNTV();
            setSeatsText();
        } else if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        ((WazeSettingsView) findViewById(C1283R.id.settingsCarpoolProfile)).setValueText(String.format(this.mNtMgr.getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_PROFILE_COMPLETE_PS), new Object[]{Integer.valueOf(CarpoolDriverProfileActivity.getPercentCompleteProfile())}));
        super.onActivityResult(requestCode, resultCode, data);
    }
}
