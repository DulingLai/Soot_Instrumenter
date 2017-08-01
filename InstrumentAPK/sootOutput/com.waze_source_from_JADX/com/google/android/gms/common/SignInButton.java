package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.C0643R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.dynamic.zzg.zza;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: dalvik_source_com.waze.apk */
public final class SignInButton extends FrameLayout implements OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private Scope[] BY;
    private View BZ;
    private OnClickListener Ca;
    private int mColor;
    private int mSize;

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface ButtonSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface ColorScheme {
    }

    public SignInButton(Context $r1) throws  {
        this($r1, null);
    }

    public SignInButton(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SignInButton(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.Ca = null;
        zza($r1, $r2);
        setStyle(this.mSize, this.mColor, this.BY);
    }

    private static Button zza(Context $r0, int $i0, int $i1, Scope[] $r1) throws  {
        zzag $r2 = new zzag($r0);
        $r2.zza($r0.getResources(), $i0, $i1, $r1);
        return $r2;
    }

    private void zza(Context $r1, AttributeSet $r2) throws  {
        TypedArray $r6 = $r1.getTheme().obtainStyledAttributes($r2, C0643R.styleable.SignInButton, 0, 0);
        try {
            this.mSize = $r6.getInt(C0643R.styleable.SignInButton_buttonSize, 0);
            this.mColor = $r6.getInt(C0643R.styleable.SignInButton_colorScheme, 2);
            String $r7 = $r6.getString(C0643R.styleable.SignInButton_scopeUris);
            if ($r7 == null) {
                this.BY = null;
            } else {
                String[] $r8 = $r7.trim().split("\\s+");
                this.BY = new Scope[$r8.length];
                for (int $i0 = 0; $i0 < $r8.length; $i0++) {
                    this.BY[$i0] = new Scope($r8[$i0].toString());
                }
            }
            $r6.recycle();
        } catch (Throwable th) {
            $r6.recycle();
        }
    }

    private void zzbu(Context $r1) throws  {
        if (this.BZ != null) {
            removeView(this.BZ);
        }
        try {
            this.BZ = zzaf.zzb($r1, this.mSize, this.mColor, this.BY);
        } catch (zza e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            this.BZ = zza($r1, this.mSize, this.mColor, this.BY);
        }
        addView(this.BZ);
        this.BZ.setEnabled(isEnabled());
        this.BZ.setOnClickListener(this);
    }

    public void onClick(View $r1) throws  {
        if (this.Ca != null && $r1 == this.BZ) {
            this.Ca.onClick(this);
        }
    }

    public void setColorScheme(int $i0) throws  {
        setStyle(this.mSize, $i0, this.BY);
    }

    public void setEnabled(boolean $z0) throws  {
        super.setEnabled($z0);
        this.BZ.setEnabled($z0);
    }

    public void setOnClickListener(OnClickListener $r1) throws  {
        this.Ca = $r1;
        if (this.BZ != null) {
            this.BZ.setOnClickListener(this);
        }
    }

    public void setScopes(Scope[] $r1) throws  {
        setStyle(this.mSize, this.mColor, $r1);
    }

    public void setSize(int $i0) throws  {
        setStyle($i0, this.mColor, this.BY);
    }

    public void setStyle(int $i0, int $i1) throws  {
        setStyle($i0, $i1, this.BY);
    }

    public void setStyle(int $i0, int $i1, Scope[] $r1) throws  {
        this.mSize = $i0;
        this.mColor = $i1;
        this.BY = $r1;
        zzbu(getContext());
    }
}
