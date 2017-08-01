package com.abaltatech.mcp.mcs.common;

public class MemoryPoolTracker {
    public String m_borrower;
    public byte[] m_memory;
    public MemoryPoolTracker m_next = null;
    public MemoryPoolTracker m_previous = null;

    public MemoryPoolTracker(byte[] $r1, String $r2) throws  {
        this.m_memory = $r1;
        this.m_borrower = $r2;
    }
}
