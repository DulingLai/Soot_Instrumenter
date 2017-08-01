package com.abaltatech.wlappservices;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.weblink.core.commandhandling.IWLConnection;
import com.abaltatech.mcp.weblink.servercore.WebLinkServerCore;
import com.abaltatech.mcp.weblink.utils.AppUtils;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager.Stub;
import com.abaltatech.weblink.service.interfaces.IWLAppsServiceNotificationHandler;
import com.abaltatech.weblink.service.interfaces.IWLRequestSuccessCallback;
import com.abaltatech.weblink.service.interfaces.IWLServiceErrorCallback;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SecureServiceManager extends Thread {
    private static final String TAG = "SecureServiceManager";
    static final String WLClientAppName = "wlclient://";
    private static SecureServiceManager ms_instance;
    private final ServiceConnection m_connection = new C04401();
    private Context m_context;
    private IDefaultServiceSelector m_defaultServiceSelector = new DefaultServiceSelector_NoUI();
    private SparseArray<IServiceDiscoveryNotification> m_discoveryMap = new SparseArray();
    private ServiceDispatcher m_dispatcher;
    private HashMap<IServiceStatusNotification, StatusNotificationWrapper> m_handlerToStatusWrapperMap = new HashMap();
    private HashMap<IServiceNotificationHandler, NotificationWrapper> m_handlerToWrapperMap = new HashMap();
    private SparseArray<StatusNotificationWrapper> m_idToStatusWrapperMap = new SparseArray();
    private SparseArray<NotificationWrapper> m_idToWrapperMap = new SparseArray();
    private SparseArray<PendingRequest> m_inProcessMap = new SparseArray();
    private boolean m_isClient = false;
    private boolean m_isInitialized = false;
    private boolean m_isMaster;
    private HashMap<String, ServiceDescriptor> m_localServiceNameMap = new HashMap();
    protected IWLAppsServiceManager m_masterService;
    private SparseArray<ServiceDescriptor> m_myServiceDescriptors = new SparseArray();
    private int m_nextFindID = Integer.MIN_VALUE;
    private int m_nextRegisterNotificationID = 0;
    private int m_nextServiceDescriptorID = 0;
    private HashMap<ServiceProxy, IWLAppsServiceNotificationHandler> m_notificationsMap = new HashMap();
    private HashMap<String, ServiceDescriptor> m_serviceNameMap = new HashMap();
    private HashMap<String, String> m_serviceNameToAppIDMap = new HashMap();
    private HashMap<String, List<ServiceDescriptor>> m_serviceProtocolMap = new HashMap();
    private HashSet<IServiceStatusNotification> m_statusNotifications = new HashSet();
    private ArrayList<PendingRequest> m_unprocessedQueue = new ArrayList();

    class C04401 implements ServiceConnection {
        C04401() throws  {
        }

        public void onServiceConnected(ComponentName className, IBinder $r2) throws  {
            synchronized (SecureServiceManager.this) {
                SecureServiceManager.this.m_masterService = Stub.asInterface($r2);
            }
        }

        public void onServiceDisconnected(ComponentName className) throws  {
            synchronized (SecureServiceManager.this) {
                SecureServiceManager.this.m_masterService = null;
            }
        }
    }

    private class NotificationWrapper {
        IServiceNotificationHandler m_notificationHandler;
        int m_registerNotificationID;

        public NotificationWrapper(int $i0, IServiceNotificationHandler $r2) throws  {
            this.m_registerNotificationID = $i0;
            this.m_notificationHandler = $r2;
        }
    }

    private class StatusNotificationWrapper {
        IServiceStatusNotification m_notificationHandler;
        int m_registerNotificationID;

        public StatusNotificationWrapper(int $i0, IServiceStatusNotification $r2) throws  {
            this.m_registerNotificationID = $i0;
            this.m_notificationHandler = $r2;
        }
    }

    boolean addDescriptor(com.abaltatech.wlappservices.ServiceDescriptor r19, java.lang.String r20) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0080 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r18 = this;
        r0 = r19;
        r1 = r0.getName();
        r2 = r1;
        if (r1 == 0) goto L_0x000d;
    L_0x0009:
        r2 = r1.trim();
    L_0x000d:
        if (r2 == 0) goto L_0x0015;
    L_0x000f:
        r3 = r2.isEmpty();
        if (r3 == 0) goto L_0x001d;
    L_0x0015:
        r4 = new java.lang.UnsupportedOperationException;
        r5 = "Service name cannot be null or empty";
        r4.<init>(r5);
        throw r4;
    L_0x001d:
        monitor-enter(r18);
        r0 = r18;
        r6 = r0.m_serviceNameToAppIDMap;
        r7 = r6.get(r2);
        r8 = r7;
        r8 = (java.lang.String) r8;
        r1 = r8;
        r0 = r18;
        r6 = r0.m_serviceNameMap;
        r3 = r6.containsKey(r2);
        if (r3 == 0) goto L_0x0071;
    L_0x0034:
        if (r1 == 0) goto L_0x0071;
    L_0x0036:
        r0 = r20;
        r9 = r1.compareToIgnoreCase(r0);
        if (r9 == 0) goto L_0x0071;
    L_0x003e:
        r3 = 0;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r5 = "registerService: service name already exists: ";
        r11 = r10.append(r5);
        r11 = r11.append(r2);
        r5 = ", app ID: ";
        r11 = r11.append(r5);
        r0 = r20;
        r11 = r11.append(r0);
        r5 = ",  previous appID: ";
        r11 = r11.append(r5);
        r11 = r11.append(r1);
        r20 = r11.toString();
        r5 = "SecureServiceManager";
        r0 = r20;
        android.util.Log.e(r5, r0);
    L_0x006f:
        monitor-exit(r18);
        return r3;
    L_0x0071:
        if (r1 == 0) goto L_0x0080;
    L_0x0073:
        r0 = r20;
        r9 = r1.compareToIgnoreCase(r0);
        if (r9 != 0) goto L_0x0080;
    L_0x007b:
        r0 = r18;
        r0.removeDescriptor(r2);
    L_0x0080:
        r0 = r18;
        r6 = r0.m_serviceNameMap;
        r0 = r19;
        r6.put(r2, r0);
        r0 = r18;
        r6 = r0.m_serviceNameToAppIDMap;
        r0 = r20;
        r6.put(r2, r0);
        r0 = r19;
        r12 = r0.getProtocols();
        r13 = r12.iterator();
    L_0x009c:
        r3 = r13.hasNext();
        if (r3 == 0) goto L_0x00d9;
    L_0x00a2:
        r7 = r13.next();
        r14 = r7;
        r14 = (java.lang.String) r14;
        r20 = r14;
        r0 = r18;
        r6 = r0.m_serviceProtocolMap;
        r0 = r20;
        r7 = r6.get(r0);
        r15 = r7;
        r15 = (java.util.List) r15;
        r12 = r15;
        if (r12 != 0) goto L_0x00d0;
    L_0x00be:
        r16 = new java.util.ArrayList;
        r12 = r16;
        r0 = r16;
        r0.<init>();
        r0 = r18;
        r6 = r0.m_serviceProtocolMap;
        r0 = r20;
        r6.put(r0, r12);
    L_0x00d0:
        r0 = r19;
        r12.add(r0);
        goto L_0x009c;
    L_0x00d6:
        r17 = move-exception;
        monitor-exit(r18);
        throw r17;
    L_0x00d9:
        r3 = 1;
        goto L_0x006f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.SecureServiceManager.addDescriptor(com.abaltatech.wlappservices.ServiceDescriptor, java.lang.String):boolean");
    }

    private SecureServiceManager() throws  {
        setName("SSM Pending Requests");
        start();
    }

    public static synchronized SecureServiceManager getInstance() throws  {
        Class cls = SecureServiceManager.class;
        synchronized (cls) {
            try {
                if (ms_instance == null) {
                    ms_instance = new SecureServiceManager();
                }
                SecureServiceManager $r0 = ms_instance;
                return $r0;
            } finally {
                cls = SecureServiceManager.class;
            }
        }
    }

    public synchronized void initServiceManager(ServiceDispatcher $r1, Context $r2) throws  {
        if (this.m_isInitialized) {
            throw new IllegalStateException("ServiceManager is already initialized");
        }
        this.m_isClient = false;
        this.m_isMaster = false;
        this.m_isInitialized = AppUtils.bindService($r2, WebLinkServerCore.WLSERVICE_IWLAPPSSERVICEMANAGER, this.m_connection);
        this.m_dispatcher = $r1;
        this.m_context = $r2;
    }

    public synchronized void initMasterServiceManager(ServiceDispatcher $r1, Context $r2) throws  {
        if (this.m_isInitialized) {
            throw new IllegalStateException("ServiceManager is already initialized");
        }
        this.m_isInitialized = true;
        this.m_dispatcher = $r1;
        this.m_isClient = false;
        this.m_isMaster = true;
        this.m_context = $r2;
    }

    public synchronized void initClientServiceManager(ServiceDispatcher $r1, Context $r2) throws  {
        if (this.m_isInitialized) {
            throw new IllegalStateException("ServiceManager is already initialized");
        }
        this.m_isInitialized = true;
        this.m_dispatcher = $r1;
        this.m_isMaster = false;
        this.m_isClient = true;
        this.m_context = $r2;
    }

    void findServiceByName(String $r3, String $r1, IServiceDiscoveryNotification $r2) throws  {
        synchronized (this) {
            if (this.m_isInitialized) {
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        if ($r2 == null || $r1 == null || $r1.trim().length() == 0) {
            throw new IllegalArgumentException("discoveryNotification and serviceName cannot be null or empty!");
        } else if (this.m_isClient) {
            sendFindServiceCommand(WLClientAppName, $r1, $r2);
        } else if (this.m_isMaster) {
            findServiceOnMaster($r3, $r1, $r2);
        } else {
            sendFindServiceCommand($r3, $r1, $r2);
        }
    }

    void findServiceByProtocol(String $r3, String $r1, IServiceDiscoveryNotification $r2, boolean $z0) throws  {
        synchronized (this) {
            if (this.m_isInitialized) {
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        if ($r2 == null || $r1 == null || $r1.trim().length() == 0) {
            throw new IllegalArgumentException("discoveryNotification and serviceName cannot be null or empty!");
        } else if (this.m_isClient) {
            sendFindServiceCommand(WLClientAppName, $r1, $r2, $z0);
        } else if (this.m_isMaster) {
            findServiceOnMaster($r3, $r1, $r2, $z0);
        } else {
            sendFindServiceCommand($r3, $r1, $r2, $z0);
        }
    }

    private ServiceProxy findStoredProxy(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I)", "Lcom/abaltatech/wlappservices/ServiceProxy;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I)", "Lcom/abaltatech/wlappservices/ServiceProxy;"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I)", "Lcom/abaltatech/wlappservices/ServiceProxy;"}) int $i0) throws  {
        ServiceDescriptor $r4 = null;
        synchronized (this) {
            if (this.m_serviceNameMap.containsKey($r1)) {
                $r4 = (ServiceDescriptor) this.m_serviceNameMap.get($r1);
            }
        }
        if ($r4 == null) {
            return null;
        }
        List $r7 = $r4.getProtocols();
        int $i1 = $r4.getServiceDescriptorID();
        ServiceProxy $r8 = $r4.getServiceProxy();
        if ($i1 != $i0 || $r2.size() != $r7.size()) {
            return null;
        }
        ListIterator $r10 = $r2.listIterator();
        ListIterator $r11 = $r7.listIterator();
        do {
            try {
                if (!$r10.hasNext()) {
                    return $r8;
                }
            } catch (NoSuchElementException e) {
                return null;
            }
        } while (((String) $r10.next()).compareTo((String) $r11.next()) == 0);
        return null;
    }

    private void sendFindServiceCommand(String $r1, String $r2, IServiceDiscoveryNotification $r3) throws  {
        synchronized (this) {
            int $i0 = this.m_nextFindID;
            this.m_nextFindID++;
        }
        if (this.m_dispatcher.sendWLServiceCommand(new FindServiceCommand($r1, ServiceDispatcher.MasterServiceAppID, $r2, $i0))) {
            this.m_discoveryMap.put($i0, $r3);
        } else {
            $r3.onServiceDiscoveryFailed(EServiceDiscoveryErrorCode.CommunicationError);
        }
    }

    private void sendFindServiceCommand(String $r1, String $r2, IServiceDiscoveryNotification $r3, boolean $z0) throws  {
        synchronized (this) {
            int $i0 = this.m_nextFindID;
            this.m_nextFindID++;
        }
        if (this.m_dispatcher.sendWLServiceCommand(new FindServiceByProtocolCommand($r1, ServiceDispatcher.MasterServiceAppID, $r2, $i0, $z0))) {
            this.m_discoveryMap.put($i0, $r3);
            return;
        }
        $r3.onServiceDiscoveryFailed(EServiceDiscoveryErrorCode.CommunicationError);
    }

    private void findServiceOnMaster(String appID, String $r2, IServiceDiscoveryNotification $r3) throws  {
        ServiceDescriptor $r6 = (ServiceDescriptor) this.m_serviceNameMap.get($r2);
        ArrayList $r7 = null;
        if ($r6 != null) {
            $r7 = new ArrayList();
            $r7.add($r6);
        }
        processFoundDescriptos($r7, $r3, true, "");
    }

    private void processFoundDescriptos(@Signature({"(", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;", "Lcom/abaltatech/wlappservices/IServiceDiscoveryNotification;", "Z", "Ljava/lang/String;", ")V"}) List<ServiceDescriptor> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;", "Lcom/abaltatech/wlappservices/IServiceDiscoveryNotification;", "Z", "Ljava/lang/String;", ")V"}) IServiceDiscoveryNotification $r2, @Signature({"(", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;", "Lcom/abaltatech/wlappservices/IServiceDiscoveryNotification;", "Z", "Ljava/lang/String;", ")V"}) boolean $z0, @Signature({"(", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;", "Lcom/abaltatech/wlappservices/IServiceDiscoveryNotification;", "Z", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        int $i0 = 0;
        if ($r1 != null) {
            if ($z0) {
                ServiceDescriptor $r5 = this.m_defaultServiceSelector.onSelectDefaultService($r3, $r1);
                if ($r5 != null) {
                    $r1.remove($r5);
                    $r1.add(0, $r5);
                }
            }
            for (ServiceDescriptor serviceProxy : $r1) {
                boolean $z1 = $r2.onServiceFound(serviceProxy.getServiceProxy(), $i0);
                $i0++;
                if ($z1) {
                    if ($z0) {
                        break;
                    }
                }
                break;
            }
        }
        if ($i0 == 0) {
            $r2.onServiceDiscoveryFailed(EServiceDiscoveryErrorCode.ServiceNotFound);
        } else {
            $r2.onServiceDiscoveryCompleted($i0);
        }
    }

    private void findServiceOnMaster(String appID, String $r2, IServiceDiscoveryNotification $r3, boolean $z0) throws  {
        processFoundDescriptos((List) this.m_serviceProtocolMap.get($r2), $r3, $z0, $r2);
    }

    boolean registerService(ServiceDescriptor $r1, String $r2) throws  {
        synchronized (this) {
            if (this.m_isInitialized) {
                boolean $z0 = this.m_isClient;
                boolean $z1 = this.m_isMaster;
                IWLAppsServiceManager $r6 = this.m_masterService;
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        if ($z1) {
            if ($r1.getServiceHandler() == null && $r1.getWLAppsServiceHandler() == null && $r1.getServiceProxy() == null) {
                throw new UnsupportedOperationException("Both descriptor.ServiceHandler and descriptor.WLAppsServiceHandler are null");
            }
            $z0 = addDescriptor($r1, $r2);
            if (!$z0) {
                return $z0;
            }
            processServiceStatusNotification($r1.getName(), $r1.getProtocols(), EServiceStatus.SS_REGISTERED);
            return $z0;
        } else if ($z0) {
            return callClientService($r1);
        } else {
            return callMasterService($r1, $r6);
        }
    }

    void unregisterService(String $r1) throws  {
        synchronized (this) {
            if (this.m_isInitialized) {
                boolean $z0 = this.m_isClient;
                boolean $z1 = this.m_isMaster;
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        String $r4 = $r1;
        if ($r1 != null) {
            $r4 = $r1.trim();
        }
        if ($r4 == null || $r4.isEmpty()) {
            throw new UnsupportedOperationException("Service name cannot be null or empty");
        } else if ($z1) {
            synchronized (this) {
                if (this.m_serviceNameMap.containsKey($r4)) {
                    removeDescriptor($r4);
                } else {
                    Log.e(TAG, "unregisterService: service name does not exist: " + $r4);
                }
            }
            processServiceStatusNotification($r4, null, EServiceStatus.SS_UNREGISTERED);
        } else if ($z0) {
            synchronized (this) {
                $r11 = (ServiceDescriptor) this.m_localServiceNameMap.remove($r1);
                if ($r11 != null) {
                    this.m_myServiceDescriptors.remove($r11.getServiceDescriptorID());
                    $r11.setServiceProxy(null);
                    r0 = new UnregisterServiceCommand(WLClientAppName, ServiceDispatcher.MasterServiceAppID, $r11.getServiceDescriptorID(), $r11.getName());
                    r0 = this.m_dispatcher;
                    $r14 = r0;
                    r0.sendWLServiceCommand(r0);
                }
            }
        } else {
            synchronized (this) {
                $r11 = (ServiceDescriptor) this.m_localServiceNameMap.remove($r1);
                if ($r11 != null) {
                    this.m_myServiceDescriptors.remove($r11.getServiceDescriptorID());
                    $r11.setServiceProxy(null);
                    Context $r16 = this.m_context;
                    $r1 = AppUtils.getAppName($r16);
                    String str = $r1;
                    r0 = new UnregisterServiceCommand(str, ServiceDispatcher.MasterServiceAppID, $r11.getServiceDescriptorID(), $r11.getName());
                    r0 = this.m_dispatcher;
                    $r14 = r0;
                    r0.sendWLServiceCommand(r0);
                }
            }
        }
    }

    private synchronized void removeDescriptor(String $r1) throws  {
        ServiceDescriptor $r4 = (ServiceDescriptor) this.m_serviceNameMap.remove($r1);
        if ($r4 != null) {
            for (String $r12 : $r4.getProtocols()) {
                List $r5 = (List) this.m_serviceProtocolMap.get($r12);
                if ($r5 != null) {
                    $r5.remove($r4);
                }
            }
            $r4.setServiceProxy(null);
        }
    }

    private boolean callClientService(ServiceDescriptor $r1) throws  {
        if ($r1.getServiceHandler() == null) {
            throw new UnsupportedOperationException("descriptor.ServiceHandler is null");
        }
        try {
            int $i0 = getNextServiceDescriptorID();
            $r1.setServiceDescriptorID($i0);
            String $r6 = $r1.getName();
            List $r7 = $r1.getProtocols();
            boolean $z0 = this.m_dispatcher.sendWLServiceCommand(new RegisterServiceCommand(WLClientAppName, ServiceDispatcher.MasterServiceAppID, $i0, $r6, $r7));
            if (!$z0) {
                return $z0;
            }
            synchronized (this) {
                SparseArray sparseArray = this.m_myServiceDescriptors;
                SparseArray $r9 = sparseArray;
                sparseArray.append($i0, $r1);
                this.m_localServiceNameMap.put($r1.getName(), $r1);
            }
            return $z0;
        } catch (Throwable $r3) {
            Log.e(TAG, "Error in callMasterService", $r3);
            return false;
        }
    }

    private boolean callMasterService(ServiceDescriptor $r1, IWLAppsServiceManager $r2) throws  {
        if ($r2 == null) {
            Log.e(TAG, "registerService called before the master service is bound!");
            return false;
        } else if ($r1.getServiceHandler() == null) {
            throw new UnsupportedOperationException("descriptor.ServiceHandler is null");
        } else {
            $r1.setWLAppsServiceHandler(new WLAppsServiceHandlerImpl($r1.getServiceHandler()));
            try {
                String $r10 = AppUtils.getAppName(this.m_context);
                int $i0 = getNextServiceDescriptorID();
                $r1.setServiceDescriptorID($i0);
                $r1.setServiceProxy(new ServiceProxy_Local($r1.getName(), $r1.getProtocols(), $r1.getServiceHandler()));
                WLServiceCommand registerServiceCommand = new RegisterServiceCommand($r10, ServiceDispatcher.MasterServiceAppID, $i0, $r1.getName(), $r1.getProtocols());
                ServiceDispatcher serviceDispatcher = this.m_dispatcher;
                ServiceDispatcher $r13 = serviceDispatcher;
                boolean $z0 = serviceDispatcher.sendWLServiceCommand(registerServiceCommand);
                if (!$z0) {
                    return $z0;
                }
                synchronized (this) {
                    SparseArray sparseArray = this.m_myServiceDescriptors;
                    SparseArray $r14 = sparseArray;
                    sparseArray.append($i0, $r1);
                    this.m_localServiceNameMap.put($r1.getName(), $r1);
                }
                return $z0;
            } catch (Throwable $r4) {
                Log.e(TAG, "Error in callMasterService", $r4);
                return false;
            }
        }
    }

    private synchronized int getNextServiceDescriptorID() throws  {
        this.m_nextServiceDescriptorID++;
        return this.m_nextServiceDescriptorID;
    }

    int sendRequest(ServiceProxy $r1, String $r2, ServiceRequest $r3, IServiceResponseNotification $r4) throws  {
        synchronized (this) {
            if (this.m_isInitialized) {
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        PendingRequest $r5 = new PendingRequest($r1, $r2, $r3, $r4);
        synchronized (this.m_unprocessedQueue) {
            this.m_unprocessedQueue.add($r5);
            this.m_unprocessedQueue.notifyAll();
        }
        return $r5.getRequestID();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() throws  {
        /*
        r14 = this;
        r0 = "SecureServiceManager";
        r1 = " thread started.";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1);
    L_0x0007:
        r2 = r14.isInterrupted();
        if (r2 != 0) goto L_0x002a;
    L_0x000d:
        r3 = 0;
        r4 = r14.m_unprocessedQueue;
        monitor-enter(r4);
    L_0x0011:
        r5 = r14.m_unprocessedQueue;	 Catch:{ Throwable -> 0x001f }
        r6 = r5.size();	 Catch:{ Throwable -> 0x001f }
        if (r6 != 0) goto L_0x0032;
    L_0x0019:
        r5 = r14.m_unprocessedQueue;	 Catch:{ Throwable -> 0x001f }
        r5.wait();	 Catch:{ Throwable -> 0x001f }
        goto L_0x0011;
    L_0x001f:
        r7 = move-exception;
        monitor-exit(r4);	 Catch:{ Throwable -> 0x001f }
        throw r7;	 Catch:{ InterruptedException -> 0x0022 }
    L_0x0022:
        r8 = move-exception;
        r0 = "SecureServiceManager";
        r1 = "Received interrupt:";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1, r8);
    L_0x002a:
        r0 = "SecureServiceManager";
        r1 = " thread stopped.";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1);
        return;
    L_0x0032:
        r5 = r14.m_unprocessedQueue;	 Catch:{ Throwable -> 0x001f }
        r6 = r5.size();	 Catch:{ Throwable -> 0x001f }
        if (r6 <= 0) goto L_0x0045;
    L_0x003a:
        r5 = r14.m_unprocessedQueue;	 Catch:{ Throwable -> 0x001f }
        r10 = 0;
        r9 = r5.remove(r10);	 Catch:{ Throwable -> 0x001f }
        r11 = r9;
        r11 = (com.abaltatech.wlappservices.PendingRequest) r11;	 Catch:{ Throwable -> 0x001f }
        r3 = r11;
    L_0x0045:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x001f }
        if (r3 == 0) goto L_0x004d;
    L_0x0048:
        r12 = 10;
        java.lang.Thread.sleep(r12);	 Catch:{ InterruptedException -> 0x0022 }
    L_0x004d:
        if (r3 == 0) goto L_0x0007;
    L_0x004f:
        r14.processRequest(r3);
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.SecureServiceManager.run():void");
    }

    private void processRequest(PendingRequest $r1) throws  {
        ServiceProxy $r6 = $r1.getServiceProxy();
        String $r7 = $r6.getServiceName();
        synchronized (this) {
            ServiceDescriptor $r10 = (ServiceDescriptor) this.m_serviceNameMap.get($r7);
        }
        ServiceProxy $r11;
        if ($r10 == null) {
            $r11 = null;
        } else {
            $r11 = $r10.getServiceProxy();
        }
        synchronized (this) {
            $r10 = (ServiceDescriptor) this.m_localServiceNameMap.get($r7);
        }
        ServiceProxy serviceProxy;
        if ($r10 == null) {
            serviceProxy = null;
        } else {
            serviceProxy = $r10.getServiceProxy();
        }
        if ($r6 == null || !($r11 == $r6 || r17 == $r6)) {
            MCSLogger.log(TAG, "processRequest - Invalid Proxy!");
            $r1.getNotification().onRequestFailed($r1, EServiceErrorCode.NotAvailable);
        }
        synchronized (this.m_inProcessMap) {
            this.m_inProcessMap.put($r1.getRequestID(), $r1);
        }
        if ($r6 instanceof ServiceProxy_Remote) {
            IWLAppsServiceHandler $r18 = ((ServiceProxy_Remote) $r6).getHandler();
            final PendingRequest pendingRequest = $r1;
            C04412 c04412 = new IWLRequestSuccessCallback.Stub() {
                public void onSuccess(int $i0, byte[] $r1) throws RemoteException {
                    SecureServiceManager.this.removeFromProcessingMap(pendingRequest.getRequestID());
                    ServiceResponse $r2 = new ServiceResponse();
                    $r2.setRequestID($i0);
                    $r2.setResponseBody($r1);
                    pendingRequest.getNotification().onResponseReceived(pendingRequest, $r2);
                }
            };
            pendingRequest = $r1;
            C04423 c04423 = new IWLServiceErrorCallback.Stub() {
                public void onError(int reqID, String $r1) throws RemoteException {
                    SecureServiceManager.this.removeFromProcessingMap(pendingRequest.getRequestID());
                    pendingRequest.getNotification().onRequestFailed(pendingRequest, EServiceErrorCode.valueOf($r1));
                }
            };
            try {
                int $i0 = $r18.sendRequest($r1.getResourcePath(), $r1.getRequestMethod().toString(), $r1.getAllowExecuteInForeground(), $r1.getRequestBody(), c04412, c04423);
                if ($i0 != 0) {
                    $r1.setRemoteRequestID($i0);
                }
            } catch (Throwable $r5) {
                Log.w(TAG, "Remote Exception", $r5);
                $r1.getNotification().onRequestFailed($r1, EServiceErrorCode.NotAvailable);
            }
        } else if ($r6 instanceof ServiceProxy_Dispatcher) {
            byte[] $r21;
            ServiceProxy_Dispatcher $r25 = (ServiceProxy_Dispatcher) $r6;
            if ($r1.m_requestBody != null) {
                $r21 = $r1.m_requestBody;
            } else {
                $r21 = new byte[null];
            }
            WLServiceCommand sendRequestCommand = new SendRequestCommand($r25.getReceiverID(), $r25.getSenderID(), $r25.getServiceDescriptorID(), $r1.getRequestID(), $r1.m_allowExecuteInForeground, $r21, $r1.m_requestMethod, $r1.getResourcePath().getBytes());
            ServiceDispatcher serviceDispatcher = this.m_dispatcher;
            ServiceDispatcher $r28 = serviceDispatcher;
            if (!serviceDispatcher.sendWLServiceCommand(sendRequestCommand)) {
                Log.w(TAG, "Could not dispatch SendRequestCommand!");
                $r1.getNotification().onRequestFailed($r1, EServiceErrorCode.NotAvailable);
            }
        } else if ($r6 instanceof ServiceProxy_Local) {
            $r1.setRemoteRequestID(((ServiceProxy_Local) $r6).m_handler.onProcessRequest($r1.getResourcePath(), $r1, $r1.getNotification()));
        } else {
            throw new UnsupportedOperationException("Unsupported ServiceProxy type: " + $r6.getClass());
        }
    }

    protected PendingRequest removeFromProcessingMap(int $i0) throws  {
        PendingRequest $r4;
        synchronized (this.m_inProcessMap) {
            $r4 = (PendingRequest) this.m_inProcessMap.get($i0);
            this.m_inProcessMap.delete($i0);
        }
        return $r4;
    }

    boolean cancelServiceRequest(int $i0) throws  {
        synchronized (this) {
            if (this.m_isInitialized) {
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        PendingRequest $r5 = null;
        synchronized (this.m_unprocessedQueue) {
            Iterator $r8 = this.m_unprocessedQueue.iterator();
            while ($r8.hasNext()) {
                PendingRequest $r10 = (PendingRequest) $r8.next();
                if ($r10.getRequestID() == $i0) {
                    this.m_unprocessedQueue.remove($r10);
                    $r5 = $r10;
                    break;
                }
            }
        }
        if ($r5 != null) {
            return true;
        }
        $r5 = removeFromProcessingMap($i0);
        if ($r5 == null) {
            return false;
        }
        if ($r5.getServiceProxy() instanceof ServiceProxy_Remote) {
            ServiceProxy_Remote $r13 = (ServiceProxy_Remote) $r5.getServiceProxy();
            $i0 = $r5.getRemoteRequestID();
            if ($r13 == null) {
                return false;
            }
            if ($i0 == 0) {
                return false;
            }
            IWLAppsServiceHandler $r14 = $r13.getHandler();
            if ($r14 == null) {
                return false;
            }
            try {
                return $r14.cancelRequest($i0);
            } catch (Throwable $r2) {
                Log.w(TAG, "Remote Exception", $r2);
                return false;
            }
        } else if ($r5.getServiceProxy() instanceof ServiceProxy_Dispatcher) {
            boolean $z0;
            WLServiceCommand cancelRequestCommand;
            ServiceDispatcher serviceDispatcher;
            ServiceDispatcher $r19;
            ServiceProxy_Dispatcher $r15 = (ServiceProxy_Dispatcher) $r5.getServiceProxy();
            if (!(ServiceDispatcher.MasterServiceAppID.compareTo($r15.getReceiverID()) == 0)) {
                if (ServiceDispatcher.MasterServiceAppID.compareTo($r15.getSenderID()) != 0) {
                    $z0 = false;
                    if ($z0) {
                        Log.e(TAG, "Invalid app ID: rcvId=" + $r15.getReceiverID() + ", sndId=" + $r15.getSenderID());
                        return false;
                    }
                    cancelRequestCommand = new CancelRequestCommand($r15.getReceiverID(), $r15.getSenderID(), $r5.getRequestID());
                    serviceDispatcher = this.m_dispatcher;
                    $r19 = serviceDispatcher;
                    return serviceDispatcher.sendWLServiceCommand(cancelRequestCommand);
                }
            }
            $z0 = true;
            if ($z0) {
                cancelRequestCommand = new CancelRequestCommand($r15.getReceiverID(), $r15.getSenderID(), $r5.getRequestID());
                serviceDispatcher = this.m_dispatcher;
                $r19 = serviceDispatcher;
                return serviceDispatcher.sendWLServiceCommand(cancelRequestCommand);
            }
            Log.e(TAG, "Invalid app ID: rcvId=" + $r15.getReceiverID() + ", sndId=" + $r15.getSenderID());
            return false;
        } else if ($r5.getServiceProxy() instanceof ServiceProxy_Local) {
            ((ServiceProxy_Local) $r5.getServiceProxy()).m_handler.onCancelRequest($r5.getRemoteRequestID());
            return false;
        } else {
            throw new UnsupportedOperationException("Please implement this!");
        }
    }

    boolean registerForServiceStatusNotification(IServiceStatusNotification $r1) throws  {
        if (this.m_isMaster) {
            boolean $z0;
            synchronized (this) {
                $z0 = this.m_statusNotifications.add($r1);
            }
            return $z0;
        }
        int $i0 = getNextRegisterNotificationID();
        String $r7 = AppUtils.getAppName(this.m_context);
        StatusNotificationWrapper $r3 = new StatusNotificationWrapper($i0, $r1);
        RegisterStatusNotificationCommand $r2 = new RegisterStatusNotificationCommand($r7, ServiceDispatcher.MasterServiceAppID, $i0);
        synchronized (this) {
            this.m_idToStatusWrapperMap.append($i0, $r3);
            this.m_handlerToStatusWrapperMap.put($r1, $r3);
        }
        return this.m_dispatcher.sendWLServiceCommand($r2);
    }

    boolean unRegisterForServiceStatusNotification(IServiceStatusNotification $r1) throws  {
        boolean $z0 = this.m_isMaster;
        this = this;
        if ($z0) {
            synchronized (this) {
                $z0 = this.m_statusNotifications.remove($r1);
            }
            return $z0;
        }
        synchronized (this) {
            StatusNotificationWrapper $r7 = (StatusNotificationWrapper) this.m_handlerToStatusWrapperMap.remove($r1);
            if ($r7 != null) {
                this.m_idToWrapperMap.remove($r7.m_registerNotificationID);
            }
        }
        if ($r7 == null) {
            return false;
        }
        return this.m_dispatcher.sendWLServiceCommand(new UnregisterStatusNotificationCommand(AppUtils.getAppName(this.m_context), ServiceDispatcher.MasterServiceAppID, $r7.m_registerNotificationID));
    }

    boolean registerForNotification(ServiceProxy $r1, String $r2, IServiceNotificationHandler $r3) throws  {
        boolean $z1;
        boolean $z0 = false;
        synchronized (this) {
            if (this.m_isInitialized) {
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        String $r10 = $r1.getServiceName();
        synchronized (this) {
            ServiceDescriptor $r13 = (ServiceDescriptor) this.m_serviceNameMap.get($r10);
        }
        if ($r13 == null || $r13.getServiceProxy() != $r1) {
            $z1 = true;
            if ($r1 instanceof ServiceProxy_Local) {
                synchronized (this) {
                    $r13 = (ServiceDescriptor) this.m_localServiceNameMap.get($r10);
                    if ($r13 != null && $r13.getServiceProxy() == $r1) {
                        $z1 = false;
                    }
                }
            }
            if ($z1) {
                Log.w(TAG, "Descriptor doesn't match serviceProxy!");
                return false;
            }
        }
        if ($r1 instanceof ServiceProxy_Remote) {
            ServiceProxy_Remote $r17 = (ServiceProxy_Remote) $r1;
            IWLAppsServiceHandler $r18 = $r17.getHandler();
            final IServiceNotificationHandler iServiceNotificationHandler = $r3;
            C04434 c04434 = new IWLAppsServiceNotificationHandler.Stub() {
                public void onNotification(String $r1, byte[] $r2) throws RemoteException {
                    iServiceNotificationHandler.onNotification($r1, $r2);
                }
            };
            try {
                $r18.registerForNotification($r2, c04434);
                $z0 = true;
            } catch (Throwable $r5) {
                Log.e(TAG, "Unable to register for notification", $r5);
                $z0 = false;
            }
            synchronized (this) {
                this.m_notificationsMap.put($r17, c04434);
            }
        } else if ($r1 instanceof ServiceProxy_Dispatcher) {
            ServiceProxy_Dispatcher $r20 = (ServiceProxy_Dispatcher) $r1;
            $z1 = (ServiceDispatcher.MasterServiceAppID.compareTo($r20.getReceiverID()) == 0) || ServiceDispatcher.MasterServiceAppID.compareTo($r20.getSenderID()) == 0;
            if ($z1) {
                int $i0 = getNextRegisterNotificationID();
                NotificationWrapper notificationWrapper = new NotificationWrapper($i0, $r3);
                WLServiceCommand registerNotificationCommand = new RegisterNotificationCommand($r20.getReceiverID(), $r20.getSenderID(), $r20.getServiceDescriptorID(), $r2.getBytes(), $i0);
                synchronized (this) {
                    SparseArray sparseArray = this.m_idToWrapperMap;
                    SparseArray $r24 = sparseArray;
                    sparseArray.append($i0, notificationWrapper);
                    this.m_handlerToWrapperMap.put($r3, notificationWrapper);
                }
                ServiceDispatcher serviceDispatcher = this.m_dispatcher;
                ServiceDispatcher $r25 = serviceDispatcher;
                $z0 = serviceDispatcher.sendWLServiceCommand(registerNotificationCommand);
            } else {
                Log.e(TAG, "Invalid app ID: rcvId=" + $r20.getReceiverID() + ", sndId=" + $r20.getSenderID());
            }
        } else if ($r1 instanceof ServiceProxy_Local) {
            ServiceProxy_Local $r27 = (ServiceProxy_Local) $r1;
            if ($r27.m_handler != null) {
                IServiceHandler iServiceHandler = $r27.m_handler;
                IServiceHandler $r28 = iServiceHandler;
                iServiceHandler.registerForNotification($r2, $r3);
                $z0 = true;
            }
        } else {
            throw new UnsupportedOperationException("Please implement this!");
        }
        return $z0;
    }

    private synchronized int getNextRegisterNotificationID() throws  {
        this.m_nextRegisterNotificationID++;
        return this.m_nextRegisterNotificationID;
    }

    boolean unregisterForNotification(ServiceProxy $r1, String $r2, IServiceNotificationHandler $r3) throws  {
        boolean $z1;
        boolean $z0 = false;
        synchronized (this) {
            if (this.m_isInitialized) {
            } else {
                throw new IllegalStateException("Not initialized yet");
            }
        }
        String $r8 = $r1.getServiceName();
        synchronized (this) {
            ServiceDescriptor $r11 = (ServiceDescriptor) this.m_serviceNameMap.get($r8);
        }
        if ($r11 == null || $r11.getServiceProxy() != $r1) {
            $z1 = true;
            if ($r1 instanceof ServiceProxy_Local) {
                synchronized (this) {
                    $r11 = (ServiceDescriptor) this.m_localServiceNameMap.get($r8);
                    if ($r11 != null && $r11.getServiceProxy() == $r1) {
                        $z1 = false;
                    }
                }
            }
            if ($z1) {
                Log.w(TAG, "Descriptor doesn't match serviceProxy!");
                return false;
            }
        }
        if ($r1 instanceof ServiceProxy_Remote) {
            IWLAppsServiceNotificationHandler $r17;
            IWLAppsServiceHandler $r16 = ((ServiceProxy_Remote) $r1).getHandler();
            synchronized (this) {
                $r17 = (IWLAppsServiceNotificationHandler) this.m_notificationsMap.remove($r1);
            }
            if ($r17 != null) {
                try {
                    $r16.unregisterFromNotification($r2, $r17);
                    $z0 = true;
                } catch (Throwable $r5) {
                    Log.w(TAG, "Unable to unregister from notification", $r5);
                    $z0 = false;
                }
            }
        } else if ($r1 instanceof ServiceProxy_Dispatcher) {
            ServiceProxy_Dispatcher $r19 = (ServiceProxy_Dispatcher) $r1;
            $z1 = (ServiceDispatcher.MasterServiceAppID.compareTo($r19.getReceiverID()) == 0) || ServiceDispatcher.MasterServiceAppID.compareTo($r19.getSenderID()) == 0;
            if ($z1) {
                NotificationWrapper $r22;
                synchronized (this) {
                    $r22 = (NotificationWrapper) this.m_handlerToWrapperMap.remove($r3);
                    if ($r22 != null) {
                        this.m_idToWrapperMap.remove($r22.m_registerNotificationID);
                    }
                }
                if ($r22 != null) {
                    WLServiceCommand unregisterNotificationCommand = new UnregisterNotificationCommand($r19.getReceiverID(), $r19.getSenderID(), $r19.getServiceDescriptorID(), $r22.m_registerNotificationID, $r2.getBytes());
                    ServiceDispatcher serviceDispatcher = this.m_dispatcher;
                    ServiceDispatcher $r25 = serviceDispatcher;
                    $z0 = serviceDispatcher.sendWLServiceCommand(unregisterNotificationCommand);
                }
            } else {
                Log.e(TAG, "Invalid app ID: rcvId=" + $r19.getReceiverID() + ", sndId=" + $r19.getSenderID());
            }
        } else if ($r1 instanceof ServiceProxy_Local) {
            ServiceProxy_Local $r27 = (ServiceProxy_Local) $r1;
            if ($r27.m_handler != null) {
                IServiceHandler iServiceHandler = $r27.m_handler;
                IServiceHandler $r28 = iServiceHandler;
                iServiceHandler.unregisterFromNotification($r2, $r3);
                $z0 = true;
            }
        } else {
            throw new UnsupportedOperationException("Please implement this!");
        }
        return $z0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void processServiceDescriptorCommand(com.abaltatech.wlappservices.WLServiceCommand r33) throws  {
        /*
        r32 = this;
        monitor-enter(r32);
        r0 = r32;
        r6 = r0.m_isInitialized;	 Catch:{ Throwable -> 0x0059 }
        if (r6 != 0) goto L_0x0010;
    L_0x0007:
        r7 = "SecureServiceManager";
        r8 = "Not initialized yet";
        android.util.Log.e(r7, r8);	 Catch:{ Throwable -> 0x0059 }
        monitor-exit(r32);	 Catch:{ Throwable -> 0x0059 }
        return;
    L_0x0010:
        monitor-exit(r32);	 Catch:{ Throwable -> 0x0059 }
        r9 = new com.abaltatech.wlappservices.ServiceDescriptorCommand;
        r10 = r9;
        r0 = r33;
        r11 = r0.getRawCommandData();
        r9.<init>(r11);
        r6 = r10.isValid();
        if (r6 == 0) goto L_0x00ee;
    L_0x0023:
        r12 = r10.getSearchID();
        r0 = r32;
        r13 = r0.m_discoveryMap;
        r14 = r13.get(r12);
        r16 = r14;
        r16 = (com.abaltatech.wlappservices.IServiceDiscoveryNotification) r16;
        r15 = r16;
        if (r15 == 0) goto L_0x00ef;
    L_0x0037:
        r17 = r10.getSearchStatus();
        r18 = r10.getDescriptorID();
        r19 = r10.getIndex();
        r20 = r10.getSenderID();
        r21 = r10.getReceiverID();
        switch(r17) {
            case 1: goto L_0x0078;
            case 2: goto L_0x005c;
            case 3: goto L_0x006b;
            default: goto L_0x004e;
        };
    L_0x004e:
        goto L_0x004f;
    L_0x004f:
        r22 = new java.lang.UnsupportedOperationException;
        r7 = "Please handle the new cases!";
        r0 = r22;
        r0.<init>(r7);
        throw r22;
    L_0x0059:
        r23 = move-exception;
        monitor-exit(r32);	 Catch:{ Throwable -> 0x0059 }
        throw r23;
    L_0x005c:
        r24 = com.abaltatech.wlappservices.EServiceDiscoveryErrorCode.ServiceNotFound;
        r0 = r24;
        r15.onServiceDiscoveryFailed(r0);
        r0 = r32;
        r13 = r0.m_discoveryMap;
        r13.remove(r12);
        return;
    L_0x006b:
        r0 = r18;
        r15.onServiceDiscoveryCompleted(r0);
        r0 = r32;
        r13 = r0.m_discoveryMap;
        r13.remove(r12);
        return;
    L_0x0078:
        r25 = r10.getServiceName();
        r26 = r10.getProtocols();
        r0 = r32;
        r1 = r25;
        r2 = r26;
        r3 = r18;
        r27 = r0.findStoredProxy(r1, r2, r3);
        r28 = r27;
        if (r27 != 0) goto L_0x00da;
    L_0x0090:
        r29 = new com.abaltatech.wlappservices.ServiceProxy_Dispatcher;
        r28 = r29;
        r0 = r29;
        r1 = r25;
        r2 = r26;
        r3 = r18;
        r4 = r20;
        r5 = r21;
        r0.<init>(r1, r2, r3, r4, r5);
        r30 = new com.abaltatech.wlappservices.ServiceDescriptor;
        r31 = r30;
        r0 = r30;
        r0.<init>();
        r0 = r31;
        r1 = r28;
        r0.setServiceProxy(r1);
        r0 = r31;
        r1 = r25;
        r0.setName(r1);
        r0 = r31;
        r1 = r26;
        r0.setProtocols(r1);
        r20 = r10.getSenderID();
        r0 = r32;
        r1 = r31;
        r2 = r20;
        r6 = r0.addDescriptor(r1, r2);
        if (r6 != 0) goto L_0x00da;
    L_0x00d1:
        r7 = "SecureServiceManager";
        r8 = "Could not add new proxy!";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r8);
        r28 = 0;
    L_0x00da:
        if (r28 == 0) goto L_0x00f0;
    L_0x00dc:
        r0 = r28;
        r1 = r19;
        r6 = r15.onServiceFound(r0, r1);
        if (r6 != 0) goto L_0x00f1;
    L_0x00e6:
        r0 = r32;
        r13 = r0.m_discoveryMap;
        r13.remove(r12);
        return;
    L_0x00ee:
        return;
    L_0x00ef:
        return;
    L_0x00f0:
        return;
    L_0x00f1:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.SecureServiceManager.processServiceDescriptorCommand(com.abaltatech.wlappservices.WLServiceCommand):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void processServiceResponseCommand(com.abaltatech.wlappservices.WLServiceCommand r15) throws  {
        /*
        r14 = this;
        monitor-enter(r14);
        r0 = r14.m_isInitialized;	 Catch:{ Throwable -> 0x004d }
        if (r0 != 0) goto L_0x000e;
    L_0x0005:
        r1 = "SecureServiceManager";
        r2 = "Not initialized yet";
        android.util.Log.e(r1, r2);	 Catch:{ Throwable -> 0x004d }
        monitor-exit(r14);	 Catch:{ Throwable -> 0x004d }
        return;
    L_0x000e:
        monitor-exit(r14);	 Catch:{ Throwable -> 0x004d }
        r3 = new com.abaltatech.wlappservices.ServiceResponseCommand;
        r4 = r15.getRawCommandData();
        r3.<init>(r4);
        r0 = r3.isValid();
        if (r0 == 0) goto L_0x0060;
    L_0x001e:
        r5 = r3.getRequestID();
        r6 = r3.getResponseBody();
        r7 = r3.getErrorCode();
        r8 = r14.removeFromProcessingMap(r5);
        if (r8 == 0) goto L_0x0061;
    L_0x0030:
        r9 = r8.getServiceProxy();
        r0 = r9 instanceof com.abaltatech.wlappservices.ServiceProxy_Dispatcher;
        if (r0 == 0) goto L_0x0058;
    L_0x0038:
        if (r7 != 0) goto L_0x0050;
    L_0x003a:
        r10 = new com.abaltatech.wlappservices.ServiceResponse;
        r10.<init>();
        r10.setRequestID(r5);
        r10.setResponseBody(r6);
        r11 = r8.getNotification();
        r11.onResponseReceived(r8, r10);
        return;
    L_0x004d:
        r12 = move-exception;
        monitor-exit(r14);	 Catch:{ Throwable -> 0x004d }
        throw r12;
    L_0x0050:
        r11 = r8.getNotification();
        r11.onRequestFailed(r8, r7);
        return;
    L_0x0058:
        r13 = new java.lang.UnsupportedOperationException;
        r1 = "Please implement this";
        r13.<init>(r1);
        throw r13;
    L_0x0060:
        return;
    L_0x0061:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.SecureServiceManager.processServiceResponseCommand(com.abaltatech.wlappservices.WLServiceCommand):void");
    }

    void processServiceNotificationCommand(WLServiceCommand $r1) throws  {
        synchronized (this) {
            boolean $z0 = this.m_isInitialized;
            this = this;
            if ($z0) {
                ServiceNotificationCommand $r2 = new ServiceNotificationCommand($r1.getRawCommandData());
                if ($r2.isValid()) {
                    NotificationWrapper $r7;
                    int $i0 = $r2.getNotificationID();
                    synchronized (this) {
                        $r7 = (NotificationWrapper) this.m_idToWrapperMap.get($i0);
                    }
                    if ($r7 != null) {
                        $r7.m_notificationHandler.onNotification(new String($r2.getResourcePath()), $r2.getNotificationData());
                        return;
                    }
                    return;
                }
                return;
            }
            Log.e(TAG, "Not initialized yet");
        }
    }

    ServiceProxy getLocalProxyDescriptor(int $i0) throws  {
        ServiceProxy $r4;
        synchronized (this) {
            ServiceDescriptor $r3 = (ServiceDescriptor) this.m_myServiceDescriptors.get($i0);
            $r4 = $r3 != null ? $r3.getServiceProxy() : null;
        }
        return $r4;
    }

    void processServiceStatusNotification(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/EServiceStatus;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/EServiceStatus;", ")V"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/EServiceStatus;", ")V"}) EServiceStatus $r3) throws  {
        synchronized (this) {
            Iterator $r6 = this.m_statusNotifications.iterator();
            while ($r6.hasNext()) {
                IServiceStatusNotification $r8 = (IServiceStatusNotification) $r6.next();
                switch ($r3) {
                    case SS_REGISTERED:
                        $r8.onServiceRegistered($r1, $r2);
                        break;
                    case SS_UNREGISTERED:
                        $r8.onServiceUnregistered($r1);
                        break;
                    default:
                        throw new UnsupportedOperationException("Please handle the new cases!");
                }
            }
            WLServiceCommand serviceStatusCommand = new ServiceStatusCommand(ServiceDispatcher.MasterServiceAppID, WLClientAppName, $r1, $r2, $r3 == EServiceStatus.SS_REGISTERED, -1);
            ServiceDispatcher serviceDispatcher = this.m_dispatcher;
            ServiceDispatcher $r13 = serviceDispatcher;
            serviceDispatcher.sendWLServiceCommand(serviceStatusCommand);
        }
    }

    void processServiceStatusNotificationCommand(WLServiceCommand $r1) throws  {
        ServiceStatusCommand $r2 = new ServiceStatusCommand($r1.getRawCommandData());
        if ($r2.isValid()) {
            StatusNotificationWrapper $r8;
            String $r4 = $r2.getServiceName();
            List $r5 = $r2.getProtocols();
            boolean $z0 = $r2.isRegistered();
            int $i0 = $r2.getRegisterNotificationID();
            synchronized (this) {
                $r8 = (StatusNotificationWrapper) this.m_idToStatusWrapperMap.get($i0);
            }
            if ($r8 != null) {
                if ($z0) {
                    $r8.m_notificationHandler.onServiceRegistered($r4, $r5);
                } else {
                    $r8.m_notificationHandler.onServiceUnregistered($r4);
                }
            } else if ($r8 == null && $i0 == -1) {
                for ($i0 = 0; $i0 < this.m_idToStatusWrapperMap.size(); $i0++) {
                    int $i1 = this.m_idToStatusWrapperMap.keyAt($i0);
                    synchronized (this) {
                        $r8 = (StatusNotificationWrapper) this.m_idToStatusWrapperMap.get($i1);
                    }
                    if ($z0) {
                        $r8.m_notificationHandler.onServiceRegistered($r4, $r5);
                    } else {
                        $r8.m_notificationHandler.onServiceUnregistered($r4);
                    }
                }
            }
        }
    }

    void onConnectionEstablished(IWLConnection $r1) throws  {
        this.m_dispatcher.onConnectionEstablished($r1);
    }
}
