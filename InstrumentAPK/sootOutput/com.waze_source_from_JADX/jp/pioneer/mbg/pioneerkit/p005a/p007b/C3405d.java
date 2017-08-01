package jp.pioneer.mbg.pioneerkit.p005a.p007b;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3400m;

class C3405d extends Handler {
    final /* synthetic */ C3404c f390a;

    public C3405d(C3404c c3404c, Looper looper) {
        this.f390a = c3404c;
        super(looper);
    }

    public void handleMessage(Message message) {
        if (message.what != 61440 && message.what == 65288) {
            C3402a.m659a(C3400m.m653a(message));
        }
    }
}
