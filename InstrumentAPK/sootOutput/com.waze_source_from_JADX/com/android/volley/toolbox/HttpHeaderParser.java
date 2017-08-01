package com.android.volley.toolbox;

import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import dalvik.annotation.Signature;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class HttpHeaderParser {
    public static Entry parseCacheHeaders(NetworkResponse $r0) throws  {
        long $l0 = System.currentTimeMillis();
        Map $r1 = $r0.headers;
        long $l1 = 0;
        long $l2 = 0;
        long $l3 = 0;
        long $l4 = 0;
        boolean $z0 = false;
        String $r3 = (String) $r1.get("Date");
        if ($r3 != null) {
            $l1 = parseDateAsEpoch($r3);
        }
        $r3 = (String) $r1.get("Cache-Control");
        if ($r3 != null) {
            $z0 = true;
            String[] $r4 = $r3.split(",");
            int $i5 = 0;
            while ($i5 < $r4.length) {
                $r3 = $r4[$i5].trim();
                if (!$r3.equals("no-cache")) {
                    if (!$r3.equals("no-store")) {
                        if ($r3.startsWith("max-age=")) {
                            try {
                                $l4 = Long.parseLong($r3.substring(8));
                            } catch (Exception e) {
                            }
                        } else {
                            if (!$r3.equals("must-revalidate")) {
                                if (!$r3.equals("proxy-revalidate")) {
                                }
                            }
                            $l4 = 0;
                        }
                        $i5++;
                    }
                }
                return null;
            }
        }
        $r3 = (String) $r1.get("Expires");
        if ($r3 != null) {
            $l2 = parseDateAsEpoch($r3);
        }
        $r3 = (String) $r1.get("ETag");
        if ($z0) {
            $l3 = $l0 + (1000 * $l4);
        } else if ($l1 > 0 && $l2 >= $l1) {
            $l3 = $l0 + ($l2 - $l1);
        }
        Entry entry = new Entry();
        byte[] bArr = $r0.data;
        byte[] $r6 = bArr;
        entry.data = bArr;
        entry.etag = $r3;
        entry.softTtl = $l3;
        entry.ttl = entry.softTtl;
        entry.serverDate = $l1;
        entry.responseHeaders = $r1;
        return entry;
    }

    public static long parseDateAsEpoch(String $r0) throws  {
        try {
            return DateUtils.parseDate($r0).getTime();
        } catch (DateParseException e) {
            return 0;
        }
    }

    public static String parseCharset(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) Map<String, String> $r0) throws  {
        String $r2 = (String) $r0.get("Content-Type");
        if ($r2 != null) {
            String[] $r3 = $r2.split(";");
            for (int $i0 = 1; $i0 < $r3.length; $i0++) {
                String[] $r4 = $r3[$i0].trim().split("=");
                if ($r4.length == 2 && $r4[0].equals("charset")) {
                    return $r4[1];
                }
            }
        }
        return "ISO-8859-1";
    }
}
