package com.waze.share;

import android.os.Bundle;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.view.web.SimpleWebActivity;

public class LinkedinProfileActivity extends SimpleWebActivity {
    private String mUrl;
    private MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUrl = getIntent().getStringExtra(ShareConstants.SHARE_EXTRA_LI_PROFILE_URL);
        String title = getIntent().getStringExtra(ShareConstants.SHARE_EXTRA_LI_PROFILE_TITLE);
        if (title == null || title.isEmpty()) {
            title = "LinkedIn profile";
        }
        setTitleStr(title);
        loadUrl(this.mUrl);
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
