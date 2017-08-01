package com.abaltatech.mcp.mcs.connector;

import com.abaltatech.mcp.mcs.common.IMCSConnectionClosedNotification;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.utils.NotificationList;

public class ConnectorLayer {
    protected static final int MinSizeHeader = 100;
    private static final String TAG = "===> ConnectorLayer";
    protected final int m_bufSize;
    protected IMCSDataLayer m_layer1;
    protected IMCSDataLayer m_layer2;
    ConnectionClosedNotificationList m_notifiables = new ConnectionClosedNotificationList();
    protected LayerNotification m_notification1;
    protected LayerNotification m_notification2;

    private class ConnectionClosedNotificationList extends NotificationList {
        private ConnectionClosedNotificationList() throws  {
        }

        public void Register(IMCSConnectionClosedNotification $r1) throws  {
            super.Register($r1);
        }

        public void NotifyConnectionClosed() throws  {
            int $i0 = Start();
            while (true) {
                IMCSConnectionClosedNotification $r2 = (IMCSConnectionClosedNotification) GetObject($i0);
                if ($r2 != null) {
                    $r2.onConnectionClosed(null);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }
    }

    protected static class LayerNotification implements IMCSDataLayerNotification {
        private ConnectorLayer m_connector;
        private IMCSDataLayer m_layer;
        private final int m_layerNo;

        public LayerNotification(int $i0, IMCSDataLayer $r1, ConnectorLayer $r2) throws  {
            this.m_layerNo = $i0;
            this.m_connector = $r2;
            this.m_layer = $r1;
            $r1.registerNotification(this);
        }

        public void onConnectionClosed(IMCSDataLayer connection) throws  {
            ConnectorLayer $r2 = this.m_connector;
            if ($r2 != null) {
                cleanUp();
                $r2.onConnectionClosed(this.m_layerNo);
            }
        }

        public void onDataReceived(IMCSDataLayer connection) throws  {
            ConnectorLayer $r2 = this.m_connector;
            if ($r2 != null) {
                $r2.onDataReceived(this.m_layerNo);
            }
        }

        private void cleanUp() throws  {
            if (this.m_layer != null) {
                this.m_layer.unRegisterNotification(this);
                this.m_layer = null;
            }
            this.m_connector = null;
        }

        public void close() throws  {
            IMCSDataLayer $r1 = this.m_layer;
            if ($r1 != null) {
                cleanUp();
                $r1.closeConnection();
            }
        }
    }

    public IMCSDataLayer getLayer(int $i0) throws  {
        return $i0 == 1 ? this.m_layer1 : $i0 == 2 ? this.m_layer2 : null;
    }

    public void onDataReceived(int r11) throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r0 = 1;
        if (r0 != r11) goto L_0x0036;
    L_0x0003:
        r1 = r10.m_layer1;
    L_0x0005:
        r0 = 1;
        if (r0 != r11) goto L_0x0039;
    L_0x0008:
        r2 = r10.m_layer2;
    L_0x000a:
        r11 = com.abaltatech.mcp.mcs.common.MemoryPool.BufferSizeBig;
        r4 = "ConnectionLayer";	 Catch:{ MCSException -> 0x003c }
        r3 = com.abaltatech.mcp.mcs.common.MemoryPool.getMem(r11, r4);	 Catch:{ MCSException -> 0x003c }
    L_0x0012:
        if (r1 == 0) goto L_0x005b;
    L_0x0014:
        if (r2 == 0) goto L_0x005b;
    L_0x0016:
        if (r3 == 0) goto L_0x005b;
    L_0x0018:
        r11 = r10.m_bufSize;	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
        r11 = r1.readData(r3, r11);	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
        if (r11 <= 0) goto L_0x005b;	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
    L_0x0020:
        r2.writeData(r3, r11);	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
        goto L_0x0018;
    L_0x0024:
        r5 = move-exception;
        r6 = r5.getMessage();	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
        r4 = "===> ConnectorLayer - Exception";	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r6);	 Catch:{ Exception -> 0x0024, Throwable -> 0x0063 }
        if (r3 == 0) goto L_0x006c;
    L_0x0030:
        r4 = "ConnectionLayer";
        com.abaltatech.mcp.mcs.common.MemoryPool.freeMem(r3, r4);
        return;
    L_0x0036:
        r1 = r10.m_layer2;
        goto L_0x0005;
    L_0x0039:
        r2 = r10.m_layer1;
        goto L_0x000a;
    L_0x003c:
        r7 = move-exception;
        r3 = 0;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r4 = "Exception: ";
        r8 = r8.append(r4);
        r6 = r7.getMessage();
        r8 = r8.append(r6);
        r6 = r8.toString();
        r4 = "===> ConnectorLayer";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r6);
        goto L_0x0012;
    L_0x005b:
        if (r3 == 0) goto L_0x006d;
    L_0x005d:
        r4 = "ConnectionLayer";
        com.abaltatech.mcp.mcs.common.MemoryPool.freeMem(r3, r4);
        return;
    L_0x0063:
        r9 = move-exception;
        if (r3 == 0) goto L_0x006b;
    L_0x0066:
        r4 = "ConnectionLayer";
        com.abaltatech.mcp.mcs.common.MemoryPool.freeMem(r3, r4);
    L_0x006b:
        throw r9;
    L_0x006c:
        return;
    L_0x006d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.connector.ConnectorLayer.onDataReceived(int):void");
    }

    public ConnectorLayer(IMCSDataLayer $r1, IMCSDataLayer $r2) throws MCSException {
        if ($r1 == null || $r2 == null) {
            throw new MCSException("NULL data layer provided");
        }
        this.m_layer1 = $r1;
        this.m_layer2 = $r2;
        this.m_notification1 = new LayerNotification(1, $r1, this);
        this.m_notification2 = new LayerNotification(2, $r2, this);
        this.m_bufSize = MemoryPool.BufferSizeBig - 100;
        MCSLogger.log(TAG, "layers attached");
    }

    protected void onConnectionClosed(int $i0) throws  {
        LayerNotification $r1;
        if (1 == $i0) {
            $r1 = this.m_notification2;
        } else {
            $r1 = this.m_notification1;
        }
        MCSLogger.log(TAG, "Connection closed by layer " + $i0);
        cleanUp();
        if ($r1 != null) {
            $r1.close();
        }
    }

    private void cleanUp() throws  {
        boolean $z0 = false;
        if (this.m_layer1 != null) {
            this.m_layer1 = null;
            this.m_layer2 = null;
            this.m_notification1 = null;
            this.m_notification2 = null;
            $z0 = true;
        }
        if ($z0) {
            notifyForConnectionClosed();
            this.m_notifiables.ClearAll();
        }
    }

    public void closeConnection() throws  {
        LayerNotification $r1 = this.m_notification1;
        LayerNotification $r2 = this.m_notification2;
        cleanUp();
        if ($r1 != null) {
            $r1.close();
        }
        if ($r2 != null) {
            $r2.close();
        }
    }

    public void detachLayers() throws  {
        cleanUp();
    }

    public void setLayer(int $i0, IMCSDataLayer $r1) throws  {
        if ($r1 == null) {
            return;
        }
        if ($i0 == 1) {
            if (this.m_layer1 != $r1) {
                this.m_notification1.cleanUp();
                this.m_layer1 = $r1;
                this.m_notification1 = new LayerNotification(1, $r1, this);
            }
        } else if ($i0 == 2 && this.m_layer2 != $r1) {
            this.m_notification2.cleanUp();
            this.m_layer2 = $r1;
            this.m_notification2 = new LayerNotification(2, $r1, this);
        }
    }

    public void registerNotification(IMCSConnectionClosedNotification $r1) throws  {
        if ($r1 != null) {
            this.m_notifiables.Register($r1);
        }
    }

    public void unRegisterNotification(IMCSConnectionClosedNotification $r1) throws  {
        if ($r1 != null) {
            this.m_notifiables.Unregister($r1);
        }
    }

    protected void notifyForConnectionClosed() throws  {
        this.m_notifiables.NotifyConnectionClosed();
    }
}
