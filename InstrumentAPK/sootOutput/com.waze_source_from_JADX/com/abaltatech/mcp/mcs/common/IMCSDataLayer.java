package com.abaltatech.mcp.mcs.common;

public interface IMCSDataLayer {

    public enum EWriteMode {
        eInterlocked,
        eBuffered
    }

    void closeConnection() throws ;

    IMCSDataStats getDataStats() throws ;

    EWriteMode getWriteMode() throws ;

    boolean isReady() throws ;

    int readData(byte[] bArr, int i) throws ;

    void registerCloseNotification(IMCSConnectionClosedNotification iMCSConnectionClosedNotification) throws ;

    void registerNotification(IMCSDataLayerNotification iMCSDataLayerNotification) throws ;

    void sendConnect() throws ;

    void setWriteMode(EWriteMode eWriteMode, int i) throws ;

    void unRegisterNotification(IMCSDataLayerNotification iMCSDataLayerNotification) throws ;

    void unregisterCloseNotification(IMCSConnectionClosedNotification iMCSConnectionClosedNotification) throws ;

    void writeData(byte[] bArr, int i) throws ;
}
