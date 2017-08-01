package com.waze.install;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class InstallPickAccountActivity extends ActivityBase {
    private NativeManager nativeManager = AppService.getNativeManager();

    class C18141 implements OnClickListener {
        C18141() throws  {
        }

        public void onClick(View v) throws  {
            InstallPickAccountActivity.this.onDestroy();
        }
    }

    class C18152 implements OnClickListener {
        C18152() throws  {
        }

        public void onClick(View paramView) throws  {
            MyWazeNativeManager.getInstance().facebookResolveConflict();
            InstallPickAccountActivity.this.setResult(-1);
            InstallPickAccountActivity.this.finish();
        }
    }

    class C18173 implements OnClickListener {

        class C18161 implements FacebookLoginListener {
            C18161() throws  {
            }

            public void onFacebookLoginResult(boolean $z0) throws  {
                if ($z0) {
                    InstallPickAccountActivity.this.setResult(-1);
                    InstallPickAccountActivity.this.finish();
                    return;
                }
                MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(387), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CANNOT_CONFIGURE_SERVICE__PLEASE_TRY), false);
            }
        }

        C18173() throws  {
        }

        public void onClick(View paramView) throws  {
            FacebookManager.getInstance().logoutFromFacebook();
            FacebookManager.getInstance().loginWithFacebook(InstallPickAccountActivity.this, FacebookLoginType.SignIn, false, new C18161());
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.install_pick_account);
        ((TextView) findViewById(C1283R.id.HeyText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_PICK_YOUR_ACCOUNT));
        ((TitleBar) findViewById(C1283R.id.myWazeTitle)).init(this, this.nativeManager.getLanguageString((int) DisplayStrings.DS_UHHOHE), false);
        ((TitleBar) findViewById(C1283R.id.myWazeTitle)).setOnClickCloseListener(new C18141());
        ((TextView) findViewById(C1283R.id.HeyBodyText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_YOUR_CREDENTIALS_ARE));
        ((TextView) findViewById(C1283R.id.CheckBoxText1)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_CUREENT_USER_ON));
        ((TextView) findViewById(C1283R.id.CheckBoxText2)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_OTHER_USER_FOUND));
        ((TextView) findViewById(C1283R.id.FriendOfFriendsTextBody)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_WE_RECOMMEND_CONNECTION_TO_YOUR));
        findViewById(C1283R.id.installPickAccount1Layout).setOnClickListener(new C18152());
        findViewById(C1283R.id.installPickAccount2Layout).setOnClickListener(new C18173());
        MyWazeNativeManager.getInstance().getMyWazeData((ActivityBase) this);
    }

    public void SetMyWazeData(MyWazeData $r1) throws  {
        if ($r1.mUserName == "") {
            $r1.mUserName = this.nativeManager.getLanguageString((int) DisplayStrings.DS_TEMPORARY_USER);
        }
        ((TextView) findViewById(C1283R.id.installPickAccount1Text)).setText($r1.mUserName + "\n" + $r1.mPts + " " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_POINTS) + " - " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_RANK) + " " + $r1.mRank + "\n" + this.nativeManager.getLanguageString((int) DisplayStrings.DS_LAST_LOGIN) + " " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_TODAY));
        $r1 = MyWazeNativeManager.getInstance().getMyConflictingUserData();
        if ($r1.mUserName == "") {
            $r1.mUserName = this.nativeManager.getLanguageString((int) DisplayStrings.DS_TEMPORARY_USER);
        }
        ((TextView) findViewById(C1283R.id.installPickAccount2Text)).setText($r1.mUserName + "\n" + $r1.mPts + " " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_POINTS) + " - " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_RANK) + " " + $r1.mRank + "\n" + $r1.mLastSeen);
    }

    public void onBackPressed() throws  {
        MyWazeNativeManager.getInstance().facebookDisconnectNow();
        FacebookManager.getInstance().logoutFromFacebook();
        super.onBackPressed();
    }
}
