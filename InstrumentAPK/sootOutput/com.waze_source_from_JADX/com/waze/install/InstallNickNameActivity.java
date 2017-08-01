package com.waze.install;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LoginCallback;
import com.waze.mywaze.MyWazeNativeManager.ProfileCallback;
import com.waze.mywaze.MyWazeNativeManager.ProfileSettings;
import com.waze.profile.ProfileLauncher;
import com.waze.profile.WelcomeDoneActivity;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class InstallNickNameActivity extends ActivityBase implements ProfileCallback {
    private boolean allowping;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();
    private TextView nickView;
    private String nickname;
    private String password;
    private String username;

    class C18111 implements OnClickListener {
        C18111() throws  {
        }

        public void onClick(View v) throws  {
            InstallNickNameActivity.this.startActivityForResult(new Intent(InstallNickNameActivity.this, WelcomeDoneActivity.class), 0);
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_NICKNAME_NEXT, null, null, false);
            InstallNickNameActivity.this.setResult(3);
            InstallNickNameActivity.this.finish();
        }
    }

    class C18122 implements OnFocusChangeListener {
        C18122() throws  {
        }

        public void onFocusChange(View v, boolean $z0) throws  {
            if (!$z0) {
                if (InstallNickNameActivity.this.mywazeManager.validateNickname(String.valueOf(InstallNickNameActivity.this.nickView.getText()))) {
                    InstallNickNameActivity.this.nickname = String.valueOf(InstallNickNameActivity.this.nickView.getText());
                } else if (InstallNickNameActivity.this.nickname != null) {
                    InstallNickNameActivity.this.nickView.setText(InstallNickNameActivity.this.nickname);
                }
            }
        }
    }

    class C18133 extends LoginCallback {
        C18133() throws  {
        }

        public void onLogin(boolean $z0) throws  {
            if (!$z0) {
                ProfileLauncher.launch(AppService.getAppContext(), true);
            }
        }
    }

    public InstallNickNameActivity() throws  {
        MyWazeNativeManager.getInstance().getProfileSettings(this);
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.nickname);
        C18111 $r2 = new C18111();
        if (this.nativeManager.getLanguageRtl()) {
            ViewGroup $r5 = (ViewGroup) findViewById(C1283R.id.nicknameFrame);
            View $r4 = $r5.findViewById(C1283R.id.nicknameTitle);
            $r5.removeView($r4);
            $r5.addView($r4);
        }
        ((TitleBar) findViewById(C1283R.id.profileTitle)).setOnClickCloseListener($r2);
        ((TitleBar) findViewById(C1283R.id.profileTitle)).init(this, this.nativeManager.getLanguageString(88), this.nativeManager.getLanguageString((int) DisplayStrings.DS_NEXT));
        ((TitleBar) findViewById(C1283R.id.profileTitle)).setUpButtonDisabled();
        ((TextView) findViewById(C1283R.id.nicknameTitle)).setText(this.nativeManager.getLanguageString(88));
        ((TextView) findViewById(C1283R.id.nicknameExplainTitleText)).setText(this.nativeManager.getLanguageString(86));
        ((TextView) findViewById(C1283R.id.nicknameExplainBodyText)).setText(this.nativeManager.getLanguageString(87));
        ((TextView) findViewById(C1283R.id.nicknameExplainFooter)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_REMEMBER_WAZE_GETS_A_WHOLE_LOT_MORE_FUN));
        this.nickView = (TextView) findViewById(C1283R.id.nickname);
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            this.nickname = $r1.getString("com.waze.mywaze.nickname");
            if (this.nickname != null) {
                this.nickView.setText(this.nickname);
            }
            this.username = $r1.getString("com.waze.mywaze.username");
            this.password = $r1.getString("com.waze.mywaze.password");
            $r1.getBoolean("com.waze.mywaze.pingable");
        }
        this.nickView.setOnFocusChangeListener(new C18122());
    }

    public void onProfile(ProfileSettings $r1) throws  {
        this.username = $r1.userName;
        this.password = $r1.password;
        this.nickname = $r1.nickName;
        this.allowping = $r1.allowPings;
        this.nickView.setText(this.nickname);
    }

    public void onBackPressed() throws  {
    }

    protected void onDestroy() throws  {
        this.nickname = String.valueOf(this.nickView.getText());
        LoginCallback $r4 = new C18133();
        this.mywazeManager.doLoginOk(this.username, this.password, this.nickname, this.allowping, $r4);
        super.onDestroy();
    }
}
