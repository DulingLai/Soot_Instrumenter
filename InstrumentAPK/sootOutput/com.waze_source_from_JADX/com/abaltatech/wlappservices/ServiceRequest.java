package com.abaltatech.wlappservices;

public class ServiceRequest {
    protected boolean m_allowExecuteInForeground;
    protected byte[] m_requestBody;
    protected int m_requestID;
    protected ERequestMethod m_requestMethod;

    public ERequestMethod getRequestMethod() throws  {
        return this.m_requestMethod;
    }

    public void setRequestMethod(ERequestMethod $r1) throws  {
        this.m_requestMethod = $r1;
    }

    public byte[] getRequestBody() throws  {
        return this.m_requestBody;
    }

    public void setRequestBody(byte[] $r1) throws  {
        this.m_requestBody = $r1;
    }

    public boolean getAllowExecuteInForeground() throws  {
        return this.m_allowExecuteInForeground;
    }

    public void setAllowExecuteInForeground(boolean $z0) throws  {
        this.m_allowExecuteInForeground = $z0;
    }

    public int getRequestID() throws  {
        return this.m_requestID;
    }
}
