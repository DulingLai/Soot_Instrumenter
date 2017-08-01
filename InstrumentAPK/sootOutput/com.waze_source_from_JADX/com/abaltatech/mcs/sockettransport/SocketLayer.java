package com.abaltatech.mcs.sockettransport;

import com.abaltatech.mcs.iostream.IOStreamLayer;
import com.abaltatech.mcs.logger.MCSLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketLayer extends IOStreamLayer {
    private Socket m_socket;

    public SocketLayer() throws  {
        super("SocketLayer");
    }

    public synchronized void attachSocket(Socket $r1) throws  {
        this.m_socket = $r1;
        InputStream $r2 = null;
        OutputStream $r3 = null;
        try {
            $r2 = this.m_socket.getInputStream();
            $r3 = this.m_socket.getOutputStream();
        } catch (IOException e) {
        }
        MCSLogger.log("SocketLayer", "IO streams created");
        attachStreams($r2, $r3);
        MCSLogger.log("SocketLayer", "Attached");
    }

    public void closeSocket() throws  {
        synchronized (this) {
            try {
                this.m_socket.close();
                this.m_input = null;
                this.m_output = null;
                this.m_socket = null;
            } catch (IOException e) {
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isOpen() throws  {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.m_input;	 Catch:{ Throwable -> 0x000b }
        if (r0 == 0) goto L_0x0008;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x000b }
        r1 = 1;
        return r1;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x000b }
        r1 = 0;
        return r1;
    L_0x000b:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ Throwable -> 0x000b }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.sockettransport.SocketLayer.isOpen():boolean");
    }

    protected void onIOException(IOException $r1) throws  {
        super.onIOException($r1);
        synchronized (this) {
            if (this.m_socket != null) {
                try {
                    this.m_socket.close();
                } catch (IOException e) {
                }
            }
            this.m_socket = null;
        }
    }
}
