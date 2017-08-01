package com.abaltatech.mcs.http;

public class Response {
    public String[] AdditionalHeaders = null;
    public final int Code;
    public final String ContentType;
    public final byte[] Data;
    public final boolean IsLast;
    public final String Phrase;
    public boolean SendOnlyData = false;

    public Response(String $r1, int $i0, String $r2, byte[] $r3, boolean $z0) throws  {
        if ($r1 == null) {
            $r1 = "";
        }
        this.Phrase = $r1;
        this.Code = $i0;
        if ($r2 == null) {
            $r2 = "";
        }
        this.ContentType = $r2;
        if ($r3 == null) {
            $r3 = new byte[0];
        }
        this.Data = $r3;
        this.IsLast = $z0;
    }
}
