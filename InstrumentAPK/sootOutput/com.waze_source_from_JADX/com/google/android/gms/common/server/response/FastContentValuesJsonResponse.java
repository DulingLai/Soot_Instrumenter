package com.google.android.gms.common.server.response;

import android.content.ContentValues;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class FastContentValuesJsonResponse extends FastJsonResponse {
    private final ContentValues KX;

    public FastContentValuesJsonResponse() throws  {
        this.KX = new ContentValues();
    }

    public FastContentValuesJsonResponse(ContentValues $r1) throws  {
        this.KX = $r1;
    }

    protected Object getValueObject(String $r1) throws  {
        return this.KX.get($r1);
    }

    public ContentValues getValues() throws  {
        return this.KX;
    }

    protected boolean isPrimitiveFieldSet(String $r1) throws  {
        return this.KX.containsKey($r1);
    }

    protected void setBoolean(String $r1, boolean $z0) throws  {
        this.KX.put($r1, Boolean.valueOf($z0));
    }

    protected void setDecodedBytes(String $r1, byte[] $r2) throws  {
        this.KX.put($r1, $r2);
    }

    protected void setDouble(String $r1, double $d0) throws  {
        this.KX.put($r1, Double.valueOf($d0));
    }

    protected void setFloat(String $r1, float $f0) throws  {
        this.KX.put($r1, Float.valueOf($f0));
    }

    protected void setInteger(String $r1, int $i0) throws  {
        this.KX.put($r1, Integer.valueOf($i0));
    }

    protected void setLong(String $r1, long $l0) throws  {
        this.KX.put($r1, Long.valueOf($l0));
    }

    protected void setString(String $r1, String $r2) throws  {
        this.KX.put($r1, $r2);
    }
}
