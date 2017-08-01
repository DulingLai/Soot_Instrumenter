package com.waze;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ProgressBarDialog;

public final class WzWebView extends WebView {
    public static final int BROWSER_FLAG_NONE = 0;
    public static final int BROWSER_FLAG_NO_PROGRESS = 65536;
    public static final int BROWSER_FLAG_WINDOW_TYPE_NO_SCROLL = 64;
    public static final int BROWSER_FLAG_WINDOW_TYPE_TRANSPARENT = 32;
    private WebViewBackCallback mBackCallback = null;
    private Context mContext = null;
    private int mFlags;
    private WebViewPageProgressCallback mPageProgressCallback = null;
    private Dialog mProgressDlg = null;
    private WebViewSizeCallback mSizeCallback = null;
    private WebViewUrlOverride mUrlOverride = null;

    public interface WebViewBackCallback {
        boolean onBackEvent(KeyEvent keyEvent) throws ;
    }

    private final class WazeWebViewClient extends WebViewClient {

        class C13332 implements Runnable {
            C13332() throws  {
            }

            public void run() throws  {
                WazeWebViewClient.this.InitProgressDlg();
                WzWebView.this.mProgressDlg.show();
            }
        }

        class C13343 implements Runnable {
            C13343() throws  {
            }

            public void run() throws  {
                WzWebView.this.mProgressDlg.dismiss();
                WzWebView.this.mProgressDlg = null;
            }
        }

        private WazeWebViewClient() throws  {
        }

        public boolean shouldOverrideUrlLoading(WebView $r1, String $r2) throws  {
            if (WzWebView.this.mUrlOverride != null && WzWebView.this.mUrlOverride.onUrlOverride($r1, $r2)) {
                return true;
            }
            if ($r2.startsWith(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX)) {
                final Intent $r3 = new Intent("android.intent.action.DIAL", Uri.parse($r2));
                final MainActivity $r7 = AppService.getMainActivity();
                $r7.runOnUiThread(new Runnable() {
                    public void run() throws  {
                        $r7.startActivity($r3);
                    }
                });
                return true;
            } else if (AppService.getNativeManager().UrlHandler($r2)) {
                return true;
            } else {
                $r1.loadUrl($r2);
                return true;
            }
        }

        public void onPageStarted(WebView $r1, String $r2, Bitmap $r3) throws  {
            if ((WzWebView.this.mFlags & 65536) == 0 && (WzWebView.this.mProgressDlg == null || !WzWebView.this.mProgressDlg.isShowing())) {
                AppService.getActiveActivity().runOnUiThread(new C13332());
            }
            if (WzWebView.this.mPageProgressCallback != null) {
                WzWebView.this.mPageProgressCallback.onWebViewPageStarted();
            }
            super.onPageStarted($r1, $r2, $r3);
        }

        public void onPageFinished(WebView $r1, String $r2) throws  {
            super.onPageFinished($r1, $r2);
            if (WzWebView.this.mProgressDlg != null) {
                AppService.getActiveActivity().runOnUiThread(new C13343());
            }
            WzWebView.this.clearCache(false);
            if (WzWebView.this.mPageProgressCallback != null) {
                WzWebView.this.mPageProgressCallback.onWebViewPageFinished();
            }
        }

        private void InitProgressDlg() throws  {
            WzWebView.this.mProgressDlg = new ProgressBarDialog(WzWebView.this.mContext);
            WzWebView.this.mProgressDlg.setCancelable(true);
        }
    }

    public interface WebViewPageProgressCallback {
        void onWebViewPageFinished() throws ;

        void onWebViewPageStarted() throws ;
    }

    public interface WebViewSizeCallback {
        void onWebViewSize(int i, int i2) throws ;
    }

    public interface WebViewUrlOverride {
        boolean onUrlOverride(WebView webView, String str) throws ;
    }

    public WzWebView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        Init($r1, null, 0);
    }

    public WzWebView(Context $r1) throws  {
        super($r1);
        Init($r1, null, 0);
    }

    public WzWebView(Context $r1, int $i0) throws  {
        super($r1);
        Init($r1, null, $i0);
    }

    public boolean dispatchKeyEventPreIme(KeyEvent $r1) throws  {
        return ($r1.getKeyCode() == 4 && this.mBackCallback != null && this.mBackCallback.onBackEvent($r1)) ? true : super.dispatchKeyEventPreIme($r1);
    }

    public void setBackCallback(WebViewBackCallback $r1) throws  {
        this.mBackCallback = $r1;
    }

    public void setSizeCallback(WebViewSizeCallback $r1) throws  {
        this.mSizeCallback = $r1;
    }

    public void setUrlOverride(WebViewUrlOverride $r1) throws  {
        this.mUrlOverride = $r1;
    }

    public void setPageProgressCallback(WebViewPageProgressCallback $r1) throws  {
        this.mPageProgressCallback = $r1;
    }

    public void setFlags(int $i0) throws  {
        this.mFlags = $i0;
        if ((this.mFlags & 64) > 0) {
            setHorizontalScrollBarEnabled(false);
            setVerticalScrollBarEnabled(false);
            setScrollContainer(false);
        }
        if ((this.mFlags & 32) > 0) {
            setBackgroundColor(0);
        }
    }

    private void Init(Context $r1, WebViewBackCallback $r2, int $i0) throws  {
        this.mContext = $r1;
        if (!isInEditMode()) {
            WebSettings $r3 = getSettings();
            $r3.setSavePassword(false);
            $r3.setSaveFormData(false);
            $r3.setJavaScriptEnabled(true);
            $r3.setSupportZoom(false);
            $r3.setSaveFormData(true);
            $r3.setAllowFileAccess(true);
            if (VERSION.SDK_INT >= 16) {
                $r3.setAllowFileAccessFromFileURLs(true);
            }
            $r3.setDomStorageEnabled(true);
        }
        setFlags($i0);
        setBackCallback($r2);
        setClickable(true);
        setFocusableInTouchMode(true);
        setWebViewClient(new WazeWebViewClient());
    }

    protected void onSizeChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onSizeChanged($i0, $i1, $i2, $i3);
        if (this.mSizeCallback != null && $i0 > 0 && $i1 > 0) {
            this.mSizeCallback.onWebViewSize($i0, $i1);
            this.mSizeCallback = null;
        }
    }
}
