package jp.pioneer.ce.aam2.AAM2Kit.p004c;

import android.view.MotionEvent;

class C3359f implements Runnable {
    final /* synthetic */ C3357d f271a;
    private final /* synthetic */ MotionEvent f272b;

    C3359f(C3357d c3357d, MotionEvent motionEvent) {
        this.f271a = c3357d;
        this.f272b = motionEvent;
    }

    public void run() {
        this.f271a.f268a.m306a(this.f272b);
    }
}
