package com.google.android.gms.plus;

import android.app.PendingIntent;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.plus.internal.zzd;
import com.google.android.gms.plus.internal.zzd.zza;
import com.google.android.gms.plus.internal.zzh;

/* compiled from: dalvik_source_com.waze.apk */
public final class PlusOneButtonWithPopup extends ViewGroup {
    private int aYq;
    private OnClickListener aYv;
    private String dL;
    private View kI;
    private int mSize;
    private String zzad;

    public PlusOneButtonWithPopup(Context $r1) throws  {
        this($r1, null);
    }

    public PlusOneButtonWithPopup(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mSize = PlusOneButton.getSize($r1, $r2);
        this.aYq = PlusOneButton.getAnnotation($r1, $r2);
        this.kI = new PlusOneDummyView($r1, this.mSize);
        addView(this.kI);
    }

    private int zzak(int $i1, int $i0) throws  {
        int $i2 = MeasureSpec.getMode($i1);
        switch ($i2) {
            case Integer.MIN_VALUE:
            case 1073741824:
                return MeasureSpec.makeMeasureSpec(MeasureSpec.getSize($i1) - $i0, $i2);
            default:
                return $i1;
        }
    }

    private void zzcgn() throws  {
        if (this.kI != null) {
            removeView(this.kI);
        }
        this.kI = zzh.zza(getContext(), this.mSize, this.aYq, this.zzad, this.dL);
        if (this.aYv != null) {
            setOnClickListener(this.aYv);
        }
        addView(this.kI);
    }

    private zzd zzcgo() throws RemoteException {
        zzd $r4 = zza.zzox((IBinder) this.kI.getTag());
        if ($r4 != null) {
            return $r4;
        }
        if (Log.isLoggable("PlusOneButtonWithPopup", 5)) {
            Log.w("PlusOneButtonWithPopup", "Failed to get PlusOneDelegate");
        }
        throw new RemoteException();
    }

    public void cancelClick() throws  {
        if (this.kI != null) {
            try {
                zzcgo().cancelClick();
            } catch (RemoteException e) {
            }
        }
    }

    public PendingIntent getResolution() throws  {
        if (this.kI != null) {
            try {
                return zzcgo().getResolution();
            } catch (RemoteException e) {
            }
        }
        return null;
    }

    public void initialize(String $r1, String $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "Url must not be null");
        this.zzad = $r1;
        this.dL = $r2;
        zzcgn();
    }

    protected void onLayout(boolean z, int $i0, int $i1, int $i2, int $i3) throws  {
        this.kI.layout(getPaddingLeft(), getPaddingTop(), ($i2 - $i0) - getPaddingRight(), ($i3 - $i1) - getPaddingBottom());
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        int $i2 = getPaddingLeft() + getPaddingRight();
        int $i3 = getPaddingTop() + getPaddingBottom();
        this.kI.measure(zzak($i0, $i2), zzak($i1, $i3));
        setMeasuredDimension($i2 + this.kI.getMeasuredWidth(), $i3 + this.kI.getMeasuredHeight());
    }

    public void reinitialize() throws  {
        if (this.kI != null) {
            try {
                zzcgo().reinitialize();
            } catch (RemoteException e) {
            }
        }
    }

    public void setAnnotation(int $i0) throws  {
        this.aYq = $i0;
        zzcgn();
    }

    public void setOnClickListener(OnClickListener $r1) throws  {
        this.aYv = $r1;
        this.kI.setOnClickListener($r1);
    }

    public void setSize(int $i0) throws  {
        this.mSize = $i0;
        zzcgn();
    }
}
