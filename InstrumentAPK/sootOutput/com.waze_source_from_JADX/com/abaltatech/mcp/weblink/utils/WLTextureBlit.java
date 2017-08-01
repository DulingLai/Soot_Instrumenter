package com.abaltatech.mcp.weblink.utils;

import android.opengl.GLES20;
import android.util.Log;
import android.view.Surface;
import com.abaltatech.mcp.weblink.utils.grafika.EglCore;
import com.abaltatech.mcp.weblink.utils.grafika.GlUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLSurface;

public class WLTextureBlit {
    protected static final FloatBuffer BUF_TEX_COORDS_PBUFFER = GlUtil.createFloatBuffer(TEX_COORDS_PBUFFER);
    protected static final FloatBuffer BUF_TEX_COORDS_SURFACE = GlUtil.createFloatBuffer(TEX_COORDS_SURFACE);
    protected static final String FRAGMENT_SHADER_EXT = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    protected static final FloatBuffer RECTANGLE_BUF = GlUtil.createFloatBuffer(RECTANGLE_COORDS);
    private static final float[] RECTANGLE_COORDS = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    private static final String TAG = "WLTextureBlit";
    private static final float[] TEX_COORDS_PBUFFER = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] TEX_COORDS_SURFACE = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    protected static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
    protected EglCore m_egl;
    protected EGLSurface m_encoderSurface;
    protected int m_height;
    protected int m_mvpMatrixLoc;
    protected int m_positionLoc;
    protected int m_program = -1;
    protected ByteBuffer m_surfaceBuffer;
    protected int m_texMatrixLoc;
    protected int m_textureCoordLoc;
    protected int m_width;

    public void drawTexture(android.graphics.SurfaceTexture r33, android.graphics.Rect r34) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x01f0 in {2, 6, 7, 12, 14, 18, 25, 28, 32, 33, 35, 36} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r32 = this;
        r6 = com.abaltatech.mcp.weblink.utils.grafika.GlUtil.IDENTITY_MATRIX;
        r7 = com.abaltatech.mcp.weblink.utils.grafika.GlUtil.IDENTITY_MATRIX;
        r8 = RECTANGLE_BUF;
        r0 = r32;
        r9 = r0.m_surfaceBuffer;
        if (r9 == 0) goto L_0x01f4;
    L_0x000c:
        r10 = BUF_TEX_COORDS_PBUFFER;
    L_0x000e:
        r11 = 0;
        r0 = r34;
        r12 = r0.left;
        if (r12 != 0) goto L_0x001b;
    L_0x0015:
        r0 = r34;
        r12 = r0.top;
        if (r12 == 0) goto L_0x00c9;
    L_0x001b:
        r0 = r34;
        r12 = r0.left;
        r13 = (float) r12;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r13 = r13 * r14;
        r0 = r32;
        r12 = r0.m_width;
        r15 = (float) r12;
        r13 = r13 / r15;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r13 = r13 - r14;
        r0 = r32;
        r12 = r0.m_height;
        r0 = r34;
        r0 = r0.top;
        r16 = r0;
        r12 = r12 - r0;
        r15 = (float) r12;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r15 = r15 * r14;
        r0 = r32;
        r12 = r0.m_height;
        r0 = (float) r12;
        r17 = r0;
        r15 = r15 / r0;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r15 = r15 - r14;
        r0 = r34;
        r12 = r0.right;
        r0 = (float) r12;
        r17 = r0;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r17;
        r0 = r0 * r14;
        r17 = r0;
        r0 = r32;
        r12 = r0.m_width;
        r0 = (float) r12;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 / r1;
        r17 = r0;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r17;
        r0 = r0 - r14;
        r17 = r0;
        r0 = r32;
        r12 = r0.m_height;
        r0 = r34;
        r0 = r0.bottom;
        r16 = r0;
        r12 = r12 - r0;
        r0 = (float) r12;
        r18 = r0;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r18;
        r0 = r0 * r14;
        r18 = r0;
        r0 = r32;
        r12 = r0.m_height;
        r0 = (float) r12;
        r19 = r0;
        r0 = r18;
        r1 = r19;
        r0 = r0 / r1;
        r18 = r0;
        r14 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r18;
        r0 = r0 - r14;
        r18 = r0;
        r21 = 8;
        r0 = r21;
        r0 = new float[r0];
        r20 = r0;
        r21 = 0;
        r20[r21] = r13;
        r21 = 1;
        r20[r21] = r18;
        r21 = 2;
        r20[r21] = r17;
        r21 = 3;
        r20[r21] = r18;
        r21 = 4;
        r20[r21] = r13;
        r21 = 5;
        r20[r21] = r15;
        r21 = 6;
        r20[r21] = r17;
        r21 = 7;
        r20[r21] = r15;
        r0 = r20;
        r8 = com.abaltatech.mcp.weblink.utils.grafika.GlUtil.createFloatBuffer(r0);
    L_0x00c9:
        r21 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;	 Catch:{ Exception -> 0x0206 }
        r0 = r33;	 Catch:{ Exception -> 0x0206 }
        r1 = r21;	 Catch:{ Exception -> 0x0206 }
        r0.attachToGLContext(r1);	 Catch:{ Exception -> 0x0206 }
        r11 = 1;	 Catch:{ Exception -> 0x0206 }
        r0 = r33;	 Catch:{ Exception -> 0x0206 }
        r0.updateTexImage();	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_width;	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r0 = r0.m_height;	 Catch:{ Exception -> 0x0206 }
        r16 = r0;	 Catch:{ Exception -> 0x0206 }
        r21 = 0;	 Catch:{ Exception -> 0x0206 }
        r22 = 0;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        r2 = r16;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glViewport(r0, r1, r12, r2);	 Catch:{ Exception -> 0x0206 }
        r21 = 3042; // 0xbe2 float:4.263E-42 double:1.503E-320;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glEnable(r0);	 Catch:{ Exception -> 0x0206 }
        r21 = 770; // 0x302 float:1.079E-42 double:3.804E-321;	 Catch:{ Exception -> 0x0206 }
        r22 = 771; // 0x303 float:1.08E-42 double:3.81E-321;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glBlendFunc(r0, r1);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_program;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glUseProgram(r12);	 Catch:{ Exception -> 0x0206 }
        r23 = "glUseProgram";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r21 = 33984; // 0x84c0 float:4.7622E-41 double:1.67903E-319;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glActiveTexture(r0);	 Catch:{ Exception -> 0x0206 }
        r21 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x0206 }
        r22 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glBindTexture(r0, r1);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_mvpMatrixLoc;	 Catch:{ Exception -> 0x0206 }
        r21 = 1;	 Catch:{ Exception -> 0x0206 }
        r22 = 0;	 Catch:{ Exception -> 0x0206 }
        r24 = 0;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        r2 = r24;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glUniformMatrix4fv(r12, r0, r1, r6, r2);	 Catch:{ Exception -> 0x0206 }
        r23 = "glUniformMatrix4fv";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_texMatrixLoc;	 Catch:{ Exception -> 0x0206 }
        r21 = 1;	 Catch:{ Exception -> 0x0206 }
        r22 = 0;	 Catch:{ Exception -> 0x0206 }
        r24 = 0;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        r2 = r24;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glUniformMatrix4fv(r12, r0, r1, r7, r2);	 Catch:{ Exception -> 0x0206 }
        r23 = "glUniformMatrix4fv";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_positionLoc;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glEnableVertexAttribArray(r12);	 Catch:{ Exception -> 0x0206 }
        r23 = "glEnableVertexAttribArray";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_positionLoc;	 Catch:{ Exception -> 0x0206 }
        r21 = 2;	 Catch:{ Exception -> 0x0206 }
        r22 = 5126; // 0x1406 float:7.183E-42 double:2.5326E-320;	 Catch:{ Exception -> 0x0206 }
        r24 = 0;	 Catch:{ Exception -> 0x0206 }
        r25 = 8;	 Catch:{ Exception -> 0x0206 }
        r0 = r12;	 Catch:{ Exception -> 0x0206 }
        r1 = r21;	 Catch:{ Exception -> 0x0206 }
        r2 = r22;	 Catch:{ Exception -> 0x0206 }
        r3 = r24;	 Catch:{ Exception -> 0x0206 }
        r4 = r25;	 Catch:{ Exception -> 0x0206 }
        r5 = r8;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glVertexAttribPointer(r0, r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0206 }
        r23 = "glVertexAttribPointer";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_textureCoordLoc;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glEnableVertexAttribArray(r12);	 Catch:{ Exception -> 0x0206 }
        r23 = "glEnableVertexAttribArray";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_textureCoordLoc;	 Catch:{ Exception -> 0x0206 }
        r21 = 2;	 Catch:{ Exception -> 0x0206 }
        r22 = 5126; // 0x1406 float:7.183E-42 double:2.5326E-320;	 Catch:{ Exception -> 0x0206 }
        r24 = 0;	 Catch:{ Exception -> 0x0206 }
        r25 = 8;	 Catch:{ Exception -> 0x0206 }
        r0 = r12;	 Catch:{ Exception -> 0x0206 }
        r1 = r21;	 Catch:{ Exception -> 0x0206 }
        r2 = r22;	 Catch:{ Exception -> 0x0206 }
        r3 = r24;	 Catch:{ Exception -> 0x0206 }
        r4 = r25;	 Catch:{ Exception -> 0x0206 }
        r5 = r10;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glVertexAttribPointer(r0, r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0206 }
        r23 = "glVertexAttribPointer";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r21 = 5;	 Catch:{ Exception -> 0x0206 }
        r22 = 0;	 Catch:{ Exception -> 0x0206 }
        r24 = 4;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        r2 = r24;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glDrawArrays(r0, r1, r2);	 Catch:{ Exception -> 0x0206 }
        r23 = "glDrawArrays";	 Catch:{ Exception -> 0x0206 }
        r0 = r23;	 Catch:{ Exception -> 0x0206 }
        com.abaltatech.mcp.weblink.utils.grafika.GlUtil.checkGlError(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_positionLoc;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glDisableVertexAttribArray(r12);	 Catch:{ Exception -> 0x0206 }
        r0 = r32;	 Catch:{ Exception -> 0x0206 }
        r12 = r0.m_textureCoordLoc;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glDisableVertexAttribArray(r12);	 Catch:{ Exception -> 0x0206 }
        r21 = 36197; // 0x8d65 float:5.0723E-41 double:1.78837E-319;	 Catch:{ Exception -> 0x0206 }
        r22 = 0;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        r1 = r22;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glBindTexture(r0, r1);	 Catch:{ Exception -> 0x0206 }
        r21 = 0;	 Catch:{ Exception -> 0x0206 }
        r0 = r21;	 Catch:{ Exception -> 0x0206 }
        android.opengl.GLES20.glUseProgram(r0);	 Catch:{ Exception -> 0x0206 }
        r0 = r33;	 Catch:{ Throwable -> 0x01f7 }
        r0.detachFromGLContext();	 Catch:{ Throwable -> 0x01f7 }
        return;
        goto L_0x01f4;
    L_0x01f1:
        goto L_0x000e;
    L_0x01f4:
        r10 = BUF_TEX_COORDS_SURFACE;
        goto L_0x01f1;
    L_0x01f7:
        r26 = move-exception;
        r23 = "WLTextureBlit";
        r27 = "ERROR detaching eGL context in drawTexture";
        r0 = r23;
        r1 = r27;
        r2 = r26;
        android.util.Log.w(r0, r1, r2);
        return;
    L_0x0206:
        r28 = move-exception;
        r23 = "WLTextureBlit";	 Catch:{ Throwable -> 0x022b }
        r27 = "Draw texture";	 Catch:{ Throwable -> 0x022b }
        r0 = r23;	 Catch:{ Throwable -> 0x022b }
        r1 = r27;	 Catch:{ Throwable -> 0x022b }
        r2 = r28;	 Catch:{ Throwable -> 0x022b }
        android.util.Log.e(r0, r1, r2);	 Catch:{ Throwable -> 0x022b }
        if (r11 == 0) goto L_0x0243;
    L_0x0216:
        r0 = r33;	 Catch:{ Throwable -> 0x021c }
        r0.detachFromGLContext();	 Catch:{ Throwable -> 0x021c }
        return;
    L_0x021c:
        r29 = move-exception;
        r23 = "WLTextureBlit";
        r27 = "ERROR detaching eGL context in drawTexture";
        r0 = r23;
        r1 = r27;
        r2 = r29;
        android.util.Log.w(r0, r1, r2);
        return;
    L_0x022b:
        r30 = move-exception;
        if (r11 == 0) goto L_0x0233;
    L_0x022e:
        r0 = r33;	 Catch:{ Throwable -> 0x0234 }
        r0.detachFromGLContext();	 Catch:{ Throwable -> 0x0234 }
    L_0x0233:
        throw r30;
    L_0x0234:
        r31 = move-exception;
        r23 = "WLTextureBlit";
        r27 = "ERROR detaching eGL context in drawTexture";
        r0 = r23;
        r1 = r27;
        r2 = r31;
        android.util.Log.w(r0, r1, r2);
        goto L_0x0233;
    L_0x0243:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.utils.WLTextureBlit.drawTexture(android.graphics.SurfaceTexture, android.graphics.Rect):void");
    }

    public WLTextureBlit(Surface $r1, int $i0, int $i1) throws Exception {
        if ($r1 == null) {
            try {
                throw new IllegalArgumentException("Invalid input surface specified");
            } catch (Exception $r2) {
                throw $r2;
            }
        }
        this.m_egl = new EglCore(null, 1);
        this.m_encoderSurface = this.m_egl.createWindowSurface($r1);
        initGL($i0, $i1);
    }

    public WLTextureBlit(ByteBuffer $r1, int $i0, int $i1) throws Exception {
        if ($r1 == null) {
            try {
                throw new IllegalArgumentException("Invalid surface buffer specified");
            } catch (Exception $r2) {
                throw $r2;
            }
        } else if ($r1.capacity() < ($i0 * $i1) * 4) {
            throw new IllegalArgumentException("The surface buffer is not large enough");
        } else {
            this.m_egl = new EglCore(null, 1);
            this.m_encoderSurface = this.m_egl.createOffscreenSurface($i0, $i1);
            this.m_surfaceBuffer = $r1;
            this.m_surfaceBuffer.order(ByteOrder.LITTLE_ENDIAN);
            initGL($i0, $i1);
        }
    }

    public void release() throws  {
        terminateGL();
    }

    public boolean beginDraw() throws  {
        if (this.m_egl == null) {
            return false;
        }
        if (this.m_encoderSurface == null) {
            return false;
        }
        try {
            this.m_egl.makeCurrent(this.m_encoderSurface);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            return true;
        } catch (Exception $r1) {
            Log.e(TAG, "Error locking H264 frame for drawing", $r1);
            return false;
        }
    }

    public synchronized void endDraw() throws  {
        if (!(this.m_egl == null || this.m_encoderSurface == null)) {
            this.m_egl.swapBuffers(this.m_encoderSurface);
            if (this.m_surfaceBuffer != null) {
                GLES20.glFinish();
                GLES20.glReadPixels(0, 0, this.m_width, this.m_height, 6408, 5121, this.m_surfaceBuffer);
            }
            this.m_egl.makeNothingCurrent();
        }
    }

    protected boolean initGL(int $i0, int $i1) throws  {
        this.m_width = $i0;
        this.m_height = $i1;
        try {
            this.m_egl.makeCurrent(this.m_encoderSurface);
            this.m_program = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT);
            this.m_positionLoc = GLES20.glGetAttribLocation(this.m_program, "aPosition");
            GlUtil.checkLocation(this.m_positionLoc, "aPosition");
            this.m_textureCoordLoc = GLES20.glGetAttribLocation(this.m_program, "aTextureCoord");
            GlUtil.checkLocation(this.m_textureCoordLoc, "aTextureCoord");
            this.m_mvpMatrixLoc = GLES20.glGetUniformLocation(this.m_program, "uMVPMatrix");
            GlUtil.checkLocation(this.m_mvpMatrixLoc, "uMVPMatrix");
            this.m_texMatrixLoc = GLES20.glGetUniformLocation(this.m_program, "uTexMatrix");
            GlUtil.checkLocation(this.m_texMatrixLoc, "uTexMatrix");
            this.m_egl.makeNothingCurrent();
            return true;
        } catch (Exception $r1) {
            Log.e(TAG, "Error initializing GL", $r1);
            return false;
        }
    }

    protected void terminateGL() throws  {
        if (this.m_egl != null) {
            this.m_egl.makeNothingCurrent();
            if (this.m_encoderSurface != null) {
                this.m_egl.releaseSurface(this.m_encoderSurface);
                this.m_encoderSurface = null;
            }
            GLES20.glDeleteProgram(this.m_program);
            this.m_egl.release();
            this.m_program = -1;
            this.m_egl = null;
            this.m_surfaceBuffer = null;
            this.m_positionLoc = 0;
            this.m_textureCoordLoc = 0;
            this.m_mvpMatrixLoc = 0;
            this.m_texMatrixLoc = 0;
            this.m_width = 0;
            this.m_height = 0;
        }
    }
}
