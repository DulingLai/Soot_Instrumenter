package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbm extends zzbp {
    private List<Long> zzahd = null;

    public zzbm(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this.zzaha.zzdp = Long.valueOf(-1);
        this.zzaha.zzdq = Long.valueOf(-1);
        if (this.zzahd == null) {
            this.zzahd = (List) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
        }
        if (this.zzahd != null && this.zzahd.size() == 2) {
            synchronized (this.zzaha) {
                this.zzaha.zzdp = Long.valueOf(((Long) this.zzahd.get(0)).longValue());
                this.zzaha.zzdq = Long.valueOf(((Long) this.zzahd.get(1)).longValue());
            }
        }
    }
}
