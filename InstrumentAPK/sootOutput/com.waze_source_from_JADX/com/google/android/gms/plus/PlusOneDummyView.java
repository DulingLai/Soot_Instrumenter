package com.google.android.gms.plus;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/* compiled from: dalvik_source_com.waze.apk */
public class PlusOneDummyView extends FrameLayout {
    public static final String TAG = "PlusOneDummyView";

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zzd {
        Drawable getDrawable(int i) throws ;

        boolean isValid() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza implements zzd {
        private Context mContext;

        private zza(Context $r1) throws  {
            this.mContext = $r1;
        }

        public Drawable getDrawable(int i) throws  {
            return this.mContext.getResources().getDrawable(17301508);
        }

        public boolean isValid() throws  {
            return true;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzb implements zzd {
        private Context mContext;

        private zzb(Context $r1) throws  {
            this.mContext = $r1;
        }

        public Drawable getDrawable(int $i0) throws  {
            try {
                String $r3;
                Resources $r2 = this.mContext.createPackageContext("com.google.android.gms", 4).getResources();
                switch ($i0) {
                    case 0:
                        $r3 = "ic_plusone_small";
                        break;
                    case 1:
                        $r3 = "ic_plusone_medium";
                        break;
                    case 2:
                        $r3 = "ic_plusone_tall";
                        break;
                    default:
                        $r3 = "ic_plusone_standard";
                        break;
                }
                return $r2.getDrawable($r2.getIdentifier($r3, "drawable", "com.google.android.gms"));
            } catch (NameNotFoundException e) {
                return null;
            }
        }

        public boolean isValid() throws  {
            try {
                this.mContext.createPackageContext("com.google.android.gms", 4).getResources();
                return true;
            } catch (NameNotFoundException e) {
                return false;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzc implements zzd {
        private Context mContext;

        private zzc(Context $r1) throws  {
            this.mContext = $r1;
        }

        public Drawable getDrawable(int $i0) throws  {
            String $r1;
            switch ($i0) {
                case 0:
                    $r1 = "ic_plusone_small_off_client";
                    break;
                case 1:
                    $r1 = "ic_plusone_medium_off_client";
                    break;
                case 2:
                    $r1 = "ic_plusone_tall_off_client";
                    break;
                default:
                    $r1 = "ic_plusone_standard_off_client";
                    break;
            }
            return this.mContext.getResources().getDrawable(this.mContext.getResources().getIdentifier($r1, "drawable", this.mContext.getPackageName()));
        }

        public boolean isValid() throws  {
            return (this.mContext.getResources().getIdentifier("ic_plusone_small_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_medium_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_tall_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_standard_off_client", "drawable", this.mContext.getPackageName()) == 0) ? false : true;
        }
    }

    public PlusOneDummyView(Context $r1, int $i0) throws  {
        super($r1);
        Button $r2 = new Button($r1);
        $r2.setEnabled(false);
        $r2.setBackgroundDrawable(zzcgp().getDrawable($i0));
        Point $r6 = zzaee($i0);
        addView($r2, new LayoutParams($r6.x, $r6.y, 17));
    }

    private Point zzaee(int $i0) throws  {
        byte $b1 = (byte) 24;
        byte $b2 = (byte) 20;
        Point $r1 = new Point();
        switch ($i0) {
            case 0:
                $b2 = (byte) 14;
                break;
            case 1:
                $b1 = (byte) 32;
                break;
            case 2:
                $b1 = (byte) 50;
                break;
            default:
                $b1 = (byte) 38;
                $b2 = (byte) 24;
                break;
        }
        DisplayMetrics $r3 = getResources().getDisplayMetrics();
        float $f0 = TypedValue.applyDimension(1, (float) $b1, $r3);
        float $f1 = TypedValue.applyDimension(1, (float) $b2, $r3);
        $r1.x = (int) (((double) $f0) + 0.5d);
        $r1.y = (int) (((double) $f1) + 0.5d);
        return $r1;
    }

    private zzd zzcgp() throws  {
        zzd $r1 = r3;
        zzb r3 = new zzb(getContext());
        if (!$r1.isValid()) {
            $r1 = r4;
            zzc r4 = new zzc(getContext());
        }
        return !$r1.isValid() ? new zza(getContext()) : $r1;
    }
}
