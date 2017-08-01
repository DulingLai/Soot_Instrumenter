package com.abaltatech.mcs.sockettransport;

import com.abaltatech.mcs.common.MCSException;

public class SSLSocketLayer extends SocketTransportLayer {
    private static final String Response = "HTTP/1.0 200 Connection established\r\n\r\n";
    private static final byte[] ResponseBytes = Response.getBytes();
    private boolean m_isFirstRead = true;
    private boolean m_isFirstWrite = true;

    public SSLSocketLayer(String $r1, int $i0) throws MCSException {
        super($r1, $i0);
    }

    public void writeData(byte[] $r1, int $i0) throws  {
        synchronized (this) {
            if (this.m_isFirstWrite) {
                this.m_isFirstWrite = false;
                notifyForData();
                return;
            }
            super.writeData($r1, $i0);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r10, int r11) throws  {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r9.m_isFirstRead;	 Catch:{ Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x0029;
    L_0x0005:
        r1 = 0;
        r9.m_isFirstRead = r1;	 Catch:{ Throwable -> 0x0026 }
        r2 = ResponseBytes;	 Catch:{ Throwable -> 0x0026 }
        r3 = r2.length;	 Catch:{ Throwable -> 0x0026 }
        if (r11 >= r3) goto L_0x0017;
    L_0x000d:
        r4 = "";
        r5 = "Too small buffer";
        com.abaltatech.mcs.logger.MCSLogger.log(r4, r5);	 Catch:{ Throwable -> 0x0026 }
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0026 }
        r1 = 0;
        return r1;
    L_0x0017:
        r2 = ResponseBytes;	 Catch:{ Throwable -> 0x0026 }
        r6 = ResponseBytes;	 Catch:{ Throwable -> 0x0026 }
        r11 = r6.length;	 Catch:{ Throwable -> 0x0026 }
        r1 = 0;
        r7 = 0;
        java.lang.System.arraycopy(r2, r1, r10, r7, r11);	 Catch:{ Throwable -> 0x0026 }
        r10 = ResponseBytes;	 Catch:{ Throwable -> 0x0026 }
        r11 = r10.length;	 Catch:{ Throwable -> 0x0026 }
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0026 }
        return r11;
    L_0x0026:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0026 }
        throw r8;
    L_0x0029:
        monitor-exit(r9);	 Catch:{ Throwable -> 0x0026 }
        r11 = super.readData(r10, r11);
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.sockettransport.SSLSocketLayer.readData(byte[], int):int");
    }
}
