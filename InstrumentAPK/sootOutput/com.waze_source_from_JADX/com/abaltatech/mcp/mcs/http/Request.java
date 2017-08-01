package com.abaltatech.mcp.mcs.http;

import dalvik.annotation.Signature;
import java.util.HashMap;

public class Request {
    public HashMap<String, String> AdditionalHeaders;
    public final byte[] Data;
    public final String Method;
    public final String Url;

    public Request(String $r1, String $r2, byte[] $r3) throws  {
        this.AdditionalHeaders = new HashMap();
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
        ProcessRequestParamsFromUri();
    }

    public Request(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[B", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[B", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[B", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) byte[] $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[B", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) HashMap<String, String> $r4) throws  {
        this($r1, $r2, $r3);
        this.AdditionalHeaders.putAll($r4);
    }

    public String getRequestParamByName(String $r1) throws  {
        if (this.AdditionalHeaders == null || !this.AdditionalHeaders.containsKey($r1)) {
            return null;
        }
        return (String) this.AdditionalHeaders.get($r1);
    }

    public HashMap<String, String> getRequestParams() throws  {
        return this.AdditionalHeaders;
    }

    public byte[] getBody() throws  {
        return this.Data;
    }

    protected void ProcessRequestParamsFromUri() throws  {
        int $i0 = this.Url.indexOf(63);
        if ($i0 >= 0) {
            for (String $r1 : this.Url.substring($i0 + 1).split("&")) {
                String[] $r3 = $r1.split("=");
                if ($r3.length == 2) {
                    this.AdditionalHeaders.put($r3[0], $r3[1]);
                }
            }
        }
    }
}
