package com.google.android.gms.gcm.nts;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import dalvik.annotation.Signature;
import java.util.List;

@Deprecated
/* compiled from: dalvik_source_com.waze.apk */
public class GcmScheduler {
    public static final String ACTION_TASK_EVENT = "com.google.android.gms.gcm.nts.TASK_READY";
    public static final String INTENT_PARAM_CALLBACK = "callback";
    public static final String INTENT_PARAM_TAG = "tag";
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    private static GcmScheduler aru;
    private ComponentName aqK;
    private Context mContext;
    private final PendingIntent mPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(), 0);

    private GcmScheduler(Context $r1) throws  {
        this.mContext = $r1;
    }

    public static synchronized GcmScheduler get(Context $r0) throws  {
        Class cls = GcmScheduler.class;
        synchronized (cls) {
            try {
                if (aru == null) {
                    aru = new GcmScheduler($r0);
                }
                GcmScheduler $r1 = aru;
                return $r1;
            } finally {
                cls = GcmScheduler.class;
            }
        }
    }

    private Intent zzbqt() throws  {
        int $i0 = GoogleCloudMessaging.getGcmVersion(this.mContext);
        if ($i0 < GoogleCloudMessaging.aqV) {
            Log.e("GcmScheduler", "Google Play Services is not available, dropping scheduler request. code=" + $i0);
            return null;
        }
        Intent $r4 = new Intent("com.google.android.gms.gcm.nts.ACTION_SCHEDULE");
        $r4.setPackage(GoogleCloudMessaging.getGcmPackage(this.mContext));
        $r4.putExtra("app", this.mPendingIntent);
        zzu($r4);
        return $r4;
    }

    public void cancelAllTasks() throws  {
        Intent $r1 = zzbqt();
        if ($r1 != null) {
            $r1.putExtra("scheduler_action", "NTS_CANCEL_ALL");
            this.mContext.sendBroadcast($r1);
        }
    }

    public void cancelTask(String $r1) throws  {
        Intent $r2 = zzbqt();
        if ($r2 != null) {
            $r2.putExtra("scheduler_action", "NTS_CANCEL_TASK");
            if ($r1 != null) {
                $r2.putExtra(INTENT_PARAM_TAG, $r1);
            }
            this.mContext.sendBroadcast($r2);
        }
    }

    public void schedulePeriodicTask(long $l0, long $l1, String $r1) throws  {
        Intent $r2 = zzbqt();
        if ($r2 != null) {
            $r2.putExtra("scheduler_action", "NTS_SCHEDULE_RECURRING");
            $r2.putExtra("period_flex", $l1);
            $r2.putExtra("period", $l0);
            if ($r1 != null) {
                $r2.putExtra(INTENT_PARAM_TAG, $r1);
            }
            this.mContext.sendBroadcast($r2);
        }
    }

    public void scheduleTask(long $l0, long $l1, String $r1) throws  {
        Intent $r2 = zzbqt();
        if ($r2 != null) {
            $r2.putExtra("scheduler_action", "ACTION_SCHEDULE");
            $r2.putExtra("window_start", $l0);
            $r2.putExtra("window_end", $l1);
            if ($r1 != null) {
                $r2.putExtra(INTENT_PARAM_TAG, $r1);
            }
            this.mContext.sendBroadcast($r2);
        }
    }

    public GcmScheduler setService(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/gcm/nts/NetworkTaskService;", ">;)", "Lcom/google/android/gms/gcm/nts/GcmScheduler;"}) Class<? extends NetworkTaskService> $r1) throws  {
        this.aqK = new ComponentName(this.mContext, $r1);
        return this;
    }

    void zzu(Intent $r1) throws  {
        if (this.aqK == null) {
            Intent $r3 = new Intent(ACTION_TASK_EVENT);
            $r3.setPackage(this.mContext.getPackageName());
            List<ResolveInfo> $r7 = this.mContext.getPackageManager().queryIntentServices($r3, 0);
            if ($r7 == null || $r7.size() == 0) {
                Log.e("GcmScheduler", "There is no TaskService component registered within this package. Have you extended NetworkTaskService correctly?");
                throw new IllegalArgumentException("There is no TaskService component registered within this package. Have you extended NetworkTaskService correctly?");
            } else if ($r7.size() > 1) {
                String $r5 = "There are multiple TaskService components registered for this package. You must setService() to distinguish between them for your task.";
                for (ResolveInfo $r11 : $r7) {
                    $r5 = String.valueOf($r5);
                    String $r13 = $r11.serviceInfo;
                    String $r12 = $r13;
                    $r13 = $r13.name;
                    String str = $r13;
                    str = String.valueOf($r13);
                    $r5 = new StringBuilder((String.valueOf($r5).length() + 2) + String.valueOf(str).length()).append($r5).append(" ").append(str).append(" ").toString();
                }
                Log.e("GcmScheduler", $r5);
                throw new IllegalArgumentException($r5);
            } else {
                Context $r4 = this.mContext;
                ServiceInfo $r122 = ((ResolveInfo) $r7.get(0)).serviceInfo;
                this.aqK = new ComponentName($r4, $r122.name);
            }
        }
        Intent intent = $r1;
        intent.putExtra("component", this.aqK);
    }
}
