package com.waze.profile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IOnUserNameResult;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.install.SmartLockManager;
import com.waze.mywaze.MyWazeActivity;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.settings.SettingsSmartLockActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.io.File;

public class MyProfileActivity extends ActivityBase implements IOnUserNameResult {
    public static int INTENT_DONT_SCROLL = 0;
    public static String INTENT_SCROLL_TO = "scroll_to";
    public static int INTENT_SCROLL_TO_EMAIL = 1;
    public static int INTENT_SCROLL_TO_PHONE = 2;
    private WazeSettingsView mEmailEdit;
    private WazeSettingsView mFirstNameEdit;
    private boolean mHasCarpoolProfile = false;
    private boolean mIgnoreTextEdits = false;
    private ImageTaker mImageTaker;
    private boolean mIsUserNameValid = false;
    private WazeSettingsView mLastNameEdit;
    private MyWazeNativeManager mMwnm;
    private NativeManager mNm;
    private WazeSettingsView mPasswordEdit;
    private WazeSettingsView mPhoneView;
    private int mScrollTo = INTENT_DONT_SCROLL;
    private String mText = null;
    private WazeSettingsView mUserNameEdit;
    private String mUserNameStr;
    private String mUserNameSuggested = null;
    private boolean mWasEmailChanged = false;
    private boolean mWasFirstNameChanged = false;
    private boolean mWasLastNameChanged = false;
    private boolean mWasPasswordChanged = false;
    private boolean mWasUserImageChanged = false;
    private boolean mWasUserNameChanged = false;
    private MyWazeData mWazeData;

    class C23721 implements OnClickListener {
        C23721() {
        }

        public void onClick(View v) {
            MyProfileActivity.this.startActivity(new Intent(MyProfileActivity.this, SettingsSmartLockActivity.class));
        }
    }

    class C23732 implements OnClickListener {
        C23732() {
        }

