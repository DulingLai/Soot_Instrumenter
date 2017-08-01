package com.abaltatech.mcp.weblink.sdk.mcs;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.RemoteException;
import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.abaltatech.mcp.mcs.common.IMCSConnectionClosedNotification;
import com.abaltatech.mcp.mcs.common.IMCSDataLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayer;
import com.abaltatech.mcp.mcs.common.IMCSMultiPointLayerNotification;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.mcs.utils.NotificationList;
import com.abaltatech.mcp.weblink.sdk.internal.WLUtils;
import com.abaltatech.mcp.weblink.servercore.WebLinkServerCore;
import com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler;
import com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler.Stub;
import com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionNotification;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"Assert"})
public class WLMultiPointLayer implements IMCSMultiPointLayer {
    static final /* synthetic */ boolean $assertionsDisabled = (!WLMultiPointLayer.class.desiredAssertionStatus());
    private static final String TAG = "WLMultiPointLayer";
    public static final WLMultiPointLayer instance = new WLMultiPointLayer();
    private final IMCSConnectionClosedNotification m_closeNotification = new C03661();
    private final ServiceConnection m_connection = new C03672();
    private final List<WLVirtualConnectionPoint> m_connections = new ArrayList();
    private Context m_context;
    private boolean m_dumpInfo = false;
    private IWLVirtualConnectionHandler m_handler;
    private boolean m_isBound;
    private final MultiPointLayerNotificationList m_multiPointNotifications = new MultiPointLayerNotificationList();
    private final IWLVirtualConnectionNotification m_notification = new C03683();
    private final List<ITransportNotification> m_transportNotifications = new ArrayList();

    class C03661 implements IMCSConnectionClosedNotification {
        C03661() throws  {
        }

        public void onConnectionClosed(IMCSDataLayer $r1) throws  {
            if (WLMultiPointLayer.this.removeConnection($r1)) {
                WLVirtualConnectionPoint $r3 = (WLVirtualConnectionPoint) $r1;
                WLMultiPointLayer.this.m_multiPointNotifications.NotifyConnectionClosed($r1, WLUtils.extractAddress($r3.getFromAddress()), WLUtils.extractAddress($r3.getToAddress()));
            }
        }
    }

    class C03672 implements ServiceConnection {
        C03672() throws  {
        }

        public synchronized void onServiceConnected(ComponentName className, IBinder $r2) throws  {
            WLMultiPointLayer.this.m_handler = Stub.asInterface($r2);
            try {
                WLMultiPointLayer.this.m_handler.registerNotification(WLMultiPointLayer.this.m_notification);
            } catch (RemoteException $r3) {
                MCSLogger.log(WLMultiPointLayer.TAG, "registerNotification: ", $r3);
            }
        }

        public synchronized void onServiceDisconnected(ComponentName className) throws  {
            WLMultiPointLayer.this.m_handler = null;
            WLMultiPointLayer.this.shutDown();
        }
    }

    class C03683 extends IWLVirtualConnectionNotification.Stub {
        C03683() throws  {
        }

        public void onTransportAttached() throws RemoteException {
            synchronized (WLMultiPointLayer.this.m_transportNotifications) {
                for (ITransportNotification onTransportAttached : WLMultiPointLayer.this.m_transportNotifications) {
                    onTransportAttached.onTransportAttached();
                }
            }
        }

        public void onTransportDetached() throws RemoteException {
            synchronized (WLMultiPointLayer.this.m_transportNotifications) {
                for (ITransportNotification onTransportDetached : WLMultiPointLayer.this.m_transportNotifications) {
                    onTransportDetached.onTransportDetached();
                }
            }
            WLMultiPointLayer.this.shutDown();
        }

