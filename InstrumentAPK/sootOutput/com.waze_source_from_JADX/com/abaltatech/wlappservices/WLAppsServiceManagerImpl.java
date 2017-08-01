package com.abaltatech.wlappservices;

import android.os.RemoteException;
import android.util.Log;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceDiscoveryNotification;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager.Stub;
import dalvik.annotation.Signature;
import java.util.List;

public class WLAppsServiceManagerImpl extends Stub {
    protected static final String TAG = "WLAppsServiceManagerImpl";

    public void ping() throws RemoteException {
    }

    public boolean registerService(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) IWLAppsServiceHandler $r4) throws RemoteException {
        ServiceDescriptor $r5 = new ServiceDescriptor();
        $r5.setName($r1);
        $r5.setProtocols($r2);
        $r5.setServiceDescrType(EServiceDescriptorType.DynamicService);
        $r5.setWLAppsServiceHandler($r4);
        $r5.setServiceProxy(new ServiceProxy_Remote($r4, $r1, $r2));
        return SecureServiceManager.getInstance().registerService($r5, $r3);
    }

    public void findServiceByName(String $r1, String $r2, final IWLAppsServiceDiscoveryNotification $r3) throws RemoteException {
        if ($r3 == null) {
            throw new RemoteException("discoveryNotification cannot be null");
        }
        SecureServiceManager.getInstance().findServiceByName($r1, $r2, new IServiceDiscoveryNotification() {
            final IWLAppsServiceDiscoveryNotification m_notification = $r3;

            public boolean onServiceFound(ServiceProxy $r1, int index) throws  {
                IWLAppsServiceHandler $r3 = null;
                if ($r1 != null && ($r1 instanceof ServiceProxy_Remote)) {
                    ServiceProxy_Remote $r4 = (ServiceProxy_Remote) $r1;
                    if ($r4 != null) {
                        try {
                            $r3 = $r4.getHandler();
                        } catch (IllegalArgumentException $r2) {
                            Log.w(WLAppsServiceManagerImpl.TAG, "", $r2);
                            return true;
                        }
                    }
                }
                if ($r3 != null) {
                    throw new UnsupportedOperationException("please implement this");
                }
                throw new UnsupportedOperationException("please implement this");
            }

            public void onServiceDiscoveryFailed(EServiceDiscoveryErrorCode $r1) throws  {
                try {
                    this.m_notification.onServiceDiscoveryFailed($r1.toString());
                } catch (RemoteException $r2) {
                    Log.w(WLAppsServiceManagerImpl.TAG, "onServiceDiscoveryFailed", $r2);
                }
            }

            public void onServiceDiscoveryCompleted(int $i0) throws  {
                try {
                    this.m_notification.onServiceDiscoveryCompleted($i0);
                } catch (RemoteException $r1) {
                    Log.w(WLAppsServiceManagerImpl.TAG, "onServiceDiscoveryCompleted", $r1);
                }
            }
        });
    }
}
