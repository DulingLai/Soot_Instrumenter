package jp.pioneer.ce.aam2.AAM2Kit.p004c;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2CertifiedInfo;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2Kit;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2LocationInfo;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2MainUnitSpecInfo;
import jp.pioneer.ce.aam2.AAM2Kit.C3333a;
import jp.pioneer.ce.aam2.AAM2Kit.C3353b;
import jp.pioneer.ce.aam2.AAM2Kit.C3380c;
import jp.pioneer.ce.aam2.AAM2Kit.C3382e;
import jp.pioneer.ce.aam2.AAM2Kit.C3383f;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2AppFocusListener;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2ExtendedDataListener;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2LocationListener;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2MediaInfoReqListener;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2RemoteCtrlListener;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2RequiredListener;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3336c;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p002a.C3343j;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b.C3349b;
import jp.pioneer.ce.aam2.AAM2Kit.protocoldispacher.AAM2PDConnectionUtility;

public class C3354a {
    private static boolean f232G = false;
    private static boolean f233H = false;
    private static String f234g = "jp.pioneer.ce.aam2service";
    private static C3354a f235h;
    private Handler f236A = null;
    private boolean f237B = false;
    private int f238C = 0;
    private Runnable f239D = new C3355b(this);
    private IntentFilter f240E;
    private BroadcastReceiver f241F;
    protected C3343j f242a;
    protected C3357d f243b;
    protected Messenger f244c;
    final String f245d = "PionnerKit";
    public Context f246e = null;
    AAM2MainUnitSpecInfo f247f = null;
    private boolean f248i;
    private boolean f249j = false;
    private C3378y f250k = null;
    private ArrayList f251l = new ArrayList();
    private ArrayList f252m = new ArrayList();
    private ArrayList f253n = new ArrayList();
    private ArrayList f254o = new ArrayList();
    private ArrayList f255p = new ArrayList();
    private ArrayList f256q = new ArrayList();
    private ArrayList f257r = new ArrayList();
    private ArrayList f258s = new ArrayList();
    private ArrayList f259t = new ArrayList();
    private ArrayList f260u = new ArrayList();
    private ArrayList f261v = new ArrayList();
    private ArrayList f262w = new ArrayList();
    private String f263x = null;
    private int f264y = 0;
    private HandlerThread f265z = null;

    public C3354a() {
        C3347a.m289a("ExtScreenServiceManager created");
        this.f250k = new C3378y(this);
        this.f243b = new C3357d(this);
        this.f244c = new Messenger(C3349b.m297a().m299b());
    }

    private void m306a(MotionEvent motionEvent) {
        if (this.f258s != null && this.f258s.size() != 0) {
            Iterator it = this.f258s.iterator();
            while (it.hasNext()) {
                ((C3382e) it.next()).m417a(motionEvent);
            }
        }
    }

    private void m307a(AAM2MainUnitSpecInfo aAM2MainUnitSpecInfo) {
        if (this.f253n != null && this.f253n.size() != 0) {
            Iterator it = this.f253n.iterator();
            while (it.hasNext()) {
                ((IAAM2RequiredListener) it.next()).onAAM2StartAdvancedAppMode(aAM2MainUnitSpecInfo);
            }
        }
    }

    public static void m313a(boolean z) {
        f233H = z;
    }

    public static synchronized C3354a m316b() {
        C3354a c3354a;
        synchronized (C3354a.class) {
            if (f235h == null) {
                f235h = new C3354a();
                C3347a.m289a("ExtScreenServiceManager created " + f235h.toString());
            }
            c3354a = f235h;
        }
        return c3354a;
    }

    private boolean m318b(String str) {
        return str.contentEquals("com.justsystems.atokmobile.service") || str.contentEquals("com.justsystems.atokmobile.tv.service") || str.contentEquals("com.justsystems.atokmobile.pv.service") || str.contentEquals("com.justsystems.atokmobile.mv.service") || str.contentEquals("com.justsystems.atokmobile.mtv.service") || str.contentEquals("jp.pioneer.mbg.appradio.carkeyboard") || str.contentEquals("jp.pioneer.linkwithkeyboard.pb.atok") || str.contentEquals("com.pioneer.keyboard");
    }

