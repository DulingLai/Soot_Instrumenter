package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.gcm.OneoffTask.Builder;
import com.google.android.gms.gcm.nts.GcmScheduler;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class GcmNetworkManager {
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    private static GcmNetworkManager aqJ;
    private ComponentName aqK;
    private Context mContext;
    private final PendingIntent mPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(), 0);

    private GcmNetworkManager(Context $r1) throws  {
        this.mContext = $r1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.gcm.GcmNetworkManager getInstance(android.content.Context r3) throws  {
        /*
        r0 = com.google.android.gms.gcm.GcmNetworkManager.class;
        monitor-enter(r0);
        r1 = aqJ;	 Catch:{ Throwable -> 0x0018 }
        if (r1 != 0) goto L_0x0012;
    L_0x0007:
        r1 = new com.google.android.gms.gcm.GcmNetworkManager;	 Catch:{ Throwable -> 0x0018 }
        r3 = r3.getApplicationContext();	 Catch:{ Throwable -> 0x0018 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x0018 }
        aqJ = r1;	 Catch:{ Throwable -> 0x0018 }
    L_0x0012:
        r1 = aqJ;	 Catch:{ Throwable -> 0x0018 }
        r0 = com.google.android.gms.gcm.GcmNetworkManager.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0018 }
        return r1;
    L_0x0018:
        r2 = move-exception;
        r0 = com.google.android.gms.gcm.GcmNetworkManager.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0018 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmNetworkManager.getInstance(android.content.Context):com.google.android.gms.gcm.GcmNetworkManager");
    }

    private void zza(String $r1, ComponentName $r2) throws  {
        zzjn($r1);
        zzjo($r2.getClassName());
        Intent $r4 = zzbqt();
        if ($r4 != null) {
            $r4.putExtra("scheduler_action", "CANCEL_TASK");
            $r4.putExtra(GcmScheduler.INTENT_PARAM_TAG, $r1);
            $r4.putExtra("component", $r2);
            this.mContext.sendBroadcast($r4);
        }
    }

    private Intent zzbqt() throws  {
        int $i0 = GoogleCloudMessaging.getGcmVersion(this.mContext);
        if ($i0 < GoogleCloudMessaging.aqU) {
            Log.e("GcmNetworkManager", "Google Play Services is not available, dropping GcmNetworkManager request. code=" + $i0);
            return null;
        }
        Intent $r4 = new Intent("com.google.android.gms.gcm.ACTION_SCHEDULE");
        $r4.setPackage(GoogleCloudMessaging.getGcmPackage(this.mContext));
        $r4.putExtra("app", this.mPendingIntent);
        return $r4;
    }

    private void zzc(ComponentName $r1) throws  {
        zzjo($r1.getClassName());
        Intent $r3 = zzbqt();
        if ($r3 != null) {
            $r3.putExtra("scheduler_action", "CANCEL_ALL");
            $r3.putExtra("component", $r1);
            this.mContext.sendBroadcast($r3);
        }
    }

    static void zzjn(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            throw new IllegalArgumentException("Must provide a valid tag.");
        } else if (100 < $r0.length()) {
            throw new IllegalArgumentException("Tag is larger than max permissible tag length (100)");
        }
    }

    private void zzjo(String $r1) throws  {
        boolean $z0 = true;
        zzab.zzb((Object) $r1, (Object) "GcmTaskService must not be null.");
        Intent $r2 = new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK);
        $r2.setPackage(this.mContext.getPackageName());
        List<ResolveInfo> $r6 = this.mContext.getPackageManager().queryIntentServices($r2, 0);
        boolean $z1 = ($r6 == null || $r6.size() == 0) ? false : true;
        zzab.zzb($z1, (Object) "There is no GcmTaskService component registered within this package. Have you extended GcmTaskService correctly?");
        for (ResolveInfo resolveInfo : $r6) {
            ServiceInfo $r10 = resolveInfo.serviceInfo;
            if ($r10.name.equals($r1)) {
                break;
            }
        }
        $z0 = false;
        zzab.zzb($z0, (Object) new StringBuilder(String.valueOf($r1).length() + 119).append("The GcmTaskService class you provided ").append($r1).append(" does not seem to support receiving com.google.android.gms.gcm.ACTION_TASK_READY.").toString());
    }

    public void cancelAllTasks(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)V"}) Class<? extends GcmTaskService> $r1) throws  {
        cancelAllTasksUnchecked($r1);
    }

    public void cancelAllTasksUnchecked(@Signature({"(", "Ljava/lang/Class", "<+", "Landroid/app/Service;", ">;)V"}) Class<? extends Service> $r1) throws  {
        zzc(new ComponentName(this.mContext, $r1));
    }

    public void cancelTask(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)V"}) Class<? extends GcmTaskService> $r2) throws  {
        cancelTaskUnchecked($r1, $r2);
    }

    public void cancelTaskUnchecked(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<+", "Landroid/app/Service;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<+", "Landroid/app/Service;", ">;)V"}) Class<? extends Service> $r2) throws  {
        zza($r1, new ComponentName(this.mContext, $r2));
    }

    public void schedule(Task $r1) throws  {
        zzjo($r1.getServiceName());
        Intent $r3 = zzbqt();
        if ($r3 != null) {
            Bundle $r4 = $r3.getExtras();
            $r4.putString("scheduler_action", "SCHEDULE_TASK");
            $r1.toBundle($r4);
            $r3.putExtras($r4);
            this.mContext.sendBroadcast($r3);
        }
    }

    @Deprecated
    public void scheduleTask(@Deprecated long $l0, @Deprecated long $l1, @Deprecated String $r1, @Deprecated boolean $z0) throws  {
        zzbqu();
        schedule((OneoffTask) ((Builder) ((Builder) new Builder().zzjv(this.aqK.getClassName()).setExecutionWindow($l0, $l1).setTag($r1)).setUpdateCurrent($z0)).build());
    }

    @Deprecated
    public GcmNetworkManager setService(@Deprecated @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/GcmTaskService;", ">;)", "Lcom/google/android/gms/gcm/GcmNetworkManager;"}) Class<? extends GcmTaskService> $r1) throws  {
        this.aqK = new ComponentName(this.mContext, $r1);
        return this;
    }

    void zzbqu() throws  {
        if (this.aqK == null) {
            Intent $r2 = new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK);
            $r2.setPackage(this.mContext.getPackageName());
            List<ResolveInfo> $r6 = this.mContext.getPackageManager().queryIntentServices($r2, 0);
            if ($r6 == null || $r6.size() == 0) {
                Log.e("GcmNetworkManager", "There is no TaskService component registered within this package. Have you extended GcmTaskService correctly?");
                throw new IllegalArgumentException("There is no TaskService component registered within this package. Have you extended GcmTaskService correctly?");
            } else if ($r6.size() > 1) {
                String $r4 = "There are multiple TaskService components registered for this package. You must setService() to distinguish between them for your task.";
                for (ResolveInfo $r10 : $r6) {
                    $r4 = String.valueOf($r4);
                    String $r12 = $r10.serviceInfo;
                    String $r11 = $r12;
                    $r12 = $r12.name;
                    String str = $r12;
                    str = String.valueOf($r12);
                    $r4 = new StringBuilder((String.valueOf($r4).length() + 2) + String.valueOf(str).length()).append($r4).append(" ").append(str).append(" ").toString();
                }
                Log.e("GcmNetworkManager", $r4);
                throw new IllegalArgumentException($r4);
            } else {
                Context $r3 = this.mContext;
                ServiceInfo $r112 = ((ResolveInfo) $r6.get(0)).serviceInfo;
                this.aqK = new ComponentName($r3, $r112.name);
                return;
            }
        }
        zzjo(this.aqK.getClassName());
    }
}
