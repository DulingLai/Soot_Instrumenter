package com.waze;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLDebugHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import dalvik.annotation.Signature;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLTextureView extends TextureView implements SurfaceTextureListener {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLTextureView";
    private static final GLThreadManager sGLThreadManager = new GLThreadManager();
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private Renderer mRenderer;
    private final WeakReference<GLTextureView> mThisWeakRef = new WeakReference(this);

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) throws ;
    }

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) throws ;

        public BaseConfigChooser(int[] $r2) throws  {
            this.mConfigSpec = filterConfigSpec($r2);
        }

        public EGLConfig chooseConfig(EGL10 $r1, EGLDisplay $r2) throws  {
            int[] $r3 = new int[1];
            if ($r1.eglChooseConfig($r2, this.mConfigSpec, null, 0, $r3)) {
                int $i0 = $r3[0];
                if ($i0 <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] $r6 = new EGLConfig[$i0];
                if ($r1.eglChooseConfig($r2, this.mConfigSpec, $r6, $i0, $r3)) {
                    EGLConfig $r7 = chooseConfig($r1, $r2, $r6);
                    if ($r7 != null) {
                        return $r7;
                    }
                    throw new IllegalArgumentException("No config chosen");
                }
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        private int[] filterConfigSpec(int[] $r2) throws  {
            if (GLTextureView.this.mEGLContextClientVersion != 2 && GLTextureView.this.mEGLContextClientVersion != 3) {
                return $r2;
            }
            int $i0 = $r2.length;
            int[] $r1 = new int[($i0 + 2)];
            System.arraycopy($r2, 0, $r1, 0, $i0 - 1);
            $r1[$i0 - 1] = 12352;
            if (GLTextureView.this.mEGLContextClientVersion == 2) {
                $r1[$i0] = 4;
            } else {
                $r1[$i0] = 64;
            }
            $r1[$i0 + 1] = 12344;
            return $r1;
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        public ComponentSizeChooser(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5) throws  {
            super(new int[]{12324, $i0, 12323, $i1, 12322, $i2, 12321, $i3, 12325, $i4, 12326, $i5, 12344});
            this.mRedSize = $i0;
            this.mGreenSize = $i1;
            this.mBlueSize = $i2;
            this.mAlphaSize = $i3;
            this.mDepthSize = $i4;
            this.mStencilSize = $i5;
        }

        public EGLConfig chooseConfig(EGL10 $r1, EGLDisplay $r2, EGLConfig[] $r3) throws  {
            for (EGLConfig $r4 : $r3) {
                int $i2 = findConfigAttrib($r1, $r2, $r4, 12325, 0);
                int $i3 = findConfigAttrib($r1, $r2, $r4, 12326, 0);
                if ($i2 >= this.mDepthSize && $i3 >= this.mStencilSize) {
                    int $i5 = findConfigAttrib($r1, $r2, $r4, 12324, 0);
                    int $i4 = findConfigAttrib($r1, $r2, $r4, 12323, 0);
                    $i2 = findConfigAttrib($r1, $r2, $r4, 12322, 0);
                    $i3 = findConfigAttrib($r1, $r2, $r4, 12321, 0);
                    if ($i5 == this.mRedSize && $i4 == this.mGreenSize && $i2 == this.mBlueSize && $i3 == this.mAlphaSize) {
                        return $r4;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(EGL10 $r1, EGLDisplay $r2, EGLConfig $r3, int $i0, int $i1) throws  {
            if ($r1.eglGetConfigAttrib($r2, $r3, $i0, this.mValue)) {
                return this.mValue[0];
            }
            return $i1;
        }
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) throws ;

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) throws ;
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() throws  {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 $r1, EGLDisplay $r2, EGLConfig $r3) throws  {
            int[] $r4 = new int[]{this.EGL_CONTEXT_CLIENT_VERSION, GLTextureView.this.mEGLContextClientVersion, 12344};
            EGLContext $r6 = EGL10.EGL_NO_CONTEXT;
            if (GLTextureView.this.mEGLContextClientVersion == 0) {
                $r4 = null;
            }
            return $r1.eglCreateContext($r2, $r3, $r6, $r4);
        }

        public void destroyContext(EGL10 $r1, EGLDisplay $r2, EGLContext $r3) throws  {
            if (!$r1.eglDestroyContext($r2, $r3)) {
                Log.e("DefaultContextFactory", "display:" + $r2 + " context: " + $r3);
                EglHelper.throwEglException("eglDestroyContex", $r1.eglGetError());
            }
        }
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) throws ;

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) throws ;
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() throws  {
        }

        public EGLSurface createWindowSurface(EGL10 $r1, EGLDisplay $r2, EGLConfig $r3, Object $r4) throws  {
            try {
                return $r1.eglCreateWindowSurface($r2, $r3, $r4, null);
            } catch (IllegalArgumentException $r5) {
                Log.e(GLTextureView.TAG, "eglCreateWindowSurface", $r5);
                return null;
            }
        }

        public void destroySurface(EGL10 $r1, EGLDisplay $r2, EGLSurface $r3) throws  {
            $r1.eglDestroySurface($r2, $r3);
        }
    }

    private static class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLTextureView> mGLTextureViewWeakRef;

        public EglHelper(@Signature({"(", "Ljava/lang/ref/WeakReference", "<", "Lcom/waze/GLTextureView;", ">;)V"}) WeakReference<GLTextureView> $r1) throws  {
            this.mGLTextureViewWeakRef = $r1;
        }

        public void start() throws  {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                GLTextureView $r10 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if ($r10 == null) {
                    this.mEglConfig = null;
                    this.mEglContext = null;
                } else {
                    this.mEglConfig = $r10.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                    this.mEglContext = $r10.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
                }
                if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                    this.mEglContext = null;
                    throwEglException("createContext");
                }
                this.mEglSurface = null;
                return;
            }
            throw new RuntimeException("eglInitialize failed");
        }

        public boolean createSurface() throws  {
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                destroySurfaceImp();
                GLTextureView $r7 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if ($r7 != null) {
                    this.mEglSurface = $r7.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, $r7.getSurfaceTexture());
                } else {
                    this.mEglSurface = null;
                }
                if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                    EGL10 $r1 = this.mEgl;
                    EGLDisplay $r3 = this.mEglDisplay;
                    EGLSurface $r10 = this.mEglSurface;
                    EGLSurface $r11 = this.mEglSurface;
                    EGLContext $r12 = this.mEglContext;
                    if ($r1.eglMakeCurrent($r3, $r10, $r11, $r12)) {
                        return true;
                    }
                    logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                    return false;
                } else if (this.mEgl.eglGetError() != 12299) {
                    return false;
                } else {
                    Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    return false;
                }
            }
        }

        GL createGL() throws  {
            GL $r2 = this.mEglContext.getGL();
            GL $r3 = $r2;
            GLTextureView $r6 = (GLTextureView) this.mGLTextureViewWeakRef.get();
            if ($r6 == null) {
                return $r2;
            }
            if ($r6.mGLWrapper != null) {
                $r3 = $r6.mGLWrapper.wrap($r2);
            }
            if (($r6.mDebugFlags & 3) == 0) {
                return $r3;
            }
            byte $b1 = (byte) 0;
            LogWriter $r8 = null;
            if (($r6.mDebugFlags & 1) != 0) {
                $b1 = (byte) 0 | (byte) 1;
            }
            if (($r6.mDebugFlags & 2) != 0) {
                $r8 = new LogWriter();
            }
            return GLDebugHelper.wrap($r3, $b1, $r8);
        }

        public int swap() throws  {
            if (this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return 12288;
            }
            return this.mEgl.eglGetError();
        }

        public void destroySurface() throws  {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() throws  {
            if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLTextureView $r8 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if ($r8 != null) {
                    $r8.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public void finish() throws  {
            if (this.mEglContext != null) {
                GLTextureView $r5 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if ($r5 != null) {
                    $r5.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String $r1) throws  {
            throwEglException($r1, this.mEgl.eglGetError());
        }

        public static void throwEglException(String $r0, int $i0) throws  {
            throw new RuntimeException(formatEglError($r0, $i0));
        }

        public static void logEglErrorAsWarning(String $r0, String $r1, int $i0) throws  {
            Log.w($r0, formatEglError($r1, $i0));
        }

        public static String formatEglError(String $r0, int $i0) throws  {
            return $r0 + " failed: " + $i0;
        }
    }

    static class GLThread extends Thread {
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue = new ArrayList();
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLTextureView> mGLTextureViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight = 0;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode = 1;
        private boolean mRequestPaused;
        private boolean mRequestRender = true;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged = true;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private int mWidth = 0;

        private void guardedRun() throws java.lang.InterruptedException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:90:0x01d8
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r46 = this;
            r3 = new com.waze.GLTextureView$EglHelper;
            r0 = r46;
            r4 = r0.mGLTextureViewWeakRef;
            r3.<init>(r4);
            r0 = r46;
            r0.mEglHelper = r3;
            r5 = 0;
            r0 = r46;
            r0.mHaveEglContext = r5;
            r5 = 0;
            r0 = r46;
            r0.mHaveEglSurface = r5;
            r6 = 0;
            r7 = 0;
            r8 = 0;
            r9 = 0;
            r10 = 0;
            r11 = 0;
            r12 = 0;
            r13 = 0;
            r14 = 0;
            r15 = 0;
            r16 = 0;
            r17 = 0;
        L_0x0025:
            r18 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e5 }
            monitor-enter(r18);	 Catch:{ Throwable -> 0x01e5 }
        L_0x002a:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mShouldExit;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x0047;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0032:
            monitor-exit(r18);	 Catch:{ Throwable -> 0x01e2 }
            r18 = com.waze.GLTextureView.sGLThreadManager;
            monitor-enter(r18);
            r0 = r46;	 Catch:{ Throwable -> 0x0044 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x0044 }
            r0 = r46;	 Catch:{ Throwable -> 0x0044 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x0044 }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0044 }
            return;
        L_0x0044:
            r20 = move-exception;	 Catch:{ Throwable -> 0x0044 }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0044 }
            throw r20;
        L_0x0047:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mEventQueue;	 Catch:{ Throwable -> 0x01e2 }
            r21 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0.isEmpty();	 Catch:{ Throwable -> 0x01e2 }
            if (r19 != 0) goto L_0x0071;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0053:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mEventQueue;	 Catch:{ Throwable -> 0x01e2 }
            r21 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r5 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r21;	 Catch:{ Throwable -> 0x01e2 }
            r22 = r0.remove(r5);	 Catch:{ Throwable -> 0x01e2 }
            r23 = r22;	 Catch:{ Throwable -> 0x01e2 }
            r23 = (java.lang.Runnable) r23;	 Catch:{ Throwable -> 0x01e2 }
            r17 = r23;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0066:
            monitor-exit(r18);	 Catch:{ Throwable -> 0x01e2 }
            if (r17 == 0) goto L_0x023e;
        L_0x0069:
            r0 = r17;	 Catch:{ Throwable -> 0x01e5 }
            r0.run();	 Catch:{ Throwable -> 0x01e5 }
            r17 = 0;
            goto L_0x0025;
        L_0x0071:
            r19 = 0;
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mPaused;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mRequestPaused;	 Catch:{ Throwable -> 0x01e2 }
            r25 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r24;	 Catch:{ Throwable -> 0x01e2 }
            r1 = r25;	 Catch:{ Throwable -> 0x01e2 }
            if (r0 == r1) goto L_0x009e;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0085:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mRequestPaused;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mRequestPaused;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r1 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r1.mPaused = r0;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01e2 }
        L_0x009e:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mShouldReleaseEglContext;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r24 == 0) goto L_0x00b6;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00a6:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x01e2 }
            r5 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mShouldReleaseEglContext = r5;	 Catch:{ Throwable -> 0x01e2 }
            r14 = 1;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00b6:
            if (r10 == 0) goto L_0x00c3;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00b8:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x01e2 }
            r10 = 0;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00c3:
            if (r19 == 0) goto L_0x00d2;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00c5:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r24 == 0) goto L_0x00d2;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00cd:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01e2 }
        L_0x00d2:
            if (r19 == 0) goto L_0x0109;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00d4:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglContext;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r24 == 0) goto L_0x0109;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00dc:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r4 = r0.mGLTextureViewWeakRef;	 Catch:{ Throwable -> 0x01e2 }
            r22 = r4.get();	 Catch:{ Throwable -> 0x01e2 }
            r28 = r22;	 Catch:{ Throwable -> 0x01e2 }
            r28 = (com.waze.GLTextureView) r28;	 Catch:{ Throwable -> 0x01e2 }
            r27 = r28;	 Catch:{ Throwable -> 0x01e2 }
            if (r27 == 0) goto L_0x01fb;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00ec:
            r0 = r27;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0.mPreserveEGLContextOnPause;	 Catch:{ Throwable -> 0x01e2 }
            if (r24 == 0) goto L_0x01fb;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00f4:
            r24 = 1;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00f6:
            if (r24 == 0) goto L_0x0104;	 Catch:{ Throwable -> 0x01e2 }
        L_0x00f8:
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r24 = r0.shouldReleaseEGLContextWhenPausing();	 Catch:{ Throwable -> 0x01e2 }
            if (r24 == 0) goto L_0x0109;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0104:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x01e2 }
        L_0x0109:
            if (r19 == 0) goto L_0x011e;	 Catch:{ Throwable -> 0x01e2 }
        L_0x010b:
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0.shouldTerminateEGLWhenPausing();	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x011e;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0117:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r3 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01e2 }
            r3.finish();	 Catch:{ Throwable -> 0x01e2 }
        L_0x011e:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHasSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 != 0) goto L_0x014e;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0126:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mWaitingForSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 != 0) goto L_0x014e;	 Catch:{ Throwable -> 0x01e2 }
        L_0x012e:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x013b;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0136:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01e2 }
        L_0x013b:
            r5 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mWaitingForSurface = r5;	 Catch:{ Throwable -> 0x01e2 }
            r5 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mSurfaceIsBad = r5;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01e2 }
        L_0x014e:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHasSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x016c;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0156:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mWaitingForSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x016c;	 Catch:{ Throwable -> 0x01e2 }
        L_0x015e:
            r5 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mWaitingForSurface = r5;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01e2 }
        L_0x016c:
            if (r13 == 0) goto L_0x017e;	 Catch:{ Throwable -> 0x01e2 }
        L_0x016e:
            r12 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r13 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r5 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mRenderComplete = r5;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01e2 }
        L_0x017e:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0.readyToDraw();	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x0230;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0186:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglContext;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 != 0) goto L_0x0191;	 Catch:{ Throwable -> 0x01e2 }
        L_0x018e:
            if (r14 == 0) goto L_0x01fe;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0190:
            r14 = 0;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0191:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglContext;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x01a9;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0199:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 != 0) goto L_0x01a9;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01a1:
            r5 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mHaveEglSurface = r5;	 Catch:{ Throwable -> 0x01e2 }
            r8 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r9 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r11 = 1;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01a9:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x0230;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01b1:
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mSizeChanged;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0;	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x01cb;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01b9:
            r11 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r15 = r0.mWidth;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r0.mHeight;	 Catch:{ Throwable -> 0x01e2 }
            r16 = r0;	 Catch:{ Throwable -> 0x01e2 }
            r12 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r8 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r5 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mSizeChanged = r5;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01cb:
            r5 = 0;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mRequestRender = r5;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            goto L_0x01dc;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01d5:
            goto L_0x0066;	 Catch:{ Throwable -> 0x01e2 }
            goto L_0x01dc;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01d9:
            goto L_0x0191;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01dc:
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01e2 }
            goto L_0x01d5;	 Catch:{ Throwable -> 0x01e2 }
        L_0x01e2:
            r29 = move-exception;	 Catch:{ Throwable -> 0x01e2 }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x01e2 }
            throw r29;	 Catch:{ Throwable -> 0x01e5 }
        L_0x01e5:
            r30 = move-exception;
            r18 = com.waze.GLTextureView.sGLThreadManager;
            monitor-enter(r18);
            r0 = r46;	 Catch:{ Throwable -> 0x0331 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x0331 }
            r0 = r46;	 Catch:{ Throwable -> 0x0331 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x0331 }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0331 }
            throw r30;
            goto L_0x01fb;
        L_0x01f8:
            goto L_0x00f6;
        L_0x01fb:
            r24 = 0;
            goto L_0x01f8;
        L_0x01fe:
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r1 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r19 = r0.tryAcquireEglContextLocked(r1);	 Catch:{ Throwable -> 0x01e2 }
            if (r19 == 0) goto L_0x0191;
        L_0x020c:
            r0 = r46;	 Catch:{ RuntimeException -> 0x0223 }
            r3 = r0.mEglHelper;	 Catch:{ RuntimeException -> 0x0223 }
            r3.start();	 Catch:{ RuntimeException -> 0x0223 }
            r5 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.mHaveEglContext = r5;	 Catch:{ Throwable -> 0x01e2 }
            r7 = 1;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01e2 }
            goto L_0x01d9;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0223:
            r31 = move-exception;	 Catch:{ Throwable -> 0x01e2 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r1 = r46;	 Catch:{ Throwable -> 0x01e2 }
            r0.releaseEglContextLocked(r1);	 Catch:{ Throwable -> 0x01e2 }
            throw r31;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0230:
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e2 }
            goto L_0x0238;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0235:
            goto L_0x002a;	 Catch:{ Throwable -> 0x01e2 }
        L_0x0238:
            r0 = r26;	 Catch:{ Throwable -> 0x01e2 }
            r0.wait();	 Catch:{ Throwable -> 0x01e2 }
            goto L_0x0235;
        L_0x023e:
            if (r8 == 0) goto L_0x025f;
        L_0x0240:
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r3 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01e5 }
            r19 = r3.createSurface();	 Catch:{ Throwable -> 0x01e5 }
            if (r19 == 0) goto L_0x030b;	 Catch:{ Throwable -> 0x01e5 }
        L_0x024a:
            r18 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e5 }
            monitor-enter(r18);	 Catch:{ Throwable -> 0x01e5 }
            r5 = 1;	 Catch:{ Throwable -> 0x0308 }
            r0 = r46;	 Catch:{ Throwable -> 0x0308 }
            r0.mFinishedCreatingEglSurface = r5;	 Catch:{ Throwable -> 0x0308 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x0308 }
            r0 = r26;	 Catch:{ Throwable -> 0x0308 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x0308 }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0308 }
            r8 = 0;
        L_0x025f:
            if (r9 == 0) goto L_0x0270;
        L_0x0261:
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r3 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01e5 }
            r32 = r3.createGL();	 Catch:{ Throwable -> 0x01e5 }
            r33 = r32;	 Catch:{ Throwable -> 0x01e5 }
            r33 = (javax.microedition.khronos.opengles.GL10) r33;	 Catch:{ Throwable -> 0x01e5 }
            r6 = r33;	 Catch:{ Throwable -> 0x01e5 }
            r9 = 0;
        L_0x0270:
            if (r7 == 0) goto L_0x0298;	 Catch:{ Throwable -> 0x01e5 }
        L_0x0272:
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r4 = r0.mGLTextureViewWeakRef;	 Catch:{ Throwable -> 0x01e5 }
            r22 = r4.get();	 Catch:{ Throwable -> 0x01e5 }
            r34 = r22;	 Catch:{ Throwable -> 0x01e5 }
            r34 = (com.waze.GLTextureView) r34;	 Catch:{ Throwable -> 0x01e5 }
            r27 = r34;	 Catch:{ Throwable -> 0x01e5 }
            if (r27 == 0) goto L_0x0297;	 Catch:{ Throwable -> 0x01e5 }
        L_0x0282:
            r0 = r27;	 Catch:{ Throwable -> 0x01e5 }
            r35 = r0.mRenderer;	 Catch:{ Throwable -> 0x01e5 }
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r3 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01e5 }
            r0 = r3.mEglConfig;	 Catch:{ Throwable -> 0x01e5 }
            r36 = r0;	 Catch:{ Throwable -> 0x01e5 }
            r0 = r35;	 Catch:{ Throwable -> 0x01e5 }
            r1 = r36;	 Catch:{ Throwable -> 0x01e5 }
            r0.onSurfaceCreated(r6, r1);	 Catch:{ Throwable -> 0x01e5 }
        L_0x0297:
            r7 = 0;
        L_0x0298:
            if (r11 == 0) goto L_0x02b8;	 Catch:{ Throwable -> 0x01e5 }
        L_0x029a:
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r4 = r0.mGLTextureViewWeakRef;	 Catch:{ Throwable -> 0x01e5 }
            r22 = r4.get();	 Catch:{ Throwable -> 0x01e5 }
            r37 = r22;	 Catch:{ Throwable -> 0x01e5 }
            r37 = (com.waze.GLTextureView) r37;	 Catch:{ Throwable -> 0x01e5 }
            r27 = r37;	 Catch:{ Throwable -> 0x01e5 }
            if (r27 == 0) goto L_0x02b7;	 Catch:{ Throwable -> 0x01e5 }
        L_0x02aa:
            r0 = r27;	 Catch:{ Throwable -> 0x01e5 }
            r35 = r0.mRenderer;	 Catch:{ Throwable -> 0x01e5 }
            r0 = r35;	 Catch:{ Throwable -> 0x01e5 }
            r1 = r16;	 Catch:{ Throwable -> 0x01e5 }
            r0.onSurfaceChanged(r6, r15, r1);	 Catch:{ Throwable -> 0x01e5 }
        L_0x02b7:
            r11 = 0;	 Catch:{ Throwable -> 0x01e5 }
        L_0x02b8:
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r4 = r0.mGLTextureViewWeakRef;	 Catch:{ Throwable -> 0x01e5 }
            r22 = r4.get();	 Catch:{ Throwable -> 0x01e5 }
            r38 = r22;	 Catch:{ Throwable -> 0x01e5 }
            r38 = (com.waze.GLTextureView) r38;	 Catch:{ Throwable -> 0x01e5 }
            r27 = r38;	 Catch:{ Throwable -> 0x01e5 }
            if (r27 == 0) goto L_0x02d3;	 Catch:{ Throwable -> 0x01e5 }
        L_0x02c8:
            r0 = r27;	 Catch:{ Throwable -> 0x01e5 }
            r35 = r0.mRenderer;	 Catch:{ Throwable -> 0x01e5 }
            r0 = r35;	 Catch:{ Throwable -> 0x01e5 }
            r0.onDrawFrame(r6);	 Catch:{ Throwable -> 0x01e5 }
        L_0x02d3:
            r0 = r46;	 Catch:{ Throwable -> 0x01e5 }
            r3 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01e5 }
            r39 = r3.swap();	 Catch:{ Throwable -> 0x01e5 }
            switch(r39) {
                case 12288: goto L_0x0300;
                case 12302: goto L_0x032c;
                default: goto L_0x02de;
            };	 Catch:{ Throwable -> 0x01e5 }
        L_0x02de:
            goto L_0x02df;	 Catch:{ Throwable -> 0x01e5 }
        L_0x02df:
            r40 = "GLThread";	 Catch:{ Throwable -> 0x01e5 }
            r41 = "eglSwapBuffers";	 Catch:{ Throwable -> 0x01e5 }
            r0 = r40;	 Catch:{ Throwable -> 0x01e5 }
            r1 = r41;	 Catch:{ Throwable -> 0x01e5 }
            r2 = r39;	 Catch:{ Throwable -> 0x01e5 }
            com.waze.GLTextureView.EglHelper.logEglErrorAsWarning(r0, r1, r2);	 Catch:{ Throwable -> 0x01e5 }
            r18 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e5 }
            monitor-enter(r18);	 Catch:{ Throwable -> 0x01e5 }
            r5 = 1;	 Catch:{ Throwable -> 0x032e }
            r0 = r46;	 Catch:{ Throwable -> 0x032e }
            r0.mSurfaceIsBad = r5;	 Catch:{ Throwable -> 0x032e }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x032e }
            r0 = r26;	 Catch:{ Throwable -> 0x032e }
            r0.notifyAll();	 Catch:{ Throwable -> 0x032e }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x032e }
        L_0x0300:
            if (r12 == 0) goto L_0x0025;
        L_0x0302:
            goto L_0x0306;
        L_0x0303:
            goto L_0x0025;
        L_0x0306:
            r13 = 1;
            goto L_0x0303;
        L_0x0308:
            r42 = move-exception;
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0308 }
            throw r42;	 Catch:{ Throwable -> 0x01e5 }
        L_0x030b:
            r18 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x01e5 }
            monitor-enter(r18);	 Catch:{ Throwable -> 0x01e5 }
            r5 = 1;	 Catch:{ Throwable -> 0x0329 }
            r0 = r46;	 Catch:{ Throwable -> 0x0329 }
            r0.mFinishedCreatingEglSurface = r5;	 Catch:{ Throwable -> 0x0329 }
            r5 = 1;	 Catch:{ Throwable -> 0x0329 }
            r0 = r46;	 Catch:{ Throwable -> 0x0329 }
            r0.mSurfaceIsBad = r5;	 Catch:{ Throwable -> 0x0329 }
            r26 = com.waze.GLTextureView.sGLThreadManager;	 Catch:{ Throwable -> 0x0329 }
            r0 = r26;	 Catch:{ Throwable -> 0x0329 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x0329 }
            goto L_0x0327;	 Catch:{ Throwable -> 0x0329 }
        L_0x0324:
            goto L_0x0025;	 Catch:{ Throwable -> 0x0329 }
        L_0x0327:
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0329 }
            goto L_0x0324;
        L_0x0329:
            r43 = move-exception;	 Catch:{ Throwable -> 0x0329 }
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0329 }
            throw r43;	 Catch:{ Throwable -> 0x01e5 }
        L_0x032c:
            r10 = 1;
            goto L_0x0300;
        L_0x032e:
            r44 = move-exception;
            monitor-exit(r18);	 Catch:{ Throwable -> 0x032e }
            throw r44;	 Catch:{ Throwable -> 0x01e5 }
        L_0x0331:
            r45 = move-exception;
            monitor-exit(r18);	 Catch:{ Throwable -> 0x0331 }
            throw r45;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.GLTextureView.GLThread.guardedRun():void");
        }

        GLThread(@Signature({"(", "Ljava/lang/ref/WeakReference", "<", "Lcom/waze/GLTextureView;", ">;)V"}) WeakReference<GLTextureView> $r1) throws  {
            this.mGLTextureViewWeakRef = $r1;
        }

        public void run() throws  {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException e) {
            } finally {
                GLTextureView.sGLThreadManager.threadExiting(this);
            }
        }

        private void stopEglSurfaceLocked() throws  {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() throws  {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLTextureView.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        public boolean ableToDraw() throws  {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        private boolean readyToDraw() throws  {
            if (!this.mPaused && this.mHasSurface && !this.mSurfaceIsBad && this.mWidth > 0 && this.mHeight > 0) {
                if (this.mRequestRender) {
                    return true;
                }
                if (this.mRenderMode == 1) {
                    return true;
                }
            }
            return false;
        }

        public void setRenderMode(int $i0) throws  {
            if ($i0 < 0 || $i0 > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRenderMode = $i0;
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() throws  {
            int i0;
            synchronized (GLTextureView.sGLThreadManager) {
                i0 = this.mRenderMode;
            }
            return i0;
        }

        public void requestRender() throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRequestRender = true;
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }

        public void surfaceCreated() throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mHasSurface = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRequestPaused = true;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int $i0, int $i1) throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mWidth = $i0;
                this.mHeight = $i1;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestExitAndWait() throws  {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mShouldExit = true;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() throws  {
            this.mShouldReleaseEglContext = true;
            GLTextureView.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLTextureView.sGLThreadManager) {
                this.mEventQueue.add($r1);
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread mEglOwner;
        private boolean mLimitedGLESContexts;
        private boolean mMultipleGLESContextsAllowed;

        private GLThreadManager() throws  {
            this.mMultipleGLESContextsAllowed = false;
        }

        public synchronized void threadExiting(GLThread $r1) throws  {
            $r1.mExited = true;
            if (this.mEglOwner == $r1) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public boolean tryAcquireEglContextLocked(GLThread $r1) throws  {
            if (this.mEglOwner == $r1 || this.mEglOwner == null) {
                this.mEglOwner = $r1;
                notifyAll();
                return true;
            } else if (this.mMultipleGLESContextsAllowed) {
                return true;
            } else {
                if (this.mEglOwner != null) {
                    this.mEglOwner.requestReleaseEglContextLocked();
                }
                return false;
            }
        }

        public void releaseEglContextLocked(GLThread $r1) throws  {
            if (this.mEglOwner == $r1) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public synchronized boolean shouldReleaseEGLContextWhenPausing() throws  {
            return this.mLimitedGLESContexts;
        }

        public synchronized boolean shouldTerminateEGLWhenPausing() throws  {
            return !this.mMultipleGLESContextsAllowed;
        }
    }

    public interface GLWrapper {
        GL wrap(GL gl) throws ;
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() throws  {
        }

        public void close() throws  {
            flushBuilder();
        }

        public void flush() throws  {
            flushBuilder();
        }

        public void write(char[] $r1, int $i0, int $i1) throws  {
            for (int $i3 = 0; $i3 < $i1; $i3++) {
                char $c2 = $r1[$i0 + $i3];
                if ($c2 == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append($c2);
                }
            }
        }

        private void flushBuilder() throws  {
            if (this.mBuilder.length() > 0) {
                Log.v(GLTextureView.TAG, this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }
    }

    public interface Renderer {
        void onDrawFrame(GL10 gl10) throws ;

        void onSurfaceChanged(GL10 gl10, int i, int i2) throws ;

        void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) throws ;
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean $z0) throws  {
            byte $b0;
            if ($z0) {
                $b0 = (byte) 16;
            } else {
                $b0 = (byte) 0;
            }
            super(8, 8, 8, 0, $b0, 0);
        }
    }

    public GLTextureView(Context $r1) throws  {
        super($r1);
        init();
    }

    public GLTextureView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        init();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    private void init() throws  {
        setSurfaceTextureListener(this);
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        surfaceChanged(getSurfaceTexture(), 0, $i2 - $i0, $i3 - $i1);
        super.onLayout($z0, $i0, $i1, $i2, $i3);
    }

    public void setGLWrapper(GLWrapper $r1) throws  {
        this.mGLWrapper = $r1;
    }

    public void setDebugFlags(int $i0) throws  {
        this.mDebugFlags = $i0;
    }

    public int getDebugFlags() throws  {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean $z0) throws  {
        this.mPreserveEGLContextOnPause = $z0;
    }

    public boolean getPreserveEGLContextOnPause() throws  {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer $r1) throws  {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = $r1;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory $r1) throws  {
        checkRenderThreadState();
        this.mEGLContextFactory = $r1;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory $r1) throws  {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = $r1;
    }

    public void setEGLConfigChooser(EGLConfigChooser $r1) throws  {
        checkRenderThreadState();
        this.mEGLConfigChooser = $r1;
    }

    public void setEGLConfigChooser(boolean $z0) throws  {
        setEGLConfigChooser(new SimpleEGLConfigChooser($z0));
    }

    public void setEGLConfigChooser(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5) throws  {
        setEGLConfigChooser(new ComponentSizeChooser($i0, $i1, $i2, $i3, $i4, $i5));
    }

    public void setEGLContextClientVersion(int $i0) throws  {
        checkRenderThreadState();
        this.mEGLContextClientVersion = $i0;
    }

    public void setRenderMode(int $i0) throws  {
        this.mGLThread.setRenderMode($i0);
    }

    public int getRenderMode() throws  {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() throws  {
        this.mGLThread.requestRender();
    }

    public void surfaceCreated(SurfaceTexture holder) throws  {
        this.mGLThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceTexture holder) throws  {
        this.mGLThread.surfaceDestroyed();
    }

    public void surfaceChanged(SurfaceTexture holder, int format, int $i1, int $i2) throws  {
        if (this.mGLThread != null) {
            this.mGLThread.onWindowResize($i1, $i2);
        }
    }

    public void onPause() throws  {
        this.mGLThread.onPause();
    }

    public void onResume() throws  {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable $r1) throws  {
        this.mGLThread.queueEvent($r1);
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int $i0 = 1;
            if (this.mGLThread != null) {
                $i0 = this.mGLThread.getRenderMode();
            }
            this.mGLThread = new GLThread(this.mThisWeakRef);
            if ($i0 != 1) {
                this.mGLThread.setRenderMode($i0);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    protected void onDetachedFromWindow() throws  {
        if (this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture $r1, int $i0, int $i1) throws  {
        surfaceCreated($r1);
        surfaceChanged($r1, 0, $i0, $i1);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture $r1, int $i0, int $i1) throws  {
        surfaceChanged($r1, 0, $i0, $i1);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture $r1) throws  {
        surfaceDestroyed($r1);
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) throws  {
    }

    private void checkRenderThreadState() throws  {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }
}
