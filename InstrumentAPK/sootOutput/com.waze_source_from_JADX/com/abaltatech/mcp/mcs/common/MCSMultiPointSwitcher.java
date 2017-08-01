package com.abaltatech.mcp.mcs.common;

import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.utils.NotificationList;
import java.util.ArrayList;
import java.util.Iterator;

public class MCSMultiPointSwitcher implements IConnectionListener, IConnectionListenerNotification, IDatagramHandler, IDatagramReceiver, IMCSMultiPointLayer, IMCSMultiPointLayerNotification, IResolveAddress, IResolveAddressRequestedNotification {
    private static final String TAG = MCSMultiPointSwitcher.class.getSimpleName();
    protected IMCSMultiPointLayer m_activeMultiPointLayer = null;
    protected ConnectionListenerNotificationList m_connectionNotificationList = new ConnectionListenerNotificationList();
    protected ArrayList<IMCSMultiPointLayer> m_multiLayerlist = new ArrayList();
    protected MultiPointLayerNotificationList m_notifiables = new MultiPointLayerNotificationList();
    protected ResolveAddressRequestedNotificationList m_resolveAddressNotificaitonList = new ResolveAddressRequestedNotificationList();

    private static class ConnectionListenerNotificationList extends NotificationList {
        private ConnectionListenerNotificationList() throws  {
        }

        public IMCSConnectionAddress NotifyOnStartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
            IMCSConnectionAddress $r3 = null;
            int $i1 = Start();
            while ($r3 == null) {
                IConnectionListenerNotification $r5 = (IConnectionListenerNotification) GetObject($i1);
                if ($r5 == null) {
                    return $r3;
                }
                $r3 = $r5.OnStartListening($r1, $i0, $r2);
                $i1 = GetNext($i1);
            }
            return $r3;
        }

