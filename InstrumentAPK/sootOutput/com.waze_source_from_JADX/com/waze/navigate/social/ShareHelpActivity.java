package com.waze.navigate.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class ShareHelpActivity extends ActivityBase {
    private LinearLayout mGotItButton;
    private WazeTextView mGotItText;

    class C22321 implements OnClickListener {
        C22321() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_HELP_DRIVE_CLICK, null, null);
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().startNavigateActivity();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.share_help);
        initMembers();
        setOnClickListeners();
        initFieldsTexts();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 0) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initFieldsTexts() {
        ((TitleBar) findViewById(C1283R.id.shareTitle)).init((Activity) this, NativeManager.getInstance().getLanguageString(44));
        ((TextView) findViewById(C1283R.id.whyResgisterBodyText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SHARE_YOUR_DRIVE_WITH_FRIENDS_AND));
        ((TextView) findViewById(C1283R.id.whyResgisterBodyText2)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FROM_THE_ETA_SCREEN_SIMPLY_ADD_FRIENDS_TO));
        this.mGotItText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_START_DRIVING));
    }

    private void setOnClickListeners() {
        this.mGotItButton.setOnClickListener(new C22321());
    }

    private void initMembers() {
        this.mGotItText = (WazeTextView) findViewById(C1283R.id.gotItText);
        this.mGotItButton = (LinearLayout) findViewById(C1283R.id.gotItButton);
    }
}
