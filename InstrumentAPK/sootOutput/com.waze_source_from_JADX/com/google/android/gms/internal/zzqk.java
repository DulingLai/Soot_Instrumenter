package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqk {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzb<R> {
        void setResult(@Signature({"(TR;)V"}) R r) throws ;

        void zzag(Status status) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<R extends Result, A extends com.google.android.gms.common.api.Api.zzb> extends zzqm<R> implements zzb<R> {
        private final zzc<A> Dj;
        private AtomicReference<zzb> Dk = new AtomicReference();
        private final Api<?> zn;

        @Deprecated
        protected zza(@Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/Api$zzc", "<TA;>;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")V"}) zzc<A> $r1, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/Api$zzc", "<TA;>;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")V"}) GoogleApiClient $r2) throws  {
            super((GoogleApiClient) zzab.zzb((Object) $r2, (Object) "GoogleApiClient must not be null"));
            this.Dj = (zzc) zzab.zzag($r1);
            this.zn = null;
        }

        protected zza(@Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")V"}) Api<?> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")V"}) GoogleApiClient $r2) throws  {
            super((GoogleApiClient) zzab.zzb((Object) $r2, (Object) "GoogleApiClient must not be null"));
            this.Dj = $r1.zzarl();
            this.zn = $r1;
        }

        private void zzc(RemoteException $r1) throws  {
            zzag(new Status(8, $r1.getLocalizedMessage(), null));
        }

        public /* synthetic */ void setResult(Object $r1) throws  {
            super.zzc((Result) $r1);
        }

        protected abstract void zza(@Signature({"(TA;)V"}) A a) throws RemoteException;

        public void zza(zzb $r1) throws  {
            this.Dk.set($r1);
        }

        public final void zzag(Status $r1) throws  {
            zzab.zzb(!$r1.isSuccess(), (Object) "Failed result must not be success");
            Result $r2 = zzb($r1);
            zzc($r2);
            zzb($r2);
        }

        public final zzc<A> zzarl() throws  {
            return this.Dj;
        }

        public final Api<?> zzars() throws  {
            return this.zn;
        }

        public void zzasi() throws  {
            setResultCallback(null);
        }

        protected void zzasj() throws  {
            zzb $r3 = (zzb) this.Dk.getAndSet(null);
            if ($r3 != null) {
                $r3.zzh(this);
            }
        }

        public final void zzb(@Signature({"(TA;)V"}) A $r1) throws DeadObjectException {
            try {
                zza((com.google.android.gms.common.api.Api.zzb) $r1);
            } catch (DeadObjectException $r2) {
                zzc($r2);
                throw $r2;
            } catch (RemoteException $r3) {
                zzc($r3);
            }
        }

        protected void zzb(@Signature({"(TR;)V"}) R r) throws  {
        }
    }
}
