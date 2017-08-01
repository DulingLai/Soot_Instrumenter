package jp.pioneer.mbg.pioneerkit.p008b;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.view.InputDeviceCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jp.pioneer.mbg.pioneerkit.p005a.C3401a;
import jp.pioneer.mbg.pioneerkit.p005a.p007b.C3406e;

public class C3433z extends C3406e {
    protected static final int f465b = VERSION.SDK_INT;
    private static C3406e f466c = null;
    private Method f467d = null;
    private Method f468e = null;
    private Method f469f = null;
    private Method f470g = null;
    private Object f471h = null;
    private Object f472i = null;

    private C3433z() {
    }

    public static synchronized C3406e m763b(Context context) {
        C3406e c3406e;
        synchronized (C3433z.class) {
            if (f466c == null) {
                f466c = new C3433z();
                f466c.mo4345a(context);
            }
            c3406e = f466c;
        }
        return c3406e;
    }

    public void m764a(MotionEvent motionEvent, int i) {
        motionEvent.setSource(i);
    }

    public boolean mo4345a(Context context) {
        if (context == null || this.a) {
            return false;
        }
        Method[] methods;
        int i;
        Method method;
        if (f465b < 16) {
            Class cls = Class.forName("android.view.IWindowManager$Stub");
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
            Object[] objArr = new Object[]{"window"};
            this.f471h = cls.getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{(IBinder) declaredMethod.invoke(null, objArr)});
            methods = cls.getMethods();
            i = 0;
            while (i < methods.length) {
                try {
                    method = methods[i];
                    if (method != null) {
                        String name = method.getName();
                        if (name.contentEquals("injectPointerEvent")) {
                            this.f467d = method;
                            C3401a.m654a(method.toString());
                        } else if (name.contentEquals("injectKeyEvent")) {
                            this.f468e = method;
                            C3401a.m654a(method.toString());
                        } else if (name.contentEquals("injectTrackballEvent")) {
                            this.f469f = method;
                            C3401a.m654a(method.toString());
                        } else if (name.contentEquals("setEventDispatching")) {
                            this.f470g = method;
                            C3401a.m654a(method.toString());
                        }
                        if (!(this.f467d == null || this.f468e == null || this.f469f == null || this.f470g == null)) {
                            break;
                        }
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        cls = Class.forName("android.hardware.input.IInputManager$Stub");
        declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
        objArr = new Object[]{"input"};
        this.f472i = cls.getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{(IBinder) declaredMethod.invoke(null, objArr)});
        methods = cls.getMethods();
        for (Method method2 : methods) {
            if (method2 != null) {
                if (method2.getName().contentEquals("injectInputEvent")) {
                    this.f467d = method2;
                }
                if (this.f467d != null) {
                    break;
                }
            }
        }
        this.a = true;
        return true;
    }

    public boolean mo4346a(KeyEvent keyEvent, boolean z) {
        m665a();
        if (f465b < 16 && this.f468e == null) {
            return false;
        }
        if (f465b >= 16 && this.f467d == null) {
            return false;
        }
        boolean booleanValue;
        try {
            if (f465b < 16) {
                booleanValue = ((Boolean) this.f468e.invoke(this.f471h, new Object[]{keyEvent, Boolean.FALSE})).booleanValue();
            } else {
                booleanValue = ((Boolean) this.f467d.invoke(this.f472i, new Object[]{keyEvent, Integer.valueOf(1)})).booleanValue();
            }
            try {
                if (keyEvent.getAction() != 0) {
                    return booleanValue;
                }
                C3408a.m682c().m710b(keyEvent.getEventTime(), true);
                return booleanValue;
            } catch (IllegalArgumentException e) {
                if (keyEvent.getAction() == 0) {
                    return booleanValue;
                }
                C3408a.m682c().m710b(keyEvent.getEventTime(), false);
                return booleanValue;
            } catch (IllegalAccessException e2) {
                if (keyEvent.getAction() == 0) {
                    return booleanValue;
                }
                C3408a.m682c().m710b(keyEvent.getEventTime(), false);
                return booleanValue;
            } catch (InvocationTargetException e3) {
                if (keyEvent.getAction() == 0) {
                    return booleanValue;
                }
                C3408a.m682c().m710b(keyEvent.getEventTime(), false);
                return booleanValue;
            }
        } catch (IllegalArgumentException e4) {
            booleanValue = false;
            if (keyEvent.getAction() == 0) {
                return booleanValue;
            }
            C3408a.m682c().m710b(keyEvent.getEventTime(), false);
            return booleanValue;
        } catch (IllegalAccessException e5) {
            booleanValue = false;
            if (keyEvent.getAction() == 0) {
                return booleanValue;
            }
            C3408a.m682c().m710b(keyEvent.getEventTime(), false);
            return booleanValue;
        } catch (InvocationTargetException e6) {
            booleanValue = false;
            if (keyEvent.getAction() == 0) {
                return booleanValue;
            }
            C3408a.m682c().m710b(keyEvent.getEventTime(), false);
            return booleanValue;
        }
    }

    public boolean mo4347a(MotionEvent motionEvent, boolean z) {
        boolean booleanValue;
        int i = 2;
        m665a();
        if (this.f467d == null) {
            return false;
        }
        try {
            if (f465b < 16) {
                booleanValue = ((Boolean) this.f467d.invoke(this.f471h, new Object[]{motionEvent, Boolean.valueOf(z)})).booleanValue();
            } else {
                if (!z) {
                    i = 1;
                }
                m764a(motionEvent, (int) InputDeviceCompat.SOURCE_TOUCHSCREEN);
                booleanValue = ((Boolean) this.f467d.invoke(this.f472i, new Object[]{motionEvent, Integer.valueOf(i)})).booleanValue();
            }
            try {
                if (motionEvent.getActionMasked() != 0) {
                    return booleanValue;
                }
                C3408a.m682c().m696a(motionEvent.getEventTime(), true);
                return booleanValue;
            } catch (IllegalArgumentException e) {
                if (motionEvent.getActionMasked() == 0) {
                    return booleanValue;
                }
                C3408a.m682c().m696a(motionEvent.getEventTime(), false);
                return booleanValue;
            } catch (IllegalAccessException e2) {
                if (motionEvent.getActionMasked() == 0) {
                    return booleanValue;
                }
                C3408a.m682c().m696a(motionEvent.getEventTime(), false);
                return booleanValue;
            } catch (InvocationTargetException e3) {
                if (motionEvent.getActionMasked() == 0) {
                    return booleanValue;
                }
                C3408a.m682c().m696a(motionEvent.getEventTime(), false);
                return booleanValue;
            }
        } catch (IllegalArgumentException e4) {
            booleanValue = false;
            if (motionEvent.getActionMasked() == 0) {
                return booleanValue;
            }
            C3408a.m682c().m696a(motionEvent.getEventTime(), false);
            return booleanValue;
        } catch (IllegalAccessException e5) {
            booleanValue = false;
            if (motionEvent.getActionMasked() == 0) {
                return booleanValue;
            }
            C3408a.m682c().m696a(motionEvent.getEventTime(), false);
            return booleanValue;
        } catch (InvocationTargetException e6) {
            booleanValue = false;
            if (motionEvent.getActionMasked() == 0) {
                return booleanValue;
            }
            C3408a.m682c().m696a(motionEvent.getEventTime(), false);
            return booleanValue;
        }
    }
}
