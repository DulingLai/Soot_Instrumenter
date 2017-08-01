package com.abaltatech.mcp.weblink.sdk;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.MediaController;
import android.widget.VideoView;
import com.abaltatech.mcp.weblink.utils.WLSurface;
import com.waze.FriendsBarFragment;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import java.lang.reflect.Field;

class WLVideoViewHandler implements IViewHandler {
    private static final String TAG = "WLVideoViewHandler";
    private Callback mSHCallback;
    private VideoSurfaceHolder m_holder;
    private final int[] m_position = new int[2];
    private VideoView m_view;

    private class VideoSurfaceHolder implements OnFrameAvailableListener, Callback, SurfaceHolder {
        int m_format;
        int m_height;
        WLSurface m_surface;
        boolean m_surfaceCreated;
        int m_width;

        class C03651 implements OnLayoutChangeListener {
            C03651() throws  {
            }

            public void onLayoutChange(View $r1, int $i0, int top, int $i2, int bottom, int $i4, int oldTop, int $i6, int oldBottom) throws  {
                if ($i2 - $i0 == WLVideoViewHandler.this.m_view.getWidth()) {
                    WindowManager $r8 = (WindowManager) $r1.getContext().getSystemService("window");
                    float $f0 = WLMirrorMode.instance.getOverrideScaleFactor($r1.getContext());
                    $r1.setScaleX($f0);
                    $r1.setScaleY($f0);
                    LayoutParams $r11 = (LayoutParams) $r1.getLayoutParams();
                    ViewGroup.LayoutParams layoutParams = new LayoutParams();
                    layoutParams.copyFrom($r11);
                    int $i02 = ((double) WLVideoViewHandler.this.m_view.getWidth()) * (FriendsBarFragment.END_LOCATION_POSITION / ((double) $f0));
                    long j = $i02;
                    $i02 = (int) $i02;
                    $i0 = $i02;
                    layoutParams.width = $i02;
                    $i02 = layoutParams.flags;
                    $i02 |= 512;
                    $i0 = $i02;
                    layoutParams.flags = $i02;
                    $r1.measure(layoutParams.width, layoutParams.height);
                    $r1.setPivotX(0.0f);
                    $r1.setPivotY((float) $r1.getMeasuredHeight());
                    $r8.updateViewLayout($r1, layoutParams);
                    WLMirrorMode.instance.registerLayoutParamView($r1);
                    WLMirrorMode.instance.registerSkipDrawbleView($r1);
                } else if ($i2 - $i0 == $i6 - $i4) {
                    WLMirrorMode.instance.unregisterSkipDrawbleView($r1);
                }
            }
        }

        public boolean isCreating() throws  {
            return false;
        }

        public Canvas lockCanvas() throws  {
            return null;
        }

        public Canvas lockCanvas(Rect inOutDirty) throws  {
            return null;
        }

        private VideoSurfaceHolder() throws  {
        }

