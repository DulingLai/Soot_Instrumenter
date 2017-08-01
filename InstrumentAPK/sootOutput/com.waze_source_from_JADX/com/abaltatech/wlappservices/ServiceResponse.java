package com.abaltatech.wlappservices;

public class ServiceResponse {
    private int requestID;
    private byte[] responseBody;

    public int getRequestID() throws  {
        return this.requestID;
    }

    public void setRequestID(int $i0) throws  {
        this.requestID = $i0;
    }

    public byte[] getResponseBody() throws  {
        return this.responseBody;
    }

    public void setResponseBody(byte[] $r1) throws  {
        this.responseBody = $r1;
    }
}
