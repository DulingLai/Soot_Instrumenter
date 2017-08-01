package com.google.android.gms.internal;

import com.google.android.gms.internal.zzau.zza;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbo {
    protected static final String TAG = zzbo.class.getSimpleName();
    private final String className;
    private final zzax zzaey;
    private final String zzahf;
    private final int zzahg = 2;
    private volatile Method zzahh = null;
    private List<Class> zzahi;
    private CountDownLatch zzahj = new CountDownLatch(1);

    /* compiled from: dalvik_source_com.waze.apk */
    class C08161 implements Runnable {
        final /* synthetic */ zzbo zzahk;

        C08161(zzbo $r1) throws  {
            this.zzahk = $r1;
        }

        public void run() throws  {
            this.zzahk.zzcx();
        }
    }

    public zzbo(@Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)V"}) zzax $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Class;", ">;)V"}) List<Class> $r4) throws  {
        this.zzaey = $r1;
        this.className = $r2;
        this.zzahf = $r3;
        this.zzahi = new ArrayList($r4);
        this.zzaey.zzcd().submit(new C08161(this));
    }

    private void zzcx() throws  {
        zzax $r1 = this.zzaey;
        this = this;
        try {
            this = this;
            Class $r5 = $r1.zzce().loadClass(zzd(this.zzaey.zzcg(), this.className));
            if ($r5 == null) {
                this.zzahj.countDown();
                return;
            }
            try {
                $r1 = this.zzaey;
                this.zzahh = $r5.getMethod(zzd($r1.zzcg(), this.zzahf), (Class[]) this.zzahi.toArray(new Class[this.zzahi.size()]));
                if (this.zzahh != null) {
                    this.zzahj.countDown();
                }
            } catch (NullPointerException e) {
            } finally {
                this.zzahj.countDown();
            }
        } catch (zza e2) {
            this.zzahj.countDown();
        } catch (UnsupportedEncodingException e3) {
            this.zzahj.countDown();
        } catch (ClassNotFoundException e4) {
            this.zzahj.countDown();
        } catch (NoSuchMethodException e5) {
            this.zzahj.countDown();
        }
    }

    private String zzd(byte[] $r1, String $r2) throws zza, UnsupportedEncodingException {
        return new String(this.zzaey.zzcf().zzc($r1, $r2), "UTF-8");
    }

    public Method zzcy() throws  {
        if (this.zzahh != null) {
            return this.zzahh;
        }
        try {
            return this.zzahj.await(2, TimeUnit.SECONDS) ? this.zzahh : null;
        } catch (InterruptedException e) {
            return null;
        }
    }
}
