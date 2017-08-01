package com.google.android.gms.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzqr implements Releasable, Result {
    protected final DataHolder DW;
    protected final Status cp;

    protected zzqr(DataHolder $r1, Status $r2) throws  {
        this.cp = $r2;
        this.DW = $r1;
    }

    public Status getStatus() throws  {
        return this.cp;
    }

    public void release() throws  {
        if (this.DW != null) {
            this.DW.close();
        }
    }
}
