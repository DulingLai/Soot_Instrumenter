package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.C0643R;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.internal.zztc;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: dalvik_source_com.waze.apk */
public class GooglePlayServicesUtilLight {
    public static boolean BP = false;
    public static boolean BQ = false;
    static boolean BR = false;
    private static String BS = null;
    private static int BT = 0;
    private static boolean BU = false;
    static final AtomicBoolean BV = new AtomicBoolean();
    private static final AtomicBoolean BW = new AtomicBoolean();
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zzarh();
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    GooglePlayServicesUtilLight() throws  {
    }

    public static void enableUsingApkIndependentContext() throws  {
        BW.set(true);
    }

    @Deprecated
    public static int getApkVersion(@Deprecated Context $r0) throws  {
        try {
            return $r0.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    @Deprecated
    public static int getClientVersion(@Deprecated Context $r0) throws  {
        zzab.zzbm(true);
        return zzd.zzp($r0, $r0.getPackageName());
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(@Deprecated int $i0, @Deprecated Context $r0, @Deprecated int $i1) throws  {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent($r0, $i0, $i1);
    }

    @Deprecated
    public static String getErrorString(@Deprecated int $i0) throws  {
        return ConnectionResult.getStatusString($i0);
    }

    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(@Deprecated int $i0) throws  {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(null, $i0, null);
    }

    @java.lang.Deprecated
    public static java.lang.String getOpenSourceSoftwareLicenseInfo(@java.lang.Deprecated android.content.Context r11) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0045 in list []
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
        r0 = new android.net.Uri$Builder;
        r0.<init>();
        r1 = "android.resource";
        r0 = r0.scheme(r1);
        r1 = "com.google.android.gms";
        r0 = r0.authority(r1);
        r1 = "raw";
        r0 = r0.appendPath(r1);
        r1 = "third_party_licenses";
        r0 = r0.appendPath(r1);
        r2 = r0.build();
        r3 = r11.getContentResolver();	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        r4 = r3.openInputStream(r2);	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        r5 = new java.util.Scanner;
        r5.<init>(r4);	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        r1 = "\\A";	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        r5 = r5.useDelimiter(r1);	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        r6 = r5.next();	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        if (r4 == 0) goto L_0x0051;
    L_0x003b:
        r4.close();	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
        return r6;
    L_0x003f:
        r7 = move-exception;
        if (r4 == 0) goto L_0x0045;	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
    L_0x0042:
        r4.close();	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
    L_0x0045:
        r8 = 0;
        return r8;
    L_0x0047:
        r9 = move-exception;
        if (r4 == 0) goto L_0x004d;	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
    L_0x004a:
        r4.close();	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
    L_0x004d:
        throw r9;	 Catch:{ NoSuchElementException -> 0x003f, Throwable -> 0x0047, Exception -> 0x004e }
    L_0x004e:
        r10 = move-exception;
        r8 = 0;
        return r8;
    L_0x0051:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GooglePlayServicesUtilLight.getOpenSourceSoftwareLicenseInfo(android.content.Context):java.lang.String");
    }

    public static Context getRemoteContext(Context $r0) throws  {
        try {
            return $r0.createPackageContext("com.google.android.gms", 3);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context $r0) throws  {
        try {
            return $r0.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean honorsDebugCertificates(Context context) throws  {
        return true;
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(@Deprecated Context $r0) throws  {
        PackageManager $r1 = $r0.getPackageManager();
        try {
            $r0.getResources().getString(C0643R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!"com.google.android.gms".equals($r0.getPackageName())) {
            zzbp($r0);
        }
        boolean $z0 = !zzh.zzcf($r0);
        PackageInfo $r5 = null;
        if ($z0) {
            try {
                $r5 = $r1.getPackageInfo("com.android.vending", 8256);
            } catch (NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                return 9;
            }
        }
        try {
            PackageInfo $r6 = $r1.getPackageInfo("com.google.android.gms", 64);
            GoogleSignatureVerifier $r7 = GoogleSignatureVerifier.getInstance($r0);
            if ($z0) {
                if ($r7.zza($r5, zzd.BO) == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                    return 9;
                }
                if ($r7.zza($r6, $r7.zza($r5, zzd.BO)) == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                    return 9;
                }
            } else if ($r7.zza($r6, zzd.BO) == null) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            }
            int $i0 = zzk.zzjj(GOOGLE_PLAY_SERVICES_VERSION_CODE);
            int $i1 = $r6.versionCode;
            int i = $i1;
            if (zzk.zzjj($i1) < $i0) {
                Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + $r6.versionCode);
                return 2;
            }
            ApplicationInfo $r14 = $r6.applicationInfo;
            if ($r14 == null) {
                try {
                    $r14 = $r1.getApplicationInfo("com.google.android.gms", 0);
                } catch (Throwable $r15) {
                    Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", $r15);
                    return 1;
                }
            }
            return !$r14.enabled ? 3 : 0;
        } catch (NameNotFoundException e2) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(@Deprecated Context $r0, @Deprecated int $i0) throws  {
        return $i0 == 18 ? true : $i0 == 1 ? zzn($r0, "com.google.android.gms") : false;
    }

    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(@Deprecated Context $r0, @Deprecated int $i0) throws  {
        return $i0 == 9 ? zzn($r0, "com.android.vending") : false;
    }

    @Deprecated
    public static boolean isSidewinderDevice(@Deprecated Context $r0) throws  {
        return zzh.zzcg($r0);
    }

    @Deprecated
    public static boolean isUserRecoverableError(@Deprecated int $i0) throws  {
        switch ($i0) {
            case 1:
            case 2:
            case 3:
            case 9:
                return true;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            default:
                break;
        }
        return false;
    }

    @TargetApi(19)
    @Deprecated
    public static boolean zza(@Deprecated Context $r0, @Deprecated int $i0, @Deprecated String $r1) throws  {
        return zzx.zza($r0, $i0, $r1);
    }

    private static int zzarh() throws  {
        return zze.IN;
    }

    @Deprecated
    public static void zzbb(@Deprecated Context $r0) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int $i0 = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable($r0);
        if ($i0 != 0) {
            Intent $r2 = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent($r0, $i0, "e");
            Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + $i0);
            if ($r2 == null) {
                throw new GooglePlayServicesNotAvailableException($i0);
            }
            throw new GooglePlayServicesRepairableException($i0, "Google Play Services not available", $r2);
        }
    }

    @java.lang.Deprecated
    public static void zzbn(@java.lang.Deprecated android.content.Context r8) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001e in list []
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
        r0 = BV;
        r2 = 1;
        r1 = r0.getAndSet(r2);
        if (r1 == 0) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        r4 = "notification";	 Catch:{ SecurityException -> 0x001c }
        r3 = r8.getSystemService(r4);	 Catch:{ SecurityException -> 0x001c }
        r6 = r3;
        r6 = (android.app.NotificationManager) r6;
        r5 = r6;
        if (r5 == 0) goto L_0x001e;	 Catch:{ SecurityException -> 0x001c }
    L_0x0016:
        r2 = 10436; // 0x28c4 float:1.4624E-41 double:5.156E-320;	 Catch:{ SecurityException -> 0x001c }
        r5.cancel(r2);	 Catch:{ SecurityException -> 0x001c }
        return;
    L_0x001c:
        r7 = move-exception;
        return;
    L_0x001e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GooglePlayServicesUtilLight.zzbn(android.content.Context):void");
    }

    private static void zzbp(Context $r0) throws  {
        if (!BW.get()) {
            zzbs($r0);
            if (BT == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (BT != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                int $i0 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                int $i1 = BT;
                String $r3 = String.valueOf("com.google.android.gms.version");
                throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 290).append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ").append($i0).append(" but found ").append($i1).append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"").append($r3).append("\" android:value=\"@integer/google_play_services_version\" />").toString());
            }
        }
    }

    public static String zzbq(Context $r0) throws  {
        String $r2 = $r0.getApplicationInfo().name;
        if (!TextUtils.isEmpty($r2)) {
            return $r2;
        }
        ApplicationInfo $r1;
        $r2 = $r0.getPackageName();
        PackageManager $r4 = $r0.getApplicationContext().getPackageManager();
        try {
            $r1 = zztc.zzcl($r0).getApplicationInfo($r0.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            $r1 = null;
        }
        return $r1 != null ? $r4.getApplicationLabel($r1).toString() : $r2;
    }

    @TargetApi(18)
    public static boolean zzbr(Context $r0) throws  {
        if (zzr.zzazg()) {
            Bundle $r4 = ((UserManager) $r0.getSystemService("user")).getApplicationRestrictions($r0.getPackageName());
            if ($r4 != null && ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals($r4.getString("restricted_profile"))) {
                return true;
            }
        }
        return false;
    }

    private static void zzbs(Context $r0) throws  {
        if (!BU) {
            zzbt($r0);
        }
    }

    private static void zzbt(android.content.Context r13) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0036 in list []
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
        r0 = r13.getPackageName();	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        BS = r0;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r1 = com.google.android.gms.internal.zztc.zzcl(r13);	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r2 = com.google.android.gms.common.internal.zzz.zzcb(r13);	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        BT = r2;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r4 = "com.google.android.gms";	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r5 = 64;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r3 = r1.getPackageInfo(r4, r5);	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        if (r3 == 0) goto L_0x0036;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
    L_0x001a:
        r6 = com.google.android.gms.common.GoogleSignatureVerifier.getInstance(r13);	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r5 = 1;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r7 = new com.google.android.gms.common.zzc.zza[r5];	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r8 = com.google.android.gms.common.zzc.zzd.BO;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r5 = 1;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r9 = r8[r5];	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r5 = 0;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r7[r5] = r9;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r9 = r6.zza(r3, r7);	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        if (r9 == 0) goto L_0x0036;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
    L_0x002f:
        r5 = 1;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        BR = r5;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
    L_0x0032:
        r5 = 1;
        BU = r5;
        return;
    L_0x0036:
        r5 = 0;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        BR = r5;	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        goto L_0x0032;
    L_0x003a:
        r10 = move-exception;
        r4 = "GooglePlayServicesUtil";	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r11 = "Cannot find Google Play services package name.";	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        android.util.Log.w(r4, r11, r10);	 Catch:{ NameNotFoundException -> 0x003a, Throwable -> 0x0046 }
        r5 = 1;
        BU = r5;
        return;
    L_0x0046:
        r12 = move-exception;
        r5 = 1;
        BU = r5;
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GooglePlayServicesUtilLight.zzbt(android.content.Context):void");
    }

    @Deprecated
    public static boolean zzd(@Deprecated Context $r0, @Deprecated int $i0) throws  {
        return zzx.zzd($r0, $i0);
    }

    static boolean zzho(int $i0) throws  {
        switch ($i0) {
            case 1:
            case 2:
            case 3:
            case 18:
            case 42:
                return true;
            default:
                return false;
        }
    }

    @android.annotation.TargetApi(21)
    static boolean zzn(android.content.Context r13, java.lang.String r14) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0056 in list []
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
        r1 = "com.google.android.gms";
        r0 = r14.equals(r1);
        if (r0 == 0) goto L_0x0010;
    L_0x0008:
        r0 = com.google.android.gms.common.util.zzd.zzzz();
        if (r0 == 0) goto L_0x0010;
    L_0x000e:
        r2 = 0;
        return r2;
    L_0x0010:
        r0 = com.google.android.gms.common.util.zzr.zzazk();
        if (r0 == 0) goto L_0x0040;
    L_0x0016:
        r3 = r13.getPackageManager();
        r4 = r3.getPackageInstaller();
        r5 = r4.getAllSessions();
        r6 = r5.iterator();
    L_0x0026:
        r0 = r6.hasNext();
        if (r0 == 0) goto L_0x0040;
    L_0x002c:
        r7 = r6.next();
        r9 = r7;
        r9 = (android.content.pm.PackageInstaller.SessionInfo) r9;
        r8 = r9;
        r10 = r8.getAppPackageName();
        r0 = r14.equals(r10);
        if (r0 == 0) goto L_0x0026;
    L_0x003e:
        r2 = 1;
        return r2;
    L_0x0040:
        r3 = r13.getPackageManager();
        r2 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;	 Catch:{ NameNotFoundException -> 0x0058 }
        r11 = r3.getApplicationInfo(r14, r2);	 Catch:{ NameNotFoundException -> 0x0058 }
        r0 = r11.enabled;
        if (r0 == 0) goto L_0x0056;	 Catch:{ NameNotFoundException -> 0x0058 }
    L_0x004e:
        r0 = zzbr(r13);	 Catch:{ NameNotFoundException -> 0x0058 }
        if (r0 != 0) goto L_0x0056;
    L_0x0054:
        r0 = 1;
    L_0x0055:
        return r0;
    L_0x0056:
        r0 = 0;
        goto L_0x0055;
    L_0x0058:
        r12 = move-exception;
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GooglePlayServicesUtilLight.zzn(android.content.Context, java.lang.String):boolean");
    }
}
