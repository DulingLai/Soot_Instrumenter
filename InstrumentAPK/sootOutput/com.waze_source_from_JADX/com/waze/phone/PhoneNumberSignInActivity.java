package com.waze.phone;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.settings.SettingsValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.EditTextUtils;
import com.waze.utils.ImageRepository;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PhoneNumberSignInActivity extends ActivityBase implements AuthenticateCallbackActivity {
    public static final int CARPOOL_MODE = 3;
    public static final int CHANGE_NUMBER_MODE = 2;
    public static final int CONNECTED_RESULT_CODE = 4;
    public static final String FON_SHON_REA_SON = "fon_shon_rea_son";
    protected static final float HINT_SIZE = 14.0f;
    public static final String INTENT_BACK_ENABLED = "back";
    public static final String INTENT_TYPE = "type";
    public static final int NORMAL_MODE = 1;
    public static final String REPORT_START = "report_start";
    public static final int RQ_ALREADY_WAZER = 3333;
    private static final int RQ_CHOOSE_COUNTRY = 1000;
    public static final int RQ_VERIFY = 1111;
    protected static final float TEXT_SIZE = 16.0f;
    public static boolean bIsBackEnabled = false;
    public static boolean bIsForCarpool = false;
    public static boolean bIsInit = false;
    public static boolean bIsInviteRequestSent = false;
    public static boolean bIsNeedVerification = false;
    public static boolean bIsUpgrade = false;
    public static boolean bStartedPinActivity = false;
    static boolean mHasTried = false;
    static SettingsValue[] strCountryCodes = null;
    int Index = -1;
    boolean IsAuthenticate = false;
    boolean IsTimerCallback = false;
    EditText PhoneBox;
    public boolean bIsChangedNumber = false;
    public boolean bIsInvite = false;
    Handler f86h = new Handler();
    String mHashNumber = null;
    private INextActionCallback mManagerAnswerCb = new C22671();
    private NativeManager mNm;
    Runnable mRunnable = null;
    private TextView mSendPhonBtn;
    String[] scountryCodes = null;
    List<String> strCodes = null;

    class C22671 implements INextActionCallback {
        C22671() {
        }

        public void setNextIntent(Intent intent) {
            if (intent != null) {
                PhoneNumberSignInActivity.this.startActivityForResult(intent, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
            } else {
                Logger.e("PhoneNumberSignInActivity: received null intent");
            }
        }

        public void setNextFragment(Fragment fragment) {
            Logger.e("PhoneNumberSignInActivity: received unexpected setNextFragment");
        }

        public void setNextResult(int result) {
            if (result == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                PhoneNumberSignInActivity.this.setResult(0);
                PhoneNumberSignInActivity.this.finish();
            } else if (result == -1) {
                PhoneNumberSignInActivity.this.setResult(-1);
                PhoneNumberSignInActivity.this.finish();
            } else {
                Logger.e("PhoneNumberSignInActivity: received unexpected result:" + result);
            }
        }

        public Context getContext() {
            return PhoneNumberSignInActivity.this;
        }
    }

    class C22682 implements OnClickListener {
        C22682() {
        }

        public void onClick(View v) {
            PhoneNumberSignInActivity.this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ALREADY_WAZER_CLICKED, null, null, true);
            PhoneNumberSignInActivity.this.startActivityForResult(new Intent(PhoneNumberSignInActivity.this, LoginOptionsActivity.class), 100);
        }
    }

    class C22693 implements OnClickListener {
        C22693() {
        }

        public void onClick(View v) {
            PhoneNumberSignInActivity.this.mNm.SignUplogAnalytics("SKIP", null, null, true);
            if (PhoneNumberSignInActivity.bIsUpgrade) {
                MyWazeNativeManager.getInstance().ShowUserScreenIfNeeded();
                NativeManager.getInstance().signup_finished();
                MainActivity.bSignupSkipped = true;
                PhoneNumberSignInActivity.this.setResult(-1);
                PhoneNumberSignInActivity.this.finish();
                return;
            }
            PhoneNumberSignInActivity.this.startActivityForResult(new Intent(PhoneNumberSignInActivity.this, PhoneKeepInMindActivity.class), 1);
        }
    }

    class C22704 implements OnClickListener {
        C22704() {
        }

        public void onClick(View v) {
            PhoneNumberSignInActivity.this.mNm.SignUplogAnalytics("LEARN_MORE", null, null, true);
            AppService.OpenBrowser(MyWazeNativeManager.getInstance().getLearnMorePrivacyUrlNTV());
        }
    }

    class C22715 implements OnClickListener {
        C22715() {
        }

        public void onClick(View v) {
            PhoneNumberSignInActivity.this.startActivityForResult(new Intent(PhoneNumberSignInActivity.this, ChooseCountryPhoneActivity.class), 1000);
        }
    }

    class C22726 implements OnClickListener {
        C22726() {
        }

        public void onClick(View v) {
            if (PhoneNumberSignInActivity.this.PhoneBox.getText() != null && PhoneNumberSignInActivity.this.PhoneBox.getText().toString() != null && !PhoneNumberSignInActivity.this.PhoneBox.getText().toString().equals("")) {
                PhoneNumberSignInActivity.this.SendPhone(v);
            }
        }
    }

    class C22737 implements OnEditorActionListener {
        C22737() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 4) {
                return false;
            }
            PhoneNumberSignInActivity.this.SendPhone(null);
            return true;
        }
    }

    class C22748 implements TextWatcher {
        C22748() {
        }

        public void afterTextChanged(Editable arg0) {
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            DisplayUtils.setButtonEnabled(PhoneNumberSignInActivity.this.mSendPhonBtn, PhoneNumberSignInActivity.this.isValidPhoneFormat());
        }
    }

    class C22759 implements OnClickListener {
        C22759() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ONBOARDING_CANCELED);
            PhoneNumberSignInActivity.this.setResult(0);
            PhoneNumberSignInActivity.this.finish();
        }
    }

    protected void onDestroy() {
        if (this.mRunnable != null) {
            AppService.Remove(this.mRunnable);
            this.mRunnable = null;
        }
        SmsReceiver.UnRegister(AppService.getAppContext());
        super.onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        setContentView(C1283R.layout.phone_number_signin);
        bIsInit = true;
        this.mNm = NativeManager.getInstance();
        if (!bIsInviteRequestSent) {
            this.mNm.GetInviteData();
            bIsInviteRequestSent = true;
        }
        bIsBackEnabled = false;
        bIsUpgrade = false;
        bIsForCarpool = false;
        if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr().isSplashVisible()) {
            AppService.getMainActivity().getLayoutMgr().cancelSplash();
        }
        if (getIntent().getExtras().getInt(INTENT_BACK_ENABLED) > 0) {
            bIsBackEnabled = true;
        }
        int activityType = getIntent().getExtras().getInt("type");
        if (activityType > 0) {
            if (activityType == 1) {
                this.bIsChangedNumber = false;
            } else if (activityType == 2) {
                this.bIsChangedNumber = true;
            } else if (activityType == 3) {
                bIsForCarpool = true;
            }
            bIsUpgrade = true;
        }
        if (getIntent().getBooleanExtra(REPORT_START, true)) {
            this.mNm.SignUplogAnalytics("START", null, null, true);
        }
        String reason = getIntent().getStringExtra(FON_SHON_REA_SON);
        if (reason != null) {
            this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_WIZARD_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, reason, true);
        } else {
            this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_WIZARD_SHOWN, null, null, true);
        }
        this.mNm.SetPhoneIsFirstTime(false);
        int nIndexOfPhone = 0;
        String CountryCode = GetCountryZipCode();
        SmsReceiver.Register(AppService.getAppContext());
        Set<String> countryCodes = PhoneNumberUtil.getInstance().getSupportedRegions();
        this.scountryCodes = new String[countryCodes.size()];
        countryCodes.toArray(this.scountryCodes);
        this.strCodes = new ArrayList(Arrays.asList(this.scountryCodes));
        strCountryCodes = new SettingsValue[this.strCodes.size()];
        int i = 0;
        while (i < strCountryCodes.length) {
            int CountryNumber = PhoneNumberUtil.getInstance().getCountryCodeForRegion((String) this.strCodes.get(i));
            if (((String) this.strCodes.get(i)).equals(CountryCode)) {
                nIndexOfPhone = i;
                this.Index = i;
            }
            Locale locale = new Locale("", (String) this.strCodes.get(i));
            this.strCodes.set(i, ((String) this.strCodes.get(i)) + " (+" + CountryNumber + ")");
            String country = locale.getDisplayCountry();
            SettingsValue settingsValue;
            if (this.Index != -1) {
                settingsValue = new SettingsValue(Integer.toString(i), country + " (+" + CountryNumber + ")", i == this.Index);
            } else {
                settingsValue = new SettingsValue(Integer.toString(i), country + " (+" + CountryNumber + ")", false);
            }
            strCountryCodes[i] = s;
            i++;
        }
        if (CountryCode == null || CountryCode.equals("")) {
            this.Index = 0;
        }
        Arrays.sort(strCountryCodes, SettingsValue.comparator);
        if (bIsForCarpool) {
            SetupCarpoolMembers();
            findViewById(C1283R.id.already_wazer_text).setVisibility(8);
            findViewById(C1283R.id.AlreadyWazerSeperator).setVisibility(8);
            findViewById(C1283R.id.alreadyWazerIcon).setVisibility(8);
        } else if (bIsUpgrade) {
            SetUpgradeMembers();
        } else {
            ((TextView) findViewById(C1283R.id.PhoneTitleText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_BECOME_A_TRUE_WAZER));
            ((TextView) findViewById(C1283R.id.PhoneBodyText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_ENTER_YOUR_PHONE_NUMBER_TO));
            findViewById(C1283R.id.already_wazer_text).setVisibility(0);
            findViewById(C1283R.id.AlreadyWazerSeperator).setVisibility(0);
            String str1 = this.mNm.getLanguageString(DisplayStrings.DS_WAZE_WILL_NEVER_SHARE) + '\n';
            String str2 = this.mNm.getLanguageString(DisplayStrings.DS_LEARN_MORE);
            SpannableString content = new SpannableString(str1 + str2);
            content.setSpan(new UnderlineSpan(), str1.length(), str1.length() + str2.length(), 0);
            ((TextView) findViewById(C1283R.id.PhoneLearnMoreText)).setText(content);
            this.mSendPhonBtn = (TextView) findViewById(C1283R.id.SendPhoneButton);
            this.mSendPhonBtn.setText(this.mNm.getLanguageString(DisplayStrings.DS_NEXT));
        }
        ((TextView) findViewById(C1283R.id.already_wazer_text)).setText(this.mNm.getLanguageString(311));
        findViewById(C1283R.id.already_wazer_text).setOnClickListener(new C22682());
        if ((MyWazeNativeManager.getInstance().getContactLoggedInNTV() && bIsUpgrade) || bIsBackEnabled || bIsForCarpool) {
            findViewById(C1283R.id.skip_text).setVisibility(8);
            findViewById(C1283R.id.skip_text_separator).setVisibility(8);
        } else {
            findViewById(C1283R.id.skip_text).setVisibility(0);
            findViewById(C1283R.id.skip_text_separator).setVisibility(0);
        }
        ((TextView) findViewById(C1283R.id.skip_text)).setText(this.mNm.getLanguageString(DisplayStrings.DS_SKIP));
        findViewById(C1283R.id.skip_text).setOnClickListener(new C22693());
        ((TextView) findViewById(C1283R.id.PhoneLearnMoreText)).setOnClickListener(new C22704());
        ((TextView) findViewById(C1283R.id.spinner_text)).setText((CharSequence) this.strCodes.get(nIndexOfPhone));
        findViewById(C1283R.id.CountryCode).setOnClickListener(new C22715());
        findViewById(C1283R.id.SendPhoneButton).setOnClickListener(new C22726());
        this.PhoneBox = (EditText) findViewById(C1283R.id.phoneNumberBox);
        this.PhoneBox.setTypeface(Typeface.createFromAsset(getAssets(), ResManager.mFontRobotoRegPath));
        this.PhoneBox.setOnTouchListener(EditTextUtils.getKeyboardLockOnTouchListener(this, this.PhoneBox, null));
        this.PhoneBox.setOnEditorActionListener(new C22737());
        this.PhoneBox.addTextChangedListener(new C22748());
        TelephonyManager tMgr = (TelephonyManager) getSystemService("phone");
        String MyPhoneNumber = MyWazeNativeManager.getInstance().socialContactsGetLastPhoneUsed();
        String countryCode = (String) this.strCodes.get(nIndexOfPhone);
        int openingBracketIndex = countryCode.indexOf("(");
        int closingBracketIndex = countryCode.indexOf(")");
        if (!(openingBracketIndex == -1 || closingBracketIndex == -1 || openingBracketIndex >= closingBracketIndex)) {
            countryCode = countryCode.substring(openingBracketIndex + 1, closingBracketIndex);
            if (MyPhoneNumber != null && MyPhoneNumber.startsWith(countryCode)) {
                MyPhoneNumber = MyPhoneNumber.substring(countryCode.length());
            }
        }
        if (MyPhoneNumber == null || MyPhoneNumber.equals("")) {
            this.mSendPhonBtn.getBackground().setAlpha(125);
            this.mSendPhonBtn.setTextColor(Color.parseColor("#77000000"));
            return;
        }
        this.PhoneBox.setText(MyPhoneNumber);
    }

    private boolean isValidPhoneFormat() {
        return PhoneFormat(this.PhoneBox.getText().toString(), this.scountryCodes[this.Index]) != null;
    }

    private void SetUpgradeMembers() {
        if (!MyWazeNativeManager.getInstance().isRandomUserNTV()) {
            findViewById(C1283R.id.already_wazer_text).setVisibility(8);
            findViewById(C1283R.id.AlreadyWazerSeperator).setVisibility(8);
            findViewById(C1283R.id.alreadyWazerIcon).setVisibility(8);
        }
        if (bIsBackEnabled) {
            if (this.bIsChangedNumber) {
                ((ImageView) findViewById(C1283R.id.phone_number_image)).setImageResource(C1283R.drawable.signup_illu_happy);
                ((TextView) findViewById(C1283R.id.PhoneTitleText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_ENTER_NEW_NUMBER));
                ((TextView) findViewById(C1283R.id.PhoneBodyText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_TYPE_A_NEW_PHONE_NUMBER_TO_UPDATE_YOUR_PROFILE));
            } else {
                ((ImageView) findViewById(C1283R.id.phone_number_image)).setImageResource(C1283R.drawable.sign_up_illu_social);
                ((TextView) findViewById(C1283R.id.PhoneTitleText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_DRIVE_SOCIALLY));
                ((TextView) findViewById(C1283R.id.PhoneBodyText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_REGISTER_TO_USE_THIS_FEATURE_ENTER_YOUR_PHONE));
            }
            String str1 = this.mNm.getLanguageString(DisplayStrings.DS_CONFIRM_IDENTITY) + '\n';
            String str2 = this.mNm.getLanguageString(DisplayStrings.DS_LEARN_MORE);
            SpannableString content = new SpannableString(str1 + str2);
            content.setSpan(new UnderlineSpan(), str1.length(), str1.length() + str2.length(), 0);
            ((TextView) findViewById(C1283R.id.PhoneLearnMoreText)).setText(content);
            this.mSendPhonBtn = (TextView) findViewById(C1283R.id.SendPhoneButton);
            this.mSendPhonBtn.setText(this.mNm.getLanguageString(DisplayStrings.DS_VERIFY_MY_NUMBER));
            return;
        }
        ((ImageView) findViewById(C1283R.id.phone_number_image)).setImageResource(C1283R.drawable.signup_illu_happy);
        ((TextView) findViewById(C1283R.id.PhoneTitleText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_WELCOME_BACK) + " " + MyWazeNativeManager.getInstance().getWelcomeName() + "!");
        findViewById(C1283R.id.alreadyWazerIcon).setVisibility(8);
        ((TextView) findViewById(C1283R.id.PhoneBodyText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_FROM_NOW_ON_MOST_FEATURES));
        str1 = this.mNm.getLanguageString(DisplayStrings.DS_WAZE_WILL_NEVER_SHARE) + '\n';
        str2 = this.mNm.getLanguageString(DisplayStrings.DS_LEARN_MORE);
        content = new SpannableString(str1 + str2);
        content.setSpan(new UnderlineSpan(), str1.length(), str1.length() + str2.length(), 0);
        ((TextView) findViewById(C1283R.id.PhoneLearnMoreText)).setText(content);
        this.mSendPhonBtn = (TextView) findViewById(C1283R.id.SendPhoneButton);
        this.mSendPhonBtn.setText(this.mNm.getLanguageString(DisplayStrings.DS_NEXT));
    }

    private void SetupCarpoolMembers() {
        TitleBar tb = (TitleBar) findViewById(C1283R.id.theTitleBar);
        tb.setVisibility(0);
        tb.init((Activity) this, this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_PHONE_TITLE), false);
        tb.setOnClickCloseListener(new C22759());
        ((ImageView) findViewById(C1283R.id.phone_number_image)).setImageResource(C1283R.drawable.signup_illu_phone_registration);
        ((TextView) findViewById(C1283R.id.PhoneTitleText)).setVisibility(8);
        ((TextView) findViewById(C1283R.id.PhoneBodyText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_PHONE_EXPLANATION1));
        ((TextView) findViewById(C1283R.id.PhoneLearnMoreText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_PHONE_EXPLANATION2));
        this.mSendPhonBtn = (TextView) findViewById(C1283R.id.SendPhoneButton);
        this.mSendPhonBtn.setText(this.mNm.getLanguageString(DisplayStrings.DS_NEXT));
    }

    public void InviteDataCallback(String FullName, String Phone, String Image) {
        this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FROM_INVITE, null, null, true);
        ((TextView) findViewById(C1283R.id.PhoneTitleText)).setText(FullName + " " + NativeManager.getInstance().getLanguageString(DisplayStrings.DS_HAS_INVITED_YOU_TO_JOIN_WAZE));
        ((ImageView) findViewById(C1283R.id.phone_number_image)).setImageResource(C1283R.drawable.signup_friendinvite);
        findViewById(C1283R.id.phone_number_invite_layout).setVisibility(0);
        this.bIsInvite = true;
        if (this.PhoneBox.getText().length() > 0) {
            this.mSendPhonBtn.setText(this.mNm.getLanguageString(DisplayStrings.DS_YES_THATS_MY_NUMBER));
        }
        if (Image != null && !Image.equals("")) {
            ImageRepository.instance.getImage(Image, (ImageView) findViewById(C1283R.id.phone_number_invite_friend_image), this);
        }
    }

    public void AuthenticateCallback(int nType) {
        Logger.d("PhoneNumberSignIn: AuthenticateCallback: " + nType);
        this.IsAuthenticate = true;
        if (nType == 0) {
            this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
            if (this.mRunnable != null) {
                AppService.Remove(this.mRunnable);
                this.mRunnable = null;
            }
            this.f86h.postDelayed(new Runnable() {
                public void run() {
                    PhoneNumberSignInActivity.this.mNm.CloseProgressPopup();
                    if (!PhoneNumberSignInActivity.bIsForCarpool) {
                        MyWazeNativeManager.getInstance().setGuestUserNTV(false);
                        MainActivity.bToOpenAccountPopup = true;
                        PhoneNumberSignInActivity.this.setResult(-1);
                        PhoneNumberSignInActivity.this.finish();
                    } else if (CarpoolOnboardingManager.inWazeRegister()) {
                        CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, PhoneNumberSignInActivity.this.mManagerAnswerCb);
                    } else {
                        Logger.w("PhoneNumberActivitySignIn: AuthenticateCallback, 0: Carpool waze register not during onborading");
                        PhoneNumberSignInActivity.this.setResult(-1);
                        PhoneNumberSignInActivity.this.finish();
                    }
                }
            }, 1000);
            this.mNm.OpenProgressIconPopup(this.mNm.getLanguageString(DisplayStrings.DS_PHONE_VERIFIED), "sign_up_big_v");
        } else if (nType == 2) {
        } else {
            if (nType == 6) {
                MyWazeNativeManager.getInstance().setContactsSignIn(false, bIsUpgrade, null, null);
                if (!bIsForCarpool) {
                    MyWazeNativeManager.getInstance().setGuestUserNTV(false);
                    setResult(-1);
                    finish();
                } else if (CarpoolOnboardingManager.inWazeRegister()) {
                    CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, this.mManagerAnswerCb);
                } else {
                    Logger.w("PhoneNumberActivitySignIn: AuthenticateCallback, 6: Carpool waze register not during onborading");
                    setResult(-1);
                    finish();
                }
            } else if (nType == 3) {
                this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
                this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_EXISTED, null, null, true);
                if (this.mRunnable != null) {
                    AppService.Remove(this.mRunnable);
                    this.mRunnable = null;
                }
                this.mNm.CloseProgressPopup();
                if (bIsUpgrade) {
                    MyWazeNativeManager.getInstance().setContactsSignIn(true, true, null, null);
                    return;
                }
                this.f86h.postDelayed(new Runnable() {
                    public void run() {
                        PhoneNumberSignInActivity.this.mNm.CloseProgressPopup();
                        PhoneNumberSignInActivity.this.startActivityForResult(new Intent(PhoneNumberSignInActivity.this, PhoneAlreadyAWazerActivity.class), 3333);
                    }
                }, 1000);
                this.mNm.OpenProgressIconPopup(this.mNm.getLanguageString(DisplayStrings.DS_PHONE_VERIFIED), "sign_up_big_v");
            } else {
                if (nType != 5) {
                    this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_SEND_FAILED, null, null, true);
                }
                if (this.mRunnable != null) {
                    AppService.Remove(this.mRunnable);
                    this.mRunnable = null;
                }
                this.mNm.CloseProgressPopup();
                MsgBox.openMessageBox(this.mNm.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNm.getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
            }
        }
    }

    String GetCountryZipCode() {
        String CountryID = "";
        return ((TelephonyManager) getSystemService("phone")).getSimCountryIso().toUpperCase();
    }

    public void SendPhone(View v) {
        mHasTried = true;
        String formattedPhone = PhoneFormat(this.PhoneBox.getText().toString(), this.scountryCodes[this.Index]);
        if (formattedPhone != null) {
            this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_PHONE_SEND, null, null, true);
            AddressBookImpl.getInstance().SetCountryID(this.scountryCodes[this.Index]);
            this.mHashNumber = this.mNm.SHA256(formattedPhone);
            this.mNm.AuthPhoneNumber(formattedPhone, this.mHashNumber, 0, this.scountryCodes[this.Index]);
            this.mNm.OpenProgressPopup(this.mNm.getLanguageString(DisplayStrings.DS_WAITING_FOR_SMS));
            if (this.mRunnable != null) {
                AppService.Remove(this.mRunnable);
                this.mRunnable = null;
            }
            this.mRunnable = new Runnable() {
                public void run() {
                    if (PhoneNumberSignInActivity.this.IsAuthenticate) {
                        PhoneNumberSignInActivity.this.IsTimerCallback = true;
                        PhoneNumberSignInActivity.this.onPinTokenSet();
                    }
                }
            };
            AppService.Post(this.mRunnable, LoginOptionsActivity.OPEN_VALIDATION_SCREEN_DELAY);
        }
    }

    public void onPinTokenSet() {
        if (this.mRunnable != null) {
            AppService.Remove(this.mRunnable);
            this.mRunnable = null;
        }
        this.mNm.CloseProgressPopup();
        if (this.IsTimerCallback) {
            this.mNm.SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FAIL_READ_SMS, null, null, true);
            this.IsTimerCallback = false;
            SmsReceiver.UnRegister(AppService.getAppContext());
            Intent i = new Intent(this, PhoneVerifyYourNumbersActivity.class);
            i.putExtra("Hash", this.mHashNumber);
            i.putExtra("bIsForCarpool", bIsForCarpool);
            startActivityForResult(i, 1111);
            bStartedPinActivity = true;
            return;
        }
        this.f86h.postDelayed(new Runnable() {
            public void run() {
                PhoneNumberSignInActivity.this.mNm.CloseProgressPopup();
                if (!PhoneNumberSignInActivity.bIsForCarpool) {
                    MyWazeNativeManager.getInstance().setGuestUserNTV(false);
                    MainActivity.bToOpenAccountPopup = true;
                    PhoneNumberSignInActivity.this.setResult(-1);
                    PhoneNumberSignInActivity.this.finish();
                } else if (!PhoneNumberSignInActivity.bStartedPinActivity) {
                    if (CarpoolOnboardingManager.inWazeRegister()) {
                        CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, PhoneNumberSignInActivity.this.mManagerAnswerCb);
                        return;
                    }
                    Logger.w("PhoneNumberActivitySignIn: onPinTokenSet: Carpool waze register not during onborading");
                    PhoneNumberSignInActivity.this.setResult(-1);
                    PhoneNumberSignInActivity.this.finish();
                }
            }
        }, 1000);
        this.mNm.OpenProgressIconPopup(this.mNm.getLanguageString(DisplayStrings.DS_PHONE_VERIFIED), "sign_up_big_v");
    }

    public void onBackPressed() {
        if (bIsBackEnabled) {
            if (bIsForCarpool) {
                if (CarpoolOnboardingManager.inWazeRegister()) {
                    CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.mManagerAnswerCb);
                } else {
                    Logger.w("PhoneNumberActivitySignIn: onBackPressed: Carpool waze register not during onborading");
                }
            }
            setResult(0);
            finish();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String PhoneFormat(java.lang.String r8, java.lang.String r9) {
        /*
        r7 = this;
        r1 = 0;
        r2 = 0;
        r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance();	 Catch:{ NumberParseException -> 0x0032 }
        r4 = r5.parse(r8, r9);	 Catch:{ NumberParseException -> 0x0032 }
        r2 = r5.isValidNumber(r4);	 Catch:{ NumberParseException -> 0x0032 }
        if (r2 != 0) goto L_0x0011;
    L_0x0010:
        return r1;
    L_0x0011:
        r6 = com.waze.NativeManager.getInstance();	 Catch:{ NumberParseException -> 0x0032 }
        r6 = r6.ValidateMobileTypeNTV();	 Catch:{ NumberParseException -> 0x0032 }
        if (r6 == 0) goto L_0x002b;
    L_0x001b:
        r3 = r5.getNumberType(r4);	 Catch:{ NumberParseException -> 0x0032 }
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;	 Catch:{ NumberParseException -> 0x0032 }
        if (r3 == r6) goto L_0x002b;
    L_0x0023:
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.MOBILE;	 Catch:{ NumberParseException -> 0x0032 }
        if (r3 == r6) goto L_0x002b;
    L_0x0027:
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER;	 Catch:{ NumberParseException -> 0x0032 }
        if (r3 != r6) goto L_0x0010;
    L_0x002b:
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;	 Catch:{ NumberParseException -> 0x0032 }
        r1 = r5.format(r4, r6);	 Catch:{ NumberParseException -> 0x0032 }
        goto L_0x0010;
    L_0x0032:
        r0 = move-exception;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.phone.PhoneNumberSignInActivity.PhoneFormat(java.lang.String, java.lang.String):java.lang.String");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            if (resultCode == -1) {
                this.Index = data.getExtras().getInt("index");
                ((TextView) findViewById(C1283R.id.spinner_text)).setText((CharSequence) this.strCodes.get(this.Index));
            }
        } else if (requestCode == 3333) {
            if (resultCode == -1) {
                if (!bIsForCarpool) {
                    MyWazeNativeManager.getInstance().setGuestUserNTV(false);
                    setResult(-1);
                    finish();
                } else if (CarpoolOnboardingManager.inWazeRegister()) {
                    CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, this.mManagerAnswerCb);
                } else {
                    Logger.w("PhoneNumberActivitySignIn: onActivityResult: RQ_ALREADY_WAZER Carpool waze register not during onborading");
                    setResult(-1);
                    finish();
                }
            }
        } else if (requestCode == 1111) {
            if (resultCode != -1) {
                bStartedPinActivity = false;
            } else if (!bIsForCarpool) {
                MyWazeNativeManager.getInstance().setGuestUserNTV(false);
                setResult(-1);
                finish();
            } else if (CarpoolOnboardingManager.inWazeRegister()) {
                CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, this.mManagerAnswerCb);
            } else {
                Logger.w("PhoneNumberActivitySignIn: onActivityResult:RQ_VERIFY Carpool waze register not during onborading");
                setResult(-1);
                finish();
            }
        } else if (requestCode == CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY && resultCode == 0) {
            if (CarpoolOnboardingManager.inWazeRegister()) {
                CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.mManagerAnswerCb);
            } else {
                Logger.w("PhoneNumberActivitySignIn: onActivityResult: REQ_CARPOOL_JOIN_ACTIVITY Carpool waze register not during onborading\"");
            }
            setResult(0);
            finish();
        } else if (resultCode == -1) {
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

    public static SettingsValue[] getCountries() {
        return strCountryCodes;
    }
}
