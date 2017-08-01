package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.internal.zzsm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: dalvik_source_com.waze.apk */
public final class ImageManager {
    private static final Object Hu = new Object();
    private static HashSet<Uri> Hv = new HashSet();
    private static ImageManager Hw;
    private static ImageManager Hx;
    private final zzsm HA;
    private final Map<zza, ImageReceiver> HB;
    private final Map<Uri, ImageReceiver> HC;
    private final Map<Uri, Long> HD;
    private final ExecutorService Hy = Executors.newFixedThreadPool(4);
    private final zzb Hz;
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @KeepName
    /* compiled from: dalvik_source_com.waze.apk */
    private final class ImageReceiver extends ResultReceiver {
        private final ArrayList<zza> HE = new ArrayList();
        final /* synthetic */ ImageManager HF;
        private final Uri mUri;

        ImageReceiver(ImageManager $r1, Uri $r2) throws  {
            this.HF = $r1;
            super(new Handler(Looper.getMainLooper()));
            this.mUri = $r2;
        }

        public void onReceiveResult(int i, Bundle $r1) throws  {
            this.HF.Hy.execute(new zzc(this.HF, this.mUri, (ParcelFileDescriptor) $r1.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public void zzavm() throws  {
            Intent $r1 = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            $r1.putExtra("com.google.android.gms.extras.uri", this.mUri);
            $r1.putExtra("com.google.android.gms.extras.resultReceiver", this);
            $r1.putExtra("com.google.android.gms.extras.priority", 3);
            this.HF.mContext.sendBroadcast($r1);
        }

        public void zzb(zza $r1) throws  {
            com.google.android.gms.common.internal.zzb.zzgp("ImageReceiver.addImageRequest() must be called in the main thread");
            this.HE.add($r1);
        }

        public void zzc(zza $r1) throws  {
            com.google.android.gms.common.internal.zzb.zzgp("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.HE.remove($r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z) throws ;
    }

    @TargetApi(11)
    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza {
        static int zza(ActivityManager $r0) throws  {
            return $r0.getLargeMemoryClass();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb extends LruCache<zza, Bitmap> {
        public zzb(Context $r1) throws  {
            super(zzbx($r1));
        }

        @TargetApi(11)
        private static int zzbx(Context $r0) throws  {
            ActivityManager $r2 = (ActivityManager) $r0.getSystemService(RecoveryParamConstants.VALUE_ACTIVITY);
            int $i0 = ((($r0.getApplicationInfo().flags & 1048576) != 0) && zzr.zzaza()) ? zza.zza($r2) : $r2.getMemoryClass();
            return (int) (((float) ($i0 * 1048576)) * 0.33f);
        }

        protected /* synthetic */ void entryRemoved(boolean $z0, Object $r1, Object $r2, Object $r3) throws  {
            zza($z0, (zza) $r1, (Bitmap) $r2, (Bitmap) $r3);
        }

        protected /* synthetic */ int sizeOf(Object $r1, Object $r2) throws  {
            return zza((zza) $r1, (Bitmap) $r2);
        }

        protected int zza(zza com_google_android_gms_common_images_zza_zza, Bitmap $r2) throws  {
            return $r2.getHeight() * $r2.getRowBytes();
        }

        protected void zza(boolean $z0, zza $r1, Bitmap $r2, Bitmap $r3) throws  {
            super.entryRemoved($z0, $r1, $r2, $r3);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zzc implements Runnable {
        final /* synthetic */ ImageManager HF;
        private final ParcelFileDescriptor HG;
        private final Uri mUri;

        public zzc(ImageManager $r1, Uri $r2, ParcelFileDescriptor $r3) throws  {
            this.HF = $r1;
            this.mUri = $r2;
            this.HG = $r3;
        }

        public void run() throws  {
            com.google.android.gms.common.internal.zzb.zzgq("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean $z0 = false;
            Bitmap $r1 = null;
            if (this.HG != null) {
                try {
                    $r1 = BitmapFactory.decodeFileDescriptor(this.HG.getFileDescriptor());
                } catch (Throwable $r9) {
                    Uri $r8 = this.mUri;
                    String $r10 = String.valueOf($r8);
                    Log.e("ImageManager", new StringBuilder(String.valueOf($r10).length() + 34).append("OOM while loading bitmap for uri: ").append($r10).toString(), $r9);
                    $z0 = true;
                }
                try {
                    this.HG.close();
                } catch (Throwable $r13) {
                    Log.e("ImageManager", "closed failed", $r13);
                }
            }
            CountDownLatch $r4 = new CountDownLatch(1);
            this.HF.mHandler.post(new zzf(this.HF, this.mUri, $r1, $z0, $r4));
            try {
                $r4.await();
            } catch (InterruptedException e) {
                $r8 = this.mUri;
                $r10 = String.valueOf($r8);
                Log.w("ImageManager", new StringBuilder(String.valueOf($r10).length() + 32).append("Latch interrupted while posting ").append($r10).toString());
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zzd implements Runnable {
        final /* synthetic */ ImageManager HF;
        private final zza HH;

        public zzd(ImageManager $r1, zza $r2) throws  {
            this.HF = $r1;
            this.HH = $r2;
        }

        public void run() throws  {
            com.google.android.gms.common.internal.zzb.zzgp("LoadImageRunnable must be executed on the main thread");
            ImageReceiver $r5 = (ImageReceiver) this.HF.HB.get(this.HH);
            if ($r5 != null) {
                this.HF.HB.remove(this.HH);
                $r5.zzc(this.HH);
            }
            zza $r6 = this.HH.HJ;
            if ($r6.uri == null) {
                this.HH.zza(this.HF.mContext, this.HF.HA, true);
                return;
            }
            Bitmap $r10 = this.HF.zza($r6);
            if ($r10 != null) {
                this.HH.zza(this.HF.mContext, $r10, true);
                return;
            }
            Long l = (Long) this.HF.HD.get($r6.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.HH.zza(this.HF.mContext, this.HF.HA, true);
                    return;
                }
                this.HF.HD.remove($r6.uri);
            }
            this.HH.zza(this.HF.mContext, this.HF.HA);
            $r5 = (ImageReceiver) this.HF.HC.get($r6.uri);
            if ($r5 == null) {
                $r5 = new ImageReceiver(this.HF, $r6.uri);
                this.HF.HC.put($r6.uri, $r5);
            }
            $r5.zzb(this.HH);
            if (!(this.HH instanceof com.google.android.gms.common.images.zza.zzc)) {
                this.HF.HB.put(this.HH, $r5);
            }
            synchronized (ImageManager.Hu) {
                if (!ImageManager.Hv.contains($r6.uri)) {
                    ImageManager.Hv.add($r6.uri);
                    $r5.zzavm();
                }
            }
        }
    }

    @TargetApi(14)
    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zze implements ComponentCallbacks2 {
        private final zzb Hz;

        public zze(zzb $r1) throws  {
            this.Hz = $r1;
        }

        public void onConfigurationChanged(Configuration configuration) throws  {
        }

        public void onLowMemory() throws  {
            this.Hz.evictAll();
        }

        public void onTrimMemory(int $i0) throws  {
            if ($i0 >= 60) {
                this.Hz.evictAll();
            } else if ($i0 >= 20) {
                this.Hz.trimToSize(this.Hz.size() / 2);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zzf implements Runnable {
        final /* synthetic */ ImageManager HF;
        private boolean HI;
        private final Bitmap mBitmap;
        private final Uri mUri;
        private final CountDownLatch zzalw;

        public zzf(ImageManager $r1, Uri $r2, Bitmap $r3, boolean $z0, CountDownLatch $r4) throws  {
            this.HF = $r1;
            this.mUri = $r2;
            this.mBitmap = $r3;
            this.HI = $z0;
            this.zzalw = $r4;
        }

        private void zza(ImageReceiver $r1, boolean $z0) throws  {
            ArrayList $r2 = $r1.HE;
            int $i0 = $r2.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                zza $r4 = (zza) $r2.get($i1);
                if ($z0) {
                    $r4.zza(this.HF.mContext, this.mBitmap, false);
                } else {
                    this.HF.HD.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                    $r4.zza(this.HF.mContext, this.HF.HA, false);
                }
                if (!($r4 instanceof com.google.android.gms.common.images.zza.zzc)) {
                    this.HF.HB.remove($r4);
                }
            }
        }

        public void run() throws  {
            com.google.android.gms.common.internal.zzb.zzgp("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean $z0 = this.mBitmap != null;
            if (this.HF.Hz != null) {
                if (this.HI) {
                    this.HF.Hz.evictAll();
                    System.gc();
                    this.HI = false;
                    this.HF.mHandler.post(this);
                    return;
                } else if ($z0) {
                    this.HF.Hz.put(new zza(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver $r9 = (ImageReceiver) this.HF.HC.remove(this.mUri);
            if ($r9 != null) {
                zza($r9, $z0);
            }
            this.zzalw.countDown();
            synchronized (ImageManager.Hu) {
                ImageManager.Hv.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context $r1, boolean $z0) throws  {
        this.mContext = $r1.getApplicationContext();
        if ($z0) {
            this.Hz = new zzb(this.mContext);
            if (zzr.zzazd()) {
                zzavk();
            }
        } else {
            this.Hz = null;
        }
        this.HA = new zzsm();
        this.HB = new HashMap();
        this.HC = new HashMap();
        this.HD = new HashMap();
    }

    public static ImageManager create(Context $r0) throws  {
        return zzg($r0, false);
    }

    private Bitmap zza(zza $r1) throws  {
        return this.Hz == null ? null : (Bitmap) this.Hz.get($r1);
    }

    @TargetApi(14)
    private void zzavk() throws  {
        this.mContext.registerComponentCallbacks(new zze(this.Hz));
    }

    public static ImageManager zzg(Context $r0, boolean $z0) throws  {
        if ($z0) {
            if (Hx == null) {
                Hx = new ImageManager($r0, true);
            }
            return Hx;
        }
        if (Hw == null) {
            Hw = new ImageManager($r0, false);
        }
        return Hw;
    }

    public void loadImage(ImageView $r1, int $i0) throws  {
        zza(new com.google.android.gms.common.images.zza.zzb($r1, $i0));
    }

    public void loadImage(ImageView $r1, Uri $r2) throws  {
        zza(new com.google.android.gms.common.images.zza.zzb($r1, $r2));
    }

    public void loadImage(ImageView $r1, Uri $r2, int $i0) throws  {
        zza $r3 = new com.google.android.gms.common.images.zza.zzb($r1, $r2);
        $r3.zzih($i0);
        zza($r3);
    }

    public void loadImage(OnImageLoadedListener $r1, Uri $r2) throws  {
        zza(new com.google.android.gms.common.images.zza.zzc($r1, $r2));
    }

    public void loadImage(OnImageLoadedListener $r1, Uri $r2, int $i0) throws  {
        zza $r3 = new com.google.android.gms.common.images.zza.zzc($r1, $r2);
        $r3.zzih($i0);
        zza($r3);
    }

    public void zza(zza $r1) throws  {
        com.google.android.gms.common.internal.zzb.zzgp("ImageManager.loadImage() must be called in the main thread");
        new zzd(this, $r1).run();
    }
}
