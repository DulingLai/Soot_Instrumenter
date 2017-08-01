package jp.pioneer.ce.aam2.AAM2Kit;

public class AAM2MainUnitSpecInfo {
    private int f182a = 0;
    private int f183b;
    private int f184c;
    private int f185d;
    private int f186e = 0;
    private int f187f = 0;

    public void m60a(int i) {
        this.f182a = i;
    }

    public void m61b(int i) {
        this.f183b = i;
    }

    public void m62c(int i) {
        this.f184c = i;
    }

    public void m63d(int i) {
        this.f185d = i;
    }

    public void m64e(int i) {
        this.f186e = i;
    }

    public void m65f(int i) {
        this.f187f = i;
    }

    public int getConnectedMode() {
        return this.f186e;
    }

    public int getLocationDevice() {
        return this.f184c;
    }

    public int getMainUnitID() {
        return this.f182a;
    }

    public int getPointerNum() {
        return this.f183b;
    }

    public int getRemoteController() {
        return this.f185d;
    }
}
