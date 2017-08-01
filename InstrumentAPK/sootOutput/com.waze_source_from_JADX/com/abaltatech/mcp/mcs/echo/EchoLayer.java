package com.abaltatech.mcp.mcs.echo;

import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.logger.MCSLogger;

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

    public synchronized int readData(IMCSDataLayer $r1, byte[] $r2, int $i0) throws  {
        if (this.m_isStopped) {
            $i0 = -1;
        } else {
            $i0 = $r1.readData($r2, $i0);
        }
        return $i0;
    }

    public synchronized void writeData(IMCSDataLayer $r1, byte[] $r2, int $i0) throws  {
        if (!this.m_isStopped) {
            $r1.writeData($r2, $i0);
        }
    }

    public void onConnectionClosed(IMCSDataLayer $r1) throws  {
        synchronized (this) {
            if (!this.m_isStopped) {
                this.m_isStopped = true;
                if ($r1 != null) {
                    $r1.unRegisterNotification(this);
                    $r1.closeConnection();
                }
                MemoryPool.freeMem(this.m_buffer, "EchoLayer");
            }
        }
    }

    public void onDataReceived(IMCSDataLayer $r1) throws  {
        try {
            int $i0 = readData($r1, this.m_buffer, this.m_size);
            while ($i0 > 0) {
                writeData($r1, this.m_buffer, $i0);
                MCSLogger.log("ECHO SENT BACK", "Size: " + $i0);
                $i0 = readData($r1, this.m_buffer, this.m_size);
            }
        } catch (Exception $r2) {
            MCSLogger.log($r2.toString());
        }
    }
}
