package com.waze.rtalerts;

import android.content.Intent;
import android.os.Bundle;
import com.waze.C1283R;
import com.waze.view.web.SimpleWebActivity;

public class RTAlertsGroupActivity extends SimpleWebActivity {
    private String mUrl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mUrl = extras.getString("url");
            onUrl(this.mUrl);
        }
        setTitleText(C1283R.string.group);
    }

    public void onWebViewSize(int width, int height) {
    }

    public void onUrl(String url) {
        loadUrl(this.mUrl);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(resultCode);
        finish();
    }
}
