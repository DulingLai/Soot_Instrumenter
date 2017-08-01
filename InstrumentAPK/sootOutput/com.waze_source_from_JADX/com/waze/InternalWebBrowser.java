package com.waze;

import android.os.Bundle;
import com.waze.view.web.SimpleWebActivity;

public class InternalWebBrowser extends SimpleWebActivity {
    private String mTitle;
    private String mUrl;

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            this.mTitle = $r1.getString("title");
            if (this.mTitle != null) {
                setTitleStr(this.mTitle);
            }
            this.mUrl = $r1.getString("url");
            onUrl(this.mUrl);
        }
    }

    public void onWebViewSize(int width, int height) throws  {
    }

    public void onUrl(String url) throws  {
        loadUrl(this.mUrl);
    }
}
