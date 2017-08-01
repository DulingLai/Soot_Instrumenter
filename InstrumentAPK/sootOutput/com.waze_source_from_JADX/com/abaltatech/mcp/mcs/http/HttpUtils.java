package com.abaltatech.mcp.mcs.http;

public class HttpUtils {
    public static String extractUrlPath(String $r0) throws  {
        if ($r0 != null && $r0.toLowerCase().startsWith("http://")) {
            String $r1 = $r0.substring(7);
            int $i0 = $r1.indexOf(47);
            if ($i0 > 0) {
                return $r1.substring($i0);
            }
        }
        return $r0;
    }

    public static byte[] getResponseBytes(Response $r0) throws  {
        if ($r0.SendOnlyData) {
            return $r0.Data;
        }
        StringBuilder $r1 = new StringBuilder("HTTP/1.0 ");
        $r1.append($r0.Code);
        $r1.append(' ');
        $r1.append($r0.Phrase);
        $r1.append("\r\n");
        if ($r0.ContentType.length() > 0) {
            $r1.append("Content-Type: ");
            $r1.append($r0.ContentType);
            $r1.append("\r\n");
        }
        if ($r0.IsLast) {
            $r1.append("Content-Length: ");
            $r1.append($r0.Data.length + "\r\n");
        }
        if ($r0.AdditionalHeaders != null) {
            for (String $r3 : $r0.AdditionalHeaders) {
                $r1.append($r3);
                $r1.append("\r\n");
            }
        }
        $r1.append("\r\n");
        byte[] $r5 = $r1.toString().getBytes();
        if ($r0.Data.length < 1) {
            return $r5;
        }
        byte[] $r2 = new byte[($r5.length + $r0.Data.length)];
        System.arraycopy($r5, 0, $r2, 0, $r5.length);
        System.arraycopy($r0.Data, 0, $r2, $r5.length, $r0.Data.length);
        return $r2;
    }
}
