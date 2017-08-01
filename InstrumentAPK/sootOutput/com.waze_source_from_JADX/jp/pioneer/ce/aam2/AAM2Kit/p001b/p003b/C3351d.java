package jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3346m;

class C3351d extends Handler {
    final /* synthetic */ C3350c f230a;

    public C3351d(C3350c c3350c, Looper looper) {
        this.f230a = c3350c;
        super(looper);
    }

    public void handleMessage(Message message) {
        if (message.what != 61440 && message.what == 65288) {
            C3348a.m295a(C3346m.m288a(message));
        }
    }
}
