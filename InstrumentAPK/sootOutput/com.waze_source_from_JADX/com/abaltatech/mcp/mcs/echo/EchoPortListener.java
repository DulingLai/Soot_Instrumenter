package com.abaltatech.mcp.mcs.echo;

import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;

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

    public void newConnectionRequested(IMCSMultiPointLayer multiPointlayer, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3, byte[] payload) throws  {
        boolean $z0 = $r3;
        $r3 = $z0;
        if ($z0 instanceof TCPIPAddress) {
            $z0 = $r2;
            $r2 = $z0;
            if ($z0 instanceof TCPIPAddress) {
                TCPIPAddress $r7 = (TCPIPAddress) $r2;
                TCPIPAddress $r8 = (TCPIPAddress) $r3;
                if ($r8.isSubsetOf(this.m_address)) {
                    MCSLogger.log("EchoPortListener", "Incoming connection");
                    IMCSDataLayer $r10 = null;
                    synchronized (this) {
                        if (this.m_layer != null) {
                            $r10 = this.m_layer.createConnectionPoint($r7, $r8);
                        }
                    }
                    if ($r10 != null) {
                        try {
                            new EchoLayer().attachToLayer($r10);
                        } catch (MCSException $r6) {
                            MCSLogger.log("ERROR", $r6.toString());
                        }
                    }
                }
            }
        }
    }

    public void onConnectionClosed(IMCSDataLayer connection, IMCSConnectionAddress fromAddress, IMCSConnectionAddress toAddress) throws  {
    }
}
