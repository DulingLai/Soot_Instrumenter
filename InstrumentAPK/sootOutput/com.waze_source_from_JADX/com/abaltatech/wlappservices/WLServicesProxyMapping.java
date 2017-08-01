package com.abaltatech.wlappservices;

import java.util.HashMap;
import java.util.Map;

public class WLServicesProxyMapping {
    protected String m_remoteHost = "";
    protected int m_remotePort = -1;
    protected String m_remoteServiceUrl = "";
    protected Map<String, String> m_resourceMappings = new HashMap();
    protected String m_serviceName = "";
    protected String m_serviceProtocol = "";

    String GetResourceAddress(int index) throws  {
        return null;
    }

    public String getServiceName() throws  {
        return this.m_serviceName;
    }

    public String getServiceProtocol() throws  {
        return this.m_serviceProtocol;
    }

    public String getRemoteHost() throws  {
        return this.m_remoteHost;
    }

    public int getRemotePort() throws  {
        return this.m_remotePort;
    }

    public String getRemoteServiceUrl() throws  {
        return this.m_remoteServiceUrl;
    }

    public Map<String, String> getResourceMappings() throws  {
        return this.m_resourceMappings;
    }

    void SetServiceName(String $r1) throws  {
        this.m_serviceName = $r1;
    }

    void SetServiceProtocol(String $r1) throws  {
        this.m_serviceProtocol = $r1;
    }

    void SetRemoteHost(String $r1) throws  {
        this.m_remoteHost = $r1;
    }

    void SetResourceAddress(int index, String val) throws  {
    }

    void SetRemotePort(int $i0) throws  {
        this.m_remotePort = $i0;
    }

    void SetRemoteServiceUrl(String $r1) throws  {
        this.m_remoteServiceUrl = $r1;
    }
}
