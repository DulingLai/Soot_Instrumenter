package com.waze.phone;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.RequestPermissionActivity;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.PublicMacros;
import com.waze.strings.DisplayStrings;
import java.util.EnumSet;

public class PhoneAlreadyAWazerActivity extends ActivityBase implements DriveToGetAddressItemArrayCallback, AuthenticateCallbackActivity {
    private static final int ACTIVITY_RESULT_VERIFY_WAZER = 1232;
    private boolean bIsVerifyClicked = false;
    private DriveToNativeManager driveToNativeManager;
    private TextView mAlreadyAWazerBodyText;
    private TextView mAlreadyAWazerHeaderText;
    private TextView mCreateANewAccountText;
    private EnumSet<HomeWorkFavorites> mHomeWorkFlags = EnumSet.noneOf(HomeWorkFavorites.class);
    private TextView mJoinedDate;
    private TextView mNotYourAccountText;
    private TextView mUserNameText;
    private LinearLayout mVerifyMyAccountButton;
    private TextView mVerifyMyAccountText;
    private TextView mYourRankText;
    private TextView mYourScoreText;
    private NativeManager nativeManager;

    class C22521 implements OnClickListener {
        C22521() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_VERIFY_MY_ACCOUNT, null, null, true);
            if (VERSION.SDK_INT >= 23) {
                Intent permissionsIntent = new Intent(PhoneAlreadyAWazerActivity.this, RequestPermissionActivity.class);
                permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.READ_CONTACTS"});
                PhoneAlreadyAWazerActivity.this.startActivityForResult(permissionsIntent, 1232);
                return;
            }
            PhoneAlreadyAWazerActivity.this.nativeManager.OpenProgressPopup(PhoneAlreadyAWazerActivity.this.nativeManager.getLanguageString(290));
            PhoneAlreadyAWazerActivity.this.nativeManager.AuthContacts();
            PhoneAlreadyAWazerActivity.this.bIsVerifyClicked = true;
        }
    }

    class C22532 implements OnClickListener {
        C22532() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_CREATE_NEW_ACCOUNT, null, null, true);
            NativeManager.getInstance().signup_finished();
            PhoneAlreadyAWazerActivity.this.bIsVerifyClicked = false;
            MyWazeNativeManager.getInstance().setContactsSignIn(true, false, null, null);
            PhoneAlreadyAWazerActivity.this.setResult(-1);
            PhoneAlreadyAWazerActivity.this.finish();
        }
    }

    public enum HomeWorkFavorites {
        HOME(0),
        WORK(1);
        
        private int numVal;

        private HomeWorkFavorites(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return this.numVal;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.phone_already_a_wazer);
        initMembers();
        setOnClickListeners();
        initFieldsTexts();
        initMyWazeData();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_ALREADY_WAZER_SHOWN, null, null, true);
        this.mCreateANewAccountText.setPaintFlags(this.mCreateANewAccountText.getPaintFlags() | 8);
    }

    private void initMyWazeData() {
        MyWazeNativeManager.getInstance().getMyWazeDataForVerification(this);
    }

    private void initFieldsTexts() {
        this.mAlreadyAWazerHeaderText.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOU_LOOK_FAMILIAR).toUpperCase());
        this.mAlreadyAWazerBodyText.setText(this.nativeManager.getLanguageString(312));
        this.mVerifyMyAccountText.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YES_ITS_ME));
        this.mNotYourAccountText.setVisibility(8);
        this.mCreateANewAccountText.setText(this.nativeManager.getLanguageString(319));
    }

    private void setOnClickListeners() {
        this.mVerifyMyAccountButton.setOnClickListener(new C22521());
        this.mCreateANewAccountText.setOnClickListener(new C22532());
    }

    public void onPinTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
        if (this.bIsVerifyClicked) {
            startActivityForResult(new Intent(this, PhoneVerifyYourAccountSuccessActivity.class), 1000);
            return;
        }
        MainActivity.bToOpenAccountPopup = true;
        setResult(-1);
        finish();
    }

    public void onBackPressed() {
    }

    public void AuthenticateCallback(int nType) {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_CONTACTS_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, nType == 0 ? AnalyticsEvents.ANALYTICS_EVENT_SUCCESS : AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
        if (nType == 0) {
            MyWazeNativeManager.getInstance().setContactsSignIn(false, PhoneNumberSignInActivity.bIsUpgrade, null, null);
        } else if (nType == 5) {
            startActivityForResult(new Intent(this, PhoneVerifyYourAccountFailureActivity.class), 1);
        } else {
            NativeManager.getInstance().CloseProgressPopup();
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false);
        }
    }

    private void initMembers() {
        this.mAlreadyAWazerHeaderText = (TextView) findViewById(C1283R.id.alreadyAWazerHeaderText);
        this.mAlreadyAWazerBodyText = (TextView) findViewById(C1283R.id.alreadyAWazerBodyText);
        this.mUserNameText = (TextView) findViewById(C1283R.id.userNameText);
        this.mYourScoreText = (TextView) findViewById(C1283R.id.yourScoreText);
        this.mYourRankText = (TextView) findViewById(C1283R.id.yourRankText);
        this.mJoinedDate = (TextView) findViewById(C1283R.id.joinedDateText);
        this.mVerifyMyAccountButton = (LinearLayout) findViewById(C1283R.id.verifyMyAccountButton);
        this.mVerifyMyAccountText = (TextView) findViewById(C1283R.id.verifyText);
        this.mNotYourAccountText = (TextView) findViewById(C1283R.id.notYourAccountText);
        this.mCreateANewAccountText = (TextView) findViewById(C1283R.id.create_a_new_account_text);
        this.nativeManager = NativeManager.getInstance();
        this.driveToNativeManager = DriveToNativeManager.getInstance();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1232) {
            if (resultCode == -1) {
                setResult(-1);
                finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
        } else if (resultCode == -1) {
            AddressBookImpl.getInstance().performSync(true, null);
            this.nativeManager.OpenProgressPopup(this.nativeManager.getLanguageString(290));
            this.nativeManager.AuthContacts();
            this.bIsVerifyClicked = true;
        } else {
            this.bIsVerifyClicked = false;
        }
    }

    public void SetMyWazeData(MyWazeData data) {
        this.mUserNameText.setText(data.mUserName);
        setScore(data);
        setRank(data);
        setJoinedString(data);
        SetMood(data);
    }

    private void SetMood(MyWazeData data) {
        ((ImageView) findViewById(C1283R.id.wazeMood)).setImageResource(MoodManager.getInstance().getMenuMoodDrawableByName(this, data.mMood));
    }

    private void setScore(MyWazeData data) {
        String rankString;
        if (data.mRank > -1) {
            rankString = String.format(this.nativeManager.getLanguageString(313), new Object[]{Integer.valueOf(data.mPts)});
        } else {
            rankString = String.format("%s %s", new Object[]{this.nativeManager.getLanguageString(315), this.nativeManager.getLanguageString(310)});
        }
        this.mYourScoreText.setText(rankString);
    }

    private void setRank(MyWazeData data) {
        String rankString;
        if (data.mRank > 0) {
            rankString = String.format(this.nativeManager.getLanguageString(314), new Object[]{Integer.valueOf(data.mRank)});
        } else {
            rankString = String.format("%s %s", new Object[]{this.nativeManager.getLanguageString(316), this.nativeManager.getLanguageString(310)});
        }
        this.mYourRankText.setText(rankString);
    }

    private void setJoinedString(MyWazeData data) {
        if (data.mJoinedStr == null || data.mJoinedStr.equals("")) {
            this.mJoinedDate.setVisibility(8);
            return;
        }
        this.mJoinedDate.setVisibility(0);
        this.mJoinedDate.setText(String.format(this.nativeManager.getLanguageString(DisplayStrings.DS_LAST_CONNECTED_PD), new Object[]{data.mJoinedStr}));
    }

    private void fillFavoritesFlags() {
        this.driveToNativeManager.getTopTenFavorites(this);
    }

    public void getAddressItemArrayCallback(AddressItem[] ai) {
        for (AddressItem type : ai) {
            switch (type.getType()) {
                case 1:
                    this.mHomeWorkFlags.add(HomeWorkFavorites.HOME);
                    break;
                case 3:
                    this.mHomeWorkFlags.add(HomeWorkFavorites.WORK);
                    break;
                default:
                    break;
            }
        }
        this.nativeManager.CloseProgressPopup();
        if (!this.mHomeWorkFlags.isEmpty()) {
            Intent intent = new Intent(this, PhoneVerifyYourAccountActivity.class);
            intent.putExtra(PublicMacros.HOME_WORK_FLAGS, this.mHomeWorkFlags);
            startActivityForResult(intent, 1);
        }
    }
}
