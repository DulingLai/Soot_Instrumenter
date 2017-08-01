package com.abaltatech.mcp.mcs.approuter;

import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.http.HttpUtils;
import com.abaltatech.mcp.mcs.http.IHttpLayer;
import com.abaltatech.mcp.mcs.http.IRequestHandler;
import com.abaltatech.mcp.mcs.http.Request;
import com.abaltatech.mcp.mcs.http.Response;
import com.abaltatech.mcp.mcs.logger.MCSLogger;

class AppRequestHandler implements IRequestHandler {
    private AppDescriptor m_app;
    private IHttpLayer m_httpLayer = null;
    private String m_name;

    public Response getAdditionalResponseData(int connectionID) throws  {
        return null;
    }

    public boolean interruptProcessing() throws  {
        return false;
    }

    public AppRequestHandler(AppDescriptor $r1) throws  {
        if ($r1 != null) {
            this.m_app = $r1;
            this.m_name = $r1.AppID.m_appName.toLowerCase();
        }
    }

    public boolean canProcess(Request $r1) throws  {
        String $r2 = $r1 != null ? HttpUtils.extractUrlPath($r1.Url) : null;
        if ($r2 == null || !$r2.toLowerCase().startsWith(this.m_name)) {
            return false;
        }
        return true;
    }

    public Response processRequest(Request $r1, int $i0) throws  {
        AppMessage appMessage = new AppMessage(HttpUtils.extractUrlPath($r1.Url.toLowerCase()), $r1.Method.toUpperCase(), $r1.Data, 0, $i0);
        AppMessage $r8 = null;
        synchronized (this.m_app) {
            if (this.m_app.Server == null) {
                IStartupConfig $r12 = this.m_app.Config;
                AppID $r13 = this.m_app.AppID;
                $r12.startApp($r13);
            }
            if (this.m_app.Server != null) {
                $r8 = this.m_app.Server.onNewRequest(appMessage);
            } else {
                MsgInfo msgInfo = new MsgInfo(appMessage);
                DataQueueMsg dataQueueMsg = this.m_app.MessageQueue;
                DataQueueMsg $r14 = dataQueueMsg;
                try {
                    dataQueueMsg.insert(msgInfo);
                } catch (MCSException $r4) {
                    MCSLogger.log("RequestHandlerWrapper", $r4.toString());
                }
            }
        }
        if ($r8 == null) {
            return null;
        }
        return createResponse($r8);
    }

    public void setHttpParams(IHttpLayer $r1) throws  {
        this.m_httpLayer = $r1;
    }

    public void onServerStarted(IAppServer $r1) throws  {
        while (true) {
            MsgInfo $r3 = getMessage();
            if ($r3 != null) {
                synchronized ($r1) {
                    while ($r1.getAppState() != 2) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException $r2) {
                            MCSLogger.log("RequestHandlerWrapper", $r2.toString());
                        }
                    }
                    AppMessage $r6 = $r1.onNewRequest($r3.Message);
                    if ($r6 != null) {
                        IHttpLayer $r9;
                        int $i0 = $r3.Message.m_connectionID;
                        Response $r8 = createResponse($r6);
                        synchronized (this) {
                            $r9 = this.m_httpLayer;
                        }
                        if ($r9 == null) {
                            return;
                        }
                        $r9.sendResponse($i0, $r8);
                    }
                }
            } else {
                return;
            }
        }
    }

    Response createResponse(AppMessage $r1) throws  {
        Response $r2 = new Response($r1.m_message, $r1.m_statusCode, $r1.m_contentType, $r1.m_messageData, $r1.m_isLast);
        $r2.AdditionalHeaders = $r1.AdditionalHeaders;
        $r2.SendOnlyData = $r1.m_sendOnlyData;
        return $r2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.abaltatech.mcp.mcs.approuter.MsgInfo getMessage() throws  {
        /*
        r10 = this;
        r0 = r10.m_app;
        monitor-enter(r0);
        r1 = r10.m_app;	 Catch:{ Throwable -> 0x0024 }
        r2 = r1.MessageQueue;	 Catch:{ Throwable -> 0x0024 }
        r3 = r2.size();	 Catch:{ Throwable -> 0x0024 }
        if (r3 <= 0) goto L_0x0021;
    L_0x000d:
        r1 = r10.m_app;
        r2 = r1.MessageQueue;
        r4 = r2.delete();	 Catch:{ MCSException -> 0x0017 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0024 }
        return r4;
    L_0x0017:
        r5 = move-exception;
        r6 = r5.toString();	 Catch:{ Throwable -> 0x0024 }
        r7 = "RequestHandlerWrapper";
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r7, r6);	 Catch:{ Throwable -> 0x0024 }
    L_0x0021:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0024 }
        r8 = 0;
        return r8;
    L_0x0024:
        r9 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0024 }
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.approuter.AppRequestHandler.getMessage():com.abaltatech.mcp.mcs.approuter.MsgInfo");
    }
}
