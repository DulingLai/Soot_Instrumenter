package com.abaltatech.mcp.mcs.socketproxy;

import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcp.mcs.pipe.IDataNotification;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import com.abaltatech.mcp.mcs.utils.SendMessageModule;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketProxy {
    private final int LISTENING_PORT = 8080;
    private AcceptThread m_acceptThread;
    private boolean m_isRunning = true;
    private IMCSMultiPointLayer m_protocolLayer;
    private ServerSocket m_serverSocket;

    private class AcceptThread extends Thread {
        public AcceptThread() throws  {
            setName("HttpProxyAcceptThread");
        }

        public void run() throws  {
            while (isRunning()) {
                try {
                    Socket $r5 = SocketProxy.this.m_serverSocket.accept();
                    if (isRunning()) {
                        new ConnectedThread($r5).start();
                    }
                } catch (IOException e) {
                    return;
                }
            }
        }

        private synchronized boolean isRunning() throws  {
            return SocketProxy.this.m_isRunning;
        }
    }

    private class ConnectedThread extends Thread implements IDataNotification {
        private boolean m_connected = true;
        private InputStream m_inputStream;
        private OutputStream m_outputStream;
        private SendMessageModule m_sendModule;
        private Socket m_socket;

        public ConnectedThread(Socket $r2) throws  {
            Thread thread = this;
            setName("HttpProxyConnectedThread");
            this.m_socket = $r2;
            Socket $r22 = this.m_socket;
            try {
                this.m_inputStream = $r22.getInputStream();
                $r22 = this.m_socket;
                this.m_outputStream = $r22.getOutputStream();
                try {
                    byte[] $r7 = new byte[]{(byte) 101, (byte) 101, (byte) 0, (byte) 2};
                    this.m_sendModule = new SendMessageModule("HUP Android", SocketProxy.this.m_protocolLayer, new TCPIPAddress(new byte[]{(byte) 101, (byte) 101, (byte) 0, (byte) 1}, 80), new TCPIPAddress($r7, 80), new TCPIPAddress($r7, 0));
                    SendMessageModule sendMessageModule = this.m_sendModule;
                    SendMessageModule $r10 = sendMessageModule;
                    sendMessageModule.registerNotification(this);
                    this.m_sendModule.openConnection(SocketProxy.this.m_protocolLayer);
                } catch (Exception e) {
                    this.m_connected = false;
                }
            } catch (IOException e2) {
                this.m_connected = false;
            }
        }

        public void run() throws  {
            byte[] $r2 = new byte[512];
            int $i1 = 1;
            while (this.m_connected && $i1 > 0) {
                try {
                    int $i0 = this.m_inputStream.read($r2);
                    $i1 = $i0;
                    this.m_sendModule.sendMessage($r2, $i0);
                } catch (Exception e) {
                    this.m_connected = false;
                }
            }
            end();
        }

        private void end() throws  {
            try {
                this.m_inputStream.close();
                this.m_outputStream.close();
                this.m_socket.close();
                this.m_sendModule.closeConnection(SocketProxy.this.m_protocolLayer);
            } catch (IOException e) {
            }
        }

        public void onDataReceived(byte[] $r1, int $i0) throws  {
            try {
                this.m_outputStream.write($r1, 0, $i0);
            } catch (IOException e) {
            }
        }
    }

    public SocketProxy() throws  {
        try {
            this.m_serverSocket = new ServerSocket(8080);
        } catch (IOException $r1) {
            $r1.printStackTrace();
        }
    }

    public synchronized void start() throws  {
        if (this.m_acceptThread == null) {
            this.m_acceptThread = new AcceptThread();
            this.m_acceptThread.start();
        }
    }

    public synchronized void stop() throws  {
        this.m_isRunning = false;
    }

    public IMCSMultiPointLayer getProtocolLayer() throws  {
        return this.m_protocolLayer;
    }

    public void setProtocolLayer(IMCSMultiPointLayer $r1) throws  {
        this.m_protocolLayer = $r1;
    }
}
