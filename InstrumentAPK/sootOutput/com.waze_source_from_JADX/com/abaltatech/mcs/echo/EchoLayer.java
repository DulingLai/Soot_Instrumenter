package com.abaltatech.mcs.echo;

import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;

public class EchoLayer implements IMCSDataLayerNotification {
    private byte[] m_buffer = MemoryPool.getMem(MemoryPool.BufferSizeBig, "EchoLayer");
    private IMCSDataLayer m_dataLayer;
    private boolean m_isStopped = false;
    private final int m_size = this.m_buffer.length;

    public synchronized void attachToLayer(IMCSDataLayer $r1) throws  {
        if (this.m_dataLayer != null) {
            this.m_dataLayer.unRegisterNotification(this);
            this.m_dataLayer = null;
        }
        this.m_dataLayer = $r1;
        if (this.m_dataLayer != null) {
            this.m_dataLayer.registerNotification(this);
        }
    }

    public synchronized int readData(byte[] $r1, int $i0) throws  {
        if (this.m_isStopped) {
            $i0 = -1;
        } else {
            $i0 = this.m_dataLayer.readData($r1, $i0);
        }
        return $i0;
    }

    public synchronized void writeData(byte[] $r1, int $i0) throws  {
        if (!this.m_isStopped) {
            this.m_dataLayer.writeData($r1, $i0);
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        synchronized (this) {
            if (!this.m_isStopped) {
                this.m_isStopped = true;
                if (this.m_dataLayer != null) {
                    this.m_dataLayer.unRegisterNotification(this);
                    this.m_dataLayer.closeConnection();
                    this.m_dataLayer = null;
                }
                MemoryPool.freeMem(this.m_buffer, "EchoLayer");
            }
        }
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
        try {
            int $i0 = readData(this.m_buffer, this.m_size);
            while ($i0 > 0) {
                writeData(this.m_buffer, $i0);
                MCSLogger.log("ECHO SENT BACK", "Size: " + $i0);
                $i0 = readData(this.m_buffer, this.m_size);
            }
        } catch (Exception $r2) {
            MCSLogger.log($r2.toString());
        }
    }

    public void closeConnection() throws  {
        onConnectionClosed(this.m_dataLayer);
    }
}
