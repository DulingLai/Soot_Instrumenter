package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.auth.api.proxy.ProxyApi.SpatulaHeaderResult;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.auth.api.proxy.ProxyResponse;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zznh implements ProxyApi {
    public PendingResult<SpatulaHeaderResult> getSpatulaHeader(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/proxy/ProxyApi$SpatulaHeaderResult;", ">;"}) GoogleApiClient $r1) throws  {
        zzab.zzag($r1);
        return $r1.zzd(new zzng(this, $r1) {
            final /* synthetic */ zznh fr;

            /* compiled from: dalvik_source_com.waze.apk */
            class C08291 extends zznb {
                final /* synthetic */ C08302 ft;

                C08291(C08302 $r1) throws  {
                    this.ft = $r1;
                }

                public void zzeo(String $r1) throws  {
                    if ($r1 != null) {
                        this.ft.zzc(new zznj($r1));
                    } else {
                        this.ft.zzc(this.ft.zzo(new Status(3006)));
                    }
                }
            }

            protected void zza(Context context, zzne $r2) throws RemoteException {
                $r2.zza(new C08291(this));
            }
        });
    }

    public PendingResult<ProxyResult> performProxyRequest(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/proxy/ProxyRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/proxy/ProxyApi$ProxyResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/proxy/ProxyRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/proxy/ProxyApi$ProxyResult;", ">;"}) final ProxyRequest $r2) throws  {
        zzab.zzag($r1);
        zzab.zzag($r2);
        return $r1.zzd(new zznf(this, $r1) {
            final /* synthetic */ zznh fr;

            /* compiled from: dalvik_source_com.waze.apk */
            class C08271 extends zznb {
                final /* synthetic */ C08281 fs;

                C08271(C08281 $r1) throws  {
                    this.fs = $r1;
                }

                public void zza(ProxyResponse $r1) throws  {
                    this.fs.zzc(new zzni($r1));
                }
            }

            protected void zza(Context context, zzne $r2) throws RemoteException {
                $r2.zza(new C08271(this), $r2);
            }
        });
    }
}
