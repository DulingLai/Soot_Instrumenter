package com.abaltatech.mcp.weblink.utils.grafika;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public final class EglCore {
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final int FLAG_RECORDABLE = 1;
    private static final String TAG = "Grafika";
    private EGLConfig mEGLConfig;
    private EGLContext mEGLContext;
    private EGLDisplay mEGLDisplay;
    private EGL10 mEgl;
    private int mGlVersion;

    public EglCore() throws  {
        this(null, 0);
    }

    public EglCore(EGLContext $r2, int $i0) throws  {
        EglCore eglCore = this;
        this.mEgl = (EGL10) EGLContext.getEGL();
        this.mEGLDisplay = EGL10.EGL_NO_DISPLAY;
        this.mEGLContext = EGL10.EGL_NO_CONTEXT;
        this.mEGLConfig = null;
        this.mGlVersion = -1;
        if (this.mEGLDisplay != EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        if ($r2 == null) {
            $r2 = EGL10.EGL_NO_CONTEXT;
        }
        this.mEGLDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.mEGLDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL10 display");
        }
        if (this.mEgl.eglInitialize(this.mEGLDisplay, new int[2])) {
            if (this.mEGLContext == EGL10.EGL_NO_CONTEXT) {
                Log.d("Grafika", "Trying GLES 2");
                EGLConfig $r11 = getConfig($i0, 2);
                if ($r11 == null) {
                    throw new RuntimeException("Unable to find a suitable EGLConfig");
                }
                $r2 = this.mEgl.eglCreateContext(this.mEGLDisplay, $r11, $r2, new int[]{EGL_CONTEXT_CLIENT_VERSION, 2, 12344});
                checkEglError("eglCreateContext");
                this.mEGLConfig = $r11;
                this.mEGLContext = $r2;
                this.mGlVersion = 2;
            }
            int[] $r1 = new int[1];
            this.mEgl.eglQueryContext(this.mEGLDisplay, this.mEGLContext, EGL_CONTEXT_CLIENT_VERSION, $r1);
            Log.d("Grafika", "EGLContext created, client version " + $r1[0]);
            return;
        }
        this.mEGLDisplay = null;
        throw new RuntimeException("unable to initialize EGL10");
    }

    private EGLConfig getConfig(int $i0, int $i1) throws  {
        int[] $r1 = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344, 0, 12344};
        if (($i0 & 1) != 0) {
            $r1[$r1.length - 3] = EGL_RECORDABLE_ANDROID;
            $r1[$r1.length - 2] = 1;
        }
        EGLConfig[] $r2 = new EGLConfig[1];
        int[] $r3 = new int[1];
        if (this.mEgl.eglChooseConfig(this.mEGLDisplay, $r1, $r2, $r2.length, $r3)) {
            return $r2[0];
        }
        Log.w("Grafika", "unable to find RGB8888 / " + $i1 + " EGLConfig");
        return null;
    }

    public void release() throws  {
        if (this.mEGLDisplay != EGL10.EGL_NO_DISPLAY) {
            this.mEgl.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.mEgl.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
            this.mEgl.eglTerminate(this.mEGLDisplay);
        }
        this.mEGLDisplay = EGL10.EGL_NO_DISPLAY;
        this.mEGLContext = EGL10.EGL_NO_CONTEXT;
        this.mEGLConfig = null;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mEGLDisplay != EGL10.EGL_NO_DISPLAY) {
                Log.w("Grafika", "WARNING: EglCore was not explicitly released -- state may be leaked");
                release();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public void releaseSurface(EGLSurface $r1) throws  {
        this.mEgl.eglDestroySurface(this.mEGLDisplay, $r1);
    }

    public EGLSurface createWindowSurface(Object $r1) throws  {
        if (($r1 instanceof Surface) || ($r1 instanceof SurfaceTexture)) {
            EGLSurface $r9 = this.mEgl.eglCreateWindowSurface(this.mEGLDisplay, this.mEGLConfig, $r1, new int[]{12344});
            checkEglError("eglCreateWindowSurface");
            if ($r9 != null) {
                return $r9;
            }
            throw new RuntimeException("surface was null");
        }
        throw new RuntimeException("invalid surface: " + $r1);
    }

    public EGLSurface createOffscreenSurface(int $i0, int $i1) throws  {
        EGLSurface $r3 = this.mEgl.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, new int[]{12375, $i0, 12374, $i1, 12344});
        checkEglError("eglCreatePbufferSurface");
        if ($r3 != null) {
            return $r3;
        }
        throw new RuntimeException("surface was null");
    }

    public synchronized void makeCurrent(EGLSurface $r1) throws  {
        if (this.mEGLDisplay == EGL10.EGL_NO_DISPLAY) {
            Log.d("Grafika", "NOTE: makeCurrent w/o display");
        }
        if (!this.mEgl.eglMakeCurrent(this.mEGLDisplay, $r1, $r1, this.mEGLContext)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void makeCurrent(EGLSurface $r1, EGLSurface $r2) throws  {
        if (this.mEGLDisplay == EGL10.EGL_NO_DISPLAY) {
            Log.d("Grafika", "NOTE: makeCurrent w/o display");
        }
        if (!this.mEgl.eglMakeCurrent(this.mEGLDisplay, $r1, $r2, this.mEGLContext)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public synchronized void makeNothingCurrent() throws  {
        if (!this.mEgl.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public boolean swapBuffers(EGLSurface $r1) throws  {
        return this.mEgl.eglSwapBuffers(this.mEGLDisplay, $r1);
    }

    public boolean isCurrent(EGLSurface $r1) throws  {
        return this.mEGLContext.equals(this.mEgl.eglGetCurrentContext()) && $r1.equals(this.mEgl.eglGetCurrentSurface(12377));
    }

    public int querySurface(EGLSurface $r1, int $i0) throws  {
        int[] $r2 = new int[1];
        this.mEgl.eglQuerySurface(this.mEGLDisplay, $r1, $i0, $r2);
        return $r2[0];
    }

    public String queryString(int $i0) throws  {
        return this.mEgl.eglQueryString(this.mEGLDisplay, $i0);
    }

    public int getGlVersion() throws  {
        return this.mGlVersion;
    }

    private void checkEglError(String $r1) throws  {
        int $i0 = this.mEgl.eglGetError();
        if ($i0 != 12288) {
            throw new RuntimeException($r1 + ": EGL error: 0x" + Integer.toHexString($i0));
        }
    }
}
