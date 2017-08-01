package com.waze.phone;

import android.app.Dialog;
import android.content.Context;
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
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import java.util.Locale;

public class PhoneRequestAccessDialog extends Dialog {
    private LinearLayout mGotItButton;
    private WazeTextView mGotItText;
    private final PhoneRequestAccessResultListener mListener;
    boolean mbOkClicked = false;

    public interface PhoneRequestAccessResultListener {
        void onResult(boolean z);
    }

    class C22831 implements OnClickListener {
        C22831() {
        }

        public void onClick(View v) {
            AppService.OpenBrowser(MyWazeNativeManager.getInstance().getLearnMorePrivacyUrlNTV());
        }
    }

    class C22842 implements OnClickListener {
        C22842() {
        }

        public void onClick(View v) {
            PhoneRequestAccessDialog.this.dismiss();
        }
    }

    class C22853 implements OnClickListener {
        C22853() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ALLOW_CONTACTS_ACCESS_CONTINUE_CLICK, null, null);
            NativeManager.getInstance().setContactsAccess(true);
            PhoneRequestAccessDialog.this.mbOkClicked = true;
            PhoneRequestAccessDialog.this.dismiss();
        }
    }

    public PhoneRequestAccessDialog(Context context, PhoneRequestAccessResultListener l) {
        super(context, C1283R.style.Dialog);
        this.mListener = l;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.phone_request_access);
        getWindow().setLayout(-1, -1);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ALLOW_CONTACTS_ACCESS_SHOWN, null, null);
        initMembers();
        setOnClickListeners();
        initFieldsTexts();
    }

    private void initFieldsTexts() {
        NativeManager nm = NativeManager.getInstance();
        ((TextView) findViewById(C1283R.id.whyResgisterHeaderText)).setText(nm.getLanguageString(DisplayStrings.DS_ALLOW_ACCESS_TO_CONTACTS).toUpperCase(Locale.US));
        ((TextView) findViewById(C1283R.id.whyResgisterBodyText)).setText(nm.getLanguageString(DisplayStrings.DS_ALLOW_WAZE_TO_ACCESS_CONTACTS_DESCRIPTION));
        this.mGotItText.setText(nm.getLanguageString(DisplayStrings.DS_ALLOW_ACCESS));
        TextView learnMore = (TextView) findViewById(C1283R.id.whyResgisterLearnMore);
        learnMore.setVisibility(0);
        learnMore.setText(nm.getLanguageString(DisplayStrings.DS_LEARN_MORE));
        learnMore.setPaintFlags(8);
        learnMore.setOnClickListener(new C22831());
        findViewById(C1283R.id.whyResgisterClose).setOnClickListener(new C22842());
    }

    private void setOnClickListeners() {
        this.mGotItButton.setOnClickListener(new C22853());
    }

    public void dismiss() {
        this.mListener.onResult(this.mbOkClicked);
        super.dismiss();
    }

    private void initMembers() {
        this.mGotItText = (WazeTextView) findViewById(C1283R.id.gotItText);
        this.mGotItButton = (LinearLayout) findViewById(C1283R.id.gotItButton);
    }
}
