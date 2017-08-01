package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzab;
import com.waze.strings.DisplayStrings;

/* compiled from: dalvik_source_com.waze.apk */
public class BooleanResult implements Result {
    private final boolean Cn;
    private final Status cp;

    public BooleanResult(Status $r1, boolean $z0) throws  {
        this.cp = (Status) zzab.zzb((Object) $r1, (Object) "Status must not be null");
        this.Cn = $z0;
    }

    public final boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof BooleanResult)) {
            return false;
        }
        BooleanResult $r2 = (BooleanResult) $r1;
        return this.cp.equals($r2.cp) && this.Cn == $r2.Cn;
    }

    public Status getStatus() throws  {
        return this.cp;
    }

    public boolean getValue() throws  {
        return this.Cn;
    }

    public final int hashCode() throws  {
        return (this.Cn ? (byte) 1 : (byte) 0) + ((this.cp.hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31);
    }
}
