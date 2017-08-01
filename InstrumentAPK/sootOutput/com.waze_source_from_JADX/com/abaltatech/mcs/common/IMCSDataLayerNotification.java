package com.abaltatech.mcs.common;

public interface IMCSDataLayerNotification extends IMCSConnectionClosedNotification {
    void onDataReceived(IMCSDataLayer iMCSDataLayer) throws ;
}
