package com.facebook.internal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.AccessToken;
import com.facebook.C0496R;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.waze.FriendsBarFragment;
import com.waze.strings.DisplayStrings;
import java.util.Locale;

public class WebDialog extends Dialog {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static final int BACKGROUND_GRAY = -872415232;
    static final String CANCEL_URI = "fbconnect://cancel";
    public static final int DEFAULT_THEME = 16973840;
    static final boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
    private static final String DISPLAY_TOUCH = "touch";
    private static final String LOG_TAG = "FacebookSDK.WebDialog";
    private static final int MAX_PADDING_SCREEN_HEIGHT = 1280;
    private static final int MAX_PADDING_SCREEN_WIDTH = 800;
    private static final double MIN_SCALE_FACTOR = 0.5d;
    private static final int NO_PADDING_SCREEN_HEIGHT = 800;
    private static final int NO_PADDING_SCREEN_WIDTH = 480;
    static final String REDIRECT_URI = "fbconnect://success";
    private FrameLayout contentFrameLayout;
    private ImageView crossImageView;
    private String expectedRedirectUrl;
    private boolean isDetached;
    private boolean isPageFinished;
    private boolean listenerCalled;
    private OnCompleteListener onCompleteListener;
    private ProgressDialog spinner;
    private String url;
    private WebView webView;

    public interface OnCompleteListener {
        void onComplete(Bundle bundle, FacebookException facebookException) throws ;
    }

    class C05401 implements OnCancelListener {
        C05401() throws  {
        }

        public void onCancel(DialogInterface dialogInterface) throws  {
            WebDialog.this.cancel();
        }
    }

    class C05412 implements OnClickListener {
        C05412() throws  {
        }

        public void onClick(View v) throws  {
            WebDialog.this.cancel();
        }
    }

    class C05434 implements OnTouchListener {
        C05434() throws  {
        }

        public boolean onTouch(View $r1, MotionEvent event) throws  {
            if (!$r1.hasFocus()) {
                $r1.requestFocus();
            }
            return false;
        }
    }

    public static class Builder {
        private AccessToken accessToken;
        private String action;
        private String applicationId;
        private Context context;
        private OnCompleteListener listener;
        private Bundle parameters;
        private int theme;

        public Builder(Context $r1, String $r2, Bundle $r3) throws  {
            this.theme = WebDialog.DEFAULT_THEME;
            this.accessToken = AccessToken.getCurrentAccessToken();
            if (this.accessToken == null) {
                String $r5 = Utility.getMetadataApplicationId($r1);
                if ($r5 != null) {
                    this.applicationId = $r5;
                } else {
                    throw new FacebookException("Attempted to create a builder without a valid access token or a valid default Application ID.");
                }
            }
            finishInit($r1, $r2, $r3);
        }

        public Builder(Context $r1, String $r4, String $r2, Bundle $r3) throws  {
            this.theme = WebDialog.DEFAULT_THEME;
            if ($r4 == null) {
                $r4 = Utility.getMetadataApplicationId($r1);
            }
            Validate.notNullOrEmpty($r4, "applicationId");
            this.applicationId = $r4;
            finishInit($r1, $r2, $r3);
        }

        public Builder setTheme(int $i0) throws  {
            this.theme = $i0;
            return this;
        }

        public Builder setOnCompleteListener(OnCompleteListener $r1) throws  {
            this.listener = $r1;
            return this;
        }

        public WebDialog build() throws  {
            if (this.accessToken != null) {
                this.parameters.putString("app_id", this.accessToken.getApplicationId());
                this.parameters.putString("access_token", this.accessToken.getToken());
            } else {
                this.parameters.putString("app_id", this.applicationId);
            }
            return new WebDialog(this.context, this.action, this.parameters, this.theme, this.listener);
        }

        public String getApplicationId() throws  {
            return this.applicationId;
        }

        public Context getContext() throws  {
            return this.context;
        }

        public int getTheme() throws  {
            return this.theme;
        }

        public Bundle getParameters() throws  {
            return this.parameters;
        }

        public OnCompleteListener getListener() throws  {
            return this.listener;
        }

        private void finishInit(Context $r1, String $r2, Bundle $r3) throws  {
            this.context = $r1;
            this.action = $r2;
            if ($r3 != null) {
                this.parameters = $r3;
            } else {
                this.parameters = new Bundle();
            }
        }
    }

