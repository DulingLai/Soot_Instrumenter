package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Presentation;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import com.abaltatech.mcp.weblink.sdk.WLDisplayManager.IWLDisplayListener;
import com.waze.strings.DisplayStrings;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"RtlHardcoded"})
public class WLPresentation {
    private static ArrayList<WLPresentation> s_presentations = new ArrayList();
    private WLDialog m_dialog;
    private WLDisplay m_display;
    private WLPresentationInternal m_presentation;
    private Timer m_timer;
    IWLPresentation m_wlPresentation;

    class C03611 extends TimerTask {
        C03611() throws  {
        }

        public void run() throws  {
            WLPresentation.this.getWindow().getDecorView().postInvalidate();
        }
    }

    interface IWLPresentation {
        void doOnCreate(Bundle bundle) throws ;

        void doOnDisplayRemoved() throws ;

        void doOnStart() throws ;

        void doOnStop() throws ;

        View findViewById(int i) throws ;

        ActionBar getActionBar() throws ;

        Context getContext() throws ;

        LayoutInflater getLayoutInflater() throws ;

        Window getWindow() throws ;

        void setContentView(int i) throws ;

        void setContentView(View view) throws ;

        void setContentView(View view, LayoutParams layoutParams) throws ;

        void show() throws ;
    }

    static class WLDialog extends Dialog implements IWLDisplayListener, IWLPresentation {
        protected static final String TAG = "WLDialog";
        private int m_displayID;
        private WLPresentation m_presentation;

        class C03621 implements OnSystemUiVisibilityChangeListener {
            C03621() throws  {
            }

            public void onSystemUiVisibilityChange(int $i0) throws  {
                if (($i0 & 4) == 0) {
                    Log.d(WLDialog.TAG, "Exit full screen");
                    WLMirrorMode.setFullScreenMode(false);
                    return;
                }
                Log.d(WLDialog.TAG, "Enter full screen");
                WLMirrorMode.setFullScreenMode(true);
            }
        }

        public WLDialog(Context $r1, WLPresentation $r2) throws  {
            super($r1);
            this.m_presentation = $r2;
            init();
        }

        public WLDialog(Context $r1, int $i0, WLPresentation $r2) throws  {
            super($r1, $i0);
            this.m_presentation = $r2;
            init();
        }

        private void init() throws  {
            this.m_displayID = this.m_presentation.getDisplay().getDisplayId();
        }

        protected void onCreate(Bundle $r1) throws  {
            this.m_presentation.onCreate($r1);
        }

        public void doOnCreate(Bundle $r1) throws  {
            super.onCreate($r1);
            getWindow().requestFeature(1);
        }

        public void doOnDisplayRemoved() throws  {
        }

        protected void onStart() throws  {
            this.m_presentation.onStart();
        }

        public void doOnStart() throws  {
            super.onStart();
            WLMirrorMode.instance.addOffScreenView(getWindow().getDecorView());
            WLDisplayManager.getInstance().registerDisplayListener(this, null);
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new C03621());
        }

        protected void onStop() throws  {
            WLMirrorMode.instance.removeOffScreenView(getWindow().getDecorView());
            WLDisplayManager.getInstance().unregisterDisplayListener(this);
            this.m_presentation.onStop();
        }

        public void doOnStop() throws  {
            super.onStop();
        }

        public void onDisplayAdded(int displayId) throws  {
        }

        public void onDisplayRemoved(int $i0) throws  {
            if ($i0 == this.m_displayID) {
                WLDisplayManager.getInstance().unregisterDisplayListener(this);
                dismiss();
            }
        }

        public void onDisplayChanged(int displayId) throws  {
        }

