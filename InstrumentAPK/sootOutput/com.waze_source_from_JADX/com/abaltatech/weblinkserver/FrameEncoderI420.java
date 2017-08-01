package com.abaltatech.weblinkserver;

import com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler;
import com.abaltatech.weblink.core.frameencoding.IFrameEncoder;
import java.nio.ByteBuffer;

public class FrameEncoderI420 implements IFrameEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = (!FrameEncoderI420.class.desiredAssertionStatus());
    private int m_encHeight = 0;
    private int m_encWidth = 0;
    private IFrameEncodedHandler m_handler = null;
    private ByteBuffer m_outBuffer = null;
    private int m_srcHeight = 0;
    private int m_srcWidth = 0;
    private boolean m_suspended = false;

    private static native int encodeFrame(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, ByteBuffer byteBuffer2) throws ;

    public int getPriority() throws  {
        return 10;
    }

    public int getType() throws  {
        return 1;
    }

    public synchronized boolean startEncoding(int $i0, int $i1, int $i2, int $i3, String encParams, IFrameEncodedHandler $r2) throws  {
        boolean $z0 = false;
        synchronized (this) {
            this.m_srcWidth = $i0;
            this.m_srcHeight = $i1;
            this.m_encWidth = $i2;
            this.m_encHeight = $i3;
            this.m_handler = $r2;
            this.m_suspended = false;
            if (this.m_handler != null) {
                this.m_outBuffer = ByteBuffer.allocateDirect((($i2 * $i3) * 4) + 4096);
            }
            if (!(this.m_handler == null || this.m_outBuffer == null)) {
                $z0 = true;
            }
        }
        return $z0;
    }

    public synchronized void stopEncoding() throws  {
        this.m_encHeight = 0;
        this.m_encWidth = 0;
        this.m_srcHeight = 0;
        this.m_srcWidth = 0;
        this.m_handler = null;
        this.m_outBuffer = null;
        this.m_suspended = false;
    }

    public synchronized boolean encodeImage(int $i0, ByteBuffer $r1, boolean isKeyFrame) throws  {
        isKeyFrame = false;
        if (!$assertionsDisabled && !$r1.isDirect()) {
            throw new AssertionError();
        } else if (!this.m_suspended && $r1.isDirect() && this.m_handler != null && ($i0 == 4 || $i0 == 5 || $i0 == 6)) {
            $i0 = encodeFrame($r1, this.m_srcWidth, this.m_srcHeight, this.m_encWidth, this.m_encHeight, this.m_outBuffer);
            if ($i0 > 0) {
                this.m_outBuffer.position(0);
                ByteBuffer byteBuffer = this.m_outBuffer;
                $r1 = byteBuffer;
                byteBuffer.limit($i0);
                this.m_handler.onFrameEncoded(this.m_encWidth, this.m_encHeight, 1, this.m_outBuffer);
                isKeyFrame = true;
            }
        }
        return isKeyFrame;
    }

    public boolean suspend() throws  {
        if (this.m_suspended) {
            return false;
        }
        this.m_suspended = true;
        return true;
    }

    public boolean resume() throws  {
        if (!this.m_suspended) {
            return false;
        }
        this.m_suspended = false;
        return true;
    }

    public boolean isSuspended() throws  {
        return this.m_suspended;
    }
}
