package com.abaltatech.mcp.weblink.utils.grafika;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import com.google.android.gms.gcm.Task;
import com.waze.strings.DisplayStrings;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GlUtil {
    public static final float[] IDENTITY_MATRIX = new float[16];
    private static final int SIZEOF_FLOAT = 4;
    public static final String TAG = "Grafika";

    static {
        Matrix.setIdentityM(IDENTITY_MATRIX, 0);
    }

    private GlUtil() throws  {
    }

    public static int createProgram(String $r0, String $r1) throws  {
        int $i0 = loadShader(35633, $r0);
        if ($i0 == 0) {
            return 0;
        }
        int $i1 = loadShader(35632, $r1);
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
        int[] $r2 = new int[1];
        GLES20.glGetProgramiv($i2, 35714, $r2, 0);
        if ($r2[0] == 1) {
            return $i2;
        }
        Log.e(TAG, "Could not link program: ");
        Log.e(TAG, GLES20.glGetProgramInfoLog($i2));
        GLES20.glDeleteProgram($i2);
        return 0;
    }

    public static int loadShader(int $i0, String $r0) throws  {
        int $i1 = GLES20.glCreateShader($i0);
        checkGlError("glCreateShader type=" + $i0);
        GLES20.glShaderSource($i1, $r0);
        GLES20.glCompileShader($i1);
        int[] $r1 = new int[1];
        GLES20.glGetShaderiv($i1, 35713, $r1, 0);
        if ($r1[0] != 0) {
            return $i1;
        }
        Log.e(TAG, "Could not compile shader " + $i0 + ":");
        Log.e(TAG, " " + GLES20.glGetShaderInfoLog($i1));
        GLES20.glDeleteShader($i1);
        return 0;
    }

    public static void checkGlError(String $r0) throws  {
        int $i0 = GLES20.glGetError();
        if ($i0 != 0) {
            $r0 = $r0 + ": glError 0x" + Integer.toHexString($i0);
            Log.e(TAG, $r0);
            throw new RuntimeException($r0);
        }
    }

    public static void checkLocation(int $i0, String $r0) throws  {
        if ($i0 < 0) {
            throw new RuntimeException("Unable to locate '" + $r0 + "' in program");
        }
    }

    public static int createImageTexture(ByteBuffer $r0, int $i0, int $i1, int $i2) throws  {
        int[] $r1 = new int[1];
        GLES20.glGenTextures(1, $r1, 0);
        int $i3 = $r1[0];
        checkGlError("glGenTextures");
        GLES20.glBindTexture(DisplayStrings.DS_OFFER_RIDE_SELECT_FROM_TITLE, $i3);
        GLES20.glTexParameteri(DisplayStrings.DS_OFFER_RIDE_SELECT_FROM_TITLE, 10241, 9729);
        GLES20.glTexParameteri(DisplayStrings.DS_OFFER_RIDE_SELECT_FROM_TITLE, Task.EXTRAS_LIMIT_BYTES, 9729);
        checkGlError("loadImageTexture");
        GLES20.glTexImage2D(DisplayStrings.DS_OFFER_RIDE_SELECT_FROM_TITLE, 0, $i2, $i0, $i1, 0, $i2, 5121, $r0);
        checkGlError("loadImageTexture");
        return $i3;
    }

    public static FloatBuffer createFloatBuffer(float[] $r0) throws  {
        ByteBuffer $r1 = ByteBuffer.allocateDirect($r0.length * 4);
        $r1.order(ByteOrder.nativeOrder());
        FloatBuffer $r3 = $r1.asFloatBuffer();
        $r3.put($r0);
        $r3.position(0);
        return $r3;
    }

    public static void logVersionInfo() throws  {
        Log.i(TAG, "vendor  : " + GLES20.glGetString(7936));
        Log.i(TAG, "renderer: " + GLES20.glGetString(7937));
        Log.i(TAG, "version : " + GLES20.glGetString(7938));
    }
}
