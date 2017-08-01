package com.waze.share;

import android.content.Intent;
import android.net.Uri;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;

public class FbLikeLauncher implements UrlCallback {
    private ActivityBase mContext = null;

    public static FbLikeLauncher start(ActivityBase context) {
        return new FbLikeLauncher(context);
    }

    private FbLikeLauncher(ActivityBase context) {
        this.mContext = context;
        MyWazeNativeManager.getInstance().getFacebookLikeUrl(this, 0, 0);
    }

    public void onUrl(String url) {
        if (this.mContext != null && url != null) {
            if (url.startsWith(ShareConstants.SHARE_FB_LIKE_APP_URL_PREFIX)) {
                try {
                    this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    return;
                } catch (Exception e) {
                    if (!NativeManager.getInstance().getFbAppNotInstalled()) {
                        NativeManager.getInstance().setFbAppNotInstalled(true);
                        MyWazeNativeManager.getInstance().getFacebookLikeUrl(this, 0, 0);
                        return;
                    }
                    return;
                }
            }
            Intent intent = new Intent(this.mContext, FacebookLikeActivity.class);
            intent.putExtra(ShareConstants.SHARE_EXTRA_ID_LIKE_URL, url);
            this.mContext.startActivityForResult(intent, 0);
        }
    }
}
