package com.waze.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.phone.LoginOptionsActivity;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.RegisterOptionsView;
import com.waze.phone.RegisterOptionsView.RegisterOptionsListener;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class TempUserProfileActivity extends ActivityBase {
    private MyWazeNativeManager mMwnm;
    private NativeManager mNm;
    private RegisterOptionsView mRegisterOptionsView = null;

    class C23991 implements OnClickListener {
        C23991() {
        }

        public void onClick(View v) {
            TempUserProfileActivity.this.setResult(-1);
            TempUserProfileActivity.this.finish();
        }
    }

    class C24002 implements OnClickListener {
        C24002() {
        }

        public void onClick(View v) {
            TempUserProfileActivity.this.showRegisterOptions();
        }
    }

    class C24013 implements OnClickListener {
        C24013() {
        }

        public void onClick(View v) {
            TempUserProfileActivity.this.startActivityForResult(new Intent(TempUserProfileActivity.this, LoginOptionsActivity.class), 1);
        }
    }

    class C24024 implements RegisterOptionsListener {
        C24024() {
        }

        public void onFacebookClick() {
            TempUserProfileActivity.this.registerWithFacebook();
        }

        public void onPhoneClick() {
            TempUserProfileActivity.this.registerWithPhone();
        }

        public void onClosed() {
            TempUserProfileActivity.this.mRegisterOptionsView = null;
            if (VERSION.SDK_INT >= 21) {
                TempUserProfileActivity.this.getWindow().setStatusBarColor(TempUserProfileActivity.this.getResources().getColor(C1283R.color.BlueDeep));
            }
        }
    }

    class C24035 implements FacebookLoginListener {
        C24035() {
        }

        public void onFacebookLoginResult(boolean success) {
            if (success) {
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().promptForSmartLockWhenResumed("FB");
                }
                TempUserProfileActivity.this.setResult(-1);
                TempUserProfileActivity.this.finish();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNm = NativeManager.getInstance();
        getWindow().setSoftInputMode(3);
        requestWindowFeature(1);
        setContentView(C1283R.layout.temp_user_profile);
        this.mMwnm = MyWazeNativeManager.getInstance();
        this.mMwnm.getMyWazeData(this);
        this.mNm.SuggestUserNameInit();
        ((TitleBar) findViewById(C1283R.id.myProfileTitle)).init((Activity) this, (int) DisplayStrings.DS_MY_PROFILE);
        ((TitleBar) findViewById(C1283R.id.myProfileTitle)).setOnClickCloseListener(new C23991());
        ((WazeTextView) findViewById(C1283R.id.myProfileYourPhotoText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_REGISTER_TO_GET_MOST_OUT_OF));
        ((SettingsTitleText) findViewById(C1283R.id.myProfileLoginTitle)).setText(this.mNm.getLanguageString(95));
        ((WazeSettingsView) findViewById(C1283R.id.myProfilePhone)).setText(this.mNm.getLanguageString(DisplayStrings.DS_CREATE_ACCOUNT));
        findViewById(C1283R.id.myProfilePhone).setOnClickListener(new C24002());
        ((SettingsTitleText) findViewById(C1283R.id.myProfileLoginTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_ACCOUNT_DETAILS));
        ((WazeSettingsView) findViewById(C1283R.id.myProfileLogOutButton)).setText(this.mNm.getLanguageString(DisplayStrings.DS_SWITCH_ACCOUNTS));
        findViewById(C1283R.id.myProfileLogOutButton).setOnClickListener(new C24013());
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        if (this.mRegisterOptionsView != null) {
            this.mRegisterOptionsView.close();
        } else {
            super.onBackPressed();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        if (resultCode == 3) {
            setResult(0);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mRegisterOptionsView != null) {
            outState.putBoolean("show_register", true);
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("show_register", false)) {
            showRegisterOptions();
        }
    }

    private void showRegisterOptions() {
        if (this.mRegisterOptionsView == null) {
            if (VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(-16777216);
            }
            this.mRegisterOptionsView = RegisterOptionsView.showRegisterOptionsView(this, new C24024());
        }
    }

    private void registerWithFacebook() {
        FacebookManager.getInstance().loginWithFacebook(this, FacebookLoginType.SetToken, true, true, new C24035());
    }

    private void registerWithPhone() {
        Intent intent = new Intent(this, PhoneRegisterActivity.class);
        intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
        intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, "UPDATE");
        startActivityForResult(intent, 1111);
    }
}
