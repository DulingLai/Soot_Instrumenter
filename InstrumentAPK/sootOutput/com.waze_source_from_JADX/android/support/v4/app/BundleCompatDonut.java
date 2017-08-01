package android.support.v4.app;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class BundleCompatDonut {
    private static final String TAG = "BundleCompatDonut";
    private static Method sGetIBinderMethod;
    private static boolean sGetIBinderMethodFetched;
    private static Method sPutIBinderMethod;
    private static boolean sPutIBinderMethodFetched;

    BundleCompatDonut() throws  {
    }

    public static IBinder getBinder(Bundle $r0, String $r1) throws  {
        Exception $r9;
        if (!sGetIBinderMethodFetched) {
            try {
                sGetIBinderMethod = Bundle.class.getMethod("getIBinder", new Class[]{String.class});
                sGetIBinderMethod.setAccessible(true);
            } catch (NoSuchMethodException $r8) {
                Log.i(TAG, "Failed to retrieve getIBinder method", $r8);
            }
            sGetIBinderMethodFetched = true;
        }
        if (sGetIBinderMethod != null) {
            try {
                return (IBinder) sGetIBinderMethod.invoke($r0, new Object[]{$r1});
            } catch (InvocationTargetException e) {
                $r9 = e;
                Log.i(TAG, "Failed to invoke getIBinder via reflection", $r9);
                sGetIBinderMethod = null;
                return null;
            } catch (IllegalAccessException e2) {
                $r9 = e2;
                Log.i(TAG, "Failed to invoke getIBinder via reflection", $r9);
                sGetIBinderMethod = null;
                return null;
            } catch (IllegalArgumentException e3) {
                $r9 = e3;
                Log.i(TAG, "Failed to invoke getIBinder via reflection", $r9);
                sGetIBinderMethod = null;
                return null;
            }
        }
        return null;
    }

    public static void putBinder(Bundle $r0, String $r1, IBinder $r2) throws  {
        Exception $r8;
        if (!sPutIBinderMethodFetched) {
            try {
                sPutIBinderMethod = Bundle.class.getMethod("putIBinder", new Class[]{String.class, IBinder.class});
                sPutIBinderMethod.setAccessible(true);
            } catch (NoSuchMethodException $r7) {
                Log.i(TAG, "Failed to retrieve putIBinder method", $r7);
            }
            sPutIBinderMethodFetched = true;
        }
        if (sPutIBinderMethod != null) {
            try {
                sPutIBinderMethod.invoke($r0, new Object[]{$r1, $r2});
            } catch (InvocationTargetException e) {
                $r8 = e;
                Log.i(TAG, "Failed to invoke putIBinder via reflection", $r8);
                sPutIBinderMethod = null;
            } catch (IllegalAccessException e2) {
                $r8 = e2;
                Log.i(TAG, "Failed to invoke putIBinder via reflection", $r8);
                sPutIBinderMethod = null;
            } catch (IllegalArgumentException e3) {
                $r8 = e3;
                Log.i(TAG, "Failed to invoke putIBinder via reflection", $r8);
                sPutIBinderMethod = null;
            }
        }
    }
}
