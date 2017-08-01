package jp.pioneer.mbg.pioneerkit.p005a.p007b;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class C3406e {
    protected boolean f391a = false;

    protected void m665a() {
        if (!this.f391a) {
            throw new IllegalStateException("PEventInputManager hasn't been initialized!");
        }
    }

    public abstract boolean mo4345a(Context context);

    public abstract boolean mo4346a(KeyEvent keyEvent, boolean z);

    public abstract boolean mo4347a(MotionEvent motionEvent, boolean z);
}
