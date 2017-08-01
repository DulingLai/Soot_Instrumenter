package com.abaltatech.mcs.usb;

import com.abaltatech.mcs.common.IMCSDataStats;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;
import com.waze.analytics.AnalyticsEvents;

public class AOALayer extends FIOStreamLayer {
    private static final short CMD_DATA = (short) 3;
    private static final short CMD_ECHO = (short) 1;
    private static final short CMD_ECHO_RESPONSE = (short) 2;
    private static final byte[] MAGIC_BYTES = new byte[]{(byte) -1, (byte) 0, (byte) -86, (byte) 102};
    private int m_availableData = 0;
    private final byte[] m_echoResponseBuf = new byte[MemoryPool.BufferSizeBig];
    private final byte[] m_header = new byte[10];
    private int m_lastPacketIndex = 0;
    private final byte[] m_readData = new byte[MemoryPool.BufferSizeBig];
    private int m_readPos = 0;

    public AOALayer() throws  {
        super(MAGIC_BYTES, 10, 4, 4);
    }

    public int readData(byte[] $r1, int $i0) throws  {
        int $i1 = 0;
        IMCSDataStats $r2 = getDataStats();
        synchronized (this) {
            if (this.m_availableData > this.m_readPos) {
                $i1 = this.m_availableData - this.m_readPos;
                if ($i1 > $i0) {
                    $i1 = $i0;
                }
                System.arraycopy(this.m_readData, this.m_readPos, $r1, 0, $i1);
                if ($r2 != null) {
                    $r2.onDataReceived($i1);
                }
                this.m_readPos += $i1;
            }
        }
        return $i1;
    }

    protected void onDataReceived(byte[] $r1, int $i0) throws  {
        if ($i0 != this.m_headerLength + this.m_dataLength) {
            throw new IllegalArgumentException("Invaid size received");
        }
        $i0 = (($r1[8] & (short) 255) << 8) | ($r1[9] & (short) 255);
        switch ($i0) {
            case 1:
                onEchoRequested($r1);
                return;
            case 2:
                onEchoResponseReceived($r1);
                break;
            case 3:
                onDataReceived($r1);
                return;
            default:
                break;
        }
        MCSLogger.log("ERROR", "Unsupported AOA command: " + $i0);
    }

    private void onEchoResponseReceived(byte[] readBuffer) throws  {
        MCSLogger.log(AnalyticsEvents.ANALYTICS_EVENT_VALUE_DEBUG, "Received echo response with size " + this.m_dataLength);
    }

    private void onEchoRequested(byte[] $r1) throws  {
        if (this.m_dataLength > this.m_echoResponseBuf.length) {
            MCSLogger.log("ERROR", "Too big echo request! Size is " + this.m_dataLength);
            this.m_dataLength = this.m_echoResponseBuf.length;
        }
        if (this.m_dataLength > 0) {
            System.arraycopy($r1, this.m_headerLength, this.m_echoResponseBuf, 0, this.m_dataLength);
        }
        MCSLogger.log("===> AOA Layer", "Sending ECHO response");
        prepareHeader(this.m_echoResponseBuf, this.m_dataLength, (short) 2);
        writeInternal(this.m_header, this.m_echoResponseBuf, this.m_dataLength);
    }

    private synchronized void onDataReceived(byte[] $r1) throws  {
        if (this.m_dataLength > 0 && this.m_dataLength <= this.m_readData.length) {
            synchronized (this) {
                System.arraycopy($r1, this.m_headerLength, this.m_readData, 0, this.m_dataLength);
                this.m_availableData = this.m_dataLength;
                this.m_readPos = 0;
            }
            notifyForData();
        }
    }

    protected byte[] prepareDataHeader(byte[] $r1, int $i0) throws  {
        return prepareHeader($r1, $i0, (short) 3);
    }

    private byte[] prepareHeader(byte[] buffer, int $i2, short $s3) throws  {
        if ($i2 > 268435455) {
            throw new IllegalArgumentException("Too big packet to be sent, size: " + $i2);
        } else if ($i2 < 0) {
            throw new IllegalArgumentException("Negative packet size: " + $i2);
        } else {
            int $i4;
            for ($i4 = 0; $i4 < MAGIC_BYTES.length; $i4++) {
                this.m_header[$i4] = MAGIC_BYTES[$i4];
            }
            for ($i4 = 0; $i4 < 4; $i4++) {
                this.m_header[(this.m_dataLengthFieldOffest + 3) - $i4] = (byte) ($i2 & 255);
                $i2 >>= 8;
            }
            this.m_header[(this.m_dataLengthFieldOffest + this.m_dataLengthFieldSize) + 1] = (byte) ($s3 & (short) 255);
            $s3 = (short) ($s3 >> (short) 8);
            this.m_header[this.m_dataLengthFieldOffest + this.m_dataLengthFieldSize] = (byte) ($s3 & (short) 255);
            if ((short) 3 == $s3) {
                this.m_lastPacketIndex++;
                if (this.m_lastPacketIndex > 255) {
                    this.m_lastPacketIndex = 0;
                }
            }
            return this.m_header;
        }
    }

    protected boolean isDataAvailable() throws  {
        boolean $z0;
        synchronized (this) {
            $z0 = this.m_availableData > this.m_readPos;
        }
        return $z0;
    }
}
