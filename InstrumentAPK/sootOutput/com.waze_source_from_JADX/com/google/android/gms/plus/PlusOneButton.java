package com.google.android.gms.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.plus.internal.zzh;

/* compiled from: dalvik_source_com.waze.apk */
public final class PlusOneButton extends FrameLayout {
    public static final int ANNOTATION_BUBBLE = 1;
    public static final int ANNOTATION_INLINE = 2;
    public static final int ANNOTATION_NONE = 0;
    public static final int DEFAULT_ACTIVITY_REQUEST_CODE = -1;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_SMALL = 0;
    public static final int SIZE_STANDARD = 3;
    public static final int SIZE_TALL = 2;
    private int aYq;
    private int aYr;
    private OnPlusOneClickListener aYs;
    private View kI;
    private int mSize;
    private String zzad;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface OnPlusOneClickListener {
        void onPlusOneClick(Intent intent) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    protected class DefaultOnPlusOneClickListener implements OnClickListener, OnPlusOneClickListener {
        private final OnPlusOneClickListener aYt;
        final /* synthetic */ PlusOneButton aYu;

        public DefaultOnPlusOneClickListener(PlusOneButton $r1, OnPlusOneClickListener $r2) throws  {
            this.aYu = $r1;
            this.aYt = $r2;
        }

        public void onClick(View view) throws  {
            Intent $r4 = (Intent) this.aYu.kI.getTag();
            if (this.aYt != null) {
                this.aYt.onPlusOneClick($r4);
            } else {
                onPlusOneClick($r4);
            }
        }

        public void onPlusOneClick(Intent $r1) throws  {
            Context $r3 = this.aYu.getContext();
            if (($r3 instanceof Activity) && $r1 != null) {
                ((Activity) $r3).startActivityForResult($r1, this.aYu.aYr);
            }
        }
    }

    public PlusOneButton(Context $r1) throws  {
        this($r1, null);
    }

    public PlusOneButton(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mSize = getSize($r1, $r2);
        this.aYq = getAnnotation($r1, $r2);
        this.aYr = -1;
        zzbu(getContext());
        if (!isInEditMode()) {
        }
    }

    protected static int getAnnotation(Context $r0, AttributeSet $r1) throws  {
        String $r2 = zzak.zza("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", $r0, $r1, true, false, "PlusOneButton");
        return "INLINE".equalsIgnoreCase($r2) ? 2 : !"NONE".equalsIgnoreCase($r2) ? 1 : 0;
    }

    protected static int getSize(Context $r0, AttributeSet $r1) throws  {
        String $r2 = zzak.zza("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "size", $r0, $r1, true, false, "PlusOneButton");
        return "SMALL".equalsIgnoreCase($r2) ? 0 : "MEDIUM".equalsIgnoreCase($r2) ? 1 : "TALL".equalsIgnoreCase($r2) ? 2 : 3;
    }

    private void zzbu(Context $r1) throws  {
        if (this.kI != null) {
            removeView(this.kI);
        }
        this.kI = zzh.zza($r1, this.mSize, this.aYq, this.zzad, this.aYr);
        setOnPlusOneClickListener(this.aYs);
        addView(this.kI);
    }

    public void initialize(String $r1, int $i0) throws  {
        zzab.zza(getContext() instanceof Activity, (Object) "To use this method, the PlusOneButton must be placed in an Activity. Use initialize(String, OnPlusOneClickListener).");
        this.zzad = $r1;
        this.aYr = $i0;
        zzbu(getContext());
    }

    public void initialize(String $r1, OnPlusOneClickListener $r2) throws  {
        this.zzad = $r1;
        this.aYr = 0;
        zzbu(getContext());
        setOnPlusOneClickListener($r2);
    }

    protected void onLayout(boolean z, int $i0, int $i1, int $i2, int $i3) throws  {
        this.kI.layout(0, 0, $i2 - $i0, $i3 - $i1);
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        View $r1 = this.kI;
        measureChild($r1, $i0, $i1);
        setMeasuredDimension($r1.getMeasuredWidth(), $r1.getMeasuredHeight());
    }

    public void plusOneClick() throws  {
        this.kI.performClick();
    }

    public void setAnnotation(int $i0) throws  {
        this.aYq = $i0;
        zzbu(getContext());
    }

    public void setIntent(Intent $r1) throws  {
        this.kI.setTag($r1);
    }

    public void setOnPlusOneClickListener(OnPlusOneClickListener $r1) throws  {
        this.aYs = $r1;
        this.kI.setOnClickListener(new DefaultOnPlusOneClickListener(this, $r1));
    }

    public void setSize(int $i0) throws  {
        this.mSize = $i0;
        zzbu(getContext());
    }
}
