package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.http.android.HttpServer;
import com.abaltatech.mcp.mcs.http.android.HttpServer.HttpServerOptions;
import com.abaltatech.mcp.mcs.tcpip.TCPIPAddress;
import java.util.ArrayList;
import java.util.List;

public class WLServicesHTTPProxy {
    private static final String TAG = WLServicesHTTPProxy.class.getSimpleName();
    private static WLServicesHTTPProxy instance = null;
    private static final int s_listeningPort = 9090;
    protected WLServicesHTTPProxyConfig m_config = new WLServicesHTTPProxyConfig();
    protected WLServicesHTTPRequestHandler m_handler = null;
    protected HttpServer m_httpServer = null;
    protected boolean m_isServerStarted = false;
    protected List<ServiceHandlerInfo> m_serviceHandlers = new ArrayList();

    private static class ServiceHandlerInfo {
        public String first = null;
        public WLServicesHTTPProxyServiceHandler second = null;

        public ServiceHandlerInfo(String $r1, WLServicesHTTPProxyServiceHandler $r2) throws  {
            this.first = $r1;
            this.second = $r2;
        }
    }

    public static int getListeningPort() throws  {
        return s_listeningPort;
    }

    private WLServicesHTTPProxy() throws  {
    }

    public static synchronized WLServicesHTTPProxy getInstance() throws  {
        Class cls = WLServicesHTTPProxy.class;
        synchronized (cls) {
            try {
                if (instance == null) {
                    instance = new WLServicesHTTPProxy();
                }
                WLServicesHTTPProxy $r0 = instance;
                return $r0;
            } finally {
                cls = WLServicesHTTPProxy.class;
            }
        }
    }

    public boolean isWLServicesClientRunning() throws  {
        return (this.m_handler == null || this.m_httpServer == null || !this.m_httpServer.isServerStarted()) ? false : true;
    }

    public boolean isWLServicesServerRunning() throws  {
        return this.m_isServerStarted;
    }

    public boolean startWLServicesClient() throws  {
        boolean $z0 = false;
        if (!(this.m_handler == null && this.m_httpServer == null)) {
            stopWLServicesClient();
        }
        try {
            this.m_handler = new WLServicesHTTPRequestHandler();
            this.m_httpServer = new HttpServer(new TCPIPAddress(null));
            this.m_httpServer.registerHandler(this.m_handler);
            HttpServerOptions $r2 = new HttpServerOptions();
            $r2.m_httpListenPort = s_listeningPort;
            boolean $z1 = this.m_httpServer.startServer($r2);
            $z0 = $z1;
            Log.d(TAG, "startWLServicesClient: http server start returned:" + $z1);
            return $z1;
        } catch (MCSException $r1) {
            Log.e(TAG, "ERROR creating WLServicesHTTPProxy: ", $r1);
            this.m_httpServer = null;
            this.m_handler = null;
            return $z0;
        }
    }

    public void stopWLServicesClient() throws  {
        if (this.m_httpServer != null) {
            this.m_httpServer.stopServer();
            this.m_httpServer = null;
            Log.d(TAG, "stopWLServicesClient: http server stopped");
        }
        if (this.m_handler != null) {
            this.m_handler.terminate();
            this.m_handler = null;
            Log.d(TAG, "stopWLServicesClient: handler terminated");
        }
    }

    public boolean startWLServicesServer(String configFile) throws  {
        List<WLServicesProxyMapping> $r6 = this.m_config.getServices();
        if (this.m_isServerStarted || $r6 == null || $r6.isEmpty()) {
            Log.w(TAG, "Could not start WL Services Server.  No services found!");
            return false;
        }
        this.m_serviceHandlers.clear();
        for (WLServicesProxyMapping $r10 : $r6) {
            ArrayList $r4 = new ArrayList();
            configFile = $r10.getServiceName();
            $r4.add($r10.getServiceProtocol());
            WLServicesHTTPProxyServiceHandler $r2 = new WLServicesHTTPProxyServiceHandler($r10.getRemoteHost(), $r10.getRemotePort(), $r10.getRemoteServiceUrl(), $r10.getResourceMappings());
            this.m_serviceHandlers.add(new ServiceHandlerInfo(configFile, $r2));
            ServiceManager.getInstance().registerService(configFile, $r4, $r2);
        }
        this.m_isServerStarted = true;
        return true;
    }

    public void stopWLServicesServer() throws  {
        if (this.m_isServerStarted) {
            for (ServiceHandlerInfo $r6 : this.m_serviceHandlers) {
                ServiceManager.getInstance().unregisterService($r6.first, $r6.second);
            }
            this.m_serviceHandlers.clear();
            this.m_isServerStarted = false;
            return;
        }
        Log.d(TAG, "No WL Services server active to stop!");
    }
}
