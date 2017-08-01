package com.abaltatech.mcp.weblink.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import com.google.android.gms.gcm.Task;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

@TargetApi(16)
public class WLSurface implements OnFrameAvailableListener {
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

    public void setOnFrameAvailableListener(OnFrameAvailableListener $r1) throws  {
        this.mListener = $r1;
    }

    public static WLSurface create(int $i0, int $i1) throws  {
        WLSurface $r0 = new WLSurface();
        if ($r0.createSurface($i0, $i1)) {
            return $r0;
        }
        $r0.destroySurface();
        return null;
    }

    public synchronized boolean createSurface(int $i0, int $i1) throws  {
        boolean $z0;
        $z0 = true;
        if (!(this.m_width == $i0 && this.m_height == $i1)) {
            destroySurface();
        }
        if (this.m_surface == null && VERSION.SDK_INT >= 16) {
            $z0 = false;
            try {
                Log.d(TAG, "createSurface() " + $i0 + "," + $i1);
                this.mEgl = (EGL10) EGLContext.getEGL();
                this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
                if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                    throw new RuntimeException("eglGetDisplay failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
                }
                int[] $r2 = new int[2];
                EGL10 $r8 = this.mEgl;
                EGLDisplay eGLDisplay = this.mEglDisplay;
                EGLDisplay $r10 = eGLDisplay;
                if ($r8.eglInitialize(eGLDisplay, $r2)) {
                    $r2 = new int[1];
                    EGLConfig[] $r1 = new EGLConfig[1];
                    int[] $r14 = getConfig();
                    if (this.mEgl.eglChooseConfig(this.mEglDisplay, $r14, $r1, 1, $r2)) {
                        if ($r2[0] > 0) {
                            this.mEglConfig = $r1[0];
                        } else {
                            this.mEglConfig = null;
                        }
                        if (this.mEglConfig == null) {
                            throw new RuntimeException("eglConfig not initialized");
                        }
                        this.mEglContext = createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
                        $r2 = new int[]{12375, $i0, 12374, $i1, 12344};
                        this.mEglSurface = this.mEgl.eglCreatePbufferSurface(this.mEglDisplay, this.mEglConfig, $r2);
                        if (this.mEglSurface != null) {
                            if (this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                                if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                                    $r2 = new int[1];
                                    GLES20.glGenTextures(1, $r2, 0);
                                    int $i2 = $r2[0];
                                    GLES20.glBindTexture(36197, $i2);
                                    GLES20.glTexParameterf(36197, 10241, 9728.0f);
                                    GLES20.glTexParameterf(36197, Task.EXTRAS_LIMIT_BYTES, 9729.0f);
                                    GLES20.glTexParameteri(36197, 10242, 33071);
                                    GLES20.glTexParameteri(36197, 10243, 33071);
                                    this.mTriangleVertices = ByteBuffer.allocateDirect(TRIANGLE_VERTICES_DATA.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                                    this.mTriangleVertices.put(TRIANGLE_VERTICES_DATA).position(0);
                                    Matrix.setIdentityM(this.mSTMatrix, 0);
                                    this.mProgram = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
                                    if (this.mProgram == 0) {
                                        throw new RuntimeException("failed creating program");
                                    }
                                    this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
                                    checkGlError("glGetAttribLocation aPosition");
                                    if (this.maPositionHandle == -1) {
                                        throw new RuntimeException("Could not get attrib location for aPosition");
                                    }
                                    this.maTextureHandle = GLES20.glGetAttribLocation(this.mProgram, "aTextureCoord");
                                    checkGlError("glGetAttribLocation aTextureCoord");
                                    if (this.maTextureHandle == -1) {
                                        throw new RuntimeException("Could not get attrib location for aTextureCoord");
                                    }
                                    this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
                                    checkGlError("glGetUniformLocation uMVPMatrix");
                                    if (this.muMVPMatrixHandle == -1) {
                                        throw new RuntimeException("Could not get attrib location for uMVPMatrix");
                                    }
                                    this.muSTMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
                                    checkGlError("glGetUniformLocation uSTMatrix");
                                    if (this.muSTMatrixHandle == -1) {
                                        throw new RuntimeException("Could not get attrib location for uSTMatrix");
                                    }
                                    this.m_texture = new SurfaceTexture($i2);
                                    SurfaceTexture surfaceTexture = this.m_texture;
                                    SurfaceTexture $r24 = surfaceTexture;
                                    surfaceTexture.setDefaultBufferSize($i0, $i1);
                                    surfaceTexture = this.m_texture;
                                    surfaceTexture.detachFromGLContext();
                                    this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                                    this.m_width = $i0;
                                    this.m_height = $i1;
                                    this.m_backBuffer = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
                                    surfaceTexture = this.m_texture;
                                    this.m_surface = new Surface(surfaceTexture);
                                    surfaceTexture = this.m_texture;
                                    $r24 = surfaceTexture;
                                    surfaceTexture.setOnFrameAvailableListener(this);
                                    $z0 = true;
                                } else {
                                    throw new RuntimeException("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
                                }
                            }
                        }
                        throw new RuntimeException("eglCreatePbufferSurface failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
                    }
                    throw new IllegalArgumentException("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
                }
                throw new RuntimeException("eglInitialize failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
            } catch (Throwable $r3) {
                Log.e(TAG, "createSurface", $r3);
                destroySurface();
            }
        }
        return $z0;
    }

    public synchronized void destroySurface() throws  {
        Log.d(TAG, "destroySurface() ");
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
                Log.e(TAG, "Error saving image", $r2);
            }
        }
        return false;
    }

    public synchronized void onFrameAvailable(SurfaceTexture st) throws  {
        boolean $z0 = false;
        if (this.mEgl == null || this.mEglDisplay == null || this.mEglSurface == null || this.mEglContext == null || this.m_texture == null) {
            Log.w(TAG, "onFrameAvailable() mEgl*/surface has null value");
        } else if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
            SurfaceTexture $r1;
            try {
                this.m_texture.attachToGLContext(1001);
                $z0 = true;
                $r1 = this.m_texture;
                $r1.updateTexImage();
                GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
                int $i0 = this.mProgram;
                GLES20.glUseProgram($i0);
                checkGlError("glUseProgram");
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(36197, 1001);
                this.mTriangleVertices.position(0);
                GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 20, this.mTriangleVertices);
                checkGlError("glVertexAttribPointer maPosition");
                $i0 = this.maPositionHandle;
                GLES20.glEnableVertexAttribArray($i0);
                checkGlError("glEnableVertexAttribArray maPositionHandle");
                this.mTriangleVertices.position(3);
                GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, 20, this.mTriangleVertices);
                checkGlError("glVertexAttribPointer maTextureHandle");
                $i0 = this.maTextureHandle;
                GLES20.glEnableVertexAttribArray($i0);
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
            } catch (Throwable $r15) {
                Log.w(TAG, "ERROR detaching eGL context in onFrameAvailable", $r15);
            }
            if (this.mListener != null) {
                this.mListener.onFrameAvailable(null);
            }
        } else {
            throw new RuntimeException("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.mEgl.eglGetError()));
        }
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
        Log.e(TAG, "Could not compile shader " + $i0 + ":");
        Log.e(TAG, " " + GLES20.glGetShaderInfoLog($i1));
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
            Log.e(TAG, "Could not create program");
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
        Log.e(TAG, "Could not link program: ");
        Log.e(TAG, GLES20.glGetProgramInfoLog($i2));
        GLES20.glDeleteProgram($i2);
        return 0;
    }

    private void checkGlError(String $r1) throws  {
        int $i0 = GLES20.glGetError();
        if ($i0 != 0) {
            Log.e(TAG, $r1 + ": glError " + $i0);
            throw new RuntimeException($r1 + ": glError " + $i0);
        }
    }

    private EGLContext createContext(EGL10 $r1, EGLDisplay $r2, EGLConfig $r3) throws  {
        return $r1.eglCreateContext($r2, $r3, EGL10.EGL_NO_CONTEXT, new int[]{EGL_CONTEXT_CLIENT_VERSION, 2, 12344});
    }
}
