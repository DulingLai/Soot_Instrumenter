package com.facebook.login.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.C0496R;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ToolTipPopup.Style;
import dalvik.annotation.Signature;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LoginButton extends FacebookButtonBase {
    private static final String TAG = LoginButton.class.getName();
    private AccessTokenTracker accessTokenTracker;
    private boolean confirmLogout;
    private String loginLogoutEventName = AnalyticsEvents.EVENT_LOGIN_VIEW_USAGE;
    private LoginManager loginManager;
    private String loginText;
    private String logoutText;
    private LoginButtonProperties properties = new LoginButtonProperties();
    private boolean toolTipChecked;
    private long toolTipDisplayTime = ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME;
    private ToolTipMode toolTipMode;
    private ToolTipPopup toolTipPopup;
    private Style toolTipStyle = Style.BLUE;

    class C05612 extends AccessTokenTracker {
        C05612() throws  {
        }

        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) throws  {
            LoginButton.this.setButtonText();
        }
    }

    static class LoginButtonProperties {
        private LoginAuthorizationType authorizationType = null;
        private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
        private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
        private List<String> permissions = Collections.emptyList();

        LoginButtonProperties() throws  {
        }

        public void setDefaultAudience(DefaultAudience $r1) throws  {
            this.defaultAudience = $r1;
        }

        public DefaultAudience getDefaultAudience() throws  {
            return this.defaultAudience;
        }

        public void setReadPermissions(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r1) throws  {
            if (LoginAuthorizationType.PUBLISH.equals(this.authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setReadPermissions after setPublishPermissions has been called.");
            }
            this.permissions = $r1;
            this.authorizationType = LoginAuthorizationType.READ;
        }

        public void setPublishPermissions(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r1) throws  {
            if (LoginAuthorizationType.READ.equals(this.authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setPublishPermissions after setReadPermissions has been called.");
            } else if (Utility.isNullOrEmpty((Collection) $r1)) {
                throw new IllegalArgumentException("Permissions for publish actions cannot be null or empty.");
            } else {
                this.permissions = $r1;
                this.authorizationType = LoginAuthorizationType.PUBLISH;
            }
        }

        List<String> getPermissions() throws  {
            return this.permissions;
        }

        public void clearPermissions() throws  {
            this.permissions = null;
            this.authorizationType = null;
        }

        public void setLoginBehavior(LoginBehavior $r1) throws  {
            this.loginBehavior = $r1;
        }

        public LoginBehavior getLoginBehavior() throws  {
            return this.loginBehavior;
        }
    }

    private class LoginClickListener implements OnClickListener {

        class C05631 implements DialogInterface.OnClickListener {
            C05631() throws  {
            }

            public void onClick(DialogInterface dialog, int which) throws  {
                LoginButton.this.getLoginManager().logOut();
            }
        }

        public void onClick(android.view.View r35) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:35:0x01d5 in {8, 9, 12, 14, 15, 16, 18, 20, 30, 33, 36} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r34 = this;
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r0 = r35;
            r3.callExternalOnClickListener(r0);
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r4 = r3.getContext();
            r5 = com.facebook.AccessToken.getCurrentAccessToken();
            if (r5 == 0) goto L_0x00f1;
        L_0x0017:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r6 = r3.confirmLogout;
            if (r6 == 0) goto L_0x00e3;
        L_0x0021:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r7 = r3.getResources();
            r8 = com.facebook.C0496R.string.com_facebook_loginview_log_out_action;
            r9 = r7.getString(r8);
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r7 = r3.getResources();
            r8 = com.facebook.C0496R.string.com_facebook_loginview_cancel_action;
            r10 = r7.getString(r8);
            r11 = com.facebook.Profile.getCurrentProfile();
            if (r11 == 0) goto L_0x00d4;
        L_0x0043:
            r12 = r11.getName();
            if (r12 == 0) goto L_0x00d4;
        L_0x0049:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r7 = r3.getResources();
            r8 = com.facebook.C0496R.string.com_facebook_loginview_logged_in_as;
            r12 = r7.getString(r8);
            r14 = 1;
            r13 = new java.lang.Object[r14];
            r15 = r11.getName();
            r14 = 0;
            r13[r14] = r15;
            r12 = java.lang.String.format(r12, r13);
        L_0x0065:
            r16 = new android.app.AlertDialog$Builder;
            r0 = r16;
            r0.<init>(r4);
            r0 = r16;
            r17 = r0.setMessage(r12);
            r14 = 1;
            r0 = r17;
            r17 = r0.setCancelable(r14);
            r18 = new com.facebook.login.widget.LoginButton$LoginClickListener$1;
            r0 = r18;
            r1 = r34;
            r0.<init>();
            r0 = r17;
            r1 = r18;
            r17 = r0.setPositiveButton(r9, r1);
            r19 = 0;
            r0 = r17;
            r1 = r19;
            r0.setNegativeButton(r10, r1);
            r0 = r16;
            r20 = r0.create();
            r0 = r20;
            r0.show();
        L_0x009e:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r4 = r3.getContext();
            r21 = com.facebook.appevents.AppEventsLogger.newLogger(r4);
            r22 = new android.os.Bundle;
            r0 = r22;
            r0.<init>();
            if (r5 == 0) goto L_0x01d9;
        L_0x00b3:
            r23 = 0;
        L_0x00b5:
            r24 = "logging_in";
            r0 = r22;
            r1 = r24;
            r2 = r23;
            r0.putInt(r1, r2);
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r9 = r3.loginLogoutEventName;
            r19 = 0;
            r0 = r21;
            r1 = r19;
            r2 = r22;
            r0.logSdkEvent(r9, r1, r2);
            return;
        L_0x00d4:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r7 = r3.getResources();
            r8 = com.facebook.C0496R.string.com_facebook_loginview_logged_in_using_facebook;
            r12 = r7.getString(r8);
            goto L_0x0065;
        L_0x00e3:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r25 = r3.getLoginManager();
            r0 = r25;
            r0.logOut();
            goto L_0x009e;
        L_0x00f1:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r25 = r3.getLoginManager();
            goto L_0x00fd;
        L_0x00fa:
            goto L_0x009e;
        L_0x00fd:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r26 = r3.getDefaultAudience();
            r0 = r25;
            r1 = r26;
            r0.setDefaultAudience(r1);
            goto L_0x0110;
        L_0x010d:
            goto L_0x009e;
        L_0x0110:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r27 = r3.getLoginBehavior();
            r0 = r25;
            r1 = r27;
            r0.setLoginBehavior(r1);
            r28 = com.facebook.internal.LoginAuthorizationType.PUBLISH;
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r29 = r3.properties;
            r0 = r29;
            r30 = r0.authorizationType;
            r0 = r28;
            r1 = r30;
            r6 = r0.equals(r1);
            if (r6 == 0) goto L_0x0183;
        L_0x0139:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r31 = r3.getFragment();
            if (r31 == 0) goto L_0x0163;
        L_0x0143:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r31 = r3.getFragment();
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r29 = r3.properties;
            r0 = r29;
            r32 = r0.permissions;
            r0 = r25;
            r1 = r31;
            r2 = r32;
            r0.logInWithPublishPermissions(r1, r2);
            goto L_0x00fa;
        L_0x0163:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r33 = r3.getActivity();
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r29 = r3.properties;
            r0 = r29;
            r32 = r0.permissions;
            r0 = r25;
            r1 = r33;
            r2 = r32;
            r0.logInWithPublishPermissions(r1, r2);
            goto L_0x010d;
        L_0x0183:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r31 = r3.getFragment();
            if (r31 == 0) goto L_0x01b1;
        L_0x018d:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r31 = r3.getFragment();
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r29 = r3.properties;
            r0 = r29;
            r32 = r0.permissions;
            goto L_0x01a7;
        L_0x01a4:
            goto L_0x009e;
        L_0x01a7:
            r0 = r25;
            r1 = r31;
            r2 = r32;
            r0.logInWithReadPermissions(r1, r2);
            goto L_0x01a4;
        L_0x01b1:
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r33 = r3.getActivity();
            r0 = r34;
            r3 = com.facebook.login.widget.LoginButton.this;
            r29 = r3.properties;
            r0 = r29;
            r32 = r0.permissions;
            goto L_0x01cb;
        L_0x01c8:
            goto L_0x009e;
        L_0x01cb:
            r0 = r25;
            r1 = r33;
            r2 = r32;
            r0.logInWithReadPermissions(r1, r2);
            goto L_0x01c8;
            goto L_0x01d9;
        L_0x01d6:
            goto L_0x00b5;
        L_0x01d9:
            r23 = 1;
            goto L_0x01d6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.login.widget.LoginButton.LoginClickListener.onClick(android.view.View):void");
        }

        private LoginClickListener() throws  {
        }
    }

    public enum ToolTipMode {
        AUTOMATIC(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, 0),
        DISPLAY_ALWAYS("display_always", 1),
        NEVER_DISPLAY("never_display", 2);
        
        public static ToolTipMode DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = AUTOMATIC;
        }

        public static ToolTipMode fromInt(int $i0) throws  {
            for (ToolTipMode $r1 : values()) {
                if ($r1.getValue() == $i0) {
                    return $r1;
                }
            }
            return null;
        }

        private ToolTipMode(@Signature({"(", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "I)V"}) int $i1) throws  {
            this.stringValue = $r2;
            this.intValue = $i1;
        }

        public String toString() throws  {
            return this.stringValue;
        }

        public int getValue() throws  {
            return this.intValue;
        }
    }

    public LoginButton(Context $r1) throws  {
        super($r1, null, 0, 0, AnalyticsEvents.EVENT_LOGIN_BUTTON_CREATE, AnalyticsEvents.EVENT_LOGIN_BUTTON_DID_TAP);
    }

    public LoginButton(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2, 0, 0, AnalyticsEvents.EVENT_LOGIN_BUTTON_CREATE, AnalyticsEvents.EVENT_LOGIN_BUTTON_DID_TAP);
    }

    public LoginButton(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0, 0, AnalyticsEvents.EVENT_LOGIN_BUTTON_CREATE, AnalyticsEvents.EVENT_LOGIN_BUTTON_DID_TAP);
    }

    public void setDefaultAudience(DefaultAudience $r1) throws  {
        this.properties.setDefaultAudience($r1);
    }

    public DefaultAudience getDefaultAudience() throws  {
        return this.properties.getDefaultAudience();
    }

    public void setReadPermissions(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r1) throws  {
        this.properties.setReadPermissions($r1);
    }

    public void setReadPermissions(String... $r1) throws  {
        this.properties.setReadPermissions(Arrays.asList($r1));
    }

    public void setPublishPermissions(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r1) throws  {
        this.properties.setPublishPermissions($r1);
    }

    public void setPublishPermissions(String... $r1) throws  {
        this.properties.setPublishPermissions(Arrays.asList($r1));
    }

    public void clearPermissions() throws  {
        this.properties.clearPermissions();
    }

    public void setLoginBehavior(LoginBehavior $r1) throws  {
        this.properties.setLoginBehavior($r1);
    }

    public LoginBehavior getLoginBehavior() throws  {
        return this.properties.getLoginBehavior();
    }

    public void setToolTipStyle(Style $r1) throws  {
        this.toolTipStyle = $r1;
    }

    public void setToolTipMode(ToolTipMode $r1) throws  {
        this.toolTipMode = $r1;
    }

    public ToolTipMode getToolTipMode() throws  {
        return this.toolTipMode;
    }

    public void setToolTipDisplayTime(long $l0) throws  {
        this.toolTipDisplayTime = $l0;
    }

    public long getToolTipDisplayTime() throws  {
        return this.toolTipDisplayTime;
    }

    public void dismissToolTip() throws  {
        if (this.toolTipPopup != null) {
            this.toolTipPopup.dismiss();
            this.toolTipPopup = null;
        }
    }

    public void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) CallbackManager $r1, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/login/LoginResult;", ">;)V"}) FacebookCallback<LoginResult> $r2) throws  {
        getLoginManager().registerCallback($r1, $r2);
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        if (this.accessTokenTracker != null && !this.accessTokenTracker.isTracking()) {
            this.accessTokenTracker.startTracking();
            setButtonText();
        }
    }

    protected void onDraw(Canvas $r1) throws  {
        super.onDraw($r1);
        if (!this.toolTipChecked && !isInEditMode()) {
            this.toolTipChecked = true;
            checkToolTipSettings();
        }
    }

    private void showToolTipPerSettings(FetchedAppSettings $r1) throws  {
        if ($r1 != null && $r1.getNuxEnabled() && getVisibility() == 0) {
            displayToolTip($r1.getNuxContent());
        }
    }

    private void displayToolTip(String $r1) throws  {
        this.toolTipPopup = new ToolTipPopup($r1, this);
        this.toolTipPopup.setStyle(this.toolTipStyle);
        this.toolTipPopup.setNuxDisplayTime(this.toolTipDisplayTime);
        this.toolTipPopup.show();
    }

    private void checkToolTipSettings() throws  {
        switch (this.toolTipMode) {
            case AUTOMATIC:
                final String $r4 = Utility.getMetadataApplicationId(getContext());
                FacebookSdk.getExecutor().execute(new Runnable() {
                    public void run() throws  {
                        final FetchedAppSettings $r2 = Utility.queryAppSettings($r4, false);
                        LoginButton.this.getActivity().runOnUiThread(new Runnable() {
                            public void run() throws  {
                                LoginButton.this.showToolTipPerSettings($r2);
                            }
                        });
                    }
                });
                return;
            case DISPLAY_ALWAYS:
                displayToolTip(getResources().getString(C0496R.string.com_facebook_tooltip_default));
                return;
            default:
                return;
        }
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        setButtonText();
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.accessTokenTracker != null) {
            this.accessTokenTracker.stopTracking();
        }
        dismissToolTip();
    }

    protected void onVisibilityChanged(View $r1, int $i0) throws  {
        super.onVisibilityChanged($r1, $i0);
        if ($i0 != 0) {
            dismissToolTip();
        }
    }

    List<String> getPermissions() throws  {
        return this.properties.getPermissions();
    }

    void setProperties(LoginButtonProperties $r1) throws  {
        this.properties = $r1;
    }

    protected void configureButton(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super.configureButton($r1, $r2, $i0, $i1);
        setInternalOnClickListener(new LoginClickListener());
        parseLoginButtonAttributes($r1, $r2, $i0, $i1);
        if (isInEditMode()) {
            setBackgroundColor(getResources().getColor(C0496R.color.com_facebook_blue));
            this.loginText = "Log in with Facebook";
        } else {
            this.accessTokenTracker = new C05612();
        }
        setButtonText();
    }

    protected int getDefaultStyleResource() throws  {
        return C0496R.style.com_facebook_loginview_default_style;
    }

    private void parseLoginButtonAttributes(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        this.toolTipMode = ToolTipMode.DEFAULT;
        TypedArray $r6 = $r1.getTheme().obtainStyledAttributes($r2, C0496R.styleable.com_facebook_login_view, $i0, $i1);
        try {
            this.confirmLogout = $r6.getBoolean(C0496R.styleable.com_facebook_login_view_com_facebook_confirm_logout, true);
            this.loginText = $r6.getString(C0496R.styleable.com_facebook_login_view_com_facebook_login_text);
            this.logoutText = $r6.getString(C0496R.styleable.com_facebook_login_view_com_facebook_logout_text);
            this.toolTipMode = ToolTipMode.fromInt($r6.getInt(C0496R.styleable.com_facebook_login_view_com_facebook_tooltip_mode, ToolTipMode.DEFAULT.getValue()));
        } finally {
            $r6.recycle();
        }
    }

    protected void onMeasure(int $i0, int heightMeasureSpec) throws  {
        int $i2;
        FontMetrics $r2 = getPaint().getFontMetrics();
        heightMeasureSpec = (getCompoundPaddingTop() + ((int) Math.ceil((double) (Math.abs($r2.top) + Math.abs($r2.bottom))))) + getCompoundPaddingBottom();
        Resources $r3 = getResources();
        String $r4 = this.loginText;
        if ($r4 == null) {
            String $r5 = $r3.getString(C0496R.string.com_facebook_loginview_log_in_button_long);
            $r4 = $r5;
            $i2 = measureButtonWidth($r5);
            if (resolveSize($i2, $i0) < $i2) {
                $r4 = $r3.getString(C0496R.string.com_facebook_loginview_log_in_button);
            }
        }
        $i2 = measureButtonWidth($r4);
        $r4 = this.logoutText;
        if ($r4 == null) {
            $r4 = $r3.getString(C0496R.string.com_facebook_loginview_log_out_button);
        }
        setMeasuredDimension(resolveSize(Math.max($i2, measureButtonWidth($r4)), $i0), heightMeasureSpec);
    }

    private int measureButtonWidth(String $r1) throws  {
        return ((getCompoundPaddingLeft() + getCompoundDrawablePadding()) + measureTextWidth($r1)) + getCompoundPaddingRight();
    }

    private void setButtonText() throws  {
        Resources $r1 = getResources();
        String $r3;
        if (!isInEditMode() && AccessToken.getCurrentAccessToken() != null) {
            if (this.logoutText != null) {
                $r3 = this.logoutText;
            } else {
                $r3 = $r1.getString(C0496R.string.com_facebook_loginview_log_out_button);
            }
            setText($r3);
        } else if (this.loginText != null) {
            setText(this.loginText);
        } else {
            $r3 = $r1.getString(C0496R.string.com_facebook_loginview_log_in_button_long);
            String $r4 = $r3;
            int $i0 = getWidth();
            if ($i0 != 0 && measureButtonWidth($r3) > $i0) {
                $r4 = $r1.getString(C0496R.string.com_facebook_loginview_log_in_button);
            }
            setText($r4);
        }
    }

    protected int getDefaultRequestCode() throws  {
        return RequestCodeOffset.Login.toRequestCode();
    }

    LoginManager getLoginManager() throws  {
        if (this.loginManager == null) {
            this.loginManager = LoginManager.getInstance();
        }
        return this.loginManager;
    }

    void setLoginManager(LoginManager $r1) throws  {
        this.loginManager = $r1;
    }
}
