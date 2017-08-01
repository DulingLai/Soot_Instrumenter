package com.abaltatech.weblinkserver;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.weblinkserver.IWLAppManager.Stub;
import com.waze.phone.LoginOptionsActivity;
import java.util.Timer;
import java.util.TimerTask;

class WLAppManagerService extends Service {
    private static final String TAG = "WLAppManagerService";
    private final Stub m_binder = new C03991();

    class C03991 extends Stub {
        private static final int TIMEOUT = 15000;
        private String m_currApp = "";
        private String m_prevApp = null;
        private TimerTask m_timeoutHandler;
        private Timer m_timer = new Timer("timeout");

        final class TimeoutHandler extends TimerTask {
            String m_app;

            TimeoutHandler(String $r2) throws  {
                this.m_app = $r2;
            }

            public void run() throws  {
                MCSLogger.log(WLAppManagerService.TAG, "Timeout reached, restarting the previous WL server: " + this.m_app);
                C03991.this.m_timeoutHandler = null;
                if (this.m_app != null && !this.m_app.isEmpty()) {
                    C03991.this.activateApp(this.m_app);
                }
            }
        }

        C03991() throws  {
        }

        public boolean canActivateApp(String $r1) throws  {
            if ($r1 == null) {
                return false;
            }
            if ($r1.isEmpty()) {
                return false;
            }
            return WLAppManagerService.this.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse($r1)), 0).size() > 0;
        }

        public boolean activateApp(String $r1) throws  {
            boolean $z0 = false;
            MCSLogger.log(WLAppManagerService.TAG, "activateApp: " + $r1);
            if (canActivateApp($r1)) {
                Intent $r3 = new Intent("android.intent.action.VIEW", Uri.parse($r1));
                $r3.setFlags(277020672);
                try {
                    WLAppManagerService.this.startActivity($r3);
                    this.m_prevApp = this.m_currApp;
                    this.m_currApp = $r1;
                    $z0 = true;
                    if (!(this.m_prevApp == null || this.m_prevApp.isEmpty())) {
                        this.m_timeoutHandler = new TimeoutHandler(this.m_prevApp);
                        this.m_timer.schedule(this.m_timeoutHandler, LoginOptionsActivity.OPEN_VALIDATION_SCREEN_DELAY);
                    }
                    MCSLogger.log(WLAppManagerService.TAG, "activateApp: Application started - " + $r1);
                    return true;
                } catch (Throwable $r2) {
                    MCSLogger.log(WLAppManagerService.TAG, "Failed to activate " + $r1, $r2);
                    return $z0;
                }
            }
            MCSLogger.log(WLAppManagerService.TAG, "activateApp: Application unavailable - " + $r1);
            return false;
        }

        public String getCurrentAppID() throws  {
            return this.m_currApp;
        }

        public void onAppReadyForClient(String $r1) throws  {
            if (this.m_timeoutHandler != null) {
                this.m_timeoutHandler.cancel();
                this.m_timeoutHandler = null;
            }
            if ($r1 != null || this.m_prevApp == null || this.m_prevApp.isEmpty()) {
                this.m_currApp = $r1;
                MCSLogger.log(WLAppManagerService.TAG, "onAppReadyForClient: " + $r1);
                return;
            }
            $r1 = this.m_prevApp;
            this.m_currApp = null;
            this.m_prevApp = null;
            activateApp($r1);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) throws  {
        return 1;
    }

    WLAppManagerService() throws  {
    }

    public void onCreate() throws  {
        super.onCreate();
    }

    public void onDestroy() throws  {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) throws  {
        return this.m_binder;
    }
}
