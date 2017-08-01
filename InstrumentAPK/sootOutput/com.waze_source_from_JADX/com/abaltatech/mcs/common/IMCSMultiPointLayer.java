package com.abaltatech.mcs.common;

public interface IMCSMultiPointLayer {
    void attachToLayer(IMCSDataLayer iMCSDataLayer) throws ;

    void closeConnection(IMCSDataLayer iMCSDataLayer) throws MCSException;

    IMCSDataLayer createConnectionPoint(IMCSConnectionAddress iMCSConnectionAddress, IMCSConnectionAddress iMCSConnectionAddress2) throws MCSException;

    void registerNotification(IMCSMultiPointLayerNotification iMCSMultiPointLayerNotification) throws ;

    void setDumpInfo(boolean z) throws ;

    void unRegisterNotification(IMCSMultiPointLayerNotification iMCSMultiPointLayerNotification) throws ;
}
