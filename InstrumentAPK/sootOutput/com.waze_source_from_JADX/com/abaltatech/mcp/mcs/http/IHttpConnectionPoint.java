package com.abaltatech.mcp.mcs.http;

import com.abaltatech.mcp.mcs.common.IMCSDataLayer;

public interface IHttpConnectionPoint {
    IMCSDataLayer getDataLayer() throws ;

    int getID() throws ;

    void onDataReceived(IMCSDataLayer iMCSDataLayer) throws ;

    void sendResponse(Response response) throws ;
}
