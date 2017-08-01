package jp.pioneer.ce.aam2.AAM2Kit.p004c;

import jp.pioneer.ce.aam2.AAM2Kit.AAM2MainUnitSpecInfo;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3334a;

class C3376w implements Runnable {
    final /* synthetic */ C3357d f303a;
    private final /* synthetic */ C3334a f304b;

    C3376w(C3357d c3357d, C3334a c3334a) {
        this.f303a = c3357d;
        this.f304b = c3334a;
    }

    public void run() {
        if (this.f304b == null) {
            this.f303a.f268a.m307a(this.f303a.f268a.f247f);
            return;
        }
        if (this.f303a.f268a.f247f == null) {
            this.f303a.f268a.f247f = new AAM2MainUnitSpecInfo();
        }
        this.f303a.f268a.f247f.m60a(this.f304b.m75a());
        this.f303a.f268a.f247f.m62c(this.f304b.m77c());
        this.f303a.f268a.f247f.m61b(this.f304b.m76b());
        this.f303a.f268a.f247f.m63d(this.f304b.m78d());
        this.f303a.f268a.f247f.m64e(this.f304b.m79e());
        this.f303a.f268a.f247f.m65f(this.f304b.m80f());
        this.f303a.f268a.m307a(this.f303a.f268a.f247f);
    }
}