        public void init() throws  {
            SurfaceHolder $r3 = WLVideoViewHandler.this.m_view.getHolder();
            Surface $r4 = $r3.getSurface();
            if ($r4 != null && $r4.isValid()) {
                this.m_width = DisplayStrings.DS_EVENT;
                this.m_height = DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO;
                this.m_format = 4;
                try {
                    Field $r6 = VideoView.class.getDeclaredField("mVideoWidth");
                    Field $r7 = VideoView.class.getDeclaredField("mVideoHeight");
                    Field $r8 = SurfaceView.class.getDeclaredField("mRequestedFormat");
                    $r6.setAccessible(true);
                    $r7.setAccessible(true);
                    $r8.setAccessible(true);
                    this.m_width = $r6.getInt(WLVideoViewHandler.this.m_view);
                    this.m_height = $r7.getInt(WLVideoViewHandler.this.m_view);
                    this.m_format = $r8.getInt(WLVideoViewHandler.this.m_view);
                } catch (Throwable $r16) {
                    Log.e(WLVideoViewHandler.TAG, "init()", $r16);
                }
                this.m_surfaceCreated = true;
                WLVideoViewHandler.this.mSHCallback.surfaceDestroyed($r3);
                WLVideoViewHandler.this.mSHCallback.surfaceCreated($r3);
                surfaceChanged($r3, this.m_format, this.m_width, this.m_height);
            }
            if (WEBLINK.instance.getUiMode() == 3) {
                try {
                    $r6 = VideoView.class.getDeclaredField("mMediaController");
                    $r6.setAccessible(true);
                    $r7 = MediaController.class.getDeclaredField("mDecor");
                    $r7.setAccessible(true);
                    View $r13 = (View) $r7.get((MediaController) $r6.get(WLVideoViewHandler.this.m_view));
                    WLMirrorMode.instance.registerSkipDrawbleView($r13);
                    $r13.addOnLayoutChangeListener(new C03651());
                } catch (Throwable $r17) {
                    Log.e(WLVideoViewHandler.TAG, "init()", $r17);
                }
            }
        }

