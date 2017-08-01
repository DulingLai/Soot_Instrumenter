package com.abaltatech.wlappservices;

public interface IServiceHandler {
    boolean onCancelRequest(int i) throws ;

    int onProcessRequest(String str, ServiceRequest serviceRequest, IServiceResponseNotification iServiceResponseNotification) throws ;

    void registerForNotification(String str, IServiceNotificationHandler iServiceNotificationHandler) throws ;

    void removeAllNotifications() throws ;

    void unregisterFromNotification(String str, IServiceNotificationHandler iServiceNotificationHandler) throws ;
}