    public static synchronized C3354a m320c() {
        C3354a c3354a;
        synchronized (C3354a.class) {
            c3354a = f235h;
        }
        return c3354a;
    }

    private void m321c(byte[] bArr) {
        if (this.f254o != null && this.f254o.size() != 0) {
            Iterator it = this.f254o.iterator();
            while (it.hasNext()) {
                ((C3380c) it.next()).m411a(bArr);
            }
        }
    }

    private void m323d(byte[] bArr) {
        if (this.f262w != null && this.f262w.size() != 0) {
            Iterator it = this.f262w.iterator();
            while (it.hasNext()) {
                ((IAAM2ExtendedDataListener) it.next()).onAAM2ReceiveExtendedData(bArr);
            }
        }
    }

    private void m324e(int i) {
        if (this.f257r != null && this.f257r.size() != 0) {
            Iterator it = this.f257r.iterator();
            while (it.hasNext()) {
                ((IAAM2RemoteCtrlListener) it.next()).onAAM2ReceiveRemoteCtrl(i);
            }
        }
    }

    private void m328m() {
        if (this.f253n != null && this.f253n.size() != 0) {
            Iterator it = this.f253n.iterator();
            while (it.hasNext()) {
                ((IAAM2RequiredListener) it.next()).onAAM2StopAdvancedAppMode();
            }
        }
    }

