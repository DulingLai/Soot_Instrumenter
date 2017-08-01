package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl {
    private AtomicInteger zzaw;
    private final Map<String, Queue<zzk<?>>> zzax;
    private final Set<zzk<?>> zzay;
    private final PriorityBlockingQueue<zzk<?>> zzaz;
    private final PriorityBlockingQueue<zzk<?>> zzba;
    private zzg[] zzbb;
    private zzc zzbc;
    private List<zza> zzbd;
    private final zzb zzi;
    private final zzn zzj;
    private final zzf zzy;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza<T> {
        void zzg(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<TT;>;)V"}) zzk<T> com_google_android_gms_internal_zzk_T) throws ;
    }

    public zzl(zzb $r1, zzf $r2) throws  {
        this($r1, $r2, 4);
    }

    public zzl(zzb $r1, zzf $r2, int $i0) throws  {
        this($r1, $r2, $i0, new zze(new Handler(Looper.getMainLooper())));
    }

    public zzl(zzb $r1, zzf $r2, int $i0, zzn $r3) throws  {
        this.zzaw = new AtomicInteger();
        this.zzax = new HashMap();
        this.zzay = new HashSet();
        this.zzaz = new PriorityBlockingQueue();
        this.zzba = new PriorityBlockingQueue();
        this.zzbd = new ArrayList();
        this.zzi = $r1;
        this.zzy = $r2;
        this.zzbb = new zzg[$i0];
        this.zzj = $r3;
    }

    public int getSequenceNumber() throws  {
        return this.zzaw.incrementAndGet();
    }

    public void start() throws  {
        stop();
        this.zzbc = new zzc(this.zzaz, this.zzba, this.zzi, this.zzj);
        this.zzbc.start();
        for (int $i0 = 0; $i0 < this.zzbb.length; $i0++) {
            zzg $r7 = new zzg(this.zzba, this.zzy, this.zzi, this.zzj);
            this.zzbb[$i0] = $r7;
            $r7.start();
        }
    }

    public void stop() throws  {
        if (this.zzbc != null) {
            this.zzbc.quit();
        }
        for (int $i0 = 0; $i0 < this.zzbb.length; $i0++) {
            if (this.zzbb[$i0] != null) {
                this.zzbb[$i0].quit();
            }
        }
    }

    public <T> zzk<T> zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzk", "<TT;>;)", "Lcom/google/android/gms/internal/zzk", "<TT;>;"}) zzk<T> $r1) throws  {
        $r1.zza(this);
        synchronized (this.zzay) {
            this.zzay.add($r1);
        }
        $r1.zza(getSequenceNumber());
        $r1.zzc("add-to-queue");
        if ($r1.zzq()) {
            synchronized (this.zzax) {
                String $r7 = $r1.zzg();
                if (this.zzax.containsKey($r7)) {
                    Queue $r10 = (Queue) this.zzax.get($r7);
                    if ($r10 == null) {
                        $r10 = r13;
                        LinkedList r13 = new LinkedList();
                    }
                    $r10.add($r1);
                    this.zzax.put($r7, $r10);
                    if (zzs.DEBUG) {
                        zzs.zza("Request for cacheKey=%s is in flight, putting on hold.", $r7);
                    }
                } else {
                    this.zzax.put($r7, null);
                    this.zzaz.add($r1);
                }
            }
            return $r1;
        }
        this.zzba.add($r1);
        return $r1;
    }

    <T> void zzf(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzk", "<TT;>;)V"}) zzk<T> $r1) throws  {
        synchronized (this.zzay) {
            this.zzay.remove($r1);
        }
        synchronized (this.zzbd) {
            for (zza zzg : this.zzbd) {
                zzg.zzg($r1);
            }
        }
        if ($r1.zzq()) {
            synchronized (this.zzax) {
                Queue $r14 = (Queue) this.zzax.remove($r1.zzg());
                if ($r14 != null) {
                    if (zzs.DEBUG) {
                        zzs.zza("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf($r14.size()), $r12);
                    }
                    PriorityBlockingQueue priorityBlockingQueue = this.zzaz;
                    PriorityBlockingQueue $r17 = priorityBlockingQueue;
                    priorityBlockingQueue.addAll($r14);
                }
            }
        }
    }
}
