package com.abaltatech.mcs.common;

public interface IConnectionListener {
    void RegisterNotification(IConnectionListenerNotification iConnectionListenerNotification) throws ;

    IMCSConnectionAddress StartListening(IMCSConnectionAddress iMCSConnectionAddress, int i, IConnectionReceiver iConnectionReceiver) throws ;

    void StopListening(IMCSConnectionAddress iMCSConnectionAddress) throws ;

    void UnregisterNotification(IConnectionListenerNotification iConnectionListenerNotification) throws ;
}
