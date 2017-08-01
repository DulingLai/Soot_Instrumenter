package jp.pioneer.ce.aam2.AAM2Kit.p001b;

import android.util.Log;

public class C3347a {
    public static String f220a = "ExtScreenService";
    public static String f221b = null;
    private static int f222c = 0;

    public static void m289a(String str) {
        C3347a.m291a("D", f220a, str);
        if ((f222c & 256) != 0) {
            Log.d(f220a, str);
        }
    }

    public static void m290a(String str, Exception exception) {
        C3347a.m291a("E", f220a, new StringBuilder(String.valueOf(str)).append(exception).toString());
        if ((f222c & 65536) != 0) {
            Log.e(f220a, str, exception);
        }
    }

    public static void m291a(String str, String str2, String str3) {
    }

    public static void m292b(String str) {
        C3347a.m291a("E", f220a, str);
        if ((f222c & 65536) != 0) {
            Log.e(f220a, str);
        }
    }
}