        @SuppressLint({"NewApi"})
        public void show() throws  {
            super.show();
            WLDisplay $r3 = this.m_presentation.getDisplay();
            Window $r4 = getWindow();
            WindowManager.LayoutParams $r1 = new WindowManager.LayoutParams();
            $r1.copyFrom($r4.getAttributes());
            $r1.gravity = 51;
            $r1.flags |= 512;
            $r1.width = $r3.getWidth();
            $r1.height = $r3.getHeight();
            $r1.x = 4000;
            $r1.y = 4000;
            $r4.setAttributes($r1);
            setCanceledOnTouchOutside(false);
        }
    }

    @TargetApi(17)
    static class WLPresentationInternal extends Presentation implements IWLPresentation {
        private WLPresentation m_presentation;

        public WLPresentationInternal(Context $r1, Display $r2, WLPresentation $r3) throws  {
            super($r1, $r2);
            this.m_presentation = $r3;
        }

        public WLPresentationInternal(Context $r1, Display $r2, int $i0, WLPresentation $r3) throws  {
            super($r1, $r2, $i0);
            this.m_presentation = $r3;
        }

        protected void onCreate(Bundle $r1) throws  {
            this.m_presentation.onCreate($r1);
        }

        public void doOnCreate(Bundle $r1) throws  {
            super.onCreate($r1);
        }

        protected void onStart() throws  {
            this.m_presentation.onStart();
        }

        public void doOnStart() throws  {
            super.onStart();
        }

        protected void onStop() throws  {
            this.m_presentation.onStop();
        }

        public void doOnStop() throws  {
            super.onStop();
        }

        public void onDisplayRemoved() throws  {
            this.m_presentation.onDisplayRemoved();
        }

        public void doOnDisplayRemoved() throws  {
            super.onDisplayRemoved();
        }

        public void onBackPressed() throws  {
            this.m_presentation.onBackPressed();
        }
    }

    static ArrayList<WLPresentation> getPresentations() throws  {
        return s_presentations;
    }

    public WLPresentation(Context $r1, WLDisplay $r2) throws  {
        this.m_display = $r2;
        if (VERSION.SDK_INT >= 19) {
            this.m_presentation = new WLPresentationInternal($r1, (Display) $r2.getDisplay(), this);
            this.m_wlPresentation = this.m_presentation;
            return;
        }
        this.m_dialog = new WLDialog($r1, this);
        this.m_wlPresentation = this.m_dialog;
    }

    public WLPresentation(Context $r1, WLDisplay $r2, int $i0) throws  {
        this.m_display = $r2;
        if (VERSION.SDK_INT >= 19) {
            this.m_presentation = new WLPresentationInternal($r1, (Display) $r2.getDisplay(), $i0, this);
            this.m_wlPresentation = this.m_presentation;
            return;
        }
        this.m_dialog = new WLDialog($r1, $i0, this);
        this.m_wlPresentation = this.m_dialog;
    }

    @TargetApi(19)
    protected void onCreate(Bundle $r1) throws  {
        this.m_wlPresentation.doOnCreate($r1);
        if (VERSION.SDK_INT >= 19) {
            getWindow().setType(DisplayStrings.DS_RIDE_REQ_ACCEPT_RT_UNREGISTERED);
            getWindow().addFlags(268435456);
        }
    }

    public Window getWindow() throws  {
        return this.m_wlPresentation.getWindow();
    }

    protected void onStart() throws  {
        this.m_wlPresentation.doOnStart();
        s_presentations.add(this);
        this.m_timer = new Timer();
        this.m_timer.schedule(new C03611(), 500, 200);
    }

    protected void onStop() throws  {
        this.m_wlPresentation.doOnStop();
        s_presentations.remove(this);
        if (this.m_timer != null) {
            this.m_timer.cancel();
            this.m_timer = null;
        }
    }

    public void onBackPressed() throws  {
        Log.v("WLPresentation", "WLPresentation has exited!");
        WEBLINK.instance.activateHomeApp();
    }

    public void show() throws  {
        this.m_wlPresentation.show();
    }

    public WLDisplay getDisplay() throws  {
        return this.m_display;
    }

    public void setContentView(View $r1) throws  {
        this.m_wlPresentation.setContentView($r1);
    }

    public void setContentView(int $i0) throws  {
        this.m_wlPresentation.setContentView($i0);
    }

    public void setContentView(View $r1, LayoutParams $r2) throws  {
        this.m_wlPresentation.setContentView($r1, $r2);
    }

    public View findViewById(int $i0) throws  {
        return this.m_wlPresentation.findViewById($i0);
    }

    public LayoutInflater getLayoutInflater() throws  {
        return this.m_wlPresentation.getLayoutInflater();
    }

    public final Context getContext() throws  {
        return this.m_wlPresentation.getContext();
    }

    public void onDisplayRemoved() throws  {
        this.m_wlPresentation.doOnDisplayRemoved();
    }

    public ActionBar getActionBar() throws  {
        return this.m_wlPresentation.getActionBar();
    }
}
