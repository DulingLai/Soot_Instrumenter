package com.abaltatech.mcp.mcs.http;

public class NotFoundHandler implements IRequestHandler {
    public boolean canProcess(Request request) throws  {
        return true;
    }

    public boolean interruptProcessing() throws  {
        return false;
    }

    public Response processRequest(Request request, int connectionPoint) throws  {
        return new Response("Not Found", 404, null, null, true);
    }

    public void setHttpParams(IHttpLayer layer) throws  {
    }

    public Response getAdditionalResponseData(int connectionID) throws  {
        return new Response("Not Found", 404, null, null, true);
    }
}
