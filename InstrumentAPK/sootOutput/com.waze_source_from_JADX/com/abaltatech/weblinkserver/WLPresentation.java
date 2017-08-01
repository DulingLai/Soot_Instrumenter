package com.abaltatech.weblinkserver;

import android.annotation.TargetApi;
import android.app.Presentation;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Display;
import com.abaltatech.mcs.logger.MCSLogger;
import com.waze.strings.DisplayStrings;
import java.util.Timer;
import java.util.TimerTask;

@TargetApi(19)
public class WLPresentation extends Presentation {
    private Timer m_timer;

    class C04141 extends TimerTask {
        C04141() throws  {
        }

        public void run() throws  {
            WLPresentation.this.getWindow().getDecorView().postInvalidate();
        }
    }

    public WLPresentation(Context $r1, Display $r2) throws  {
        super($r1, $r2);
    }

    public WLPresentation(Context $r1, Display $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if (VERSION.SDK_INT >= 19) {
            getWindow().setType(DisplayStrings.DS_RIDE_REQ_ACCEPT_RT_UNREGISTERED);
        }
    }

    protected void onStart() throws  {
        super.onStart();
        this.m_timer = new Timer();
        this.m_timer.schedule(new C04141(), 500, 200);
    }

    protected void onStop() throws  {
        if (this.m_timer != null) {
            this.m_timer.cancel();
            this.m_timer = null;
        }
        super.onStop();
    }

    public void onBackPressed() throws  {
        MCSLogger.log("WLPresentation", "WLPresentation has exited!");
        WLServer.getInstance().switchToHomeApp();
    }
}
