package com.abaltatech.wlappservices;

public interface IServiceResponseNotification {
    void onRequestFailed(ServiceRequest serviceRequest, EServiceErrorCode eServiceErrorCode) throws ;

    void onResponseReceived(ServiceRequest serviceRequest, ServiceResponse serviceResponse) throws ;
}
