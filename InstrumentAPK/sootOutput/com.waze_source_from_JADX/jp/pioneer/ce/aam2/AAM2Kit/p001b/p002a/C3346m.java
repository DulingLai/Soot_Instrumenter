package jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;

public class C3346m {
    private static final int f219a = VERSION.SDK_INT;

    public static Object m288a(Message message) {
        if (message.obj == null && f219a < 8) {
            Bundle peekData = message.peekData();
            if (peekData != null) {
                return peekData.getParcelable("Object");
            }
        } else if (message.obj != null) {
            return message.obj;
        }
        return null;
    }
}
