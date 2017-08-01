package jp.pioneer.mbg.pioneerkit.p008b;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3388a;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3390c;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3392e;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3395h;
import jp.pioneer.mbg.pioneerkit.replydata.C3440a;

class C3411d extends C3395h {
    final /* synthetic */ C3408a f426a;

    C3411d(C3408a c3408a) {
        this.f426a = c3408a;
    }

    public void mo4258a() {
        new Handler(Looper.getMainLooper()).post(new C3431x(this));
    }

    public void mo4259a(byte b, byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3415h(this, b, C3440a.m786a(bArr)));
    }

    public void mo4260a(int i) {
        new Handler(Looper.getMainLooper()).post(new C3412e(this, i));
    }

    public void mo4261a(int i, int i2) {
        new Handler(Looper.getMainLooper()).post(new C3419l(this, i, i2));
    }

    public void mo4262a(KeyEvent keyEvent, boolean z) {
    }

    public void mo4263a(MotionEvent motionEvent) {
        new Handler(Looper.getMainLooper()).post(new C3413f(this, motionEvent));
    }

    public void mo4264a(MotionEvent motionEvent, boolean z) {
    }

    public void mo4265a(C3388a c3388a) {
        new Handler(Looper.getMainLooper()).post(new C3430w(this, c3388a));
    }

    public void mo4266a(C3390c c3390c) {
        new Handler(Looper.getMainLooper()).post(new C3423p(this, c3390c));
    }

    public void mo4267a(C3392e c3392e) {
    }

    public void mo4268a(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3425r(this, z));
    }

    public void mo4269a(byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3429v(this, bArr));
    }

    public void mo4270b() {
    }

    public void mo4271b(int i) {
        new Handler(Looper.getMainLooper()).post(new C3426s(this, i));
    }

    public void mo4272b(MotionEvent motionEvent, boolean z) {
    }

    public void mo4273b(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3427t(this, z));
    }

    public void mo4274b(byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3414g(this));
    }

    public void mo4275c() {
        new Handler(Looper.getMainLooper()).post(new C3416i(this));
    }

    public void mo4276c(int i) {
        new Handler(Looper.getMainLooper()).post(new C3428u(this, i));
    }

    public void mo4277c(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3418k(this, z));
    }

    public void mo4278c(byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3424q(this, bArr));
    }

    public void mo4279d() {
        new Handler(Looper.getMainLooper()).post(new C3417j(this));
    }

    public void mo4280d(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3421n(this, z));
    }

    public void mo4281e() {
        new Handler(Looper.getMainLooper()).post(new C3420m(this));
    }

    public void mo4282e(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3422o(this, z));
    }
}
