package com.abaltatech.mcs.utils;

import com.abaltatech.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.pipe.IDataNotification;

public class SendMessageModule implements IMCSDataLayerNotification, IMCSMultiPointLayerNotification {
    byte[] m_buffer = MemoryPool.getMem(4096, "SendMessageTest");
    IMCSDataLayer m_dataLayer;
    private IMCSMultiPointLayer m_multiPointLayer;
    IMCSConnectionAddress m_myAddress;
    String m_name;
    IDataNotification m_notifiable;
    IMCSConnectionAddress m_remoteAddress;
    IMCSConnectionAddress m_remoteAddressAccept;

    public SendMessageModule(String $r1, IMCSMultiPointLayer $r2, IMCSConnectionAddress $r3, IMCSConnectionAddress $r4, IMCSConnectionAddress $r5) throws MCSException {
        this.m_multiPointLayer = $r2;
        this.m_myAddress = $r3;
        this.m_remoteAddress = $r4;
        this.m_remoteAddressAccept = $r5;
        this.m_multiPointLayer.registerNotification(this);
        this.m_name = $r1;
    }

    public void registerNotification(IDataNotification $r1) throws  {
        this.m_notifiable = $r1;
    }

    public void openConnection() throws MCSException {
        this.m_dataLayer = this.m_multiPointLayer.createConnectionPoint(this.m_remoteAddress, this.m_myAddress);
        this.m_dataLayer.registerNotification(this);
        this.m_dataLayer.sendConnect();
        int $i0 = 0;
        while (!this.m_dataLayer.isReady()) {
            $i0 += 10;
            if ($i0 > 5000) {
                try {
                    throw new MCSException("Error connecting to remote address");
                } catch (InterruptedException $r1) {
                    $r1.printStackTrace();
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

    public void newConnectionRequested(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, byte[] payload) throws  {
        if ($r1.isSubsetOf(this.m_remoteAddressAccept) && $r2.isSameAs(this.m_myAddress)) {
            try {
                this.m_dataLayer = this.m_multiPointLayer.createConnectionPoint($r1, $r2);
                this.m_dataLayer.registerNotification(this);
            } catch (MCSException $r4) {
                MCSLogger.log("SendMessageTest Exception", $r4.toString());
            }
        }
    }

    public void onConnectionClosed(IMCSDataLayer $r1, IMCSConnectionAddress fromAddress, IMCSConnectionAddress toAddress) throws  {
        if ($r1 == this.m_dataLayer) {
            onConnectionClosed($r1);
        }
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
        if (this.m_dataLayer != null && this.m_buffer != null) {
            int $i0 = this.m_dataLayer.readData(this.m_buffer, this.m_buffer.length);
            if (this.m_notifiable != null) {
                this.m_notifiable.onDataReceived(this.m_buffer, $i0);
            }
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        this.m_dataLayer.unRegisterNotification(this);
        this.m_dataLayer = null;
    }

    public void closeConnection() throws  {
        if (this.m_dataLayer != null) {
            this.m_dataLayer.unRegisterNotification(this);
            IMCSDataLayer $r2 = this.m_dataLayer;
            this.m_dataLayer = null;
            try {
                this.m_multiPointLayer.closeConnection($r2);
            } catch (MCSException $r1) {
                $r1.printStackTrace();
            }
        }
        if (this.m_buffer != null) {
            try {
                MemoryPool.freeMem(this.m_buffer);
                this.m_buffer = null;
            } catch (MCSException e) {
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
