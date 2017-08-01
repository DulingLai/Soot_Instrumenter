package com.waze.profile;

import android.content.Intent;
import android.os.Bundle;
import com.waze.C1283R;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class FacebookConnectActivity extends SimpleWebActivity implements UrlCallback {
    public static final String FROM_MENU_KEY = "com.waze.facebook.frommenu";
    private boolean fromMenu;
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        boolean z = b != null && b.containsKey(FROM_MENU_KEY);
        this.fromMenu = z;
        setTitleText(C1283R.string.wfb_connect_title, this.fromMenu);
    }

    public void onWebViewSize(int width, int height) {
        this.myWazeNativeManager.getFacebookConnectUrl(this, width, height);
    }

    public void onUrl(String url) {
        loadUrl(url);
    }

    protected void onDestroy() {
        this.myWazeNativeManager.facebookPostConnect();
        super.onDestroy();
    }

    protected boolean onUrlOverride(String url) {
        if (!"waze://dialog_hide_current".equals(url) && !"waze://browser_close".equals(url)) {
            return false;
        }
        if (this.fromMenu) {
            setResult(0);
            finish();
        } else {
            startActivityForResult(new Intent(this, WelcomeDoneActivity.class), 0);
        }
        return true;
    }
}
