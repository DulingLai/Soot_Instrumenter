package com.abaltatech.mcp.mcs.common;

public interface IDatagramHandlerNotification {
    void OnSendDatagram(int i, IMCSConnectionAddress iMCSConnectionAddress, byte[] bArr, int i2) throws ;

    int OnStartDatagramListening(IMCSConnectionAddress iMCSConnectionAddress, IDatagramReceiver iDatagramReceiver) throws ;

    void OnStopDatagramListening(int i) throws ;
}
