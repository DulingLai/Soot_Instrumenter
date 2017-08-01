package jp.pioneer.mbg.pioneerkit.p008b;

class C3415h implements Runnable {
    final /* synthetic */ C3411d f432a;
    private final /* synthetic */ byte f433b;
    private final /* synthetic */ long f434c;

    C3415h(C3411d c3411d, byte b, long j) {
        this.f432a = c3411d;
        this.f433b = b;
        this.f434c = j;
    }

    public void run() {
        this.f432a.f426a.m693a(this.f433b, this.f434c);
    }
}
