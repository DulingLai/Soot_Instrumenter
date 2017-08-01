package com.waze.profile;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.button.AutoResizeTextButton;

public class LoginActivity extends ActivityBase {
    private boolean allowPings;
    private AutoResizeTextButton doneButton;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private TextView nickView;
    private String nickname;
    private TextView passView;
    private String password;
    private TextView userView;
    private String username;

    class C23601 implements OnClickListener {
        C23601() {
        }

        public void onClick(View v) {
            LoginActivity.this.conclude();
        }
    }

    class C23612 implements OnFocusChangeListener {
        C23612() {
        }

        public void onFocusChange(View v, boolean focused) {
            if (!focused) {
                if (LoginActivity.this.mywazeManager.validateNickname(String.valueOf(LoginActivity.this.nickView.getText()))) {
                    LoginActivity.this.nickname = String.valueOf(LoginActivity.this.nickView.getText());
                } else if (LoginActivity.this.nickname != null) {
                    LoginActivity.this.nickView.setText(LoginActivity.this.nickname);
                }
            }
        }
    }

    class C23623 implements OnFocusChangeListener {
        C23623() {
        }

        public void onFocusChange(View v, boolean focused) {
            if (!focused) {
                if (LoginActivity.this.nickname == null || LoginActivity.this.nickname.length() == 0) {
                    LoginActivity.this.nickname = String.valueOf(LoginActivity.this.userView.getText());
                    LoginActivity.this.nickView.setText(LoginActivity.this.nickname);
                }
            }
        }
    }

    class C23634 extends LoginCallback {
        C23634() {
        }

        public void onLogin(boolean result) {
            if (!result) {
                ProfileLauncher.launch(AppService.getAppContext(), true);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.login);
        this.doneButton = (AutoResizeTextButton) findViewById(C1283R.id.doneButton);
        this.doneButton.setText(this.nativeManager.getLanguageString(375));
        this.doneButton.setOnClickListener(new C23601());
        ((TextView) findViewById(C1283R.id.titleBar)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_LOG_IN));
        ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USER_NAME));
        ((TextView) findViewById(C1283R.id.passwordTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_PASSWORD));
        ((TextView) findViewById(C1283R.id.nicknameTitle)).setText(this.nativeManager.getLanguageString(88));
        this.userView = (TextView) findViewById(C1283R.id.userName);
        this.passView = (TextView) findViewById(C1283R.id.password);
        this.nickView = (TextView) findViewById(C1283R.id.nickname);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.nickname = b.getString("com.waze.mywaze.nickname");
            if (this.nickname != null) {
                this.nickView.setText(this.nickname);
            }
            this.allowPings = b.getBoolean("com.waze.mywaze.pingable");
        }
        this.nickView.setOnFocusChangeListener(new C23612());
        this.userView.setOnFocusChangeListener(new C23623());
    }

    public void onBackPressed() {
        conclude();
    }

    private void conclude() {
        this.username = String.valueOf(this.userView.getText());
        this.password = String.valueOf(this.passView.getText());
        this.nickname = String.valueOf(this.nickView.getText());
        this.mywazeManager.doLoginOk(this.username, this.password, this.nickname, this.allowPings, new C23634());
        setResult(-1);
        finish();
    }
}
