package com.waze.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import java.io.File;

public class AccountSignInDetails extends Dialog {
    public static final String PROFILE_IMAGE_FILE = "ProfileImage";
    private static boolean active = false;
    private static boolean bIsBackFromFBScreen = false;
    private static boolean bIsContinueClicked = false;
    private static boolean bIsFacebookClicked = false;
    private static boolean bIsNew = false;
    boolean bIsFacebookConnect = false;
    private TextView facebookButton;
    private TextView firstNameView;
    private TextView lastNameView;
    private Context mCtx;
    private Handler mHandler = new Handler();
    private ImageTaker mImageTaker;
    private MyWazeNativeManager mywazeManager;
    private NativeManager nativeManager = AppService.getNativeManager();
    private TextView signButton;

    class C23491 implements OnClickListener {
        C23491() {
        }

        public void onClick(View v) {
            AccountSignInDetails.this.onContinueClicked();
        }
    }

    class C23502 implements OnEditorActionListener {
        C23502() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            AccountSignInDetails.this.onContinueClicked();
            return true;
        }
    }

    class C23513 implements OnClickListener {
        C23513() {
        }

        public void onClick(View v) {
            AccountSignInDetails.this.mImageTaker = new ImageTaker(AppService.getMainActivity(), AccountSignInDetails.PROFILE_IMAGE_FILE);
            int imageDim = ConfigValues.getIntValue(40);
            AccountSignInDetails.this.mImageTaker.setOutputResolution(imageDim, imageDim, 1, 1);
            AccountSignInDetails.this.mImageTaker.sendIntent();
        }
    }

    class C23534 implements OnClickListener {

        class C23521 implements FacebookLoginListener {
            C23521() {
            }

            public void onFacebookLoginResult(boolean success) {
                if (success) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_IMPORT_FB_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
                    NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                    return;
                }
                AccountSignInDetails.bIsFacebookClicked = false;
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_IMPORT_FB_STATUS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
            }
        }

        C23534() {
        }

        public void onClick(View v) {
            AccountSignInDetails.bIsFacebookClicked = true;
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_IMPORT_FB, null, null, true);
            FacebookLoginListener listener = new C23521();
            if (PhoneNumberSignInActivity.bIsUpgrade) {
                FacebookManager.getInstance().loginWithFacebook(AppService.getMainActivity(), FacebookLoginType.SetToken, true, listener);
            } else {
                FacebookManager.getInstance().loginWithFacebook(AppService.getMainActivity(), FacebookLoginType.SignIn, true, listener);
            }
        }
    }

    public AccountSignInDetails(Context context) {
        super(context, C1283R.style.Dialog);
        this.mCtx = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ACCOUNT_DETAILS_SHOWN, null, null, true);
    }

    public void onBackPressed() {
    }

    private void initLayout() {
        setContentView(C1283R.layout.account_signin_popup);
        getWindow().setLayout(-1, -1);
        if (PhoneNumberSignInActivity.bIsUpgrade) {
            ((TextView) findViewById(C1283R.id.account_details_title)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_ACCOUNT_DETAILS_CAPITAL_UPGRADE));
        } else {
            ((TextView) findViewById(C1283R.id.account_details_title)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_ACCOUNT_DETAILS_CAPITAL));
        }
        ((TextView) findViewById(C1283R.id.account_details_title3)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_VISIBLE_ONLY_TO_YOUR_WAZE));
        ((TextView) findViewById(C1283R.id.account_details_tap_to_add_text)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        this.firstNameView = (TextView) findViewById(C1283R.id.firstName);
        this.lastNameView = (TextView) findViewById(C1283R.id.lastName);
        AppService.getMainActivity().DisableOrientation();
        ((EditText) findViewById(C1283R.id.firstName)).setHint(this.nativeManager.getLanguageString(DisplayStrings.DS_FIRST_NAME));
        ((EditText) findViewById(C1283R.id.lastName)).setHint(this.nativeManager.getLanguageString(DisplayStrings.DS_LAST_NAME));
        this.signButton = (TextView) findViewById(C1283R.id.account_details_continue);
        this.signButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_NEXT));
        this.signButton.setOnClickListener(new C23491());
        ((EditText) findViewById(C1283R.id.lastName)).setOnEditorActionListener(new C23502());
        findViewById(C1283R.id.account_details_tap_to_add).setOnClickListener(new C23513());
        this.facebookButton = (TextView) findViewById(C1283R.id.facebookConnect);
        this.facebookButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_IMPORT_FROM_FACEBOOK));
        this.facebookButton.setPaintFlags(8);
        MyWazeNativeManager.getInstance().getMyWazeData(AppService.getMainActivity());
        if (MyWazeNativeManager.getInstance().getFacebookLoggedInNTV()) {
            this.facebookButton.setVisibility(8);
            findViewById(C1283R.id.account_details_facebook_icon).setVisibility(8);
        } else {
            this.facebookButton.setVisibility(0);
            findViewById(C1283R.id.account_details_facebook_icon).setVisibility(0);
        }
        this.facebookButton.setOnClickListener(new C23534());
    }

    private void onContinueClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ACCOUNT_DETAILS_CONTINUE, null, null, true);
        if (this.firstNameView.getText().toString() == null || this.firstNameView.getText().toString().trim().equals("") || this.lastNameView.getText().toString() == null || this.lastNameView.getText().toString().trim().equals("")) {
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FILL_FIRST_AND_LAST_NAME), true);
            return;
        }
        if (this.mImageTaker != null && this.mImageTaker.hasImage()) {
            this.nativeManager.UploadProfileImage(new File(this.mImageTaker.getImagePath()).getAbsolutePath());
        }
        bIsContinueClicked = true;
        close();
    }

    private void close() {
        MainActivity.bToOpenAccountPopup = false;
        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
        if (PhoneNumberSignInActivity.bIsUpgrade) {
            MyWazeNativeManager.getInstance().setContactsSignIn(true, PhoneNumberSignInActivity.bIsUpgrade, String.valueOf(this.firstNameView.getText()), String.valueOf(this.lastNameView.getText()));
        } else if (this.bIsFacebookConnect) {
            NativeManager.getInstance().SuggestUserNameInit();
            NativeManager.getInstance().SuggestUserNameRequest(String.valueOf(this.firstNameView.getText()), String.valueOf(this.lastNameView.getText()), null);
        } else {
            MyWazeNativeManager.getInstance().setContactsSignIn(true, PhoneNumberSignInActivity.bIsUpgrade, String.valueOf(this.firstNameView.getText()), String.valueOf(this.lastNameView.getText()));
        }
    }

    public void dismiss() {
        MainActivity.bToOpenAccountPopup = false;
        NativeManager.getInstance().CloseProgressPopup();
        super.dismiss();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 222 || requestCode == 223) && this.mImageTaker != null) {
            this.mImageTaker.onActivityResult(requestCode, resultCode, data);
            if (this.mImageTaker.hasImage()) {
                ((ImageView) findViewById(C1283R.id.account_details_tap_to_add)).setImageBitmap(this.mImageTaker.getImage());
                findViewById(C1283R.id.account_details_tap_to_add).buildDrawingCache();
            }
        }
    }

    public void onFacebookTokenSet() {
        if (bIsFacebookClicked) {
            NativeManager.getInstance().CloseProgressPopup();
            MyWazeNativeManager.getInstance().getMyWazeData(AppService.getMainActivity());
            this.bIsFacebookConnect = true;
            bIsFacebookClicked = false;
            this.facebookButton.setVisibility(8);
            findViewById(C1283R.id.account_details_facebook_icon).setVisibility(8);
            NativeManager nm = NativeManager.getInstance();
            if (nm.GetSocialIsFirstTimeNTV() && !MyWazeNativeManager.getInstance().isJustJoinedNTV()) {
                nm.SetSocialIsFirstTime(false);
                nm.SignUplogAnalytics("START", null, null, true);
            }
        } else if (bIsContinueClicked) {
            NativeManager.getInstance().SuggestUserNameInit();
            NativeManager.getInstance().SuggestUserNameRequest(String.valueOf(this.firstNameView.getText()), String.valueOf(this.lastNameView.getText()), null);
        }
    }

    public void SetMyWazeData(MyWazeData Data) {
        if (Data.mFirstName == null || Data.mFirstName.equals("")) {
            String sFirstName = AddressBookImpl.getInstance().getLocalFirstName();
            if (sFirstName != null) {
                ((EditText) findViewById(C1283R.id.firstName)).setText(sFirstName);
            }
        } else {
            ((EditText) findViewById(C1283R.id.firstName)).setText(Data.mFirstName);
        }
        if (Data.mLastName == null || Data.mLastName.equals("")) {
            String sLastName = AddressBookImpl.getInstance().getLocalLastName();
            if (sLastName != null) {
                ((EditText) findViewById(C1283R.id.lastName)).setText(sLastName);
            }
        } else {
            ((EditText) findViewById(C1283R.id.lastName)).setText(Data.mLastName);
        }
        final ImageView picture = (ImageView) findViewById(C1283R.id.account_details_tap_to_add);
        if (Data.mFaceBookLoggedIn) {
            ImageRepository.instance.getImage(Data.mImageUrl, new ImageRepository$ImageRepositoryListener() {
                public void onImageRetrieved(final Bitmap drawable) {
                    AccountSignInDetails.this.mHandler.post(new Runnable() {
                        public void run() {
                            picture.setImageBitmap(drawable);
                        }
                    });
                }
            });
            return;
        }
        String sImage = AddressBookImpl.getInstance().getLocalImageURI();
        if (sImage != null && !sImage.equals("") && !sImage.equals("-1")) {
            ImageRepository.instance.getImage(sImage, picture, AppService.getMainActivity());
        }
    }
}
