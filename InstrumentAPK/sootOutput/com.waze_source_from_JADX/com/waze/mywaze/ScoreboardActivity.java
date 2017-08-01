package com.waze.mywaze;

import android.os.Bundle;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class ScoreboardActivity extends SimpleWebActivity implements UrlCallback {
    public MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setTitleText(C1283R.string.myscoreboard);
    }

    public void onWebViewSize(int $i0, int $i1) throws  {
        this.myWazeNativeManager.getScoreboardUrl(this, $i0, $i1);
    }

    public void onUrl(String $r1) throws  {
        loadUrl($r1);
    }

    protected boolean onUrlOverride(String $r1) throws  {
        if ($r1 == null || !$r1.startsWith(NativeManager.WAZE_URL_PATTERN)) {
            return super.onUrlOverride($r1);
        }
        if (AppService.getNativeManager().UrlHandler($r1.toString(), true)) {
            return true;
        }
        loadUrl($r1);
        return true;
    }
}
