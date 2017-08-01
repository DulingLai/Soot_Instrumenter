package android.support.v4.media;

import android.media.session.MediaSession.Token;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class IMediaBrowserServiceCallbacksAdapterApi21 {
    private Method mAsBinderMethod;
    Object mCallbackObject;
    private Method mOnConnectFailedMethod;
    private Method mOnConnectMethod;
    private Method mOnLoadChildrenMethod;

    static class Stub {
        static Method sAsInterfaceMethod;

        Stub() throws  {
        }

        static {
            ReflectiveOperationException $r3;
            try {
                Class $r0 = Class.forName("android.service.media.IMediaBrowserServiceCallbacks$Stub");
                Class[] $r1 = new Class[1];
                $r1[0] = IBinder.class;
                sAsInterfaceMethod = $r0.getMethod("asInterface", $r1);
            } catch (ClassNotFoundException e) {
                $r3 = e;
                $r3.printStackTrace();
            } catch (NoSuchMethodException e2) {
                $r3 = e2;
                $r3.printStackTrace();
            }
        }

        static Object asInterface(IBinder $r0) throws  {
            ReflectiveOperationException $r4;
            try {
                return sAsInterfaceMethod.invoke(null, new Object[]{$r0});
            } catch (IllegalAccessException e) {
                $r4 = e;
                $r4.printStackTrace();
                return null;
            } catch (InvocationTargetException e2) {
                $r4 = e2;
                $r4.printStackTrace();
                return null;
            }
        }
    }

    IMediaBrowserServiceCallbacksAdapterApi21(Object $r1) throws  {
        ReflectiveOperationException $r6;
        this.mCallbackObject = $r1;
        try {
            Class $r2 = Class.forName("android.service.media.IMediaBrowserServiceCallbacks");
            Class $r3 = Class.forName("android.content.pm.ParceledListSlice");
            this.mAsBinderMethod = $r2.getMethod("asBinder", new Class[0]);
            Class[] $r4 = new Class[3];
            $r4[0] = String.class;
            $r4[1] = Token.class;
            $r4[2] = Bundle.class;
            this.mOnConnectMethod = $r2.getMethod("onConnect", $r4);
            this.mOnConnectFailedMethod = $r2.getMethod("onConnectFailed", new Class[0]);
            $r4 = new Class[2];
            $r4[0] = String.class;
            $r4[1] = $r3;
            this.mOnLoadChildrenMethod = $r2.getMethod("onLoadChildren", $r4);
        } catch (ClassNotFoundException e) {
            $r6 = e;
            $r6.printStackTrace();
        } catch (NoSuchMethodException e2) {
            $r6 = e2;
            $r6.printStackTrace();
        }
    }

    IBinder asBinder() throws  {
        ReflectiveOperationException $r5;
        try {
            return (IBinder) this.mAsBinderMethod.invoke(this.mCallbackObject, new Object[0]);
        } catch (IllegalAccessException e) {
            $r5 = e;
            $r5.printStackTrace();
            return null;
        } catch (InvocationTargetException e2) {
            $r5 = e2;
            $r5.printStackTrace();
            return null;
        }
    }

    void onConnect(String $r1, Object $r2, Bundle $r3) throws RemoteException {
        ReflectiveOperationException $r7;
        try {
            this.mOnConnectMethod.invoke(this.mCallbackObject, new Object[]{$r1, $r2, $r3});
        } catch (IllegalAccessException e) {
            $r7 = e;
            $r7.printStackTrace();
        } catch (InvocationTargetException e2) {
            $r7 = e2;
            $r7.printStackTrace();
        }
    }

    void onConnectFailed() throws RemoteException {
        ReflectiveOperationException $r4;
        try {
            this.mOnConnectFailedMethod.invoke(this.mCallbackObject, new Object[0]);
        } catch (IllegalAccessException e) {
            $r4 = e;
            $r4.printStackTrace();
        } catch (InvocationTargetException e2) {
            $r4 = e2;
            $r4.printStackTrace();
        }
    }

    void onLoadChildren(String $r1, Object $r2) throws RemoteException {
        ReflectiveOperationException $r6;
        try {
            this.mOnLoadChildrenMethod.invoke(this.mCallbackObject, new Object[]{$r1, $r2});
        } catch (IllegalAccessException e) {
            $r6 = e;
            $r6.printStackTrace();
        } catch (InvocationTargetException e2) {
            $r6 = e2;
            $r6.printStackTrace();
        }
    }
}
