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
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.view.map.ProgressAnimation;

public class BonusWebPopUP extends PopUp {
    private int mId;
    private boolean mIsShown = false;
    private int mLat;
    private LayoutManager mLayoutManager;
    private int mLon;
    private ProgressAnimation mProgressAnimation = null;
    private WebView mWebView = null;

    class C30621 implements OnClickListener {
        C30621() {
        }

        public void onClick(View v) {
            BonusWebPopUP.this.hide();
        }
    }

    class C30642 implements OnClickListener {

        class C30631 implements Runnable {
            C30631() {
            }

            public void run() {
                AppService.getNativeManager().navigateToBonusPointNTV(BonusWebPopUP.this.mId, BonusWebPopUP.this.mLat, BonusWebPopUP.this.mLon);
            }
        }

        C30642() {
        }

        public void onClick(View v) {
            BonusWebPopUP.this.hide();
            NativeManager.Post(new C30631());
        }
    }

    private final class BonusWebViewClient extends WebViewClient {
        private BonusWebViewClient() {
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
            BonusWebPopUP.this.mProgressAnimation.start();
            BonusWebPopUP.this.mProgressAnimation.setVisibility(0);
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BonusWebPopUP.this.mProgressAnimation.stop();
            BonusWebPopUP.this.mProgressAnimation.setVisibility(8);
            BonusWebPopUP.this.mWebView.setVisibility(0);
        }
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    public BonusWebPopUP(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public void setUpButtonsTxt() {
        ((TextView) findViewById(C1283R.id.CloseButtonText)).setText(AppService.getNativeManager().getLanguageString(354));
        ((TextView) findViewById(C1283R.id.NavigateButtonText)).setText(AppService.getNativeManager().getLanguageString(9));
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.bonus_web_popup, this);
        setUpButtonsTxt();
    }

    public void hide() {
        this.mIsShown = false;
        this.mLayoutManager.dismiss(this);
    }

    public void show(int iID, String url, int lat, int lon) {
        if (this.mIsShown) {
            hide();
        }
        this.mIsShown = true;
        this.mId = iID;
        this.mLat = lat;
        this.mLon = lon;
        findViewById(C1283R.id.CloseButton).setOnClickListener(new C30621());
        findViewById(C1283R.id.NavigateButton).setOnClickListener(new C30642());
        this.mWebView = (WebView) findViewById(C1283R.id.popupBonusWeb);
        this.mLayoutManager.addView(this);
        this.mProgressAnimation = (ProgressAnimation) findViewById(C1283R.id.progressAnimation1);
        this.mWebView.clearCache(true);
        this.mWebView.loadUrl(url);
        this.mWebView.setVisibility(8);
        this.mWebView.setWebViewClient(new BonusWebViewClient());
    }
}
