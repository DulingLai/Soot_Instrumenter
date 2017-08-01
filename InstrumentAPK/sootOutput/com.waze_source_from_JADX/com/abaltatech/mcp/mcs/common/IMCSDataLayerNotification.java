package com.abaltatech.mcp.mcs.common;

public interface IMCSDataLayerNotification extends IMCSConnectionClosedNotification {
    void onDataReceived(IMCSDataLayer iMCSDataLayer) throws ;
}
