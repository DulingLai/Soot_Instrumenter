package com.abaltatech.wlappservices;

import android.os.RemoteException;
import android.util.Log;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler.Stub;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceNotificationHandler;
import com.abaltatech.weblink.service.interfaces.IWLRequestSuccessCallback;
import com.abaltatech.weblink.service.interfaces.IWLServiceErrorCallback;
import java.util.HashMap;

class WLAppsServiceHandlerImpl extends Stub {
    protected static final String TAG = "WLAppsServiceHandlerImpl";
    private HashMap<IWLAppsServiceNotificationHandler, IServiceNotificationHandler> m_notificationsMap = new HashMap();
    private IServiceHandler m_serviceHandler;

    public WLAppsServiceHandlerImpl(IServiceHandler $r1) throws  {
        this.m_serviceHandler = $r1;
    }

    public boolean cancelRequest(int $i0) throws RemoteException {
        return this.m_serviceHandler.onCancelRequest($i0);
    }

    public int sendRequest(String $r1, String $r2, boolean $z0, byte[] $r3, final IWLRequestSuccessCallback $r4, final IWLServiceErrorCallback $r5) throws RemoteException {
        ServiceRequest $r7 = new ServiceRequest();
        $r7.setAllowExecuteInForeground($z0);
        $r7.setRequestBody($r3);
        $r7.setRequestMethod(ERequestMethod.valueOf($r2));
        return this.m_serviceHandler.onProcessRequest($r1, $r7, new IServiceResponseNotification() {
            public void onResponseReceived(ServiceRequest request, ServiceResponse $r2) throws  {
                try {
                    $r4.onSuccess($r2.getRequestID(), $r2.getResponseBody());
                } catch (RemoteException $r3) {
                    Log.w(WLAppsServiceHandlerImpl.TAG, "successCallbackParam", $r3);
                }
            }

            public void onRequestFailed(ServiceRequest request, EServiceErrorCode $r2) throws  {
                try {
                    $r5.onError(0, $r2.toString());
                } catch (RemoteException $r3) {
                    Log.w(WLAppsServiceHandlerImpl.TAG, "errorCallbackParam", $r3);
                }
            }
        });
    }

    public void registerForNotification(final String $r1, final IWLAppsServiceNotificationHandler $r2) throws RemoteException {
        C04572 $r3 = new IServiceNotificationHandler() {
            public void onNotification(String $r1, byte[] $r2) throws  {
                if ($r1.compareTo($r1) == 0) {
                    try {
                        $r2.onNotification($r1, $r2);
                    } catch (RemoteException $r3) {
                        Log.w(WLAppsServiceHandlerImpl.TAG, "Error sending notification", $r3);
                    }
                }
            }
        };
        this.m_serviceHandler.registerForNotification($r1, $r3);
        synchronized (this.m_notificationsMap) {
            this.m_notificationsMap.put($r2, $r3);
        }
    }

    public void unregisterFromNotification(String $r1, IWLAppsServiceNotificationHandler $r2) throws RemoteException {
        synchronized (this.m_notificationsMap) {
            IServiceNotificationHandler $r6 = (IServiceNotificationHandler) this.m_notificationsMap.remove($r2);
        }
        if ($r6 != null) {
            this.m_serviceHandler.unregisterFromNotification($r1, $r6);
        }
    }
}
