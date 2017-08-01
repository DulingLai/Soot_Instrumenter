package com.waze.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class WelcomeActivity extends ActivityBase {

    class C24041 implements OnClickListener {
        C24041() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_BABY_WAZER_NEXT, null, null, false);
            WelcomeActivity.this.startActivityForResult(new Intent(WelcomeActivity.this, WelcomeDoneActivity.class), 0);
            WelcomeActivity.this.setResult(-1);
            WelcomeActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.welcome);
        OnClickListener Click = new C24041();
        ((TextView) findViewById(C1283R.id.welcomeContribute)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_WELCOME_DONT_WORRY));
        ((TextView) findViewById(C1283R.id.welcomeReporting)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_DRIVE_AROUND_WITH_WAZE));
        ((TextView) findViewById(C1283R.id.welcomeMapEditing)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_REPORT_TRAFFIC_AND_EVENTS_ON_THE_ROAD));
        ((TextView) findViewById(C1283R.id.welcomeDriving)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_EDIT_AND_UPDATE_THE_MAP));
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, NativeManager.getInstance().getLanguageString(DisplayStrings.DS_WELCOME), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NEXT));
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).setUpButtonDisabled();
        ((TextView) findViewById(C1283R.id.welcomeUserName)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_OH_AND_YOURE_A_BABY));
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).setOnClickCloseListener(Click);
    }

    public void onBackPressed() {
    }
}
