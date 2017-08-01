package com.abaltatech.mcp.mcs.filedownload;

public class RequestData {
    protected int m_connectionID;
    protected int m_fileHandle;
    protected long m_fileOffset;
    protected boolean m_isRange;
    protected long m_offset;
    protected long m_size;
    protected long m_totalSize;

    public void setSize(long $l0) throws  {
        this.m_size = $l0;
    }

    public long getSize() throws  {
        return this.m_size;
    }

    public long getFileOffset() throws  {
        return this.m_fileOffset;
    }

    public void setFileOffset(long $l0) throws  {
        this.m_fileOffset = $l0;
    }

    public long getOffset() throws  {
        return this.m_offset;
    }

    public void setOffset(long $l0) throws  {
        this.m_offset = $l0;
    }

    public int getConnectionID() throws  {
        return this.m_connectionID;
    }

    public void setConnectionID(int $i0) throws  {
        this.m_connectionID = $i0;
    }

    public long getTotalSize() throws  {
        return this.m_totalSize;
    }

    public void setTotalSize(long $l0) throws  {
        this.m_totalSize = $l0;
    }

    public boolean isRange() throws  {
        return this.m_isRange;
    }

    public void setRange(boolean $z0) throws  {
        this.m_isRange = $z0;
    }

    public int getFileHandle() throws  {
        return this.m_fileHandle;
    }

    public void setFileHandle(int $i0) throws  {
        this.m_fileHandle = $i0;
    }
}
