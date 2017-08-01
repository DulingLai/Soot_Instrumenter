package com.google.android.gms.common.util;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzt {
    private static int zza(StackTraceElement[] $r0, StackTraceElement[] $r1) throws  {
        int $i0 = 0;
        int $i1 = $r1.length;
        int $i2 = $r0.length;
        while (true) {
            $i2--;
            if ($i2 < 0) {
                return $i0;
            }
            $i1--;
            if ($i1 < 0 || !$r1[$i1].equals($r0[$i2])) {
                return $i0;
            }
            $i0++;
        }
    }

    public static String zzazp() throws  {
        int $i1;
        int $i0;
        StringBuilder $r0 = new StringBuilder();
        Throwable $r1 = new Throwable();
        StackTraceElement[] $r2 = $r1.getStackTrace();
        $r0.append("Async stack trace:");
        for (StackTraceElement $r3 : $r2) {
            $r0.append("\n\tat ").append($r3);
        }
        $r1 = $r1.getCause();
        while ($r1 != null) {
            $r0.append("\nCaused by: ");
            $r0.append($r1);
            StackTraceElement[] $r5 = $r1.getStackTrace();
            $i0 = zza($r5, $r2);
            for ($i1 = 0; $i1 < $r5.length - $i0; $i1++) {
                String $r6 = String.valueOf($r5[$i1]);
                $r0.append(new StringBuilder(String.valueOf($r6).length() + 5).append("\n\tat ").append($r6).toString());
            }
            if ($i0 > 0) {
                $r0.append("\n\t... " + $i0 + " more");
            }
            $r1 = $r1.getCause();
            $r2 = $r5;
        }
        return $r0.toString();
    }
}
