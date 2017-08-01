package com.abaltatech.wlappservices;

import com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler;
import dalvik.annotation.Signature;
import java.util.Date;
import java.util.List;

final class ServiceDescriptor {
    private IWLAppsServiceHandler m_WLAppsServiceHandler;
    private String m_name;
    private List<String> m_protocols;
    private EServiceDescriptorType m_serviceDescrType;
    private int m_serviceDescriptorID;
    private IServiceHandler m_serviceHandler;
    private int m_servicePortNumber;
    private ServiceProxy m_serviceProxy;
    private Date m_timestamp = new Date();

    ServiceDescriptor() throws  {
    }

    public String getName() throws  {
        return this.m_name;
    }

    public void setName(String $r1) throws  {
        this.m_name = $r1;
    }

    public List<String> getProtocols() throws  {
        return this.m_protocols;
    }

    public void setProtocols(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r1) throws  {
        this.m_protocols = $r1;
    }

    public int getServicePortNumber() throws  {
        return this.m_servicePortNumber;
    }

    public void setServicePortNumber(int $i0) throws  {
        this.m_servicePortNumber = $i0;
    }

    public Date getTimestamp() throws  {
        return this.m_timestamp;
    }

    public EServiceDescriptorType getServiceDescrType() throws  {
        return this.m_serviceDescrType;
    }

    public void setServiceDescrType(EServiceDescriptorType $r1) throws  {
        this.m_serviceDescrType = $r1;
    }

    public IServiceHandler getServiceHandler() throws  {
        return this.m_serviceHandler;
    }

    public void setServiceHandler(IServiceHandler $r1) throws  {
        this.m_serviceHandler = $r1;
    }

    public IWLAppsServiceHandler getWLAppsServiceHandler() throws  {
        return this.m_WLAppsServiceHandler;
    }

    public void setWLAppsServiceHandler(IWLAppsServiceHandler $r1) throws  {
        this.m_WLAppsServiceHandler = $r1;
    }

    public ServiceProxy getServiceProxy() throws  {
        return this.m_serviceProxy;
    }

    public void setServiceProxy(ServiceProxy $r1) throws  {
        this.m_serviceProxy = $r1;
    }

    public int getServiceDescriptorID() throws  {
        return this.m_serviceDescriptorID;
    }

    public void setServiceDescriptorID(int $i0) throws  {
        this.m_serviceDescriptorID = $i0;
    }
}
