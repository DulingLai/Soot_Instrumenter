package jp.pioneer.mbg.pioneerkit.p008b;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import jp.pioneer.mbg.pioneerkit.p005a.C3401a;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3398k;

class C3432y implements ServiceConnection {
    final /* synthetic */ C3408a f464a;

    C3432y(C3408a c3408a) {
        this.f464a = c3408a;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f464a.f405h = true;
        if (iBinder != null) {
            this.f464a.f400a = C3398k.m590b(iBinder);
            try {
                C3401a.m654a("onServiceConnected " + componentName + "," + this.f464a.f420w);
                this.f464a.f400a.mo4291a(this.f464a.f401b, this.f464a.f420w);
                this.f464a.f400a.mo4287a(this.f464a.f402c.getBinder(), this.f464a.f420w);
                this.f464a.f400a.mo4304b(this.f464a.f420w, "2.4.2");
                this.f464a.m691n();
                if (this.f464a.f421x != null) {
                    this.f464a.m697a(this.f464a.f421x);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f464a.f405h = false;
        this.f464a.f400a = null;
        C3401a.m654a("onServiceDisconnected " + componentName + "," + this.f464a.f420w);
        this.f464a.m690m();
        if (this.f464a.f421x != null) {
            this.f464a.m728e(this.f464a.f421x);
        }
    }
}
