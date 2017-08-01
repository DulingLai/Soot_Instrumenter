package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzc {
    protected final DataHolder DW;
    protected int GO;
    private int GP;

    public zzc(DataHolder $r1, int $i0) throws  {
        this.DW = (DataHolder) zzab.zzag($r1);
        zzia($i0);
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof zzc)) {
            return false;
        }
        zzc $r2 = (zzc) $r1;
        return zzaa.equal(Integer.valueOf($r2.GO), Integer.valueOf(this.GO)) ? zzaa.equal(Integer.valueOf($r2.GP), Integer.valueOf(this.GP)) ? $r2.DW == this.DW : false : false;
    }

    protected boolean getBoolean(String $r1) throws  {
        return this.DW.getBoolean($r1, this.GO, this.GP);
    }

    protected byte[] getByteArray(String $r1) throws  {
        return this.DW.getByteArray($r1, this.GO, this.GP);
    }

    protected double getDouble(String $r1) throws  {
        return this.DW.getDouble($r1, this.GO, this.GP);
    }

    protected float getFloat(String $r1) throws  {
        return this.DW.getFloat($r1, this.GO, this.GP);
    }

    protected int getInteger(String $r1) throws  {
        return this.DW.getInteger($r1, this.GO, this.GP);
    }

    protected long getLong(String $r1) throws  {
        return this.DW.getLong($r1, this.GO, this.GP);
    }

    protected String getString(String $r1) throws  {
        return this.DW.getString($r1, this.GO, this.GP);
    }

    public boolean hasColumn(String $r1) throws  {
        return this.DW.hasColumn($r1);
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.GO), Integer.valueOf(this.GP), this.DW);
    }

    public boolean isDataValid() throws  {
        return !this.DW.isClosed();
    }

    protected void zza(String $r1, CharArrayBuffer $r2) throws  {
        this.DW.copyToBuffer($r1, this.GO, this.GP, $r2);
    }

    protected int zzavc() throws  {
        return this.GO;
    }

    protected Uri zzgl(String $r1) throws  {
        return this.DW.parseUri($r1, this.GO, this.GP);
    }

    protected boolean zzgm(String $r1) throws  {
        return this.DW.hasNull($r1, this.GO, this.GP);
    }

    protected void zzia(int $i0) throws  {
        boolean $z0 = $i0 >= 0 && $i0 < this.DW.getCount();
        zzab.zzbm($z0);
        this.GO = $i0;
        this.GP = this.DW.zzic(this.GO);
    }
}
