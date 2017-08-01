package com.abaltatech.weblinkserver;

import com.abaltatech.weblink.core.frameencoding.EncoderUtils;
import com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler;
import com.abaltatech.weblink.core.frameencoding.IFrameEncoder;
import java.nio.ByteBuffer;
import java.util.Map;

public class FrameEncoderXOR implements IFrameEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = (!FrameEncoderXOR.class.desiredAssertionStatus());
    private static final int DEF_KEY_FRAME_INTERVAL = 30;
    private int m_encHeight = 0;
    private int m_encWidth = 0;
    private IFrameEncodedHandler m_handler = null;
    private int m_keyFrameCounter = 0;
    private int m_keyFrameInterval = 0;
    private ByteBuffer m_outBuffer = null;
    private ByteBuffer m_prevFrame = null;
    private int m_srcHeight = 0;
    private int m_srcWidth = 0;
    private boolean m_suspended = false;

    private static native int encodeFrame(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, boolean z, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3) throws ;

    public int getPriority() throws  {
        return 9;
    }

    public int getType() throws  {
        return 4;
    }

    public synchronized boolean startEncoding(int $i0, int $i1, int $i2, int $i3, String $r1, IFrameEncodedHandler $r2) throws  {
        boolean $z0 = true;
        synchronized (this) {
            Map $r3 = EncoderUtils.parseEncoderParams($r1);
            this.m_srcWidth = $i0;
            this.m_srcHeight = $i1;
            this.m_encWidth = $i2;
            this.m_encHeight = $i3;
            this.m_handler = $r2;
            this.m_suspended = false;
            this.m_keyFrameInterval = EncoderUtils.getParam($r3, "maxKeyFrameInterval", 30);
            this.m_keyFrameInterval = Math.max(1, this.m_keyFrameInterval);
            this.m_keyFrameCounter = 0;
            if (this.m_handler != null) {
                this.m_outBuffer = ByteBuffer.allocateDirect(((($i2 * $i3) * 3) / 2) + 4096);
                this.m_prevFrame = ByteBuffer.allocateDirect(((($i2 * $i3) * 3) / 2) + 4096);
            }
            if (this.m_handler == null || this.m_outBuffer == null || this.m_prevFrame == null) {
                $z0 = false;
            }
        }
        return $z0;
    }

    public synchronized void stopEncoding() throws  {
        this.m_srcWidth = 0;
        this.m_srcHeight = 0;
        this.m_encWidth = 0;
        this.m_encHeight = 0;
        this.m_keyFrameCounter = 0;
        this.m_keyFrameInterval = 0;
        this.m_handler = null;
        this.m_outBuffer = null;
        this.m_prevFrame = null;
        this.m_suspended = false;
    }

    public synchronized boolean encodeImage(int $i0, ByteBuffer $r1, boolean $z0) throws  {
        boolean $z2;
        boolean $z1 = true;
        synchronized (this) {
            $z2 = false;
            if ($assertionsDisabled || $r1.isDirect()) {
                if (!this.m_suspended && $r1.isDirect() && this.m_handler != null && ($i0 == 4 || $i0 == 5 || $i0 == 6)) {
                    $i0 = this.m_srcWidth;
                    int $i1 = this.m_srcHeight;
                    int $i2 = this.m_encWidth;
                    int $i3 = this.m_encHeight;
                    $z0 = $z0 || this.m_keyFrameCounter == 0;
                    $i0 = encodeFrame($r1, $i0, $i1, $i2, $i3, $z0, this.m_prevFrame, this.m_outBuffer);
                    if ($i0 > 0) {
                        if (this.m_outBuffer.get(0) != (byte) 1) {
                            $z1 = false;
                        }
                        if ($z1) {
                            this.m_keyFrameCounter = this.m_keyFrameInterval - 1;
                        } else {
                            $i1 = this.m_keyFrameCounter;
                            int $i22 = this.m_keyFrameInterval;
                            $i1 = ($i1 + $i22) - 1;
                            $i22 = this.m_keyFrameInterval;
                            this.m_keyFrameCounter = $i1 % $i22;
                        }
                        this.m_outBuffer.position(0);
                        ByteBuffer byteBuffer = this.m_outBuffer;
                        $r1 = byteBuffer;
                        byteBuffer.limit($i0);
                        this.m_handler.onFrameEncoded(this.m_encWidth, this.m_encHeight, 4, this.m_outBuffer);
                        $z2 = true;
                    }
                }
            } else {
                throw new AssertionError();
            }
        }
        return $z2;
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
