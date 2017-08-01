package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqi extends zzql {
    private final SparseArray<zza> Dd = new SparseArray();

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza implements OnConnectionFailedListener {
        public final GoogleApiClient De;
        public final OnConnectionFailedListener Df;
        final /* synthetic */ zzqi Dg;
        public final int clientId;

        public zza(zzqi $r1, int $i0, GoogleApiClient $r2, OnConnectionFailedListener $r3) throws  {
            this.Dg = $r1;
            this.clientId = $i0;
            this.De = $r2;
            this.Df = $r3;
            $r2.registerConnectionFailedListener(this);
        }

        public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
            $r3.append($r1).append("GoogleApiClient #").print(this.clientId);
            $r3.println(":");
            this.De.dump(String.valueOf($r1).concat("  "), $r2, $r3, $r4);
        }

        public void onConnectionFailed(@NonNull ConnectionResult $r1) throws  {
            String $r2 = String.valueOf($r1);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf($r2).length() + 27).append("beginFailureResolution for ").append($r2).toString());
            this.Dg.zzb($r1, this.clientId);
        }

        public void zzasg() throws  {
            this.De.unregisterConnectionFailedListener(this);
            this.De.disconnect();
        }
    }

    private zzqi(zzri $r1) throws  {
        super($r1);
        this.FM.zza("AutoManageHelper", (zzrh) this);
    }

    public static zzqi zza(zzrg $r0) throws  {
        zzri $r1 = zzrh.zzc($r0);
        zzqi $r3 = (zzqi) $r1.zza("AutoManageHelper", zzqi.class);
        return $r3 != null ? $r3 : new zzqi($r1);
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        for (int $i0 = 0; $i0 < this.Dd.size(); $i0++) {
            ((zza) this.Dd.valueAt($i0)).dump($r1, $r2, $r3, $r4);
        }
    }

    public void onStart() throws  {
        super.onStart();
        boolean $z0 = this.mStarted;
        String $r2 = String.valueOf(this.Dd);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf($r2).length() + 14).append("onStart ").append($z0).append(" ").append($r2).toString());
        if (!this.Dl) {
            for (int $i0 = 0; $i0 < this.Dd.size(); $i0++) {
                ((zza) this.Dd.valueAt($i0)).De.connect();
            }
        }
    }

    public void onStop() throws  {
        super.onStop();
        for (int $i0 = 0; $i0 < this.Dd.size(); $i0++) {
            ((zza) this.Dd.valueAt($i0)).De.disconnect();
        }
    }

    public void zza(int $i0, GoogleApiClient $r1, OnConnectionFailedListener $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "GoogleApiClient instance cannot be null");
        zzab.zza(this.Dd.indexOfKey($i0) < 0, "Already managing a GoogleApiClient with id " + $i0);
        Log.d("AutoManageHelper", "starting AutoManage for client " + $i0 + " " + this.mStarted + " " + this.Dl);
        this.Dd.put($i0, new zza(this, $i0, $r1, $r2));
        if (this.mStarted && !this.Dl) {
            String $r5 = String.valueOf($r1);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf($r5).length() + 11).append("connecting ").append($r5).toString());
            $r1.connect();
        }
    }

    protected void zza(ConnectionResult $r1, int $i0) throws  {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if ($i0 < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza $r5 = (zza) this.Dd.get($i0);
        if ($r5 != null) {
            zzhs($i0);
            OnConnectionFailedListener $r6 = $r5.Df;
            if ($r6 != null) {
                $r6.onConnectionFailed($r1);
            }
        }
    }

    protected void zzasf() throws  {
        for (int $i0 = 0; $i0 < this.Dd.size(); $i0++) {
            ((zza) this.Dd.valueAt($i0)).De.connect();
        }
    }

    public void zzhs(int $i0) throws  {
        zza $r3 = (zza) this.Dd.get($i0);
        this.Dd.remove($i0);
        if ($r3 != null) {
            $r3.zzasg();
        }
    }
}
