package com.abaltatech.weblink.servercore;

import com.abaltatech.mcs.common.IMCSConnectionClosedNotification;
import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.NotificationList;
import com.abaltatech.weblink.core.WebLinkConnection;
import com.abaltatech.weblink.core.commandhandling.Command;
import com.abaltatech.weblink.core.commandhandling.FillRectangleCommand;
import com.abaltatech.weblink.core.commandhandling.ICommandHandler;
import com.abaltatech.weblink.core.commandhandling.VideoConfigCommand;
import com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler;
import com.abaltatech.weblink.core.frameencoding.IFrameEncoder;
import java.nio.ByteBuffer;
import sun.java2d.Surface;

public abstract class WebLinkServerConnection implements IMCSConnectionClosedNotification, ICommandHandler, IFrameEncodedHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!WebLinkServerConnection.class.desiredAssertionStatus());
    private static final String TAG = "WebLinkServerConnection";
    protected WebLinkConnection m_connection = null;
    protected int m_currAppID = 0;
    protected IMCSDataLayer m_dataLayer = null;
    protected int m_encHeight = 0;
    protected String m_encParams = null;
    protected int m_encWidth = 0;
    protected Surface m_encoderSurface;
    protected IFrameEncoder m_frameEncoder = null;
    protected ConnectionClosedNotificationList m_notification = new ConnectionClosedNotificationList();
    protected int m_srcHeight = 0;
    protected int m_srcWidth = 0;

    class C03931 implements Runnable {
        C03931() throws  {
        }

        public void run() throws  {
            WebLinkServerConnection.this.disconnect();
        }
    }

    private class ConnectionClosedNotificationList extends NotificationList {
        private ConnectionClosedNotificationList() throws  {
        }

        public void NotifyConnectionClosed() throws  {
            int $i0 = Start();
            while (true) {
                IWLConnectionClosedNotification $r3 = (IWLConnectionClosedNotification) GetObject($i0);
                if ($r3 != null) {
                    $r3.onConnectionClosed(WebLinkServerConnection.this);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }
    }

    protected void configureVideoEncoder(com.abaltatech.weblink.core.commandhandling.VideoConfigCommand r1) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.abaltatech.weblink.servercore.WebLinkServerConnection.configureVideoEncoder(com.abaltatech.weblink.core.commandhandling.VideoConfigCommand):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 5 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblink.servercore.WebLinkServerConnection.configureVideoEncoder(com.abaltatech.weblink.core.commandhandling.VideoConfigCommand):void");
    }

    protected abstract boolean configureVirtualView(int i, int i2) throws ;

    protected abstract WebLinkConnection createConnection() throws ;

    protected abstract void onVideoConfigurationCompleted() throws ;

    public boolean connect(IMCSDataLayer $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        this.m_dataLayer = $r1;
        this.m_connection = createConnection();
        if (prepareConnection(this.m_connection) && this.m_connection.init(this.m_dataLayer)) {
            this.m_dataLayer.registerCloseNotification(this);
            return true;
        }
        this.m_connection = null;
        this.m_dataLayer = null;
        return false;
    }

    public void disconnect() throws  {
        WebLinkConnection $r1 = this.m_connection;
        if ($r1 != null) {
            IFrameEncoder $r2;
            synchronized (this) {
                $r2 = this.m_frameEncoder;
                this.m_connection = null;
                this.m_frameEncoder = null;
                this.m_encHeight = 0;
                this.m_encWidth = 0;
                this.m_srcHeight = 0;
                this.m_srcWidth = 0;
                this.m_encParams = null;
            }
            if ($r2 != null) {
                $r2.stopEncoding();
            }
            $r1.terminate();
        }
    }

    public WebLinkConnection getConnection() throws  {
        return this.m_connection;
    }

    public boolean isConnected() throws  {
        return this.m_connection != null;
    }

    public boolean canSendFrame() throws  {
        return (this.m_frameEncoder == null || this.m_connection == null || !this.m_connection.canSendCommand() || this.m_connection.hasCommand(1)) ? false : true;
    }

    public void registerCloseNotification(IWLConnectionClosedNotification $r1) throws  {
        this.m_notification.Register($r1);
    }

    public void unregisterCloseNotification(IWLConnectionClosedNotification $r1) throws  {
        this.m_notification.Unregister($r1);
    }

    public boolean handleCommand(Command $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if (!$r1.isValid()) {
            return false;
        }
        switch ($r1.getCommandID()) {
            case (short) 32:
                MCSLogger.log("WebLinkServerConnection - handleCommand", "Will call configureVideoEncoder()");
                configureVideoEncoder(new VideoConfigCommand($r1.getRawCommandData()));
                return true;
            default:
                return false;
        }
    }

    public boolean sendCommand(Command $r1) throws  {
        return this.m_connection != null ? this.m_connection.sendCommand($r1) : false;
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        if (this.m_dataLayer != null) {
            this.m_dataLayer.unregisterCloseNotification(this);
            this.m_dataLayer = null;
            this.m_notification.NotifyConnectionClosed();
            if (this.m_connection != null) {
                disconnect();
            }
        }
    }

    public void onFrameEncoded(int width, int height, int frameEncoding, ByteBuffer $r1) throws  {
        synchronized (this) {
            if (this.m_connection != null) {
                this.m_connection.sendCommand(new FillRectangleCommand(this.m_currAppID, this.m_encWidth, this.m_encHeight, this.m_frameEncoder.getType(), $r1));
            }
        }
    }

    protected boolean prepareConnection(WebLinkConnection $r1) throws  {
        if ($assertionsDisabled || $r1 != null) {
            $r1.registerHandler((short) 32, this);
            return true;
        }
        throw new AssertionError();
    }

    protected String getEncoderParams(String $r1, int $i0) throws  {
        if ($r1 == null) {
            return null;
        }
        for (String $r12 : $r12.split(";")) {
            String[] $r3 = $r12.split(":");
            if ($r3.length == 2 && $r3[0].equals(Integer.toString($i0))) {
                return $r3[1];
            }
        }
        return null;
    }
}
