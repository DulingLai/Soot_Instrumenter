package jp.pioneer.ce.aam2.AAM2Kit.p000a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.abaltatech.protocoldispatcher.IPProtocolDispatcherPrivate;
import jp.pioneer.ce.aam2.AAM2Kit.p004c.C3354a;

public class C3331a {
    private static C3331a f188c = null;
    boolean f189a = false;
    private boolean f190b = false;
    private IPProtocolDispatcherPrivate f191d;
    private ServiceConnection f192e = new C3332b(this);

    public static C3331a m67a() {
        if (f188c == null) {
            f188c = new C3331a();
        }
        return f188c;
    }

    public void m71b() {
        if (!this.f190b) {
            ComponentName componentName = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "com.abaltatech.protocoldispatcher.ProtocolDispatcherService");
            Intent intent = new Intent("abaltatech.intent.action.bindProtocolDispatcherPrivateService");
            intent.setComponent(componentName);
            Context context = C3354a.m316b().f246e;
            if (context != null) {
                context.bindService(intent, this.f192e, 1);
            }
        }
    }
}
