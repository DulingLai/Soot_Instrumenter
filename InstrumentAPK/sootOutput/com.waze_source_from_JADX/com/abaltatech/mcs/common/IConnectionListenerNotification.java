package com.abaltatech.mcs.common;

public interface IConnectionListenerNotification {
    IMCSConnectionAddress OnStartListening(IMCSConnectionAddress iMCSConnectionAddress, int i, IConnectionReceiver iConnectionReceiver) throws ;

    void OnStopListening(IMCSConnectionAddress iMCSConnectionAddress) throws ;
}
