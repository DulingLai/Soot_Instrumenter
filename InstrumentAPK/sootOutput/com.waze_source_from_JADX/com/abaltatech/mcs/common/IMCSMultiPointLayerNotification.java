package com.abaltatech.mcs.common;

public interface IMCSMultiPointLayerNotification {
    void newConnectionRequested(IMCSConnectionAddress iMCSConnectionAddress, IMCSConnectionAddress iMCSConnectionAddress2, byte[] bArr) throws ;

    void onConnectionClosed(IMCSDataLayer iMCSDataLayer, IMCSConnectionAddress iMCSConnectionAddress, IMCSConnectionAddress iMCSConnectionAddress2) throws ;
}
