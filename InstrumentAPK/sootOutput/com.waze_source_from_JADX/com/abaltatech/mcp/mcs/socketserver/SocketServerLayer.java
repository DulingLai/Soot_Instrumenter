package com.abaltatech.mcp.mcs.socketserver;

import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.common.MCSMultiPointLayerBase;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.sockettransport.SocketTransportLayer;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketServerLayer extends MCSMultiPointLayerBase {
    private static final String TAG = SocketServerLayer.class.getSimpleName();
    private AcceptThread m_acceptThread;
    private TCPIPAddress m_address = null;
    private boolean m_isRunning = false;
    private ServerSocket m_serverSocket = null;
    HashMap<TCPIPAddress, Socket> m_socketMap = new HashMap();

    private class AcceptThread extends Thread {
        public AcceptThread() throws  {
            setName("HttpServerAcceptThread");
        }

        public void run() throws  {
            AcceptThread $r2;
            while (true) {
                $r2 = $r0;
                $r0 = $r2;
                if (!SocketServerLayer.this.isRunning()) {
                    break;
                }
                $r2 = $r0;
                $r0 = $r2;
                Socket $r4 = SocketServerLayer.this.m_serverSocket.accept();
                $r2 = $r0;
                $r0 = $r2;
                if (SocketServerLayer.this.isRunning()) {
                    TCPIPAddress $r1 = new TCPIPAddress($r4.getInetAddress(), $r4.getPort());
                    $r2 = $r0;
                    $r0 = $r2;
                    SocketServerLayer.this.m_socketMap.put($r1, $r4);
                    $r2 = $r0;
                    $r0 = $r2;
                    SocketServerLayer.this.notifyForNewConnection($r1, SocketServerLayer.this.m_address, null);
                    $r2 = $r0;
                    $r0 = $r2;
                    if (SocketServerLayer.this.m_socketMap.containsKey($r1)) {
                        $r2 = $r0;
                        $r0 = $r2;
                        try {
                            SocketServerLayer.this.m_socketMap.remove($r1);
                            try {
                                $r4.close();
                            } catch (IOException e) {
                            }
                        } catch (IOException $r10) {
                            MCSLogger.log(SocketServerLayer.TAG, "HttpServerAcceptThread IOexception e=", $r10);
                        }
                    } else {
                        continue;
                    }
                }
            }
            $r2 = $r0;
            $r0 = $r2;
            try {
                SocketServerLayer.this.m_serverSocket.close();
            } catch (IOException $r12) {
                MCSLogger.log(SocketServerLayer.TAG, "HttpServerAcceptThread IOexception2 e=", $r12);
            } finally {
                $r2 = $r0;
                $r0 = $r2;
                SocketServerLayer.this.m_serverSocket = null;
            }
        }
    }

    public SocketServerLayer(TCPIPAddress $r1) throws  {
        this.m_address = $r1;
    }

    public SocketServerLayer(int $i0, TCPIPAddress $r1) throws  {
        this.m_address = $r1;
        try {
            this.m_serverSocket = new ServerSocket($i0);
        } catch (IOException $r2) {
            $r2.printStackTrace();
        }
    }

    public synchronized boolean start(int $i0, int maxConnections) throws  {
        boolean $z0 = true;
        synchronized (this) {
            try {
                this.m_serverSocket = new ServerSocket($i0);
                if (this.m_acceptThread != null) {
                    this.m_acceptThread.interrupt();
                    this.m_acceptThread = null;
                }
                this.m_acceptThread = new AcceptThread();
                this.m_acceptThread.start();
                this.m_isRunning = true;
            } catch (IOException $r1) {
                $r1.printStackTrace();
                this.m_serverSocket = null;
                $z0 = false;
            }
        }
        return $z0;
    }

    public synchronized void start() throws  {
        if (this.m_acceptThread != null) {
            this.m_acceptThread.interrupt();
            this.m_acceptThread = null;
        }
        this.m_acceptThread = new AcceptThread();
        this.m_acceptThread.start();
        this.m_isRunning = true;
    }

    public synchronized void stop() throws  {
        this.m_isRunning = false;
        if (this.m_serverSocket != null) {
            try {
                this.m_serverSocket.close();
            } catch (IOException $r1) {
                $r1.printStackTrace();
            }
        }
    }

    protected synchronized boolean isRunning() throws  {
        return this.m_isRunning;
    }

    public IMCSDataLayer createConnectionPoint(IMCSConnectionAddress $r1, IMCSConnectionAddress toAddress) throws MCSException {
        SocketTransportLayer $r6 = null;
        try {
            $r6 = new SocketTransportLayer((Socket) this.m_socketMap.get($r1));
        } catch (MCSException $r3) {
            MCSLogger.log("SocketServer", "createConnectionPoint", $r3);
        }
        this.m_socketMap.remove($r1);
        return $r6;
    }

    public void closeConnection(IMCSDataLayer $r1) throws MCSException {
        if ($r1 != null) {
            $r1.closeConnection();
        }
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
    }
}
