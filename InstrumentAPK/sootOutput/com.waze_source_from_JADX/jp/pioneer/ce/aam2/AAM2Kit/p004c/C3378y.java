package jp.pioneer.ce.aam2.AAM2Kit.p004c;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3344k;

class C3378y implements ServiceConnection {
    final /* synthetic */ C3354a f306a;

    C3378y(C3354a c3354a) {
        this.f306a = c3354a;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f306a.f248i = true;
        if (iBinder != null) {
            this.f306a.f242a = C3344k.m221b(iBinder);
            try {
                C3347a.m289a("onServiceConnected " + componentName + "," + this.f306a.f263x);
                this.f306a.f242a.mo4180a(this.f306a.f243b, this.f306a.f263x);
                this.f306a.f242a.mo4176a(this.f306a.f244c.getBinder(), this.f306a.f263x);
                this.f306a.f242a.mo4194b(this.f306a.f263x, "1.3.0");
                this.f306a.m329n();
                if (this.f306a.f246e != null) {
                    this.f306a.m335a(this.f306a.f246e);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f306a.f248i = false;
        this.f306a.f242a = null;
        C3347a.m289a("onServiceDisconnected " + componentName + "," + this.f306a.f263x);
        this.f306a.m328m();
        if (this.f306a.f246e != null) {
            this.f306a.m368e(this.f306a.f246e);
        }
    }
}
