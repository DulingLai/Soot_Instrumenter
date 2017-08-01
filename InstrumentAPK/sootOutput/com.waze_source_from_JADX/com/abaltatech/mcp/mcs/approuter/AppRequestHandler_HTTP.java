package com.abaltatech.mcp.mcs.approuter;

import com.abaltatech.mcp.mcs.http.IHttpLayer;
import com.abaltatech.mcp.mcs.http.Request;
import com.abaltatech.mcp.mcs.http.Response;

public class AppRequestHandler_HTTP extends AppRequestHandler {
    private final AppRouter_HTTP m_router;

    public /* bridge */ /* synthetic */ boolean canProcess(Request $r1) throws  {
        return super.canProcess($r1);
    }

    public /* bridge */ /* synthetic */ Response getAdditionalResponseData(int $i0) throws  {
        return super.getAdditionalResponseData($i0);
    }

    public /* bridge */ /* synthetic */ MsgInfo getMessage() throws  {
        return super.getMessage();
    }

    public /* bridge */ /* synthetic */ boolean interruptProcessing() throws  {
        return super.interruptProcessing();
    }

    public /* bridge */ /* synthetic */ void onServerStarted(IAppServer $r1) throws  {
        super.onServerStarted($r1);
    }

    public /* bridge */ /* synthetic */ Response processRequest(Request $r1, int $i0) throws  {
        return super.processRequest($r1, $i0);
    }

    public /* bridge */ /* synthetic */ void setHttpParams(IHttpLayer $r1) throws  {
        super.setHttpParams($r1);
    }

    public AppRequestHandler_HTTP(AppDescriptor $r1, AppRouter_HTTP $r2) throws  {
        super($r1);
        this.m_router = $r2;
    }

    Response createResponse(AppMessage $r1) throws  {
        Response $r3 = super.createResponse($r1);
        if ($r1.AdditionalHeaders != null) {
            $r3.AdditionalHeaders = $r1.AdditionalHeaders;
        }
        if (!this.m_router.getAllowCrossDomainOrigin()) {
            return $r3;
        }
        if ($r3.AdditionalHeaders == null) {
            $r3.AdditionalHeaders = new String[1];
            $r3.AdditionalHeaders[0] = "Access-Control-Allow-Origin: *";
            return $r3;
        }
        String[] $r2 = new String[($r3.AdditionalHeaders.length + 1)];
        System.arraycopy($r3.AdditionalHeaders, 0, $r2, 0, $r3.AdditionalHeaders.length);
        $r2[$r3.AdditionalHeaders.length] = "Access-Control-Allow-Origin: *";
        $r3.AdditionalHeaders = $r2;
        return $r3;
    }
}
