package jp.pioneer.mbg.pioneerkit.p008b;

import android.view.MotionEvent;

class C3413f implements Runnable {
    final /* synthetic */ C3411d f429a;
    private final /* synthetic */ MotionEvent f430b;

    C3413f(C3411d c3411d, MotionEvent motionEvent) {
        this.f429a = c3411d;
        this.f430b = motionEvent;
    }

    public void run() {
        this.f429a.f426a.m670a(this.f430b);
    }
}
