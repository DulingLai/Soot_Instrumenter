package jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class C3352e {
    protected boolean f231a = false;

    protected void m301a() {
        if (!this.f231a) {
            throw new IllegalStateException("PEventInputManager hasn't been initialized!");
        }
    }

    public abstract boolean mo4238a(Context context);

    public abstract boolean mo4239a(KeyEvent keyEvent, boolean z);

    public abstract boolean mo4240a(MotionEvent motionEvent, boolean z);
}
