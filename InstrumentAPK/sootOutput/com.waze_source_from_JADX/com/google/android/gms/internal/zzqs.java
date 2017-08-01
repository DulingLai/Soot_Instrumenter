package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqs extends GoogleApiClient {
    private final UnsupportedOperationException DX;

    public zzqs(String $r1) throws  {
        this.DX = new UnsupportedOperationException($r1);
    }

    public ConnectionResult blockingConnect() throws  {
        throw this.DX;
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) throws  {
        throw this.DX;
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() throws  {
        throw this.DX;
    }

    public void connect() throws  {
        throw this.DX;
    }

    public void disconnect() throws  {
        throw this.DX;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws  {
        throw this.DX;
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Lcom/google/android/gms/common/ConnectionResult;"}) Api<?> api) throws  {
        throw this.DX;
    }

    public boolean hasConnectedApi(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)Z"}) Api<?> api) throws  {
        throw this.DX;
    }

    public boolean isConnected() throws  {
        throw this.DX;
    }

    public boolean isConnecting() throws  {
        throw this.DX;
    }

    public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) throws  {
        throw this.DX;
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) throws  {
        throw this.DX;
    }

    public void reconnect() throws  {
        throw this.DX;
    }

    public void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) throws  {
        throw this.DX;
    }

    public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) throws  {
        throw this.DX;
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) throws  {
        throw this.DX;
    }

    public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) throws  {
        throw this.DX;
    }

    public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) throws  {
        throw this.DX;
    }
}
