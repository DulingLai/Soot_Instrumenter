package com.spotify.sdk.android.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LoginActivity extends Activity implements AuthenticationClientListener {
    static final String EXTRA_AUTH_REQUEST = "EXTRA_AUTH_REQUEST";
    static final String EXTRA_AUTH_RESPONSE = "EXTRA_AUTH_RESPONSE";
    private static final String NO_CALLER_ERROR = "Can't use LoginActivity with a null caller. Possible reasons: calling activity has a singleInstance mode or LoginActivity is in a singleInstance/singleTask mode";
    private static final String NO_REQUEST_ERROR = "No authentication request";
    public static final int REQUEST_CODE = 1138;
    public static final String REQUEST_KEY = "request";
    public static final String RESPONSE_KEY = "response";
    private static final int RESULT_ERROR = -2;
    private static final String TAG = LoginActivity.class.getName();
    private AuthenticationClient mAuthenticationClient = new AuthenticationClient(this);
    private boolean mBackgrounded;
    private AuthenticationRequest mRequest;

    protected void onActivityResult(int r21, int r22, android.content.Intent r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:18:0x006f
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r20 = this;
        r4 = -1;
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r3 = r23;
        super.onActivityResult(r1, r2, r3);
        r5 = 1138; // 0x472 float:1.595E-42 double:5.62E-321;
        r0 = r21;
        if (r0 != r5) goto L_0x0102;
    L_0x0012:
        r6 = new com.spotify.sdk.android.authentication.AuthenticationResponse$Builder;
        r6.<init>();
        r5 = -2;
        r0 = r22;
        if (r0 != r5) goto L_0x0051;
    L_0x001c:
        r7 = TAG;
        r8 = "Error authenticating";
        android.util.Log.d(r7, r8);
        r9 = com.spotify.sdk.android.authentication.AuthenticationResponse.Type.ERROR;
        r6.setType(r9);
        if (r23 != 0) goto L_0x0048;
    L_0x002a:
        r7 = "Invalid message format";
    L_0x002c:
        if (r7 != 0) goto L_0x0030;
    L_0x002e:
        r7 = "Unknown error";
    L_0x0030:
        r6.setError(r7);
    L_0x0033:
        r0 = r20;
        r10 = r0.mAuthenticationClient;
        r0 = r20;
        r10.setOnCompleteListener(r0);
        r0 = r20;
        r10 = r0.mAuthenticationClient;
        r11 = r6.build();
        r10.complete(r11);
        return;
    L_0x0048:
        r8 = "ERROR";
        r0 = r23;
        r7 = r0.getStringExtra(r8);
        goto L_0x002c;
    L_0x0051:
        r5 = -1;
        r0 = r22;
        if (r0 != r5) goto L_0x00fc;
    L_0x0056:
        r8 = "REPLY";
        r0 = r23;
        r12 = r0.getParcelableExtra(r8);
        r14 = r12;
        r14 = (android.os.Bundle) r14;
        r13 = r14;
        if (r13 != 0) goto L_0x0073;
    L_0x0064:
        r9 = com.spotify.sdk.android.authentication.AuthenticationResponse.Type.ERROR;
        r6.setType(r9);
        r8 = "Missing response data";
        r6.setError(r8);
        goto L_0x0033;
        goto L_0x0073;
    L_0x0070:
        goto L_0x0033;
    L_0x0073:
        r8 = "RESPONSE_TYPE";
        r15 = "unknown";
        r7 = r13.getString(r8, r15);
        r16 = TAG;
        r17 = new java.lang.StringBuilder;
        r0 = r17;
        r0.<init>();
        goto L_0x0089;
    L_0x0086:
        goto L_0x0033;
    L_0x0089:
        r8 = "Response: ";
        r0 = r17;
        r17 = r0.append(r8);
        goto L_0x0099;
    L_0x0092:
        goto L_0x0033;
        goto L_0x0099;
    L_0x0096:
        goto L_0x0033;
    L_0x0099:
        r0 = r17;
        r17 = r0.append(r7);
        r0 = r17;
        r18 = r0.toString();
        r0 = r16;
        r1 = r18;
        android.util.Log.d(r0, r1);
        r21 = r7.hashCode();
        switch(r21) {
            case 3059181: goto L_0x00c9;
            case 110541305: goto L_0x00be;
            default: goto L_0x00b3;
        };
    L_0x00b3:
        goto L_0x00b4;
    L_0x00b4:
        switch(r4) {
            case 0: goto L_0x00d3;
            case 1: goto L_0x00ed;
            default: goto L_0x00b7;
        };
    L_0x00b7:
        goto L_0x00b8;
    L_0x00b8:
        r9 = com.spotify.sdk.android.authentication.AuthenticationResponse.Type.UNKNOWN;
        r6.setType(r9);
        goto L_0x0070;
    L_0x00be:
        r8 = "token";
        r19 = r7.equals(r8);
        if (r19 == 0) goto L_0x00b4;
    L_0x00c7:
        r4 = 0;
        goto L_0x00b4;
    L_0x00c9:
        r8 = "code";
        r19 = r7.equals(r8);
        if (r19 == 0) goto L_0x00b4;
    L_0x00d1:
        r4 = 1;
        goto L_0x00b4;
    L_0x00d3:
        r8 = "ACCESS_TOKEN";
        r7 = r13.getString(r8);
        r8 = "EXPIRES_IN";
        r21 = r13.getInt(r8);
        r9 = com.spotify.sdk.android.authentication.AuthenticationResponse.Type.TOKEN;
        r6.setType(r9);
        r6.setAccessToken(r7);
        r0 = r21;
        r6.setExpiresIn(r0);
        goto L_0x0086;
    L_0x00ed:
        r8 = "AUTHORIZATION_CODE";
        r7 = r13.getString(r8);
        r9 = com.spotify.sdk.android.authentication.AuthenticationResponse.Type.CODE;
        r6.setType(r9);
        r6.setCode(r7);
        goto L_0x0092;
    L_0x00fc:
        r9 = com.spotify.sdk.android.authentication.AuthenticationResponse.Type.EMPTY;
        r6.setType(r9);
        goto L_0x0096;
    L_0x0102:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.spotify.sdk.android.authentication.LoginActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    public static Intent getAuthIntent(Activity $r0, AuthenticationRequest $r1) throws  {
        if ($r0 == null || $r1 == null) {
            throw new IllegalArgumentException("Context activity or request can't be null");
        }
        Bundle $r2 = new Bundle();
        $r2.putParcelable("request", $r1);
        Intent $r3 = new Intent($r0, LoginActivity.class);
        $r3.putExtra(EXTRA_AUTH_REQUEST, $r2);
        return $r3;
    }

    static AuthenticationResponse getResponseFromIntent(Intent $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Bundle $r2 = $r0.getBundleExtra(EXTRA_AUTH_RESPONSE);
        return $r2 != null ? (AuthenticationResponse) $r2.getParcelable(RESPONSE_KEY) : null;
    }

    protected void onNewIntent(Intent $r1) throws  {
        super.onNewIntent($r1);
        this.mAuthenticationClient.complete(AuthenticationResponse.fromUri($r1.getData()));
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1086R.layout.com_spotify_sdk_login_activity);
        this.mRequest = getRequestFromIntent();
        this.mAuthenticationClient.setOnCompleteListener(this);
        if (getCallingActivity() == null) {
            Log.e(TAG, NO_CALLER_ERROR);
            finish();
        } else if (this.mRequest == null) {
            Log.e(TAG, NO_REQUEST_ERROR);
            setResult(0);
            finish();
        } else {
            Log.d(TAG, this.mRequest.toUri().toString());
            this.mAuthenticationClient.authenticate(this.mRequest);
        }
    }

    private AuthenticationRequest getRequestFromIntent() throws  {
        Bundle $r2 = getIntent().getBundleExtra(EXTRA_AUTH_REQUEST);
        return $r2 == null ? null : (AuthenticationRequest) $r2.getParcelable("request");
    }

    protected void onResume() throws  {
        super.onResume();
        if (this.mBackgrounded) {
            this.mBackgrounded = false;
            onClientCancelled();
        }
    }

    protected void onPause() throws  {
        super.onPause();
        this.mBackgrounded = true;
    }

    protected void onDestroy() throws  {
        this.mAuthenticationClient.cancel();
        this.mAuthenticationClient.setOnCompleteListener(null);
        super.onDestroy();
    }

    public void onClientComplete(AuthenticationResponse $r1) throws  {
        Intent $r3 = new Intent();
        Bundle $r2 = new Bundle();
        $r2.putParcelable(RESPONSE_KEY, $r1);
        $r3.putExtra(EXTRA_AUTH_RESPONSE, $r2);
        setResult(-1, $r3);
        finish();
    }

    public void onClientCancelled() throws  {
        setResult(0);
        finish();
    }
}