    private void m329n() {
        if (this.f253n != null && this.f253n.size() != 0) {
            Iterator it = this.f253n.iterator();
            while (it.hasNext()) {
                IAAM2RequiredListener iAAM2RequiredListener = (IAAM2RequiredListener) it.next();
                AAM2CertifiedInfo onAAM2RequireCertification = iAAM2RequiredListener.onAAM2RequireCertification();
                try {
                    boolean z;
                    C3347a.m289a("handleRequireCertification ");
                    if (this.f242a == null) {
                        z = false;
                    } else if (onAAM2RequireCertification == null) {
                        C3347a.m289a("handleRequireCertification " + this.f263x + "none");
                        z = this.f242a.mo4186a(this.f263x, "", "");
                    } else {
                        C3347a.m289a("handleRequireCertification " + onAAM2RequireCertification.m49b() + onAAM2RequireCertification.m48a() + onAAM2RequireCertification.m50c());
                        if (this.f263x.contentEquals(onAAM2RequireCertification.m49b())) {
                            z = this.f242a.mo4186a(onAAM2RequireCertification.m49b(), onAAM2RequireCertification.m48a(), onAAM2RequireCertification.m50c());
                        } else {
                            this.f242a.mo4195b(this.f263x, onAAM2RequireCertification.m48a(), onAAM2RequireCertification.m50c());
                            z = false;
                        }
                    }
                    iAAM2RequiredListener.onAAM2CertifiedResult(z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void m330a() {
        boolean a = m339a("jp.pioneer.mbg.appradio.AppRadioLauncher");
        boolean a2 = m339a("jp.pioneer.mbgdop.appradio.AppRadioLauncher");
        int i = 0;
        if (this.f246e != null && (this.f246e.getPackageName().contentEquals("jp.pioneer.mbg.appradio.AppRadioLauncher") || this.f246e.getPackageName().contentEquals("jp.pioneer.mbgdop.appradio.AppRadioLauncher"))) {
            i = 1;
        }
        if ((a || a2) && r0 == 0) {
            if (this.f242a != null) {
                if (!(this.f242a.asBinder().pingBinder() || this.f246e == null)) {
                    ComponentName componentName = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AAM2Service.app.ExtScreenService");
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    intent.setAction(f234g);
                    this.f246e.bindService(intent, this.f250k, 1);
                }
            } else if (this.f246e != null) {
                m368e(this.f246e);
            }
            m372g();
        }
    }

    protected void m331a(byte b, long j) {
        if (this.f259t != null && this.f259t.size() != 0) {
            Iterator it = this.f259t.iterator();
            while (it.hasNext()) {
                ((IAAM2MediaInfoReqListener) it.next()).onAAM2ReceiveTrackInfoRequest(b, j);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void m332a(int r3) {
        /*
        r2 = this;
        r0 = r2.f251l;
        if (r0 == 0) goto L_0x000c;
    L_0x0004:
        r0 = r2.f251l;
        r0 = r0.size();
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r0 = r2.f251l;
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
        r0 = (jp.pioneer.ce.aam2.AAM2Kit.C3381d) r0;
        r0.m416e();
        goto L_0x0017;
    L_0x0027:
        r0 = r1.next();
        r0 = (jp.pioneer.ce.aam2.AAM2Kit.C3381d) r0;
        r0.m412a();
    L_0x0030:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0027;
    L_0x0036:
        goto L_0x000c;
    L_0x0037:
        r0 = r1.next();
        r0 = (jp.pioneer.ce.aam2.AAM2Kit.C3381d) r0;
        r0.m415d();
    L_0x0040:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0037;
    L_0x0046:
        goto L_0x000c;
    L_0x0047:
        r0 = r1.next();
        r0 = (jp.pioneer.ce.aam2.AAM2Kit.C3381d) r0;
        r0.m414c();
    L_0x0050:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0047;
    L_0x0056:
        goto L_0x000c;
    L_0x0057:
        r0 = r1.next();
        r0 = (jp.pioneer.ce.aam2.AAM2Kit.C3381d) r0;
        r0.m413b();
    L_0x0060:
        r0 = r1.hasNext();
        if (r0 != 0) goto L_0x0057;
    L_0x0066:
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.pioneer.ce.aam2.AAM2Kit.c.a.a(int):void");
    }

    protected void m333a(int i, int i2) {
        if (this.f261v != null && this.f261v.size() != 0) {
            Iterator it = this.f261v.iterator();
            while (it.hasNext()) {
                ((C3383f) it.next()).m419a(i, i2);
            }
        }
    }

    public void m334a(long j, boolean z) {
        try {
            if (this.f242a != null) {
                this.f242a.mo4174a(j, z);
            }
        } catch (Exception e) {
        }
    }

    public void m335a(Context context) {
        if (!f232G) {
            this.f240E = new IntentFilter();
            this.f240E.addAction("jp.pioneer.mbg.appradio.aam2Serivce.BindFilter");
            this.f240E.addCategory("android.intent.category.BindFilter");
            this.f241F = new C3356c(this);
            if (context != null) {
                context.registerReceiver(this.f241F, this.f240E);
                f232G = true;
            }
        }
    }

    protected void m336a(C3336c c3336c) {
        if (this.f252m != null && this.f252m.size() != 0) {
            AAM2LocationInfo aAM2LocationInfo = new AAM2LocationInfo();
            aAM2LocationInfo.m59c(c3336c.m94e());
            aAM2LocationInfo.m58b(c3336c.m96g());
            aAM2LocationInfo.m52a(c3336c.m92c());
            aAM2LocationInfo.m57b(c3336c.m93d());
            aAM2LocationInfo.m53a(c3336c.m95f());
            aAM2LocationInfo.m55a(c3336c.m90a());
            aAM2LocationInfo.m54a(c3336c.m91b());
            aAM2LocationInfo.m56a(c3336c.m97h() != (byte) 0);
            Iterator it = this.f252m.iterator();
            while (it.hasNext()) {
                ((IAAM2LocationListener) it.next()).onAAM2ReceiveLocationInfo(aAM2LocationInfo);
            }
        }
    }

    public void m337a(byte[] bArr) {
        if (this.f242a != null) {
            try {
                this.f242a.mo4183a(bArr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean m338a(byte b, byte[] bArr) {
        try {
            if (this.f242a == null) {
                return false;
            }
            this.f242a.mo4172a(b, bArr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean m339a(String str) {
        PackageInfo packageInfo = null;
        try {
            if (this.f246e != null) {
                packageInfo = this.f246e.getPackageManager().getPackageInfo(str, 0);
            }
        } catch (NameNotFoundException e) {
        }
        return packageInfo != null;
    }

    public boolean m340a(String str, String str2, String str3) {
        if (this.f246e == null) {
            return false;
        }
        ApplicationInfo applicationInfo = null;
        PackageManager packageManager = this.f246e.getPackageManager();
        if (packageManager.checkPermission(str2, str) == 0) {
            return true;
        }
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 128);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return (applicationInfo == null || applicationInfo.metaData == null) ? false : applicationInfo.metaData.getBoolean(str3);
    }

    public boolean m341a(IAAM2AppFocusListener iAAM2AppFocusListener) {
        if (iAAM2AppFocusListener == null) {
            return false;
        }
        if (this.f260u.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4203d(this.f243b, this.f263x);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f260u.contains(iAAM2AppFocusListener)) {
            return false;
        }
        this.f260u.add(iAAM2AppFocusListener);
        return true;
    }

    public boolean m342a(IAAM2ExtendedDataListener iAAM2ExtendedDataListener) {
        if (iAAM2ExtendedDataListener == null) {
            return false;
        }
        if (this.f262w.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4222j(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f262w.contains(iAAM2ExtendedDataListener)) {
            return false;
        }
        this.f262w.add(iAAM2ExtendedDataListener);
        return true;
    }

    public boolean m343a(IAAM2LocationListener iAAM2LocationListener) {
        if (this.f247f == null || this.f247f.getLocationDevice() == 0) {
            return false;
        }
        if (iAAM2LocationListener == null) {
            return false;
        }
        if (this.f252m.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4208e(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f252m.contains(iAAM2LocationListener)) {
            return false;
        }
        this.f252m.add(iAAM2LocationListener);
        return true;
    }

    public boolean m344a(IAAM2MediaInfoReqListener iAAM2MediaInfoReqListener) {
        if (iAAM2MediaInfoReqListener == null) {
            return false;
        }
        if (this.f259t.isEmpty()) {
            try {
                if (!(this.f242a == null || this.f263x == null)) {
                    this.f242a.mo4179a(this.f263x, true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f259t.contains(iAAM2MediaInfoReqListener)) {
            return false;
        }
        this.f259t.add(iAAM2MediaInfoReqListener);
        return true;
    }

    public boolean m345a(IAAM2RemoteCtrlListener iAAM2RemoteCtrlListener) {
        if (this.f247f == null || iAAM2RemoteCtrlListener == null) {
            return false;
        }
        if (this.f257r.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4200c(this.f243b, this.f263x);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.f257r.contains(iAAM2RemoteCtrlListener)) {
            return false;
        }
        this.f257r.add(iAAM2RemoteCtrlListener);
        return true;
    }

    public boolean m346a(IAAM2RequiredListener iAAM2RequiredListener) {
        if (iAAM2RequiredListener == null || this.f253n.contains(iAAM2RequiredListener)) {
            return false;
        }
        this.f253n.add(iAAM2RequiredListener);
        return true;
    }

    public boolean m347a(C3380c c3380c) {
        if (c3380c == null || this.f254o.isEmpty() || !this.f254o.remove(c3380c)) {
            return false;
        }
        if (this.f254o.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4229n(false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    protected void m348b(int i) {
        this.f264y = i;
        if (this.f256q != null && this.f256q.size() != 0) {
            Iterator it = this.f256q.iterator();
            while (it.hasNext()) {
                ((C3333a) it.next()).m72a(i);
            }
        }
    }

    public void m349b(long j, boolean z) {
        try {
            if (this.f242a != null) {
                this.f242a.mo4189b(j, z);
            }
        } catch (Exception e) {
        }
    }

    public void m350b(Context context) {
        if (f232G && context != null) {
            context.unregisterReceiver(this.f241F);
            f232G = false;
        }
    }

    protected void m351b(boolean z) {
    }

    public boolean m352b(IAAM2AppFocusListener iAAM2AppFocusListener) {
        if (iAAM2AppFocusListener == null || this.f260u.isEmpty() || !this.f260u.remove(iAAM2AppFocusListener)) {
            return false;
        }
        if (this.f260u.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4207e(this.f263x);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m353b(IAAM2ExtendedDataListener iAAM2ExtendedDataListener) {
        if (iAAM2ExtendedDataListener == null || this.f262w.isEmpty() || !this.f262w.remove(iAAM2ExtendedDataListener)) {
            return false;
        }
        if (this.f262w.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4222j(false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m354b(IAAM2LocationListener iAAM2LocationListener) {
        if (iAAM2LocationListener == null || this.f252m.isEmpty() || !this.f252m.remove(iAAM2LocationListener)) {
            return false;
        }
        if (this.f252m.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4208e(false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m355b(IAAM2MediaInfoReqListener iAAM2MediaInfoReqListener) {
        if (iAAM2MediaInfoReqListener == null || this.f259t.isEmpty() || !this.f259t.remove(iAAM2MediaInfoReqListener)) {
            return false;
        }
        if (this.f259t.isEmpty()) {
            try {
                if (!(this.f242a == null || this.f263x == null)) {
                    this.f242a.mo4179a(this.f263x, false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m356b(IAAM2RemoteCtrlListener iAAM2RemoteCtrlListener) {
        if (iAAM2RemoteCtrlListener == null || this.f257r.isEmpty() || !this.f257r.remove(iAAM2RemoteCtrlListener)) {
            return false;
        }
        if (this.f257r.isEmpty()) {
            try {
                if (this.f242a != null) {
                    this.f242a.mo4177a(this.f263x);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean m357b(IAAM2RequiredListener iAAM2RequiredListener) {
        if (iAAM2RequiredListener == null) {
            return false;
        }
        this.f253n.remove(iAAM2RequiredListener);
        return true;
    }

    public boolean m358b(byte[] bArr) {
        try {
            if (this.f242a == null) {
                return false;
            }
            this.f242a.mo4192b(bArr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void m359c(boolean z) {
        if (this.f253n != null && this.f253n.size() != 0) {
            Iterator it = this.f253n.iterator();
            while (it.hasNext()) {
                ((IAAM2RequiredListener) it.next()).onAAM2ReceiveDriveStopping(z);
            }
        }
    }

    public boolean m360c(int i) {
        try {
            if (this.f242a == null) {
                return false;
            }
            this.f242a.mo4178a(this.f263x, i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean m361c(Context context) {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        this.f246e = applicationContext;
        ComponentName componentName = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AAM2Service.app.ExtScreenService");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setAction(f234g);
        if (applicationContext.startService(intent) != null) {
            C3347a.m289a("PDPrivateService starts successfully");
        }
        AAM2PDConnectionUtility instance = AAM2PDConnectionUtility.getInstance();
        if (instance != null) {
            instance.startWLServer();
        }
        return m368e(applicationContext);
    }

    public void m362d(int i) {
        this.f238C = i;
    }

    public void m363d(Context context) {
        if (context != null) {
            m369f(context.getApplicationContext());
        }
        AAM2PDConnectionUtility instance = AAM2PDConnectionUtility.getInstance();
        if (instance != null) {
            instance.stopWLServer();
        }
    }

    protected void m364d(boolean z) {
        if (this.f253n != null && this.f253n.size() != 0) {
            Iterator it = this.f253n.iterator();
            while (it.hasNext()) {
                ((IAAM2RequiredListener) it.next()).onAAM2ReceiveParkingSwitch(z);
            }
        }
    }

    public boolean m365d() {
        boolean z = false;
        if (this.f242a != null) {
            try {
                if (this.f249j) {
                    z = this.f242a.mo4193b(this.f263x);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    protected void m366e(boolean z) {
        if (this.f255p != null && this.f255p.size() != 0) {
            Iterator it = this.f255p.iterator();
            while (it.hasNext()) {
                ((C3353b) it.next()).m305a(z);
            }
        }
    }

    public boolean m367e() {
        boolean z = false;
        if (this.f249j && this.f242a != null) {
            try {
                z = this.f242a.mo4233p();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    protected boolean m368e(Context context) {
        if (this.f248i) {
            return true;
        }
        m335a(context);
        if (context instanceof Service) {
            this.f263x = context.getClass().getSimpleName();
        } else if ((context instanceof Activity) || (context instanceof Application)) {
            this.f263x = context.getPackageName();
        }
        if (this.f263x != null && !m340a(this.f263x, "pioneer.permission.appradio.AAM2", "pioneer_supported_aam2") && !m318b(this.f263x)) {
            return false;
        }
        ComponentName componentName = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "jp.pioneer.mbg.appradio.AAM2Service.app.ExtScreenService");
        Intent intent = new Intent();
        intent.setAction(f234g);
        intent.setComponent(componentName);
        if (!context.bindService(intent, this.f250k, 1)) {
            return false;
        }
        C3347a.m289a("bindSppService ");
        this.f249j = true;
        return true;
    }

    protected void m369f(Context context) {
        if (this.f248i) {
            if (this.f242a != null) {
                try {
                    this.f242a.mo4190b(this.f243b, this.f263x);
                    this.f242a.mo4175a(this.f244c.getBinder());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            C3347a.m289a("unBindSppService ");
            try {
                this.f248i = false;
                context.getApplicationContext().unbindService(this.f250k);
                m350b(context);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.f249j = false;
        }
    }

    protected void m370f(boolean z) {
        if (this.f261v != null && this.f261v.size() != 0) {
            Iterator it = this.f261v.iterator();
            while (it.hasNext()) {
                ((C3383f) it.next()).m420a(z);
            }
        }
    }

    public boolean m371f() {
        boolean z = false;
        if (this.f249j && this.f242a != null) {
            try {
                z = this.f242a.mo4216g();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    protected void m372g() {
        AAM2PDConnectionUtility instance = AAM2PDConnectionUtility.getInstance();
        if (instance != null) {
            instance.startWLServer();
        }
        if (f233H) {
            AAM2Kit.m51a(this.f246e);
        }
    }

    public boolean m373g(boolean z) {
        if (this.f246e == null || !this.f249j) {
            return false;
        }
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = this.f246e.getPackageManager().getApplicationInfo(this.f263x, 128);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return false;
        }
        Object obj = applicationInfo.metaData.get("aam2_sound_category");
        if (obj == null || !obj.equals("no_sound") || this.f242a == null) {
            return false;
        }
        try {
            this.f242a.mo4182a(z, this.f263x);
            return true;
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    protected void m374h() {
        if (this.f261v != null && this.f261v.size() != 0) {
            Iterator it = this.f261v.iterator();
            while (it.hasNext()) {
                ((C3383f) it.next()).m418a();
            }
        }
    }

    protected void m375i() {
        if (this.f259t != null && this.f259t.size() != 0) {
            Iterator it = this.f259t.iterator();
            while (it.hasNext()) {
                ((IAAM2MediaInfoReqListener) it.next()).onAAM2ReceiveTrackInfoSettingRequest();
            }
        }
    }

    protected void m376j() {
        if (this.f260u != null && this.f260u.size() != 0) {
            Iterator it = this.f260u.iterator();
            while (it.hasNext()) {
                ((IAAM2AppFocusListener) it.next()).onAAM2StartFocus();
            }
        }
    }

    protected void m377k() {
        if (this.f260u != null && this.f260u.size() != 0) {
            Iterator it = this.f260u.iterator();
            while (it.hasNext()) {
                ((IAAM2AppFocusListener) it.next()).onAAM2StopFocus();
            }
        }
    }

    public boolean m378l() {
        boolean z = false;
        try {
            if (!(this.f242a == null || this.f263x == null)) {
                z = this.f242a.mo4213f(this.f263x);
            }
        } catch (Exception e) {
        }
        return z;
    }
}
