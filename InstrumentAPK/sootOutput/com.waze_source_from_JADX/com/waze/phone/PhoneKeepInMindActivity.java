package com.waze.phone;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.strings.DisplayStrings;

public class PhoneKeepInMindActivity extends ActivityBase {
    private TextView mBodyText;
    private TextView mFooterText;
    private TextView mHeaderText;
    private LinearLayout mNextButton;
    private TextView mNextText;

    class C22581 implements OnClickListener {
        C22581() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SKIP_ANYWAY, null, null, true);
            MainActivity.bReportMapShownAnalytics = true;
            MainActivity.bSignupSkipped = true;
            if (!PhoneNumberSignInActivity.bIsUpgrade) {
                MyWazeNativeManager.getInstance().skipSignup();
            }
            NativeManager.getInstance().signup_finished();
            PhoneKeepInMindActivity.this.setResult(-1);
            PhoneKeepInMindActivity.this.finish();
        }
    }

    class C22592 implements OnClickListener {
        C22592() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_KEEP_IN_MIND_BACK, null, null, true);
            PhoneKeepInMindActivity.this.setResult(0);
            PhoneKeepInMindActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initFieldsTexts();
        setListeners();
        setRequestedOrientation(1);
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_KEEP_IN_MIND_SHOWN, null, null, true);
    }

    private void setListeners() {
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        findViewById(C1283R.id.skip_text).setOnClickListener(new C22581());
        this.mNextButton.setOnClickListener(new C22592());
    }

    public void onBackPressed() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_KEEP_IN_MIND_BACK, null, null, true);
        super.onBackPressed();
    }

    private void initFieldsTexts() {
        setTextContent();
    }

    private void setTextContent() {
        ((TextView) findViewById(C1283R.id.skip_text)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SKIP_ANYWAY));
        this.mHeaderText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q).toUpperCase());
        this.mBodyText.setText(NativeManager.getInstance().getLanguageString(303));
        this.mFooterText.setText(NativeManager.getInstance().getLanguageString(304));
        this.mNextText.setText(NativeManager.getInstance().getLanguageString(334));
    }

    private void initLayout() {
        setContentView(C1283R.layout.phone_keep_in_mind);
        this.mNextButton = (LinearLayout) findViewById(C1283R.id.nextButton);
        this.mNextText = (TextView) findViewById(C1283R.id.nextText);
        this.mHeaderText = (TextView) findViewById(C1283R.id.headerText);
        this.mBodyText = (TextView) findViewById(C1283R.id.bodyText);
        this.mFooterText = (TextView) findViewById(C1283R.id.footerText);
    }
}
