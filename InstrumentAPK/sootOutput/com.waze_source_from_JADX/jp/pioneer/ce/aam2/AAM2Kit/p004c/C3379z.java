package jp.pioneer.ce.aam2.AAM2Kit.p004c;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.view.InputDeviceCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b.C3352e;

public class C3379z extends C3352e {
    protected static final int f307b = VERSION.SDK_INT;
    private static C3352e f308c = null;
    private Method f309d = null;
    private Method f310e = null;
    private Method f311f = null;
    private Method f312g = null;
    private Object f313h = null;
    private Object f314i = null;

    private C3379z() {
    }

    public static synchronized C3352e m406b(Context context) {
        C3352e c3352e;
        synchronized (C3379z.class) {
            if (f308c == null) {
                f308c = new C3379z();
                f308c.mo4238a(context);
            }
            c3352e = f308c;
        }
        return c3352e;
    }

    public void m407a(MotionEvent motionEvent, int i) {
        motionEvent.setSource(i);
    }

    public boolean mo4238a(Context context) {
        if (context == null || this.a) {
            return false;
        }
        Method[] methods;
        int i;
        Method method;
        if (f307b < 16) {
            Class cls = Class.forName("android.view.IWindowManager$Stub");
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
            Object[] objArr = new Object[]{"window"};
            this.f313h = cls.getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{(IBinder) declaredMethod.invoke(null, objArr)});
            methods = cls.getMethods();
            i = 0;
            while (i < methods.length) {
                try {
                    method = methods[i];
                    if (method != null) {
                        String name = method.getName();
                        if (name.contentEquals("injectPointerEvent")) {
                            this.f309d = method;
                            C3347a.m289a(method.toString());
                        } else if (name.contentEquals("injectKeyEvent")) {
                            this.f310e = method;
                            C3347a.m289a(method.toString());
                        } else if (name.contentEquals("injectTrackballEvent")) {
                            this.f311f = method;
                            C3347a.m289a(method.toString());
                        } else if (name.contentEquals("setEventDispatching")) {
                            this.f312g = method;
                            C3347a.m289a(method.toString());
                        }
                        if (!(this.f309d == null || this.f310e == null || this.f311f == null || this.f312g == null)) {
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
        this.f314i = cls.getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{(IBinder) declaredMethod.invoke(null, objArr)});
        methods = cls.getMethods();
        for (Method method2 : methods) {
            if (method2 != null) {
                if (method2.getName().contentEquals("injectInputEvent")) {
                    this.f309d = method2;
                }
                if (this.f309d != null) {
                    break;
                }
            }
        }
        this.a = true;
        return true;
    }

    public boolean mo4239a(KeyEvent keyEvent, boolean z) {
        boolean booleanValue;
        m301a();
        if (f307b < 16 && this.f310e == null) {
            return false;
        }
        if (f307b >= 16 && this.f309d == null) {
            return false;
        }
        try {
            if (f307b < 16) {
                booleanValue = ((Boolean) this.f310e.invoke(this.f313h, new Object[]{keyEvent, Boolean.FALSE})).booleanValue();
            } else {
                booleanValue = ((Boolean) this.f309d.invoke(this.f314i, new Object[]{keyEvent, Integer.valueOf(1)})).booleanValue();
            }
            try {
                if (keyEvent.getAction() != 0) {
                    return booleanValue;
                }
                C3354a.m320c().m349b(keyEvent.getEventTime(), true);
                return booleanValue;
            } catch (IllegalArgumentException e) {
                if (keyEvent.getAction() == 0) {
                    return booleanValue;
                }
                C3354a.m320c().m349b(keyEvent.getEventTime(), false);
                return booleanValue;
            } catch (IllegalAccessException e2) {
                if (keyEvent.getAction() == 0) {
                    return booleanValue;
                }
                C3354a.m320c().m349b(keyEvent.getEventTime(), false);
                return booleanValue;
            } catch (InvocationTargetException e3) {
                if (keyEvent.getAction() == 0) {
                    return booleanValue;
                }
                C3354a.m320c().m349b(keyEvent.getEventTime(), false);
                return booleanValue;
            }
        } catch (IllegalArgumentException e4) {
            booleanValue = false;
            if (keyEvent.getAction() == 0) {
                return booleanValue;
            }
            C3354a.m320c().m349b(keyEvent.getEventTime(), false);
            return booleanValue;
        } catch (IllegalAccessException e5) {
            booleanValue = false;
            if (keyEvent.getAction() == 0) {
                return booleanValue;
            }
            C3354a.m320c().m349b(keyEvent.getEventTime(), false);
            return booleanValue;
        } catch (InvocationTargetException e6) {
            booleanValue = false;
            if (keyEvent.getAction() == 0) {
                return booleanValue;
            }
            C3354a.m320c().m349b(keyEvent.getEventTime(), false);
            return booleanValue;
        }
    }

    public boolean mo4240a(MotionEvent motionEvent, boolean z) {
        boolean booleanValue;
        int i = 2;
        m301a();
        if (this.f309d == null) {
            return false;
        }
        try {
            if (f307b < 16) {
                booleanValue = ((Boolean) this.f309d.invoke(this.f313h, new Object[]{motionEvent, Boolean.valueOf(z)})).booleanValue();
            } else {
                if (!z) {
                    i = 1;
                }
                m407a(motionEvent, (int) InputDeviceCompat.SOURCE_TOUCHSCREEN);
                booleanValue = ((Boolean) this.f309d.invoke(this.f314i, new Object[]{motionEvent, Integer.valueOf(i)})).booleanValue();
            }
            try {
                if (motionEvent.getActionMasked() != 0) {
                    return booleanValue;
                }
                C3354a.m320c().m334a(motionEvent.getEventTime(), true);
                return booleanValue;
            } catch (IllegalArgumentException e) {
                if (motionEvent.getActionMasked() == 0) {
                    return booleanValue;
                }
                C3354a.m320c().m334a(motionEvent.getEventTime(), false);
                return booleanValue;
            } catch (IllegalAccessException e2) {
                if (motionEvent.getActionMasked() == 0) {
                    return booleanValue;
                }
                C3354a.m320c().m334a(motionEvent.getEventTime(), false);
                return booleanValue;
            } catch (InvocationTargetException e3) {
                if (motionEvent.getActionMasked() == 0) {
                    return booleanValue;
                }
                C3354a.m320c().m334a(motionEvent.getEventTime(), false);
                return booleanValue;
            }
        } catch (IllegalArgumentException e4) {
            booleanValue = false;
            if (motionEvent.getActionMasked() == 0) {
                return booleanValue;
            }
            C3354a.m320c().m334a(motionEvent.getEventTime(), false);
            return booleanValue;
        } catch (IllegalAccessException e5) {
            booleanValue = false;
            if (motionEvent.getActionMasked() == 0) {
                return booleanValue;
            }
            C3354a.m320c().m334a(motionEvent.getEventTime(), false);
            return booleanValue;
        } catch (InvocationTargetException e6) {
            booleanValue = false;
            if (motionEvent.getActionMasked() == 0) {
                return booleanValue;
            }
            C3354a.m320c().m334a(motionEvent.getEventTime(), false);
            return booleanValue;
        }
    }
}
