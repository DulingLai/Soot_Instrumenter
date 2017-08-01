package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class AttributionIdentifiers {
    private static final String ANDROID_ID_COLUMN_NAME = "androidid";
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final String ATTRIBUTION_ID_CONTENT_PROVIDER = "com.facebook.katana.provider.AttributionIdProvider";
    private static final String ATTRIBUTION_ID_CONTENT_PROVIDER_WAKIZASHI = "com.facebook.wakizashi.provider.AttributionIdProvider";
    private static final int CONNECTION_RESULT_SUCCESS = 0;
    private static final long IDENTIFIER_REFRESH_INTERVAL_MILLIS = 3600000;
    private static final String LIMIT_TRACKING_COLUMN_NAME = "limit_tracking";
    private static final String TAG = AttributionIdentifiers.class.getCanonicalName();
    private static AttributionIdentifiers recentlyFetchedIdentifiers;
    private String androidAdvertiserId;
    private String androidInstallerPackage;
    private String attributionId;
    private long fetchTime;
    private boolean limitTracking;

    private static final class GoogleAdInfo implements IInterface {
        private static final int FIRST_TRANSACTION_CODE = 1;
        private static final int SECOND_TRANSACTION_CODE = 2;
        private IBinder binder;

        GoogleAdInfo(IBinder $r1) throws  {
            this.binder = $r1;
        }

        public IBinder asBinder() throws  {
            return this.binder;
        }

        public String getAdvertiserId() throws RemoteException {
            Parcel $r1 = Parcel.obtain();
            Parcel $r2 = Parcel.obtain();
            try {
                $r1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.binder.transact(1, $r1, $r2, 0);
                $r2.readException();
                String $r4 = $r2.readString();
                return $r4;
            } finally {
                $r2.recycle();
                $r1.recycle();
            }
        }

        public boolean isTrackingLimited() throws RemoteException {
            boolean $z0 = true;
            Parcel $r1 = Parcel.obtain();
            Parcel $r2 = Parcel.obtain();
            try {
                $r1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                $r1.writeInt(1);
                this.binder.transact(2, $r1, $r2, 0);
                $r2.readException();
                if ($r2.readInt() == 0) {
                    $z0 = false;
                }
                $r2.recycle();
                $r1.recycle();
                return $z0;
            } catch (Throwable th) {
                $r2.recycle();
                $r1.recycle();
            }
        }
    }

    private static final class GoogleAdServiceConnection implements ServiceConnection {
        private AtomicBoolean consumed;
        private final BlockingQueue<IBinder> queue;

        private GoogleAdServiceConnection() throws  {
            this.consumed = new AtomicBoolean(false);
            this.queue = new LinkedBlockingDeque();
        }

        public void onServiceConnected(ComponentName name, IBinder $r2) throws  {
            try {
                this.queue.put($r2);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName name) throws  {
        }

        public IBinder getBinder() throws InterruptedException {
            if (!this.consumed.compareAndSet(true, true)) {
                return (IBinder) this.queue.take();
            }
            throw new IllegalStateException("Binder already consumed");
        }
    }

    private static com.facebook.internal.AttributionIdentifiers getAndroidIdViaReflection(android.content.Context r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00c1 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
        r2 = android.os.Looper.myLooper();	 Catch:{ Exception -> 0x0012 }
        r3 = android.os.Looper.getMainLooper();	 Catch:{ Exception -> 0x0012 }
        if (r2 != r3) goto L_0x001a;	 Catch:{ Exception -> 0x0012 }
    L_0x000a:
        r4 = new com.facebook.FacebookException;	 Catch:{ Exception -> 0x0012 }
        r5 = "getAndroidId cannot be called on the main thread.";	 Catch:{ Exception -> 0x0012 }
        r4.<init>(r5);	 Catch:{ Exception -> 0x0012 }
        throw r4;	 Catch:{ Exception -> 0x0012 }
    L_0x0012:
        r6 = move-exception;
        r5 = "android_id";
        com.facebook.internal.Utility.logd(r5, r6);
        r7 = 0;
        return r7;
    L_0x001a:
        r9 = 1;	 Catch:{ Exception -> 0x0012 }
        r8 = new java.lang.Class[r9];	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r10 = android.content.Context.class;	 Catch:{ Exception -> 0x0012 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x0012 }
        r5 = "com.google.android.gms.common.GooglePlayServicesUtil";	 Catch:{ Exception -> 0x0012 }
        r12 = "isGooglePlayServicesAvailable";	 Catch:{ Exception -> 0x0012 }
        r11 = com.facebook.internal.Utility.getMethodQuietly(r5, r12, r8);	 Catch:{ Exception -> 0x0012 }
        if (r11 == 0) goto L_0x00bf;	 Catch:{ Exception -> 0x0012 }
    L_0x002c:
        r9 = 1;	 Catch:{ Exception -> 0x0012 }
        r13 = new java.lang.Object[r9];	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r13[r9] = r27;	 Catch:{ Exception -> 0x0012 }
        r7 = 0;	 Catch:{ Exception -> 0x0012 }
        r14 = com.facebook.internal.Utility.invokeMethodQuietly(r7, r11, r13);	 Catch:{ Exception -> 0x0012 }
        r15 = r14 instanceof java.lang.Integer;
        if (r15 == 0) goto L_0x00c1;	 Catch:{ Exception -> 0x0012 }
    L_0x003b:
        r17 = r14;	 Catch:{ Exception -> 0x0012 }
        r17 = (java.lang.Integer) r17;	 Catch:{ Exception -> 0x0012 }
        r16 = r17;	 Catch:{ Exception -> 0x0012 }
        r0 = r16;	 Catch:{ Exception -> 0x0012 }
        r18 = r0.intValue();	 Catch:{ Exception -> 0x0012 }
        if (r18 != 0) goto L_0x00c3;	 Catch:{ Exception -> 0x0012 }
    L_0x0049:
        r9 = 1;	 Catch:{ Exception -> 0x0012 }
        r8 = new java.lang.Class[r9];	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r10 = android.content.Context.class;	 Catch:{ Exception -> 0x0012 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x0012 }
        r5 = "com.google.android.gms.ads.identifier.AdvertisingIdClient";	 Catch:{ Exception -> 0x0012 }
        r12 = "getAdvertisingIdInfo";	 Catch:{ Exception -> 0x0012 }
        r11 = com.facebook.internal.Utility.getMethodQuietly(r5, r12, r8);	 Catch:{ Exception -> 0x0012 }
        if (r11 == 0) goto L_0x00c5;	 Catch:{ Exception -> 0x0012 }
    L_0x005b:
        r9 = 1;	 Catch:{ Exception -> 0x0012 }
        r13 = new java.lang.Object[r9];	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r13[r9] = r27;	 Catch:{ Exception -> 0x0012 }
        r7 = 0;	 Catch:{ Exception -> 0x0012 }
        r14 = com.facebook.internal.Utility.invokeMethodQuietly(r7, r11, r13);	 Catch:{ Exception -> 0x0012 }
        if (r14 == 0) goto L_0x00c7;	 Catch:{ Exception -> 0x0012 }
    L_0x0068:
        r19 = r14.getClass();	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r8 = new java.lang.Class[r9];	 Catch:{ Exception -> 0x0012 }
        r5 = "getId";	 Catch:{ Exception -> 0x0012 }
        r0 = r19;	 Catch:{ Exception -> 0x0012 }
        r11 = com.facebook.internal.Utility.getMethodQuietly(r0, r5, r8);	 Catch:{ Exception -> 0x0012 }
        r19 = r14.getClass();	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r8 = new java.lang.Class[r9];	 Catch:{ Exception -> 0x0012 }
        r5 = "isLimitAdTrackingEnabled";	 Catch:{ Exception -> 0x0012 }
        r0 = r19;	 Catch:{ Exception -> 0x0012 }
        r20 = com.facebook.internal.Utility.getMethodQuietly(r0, r5, r8);	 Catch:{ Exception -> 0x0012 }
        if (r11 == 0) goto L_0x00c9;
    L_0x0088:
        if (r20 == 0) goto L_0x00cb;
    L_0x008a:
        r21 = new com.facebook.internal.AttributionIdentifiers;	 Catch:{ Exception -> 0x0012 }
        r0 = r21;	 Catch:{ Exception -> 0x0012 }
        r0.<init>();	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r13 = new java.lang.Object[r9];	 Catch:{ Exception -> 0x0012 }
        r22 = com.facebook.internal.Utility.invokeMethodQuietly(r14, r11, r13);	 Catch:{ Exception -> 0x0012 }
        r24 = r22;	 Catch:{ Exception -> 0x0012 }
        r24 = (java.lang.String) r24;	 Catch:{ Exception -> 0x0012 }
        r23 = r24;	 Catch:{ Exception -> 0x0012 }
        r0 = r23;	 Catch:{ Exception -> 0x0012 }
        r1 = r21;	 Catch:{ Exception -> 0x0012 }
        r1.androidAdvertiserId = r0;	 Catch:{ Exception -> 0x0012 }
        r9 = 0;	 Catch:{ Exception -> 0x0012 }
        r13 = new java.lang.Object[r9];	 Catch:{ Exception -> 0x0012 }
        r0 = r20;	 Catch:{ Exception -> 0x0012 }
        r14 = com.facebook.internal.Utility.invokeMethodQuietly(r14, r0, r13);	 Catch:{ Exception -> 0x0012 }
        r26 = r14;	 Catch:{ Exception -> 0x0012 }
        r26 = (java.lang.Boolean) r26;	 Catch:{ Exception -> 0x0012 }
        r25 = r26;	 Catch:{ Exception -> 0x0012 }
        r0 = r25;	 Catch:{ Exception -> 0x0012 }
        r15 = r0.booleanValue();	 Catch:{ Exception -> 0x0012 }
        r0 = r21;	 Catch:{ Exception -> 0x0012 }
        r0.limitTracking = r15;	 Catch:{ Exception -> 0x0012 }
        r7 = 0;
        return r7;
    L_0x00bf:
        r7 = 0;
        return r7;
    L_0x00c1:
        r7 = 0;
        return r7;
    L_0x00c3:
        r7 = 0;
        return r7;
    L_0x00c5:
        r7 = 0;
        return r7;
    L_0x00c7:
        r7 = 0;
        return r7;
    L_0x00c9:
        r7 = 0;
        return r7;
    L_0x00cb:
        r7 = 0;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.AttributionIdentifiers.getAndroidIdViaReflection(android.content.Context):com.facebook.internal.AttributionIdentifiers");
    }

    public static com.facebook.internal.AttributionIdentifiers getAttributionIdentifiers(android.content.Context r35) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00af in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
        r6 = recentlyFetchedIdentifiers;
        if (r6 == 0) goto L_0x0017;
    L_0x0004:
        r7 = java.lang.System.currentTimeMillis();
        r6 = recentlyFetchedIdentifiers;
        r9 = r6.fetchTime;
        r7 = r7 - r9;
        r12 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
        r11 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1));
        if (r11 >= 0) goto L_0x0017;
    L_0x0014:
        r6 = recentlyFetchedIdentifiers;
        return r6;
    L_0x0017:
        r0 = r35;
        r6 = getAndroidId(r0);
        r14 = 0;
        r16 = 3;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r16;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r15 = new java.lang.String[r0];	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r16 = 0;	 Catch:{  }
        r17 = "aid";	 Catch:{  }
        r15[r16] = r17;	 Catch:{  }
        r16 = 1;	 Catch:{  }
        r17 = "androidid";	 Catch:{  }
        r15[r16] = r17;	 Catch:{  }
        r16 = 2;	 Catch:{  }
        r17 = "limit_tracking";	 Catch:{  }
        r15[r16] = r17;	 Catch:{  }
        r18 = 0;	 Catch:{  }
        r0 = r35;	 Catch:{  }
        r19 = r0.getPackageManager();	 Catch:{  }
        r17 = "com.facebook.katana.provider.AttributionIdProvider";	 Catch:{  }
        r16 = 0;	 Catch:{  }
        r0 = r19;	 Catch:{  }
        r1 = r17;	 Catch:{  }
        r2 = r16;	 Catch:{  }
        r20 = r0.resolveContentProvider(r1, r2);	 Catch:{  }
        if (r20 == 0) goto L_0x0069;	 Catch:{  }
    L_0x004e:
        r17 = "content://com.facebook.katana.provider.AttributionIdProvider";	 Catch:{  }
        r0 = r17;	 Catch:{  }
        r18 = android.net.Uri.parse(r0);	 Catch:{  }
    L_0x0056:
        r0 = r35;	 Catch:{  }
        r21 = getInstallerPackageName(r0);	 Catch:{  }
        if (r21 == 0) goto L_0x0062;	 Catch:{  }
    L_0x005e:
        r0 = r21;	 Catch:{  }
        r6.androidInstallerPackage = r0;	 Catch:{  }
    L_0x0062:
        if (r18 != 0) goto L_0x0088;	 Catch:{  }
    L_0x0064:
        r6 = cacheAndReturnIdentifiers(r6);	 Catch:{  }
        goto L_0x00e2;
    L_0x0069:
        r0 = r35;	 Catch:{  }
        r19 = r0.getPackageManager();	 Catch:{  }
        r17 = "com.facebook.wakizashi.provider.AttributionIdProvider";	 Catch:{  }
        r16 = 0;	 Catch:{  }
        r0 = r19;	 Catch:{  }
        r1 = r17;	 Catch:{  }
        r2 = r16;	 Catch:{  }
        r20 = r0.resolveContentProvider(r1, r2);	 Catch:{  }
        if (r20 == 0) goto L_0x0056;	 Catch:{  }
    L_0x007f:
        r17 = "content://com.facebook.wakizashi.provider.AttributionIdProvider";	 Catch:{  }
        r0 = r17;	 Catch:{  }
        r18 = android.net.Uri.parse(r0);	 Catch:{  }
        goto L_0x0056;	 Catch:{  }
    L_0x0088:
        r0 = r35;	 Catch:{  }
        r22 = r0.getContentResolver();	 Catch:{  }
        r24 = 0;	 Catch:{  }
        r25 = 0;	 Catch:{  }
        r26 = 0;	 Catch:{  }
        r0 = r22;	 Catch:{  }
        r1 = r18;	 Catch:{  }
        r2 = r15;	 Catch:{  }
        r3 = r24;	 Catch:{  }
        r4 = r25;	 Catch:{  }
        r5 = r26;	 Catch:{  }
        r23 = r0.query(r1, r2, r3, r4, r5);	 Catch:{  }
        r14 = r23;
        if (r23 == 0) goto L_0x00af;	 Catch:{  }
    L_0x00a7:
        r0 = r23;	 Catch:{  }
        r27 = r0.moveToFirst();	 Catch:{  }
        if (r27 != 0) goto L_0x00bb;	 Catch:{  }
    L_0x00af:
        r6 = cacheAndReturnIdentifiers(r6);	 Catch:{  }
        if (r23 == 0) goto L_0x015c;
    L_0x00b5:
        r0 = r23;
        r0.close();
        return r6;
    L_0x00bb:
        r17 = "aid";	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r17;	 Catch:{  }
        r28 = r0.getColumnIndex(r1);	 Catch:{  }
        r17 = "androidid";	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r17;	 Catch:{  }
        r29 = r0.getColumnIndex(r1);	 Catch:{  }
        r17 = "limit_tracking";	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r17;	 Catch:{  }
        r30 = r0.getColumnIndex(r1);	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r28;	 Catch:{  }
        r21 = r0.getString(r1);	 Catch:{  }
        goto L_0x00e5;	 Catch:{  }
    L_0x00e2:
        goto L_0x015b;	 Catch:{  }
    L_0x00e5:
        r0 = r21;	 Catch:{  }
        r6.attributionId = r0;	 Catch:{  }
        if (r29 <= 0) goto L_0x0111;
    L_0x00eb:
        if (r30 <= 0) goto L_0x0111;	 Catch:{  }
    L_0x00ed:
        r21 = r6.getAndroidAdvertiserId();	 Catch:{  }
        if (r21 != 0) goto L_0x0111;	 Catch:{  }
    L_0x00f3:
        r0 = r23;	 Catch:{  }
        r1 = r29;	 Catch:{  }
        r21 = r0.getString(r1);	 Catch:{  }
        r0 = r21;	 Catch:{  }
        r6.androidAdvertiserId = r0;	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r30;	 Catch:{  }
        r21 = r0.getString(r1);	 Catch:{  }
        r0 = r21;	 Catch:{  }
        r27 = java.lang.Boolean.parseBoolean(r0);	 Catch:{  }
        r0 = r27;	 Catch:{  }
        r6.limitTracking = r0;	 Catch:{  }
    L_0x0111:
        if (r23 == 0) goto L_0x0118;
    L_0x0113:
        r0 = r23;
        r0.close();
    L_0x0118:
        r6 = cacheAndReturnIdentifiers(r6);
        return r6;
    L_0x011d:
        r31 = move-exception;
        r21 = TAG;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r32 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r32;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0.<init>();	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r17 = "Caught unexpected exception in getAttributionId(): ";	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r32;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r1 = r17;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r32 = r0.append(r1);	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r31;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r33 = r0.toString();	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r32;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r1 = r33;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r32 = r0.append(r1);	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r32;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r33 = r0.toString();	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r0 = r21;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        r1 = r33;	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x011d, Throwable -> 0x0154 }
        if (r14 == 0) goto L_0x015d;
    L_0x014e:
        r14.close();
        r24 = 0;
        return r24;
    L_0x0154:
        r34 = move-exception;
        if (r14 == 0) goto L_0x015a;
    L_0x0157:
        r14.close();
    L_0x015a:
        throw r34;
    L_0x015b:
        return r6;
    L_0x015c:
        return r6;
    L_0x015d:
        r24 = 0;
        return r24;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(android.content.Context):com.facebook.internal.AttributionIdentifiers");
    }

    private static AttributionIdentifiers getAndroidId(Context $r0) throws  {
        AttributionIdentifiers $r1 = getAndroidIdViaReflection($r0);
        if ($r1 != null) {
            return $r1;
        }
        $r1 = getAndroidIdViaService($r0);
        if ($r1 == null) {
            return new AttributionIdentifiers();
        }
        return $r1;
    }

    private static AttributionIdentifiers getAndroidIdViaService(Context $r0) throws  {
        GoogleAdServiceConnection $r2 = new GoogleAdServiceConnection();
        Intent $r4 = new Intent("com.google.android.gms.ads.identifier.service.START");
        $r4.setPackage("com.google.android.gms");
        if ($r0.bindService($r4, $r2, 1)) {
            try {
                GoogleAdInfo $r1 = new GoogleAdInfo($r2.getBinder());
                AttributionIdentifiers $r6 = new AttributionIdentifiers();
                $r6.androidAdvertiserId = $r1.getAdvertiserId();
                $r6.limitTracking = $r1.isTrackingLimited();
                return $r6;
            } catch (Exception $r3) {
                Utility.logd("android_id", $r3);
            } finally {
                $r0.unbindService($r2);
            }
        }
        return null;
    }

    private static AttributionIdentifiers cacheAndReturnIdentifiers(AttributionIdentifiers $r0) throws  {
        $r0.fetchTime = System.currentTimeMillis();
        recentlyFetchedIdentifiers = $r0;
        return $r0;
    }

    public String getAttributionId() throws  {
        return this.attributionId;
    }

    public String getAndroidAdvertiserId() throws  {
        return this.androidAdvertiserId;
    }

    public String getAndroidInstallerPackage() throws  {
        return this.androidInstallerPackage;
    }

    public boolean isTrackingLimited() throws  {
        return this.limitTracking;
    }

    @Nullable
    private static String getInstallerPackageName(Context $r0) throws  {
        PackageManager $r1 = $r0.getPackageManager();
        if ($r1 != null) {
            return $r1.getInstallerPackageName($r0.getPackageName());
        }
        return null;
    }
}
