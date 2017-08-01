package jp.pioneer.mbg.pioneerkit.p008b;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import jp.pioneer.mbg.pioneerkit.C3407a;
import jp.pioneer.mbg.pioneerkit.C3434b;
import jp.pioneer.mbg.pioneerkit.C3435c;
import jp.pioneer.mbg.pioneerkit.C3436d;
import jp.pioneer.mbg.pioneerkit.C3437e;
import jp.pioneer.mbg.pioneerkit.ExtCertifiedInfo;
import jp.pioneer.mbg.pioneerkit.ExtDeviceSpecInfo;
import jp.pioneer.mbg.pioneerkit.ExtLocation;
import jp.pioneer.mbg.pioneerkit.IExtAppFocusListener;
import jp.pioneer.mbg.pioneerkit.IExtLocationListener;
import jp.pioneer.mbg.pioneerkit.IExtMediaInfoReqListener;
import jp.pioneer.mbg.pioneerkit.IExtRemoteCtrlListener;
import jp.pioneer.mbg.pioneerkit.IExtRequiredListener;
import jp.pioneer.mbg.pioneerkit.IExtSpecificDataListener;
import jp.pioneer.mbg.pioneerkit.p005a.C3401a;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3390c;
import jp.pioneer.mbg.pioneerkit.p005a.p006a.C3397j;
import jp.pioneer.mbg.pioneerkit.p005a.p007b.C3403b;

public class C3408a {
    private static boolean f392F = false;
    private static String f393f = "com.extscreen.service";
    private static C3408a f394g;
    private Handler f395A = null;
    private boolean f396B = false;
    private Runnable f397C = new C3409b(this);
    private IntentFilter f398D;
    private BroadcastReceiver f399E;
    protected C3397j f400a;
    protected C3411d f401b;
    protected Messenger f402c;
    final String f403d = "PionnerKit";
    ExtDeviceSpecInfo f404e = null;
    private boolean f405h;
    private boolean f406i = false;
    private C3432y f407j = null;
    private ArrayList f408k = new ArrayList();
    private ArrayList f409l = new ArrayList();
    private ArrayList f410m = new ArrayList();
    private ArrayList f411n = new ArrayList();
    private ArrayList f412o = new ArrayList();
    private ArrayList f413p = new ArrayList();
    private ArrayList f414q = new ArrayList();
    private ArrayList f415r = new ArrayList();
    private ArrayList f416s = new ArrayList();
    private ArrayList f417t = new ArrayList();
    private ArrayList f418u = new ArrayList();
    private ArrayList f419v = new ArrayList();
    private String f420w = null;
    private Context f421x = null;
    private int f422y = 0;
    private HandlerThread f423z = null;

    public C3408a() {
        C3401a.m654a("ExtScreenServiceManager created");
        this.f407j = new C3432y(this);
        this.f401b = new C3411d(this);
        this.f402c = new Messenger(C3403b.m661a().m663b());
    }

    private void m670a(MotionEvent motionEvent) {
        if (this.f415r != null && this.f415r.size() != 0) {
            Iterator it = this.f415r.iterator();
            while (it.hasNext()) {
                ((C3436d) it.next()).m770a(motionEvent);
            }
        }
    }

    private void m671a(ExtDeviceSpecInfo extDeviceSpecInfo) {
        if (this.f410m != null && this.f410m.size() != 0) {
            Iterator it = this.f410m.iterator();
            while (it.hasNext()) {
                ((IExtRequiredListener) it.next()).onStartAdvancedAppMode(extDeviceSpecInfo);
            }
        }
    }

    public static synchronized C3408a m679b() {
        C3408a c3408a;
        synchronized (C3408a.class) {
            if (f394g == null) {
                f394g = new C3408a();
                C3401a.m654a("ExtScreenServiceManager created " + f394g.toString());
            }
            c3408a = f394g;
        }
        return c3408a;
    }

    public static synchronized C3408a m682c() {
        C3408a c3408a;
        synchronized (C3408a.class) {
            c3408a = f394g;
        }
        return c3408a;
    }

    private void m683c(byte[] bArr) {
        if (this.f411n != null && this.f411n.size() != 0) {
            Iterator it = this.f411n.iterator();
            while (it.hasNext()) {
                ((C3435c) it.next()).m769a(bArr);
            }
        }
    }

    private void m685d(int i) {
        if (this.f414q != null && this.f414q.size() != 0) {
            Iterator it = this.f414q.iterator();
            while (it.hasNext()) {
                ((IExtRemoteCtrlListener) it.next()).onReceiveRemoteCtrl(i);
            }
        }
    }

