package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqn implements ConnectionCallbacks, OnConnectionFailedListener {
    private final int DG;
    private zzqy DH;
    public final Api<?> zn;

    public zzqn(@Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int $i0) throws  {
        this.zn = $r1;
        this.DG = $i0;
    }

    private void zzasr() throws  {
        zzab.zzb(this.DH, (Object) "Callbacks must be attached to a GoogleApiClient instance before connecting the client.");
    }

    public void onConnected(@Nullable Bundle $r1) throws  {
        zzasr();
        this.DH.onConnected($r1);
    }

    public void onConnectionFailed(@NonNull ConnectionResult $r1) throws  {
        zzasr();
        this.DH.zza($r1, this.zn, this.DG);
    }

    public void onConnectionSuspended(int $i0) throws  {
        zzasr();
        this.DH.onConnectionSuspended($i0);
    }

    public void zza(zzqy $r1) throws  {
        this.DH = $r1;
    }
}
