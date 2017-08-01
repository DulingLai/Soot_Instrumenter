package com.waze.view.popups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.view.map.ProgressAnimation;

public class SystemMessageWeb extends PopUp {
    private static SystemMessageWeb mInstance;
    private static boolean mIsShown = false;
    private static LayoutManager mLayoutManager;
    private ProgressAnimation mProgressAnimation = null;
    private WebView mWebView = null;

    class C32221 implements OnClickListener {
        C32221() {
        }

        public void onClick(View v) {
            SystemMessageWeb.this.hide();
        }
    }

    private final class SystemMessageWebViewClient extends WebViewClient {
        private SystemMessageWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX)) {
                final Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(url));
                final Activity activity = AppService.getMainActivity();
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        activity.startActivity(intent);
                    }
                });
            } else if (!AppService.getNativeManager().UrlHandler(url)) {
                view.loadUrl(url);
            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            SystemMessageWeb.this.mProgressAnimation.start();
            SystemMessageWeb.this.mProgressAnimation.setVisibility(0);
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            SystemMessageWeb.this.mProgressAnimation.stop();
            SystemMessageWeb.this.mProgressAnimation.setVisibility(8);
            SystemMessageWeb.this.mWebView.setVisibility(0);
        }
    }

    public static SystemMessageWeb getInstance(Context context, LayoutManager layoutManager) {
        if (mInstance == null) {
            mInstance = new SystemMessageWeb(context, layoutManager);
        }
        return mInstance;
    }

    private SystemMessageWeb(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        mLayoutManager = layoutManager;
        init();
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    public void setUpButtonsTxt() {
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.system_message_web, this);
        setUpButtonsTxt();
    }

    public void hide() {
        mIsShown = false;
        mLayoutManager.dismiss(mInstance);
    }

    public void show(String url) {
        if (mIsShown) {
            mInstance.hide();
        }
        mIsShown = true;
        findViewById(C1283R.id.CloseButton).setOnClickListener(new C32221());
        this.mWebView = (WebView) findViewById(C1283R.id.systemMessageWeb);
        mLayoutManager.addView(this);
        this.mProgressAnimation = (ProgressAnimation) findViewById(C1283R.id.progressAnimation1);
        this.mWebView.clearCache(true);
        this.mWebView.loadUrl(url);
        this.mWebView.setVisibility(8);
        this.mWebView.setWebViewClient(new SystemMessageWebViewClient());
    }
}
