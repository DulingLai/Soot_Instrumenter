package com.abaltatech.weblinkserver;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

class WLImageUtils {
    public static native void convertARGBtoABGR(ByteBuffer byteBuffer, int i, int i2) throws ;

    public static native void copyImage(ByteBuffer byteBuffer, int i, int i2, int i3, ByteBuffer byteBuffer2, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) throws ;

    public static native boolean glReadPixels(Bitmap bitmap, boolean z) throws ;

    public static native void mirrorImage(ByteBuffer byteBuffer, int i, int i2) throws ;

    WLImageUtils() throws  {
    }
}
