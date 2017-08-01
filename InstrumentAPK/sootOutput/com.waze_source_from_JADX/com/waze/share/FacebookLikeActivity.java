package com.waze.share;

import android.os.Bundle;
import android.util.Log;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.view.web.SimpleWebActivity;

public class FacebookLikeActivity extends SimpleWebActivity {
    private String mUrl;
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUrl = getIntent().getStringExtra(ShareConstants.SHARE_EXTRA_ID_LIKE_URL);
        String title = getIntent().getStringExtra(ShareConstants.SHARE_EXTRA_ID_LIKE_TITLE);
        if (title == null) {
            setTitleText(C1283R.string.share_like_title);
        } else {
            setTitleStr(title);
        }
        Log.d(Logger.TAG, "FB URL is " + this.mUrl);
        loadUrl(this.mUrl);
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
