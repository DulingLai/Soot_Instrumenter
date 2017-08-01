package com.google.android.gms.internal;

import java.lang.reflect.Field;
import java.util.Locale;

/* compiled from: dalvik_source_com.waze.apk */
public enum zzaum implements zzaun {
    IDENTITY {
        public String zzc(Field $r1) throws  {
            return $r1.getName();
        }
    },
    UPPER_CAMEL_CASE {
        public String zzc(Field $r1) throws  {
            return zzaum.zzyj($r1.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        public String zzc(Field $r1) throws  {
            return zzaum.zzyj(zzaum.zzck($r1.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {
        public String zzc(Field $r1) throws  {
            return zzaum.zzck($r1.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    },
    LOWER_CASE_WITH_DASHES {
        public String zzc(Field $r1) throws  {
            return zzaum.zzck($r1.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    };

    private static String zza(char $c0, String $r0, int $i1) throws  {
        if ($i1 >= $r0.length()) {
            return String.valueOf($c0);
        }
        $r0 = String.valueOf($r0.substring($i1));
        return new StringBuilder(String.valueOf($r0).length() + 1).append($c0).append($r0).toString();
    }

    private static String zzck(String $r0, String $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            char $c2 = $r0.charAt($i0);
            if (Character.isUpperCase($c2) && $r2.length() != 0) {
                $r2.append($r1);
            }
            $r2.append($c2);
        }
        return $r2.toString();
    }

    private static String zzyj(String $r1) throws  {
        int $i0 = 0;
        StringBuilder $r0 = new StringBuilder();
        char $c1 = $r1.charAt(0);
        while ($i0 < $r1.length() - 1 && !Character.isLetter($c1)) {
            $r0.append($c1);
            $i0++;
            $c1 = $r1.charAt($i0);
        }
        return $i0 == $r1.length() ? $r0.toString() : !Character.isUpperCase($c1) ? $r0.append(zza(Character.toUpperCase($c1), $r1, $i0 + 1)).toString() : $r1;
    }
}
