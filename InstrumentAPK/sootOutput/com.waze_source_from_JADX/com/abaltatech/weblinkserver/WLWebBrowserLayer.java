package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.abaltatech.mcs.logger.MCSLogger;
import java.nio.ByteBuffer;

@SuppressLint({"DefaultLocale", "SetJavaScriptEnabled"})
public class WLWebBrowserLayer implements WLLayer, WLMouseEventHandler, WLTouchEventHandler {
    private static final String JS_INJECT_SCALE = "var meta = document.createElement('meta');meta.id = 'viewport';meta.name='viewport';meta.content = 'width=%d, height=%d, target-densitydpi=device-dpi, user-scalable=no';document.getElementsByTagName('head')[0].appendChild(meta);";
    private Bitmap m_bitmap = null;
    private Canvas m_canvas = null;
    private volatile boolean m_drawing = false;
    private ByteBuffer m_frameBuffer = null;
    private String m_homePage = null;
    private WLRect m_rect = null;
    private RelativeLayout m_scrollView = null;
    private Handler m_uiHandler = null;
    private WebView m_webView = null;

    class C04281 implements OnTouchListener {
        C04281() throws  {
        }

        public boolean onTouch(View $r1, MotionEvent $r2) throws  {
            switch ($r2.getAction()) {
                case 0:
                case 1:
                    if (!$r1.hasFocus()) {
                        $r1.requestFocus();
                        break;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    class C04292 implements Runnable {
        C04292() throws  {
        }

        public void run() throws  {
            synchronized (WLWebBrowserLayer.this) {
                int $i0 = MeasureSpec.makeMeasureSpec(WLWebBrowserLayer.this.m_rect.width(), 1073741824);
                int $i1 = MeasureSpec.makeMeasureSpec(WLWebBrowserLayer.this.m_rect.height(), 1073741824);
                WLWebBrowserLayer.this.m_scrollView.measure($i0, $i1);
                WLWebBrowserLayer.this.m_scrollView.layout(0, 0, WLWebBrowserLayer.this.m_scrollView.getMeasuredWidth(), WLWebBrowserLayer.this.m_scrollView.getMeasuredHeight());
                WLWebBrowserLayer.this.m_webView.measure($i0, $i1);
                WLWebBrowserLayer.this.m_webView.layout(0, 0, WLWebBrowserLayer.this.m_webView.getMeasuredWidth(), WLWebBrowserLayer.this.m_webView.getMeasuredHeight());
                WLWebBrowserLayer.this.m_bitmap = Bitmap.createBitmap(WLWebBrowserLayer.this.m_rect.width(), WLWebBrowserLayer.this.m_rect.height(), Config.ARGB_8888);
                WLWebBrowserLayer.this.m_canvas = new Canvas(WLWebBrowserLayer.this.m_bitmap);
                WLWebBrowserLayer.this.m_frameBuffer = ByteBuffer.allocateDirect(WLWebBrowserLayer.this.m_bitmap.getRowBytes() * WLWebBrowserLayer.this.m_rect.height());
            }
        }
    }

    class C04303 implements Runnable {
        C04303() throws  {
        }

        public void run() throws  {
            synchronized (WLWebBrowserLayer.this) {
                if (WLWebBrowserLayer.this.m_canvas != null) {
                    WLWebBrowserLayer.this.m_scrollView.draw(WLWebBrowserLayer.this.m_canvas);
                    WLWebBrowserLayer.this.m_frameBuffer.position(0);
                    WLWebBrowserLayer.this.m_bitmap.copyPixelsToBuffer(WLWebBrowserLayer.this.m_frameBuffer);
                }
                WLWebBrowserLayer.this.m_drawing = false;
            }
        }
    }

    class C04336 implements Runnable {
        C04336() throws  {
        }

        public void run() throws  {
            if (WLWebBrowserLayer.this.m_webView != null && WLWebBrowserLayer.this.m_homePage != null) {
                WLWebBrowserLayer.this.m_webView.stopLoading();
                WLWebBrowserLayer.this.m_webView.loadUrl(WLWebBrowserLayer.this.m_homePage);
            }
        }
    }

    class C04347 implements Runnable {
        C04347() throws  {
        }

        public void run() throws  {
            if (WLWebBrowserLayer.this.m_webView != null && WLWebBrowserLayer.this.m_webView.canGoBack()) {
                WLWebBrowserLayer.this.m_webView.stopLoading();
                WLWebBrowserLayer.this.m_webView.goBack();
            }
        }
    }

    class C04358 implements Runnable {
        C04358() throws  {
        }

        public void run() throws  {
            if (WLWebBrowserLayer.this.m_webView != null && WLWebBrowserLayer.this.m_webView.canGoForward()) {
                WLWebBrowserLayer.this.m_webView.stopLoading();
                WLWebBrowserLayer.this.m_webView.goForward();
            }
        }
    }

    private class WLWebViewClient extends WebViewClient {
        private static final String TAG = "WLWebViewClient";

        public boolean shouldOverrideUrlLoading(WebView view, String url) throws  {
            return false;
        }

        public void onPageStarted(WebView view, String $r2, Bitmap favicon) throws  {
            MCSLogger.log(TAG, "onPageStarted: " + $r2);
        }

        public void onPageFinished(WebView view, String $r2) throws  {
            MCSLogger.log(TAG, "onPageFinished: " + $r2);
            if (WLWebBrowserLayer.this.m_rect != null && !WLWebBrowserLayer.this.m_rect.isEmpty()) {
                WLWebBrowserLayer.this.m_webView.loadUrl("javascript:" + String.format(WLWebBrowserLayer.JS_INJECT_SCALE, new Object[]{Integer.valueOf(WLWebBrowserLayer.this.m_rect.width()), Integer.valueOf(WLWebBrowserLayer.this.m_rect.height())}));
            }
        }

        public void onReceivedError(WebView view, int errorCode, String $r2, String failingUrl) throws  {
            MCSLogger.log(TAG, "onReceivedError: " + $r2);
        }
    }

    public WLWebBrowserLayer(Context $r1) throws  {
        WLWebBrowserLayer wLWebBrowserLayer = this;
        LayoutParams $r2 = new LayoutParams(-1, -1);
        this.m_uiHandler = new Handler(WLServerApp.getAppContext().getMainLooper());
        this.m_scrollView = new RelativeLayout($r1);
        this.m_webView = new WebView($r1);
        WebSettings $r8 = this.m_webView.getSettings();
        $r8.setJavaScriptEnabled(true);
        $r8.setRenderPriority(RenderPriority.HIGH);
        $r8.setLoadWithOverviewMode(true);
        $r8.setUseWideViewPort(true);
        $r8.setDatabaseEnabled(true);
        $r8.setGeolocationDatabasePath($r1.getFilesDir().getPath());
        $r8.setDatabasePath($r1.getFilesDir().getPath());
        $r8.setDomStorageEnabled(true);
        $r8.setGeolocationEnabled(true);
        $r8.setAppCacheEnabled(true);
        $r8.setSavePassword(false);
        this.m_webView.setWebViewClient(createWebViewClient());
        this.m_webView.setWebChromeClient(createWebChromeClient());
        this.m_webView.setOnTouchListener(new C04281());
        this.m_scrollView.addView(this.m_webView, $r2);
    }

    public synchronized void setScreenRect(WLRect $r1) throws  {
        int $i0 = 0;
        synchronized (this) {
            int $i1 = this.m_rect == null ? 0 : this.m_rect.width();
            if (this.m_rect != null) {
                $i0 = this.m_rect.height();
            }
            this.m_rect = $r1;
            if (!($r1.width() == $i1 && $r1.height() == $i0)) {
                this.m_bitmap = Bitmap.createBitmap($r1.width(), $r1.height(), Config.ARGB_8888);
                this.m_canvas = new Canvas(this.m_bitmap);
                this.m_uiHandler.post(new C04292());
            }
        }
    }

    public WLRect getScreenRect() throws  {
        return this.m_rect;
    }

    public void render(ByteBuffer $r1, int $i0, int $i1, int $i2) throws  {
        synchronized (this) {
            if (!(this.m_canvas == null || this.m_rect == null || this.m_frameBuffer == null)) {
                this.m_frameBuffer.position(0);
                $r1.position(0);
                if (this.m_rect.left() == 0 && this.m_rect.top() == 0 && this.m_rect.height() == $i1) {
                    Bitmap $r5 = this.m_bitmap;
                    if ($r5.getRowBytes() == $i2) {
                        $r1.put(this.m_frameBuffer);
                    }
                }
                WLRect $r4 = new WLRect(0, 0, $i0, $i1);
                WLRect $r8 = this.m_rect;
                $r4 = $r4.intersected($r8);
                if (!$r4.isEmpty()) {
                    ByteBuffer $r2 = this.m_frameBuffer;
                    $r8 = this.m_rect;
                    int $i3 = $r8.left();
                    $r8 = this.m_rect;
                    int $i4 = $r8.top();
                    $r8 = this.m_rect;
                    int $i5 = $r8.width();
                    $r8 = this.m_rect;
                    WLImageUtils.copyImage($r1, $i0, $i1, $i2, $r2, $i3, $i4, $i5, $r8.height(), $r4.left(), $r4.top(), $r4.width(), $r4.height());
                }
            }
        }
        if (!this.m_drawing) {
            this.m_drawing = true;
            this.m_uiHandler.post(new C04303());
        }
    }

    public synchronized boolean canRender() throws  {
        boolean $z0;
        $z0 = (this.m_canvas == null || this.m_frameBuffer == null) ? false : true;
        return $z0;
    }

    public WebView getView() throws  {
        return this.m_webView;
    }

    public String getHomePage() throws  {
        return this.m_homePage;
    }

    public void setHomePage(String $r1) throws  {
        this.m_homePage = $r1;
    }

    public WebViewClient createWebViewClient() throws  {
        return new WLWebViewClient();
    }

    public WebChromeClient createWebChromeClient() throws  {
        return new WebChromeClient();
    }

    public boolean onMouseEvent(MotionEvent $r1) throws  {
        if (this.m_webView != null) {
            $r1 = MotionEvent.obtain($r1);
            this.m_uiHandler.post(new Runnable() {
                public void run() throws  {
                    WLWebBrowserLayer.this.m_webView.dispatchTouchEvent($r1);
                    $r1.recycle();
                }
            });
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        if (this.m_webView != null) {
            $r1 = MotionEvent.obtain($r1);
            this.m_uiHandler.post(new Runnable() {
                public void run() throws  {
                    WLWebBrowserLayer.this.m_webView.dispatchTouchEvent($r1);
                    $r1.recycle();
                }
            });
        }
        return true;
    }

    public void goHome() throws  {
        this.m_uiHandler.post(new C04336());
    }

    public void goBack() throws  {
        this.m_uiHandler.post(new C04347());
    }

    public void goForward() throws  {
        this.m_uiHandler.post(new C04358());
    }

    public void gotoUrl(final String $r1) throws  {
        this.m_uiHandler.post(new Runnable() {
            public void run() throws  {
                if (WLWebBrowserLayer.this.m_webView != null) {
                    WLWebBrowserLayer.this.m_webView.stopLoading();
                    WLWebBrowserLayer.this.m_webView.loadUrl($r1);
                }
            }
        });
    }
}
