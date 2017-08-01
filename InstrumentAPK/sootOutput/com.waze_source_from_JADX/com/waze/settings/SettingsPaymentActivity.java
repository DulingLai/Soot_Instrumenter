package com.waze.settings;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.WzWebView;
import com.waze.WzWebView.WebViewBackCallback;
import com.waze.WzWebView.WebViewPageProgressCallback;
import com.waze.WzWebView.WebViewSizeCallback;
import com.waze.WzWebView.WebViewUrlOverride;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.title.TitleBar;

public class SettingsPaymentActivity extends ActivityBase implements WebViewSizeCallback {
    public static final int REGISTRATION_ALREADY_REGISTERED = 2;
    public static final int REGISTRATION_FAILED = 1;
    public static final int REGISTRATION_SUCCESS_APP = 99;
    public static final int REGISTRATION_SUCCESS_PAYONEER = 0;
    public static final int REGISTRATION_TOKEN_EXPIRED = 3;
    protected TitleBar mTitleBar;
    protected String mUrl;
    private ProgressAnimation mWebViewLoadAnimation;
    protected WzWebView webView;

    class C27451 implements WebViewBackCallback {
        C27451() {
        }

        public boolean onBackEvent(KeyEvent aEvent) {
            return false;
        }
    }

    class C27462 implements WebViewUrlOverride {
        C27462() {
        }

        public boolean onUrlOverride(WebView view, String url) {
            return SettingsPaymentActivity.this.onUrlOverride(url);
        }
    }

    class C27473 implements WebViewPageProgressCallback {
        C27473() {
        }

        public void onWebViewPageStarted() {
            SettingsPaymentActivity.this.onLoadStarted();
        }

        public void onWebViewPageFinished() {
            SettingsPaymentActivity.this.onLoadFinished();
        }
    }

    class C27484 implements OnClickListener {
        C27484() {
        }

        public void onClick(View v) {
            SettingsPaymentActivity.this.setResult(-1);
            SettingsPaymentActivity.this.finish();
        }
    }

    final class MyWebChromeClient extends WebChromeClient {
        MyWebChromeClient() {
        }

        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new Builder(SettingsPaymentActivity.this, C1283R.style.CustomPopup).setTitle("").setMessage(message).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).create().show();
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.web_simple);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.webTitle);
        this.mWebViewLoadAnimation = (ProgressAnimation) findViewById(C1283R.id.webTitleProgress);
        this.webView = (WzWebView) findViewById(C1283R.id.webView);
        this.webView.setFlags(65536);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebChromeClient(new MyWebChromeClient());
        this.webView.setBackCallback(new C27451());
        this.webView.setUrlOverride(new C27462());
        this.webView.setPageProgressCallback(new C27473());
        this.webView.setSizeCallback(this);
        setTitleStr(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CARPOOL_SETTINGS_PAYMENTS));
        this.mTitleBar.setOnClickCloseListener(new C27484());
        loadUrl(getIntent().getStringExtra("URL"));
    }

    protected void onLoadStarted() {
        this.mTitleBar.findViewById(C1283R.id.titleBarTitleText).setVisibility(4);
        this.mWebViewLoadAnimation.setVisibility(0);
        this.mWebViewLoadAnimation.start();
    }

    protected void onLoadFinished() {
        this.mWebViewLoadAnimation.stop();
        this.mWebViewLoadAnimation.setVisibility(8);
        this.mTitleBar.findViewById(C1283R.id.titleBarTitleText).setVisibility(0);
    }

    public void onBackPressed() {
        if (passBackPresses() && this.webView != null && this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected void onDestroy() {
        if (this.webView != null) {
            this.webView = null;
        }
        super.onDestroy();
    }

    public void setWebViewFlags(int flags) {
        if (this.webView != null) {
            this.webView.setFlags(flags);
        }
    }

    public void onWebViewSize(int width, int height) {
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        if (this.webView != null) {
            this.webView.saveState(state);
        }
    }

    protected void onRestoreInstanceState(Bundle state) {
        if (this.webView != null) {
            this.webView.restoreState(state);
        }
        super.onRestoreInstanceState(state);
    }

    private void processRegistrationReturnValue(int value) {
        switch (value) {
            case 3:
                return;
            default:
                if (value == 0) {
                    setResult(99);
                } else {
                    setResult(value);
                }
                finish();
                return;
        }
    }

    protected boolean canGoBack() {
        return this.webView != null && this.webView.canGoBack();
    }

    protected void setTitleText(int resTitle, boolean showClose) {
        if (showClose) {
            this.mTitleBar.init((Activity) this, getString(resTitle));
        } else {
            this.mTitleBar.init((Activity) this, getString(resTitle), showClose);
        }
    }

    protected void setTitleText(int resTitle) {
        setTitleText(resTitle, true);
    }

    protected void setTitleStr(String title) {
        this.mTitleBar.init((Activity) this, title);
    }

    protected void loadUrl(String url) {
        if (this.webView != null) {
            this.webView.addJavascriptInterface(new MyJavascriptInterface(this), "MobileAppBridge");
            this.webView.loadUrl(url);
            this.mUrl = url;
        }
    }

    protected boolean onUrlOverride(String url) {
        return false;
    }

    protected boolean passBackPresses() {
        return true;
    }

    protected WzWebView getWebView() {
        return this.webView;
    }
}
