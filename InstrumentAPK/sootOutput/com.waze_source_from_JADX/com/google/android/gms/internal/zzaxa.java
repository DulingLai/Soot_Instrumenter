package com.google.android.gms.internal;

import com.google.android.gms.people.PeopleConstants.Endpoints;
import dalvik.annotation.Signature;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaxa {
    private static void zza(String $r0, Object $r3, StringBuffer $r1, StringBuffer $r2) throws IllegalAccessException, InvocationTargetException {
        if ($r3 != null) {
            if ($r3 instanceof zzawz) {
                String $r5;
                int $i0 = $r1.length();
                if ($r0 != null) {
                    $r2.append($r1).append(zzyv($r0)).append(" <\n");
                    $r1.append("  ");
                }
                Class $r6 = $r3.getClass();
                for (Field $r8 : $r6.getFields()) {
                    int $i3 = $r8.getModifiers();
                    $r5 = $r8.getName();
                    if (!("cachedSize".equals($r5) || ($i3 & 1) != 1 || ($i3 & 8) == 8 || $r5.startsWith("_") || $r5.endsWith("_"))) {
                        Class $r10 = $r8.getType();
                        Object $r11 = $r8.get($r3);
                        if ($r10.isArray()) {
                            if ($r10.getComponentType() == Byte.TYPE) {
                                zza($r5, $r11, $r1, $r2);
                            } else {
                                $i3 = $r11 == null ? 0 : Array.getLength($r11);
                                for (int $i4 = 0; $i4 < $i3; $i4++) {
                                    zza($r5, Array.get($r11, $i4), $r1, $r2);
                                }
                            }
                        } else {
                            zza($r5, $r11, $r1, $r2);
                        }
                    }
                }
                for (Method $r15 : $r6.getMethods()) {
                    $r5 = $r15.getName();
                    if ($r5.startsWith("set")) {
                        $r5 = $r5.substring(3);
                        String $r9 = "has";
                        try {
                            String $r16 = String.valueOf($r5);
                            Class[] $r17 = new Class[null];
                            if (((Boolean) $r6.getMethod($r16.length() != 0 ? $r9.concat($r16) : new String("has"), $r17).invoke($r3, new Object[null])).booleanValue()) {
                                $r9 = Endpoints.ENDPOINT_GET;
                                try {
                                    $r16 = String.valueOf($r5);
                                    $r17 = new Class[null];
                                    zza($r5, $r6.getMethod($r16.length() != 0 ? $r9.concat($r16) : new String(Endpoints.ENDPOINT_GET), $r17).invoke($r3, new Object[null]), $r1, $r2);
                                } catch (NoSuchMethodException e) {
                                }
                            }
                        } catch (NoSuchMethodException e2) {
                        }
                    }
                }
                if ($r0 != null) {
                    $r1.setLength($i0);
                    $r2.append($r1).append(">\n");
                    return;
                }
                return;
            }
            $r2.append($r1).append(zzyv($r0)).append(": ");
            if ($r3 instanceof String) {
                $r2.append("\"").append(zzfd((String) $r3)).append("\"");
            } else if ($r3 instanceof byte[]) {
                zza((byte[]) $r3, $r2);
            } else {
                $r2.append($r3);
            }
            $r2.append("\n");
        }
    }

    private static void zza(byte[] $r0, StringBuffer $r1) throws  {
        if ($r0 == null) {
            $r1.append("\"\"");
            return;
        }
        $r1.append('\"');
        for (byte $b2 : $r0) {
            short $s3 = $b2 & (short) 255;
            if ($s3 == (short) 92 || $s3 == (short) 34) {
                $r1.append('\\').append((char) $s3);
            } else if ($s3 < (short) 32 || $s3 >= (short) 127) {
                $r1.append(String.format("\\%03o", new Object[]{Integer.valueOf($s3)}));
            } else {
                $r1.append((char) $s3);
            }
        }
        $r1.append('\"');
    }

    private static String zzfd(String $r0) throws  {
        if (!$r0.startsWith("http") && $r0.length() > 200) {
            $r0 = String.valueOf($r0.substring(0, 200)).concat("[...]");
        }
        return zzhf($r0);
    }

    public static <T extends zzawz> String zzg(@Signature({"<T:", "Lcom/google/android/gms/internal/zzawz;", ">(TT;)", "Ljava/lang/String;"}) T $r0) throws  {
        String $r1;
        String $r5;
        if ($r0 == null) {
            return "";
        }
        StringBuffer $r2 = new StringBuffer();
        try {
            zza(null, $r0, new StringBuffer(), $r2);
            return $r2.toString();
        } catch (IllegalAccessException $r4) {
            $r1 = "Error printing proto: ";
            $r5 = String.valueOf($r4.getMessage());
            return $r5.length() != 0 ? $r1.concat($r5) : new String("Error printing proto: ");
        } catch (InvocationTargetException $r6) {
            $r1 = "Error printing proto: ";
            $r5 = String.valueOf($r6.getMessage());
            return $r5.length() != 0 ? $r1.concat($r5) : new String("Error printing proto: ");
        }
    }

    private static String zzhf(String $r0) throws  {
        int $i0 = $r0.length();
        StringBuilder $r1 = new StringBuilder($i0);
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            char $c2 = $r0.charAt($i1);
            if ($c2 < ' ' || $c2 > '~' || $c2 == '\"' || $c2 == '\'') {
                $r1.append(String.format("\\u%04x", new Object[]{Integer.valueOf($c2)}));
            } else {
                $r1.append($c2);
            }
        }
        return $r1.toString();
    }

    private static String zzyv(String $r0) throws  {
        StringBuffer $r1 = new StringBuffer();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            char $c2 = $r0.charAt($i0);
            if ($i0 == 0) {
                $r1.append(Character.toLowerCase($c2));
            } else if (Character.isUpperCase($c2)) {
                $r1.append('_').append(Character.toLowerCase($c2));
            } else {
                $r1.append($c2);
            }
        }
        return $r1.toString();
    }
}
