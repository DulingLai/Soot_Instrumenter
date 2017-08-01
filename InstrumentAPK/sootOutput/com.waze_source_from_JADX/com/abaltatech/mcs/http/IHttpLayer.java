package com.abaltatech.mcs.http;

public interface IHttpLayer {
    IRequestHandler findHandler(Request request) throws ;

    void onConnectionClosed(IHttpConnectionPoint iHttpConnectionPoint) throws ;

    void registerHandler(IRequestHandler iRequestHandler) throws ;

    void sendResponse(int i, Response response) throws ;

    void unregisterHandler(IRequestHandler iRequestHandler) throws ;
}
