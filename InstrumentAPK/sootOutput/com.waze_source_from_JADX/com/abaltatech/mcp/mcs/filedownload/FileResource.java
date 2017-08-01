package com.abaltatech.mcp.mcs.filedownload;

public class FileResource {
    private long m_contentLength;
    private boolean m_isLocked;
    private long m_lastChecksum;
    private String m_localPath;
    private String m_remoteName;

    public FileResource(String $r1, String $r2, long $l0, long $l1) throws  {
        this.m_localPath = $r1;
        this.m_remoteName = $r2;
        this.m_lastChecksum = $l1;
        this.m_contentLength = $l0;
    }

    public boolean isLocked() throws  {
        return this.m_isLocked;
    }

    public void setLocked(boolean $z0) throws  {
        this.m_isLocked = $z0;
    }

    public String getLocalPath() throws  {
        return this.m_localPath;
    }

    public void setLocalPath(String $r1) throws  {
        this.m_localPath = $r1;
    }

    public String getRemoteName() throws  {
        return this.m_remoteName;
    }

    public void setRemoteName(String $r1) throws  {
        this.m_remoteName = $r1;
    }

    public long getLastChecksum() throws  {
        return this.m_lastChecksum;
    }

    public void setLastChecksum(long $l0) throws  {
        this.m_lastChecksum = $l0;
    }

    public long getContentLength() throws  {
        return this.m_contentLength;
    }

    public void setContentLength(long $l0) throws  {
        this.m_contentLength = $l0;
    }
}
