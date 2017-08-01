package jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b;

import android.os.Handler;

public class C3349b {
    private static String f225a = "EventProcess";
    private static C3349b f226b = null;
    private C3350c f227c = null;
    private Handler f228d = null;

    public static synchronized C3349b m297a() {
        C3349b c3349b;
        synchronized (C3349b.class) {
            if (f226b == null) {
                f226b = new C3349b();
                f226b.m298c();
            }
            c3349b = f226b;
        }
        return c3349b;
    }

    private void m298c() {
        this.f227c = new C3350c(f225a);
        this.f227c.start();
        this.f228d = this.f227c.m300a();
    }

    public Handler m299b() {
        if (this.f228d != null) {
            return this.f228d;
        }
        throw new IllegalStateException("EventProcessManager has not been init!");
    }
}
