package com.abaltatech.mcp.weblink.utils;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

public class WLImageUtils {
    public static native int copyBitmap(Bitmap bitmap, ByteBuffer byteBuffer) throws ;

    public static native void copyImage(ByteBuffer byteBuffer, int i, int i2, int i3, ByteBuffer byteBuffer2, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) throws ;

    public static native boolean glReadPixels(Bitmap bitmap, boolean z) throws ;

    static {
        System.loadLibrary("WLUtils");
    }
}
