package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzsk;
import com.google.android.gms.internal.zzsl;
import com.google.android.gms.internal.zzsm;
import java.lang.ref.WeakReference;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zza {
    final zza HJ;
    protected int HK = 0;
    protected int HL = 0;
    protected boolean HM = false;
    private boolean HN = true;
    private boolean HO = false;
    private boolean HP = true;

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zza {
        public final Uri uri;

        public zza(Uri $r1) throws  {
            this.uri = $r1;
        }

        public boolean equals(Object $r2) throws  {
            return !($r2 instanceof zza) ? false : this == $r2 ? true : zzaa.equal(((zza) $r2).uri, this.uri);
        }

        public int hashCode() throws  {
            return zzaa.hashCode(this.uri);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb extends zza {
        private WeakReference<ImageView> HQ;

        public zzb(ImageView $r1, int $i0) throws  {
            super(null, $i0);
            com.google.android.gms.common.internal.zzb.zzac($r1);
            this.HQ = new WeakReference($r1);
        }

        public zzb(ImageView $r1, Uri $r2) throws  {
            super($r2, 0);
            com.google.android.gms.common.internal.zzb.zzac($r1);
            this.HQ = new WeakReference($r1);
        }

        private void zza(ImageView $r2, Drawable $r1, boolean $z0, boolean $z1, boolean $z2) throws  {
            boolean $z3 = ($z1 || $z2) ? false : true;
            if ($z3 && ($r2 instanceof zzsl)) {
                int $i0 = ((zzsl) $r2).zzavp();
                if (this.HL != 0 && $i0 == this.HL) {
                    return;
                }
            }
            $z0 = zzd($z0, $z1);
            if ($z0) {
                $r1 = zza($r2.getDrawable(), $r1);
            }
            $r2.setImageDrawable($r1);
            if ($r2 instanceof zzsl) {
                zzsl $r3 = (zzsl) $r2;
                $r3.zzp($z2 ? this.HJ.uri : null);
                $r3.zzij($z3 ? this.HL : 0);
            }
            if ($z0) {
                ((zzsk) $r1).startTransition(250);
            }
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof zzb)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            ImageView $r4 = (ImageView) this.HQ.get();
            ImageView $r5 = (ImageView) ((zzb) $r1).HQ.get();
            boolean $z0 = ($r5 == null || $r4 == null || !zzaa.equal($r5, $r4)) ? false : true;
            return $z0;
        }

        public int hashCode() throws  {
            return 0;
        }

        protected void zza(Drawable $r1, boolean $z0, boolean $z1, boolean $z2) throws  {
            ImageView $r4 = (ImageView) this.HQ.get();
            if ($r4 != null) {
                zza($r4, $r1, $z0, $z1, $z2);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzc extends zza {
        private WeakReference<OnImageLoadedListener> HR;

        public zzc(OnImageLoadedListener $r1, Uri $r2) throws  {
            super($r2, 0);
            com.google.android.gms.common.internal.zzb.zzac($r1);
            this.HR = new WeakReference($r1);
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof zzc)) {
                return false;
            }
            if (this == $r1) {
                return true;
            }
            zzc $r2 = (zzc) $r1;
            OnImageLoadedListener $r4 = (OnImageLoadedListener) this.HR.get();
            OnImageLoadedListener $r5 = (OnImageLoadedListener) $r2.HR.get();
            boolean $z0 = $r5 != null && $r4 != null && zzaa.equal($r5, $r4) && zzaa.equal($r2.HJ, this.HJ);
            return $z0;
        }

        public int hashCode() throws  {
            return zzaa.hashCode(this.HJ);
        }

        protected void zza(Drawable $r1, boolean z, boolean $z1, boolean $z2) throws  {
            if (!$z1) {
                OnImageLoadedListener $r4 = (OnImageLoadedListener) this.HR.get();
                if ($r4 != null) {
                    $r4.onImageLoaded(this.HJ.uri, $r1, $z2);
                }
            }
        }
    }

    public zza(Uri $r1, int $i0) throws  {
        this.HJ = new zza($r1);
        this.HL = $i0;
    }

    private Drawable zza(Context $r1, zzsm com_google_android_gms_internal_zzsm, int $i0) throws  {
        return $r1.getResources().getDrawable($i0);
    }

    protected zzsk zza(Drawable $r2, Drawable $r1) throws  {
        if ($r2 == null) {
            $r2 = null;
        } else if ($r2 instanceof zzsk) {
            $r2 = ((zzsk) $r2).zzavn();
        }
        return new zzsk($r2, $r1);
    }

    void zza(Context $r1, Bitmap $r2, boolean $z0) throws  {
        com.google.android.gms.common.internal.zzb.zzac($r2);
        zza(new BitmapDrawable($r1.getResources(), $r2), $z0, false, true);
    }

    void zza(Context context, zzsm com_google_android_gms_internal_zzsm) throws  {
        if (this.HP) {
            zza(null, false, true, false);
        }
    }

    void zza(Context $r1, zzsm $r2, boolean $z0) throws  {
        Drawable $r3 = null;
        if (this.HL != 0) {
            $r3 = zza($r1, $r2, this.HL);
        }
        zza($r3, $z0, false, false);
    }

    protected abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3) throws ;

    protected boolean zzd(boolean $z0, boolean $z1) throws  {
        return (!this.HN || $z1 || $z0) ? false : true;
    }

    public void zzih(int $i0) throws  {
        this.HL = $i0;
    }
}
