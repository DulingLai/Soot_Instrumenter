package com.waze.profile;

import android.os.Bundle;
import com.waze.C1283R;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class TwitterConnectActivity extends SimpleWebActivity implements UrlCallback {
    public static final String FROM_MENU_KEY = "com.waze.twitter.frommenu";
    public static final String INITIAL_TWEET = "com.waze.twitter.initial";
    private boolean fromMenu;
    private boolean initialTweet;
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        boolean z;
        boolean z2 = true;
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey(FROM_MENU_KEY)) {
            z = false;
        } else {
            z = true;
        }
        this.fromMenu = z;
        if (b == null || !b.containsKey(INITIAL_TWEET)) {
            z2 = false;
        }
        this.initialTweet = z2;
        setTitleText(C1283R.string.social_twitter_connect_title, this.fromMenu);
    }

    public void onWebViewSize(int width, int height) {
        this.myWazeNativeManager.getTwitterConnectUrl(this, width, height, this.initialTweet);
    }

    public void onUrl(String url) {
        loadUrl(url);
    }

    protected void onDestroy() {
        this.myWazeNativeManager.twitterPostConnect();
        super.onDestroy();
    }

    protected boolean onUrlOverride(String url) {
        if (!"waze://dialog_hide_current".equals(url) && !"waze://browser_close".equals(url)) {
            return false;
        }
        if (this.fromMenu) {
            setResult(0);
            finish();
        }
        return true;
    }
}
