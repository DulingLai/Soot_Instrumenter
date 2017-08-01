package com.abaltatech.mcs.echo;

import com.abaltatech.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.tcpip.TCPIPAddress;

public class EchoPortListener implements IMCSMultiPointLayerNotification {
    public static final int EchoPort = 7;
    private TCPIPAddress m_address;
    private IMCSMultiPointLayer m_layer;

    public EchoPortListener(TCPIPAddress $r1) throws  {
        this.m_address = $r1;
        if (this.m_address == null) {
            this.m_address = new TCPIPAddress(7);
        }
        MCSLogger.log("EchoPortListener", "Start listening for connections to " + this.m_address);
    }

    public void attachToLayer(IMCSMultiPointLayer $r1) throws  {
        synchronized (this) {
            if (this.m_layer != null) {
                this.m_layer.unRegisterNotification(this);
                this.m_layer = null;
            }
            this.m_layer = $r1;
            if (this.m_layer != null) {
                this.m_layer.registerNotification(this);
            }
        }
    }

    public void newConnectionRequested(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, byte[] payload) throws  {
        boolean $z0 = $r2;
        $r2 = $z0;
        if ($z0 instanceof TCPIPAddress) {
            $z0 = $r1;
            $r1 = $z0;
            if ($z0 instanceof TCPIPAddress) {
                TCPIPAddress $r6 = (TCPIPAddress) $r1;
                TCPIPAddress $r7 = (TCPIPAddress) $r2;
                if ($r7.isSubsetOf(this.m_address)) {
                    MCSLogger.log("EchoPortListener", "Incoming connection");
                    IMCSDataLayer $r9 = null;
                    synchronized (this) {
                        if (this.m_layer != null) {
                            $r9 = this.m_layer.createConnectionPoint($r6, $r7);
                        }
                    }
                    if ($r9 != null) {
                        try {
                            new EchoLayer().attachToLayer($r9);
                        } catch (MCSException $r5) {
                            MCSLogger.log("ERROR", $r5.toString());
                        }
                    }
                }
            }
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection, IMCSConnectionAddress fromAddress, IMCSConnectionAddress toAddress) throws  {
    }
}
