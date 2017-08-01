package com.waze.phone;

import android.content.Intent;
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
import com.waze.view.text.WazeTextView;

public class PhoneVerifyNumberFailure extends ActivityBase {
    private int mAuthNumberOfRetries = 0;
    private LinearLayout mGotItButton;
    private WazeTextView mGotItText;

    class C22921 implements OnClickListener {
        C22921() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_CODE_PROBLEM_SKIP, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + PhoneVerifyNumberFailure.this.mAuthNumberOfRetries, true);
            MainActivity.bSignupSkipped = true;
            if (!PhoneNumberSignInActivity.bIsUpgrade) {
                MyWazeNativeManager.getInstance().skipSignup();
            }
            NativeManager.getInstance().signup_finished();
            PhoneVerifyNumberFailure.this.setResult(-1);
            PhoneVerifyNumberFailure.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if (i != null && i.hasExtra("AuthNumberOfRetries")) {
            this.mAuthNumberOfRetries = i.getIntExtra("AuthNumberOfRetries", 0);
        }
        setContentView(C1283R.layout.phone_verify_phone_failure);
        initMembers();
        setOnClickListeners();
        initFieldsTexts();
    }

    private void initFieldsTexts() {
        ((TextView) findViewById(C1283R.id.whyResgisterHeaderText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE).toUpperCase());
        ((TextView) findViewById(C1283R.id.whyResgisterBodyText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_WE_COULDNT_VERIFY_YOUR_PHONE_NUMBER_CONTINUE_TO_MAP));
        this.mGotItText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SKIP_TO_MAP));
    }

    private void setOnClickListeners() {
        this.mGotItButton.setOnClickListener(new C22921());
    }

    private void initMembers() {
        this.mGotItText = (WazeTextView) findViewById(C1283R.id.gotItText);
        this.mGotItButton = (LinearLayout) findViewById(C1283R.id.gotItButton);
    }
}
