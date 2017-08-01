package com.google.android.gms.people.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzab;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class zzq {
    public static final Map<String, Integer> aQU = new HashMap();
    public static final Handler aQV = new Handler(Looper.getMainLooper());
    public static final String[] aQW = new String[0];
    public static final Pattern aQX = Pattern.compile("\\,");
    public static final Pattern aQY = Pattern.compile("[     ᠎             　\t\u000b\f\u001c\u001d\u001e\u001f\n\r]+");
    public static final Pattern aQZ = Pattern.compile(Pattern.quote(String.valueOf('\u0001')));
    public static final Pattern aRa = Pattern.compile(Pattern.quote(String.valueOf('\u0002')));
    public static final String aRb = String.valueOf('\u0001');
    public static final String aRc = String.valueOf('\u0002');
    public static final SecureRandom aRd = new SecureRandom();
    private static final ThreadLocal<StringBuilder> aRe = new C09911();
    private static final ThreadLocal<String[]> aRf = new C09922();
    private static final ThreadLocal<String[]> aRg = new C09933();
    private static final ThreadLocal<String[]> aRh = new C09944();
    private static final ThreadLocal<String[]> aRi = new C09955();
    private static final ThreadLocal<String[]> aRj = new C09966();

    /* compiled from: dalvik_source_com.waze.apk */
    class C09911 extends ThreadLocal<StringBuilder> {
        C09911() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzcfg();
        }

        protected StringBuilder zzcfg() throws  {
            return new StringBuilder();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09922 extends ThreadLocal<String[]> {
        C09922() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzcfh();
        }

        protected String[] zzcfh() throws  {
            return new String[1];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09933 extends ThreadLocal<String[]> {
        C09933() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzcfh();
        }

        protected String[] zzcfh() throws  {
            return new String[2];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09944 extends ThreadLocal<String[]> {
        C09944() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzcfh();
        }

        protected String[] zzcfh() throws  {
            return new String[3];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09955 extends ThreadLocal<String[]> {
        C09955() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzcfh();
        }

        protected String[] zzcfh() throws  {
            return new String[4];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09966 extends ThreadLocal<String[]> {
        C09966() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzcfh();
        }

        protected String[] zzcfh() throws  {
            return new String[5];
        }
    }

    static {
        aQU.put("circle", Integer.valueOf(-1));
        aQU.put("extendedCircles", Integer.valueOf(4));
        aQU.put("myCircles", Integer.valueOf(3));
        aQU.put("domain", Integer.valueOf(2));
        aQU.put("public", Integer.valueOf(1));
        aQU.put(null, Integer.valueOf(-2));
    }

    public static void zzbs(String $r0, String $r1) throws  {
        zzab.zzi($r0, $r1);
        boolean $z0 = $r0.startsWith("g:") || $r0.startsWith("e:");
        zzab.zzb($z0, String.valueOf($r1).concat(": Expecting qualified-id, not gaia-id"));
    }

    public static String[] zzbt(String $r0, String $r1) throws  {
        String[] $r4 = (String[]) aRg.get();
        $r4[0] = $r0;
        $r4[1] = $r1;
        return $r4;
    }

    public static StringBuilder zzcff() throws  {
        StringBuilder $r2 = (StringBuilder) aRe.get();
        $r2.setLength(0);
        return $r2;
    }

    public static Random zzdk(Context $r0) throws  {
        Random $r2 = (Random) $r0.getSystemService("gms.people.random");
        return $r2 != null ? $r2 : aRd;
    }

    public static String zzqy(String $r0) throws  {
        return ($r0 == null || $r0.length() == 0) ? null : $r0;
    }

    public static String[] zzqz(String $r0) throws  {
        return TextUtils.isEmpty($r0) ? aQW : aQX.split($r0, 0);
    }

    public static String zzra(String $r0) throws  {
        return ($r0 == null || !$r0.startsWith("g:")) ? null : $r0.substring("g:".length());
    }

    public static String zzrb(String $r0) throws  {
        zzab.zzag($r0);
        String $r1 = String.valueOf("g:");
        $r0 = String.valueOf($r0);
        return $r0.length() != 0 ? $r1.concat($r0) : new String($r1);
    }

    public static String zzrc(String $r0) throws  {
        return ($r0 == null || !$r0.startsWith("e:")) ? null : $r0.substring("e:".length());
    }

    public static String zzrd(String $r0) throws  {
        zzab.zzgy($r0);
        String $r1 = String.valueOf("e:");
        $r0 = String.valueOf($r0);
        return $r0.length() != 0 ? $r1.concat($r0) : new String($r1);
    }

    public static boolean zzre(String $r0) throws  {
        return $r0 != null && $r0.startsWith("e:");
    }

    public static boolean zzrf(String $r0) throws  {
        return $r0 != null && $r0.startsWith("g:");
    }

    public static boolean zzrg(String $r0) throws  {
        return zzre($r0) || zzrf($r0);
    }

    public static String zzrh(String $r0) throws  {
        int $i0 = 0;
        while ($i0 < $r0.length() && $r0.charAt($i0) == '0') {
            $i0++;
        }
        return $r0.substring($i0);
    }
}
