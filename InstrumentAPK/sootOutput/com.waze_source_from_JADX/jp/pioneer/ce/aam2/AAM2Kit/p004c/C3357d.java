package jp.pioneer.ce.aam2.AAM2Kit.p004c;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3334a;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3336c;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3338e;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3341h;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.C3387a;

class C3357d extends C3341h {
    final /* synthetic */ C3354a f268a;

    C3357d(C3354a c3354a) {
        this.f268a = c3354a;
    }

    public void mo4146a() {
        new Handler(Looper.getMainLooper()).post(new C3377x(this));
    }

    public void mo4147a(byte b, byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3361h(this, b, C3387a.m440a(bArr)));
    }

    public void mo4148a(int i) {
        new Handler(Looper.getMainLooper()).post(new C3358e(this, i));
    }

    public void mo4149a(int i, int i2) {
        new Handler(Looper.getMainLooper()).post(new C3365l(this, i, i2));
    }

    public void mo4150a(KeyEvent keyEvent, boolean z) {
    }

    public void mo4151a(MotionEvent motionEvent) {
        new Handler(Looper.getMainLooper()).post(new C3359f(this, motionEvent));
    }

    public void mo4152a(MotionEvent motionEvent, boolean z) {
    }

    public void mo4153a(C3334a c3334a) {
        new Handler(Looper.getMainLooper()).post(new C3376w(this, c3334a));
    }

    public void mo4154a(C3336c c3336c) {
        new Handler(Looper.getMainLooper()).post(new C3369p(this, c3336c));
    }

    public void mo4155a(C3338e c3338e) {
    }

    public void mo4156a(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3371r(this, z));
    }

    public void mo4157a(byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3375v(this, bArr));
    }

    public void mo4158b() {
    }

    public void mo4159b(int i) {
        new Handler(Looper.getMainLooper()).post(new C3372s(this, i));
    }

    public void mo4160b(MotionEvent motionEvent, boolean z) {
    }

    public void mo4161b(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3373t(this, z));
    }

    public void mo4162b(byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3360g(this));
    }

    public void mo4163c() {
        new Handler(Looper.getMainLooper()).post(new C3362i(this));
    }

    public void mo4164c(int i) {
        new Handler(Looper.getMainLooper()).post(new C3374u(this, i));
    }

    public void mo4165c(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3364k(this, z));
    }

    public void mo4166c(byte[] bArr) {
        new Handler(Looper.getMainLooper()).post(new C3370q(this, bArr));
    }

    public void mo4167d() {
        new Handler(Looper.getMainLooper()).post(new C3363j(this));
    }

    public void mo4168d(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3367n(this, z));
    }

    public void mo4169e() {
        new Handler(Looper.getMainLooper()).post(new C3366m(this));
    }

    public void mo4170e(boolean z) {
        new Handler(Looper.getMainLooper()).post(new C3368o(this, z));
    }

    public int mo4171f() {
        return this.f268a.f238C;
    }
}
