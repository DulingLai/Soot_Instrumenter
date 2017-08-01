package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;

@SuppressLint({"NewApi"})
public class WLServerApp extends Application {
    public static final int DEFAULT_MODE = 0;
    public static final int MIRROR_MODE = 1;
    public static final int PRESENTATION_MODE = 2;
    protected static IWLLockScreenViewProvider m_lockScreenViewProvider = null;
    protected static boolean m_showLockScreenInMirrorMode = false;
    protected static Context ms_context;
    protected static WLServerApp ms_instance = null;
    protected static int ms_mode = 0;

    private static synchronized Context getContext() throws  {
        Class cls = WLServerApp.class;
        synchronized (cls) {
            try {
                Context r1 = ms_context;
                return r1;
            } finally {
                cls = WLServerApp.class;
            }
        }
    }

    static {
        if (VERSION.SDK_INT >= WLServer.MIN_ANDROID_OS_SUPPORTED_VERSION) {
            System.loadLibrary("WebLinkServerLib");
        }
    }

    public static Context getAppContext() throws  {
        Context $r0 = getContext();
        return $r0 != null ? $r0.getApplicationContext() : null;
    }

    public static int getMode() throws  {
        return ms_mode;
    }

    public static void setMode(int $i0) throws  {
        ms_mode = $i0;
    }

    public void onCreate() throws  {
        super.onCreate();
        ms_instance = this;
        setContext(this);
    }

    static synchronized void setContext(Context $r0) throws  {
        Class cls = WLServerApp.class;
        synchronized (cls) {
            try {
                ms_context = $r0;
            } finally {
                cls = WLServerApp.class;
            }
        }
    }

    public static WLServerApp getApplication() throws  {
        return ms_instance;
    }

    public static boolean getShowLockScreenInMirrorMode() throws  {
        return m_showLockScreenInMirrorMode;
    }

    public static void setShowLockScreenInMirrorMode(boolean $z0) throws  {
        m_showLockScreenInMirrorMode = $z0;
    }

    public static IWLLockScreenViewProvider getLockScreenViewProvider() throws  {
        return m_lockScreenViewProvider;
    }

    public static void setLockScreenViewProvider(IWLLockScreenViewProvider $r0) throws  {
        m_lockScreenViewProvider = $r0;
    }
}
