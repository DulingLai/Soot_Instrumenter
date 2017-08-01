package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzwe.zzd;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public class zzwf {
    private final Collection<zzwe> zzayi = new ArrayList();
    private final Collection<zzd> zzayj = new ArrayList();
    private final Collection<zzd> zzayk = new ArrayList();

    public static void initialize(Context $r0) throws  {
        zzwi.zzblh().initialize($r0);
    }

    public void zza(zzwe $r1) throws  {
        this.zzayi.add($r1);
    }
}
