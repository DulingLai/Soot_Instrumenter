package com.google.android.gms.internal;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.zzr;

/* compiled from: dalvik_source_com.waze.apk */
public class zzrg {
    private final Object FL;

    public zzrg(Activity $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "Activity must not be null");
        boolean $z0 = zzr.zzaza() || ($r1 instanceof FragmentActivity);
        zzab.zzb($z0, (Object) "This Activity is not supported before platform version 11 (3.0 Honeycomb). Please use FragmentActivity instead.");
        this.FL = $r1;
    }

    public boolean zzaug() throws  {
        return this.FL instanceof FragmentActivity;
    }

    public Activity zzauh() throws  {
        return (Activity) this.FL;
    }

    public FragmentActivity zzaui() throws  {
        return (FragmentActivity) this.FL;
    }

    public Object zzauj() throws  {
        return this.FL;
    }
}
