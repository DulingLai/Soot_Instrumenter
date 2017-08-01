package com.abaltatech.mcp.mcs.gateway;

import com.abaltatech.mcp.mcs.common.IConnectionReceiver;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.sockettransport.SocketChannelTransportLayer;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ListenConnectionHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!ListenConnectionHandler.class.desiredAssertionStatus());
    private ConcurrentHashMap<IMCSConnectionAddress, ServerImpl> m_servers = new ConcurrentHashMap();

    class ServerImpl implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = (!ListenConnectionHandler.class.desiredAssertionStatus());
        private IMCSConnectionAddress m_listenAddress = null;
        private int m_numConnections = 0;
        private IConnectionReceiver m_receiver = null;
        private Selector m_selector = null;
        private IMCSConnectionAddress m_serverAddress = null;
        private ServerSocketChannel m_serverChannel = null;
        private Thread m_thread = null;

        ServerImpl(IMCSConnectionAddress $r2, int $i0, IConnectionReceiver $r3) throws  {
            this.m_listenAddress = $r2;
            this.m_numConnections = $i0;
            this.m_receiver = $r3;
            if (!$assertionsDisabled && this.m_receiver == null) {
                throw new AssertionError();
            }
        }

        public IMCSConnectionAddress GetServerAddress() throws  {
            return this.m_serverAddress;
        }

        public boolean Init() throws  {
            if (!$assertionsDisabled && (this.m_receiver == null || this.m_listenAddress == null || !(this.m_listenAddress instanceof TCPIPAddress))) {
                throw new AssertionError();
            } else if (this.m_receiver == null) {
                return false;
            } else {
                if (this.m_listenAddress == null) {
                    return false;
                }
                if (!(this.m_listenAddress instanceof TCPIPAddress)) {
                    return false;
                }
                TCPIPAddress $r5 = (TCPIPAddress) this.m_listenAddress;
                try {
                    this.m_selector = Selector.open();
                    this.m_serverChannel = ServerSocketChannel.open();
                    this.m_thread = new Thread(this);
                    this.m_serverChannel.configureBlocking(false);
                    ServerSocket $r9 = this.m_serverChannel.socket();
                    $r9.bind(new InetSocketAddress($r5.getPort()), this.m_numConnections);
                    this.m_serverAddress = new TCPIPAddress((InetSocketAddress) $r9.getLocalSocketAddress());
                    this.m_serverChannel.register(this.m_selector, 16);
                    this.m_thread.start();
                    return true;
                } catch (Exception $r1) {
                    MCSLogger.log("ListenConnectionHandler", $r1.toString());
                    if (this.m_thread != null) {
                        this.m_thread.interrupt();
                        this.m_thread = null;
                    }
                    if (this.m_serverChannel != null) {
                        try {
                            this.m_serverChannel.close();
                        } catch (Exception e) {
                        }
                        this.m_serverChannel = null;
                    }
                    if (this.m_selector == null) {
                        return false;
                    }
                    try {
                        this.m_selector.close();
                    } catch (Exception e2) {
                    }
                    this.m_selector = null;
                    return false;
                }
            }
        }

        public void Terminate() throws  {
            if (this.m_thread != null) {
                this.m_thread.interrupt();
                this.m_thread = null;
            }
            if (this.m_serverChannel != null) {
                try {
                    this.m_serverChannel.close();
                } catch (Exception e) {
                }
                this.m_serverChannel = null;
            }
            if (this.m_selector != null) {
                try {
                    this.m_selector.close();
                } catch (Exception e2) {
                }
                this.m_selector = null;
            }
            this.m_serverAddress = null;
        }

        public void run() throws  {
            while (!Thread.interrupted() && this.m_thread != null) {
                try {
                    this.m_selector.select();
                    Iterator $r7 = this.m_selector.selectedKeys().iterator();
                    while ($r7.hasNext()) {
                        SelectionKey $r9 = (SelectionKey) $r7.next();
                        $r7.remove();
                        if ($r9.isAcceptable()) {
                            SocketChannel $r12 = ((ServerSocketChannel) $r9.channel()).accept();
                            SocketChannelTransportLayer socketChannelTransportLayer = new SocketChannelTransportLayer($r12);
                            TCPIPAddress tCPIPAddress = new TCPIPAddress((InetSocketAddress) $r12.socket().getRemoteSocketAddress());
                            this.m_receiver.OnConnectionEstablished(tCPIPAddress, this.m_serverAddress, socketChannelTransportLayer, null);
                        }
                    }
                } catch (Exception e) {
                }
                MCSLogger.log("ServerImpl:  listen thread exited !");
            }
        }
    }

    public IMCSConnectionAddress StartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
        ServerImpl $r3 = new ServerImpl($r1, $i0, $r2);
        if ($r3.Init()) {
            $r1 = $r3.GetServerAddress();
            this.m_servers.put($r1, $r3);
            return $r1;
        }
        MCSLogger.log("ListenConnectionHandler:  failed to start listening on " + $r1);
        return null;
    }

    public void StopListening(IMCSConnectionAddress $r1) throws  {
        ServerImpl $r4 = (ServerImpl) this.m_servers.get($r1);
        this.m_servers.remove($r1);
        if (!$assertionsDisabled && $r4 == null) {
            throw new AssertionError();
        } else if ($r4 != null) {
            $r4.Terminate();
        }
    }
}
