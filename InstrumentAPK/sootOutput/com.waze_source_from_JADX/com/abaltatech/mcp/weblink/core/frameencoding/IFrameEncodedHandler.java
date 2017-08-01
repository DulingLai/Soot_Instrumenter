package com.abaltatech.mcp.weblink.core.frameencoding;

import java.nio.ByteBuffer;

public interface IFrameEncodedHandler {
    void onFrameEncoded(int i, int i2, int i3, ByteBuffer byteBuffer) throws ;
}