    private void m686d(byte[] bArr) {
        if (this.f419v != null && this.f419v.size() != 0) {
            Iterator it = this.f419v.iterator();
            while (it.hasNext()) {
                ((IExtSpecificDataListener) it.next()).onReceiveSpecificData(bArr);
            }
        }
    }

    private void m690m() {
        if (this.f410m != null && this.f410m.size() != 0) {
            Iterator it = this.f410m.iterator();
            while (it.hasNext()) {
                ((IExtRequiredListener) it.next()).onStopAdvancedAppMode();
            }
        }
    }

    private void m691n() {
        if (this.f410m != null && this.f410m.size() != 0) {
            Iterator it = this.f410m.iterator();
            while (it.hasNext()) {
                IExtRequiredListener iExtRequiredListener = (IExtRequiredListener) it.next();
                ExtCertifiedInfo onRequireCertification = iExtRequiredListener.onRequireCertification();
                try {
                    boolean z;
                    C3401a.m654a("handleRequireCertification ");
                    if (this.f400a == null) {
                        z = false;
                    } else if (onRequireCertification == null) {
                        C3401a.m654a("handleRequireCertification " + this.f420w + "none");
                        z = this.f400a.mo4296a(this.f420w, "", "");
                    } else {
                        C3401a.m654a("handleRequireCertification " + onRequireCertification.getPackageName() + onRequireCertification.getCompanyName() + onRequireCertification.getSecretKey());
                        if (this.f420w.contentEquals(onRequireCertification.getPackageName())) {
                            z = this.f400a.mo4296a(onRequireCertification.getPackageName(), onRequireCertification.getCompanyName(), onRequireCertification.getSecretKey());
                        } else {
                            this.f400a.mo4305b(this.f420w, onRequireCertification.getCompanyName(), onRequireCertification.getSecretKey());
                            z = false;
                        }
                    }
                    iExtRequiredListener.onCertifiedResult(z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void m692a() {
        boolean a = m702a("jp.pioneer.mbg.appradio.AppRadioLauncher");
        boolean a2 = m702a("jp.pioneer.mbgdop.appradio.AppRadioLauncher");
        int i = 0;
        if (this.f421x != null && (this.f421x.getPackageName().contentEquals("jp.pioneer.mbg.appradio.AppRadioLauncher") || this.f421x.getPackageName().contentEquals("jp.pioneer.mbgdop.appradio.AppRadioLauncher"))) {
            i = 1;
        }
        if ((!a && !a2) || r0 != 0) {
            return;
        }
        if (this.f400a != null) {
            if (!this.f400a.asBinder().pingBinder() && this.f421x != null) {
                Intent intent = new Intent();
                intent.setAction(f393f);
                if (a) {
                    intent.setComponent(new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AppRadioService.app.ExtScreenService"));
                } else if (a2) {
                    intent.setComponent(new ComponentName("jp.pioneer.mbgdop.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AppRadioService.app.ExtScreenService"));
                }
                this.f421x.bindService(intent, this.f407j, 1);
            }
        } else if (this.f421x != null) {
            m728e(this.f421x);
        }
    }

    protected void m693a(byte b, long j) {
        if (this.f416s != null && this.f416s.size() != 0) {
            Iterator it = this.f416s.iterator();
            while (it.hasNext()) {
                ((IExtMediaInfoReqListener) it.next()).onReceiveTrackInfoRequest(b, j);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void m694a(int r3) {
        /*
        r2 = this;
        r0 = r2.f408k;
        if (r0 == 0) goto L_0x000c;
    L_0x0004:
        r0 = r2.f408k;
        r0 = r0.size();
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r0 = r2.f408k;
        r1 = r0.iterator();
        switch(r3) {
            case 65280: goto L_0x0017;
            case 65281: goto L_0x0040;
            case 65282: goto L_0x0050;
            case 65283: goto L_0x0060;
            case 65284: goto L_0x0030;
            default: goto L_0x0016;
        };
    L_0x0016:
        goto L_0x000c;
    L_0x0017:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x000c;
    L_0x001d:
        r0 = r1.next();
        r0 = (jp.pioneer.mbg.pioneerkit.C3438f) r0;
        r0.m778e();
        goto L_0x0017;
    L_0x0027:
        r0 = r1.next();
        r0 = (jp.pioneer.mbg.pioneerkit.C3438f) r0;
        r0.m774a();
    L_0x0030:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0027;
    L_0x0036:
        goto L_0x000c;
    L_0x0037:
        r0 = r1.next();
        r0 = (jp.pioneer.mbg.pioneerkit.C3438f) r0;
        r0.m777d();
    L_0x0040:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0037;
    L_0x0046:
        goto L_0x000c;
    L_0x0047:
        r0 = r1.next();
        r0 = (jp.pioneer.mbg.pioneerkit.C3438f) r0;
        r0.m776c();
    L_0x0050:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0047;
    L_0x0056:
        goto L_0x000c;
    L_0x0057:
        r0 = r1.next();
        r0 = (jp.pioneer.mbg.pioneerkit.C3438f) r0;
        r0.m775b();
    L_0x0060:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0057;
    L_0x0066:
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.pioneer.mbg.pioneerkit.b.a.a(int):void");
    }

    protected void m695a(int i, int i2) {
        if (this.f418u != null && this.f418u.size() != 0) {
            Iterator it = this.f418u.iterator();
            while (it.hasNext()) {
                ((C3437e) it.next()).m772a(i, i2);
            }
        }
    }

    public void m696a(long j, boolean z) {
        try {
            if (this.f400a != null) {
                this.f400a.mo4285a(j, z);
            }
        } catch (Exception e) {
        }
    }

    public void m697a(Context context) {
        if (!f392F) {
            this.f398D = new IntentFilter();
            this.f398D.addAction("jp.pioneer.mbg.appradio.AppRadioLauncher.BindFilter");
            this.f398D.addCategory("android.intent.category.BindFilter");
            this.f399E = new C3410c(this);
            if (context != null) {
                context.registerReceiver(this.f399E, this.f398D);
                f392F = true;
            }
        }
    }

    protected void m698a(C3390c c3390c) {
        if (this.f409l != null && this.f409l.size() != 0) {
            ExtLocation extLocation = new ExtLocation();
            extLocation.setAltitude(c3390c.m469e());
            extLocation.setBearing(c3390c.m471g());
            extLocation.setLatitude(c3390c.m467c());
            extLocation.setLongitude(c3390c.m468d());
            extLocation.setSpeed(c3390c.m470f());
            extLocation.setTime(c3390c.m465a());
            extLocation.setAccuracy(c3390c.m466b());
            extLocation.setRealGpsTime(c3390c.m472h() != (byte) 0);
            Iterator it = this.f409l.iterator();
            while (it.hasNext()) {
                ((IExtLocationListener) it.next()).onReceiveLocationInfo(extLocation);
            }
        }
    }

    protected void m699a(boolean z) {
        if (this.f410m != null && this.f410m.size() != 0) {
            Iterator it = this.f410m.iterator();
            while (it.hasNext()) {
                ((IExtRequiredListener) it.next()).onReceiveParkingInfo(z);
            }
        }
    }

    public void m700a(byte[] bArr) {
        if (this.f400a != null) {
            try {
                this.f400a.mo4293a(bArr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean m701a(byte b, byte[] bArr) {
        try {
            if (this.f400a == null) {
                return false;
            }
            this.f400a.mo4283a(b, bArr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean m702a(String str) {
        PackageInfo packageInfo = null;
        try {
            if (this.f421x != null) {
                packageInfo = this.f421x.getPackageManager().getPackageInfo(str, 0);
            }
        } catch (NameNotFoundException e) {
        }
        return packageInfo != null;
    }

    public boolean m703a(IExtAppFocusListener iExtAppFocusListener) {
        if (iExtAppFocusListener == null) {
            return false;
        }
        if (this.f417t.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4313d(this.f401b, this.f420w);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f417t.contains(iExtAppFocusListener)) {
            return false;
        }
        this.f417t.add(iExtAppFocusListener);
        return true;
    }

    public boolean m704a(IExtLocationListener iExtLocationListener) {
        if (this.f404e == null || this.f404e.getLocationDevice() == 0) {
            return false;
        }
        if (iExtLocationListener == null) {
            return false;
        }
        if (this.f409l.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4318e(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f409l.contains(iExtLocationListener)) {
            return false;
        }
        this.f409l.add(iExtLocationListener);
        return true;
    }

    public boolean m705a(IExtMediaInfoReqListener iExtMediaInfoReqListener) {
        if (iExtMediaInfoReqListener == null) {
            return false;
        }
        if (this.f416s.isEmpty()) {
            try {
                if (!(this.f400a == null || this.f420w == null)) {
                    this.f400a.mo4290a(this.f420w, true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f416s.contains(iExtMediaInfoReqListener)) {
            return false;
        }
        this.f416s.add(iExtMediaInfoReqListener);
        return true;
    }

    public boolean m706a(IExtRemoteCtrlListener iExtRemoteCtrlListener) {
        if (this.f404e == null || iExtRemoteCtrlListener == null) {
            return false;
        }
        if (this.f414q.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4310c(this.f401b, this.f420w);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f414q.contains(iExtRemoteCtrlListener)) {
            return false;
        }
        this.f414q.add(iExtRemoteCtrlListener);
        return true;
    }

    public boolean m707a(IExtRequiredListener iExtRequiredListener) {
        if (iExtRequiredListener == null || this.f410m.contains(iExtRequiredListener)) {
            return false;
        }
        this.f410m.add(iExtRequiredListener);
        return true;
    }

    public boolean m708a(IExtSpecificDataListener iExtSpecificDataListener) {
        if (iExtSpecificDataListener == null) {
            return false;
        }
        if (this.f419v.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4332j(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f419v.contains(iExtSpecificDataListener)) {
            return false;
        }
        this.f419v.add(iExtSpecificDataListener);
        return true;
    }

    protected void m709b(int i) {
        this.f422y = i;
        if (this.f413p != null && this.f413p.size() != 0) {
            Iterator it = this.f413p.iterator();
            while (it.hasNext()) {
                ((C3407a) it.next()).m669a(i);
            }
        }
    }

    public void m710b(long j, boolean z) {
        try {
            if (this.f400a != null) {
                this.f400a.mo4299b(j, z);
            }
        } catch (Exception e) {
        }
    }

    public void m711b(Context context) {
        if (f392F && context != null) {
            context.unregisterReceiver(this.f399E);
            f392F = false;
        }
    }

    protected void m712b(boolean z) {
        if (this.f410m != null && this.f410m.size() != 0) {
            Iterator it = this.f410m.iterator();
            while (it.hasNext()) {
                ((IExtRequiredListener) it.next()).onReceiveDriveStopping(z);
            }
        }
    }

    public boolean m713b(IExtAppFocusListener iExtAppFocusListener) {
        if (iExtAppFocusListener == null || this.f417t.isEmpty() || !this.f417t.remove(iExtAppFocusListener)) {
            return false;
        }
        if (this.f417t.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4317e(this.f420w);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m714b(IExtLocationListener iExtLocationListener) {
        if (iExtLocationListener == null || this.f409l.isEmpty() || !this.f409l.remove(iExtLocationListener)) {
            return false;
        }
        if (this.f409l.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4318e(false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m715b(IExtMediaInfoReqListener iExtMediaInfoReqListener) {
        if (iExtMediaInfoReqListener == null || this.f416s.isEmpty() || !this.f416s.remove(iExtMediaInfoReqListener)) {
            return false;
        }
        if (this.f416s.isEmpty()) {
            try {
                if (!(this.f400a == null || this.f420w == null)) {
                    this.f400a.mo4290a(this.f420w, false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m716b(IExtRemoteCtrlListener iExtRemoteCtrlListener) {
        if (iExtRemoteCtrlListener == null || this.f414q.isEmpty() || !this.f414q.remove(iExtRemoteCtrlListener)) {
            return false;
        }
        if (this.f414q.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4288a(this.f420w);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m717b(IExtRequiredListener iExtRequiredListener) {
        if (iExtRequiredListener == null) {
            return false;
        }
        this.f410m.remove(iExtRequiredListener);
        return true;
    }

    public boolean m718b(IExtSpecificDataListener iExtSpecificDataListener) {
        if (iExtSpecificDataListener == null || this.f419v.isEmpty() || !this.f419v.remove(iExtSpecificDataListener)) {
            return false;
        }
        if (this.f419v.isEmpty()) {
            try {
                if (this.f400a != null) {
                    this.f400a.mo4332j(false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m719b(byte[] bArr) {
        try {
            if (this.f400a == null) {
                return false;
            }
            this.f400a.mo4302b(bArr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void m720c(boolean z) {
        if (this.f410m != null && this.f410m.size() != 0) {
            Iterator it = this.f410m.iterator();
            while (it.hasNext()) {
                ((IExtRequiredListener) it.next()).onReceiveParkingSwitch(z);
            }
        }
    }

    public boolean m721c(int i) {
        try {
            if (this.f400a == null) {
                return false;
            }
            this.f400a.mo4289a(this.f420w, i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean m722c(Context context) {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        this.f421x = applicationContext;
        m697a(applicationContext);
        Intent intent = new Intent();
        intent.setAction(f393f);
        boolean a = m702a("jp.pioneer.mbg.appradio.AppRadioLauncher");
        boolean a2 = m702a("jp.pioneer.mbgdop.appradio.AppRadioLauncher");
        if (a) {
            intent.setComponent(new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AppRadioService.app.ExtScreenService"));
        } else if (!a2) {
            return false;
        } else {
            intent.setComponent(new ComponentName("jp.pioneer.mbgdop.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AppRadioService.app.ExtScreenService"));
        }
        applicationContext.startService(intent);
        return m728e(applicationContext);
    }

    public void m723d(Context context) {
        if (context != null) {
            m729f(context.getApplicationContext());
        }
    }

    protected void m724d(boolean z) {
        if (this.f412o != null && this.f412o.size() != 0) {
            Iterator it = this.f412o.iterator();
            while (it.hasNext()) {
                ((C3434b) it.next()).m768a(z);
            }
        }
    }

    public boolean m725d() {
        boolean z = false;
        if (this.f400a != null) {
            try {
                if (this.f406i) {
                    z = this.f400a.mo4303b(this.f420w);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    protected void m726e(boolean z) {
        if (this.f418u != null && this.f418u.size() != 0) {
            Iterator it = this.f418u.iterator();
            while (it.hasNext()) {
                ((C3437e) it.next()).m773a(z);
            }
        }
    }

    public boolean m727e() {
        boolean z = false;
        if (this.f406i && this.f400a != null) {
            try {
                z = this.f400a.mo4340p();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    protected boolean m728e(Context context) {
        if (this.f405h) {
            return true;
        }
        if (context instanceof Service) {
            this.f420w = context.getClass().getSimpleName();
        } else if ((context instanceof Activity) || (context instanceof Application)) {
            this.f420w = context.getPackageName();
        }
        Intent intent = new Intent();
        intent.setAction(f393f);
        boolean a = m702a("jp.pioneer.mbg.appradio.AppRadioLauncher");
        boolean a2 = m702a("jp.pioneer.mbgdop.appradio.AppRadioLauncher");
        if (a) {
            intent.setComponent(new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AppRadioService.app.ExtScreenService"));
        } else if (a2) {
            intent.setComponent(new ComponentName("jp.pioneer.mbgdop.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AppRadioService.app.ExtScreenService"));
        }
        if (!context.bindService(intent, this.f407j, 1)) {
            return false;
        }
        C3401a.m654a("bindSppService ");
        this.f406i = true;
        return true;
    }

    protected void m729f(Context context) {
        if (this.f405h) {
            if (this.f400a != null) {
                try {
                    this.f400a.mo4300b(this.f401b, this.f420w);
                    this.f400a.mo4286a(this.f402c.getBinder());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            C3401a.m654a("unBindSppService ");
            try {
                this.f405h = false;
                context.getApplicationContext().unbindService(this.f407j);
                m711b(context);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.f406i = false;
        }
    }

    public boolean m730f() {
        boolean z = false;
        if (this.f406i && this.f400a != null) {
            try {
                z = this.f400a.mo4326g();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    protected void m731g() {
        if (this.f418u != null && this.f418u.size() != 0) {
            Iterator it = this.f418u.iterator();
            while (it.hasNext()) {
                ((C3437e) it.next()).m771a();
            }
        }
    }

    protected void m732h() {
        if (this.f416s != null && this.f416s.size() != 0) {
            Iterator it = this.f416s.iterator();
            while (it.hasNext()) {
                ((IExtMediaInfoReqListener) it.next()).onReceiveTrackInfoSettingRequest();
            }
        }
    }

    protected void m733i() {
        if (this.f417t != null && this.f417t.size() != 0) {
            Iterator it = this.f417t.iterator();
            while (it.hasNext()) {
                ((IExtAppFocusListener) it.next()).onStartFocus();
            }
        }
    }

    protected void m734j() {
        if (this.f417t != null && this.f417t.size() != 0) {
            Iterator it = this.f417t.iterator();
            while (it.hasNext()) {
                ((IExtAppFocusListener) it.next()).onStopFocus();
            }
        }
    }

    public boolean m735k() {
        boolean z = false;
        try {
            if (this.f406i && this.f400a != null) {
                z = this.f400a.mo4316d(this.f420w);
            }
        } catch (Exception e) {
        }
        return z;
    }

    public boolean m736l() {
        boolean z = false;
        try {
            if (!(this.f400a == null || this.f420w == null)) {
                z = this.f400a.mo4323f(this.f420w);
            }
        } catch (Exception e) {
        }
        return z;
    }
}
