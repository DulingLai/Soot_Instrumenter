package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.C0643R;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.waze.map.CanvasFont;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzag extends Button {
    public zzag(Context $r1) throws  {
        this($r1, null);
    }

    public zzag(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2, 16842824);
    }

    private void zza(Resources $r1) throws  {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float $f0 = $r1.getDisplayMetrics().density;
        setMinHeight((int) (($f0 * 48.0f) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        setMinWidth((int) (($f0 * 48.0f) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
    }

    private void zza(Resources $r1, int $i0, int $i1, boolean $z0) throws  {
        setBackgroundDrawable($r1.getDrawable($z0 ? zzf($i0, zzh($i1, C0643R.drawable.common_plus_signin_btn_icon_dark, C0643R.drawable.common_plus_signin_btn_icon_light, C0643R.drawable.common_plus_signin_btn_icon_dark), zzh($i1, C0643R.drawable.common_plus_signin_btn_text_dark, C0643R.drawable.common_plus_signin_btn_text_light, C0643R.drawable.common_plus_signin_btn_text_dark)) : zzf($i0, zzh($i1, C0643R.drawable.common_google_signin_btn_icon_dark, C0643R.drawable.common_google_signin_btn_icon_light, C0643R.drawable.common_google_signin_btn_icon_light), zzh($i1, C0643R.drawable.common_google_signin_btn_text_dark, C0643R.drawable.common_google_signin_btn_text_light, C0643R.drawable.common_google_signin_btn_text_light))));
    }

    private boolean zza(Scope[] $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        for (Scope $r2 : $r1) {
            String $r3 = $r2.zzasc();
            if ($r3.contains("/plus.") && !$r3.equals(Scopes.PLUS_ME)) {
                return true;
            }
            if ($r3.equals(Scopes.GAMES)) {
                return true;
            }
        }
        return false;
    }

    private void zzb(Resources $r1, int $i0, int $i1, boolean $z0) throws  {
        setTextColor((ColorStateList) zzab.zzag($r1.getColorStateList($z0 ? zzh($i1, C0643R.color.common_plus_signin_btn_text_dark, C0643R.color.common_plus_signin_btn_text_light, C0643R.color.common_plus_signin_btn_text_dark) : zzh($i1, C0643R.color.common_google_signin_btn_text_dark, C0643R.color.common_google_signin_btn_text_light, C0643R.color.common_google_signin_btn_text_light))));
        switch ($i0) {
            case 0:
                setText($r1.getString(C0643R.string.common_signin_button_text));
                break;
            case 1:
                setText($r1.getString(C0643R.string.common_signin_button_text_long));
                break;
            case 2:
                setText(null);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + $i0);
        }
        setTransformationMethod(null);
    }

    private int zzf(int $i0, int $i1, int $i2) throws  {
        switch ($i0) {
            case 0:
            case 1:
                return $i2;
            case 2:
                return $i1;
            default:
                throw new IllegalStateException("Unknown button size: " + $i0);
        }
    }

    private int zzh(int $i0, int $i3, int $i1, int $i2) throws  {
        switch ($i0) {
            case 0:
                break;
            case 1:
                $i3 = $i1;
                break;
            case 2:
                return $i2;
            default:
                throw new IllegalStateException("Unknown color scheme: " + $i0);
        }
        return $i3;
    }

    public void zza(Resources $r1, int $i0, int $i1, Scope[] $r2) throws  {
        boolean $z0 = zza($r2);
        zza($r1);
        zza($r1, $i0, $i1, $z0);
        zzb($r1, $i0, $i1, $z0);
    }
}
