package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzr;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzsk extends Drawable implements Callback {
    private boolean HN;
    private int HT;
    private int HU;
    private int HV;
    private int HW;
    private int HX;
    private boolean HY;
    private zzb HZ;
    private Drawable Ia;
    private Drawable Ib;
    private boolean Ic;
    private boolean Id;
    private boolean Ie;
    private int If;
    private int mFrom;
    private long zzcyx;

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza extends Drawable {
        private static final zza Ig = new zza();
        private static final zza Ih = new zza();

        /* compiled from: dalvik_source_com.waze.apk */
        private static final class zza extends ConstantState {
            private zza() throws  {
            }

            public int getChangingConfigurations() throws  {
                return 0;
            }

            public Drawable newDrawable() throws  {
                return zza.Ig;
            }
        }

        private zza() throws  {
        }

        public void draw(Canvas canvas) throws  {
        }

        public ConstantState getConstantState() throws  {
            return Ih;
        }

        public int getOpacity() throws  {
            return -2;
        }

        public void setAlpha(int i) throws  {
        }

        public void setColorFilter(ColorFilter colorFilter) throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zzb extends ConstantState {
        int Ii;
        int mChangingConfigurations;

        zzb(zzb $r1) throws  {
            if ($r1 != null) {
                this.mChangingConfigurations = $r1.mChangingConfigurations;
                this.Ii = $r1.Ii;
            }
        }

        public int getChangingConfigurations() throws  {
            return this.mChangingConfigurations;
        }

        public Drawable newDrawable() throws  {
            return new zzsk(this);
        }
    }

    public zzsk(Drawable $r1, Drawable $r2) throws  {
        this(null);
        if ($r1 == null) {
            $r1 = zza.Ig;
        }
        this.Ia = $r1;
        $r1.setCallback(this);
        zzb $r4 = this.HZ;
        $r4.Ii |= $r1.getChangingConfigurations();
        if ($r2 == null) {
            $r2 = zza.Ig;
        }
        this.Ib = $r2;
        $r2.setCallback(this);
        $r4 = this.HZ;
        $r4.Ii |= $r2.getChangingConfigurations();
    }

    zzsk(zzb $r1) throws  {
        this.HT = 0;
        this.HV = 255;
        this.HX = 0;
        this.HN = true;
        this.HZ = new zzb($r1);
    }

    public boolean canConstantState() throws  {
        if (!this.Ic) {
            boolean $z0 = (this.Ia.getConstantState() == null || this.Ib.getConstantState() == null) ? false : true;
            this.Id = $z0;
            this.Ic = true;
        }
        return this.Id;
    }

    public void draw(Canvas $r1) throws  {
        boolean $z0 = true;
        boolean $z1 = false;
        switch (this.HT) {
            case 1:
                this.zzcyx = SystemClock.uptimeMillis();
                this.HT = 2;
                break;
            case 2:
                if (this.zzcyx >= 0) {
                    float $f1 = (float) this.HW;
                    float $f0 = ((float) (SystemClock.uptimeMillis() - this.zzcyx)) / $f1;
                    if ($f0 < 1.0f) {
                        $z0 = false;
                    }
                    if ($z0) {
                        this.HT = 0;
                    }
                    $f1 = (float) (this.HU + 0);
                    this.HX = (int) ((Math.min($f0, 1.0f) * $f1) + 0.0f);
                    break;
                }
                break;
            default:
                break;
        }
        $z1 = $z0;
        int $i0 = this.HX;
        $z0 = this.HN;
        Drawable $r2 = this.Ia;
        Drawable $r3 = this.Ib;
        if ($z1) {
            if (!$z0 || $i0 == 0) {
                $r2.draw($r1);
            }
            if ($i0 == this.HV) {
                $r3.setAlpha(this.HV);
                $r3.draw($r1);
                return;
            }
            return;
        }
        if ($z0) {
            $r2.setAlpha(this.HV - $i0);
        }
        $r2.draw($r1);
        if ($z0) {
            $r2.setAlpha(this.HV);
        }
        if ($i0 > 0) {
            $r3.setAlpha($i0);
            $r3.draw($r1);
            $r3.setAlpha(this.HV);
        }
        invalidateSelf();
    }

    public int getChangingConfigurations() throws  {
        return (super.getChangingConfigurations() | this.HZ.mChangingConfigurations) | this.HZ.Ii;
    }

    public ConstantState getConstantState() throws  {
        if (!canConstantState()) {
            return null;
        }
        this.HZ.mChangingConfigurations = getChangingConfigurations();
        return this.HZ;
    }

    public int getIntrinsicHeight() throws  {
        return Math.max(this.Ia.getIntrinsicHeight(), this.Ib.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() throws  {
        return Math.max(this.Ia.getIntrinsicWidth(), this.Ib.getIntrinsicWidth());
    }

    public int getOpacity() throws  {
        if (!this.Ie) {
            this.If = Drawable.resolveOpacity(this.Ia.getOpacity(), this.Ib.getOpacity());
            this.Ie = true;
        }
        return this.If;
    }

    @TargetApi(11)
    public void invalidateDrawable(Drawable drawable) throws  {
        if (zzr.zzaza()) {
            Callback $r2 = getCallback();
            if ($r2 != null) {
                $r2.invalidateDrawable(this);
            }
        }
    }

    public Drawable mutate() throws  {
        if (this.HY || super.mutate() != this) {
            return this;
        }
        if (canConstantState()) {
            this.Ia.mutate();
            this.Ib.mutate();
            this.HY = true;
            return this;
        }
        throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
    }

    protected void onBoundsChange(Rect $r1) throws  {
        this.Ia.setBounds($r1);
        this.Ib.setBounds($r1);
    }

    @TargetApi(11)
    public void scheduleDrawable(Drawable drawable, Runnable $r2, long $l0) throws  {
        if (zzr.zzaza()) {
            Callback $r3 = getCallback();
            if ($r3 != null) {
                $r3.scheduleDrawable(this, $r2, $l0);
            }
        }
    }

    public void setAlpha(int $i0) throws  {
        if (this.HX == this.HV) {
            this.HX = $i0;
        }
        this.HV = $i0;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.Ia.setColorFilter($r1);
        this.Ib.setColorFilter($r1);
    }

    public void startTransition(int $i0) throws  {
        this.mFrom = 0;
        this.HU = this.HV;
        this.HX = 0;
        this.HW = $i0;
        this.HT = 1;
        invalidateSelf();
    }

    @TargetApi(11)
    public void unscheduleDrawable(Drawable drawable, Runnable $r2) throws  {
        if (zzr.zzaza()) {
            Callback $r3 = getCallback();
            if ($r3 != null) {
                $r3.unscheduleDrawable(this, $r2);
            }
        }
    }

    public Drawable zzavn() throws  {
        return this.Ib;
    }
}
