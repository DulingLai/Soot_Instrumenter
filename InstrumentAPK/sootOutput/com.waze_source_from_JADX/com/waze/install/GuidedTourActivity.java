package com.waze.install;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.install.InstallNativeManager.VideoUrlListener;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.profile.MinimalSignInActivity;
import com.waze.profile.SignInActivity;
import com.waze.profile.WelcomeDoneActivity;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;

public class GuidedTourActivity extends ActivityBase {
    private static boolean bIsBackFromFBScreen = false;
    private static boolean bIsFacebookClicked = false;
    private static boolean bIsMovieClicked = false;
    private static boolean bIsNew = false;

    class C17931 implements OnClickListener {

        class C17911 implements FacebookLoginListener {
            C17911() throws  {
            }

            public void onFacebookLoginResult(boolean $z0) throws  {
                if ($z0) {
                    NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                    return;
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FACEBOOK_DECLINE, null, null, true);
            }
        }

        class C17922 implements FacebookLoginListener {
            C17922() throws  {
            }

            public void onFacebookLoginResult(boolean $z0) throws  {
                if (!$z0) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SOCIAL_UPGRADE_FACEBOOK_DECLINE, null, null, true);
                }
            }
        }

        C17931() throws  {
        }

        public void onClick(View v) throws  {
            if (InstallNativeManager.IsMinimalInstallation()) {
                MyWazeNativeManager.getInstance().skipSignup();
                return;
            }
            GuidedTourActivity.bIsFacebookClicked = true;
            VideoView $r4 = (VideoView) GuidedTourActivity.this.findViewById(C1283R.id.guidedTourVideo);
            if ($r4.isPlaying()) {
                $r4.stopPlayback();
            }
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_START_FBCONNECT, null, null, true);
            if (GuidedTourActivity.bIsBackFromFBScreen) {
                FacebookManager.getInstance().loginWithFacebook(GuidedTourActivity.this, FacebookLoginType.SetToken, false);
            } else if (InstallNativeManager.IsCleanInstallation()) {
                FacebookManager.getInstance().loginWithFacebook(GuidedTourActivity.this, FacebookLoginType.SignIn, true, new C17911());
            } else {
                FacebookManager.getInstance().loginWithFacebook(GuidedTourActivity.this, FacebookLoginType.SetToken, true, new C17922());
            }
        }
    }

    class C17942 implements OnClickListener {
        C17942() throws  {
        }

        public void onClick(View v) throws  {
            VideoView $r3;
            if (InstallNativeManager.IsMinimalInstallation()) {
                $r3 = (VideoView) GuidedTourActivity.this.findViewById(C1283R.id.guidedTourVideo);
                if ($r3.isPlaying()) {
                    $r3.stopPlayback();
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN, null, null, true);
                GuidedTourActivity.this.startActivityForResult(new Intent(GuidedTourActivity.this, MinimalSignInActivity.class), 5000);
            } else if (!GuidedTourActivity.bIsFacebookClicked) {
                $r3 = (VideoView) GuidedTourActivity.this.findViewById(C1283R.id.guidedTourVideo);
                if ($r3.isPlaying()) {
                    $r3.stopPlayback();
                }
                if (InstallNativeManager.IsCleanInstallation()) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_START_OTHER, null, null, true);
                    if (!GuidedTourActivity.bIsBackFromFBScreen) {
                        GuidedTourActivity.this.startActivityForResult(new Intent(GuidedTourActivity.this, SignInActivity.class), 5000);
                        return;
                    } else if (MyWazeNativeManager.getInstance().isRandomUserNTV()) {
                        GuidedTourActivity.this.startActivityForResult(new Intent(GuidedTourActivity.this, SignInActivity.class), 5000);
                        return;
                    } else {
                        GuidedTourActivity.this.startActivityForResult(new Intent(GuidedTourActivity.this, WelcomeDoneActivity.class), 5000);
                        return;
                    }
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SOCIAL_UPGRADE_SKIP, null, null, false);
                MyWazeNativeManager.getInstance().facebookDisconnectNow();
                FacebookManager.getInstance().logoutFromFacebook();
                GuidedTourActivity.this.setResult(-1);
                GuidedTourActivity.this.finish();
            }
        }
    }

    class C17953 implements OnPreparedListener {
        C17953() throws  {
        }

        public void onPrepared(MediaPlayer mp) throws  {
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourTextTop).setVisibility(8);
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourTextBottom).setVisibility(8);
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourProgress).setVisibility(8);
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourImage).setVisibility(8);
            ((VideoView) GuidedTourActivity.this.findViewById(C1283R.id.guidedTourVideo)).setVisibility(0);
        }
    }

    class C17964 implements OnCompletionListener {
        C17964() throws  {
        }

        public void onCompletion(MediaPlayer mp) throws  {
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourTextTop).setVisibility(0);
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourTextBottom).setVisibility(0);
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourImage).setVisibility(0);
            ((VideoView) GuidedTourActivity.this.findViewById(C1283R.id.guidedTourVideo)).setVisibility(8);
        }
    }

    class C17975 implements OnClickListener {
        C17975() throws  {
        }

        public void onClick(View v) throws  {
            if (!GuidedTourActivity.bIsMovieClicked) {
                GuidedTourActivity.bIsMovieClicked = true;
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_START_MOVIE, null, null, true);
            }
            GuidedTourActivity.this.findViewById(C1283R.id.guidedTourProgress).setVisibility(0);
            VideoView $r4 = (VideoView) GuidedTourActivity.this.findViewById(C1283R.id.guidedTourVideo);
            $r4.setVisibility(0);
            $r4.start();
            $r4.setBackgroundDrawable(null);
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.install_login);
        if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr().isSplashVisible()) {
            AppService.getMainActivity().getLayoutMgr().cancelSplash();
        }
        if (InstallNativeManager.IsMinimalInstallation()) {
            NativeManager.getInstance().SignUplogAnalytics("START", null, null, true);
            ((TextView) findViewById(C1283R.id.guidedTourFacebookText)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NEW_USERS_START_DRIVING));
            ((TextView) findViewById(C1283R.id.guidedTourFacebookBottomText)).setVisibility(8);
            findViewById(C1283R.id.guidedTourFacebook).setBackgroundResource(C1283R.drawable.navy_button);
            findViewById(C1283R.id.guidedTourFacebook).setPadding(0, 0, 0, 0);
        } else if (InstallNativeManager.IsCleanInstallation()) {
            NativeManager.getInstance().SignUplogAnalytics("START", null, null, true);
            ((TextView) findViewById(C1283R.id.guidedTourFacebookText)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_SIGN_UP_WITH_FACEBOOK));
            ((TextView) findViewById(C1283R.id.guidedTourFacebookBottomText)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_DONT_WORRY_YOU_CONTROL));
            ((TextView) findViewById(C1283R.id.guidedTourFacebookBottomText)).setVisibility(8);
        } else {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SOCIAL_UPGRADE, null, null, false);
            ((TextView) findViewById(C1283R.id.guidedTourFacebookText)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CONNECT_TO_FACEBOOK));
            ((TextView) findViewById(C1283R.id.guidedTourFacebookBottomText)).setVisibility(8);
        }
        findViewById(C1283R.id.guidedTourFacebook).setOnClickListener(new C17931());
        if (InstallNativeManager.IsMinimalInstallation()) {
            ((Button) findViewById(C1283R.id.guidedTourOther)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_LOGIN));
        } else if (InstallNativeManager.IsCleanInstallation()) {
            ((Button) findViewById(C1283R.id.guidedTourOther)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CREATE_ACCOUNT));
        } else {
            ((Button) findViewById(C1283R.id.guidedTourOther)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_GO_SOLO));
        }
        findViewById(C1283R.id.guidedTourOther).setOnClickListener(new C17942());
        VideoView $r12 = SetVideoUri();
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer($r12);
        $r12.setMediaController(mediaController);
        if (InstallNativeManager.IsMinimalInstallation()) {
            ((TextView) findViewById(C1283R.id.guidedTourTextTop)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_WELCOME_TO_WAZE_INSTALL));
            ((TextView) findViewById(C1283R.id.guidedTourTextBottom)).setVisibility(8);
        } else if (InstallNativeManager.IsCleanInstallation()) {
            ((TextView) findViewById(C1283R.id.guidedTourTextTop)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_WELCOME_SIGN_IN_TO_GET_STARTED));
            ((TextView) findViewById(C1283R.id.guidedTourTextBottom)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_WE_WILL_NEVER_POST_WITHOUT_APPROVAL));
        } else {
            ((TextView) findViewById(C1283R.id.guidedTourTextTop)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_A_WHOLE_NEW_WAZE_AWAITS));
            ((TextView) findViewById(C1283R.id.guidedTourTextBottom)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_PLEASE_RECONNECT_EVEN_IF_YOU_DID));
        }
        $r12.setOnPreparedListener(new C17953());
        $r12.setOnCompletionListener(new C17964());
        findViewById(C1283R.id.guidedTourImage).setOnClickListener(new C17975());
        NativeManager.getInstance().SetPhoneIsFirstTime(false);
        GoogleTest();
    }

    public void GoogleTest() throws  {
    }

    public VideoView SetVideoUri() throws  {
        final VideoView $r2 = (VideoView) findViewById(C1283R.id.guidedTourVideo);
        $r2.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            class C17981 implements VideoUrlListener {
                C17981() throws  {
                }

                public void onComplete(String $r1) throws  {
                    $r2.setVideoURI(Uri.parse($r1));
                }
            }

            public void onGlobalLayout() throws  {
                new InstallNativeManager().getVideoUrl(!InstallNativeManager.IsCleanInstallation(), $r2.getWidth(), $r2.getHeight(), new C17981());
                $r2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return $r2;
    }

    public void onFacebookTokenSet() throws  {
        NativeManager.getInstance().CloseProgressPopup();
        Intent $r3;
        if (!InstallNativeManager.IsCleanInstallation()) {
            $r3 = new Intent(this, InstallFacebookPrivacyActivity.class);
            $r3.putExtra("isNew", false);
            bIsNew = false;
            startActivityForResult($r3, 3000);
        } else if (MyWazeNativeManager.getInstance().getUserTypeNTV() == 1) {
            $r3 = new Intent(this, InstallFacebookPrivacyActivity.class);
            $r3.putExtra("isNew", false);
            bIsNew = false;
            startActivityForResult($r3, 3000);
        } else {
            $r3 = new Intent(this, InstallFacebookPrivacyActivity.class);
            $r3.putExtra("isNew", true);
            bIsNew = true;
            startActivityForResult($r3, 3000);
        }
    }

    public void onBackPressed() throws  {
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        bIsFacebookClicked = false;
        if (!InstallNativeManager.IsMinimalInstallation()) {
            if ($i1 == 0) {
                NativeManager.getInstance().CloseProgressPopup();
            }
            if ($i0 == 3000) {
                if ($i1 == 0) {
                    if (InstallNativeManager.IsCleanInstallation()) {
                        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FACEBOOK_BACK, null, null, true);
                    } else {
                        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_FACEBOOK_BACK, null, null, true);
                    }
                    bIsBackFromFBScreen = true;
                } else {
                    setResult(-1);
                    finish();
                }
            }
            if ($i0 == 5000) {
                if ($i1 == 0) {
                    NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_NEW_EXISTING_BACK, null, null, true);
                } else {
                    setResult(-1);
                    finish();
                }
            }
        } else if ($i0 == 5000) {
            if ($i1 == -1) {
                setResult(-1);
                finish();
            } else if ($i1 == 0) {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOGIN_BACK, null, null, true);
            }
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    public static boolean IsNewUser() throws  {
        return bIsNew;
    }

    public static boolean IsAlreadyCreactedUser() throws  {
        return bIsBackFromFBScreen;
    }
}
