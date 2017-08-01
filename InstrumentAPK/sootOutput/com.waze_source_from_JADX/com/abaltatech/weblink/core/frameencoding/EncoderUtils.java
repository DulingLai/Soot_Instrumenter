package com.abaltatech.weblink.core.frameencoding;

import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;

public final class EncoderUtils {
    public static Map<String, String> parseEncoderParams(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) String $r0) throws  {
        HashMap $r1 = new HashMap();
        if ($r0 == null) {
            return $r1;
        }
        for (String $r02 : $r02.split(",")) {
            String[] $r3 = $r02.split("=");
            if ($r3.length == 2) {
                $r1.put($r3[0].trim(), $r3[1].trim());
            }
        }
        return $r1;
    }

    public static int getParam(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "I)I"}) Map<String, String> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "I)I"}) String $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "I)I"}) int $i0) throws  {
        $r1 = (String) $r0.get($r1);
        if ($r1 == null) {
            return $i0;
        }
        try {
            $i0 = Integer.parseInt($r1);
            return $i0;
        } catch (NumberFormatException e) {
            return $i0;
        }
    }
}
