package com.abaltatech.mcs.approuter;

public class AppMessage {
    public String[] AdditionalHeaders = null;
    public int m_connectionID;
    public String m_contentType;
    public boolean m_isLast = true;
    public String m_message;
    public byte[] m_messageData;
    public boolean m_sendOnlyData = false;
    public int m_statusCode;

    public AppMessage(String $r1, String $r2, byte[] $r3, int $i0, int $i1) throws  {
        this.m_message = $r1;
        this.m_contentType = $r2;
        this.m_messageData = $r3;
        this.m_statusCode = $i0;
        this.m_connectionID = $i1;
    }
}
