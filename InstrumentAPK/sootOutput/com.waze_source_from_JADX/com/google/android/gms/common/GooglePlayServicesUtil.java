package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatExtras;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.C0643R;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.common.util.zzr;

/* compiled from: dalvik_source_com.waze.apk */
public final class GooglePlayServicesUtil extends GooglePlayServicesUtilLight {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends Handler {
        private final Context zzard;

        zza(Context $r1) throws  {
            super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
            this.zzard = $r1.getApplicationContext();
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 1:
                    int $i0 = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzard);
                    if (GooglePlayServicesUtil.isUserRecoverableError($i0)) {
                        GooglePlayServicesUtil.zza($i0, this.zzard);
                        return;
                    }
                    return;
                default:
                    String str = "GooglePlayServicesUtil";
                    Log.w(str, "Don't know how to handle this message: " + $r1.what);
                    return;
            }
        }
    }

    private GooglePlayServicesUtil() throws  {
    }

    public static void enableUsingApkIndependentContext() throws  {
        GooglePlayServicesUtilLight.enableUsingApkIndependentContext();
    }

    @Deprecated
    public static int getApkVersion(@Deprecated Context $r0) throws  {
        return GooglePlayServicesUtilLight.getApkVersion($r0);
    }

    @Deprecated
    public static int getClientVersion(@Deprecated Context $r0) throws  {
        return GooglePlayServicesUtilLight.getClientVersion($r0);
    }

    @Deprecated
    public static Dialog getErrorDialog(@Deprecated int $i0, @Deprecated Activity $r0, @Deprecated int $i1) throws  {
        return getErrorDialog($i0, $r0, $i1, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(@Deprecated int $i0, @Deprecated Activity $r0, @Deprecated int $i1, @Deprecated OnCancelListener $r1) throws  {
        return zza($i0, $r0, zzi.zza($r0, GoogleApiAvailability.getInstance().getErrorResolutionIntent($r0, $i0, "d"), $i1), $r1);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(@Deprecated int $i0, @Deprecated Context $r0, @Deprecated int $i1) throws  {
        return GooglePlayServicesUtilLight.getErrorPendingIntent($i0, $r0, $i1);
    }

    @Deprecated
    public static String getErrorString(@Deprecated int $i0) throws  {
        return GooglePlayServicesUtilLight.getErrorString($i0);
    }

    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(@Deprecated int $i0) throws  {
        return GooglePlayServicesUtilLight.getGooglePlayServicesAvailabilityRecoveryIntent($i0);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(@Deprecated Context $r0) throws  {
        return GooglePlayServicesUtilLight.getOpenSourceSoftwareLicenseInfo($r0);
    }

    public static Context getRemoteContext(Context $r0) throws  {
        return GooglePlayServicesUtilLight.getRemoteContext($r0);
    }

    public static Resources getRemoteResource(Context $r0) throws  {
        return GooglePlayServicesUtilLight.getRemoteResource($r0);
    }

    public static boolean honorsDebugCertificates(Context $r0) throws  {
        return GooglePlayServicesUtilLight.honorsDebugCertificates($r0);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(@Deprecated Context $r0) throws  {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable($r0);
    }

    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(@Deprecated Context $r0, @Deprecated int $i0) throws  {
        return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating($r0, $i0);
    }

    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(@Deprecated Context $r0, @Deprecated int $i0) throws  {
        return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating($r0, $i0);
    }

    public static boolean isSidewinderDevice(Context $r0) throws  {
        return GooglePlayServicesUtilLight.isSidewinderDevice($r0);
    }

    @Deprecated
    public static boolean isUserRecoverableError(@Deprecated int $i0) throws  {
        return GooglePlayServicesUtilLight.isUserRecoverableError($i0);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(@Deprecated int $i0, @Deprecated Activity $r0, @Deprecated int $i1) throws  {
        return showErrorDialogFragment($i0, $r0, $i1, null);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(@Deprecated int $i0, @Deprecated Activity $r0, @Deprecated int $i1, @Deprecated OnCancelListener $r1) throws  {
        return showErrorDialogFragment($i0, $r0, null, $i1, $r1);
    }

    public static boolean showErrorDialogFragment(int $i0, Activity $r0, Fragment $r1, int $i1, OnCancelListener $r2) throws  {
        Intent $r4 = GoogleApiAvailability.getInstance().getErrorResolutionIntent($r0, $i0, "d");
        Dialog $r6 = zza($i0, $r0, $r1 == null ? zzi.zza($r0, $r4, $i1) : zzi.zza($r1, $r4, $i1), $r2);
        if ($r6 == null) {
            return false;
        }
        zza($r0, $r2, GMS_ERROR_DIALOG, $r6);
        return true;
    }

    @Deprecated
    public static void showErrorNotification(@Deprecated int $i0, @Deprecated Context $r0) throws  {
        if (zzh.zzcf($r0) && $i0 == 2) {
            $i0 = 42;
        }
        if (isPlayServicesPossiblyUpdating($r0, $i0) || isPlayStorePossiblyUpdating($r0, $i0)) {
            zzbo($r0);
        } else {
            zza($i0, $r0);
        }
    }

    @TargetApi(14)
    public static Dialog zza(int $i0, Activity $r0, zzi $r1, OnCancelListener $r2) throws  {
        Builder $r3 = null;
        if ($i0 == 0) {
            return null;
        }
        if (zzh.zzcf($r0) && $i0 == 2) {
            $i0 = 42;
        }
        if (isPlayServicesPossiblyUpdating($r0, $i0)) {
            $i0 = 18;
        }
        if (zzr.zzazd()) {
            TypedValue $r4 = new TypedValue();
            $r0.getTheme().resolveAttribute(16843529, $r4, true);
            if ("Theme.Dialog.Alert".equals($r0.getResources().getResourceEntryName($r4.resourceId))) {
                $r3 = new Builder($r0, 5);
            }
        }
        if ($r3 == null) {
            $r3 = new Builder($r0);
        }
        $r3.setMessage(com.google.android.gms.common.internal.zzh.zzb($r0, $i0, GooglePlayServicesUtilLight.zzbq($r0)));
        if ($r2 != null) {
            $r3.setOnCancelListener($r2);
        }
        String $r7 = com.google.android.gms.common.internal.zzh.zzg($r0, $i0);
        if ($r7 != null) {
            $r3.setPositiveButton($r7, $r1);
        }
        $r7 = com.google.android.gms.common.internal.zzh.zze($r0, $i0);
        if ($r7 != null) {
            $r3.setTitle($r7);
        }
        return $r3.create();
    }

    private static void zza(int $i0, Context $r0) throws  {
        zza($i0, $r0, null);
    }

    static void zza(int $i0, Context $r0, PendingIntent $r1) throws  {
        zza($i0, $r0, null, $r1);
    }

    private static void zza(int $i0, Context $r0, String $r1) throws  {
        zza($i0, $r0, $r1, GoogleApiAvailability.getInstance().getErrorResolutionPendingIntent($r0, $i0, 0, "n"));
    }

    @TargetApi(20)
    private static void zza(int $i0, Context $r0, String $r1, PendingIntent $r2) throws  {
        Notification $r10;
        char $c3;
        Resources $r3 = $r0.getResources();
        String $r4 = GooglePlayServicesUtilLight.zzbq($r0);
        String $r5 = com.google.android.gms.common.internal.zzh.zzf($r0, $i0);
        String $r6 = $r5;
        if ($r5 == null) {
            $r6 = $r3.getString(C0643R.string.common_google_play_services_notification_ticker);
        }
        $r4 = com.google.android.gms.common.internal.zzh.zzc($r0, $i0, $r4);
        if (zzh.zzcf($r0)) {
            zzab.zzbm(zzr.zzaze());
            $r10 = new Notification.Builder($r0).setSmallIcon(C0643R.drawable.common_ic_googleplayservices).setPriority(2).setAutoCancel(true).setStyle(new BigTextStyle().bigText(new StringBuilder((String.valueOf($r6).length() + 1) + String.valueOf($r4).length()).append($r6).append(" ").append($r4).toString())).addAction(C0643R.drawable.common_full_open_on_phone, $r3.getString(C0643R.string.common_open_on_phone), $r2).build();
        } else {
            $r5 = $r3.getString(C0643R.string.common_google_play_services_notification_ticker);
            if (zzr.zzaza()) {
                Notification.Builder $r7 = new Notification.Builder($r0).setSmallIcon(17301642).setContentTitle($r6).setContentText($r4).setContentIntent($r2).setTicker($r5).setAutoCancel(true);
                if (zzr.zzazi()) {
                    $r7.setLocalOnly(true);
                }
                if (zzr.zzaze()) {
                    $r7.setStyle(new BigTextStyle().bigText($r4));
                    $r10 = $r7.build();
                } else {
                    $r10 = $r7.getNotification();
                }
                if (VERSION.SDK_INT == 19) {
                    $r10.extras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
                }
            } else {
                $r10 = new NotificationCompat.Builder($r0).setSmallIcon(17301642).setTicker($r5).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent($r2).setContentTitle($r6).setContentText($r4).build();
            }
        }
        if (GooglePlayServicesUtilLight.zzho($i0)) {
            GooglePlayServicesUtilLight.BV.set(false);
            $c3 = '⣄';
        } else {
            $c3 = '魭';
        }
        NotificationManager $r13 = (NotificationManager) $r0.getSystemService("notification");
        if ($r1 != null) {
            $r13.notify($r1, $c3, $r10);
        } else {
            $r13.notify($c3, $r10);
        }
    }

    @TargetApi(11)
    public static void zza(Activity $r3, OnCancelListener $r0, String $r1, @NonNull Dialog $r2) throws  {
        boolean $z0;
        try {
            $z0 = $r3 instanceof FragmentActivity;
        } catch (NoClassDefFoundError e) {
            $z0 = false;
        }
        if ($z0) {
            SupportErrorDialogFragment.newInstance($r2, $r0).show(((FragmentActivity) $r3).getSupportFragmentManager(), $r1);
        } else if (zzr.zzaza()) {
            ErrorDialogFragment.newInstance($r2, $r0).show($r3.getFragmentManager(), $r1);
        } else {
            throw new RuntimeException("This Activity does not support Fragments.");
        }
    }

    private static void zzbo(Context $r0) throws  {
        zza $r1 = new zza($r0);
        $r1.sendMessageDelayed($r1.obtainMessage(1), 120000);
    }
}
