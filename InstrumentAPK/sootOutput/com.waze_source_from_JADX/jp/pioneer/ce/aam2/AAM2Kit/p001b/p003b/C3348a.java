package jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b;

import android.view.KeyEvent;
import android.view.MotionEvent;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;

public class C3348a {
    private static C3352e f223a = null;
    private static boolean f224b = true;

    private static void m293a(KeyEvent keyEvent) {
        if (f223a == null) {
            throw new IllegalStateException("PEventInputManager is null!");
        }
        f223a.mo4239a(keyEvent, false);
    }

    private static void m294a(MotionEvent motionEvent) {
        if (!f224b) {
            return;
        }
        if (f223a == null) {
            throw new IllegalStateException("PEventInputManager is null!");
        }
        f223a.mo4240a(motionEvent, false);
    }

    public static void m295a(Object obj) {
        if (obj == null) {
            C3347a.m292b("deliverEvent: Event to be delivered is null\t");
        } else if (obj instanceof MotionEvent) {
            C3348a.m294a((MotionEvent) obj);
        } else if (obj instanceof KeyEvent) {
            C3348a.m293a((KeyEvent) obj);
        }
    }

    public static void m296a(C3352e c3352e) {
        f223a = c3352e;
    }
}
