package com.abaltatech.wlappservices;

public interface IServiceDiscoveryNotification {
    void onServiceDiscoveryCompleted(int i) throws ;

    void onServiceDiscoveryFailed(EServiceDiscoveryErrorCode eServiceDiscoveryErrorCode) throws ;

    boolean onServiceFound(ServiceProxy serviceProxy, int i) throws ;
}
