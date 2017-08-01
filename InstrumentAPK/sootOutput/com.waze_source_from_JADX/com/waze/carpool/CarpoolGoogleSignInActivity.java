package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.carpool.MissingPermissionsActivity.MPType;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityBase.IncomingHandler;
import com.waze.location.LocationHistory;
import com.waze.location.LocationHistory.LocationHistoryOptInStatus;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class CarpoolGoogleSignInActivity extends ActivityBase implements FacebookCallback {
    static final /* synthetic */ boolean $assertionsDisabled = (!CarpoolGoogleSignInActivity.class.desiredAssertionStatus());
    public static final int FB_TOKEN_SET_TO_MILISEC = 10000;
    public static final String INTENT_FAMILY_NAME = "FamilyName";
    public static final String INTENT_GIVEN_NAME = "GivenName";
    public static final String INTENT_GOOGLE_CONNECT_MODE = "GOOGLE_CONNECT_MODE";
    public static final String INTENT_IMAGE_URL = "ImageUrl";
    private static final int RC_FB_FAILED = 12047;
    private static final int RC_FB_USER_IN_USE = 12048;
    private static final int RC_GOOGLE_SIGN_IN = 10001;
    private static final int RC_TOO_YOUNG = 1002;
    private static final int RQ_GET_LOCATION_HISTORY = 3000;
    public static final int STATE_CONNECT = 1;
    private static final int STATE_CREATE = 2;
    public static final int STATE_UNKNOWN = 0;
    INextActionCallback getNextCb = new C14181();
    private int mConnectProvider = 0;
    private CarpoolNativeManager mCpnm;
    private boolean mFbAlreadyConnected = false;
    private String mFirstName = null;
    private boolean mGaialessFlowEnabled = false;
    private String mImageUrl = null;
    private String mLastName = null;
    private NativeManager mNm;
    private FacebookSettings mSettings;
    private int mState = 0;
    private boolean mTimeoutOccurred = false;
    Runnable timeoutRunnable = new C14225();

    class C14181 implements INextActionCallback {
        C14181() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            CarpoolGoogleSignInActivity.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
        }

        public void setNextFragment(Fragment fragment) throws  {
            Logger.m38e("CarpoolIdSignInActivity: unexpected action: setNextFragment");
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                super.onBackPressed();
            } else if ($i0 == -1) {
                CarpoolGoogleSignInActivity.this.setResult(-1);
                CarpoolGoogleSignInActivity.this.finish();
            } else {
                Logger.m38e("CarpoolIdSignInActivity: received unexpected result:" + $i0);
            }
        }

        public Context getContext() throws  {
            return CarpoolGoogleSignInActivity.this;
        }
    }

    class C14192 implements OnClickListener {
        C14192() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolGoogleSignInActivity.this.startActivityForResult(new Intent(CarpoolGoogleSignInActivity.this, CarpoolTermsActivity.class), 0);
        }
    }

    class C14203 implements OnClickListener {
        C14203() throws  {
        }

        public void onClick(View view) throws  {
            Logger.m41i("CarpoolIdSigninActivity: Requesting Google connect");
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_CLICKED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONNECT);
            CarpoolGoogleSignInActivity.this.mConnectProvider = 1;
            CarpoolGoogleSignInActivity.this.mNm.OpenProgressPopup("");
            CarpoolGoogleSignInActivity.this.mNm.lockProgressPopup();
            Intent $r2 = new Intent(CarpoolGoogleSignInActivity.this, GoogleSignInActivity.class);
            $r2.putExtra(GoogleSignInActivity.GOOGLE_CONNECT_ACTION, 1);
            CarpoolGoogleSignInActivity.this.startActivityForResult($r2, CarpoolGoogleSignInActivity.RC_GOOGLE_SIGN_IN);
        }
    }

    class C14214 implements OnClickListener {
        C14214() throws  {
        }

        public void onClick(View view) throws  {
            CarpoolGoogleSignInActivity.this.mConnectProvider = 2;
            CarpoolGoogleSignInActivity.this.mFbAlreadyConnected = MyWazeNativeManager.getInstance().getFacebookLoggedInNTV();
            if (MyWazeNativeManager.getInstance().getFacebookLoggedInNTV()) {
                Logger.m41i("CarpoolIdSigninActivity: User already connected to FB, creaqting profile");
                CarpoolGoogleSignInActivity.this.updateConnectionDone(2);
                return;
            }
            Logger.m41i("CarpoolIdSigninActivity: Requesting Facebook connect");
            CarpoolGoogleSignInActivity.this.mNm.OpenProgressPopup("");
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FACEBOOK_CONNECT_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SETTINGS_SCREEN);
            MyWazeNativeManager.getInstance().getFacebookSettings(CarpoolGoogleSignInActivity.this);
        }
    }

    class C14225 implements Runnable {
        C14225() throws  {
        }

        public void run() throws  {
            CarpoolGoogleSignInActivity.this.mTimeoutOccurred = true;
            MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, CarpoolGoogleSignInActivity.this.mHandler);
            Logger.m38e("CarpoolIdSignInActivity: Timeout occurred when trying to connect to FB");
            CarpoolGoogleSignInActivity.this.showConnectError(CarpoolGoogleSignInActivity.RC_FB_FAILED);
        }
    }

    class C14236 implements FacebookLoginListener {
        C14236() throws  {
        }

        public void onFacebookLoginResult(boolean $z0) throws  {
            CarpoolGoogleSignInActivity.this.mHandler.postDelayed(CarpoolGoogleSignInActivity.this.timeoutRunnable, 10000);
            CarpoolGoogleSignInActivity.this.processFacebookConnectResult($z0);
        }
    }

    class C14257 implements LocationHistoryOptInStatus {

        class C14241 implements DialogInterface.OnClickListener {
            C14241() throws  {
            }

            public void onClick(DialogInterface dialog, int which) throws  {
                CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, CarpoolGoogleSignInActivity.this.getNextCb);
            }
        }

        C14257() throws  {
        }

        public void onLocationHistoryOptInStatus(boolean $z0, boolean $z1, boolean $z2) throws  {
            boolean $z3 = ConfigValues.getBoolValue(4);
            if ($z0) {
                if ($z2 || ($z1 && !$z3)) {
                    CarpoolGoogleSignInActivity.this.updateConnectionDone(1);
                    return;
                }
                Intent $r1 = new Intent(CarpoolGoogleSignInActivity.this, MissingPermissionsActivity.class);
                $r1.putExtra("MPType", MPType.MissingLocationHistory);
                CarpoolGoogleSignInActivity.this.startActivityForResult($r1, 3000);
            } else if ($z3) {
                MsgBox.openMessageBoxWithCallback(CarpoolGoogleSignInActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_UHHOHE), CarpoolGoogleSignInActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false, new C14241());
            } else {
                CarpoolGoogleSignInActivity.this.updateConnectionDone(1);
            }
        }
    }

    class C14268 implements Runnable {
        final /* synthetic */ String val$message;
        final /* synthetic */ int val$type;

        C14268(int $i0, String $r2) throws  {
            this.val$type = $i0;
            this.val$message = $r2;
        }

        public void run() throws  {
            CarpoolUtils.parseCarpoolUserCreateError(this.val$type, this.val$message, CarpoolGoogleSignInActivity.this.mFirstName, CarpoolGoogleSignInActivity.this.mLastName);
        }
    }

    protected boolean myHandleMessage(android.os.Message r35) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:19:0x00e8
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r34 = this;
        r0 = r35;
        r5 = r0.what;
        r6 = com.waze.carpool.CarpoolNativeManager.UH_CARPOOL_USER;
        if (r5 != r6) goto L_0x0157;
    L_0x0008:
        r0 = r35;
        r7 = r0.getData();
        r0 = r34;
        r8 = r0.mCpnm;
        r5 = com.waze.carpool.CarpoolNativeManager.UH_CARPOOL_USER;
        r0 = r34;
        r9 = r0.mHandler;
        r8.unsetUpdateHandler(r5, r9);
        r0 = r34;
        r10 = r0.mNm;
        r10.unlockProgressPopup();
        r0 = r34;
        r10 = r0.mNm;
        r10.CloseProgressPopup();
        r0 = r34;
        r8 = r0.mCpnm;
        r5 = com.waze.carpool.CarpoolNativeManager.UH_CARPOOL_ERROR;
        r0 = r34;
        r9 = r0.mHandler;
        r8.unsetUpdateHandler(r5, r9);
        r12 = "success";
        r11 = r7.getBoolean(r12);
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r12 = "CarpoolGoogleSignInActivity: Received UH_CARPOOL_USER msg, success=";
        r13 = r13.append(r12);
        r13 = r13.append(r11);
        r14 = r13.toString();
        com.waze.Logger.m41i(r14);
        r12 = "user";
        r15 = r7.getParcelable(r12);
        r17 = r15;
        r17 = (com.waze.carpool.CarpoolUserData) r17;
        r16 = r17;
        r0 = r34;
        r5 = r0.mState;
        r18 = 2;
        r0 = r18;
        if (r5 != r0) goto L_0x00fb;
    L_0x006a:
        if (r11 == 0) goto L_0x00ab;
    L_0x006c:
        r14 = "TRUE";
    L_0x006e:
        r12 = "RW_CREATE_PROFILE_DONE";
        r19 = "SUCCESS";
        r0 = r19;
        com.waze.analytics.Analytics.log(r12, r0, r14);
        if (r11 == 0) goto L_0x00ae;
    L_0x0079:
        if (r16 == 0) goto L_0x00ae;
    L_0x007b:
        r0 = r16;
        r20 = r0.didFinishOnboarding();
        if (r20 == 0) goto L_0x00ae;
    L_0x0083:
        r18 = 1;
        r0 = r18;
        com.waze.NativeManager.setPushToken(r0);
        r12 = "CarpoolGoogleSignInActivity: Carpool user created successfully";
        com.waze.Logger.m41i(r12);
        r21 = com.waze.carpool.CarpoolOnboardingManager.getInstance();
        r5 = com.waze.carpool.CarpoolOnboardingManager.RES_CARPOOL_ACCEPT;
        r0 = r34;
        r0 = r0.getNextCb;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.getNext(r5, r1);
    L_0x00a2:
        r0 = r34;
        r1 = r35;
        r11 = super.myHandleMessage(r1);
        return r11;
    L_0x00ab:
        r14 = "FALSE";
        goto L_0x006e;
    L_0x00ae:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r12 = "CarpoolGoogleSignInActivity: Failed to create user in server, success=";
        r13 = r13.append(r12);
        r13 = r13.append(r11);
        r12 = "; data=";
        r13 = r13.append(r12);
        r0 = r16;
        r13 = r13.append(r0);
        r12 = "; finish=";
        r13 = r13.append(r12);
        if (r16 != 0) goto L_0x00ec;
    L_0x00d1:
        r23 = "null";
    L_0x00d3:
        r0 = r23;
        r13 = r13.append(r0);
        r14 = r13.toString();
        com.waze.Logger.m38e(r14);
        r24 = 0;
        r0 = r24;
        com.waze.carpool.CarpoolUtils.showError(r0);
        goto L_0x00a2;
        goto L_0x00ec;
    L_0x00e9:
        goto L_0x00a2;
    L_0x00ec:
        r0 = r16;
        r11 = r0.didFinishOnboarding();
        r23 = java.lang.Boolean.valueOf(r11);
        goto L_0x00d3;
        goto L_0x00fb;
    L_0x00f8:
        goto L_0x00a2;
    L_0x00fb:
        r0 = r34;
        r5 = r0.mState;
        r18 = 1;
        r0 = r18;
        if (r5 != r0) goto L_0x0140;
    L_0x0105:
        if (r11 == 0) goto L_0x011f;
    L_0x0107:
        r14 = "TRUE";
    L_0x0109:
        r12 = "RW_CONNECT_PROVIDER_DONE";
        r19 = "SUCCESS";
        r0 = r19;
        com.waze.analytics.Analytics.log(r12, r0, r14);
        if (r11 == 0) goto L_0x0122;
    L_0x0114:
        r12 = "CarpoolGoogleSignInActivity: Carpool user connected to successfully";
        com.waze.Logger.m41i(r12);
        r0 = r34;
        r0.updateDriverProfile();
        goto L_0x00a2;
    L_0x011f:
        r14 = "FALSE";
        goto L_0x0109;
    L_0x0122:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r12 = "CarpoolGoogleSignInActivity: Failed to connect user in server, success=";
        r13 = r13.append(r12);
        r13 = r13.append(r11);
        r14 = r13.toString();
        com.waze.Logger.m38e(r14);
        r24 = 0;
        r0 = r24;
        com.waze.carpool.CarpoolUtils.showError(r0);
        goto L_0x00e9;
    L_0x0140:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r12 = "CarpoolGoogleSignInActivity: received UH_CARPOOL_USER from server, but did not request it; success=";
        r13 = r13.append(r12);
        r13 = r13.append(r11);
        r14 = r13.toString();
        com.waze.Logger.m38e(r14);
        goto L_0x00f8;
    L_0x0157:
        r0 = r35;
        r5 = r0.what;
        r6 = com.waze.carpool.CarpoolNativeManager.UH_CARPOOL_ERROR;
        if (r5 != r6) goto L_0x01b5;
    L_0x015f:
        r0 = r35;
        r7 = r0.getData();
        r12 = "type";
        r5 = r7.getInt(r12);
        r12 = "message";
        r14 = r7.getString(r12);
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r12 = "CarpoolGoogleSignInActivity: received carpool error from server, type=";
        r13 = r13.append(r12);
        r13 = r13.append(r5);
        r12 = "; message=";
        r13 = r13.append(r12);
        r13 = r13.append(r14);
        r25 = r13.toString();
        r0 = r25;
        com.waze.Logger.m38e(r0);
        r26 = new com.waze.carpool.CarpoolGoogleSignInActivity$8;
        r0 = r26;
        r1 = r34;
        r0.<init>(r5, r14);
        r27 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0 = r34;
        r1 = r26;
        r2 = r27;
        r0.postDelayed(r1, r2);
        r0 = r34;
        r0.carpoolError();
        r0 = r34;
        r0.logoutOnError();
        r18 = 1;
        return r18;
    L_0x01b5:
        r0 = r35;
        r5 = r0.what;
        r6 = com.waze.mywaze.MyWazeNativeManager.UH_FACEBOOK_CONNECT;
        if (r5 != r6) goto L_0x027f;
    L_0x01bd:
        r0 = r34;
        r9 = r0.mHandler;
        r0 = r34;
        r0 = r0.timeoutRunnable;
        r29 = r0;
        r9.removeCallbacks(r0);
        r30 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r5 = com.waze.mywaze.MyWazeNativeManager.UH_FACEBOOK_CONNECT;
        r0 = r34;
        r9 = r0.mHandler;
        r0 = r30;
        r0.unsetUpdateHandler(r5, r9);
        r12 = "CarpoolGoogleSignInActivity: received FB connect from server";
        com.waze.Logger.m36d(r12);
        r0 = r34;
        r11 = r0.mTimeoutOccurred;
        if (r11 != 0) goto L_0x0279;
    L_0x01e4:
        r0 = r35;
        r7 = r0.getData();
        r12 = "FBconnected";
        r11 = r7.getBoolean(r12);
        r30 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r30;
        r20 = r0.getFacebookLoggedInNTV();
        if (r11 == 0) goto L_0x020f;
    L_0x01fc:
        if (r20 == 0) goto L_0x020f;
    L_0x01fe:
        r12 = "CarpoolGoogleSignInActivity:  FB connected successfully to server";
        com.waze.Logger.m36d(r12);
        r18 = 2;
        r0 = r34;
        r1 = r18;
        r0.updateConnectionDone(r1);
    L_0x020c:
        r18 = 1;
        return r18;
    L_0x020f:
        r5 = -1;
        r12 = "FBErrorReason";
        r31 = r7.containsKey(r12);
        if (r31 == 0) goto L_0x021e;
    L_0x0218:
        r12 = "FBErrorReason";
        r5 = r7.getInt(r12);
    L_0x021e:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r12 = "CarpoolGoogleSignInActivity: FB Failed to connect to server, result=";
        r13 = r13.append(r12);
        r13 = r13.append(r11);
        r12 = "; connected=";
        r13 = r13.append(r12);
        r0 = r20;
        r13 = r13.append(r0);
        r12 = "; reason=";
        r13 = r13.append(r12);
        r13 = r13.append(r5);
        r14 = r13.toString();
        com.waze.Logger.m38e(r14);
        r18 = 6;
        r0 = r18;
        if (r5 == r0) goto L_0x0268;
    L_0x0250:
        r32 = 12047; // 0x2f0f float:1.6881E-41 double:5.952E-320;
    L_0x0252:
        r0 = r34;
        r10 = r0.mNm;
        r10.unlockProgressPopup();
        r0 = r34;
        r10 = r0.mNm;
        r10.CloseProgressPopup();
        r0 = r34;
        r1 = r32;
        r0.showConnectError(r1);
        goto L_0x020c;
    L_0x0268:
        r12 = "CarpoolGoogleSignInActivity: FB UH_FACEBOOK_CONNECT:User already connected, disconnecting from FB";
        com.waze.Logger.m43w(r12);
        r33 = com.waze.social.facebook.FacebookManager.getInstance();
        r0 = r33;
        r0.logoutFromFacebook();
        r32 = 12048; // 0x2f10 float:1.6883E-41 double:5.9525E-320;
        goto L_0x0252;
    L_0x0279:
        r12 = "CarpoolGoogleSignInActivity: Timeout already occurred, ignoring FB connect";
        com.waze.Logger.m43w(r12);
        goto L_0x020c;
    L_0x027f:
        r0 = r35;
        r5 = r0.what;
        r6 = com.waze.carpool.CarpoolNativeManager.UH_IS_AGE_OK;
        if (r5 != r6) goto L_0x00a2;
    L_0x0287:
        r0 = r34;
        r8 = r0.mCpnm;
        r5 = com.waze.carpool.CarpoolNativeManager.UH_IS_AGE_OK;
        r0 = r34;
        r9 = r0.mHandler;
        r8.unsetUpdateHandler(r5, r9);
        r0 = r35;
        r5 = r0.arg1;
        if (r5 != 0) goto L_0x02a7;
    L_0x029a:
        r0 = r34;
        r0.carpoolError();
        r0 = r34;
        r0.tooYoung();
    L_0x02a4:
        r18 = 1;
        return r18;
    L_0x02a7:
        r0 = r34;
        r0.testLocationHistory();
        goto L_0x02a4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolGoogleSignInActivity.myHandleMessage(android.os.Message):boolean");
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mGaialessFlowEnabled = ConfigManager.getInstance().getConfigValueBool(37);
        Logger.m41i("CarpoolIdSigninActivity: Gaialess flow config=" + this.mGaialessFlowEnabled);
        setContentView(C1283R.layout.google_connect);
        TitleBar $r8 = (TitleBar) findViewById(C1283R.id.theTitleBar);
        WazeTextView $r9 = (WazeTextView) findViewById(C1283R.id.googleConnectTitle);
        WazeTextView $r10 = (WazeTextView) findViewById(C1283R.id.googleConnectSubTitle);
        WazeTextView $r11 = (WazeTextView) findViewById(C1283R.id.googleConnectTos);
        $r11.setText(Html.fromHtml(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_LINK)));
        $r11.setOnClickListener(new C14192());
        $r11 = (WazeTextView) findViewById(C1283R.id.googleConnectButton);
        $r11.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_CTA));
        $r11.setOnClickListener(new C14203());
        $r11 = (WazeTextView) findViewById(C1283R.id.facebookConnectButton);
        if (this.mGaialessFlowEnabled) {
            $r11.setVisibility(0);
            $r11.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_FACEBOOK_CONNECT_CTA));
            $r11.setOnClickListener(new C14214());
        } else {
            $r11.setVisibility(8);
        }
        $r8.init(this, DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_TITLE), false);
        $r9.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_ACCEPT_FLOW_HEADER));
        if (this.mGaialessFlowEnabled) {
            $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_ACCEPT_FLOW_FB_BODY));
        } else {
            $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_GOOGLE_CONNECT_ACCEPT_FLOW_BODY));
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_SHOWN);
    }

    protected void onDestroy() throws  {
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_IS_AGE_OK, this.mHandler);
        super.onDestroy();
    }

    private void connectToFB(boolean $z0) throws  {
        if ($z0) {
            Logger.m41i("FB already connected for this user");
            updateConnectionDone(2);
            return;
        }
        Logger.m43w("CarpoolIdSignInActivity: Connecting to FB. Logging in...");
        MyWazeNativeManager.getInstance().setUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, this.mHandler);
        FacebookManager.getInstance().loginWithFacebook(this, FacebookLoginType.SetToken, true, true, new C14236());
    }

    private void processFacebookConnectResult(boolean $z0) throws  {
        if ($z0) {
            Logger.m43w("CarpoolGoogleSignInActivity: Recevied OK result from FB: continue to wait, loggedin=" + MyWazeNativeManager.getInstance().getFacebookLoggedInNTV());
            this.mTimeoutOccurred = false;
            return;
        }
        this.mNm.unlockProgressPopup();
        this.mNm.CloseProgressPopup();
        MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, this.mHandler);
        this.mHandler.removeCallbacks(this.timeoutRunnable);
        Logger.m38e("CarpoolGoogleSignInActivity: Recevied err result from FB, isloggedin= " + MyWazeNativeManager.getInstance().getFacebookLoggedInNTV());
        if (this.mTimeoutOccurred) {
            Logger.m43w("CarpoolGoogleSignInActivity: Timeout already occurred, not displying err result from FB");
        } else {
            showConnectError(RC_FB_FAILED);
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        boolean $z0 = false;
        if ($i0 == RC_GOOGLE_SIGN_IN) {
            if ($i1 == -1) {
                MyWazeData $r2 = CarpoolOnboardingManager.getWazeData();
                if ($r2 != null && $r2.mFirstName != null && !$r2.mFirstName.isEmpty()) {
                    Logger.m36d("CarpoolIdSignInActivity: first name from Waze data");
                    this.mFirstName = $r2.mFirstName;
                } else if ($r1 != null && $r1.hasExtra("GivenName")) {
                    Logger.m36d("CarpoolIdSignInActivity: first name from Google Sign in");
                    this.mFirstName = $r1.getStringExtra("GivenName");
                }
                if ($r2 != null && $r2.mLastName != null && !$r2.mLastName.isEmpty()) {
                    Logger.m36d("CarpoolIdSignInActivity: last name from Waze data");
                    this.mLastName = $r2.mLastName;
                } else if ($r1 != null && $r1.hasExtra("FamilyName")) {
                    Logger.m36d("CarpoolIdSignInActivity: last name from Google Sign in");
                    this.mLastName = $r1.getStringExtra("FamilyName");
                }
                if ($r2 == null || $r2.mImageUrl == null || $r2.mImageUrl.isEmpty()) {
                    this.mImageUrl = "";
                    Logger.m36d("CarpoolIdSignInActivity: Waze data image URL empty, not passing image");
                } else {
                    this.mImageUrl = $r2.mImageUrl;
                    Logger.m36d("CarpoolGoogleSignInActivity: Waze data image URL ok");
                }
                checkAge();
                return;
            }
            showConnectError($i1);
        } else if ($i0 == 3000) {
            $z0 = true;
            if (ConfigValues.getBoolValue(4)) {
                $z0 = $r1 != null ? $r1.getBooleanExtra("clicked_yes", false) : false;
            }
            if ($z0) {
                updateConnectionDone(1);
                return;
            }
            this.mNm.unlockProgressPopup();
            this.mNm.CloseProgressPopup();
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.getNextCb);
            finish();
        } else if ($i0 == 1002) {
            logoutOnError();
        } else {
            Intent $r7;
            if ($r1 == null) {
                $r7 = new Intent();
            } else {
                $r7 = $r1;
            }
            super.onActivityResult($i0, $i1, $r7);
            if ($r7.getBooleanExtra(FacebookManager.DATA_FACEBOOK_FLAG, false)) {
                if ($i1 == -1) {
                    $z0 = true;
                }
                processFacebookConnectResult($z0);
            } else if ($i1 == -1) {
                setResult(-1);
                finish();
            } else {
                Logger.m38e("CarpoolGoogleSignInActivity: Unexpected result, requestcode: " + $i0 + "; resultCode: " + $i1);
                super.onActivityResult($i0, $i1, $r1);
            }
        }
    }

    private void showConnectError(int $i0) throws  {
        int $s1 = (short) 2607;
        if ($i0 == 1227) {
            $s1 = (short) 485;
        } else if ($i0 == 1226) {
            $s1 = (short) 2107;
        } else if ($i0 == RC_FB_FAILED) {
            $s1 = (short) 876;
        } else if ($i0 == RC_FB_USER_IN_USE) {
            $s1 = (short) 3710;
        }
        this.mNm.unlockProgressPopup();
        this.mNm.CloseProgressPopup();
        CarpoolUtils.showError(this.mNm.getLanguageString($s1));
    }

    private void checkAge() throws  {
        this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_IS_AGE_OK, this.mHandler);
        this.mCpnm.checkUserAge(NativeManager.getInstance().getLocale().getCountry());
    }

    private void testLocationHistory() throws  {
        LocationHistory.checkLocationHistoryOptInStatus(this, new C14257());
    }

    private void updateConnectionDone(int $i0) throws  {
        if ($i0 != 1 || this.mGaialessFlowEnabled) {
            Logger.m43w("CarpoolGoogleSignInActivity: Gaialess flow enabled! Sending provider connected to server");
            this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
            this.mNm.lockProgressPopup();
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
            Logger.m41i("CarpoolGoogleSignInActivity: updating ID connection: type= " + $i0 + "; mConnectProvider=" + this.mConnectProvider);
            this.mState = 1;
            if ($assertionsDisabled || $i0 == this.mConnectProvider) {
                this.mCpnm.idProviderConnected($i0);
                return;
            }
            throw new AssertionError();
        }
        Logger.m43w("CarpoolGoogleSignInActivity: Gaialess flow not enabled! not sending provider connected to server");
        updateDriverProfile();
    }

    private void updateDriverProfile() throws  {
        CarpoolNativeManager $r1 = this.mCpnm;
        int $i0 = CarpoolNativeManager.UH_CARPOOL_USER;
        IncomingHandler $r2 = this.mHandler;
        $r1.setUpdateHandler($i0, $r2);
        $r1 = this.mCpnm;
        $i0 = CarpoolNativeManager.UH_CARPOOL_ERROR;
        $r2 = this.mHandler;
        $r1.setUpdateHandler($i0, $r2);
        this.mState = 2;
        String $r3 = CarpoolUtils.getPhoneIfLoggedIn();
        if (this.mGaialessFlowEnabled) {
            Logger.m36d("CarpoolGoogleSignInActivity:tryToCreateProfile: Updating only the phone number, rest filled by RT");
            this.mCpnm.createUser(5, null, null, null, null, $r3, null, null, null, null, 0, null, null);
        } else {
            Logger.m36d("CarpoolGoogleSignInActivity:tryToCreateProfile: Updating all details, not gaialess flow");
            this.mCpnm.createUser(5, GoogleSignInActivity.GetAuthorizedAccountName(), this.mFirstName, this.mLastName, this.mImageUrl, $r3, null, null, null, null, 0, null, null);
        }
        Logger.m41i("CarpoolGoogleSignInActivity: updating carpool user with name and phone details");
    }

    private void carpoolError() throws  {
        this.mNm.unlockProgressPopup();
        this.mNm.CloseProgressPopup();
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CREATE_PROFILE_DONE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE);
    }

    private void logoutOnError() throws  {
        if (this.mState != 1 && this.mState != 2) {
            Logger.m43w("CarpoolGoogleSignInActivity: logoutOnError: Error occurred, but state is neither connected or created: " + this.mState);
        } else if (this.mConnectProvider == 2) {
            if (this.mFbAlreadyConnected) {
                Logger.m43w("CarpoolGoogleSignInActivity: logoutOnError: Error occurred, but FB was already connected so not disconnecting");
            } else {
                Logger.m43w("CarpoolGoogleSignInActivity: logoutOnError: Error occurred, disconnecting from FB");
                FacebookManager.getInstance().logoutFromFacebook();
            }
        } else if (this.mConnectProvider == 1) {
            Logger.m43w("CarpoolGoogleSignInActivity: logoutOnError: Error occurred, disconnecting from Google");
            Intent $r1 = new Intent(this, GoogleSignInActivity.class);
            $r1.putExtra(GoogleSignInActivity.GOOGLE_CONNECT_ACTION, 2);
            startActivity($r1);
        } else {
            Logger.m38e("CarpoolGoogleSignInActivity: logoutOnError: Error occurred, but unknown connection provider: " + this.mConnectProvider);
        }
        this.mConnectProvider = 0;
        this.mState = 0;
    }

    private void tooYoung() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_FAILED, AnalyticsEvents.ANALYTICS_EVENT_INFO_CAUSE, "AGE");
        startActivityForResult(new Intent(this, TooYoungActivity.class), 1002);
    }

    public void onBackPressed() throws  {
        Logger.m36d("CarpoolGoogleSignInActivity: back pressed, exiting on cancel");
        CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.getNextCb);
        exitOnCancel();
        super.onBackPressed();
    }

    private void exitOnCancel() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_GOOGLE_CONNECT_CANCELED);
        setResult(0);
        finish();
    }

    public void finish() throws  {
        this.mHandler.removeCallbacks(this.timeoutRunnable);
        this.mTimeoutOccurred = false;
        MyWazeNativeManager.getInstance().unsetUpdateHandler(MyWazeNativeManager.UH_FACEBOOK_CONNECT, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
        super.finish();
    }

    public void onFacebookSettings(FacebookSettings $r1) throws  {
        Logger.m36d("CarpoolGoogleSignInActivity: received onFacebookSettings");
        connectToFB($r1.loggedIn);
    }
}
