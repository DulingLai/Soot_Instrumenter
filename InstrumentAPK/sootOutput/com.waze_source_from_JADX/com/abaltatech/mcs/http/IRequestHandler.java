package com.abaltatech.mcs.http;

public interface IRequestHandler {
    boolean canProcess(Request request) throws ;

    Response processRequest(Request request, int i) throws ;

    void setHttpParams(IHttpLayer iHttpLayer) throws ;
}
