package com.google.android.gms.gcm.nts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* compiled from: dalvik_source_com.waze.apk */
public abstract class NetworkTaskService extends Service {
    public static final String PERMISSION_BIND = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    protected static final String TAG = "GcmTaskService";
    private final Set<String> aqQ = new HashSet();
    private int aqR;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza extends Thread {
        private final zza arv;
        final /* synthetic */ NetworkTaskService arw;
        private final String mTag;

        zza(NetworkTaskService $r1, String $r2, IBinder $r3) throws  {
            this.arw = $r1;
            this.mTag = $r2;
            this.arv = com.google.android.gms.gcm.nts.zza.zza.zzkd($r3);
        }

        public void run() throws  {
            try {
                this.arv.taskFinished(this.arw.onRunTask(this.mTag));
            } catch (RemoteException e) {
                String $r2 = "Error reporting result of operation to scheduler for ";
                String $r5 = String.valueOf(this.mTag);
                Log.e(NetworkTaskService.TAG, $r5.length() != 0 ? $r2.concat($r5) : new String("Error reporting result of operation to scheduler for "));
            } finally {
                this.arw.zzjs(this.mTag);
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

    public IBinder onBind(Intent intent) throws  {
        return null;
    }

    public abstract int onRunTask(String str) throws ;

    public int onStartCommand(Intent $r1, int i, int $i1) throws  {
        if (!GcmScheduler.ACTION_TASK_EVENT.equals($r1.getAction())) {
            return 2;
        }
        String $r2 = $r1.getStringExtra(GcmScheduler.INTENT_PARAM_TAG);
        Parcelable $r4 = $r1.getParcelableExtra(GcmScheduler.INTENT_PARAM_CALLBACK);
        if ($r4 == null || !($r4 instanceof PendingCallback)) {
            String $r3 = String.valueOf(getPackageName());
            int $i0 = (String.valueOf($r3).length() + 47) + String.valueOf($r2).length();
            i = $i0;
            Log.e(TAG, $r3 + " " + $r2 + ": Could not process request, invalid callback.");
            return 2;
        }
        synchronized (this.aqQ) {
            this.aqQ.add($r2);
            stopSelf(this.aqR);
            this.aqR = $i1;
        }
        new zza(this, $r2, ((PendingCallback) $r4).getIBinder()).start();
        return 2;
    }
}
