package com.abaltatech.mcp.weblink.sdk.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WLWebView extends WebView {
    private static final String JS_INJECT_OUTLINE = "if(!document.getElementById('weblinkSelectStyleInsert')) {var div = document.createElement( 'div' ); div.innerHTML = '<p>x</p><style id=weblinkSelectStyleInsert>' + '*{}:focus{outline: solid red 5px; outline-offset:-5px;}' + '</style>'; document.body.appendChild(div.childNodes[1]); }";
    private static String ms_jsInjectEventList = null;
    private static String ms_jsInjectWebLink = null;
    private boolean m_injectNavigation = false;
    private boolean m_injectOutline = true;
    private WLWebViewClient m_innerWebViewClient = new WLWebViewClient();
    private WebViewClient m_realWebViewClient = null;

    private class WLWebViewClient extends WebViewClient {
        private WLWebViewClient() throws  {
        }

        public void doUpdateVisitedHistory(WebView $r1, String $r2, boolean $z0) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.doUpdateVisitedHistory($r1, $r2, $z0);
            } else {
                super.doUpdateVisitedHistory($r1, $r2, $z0);
            }
        }

        public void onFormResubmission(WebView $r1, Message $r2, Message $r3) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onFormResubmission($r1, $r2, $r3);
            } else {
                super.onFormResubmission($r1, $r2, $r3);
            }
        }

        public void onLoadResource(WebView $r1, String $r2) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onLoadResource($r1, $r2);
            } else {
                super.onLoadResource($r1, $r2);
            }
        }

        public void onPageFinished(WebView $r1, String $r2) throws  {
            if (WLWebView.this.m_injectNavigation) {
                $r1.loadUrl("javascript:" + WLWebView.ms_jsInjectEventList);
                $r1.loadUrl("javascript:" + WLWebView.ms_jsInjectWebLink);
            }
            if (WLWebView.this.m_injectOutline) {
                $r1.loadUrl("javascript:if(!document.getElementById('weblinkSelectStyleInsert')) {var div = document.createElement( 'div' ); div.innerHTML = '<p>x</p><style id=weblinkSelectStyleInsert>' + '*{}:focus{outline: solid red 5px; outline-offset:-5px;}' + '</style>'; document.body.appendChild(div.childNodes[1]); }");
            }
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onPageFinished($r1, $r2);
            } else {
                super.onPageFinished($r1, $r2);
            }
        }

        public void onPageStarted(WebView $r1, String $r2, Bitmap $r3) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onPageStarted($r1, $r2, $r3);
            } else {
                super.onPageStarted($r1, $r2, $r3);
            }
        }

        public void onReceivedError(WebView $r1, int $i0, String $r2, String $r3) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onReceivedError($r1, $i0, $r2, $r3);
            } else {
                super.onReceivedError($r1, $i0, $r2, $r3);
            }
        }

        public void onReceivedHttpAuthRequest(WebView $r1, HttpAuthHandler $r2, String $r3, String $r4) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onReceivedHttpAuthRequest($r1, $r2, $r3, $r4);
            } else {
                super.onReceivedHttpAuthRequest($r1, $r2, $r3, $r4);
            }
        }

        public void onReceivedLoginRequest(WebView $r1, String $r2, String $r3, String $r4) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onReceivedLoginRequest($r1, $r2, $r3, $r4);
            } else {
                super.onReceivedLoginRequest($r1, $r2, $r3, $r4);
            }
        }

        public void onReceivedSslError(WebView $r1, SslErrorHandler $r2, SslError $r3) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onReceivedSslError($r1, $r2, $r3);
            } else {
                super.onReceivedSslError($r1, $r2, $r3);
            }
        }

        public void onScaleChanged(WebView $r1, float $f0, float $f1) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onScaleChanged($r1, $f0, $f1);
            } else {
                super.onScaleChanged($r1, $f0, $f1);
            }
        }

        @Deprecated
        public void onTooManyRedirects(@Deprecated WebView $r1, @Deprecated Message $r2, @Deprecated Message $r3) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onTooManyRedirects($r1, $r2, $r3);
            } else {
                super.onTooManyRedirects($r1, $r2, $r3);
            }
        }

        public void onUnhandledKeyEvent(WebView $r1, KeyEvent $r2) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                WLWebView.this.m_realWebViewClient.onUnhandledKeyEvent($r1, $r2);
            } else {
                super.onUnhandledKeyEvent($r1, $r2);
            }
        }

        public WebResourceResponse shouldInterceptRequest(WebView $r1, String $r2) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                return WLWebView.this.m_realWebViewClient.shouldInterceptRequest($r1, $r2);
            }
            return super.shouldInterceptRequest($r1, $r2);
        }

        public boolean shouldOverrideKeyEvent(WebView $r1, KeyEvent $r2) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                return WLWebView.this.m_realWebViewClient.shouldOverrideKeyEvent($r1, $r2);
            }
            return super.shouldOverrideKeyEvent($r1, $r2);
        }

        public boolean shouldOverrideUrlLoading(WebView $r1, String $r2) throws  {
            if (WLWebView.this.m_realWebViewClient != null) {
                return WLWebView.this.m_realWebViewClient.shouldOverrideUrlLoading($r1, $r2);
            }
            return super.shouldOverrideUrlLoading($r1, $r2);
        }
    }

    public boolean shouldInjectOutline() throws  {
        return this.m_injectOutline;
    }

    public void setShouldInjectOutline(boolean $z0) throws  {
        this.m_injectOutline = $z0;
    }

    public boolean shouldInjectNavigation() throws  {
        return this.m_injectNavigation;
    }

    public void setShouldInjectNavigation(boolean $z0) throws  {
        this.m_injectNavigation = $z0;
    }

    public void setWebViewClient(WebViewClient $r1) throws  {
        this.m_realWebViewClient = $r1;
    }

    public WLWebView(Context $r1) throws  {
        super($r1);
        Init($r1);
    }

    public WLWebView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        Init($r1);
    }

    public WLWebView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        Init($r1);
    }

    private void Init(Context $r1) throws  {
        super.setWebViewClient(this.m_innerWebViewClient);
        if (ms_jsInjectEventList == null) {
            ms_jsInjectEventList = WebViewJSInjections.getResource("eventlist_inject", $r1);
        }
        if (ms_jsInjectWebLink == null) {
            ms_jsInjectWebLink = WebViewJSInjections.getResource("weblink_inject", $r1);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        int $i0 = $r1.getKeyCode();
        if (!this.m_injectNavigation || ($i0 != 23 && $i0 != 21 && $i0 != 19 && $i0 != 22 && $i0 != 20)) {
            return super.dispatchKeyEvent($r1);
        }
        byte $b1 = (byte) 0;
        switch ($i0) {
            case 19:
                $b1 = (byte) 19;
                break;
            case 20:
                $b1 = (byte) 21;
                break;
            case 21:
                $b1 = (byte) 18;
                break;
            case 22:
                $b1 = (byte) 20;
                break;
            case 23:
                $b1 = (byte) 4;
                break;
            default:
                break;
        }
        if ($r1.getAction() == 0) {
            loadUrl("javascript:WebLink.keyboardEvent('keydown'," + $b1 + ");");
            return true;
        } else if ($r1.getAction() != 1) {
            return true;
        } else {
            loadUrl("javascript:WebLink.keyboardEvent('keyup'," + $b1 + ");");
            loadUrl("javascript:WebLink.keyboardEvent('keypress'," + $b1 + ");");
            return true;
        }
    }
}
