package com.abaltatech.weblink.servercore;

import com.abaltatech.mcs.common.IConnectionListener;
import com.abaltatech.mcs.common.IConnectionReceiver;
import com.abaltatech.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcs.common.IMCSConnectionClosedNotification;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.logger.MCSLogger;

public abstract class WebLinkServerCore implements IConnectionReceiver, IWLConnectionClosedNotification {
    static final /* synthetic */ boolean $assertionsDisabled = (!WebLinkServerCore.class.desiredAssertionStatus());
    protected IMCSConnectionAddress m_address;
    protected WebLinkServerConnection m_connection = null;
    protected IConnectionListener m_connectionListener = null;
    private boolean m_isDataLayerClosed;
    protected boolean m_serverStarted = false;

    class C03941 implements IMCSConnectionClosedNotification {
        C03941() throws  {
        }

        public void onConnectionClosed(IMCSDataLayer connection) throws  {
            WebLinkServerCore.this.m_isDataLayerClosed = true;
        }
    }

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

    public IMCSConnectionAddress getListenAddress() throws  {
        return this.m_address;
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
            if ($r4 == null || $r4.m_connection == null || $r4.m_connection.isClosed()) {
                if ($r4 != null) {
                    MCSLogger.log("WebLinkServerCore - OnConnectionEstablished", "Error: previous connection not closed susccessfully!");
                }
                $r4 = createConnection();
                this.m_isDataLayerClosed = false;
                $r3.registerCloseNotification(new C03941());
                this.m_connection = $r4;
                if ($r4 == null || !$r4.connect($r3)) {
                    MCSLogger.log("====>", "====> FAILED to connect");
                    this.m_connection = null;
                    $r3.closeConnection();
                    return;
                } else if (this.m_isDataLayerClosed) {
                    $r4.disconnect();
                    this.m_connection = null;
                    return;
                } else {
                    $r4.registerCloseNotification(this);
                    onConnectionEstablished($r4);
                    return;
                }
            }
            $r3.closeConnection();
        }
    }

    protected void onConnectionEstablished(WebLinkServerConnection connection) throws  {
    }
}
