package com.abaltatech.weblinkserver;

import android.view.Surface;
import com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler;

public interface IFrameEncoderSurface {
    boolean encodeSurface() throws ;

    Surface startEncodingWithSurface(int i, int i2, int i3, int i4, String str, IFrameEncodedHandler iFrameEncodedHandler) throws ;
}
