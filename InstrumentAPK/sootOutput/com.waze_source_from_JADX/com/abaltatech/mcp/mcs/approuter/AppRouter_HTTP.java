package com.abaltatech.mcp.mcs.approuter;

import com.abaltatech.mcp.mcs.http.IHttpLayer;

public abstract class AppRouter_HTTP extends AppRouter_base {
    private boolean m_allowCrossDomainOrigin = true;
    private AppRequestHandler m_dummy;
    private IHttpLayer m_http;
    private AppRequestHandler[] m_requestHandlers = new AppRequestHandler[16];

    public AppRouter_HTTP(IHttpLayer $r1) throws  {
        this.m_http = $r1;
        this.m_dummy = new AppRequestHandler_HTTP(null, this);
    }

    protected void onServerRegistered(int $i0, AppDescriptor $r1) throws  {
        Throwable $r6;
        synchronized (this.m_requestHandlers) {
            try {
                AppRequestHandler_HTTP $r2 = new AppRequestHandler_HTTP($r1, this);
                try {
                    this.m_requestHandlers[$i0] = $r2;
                    this.m_http.registerHandler($r2);
                } catch (Throwable th) {
                    $r6 = th;
                    throw $r6;
                }
            } catch (Throwable th2) {
                $r6 = th2;
                throw $r6;
            }
        }
    }

    protected void onServerUnregistered(int $i0, AppDescriptor descriptor) throws  {
        synchronized (this.m_requestHandlers) {
            AppRequestHandler $r4 = this.m_requestHandlers[$i0];
            this.m_requestHandlers[$i0] = null;
        }
        if ($r4 != null) {
            this.m_http.unregisterHandler($r4);
        }
    }

    protected void onServerStarted(int $i0, IAppServer $r1) throws  {
        synchronized (this.m_requestHandlers) {
            AppRequestHandler $r4 = this.m_requestHandlers[$i0];
        }
        if ($r4 != null) {
            $r4.onServerStarted($r1);
        }
    }

    protected void onServerStopped(int id, IAppServer server) throws  {
    }

    public void sendAppResponse(AppMessage $r1) throws  {
        this.m_http.sendResponse($r1.m_connectionID, this.m_dummy.createResponse($r1));
    }

    public synchronized boolean getAllowCrossDomainOrigin() throws  {
        return this.m_allowCrossDomainOrigin;
    }

    public synchronized void setAllowCrossDomainOrigin(boolean $z0) throws  {
        this.m_allowCrossDomainOrigin = $z0;
    }
}
