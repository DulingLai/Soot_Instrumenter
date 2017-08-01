package com.waze.settings;

import android.os.Bundle;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class SettingsNotificationActivity extends SimpleWebActivity implements UrlCallback {
    public static final String NOTIFICATIONS_VALUE = "SettingsNotificationOnOff";
    public static final String screenName = "SettingsNotification";
    public MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText(C1283R.string.notifications);
    }

    public void onWebViewSize(int width, int height) {
        this.myWazeNativeManager.getNotificationSettingsUrl(this, width, height);
    }

    public void onUrl(String url) {
        loadUrl(url);
    }

    protected boolean onUrlOverride(String url) {
        if (url == null || !url.startsWith(NativeManager.WAZE_URL_PATTERN)) {
            return super.onUrlOverride(url);
        }
        if (AppService.getNativeManager().UrlHandler(url, true)) {
            return true;
        }
        loadUrl(url);
        return true;
    }
}
