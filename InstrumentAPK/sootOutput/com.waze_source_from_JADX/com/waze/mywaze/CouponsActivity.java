package com.waze.mywaze;

import android.os.Bundle;
import com.waze.C1283R;
import com.waze.mywaze.MyWazeNativeManager.UrlCallback;
import com.waze.view.web.SimpleWebActivity;

public class CouponsActivity extends SimpleWebActivity implements UrlCallback {
    public MyWazeNativeManager myWazeNativeManager = MyWazeNativeManager.getInstance();

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setTitleText(C1283R.string.mycoupons);
    }

    public void onWebViewSize(int $i0, int $i1) throws  {
        this.myWazeNativeManager.getCouponsUrl(this, $i0, $i1);
    }

    public void onUrl(String $r1) throws  {
        loadUrl($r1);
    }
}
