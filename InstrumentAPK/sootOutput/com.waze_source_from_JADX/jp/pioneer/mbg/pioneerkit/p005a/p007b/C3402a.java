package jp.pioneer.mbg.pioneerkit.p005a.p007b;

import android.view.KeyEvent;
import android.view.MotionEvent;
import jp.pioneer.mbg.pioneerkit.p005a.C3401a;

public class C3402a {
    private static C3406e f383a = null;
    private static boolean f384b = true;

    private static void m657a(KeyEvent keyEvent) {
        if (f383a == null) {
            throw new IllegalStateException("PEventInputManager is null!");
        }
        f383a.mo4346a(keyEvent, false);
    }

    private static void m658a(MotionEvent motionEvent) {
        if (!f384b) {
            return;
        }
        if (f383a == null) {
            throw new IllegalStateException("PEventInputManager is null!");
        }
        f383a.mo4347a(motionEvent, false);
    }

    public static void m659a(Object obj) {
        if (obj == null) {
            C3401a.m656b("deliverEvent: Event to be delivered is null\t");
        } else if (obj instanceof MotionEvent) {
            C3402a.m658a((MotionEvent) obj);
        } else if (obj instanceof KeyEvent) {
            C3402a.m657a((KeyEvent) obj);
        }
    }

    public static void m660a(C3406e c3406e) {
        f383a = c3406e;
    }
}
