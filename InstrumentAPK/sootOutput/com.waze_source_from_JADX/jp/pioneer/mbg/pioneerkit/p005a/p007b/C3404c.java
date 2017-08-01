package jp.pioneer.mbg.pioneerkit.p005a.p007b;

import android.os.Handler;
import android.os.HandlerThread;

public class C3404c extends HandlerThread {
    private C3405d f389a = null;

    public C3404c(String str) {
        super(str, -8);
    }

    public Handler m664a() {
        if (this.f389a == null) {
            this.f389a = new C3405d(this, getLooper());
        }
        return this.f389a;
    }

    protected void onLooperPrepared() {
        super.setPriority(4);
    }
}
