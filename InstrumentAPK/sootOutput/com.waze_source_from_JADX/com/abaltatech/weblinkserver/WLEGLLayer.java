package com.abaltatech.weblinkserver;

import android.opengl.GLSurfaceView.Renderer;
import com.abaltatech.mcs.logger.MCSLogger;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public class WLEGLLayer implements WLClientConnectionHandler, WLLayer {
    private int m_eglVersion = 1;
    private ByteBuffer m_frameBuffer;
    private PixelBuffer m_pixelBuffer;
    private WLRect m_rect;
    private Renderer m_renderer;

    private class PixelBuffer {
        static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        static final boolean LIST_CONFIGS = false;
        static final String TAG = "PixelBuffer";
        EGL10 mEGL = ((EGL10) EGLContext.getEGL());
        EGLConfig mEGLConfig;
        EGLConfig[] mEGLConfigs;
        EGLContext mEGLContext;
        EGLDisplay mEGLDisplay = this.mEGL.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        EGLSurface mEGLSurface;
        GL10 mGL;
        String mThreadOwner;
        int m_height;
        Renderer m_renderer;
        int m_width;

        public PixelBuffer(int $i0, int $i1, int $i2) throws  {
            PixelBuffer pixelBuffer = this;
            this.m_width = $i0;
            this.m_height = $i1;
            int[] $r4 = new int[]{1, 1};
            int[] $r3 = new int[]{12375, this.m_width, 12374, this.m_height, 12344};
            int[] $r2 = new int[]{EGL_CONTEXT_CLIENT_VERSION, $i2, 12344};
            this.mEGL.eglInitialize(this.mEGLDisplay, $r4);
            this.mEGLConfig = chooseConfig();
            this.mEGLContext = this.mEGL.eglCreateContext(this.mEGLDisplay, this.mEGLConfig, EGL10.EGL_NO_CONTEXT, $r2);
            this.mEGLSurface = this.mEGL.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, $r3);
            this.mEGL.eglMakeCurrent(this.mEGLDisplay, this.mEGLSurface, this.mEGLSurface, this.mEGLContext);
            this.mGL = (GL10) this.mEGLContext.getGL();
            this.mThreadOwner = Thread.currentThread().getName();
        }

        public void setRenderer(Renderer $r1) throws  {
            this.m_renderer = $r1;
            if (Thread.currentThread().getName().equals(this.mThreadOwner)) {
                this.m_renderer.onSurfaceCreated(this.mGL, this.mEGLConfig);
                this.m_renderer.onSurfaceChanged(this.mGL, this.m_width, this.m_height);
                return;
            }
            MCSLogger.log(TAG, "setRenderer: This thread does not own the OpenGL context.");
        }

        public boolean renderToBuffer(ByteBuffer $r1, WLRect $r2) throws  {
            if (this.m_renderer == null) {
                MCSLogger.log(TAG, "renderToBuffer: Renderer was not set.");
                return false;
            } else if (Thread.currentThread().getName().equals(this.mThreadOwner)) {
                $r2 = new WLRect(0, 0, this.m_width, this.m_height).intersected($r2);
                if ($r2.isEmpty()) {
                    MCSLogger.log(TAG, "renderToBuffer: The rect to be copied is empty.");
                    return false;
                }
                if ($r1 != null) {
                    if ($r1.capacity() >= ($r2.width() * $r2.height()) * 4) {
                        Renderer $r3 = this.m_renderer;
                        GL10 $r8 = this.mGL;
                        $r3.onDrawFrame($r8);
                        $r1.position(0);
                        this.mGL.glReadPixels($r2.left(), $r2.top(), $r2.width(), $r2.height(), 6408, 5121, $r1);
                        return true;
                    }
                }
                MCSLogger.log(TAG, "renderToBuffer: The supplied bufferis not large enough.");
                return false;
            } else {
                MCSLogger.log(TAG, "renderToBuffer: This thread does not own the OpenGL context.");
                return false;
            }
        }

        private EGLConfig chooseConfig() throws  {
            int[] $r2 = new int[]{12325, 0, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344};
            int[] $r3 = new int[1];
            this.mEGL.eglChooseConfig(this.mEGLDisplay, $r2, null, 0, $r3);
            int $i0 = $r3[0];
            this.mEGLConfigs = new EGLConfig[$i0];
            this.mEGL.eglChooseConfig(this.mEGLDisplay, $r2, this.mEGLConfigs, $i0, $r3);
            return this.mEGLConfigs[0];
        }

        private void listConfig() throws  {
            MCSLogger.log(TAG, "Config List {");
            for (EGLConfig $r1 : this.mEGLConfigs) {
                MCSLogger.log(TAG, "    <d,s,r,g,b,a> = <" + getConfigAttrib($r1, 12325) + "," + getConfigAttrib($r1, 12326) + "," + getConfigAttrib($r1, 12324) + "," + getConfigAttrib($r1, 12323) + "," + getConfigAttrib($r1, 12322) + "," + getConfigAttrib($r1, 12321) + ">");
            }
            MCSLogger.log(TAG, "}");
        }

        private int getConfigAttrib(EGLConfig $r1, int $i0) throws  {
            int[] $r2 = new int[1];
            return this.mEGL.eglGetConfigAttrib(this.mEGLDisplay, $r1, $i0, $r2) ? $r2[0] : 0;
        }
    }

    public WLEGLLayer(int $i0) throws  {
        this.m_eglVersion = $i0;
    }

    public synchronized void setRenderer(Renderer $r1) throws  {
        this.m_renderer = $r1;
        if (this.m_pixelBuffer != null) {
            this.m_pixelBuffer.setRenderer($r1);
        }
    }

    public synchronized void setScreenRect(WLRect $r1) throws  {
        int $i0 = 0;
        synchronized (this) {
            int $i1 = this.m_rect == null ? 0 : this.m_rect.width();
            if (this.m_rect != null) {
                $i0 = this.m_rect.height();
            }
            this.m_rect = $r1;
            if (!($r1.width() == $i1 && $r1.height() == $i0)) {
                this.m_frameBuffer = ByteBuffer.allocateDirect(($r1.width() * $r1.height()) * 4);
                this.m_pixelBuffer = null;
            }
        }
    }

    public synchronized WLRect getScreenRect() throws  {
        return this.m_rect;
    }

    public void render(ByteBuffer $r1, int $i0, int $i1, int $i2) throws  {
        synchronized (this) {
            if (!(this.m_pixelBuffer != null || this.m_frameBuffer == null || this.m_rect == null || this.m_rect.isEmpty())) {
                this.m_pixelBuffer = new PixelBuffer(this.m_rect.width(), this.m_rect.height(), this.m_eglVersion);
                PixelBuffer $r2 = this.m_pixelBuffer;
                Renderer $r5 = this.m_renderer;
                $r2.setRenderer($r5);
            }
        }
        if (this.m_pixelBuffer == null) {
            return;
        }
        if (this.m_rect.left() == 0 && this.m_rect.top() == 0 && this.m_rect.width() == $i0 && this.m_rect.height() == $i1) {
            this.m_pixelBuffer.renderToBuffer($r1, this.m_rect);
            WLImageUtils.mirrorImage($r1, $i1, $i2);
            return;
        }
        WLRect $r4 = new WLRect(0, 0, $i0, $i1);
        WLRect $r7 = this.m_rect;
        $r4 = $r4.intersected($r7);
        if (!$r4.isEmpty()) {
            $r2 = this.m_pixelBuffer;
            ByteBuffer $r3 = this.m_frameBuffer;
            $r7 = this.m_rect;
            int $i3 = $r7.width();
            $r7 = this.m_rect;
            $r2.renderToBuffer($r3, new WLRect(0, 0, $i3, $r7.height()));
            $r3 = this.m_frameBuffer;
            $r7 = this.m_rect;
            $i3 = $r7.height();
            $r7 = this.m_rect;
            WLImageUtils.mirrorImage($r3, $i3, $r7.width() * 4);
            $r3 = this.m_frameBuffer;
            $r7 = this.m_rect;
            $i3 = $r7.left();
            $r7 = this.m_rect;
            int $i4 = $r7.top();
            $r7 = this.m_rect;
            int $i5 = $r7.width();
            $r7 = this.m_rect;
            WLImageUtils.copyImage($r1, $i0, $i1, $i2, $r3, $i3, $i4, $i5, $r7.height(), $r4.left(), $r4.top(), $r4.width(), $r4.height());
        }
    }

    public synchronized boolean canRender() throws  {
        return this.m_rect != null;
    }

    public synchronized void onClientConnected() throws  {
        this.m_pixelBuffer = null;
    }

    public synchronized void onClientDisconnected() throws  {
        this.m_pixelBuffer = null;
    }
}
