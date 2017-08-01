package com.waze.phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.PublicMacros;
import com.waze.strings.DisplayStrings;

public class PhoneVerifyYourAccountFailureActivity extends ActivityBase {
    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 1;
    private LinearLayout mNextButton;
    private TextView mNextText;
    private TextView mVerifyBodyText;
    private TextView mVerifyHeaderText;

    class C22981 implements OnClickListener {
        C22981() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_CREATE_NEW_ACCOUNT, null, null, true);
            NativeManager.getInstance().signup_finished();
            MyWazeNativeManager.getInstance().setContactsSignIn(true, false, null, null);
            PhoneVerifyYourAccountFailureActivity.this.setResult(-1);
            PhoneVerifyYourAccountFailureActivity.this.finish();
        }
    }

    class C22992 implements OnClickListener {
        C22992() {
        }

        public void onClick(View v) {
            Intent i = new Intent(PhoneVerifyYourAccountFailureActivity.this, LoginOptionsActivity.class);
            i.putExtra("verifyMode", 1);
            i.putExtra(LoginOptionsActivity.EXTRA_IS_INSTALL_FLOW, true);
            PhoneVerifyYourAccountFailureActivity.this.startActivityForResult(i, 4321);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initFieldsTexts();
        setListeners();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_CONTACTS_FAILED_SHOWN, null, null, true);
    }

    private void setListeners() {
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        this.mNextButton.setOnClickListener(new C22981());
        ((TextView) findViewById(C1283R.id.verifyByUserName)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ALREADY_A_WAZER_LOGIN));
        ((TextView) findViewById(C1283R.id.verifyByUserName)).setPaintFlags(((TextView) findViewById(C1283R.id.verifyByUserName)).getPaintFlags() | 8);
        findViewById(C1283R.id.verifyByUserName).setOnClickListener(new C22992());
    }

    public void onBackPressed() {
    }

    public void onPinTokenSet() {
        NativeManager.getInstance().CloseProgressPopup();
    }

    private void initFieldsTexts() {
        setTextContent();
    }

    private void setTextContent() {
        this.mVerifyHeaderText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_BAD_NEWS_BUCKOE).toUpperCase());
        this.mVerifyBodyText.setText(NativeManager.getInstance().getLanguageString(300));
        this.mNextText.setText(NativeManager.getInstance().getLanguageString(319));
    }

    private void initLayout() {
        setContentView(C1283R.layout.phone_verify_your_account_failure);
        this.mNextButton = (LinearLayout) findViewById(C1283R.id.nextButton);
        this.mNextText = (TextView) findViewById(C1283R.id.nextText);
        this.mVerifyHeaderText = (TextView) findViewById(C1283R.id.verifyHeaderText);
        this.mVerifyBodyText = (TextView) findViewById(C1283R.id.verifyBodyText);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Logger.TAG, "phoneVerifyYourAccount onActRes requestCode=" + requestCode + " resultCode=" + resultCode + " Intent=" + data);
        if (requestCode == 4321 && resultCode == -1) {
            MainActivity.bToOpenAccountPopup = true;
            setResult(-1);
            finish();
            return;
        }
        if (resultCode == -1 && requestCode == 1 && data != null) {
            AddressItem addressItem = (AddressItem) data.getExtras().getSerializable(PublicMacros.ADDRESS_ITEM);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
