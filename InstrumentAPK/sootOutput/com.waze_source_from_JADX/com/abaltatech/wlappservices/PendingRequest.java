package com.abaltatech.wlappservices;

class PendingRequest extends ServiceRequest {
    private static int m_nextRequestID = 0;
    private IServiceResponseNotification m_notification;
    private int m_remoteRequestID = 0;
    private String m_resourcePath;
    private ServiceProxy m_serviceProxy;

    PendingRequest(ServiceProxy $r1, String $r2, ServiceRequest $r3, IServiceResponseNotification $r4) throws  {
        this.m_serviceProxy = $r1;
        this.m_resourcePath = $r2;
        this.m_requestMethod = $r3.m_requestMethod;
        this.m_requestBody = $r3.m_requestBody;
        this.m_allowExecuteInForeground = $r3.m_allowExecuteInForeground;
        this.m_notification = $r4;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getNextRequestID() throws  {
        /*
        r3 = this;
        r0 = com.abaltatech.wlappservices.PendingRequest.class;
        monitor-enter(r0);
        r1 = m_nextRequestID;	 Catch:{ Throwable -> 0x000f }
        r1 = r1 + 1;
        m_nextRequestID = r1;	 Catch:{ Throwable -> 0x000f }
        r1 = m_nextRequestID;	 Catch:{ Throwable -> 0x000f }
        r0 = com.abaltatech.wlappservices.PendingRequest.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x000f }
        return r1;
    L_0x000f:
        r2 = move-exception;
        r0 = com.abaltatech.wlappservices.PendingRequest.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x000f }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.wlappservices.PendingRequest.getNextRequestID():int");
    }

    public ServiceProxy getServiceProxy() throws  {
        return this.m_serviceProxy;
    }

    public String getResourcePath() throws  {
        return this.m_resourcePath;
    }

    public IServiceResponseNotification getNotification() throws  {
        return this.m_notification;
    }

    public int getRemoteRequestID() throws  {
        return this.m_remoteRequestID;
    }

    public void setRemoteRequestID(int $i0) throws  {
        this.m_remoteRequestID = $i0;
    }
}
