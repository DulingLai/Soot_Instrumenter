package com.abaltatech.mcp.mcs.common;

import com.abaltatech.mcp.mcs.common.IMCSDataLayer.EWriteMode;
import com.abaltatech.mcp.mcs.utils.ByteBuffer;
import com.abaltatech.mcp.mcs.utils.NotificationList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public abstract class MCSDataLayerBase implements IMCSDataLayer {
    static final /* synthetic */ boolean $assertionsDisabled = (!MCSDataLayerBase.class.desiredAssertionStatus());
    protected ConnClosedNotificationList m_connCloseListeners = new ConnClosedNotificationList();
    protected DataLayerNotificationList m_dataListeners = new DataLayerNotificationList();
    protected IMCSDataStats m_statistics = null;
    private EWriteMode m_writeMode = EWriteMode.eInterlocked;
    private BufferedWriter m_writer = null;

    protected class BufferedWriter implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = (!MCSDataLayerBase.class.desiredAssertionStatus());
        private LinkedBlockingQueue<ByteBuffer> m_queue = new LinkedBlockingQueue();
        private Semaphore m_semaphore = null;
        private boolean m_shutDownRequested = false;
        private Thread m_thread = null;

        public BufferedWriter(int $i0) throws  {
            this.m_semaphore = new Semaphore($i0);
        }

        public void addBuffer(ByteBuffer $r1) throws  {
            if (!$assertionsDisabled && this.m_shutDownRequested) {
                throw new AssertionError();
            } else if (!this.m_shutDownRequested) {
                try {
                    this.m_semaphore.acquire($r1.getSize());
                    this.m_queue.put($r1);
                    if (this.m_thread == null) {
                        this.m_thread = new Thread(this);
                        this.m_thread.start();
                    }
                } catch (InterruptedException e) {
                }
            }
        }

        public void shutDown() throws  {
            this.m_shutDownRequested = true;
            if (this.m_thread != null) {
                this.m_thread.interrupt();
            }
        }

        public void run() throws  {
            while (!this.m_shutDownRequested) {
                try {
                    ByteBuffer $r3 = (ByteBuffer) this.m_queue.take();
                    int $i0 = $r3.getSize();
                    MCSDataLayerBase.this.writeDataInternal($r3.getData(), $i0);
                    this.m_semaphore.release($i0);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private class ConnClosedNotificationList extends NotificationList {
        private ConnClosedNotificationList() throws  {
        }

        public void Register(IMCSConnectionClosedNotification $r1) throws  {
            super.Register($r1);
        }

        public void NotifyConnectionClosed() throws  {
            int $i0 = Start();
            while (true) {
                IMCSConnectionClosedNotification $r3 = (IMCSConnectionClosedNotification) GetObject($i0);
                if ($r3 != null) {
                    $r3.onConnectionClosed(MCSDataLayerBase.this);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }
    }

    private class DataLayerNotificationList extends NotificationList {
        private DataLayerNotificationList() throws  {
        }

        public void Register(IMCSDataLayerNotification $r1) throws  {
            super.Register($r1);
        }

        public boolean NotifyDataReceived() throws  {
            int $i0 = Start();
            boolean $z0 = false;
            while (true) {
                IMCSDataLayerNotification $r3 = (IMCSDataLayerNotification) GetObject($i0);
                if ($r3 == null) {
                    return $z0;
                }
                $r3.onDataReceived(MCSDataLayerBase.this);
                $z0 = true;
                $i0 = GetNext($i0);
            }
        }
    }

    public boolean isReady() throws  {
        return true;
    }

    protected abstract void writeDataInternal(byte[] bArr, int i) throws ;

    public IMCSDataStats getDataStats() throws  {
        if (!MCSDataStatsImpl.isStatsEnabled()) {
            return null;
        }
        if (this.m_statistics == null) {
            this.m_statistics = new MCSDataStatsImpl();
        }
        return this.m_statistics;
    }

    public void registerCloseNotification(IMCSConnectionClosedNotification $r1) throws  {
        if ($r1 != null) {
            this.m_connCloseListeners.Register($r1);
        }
    }

    public void unregisterCloseNotification(IMCSConnectionClosedNotification $r1) throws  {
        if ($r1 != null) {
            this.m_connCloseListeners.Unregister($r1);
        }
    }

    public void registerNotification(IMCSDataLayerNotification $r1) throws  {
        if ($r1 != null) {
            this.m_dataListeners.Register($r1);
            this.m_connCloseListeners.Register($r1);
        }
    }

    public void unRegisterNotification(IMCSDataLayerNotification $r1) throws  {
        if ($r1 != null) {
            this.m_dataListeners.Unregister($r1);
            this.m_connCloseListeners.Unregister($r1);
        }
    }

    public void writeData(byte[] $r1, int $i0) throws  {
        if (this.m_writeMode == EWriteMode.eBuffered) {
            BufferedWriter $r3 = this.m_writer;
            if (!$assertionsDisabled && $r3 == null) {
                throw new AssertionError();
            } else if ($r3 != null) {
                $r3.addBuffer(new ByteBuffer($r1, $i0));
                return;
            } else {
                return;
            }
        }
        synchronized (this) {
            writeDataInternal($r1, $i0);
        }
    }

    public void setWriteMode(EWriteMode $r1, int $i0) throws  {
        if (this.m_writeMode == EWriteMode.eBuffered && $i0 <= 0) {
            $i0 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        synchronized (this.m_writeMode) {
            if ($r1 != this.m_writeMode && this.m_writeMode == EWriteMode.eBuffered) {
                if (!$assertionsDisabled && this.m_writer == null) {
                    throw new AssertionError();
                } else if (this.m_writer != null) {
                    this.m_writer.shutDown();
                    this.m_writer = null;
                }
            }
            this.m_writeMode = $r1;
            if (this.m_writeMode == EWriteMode.eBuffered && this.m_writer == null) {
                this.m_writer = new BufferedWriter($i0);
            }
        }
    }

    public EWriteMode getWriteMode() throws  {
        return this.m_writeMode;
    }

    public void sendConnect() throws  {
    }

    protected boolean notifyForData() throws  {
        return this.m_dataListeners.NotifyDataReceived();
    }

    protected void notifyForConnectionClosed() throws  {
        this.m_connCloseListeners.NotifyConnectionClosed();
        if (this.m_writer != null) {
            this.m_writer.shutDown();
            this.m_writer = null;
        }
    }

    protected void clearNotifiables() throws  {
        this.m_dataListeners.ClearAll();
        this.m_connCloseListeners.ClearAll();
    }
}
