package com.abaltatech.mcs.http;

public class Request {
    public final byte[] Data;
    public final String Method;
    public final String Url;

    public Request(String $r1, String $r2, byte[] $r3) throws  {
        if ($r1 == null) {
            $r1 = "";
        }
        this.Url = $r1;
        if ($r2 == null) {
            $r2 = "";
        }
        this.Method = $r2;
        if ($r3 == null) {
            $r3 = new byte[0];
        }
        this.Data = $r3;
    }
}