        public boolean onVirtualConnectionData(byte[] $r1, byte[] $r2, byte[] $r3) throws RemoteException {
            WLVirtualConnectionPoint $r5 = WLMultiPointLayer.this.findConnection($r1, $r2);
            if ($r5 != null) {
                $r5.readDataInternal($r3);
                return true;
            }
            IMCSConnectionAddress $r6 = WLUtils.extractAddress($r1);
            IMCSConnectionAddress $r7 = WLUtils.extractAddress($r2);
            if (WLMultiPointLayer.this.m_dumpInfo) {
                MCSLogger.log(WLMultiPointLayer.TAG, "Received new connection request (from='" + $r6 + "' to='" + $r7 + "')");
            }
            boolean $z0 = false;
            if (!($r6 == null || $r7 == null)) {
                WLMultiPointLayer.this.m_multiPointNotifications.NotifyNewConnectionRequested($r6, $r7, null);
                $r5 = WLMultiPointLayer.this.findConnection($r1, $r2);
                if ($r5 != null) {
                    if (WLMultiPointLayer.this.m_dumpInfo) {
                        MCSLogger.log(WLMultiPointLayer.TAG, "Received " + $r3.length + " bytes (from='" + $r6 + "' to='" + $r7 + "')");
                    }
                    $r5.readDataInternal($r3);
                    $z0 = true;
                } else {
                    MCSLogger.log(WLMultiPointLayer.TAG, "The connection request has been rejected (from='" + $r6 + "' to='" + $r7 + "')");
                }
            }
            if ($z0) {
                return $z0;
            }
            synchronized (this) {
                if (WLMultiPointLayer.this.m_handler != null) {
                    try {
                        WLMultiPointLayer.this.m_handler.closeVirtualConnection($r1, $r2);
                    } catch (RemoteException e) {
                    }
                }
            }
            return $z0;
        }

        public void onVirtualConnectionClosed(byte[] $r1, byte[] $r2) throws RemoteException {
            WLVirtualConnectionPoint $r4 = WLMultiPointLayer.this.findConnection($r1, $r2);
            MCSLogger.log(WLMultiPointLayer.TAG, "Close connection requested (from='" + WLUtils.extractAddress($r1) + "' to='" + WLUtils.extractAddress($r2) + "')");
            if ($r4 != null) {
                $r4.closeRequested();
                return;
            }
            MCSLogger.log(WLMultiPointLayer.TAG, "Connection is not found or already closed (from='" + WLUtils.extractAddress($r1) + "' to='" + WLUtils.extractAddress($r2) + "')");
        }
    }

    public interface ITransportNotification {
        void onTransportAttached() throws ;

        void onTransportDetached() throws ;
    }

    private class MultiPointLayerNotificationList extends NotificationList {
        private MultiPointLayerNotificationList() throws  {
        }

        public void Register(IMCSMultiPointLayerNotification $r1) throws  {
            super.Register($r1);
        }

