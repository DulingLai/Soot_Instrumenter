package com.abaltatech.mcs.common;

import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.NotificationList;

public abstract class MCSMultiPointLayerBase implements IMCSDataLayerNotification, IMCSMultiPointLayer {
    protected IMCSDataLayer m_dataLayer;
    protected boolean m_dumpInfo;
    protected MultiPointLayerNotificationList m_notifiables = new MultiPointLayerNotificationList();

    private class MultiPointLayerNotificationList extends NotificationList {
        private MultiPointLayerNotificationList() throws  {
        }

        public void Register(IMCSMultiPointLayerNotification $r1) throws  {
            super.Register($r1);
        }

        public void NotifyNewConnectionRequested(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, byte[] $r3) throws  {
            int $i0 = Start();
            while (true) {
                IMCSMultiPointLayerNotification $r5 = (IMCSMultiPointLayerNotification) GetObject($i0);
                if ($r5 != null) {
                    $r5.newConnectionRequested($r1, $r2, $r3);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }

        public void NotifyConnectionClosed(IMCSDataLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3) throws  {
            int $i0 = Start();
            while (true) {
                IMCSMultiPointLayerNotification $r5 = (IMCSMultiPointLayerNotification) GetObject($i0);
                if ($r5 != null) {
                    $r5.onConnectionClosed($r1, $r2, $r3);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }
    }

    public void registerNotification(IMCSMultiPointLayerNotification $r1) throws  {
        if ($r1 != null) {
            this.m_notifiables.Register($r1);
        }
    }

    public void unRegisterNotification(IMCSMultiPointLayerNotification $r1) throws  {
        if ($r1 != null) {
            this.m_notifiables.Unregister($r1);
        }
    }

    public void attachToLayer(IMCSDataLayer $r1) throws  {
        synchronized (this) {
            if (this.m_dataLayer != null) {
                this.m_dataLayer.unRegisterNotification(this);
                this.m_dataLayer = null;
            }
            this.m_dataLayer = $r1;
            if ($r1 != null) {
                $r1.registerNotification(this);
            }
        }
    }

    public IMCSDataLayer getDataLayer() throws  {
        return this.m_dataLayer;
    }

    protected void notifyForConnectionClosed(IMCSDataLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3) throws  {
        this.m_notifiables.NotifyConnectionClosed($r1, $r2, $r3);
    }

    protected void notifyForNewConnection(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, byte[] $r3) throws  {
        this.m_notifiables.NotifyNewConnectionRequested($r1, $r2, $r3);
    }

    public synchronized void setDumpInfo(boolean $z0) throws  {
        this.m_dumpInfo = $z0;
    }

    public synchronized boolean getDumpInfo() throws  {
        return this.m_dumpInfo;
    }

    public void dumpInfo(String $r1, String $r2) throws  {
        if (getDumpInfo()) {
            MCSLogger.log($r1, $r2);
        }
    }
}
