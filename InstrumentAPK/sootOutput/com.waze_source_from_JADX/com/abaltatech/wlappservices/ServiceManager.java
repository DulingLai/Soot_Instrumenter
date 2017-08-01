package com.abaltatech.wlappservices;

import android.content.Context;
import com.abaltatech.mcp.weblink.core.commandhandling.IWLConnection;
import com.abaltatech.mcp.weblink.utils.AppUtils;
import dalvik.annotation.Signature;
import java.util.List;

public class ServiceManager {
    public static final int MAX_PROTOCOLS = 10;
    public static final int MAX_PROTOCOL_NAME = 50;
    public static final int MAX_SERVICE_NAME = 50;
    private static ServiceManager ms_instance;
    private Context m_context;
    private boolean m_isInitialized = false;

    public static synchronized ServiceManager getInstance() throws  {
        Class cls = ServiceManager.class;
        synchronized (cls) {
            try {
                if (ms_instance == null) {
                    ms_instance = new ServiceManager();
                }
                ServiceManager $r0 = ms_instance;
                return $r0;
            } finally {
                cls = ServiceManager.class;
            }
        }
    }

    private ServiceManager() throws  {
    }

    public synchronized void init(Context $r1) throws  {
        if (this.m_isInitialized) {
            throw new IllegalStateException("ServiceManager is already initialized");
        } else if ($r1 == null) {
            throw new UnsupportedOperationException("context cannot be null");
        } else {
            this.m_isInitialized = true;
            this.m_context = $r1;
        }
    }

    public void findServiceByName(String $r2, IServiceDiscoveryNotification $r1) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("serviceName cannot be null");
        } else if ($r1 == null) {
            throw new IllegalArgumentException("discoveryNotification cannot be null");
        } else {
            $r2 = $r2.trim();
            if ($r2.length() == 0) {
                throw new IllegalArgumentException("serviceName cannot be empty");
            }
            SecureServiceManager.getInstance().findServiceByName(AppUtils.getAppName(this.m_context), $r2, $r1);
        }
    }

    public void findServiceByProtocol(String $r2, IServiceDiscoveryNotification $r1, boolean $z0) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("protocolName cannot be null");
        } else if ($r1 == null) {
            throw new IllegalArgumentException("discoveryNotification cannot be null");
        } else {
            $r2 = $r2.trim();
            if ($r2.length() == 0) {
                throw new IllegalArgumentException("serviceName cannot be empty");
            }
            SecureServiceManager.getInstance().findServiceByProtocol(AppUtils.getAppName(this.m_context), $r2, $r1, $z0);
        }
    }

    public boolean registerForServiceStatusNotification(IServiceStatusNotification $r1) throws  {
        return SecureServiceManager.getInstance().registerForServiceStatusNotification($r1);
    }

    public boolean unRegisterForServiceStatusNotification(IServiceStatusNotification $r1) throws  {
        return SecureServiceManager.getInstance().unRegisterForServiceStatusNotification($r1);
    }

    public boolean registerService(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/IServiceHandler;", ")Z"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/IServiceHandler;", ")Z"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/IServiceHandler;", ")Z"}) IServiceHandler $r3) throws  {
        ServiceDescriptor $r4 = new ServiceDescriptor();
        $r4.setName($r1);
        $r4.setProtocols($r2);
        $r4.setServiceDescrType(EServiceDescriptorType.DynamicService);
        $r4.setServiceHandler($r3);
        $r4.setServiceProxy(new ServiceProxy_Local($r1, $r2, $r3));
        return SecureServiceManager.getInstance().registerService($r4, AppUtils.getAppName(this.m_context));
    }

    public void unregisterService(String $r1, IServiceHandler $r2) throws  {
        SecureServiceManager.getInstance().unregisterService($r1);
        if ($r2 != null) {
            $r2.removeAllNotifications();
        }
    }

    public void onConnectionEstablished(IWLConnection $r1) throws  {
        SecureServiceManager.getInstance().onConnectionEstablished($r1);
    }
}