        public void onClick(View v) {
            if (MyProfileActivity.this.mWasPasswordChanged && MyWazeNativeManager.getInstance().isRandomUserNTV() && !MyProfileActivity.this.mWasUserNameChanged) {
                MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ENTER_USERNAME_FIRST), false);
                return;
            }
            MyProfileActivity.this.setResult(-1);
            MyProfileActivity.this.finish();
        }
    }

    class C23743 implements SettingsToggleCallback {
        C23743() {
        }

        public void onToggle(boolean bIsChecked) {
            NativeManager.getInstance().AllowSendmails(bIsChecked);
        }
    }

    class C23754 implements OnClickListener {
        C23754() {
        }

        public void onClick(View v) {
            if (MyProfileActivity.this.mImageTaker == null) {
                MyProfileActivity.this.mImageTaker = new ImageTaker(MyProfileActivity.this, AccountSignInDetails.PROFILE_IMAGE_FILE);
                int imageDim = ConfigValues.getIntValue(40);
                MyProfileActivity.this.mImageTaker.setOutputResolution(imageDim, imageDim, 1, 1);
            }
            MyProfileActivity.this.mImageTaker.sendIntent();
        }
    }

    class C23765 implements OnEditorActionListener {
        C23765() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == 6) {
            }
            return false;
        }
    }

    class C23786 implements OnFocusChangeListener {
        C23786() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                final ScrollView sv = (ScrollView) MyProfileActivity.this.findViewById(C1283R.id.myProfileScroll);
                sv.post(new Runnable() {
                    public void run() {
                        sv.smoothScrollTo(0, MyProfileActivity.this.mUserNameEdit.getTop() - 100);
                    }
                });
            }
        }
    }

    class C23797 implements OnClickListener {
        C23797() {
        }

        public void onClick(View v) {
            if (MyProfileActivity.this.mUserNameSuggested != null) {
                MyProfileActivity.this.mUserNameEdit.setValueText(MyProfileActivity.this.mUserNameSuggested);
                MyProfileActivity.this.mUserNameEdit.setValueSelection(MyProfileActivity.this.mUserNameSuggested.length());
            }
        }
    }

    class C23808 implements OnClickListener {
        C23808() {
        }

        public void onClick(View v) {
            MyProfileActivity.this.changePhoneNumber();
        }
    }

    private static final class OnClickListenerImplementation implements DialogInterface.OnClickListener {
        boolean _bSetImage;
        private boolean _bSetNames;
        private String _email;
        private String _firstName;
        private Bitmap _imageBmp;
        File _imageFile;
        private String _imageUrl;
        private String _lastName;
        private String _originalPassword;
        private String _originalUsername;
        private String _password;
        private String _username;

        private OnClickListenerImplementation() {
            this._bSetNames = false;
        }

        public void setNames(String firstName, String lastName, String username, String password, String email) {
            this._bSetNames = true;
            this._firstName = firstName;
            this._lastName = lastName;
            this._username = username;
            this._password = password;
            this._email = email;
        }

        public void setOriginalCredentials(String originalUsername, String originalPassword) {
            this._originalUsername = originalUsername;
            this._originalPassword = originalPassword;
        }

        public void setImage(File imageFile, String imageUrl, Bitmap imageBmp) {
            this._bSetImage = true;
            this._imageFile = imageFile;
            this._imageUrl = imageUrl;
            this._imageBmp = imageBmp;
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 1) {
                if (this._bSetNames) {
                    if (!(TextUtils.isEmpty(this._originalUsername) || TextUtils.isEmpty(this._originalPassword))) {
                        SmartLockManager.getInstance().renameExistingCredentials(AppService.getMainActivity(), this._originalUsername, this._username, this._originalPassword, this._password);
                    }
                    MyWazeNativeManager.getInstance().setNames(this._firstName, this._lastName, this._username, this._password, this._email);
                }
                if (this._bSetImage) {
                    NativeManager.getInstance().UploadProfileImage(this._imageFile.getAbsolutePath());
                    if (!(this._imageUrl == null || this._imageUrl.isEmpty())) {
                        ImageRepository.instance.unCache(this._imageUrl);
                        ImageRepository.instance.forceCache(this._imageUrl, this._imageBmp, false);
                    }
                    ActivityBase act = AppService.getActiveActivity();
                    if (act instanceof MyWazeActivity) {
                        ((MyWazeActivity) act).updateImage();
                    }
                }
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNm = NativeManager.getInstance();
        getWindow().setSoftInputMode(3);
        requestWindowFeature(1);
        setContentView(C1283R.layout.my_profile);
        this.mMwnm = MyWazeNativeManager.getInstance();
        this.mMwnm.getMyWazeData(this);
        this.mNm.SuggestUserNameInit();
        Intent intent = getIntent();
        if (intent != null) {
            this.mScrollTo = intent.getIntExtra(INTENT_SCROLL_TO, INTENT_DONT_SCROLL);
        }
        if (CarpoolNativeManager.getInstance().configIsOnNTV()) {
            this.mHasCarpoolProfile = CarpoolNativeManager.getInstance().hasCarpoolProfileNTV();
        }
        WazeSettingsView smartLockSetting = (WazeSettingsView) findViewById(C1283R.id.smartLockButton);
        if (ConfigManager.getInstance().getConfigValueBool(370)) {
            smartLockSetting.setVisibility(0);
            smartLockSetting.setText(this.mNm.getLanguageString(DisplayStrings.DS_SMART_LOCK_SETTINGS));
            smartLockSetting.setOnClickListener(new C23721());
        } else {
            smartLockSetting.setVisibility(8);
        }
        ((TitleBar) findViewById(C1283R.id.myProfileTitle)).init((Activity) this, (int) DisplayStrings.DS_MY_PROFILE);
        ((TitleBar) findViewById(C1283R.id.myProfileTitle)).setOnClickCloseListener(new C23732());
        ((SettingsTitleText) findViewById(C1283R.id.myProfileHowTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_HOW_YOUR_FRIENDS_SEE_YOU));
        WazeSettingsView allowEmail = (WazeSettingsView) findViewById(C1283R.id.allowEmail);
        allowEmail.setText(this.mNm.getLanguageString(DisplayStrings.DS_WAZE_CAN_SEND_ME_EMAILS));
        allowEmail.setValue(this.mNm.GetAllowSendMailNTV());
        allowEmail.setOnChecked(new C23743());
        OnClickListener tapToEditPicListener = new C23754();
        findViewById(C1283R.id.myProfileUserPic).setOnClickListener(tapToEditPicListener);
        ((WazeTextView) findViewById(C1283R.id.myProfileTapTo)).setText(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        ((WazeTextView) findViewById(C1283R.id.myProfileTapTo)).setOnClickListener(tapToEditPicListener);
        ((SettingsTitleText) findViewById(C1283R.id.myProfileAdvancedTitle)).setText(NativeManager.getInstance().getLanguageString(96));
        this.mFirstNameEdit = (WazeSettingsView) findViewById(C1283R.id.myProfileFirstName);
        this.mFirstNameEdit.setKeyText(this.mNm.getLanguageString(DisplayStrings.DS_FIRST_NAME));
        this.mFirstNameEdit.setValueText("");
        this.mPasswordEdit = (WazeSettingsView) findViewById(C1283R.id.myProfilePassword);
        this.mPasswordEdit.setValueText("");
        this.mPasswordEdit.setValueType(97);
        this.mPasswordEdit.setValueType(129);
        this.mPasswordEdit.setKeyText(this.mNm.getLanguageString(DisplayStrings.DS_PASSWORD));
        this.mLastNameEdit = (WazeSettingsView) findViewById(C1283R.id.myProfileLastName);
        this.mLastNameEdit.setValueText("");
        this.mLastNameEdit.setValueType(97);
        this.mLastNameEdit.setKeyText(this.mNm.getLanguageString(DisplayStrings.DS_LAST_NAME));
        this.mLastNameEdit.setOnEditorActionListener(new C23765());
        this.mUserNameEdit = (WazeSettingsView) findViewById(C1283R.id.myProfileUserName);
        this.mUserNameEdit.setValueText("");
        this.mUserNameEdit.setKeyText(this.mNm.getLanguageString(DisplayStrings.DS_USER_NAME));
        this.mNm.registerOnUserNameResultListerner(this);
        this.mUserNameEdit.setOnFocusChangeListener(new C23786());
        ((TextView) findViewById(C1283R.id.myProfileUserName_error_code2)).setPaintFlags(8);
        findViewById(C1283R.id.myProfileUserName_error_code2).setOnClickListener(new C23797());
        WazeTextView tvYourPhoto = (WazeTextView) findViewById(C1283R.id.myProfileYourPhotoText);
        if (this.mHasCarpoolProfile) {
            tvYourPhoto.setText(this.mNm.getLanguageString(DisplayStrings.DS_YOUR_PHOTO_AND_FULL_NAME_WILL_BE_SHOWN_TO_FRIENDS_RW));
        } else {
            tvYourPhoto.setText(this.mNm.getLanguageString(DisplayStrings.DS_YOUR_PHOTO_AND_FULL_NAME_WILL_BE_SHOWN_TO_FRIENDS));
        }
        ((SettingsTitleText) findViewById(C1283R.id.myProfileLoginTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_LOGIN_CREDENTIALS));
        this.mPhoneView = (WazeSettingsView) findViewById(C1283R.id.myProfilePhone);
        this.mPhoneView.setKeyText(this.mNm.getLanguageString(308));
        this.mPhoneView.setValueText("");
        this.mPhoneView.setValueType(0);
        this.mPhoneView.setOnClickListener(new C23808());
        this.mPhoneView.setClickOnEdit(true);
        this.mEmailEdit = (WazeSettingsView) findViewById(C1283R.id.myProfileEmail);
        this.mEmailEdit.setKeyText(this.mNm.getLanguageString(383));
        this.mEmailEdit.setValueText("");
        this.mEmailEdit.setValueType(33);
        if (this.mScrollTo != INTENT_DONT_SCROLL) {
            WazeSettingsView scrollTo = this.mFirstNameEdit;
            if (this.mScrollTo == INTENT_SCROLL_TO_EMAIL) {
                scrollTo = this.mEmailEdit;
            } else if (this.mScrollTo == INTENT_SCROLL_TO_PHONE) {
                scrollTo = this.mPhoneView;
            }
            final WazeSettingsView sFinal = scrollTo;
            final ScrollView sv = (ScrollView) findViewById(C1283R.id.myProfileScroll);
            sv.post(new Runnable() {
                public void run() {
                    sv.smoothScrollTo(0, sFinal.getTop() - 100);
                }
            });
        }
        ((SettingsTitleText) findViewById(C1283R.id.myProfileLoginTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_LOGIN_CREDENTIALS));
        ((WazeSettingsView) findViewById(C1283R.id.myProfileLogOutButton)).setText(this.mNm.getLanguageString(DisplayStrings.DS_LOG_OUT));
        WazeSettingsView deleteAccountButton = (WazeSettingsView) findViewById(C1283R.id.myProfileDeleteAccountButton);
        deleteAccountButton.setText(this.mNm.getLanguageString(91));
        deleteAccountButton.setKeyColor(SupportMenu.CATEGORY_MASK);
        findViewById(C1283R.id.myProfileLogOutButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                NativeManager.getInstance().LogOutAccount();
            }
        });
        deleteAccountButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                NativeManager.getInstance().DeleteAccount();
            }
        });
        ((WazeTextView) findViewById(C1283R.id.myProfileYouCanDeleteText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_YOU_CAN_DELETE_YOUR_ACCOUNT_AND_PERSONAL_DATA_ANYTIME));
    }

    public void onPostCreate(Bundle savedInstanceState) {
        this.mFirstNameEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!MyProfileActivity.this.mIgnoreTextEdits) {
                    MyProfileActivity.this.mWasFirstNameChanged = true;
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            }
        });
        this.mLastNameEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!MyProfileActivity.this.mIgnoreTextEdits) {
                    MyProfileActivity.this.mWasLastNameChanged = true;
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            }
        });
        this.mUserNameEdit.addTextChangedListener(new TextWatcher() {
            private Runnable aEvent = null;

            class C23711 implements Runnable {
                C23711() {
                }

                public void run() {
                    if (MyProfileActivity.this.mText != null && MyProfileActivity.this.mText.length() > 0) {
                        MyProfileActivity.this.mNm.SuggestUserNameRequest(null, null, MyProfileActivity.this.mText);
                    }
                }
            }

            public void afterTextChanged(Editable s) {
                if (!MyProfileActivity.this.mIgnoreTextEdits) {
                    MyProfileActivity.this.mWasUserNameChanged = true;
                    View errorFrame = MyProfileActivity.this.findViewById(C1283R.id.myProfileUserName_error_frame);
                    if (errorFrame.getHeight() == 0) {
                        AnimationUtils.growViewToFullsize(errorFrame);
                    }
                    TextView errCode = (TextView) MyProfileActivity.this.findViewById(C1283R.id.myProfileUserName_error_code);
                    errCode.setText(MyProfileActivity.this.mNm.getLanguageString(DisplayStrings.DS_CHECKING));
                    errCode.setTextColor(AppService.getAppResources().getColor(C1283R.color.regular_name_your_wazer_color));
                    errCode.setVisibility(0);
                    MyProfileActivity.this.findViewById(C1283R.id.myProfileUserName_error_code2).setVisibility(8);
                    MyProfileActivity.this.mIsUserNameValid = false;
                    if (this.aEvent != null) {
                        AppService.Remove(this.aEvent);
                    } else {
                        this.aEvent = new C23711();
                    }
                    AppService.Post(this.aEvent, 500);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                MyProfileActivity.this.mText = arg0.toString();
            }
        });
        this.mPasswordEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!MyProfileActivity.this.mIgnoreTextEdits) {
                    MyProfileActivity.this.mWasPasswordChanged = true;
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            }
        });
        this.mEmailEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!MyProfileActivity.this.mIgnoreTextEdits) {
                    MyProfileActivity.this.mWasEmailChanged = true;
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            }
        });
        super.onPostCreate(savedInstanceState);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onDestroy() {
        /*
        r20 = this;
        r1 = 0;
        r0 = r20;
        r7 = r0.mWazeData;
        if (r7 == 0) goto L_0x00f0;
    L_0x0007:
        r0 = r20;
        r7 = r0.mWasLastNameChanged;
        if (r7 != 0) goto L_0x0025;
    L_0x000d:
        r0 = r20;
        r7 = r0.mWasFirstNameChanged;
        if (r7 != 0) goto L_0x0025;
    L_0x0013:
        r0 = r20;
        r7 = r0.mWasUserNameChanged;
        if (r7 != 0) goto L_0x0025;
    L_0x0019:
        r0 = r20;
        r7 = r0.mWasPasswordChanged;
        if (r7 != 0) goto L_0x0025;
    L_0x001f:
        r0 = r20;
        r7 = r0.mWasEmailChanged;
        if (r7 == 0) goto L_0x00f0;
    L_0x0025:
        r0 = r20;
        r7 = r0.mIsUserNameValid;
        if (r7 == 0) goto L_0x0175;
    L_0x002b:
        r0 = r20;
        r4 = r0.mText;
    L_0x002f:
        r5 = "";
        r0 = r20;
        r7 = r0.mWasUserNameChanged;
        if (r7 != 0) goto L_0x0041;
    L_0x0038:
        r0 = r20;
        r7 = r0.mWasPasswordChanged;
        if (r7 != 0) goto L_0x0041;
    L_0x003e:
        r4 = "";
    L_0x0041:
        r0 = r20;
        r7 = r0.mFirstNameEdit;
        r7 = r7.getValueText();
        r7 = r7.toString();
        r18 = r7.trim();
        r7 = r18.isEmpty();
        if (r7 == 0) goto L_0x017d;
    L_0x0057:
        r0 = r20;
        r7 = r0.mWazeData;
        r2 = r7.mFirstName;
    L_0x005d:
        r0 = r20;
        r7 = r0.mLastNameEdit;
        r7 = r7.getValueText();
        r7 = r7.toString();
        r19 = r7.trim();
        r7 = r19.isEmpty();
        if (r7 == 0) goto L_0x0181;
    L_0x0073:
        r0 = r20;
        r7 = r0.mWazeData;
        r3 = r7.mLastName;
    L_0x0079:
        r0 = r20;
        r7 = r0.mEmailEdit;
        r7 = r7.getValueText();
        r7 = r7.toString();
        r17 = r7.trim();
        r7 = r17.isEmpty();
        if (r7 == 0) goto L_0x0185;
    L_0x008f:
        r0 = r20;
        r7 = r0.mWazeData;
        r6 = r7.mEmail;
    L_0x0095:
        r0 = r20;
        r7 = r0.mWasPasswordChanged;
        if (r7 != 0) goto L_0x00a1;
    L_0x009b:
        r0 = r20;
        r7 = r0.mWasUserNameChanged;
        if (r7 == 0) goto L_0x00ad;
    L_0x00a1:
        r0 = r20;
        r7 = r0.mPasswordEdit;
        r7 = r7.getValueText();
        r5 = r7.toString();
    L_0x00ad:
        if (r1 != 0) goto L_0x00b5;
    L_0x00af:
        r1 = new com.waze.profile.MyProfileActivity$OnClickListenerImplementation;
        r7 = 0;
        r1.<init>();
    L_0x00b5:
        r0 = r20;
        r7 = r0.mWasUserNameChanged;
        if (r7 != 0) goto L_0x00c1;
    L_0x00bb:
        r0 = r20;
        r7 = r0.mWasPasswordChanged;
        if (r7 == 0) goto L_0x00ed;
    L_0x00c1:
        r0 = r20;
        r7 = r0.mWazeData;
        r0 = r7.mUserName;
        r16 = r0;
        r7 = android.text.TextUtils.isEmpty(r16);
        if (r7 != 0) goto L_0x00da;
    L_0x00cf:
        r7 = "Wazer";
        r0 = r16;
        r7 = r0.equals(r7);
        if (r7 == 0) goto L_0x00e2;
    L_0x00da:
        r7 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r16 = r7.getRealUserNameNTV();
    L_0x00e2:
        r0 = r20;
        r7 = r0.mWazeData;
        r7 = r7.mPassword;
        r0 = r16;
        r1.setOriginalCredentials(r0, r7);
    L_0x00ed:
        r1.setNames(r2, r3, r4, r5, r6);
    L_0x00f0:
        r0 = r20;
        r7 = r0.mWasUserImageChanged;
        if (r7 == 0) goto L_0x012c;
    L_0x00f6:
        r0 = r20;
        r7 = r0.mImageTaker;
        if (r7 == 0) goto L_0x012c;
    L_0x00fc:
        r0 = r20;
        r7 = r0.mImageTaker;
        r7 = r7.hasImage();
        if (r7 == 0) goto L_0x012c;
    L_0x0106:
        r15 = new java.io.File;
        r0 = r20;
        r7 = r0.mImageTaker;
        r7 = r7.getImagePath();
        r15.<init>(r7);
        if (r1 != 0) goto L_0x011b;
    L_0x0115:
        r1 = new com.waze.profile.MyProfileActivity$OnClickListenerImplementation;
        r7 = 0;
        r1.<init>();
    L_0x011b:
        r0 = r20;
        r7 = r0.mWazeData;
        r7 = r7.mImageUrl;
        r0 = r20;
        r8 = r0.mImageTaker;
        r8 = r8.getImage();
        r1.setImage(r15, r7, r8);
    L_0x012c:
        if (r1 == 0) goto L_0x0160;
    L_0x012e:
        r7 = com.waze.MsgBox.getInstance();
        r0 = r20;
        r8 = r0.mNm;
        r9 = 848; // 0x350 float:1.188E-42 double:4.19E-321;
        r8 = r8.getLanguageString(r9);
        r0 = r20;
        r9 = r0.mNm;
        r10 = 1431; // 0x597 float:2.005E-42 double:7.07E-321;
        r9 = r9.getLanguageString(r10);
        r10 = 1;
        r0 = r20;
        r11 = r0.mNm;
        r12 = 690; // 0x2b2 float:9.67E-43 double:3.41E-321;
        r12 = r11.getLanguageString(r12);
        r0 = r20;
        r11 = r0.mNm;
        r13 = 344; // 0x158 float:4.82E-43 double:1.7E-321;
        r13 = r11.getLanguageString(r13);
        r14 = -1;
        r11 = r1;
        r7.OpenConfirmDialogCustomTimeoutCbJava(r8, r9, r10, r11, r12, r13, r14);
    L_0x0160:
        r0 = r20;
        r7 = r0.mNm;
        r8 = 0;
        r7.SuggestUserNameTerminate(r8);
        r0 = r20;
        r7 = r0.mNm;
        r0 = r20;
        r7.unregisterOnUserNameResultListerner(r0);
        super.onDestroy();
        return;
    L_0x0175:
        r0 = r20;
        r7 = r0.mWazeData;
        r4 = r7.mUserName;
        goto L_0x002f;
    L_0x017d:
        r2 = r18;
        goto L_0x005d;
    L_0x0181:
        r3 = r19;
        goto L_0x0079;
    L_0x0185:
        r6 = r17;
        goto L_0x0095;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.profile.MyProfileActivity.onDestroy():void");
    }

    public void SetMyWazeData(MyWazeData data) {
        this.mWazeData = data;
        this.mIgnoreTextEdits = true;
        if (this.mWazeData.mFirstName == null || this.mWazeData.mFirstName.equals("")) {
            this.mFirstNameEdit.setValueHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        } else {
            this.mFirstNameEdit.setValueText(data.mFirstName);
        }
        if (this.mWazeData.mLastName == null || this.mWazeData.mLastName.equals("")) {
            this.mLastNameEdit.setValueHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        } else {
            this.mLastNameEdit.setValueText(data.mLastName);
        }
        if (this.mWazeData.mEmail == null || this.mWazeData.mEmail.equals("")) {
            this.mEmailEdit.setValueHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        } else {
            this.mEmailEdit.setValueText(data.mEmail);
        }
        if (MyWazeNativeManager.getInstance().isRandomUserNTV()) {
            this.mUserNameEdit.setValueHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        } else {
            this.mUserNameEdit.setValueText(data.mUserName);
        }
        if (this.mWazeData.mPassword != null) {
            this.mPasswordEdit.setValueText(data.mPassword);
        }
        if (data.mPhoneNumber == null || data.mPhoneNumber.equals("")) {
            this.mPhoneView.setValueHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        } else {
            this.mPhoneView.setValueText(data.mPhoneNumber);
        }
        this.mIgnoreTextEdits = false;
        ((ImageView) findViewById(C1283R.id.myProfileUserMood)).setImageResource(MoodManager.getInstance().getBigMoodDrawbleId());
        ImageView picture = (ImageView) findViewById(C1283R.id.myProfileUserPic);
        picture.setImageResource(C1283R.drawable.rs_profilepic_placeholder);
        if (data.mImageUrl != null) {
            ((WazeTextView) findViewById(C1283R.id.myProfileTapTo)).setText(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_EDIT));
            ImageRepository.instance.getImage(data.mImageUrl, 2, picture, null, this);
        }
    }

    public void onBackPressed() {
        if (this.mWasPasswordChanged && MyWazeNativeManager.getInstance().isRandomUserNTV() && !this.mWasUserNameChanged) {
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ENTER_USERNAME_FIRST), false);
        } else {
            super.onBackPressed();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1111 && resultCode == -1) {
            setResult(-1);
            finish();
        }
        if (resultCode == 3) {
            setResult(0);
            finish();
        }
        if ((requestCode == 222 || requestCode == 223) && this.mImageTaker != null) {
            this.mImageTaker.onActivityResult(requestCode, resultCode, data);
            if (this.mImageTaker.hasImage()) {
                ((ImageView) findViewById(C1283R.id.myProfileUserPic)).setImageDrawable(new CircleShaderDrawable(this.mImageTaker.getImage(), 0));
                findViewById(C1283R.id.myProfileUserPic).buildDrawingCache();
                this.mWasUserImageChanged = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showErrorPopup(int bodyTextDS) {
        MsgBox.openMessageBoxWithCallback(this.mNm.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNm.getLanguageString(bodyTextDS), false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MyProfileActivity.this.setResult(0);
                MyProfileActivity.this.finish();
            }
        });
    }

    private void changePhoneNumber() {
        Intent intent = new Intent(this, PhoneRegisterActivity.class);
        intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 2);
        intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, "UPDATE");
        startActivityForResult(intent, 1111);
    }

    public void onUserNameResult(int nType, String username) {
        String errorText = null;
        if (username != null) {
            this.mUserNameStr = username;
            TextView errCode1 = (TextView) findViewById(C1283R.id.myProfileUserName_error_code);
            TextView errCode2 = (TextView) findViewById(C1283R.id.myProfileUserName_error_code2);
            switch (nType) {
                case 0:
                    errorText = this.mNm.getLanguageString(DisplayStrings.DS_LOOKS_GOOD);
                    this.mIsUserNameValid = true;
                    errCode1.setTextColor(AppService.getAppResources().getColor(C1283R.color.regular_name_your_wazer_color));
                    errCode2.setVisibility(8);
                    break;
                case 1:
                    errorText = this.mNm.getLanguageString(DisplayStrings.DS_YOUR_USER_NAME_IS_TOO_SHORT);
                    errCode1.setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    errCode2.setVisibility(8);
                    break;
                case 2:
                    errorText = this.mNm.getLanguageString(DisplayStrings.DS_USERNAME_IS_TOO_LONG);
                    errCode1.setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    errCode2.setVisibility(8);
                    break;
                case 3:
                    errorText = this.mNm.getLanguageString(DisplayStrings.DS_INVALID_CHARACTER_USE_ONLY_LETTERS_AND_NUMBERS);
                    errCode1.setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    errCode2.setVisibility(8);
                    break;
                case 4:
                    errorText = this.mNm.getLanguageString(DisplayStrings.DS_THATS_TAKEN_TRY);
                    errCode2.setVisibility(0);
                    errCode1.setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    this.mUserNameSuggested = username;
                    errCode2.setText(this.mUserNameSuggested);
                    break;
                case 5:
                    errorText = this.mNm.getLanguageString(DisplayStrings.DS_YOU_CANT_USE_THIS_USERNAME);
                    errCode1.setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    break;
            }
            errCode1.setText(errorText);
        }
    }
}
