package com.abaltatech.weblink.core.frameencoding;

import java.nio.ByteBuffer;

public interface IFrameEncoder {
    boolean encodeImage(int i, ByteBuffer byteBuffer, boolean z) throws ;

    int getPriority() throws ;

    int getType() throws ;

    boolean isSuspended() throws ;

    boolean resume() throws ;

    boolean startEncoding(int i, int i2, int i3, int i4, String str, IFrameEncodedHandler iFrameEncodedHandler) throws ;

    void stopEncoding() throws ;

    boolean suspend() throws ;
}
