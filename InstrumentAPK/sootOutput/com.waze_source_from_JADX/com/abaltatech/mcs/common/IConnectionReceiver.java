package com.abaltatech.mcs.common;

public interface IConnectionReceiver {
    void OnConnectionEstablished(IMCSConnectionAddress iMCSConnectionAddress, IMCSConnectionAddress iMCSConnectionAddress2, IMCSDataLayer iMCSDataLayer) throws ;
}
