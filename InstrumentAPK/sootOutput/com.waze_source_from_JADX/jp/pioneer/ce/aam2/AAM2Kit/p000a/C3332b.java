package jp.pioneer.ce.aam2.AAM2Kit.p000a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import com.abaltatech.protocoldispatcher.IPProtocolDispatcherPrivate.Stub;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;
import jp.pioneer.ce.aam2.AAM2Kit.p004c.C3354a;

class C3332b implements ServiceConnection {
    final /* synthetic */ C3331a f193a;

    C3332b(C3331a c3331a) {
        this.f193a = c3331a;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f193a.f191d = Stub.asInterface(iBinder);
        this.f193a.f190b = true;
        if (this.f193a.f189a) {
            if (this.f193a.f191d != null) {
                try {
                    this.f193a.f191d.setActiveApp(Process.myPid());
                } catch (Exception e) {
                    C3347a.m290a("An error occured during the call", e);
                }
            }
            this.f193a.f189a = false;
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f193a.f191d = null;
        this.f193a.f190b = false;
        ComponentName componentName2 = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "com.abaltatech.protocoldispatcher.ProtocolDispatcherService");
        Intent intent = new Intent("abaltatech.intent.action.bindProtocolDispatcherPrivateService");
        intent.setComponent(componentName2);
        Context context = C3354a.m316b().f246e;
        if (context != null) {
            context.bindService(intent, this.f193a.f192e, 1);
        }
    }
}
