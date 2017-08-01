package com.abaltatech.wlappservices;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import com.abaltatech.mcp.weblink.core.commandhandling.Command;
import com.abaltatech.mcp.weblink.core.commandhandling.ICommandHandler;
import com.abaltatech.mcp.weblink.core.commandhandling.IWLConnection;
import com.abaltatech.mcp.weblink.servercore.WebLinkServerCore;
import com.abaltatech.mcp.weblink.utils.AppUtils;
import com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService;
import com.abaltatech.weblink.service.interfaces.IWLAppServiceCommandReceiver.Stub;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceDispatcher implements ICommandHandler {
    private static final byte[] EmptyResponseBody = new byte[0];
    public static final String MasterServiceAppID = "wlmasterservicedispatcher://";
    private static final String TAG = "ServiceDispatcher";
    private static final String WLCLIENT_ID = "wlclient://";
    private String m_appName = "";
    private CommandReceiver m_commandReceiver;
    private SparseArray<ServiceProxyWrapper> m_idToProxyMap = new SparseArray();
    private IWLAppDispatcherService m_masterService;
    private int m_nextProxyID = Integer.MIN_VALUE;
    private HashMap<String, SparseArray<NotificationWrapper>> m_notificationMap = new HashMap();
    private HashMap<ServiceProxy, ServiceProxyWrapper> m_proxyToWrapperMap = new HashMap();
    private HashMap<String, SparseArray<RequestWrapper>> m_requestMap = new HashMap();
    private HashMap<String, SparseArray<ServiceProxy_Dispatcher>> m_servicesMap = new HashMap();
    private HashMap<String, SparseArray<StatusNotificationWrapper>> m_statusNotificationMap = new HashMap();
    private ServiceDispatcherType m_type;
    private IWLConnection m_wlConnection;

    private class CommandReceiver extends Stub {
        private CommandReceiver() throws  {
        }

        public void onCommandReceived(byte[] $r1) throws RemoteException {
            WLServiceCommand $r3 = new WLServiceCommand(new DataBuffer($r1, 0, $r1.length));
            short $s1 = $r3.getCommandID();
            switch ($s1) {
                case (short) 97:
                    SecureServiceManager.getInstance().processServiceDescriptorCommand($r3);
                    return;
                case (short) 98:
                    ServiceDispatcher.this.onSendRequestCommand($r3);
                    return;
                case (short) 99:
                    SecureServiceManager.getInstance().processServiceResponseCommand($r3);
                    return;
                case (short) 100:
                    ServiceDispatcher.this.onCancelRequestCommand($r3);
                    return;
                case (short) 101:
                    ServiceDispatcher.this.onRegisterNotificationCommand($r3);
                    return;
                case (short) 102:
                    ServiceDispatcher.this.onUnregisterNotificationCommand($r3);
                    return;
                case (short) 103:
                    SecureServiceManager.getInstance().processServiceNotificationCommand($r3);
                    return;
                case (short) 104:
                case (short) 105:
                case (short) 106:
                case (short) 107:
                case (short) 108:
                case (short) 109:
                case (short) 110:
                case (short) 111:
                    break;
                case (short) 112:
                    ServiceDispatcher.this.onRegisterStatusNotificationCommand($r3);
                    return;
                case (short) 113:
                    ServiceDispatcher.this.onUnregisterStatusNotificationCommand($r3);
                    return;
                case (short) 114:
                    SecureServiceManager.getInstance().processServiceStatusNotificationCommand($r3);
                    return;
                default:
                    break;
            }
            Log.e(ServiceDispatcher.TAG, "Unsupported command ID: " + $s1);
        }
    }

    private class NotificationWrapper {
        IServiceNotificationHandler m_handler;
        int m_notificationID;
        String m_resourcePath;
        String m_senderID;

        public NotificationWrapper(String receiverID, String $r3, int $i0, int descriptorID, String $r4) throws  {
            this.m_senderID = $r3;
            this.m_notificationID = $i0;
            this.m_resourcePath = $r4;
        }
    }

    private class RequestWrapper {
        int m_descriptorID;
        int m_myRequestID;
        int m_requestID;
        String m_senderID;

        public RequestWrapper(String receiverID, String $r3, int $i0, int $i1) throws  {
            this.m_senderID = $r3;
            this.m_requestID = $i0;
            this.m_descriptorID = $i1;
        }
    }

    public enum ServiceDispatcherType {
        WLMaster,
        WLApp,
        WLClient
    }

    private static class ServiceProxyWrapper {
        int m_id;
        ServiceProxy m_proxy;

        ServiceProxyWrapper(int $i0, ServiceProxy $r1) throws  {
            this.m_id = $i0;
            this.m_proxy = $r1;
        }
    }

    private class StatusNotificationWrapper {
        IServiceStatusNotification m_handler;
        int m_notificationID;
        String m_senderID;

        public StatusNotificationWrapper(String receiverID, String $r3, int $i0) throws  {
            this.m_senderID = $r3;
            this.m_notificationID = $i0;
        }
    }

    public ServiceDispatcher(ServiceDispatcherType $r1, final Context $r2) throws  {
        this.m_type = $r1;
        if ($r1 == null) {
            throw new NullPointerException("ServiceDispatcherType cannot be null");
        } else if (this.m_type == ServiceDispatcherType.WLMaster) {
            WLAppDispatcherServiceImpl.instance().setServiceDispatcher(this);
            Log.e(TAG, "Please implement this: connect internally to the service, bindWLClientChannel");
            Log.e(TAG, "Please implement this: connect internally to the service, bindWLClientChannel");
            Log.e(TAG, "Please implement this: connect internally to the service, bindWLClientChannel");
        } else if (this.m_type == ServiceDispatcherType.WLApp) {
            AppUtils.bindService($r2, WebLinkServerCore.WLSERVICE_IWLAPPDISPATCHERSERVICE, new ServiceConnection() {
                public void onServiceConnected(ComponentName className, IBinder $r2) throws  {
                    synchronized (ServiceDispatcher.this) {
                        ServiceDispatcher.this.m_masterService = IWLAppDispatcherService.Stub.asInterface($r2);
                        IWLAppDispatcherService $r6 = ServiceDispatcher.this.m_masterService;
                    }
                    if ($r6 != null) {
                        ServiceDispatcher.this.m_appName = AppUtils.getAppName($r2);
                        ServiceDispatcher.this.m_commandReceiver = new CommandReceiver();
                        try {
                            $r6.registerCommandReceiver(ServiceDispatcher.this.m_commandReceiver, ServiceDispatcher.this.m_appName);
                        } catch (RemoteException $r3) {
                            Log.e(ServiceDispatcher.TAG, "Error registering with the master service", $r3);
                            ServiceDispatcher.this.m_masterService = null;
                            ServiceDispatcher.this.m_commandReceiver = null;
                        }
                    }
                }

                public void onServiceDisconnected(ComponentName className) throws  {
                    synchronized (ServiceDispatcher.this) {
                        ServiceDispatcher.this.m_masterService = null;
                    }
                }
            });
        } else if (this.m_type == ServiceDispatcherType.WLClient) {
            this.m_appName = WLCLIENT_ID;
        } else {
            throw new UnsupportedOperationException("Unsupported ServiceDispatcherType: " + $r1);
        }
    }

    boolean sendWLServiceCommand(WLServiceCommand $r1) throws  {
        boolean $z0 = false;
        String $r3 = $r1.getReceiverID();
        if (this.m_type == ServiceDispatcherType.WLApp) {
            try {
                this.m_masterService.sendCommand(this.m_appName, $r1.getRawCommandData().getData());
                $z0 = true;
            } catch (Throwable $r2) {
                Log.e(TAG, "sendWLServiceCommand failed", $r2);
                $z0 = false;
            }
        } else if (this.m_type == ServiceDispatcherType.WLMaster) {
            if ($r3.compareTo(WLCLIENT_ID) == 0) {
                $r9 = this.m_wlConnection;
                if ($r9 != null) {
                    $z0 = $r9.sendCommand($r1);
                }
            } else {
                $z0 = WLAppDispatcherServiceImpl.instance().sendRemoteCommand($r1);
            }
        } else if (this.m_type == ServiceDispatcherType.WLClient) {
            $r9 = this.m_wlConnection;
            if ($r9 != null) {
                $z0 = $r9.sendCommand($r1);
            }
        } else {
            throw new UnsupportedOperationException("Please implement this!");
        }
        if ($z0) {
            return $z0;
        }
        Log.w(TAG, "sendWLServiceCommand failed!");
        return $z0;
    }

    void processWLServiceCommand(WLServiceCommand $r1) throws  {
        short $s0 = $r1.getCommandID();
        switch ($s0) {
            case (short) 96:
                onFindServiceCommand($r1);
                return;
            case (short) 97:
                SecureServiceManager.getInstance().processServiceDescriptorCommand($r1);
                return;
            case (short) 98:
                onSendRequestCommand($r1);
                return;
            case (short) 99:
                SecureServiceManager.getInstance().processServiceResponseCommand($r1);
                return;
            case (short) 100:
                onCancelRequestCommand($r1);
                return;
            case (short) 101:
                onRegisterNotificationCommand($r1);
                return;
            case (short) 102:
                onUnregisterNotificationCommand($r1);
                return;
            case (short) 103:
                SecureServiceManager.getInstance().processServiceNotificationCommand($r1);
                return;
            case (short) 104:
                onRegisterServiceCommand($r1);
                return;
            case (short) 105:
                onUnregisterServiceCommand($r1);
                return;
            case (short) 106:
            case (short) 107:
            case (short) 108:
            case (short) 109:
            case (short) 110:
            case (short) 111:
                break;
            case (short) 112:
                onRegisterStatusNotificationCommand($r1);
                return;
            case (short) 113:
                onUnregisterStatusNotificationCommand($r1);
                return;
            case (short) 114:
                SecureServiceManager.getInstance().processServiceStatusNotificationCommand($r1);
                return;
            case (short) 115:
                onFindServiceByProtocol($r1);
                return;
            default:
                break;
        }
        Log.e(TAG, "Unsupported command ID: " + $s0);
    }

    private void onUnregisterServiceCommand(WLServiceCommand $r1) throws  {
        UnregisterServiceCommand $r2 = new UnregisterServiceCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            String $r4 = $r2.getReceiverID();
            String $r5 = $r2.getSenderID();
            int $i0 = $r2.getServiceDescriptorID();
            if ($r4.compareToIgnoreCase(MasterServiceAppID) == 0 || $r5.compareToIgnoreCase(MasterServiceAppID) == 0) {
                ServiceProxy_Dispatcher $r8 = null;
                synchronized (this.m_servicesMap) {
                    SparseArray $r12 = (SparseArray) this.m_servicesMap.get($r5);
                    if ($r12 != null) {
                        ServiceProxy_Dispatcher $r13 = (ServiceProxy_Dispatcher) $r12.get($i0);
                        $r8 = $r13;
                        if ($r13 != null) {
                            $r12.delete($i0);
                        }
                    }
                }
                if ($r8 == null) {
                    Log.e(TAG, "Invalid service to unregister!");
                    return;
                }
                SecureServiceManager.getInstance().unregisterService($r2.getServiceName());
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r4);
        }
    }

    private void onRegisterServiceCommand(WLServiceCommand $r1) throws  {
        RegisterServiceCommand $r3 = new RegisterServiceCommand($r1.getRawCommandData());
        if ($r3.isValid()) {
            String $r6 = $r3.getReceiverID();
            String $r7 = $r3.getSenderID();
            int $i0 = $r3.getServiceDescriptorID();
            if ($r6.compareToIgnoreCase(MasterServiceAppID) == 0 || $r7.compareToIgnoreCase(MasterServiceAppID) == 0) {
                ServiceProxy serviceProxy_Dispatcher = new ServiceProxy_Dispatcher($r3.getServiceName(), $r3.getProtocols(), $i0, $r7, $r6);
                ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
                serviceDescriptor.setName($r3.getServiceName());
                serviceDescriptor.setProtocols($r3.getProtocols());
                serviceDescriptor.setServiceDescrType(EServiceDescriptorType.DynamicService);
                serviceDescriptor.setServiceProxy(serviceProxy_Dispatcher);
                if (SecureServiceManager.getInstance().registerService(serviceDescriptor, $r3.getSenderID())) {
                    synchronized (this.m_servicesMap) {
                        HashMap $r15 = this.m_servicesMap;
                        SparseArray $r17 = (SparseArray) $r15.get($r7);
                        if ($r17 == null) {
                            SparseArray sparseArray = new SparseArray();
                            $r15 = this.m_servicesMap;
                            HashMap $r152 = $r15;
                            $r15.put($r7, sparseArray);
                        }
                        $r17.put($i0, serviceProxy_Dispatcher);
                    }
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r6);
        }
    }

    private void onUnregisterNotificationCommand(WLServiceCommand $r1) throws  {
        UnregisterNotificationCommand $r2 = new UnregisterNotificationCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            String $r6 = $r2.getReceiverID();
            String $r7 = $r2.getSenderID();
            int $i0 = $r2.getNotificationID();
            int $i1 = $r2.getDescriptorID();
            String $r4 = new String($r2.getResourcePath());
            if ($r6.compareToIgnoreCase(MasterServiceAppID) == 0 || $r7.compareToIgnoreCase(MasterServiceAppID) == 0) {
                ServiceProxy $r11 = getServiceProxy($i1);
                if ($r11 != null) {
                    NotificationWrapper $r12 = null;
                    synchronized (this.m_notificationMap) {
                        HashMap $r14 = this.m_notificationMap;
                        SparseArray $r16 = (SparseArray) $r14.get($r7);
                        if ($r16 != null) {
                            NotificationWrapper $r17 = (NotificationWrapper) $r16.get($i0);
                            $r12 = $r17;
                            if ($r17 == null || $r17.m_resourcePath.compareTo($r4) != 0) {
                                Log.e(TAG, "Incorrect path to unregister from a service: " + $r4);
                                $r12 = null;
                            } else {
                                $r16.remove($i0);
                            }
                        }
                    }
                    if ($r12 != null && $r12.m_handler != null) {
                        IServiceNotificationHandler $r3 = $r12.m_handler;
                        $r12.m_handler = null;
                        $r11.unregisterFromNotification($r4, $r3);
                        return;
                    }
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r6);
        }
    }

    private void onRegisterNotificationCommand(WLServiceCommand $r1) throws  {
        RegisterNotificationCommand $r5 = new RegisterNotificationCommand($r1.getRawCommandData());
        if ($r5.isValid()) {
            String $r7 = $r5.getReceiverID();
            String $r8 = $r5.getSenderID();
            int $i0 = $r5.getRegisterNotificationID();
            int $i1 = $r5.getDescriptorID();
            String $r3 = new String($r5.getResourcePath());
            if ($r7.compareToIgnoreCase(MasterServiceAppID) == 0 || $r8.compareToIgnoreCase(MasterServiceAppID) == 0) {
                ServiceProxy $r12 = getServiceProxy($i1);
                if ($r12 != null) {
                    NotificationWrapper notificationWrapper = new NotificationWrapper($r7, $r8, $i0, $i1, $r3);
                    final NotificationWrapper notificationWrapper2 = notificationWrapper;
                    final String str = $r7;
                    final String str2 = $r8;
                    final int i = $i1;
                    final int i2 = $i0;
                    C04462 c04462 = new IServiceNotificationHandler() {
                        public void onNotification(String $r1, byte[] $r2) throws  {
                            if (hasNotificationWrapper(notificationWrapper2.m_senderID, notificationWrapper2.m_notificationID)) {
                                ServiceDispatcher.this.sendWLServiceCommand(new ServiceNotificationCommand(str, str2, i, i2, $r1.getBytes(), $r2));
                            }
                        }

                        protected boolean hasNotificationWrapper(String $r1, int $i0) throws  {
                            boolean $z0 = false;
                            synchronized (ServiceDispatcher.this.m_notificationMap) {
                                SparseArray $r6 = (SparseArray) ServiceDispatcher.this.m_notificationMap.get($r1);
                                if ($r6 != null) {
                                    $z0 = $r6.get($i0) != null;
                                }
                            }
                            return $z0;
                        }
                    };
                    if ($r12.registerForNotification($r3, c04462)) {
                        synchronized (this.m_notificationMap) {
                            HashMap $r14 = this.m_notificationMap;
                            SparseArray $r16 = (SparseArray) $r14.get($r8);
                            if ($r16 == null) {
                                SparseArray sparseArray = new SparseArray();
                                $r14 = this.m_notificationMap;
                                HashMap $r142 = $r14;
                                $r14.put($r8, sparseArray);
                            }
                            $r16.append($i0, notificationWrapper);
                        }
                        notificationWrapper.m_handler = c04462;
                        return;
                    }
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r7);
        }
    }

    private void onRegisterStatusNotificationCommand(WLServiceCommand $r1) throws  {
        RegisterStatusNotificationCommand $r4 = new RegisterStatusNotificationCommand($r1.getRawCommandData());
        if ($r4.isValid()) {
            String $r6 = $r4.getReceiverID();
            String $r7 = $r4.getSenderID();
            int $i0 = $r4.getRegisterNotificationID();
            if ($r6.compareToIgnoreCase(MasterServiceAppID) == 0 || $r7.compareToIgnoreCase(MasterServiceAppID) == 0) {
                StatusNotificationWrapper statusNotificationWrapper = new StatusNotificationWrapper($r6, $r7, $i0);
                final StatusNotificationWrapper statusNotificationWrapper2 = statusNotificationWrapper;
                final String str = $r6;
                final String str2 = $r7;
                final int i = $i0;
                C04473 c04473 = new IServiceStatusNotification() {
                    public void onServiceRegistered(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2) throws  {
                        if (hasNotificationWrapper(statusNotificationWrapper2.m_senderID, statusNotificationWrapper2.m_notificationID)) {
                            ServiceDispatcher.this.sendWLServiceCommand(new ServiceStatusCommand(str, str2, $r1, $r2, true, i));
                        }
                    }

                    public void onServiceUnregistered(String $r1) throws  {
                        if (hasNotificationWrapper(statusNotificationWrapper2.m_senderID, statusNotificationWrapper2.m_notificationID)) {
                            ServiceDispatcher.this.sendWLServiceCommand(new ServiceStatusCommand(str, str2, $r1, new ArrayList(), false, i));
                        }
                    }

                    protected boolean hasNotificationWrapper(String $r1, int $i0) throws  {
                        boolean $z0 = false;
                        synchronized (ServiceDispatcher.this.m_statusNotificationMap) {
                            SparseArray $r6 = (SparseArray) ServiceDispatcher.this.m_statusNotificationMap.get($r1);
                            if ($r6 != null) {
                                $z0 = $r6.get($i0) != null;
                            }
                        }
                        return $z0;
                    }
                };
                statusNotificationWrapper.m_handler = c04473;
                if (SecureServiceManager.getInstance().registerForServiceStatusNotification(c04473)) {
                    synchronized (this.m_statusNotificationMap) {
                        HashMap $r12 = this.m_statusNotificationMap;
                        SparseArray $r14 = (SparseArray) $r12.get($r7);
                        if ($r14 == null) {
                            SparseArray sparseArray = new SparseArray();
                            $r12 = this.m_statusNotificationMap;
                            HashMap $r122 = $r12;
                            $r12.put($r7, sparseArray);
                        }
                        $r14.append($i0, statusNotificationWrapper);
                    }
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r6);
        }
    }

    private void onUnregisterStatusNotificationCommand(WLServiceCommand $r1) throws  {
        UnregisterStatusNotificationCommand $r2 = new UnregisterStatusNotificationCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            String $r5 = $r2.getReceiverID();
            String $r6 = $r2.getSenderID();
            int $i0 = $r2.getRegisterNotificationID();
            if ($r5.compareToIgnoreCase(MasterServiceAppID) == 0 || $r6.compareToIgnoreCase(MasterServiceAppID) == 0) {
                StatusNotificationWrapper $r9 = null;
                synchronized (this.m_statusNotificationMap) {
                    SparseArray $r13 = (SparseArray) this.m_statusNotificationMap.get($r6);
                    if ($r13 != null) {
                        StatusNotificationWrapper $r14 = (StatusNotificationWrapper) $r13.get($i0);
                        $r9 = $r14;
                        if ($r14 != null) {
                            $r13.remove($i0);
                        } else {
                            Log.e(TAG, "Incorrect path to unregister from a service: ");
                            $r9 = null;
                        }
                    }
                }
                if ($r9 != null && $r9.m_handler != null) {
                    IServiceStatusNotification $r3 = $r9.m_handler;
                    $r9.m_handler = null;
                    SecureServiceManager.getInstance().unRegisterForServiceStatusNotification($r3);
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r5);
        }
    }

    private void onCancelRequestCommand(WLServiceCommand $r1) throws  {
        CancelRequestCommand $r2 = new CancelRequestCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            String $r4 = $r2.getReceiverID();
            String $r5 = $r2.getSenderID();
            int $i0 = $r2.getRequestID();
            if ($r4.compareToIgnoreCase(MasterServiceAppID) == 0 || $r4.compareToIgnoreCase(this.m_appName) == 0) {
                RequestWrapper $r9 = removeWrapper($r5, $i0);
                if ($r9 != null) {
                    ServiceProxy $r10 = getServiceProxy($r9.m_descriptorID);
                    if ($r10 != null) {
                        $r10.cancelRequest($r9.m_myRequestID);
                        return;
                    }
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r4);
        }
    }

    private void onSendRequestCommand(WLServiceCommand $r1) throws  {
        SendRequestCommand $r3 = new SendRequestCommand($r1.getRawCommandData());
        if ($r3.isValid()) {
            String $r7 = $r3.getReceiverID();
            String $r8 = $r3.getSenderID();
            int $i0 = $r3.getRequestID();
            int $i1 = $r3.getDescriptorID();
            if ($r7.compareToIgnoreCase(MasterServiceAppID) == 0 || $r7.compareToIgnoreCase(this.m_appName) == 0) {
                ServiceProxy $r11 = getServiceProxy($i1);
                if ($r11 == null) {
                    sendResponse($r7, $r8, $i0, EmptyResponseBody, EServiceErrorCode.NotAvailable);
                    return;
                }
                ServiceRequest serviceRequest = new ServiceRequest();
                serviceRequest.setAllowExecuteInForeground($r3.getAllowExecuteInForeground());
                serviceRequest.setRequestBody($r3.getRequestBody());
                serviceRequest.setRequestMethod($r3.getRequestMethod());
                String $r5 = new String($r3.getResourcePath());
                RequestWrapper requestWrapper = new RequestWrapper($r7, $r8, $i0, $i1);
                final RequestWrapper requestWrapper2 = requestWrapper;
                final String str = $r7;
                final String str2 = $r8;
                final int i = $i0;
                C04484 c04484 = new IServiceResponseNotification() {
                    public void onResponseReceived(ServiceRequest request, ServiceResponse $r2) throws  {
                        if (ServiceDispatcher.this.removeWrapper(requestWrapper2.m_senderID, requestWrapper2.m_requestID) != null) {
                            byte[] $r6 = $r2.getResponseBody();
                            ServiceDispatcher $r3 = ServiceDispatcher.this;
                            String $r5 = str;
                            String $r7 = str2;
                            int $i0 = i;
                            if ($r6 == null) {
                                $r6 = ServiceDispatcher.EmptyResponseBody;
                            }
                            $r3.sendResponse($r5, $r7, $i0, $r6, null);
                        }
                    }

                    public void onRequestFailed(ServiceRequest request, EServiceErrorCode $r2) throws  {
                        if (ServiceDispatcher.this.removeWrapper(requestWrapper2.m_senderID, requestWrapper2.m_requestID) != null) {
                            byte[] $r6 = ServiceDispatcher.EmptyResponseBody;
                            ServiceDispatcher.this.sendResponse(str, str2, i, $r6, $r2);
                        }
                    }
                };
                synchronized (this.m_requestMap) {
                    HashMap $r17 = this.m_requestMap;
                    SparseArray $r19 = (SparseArray) $r17.get($r8);
                    if ($r19 == null) {
                        SparseArray sparseArray = new SparseArray();
                        $r17 = this.m_requestMap;
                        HashMap $r172 = $r17;
                        $r17.put($r8, sparseArray);
                    }
                    $r19.append($i0, requestWrapper);
                }
                requestWrapper.m_myRequestID = $r11.sendRequest($r5, serviceRequest, c04484);
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r7);
        }
    }

    private void sendResponse(String $r1, String $r2, int $i0, byte[] $r3, EServiceErrorCode $r4) throws  {
        sendWLServiceCommand(new ServiceResponseCommand($r1, $r2, $i0, $r3, $r4));
    }

    protected RequestWrapper removeWrapper(String $r1, int $i0) throws  {
        RequestWrapper $r3 = null;
        synchronized (this.m_requestMap) {
            SparseArray $r6 = (SparseArray) this.m_requestMap.get($r1);
            if ($r6 != null) {
                RequestWrapper $r7 = (RequestWrapper) $r6.get($i0);
                $r3 = $r7;
                if ($r7 != null) {
                    $r6.delete($i0);
                }
            }
        }
        return $r3;
    }

    private void onFindServiceByProtocol(WLServiceCommand $r1) throws  {
        FindServiceByProtocolCommand $r2 = new FindServiceByProtocolCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            final String $r5 = $r2.getReceiverID();
            final String $r6 = $r2.getSenderID();
            String $r7 = $r2.getProtocolName();
            final int $i0 = $r2.getSearchID();
            boolean $z0 = $r2.getSelectDefault();
            if ($r5.compareToIgnoreCase(MasterServiceAppID) == 0 || $r6.compareToIgnoreCase(MasterServiceAppID) == 0) {
                SecureServiceManager.getInstance().findServiceByProtocol($r6, $r7, new IServiceDiscoveryNotification() {
                    public boolean onServiceFound(ServiceProxy $r1, int $i0) throws  {
                        int $i2 = ServiceDispatcher.this.getProxyID($r1);
                        sendDescriptorCommand($r5, $r6, $i0, 1, $i2, $i0, $r1.getServiceName(), $r1.getProtocols());
                        return true;
                    }

                    public void onServiceDiscoveryFailed(EServiceDiscoveryErrorCode code) throws  {
                        sendDescriptorCommand($r5, $r6, $i0, 2, 0, 0, "", null);
                    }

                    public void onServiceDiscoveryCompleted(int $i0) throws  {
                        sendDescriptorCommand($r5, $r6, $i0, 3, $i0, 0, "", null);
                    }

                    private void sendDescriptorCommand(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r4) throws  {
                        ServiceDispatcher.this.sendWLServiceCommand(new ServiceDescriptorCommand($r1, $r2, $i0, $i1, $i2, $i3, $r3, $r4));
                    }
                }, $z0);
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r5);
        }
    }

    private void onFindServiceCommand(WLServiceCommand $r1) throws  {
        FindServiceCommand $r2 = new FindServiceCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            final String $r5 = $r2.getReceiverID();
            final String $r6 = $r2.getSenderID();
            String $r7 = $r2.getServiceName();
            final int $i0 = $r2.getSearchID();
            if ($r5.compareToIgnoreCase(MasterServiceAppID) == 0 || $r6.compareToIgnoreCase(MasterServiceAppID) == 0) {
                SecureServiceManager.getInstance().findServiceByName($r6, $r7, new IServiceDiscoveryNotification() {
                    public boolean onServiceFound(ServiceProxy $r1, int $i0) throws  {
                        int $i2 = ServiceDispatcher.this.getProxyID($r1);
                        sendDescriptorCommand($r5, $r6, $i0, 1, $i2, $i0, $r1.getServiceName(), $r1.getProtocols());
                        return true;
                    }

                    public void onServiceDiscoveryFailed(EServiceDiscoveryErrorCode code) throws  {
                        sendDescriptorCommand($r5, $r6, $i0, 2, 0, 0, "", null);
                    }

                    public void onServiceDiscoveryCompleted(int $i0) throws  {
                        sendDescriptorCommand($r5, $r6, $i0, 3, $i0, 0, "", null);
                    }

                    private void sendDescriptorCommand(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "IIII", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r4) throws  {
                        ServiceDispatcher.this.sendWLServiceCommand(new ServiceDescriptorCommand($r1, $r2, $i0, $i1, $i2, $i3, $r3, $r4));
                    }
                });
                return;
            }
            throw new UnsupportedOperationException("Receiver ID is incorrect: " + $r5);
        }
    }

    private synchronized int getProxyID(ServiceProxy $r1) throws  {
        int $i0;
        ServiceProxyWrapper $r4 = (ServiceProxyWrapper) this.m_proxyToWrapperMap.get($r1);
        if ($r4 == null) {
            this.m_nextProxyID++;
            $r4 = new ServiceProxyWrapper(this.m_nextProxyID, $r1);
            this.m_proxyToWrapperMap.put($r1, $r4);
            this.m_idToProxyMap.append(this.m_nextProxyID, $r4);
            $i0 = this.m_nextProxyID;
        } else {
            $i0 = $r4.m_id;
        }
        return $i0;
    }

    private synchronized ServiceProxy getServiceProxy(int $i0) throws  {
        ServiceProxy $r1;
        $r1 = null;
        ServiceProxyWrapper $r4 = (ServiceProxyWrapper) this.m_idToProxyMap.get($i0);
        if ($r4 != null) {
            $r1 = $r4.m_proxy;
        }
        if ($r1 == null) {
            $r1 = SecureServiceManager.getInstance().getLocalProxyDescriptor($i0);
        }
        return $r1;
    }

    void onConnectionEstablished(IWLConnection $r1) throws  {
        if (this.m_type == ServiceDispatcherType.WLMaster || this.m_type == ServiceDispatcherType.WLClient) {
            this.m_wlConnection = $r1;
            $r1.registerHandler((short) 96, this);
            $r1.registerHandler((short) 97, this);
            $r1.registerHandler((short) 98, this);
            $r1.registerHandler((short) 99, this);
            $r1.registerHandler((short) 100, this);
            $r1.registerHandler(RegisterNotificationCommand.ID, this);
            $r1.registerHandler(UnregisterNotificationCommand.ID, this);
            $r1.registerHandler(ServiceNotificationCommand.ID, this);
            $r1.registerHandler(RegisterServiceCommand.ID, this);
            $r1.registerHandler(UnregisterServiceCommand.ID, this);
            $r1.registerHandler(RegisterStatusNotificationCommand.ID, this);
            $r1.registerHandler(UnregisterStatusNotificationCommand.ID, this);
            $r1.registerHandler(ServiceStatusCommand.ID, this);
            $r1.registerHandler(FindServiceByProtocolCommand.ID, this);
            return;
        }
        throw new UnsupportedOperationException("Addition IWLConnection to wrong Dispatcher type:" + this.m_type);
    }

    public boolean handleCommand(Command $r1) throws  {
        WLServiceCommand $r2 = new WLServiceCommand($r1.getRawCommandData());
        switch ($r1.getCommandID()) {
            case (short) 96:
            case (short) 97:
            case (short) 98:
            case (short) 99:
            case (short) 100:
            case (short) 101:
            case (short) 102:
            case (short) 103:
            case (short) 104:
            case (short) 105:
            case (short) 112:
            case (short) 113:
            case (short) 114:
            case (short) 115:
                try {
                    processWLServiceCommand($r2);
                    break;
                } catch (UnsupportedOperationException $r3) {
                    Log.e(TAG, "handleCommand: unsupported operation " + $r1.getCommandID() + "! ", $r3);
                    break;
                }
            case (short) 106:
            case (short) 107:
            case (short) 108:
            case (short) 109:
            case (short) 110:
            case (short) 111:
                break;
            default:
                break;
        }
        Log.e(TAG, "Unsupported command ID: " + $r1.getCommandID());
        return false;
    }
}
