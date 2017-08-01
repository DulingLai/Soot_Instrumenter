package com.abaltatech.mcp.mcs.common;

public interface IConnectionReceiver {
    void OnConnectionEstablished(IMCSConnectionAddress iMCSConnectionAddress, IMCSConnectionAddress iMCSConnectionAddress2, IMCSDataLayer iMCSDataLayer, IMCSMultiPointLayer iMCSMultiPointLayer) throws ;

    void OnConnectionFailed() throws ;
}
