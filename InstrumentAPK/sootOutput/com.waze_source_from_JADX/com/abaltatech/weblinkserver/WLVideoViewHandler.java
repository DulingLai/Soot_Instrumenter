package com.abaltatech.weblinkserver;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.VideoView;
import com.abaltatech.mcs.logger.MCSLogger;
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
            SurfaceHolder $r4 = WLVideoViewHandler.this.m_view.getHolder();
            Surface $r5 = $r4.getSurface();
            if ($r5 != null && $r5.isValid()) {
                this.m_width = DisplayStrings.DS_EVENT;
                this.m_height = DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO;
                this.m_format = 4;
                try {
                    Field $r7 = VideoView.class.getDeclaredField("mVideoWidth");
                    Field $r8 = VideoView.class.getDeclaredField("mVideoHeight");
                    Field $r9 = SurfaceView.class.getDeclaredField("mRequestedFormat");
                    $r7.setAccessible(true);
                    $r8.setAccessible(true);
                    $r9.setAccessible(true);
                    this.m_width = $r7.getInt(WLVideoViewHandler.this.m_view);
                    this.m_height = $r8.getInt(WLVideoViewHandler.this.m_view);
                    this.m_format = $r9.getInt(WLVideoViewHandler.this.m_view);
                } catch (Throwable $r1) {
                    MCSLogger.log(WLVideoViewHandler.TAG, "init()", $r1);
                }
                this.m_surfaceCreated = true;
                WLVideoViewHandler.this.mSHCallback.surfaceDestroyed($r4);
                WLVideoViewHandler.this.mSHCallback.surfaceCreated($r4);
                surfaceChanged($r4, this.m_format, this.m_width, this.m_height);
            }
        }

        public void terminate() throws  {
            if (this.m_surfaceCreated) {
                SurfaceHolder $r3 = WLVideoViewHandler.this.m_view.getHolder();
                WLVideoViewHandler.this.mSHCallback.surfaceDestroyed(this);
                WLVideoViewHandler.this.mSHCallback.surfaceCreated($r3);
                WLVideoViewHandler.this.mSHCallback.surfaceChanged($r3, this.m_format, this.m_width, this.m_height);
                this.m_surfaceCreated = false;
                this.m_width = 0;
                this.m_height = 0;
                this.m_format = 0;
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int $i0, int $i1, int $i2) throws  {
            MCSLogger.log(WLVideoViewHandler.TAG, "surfaceChanged");
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
            MCSLogger.log(WLVideoViewHandler.TAG, "surfaceCreated");
        }

        public void surfaceDestroyed(SurfaceHolder holder) throws  {
            MCSLogger.log(WLVideoViewHandler.TAG, "surfaceDestroyed");
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
            MCSLogger.log(WLVideoViewHandler.TAG, "setFixedSize");
        }

        public void setSizeFromLayout() throws  {
            MCSLogger.log(WLVideoViewHandler.TAG, "setSizeFromLayout");
        }

        public void setFormat(int format) throws  {
            MCSLogger.log(WLVideoViewHandler.TAG, "setFormat");
        }

        @Deprecated
        public void setType(@Deprecated int type) throws  {
        }

        public void setKeepScreenOn(boolean screenOn) throws  {
            MCSLogger.log(WLVideoViewHandler.TAG, "setKeepScreenOn");
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

        void drawToCanvas(Canvas $r1, float $f0, float $f1) throws  {
            if (this.m_surface != null) {
                int $i2 = (int) ((((float) WLVideoViewHandler.this.m_view.getWidth()) / $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                int $i3 = (int) ((((float) WLVideoViewHandler.this.m_view.getHeight()) / $f1) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                WLVideoViewHandler.this.m_view.getLocationOnScreen(WLVideoViewHandler.this.m_position);
                float $f02 = (((float) WLVideoViewHandler.this.m_position[0]) / $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                $f0 = $f02;
                int $i0 = (int) $f02;
                $f02 = (float) WLVideoViewHandler.this.m_position[1];
                $f02 = ($f02 / $f1) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                $f0 = $f02;
                int $i1 = (int) $f02;
                $r1.save();
                $r1.setMatrix(null);
                this.m_surface.drawToCanvas($r1, new RectF((float) $i0, (float) $i1, (float) ($i0 + $i2), (float) ($i1 + $i3)));
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
            MCSLogger.log(TAG, "Interception error", $r2);
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

    public synchronized void draw(Canvas $r1, float $f0, float $f1, float viewHierarchyScaleX, float viewHierarchyScaleY) throws  {
        if (this.m_holder != null) {
            this.m_holder.drawToCanvas($r1, $f0, $f1);
        }
    }
}
