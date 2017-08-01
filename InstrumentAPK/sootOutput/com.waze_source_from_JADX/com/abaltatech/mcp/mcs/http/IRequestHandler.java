package com.abaltatech.mcp.mcs.http;

public interface IRequestHandler {
    boolean canProcess(Request request) throws ;

    Response getAdditionalResponseData(int i) throws ;

    boolean interruptProcessing() throws ;

    Response processRequest(Request request, int i) throws ;

    void setHttpParams(IHttpLayer iHttpLayer) throws ;
}
