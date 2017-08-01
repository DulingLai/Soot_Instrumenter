package com.abaltatech.weblinkserver;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;
import com.abaltatech.mcs.logger.MCSLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

@TargetApi(16)
class WLSurface implements OnFrameAvailableListener {
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private static final int FLOAT_SIZE_BYTES = 4;
    private static final String FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    private static final String TAG = "WLSurface";
    private static final float[] TRIANGLE_VERTICES_DATA = new float[]{-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";
    EGL10 mEgl;
    EGLConfig mEglConfig;
    EGLContext mEglContext;
    EGLDisplay mEglDisplay;
    EGLSurface mEglSurface;
    OnFrameAvailableListener mListener;
    private float[] mMVPMatrix = new float[16];
    private int mProgram;
    private float[] mSTMatrix = new float[16];
    private FloatBuffer mTriangleVertices;
    Bitmap m_backBuffer;
    int m_height;
    Surface m_surface;
    SurfaceTexture m_texture;
    int m_width;
    private int maPositionHandle;
    private int maTextureHandle;
    private int muMVPMatrixHandle;
    private int muSTMatrixHandle;

    private int[] getConfig() throws  {
        return new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344};
    }

    public synchronized boolean createSurface(int r45, int r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0092 in list [B:24:0x007d]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r44 = this;
        monitor-enter(r44);
        r6 = 1;
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r7 = r0.m_width;	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        if (r7 != r0) goto L_0x0012;	 Catch:{ Exception -> 0x007d }
    L_0x000a:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r7 = r0.m_height;	 Catch:{ Exception -> 0x007d }
        r0 = r46;	 Catch:{ Exception -> 0x007d }
        if (r7 == r0) goto L_0x0017;	 Catch:{ Exception -> 0x007d }
    L_0x0012:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0.destroySurface();	 Catch:{ Exception -> 0x007d }
    L_0x0017:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r8 = r0.m_surface;	 Catch:{ Exception -> 0x007d }
        if (r8 != 0) goto L_0x0090;	 Catch:{ Exception -> 0x007d }
    L_0x001d:
        r7 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x007d }
        r9 = 16;
        if (r7 < r9) goto L_0x0090;
    L_0x0023:
        r6 = 0;
        r10 = javax.microedition.khronos.egl.EGLContext.getEGL();	 Catch:{ Exception -> 0x007d }
        r12 = r10;	 Catch:{ Exception -> 0x007d }
        r12 = (javax.microedition.khronos.egl.EGL10) r12;	 Catch:{ Exception -> 0x007d }
        r11 = r12;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0.mEgl = r11;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r13 = javax.microedition.khronos.egl.EGL10.EGL_DEFAULT_DISPLAY;	 Catch:{ Exception -> 0x007d }
        r14 = r11.eglGetDisplay(r13);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0.mEglDisplay = r14;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r15 = javax.microedition.khronos.egl.EGL10.EGL_NO_DISPLAY;	 Catch:{ Exception -> 0x007d }
        if (r14 != r15) goto L_0x0092;	 Catch:{ Exception -> 0x007d }
    L_0x0046:
        r16 = new java.lang.RuntimeException;
        r17 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r0.<init>();	 Catch:{ Exception -> 0x007d }
        r18 = "eglGetDisplay failed ";	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r45 = r11.eglGetError();	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r19 = android.opengl.GLUtils.getEGLErrorString(r0);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r19 = r0.toString();	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x007d:
        r20 = move-exception;
        r18 = "WLSurface";	 Catch:{ Exception -> 0x007d }
        r21 = "createSurface";	 Catch:{ Exception -> 0x007d }
        r0 = r18;	 Catch:{ Exception -> 0x007d }
        r1 = r21;	 Catch:{ Exception -> 0x007d }
        r2 = r20;	 Catch:{ Exception -> 0x007d }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1, r2);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0.destroySurface();	 Catch:{ Exception -> 0x007d }
    L_0x0090:
        monitor-exit(r44);
        return r6;
    L_0x0092:
        r9 = 2;	 Catch:{ Exception -> 0x007d }
        r0 = new int[r9];	 Catch:{ Exception -> 0x007d }
        r22 = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r0 = r22;	 Catch:{ Exception -> 0x007d }
        r23 = r11.eglInitialize(r14, r0);	 Catch:{ Exception -> 0x007d }
        if (r23 != 0) goto L_0x00e1;	 Catch:{ Exception -> 0x007d }
    L_0x00a7:
        r16 = new java.lang.RuntimeException;
        r17 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r0.<init>();	 Catch:{ Exception -> 0x007d }
        r18 = "eglInitialize failed ";	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r45 = r11.eglGetError();	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r19 = android.opengl.GLUtils.getEGLErrorString(r0);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r19 = r0.toString();	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x00de:
        r24 = move-exception;
        monitor-exit(r44);
        throw r24;
    L_0x00e1:
        r9 = 1;	 Catch:{ Exception -> 0x007d }
        r0 = new int[r9];	 Catch:{ Exception -> 0x007d }
        r22 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = 1;	 Catch:{ Exception -> 0x007d }
        r0 = new javax.microedition.khronos.egl.EGLConfig[r9];	 Catch:{ Exception -> 0x007d }
        r25 = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r26 = r0.getConfig();	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r9 = 1;	 Catch:{ Exception -> 0x007d }
        r0 = r11;	 Catch:{ Exception -> 0x007d }
        r1 = r14;	 Catch:{ Exception -> 0x007d }
        r2 = r26;	 Catch:{ Exception -> 0x007d }
        r3 = r25;	 Catch:{ Exception -> 0x007d }
        r4 = r9;	 Catch:{ Exception -> 0x007d }
        r5 = r22;	 Catch:{ Exception -> 0x007d }
        r23 = r0.eglChooseConfig(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x007d }
        if (r23 != 0) goto L_0x0140;	 Catch:{ Exception -> 0x007d }
    L_0x0109:
        r27 = new java.lang.IllegalArgumentException;	 Catch:{ Exception -> 0x007d }
        r17 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r0.<init>();	 Catch:{ Exception -> 0x007d }
        r18 = "eglChooseConfig failed ";	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r45 = r11.eglGetError();	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r19 = android.opengl.GLUtils.getEGLErrorString(r0);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r19 = r0.toString();	 Catch:{ Exception -> 0x007d }
        r0 = r27;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r27;	 Catch:{ Exception -> 0x007d }
    L_0x0140:
        r9 = 0;	 Catch:{ Exception -> 0x007d }
        r7 = r22[r9];	 Catch:{ Exception -> 0x007d }
        if (r7 <= 0) goto L_0x0162;	 Catch:{ Exception -> 0x007d }
    L_0x0145:
        r9 = 0;	 Catch:{ Exception -> 0x007d }
        r28 = r25[r9];	 Catch:{ Exception -> 0x007d }
        r0 = r28;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.mEglConfig = r0;	 Catch:{ Exception -> 0x007d }
    L_0x014e:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglConfig;	 Catch:{ Exception -> 0x007d }
        r28 = r0;	 Catch:{ Exception -> 0x007d }
        if (r28 != 0) goto L_0x016b;	 Catch:{ Exception -> 0x007d }
    L_0x0156:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r18 = "eglConfig not initialized";	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x0162:
        r29 = 0;	 Catch:{ Exception -> 0x007d }
        r0 = r29;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.mEglConfig = r0;	 Catch:{ Exception -> 0x007d }
        goto L_0x014e;	 Catch:{ Exception -> 0x007d }
    L_0x016b:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglConfig;	 Catch:{ Exception -> 0x007d }
        r28 = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r1 = r28;	 Catch:{ Exception -> 0x007d }
        r30 = r0.createContext(r11, r14, r1);	 Catch:{ Exception -> 0x007d }
        r0 = r30;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.mEglContext = r0;	 Catch:{ Exception -> 0x007d }
        r9 = 5;	 Catch:{ Exception -> 0x007d }
        r0 = new int[r9];	 Catch:{ Exception -> 0x007d }
        r22 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = 0;	 Catch:{ Exception -> 0x007d }
        r31 = 12375; // 0x3057 float:1.7341E-41 double:6.114E-320;	 Catch:{ Exception -> 0x007d }
        r22[r9] = r31;	 Catch:{ Exception -> 0x007d }
        r9 = 1;	 Catch:{ Exception -> 0x007d }
        r22[r9] = r45;	 Catch:{ Exception -> 0x007d }
        r9 = 2;	 Catch:{ Exception -> 0x007d }
        r31 = 12374; // 0x3056 float:1.734E-41 double:6.1136E-320;	 Catch:{ Exception -> 0x007d }
        r22[r9] = r31;	 Catch:{ Exception -> 0x007d }
        r9 = 3;	 Catch:{ Exception -> 0x007d }
        r22[r9] = r46;	 Catch:{ Exception -> 0x007d }
        r9 = 4;	 Catch:{ Exception -> 0x007d }
        r31 = 12344; // 0x3038 float:1.7298E-41 double:6.0987E-320;	 Catch:{ Exception -> 0x007d }
        r22[r9] = r31;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglConfig;	 Catch:{ Exception -> 0x007d }
        r28 = r0;	 Catch:{ Exception -> 0x007d }
        r1 = r22;	 Catch:{ Exception -> 0x007d }
        r32 = r11.eglCreatePbufferSurface(r14, r0, r1);	 Catch:{ Exception -> 0x007d }
        r0 = r32;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.mEglSurface = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglSurface;	 Catch:{ Exception -> 0x007d }
        r32 = r0;	 Catch:{ Exception -> 0x007d }
        if (r32 == 0) goto L_0x01d1;	 Catch:{ Exception -> 0x007d }
    L_0x01c3:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglSurface;	 Catch:{ Exception -> 0x007d }
        r32 = r0;	 Catch:{ Exception -> 0x007d }
        r33 = javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;	 Catch:{ Exception -> 0x007d }
        r0 = r32;	 Catch:{ Exception -> 0x007d }
        r1 = r33;	 Catch:{ Exception -> 0x007d }
        if (r0 != r1) goto L_0x0208;	 Catch:{ Exception -> 0x007d }
    L_0x01d1:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r45 = r11.eglGetError();	 Catch:{ Exception -> 0x007d }
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r17 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r0.<init>();	 Catch:{ Exception -> 0x007d }
        r18 = "eglCreatePbufferSurface failed ";	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r19 = android.opengl.GLUtils.getEGLErrorString(r0);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r19 = r0.toString();	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x0208:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglSurface;	 Catch:{ Exception -> 0x007d }
        r32 = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglSurface;	 Catch:{ Exception -> 0x007d }
        r33 = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mEglContext;	 Catch:{ Exception -> 0x007d }
        r30 = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r32;	 Catch:{ Exception -> 0x007d }
        r1 = r33;	 Catch:{ Exception -> 0x007d }
        r2 = r30;	 Catch:{ Exception -> 0x007d }
        r23 = r11.eglMakeCurrent(r14, r0, r1, r2);	 Catch:{ Exception -> 0x007d }
        if (r23 != 0) goto L_0x0265;	 Catch:{ Exception -> 0x007d }
    L_0x022e:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r17 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r0.<init>();	 Catch:{ Exception -> 0x007d }
        r18 = "eglMakeCurrent failed ";	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r45 = r11.eglGetError();	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r19 = android.opengl.GLUtils.getEGLErrorString(r0);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r17 = r0.append(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r17;	 Catch:{ Exception -> 0x007d }
        r19 = r0.toString();	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r19;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x0265:
        r9 = 1;	 Catch:{ Exception -> 0x007d }
        r0 = new int[r9];	 Catch:{ Exception -> 0x007d }
        r22 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = 1;	 Catch:{ Exception -> 0x007d }
        r31 = 0;	 Catch:{ Exception -> 0x007d }
        r0 = r22;	 Catch:{ Exception -> 0x007d }
        r1 = r31;	 Catch:{ Exception -> 0x007d }
        android.opengl.GLES20.glGenTextures(r9, r0, r1);	 Catch:{ Exception -> 0x007d }
        r9 = 0;	 Catch:{ Exception -> 0x007d }
        r7 = r22[r9];	 Catch:{ Exception -> 0x007d }
        r9 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x007d }
        android.opengl.GLES20.glBindTexture(r9, r7);	 Catch:{ Exception -> 0x007d }
        r9 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x007d }
        r31 = 10241; // 0x2801 float:1.435E-41 double:5.0597E-320;	 Catch:{ Exception -> 0x007d }
        r34 = 1175977984; // 0x46180000 float:9728.0 double:5.81010322E-315;	 Catch:{ Exception -> 0x007d }
        r0 = r31;	 Catch:{ Exception -> 0x007d }
        r1 = r34;	 Catch:{ Exception -> 0x007d }
        android.opengl.GLES20.glTexParameterf(r9, r0, r1);	 Catch:{ Exception -> 0x007d }
        r9 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x007d }
        r31 = 10240; // 0x2800 float:1.4349E-41 double:5.059E-320;	 Catch:{ Exception -> 0x007d }
        r34 = 1175979008; // 0x46180400 float:9729.0 double:5.81010828E-315;	 Catch:{ Exception -> 0x007d }
        r0 = r31;	 Catch:{ Exception -> 0x007d }
        r1 = r34;	 Catch:{ Exception -> 0x007d }
        android.opengl.GLES20.glTexParameterf(r9, r0, r1);	 Catch:{ Exception -> 0x007d }
        r9 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x007d }
        r31 = 10242; // 0x2802 float:1.4352E-41 double:5.06E-320;	 Catch:{ Exception -> 0x007d }
        r35 = 33071; // 0x812f float:4.6342E-41 double:1.6339E-319;	 Catch:{ Exception -> 0x007d }
        r0 = r31;	 Catch:{ Exception -> 0x007d }
        r1 = r35;	 Catch:{ Exception -> 0x007d }
        android.opengl.GLES20.glTexParameteri(r9, r0, r1);	 Catch:{ Exception -> 0x007d }
        r9 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x007d }
        r31 = 10243; // 0x2803 float:1.4354E-41 double:5.0607E-320;	 Catch:{ Exception -> 0x007d }
        r35 = 33071; // 0x812f float:4.6342E-41 double:1.6339E-319;	 Catch:{ Exception -> 0x007d }
        r0 = r31;	 Catch:{ Exception -> 0x007d }
        r1 = r35;	 Catch:{ Exception -> 0x007d }
        android.opengl.GLES20.glTexParameteri(r9, r0, r1);	 Catch:{ Exception -> 0x007d }
        r36 = TRIANGLE_VERTICES_DATA;	 Catch:{ Exception -> 0x007d }
        r0 = r36;	 Catch:{ Exception -> 0x007d }
        r0 = r0.length;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r37 = r37 * 4;	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r38 = java.nio.ByteBuffer.allocateDirect(r0);	 Catch:{ Exception -> 0x007d }
        r39 = java.nio.ByteOrder.nativeOrder();	 Catch:{ Exception -> 0x007d }
        r0 = r38;	 Catch:{ Exception -> 0x007d }
        r1 = r39;	 Catch:{ Exception -> 0x007d }
        r38 = r0.order(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r38;	 Catch:{ Exception -> 0x007d }
        r40 = r0.asFloatBuffer();	 Catch:{ Exception -> 0x007d }
        r0 = r40;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.mTriangleVertices = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mTriangleVertices;	 Catch:{ Exception -> 0x007d }
        r40 = r0;	 Catch:{ Exception -> 0x007d }
        r36 = TRIANGLE_VERTICES_DATA;	 Catch:{ Exception -> 0x007d }
        r0 = r40;	 Catch:{ Exception -> 0x007d }
        r1 = r36;	 Catch:{ Exception -> 0x007d }
        r40 = r0.put(r1);	 Catch:{ Exception -> 0x007d }
        r9 = 0;	 Catch:{ Exception -> 0x007d }
        r0 = r40;	 Catch:{ Exception -> 0x007d }
        r0.position(r9);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mSTMatrix;	 Catch:{ Exception -> 0x007d }
        r36 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = 0;	 Catch:{ Exception -> 0x007d }
        r0 = r36;	 Catch:{ Exception -> 0x007d }
        android.opengl.Matrix.setIdentityM(r0, r9);	 Catch:{ Exception -> 0x007d }
        r18 = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";	 Catch:{ Exception -> 0x007d }
        r21 = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r2 = r21;	 Catch:{ Exception -> 0x007d }
        r37 = r0.createProgram(r1, r2);	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.mProgram = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mProgram;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        if (r37 != 0) goto L_0x032b;	 Catch:{ Exception -> 0x007d }
    L_0x031f:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r18 = "failed creating program";	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x032b:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mProgram;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "aPosition";	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r37 = android.opengl.GLES20.glGetAttribLocation(r0, r1);	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.maPositionHandle = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "glGetAttribLocation aPosition";	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.checkGlError(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.maPositionHandle;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = -1;	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        if (r0 != r9) goto L_0x0361;	 Catch:{ Exception -> 0x007d }
    L_0x0355:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r18 = "Could not get attrib location for aPosition";	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x0361:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mProgram;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "aTextureCoord";	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r37 = android.opengl.GLES20.glGetAttribLocation(r0, r1);	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.maTextureHandle = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "glGetAttribLocation aTextureCoord";	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.checkGlError(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.maTextureHandle;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = -1;	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        if (r0 != r9) goto L_0x0397;	 Catch:{ Exception -> 0x007d }
    L_0x038b:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r18 = "Could not get attrib location for aTextureCoord";	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x0397:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mProgram;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "uMVPMatrix";	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r37 = android.opengl.GLES20.glGetUniformLocation(r0, r1);	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.muMVPMatrixHandle = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "glGetUniformLocation uMVPMatrix";	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.checkGlError(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.muMVPMatrixHandle;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = -1;	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        if (r0 != r9) goto L_0x03ce;	 Catch:{ Exception -> 0x007d }
    L_0x03c2:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r18 = "Could not get attrib location for uMVPMatrix";	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x03ce:
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.mProgram;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "uSTMatrix";	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r37 = android.opengl.GLES20.glGetUniformLocation(r0, r1);	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.muSTMatrixHandle = r0;	 Catch:{ Exception -> 0x007d }
        r18 = "glGetUniformLocation uSTMatrix";	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.checkGlError(r1);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.muSTMatrixHandle;	 Catch:{ Exception -> 0x007d }
        r37 = r0;	 Catch:{ Exception -> 0x007d }
        r9 = -1;	 Catch:{ Exception -> 0x007d }
        r0 = r37;	 Catch:{ Exception -> 0x007d }
        if (r0 != r9) goto L_0x0405;	 Catch:{ Exception -> 0x007d }
    L_0x03f9:
        r16 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x007d }
        r18 = "Could not get attrib location for uSTMatrix";	 Catch:{ Exception -> 0x007d }
        r0 = r16;	 Catch:{ Exception -> 0x007d }
        r1 = r18;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r1);	 Catch:{ Exception -> 0x007d }
        throw r16;	 Catch:{ Exception -> 0x007d }
    L_0x0405:
        r41 = new android.graphics.SurfaceTexture;	 Catch:{ Exception -> 0x007d }
        r0 = r41;	 Catch:{ Exception -> 0x007d }
        r0.<init>(r7);	 Catch:{ Exception -> 0x007d }
        r0 = r41;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.m_texture = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.m_texture;	 Catch:{ Exception -> 0x007d }
        r41 = r0;	 Catch:{ Exception -> 0x007d }
        r1 = r45;	 Catch:{ Exception -> 0x007d }
        r2 = r46;	 Catch:{ Exception -> 0x007d }
        r0.setDefaultBufferSize(r1, r2);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.m_texture;	 Catch:{ Exception -> 0x007d }
        r41 = r0;	 Catch:{ Exception -> 0x007d }
        r0.detachFromGLContext();	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r11 = r0.mEgl;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r14 = r0.mEglDisplay;	 Catch:{ Exception -> 0x007d }
        r32 = javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;
        r33 = javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;
        r30 = javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT;	 Catch:{ Exception -> 0x007d }
        r0 = r32;	 Catch:{ Exception -> 0x007d }
        r1 = r33;	 Catch:{ Exception -> 0x007d }
        r2 = r30;	 Catch:{ Exception -> 0x007d }
        r11.eglMakeCurrent(r14, r0, r1, r2);	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.m_width = r0;	 Catch:{ Exception -> 0x007d }
        r0 = r46;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.m_height = r0;	 Catch:{ Exception -> 0x007d }
        r42 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Exception -> 0x007d }
        r0 = r45;	 Catch:{ Exception -> 0x007d }
        r1 = r46;	 Catch:{ Exception -> 0x007d }
        r2 = r42;	 Catch:{ Exception -> 0x007d }
        r43 = android.graphics.Bitmap.createBitmap(r0, r1, r2);	 Catch:{ Exception -> 0x007d }
        r0 = r43;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r1.m_backBuffer = r0;	 Catch:{ Exception -> 0x007d }
        r8 = new android.view.Surface;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.m_texture;	 Catch:{ Exception -> 0x007d }
        r41 = r0;	 Catch:{ Exception -> 0x007d }
        r8.<init>(r0);	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0.m_surface = r8;	 Catch:{ Exception -> 0x007d }
        r0 = r44;	 Catch:{ Exception -> 0x007d }
        r0 = r0.m_texture;	 Catch:{ Exception -> 0x007d }
        r41 = r0;	 Catch:{ Exception -> 0x007d }
        r1 = r44;	 Catch:{ Exception -> 0x007d }
        r0.setOnFrameAvailableListener(r1);	 Catch:{ Exception -> 0x007d }
        goto L_0x047b;	 Catch:{ Exception -> 0x007d }
    L_0x0478:
        goto L_0x0090;	 Catch:{ Exception -> 0x007d }
    L_0x047b:
        r6 = 1;
        goto L_0x0478;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLSurface.createSurface(int, int):boolean");
    }

    WLSurface() throws  {
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener $r1) throws  {
        this.mListener = $r1;
    }

    public static WLSurface create(int $i0, int $i1) throws  {
        WLSurface $r0 = new WLSurface();
        if ($r0.createSurface($i0, $i1)) {
            return $r0;
        }
        $r0.destroySurface();
        Log.e(TAG, "Error creating surface, width=" + $i0 + ", height=" + $i1);
        return null;
    }

    public synchronized void destroySurface() throws  {
        if (this.m_backBuffer != null) {
            this.m_backBuffer.recycle();
            this.m_backBuffer = null;
        }
        if (this.m_surface != null) {
            this.m_surface.release();
            this.m_surface = null;
        }
        if (this.m_texture != null) {
            this.m_texture.release();
            this.m_texture = null;
        }
        if (this.mEgl != null) {
            if (this.mProgram != 0) {
                if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                    GLES20.glDeleteProgram(this.mProgram);
                }
                this.mProgram = 0;
            }
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            if (this.mEglSurface != null) {
                this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
                this.mEglSurface = null;
            }
            if (this.mEglContext != null) {
                this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
                this.mEglContext = null;
            }
            this.mEgl = null;
            this.mEglConfig = null;
        }
    }

    public Surface getSurface() throws  {
        return this.m_surface;
    }

    public Bitmap getBitmap() throws  {
        return this.m_backBuffer;
    }

    public int getWidth() throws  {
        return this.m_width;
    }

    public int getHeight() throws  {
        return this.m_height;
    }

    public synchronized boolean drawToCanvas(Canvas $r1, float $f0, float $f1) throws  {
        boolean $z0;
        $z0 = false;
        if (this.m_backBuffer != null) {
            $r1.drawBitmap(this.m_backBuffer, $f0, $f1, null);
            $z0 = true;
        }
        return $z0;
    }

    public synchronized boolean drawToCanvas(Canvas $r1, RectF $r2) throws  {
        boolean $z0;
        $z0 = false;
        if (this.m_backBuffer != null) {
            Paint $r4 = null;
            if (((float) this.m_width) < $r2.width() && ((float) this.m_height) < $r2.height()) {
                $r4 = new Paint(2);
            }
            $r1.drawBitmap(this.m_backBuffer, new Rect(0, 0, this.m_width, this.m_height), $r2, $r4);
            $z0 = true;
        }
        return $z0;
    }

    public synchronized boolean saveImage(File $r1) throws  {
        if (!(this.m_backBuffer == null || $r1 == null)) {
            if ($r1.exists()) {
                $r1.delete();
            }
            try {
                FileOutputStream $r3 = new FileOutputStream($r1);
                this.m_backBuffer.compress(CompressFormat.PNG, 100, $r3);
                $r3.flush();
                $r3.close();
            } catch (Exception $r2) {
                MCSLogger.log(TAG, "Error saving image", $r2);
            }
        }
        return false;
    }

    public synchronized void onFrameAvailable(SurfaceTexture st) throws  {
        if (!(this.mEgl == null || this.mEglDisplay == null || this.mEglSurface == null || this.mEglContext == null || this.m_texture == null)) {
            if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                SurfaceTexture $r1;
                try {
                    this.m_texture.attachToGLContext(1001);
                    $r1 = this.m_texture;
                    $r1.updateTexImage();
                    GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
                    GLES20.glClear(16640);
                    GLES20.glUseProgram(this.mProgram);
                    checkGlError("glUseProgram");
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(36197, 1001);
                    this.mTriangleVertices.position(0);
                    GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 20, this.mTriangleVertices);
                    checkGlError("glVertexAttribPointer maPosition");
                    GLES20.glEnableVertexAttribArray(this.maPositionHandle);
                    checkGlError("glEnableVertexAttribArray maPositionHandle");
                    this.mTriangleVertices.position(3);
                    GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, 20, this.mTriangleVertices);
                    checkGlError("glVertexAttribPointer maTextureHandle");
                    GLES20.glEnableVertexAttribArray(this.maTextureHandle);
                    checkGlError("glEnableVertexAttribArray maTextureHandle");
                    Matrix.setIdentityM(this.mMVPMatrix, 0);
                    GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false, this.mMVPMatrix, 0);
                    GLES20.glUniformMatrix4fv(this.muSTMatrixHandle, 1, false, this.mSTMatrix, 0);
                    GLES20.glDrawArrays(5, 0, 4);
                    checkGlError("glDrawArrays");
                    WLImageUtils.glReadPixels(this.m_backBuffer, false);
                    $r1 = this.m_texture;
                    $r1.detachFromGLContext();
                    this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                    if (this.mListener != null) {
                        this.mListener.onFrameAvailable(null);
                    }
                    this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                } catch (Throwable $r17) {
                    Log.w(TAG, "ERROR detaching eGL context in onFrameAvailable", $r17);
                }
            } else {
                throw new RuntimeException("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
            }
        }
        return;
        this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
    }

    private int loadShader(int $i0, String $r1) throws  {
        int $i1 = GLES20.glCreateShader($i0);
        checkGlError("glCreateShader type=" + $i0);
        GLES20.glShaderSource($i1, $r1);
        GLES20.glCompileShader($i1);
        int[] $r2 = new int[1];
        GLES20.glGetShaderiv($i1, 35713, $r2, 0);
        if ($r2[0] != 0) {
            return $i1;
        }
        MCSLogger.log(TAG, "Could not compile shader " + $i0 + ":");
        MCSLogger.log(TAG, " " + GLES20.glGetShaderInfoLog($i1));
        GLES20.glDeleteShader($i1);
        return 0;
    }

    private int createProgram(String $r1, String $r2) throws  {
        int $i0 = loadShader(35633, $r1);
        if ($i0 == 0) {
            return 0;
        }
        int $i1 = loadShader(35632, $r2);
        if ($i1 == 0) {
            return 0;
        }
        int $i2 = GLES20.glCreateProgram();
        checkGlError("glCreateProgram");
        if ($i2 == 0) {
            MCSLogger.log(TAG, "Could not create program");
        }
        GLES20.glAttachShader($i2, $i0);
        checkGlError("glAttachShader");
        GLES20.glAttachShader($i2, $i1);
        checkGlError("glAttachShader");
        GLES20.glLinkProgram($i2);
        int[] $r3 = new int[1];
        GLES20.glGetProgramiv($i2, 35714, $r3, 0);
        if ($r3[0] == 1) {
            return $i2;
        }
        MCSLogger.log(TAG, "Could not link program: ");
        MCSLogger.log(TAG, GLES20.glGetProgramInfoLog($i2));
        GLES20.glDeleteProgram($i2);
        return 0;
    }

    private void checkGlError(String $r1) throws  {
        int $i0 = GLES20.glGetError();
        if ($i0 != 0) {
            MCSLogger.log(TAG, $r1 + ": glError " + $i0);
            throw new RuntimeException($r1 + ": glError " + $i0);
        }
    }

    private EGLContext createContext(EGL10 $r1, EGLDisplay $r2, EGLConfig $r3) throws  {
        return $r1.eglCreateContext($r2, $r3, EGL10.EGL_NO_CONTEXT, new int[]{EGL_CONTEXT_CLIENT_VERSION, 2, 12344});
    }
}
