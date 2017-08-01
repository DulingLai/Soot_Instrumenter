package com.waze.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.install.InstallNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class WelcomeDoneActivity extends ActivityBase {
    private NativeManager nativeManager = AppService.getNativeManager();

    class C24051 implements OnClickListener {
        C24051() {
        }

        public void onClick(View v) {
            WelcomeDoneActivity.this.onDoneAnalytics();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.welcome_done);
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.init((Activity) this, this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_DONE));
        titleBar.setCloseText(this.nativeManager.getLanguageString(375));
        titleBar.setUpButtonDisabled();
        titleBar.setOnClickCloseListener(new C24051());
        ((TextView) findViewById(C1283R.id.text1)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_REMEMBER_THE_MORE));
        ((TextView) findViewById(C1283R.id.text2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_DRIVE_SAFE));
    }

    public void onBackPressed() {
    }

    private void onDoneAnalytics() {
        if (InstallNativeManager.IsCleanInstallation()) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_START_DONE, null, null, false);
        } else {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SOCIAL_UPGRADE_DONE, null, null, false);
        }
        NativeManager.getInstance().logAnalyticsFlush();
        setResult(3);
        finish();
    }
}
