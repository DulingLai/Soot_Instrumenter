package com.waze;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceView;
import com.google.android.gms.auth.firstparty.shared.Status;
import com.waze.strings.DisplayStrings;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public final class EglManager {
    private static final int EGL_CONTEXT_RECOVER_RETRY_NUM = 5;
    private static final boolean EGL_DEBUG = false;
    private int mContextRecoverRetry = 0;
    private EGL10 mEgl = null;
    private EGLConfig mEglConfig = null;
    private EGLContext mEglContext = null;
    private EGLDisplay mEglDisplay = null;
    private EGLSurface mEglSurface = null;
    public boolean mGLTestStop = false;
    boolean mIsFirstSwap = false;
    private SurfaceView mSurfaceView = null;

    private class EglErrorListener implements OnClickListener {
        private EglErrorListener() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
            MsgBox $r3 = MsgBox.getInstance();
            synchronized ($r3) {
                $r3.notify();
            }
        }
    }

    public EglManager(SurfaceView $r1) throws  {
        this.mSurfaceView = $r1;
    }

    public boolean InitEgl() throws  {
        this.mEgl = (EGL10) EGLContext.getEGL();
        this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        EglCheck("InitEgl eglGetDisplay");
        this.mEgl.eglInitialize(this.mEglDisplay, new int[2]);
        EglCheck("InitEgl eglInitialize");
        EGLConfig[] $r2 = new EGLConfig[1];
        this.mEglConfig = ChooseEglConfig(new int[]{12324, 5, 12323, 6, 12322, 5, 12321, 0, 12325, 0, 12326, 0, 12344});
        this.mEglContext = this.mEgl.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL10.EGL_NO_CONTEXT, null);
        EglCheck("InitEgl eglCreateContext");
        return CreateEglSurface();
    }

    public void DestroyEgl() throws  {
        if (this.mEglDisplay != null) {
            DestroyEglSurface();
            this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
            EglCheck("DestroyEgl eglDestroyContext");
            this.mEgl.eglTerminate(this.mEglDisplay);
            EglCheck("DestroyEgl eglTerminate");
            this.mEglContext = null;
            this.mEglSurface = null;
            this.mEglDisplay = null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.microedition.khronos.egl.EGLConfig ChooseEglConfig(int[] r24) throws  {
        /*
        r23 = this;
        r6 = 0;
        r8 = 1;
        r7 = new int[r8];
        r0 = r23;
        r9 = r0.mEgl;
        r0 = r23;
        r10 = r0.mEglDisplay;
        r12 = 0;
        r8 = 0;
        r0 = r9;
        r1 = r10;
        r2 = r24;
        r3 = r12;
        r4 = r8;
        r5 = r7;
        r11 = r0.eglChooseConfig(r1, r2, r3, r4, r5);
        if (r11 != 0) goto L_0x0029;
    L_0x001b:
        r13 = "InitEgl eglChooseConfig";
        r0 = r23;
        r0.EglCheck(r13);
        r13 = "eglChooseConfigfailed - failed getting configuration number";
        com.waze.Logger.m43w(r13);
        r12 = 0;
        return r12;
    L_0x0029:
        r8 = 0;
        r14 = r7[r8];
        if (r14 > 0) goto L_0x0035;
    L_0x002e:
        r13 = "eglChooseConfigfailed - failed getting configuration number";
        com.waze.Logger.m43w(r13);
        r12 = 0;
        return r12;
    L_0x0035:
        r15 = new javax.microedition.khronos.egl.EGLConfig[r14];
        r0 = r23;
        r9 = r0.mEgl;
        r0 = r23;
        r10 = r0.mEglDisplay;
        r0 = r9;
        r1 = r10;
        r2 = r24;
        r3 = r15;
        r4 = r14;
        r5 = r7;
        r11 = r0.eglChooseConfig(r1, r2, r3, r4, r5);
        if (r11 != 0) goto L_0x005a;
    L_0x004c:
        r13 = "InitEgl eglChooseConfig II";
        r0 = r23;
        r0.EglCheck(r13);
        r13 = "eglChooseConfigfailed - failed getting configuration number";
        com.waze.Logger.m43w(r13);
        r12 = 0;
        return r12;
    L_0x005a:
        r14 = 0;
    L_0x005b:
        r0 = r15.length;
        r16 = r0;
        if (r14 >= r0) goto L_0x011c;
    L_0x0060:
        r17 = r15[r14];
        r8 = 12325; // 0x3025 float:1.7271E-41 double:6.0894E-320;
        r19 = 0;
        r0 = r23;
        r1 = r17;
        r2 = r19;
        r18 = r0.getCfgValue(r1, r8, r2);
        r8 = 12326; // 0x3026 float:1.7272E-41 double:6.09E-320;
        r19 = 0;
        r0 = r23;
        r1 = r17;
        r2 = r19;
        r16 = r0.getCfgValue(r1, r8, r2);
        r8 = 12325; // 0x3025 float:1.7271E-41 double:6.0894E-320;
        r0 = r23;
        r1 = r24;
        r20 = r0.getAttrValue(r1, r8);
        r0 = r18;
        r1 = r20;
        if (r0 < r1) goto L_0x0149;
    L_0x008e:
        r8 = 12326; // 0x3026 float:1.7272E-41 double:6.09E-320;
        r0 = r23;
        r1 = r24;
        r18 = r0.getAttrValue(r1, r8);
        r0 = r16;
        r1 = r18;
        if (r0 < r1) goto L_0x0149;
    L_0x009e:
        r8 = 12324; // 0x3024 float:1.727E-41 double:6.089E-320;
        r19 = 0;
        r0 = r23;
        r1 = r17;
        r2 = r19;
        r21 = r0.getCfgValue(r1, r8, r2);
        r8 = 12323; // 0x3023 float:1.7268E-41 double:6.0884E-320;
        r19 = 0;
        r0 = r23;
        r1 = r17;
        r2 = r19;
        r20 = r0.getCfgValue(r1, r8, r2);
        r8 = 12322; // 0x3022 float:1.7267E-41 double:6.088E-320;
        r19 = 0;
        r0 = r23;
        r1 = r17;
        r2 = r19;
        r18 = r0.getCfgValue(r1, r8, r2);
        r8 = 12321; // 0x3021 float:1.7265E-41 double:6.0874E-320;
        r19 = 0;
        r0 = r23;
        r1 = r17;
        r2 = r19;
        r16 = r0.getCfgValue(r1, r8, r2);
        goto L_0x00da;
    L_0x00d7:
        goto L_0x005b;
    L_0x00da:
        r8 = 12324; // 0x3024 float:1.727E-41 double:6.089E-320;
        r0 = r23;
        r1 = r24;
        r22 = r0.getAttrValue(r1, r8);
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x0149;
    L_0x00ea:
        r8 = 12323; // 0x3023 float:1.7268E-41 double:6.0884E-320;
        r0 = r23;
        r1 = r24;
        r21 = r0.getAttrValue(r1, r8);
        r0 = r20;
        r1 = r21;
        if (r0 != r1) goto L_0x0149;
    L_0x00fa:
        r8 = 12322; // 0x3022 float:1.7267E-41 double:6.088E-320;
        r0 = r23;
        r1 = r24;
        r20 = r0.getAttrValue(r1, r8);
        r0 = r18;
        r1 = r20;
        if (r0 != r1) goto L_0x0149;
    L_0x010a:
        r8 = 12321; // 0x3021 float:1.7265E-41 double:6.0874E-320;
        r0 = r23;
        r1 = r24;
        r18 = r0.getAttrValue(r1, r8);
        r0 = r16;
        r1 = r18;
        if (r0 != r1) goto L_0x0149;
    L_0x011a:
        r6 = r17;
    L_0x011c:
        if (r6 != 0) goto L_0x0148;
    L_0x011e:
        r8 = 1;
        r0 = new int[r8];
        r24 = r0;
        r8 = 0;
        r19 = 12344; // 0x3038 float:1.7298E-41 double:6.0987E-320;
        r24[r8] = r19;
        r8 = 1;
        r15 = new javax.microedition.khronos.egl.EGLConfig[r8];
        r0 = r23;
        r9 = r0.mEgl;
        r0 = r23;
        r10 = r0.mEglDisplay;
        r8 = 1;
        r0 = r9;
        r1 = r10;
        r2 = r24;
        r3 = r15;
        r4 = r8;
        r5 = r7;
        r0.eglChooseConfig(r1, r2, r3, r4, r5);
        r13 = "InitEgl eglChooseConfig III";
        r0 = r23;
        r0.EglCheck(r13);
        r8 = 0;
        r6 = r15[r8];
    L_0x0148:
        return r6;
    L_0x0149:
        r14 = r14 + 1;
        goto L_0x00d7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.EglManager.ChooseEglConfig(int[]):javax.microedition.khronos.egl.EGLConfig");
    }

    public void DestroyEglSurface() throws  {
        if (this.mEglDisplay == null || this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
            Log.e(Logger.TAG, "Surface parameters are not initialized");
            Logger.m38e("Surface parameters are not initialized");
            return;
        }
        this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        EglCheck("DestroyEglSurface eglMakeCurrent");
        this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
        EglCheck("DestroyEglSurface eglDestroySurface");
        this.mEglSurface = null;
    }

    public boolean CreateEglSurface() throws  {
        if (this.mEglSurface != null) {
            DestroyEglSurface();
        }
        this.mEglSurface = this.mEgl.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, this.mSurfaceView.getHolder(), null);
        if (EglCheck("CreateEglSurface eglCreateWindowSurface") != 12288) {
            return false;
        }
        this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext);
        return EglCheck("CreateEglSurface eglMakeCurrent") == 12288;
    }

    public void SwapBuffersEgl() throws  {
        if (this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
            this.mContextRecoverRetry = 0;
            return;
        }
        switch (EglCheck("EGL Swap buffers")) {
            case 12288:
                break;
            case 12302:
                int $i0 = this.mContextRecoverRetry;
                this.mContextRecoverRetry = $i0 + 1;
                if ($i0 < 5) {
                    DestroyEglSurface();
                    CreateEglSurface();
                    return;
                }
                return;
            default:
                MsgBox $r5 = MsgBox.getInstance();
                $r5.setBlocking(true);
                $r5.Show(Status.EXTRA_KEY_STATUS, "Critical problem occured! Please restart waze", "Ok", null, new EglErrorListener(), null);
                break;
        }
    }

    private int getCfgValue(EGLConfig $r1, int $i0, int $i1) throws  {
        int[] $r2 = new int[1];
        if (this.mEgl.eglGetConfigAttrib(this.mEglDisplay, $r1, $i0, $r2)) {
            return $r2[0];
        }
        return $i1;
    }

    private int getAttrValue(int[] $r1, int $i0) throws  {
        for (int $i1 = 0; $i1 < $r1.length; $i1 += 2) {
            if ($r1[$i1] == $i0) {
                return $r1[$i1 + 1];
            }
        }
        return -1;
    }

    private int EglCheck(String $r1) throws  {
        int $i0 = this.mEgl.eglGetError();
        if ($i0 == 12288) {
            return $i0;
        }
        String $r2 = new String("EGL error in " + $r1 + ". Error code: " + $i0);
        Log.e(Logger.TAG, $r2);
        Logger.m38e($r2);
        return $i0;
    }

    public void DrawGLTest() throws  {
        this.mGLTestStop = false;
        int $i0 = this.mSurfaceView.getMeasuredWidth();
        int $i1 = this.mSurfaceView.getMeasuredHeight();
        GL10 $r4 = (GL10) this.mEglContext.getGL();
        $r4.glMatrixMode(5889);
        $r4.glLoadIdentity();
        $r4.glViewport(0, 0, $i0, $i1);
        $r4.glOrthof(0.0f, (float) $i0, 0.0f, (float) $i1, 0.0f, 1.0f);
        $r4.glShadeModel(7424);
        $r4.glEnable(DisplayStrings.DS_SEND_DRIVE_EMAIL_BODY_PS_PS_PS_PS);
        $r4.glEnable(DisplayStrings.DS_OFFER_RIDE_SELECT_FROM_TITLE);
        $i0 = 3;
        while (!this.mGLTestStop && $i0 < 50) {
            $r4.glClearColor(((float) $i0) % 3.0f, ((float) ($i0 + 1)) % 3.0f, ((float) ($i0 + 2)) % 3.0f, 1.0f);
            $r4.glClear(16640);
            if (this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                SystemClock.sleep(300);
                $i0++;
            } else {
                throw new RuntimeException("eglSwapBuffers failed.");
            }
        }
    }

    public GL10 getGL() throws  {
        if (this.mEglContext == null) {
            return null;
        }
        return (GL10) this.mEglContext.getGL();
    }
}
