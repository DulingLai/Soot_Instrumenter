package jp.pioneer.mbg.pioneerkit.p008b;

class C3409b implements Runnable {
    final /* synthetic */ C3408a f424a;

    C3409b(C3408a c3408a) {
        this.f424a = c3408a;
    }

    public void run() {
        if (!this.f424a.f396B) {
            this.f424a.m692a();
            this.f424a.f395A.postDelayed(this.f424a.f397C, 5000);
        }
    }
}
