package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;
import com.google.android.gms.C0643R;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzrc;
import com.google.android.gms.internal.zzrc.zza;
import com.google.android.gms.internal.zzri;
import com.google.android.gms.internal.zzro;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleApiAvailability extends GoogleApiAvailabilityLight {
    private static final GoogleApiAvailability BE = new GoogleApiAvailability();
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;

    GoogleApiAvailability() throws  {
    }

    public static GoogleApiAvailability getInstance() throws  {
        return BE;
    }

    public int getApkVersion(Context $r1) throws  {
        return super.getApkVersion($r1);
    }

    public int getClientVersion(Context $r1) throws  {
        return super.getClientVersion($r1);
    }

    public Dialog getErrorDialog(Activity $r1, int $i0, int $i1) throws  {
        return GooglePlayServicesUtil.getErrorDialog($i0, $r1, $i1);
    }

    public Dialog getErrorDialog(Activity $r1, int $i0, int $i1, OnCancelListener $r2) throws  {
        return GooglePlayServicesUtil.getErrorDialog($i0, $r1, $i1, $r2);
    }

    @Nullable
    @Deprecated
    public Intent getErrorResolutionIntent(@Deprecated int $i0) throws  {
        return super.getErrorResolutionIntent($i0);
    }

    @Nullable
    public Intent getErrorResolutionIntent(Context $r1, int $i0, @Nullable String $r2) throws  {
        return super.getErrorResolutionIntent($r1, $i0, $r2);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context $r1, int $i0, int $i1) throws  {
        return super.getErrorResolutionPendingIntent($r1, $i0, $i1);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context $r1, int $i0, int $i1, @Nullable String $r2) throws  {
        return super.getErrorResolutionPendingIntent($r1, $i0, $i1, $r2);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context $r1, ConnectionResult $r2) throws  {
        if ($r2.hasResolution()) {
            return $r2.getResolution();
        }
        int $i0 = $r2.getErrorCode();
        int $i1 = $i0;
        if (zzh.zzcf($r1) && $i0 == 2) {
            $i1 = 42;
        }
        return getErrorResolutionPendingIntent($r1, $i1, 0);
    }

    public final String getErrorString(int $i0) throws  {
        return super.getErrorString($i0);
    }

    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context $r1) throws  {
        return super.getOpenSourceSoftwareLicenseInfo($r1);
    }

    public int isGooglePlayServicesAvailable(Context $r1) throws  {
        return super.isGooglePlayServicesAvailable($r1);
    }

    public boolean isPlayServicesPossiblyUpdating(Context $r1, int $i0) throws  {
        return super.isPlayServicesPossiblyUpdating($r1, $i0);
    }

    public boolean isPlayStorePossiblyUpdating(Context $r1, int $i0) throws  {
        return super.isPlayStorePossiblyUpdating($r1, $i0);
    }

    public final boolean isUserResolvableError(int $i0) throws  {
        return super.isUserResolvableError($i0);
    }

    @MainThread
    public Task<Void> makeGooglePlayServicesAvailable(@Signature({"(", "Landroid/app/Activity;", ")", "Lcom/google/android/gms/tasks/Task", "<", "Ljava/lang/Void;", ">;"}) Activity $r1) throws  {
        zzab.zzgp("makeGooglePlayServicesAvailable must be called from the main thread");
        int $i0 = isGooglePlayServicesAvailable($r1);
        if ($i0 == 0) {
            return Tasks.forResult(null);
        }
        zzro $r4 = zzro.zzv($r1);
        $r4.zzk(new ConnectionResult($i0, null));
        return $r4.getTask();
    }

    public boolean showErrorDialogFragment(Activity $r1, int $i0, int $i1) throws  {
        return GooglePlayServicesUtil.showErrorDialogFragment($i0, $r1, $i1);
    }

    public boolean showErrorDialogFragment(Activity $r1, int $i0, int $i1, OnCancelListener $r2) throws  {
        return GooglePlayServicesUtil.showErrorDialogFragment($i0, $r1, $i1, $r2);
    }

    public void showErrorNotification(Context $r1, int $i0) throws  {
        if ($i0 == 6) {
            Log.e("GoogleApiAvailability", "showErrorNotification(context, errorCode) is called for RESOLUTION_REQUIRED when showErrorNotification(context, result) should be called");
        }
        if (isUserResolvableError($i0)) {
            GooglePlayServicesUtil.showErrorNotification($i0, $r1);
        }
    }

    public void showErrorNotification(Context $r1, ConnectionResult $r2) throws  {
        PendingIntent $r3 = getErrorResolutionPendingIntent($r1, $r2);
        if ($r3 != null) {
            GooglePlayServicesUtil.zza($r2.getErrorCode(), $r1, $r3);
        }
    }

    public Dialog zza(Activity $r1, OnCancelListener $r2) throws  {
        ProgressBar $r3 = new ProgressBar($r1, null, 16842874);
        $r3.setIndeterminate(true);
        $r3.setVisibility(0);
        Builder $r4 = new Builder($r1);
        $r4.setView($r3);
        String $r5 = GooglePlayServicesUtilLight.zzbq($r1);
        $r4.setMessage($r1.getResources().getString(C0643R.string.common_google_play_services_updating_text, new Object[]{$r5}));
        $r4.setTitle(C0643R.string.common_google_play_services_updating_title);
        $r4.setPositiveButton("", null);
        Dialog $r8 = $r4.create();
        GooglePlayServicesUtil.zza($r1, $r2, "GooglePlayServicesUpdatingDialog", $r8);
        return $r8;
    }

    @Nullable
    public zzrc zza(Context $r1, zza $r2) throws  {
        IntentFilter $r3 = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        $r3.addDataScheme("package");
        zzrc $r4 = new zzrc($r2);
        $r1.registerReceiver($r4, $r3);
        $r4.setContext($r1);
        if (zzn($r1, "com.google.android.gms")) {
            return $r4;
        }
        $r2.zzasl();
        $r4.unregister();
        return null;
    }

    public void zza(Context $r1, ConnectionResult $r2, int $i0) throws  {
        PendingIntent $r3 = getErrorResolutionPendingIntent($r1, $r2);
        if ($r3 != null) {
            GooglePlayServicesUtil.zza($r2.getErrorCode(), $r1, GoogleApiActivity.zza($r1, $r3, $i0));
        }
    }

    public boolean zza(Activity $r1, @NonNull zzri $r2, int $i0, int $i1, OnCancelListener $r3) throws  {
        Dialog $r6 = GooglePlayServicesUtil.zza($i0, $r1, zzi.zza($r2, getErrorResolutionIntent($r1, $i0, "d"), $i1), $r3);
        if ($r6 == null) {
            return false;
        }
        GooglePlayServicesUtil.zza($r1, $r3, GooglePlayServicesUtil.GMS_ERROR_DIALOG, $r6);
        return true;
    }
}
