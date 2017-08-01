package com.google.android.gms.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.UUID;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzay {
    private static final char[] zzagx = "0123456789abcdef".toCharArray();

    public static String zza(Throwable $r0) throws  {
        StringWriter $r3 = new StringWriter();
        $r0.printStackTrace(new PrintWriter($r3));
        return $r3.toString();
    }

    public static String zzo(String $r0) throws  {
        if ($r0 == null || !$r0.matches("^[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}$")) {
            return $r0;
        }
        UUID $r1 = UUID.fromString($r0);
        byte[] $r2 = new byte[16];
        ByteBuffer $r3 = ByteBuffer.wrap($r2);
        $r3.putLong($r1.getMostSignificantBits());
        $r3.putLong($r1.getLeastSignificantBits());
        return zzaj.zza($r2, true);
    }
}
