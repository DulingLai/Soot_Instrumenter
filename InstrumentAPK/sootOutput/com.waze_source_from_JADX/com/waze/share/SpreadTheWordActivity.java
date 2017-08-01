package com.waze.share;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.settings.WazeSettingsView;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SpreadTheWordActivity extends ActivityBase {
    private WazeSettingsView email;
    private WazeSettingsView follow;
    private WazeSettingsView like;
    private NativeManager nativeManager = AppService.getNativeManager();
    private WazeSettingsView rate;
    private WazeSettingsView share;

    class C28671 implements OnClickListener {
        C28671() {
        }

        public void onClick(View arg0) {
            if (AppService.isNetworkAvailable()) {
                SpreadTheWordActivity.this.onShare();
            } else {
                MsgBox.openMessageBox(SpreadTheWordActivity.this.nativeManager.getLanguageString(DisplayStrings.DS_NO_NETWORK_CONNECTION), SpreadTheWordActivity.this.nativeManager.getLanguageString(252), false);
            }
        }
    }

    class C28682 implements OnClickListener {
        C28682() {
        }

        public void onClick(View arg0) {
            SpreadTheWordActivity.this.onEmail();
        }
    }

    class C28693 implements OnClickListener {
        C28693() {
        }

        public void onClick(View arg0) {
            if (AppService.isNetworkAvailable()) {
                SpreadTheWordActivity.this.onLike();
            } else {
                MsgBox.openMessageBox(SpreadTheWordActivity.this.nativeManager.getLanguageString(DisplayStrings.DS_NO_NETWORK_CONNECTION), SpreadTheWordActivity.this.nativeManager.getLanguageString(252), false);
            }
        }
    }

    class C28704 implements OnClickListener {
        C28704() {
        }

        public void onClick(View arg0) {
            if (AppService.isNetworkAvailable()) {
                SpreadTheWordActivity.this.onFollow();
            } else {
                MsgBox.openMessageBox(SpreadTheWordActivity.this.nativeManager.getLanguageString(DisplayStrings.DS_NO_NETWORK_CONNECTION), SpreadTheWordActivity.this.nativeManager.getLanguageString(252), false);
            }
        }
    }

    class C28715 implements OnClickListener {
        C28715() {
        }

        public void onClick(View arg0) {
            SpreadTheWordActivity.this.onRate();
        }
    }

    protected class OnOkMsgSmsService implements DialogInterface.OnClickListener {
        protected OnOkMsgSmsService() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            if (which == 1) {
                FacebookManager.getInstance().loginWithFacebook(SpreadTheWordActivity.this, FacebookLoginType.SetToken, false);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.spread_the_word);
        ((TitleBar) findViewById(C1283R.id.shareTitle)).init((Activity) this, (int) DisplayStrings.DS_SHARE);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SPREAD_THE_WORD);
        this.share = (WazeSettingsView) findViewById(C1283R.id.facebookShare);
        if (MyWazeNativeManager.getInstance().FacebookEnabledNTV()) {
            this.share.setText(this.nativeManager.getLanguageString(143));
            this.share.setOnClickListener(new C28671());
        } else {
            this.share.setVisibility(8);
        }
        this.email = (WazeSettingsView) findViewById(C1283R.id.mail);
        this.email.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_EMAIL_YOUR_FRIENDS));
        this.email.setOnClickListener(new C28682());
        this.like = (WazeSettingsView) findViewById(C1283R.id.facebookLike);
        if (MyWazeNativeManager.getInstance().FacebookEnabledNTV()) {
            this.like.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_LIKE_OUR_PAGE));
            this.like.setOnClickListener(new C28693());
        } else {
            this.like.setVisibility(8);
        }
        this.follow = (WazeSettingsView) findViewById(C1283R.id.follow);
        if (MyWazeNativeManager.getInstance().TwitterEnabledNTV()) {
            this.follow.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_FOLLOW_US_ON_TWITTER));
            this.follow.setOnClickListener(new C28704());
        } else {
            this.follow.setVisibility(8);
        }
        this.rate = (WazeSettingsView) findViewById(C1283R.id.rateUs);
        if (MyWazeNativeManager.getInstance().MarketEnabledNTV()) {
            this.rate.setText(this.nativeManager.getLanguageString(144));
            this.rate.setOnClickListener(new C28715());
            return;
        }
        this.rate.setVisibility(8);
    }

    private void onShare() {
        if (MyWazeNativeManager.getInstance().getFacebookLoggedInNTV()) {
            ShareFbQueries.postDialogShow(this);
        } else {
            MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.nativeManager.getLanguageString(396), this.nativeManager.getLanguageString(DisplayStrings.DS_WAZE_NEED_TO_CONNECT), true, new OnOkMsgSmsService(), this.nativeManager.getLanguageString(358), this.nativeManager.getLanguageString(344), -1);
        }
    }

    private void onEmail() {
        String subject = this.nativeManager.getLanguageString(361);
        String format = getString(C1283R.string.share_email_format);
        String body1 = this.nativeManager.getLanguageString(444);
        String body2 = this.nativeManager.getLanguageString(DisplayStrings.f150x490377fe);
        String body3 = this.nativeManager.getLanguageString(377);
        String body4 = this.nativeManager.getLanguageString(53);
        String body = String.format(format, new Object[]{body1, body2, body3, body4});
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.putExtra("android.intent.extra.TEXT", Html.fromHtml(body));
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, this.nativeManager.getLanguageString(DisplayStrings.DS_EMAIL_YOUR_FRIENDS)));
    }

    private void onLike() {
        FbLikeLauncher.start(this);
    }

    private void onFollow() {
        startActivityForResult(new Intent(this, TwitterFollowActivity.class), 0);
    }

    private void onRate() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.waze")));
    }
}
