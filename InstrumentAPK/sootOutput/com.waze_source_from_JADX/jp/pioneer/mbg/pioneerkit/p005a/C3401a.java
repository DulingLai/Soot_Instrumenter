package jp.pioneer.mbg.pioneerkit.p005a;

import android.util.Log;

public class C3401a {
    public static String f380a = "ExtScreenService";
    public static String f381b = null;
    private static int f382c = 0;

    public static void m654a(String str) {
        C3401a.m655a("D", f380a, str);
        if ((f382c & 256) != 0) {
            Log.d(f380a, str);
        }
    }

    public static void m655a(String str, String str2, String str3) {
    }

    public static void m656b(String str) {
        C3401a.m655a("E", f380a, str);
        if ((f382c & 65536) != 0) {
            Log.e(f380a, str);
        }
    }
}
