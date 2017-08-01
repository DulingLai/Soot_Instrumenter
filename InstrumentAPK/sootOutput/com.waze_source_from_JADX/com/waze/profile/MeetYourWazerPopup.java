package com.waze.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.strings.DisplayStrings;

public class MeetYourWazerPopup extends Dialog {
    private boolean IsValid = false;
    boolean already_queried = false;
    private Context mContext = null;
    private String mText = null;
    private NativeManager nativeManager;
    private TextView signButton;
    private EditText userNameView;

    class C23641 implements OnClickListener {
        C23641() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_MINIMAL_ALREADY_WAZER_CLICKED, null, null, true);
            Intent intent = new Intent(MeetYourWazerPopup.this.mContext, PhoneRegisterActivity.class);
            intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 1);
            intent.putExtra(PhoneNumberSignInActivity.REPORT_START, false);
            intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_SIGNUP);
            AppService.getMainActivity().startActivityForResult(intent, 5000);
        }
    }

    class C23652 implements OnClickListener {
        C23652() {
        }

        public void onClick(View v) {
            MeetYourWazerPopup.this.onContinueClicked();
        }
    }

    public MeetYourWazerPopup(Context context) {
        super(context, C1283R.style.Dialog);
        this.mContext = context;
        MainActivity.bToOpenMeetYourWazer = false;
        this.nativeManager = AppService.getNativeManager();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NativeManager.getInstance().SignUplogAnalytics("START", null, null, true);
        initLayout();
    }

    private void initLayout() {
        setContentView(C1283R.layout.meet_your_wazer);
        getWindow().setLayout(-1, -1);
        ((TextView) findViewById(C1283R.id.account_details_title)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_MEET_YOUR_WAZER));
        ((TextView) findViewById(C1283R.id.account_details_title2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_WAZER_REPRESENTS));
        ((TextView) findViewById(C1283R.id.account_details_title3)).setText(this.nativeManager.getLanguageString(311));
        ((TextView) findViewById(C1283R.id.account_details_title3)).setPaintFlags(8);
        ((TextView) findViewById(C1283R.id.account_details_title4)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_REPORT_ON_ROAD_CONDITIONS_TO_COLLECT_POINTS));
        AppService.getMainActivity().DisableOrientation();
        ((TextView) findViewById(C1283R.id.account_details_title3)).setOnClickListener(new C23641());
        this.signButton = (TextView) findViewById(C1283R.id.account_details_continue);
        this.signButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_START_DRIVING));
        this.signButton.setOnClickListener(new C23652());
    }

    public void onBackPressed() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        close();
    }

    private void onContinueClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_MINIMAL_START_DRIVING_CLICKED, null, null, true);
        NativeManager.getInstance().SetPhoneIsFirstTime(false);
        MyWazeNativeManager.getInstance().skipSignup();
        NativeManager.getInstance().signup_finished();
        close();
    }

    protected void close() {
        NativeManager.getInstance().SetPhoneIsFirstTime(false);
        AppService.getMainActivity().EnableOrientation();
        dismiss();
    }
}