    private class DialogWebViewClient extends WebViewClient {
        private DialogWebViewClient() throws  {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean shouldOverrideUrlLoading(android.webkit.WebView r18, java.lang.String r19) throws  {
            /*
            r17 = this;
            r1 = new java.lang.StringBuilder;
            r1.<init>();
            r2 = "Redirect URL: ";
            r1 = r1.append(r2);
            r0 = r19;
            r1 = r1.append(r0);
            r3 = r1.toString();
            r2 = "FacebookSDK.WebDialog";
            com.facebook.internal.Utility.logd(r2, r3);
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r3 = r4.expectedRedirectUrl;
            r0 = r19;
            r5 = r0.startsWith(r3);
            if (r5 == 0) goto L_0x00c7;
        L_0x002a:
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r0 = r19;
            r6 = r4.parseResponseUri(r0);
            r2 = "error";
            r3 = r6.getString(r2);
            r19 = r3;
            if (r3 != 0) goto L_0x0044;
        L_0x003e:
            r2 = "error_type";
            r19 = r6.getString(r2);
        L_0x0044:
            r2 = "error_msg";
            r7 = r6.getString(r2);
            r3 = r7;
            if (r7 != 0) goto L_0x0053;
        L_0x004d:
            r2 = "error_message";
            r3 = r6.getString(r2);
        L_0x0053:
            if (r3 != 0) goto L_0x005b;
        L_0x0055:
            r2 = "error_description";
            r3 = r6.getString(r2);
        L_0x005b:
            r2 = "error_code";
            r7 = r6.getString(r2);
            r8 = -1;
            r5 = com.facebook.internal.Utility.isNullOrEmpty(r7);
            if (r5 != 0) goto L_0x006c;
        L_0x0068:
            r8 = java.lang.Integer.parseInt(r7);	 Catch:{ NumberFormatException -> 0x0086 }
        L_0x006c:
            r0 = r19;
            r5 = com.facebook.internal.Utility.isNullOrEmpty(r0);
            if (r5 == 0) goto L_0x0089;
        L_0x0074:
            r5 = com.facebook.internal.Utility.isNullOrEmpty(r3);
            if (r5 == 0) goto L_0x0089;
        L_0x007a:
            r9 = -1;
            if (r8 != r9) goto L_0x0089;
        L_0x007d:
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r4.sendSuccessToListener(r6);
        L_0x0084:
            r9 = 1;
            return r9;
        L_0x0086:
            r10 = move-exception;
            r8 = -1;
            goto L_0x006c;
        L_0x0089:
            if (r19 == 0) goto L_0x00a7;
        L_0x008b:
            r2 = "access_denied";
            r0 = r19;
            r5 = r0.equals(r2);
            if (r5 != 0) goto L_0x009f;
        L_0x0095:
            r2 = "OAuthAccessDeniedException";
            r0 = r19;
            r5 = r0.equals(r2);
            if (r5 == 0) goto L_0x00a7;
        L_0x009f:
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r4.cancel();
            goto L_0x0084;
        L_0x00a7:
            r9 = 4201; // 0x1069 float:5.887E-42 double:2.0756E-320;
            if (r8 != r9) goto L_0x00b3;
        L_0x00ab:
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r4.cancel();
            goto L_0x0084;
        L_0x00b3:
            r11 = new com.facebook.FacebookRequestError;
            r0 = r19;
            r11.<init>(r8, r0, r3);
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r12 = new com.facebook.FacebookServiceException;
            r12.<init>(r11, r3);
            r4.sendErrorToListener(r12);
            goto L_0x0084;
        L_0x00c7:
            r2 = "fbconnect://cancel";
            r0 = r19;
            r5 = r0.startsWith(r2);
            if (r5 == 0) goto L_0x00da;
        L_0x00d1:
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r4.cancel();
            r9 = 1;
            return r9;
        L_0x00da:
            r2 = "touch";
            r0 = r19;
            r5 = r0.contains(r2);
            if (r5 == 0) goto L_0x00e7;
        L_0x00e5:
            r9 = 0;
            return r9;
        L_0x00e7:
            r0 = r17;
            r4 = com.facebook.internal.WebDialog.this;
            r13 = r4.getContext();	 Catch:{ ActivityNotFoundException -> 0x0101 }
            r14 = new android.content.Intent;	 Catch:{ ActivityNotFoundException -> 0x0101 }
            r0 = r19;
            r15 = android.net.Uri.parse(r0);	 Catch:{ ActivityNotFoundException -> 0x0101 }
            r2 = "android.intent.action.VIEW";
            r14.<init>(r2, r15);	 Catch:{ ActivityNotFoundException -> 0x0101 }
            r13.startActivity(r14);	 Catch:{ ActivityNotFoundException -> 0x0101 }
            r9 = 1;
            return r9;
        L_0x0101:
            r16 = move-exception;
            r9 = 0;
            return r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.WebDialog.DialogWebViewClient.shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String):boolean");
        }

        public void onReceivedError(WebView $r1, int $i0, String $r2, String $r3) throws  {
            super.onReceivedError($r1, $i0, $r2, $r3);
            WebDialog.this.sendErrorToListener(new FacebookDialogException($r2, $i0, $r3));
        }

        public void onReceivedSslError(WebView $r1, SslErrorHandler $r2, SslError $r3) throws  {
            super.onReceivedSslError($r1, $r2, $r3);
            $r2.cancel();
            WebDialog.this.sendErrorToListener(new FacebookDialogException(null, -11, null));
        }

        public void onPageStarted(WebView $r1, String $r2, Bitmap $r3) throws  {
            Utility.logd(WebDialog.LOG_TAG, "Webview loading URL: " + $r2);
            super.onPageStarted($r1, $r2, $r3);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.show();
            }
        }

        public void onPageFinished(WebView $r1, String $r2) throws  {
            super.onPageFinished($r1, $r2);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.dismiss();
            }
            WebDialog.this.contentFrameLayout.setBackgroundColor(0);
            WebDialog.this.webView.setVisibility(0);
            WebDialog.this.crossImageView.setVisibility(0);
            WebDialog.this.isPageFinished = true;
        }
    }

    public WebDialog(Context $r1, String $r2) throws  {
        this($r1, $r2, DEFAULT_THEME);
    }

    public WebDialog(Context $r1, String $r2, int $i0) throws  {
        if ($i0 == 0) {
            $i0 = DEFAULT_THEME;
        }
        super($r1, $i0);
        this.expectedRedirectUrl = "fbconnect://success";
        this.listenerCalled = false;
        this.isDetached = false;
        this.isPageFinished = false;
        this.url = $r2;
    }

    public WebDialog(Context $r1, String $r2, Bundle $r4, int $i0, OnCompleteListener $r3) throws  {
        if ($i0 == 0) {
            $i0 = DEFAULT_THEME;
        }
        super($r1, $i0);
        this.expectedRedirectUrl = "fbconnect://success";
        this.listenerCalled = false;
        this.isDetached = false;
        this.isPageFinished = false;
        if ($r4 == null) {
            $r4 = new Bundle();
        }
        $r4.putString("redirect_uri", "fbconnect://success");
        $r4.putString(ServerProtocol.DIALOG_PARAM_DISPLAY, "touch");
        $r4.putString(ServerProtocol.DIALOG_PARAM_SDK_VERSION, String.format(Locale.ROOT, "android-%s", new Object[]{FacebookSdk.getSdkVersion()}));
        this.url = Utility.buildUri(ServerProtocol.getDialogAuthority(), ServerProtocol.getAPIVersion() + "/" + ServerProtocol.DIALOG_PATH + $r2, $r4).toString();
        this.onCompleteListener = $r3;
    }

    public void setOnCompleteListener(OnCompleteListener $r1) throws  {
        this.onCompleteListener = $r1;
    }

    public OnCompleteListener getOnCompleteListener() throws  {
        return this.onCompleteListener;
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        if ($i0 == 4) {
            cancel();
        }
        return super.onKeyDown($i0, $r1);
    }

    public void dismiss() throws  {
        if (this.webView != null) {
            this.webView.stopLoading();
        }
        if (!(this.isDetached || this.spinner == null || !this.spinner.isShowing())) {
            this.spinner.dismiss();
        }
        super.dismiss();
    }

    protected void onStart() throws  {
        super.onStart();
        resize();
    }

    public void onDetachedFromWindow() throws  {
        this.isDetached = true;
        super.onDetachedFromWindow();
    }

    public void onAttachedToWindow() throws  {
        this.isDetached = false;
        super.onAttachedToWindow();
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.spinner = new ProgressDialog(getContext());
        this.spinner.requestWindowFeature(1);
        this.spinner.setMessage(getContext().getString(C0496R.string.com_facebook_loading));
        this.spinner.setOnCancelListener(new C05401());
        requestWindowFeature(1);
        this.contentFrameLayout = new FrameLayout(getContext());
        resize();
        getWindow().setGravity(17);
        getWindow().setSoftInputMode(16);
        createCrossImage();
        setUpWebView((this.crossImageView.getDrawable().getIntrinsicWidth() / 2) + 1);
        this.contentFrameLayout.addView(this.crossImageView, new LayoutParams(-2, -2));
        setContentView(this.contentFrameLayout);
    }

    protected void setExpectedRedirectUrl(String $r1) throws  {
        this.expectedRedirectUrl = $r1;
    }

    protected Bundle parseResponseUri(String $r1) throws  {
        Uri $r2 = Uri.parse($r1);
        Bundle $r3 = Utility.parseUrlQueryString($r2.getQuery());
        $r3.putAll(Utility.parseUrlQueryString($r2.getFragment()));
        return $r3;
    }

    protected boolean isListenerCalled() throws  {
        return this.listenerCalled;
    }

    protected boolean isPageFinished() throws  {
        return this.isPageFinished;
    }

    protected WebView getWebView() throws  {
        return this.webView;
    }

    public void resize() throws  {
        Display $r5 = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics $r1 = new DisplayMetrics();
        $r5.getMetrics($r1);
        getWindow().setLayout(Math.min(getScaledSize($r1.widthPixels < $r1.heightPixels ? $r1.widthPixels : $r1.heightPixels, $r1.density, 480, DisplayStrings.DS_EVENT), $r1.widthPixels), Math.min(getScaledSize($r1.widthPixels < $r1.heightPixels ? $r1.heightPixels : $r1.widthPixels, $r1.density, DisplayStrings.DS_EVENT, 1280), $r1.heightPixels));
    }

    private int getScaledSize(int $i0, float $f0, int $i1, int $i2) throws  {
        double $d0;
        int $i3 = (int) (((float) $i0) / $f0);
        if ($i3 <= $i1) {
            $d0 = FriendsBarFragment.END_LOCATION_POSITION;
        } else if ($i3 >= $i2) {
            $d0 = MIN_SCALE_FACTOR;
        } else {
            $d0 = MIN_SCALE_FACTOR + ((((double) ($i2 - $i3)) / ((double) ($i2 - $i1))) * MIN_SCALE_FACTOR);
        }
        return (int) (((double) $i0) * $d0);
    }

    protected void sendSuccessToListener(Bundle $r1) throws  {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.listenerCalled = true;
            this.onCompleteListener.onComplete($r1, null);
            dismiss();
        }
    }

    protected void sendErrorToListener(Throwable $r1) throws  {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            FacebookException $r3;
            this.listenerCalled = true;
            if ($r1 instanceof FacebookException) {
                $r3 = (FacebookException) $r1;
            } else {
                $r3 = new FacebookException($r1);
            }
            this.onCompleteListener.onComplete(null, $r3);
            dismiss();
        }
    }

    public void cancel() throws  {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            sendErrorToListener(new FacebookOperationCanceledException());
        }
    }

    private void createCrossImage() throws  {
        this.crossImageView = new ImageView(getContext());
        this.crossImageView.setOnClickListener(new C05412());
        this.crossImageView.setImageDrawable(getContext().getResources().getDrawable(C0496R.drawable.com_facebook_close));
        this.crossImageView.setVisibility(4);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setUpWebView(int $i0) throws  {
        LinearLayout $r1 = new LinearLayout(getContext());
        this.webView = new WebView(getContext().getApplicationContext()) {
            public void onWindowFocusChanged(boolean $z0) throws  {
                try {
                    super.onWindowFocusChanged($z0);
                } catch (NullPointerException e) {
                }
            }
        };
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setWebViewClient(new DialogWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(this.url);
        this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.webView.setVisibility(4);
        this.webView.getSettings().setSavePassword(false);
        this.webView.getSettings().setSaveFormData(false);
        this.webView.setFocusable(true);
        this.webView.setFocusableInTouchMode(true);
        this.webView.setOnTouchListener(new C05434());
        $r1.setPadding($i0, $i0, $i0, $i0);
        $r1.addView(this.webView);
        $r1.setBackgroundColor(BACKGROUND_GRAY);
        this.contentFrameLayout.addView($r1);
    }
}
