package com.abaltatech.mcs.common;

public interface IDatagramHandler {
    void RegisterNotification(IDatagramHandlerNotification iDatagramHandlerNotification) throws ;

    void SendDatagram(int i, IMCSConnectionAddress iMCSConnectionAddress, byte[] bArr, int i2) throws ;

    int StartDatagramListening(IMCSConnectionAddress iMCSConnectionAddress, IDatagramReceiver iDatagramReceiver) throws ;

    void StopDatagramListening(int i) throws ;

    void UnregisterNotification(IDatagramHandlerNotification iDatagramHandlerNotification) throws ;
}
