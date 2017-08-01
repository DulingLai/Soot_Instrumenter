package com.abaltatech.mcs.sockettransport;

import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSDataLayerNotification;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketConnectionService implements IMCSDataLayerNotification {
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private AcceptThread m_acceptThread;
    private SocketLayer m_dataLayer;
    private int m_port;
    private int m_state = 0;

    private class AcceptThread extends Thread {
        private boolean m_canceled = false;
        private ServerSocket mmServerSocket;

        private void prepareSocket() throws  {
            try {
                this.mmServerSocket = new ServerSocket(SocketConnectionService.this.m_port);
            } catch (IOException $r1) {
                $r1.printStackTrace();
            }
        }

        public void run() throws  {
            prepareSocket();
            while (true) {
                acceptConnection();
                System.out.println();
                if (!this.m_canceled) {
                    while (SocketConnectionService.this.m_state == 3) {
                        if (!this.m_canceled) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException $r1) {
                                $r1.printStackTrace();
                            }
                        } else {
                            return;
                        }
                    }
                }
                return;
            }
        }

        private void acceptConnection() throws  {
            while (SocketConnectionService.this.m_state != 3 && !this.m_canceled) {
                try {
                    Socket $r4 = this.mmServerSocket.accept();
                    if ($r4 != null) {
                        synchronized (SocketConnectionService.this) {
                            switch (SocketConnectionService.this.m_state) {
                                case 0:
                                case 3:
                                    try {
                                        $r4.close();
                                        break;
                                    } catch (IOException e) {
                                        break;
                                    }
                                case 1:
                                case 2:
                                    SocketConnectionService.this.connected($r4);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (IOException e2) {
                    return;
                }
            }
            return;
        }

        public void cancel() throws  {
            this.m_canceled = true;
            if (this.mmServerSocket != null) {
                try {
                    this.mmServerSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public SocketConnectionService(SocketLayer $r1, int $i0) throws  {
        this.m_port = $i0;
        this.m_dataLayer = $r1;
        this.m_dataLayer.registerNotification(this);
    }

    private synchronized void setState(int $i0) throws  {
        this.m_state = $i0;
    }

    public synchronized int getState() throws  {
        return this.m_state;
    }

    public synchronized void start() throws  {
        if (this.m_dataLayer.isOpen()) {
            this.m_dataLayer.closeSocket();
        }
        if (this.m_acceptThread == null) {
            this.m_acceptThread = new AcceptThread();
            this.m_acceptThread.start();
        }
        setState(1);
    }

    public synchronized void connected(Socket $r1) throws  {
        if (this.m_dataLayer.isOpen()) {
            this.m_dataLayer.closeSocket();
        }
        this.m_dataLayer.attachSocket($r1);
        setState(3);
    }

    public synchronized void stop() throws  {
        if (this.m_dataLayer.isOpen()) {
            this.m_dataLayer.closeSocket();
        }
        if (this.m_acceptThread != null) {
            this.m_acceptThread.cancel();
            this.m_acceptThread = null;
        }
        setState(0);
    }

    public synchronized void disconnect() throws  {
        if (this.m_dataLayer.isOpen()) {
            this.m_dataLayer.closeSocket();
        }
        if (this.m_acceptThread != null) {
            this.m_acceptThread.cancel();
            this.m_acceptThread = null;
        }
        start();
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
    }

    public void onDataReceived(IMCSDataLayer connection) throws  {
    }
}