        public void terminate() throws  {
            if (this.m_surfaceCreated) {
                SurfaceHolder $r4 = WLVideoViewHandler.this.m_view.getHolder();
                WLVideoViewHandler.this.mSHCallback.surfaceDestroyed(this);
                WLVideoViewHandler.this.mSHCallback.surfaceCreated($r4);
                WLVideoViewHandler.this.mSHCallback.surfaceChanged($r4, this.m_format, this.m_width, this.m_height);
                this.m_surfaceCreated = false;
                this.m_width = 0;
                this.m_height = 0;
                this.m_format = 0;
            }
            if (WEBLINK.instance.getUiMode() == 3) {
                try {
                    Field $r8 = VideoView.class.getDeclaredField("mMediaController");
                    $r8.setAccessible(true);
                    Field $r9 = MediaController.class.getDeclaredField("mDecor");
                    $r9.setAccessible(true);
                    MediaController $r11 = (MediaController) $r8.get(WLVideoViewHandler.this.m_view);
                    if ($r11 != null) {
                        View $r12 = (View) $r9.get($r11);
                        WLMirrorMode.instance.unregisterSkipDrawbleView($r12);
                        WLMirrorMode.instance.unregisterLayoutParamView($r12);
                    }
                } catch (Throwable $r1) {
                    Log.e(WLVideoViewHandler.TAG, "init()", $r1);
                }
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int $i0, int $i1, int $i2) throws  {
            Log.v(WLVideoViewHandler.TAG, "surfaceChanged");
            destroySurface();
            WLSurface $r2 = new WLSurface();
            if ($r2.createSurface($i1, $i2)) {
                this.m_surfaceCreated = true;
                this.m_width = $i1;
                this.m_height = $i2;
                this.m_format = $i0;
                this.m_surface = $r2;
                this.m_surface.setOnFrameAvailableListener(this);
                WLVideoViewHandler.this.mSHCallback.surfaceCreated(this);
                WLVideoViewHandler.this.mSHCallback.surfaceChanged(this, 2, $i1, $i2);
            }
        }

        public void surfaceCreated(SurfaceHolder holder) throws  {
            Log.v(WLVideoViewHandler.TAG, "surfaceCreated");
        }

        public void surfaceDestroyed(SurfaceHolder holder) throws  {
            Log.v(WLVideoViewHandler.TAG, "surfaceDestroyed");
            this.m_surfaceCreated = false;
            this.m_width = 0;
            this.m_height = 0;
            this.m_format = 0;
            destroySurface();
            WLVideoViewHandler.this.mSHCallback.surfaceDestroyed(this);
        }

        public void addCallback(Callback callback) throws  {
        }

        public void removeCallback(Callback callback) throws  {
        }

        public void setFixedSize(int width, int height) throws  {
            Log.v(WLVideoViewHandler.TAG, "setFixedSize");
        }

        public void setSizeFromLayout() throws  {
            Log.v(WLVideoViewHandler.TAG, "setSizeFromLayout");
        }

        public void setFormat(int format) throws  {
            Log.v(WLVideoViewHandler.TAG, "setFormat");
        }

        @Deprecated
        public void setType(@Deprecated int type) throws  {
        }

        public void setKeepScreenOn(boolean screenOn) throws  {
            Log.v(WLVideoViewHandler.TAG, "setKeepScreenOn");
        }

        public void unlockCanvasAndPost(Canvas canvas) throws  {
        }

        public Surface getSurface() throws  {
            return this.m_surface != null ? this.m_surface.getSurface() : null;
        }

        public Rect getSurfaceFrame() throws  {
            return new Rect(0, 0, 100, 100);
        }

        public synchronized void onFrameAvailable(SurfaceTexture st) throws  {
            if (!(this.m_surface == null || WLVideoViewHandler.this.m_view == null)) {
                Canvas $r6 = WLVideoViewHandler.this.m_view.getHolder().lockCanvas();
                this.m_surface.drawToCanvas($r6, 0.0f, 0.0f);
                WLVideoViewHandler.this.m_view.getHolder().unlockCanvasAndPost($r6);
            }
        }

        void drawToCanvas(Canvas $r1, float $f0, float $f1, int $i0, int $i1) throws  {
            if (this.m_surface != null) {
                int $i2 = (int) ((((float) WLVideoViewHandler.this.m_view.getWidth()) / $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                int $i3 = (int) ((((float) WLVideoViewHandler.this.m_view.getHeight()) / $f1) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                WLVideoViewHandler.this.m_view.getLocationOnScreen(WLVideoViewHandler.this.m_position);
                int $i02 = (((float) (WLVideoViewHandler.this.m_position[0] + $i0)) / $f0) + 1056964608;
                int i = $i02;
                $i0 = (int) $i02;
                float $f02 = (float) (WLVideoViewHandler.this.m_position[1] + $i1);
                $i02 = ($f02 / $f1) + 1056964608;
                i = $i02;
                $i1 = (int) $i02;
                $r1.save();
                $r1.setMatrix(null);
                $i02 = $i0 + $i2;
                $i0 = $i02;
                this.m_surface.drawToCanvas($r1, new RectF((float) $i0, (float) $i1, (float) $i02, (float) ($i1 + $i3)));
                $r1.restore();
            }
        }

        private void destroySurface() throws  {
            if (this.m_surface != null) {
                this.m_surface.setOnFrameAvailableListener(null);
                this.m_surface.destroySurface();
                this.m_surface = null;
            }
        }
    }

    public WLVideoViewHandler(VideoView $r1) throws  {
        this.m_view = $r1;
        try {
            SurfaceHolder $r4 = $r1.getHolder();
            Field $r6 = VideoView.class.getDeclaredField("mSHCallback");
            $r6.setAccessible(true);
            this.mSHCallback = (Callback) $r6.get($r1);
            this.m_holder = new VideoSurfaceHolder();
            $r4.removeCallback(this.mSHCallback);
            $r4.addCallback(this.m_holder);
            this.m_holder.init();
        } catch (Exception $r2) {
            Log.d(TAG, "Interception error", $r2);
        }
    }

    public void attach() throws  {
    }

    public void detach() throws  {
    }

    public synchronized void destroy() throws  {
        if (this.m_view != null) {
            SurfaceHolder $r1 = this.m_view.getHolder();
            $r1.removeCallback(this.m_holder);
            $r1.addCallback(this.mSHCallback);
            this.m_holder.terminate();
            this.m_view = null;
            this.m_holder = null;
            this.mSHCallback = null;
        }
    }

    public synchronized void draw(Canvas $r1, float $f0, float $f1, int $i0, int $i1, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
        if (this.m_holder != null) {
            this.m_holder.drawToCanvas($r1, $f0, $f1, $i0, $i1);
        }
    }
}
