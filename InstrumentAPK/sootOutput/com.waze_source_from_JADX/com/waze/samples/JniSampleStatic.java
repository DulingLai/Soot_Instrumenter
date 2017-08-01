package com.waze.samples;

import android.util.Log;

public class JniSampleStatic {
    private static final int INTEGER_FIELD = 2011;
    private static final String LOG_TAG = "WAZE JniStaticSample";
    private static final String STRING_FIELD = "StaticMethod call from native layer";

    public static final class JniSampleStaticData {
        public int _int;
        public String _string;

        public JniSampleStaticData(int i) {
            this._int = i;
        }
    }

    private static native void InitNativeLayerNTV();

    private static native void RunSampleNTV(String str, int i);

    public static void InitNativeLayer() {
        InitNativeLayerNTV();
    }

    public static void StaticMethod(JniSampleStaticData data) {
        Log.w(LOG_TAG, "## ============== Call from Native layer ============== \n## JniSampleStatic.StaticMethod.\n## String: '" + data._string + "'\n" + "## Integer: " + data._int);
    }

    public static void runSample() {
        Log.w(LOG_TAG, "## ============== Jni Sample for static calls and C=>Java data pass has been executed. ============== \n## Your are supposed to see JniSampleStatic.StaticMethod print with string and integer fields \n## String field: 'StaticMethod call from native layer'\n## Integer field: 2011");
        InitNativeLayer();
        RunSampleNTV(STRING_FIELD, 2011);
    }

    private JniSampleStatic() {
    }
}
