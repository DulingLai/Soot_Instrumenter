package com.spotify.sdk.android.authentication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import com.spotify.sdk.android.authentication.AuthenticationHandler.OnCompleteListener;

class LoginDialog extends Dialog {
    private static final int DEFAULT_THEME = 16973840;
    private static final int MAX_HEIGHT_DP = 640;
    private static final int MAX_WIDTH_DP = 400;
    private static final String TAG = LoginDialog.class.getName();
    private static final String WEBVIEW_URIS = "^(.+\\.facebook\\.com)|(accounts\\.spotify\\.com)$";
    private boolean mAttached;
    private OnCompleteListener mListener;
    private ProgressDialog mProgressDialog;
    private boolean mResultDelivered;
    private final Uri mUri;

    class C10841 implements OnCancelListener {
        C10841() throws  {
        }

        public void onCancel(DialogInterface dialogInterface) throws  {
            LoginDialog.this.dismiss();
        }
    }

    public LoginDialog(Activity $r1, AuthenticationRequest $r2) throws  {
        super($r1, 16973840);
        this.mUri = $r2.toUri();
    }

    public LoginDialog(Activity $r1, int $i0, AuthenticationRequest $r2) throws  {
        super($r1, $i0);
        this.mUri = $r2.toUri();
    }

    public void setOnCompleteListener(OnCompleteListener $r1) throws  {
        this.mListener = $r1;
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mResultDelivered = false;
        this.mProgressDialog = new ProgressDialog(getContext());
        this.mProgressDialog.setMessage(getContext().getString(C1086R.string.com_spotify_sdk_login_progress));
        this.mProgressDialog.requestWindowFeature(1);
        this.mProgressDialog.setOnCancelListener(new C10841());
        requestWindowFeature(1);
        getWindow().setSoftInputMode(16);
        getWindow().setBackgroundDrawableResource(17301673);
        setContentView(C1086R.layout.com_spotify_sdk_login_dialog);
        setLayoutSize();
        createWebView(this.mUri);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void createWebView(Uri $r1) throws  {
        if (!internetPermissionGranted()) {
            Log.e(TAG, "Missing INTERNET permission");
        }
        final WebView $r4 = (WebView) findViewById(C1086R.id.com_spotify_sdk_login_webview);
        final LinearLayout $r5 = (LinearLayout) findViewById(C1086R.id.com_spotify_sdk_login_webview_container);
        final String $r2 = $r1.getQueryParameter("redirect_uri");
        WebSettings $r6 = $r4.getSettings();
        $r6.setJavaScriptEnabled(true);
        $r6.setSaveFormData(false);
        $r6.setSavePassword(false);
        $r4.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView $r1, String $r2) throws  {
                if (LoginDialog.this.mAttached) {
                    LoginDialog.this.mProgressDialog.dismiss();
                }
                $r4.setVisibility(0);
                $r5.setVisibility(0);
                super.onPageFinished($r1, $r2);
            }

            public void onPageStarted(WebView $r1, String $r2, Bitmap $r3) throws  {
                super.onPageStarted($r1, $r2, $r3);
                if (LoginDialog.this.mAttached) {
                    LoginDialog.this.mProgressDialog.show();
                }
            }

            public boolean shouldOverrideUrlLoading(WebView view, String $r2) throws  {
                Uri $r4 = Uri.parse($r2);
                if ($r2.startsWith($r2)) {
                    LoginDialog.this.sendComplete($r4);
                    return true;
                } else if ($r4.getAuthority().matches(LoginDialog.WEBVIEW_URIS)) {
                    return false;
                } else {
                    LoginDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", $r4));
                    return true;
                }
            }

            public void onReceivedError(WebView $r1, int $i0, String $r2, String $r3) throws  {
                super.onReceivedError($r1, $i0, $r2, $r3);
                LoginDialog.this.sendError(new Error(String.format("%s, code: %s, failing url: %s", new Object[]{$r2, Integer.valueOf($i0), $r3})));
            }
        });
        $r4.loadUrl($r1.toString());
    }

    private void sendComplete(Uri $r1) throws  {
        this.mResultDelivered = true;
        if (this.mListener != null) {
            this.mListener.onComplete(AuthenticationResponse.fromUri($r1));
        }
        close();
    }

    private void sendError(Throwable $r1) throws  {
        this.mResultDelivered = true;
        if (this.mListener != null) {
            this.mListener.onError($r1);
        }
        close();
    }

    public void onAttachedToWindow() throws  {
        this.mAttached = true;
        super.onAttachedToWindow();
    }

    public void onDetachedFromWindow() throws  {
        this.mAttached = false;
        super.onDetachedFromWindow();
    }

    protected void onStop() throws  {
        if (!(this.mResultDelivered || this.mListener == null)) {
            this.mListener.onCancel();
        }
        this.mResultDelivered = true;
        this.mProgressDialog.dismiss();
        super.onStop();
    }

    public void close() throws  {
        if (this.mAttached) {
            dismiss();
        }
    }

    private boolean internetPermissionGranted() throws  {
        return getContext().getPackageManager().checkPermission("android.permission.INTERNET", getContext().getPackageName()) == 0;
    }

    private void setLayoutSize() throws  {
        Display $r5 = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics $r1 = new DisplayMetrics();
        $r5.getMetrics($r1);
        int $i0 = -1;
        int $i1 = -1;
        if (((float) $r1.widthPixels) / $r1.density > 400.0f) {
            $i0 = (int) ($r1.density * 400.0f);
        }
        if (((float) $r1.heightPixels) / $r1.density > 640.0f) {
            $i1 = (int) ($r1.density * 640.0f);
        }
        ((LinearLayout) findViewById(C1086R.id.com_spotify_sdk_login_webview_container)).setLayoutParams(new LayoutParams($i0, $i1, 17));
    }
}
