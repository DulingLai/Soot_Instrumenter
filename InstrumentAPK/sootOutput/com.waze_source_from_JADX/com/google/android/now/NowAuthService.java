package com.google.android.now;

import android.content.Intent;
import android.os.Looper;

public class NowAuthService {
    static final String AUTH_SERVICE_ACTION = "com.google.android.now.NOW_AUTH_SERVICE";
    private static final Intent AUTH_SERVICE_INTENT = new Intent(AUTH_SERVICE_ACTION).setPackage("com.google.android.googlequicksearchbox");
    static final int ERROR_DISABLED = 3;
    static final int ERROR_HAVE_TOKEN_ALREADY = 2;
    static final int ERROR_TOO_MANY_REQUESTS = 1;
    static final int ERROR_UNAUTHORIZED = 0;
    static final String EXTRA_ACCESS_TOKEN = "access-token";
    static final String EXTRA_AUTH_CODE = "auth-code";
    static final String EXTRA_ERROR = "error";
    static final String EXTRA_NEXT_RETRY_TIMESTAMP_MILLIS = "next-retry-timestamp-millis";
    private static final String TAG = "NowAuthService";
    static boolean sThreadCheckDisabled;

    public static class DisabledException extends Exception {
    }

    public static class HaveTokenAlreadyException extends Exception {
        private final String mAccessToken;

        public HaveTokenAlreadyException(String $r1) throws  {
            this.mAccessToken = $r1;
        }

        public String getAccessToken() throws  {
            return this.mAccessToken;
        }
    }

    public static class TooManyRequestsException extends Exception {
        private final long mNextRetryTimestampMillis;

        public TooManyRequestsException(long $l0) throws  {
            this.mNextRetryTimestampMillis = $l0;
        }

        public long getNextRetryTimestampMillis() throws  {
            return this.mNextRetryTimestampMillis;
        }
    }

    public static class UnauthorizedException extends Exception {
    }

