package com.google.android.gms.people.exp;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zza {
    private final DataHolder DW;
    private int mPos = -1;

    protected zza(DataHolder $r1) throws  {
        zzab.zzag($r1);
        this.DW = $r1;
    }

    public void close() throws  {
        this.DW.close();
    }

    public double getDouble(String $r1) throws  {
        return this.DW.getDouble($r1, getPosition(), zzccs());
    }

    public int getInteger(String $r1) throws  {
        return this.DW.getInteger($r1, getPosition(), zzccs());
    }

    public long getLong(String $r1) throws  {
        return this.DW.getLong($r1, getPosition(), zzccs());
    }

    public int getPosition() throws  {
        return this.mPos;
    }

    public String getString(String $r1) throws  {
        return this.DW.getString($r1, getPosition(), zzccs());
    }

    public Bundle zzava() throws  {
        return this.DW.zzava();
    }

    protected int zzccs() throws  {
        return this.DW.zzic(this.mPos);
    }
}
