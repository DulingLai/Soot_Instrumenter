package com.waze.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.strings.DisplayStrings;
import java.util.Timer;
import java.util.TimerTask;

public class NameYourWazerPopup extends Dialog {
    private static int bIsFirstTime = 0;
    private boolean IsValid = false;
    boolean already_queried = false;
    Handler f88h = new Handler();
    long idle_min = 500;
    long last_text_edit = 0;
    private Context mContext = null;
    private String mText = null;
    private String mUserName = null;
    private NativeManager nativeManager;
    private TextView signButton;
    private EditText userNameView;

    class C23821 implements OnClickListener {
        C23821() {
        }

        public void onClick(View v) {
            if (NameYourWazerPopup.this.mUserName != null) {
                NameYourWazerPopup.this.userNameView.setText(NameYourWazerPopup.this.mUserName);
                NameYourWazerPopup.this.userNameView.setSelection(NameYourWazerPopup.this.mUserName.length());
            }
        }
    }

    class C23832 implements OnClickListener {
        C23832() {
        }

        public void onClick(View v) {
            NameYourWazerPopup.this.onContinueClicked();
        }
    }

    class C23843 implements OnEditorActionListener {
        C23843() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            NameYourWazerPopup.this.onContinueClicked();
            return true;
        }
    }

    class C23874 implements TextWatcher {
        private Timer timer = new Timer();

        class C23861 extends TimerTask {

            class C23851 implements Runnable {
                C23851() {
                }

                public void run() {
                    if (!(NameYourWazerPopup.this.mText == null || NameYourWazerPopup.this.mText.equals(""))) {
                        NameYourWazerPopup.this.nativeManager.SuggestUserNameRequest(null, null, NameYourWazerPopup.this.mText);
                    }
                    C23874.this.timer.cancel();
                }
            }

            C23861() {
            }

            public void run() {
                AppService.getMainActivity().runOnUiThread(new C23851());
            }
        }

        C23874() {
        }

        public void afterTextChanged(Editable s) {
            ((TextView) NameYourWazerPopup.this.findViewById(C1283R.id.account_details_error_code)).setText(NameYourWazerPopup.this.nativeManager.getLanguageString(DisplayStrings.DS_CHECKING));
            ((TextView) NameYourWazerPopup.this.findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.regular_name_your_wazer_color));
            NameYourWazerPopup.this.findViewById(C1283R.id.account_details_error_code2).setVisibility(8);
            this.timer.cancel();
            this.timer = new Timer();
            this.timer.schedule(new C23861(), 500);
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            NameYourWazerPopup.this.IsValid = false;
            NameYourWazerPopup.this.mText = arg0.toString();
        }
    }

    public class UserSuggestionType {
        public static final int suggestion_ok = 0;
        public static final int suggestion_status_already_taken = 4;
        public static final int suggestion_status_forbidden = 5;
        public static final int suggestion_status_invalid_characters = 3;
        public static final int suggestion_status_too_long = 2;
        public static final int suggestion_status_too_short = 1;
    }

    public NameYourWazerPopup(Context context) {
        super(context, C1283R.style.Dialog);
        this.mContext = context;
        this.nativeManager = AppService.getNativeManager();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_SELECT_USERNAME_SHOWN, null, null, true);
    }

    private void initLayout() {
        setContentView(C1283R.layout.name_your_wazer_popup);
        getWindow().setLayout(-1, -1);
        String Title = this.nativeManager.getLanguageString(DisplayStrings.DS_NAME_YOUR_WAZER);
        if (!PhoneNumberSignInActivity.bIsInit || (PhoneNumberSignInActivity.bIsInit && PhoneNumberSignInActivity.bIsUpgrade)) {
            Title = this.nativeManager.getLanguageString(DisplayStrings.DS_UPDATE_YOUR_WAZER);
        }
        ((TextView) findViewById(C1283R.id.account_details_title)).setText(Title);
        if (!PhoneNumberSignInActivity.bIsInit || (PhoneNumberSignInActivity.bIsInit && PhoneNumberSignInActivity.bIsUpgrade)) {
            ((TextView) findViewById(C1283R.id.account_details_title2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_USERNAME_WILL_REPLACE_YOUR_NICKNAME));
        } else {
            ((TextView) findViewById(C1283R.id.account_details_title2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_VISIBLE_TO_ALL_ON_MAP));
        }
        ((TextView) findViewById(C1283R.id.account_details_error_code2)).setPaintFlags(8);
        this.userNameView = (EditText) findViewById(C1283R.id.UserName);
        findViewById(C1283R.id.account_details_error_code2).setOnClickListener(new C23821());
        this.signButton = (TextView) findViewById(C1283R.id.account_details_continue);
        this.signButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_NEXT));
        this.signButton.setOnClickListener(new C23832());
        this.userNameView.setOnEditorActionListener(new C23843());
        this.userNameView.addTextChangedListener(new C23874());
    }

    public void onBackPressed() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onUserNameResult(int nType, String username) {
        String Text = null;
        if (username != null) {
            this.mUserName = username;
            if (this.userNameView.getText().toString() == null || this.userNameView.getText().toString().equals("")) {
                this.userNameView.setText(this.mUserName);
                this.userNameView.setSelection(this.mUserName.length());
            }
            switch (nType) {
                case 0:
                    if (bIsFirstTime < 2) {
                        if (!PhoneNumberSignInActivity.bIsInit || (PhoneNumberSignInActivity.bIsInit && PhoneNumberSignInActivity.bIsUpgrade)) {
                            Text = this.nativeManager.getLanguageString(DisplayStrings.DS_CONFIRM_YOUR_EXISTING_USERNAME_OR_CHANGE);
                        } else {
                            Text = this.nativeManager.getLanguageString(DisplayStrings.DS_DONT_LIKE_IT_TRY_SOMETHING_ELSE);
                        }
                        bIsFirstTime++;
                    } else {
                        Text = this.nativeManager.getLanguageString(DisplayStrings.DS_LOOKS_GOOD);
                    }
                    this.IsValid = true;
                    ((TextView) findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.regular_name_your_wazer_color));
                    findViewById(C1283R.id.account_details_error_code2).setVisibility(8);
                    break;
                case 1:
                    Text = this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_USER_NAME_IS_TOO_SHORT);
                    ((TextView) findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    findViewById(C1283R.id.account_details_error_code2).setVisibility(8);
                    bIsFirstTime++;
                    break;
                case 2:
                    Text = this.nativeManager.getLanguageString(DisplayStrings.DS_USERNAME_IS_TOO_LONG);
                    ((TextView) findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    findViewById(C1283R.id.account_details_error_code2).setVisibility(8);
                    bIsFirstTime++;
                    break;
                case 3:
                    Text = this.nativeManager.getLanguageString(DisplayStrings.DS_INVALID_CHARACTER_USE_ONLY_LETTERS_AND_NUMBERS);
                    ((TextView) findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    findViewById(C1283R.id.account_details_error_code2).setVisibility(8);
                    bIsFirstTime++;
                    break;
                case 4:
                    Text = this.nativeManager.getLanguageString(DisplayStrings.DS_THATS_TAKEN_TRY);
                    findViewById(C1283R.id.account_details_error_code2).setVisibility(0);
                    ((TextView) findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    ((TextView) findViewById(C1283R.id.account_details_error_code2)).setText(username);
                    bIsFirstTime++;
                    break;
                case 5:
                    Text = this.nativeManager.getLanguageString(DisplayStrings.DS_YOU_CANT_USE_THIS_USERNAME);
                    ((TextView) findViewById(C1283R.id.account_details_error_code)).setTextColor(AppService.getAppResources().getColor(C1283R.color.pastrami_pink));
                    bIsFirstTime++;
                    break;
            }
            ((TextView) findViewById(C1283R.id.account_details_error_code)).setText(Text);
        }
    }

    private void onContinueClicked() {
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_USERNAME_CONTINUE, null, null, true);
        if (this.userNameView.getText().toString() == null || this.userNameView.getText().toString().equals("")) {
            MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_UHHOHE), "Username is invalid", true);
            return;
        }
        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
        NativeManager.getInstance().SuggestUserNameTerminate(this.userNameView.getText().toString());
    }

    public void OnUpdateResult(boolean bIsSuccess) {
        NativeManager.getInstance().CloseProgressPopup();
        if (bIsSuccess) {
            AppService.getMainActivity().EnableOrientation();
            NativeManager.getInstance().SetPhoneIsFirstTime(false);
            dismiss();
            if (MainActivity.bToOpenPasswordRecovery) {
                AppService.getMainActivity().openPasswordRecovery();
            } else {
                AppService.getMainActivity().openAddFriendPopup();
            }
        }
    }

    protected void close() {
    }
}