        public void NotifyNewConnectionRequested(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2, byte[] $r3) throws  {
            int $i0 = Start();
            while (true) {
                IMCSMultiPointLayerNotification $r6 = (IMCSMultiPointLayerNotification) GetObject($i0);
                if ($r6 != null) {
                    $r6.newConnectionRequested(WLMultiPointLayer.this, $r1, $r2, $r3);
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

    public IMCSDataLayer getDataLayer() throws  {
        return null;
    }

    public synchronized boolean init(Context $r1) throws  {
        boolean $z0;
        $z0 = false;
        if (this.m_context == null && $r1 != null) {
            this.m_context = $r1;
            this.m_isBound = bindService(this.m_context, WebLinkServerCore.WLSERVICE_IWLVIRTUALCONNECTIONHANDLER, this.m_connection);
            $z0 = this.m_isBound;
            if (!$z0) {
                terminate();
                this.m_context = $r1;
            }
        }
        return $z0;
    }

    public synchronized void terminate() throws  {
        if (this.m_context != null) {
            if (this.m_isBound) {
                this.m_context.unbindService(this.m_connection);
                this.m_isBound = false;
            }
            this.m_context = null;
        }
        shutDown();
    }

    public void registerTransportNotification(ITransportNotification $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_transportNotifications) {
                this.m_transportNotifications.add($r1);
            }
        }
    }

    public void unregisterTransportNotification(ITransportNotification $r1) throws  {
        if ($r1 != null) {
            synchronized (this.m_transportNotifications) {
                this.m_transportNotifications.remove($r1);
            }
        }
    }

    public boolean isInitialized() throws  {
        return this.m_isBound;
    }

    public IMCSDataLayer createConnectionPoint(IMCSConnectionAddress $r1, IMCSConnectionAddress $r2) throws MCSException {
        Throwable $r10;
        if (this.m_handler == null) {
            return null;
        }
        byte[] $r6 = WLUtils.extractData($r1);
        byte[] $r7 = WLUtils.extractData($r2);
        if ($r6 != null && $r7 != null) {
            WLVirtualConnectionPoint $r3 = findConnection($r6, $r7);
            if ($r3 != null) {
                return $r3;
            }
            synchronized (this.m_connections) {
                try {
                    $r3 = new WLVirtualConnectionPoint($r6, $r7, this.m_handler);
                    try {
                        this.m_connections.add($r3);
                        $r3.registerCloseNotification(this.m_closeNotification);
                        return $r3;
                    } catch (Throwable th) {
                        $r10 = th;
                        throw $r10;
                    }
                } catch (Throwable th2) {
                    $r10 = th2;
                    throw $r10;
                }
            }
        } else if ($assertionsDisabled) {
            return null;
        } else {
            throw new AssertionError();
        }
    }

    public void closeConnection(IMCSDataLayer $r1) throws MCSException {
        $r1.closeConnection();
    }

    public void registerNotification(IMCSMultiPointLayerNotification $r1) throws  {
        if ($r1 != null) {
            this.m_multiPointNotifications.Register($r1);
        }
    }

    public void unRegisterNotification(IMCSMultiPointLayerNotification $r1) throws  {
        if ($r1 != null) {
            this.m_multiPointNotifications.Unregister($r1);
        }
    }

    public void attachToLayer(IMCSDataLayer dataLayer) throws  {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public void setDumpInfo(boolean $z0) throws  {
        this.m_dumpInfo = $z0;
    }

    private WLMultiPointLayer() throws  {
    }

    private void shutDown() throws  {
        synchronized (this.m_connections) {
            ArrayList<WLVirtualConnectionPoint> $r1 = new ArrayList(this.m_connections);
        }
        for (WLVirtualConnectionPoint closeConnection : $r1) {
            closeConnection.closeConnection();
        }
    }

    private WLVirtualConnectionPoint findConnection(byte[] $r1, byte[] $r2) throws  {
        WLVirtualConnectionPoint $r4 = null;
        synchronized (this.m_connections) {
            for (WLVirtualConnectionPoint $r8 : this.m_connections) {
                if ($r8.checkForAddressMatch($r1, $r2)) {
                    $r4 = $r8;
                    break;
                }
            }
        }
        return $r4;
    }

    private boolean removeConnection(IMCSDataLayer $r1) throws  {
        boolean $z1 = false;
        synchronized (this.m_connections) {
            if (this.m_connections.remove($r1)) {
                $r1.unregisterCloseNotification(this.m_closeNotification);
                $z1 = true;
            }
        }
        return $z1;
    }

    public static final boolean bindService(Context $r0, String $r1, ServiceConnection $r2) throws  {
        Intent $r4 = new Intent($r1);
        ResolveInfo $r7 = $r0.getPackageManager().resolveService($r4, 0);
        if ($r7 == null) {
            return false;
        }
        ServiceInfo $r5 = $r7.serviceInfo;
        $r4.setComponent(new ComponentName($r5.packageName, $r5.name));
        return $r0.bindService($r4, $r2, 1);
    }

    public void detachFromLayer(IMCSDataLayer dataLayer) throws  {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }
}
