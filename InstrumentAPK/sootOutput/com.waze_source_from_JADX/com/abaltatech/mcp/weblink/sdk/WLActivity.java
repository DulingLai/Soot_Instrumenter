package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import java.util.Timer;
import java.util.TimerTask;

public abstract class WLActivity extends Activity {
    private static final String TAG = "WLActivity";
    private static final float XTRANSLATE_LOWER_BOUND = -40000.0f;
    private static final float XTRANSLATE_UPPER_BOUND = -400.0f;
    private static final float YTRANSLATE_LOWER_BOUND = -40000.0f;
    private static final float YTRANSLATE_UPPER_BOUND = -400.0f;
    private static final Handler m_handler = new Handler();
    private static int m_repositionErrorCounter = 0;
    private static int offscreenThemeID;
    private Timer m_timer;

    class C03321 extends TimerTask {
        C03321() throws  {
        }

        public void run() throws  {
            WLActivity.this.getWindow().getDecorView().postInvalidate();
        }
    }

    static class C03332 implements Runnable {
        C03332() throws  {
        }

        public void run() throws  {
            WLTreeViewObserver.getInstance().signalImmediateUpdate();
        }
    }

    static class C03354 implements Runnable {
        C03354() throws  {
        }

        public void run() throws  {
            WLTreeViewObserver.getInstance().signalImmediateUpdate();
        }
    }

    static class C03376 implements Runnable {
        C03376() throws  {
        }

        public void run() throws  {
            WLTreeViewObserver.getInstance().signalImmediateUpdate();
        }
    }

    protected abstract int getTheOffscreenThemeID() throws ;

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setOffscreenThemeID(getTheOffscreenThemeID());
        processOnCreate(this);
    }

    @SuppressLint({"RtlHardcoded"})
    public void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        processOnAttachedToWindow(this);
    }

    public void onDetachedFromWindow() throws  {
        processOnDetachedFromWindow(this);
        super.onDetachedFromWindow();
    }

    protected void onStart() throws  {
        super.onStart();
    }

    protected void onResume() throws  {
        super.onResume();
        processOnResume(this);
        this.m_timer = new Timer();
        this.m_timer.schedule(new C03321(), 500, 200);
    }

    protected void onPause() throws  {
        processOnPause(this);
        if (this.m_timer != null) {
            this.m_timer.cancel();
            this.m_timer = null;
        }
        super.onPause();
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        return processTouchEvent($r1, this) ? true : super.dispatchTouchEvent($r1);
    }

    public static int getOffscreenThemeID() throws  {
        return offscreenThemeID;
    }

    public static void setOffscreenThemeID(int $i0) throws  {
        offscreenThemeID = $i0;
    }

    public static void processOnCreate(Activity $r0) throws  {
        if (WEBLINK.instance.getUiMode() == 3) {
            $r0.setTheme(getOffscreenThemeID());
            WLMirrorMode.instance.initOffscreenModeResourceMetrics($r0);
        } else if (WEBLINK.instance.getUiMode() == 1) {
            WLMirrorMode.instance.initMirrorModeResourceMetrics($r0);
        }
        $r0.getWindow().requestFeature(1);
    }

    public static void processOnAttachedToWindow(Activity $r0) throws  {
        View $r2 = $r0.getWindow().getDecorView();
        if (WEBLINK.instance.getUiMode() == 3) {
            repositionWindow($r0, -1, -1, 4000, 4000);
            WLMirrorMode.instance.addOffScreenView($r2);
        }
        WLMirrorMode.instance.onAttachedToWindow($r2);
        m_handler.post(new C03332());
    }

    @SuppressLint({"RtlHardcoded"})
    private static void repositionWindow(Activity $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        if (WEBLINK.instance.getUiMode() == 3) {
            View $r6 = $r0.getWindow().getDecorView();
            int $i4 = WLMirrorMode.instance.getWidth();
            int $i5 = WLMirrorMode.instance.getHeight();
            LayoutParams $r9 = (LayoutParams) $r6.getLayoutParams();
            if ($r9 != null) {
                ViewGroup.LayoutParams layoutParams = new LayoutParams();
                layoutParams.copyFrom($r9);
                layoutParams.gravity = 51;
                int $i6 = layoutParams.flags;
                $i6 |= 512;
                int $i62 = $i6;
                layoutParams.flags = $i6;
                layoutParams.x = $i2;
                layoutParams.y = $i3;
                if ($i0 > 0) {
                    $i4 = $i0;
                }
                layoutParams.width = $i4;
                if ($i1 > 0) {
                    $i5 = $i1;
                }
                layoutParams.height = $i5;
                try {
                    $r0.getWindowManager().updateViewLayout($r6, layoutParams);
                    return;
                } catch (Exception e) {
                    if (m_repositionErrorCounter < 3) {
                        Log.w(TAG, "Error repositioning window in Off-screen mode");
                        m_repositionErrorCounter++;
                        return;
                    }
                    return;
                }
            }
            final Activity activity = $r0;
            final int i = $i0;
            final int i2 = $i1;
            final int i3 = $i2;
            final int i4 = $i3;
            m_handler.postDelayed(new Runnable() {
                public void run() throws  {
                    WLActivity.repositionWindow(activity, i, i2, i3, i4);
                }
            }, 100);
        }
    }

    public static void processOnDetachedFromWindow(Activity $r0) throws  {
        View $r3 = $r0.getWindow().getDecorView();
        WLMirrorMode.instance.removeOffScreenView($r3);
        WLMirrorMode.instance.onDetachedFromWindow($r3);
        m_handler.post(new C03354());
    }

    public static void processOnResume(final Activity $r0) throws  {
        if (WEBLINK.instance.getUiMode() == 3) {
            m_handler.post(new Runnable() {
                public void run() throws  {
                    WLActivity.repositionWindow($r0, -1, -1, 4000, 4000);
                    WLTreeViewObserver.getInstance().signalImmediateUpdate();
                }
            });
        } else {
            m_handler.post(new C03376());
        }
    }

    public static void processOnPause(Activity targetActivity) throws  {
    }

    public static boolean processTouchEvent(MotionEvent $r0, Activity targetActivity) throws  {
        float $f0 = $r0.getX();
        float $f1 = $r0.getY();
        if (($f0 >= -400.0f && $f1 >= -400.0f) || $f0 <= -40000.0f || $f1 <= -40000.0f) {
            return false;
        }
        WLMirrorMode.instance.dipatchTouchEventOnScreenViews($r0);
        return true;
    }
}
