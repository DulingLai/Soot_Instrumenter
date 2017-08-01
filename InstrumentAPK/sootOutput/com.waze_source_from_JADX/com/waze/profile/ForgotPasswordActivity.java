package com.waze.profile;

import android.os.Bundle;
import com.waze.C1283R;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class ForgotPasswordActivity extends SimpleWebActivity implements UrlCallback {
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        setTitleText(C1283R.string.fp_title, false);
    }

    public void onWebViewSize(int width, int height) {
        this.myWazeNativeManager.getForgotPasswordUrl(this, width, height);
    }

    public void onUrl(String url) {
        loadUrl(url);
    }

    protected boolean passBackPresses() {
        return false;
    }
}
