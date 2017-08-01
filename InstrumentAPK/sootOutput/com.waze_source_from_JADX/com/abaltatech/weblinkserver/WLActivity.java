package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.weblinkserver.WLServer.IDisplayListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"NewApi"})
class WLActivity extends Activity implements IDisplayListener {
    private static final String TAG = "WLActivity";
    private Timer m_timer;
    private WindowManager m_windowManager;

    class C03981 extends TimerTask {
        C03981() throws  {
        }

        public void run() throws  {
            WLActivity.this.getWindow().getDecorView().postInvalidate();
        }
    }

    WLActivity() throws  {
    }

    protected void onCreate(Bundle $r1) throws  {
        if (WLServerApp.getMode() == 2) {
            try {
                Field $r4 = Window.class.getDeclaredField("mAppToken");
                Field $r5 = Window.class.getDeclaredField("mAppName");
                Field $r6 = Window.class.getDeclaredField("mHardwareAccelerated");
                Field $r7 = Activity.class.getDeclaredField("mWindow");
                Field $r8 = Activity.class.getDeclaredField("mWindowManager");
                $r4.setAccessible(true);
                $r5.setAccessible(true);
                $r6.setAccessible(true);
                $r7.setAccessible(true);
                $r8.setAccessible(true);
                Window $r10 = (Window) $r7.get(this);
                $r10.setWindowManager((WindowManager) getSystemService("window"), (IBinder) $r4.get($r10), (String) $r5.get($r10), $r6.getBoolean($r10));
                this.m_windowManager = $r10.getWindowManager();
                $r8.set(this, this.m_windowManager);
                WLServer $r14 = WLServer.getInstance();
                $r14.registerActivity(this);
                $r14.registerDisplayListener(this);
            } catch (Throwable $r2) {
                MCSLogger.log("TAG", "onCreate()", $r2);
            }
        }
        super.onCreate($r1);
    }

    protected void onDestroy() throws  {
        WLServer $r1 = WLServer.getInstance();
        $r1.unregisterDisplayListener(this);
        $r1.unregisterActivity(this);
        super.onDestroy();
    }

    protected void onResume() throws  {
        super.onResume();
        this.m_timer = new Timer();
        this.m_timer.schedule(new C03981(), 500, 200);
    }

    protected void onPause() throws  {
        if (this.m_timer != null) {
            this.m_timer.cancel();
            this.m_timer = null;
        }
        super.onPause();
    }

    public Object getSystemService(String $r1) throws  {
        if (WLServerApp.getMode() == 2 && "window".equals($r1)) {
            return getBaseContext().getSystemService($r1);
        }
        return super.getSystemService($r1);
    }

    protected void attachBaseContext(Context $r2) throws  {
        if (WLServer.getInstance().getWLDisplay() != null && WLServerApp.getMode() == 2) {
            try {
                Method $r8 = Presentation.class.getDeclaredMethod("createPresentationContext", new Class[]{Context.class, Display.class, Integer.TYPE});
                $r8.setAccessible(true);
                $r2 = (Context) $r8.invoke(null, new Object[]{$r2, $r4, Integer.valueOf(0)});
            } catch (Throwable $r1) {
                MCSLogger.log(TAG, "attachBaseContext", $r1);
            }
        }
        super.attachBaseContext($r2);
    }

    public void onDisplayAdded(Display display) throws  {
    }

    public void onDisplayRemoved(Display display) throws  {
        finish();
    }
}
