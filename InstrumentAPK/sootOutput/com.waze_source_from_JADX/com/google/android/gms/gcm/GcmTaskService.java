package com.google.android.gms.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.util.Log;
import com.google.android.gms.gcm.INetworkTaskCallback.Stub;
import com.google.android.gms.gcm.nts.GcmScheduler;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    private final Set<String> aqQ = new HashSet();
    private int aqR;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza extends Thread {
        private final INetworkTaskCallback aqS;
        final /* synthetic */ GcmTaskService aqT;
        private final Bundle mExtras;
        private final String mTag;

        zza(GcmTaskService $r1, String $r2, IBinder $r3, Bundle $r4) throws  {
            this.aqT = $r1;
            super(String.valueOf($r2).concat(" GCM Task"));
            this.mTag = $r2;
            this.aqS = Stub.asInterface($r3);
            this.mExtras = $r4;
        }

        public void run() throws  {
            Process.setThreadPriority(10);
            try {
                this.aqS.taskFinished(this.aqT.onRunTask(new TaskParams(this.mTag, this.mExtras)));
            } catch (RemoteException e) {
                String $r3 = "Error reporting result of operation to scheduler for ";
                String $r7 = String.valueOf(this.mTag);
                Log.e("GcmTaskService", $r7.length() != 0 ? $r3.concat($r7) : new String("Error reporting result of operation to scheduler for "));
            } finally {
                this.aqT.zzjs(this.mTag);
            }
        }
    }

    private void zzjs(String $r1) throws  {
        synchronized (this.aqQ) {
            this.aqQ.remove($r1);
            if (this.aqQ.size() == 0) {
                stopSelf(this.aqR);
            }
        }
    }

    private void zzvi(int $i0) throws  {
        synchronized (this.aqQ) {
            this.aqR = $i0;
            if (this.aqQ.size() == 0) {
                stopSelf(this.aqR);
            }
        }
    }

    public IBinder onBind(Intent intent) throws  {
        return null;
    }

    public void onInitializeTasks() throws  {
    }

    public abstract int onRunTask(TaskParams taskParams) throws ;

    @CallSuper
    public int onStartCommand(Intent $r1, int i, int $i1) throws  {
        if ($r1 == null) {
            zzvi($i1);
            return 2;
        }
        try {
            $r1.setExtrasClassLoader(PendingCallback.class.getClassLoader());
            String $r4 = $r1.getAction();
            if (SERVICE_ACTION_EXECUTE_TASK.equals($r4)) {
                $r4 = $r1.getStringExtra(GcmScheduler.INTENT_PARAM_TAG);
                Parcelable $r6 = $r1.getParcelableExtra(GcmScheduler.INTENT_PARAM_CALLBACK);
                Bundle $r8 = (Bundle) $r1.getParcelableExtra("extras");
                if ($r6 == null || !($r6 instanceof PendingCallback)) {
                    String $r5 = String.valueOf(getPackageName());
                    int $i0 = (String.valueOf($r5).length() + 47) + String.valueOf($r4).length();
                    i = $i0;
                    Log.e("GcmTaskService", $r5 + " " + $r4 + ": Could not process request, invalid callback.");
                    zzvi($i1);
                    return 2;
                }
                synchronized (this.aqQ) {
                    Set $r12 = this.aqQ;
                    $r12.add($r4);
                }
                new zza(this, $r4, ((PendingCallback) $r6).getIBinder(), $r8).start();
            } else if (SERVICE_ACTION_INITIALIZE.equals($r4)) {
                onInitializeTasks();
            } else {
                Log.e("GcmTaskService", new StringBuilder(String.valueOf($r4).length() + 37).append("Unknown action received ").append($r4).append(", terminating").toString());
            }
            zzvi($i1);
            return 2;
        } catch (Throwable th) {
            zzvi($i1);
        }
    }
}
