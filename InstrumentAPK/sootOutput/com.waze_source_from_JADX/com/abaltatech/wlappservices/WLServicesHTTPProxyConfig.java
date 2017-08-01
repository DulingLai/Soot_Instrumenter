package com.abaltatech.wlappservices;

import java.util.ArrayList;
import java.util.List;

public class WLServicesHTTPProxyConfig {
    List<WLServicesProxyMapping> m_services = new ArrayList();

    public WLServicesHTTPProxyConfig() throws  {
        WLServicesProxyMapping $r1 = new WLServicesProxyMapping();
        $r1.SetServiceName("com.wlservices.abaltatech.timerservice.proxy");
        $r1.SetServiceProtocol("timer");
        $r1.SetRemoteHost("localhost");
        $r1.SetRemotePort(7777);
        $r1.SetRemoteServiceUrl("/");
        $r1.SetResourceAddress(0, "/timer");
        $r1.SetResourceAddress(1, "timer");
        this.m_services.add($r1);
    }

    public List<WLServicesProxyMapping> getServices() throws  {
        return this.m_services;
    }
}