        public void NotifyOnStopListening(IMCSConnectionAddress $r1) throws  {
            int $i0 = Start();
            while (true) {
                IConnectionListenerNotification $r3 = (IConnectionListenerNotification) GetObject($i0);
                if ($r3 != null) {
                    $r3.OnStopListening($r1);
                    $i0 = GetNext($i0);
                } else {
                    return;
                }
            }
        }
    }

    private static class MultiPointLayerNotificationList extends NotificationList {
        private MultiPointLayerNotificationList() throws  {
        }

        public void NotifyNewConnectionRequested(IMCSMultiPointLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3, byte[] $r4) throws  {
            int $i0 = Start();
            while (true) {
                IMCSMultiPointLayerNotification $r6 = (IMCSMultiPointLayerNotification) GetObject($i0);
                if ($r6 != null) {
                    $r6.newConnectionRequested($r1, $r2, $r3, $r4);
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

    private static class ResolveAddressRequestedNotificationList extends NotificationList {
        private ResolveAddressRequestedNotificationList() throws  {
        }

        public boolean NotifyOnResolveAddressRequested(IResolveAddress $r1, int $i0, String $r2) throws  {
            int $i1 = Start();
            boolean $z0 = false;
            while (true) {
                IResolveAddressRequestedNotification $r4 = (IResolveAddressRequestedNotification) GetObject($i1);
                if ($r4 == null) {
                    return $z0;
                }
                if (true == $r4.OnResolveAddressRequested($r1, $i0, $r2)) {
                    $z0 = true;
                }
                $i1 = GetNext($i1);
            }
        }
    }

    public int SendResolveAddressRequest(String name) throws  {
        return 0;
    }

    public boolean SendResolveAddressResponse(int requestID, String name, IMCSConnectionAddress address) throws  {
        return false;
    }

    public int StartDatagramListening(IMCSConnectionAddress listenAddress, IDatagramReceiver receiver) throws  {
        return 0;
    }

    public synchronized void attachToLayer(IMCSMultiPointLayer $r1) throws  {
        if ($r1 != null) {
            $r1.registerNotification(this);
            synchronized (this.m_multiLayerlist) {
                this.m_multiLayerlist.add($r1);
            }
            if ($r1 instanceof IConnectionListener) {
                ((IConnectionListener) $r1).UnregisterNotification(this);
            }
            if ($r1 instanceof IResolveAddress) {
                ((IResolveAddress) $r1).UnregisterNotification((IResolveAddressRequestedNotification) this);
            }
        } else {
            MCSLogger.log(TAG, "attachToLayer: cannot attach null");
        }
    }

    public synchronized void detachFromLayer(IMCSMultiPointLayer $r1) throws  {
        if ($r1 != null) {
            $r1.unRegisterNotification(this);
            synchronized (this.m_multiLayerlist) {
                this.m_multiLayerlist.remove($r1);
            }
        } else {
            MCSLogger.log(TAG, "detachFromLayer: cannot detach null");
        }
    }

    public synchronized void newConnectionRequested(IMCSMultiPointLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3, byte[] $r4) throws  {
        this.m_activeMultiPointLayer = $r1;
        this.m_notifiables.NotifyNewConnectionRequested($r1, $r2, $r3, $r4);
    }

    public void onConnectionClosed(IMCSDataLayer $r1, IMCSConnectionAddress $r2, IMCSConnectionAddress $r3) throws  {
        this.m_notifiables.NotifyConnectionClosed($r1, $r2, $r3);
    }

    public synchronized IMCSDataLayer createConnectionPoint(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2) throws MCSException {
        IMCSDataLayer $r3;
        $r3 = null;
        if (this.m_activeMultiPointLayer != null) {
            if (this.m_activeMultiPointLayer.getDataLayer() == null) {
                synchronized (this.m_multiLayerlist) {
                    Iterator $r7 = this.m_multiLayerlist.iterator();
                    while ($r7.hasNext()) {
                        IMCSMultiPointLayer $r4 = (IMCSMultiPointLayer) $r7.next();
                        if ($r4.getDataLayer() != null) {
                            this.m_activeMultiPointLayer = $r4;
                            break;
                        }
                    }
                }
            }
            $r3 = this.m_activeMultiPointLayer.createConnectionPoint($r1, $r2);
        } else {
            MCSLogger.log(TAG, "createConnectionPoint: null active Multi Point layer");
        }
        return $r3;
    }

    public void closeConnection(IMCSDataLayer $r1) throws MCSException {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                ((IMCSMultiPointLayer) $r4.next()).closeConnection($r1);
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

    public void setDumpInfo(boolean allow) throws  {
    }

    public void attachToLayer(IMCSDataLayer dataLayer) throws  {
    }

    public void detachFromLayer(IMCSDataLayer dataLayer) throws  {
    }

    public synchronized IMCSDataLayer getDataLayer() throws  {
        IMCSDataLayer $r2;
        $r2 = null;
        if (this.m_activeMultiPointLayer != null) {
            IMCSDataLayer $r4 = this.m_activeMultiPointLayer.getDataLayer();
            $r2 = $r4;
            if ($r4 == null) {
                synchronized (this.m_multiLayerlist) {
                    Iterator $r6 = this.m_multiLayerlist.iterator();
                    while ($r6.hasNext()) {
                        $r4 = ((IMCSMultiPointLayer) $r6.next()).getDataLayer();
                        $r2 = $r4;
                        if ($r4 != null) {
                            break;
                        }
                    }
                }
            }
        }
        return $r2;
    }

    public boolean OnResolveAddressRequested(IResolveAddress $r1, int $i0, String $r2) throws  {
        return this.m_resolveAddressNotificaitonList.NotifyOnResolveAddressRequested($r1, $i0, $r2);
    }

    public IMCSConnectionAddress OnStartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
        return this.m_connectionNotificationList.NotifyOnStartListening($r1, $i0, $r2);
    }

    public void OnStopListening(IMCSConnectionAddress $r1) throws  {
        this.m_connectionNotificationList.NotifyOnStopListening($r1);
    }

    public IMCSConnectionAddress StartListening(IMCSConnectionAddress $r1, int $i0, IConnectionReceiver $r2) throws  {
        IMCSConnectionAddress $r4 = null;
        synchronized (this.m_multiLayerlist) {
            Iterator $r6 = this.m_multiLayerlist.iterator();
            while ($r6.hasNext()) {
                IMCSMultiPointLayer $r8 = (IMCSMultiPointLayer) $r6.next();
                if ($r8 instanceof IConnectionListener) {
                    $r4 = ((IConnectionListener) $r8).StartListening($r1, $i0, $r2);
                    continue;
                }
                if ($r4 != null) {
                    break;
                }
            }
        }
        return $r4;
    }

    public void StopListening(IMCSConnectionAddress $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IConnectionListener) {
                    ((IConnectionListener) $r6).StopListening($r1);
                }
            }
        }
    }

    public void RegisterNotification(IConnectionListenerNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IConnectionListener) {
                    ((IConnectionListener) $r6).RegisterNotification($r1);
                }
            }
        }
    }

    public void UnregisterNotification(IConnectionListenerNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IConnectionListener) {
                    ((IConnectionListener) $r6).UnregisterNotification($r1);
                }
            }
        }
    }

    public void RegisterNotification(IResolveAddressRequestedNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IResolveAddress) {
                    ((IResolveAddress) $r6).RegisterNotification($r1);
                }
            }
        }
    }

    public void UnregisterNotification(IResolveAddressRequestedNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IResolveAddress) {
                    ((IResolveAddress) $r6).UnregisterNotification($r1);
                }
            }
        }
    }

    public void RegisterNotification(IResolveAddressCompletedNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IResolveAddress) {
                    ((IResolveAddress) $r6).RegisterNotification($r1);
                }
            }
        }
    }

    public void UnregisterNotification(IResolveAddressCompletedNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IResolveAddress) {
                    ((IResolveAddress) $r6).UnregisterNotification($r1);
                }
            }
        }
    }

    public void StopDatagramListening(int connID) throws  {
    }

    public void SendDatagram(int connID, IMCSConnectionAddress destAddr, byte[] buffer, int size) throws  {
    }

    public void RegisterNotification(IDatagramHandlerNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IDatagramHandler) {
                    ((IDatagramHandler) $r6).RegisterNotification($r1);
                }
            }
        }
    }

    public void UnregisterNotification(IDatagramHandlerNotification $r1) throws  {
        synchronized (this.m_multiLayerlist) {
            Iterator $r4 = this.m_multiLayerlist.iterator();
            while ($r4.hasNext()) {
                IMCSMultiPointLayer $r6 = (IMCSMultiPointLayer) $r4.next();
                if ($r6 instanceof IDatagramHandler) {
                    ((IDatagramHandler) $r6).UnregisterNotification($r1);
                }
            }
        }
    }

    public void OnDatagramReceived(int connID, IMCSConnectionAddress address, byte[] buffer, int size) throws  {
    }
}
