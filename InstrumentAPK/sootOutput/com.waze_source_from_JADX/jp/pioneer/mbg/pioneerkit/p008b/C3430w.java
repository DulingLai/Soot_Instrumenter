package jp.pioneer.mbg.pioneerkit.p008b;

import jp.pioneer.mbg.pioneerkit.ExtDeviceSpecInfo;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3388a;

class C3430w implements Runnable {
    final /* synthetic */ C3411d f461a;
    private final /* synthetic */ C3388a f462b;

    C3430w(C3411d c3411d, C3388a c3388a) {
        this.f461a = c3411d;
        this.f462b = c3388a;
    }

    public void run() {
        if (this.f462b == null) {
            this.f461a.f426a.m671a(this.f461a.f426a.f404e);
            return;
        }
        if (this.f461a.f426a.f404e == null) {
            this.f461a.f426a.f404e = new ExtDeviceSpecInfo();
        }
        this.f461a.f426a.f404e.setExtDeviceID(this.f462b.m450a());
        this.f461a.f426a.f404e.setLocationDevice(this.f462b.m452c());
        this.f461a.f426a.f404e.setPointerNum(this.f462b.m451b());
        this.f461a.f426a.f404e.setRemoteController(this.f462b.m453d());
        this.f461a.f426a.f404e.setConnectedMode(this.f462b.m454e());
        this.f461a.f426a.f404e.setCanBusController(this.f462b.m455f());
        this.f461a.f426a.m671a(this.f461a.f426a.f404e);
    }
}
