package com.waze.profile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.mywaze.MyWazeNativeManager.ProfileCallback;
import com.waze.mywaze.MyWazeNativeManager.ProfileSettings;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class ProfileActivity extends ActivityBase implements ProfileCallback {
    private boolean allowping;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private String nickname;
    private TextView passView;
    private String password;
    private TextView userView;
    private String username;

    class C23911 extends LoginCallback {
        C23911() {
        }

        public void onLogin(boolean result) {
            if (!result) {
                ProfileLauncher.launch(AppService.getAppContext(), true);
            }
        }
    }

    public ProfileActivity() {
        this.mywazeManager.getProfileSettings(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.profile);
        ((TitleBar) findViewById(C1283R.id.profileTitle)).init((Activity) this, (int) DisplayStrings.DS_PROFILE);
        ((TextView) findViewById(C1283R.id.userNameTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_USERNAME));
        ((TextView) findViewById(C1283R.id.passwordTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_PASSWORD));
        ((TextView) findViewById(C1283R.id.ProfileBodyText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_CREDENTIALS_EXPLAINED_TEXT));
        this.userView = (TextView) findViewById(C1283R.id.userName);
        this.passView = (TextView) findViewById(C1283R.id.password);
    }

    public void onProfile(ProfileSettings profile) {
        this.username = profile.userName;
        this.password = profile.password;
        this.nickname = profile.nickName;
        this.allowping = profile.allowPings;
        if (this.username != null) {
            this.userView.setText(this.username);
        }
        if (this.password != null) {
            this.passView.setText(this.password);
        }
    }

    protected void onDestroy() {
        this.username = String.valueOf(this.userView.getText());
        this.password = String.valueOf(this.passView.getText());
        this.mywazeManager.doLoginOk(this.username, this.password, this.nickname, this.allowping, new C23911());
        super.onDestroy();
    }
}
