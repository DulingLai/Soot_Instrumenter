package com.waze.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.mywaze.social.FacebookActivity;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.profile.MyProfileActivity;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class AccountAndLoginActivity extends ActivityBase implements FacebookCallback {
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();

    class C26091 implements OnClickListener {
        C26091() {
        }

        public void onClick(View v) {
            AccountAndLoginActivity.this.nativeManager.DeleteAccount();
        }
    }

    class C26102 implements OnClickListener {
        C26102() {
        }

        public void onClick(View v) {
            if (AccountAndLoginActivity.this.mywazeManager.isGuestUserNTV()) {
                Intent intent = new Intent(AccountAndLoginActivity.this, PhoneRegisterActivity.class);
                intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
                intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, "SETTINGS");
                AccountAndLoginActivity.this.startActivityForResult(intent, 0);
                return;
            }
            AccountAndLoginActivity.this.startActivityForResult(new Intent(AccountAndLoginActivity.this, MyProfileActivity.class), 0);
        }
    }

    class C26113 implements OnClickListener {
        C26113() {
        }

        public void onClick(View v) {
            MyWazeNativeManager.getInstance().getFacebookSettings(AccountAndLoginActivity.this);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1283R.layout.account_and_login);
        this.mywazeManager.getMyWazeData(this);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_ACCOUNT_AND_LOGIN);
        ((TitleBar) findViewById(C1283R.id.profileTitle)).init((Activity) this, (int) DisplayStrings.DS_ACCOUNT_AND_SETTINGS);
        ((TextView) findViewById(C1283R.id.AccountTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_ACCOUNT));
        ((TextView) findViewById(C1283R.id.AccountAndLoginExplainTitleText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_WAZE_ACCOUNT));
        ((TextView) findViewById(C1283R.id.AccountAndLoginExplainBodyText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_AN_ACCOUNT_IS_CREATED));
        ((TextView) findViewById(C1283R.id.UserAndPasswordText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USERNAME_AND_PASSWORD));
        ((TextView) findViewById(C1283R.id.DisconnectUserText)).setText("Disconnect phone");
        ((TextView) findViewById(C1283R.id.DeleteAccButtonText)).setText(this.nativeManager.getLanguageString(91));
        ((TextView) findViewById(C1283R.id.AccountAndLoginExplainBodyText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_AN_ACCOUNT_IS_CREATED));
        ((TextView) findViewById(C1283R.id.AccountAndLoginPointsExplainBodyText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_POINTS_RANK));
        OnClickListener click3 = new C26091();
        findViewById(C1283R.id.DisconnectUser).setVisibility(8);
        findViewById(C1283R.id.DeleteAccButton).setOnClickListener(click3);
        OnClickListener click = new C26102();
        if (MyWazeNativeManager.getInstance().FacebookEnabledNTV()) {
            findViewById(C1283R.id.FacebookIndication).setVisibility(0);
        } else {
            findViewById(C1283R.id.FacebookIndication).setVisibility(8);
        }
        findViewById(C1283R.id.UsernameAndPassword).setOnClickListener(click);
        findViewById(C1283R.id.FacebookIndication).setOnClickListener(new C26113());
    }

    public void SetMyWazeData(MyWazeData Data) {
        if (!this.mywazeManager.isRandomUserNTV()) {
            ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USER_NAME) + ": " + Data.mUserName);
            findViewById(C1283R.id.JoinedLabel).setVisibility(0);
            ((TextView) findViewById(C1283R.id.JoinedLabel)).setText(Data.mJoinedStr);
        } else if (Data.mFaceBookLoggedIn) {
            ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_TEMPORARY_USER));
            findViewById(C1283R.id.JoinedLabel).setVisibility(0);
            ((TextView) findViewById(C1283R.id.JoinedLabel)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOU_ARE_NOT_CONNECTED_TO_WAZE_ACCOUNT));
        } else {
            ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_WAZE_USERNAME));
            findViewById(C1283R.id.JoinedLabel).setVisibility(8);
        }
        if (Data.mFaceBookLoggedIn) {
            ((TextView) findViewById(C1283R.id.FacebookIndicationText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_CONNECTED_TO_FACEBOOK));
        } else {
            ((TextView) findViewById(C1283R.id.FacebookIndicationText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_CONNECT_TO_FACEBOOK));
        }
    }

    public void onFacebookSettings(FacebookSettings settings) {
        Intent intent = new Intent(this, FacebookActivity.class);
        intent.putExtra("com.waze.mywaze.facebooksettings", settings);
        startActivityForResult(intent, 0);
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
