package com.abaltatech.mcp.weblink.sdk.widgets;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView.Renderer;
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

public class WLGLSurfaceView extends TextureView implements SurfaceTextureListener {
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
    private static final String TAG = "WLGLSurfaceView";
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
    private final WeakReference<WLGLSurfaceView> mThisWeakRef = new WeakReference(this);

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
            if (WLGLSurfaceView.this.mEGLContextClientVersion != 2) {
                return $r2;
            }
            int $i0 = $r2.length;
            int[] $r1 = new int[($i0 + 2)];
            System.arraycopy($r2, 0, $r1, 0, $i0 - 1);
            $r1[$i0 - 1] = 12352;
            $r1[$i0] = 4;
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
            int[] $r4 = new int[]{this.EGL_CONTEXT_CLIENT_VERSION, WLGLSurfaceView.this.mEGLContextClientVersion, 12344};
            EGLContext $r6 = EGL10.EGL_NO_CONTEXT;
            if (WLGLSurfaceView.this.mEGLContextClientVersion == 0) {
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
                Log.e(WLGLSurfaceView.TAG, "eglCreateWindowSurface", $r5);
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
        private WeakReference<WLGLSurfaceView> mWLGLSurfaceViewWeakRef;

        public EglHelper(@Signature({"(", "Ljava/lang/ref/WeakReference", "<", "Lcom/abaltatech/mcp/weblink/sdk/widgets/WLGLSurfaceView;", ">;)V"}) WeakReference<WLGLSurfaceView> $r1) throws  {
            this.mWLGLSurfaceViewWeakRef = $r1;
        }

        public void start() throws  {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                WLGLSurfaceView $r10 = (WLGLSurfaceView) this.mWLGLSurfaceViewWeakRef.get();
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
                WLGLSurfaceView $r7 = (WLGLSurfaceView) this.mWLGLSurfaceViewWeakRef.get();
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
            WLGLSurfaceView $r6 = (WLGLSurfaceView) this.mWLGLSurfaceViewWeakRef.get();
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
                WLGLSurfaceView $r8 = (WLGLSurfaceView) this.mWLGLSurfaceViewWeakRef.get();
                if ($r8 != null) {
                    $r8.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public void finish() throws  {
            if (this.mEglContext != null) {
                WLGLSurfaceView $r5 = (WLGLSurfaceView) this.mWLGLSurfaceViewWeakRef.get();
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

        public static String formatEglError(String $r0, int error) throws  {
            return $r0 + " failed: ";
        }
    }

    static class GLThread extends Thread {
        private static final String TAG = "GLThread";
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue = new ArrayList();
        private boolean mExited;
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
        private WeakReference<WLGLSurfaceView> mWLGLSurfaceViewWeakRef;
        private boolean mWaitingForSurface;
        private int mWidth = 0;

        private void guardedRun() throws java.lang.InterruptedException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:102:0x020a in {13, 17, 21, 26, 31, 34, 36, 40, 46, 49, 50, 54, 61, 62, 67, 69, 75, 80, 85, 87, 89, 94, 101, 103, 114, 116, 127, 134, 135, 137, 139, 141, 144, 145, 147, 150, 151, 154, 162, 165, 167, 172, 176} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
            r47 = this;
            r3 = "GLThread";
            r4 = "Guarded started";
            android.util.Log.d(r3, r4);
            r5 = new com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView$EglHelper;
            r0 = r47;
            r6 = r0.mWLGLSurfaceViewWeakRef;
            r5.<init>(r6);
            r0 = r47;
            r0.mEglHelper = r5;
            r7 = 0;
            r0 = r47;
            r0.mHaveEglContext = r7;
            r7 = 0;
            r0 = r47;
            r0.mHaveEglSurface = r7;
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
            r18 = 0;
            r19 = 0;
        L_0x002e:
            r20 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01f1 }
            monitor-enter(r20);	 Catch:{ Throwable -> 0x01f1 }
        L_0x0033:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mShouldExit;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0057;	 Catch:{ Throwable -> 0x01ee }
        L_0x003b:
            monitor-exit(r20);	 Catch:{ Throwable -> 0x01ee }
            r3 = "====>";
            r4 = "Finally!";
            android.util.Log.i(r3, r4);
            r22 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;
            monitor-enter(r22);
            r0 = r47;	 Catch:{ Throwable -> 0x0054 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x0054 }
            r0 = r47;	 Catch:{ Throwable -> 0x0054 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x0054 }
            monitor-exit(r22);	 Catch:{ Throwable -> 0x0054 }
            return;
        L_0x0054:
            r23 = move-exception;	 Catch:{ Throwable -> 0x0054 }
            monitor-exit(r22);	 Catch:{ Throwable -> 0x0054 }
            throw r23;
        L_0x0057:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mEventQueue;	 Catch:{ Throwable -> 0x01ee }
            r24 = r0;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0.isEmpty();	 Catch:{ Throwable -> 0x01ee }
            if (r21 != 0) goto L_0x0081;	 Catch:{ Throwable -> 0x01ee }
        L_0x0063:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mEventQueue;	 Catch:{ Throwable -> 0x01ee }
            r24 = r0;	 Catch:{ Throwable -> 0x01ee }
            r7 = 0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r24;	 Catch:{ Throwable -> 0x01ee }
            r25 = r0.remove(r7);	 Catch:{ Throwable -> 0x01ee }
            r26 = r25;	 Catch:{ Throwable -> 0x01ee }
            r26 = (java.lang.Runnable) r26;	 Catch:{ Throwable -> 0x01ee }
            r19 = r26;	 Catch:{ Throwable -> 0x01ee }
        L_0x0076:
            monitor-exit(r20);	 Catch:{ Throwable -> 0x01ee }
            if (r19 == 0) goto L_0x0255;
        L_0x0079:
            r0 = r19;	 Catch:{ Throwable -> 0x01f1 }
            r0.run();	 Catch:{ Throwable -> 0x01f1 }
            r19 = 0;
            goto L_0x002e;
        L_0x0081:
            r21 = 0;
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mPaused;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mRequestPaused;	 Catch:{ Throwable -> 0x01ee }
            r28 = r0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r27;	 Catch:{ Throwable -> 0x01ee }
            r1 = r28;	 Catch:{ Throwable -> 0x01ee }
            if (r0 == r1) goto L_0x00ae;	 Catch:{ Throwable -> 0x01ee }
        L_0x0095:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mRequestPaused;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mRequestPaused;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0;	 Catch:{ Throwable -> 0x01ee }
            r1 = r47;	 Catch:{ Throwable -> 0x01ee }
            r1.mPaused = r0;	 Catch:{ Throwable -> 0x01ee }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01ee }
        L_0x00ae:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mShouldReleaseEglContext;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r27 == 0) goto L_0x00c7;	 Catch:{ Throwable -> 0x01ee }
        L_0x00b6:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x01ee }
            r7 = 0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mShouldReleaseEglContext = r7;	 Catch:{ Throwable -> 0x01ee }
            r16 = 1;	 Catch:{ Throwable -> 0x01ee }
        L_0x00c7:
            if (r12 == 0) goto L_0x00d4;	 Catch:{ Throwable -> 0x01ee }
        L_0x00c9:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x01ee }
            r12 = 0;	 Catch:{ Throwable -> 0x01ee }
        L_0x00d4:
            if (r21 == 0) goto L_0x00e3;	 Catch:{ Throwable -> 0x01ee }
        L_0x00d6:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r27 == 0) goto L_0x00e3;	 Catch:{ Throwable -> 0x01ee }
        L_0x00de:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01ee }
        L_0x00e3:
            if (r21 == 0) goto L_0x0112;	 Catch:{ Throwable -> 0x01ee }
        L_0x00e5:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglContext;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r27 == 0) goto L_0x0112;	 Catch:{ Throwable -> 0x01ee }
        L_0x00ed:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r6 = r0.mWLGLSurfaceViewWeakRef;	 Catch:{ Throwable -> 0x01ee }
            r25 = r6.get();	 Catch:{ Throwable -> 0x01ee }
            r31 = r25;	 Catch:{ Throwable -> 0x01ee }
            r31 = (com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView) r31;	 Catch:{ Throwable -> 0x01ee }
            r30 = r31;	 Catch:{ Throwable -> 0x01ee }
            if (r30 != 0) goto L_0x020e;	 Catch:{ Throwable -> 0x01ee }
        L_0x00fd:
            r27 = 0;	 Catch:{ Throwable -> 0x01ee }
        L_0x00ff:
            if (r27 == 0) goto L_0x010d;	 Catch:{ Throwable -> 0x01ee }
        L_0x0101:
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0.shouldReleaseEGLContextWhenPausing();	 Catch:{ Throwable -> 0x01ee }
            if (r27 == 0) goto L_0x0112;	 Catch:{ Throwable -> 0x01ee }
        L_0x010d:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x01ee }
        L_0x0112:
            if (r21 == 0) goto L_0x0127;	 Catch:{ Throwable -> 0x01ee }
        L_0x0114:
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0.shouldTerminateEGLWhenPausing();	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0127;	 Catch:{ Throwable -> 0x01ee }
        L_0x0120:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r5 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01ee }
            r5.finish();	 Catch:{ Throwable -> 0x01ee }
        L_0x0127:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHasSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 != 0) goto L_0x0157;	 Catch:{ Throwable -> 0x01ee }
        L_0x012f:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mWaitingForSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 != 0) goto L_0x0157;	 Catch:{ Throwable -> 0x01ee }
        L_0x0137:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0144;	 Catch:{ Throwable -> 0x01ee }
        L_0x013f:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x01ee }
        L_0x0144:
            r7 = 1;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mWaitingForSurface = r7;	 Catch:{ Throwable -> 0x01ee }
            r7 = 0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mSurfaceIsBad = r7;	 Catch:{ Throwable -> 0x01ee }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01ee }
        L_0x0157:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHasSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0175;	 Catch:{ Throwable -> 0x01ee }
        L_0x015f:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mWaitingForSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0175;	 Catch:{ Throwable -> 0x01ee }
        L_0x0167:
            r7 = 0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mWaitingForSurface = r7;	 Catch:{ Throwable -> 0x01ee }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01ee }
        L_0x0175:
            if (r15 == 0) goto L_0x0187;	 Catch:{ Throwable -> 0x01ee }
        L_0x0177:
            r14 = 0;	 Catch:{ Throwable -> 0x01ee }
            r15 = 0;	 Catch:{ Throwable -> 0x01ee }
            r7 = 1;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mRenderComplete = r7;	 Catch:{ Throwable -> 0x01ee }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01ee }
        L_0x0187:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0.readyToDraw();	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0247;	 Catch:{ Throwable -> 0x01ee }
        L_0x018f:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglContext;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 != 0) goto L_0x019b;	 Catch:{ Throwable -> 0x01ee }
        L_0x0197:
            if (r16 == 0) goto L_0x0215;	 Catch:{ Throwable -> 0x01ee }
        L_0x0199:
            r16 = 0;	 Catch:{ Throwable -> 0x01ee }
        L_0x019b:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglContext;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x01b3;	 Catch:{ Throwable -> 0x01ee }
        L_0x01a3:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 != 0) goto L_0x01b3;	 Catch:{ Throwable -> 0x01ee }
        L_0x01ab:
            r7 = 1;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mHaveEglSurface = r7;	 Catch:{ Throwable -> 0x01ee }
            r10 = 1;	 Catch:{ Throwable -> 0x01ee }
            r11 = 1;	 Catch:{ Throwable -> 0x01ee }
            r13 = 1;	 Catch:{ Throwable -> 0x01ee }
        L_0x01b3:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHaveEglSurface;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x0247;	 Catch:{ Throwable -> 0x01ee }
        L_0x01bb:
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mSizeChanged;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0;	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x01d7;	 Catch:{ Throwable -> 0x01ee }
        L_0x01c3:
            r13 = 1;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mWidth;	 Catch:{ Throwable -> 0x01ee }
            r17 = r0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0 = r0.mHeight;	 Catch:{ Throwable -> 0x01ee }
            r18 = r0;	 Catch:{ Throwable -> 0x01ee }
            r14 = 1;	 Catch:{ Throwable -> 0x01ee }
            r10 = 1;	 Catch:{ Throwable -> 0x01ee }
            r7 = 0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mSizeChanged = r7;	 Catch:{ Throwable -> 0x01ee }
        L_0x01d7:
            r7 = 0;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mRequestRender = r7;	 Catch:{ Throwable -> 0x01ee }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            goto L_0x01e4;	 Catch:{ Throwable -> 0x01ee }
        L_0x01e1:
            goto L_0x0076;	 Catch:{ Throwable -> 0x01ee }
        L_0x01e4:
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01ee }
            goto L_0x01ed;	 Catch:{ Throwable -> 0x01ee }
        L_0x01ea:
            goto L_0x019b;	 Catch:{ Throwable -> 0x01ee }
        L_0x01ed:
            goto L_0x01e1;	 Catch:{ Throwable -> 0x01ee }
        L_0x01ee:
            r32 = move-exception;	 Catch:{ Throwable -> 0x01ee }
            monitor-exit(r20);	 Catch:{ Throwable -> 0x01ee }
            throw r32;	 Catch:{ Throwable -> 0x01f1 }
        L_0x01f1:
            r33 = move-exception;
            r3 = "====>";
            r4 = "Finally!";
            android.util.Log.i(r3, r4);
            r22 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;
            monitor-enter(r22);
            r0 = r47;	 Catch:{ Throwable -> 0x0337 }
            r0.stopEglSurfaceLocked();	 Catch:{ Throwable -> 0x0337 }
            r0 = r47;	 Catch:{ Throwable -> 0x0337 }
            r0.stopEglContextLocked();	 Catch:{ Throwable -> 0x0337 }
            monitor-exit(r22);	 Catch:{ Throwable -> 0x0337 }
            throw r33;
            goto L_0x020e;
        L_0x020b:
            goto L_0x00ff;
        L_0x020e:
            r0 = r30;	 Catch:{ Throwable -> 0x01ee }
            r27 = r0.mPreserveEGLContextOnPause;	 Catch:{ Throwable -> 0x01ee }
            goto L_0x020b;	 Catch:{ Throwable -> 0x01ee }
        L_0x0215:
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r1 = r47;	 Catch:{ Throwable -> 0x01ee }
            r21 = r0.tryAcquireEglContextLocked(r1);	 Catch:{ Throwable -> 0x01ee }
            if (r21 == 0) goto L_0x019b;
        L_0x0223:
            r0 = r47;	 Catch:{ RuntimeException -> 0x023a }
            r5 = r0.mEglHelper;	 Catch:{ RuntimeException -> 0x023a }
            r5.start();	 Catch:{ RuntimeException -> 0x023a }
            r7 = 1;	 Catch:{ Throwable -> 0x01ee }
            r0 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.mHaveEglContext = r7;	 Catch:{ Throwable -> 0x01ee }
            r9 = 1;	 Catch:{ Throwable -> 0x01ee }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.notifyAll();	 Catch:{ Throwable -> 0x01ee }
            goto L_0x01ea;	 Catch:{ Throwable -> 0x01ee }
        L_0x023a:
            r34 = move-exception;	 Catch:{ Throwable -> 0x01ee }
            r22 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            r0 = r22;	 Catch:{ Throwable -> 0x01ee }
            r1 = r47;	 Catch:{ Throwable -> 0x01ee }
            r0.releaseEglContextLocked(r1);	 Catch:{ Throwable -> 0x01ee }
            throw r34;	 Catch:{ Throwable -> 0x01ee }
        L_0x0247:
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01ee }
            goto L_0x024f;	 Catch:{ Throwable -> 0x01ee }
        L_0x024c:
            goto L_0x0033;	 Catch:{ Throwable -> 0x01ee }
        L_0x024f:
            r0 = r29;	 Catch:{ Throwable -> 0x01ee }
            r0.wait();	 Catch:{ Throwable -> 0x01ee }
            goto L_0x024c;
        L_0x0255:
            if (r10 == 0) goto L_0x027e;
        L_0x0257:
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r5 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01f1 }
            r21 = r5.createSurface();	 Catch:{ Throwable -> 0x01f1 }
            if (r21 != 0) goto L_0x027d;	 Catch:{ Throwable -> 0x01f1 }
        L_0x0261:
            r20 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01f1 }
            monitor-enter(r20);	 Catch:{ Throwable -> 0x01f1 }
            r7 = 1;	 Catch:{ Throwable -> 0x027a }
            r0 = r47;	 Catch:{ Throwable -> 0x027a }
            r0.mSurfaceIsBad = r7;	 Catch:{ Throwable -> 0x027a }
            r29 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x027a }
            r0 = r29;	 Catch:{ Throwable -> 0x027a }
            r0.notifyAll();	 Catch:{ Throwable -> 0x027a }
            goto L_0x0278;	 Catch:{ Throwable -> 0x027a }
        L_0x0275:
            goto L_0x002e;	 Catch:{ Throwable -> 0x027a }
        L_0x0278:
            monitor-exit(r20);	 Catch:{ Throwable -> 0x027a }
            goto L_0x0275;
        L_0x027a:
            r35 = move-exception;	 Catch:{ Throwable -> 0x027a }
            monitor-exit(r20);	 Catch:{ Throwable -> 0x027a }
            throw r35;	 Catch:{ Throwable -> 0x01f1 }
        L_0x027d:
            r10 = 0;
        L_0x027e:
            if (r11 == 0) goto L_0x029c;	 Catch:{ Throwable -> 0x01f1 }
        L_0x0280:
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r5 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01f1 }
            r36 = r5.createGL();	 Catch:{ Throwable -> 0x01f1 }
            r38 = r36;	 Catch:{ Throwable -> 0x01f1 }
            r38 = (javax.microedition.khronos.opengles.GL10) r38;	 Catch:{ Throwable -> 0x01f1 }
            r37 = r38;	 Catch:{ Throwable -> 0x01f1 }
            r8 = r37;	 Catch:{ Throwable -> 0x01f1 }
            r20 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01f1 }
            r0 = r20;	 Catch:{ Throwable -> 0x01f1 }
            r1 = r37;	 Catch:{ Throwable -> 0x01f1 }
            r0.checkGLDriver(r1);	 Catch:{ Throwable -> 0x01f1 }
            r11 = 0;
        L_0x029c:
            if (r9 == 0) goto L_0x02c4;	 Catch:{ Throwable -> 0x01f1 }
        L_0x029e:
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r6 = r0.mWLGLSurfaceViewWeakRef;	 Catch:{ Throwable -> 0x01f1 }
            r25 = r6.get();	 Catch:{ Throwable -> 0x01f1 }
            r39 = r25;	 Catch:{ Throwable -> 0x01f1 }
            r39 = (com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView) r39;	 Catch:{ Throwable -> 0x01f1 }
            r30 = r39;	 Catch:{ Throwable -> 0x01f1 }
            if (r30 == 0) goto L_0x02c3;	 Catch:{ Throwable -> 0x01f1 }
        L_0x02ae:
            r0 = r30;	 Catch:{ Throwable -> 0x01f1 }
            r40 = r0.mRenderer;	 Catch:{ Throwable -> 0x01f1 }
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r5 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01f1 }
            r0 = r5.mEglConfig;	 Catch:{ Throwable -> 0x01f1 }
            r41 = r0;	 Catch:{ Throwable -> 0x01f1 }
            r0 = r40;	 Catch:{ Throwable -> 0x01f1 }
            r1 = r41;	 Catch:{ Throwable -> 0x01f1 }
            r0.onSurfaceCreated(r8, r1);	 Catch:{ Throwable -> 0x01f1 }
        L_0x02c3:
            r9 = 0;
        L_0x02c4:
            if (r13 == 0) goto L_0x02e6;	 Catch:{ Throwable -> 0x01f1 }
        L_0x02c6:
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r6 = r0.mWLGLSurfaceViewWeakRef;	 Catch:{ Throwable -> 0x01f1 }
            r25 = r6.get();	 Catch:{ Throwable -> 0x01f1 }
            r42 = r25;	 Catch:{ Throwable -> 0x01f1 }
            r42 = (com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView) r42;	 Catch:{ Throwable -> 0x01f1 }
            r30 = r42;	 Catch:{ Throwable -> 0x01f1 }
            if (r30 == 0) goto L_0x02e5;	 Catch:{ Throwable -> 0x01f1 }
        L_0x02d6:
            r0 = r30;	 Catch:{ Throwable -> 0x01f1 }
            r40 = r0.mRenderer;	 Catch:{ Throwable -> 0x01f1 }
            r0 = r40;	 Catch:{ Throwable -> 0x01f1 }
            r1 = r17;	 Catch:{ Throwable -> 0x01f1 }
            r2 = r18;	 Catch:{ Throwable -> 0x01f1 }
            r0.onSurfaceChanged(r8, r1, r2);	 Catch:{ Throwable -> 0x01f1 }
        L_0x02e5:
            r13 = 0;	 Catch:{ Throwable -> 0x01f1 }
        L_0x02e6:
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r6 = r0.mWLGLSurfaceViewWeakRef;	 Catch:{ Throwable -> 0x01f1 }
            r25 = r6.get();	 Catch:{ Throwable -> 0x01f1 }
            r43 = r25;	 Catch:{ Throwable -> 0x01f1 }
            r43 = (com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView) r43;	 Catch:{ Throwable -> 0x01f1 }
            r30 = r43;	 Catch:{ Throwable -> 0x01f1 }
            if (r30 == 0) goto L_0x0301;	 Catch:{ Throwable -> 0x01f1 }
        L_0x02f6:
            r0 = r30;	 Catch:{ Throwable -> 0x01f1 }
            r40 = r0.mRenderer;	 Catch:{ Throwable -> 0x01f1 }
            r0 = r40;	 Catch:{ Throwable -> 0x01f1 }
            r0.onDrawFrame(r8);	 Catch:{ Throwable -> 0x01f1 }
        L_0x0301:
            r0 = r47;	 Catch:{ Throwable -> 0x01f1 }
            r5 = r0.mEglHelper;	 Catch:{ Throwable -> 0x01f1 }
            r44 = r5.swap();	 Catch:{ Throwable -> 0x01f1 }
            switch(r44) {
                case 12288: goto L_0x032a;
                case 12302: goto L_0x0332;
                default: goto L_0x030c;
            };	 Catch:{ Throwable -> 0x01f1 }
        L_0x030c:
            goto L_0x030d;	 Catch:{ Throwable -> 0x01f1 }
        L_0x030d:
            r3 = "GLThread";	 Catch:{ Throwable -> 0x01f1 }
            r4 = "eglSwapBuffers";	 Catch:{ Throwable -> 0x01f1 }
            r0 = r44;	 Catch:{ Throwable -> 0x01f1 }
            com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.EglHelper.logEglErrorAsWarning(r3, r4, r0);	 Catch:{ Throwable -> 0x01f1 }
            r22 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x01f1 }
            monitor-enter(r22);	 Catch:{ Throwable -> 0x01f1 }
            r7 = 1;	 Catch:{ Throwable -> 0x0334 }
            r0 = r47;	 Catch:{ Throwable -> 0x0334 }
            r0.mSurfaceIsBad = r7;	 Catch:{ Throwable -> 0x0334 }
            r20 = com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.sGLThreadManager;	 Catch:{ Throwable -> 0x0334 }
            r0 = r20;	 Catch:{ Throwable -> 0x0334 }
            r0.notifyAll();	 Catch:{ Throwable -> 0x0334 }
            monitor-exit(r22);	 Catch:{ Throwable -> 0x0334 }
        L_0x032a:
            if (r14 == 0) goto L_0x002e;
        L_0x032c:
            goto L_0x0330;
        L_0x032d:
            goto L_0x002e;
        L_0x0330:
            r15 = 1;
            goto L_0x032d;
        L_0x0332:
            r12 = 1;
            goto L_0x032a;
        L_0x0334:
            r45 = move-exception;	 Catch:{ Throwable -> 0x0334 }
            monitor-exit(r22);	 Catch:{ Throwable -> 0x0334 }
            throw r45;	 Catch:{ Throwable -> 0x01f1 }
        L_0x0337:
            r46 = move-exception;
            monitor-exit(r22);	 Catch:{ Throwable -> 0x0337 }
            throw r46;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.widgets.WLGLSurfaceView.GLThread.guardedRun():void");
        }

        GLThread(@Signature({"(", "Ljava/lang/ref/WeakReference", "<", "Lcom/abaltatech/mcp/weblink/sdk/widgets/WLGLSurfaceView;", ">;)V"}) WeakReference<WLGLSurfaceView> $r1) throws  {
            this.mWLGLSurfaceViewWeakRef = $r1;
            Log.d(TAG, "GLThread created");
        }

        public void run() throws  {
            Log.d(TAG, "GLThread started");
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException $r1) {
                Log.e(TAG, "GLThread exception", $r1);
            } catch (Throwable $r2) {
                Log.e(TAG, "GLThread exception", $r2);
            } finally {
                Log.d(TAG, "GLThread exiting");
                WLGLSurfaceView.sGLThreadManager.threadExiting(this);
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
                WLGLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
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
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mRenderMode = $i0;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() throws  {
            int i0;
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                i0 = this.mRenderMode;
            }
            return i0;
        }

        public void requestRender() throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mRequestRender = true;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void surfaceCreated() throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mHasSurface = true;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mExited) {
                    try {
                        WLGLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mHasSurface = false;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        WLGLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = true;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        WLGLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        WLGLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int $i0, int $i1) throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mWidth = $i0;
                this.mHeight = $i1;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                    try {
                        WLGLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestExitAndWait() throws  {
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mShouldExit = true;
                WLGLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        WLGLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() throws  {
            this.mShouldReleaseEglContext = true;
            WLGLSurfaceView.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (WLGLSurfaceView.sGLThreadManager) {
                this.mEventQueue.add($r1);
                WLGLSurfaceView.sGLThreadManager.notifyAll();
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread mEglOwner;
        private boolean mGLESDriverCheckComplete;
        private int mGLESVersion;
        private boolean mGLESVersionCheckComplete;
        private boolean mLimitedGLESContexts;
        private boolean mMultipleGLESContextsAllowed;

        private GLThreadManager() throws  {
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
            }
            checkGLESVersion();
            if (this.mMultipleGLESContextsAllowed) {
                return true;
            }
            if (this.mEglOwner != null) {
                this.mEglOwner.requestReleaseEglContextLocked();
            }
            return false;
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
            checkGLESVersion();
            return !this.mMultipleGLESContextsAllowed;
        }

        public synchronized void checkGLDriver(GL10 $r1) throws  {
            boolean $z0 = true;
            synchronized (this) {
                if (!this.mGLESDriverCheckComplete) {
                    checkGLESVersion();
                    String $r2 = $r1.glGetString(7937);
                    if (this.mGLESVersion < 131072) {
                        this.mMultipleGLESContextsAllowed = !$r2.startsWith(kMSM7K_RENDERER_PREFIX);
                        notifyAll();
                    }
                    if (this.mMultipleGLESContextsAllowed) {
                        $z0 = false;
                    }
                    this.mLimitedGLESContexts = $z0;
                    this.mGLESDriverCheckComplete = true;
                }
            }
        }

        private void checkGLESVersion() throws  {
            if (!this.mGLESVersionCheckComplete) {
                this.mMultipleGLESContextsAllowed = true;
                this.mGLESVersionCheckComplete = true;
            }
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
                Log.v(WLGLSurfaceView.TAG, this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }
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

    public WLGLSurfaceView(Context $r1) throws  {
        super($r1);
        init();
    }

    public WLGLSurfaceView(Context $r1, AttributeSet $r2) throws  {
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

    protected void onSizeChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onSizeChanged($i0, $i1, $i2, $i3);
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

    public void surfaceCreated(SurfaceTexture texture) throws  {
        this.mGLThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceTexture texture) throws  {
        this.mGLThread.surfaceDestroyed();
    }

    public void surfaceChanged(SurfaceTexture texture, int format, int $i1, int $i2) throws  {
        this.mGLThread.onWindowResize($i1, $i2);
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

    private void checkRenderThreadState() throws  {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture $r1, int $i0, int $i1) throws  {
        surfaceCreated($r1);
        surfaceChanged($r1, 0, $i0, $i1);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture $r1) throws  {
        surfaceDestroyed($r1);
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture $r1, int $i0, int $i1) throws  {
        surfaceChanged($r1, 0, $i0, $i1);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) throws  {
    }
}
