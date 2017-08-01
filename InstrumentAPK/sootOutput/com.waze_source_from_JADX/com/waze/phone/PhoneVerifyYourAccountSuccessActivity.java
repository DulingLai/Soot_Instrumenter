package com.waze.phone;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;

public class PhoneVerifyYourAccountSuccessActivity extends ActivityBase {
    private LinearLayout mNextButton;
    private TextView mNextText;
    private TextView mVerifyBodyText;
    private TextView mVerifyHeaderText;

    class C23001 implements OnClickListener {
        C23001() {
        }

        public void onClick(View v) {
            PhoneVerifyYourAccountSuccessActivity.this.setResult(-1);
            PhoneVerifyYourAccountSuccessActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initFieldsTexts();
        this.mNextButton.setOnClickListener(new C23001());
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_CONGRATS_DONE, null, null, true);
    }

    protected void onDestroy() {
        NativeManager.getInstance().signup_finished();
        super.onDestroy();
    }

    public void onBackPressed() {
    }

    private void initFieldsTexts() {
        setTextContent();
    }

    private void setTextContent() {
        this.mVerifyHeaderText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_HOORAY).toUpperCase());
        this.mVerifyBodyText.setText(NativeManager.getInstance().getLanguageString(302));
        this.mNextText.setText(NativeManager.getInstance().getLanguageString(375));
    }

    private void initLayout() {
        setContentView(C1283R.layout.phone_verify_your_account_success);
        this.mNextButton = (LinearLayout) findViewById(C1283R.id.nextButton);
        this.mNextText = (TextView) findViewById(C1283R.id.nextText);
        this.mVerifyHeaderText = (TextView) findViewById(C1283R.id.verifyHeaderText);
        this.mVerifyBodyText = (TextView) findViewById(C1283R.id.verifyBodyText);
    }
}
