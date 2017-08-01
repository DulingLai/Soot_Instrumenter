package jp.pioneer.mbg.pioneerkit.p005a.p007b;

import android.os.Handler;

public class C3403b {
    private static String f385a = "EventProcess";
    private static C3403b f386b = null;
    private C3404c f387c = null;
    private Handler f388d = null;

    public static synchronized C3403b m661a() {
        C3403b c3403b;
        synchronized (C3403b.class) {
            if (f386b == null) {
                f386b = new C3403b();
                f386b.m662c();
            }
            c3403b = f386b;
        }
        return c3403b;
    }

    private void m662c() {
        this.f387c = new C3404c(f385a);
        this.f387c.start();
        this.f388d = this.f387c.m664a();
    }

    public Handler m663b() {
        if (this.f388d != null) {
            return this.f388d;
        }
        throw new IllegalStateException("EventProcessManager has not been init!");
    }
}
