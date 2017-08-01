package com.waze.mywaze.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.LinkedinSettings;
import com.waze.profile.LinkedinConnectActivity;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class LinkedInActivity extends ActivityBase {
    private static LinkedInActivity mActiveInstance = null;
    private Button mBtnConnect;
    private boolean mConnected = false;
    private MyWazeNativeManager mMywazeManager = MyWazeNativeManager.getInstance();
    private NativeManager mNativeManager = AppService.getNativeManager();

    class C20171 implements OnClickListener {
        C20171() throws  {
        }

        public void onClick(View v) throws  {
            if (LinkedInActivity.this.mConnected) {
                LinkedInActivity.this.onLogout();
            } else {
                LinkedInActivity.this.onLogin();
            }
        }
    }

    class C20182 implements DialogInterface.OnClickListener {
        C20182() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
        }
    }

    public LinkedInActivity() throws  {
        mActiveInstance = this;
    }

    protected void onDestroy() throws  {
        mActiveInstance = null;
        super.onDestroy();
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.linkedin);
        this.mNativeManager = AppService.getNativeManager();
        this.mMywazeManager = MyWazeNativeManager.getInstance();
        this.mBtnConnect = (Button) findViewById(C1283R.id.linkedinConnectBtn);
        this.mBtnConnect.setVisibility(0);
        this.mBtnConnect.setTextColor(-12303292);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, DisplayStrings.DS_LINKEDIN_CONNECT_TITLE);
        ((TextView) findViewById(C1283R.id.linkedinLabelHeaderText)).setText(this.mNativeManager.getLanguageString((int) DisplayStrings.DS_LINKEDIN_LABEL_HEADER));
        ((TextView) findViewById(C1283R.id.linkedJustificationText)).setText(this.mNativeManager.getLanguageString((int) DisplayStrings.DS_LINKEDIN_LABEL_NOTES));
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            boolean z = ((LinkedinSettings) $r1.getSerializable("com.waze.mywaze.linkedinsettings")).loggedIn;
            boolean $z0 = z;
            this.mConnected = z;
        }
        updateStatus();
        this.mBtnConnect.setOnClickListener(new C20171());
    }

    private void updateStatus() throws  {
        if (this.mConnected) {
            this.mBtnConnect.setText(this.mNativeManager.getLanguageString((int) DisplayStrings.DS_LINKEDIN_DISCONNECT_BUTTON));
            Logger.m41i("LinkedIn session is currently connected");
            return;
        }
        this.mBtnConnect.setText(this.mNativeManager.getLanguageString((int) DisplayStrings.DS_LINKEDIN_CONNECT_BUTTON));
        Logger.m41i("LinkedIn session is currently disconnected");
    }

    private void onLogin() throws  {
        if (AppService.isNetworkAvailable()) {
            Logger.m41i("Requesting LinkedIn connect");
            this.mNativeManager.OpenProgressPopup("");
            startActivityForResult(new Intent(this, LinkedinConnectActivity.class), 0);
            return;
        }
        Logger.m38e("LinkedinActivity:: onLogin, no network connection");
        MsgBox.openMessageBoxWithCallback(this.mNativeManager.getLanguageString((int) DisplayStrings.DS_NO_NETWORK_CONNECTION), this.mNativeManager.getLanguageString(252), false, new C20182());
    }

    private void onLogout() throws  {
        Logger.m41i("Requesting LinkedIn disconnect");
        this.mMywazeManager.linkedinDisconnect();
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        if ($i1 == 6) {
            MsgBox.getInstance().OpenMessageBoxTimeoutCb(this.mNativeManager.getLanguageString((int) DisplayStrings.DS_UHHOHE), this.mNativeManager.getLanguageString(179), -1, -1);
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    public static void refreshStatus(boolean $z0) throws  {
        if (mActiveInstance != null && mActiveInstance.isRunning()) {
            mActiveInstance.mConnected = $z0;
            mActiveInstance.updateStatus();
            NativeManager.getInstance().CloseProgressPopup();
        }
    }
}
