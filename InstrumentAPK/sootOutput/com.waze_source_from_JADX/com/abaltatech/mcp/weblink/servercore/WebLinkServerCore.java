package com.abaltatech.mcp.weblink.servercore;

import com.abaltatech.mcp.mcs.common.IConnectionListener;
import com.abaltatech.mcp.mcs.common.IConnectionReceiver;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;

public abstract class WebLinkServerCore implements IConnectionReceiver, IWLConnectionClosedNotification {
    static final /* synthetic */ boolean $assertionsDisabled = (!WebLinkServerCore.class.desiredAssertionStatus());
    public static final String WLSERVICE_IWLAPPDISPATCHERSERVICE = "com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService";
    public static final String WLSERVICE_IWLAPPSSERVICEMANAGER = "com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager";
    public static final String WLSERVICE_IWLDISPLAYNOTIFICATIONMANAGER = "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager";
    public static final String WLSERVICE_IWLSERVICE = "com.abaltatech.weblink.service.interfaces.IWLService";
    public static final String WLSERVICE_IWLSERVICEPRIVATE = "com.abaltatech.weblink.service.interfaces.IWLServicePrivate";
    public static final String WLSERVICE_IWLVEHICLEDATARECEIVER = "com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver";
    public static final String WLSERVICE_IWLVEHICLEINFO = "com.abaltatech.weblink.service.interfaces.IWLVehicleInfo";
    public static final String WLSERVICE_IWLVIRTUALCONNECTIONHANDLER = "com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler";
    protected IMCSConnectionAddress m_address;
    protected WebLinkServerConnection m_connection = null;
    protected IConnectionListener m_connectionListener = null;
    protected boolean m_serverStarted = false;

    protected abstract WebLinkServerConnection createConnection() throws ;

    public boolean startServer(IConnectionListener $r1, IMCSConnectionAddress $r2) throws  {
        if (this.m_serverStarted) {
            return false;
        }
        if ($r1 == null) {
            return false;
        }
        this.m_connectionListener = $r1;
        this.m_address = this.m_connectionListener.StartListening($r2, 1, this);
        if (this.m_address == null) {
            return false;
        }
        this.m_serverStarted = true;
        return true;
    }

    public void stopServer() throws  {
        if (this.m_serverStarted && this.m_connectionListener != null) {
            this.m_connectionListener.StopListening(this.m_address);
            this.m_connectionListener = null;
            this.m_address = null;
            this.m_serverStarted = false;
            if (this.m_connection != null) {
                this.m_connection.disconnect();
            }
        }
    }

    public boolean isStarted() throws  {
        return this.m_serverStarted;
    }

    public boolean haveOpenConnection() throws  {
        return this.m_connection != null;
    }

    public void onConnectionClosed(WebLinkServerConnection $r1) throws  {
        $r1.unregisterCloseNotification(this);
        if ($assertionsDisabled || this.m_connection == $r1) {
            this.m_connection = null;
            return;
        }
        throw new AssertionError();
    }

    public void OnConnectionEstablished(IMCSConnectionAddress fromAddress, IMCSConnectionAddress toAddress, IMCSDataLayer $r3) throws  {
        if (!$assertionsDisabled && $r3 == null) {
            throw new AssertionError();
        } else if ($r3 != null) {
            WebLinkServerConnection $r4 = this.m_connection;
            if ($r4 == null || $r4.m_connection.isClosed()) {
                $r4 = createConnection();
                this.m_connection = $r4;
                if ($r4 == null || !$r4.connect($r3)) {
                    this.m_connection = null;
                    $r3.closeConnection();
                    return;
                }
                $r4.registerCloseNotification(this);
                onConnectionEstablished($r4);
                return;
            }
            $r3.closeConnection();
        }
    }

    protected void onConnectionEstablished(WebLinkServerConnection connection) throws  {
    }
}
