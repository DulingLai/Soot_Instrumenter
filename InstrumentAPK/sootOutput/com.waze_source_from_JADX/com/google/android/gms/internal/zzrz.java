package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.tasks.TaskCompletionSource;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzrz<A extends zzb, TResult> {
    protected abstract void zza(@Signature({"(TA;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) A a, @Signature({"(TA;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) TaskCompletionSource<TResult> taskCompletionSource) throws RemoteException;
}
