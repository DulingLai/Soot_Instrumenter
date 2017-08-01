package com.waze.share;

import android.os.Bundle;
import com.waze.C1283R;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class TwitterFollowActivity extends SimpleWebActivity implements UrlCallback {
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText(C1283R.string.share_follow_title);
    }

    public void onWebViewSize(int width, int height) {
        this.myWazeNativeManager.getTwitterFollowUrl(this, width, height);
    }

    public void onUrl(String url) {
        loadUrl(url);
    }

    protected void onDestroy() {
        this.myWazeNativeManager.foursquarePostConnect();
        super.onDestroy();
    }

    protected boolean onUrlOverride(String url) {
        if (!"waze://dialog_hide_current".equals(url) && !"waze://browser_close".equals(url)) {
            return false;
        }
        setResult(0);
        finish();
        return true;
    }
}
