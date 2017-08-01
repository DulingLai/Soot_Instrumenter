package jp.pioneer.ce.aam2.AAM2Kit.p004c;

class C3355b implements Runnable {
    final /* synthetic */ C3354a f266a;

    C3355b(C3354a c3354a) {
        this.f266a = c3354a;
    }

    public void run() {
        if (!this.f266a.f237B) {
            this.f266a.m330a();
            this.f266a.f236A.postDelayed(this.f266a.f239D, 5000);
        }
    }
}
