package jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b;

import android.os.Handler;
import android.os.HandlerThread;

public class C3350c extends HandlerThread {
    private C3351d f229a = null;

    public C3350c(String str) {
        super(str, -8);
    }

    public Handler m300a() {
        if (this.f229a == null) {
            this.f229a = new C3351d(this, getLooper());
        }
        return this.f229a;
    }

    protected void onLooperPrepared() {
        super.setPriority(4);
    }
}
