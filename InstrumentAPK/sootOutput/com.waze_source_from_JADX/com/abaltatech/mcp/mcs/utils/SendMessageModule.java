package com.abaltatech.mcp.mcs.utils;

import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.pipe.IDataNotification;

public class SendMessageModule implements IMCSDataLayerNotification, IMCSMultiPointLayerNotification {
    private static final String TAG = SendMessageModule.class.getSimpleName();
    byte[] m_buffer = MemoryPool.getMem(4096, "SendMessageTest");
    IMCSDataLayer m_dataLayer;
    IMCSConnectionAddress m_myAddress;
    String m_name;
    IDataNotification m_notifiable;
    IMCSConnectionAddress m_remoteAddress;
    IMCSConnectionAddress m_remoteAddressAccept;

    public SendMessageModule(String $r1, IMCSMultiPointLayer $r2, IMCSConnectionAddress $r3, IMCSConnectionAddress $r4, IMCSConnectionAddress $r5) throws MCSException {
        this.m_myAddress = $r3;
        this.m_remoteAddress = $r4;
        this.m_remoteAddressAccept = $r5;
        $r2.registerNotification(this);
        this.m_name = $r1;
    }

    public void registerNotification(IDataNotification $r1) throws  {
        this.m_notifiable = $r1;
    }

    public void openConnection(IMCSMultiPointLayer $r1) throws MCSException {
        this.m_dataLayer = $r1.createConnectionPoint(this.m_remoteAddress, this.m_myAddress);
        this.m_dataLayer.registerNotification(this);
        this.m_dataLayer.sendConnect();
        int $i0 = 0;
        while (!this.m_dataLayer.isReady()) {
            $i0 += 10;
            if ($i0 > 5000) {
                try {
                    throw new MCSException("Error connecting to remote address");
                } catch (InterruptedException $r2) {
                    MCSLogger.log(TAG, "openConnection interrupted:", $r2);
                }
            } else {
                Thread.sleep((long) 10);
            }
        }
    }

    public void sendMessage(String $r1) throws  {
        sendMessage($r1.getBytes());
    }

    public void sendMessage(byte[] $r1) throws  {
        this.m_dataLayer.writeData($r1, $r1.length);
    }

    public void sendMessage(byte[] $r1, int $i0) throws  {
        this.m_dataLayer.writeData($r1, $i0);
    }

    public void newConnectionRequested(IMCSMultiPointLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3, byte[] payload) throws  {
        if ($r2.isSubsetOf(this.m_remoteAddressAccept) && $r3.isSameAs(this.m_myAddress)) {
            try {
                this.m_dataLayer = $r1.createConnectionPoint($r2, $r3);
                this.m_dataLayer.registerNotification(this);
                return;
            } catch (MCSException $r5) {
                MCSLogger.log(TAG, "SendMessageTest Exception", $r5);
                return;
            }
        }
        MCSLogger.log(TAG, "newConnectionRequested: from/to address mismatch");
    }

    public void onConnectionClosed(IMCSDataLayer $r1, IMCSConnectionAddress fromAddress, IMCSConnectionAddress toAddress) throws  {
        if ($r1 == this.m_dataLayer) {
            onConnectionClosed($r1);
        }
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
        if (this.m_dataLayer == null || this.m_buffer == null) {
            MCSLogger.log(TAG, "onDataReceived: null dataLayer / buffer");
            return;
        }
        int $i0 = this.m_dataLayer.readData(this.m_buffer, this.m_buffer.length);
        if (this.m_notifiable != null) {
            this.m_notifiable.onDataReceived(this.m_buffer, $i0);
        } else {
            MCSLogger.log(TAG, "onDataReceived: no notifiable");
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        this.m_dataLayer.unRegisterNotification(this);
        this.m_dataLayer = null;
    }

    public void closeConnection(IMCSMultiPointLayer $r1) throws  {
        if (this.m_dataLayer != null) {
            this.m_dataLayer.unRegisterNotification(this);
            IMCSDataLayer $r2 = this.m_dataLayer;
            this.m_dataLayer = null;
            try {
                $r1.closeConnection($r2);
            } catch (MCSException $r4) {
                MCSLogger.log(TAG, "closeConnection ex:", $r4);
            }
        }
        if (this.m_buffer != null) {
            try {
                MemoryPool.freeMem(this.m_buffer);
                this.m_buffer = null;
            } catch (MCSException $r6) {
                MCSLogger.log(TAG, "closeConnection ex:", $r6);
            }
        }
    }

    public IMCSConnectionAddress getRemoteAddress() throws  {
        return this.m_remoteAddress;
    }

    public IMCSConnectionAddress getMyAddress() throws  {
        return this.m_myAddress;
    }

    public IMCSConnectionAddress getRemoteAddressAccept() throws  {
        return this.m_remoteAddressAccept;
    }
}
