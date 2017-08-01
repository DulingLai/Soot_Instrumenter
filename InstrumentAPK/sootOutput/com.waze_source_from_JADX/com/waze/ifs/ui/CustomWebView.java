package com.waze.ifs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class CustomWebView extends WebView {
    public CustomWebView(Context $r1) throws  {
        super($r1);
    }

    public CustomWebView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public CustomWebView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public void onWindowFocusChanged(boolean $z0) throws  {
        try {
            super.onWindowFocusChanged($z0);
        } catch (NullPointerException e) {
        }
    }
}
