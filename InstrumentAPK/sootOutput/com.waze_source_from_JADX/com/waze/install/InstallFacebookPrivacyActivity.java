package com.waze.install;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MoodManager;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.profile.WelcomeActivity;
import com.waze.profile.WelcomeDoneActivity;
import com.waze.social.facebook.FacebookManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.title.TitleBar;

public class InstallFacebookPrivacyActivity extends ActivityBase {
    private boolean bIsFof = true;
    private boolean isNew;
    private NativeManager nativeManager = AppService.getNativeManager();

    class C18001 implements OnClickListener {
        C18001() throws  {
        }

        public void onClick(View v) throws  {
            if (InstallFacebookPrivacyActivity.this.isNew) {
                InstallFacebookPrivacyActivity.this.startActivityForResult(new Intent(InstallFacebookPrivacyActivity.this, WelcomeActivity.class), 0);
            } else {
                InstallFacebookPrivacyActivity.this.startActivityForResult(new Intent(InstallFacebookPrivacyActivity.this, WelcomeDoneActivity.class), 0);
            }
            if (InstallNativeManager.IsCleanInstallation() || InstallNativeManager.IsMinimalInstallation()) {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_YOUR_WAZER_NEXT, null, null, true);
            } else {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SOCIAL_UPGRADE_YOUR_WAZER_NEXT, null, null, true);
            }
            InstallFacebookPrivacyActivity.this.setResult(-1);
            InstallFacebookPrivacyActivity.this.finish();
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.install_facebook_privacy);
        this.isNew = getIntent().getBooleanExtra("isNew", false);
        if (InstallNativeManager.IsMinimalInstallation()) {
            if (this.isNew) {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_THIS_IS_YOU_NEW, null, null, false);
            } else {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_THIS_IS_YOU_EXISTING, null, null, false);
            }
        } else if (this.isNew) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_THIS_IS_YOU_NEW, null, null, false);
        } else {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_THIS_IS_YOU_EXISTING, null, null, false);
        }
        ((ImageView) findViewById(C1283R.id.Mood)).setImageDrawable(MoodManager.getInstance().getMoodDrawable(this));
        ((TextView) findViewById(C1283R.id.HeyText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_FRIENDS_SEE_YOUR_FULL_NAME));
        ((TextView) findViewById(C1283R.id.HeyAnotherText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_OTHERS_SEE_YOUR_NICKNAME));
        ((TitleBar) findViewById(C1283R.id.myWazeTitle)).init(this, this.nativeManager.getLanguageString((int) DisplayStrings.DS_THIS_IS_YOU), this.nativeManager.getLanguageString((int) DisplayStrings.DS_NEXT));
        ((TitleBar) findViewById(C1283R.id.myWazeTitle)).setUpButtonDisabled();
        ((TitleBar) findViewById(C1283R.id.myWazeTitle)).setOnClickCloseListener(new C18001());
        ((TextView) findViewById(C1283R.id.HeyBodyText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_SEE_MORE_FAMILIAR_FACES));
        ((TextView) findViewById(C1283R.id.CheckBoxText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_MAKE_IT_MORE_FUN_TO_DRIVE));
        String $r10 = AppService.getNativeManager().getLanguageString((int) DisplayStrings.DS_YOU_HAVE_FRIENDS_CONNECTED);
        int $i0 = MyWazeNativeManager.getInstance().getNumberOfFriends();
        if ($i0 > 0) {
            ((TextView) findViewById(C1283R.id.InstallFacebookConnectedFriends)).setVisibility(0);
            ((TextView) findViewById(C1283R.id.InstallFacebookConnectedFriends)).setText("" + $i0 + " " + $r10);
        } else {
            ((TextView) findViewById(C1283R.id.InstallFacebookConnectedFriends)).setVisibility(8);
        }
        MyWazeNativeManager.getInstance().getMyWazeData((ActivityBase) this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) throws  {
    }

    public void SetMyWazeData(MyWazeData $r1) throws  {
        ((TextView) findViewById(C1283R.id.TitleText)).setText($r1.mUserName);
        ((TextView) findViewById(C1283R.id.JoinedLabel)).setText($r1.mJoinedStr);
        ((TextView) findViewById(C1283R.id.TitleText2)).setText($r1.mFaceBookNickName);
        ImageRepository.instance.getImage($r1.mImageUrl, (ImageView) findViewById(C1283R.id.Picture), (ActivityBase) this);
    }

    public void SetMoodInView() throws  {
        ((ImageView) findViewById(C1283R.id.Mood)).setImageDrawable(MoodManager.getInstance().getMoodDrawable(this));
    }

    public void onBackPressed() throws  {
        FacebookManager.getInstance().logoutFromFacebook();
        MyWazeNativeManager.getInstance().facebookDisconnectNow();
        super.onBackPressed();
    }
}
