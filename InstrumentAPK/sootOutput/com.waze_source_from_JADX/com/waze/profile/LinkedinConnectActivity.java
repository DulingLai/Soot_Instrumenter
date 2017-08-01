package com.waze.profile;

import android.os.Bundle;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.web.SimpleWebActivity;

public class LinkedinConnectActivity extends SimpleWebActivity implements UrlCallback {
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWebViewFlags(0);
        setTitleStr(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_DRIVER_CONNECT_LINKEDIN_TITLE));
    }

    public void onWebViewSize(int width, int height) {
        this.myWazeNativeManager.getLinkedinConnectUrl(this, width, height);
    }

    public void onUrl(String url) {
        Logger.d("Linkedin loading url: " + url);
        loadUrl(url);
    }

    protected void onDestroy() {
        this.myWazeNativeManager.linkedinPostConnect();
        super.onDestroy();
    }

    protected boolean onUrlOverride(String url) {
        if (url == null) {
            return false;
        }
        Logger.d("Linkedin: onUrlOverride url: " + url);
        if (url.startsWith("waze://dialog_hide_current") || url.startsWith("waze://browser_close")) {
            setResult(0);
            finish();
            return true;
        } else if (!url.startsWith("waze://browser_error")) {
            return false;
        } else {
            setResult(6);
            finish();
            return true;
        }
    }
}
