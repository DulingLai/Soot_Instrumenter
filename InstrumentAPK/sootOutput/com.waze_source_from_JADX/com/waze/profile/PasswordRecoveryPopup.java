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
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.strings.DisplayStrings;

public class PasswordRecoveryPopup extends Dialog {
    private boolean IsValid = false;
    boolean already_queried = false;
    Handler f89h = new Handler();
    long idle_min = 500;
    long last_text_edit = 0;
    private Context mContext = null;
    private String mText = null;
    private String mUserName = null;
    private MyWazeData mWazeData = null;
    private NativeManager nativeManager;
    private TextView signButton;
    private EditText userNameView;

    class C23881 implements OnClickListener {
        C23881() {
        }

        public void onClick(View v) {
            PasswordRecoveryPopup.this.onContinueClicked();
        }
    }

    class C23892 implements OnEditorActionListener {
        C23892() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            PasswordRecoveryPopup.this.onContinueClicked();
            return true;
        }
    }

    class C23903 implements TextWatcher {
        C23903() {
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        public void onTextChanged(CharSequence arg0, int start, int before, int count) {
        }
    }

    public PasswordRecoveryPopup(Context context) {
        super(context, C1283R.style.Dialog);
        this.mContext = context;
        this.nativeManager = AppService.getNativeManager();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        MyWazeNativeManager.getInstance().getMyWazeData(AppService.getMainActivity());
    }

    public void SetMyWazeData(MyWazeData Data) {
        this.mWazeData = Data;
        ((TextView) findViewById(C1283R.id.account_details_title)).setText(Data.mUserName);
    }

    private void initLayout() {
        setContentView(C1283R.layout.password_recovery);
        getWindow().setLayout(-1, -1);
        ((TextView) findViewById(C1283R.id.account_details_title)).setText("");
        ((TextView) findViewById(C1283R.id.account_details_title2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_YOUR_USER_NAME_IS));
        ((TextView) findViewById(C1283R.id.account_details_error_code2)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_6_CHARACTERS_MINIMUM));
        ((TextView) findViewById(C1283R.id.account_details_error_code)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SELECT_YOUR_PASSWORD));
        MainActivity.bToOpenPasswordRecovery = false;
        this.userNameView = (EditText) findViewById(C1283R.id.UserName);
        this.signButton = (TextView) findViewById(C1283R.id.account_details_continue);
        this.signButton.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_CHANGE_PASSWORD));
        this.signButton.setOnClickListener(new C23881());
        this.userNameView.setOnEditorActionListener(new C23892());
        this.userNameView.addTextChangedListener(new C23903());
    }

    public void onBackPressed() {
        close();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void OnUpdateResult(boolean bIsSuccess) {
        NativeManager.getInstance().CloseProgressPopup();
        if (bIsSuccess) {
            close();
        }
    }

    private void onContinueClicked() {
        String password = this.userNameView.getText().toString();
        if (password != null && !password.equals("")) {
            String password1 = "";
            String firstName = this.mWazeData.mFirstName;
            String lastName = this.mWazeData.mLastName;
            String username = this.mWazeData.mUserName;
            password1 = this.userNameView.getText().toString();
            if (password1 == null || password1.equals("")) {
                password1 = "";
            }
            NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
            MyWazeNativeManager.getInstance().setNames(firstName, lastName, username, password1, "");
        }
    }

    protected void close() {
        AppService.getMainActivity().EnableOrientation();
        dismiss();
    }
}
