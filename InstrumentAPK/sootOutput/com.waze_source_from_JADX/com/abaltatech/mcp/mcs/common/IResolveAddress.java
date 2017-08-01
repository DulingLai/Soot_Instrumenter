package com.abaltatech.mcp.mcs.common;

public interface IResolveAddress {
    void RegisterNotification(IResolveAddressCompletedNotification iResolveAddressCompletedNotification) throws ;

    void RegisterNotification(IResolveAddressRequestedNotification iResolveAddressRequestedNotification) throws ;

    int SendResolveAddressRequest(String str) throws ;

    boolean SendResolveAddressResponse(int i, String str, IMCSConnectionAddress iMCSConnectionAddress) throws ;

    void UnregisterNotification(IResolveAddressCompletedNotification iResolveAddressCompletedNotification) throws ;

    void UnregisterNotification(IResolveAddressRequestedNotification iResolveAddressRequestedNotification) throws ;
}