    public static java.lang.String getAuthCode(android.content.Context r27, java.lang.String r28) throws java.io.IOException, com.google.android.now.NowAuthService.UnauthorizedException, com.google.android.now.NowAuthService.TooManyRequestsException, com.google.android.now.NowAuthService.HaveTokenAlreadyException, com.google.android.now.NowAuthService.DisabledException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00b6 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.visitors.regions.RegionMaker.processSwitch(RegionMaker.java:708)
	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:132)
	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:94)
	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:643)
	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:127)
	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:94)
	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:648)
	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:127)
	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:94)
	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:643)
	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:127)
	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:94)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        checkNotMainThread();
        r4 = new com.google.android.now.BlockingServiceConnection;
        r4.<init>();
        r5 = AUTH_SERVICE_INTENT;
        r7 = 1;
        r0 = r27;
        r6 = r0.bindService(r5, r4, r7);
        if (r6 == 0) goto L_0x00f2;
    L_0x0013:
        r8 = r4.getService();	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r9 = com.google.android.now.INowAuthService.Stub.asInterface(r8);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r27;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r10 = r0.getPackageName();	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r28;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r11 = r9.getAuthCode(r0, r10);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        if (r11 != 0) goto L_0x0041;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
    L_0x0029:
        r12 = new java.io.IOException;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r13 = "Unexpected response from Google Now app";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r12.<init>(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        throw r12;	 Catch:{ Throwable -> 0x003a }
    L_0x0031:
        r14 = move-exception;
        r12 = new java.io.IOException;	 Catch:{ Throwable -> 0x003a }
        r13 = "Call to Google Now app failed";	 Catch:{ Throwable -> 0x003a }
        r12.<init>(r13, r14);	 Catch:{ Throwable -> 0x003a }
        throw r12;	 Catch:{ Throwable -> 0x003a }
    L_0x003a:
        r15 = move-exception;
        r0 = r27;
        r4.unbindServiceIfConnected(r0);
        throw r15;
    L_0x0041:
        r13 = "error";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r6 = r11.containsKey(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        if (r6 == 0) goto L_0x00e6;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
    L_0x0049:
        r13 = "error";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r16 = r11.getInt(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        switch(r16) {
            case 0: goto L_0x00b6;
            case 1: goto L_0x00be;
            case 2: goto L_0x00ce;
            case 3: goto L_0x00de;
            default: goto L_0x0052;
        };	 Catch:{ Throwable -> 0x003a }
    L_0x0052:
        goto L_0x0053;	 Catch:{ Throwable -> 0x003a }
    L_0x0053:
        r17 = new java.lang.StringBuilder;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r7 = 26;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0.<init>(r7);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r13 = "Unknown error: ";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r17 = r0.append(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r1 = r16;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r17 = r0.append(r1);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r28 = r0.toString();	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r13 = "NowAuthService";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r28;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        android.util.Log.e(r13, r0);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r12 = new java.io.IOException;	 Catch:{ Throwable -> 0x003a }
        r17 = new java.lang.StringBuilder;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r7 = 49;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0.<init>(r7);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r13 = "Unexpected error from Google Now app: ";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r17 = r0.append(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r1 = r16;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r17 = r0.append(r1);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r17;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r28 = r0.toString();	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r28;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r12.<init>(r0);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        throw r12;	 Catch:{ Throwable -> 0x003a }
    L_0x00a0:
        r18 = move-exception;
        r13 = "NowAuthService";	 Catch:{ Throwable -> 0x003a }
        r19 = "Interrupted";	 Catch:{ Throwable -> 0x003a }
        r0 = r19;	 Catch:{ Throwable -> 0x003a }
        r1 = r18;	 Catch:{ Throwable -> 0x003a }
        android.util.Log.w(r13, r0, r1);	 Catch:{ Throwable -> 0x003a }
        r20 = new java.io.InterruptedIOException;	 Catch:{ Throwable -> 0x003a }
        r13 = "Interrupted while contacting Google Now app";	 Catch:{ Throwable -> 0x003a }
        r0 = r20;	 Catch:{ Throwable -> 0x003a }
        r0.<init>(r13);	 Catch:{ Throwable -> 0x003a }
        throw r20;	 Catch:{ Throwable -> 0x003a }
    L_0x00b6:
        r21 = new com.google.android.now.NowAuthService$UnauthorizedException;	 Catch:{ Throwable -> 0x003a }
        r0 = r21;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0.<init>();	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        throw r21;	 Catch:{ Throwable -> 0x003a }
    L_0x00be:
        r22 = new com.google.android.now.NowAuthService$TooManyRequestsException;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r13 = "next-retry-timestamp-millis";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r23 = r11.getLong(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r22;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r1 = r23;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0.<init>(r1);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        throw r22;	 Catch:{ Throwable -> 0x003a }
    L_0x00ce:
        r25 = new com.google.android.now.NowAuthService$HaveTokenAlreadyException;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r13 = "access-token";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r28 = r11.getString(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r25;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r1 = r28;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0.<init>(r1);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        throw r25;	 Catch:{ Throwable -> 0x003a }
    L_0x00de:
        r26 = new com.google.android.now.NowAuthService$DisabledException;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r26;	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0.<init>();	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        throw r26;
    L_0x00e6:
        r13 = "auth-code";	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r28 = r11.getString(r13);	 Catch:{ RemoteException -> 0x0031, InterruptedException -> 0x00a0 }
        r0 = r27;
        r4.unbindServiceIfConnected(r0);
        return r28;
    L_0x00f2:
        r12 = new java.io.IOException;
        r13 = "Failed to contact Google Now app";
        r12.<init>(r13);
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.now.NowAuthService.getAuthCode(android.content.Context, java.lang.String):java.lang.String");
    }

    private static void checkNotMainThread() throws  {
        if (Looper.myLooper() == Looper.getMainLooper() && !sThreadCheckDisabled) {
            throw new IllegalStateException("Cannot call this API from the main thread");
        }
    }
}
