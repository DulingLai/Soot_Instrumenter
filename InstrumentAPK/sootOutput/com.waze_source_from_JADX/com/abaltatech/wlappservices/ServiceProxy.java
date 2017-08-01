package com.abaltatech.wlappservices;

import dalvik.annotation.Signature;
import java.util.List;

public abstract class ServiceProxy {
    private List<String> m_protocols;
    private String m_serviceName;

    ServiceProxy(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r2) throws  {
        this.m_serviceName = $r1;
        this.m_protocols = $r2;
    }

    public int sendRequest(String $r1, ServiceRequest $r2, IServiceResponseNotification $r3) throws  {
        if ($r3 == null) {
            throw new IllegalArgumentException("notification cannot be null");
        } else if ($r2 == null) {
            throw new IllegalArgumentException("request cannot be null");
        } else if ($r1 != null) {
            return SecureServiceManager.getInstance().sendRequest(this, $r1, $r2, $r3);
        } else {
            throw new IllegalArgumentException("resourcePath cannot be null");
        }
    }

    public boolean cancelRequest(int $i0) throws  {
        return SecureServiceManager.getInstance().cancelServiceRequest($i0);
    }

    public boolean registerForNotification(String $r1, IServiceNotificationHandler $r2) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("resourcePath cannot be null");
        } else if ($r2 != null) {
            return SecureServiceManager.getInstance().registerForNotification(this, $r1, $r2);
        } else {
            throw new IllegalArgumentException("handler cannot be null");
        }
    }

    public boolean unregisterFromNotification(String $r1, IServiceNotificationHandler $r2) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("resourcePath cannot be null");
        } else if ($r2 != null) {
            return SecureServiceManager.getInstance().unregisterForNotification(this, $r1, $r2);
        } else {
            throw new IllegalArgumentException("handler cannot be null");
        }
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public List<String> getProtocols() throws  {
        return this.m_protocols;
    }
}
