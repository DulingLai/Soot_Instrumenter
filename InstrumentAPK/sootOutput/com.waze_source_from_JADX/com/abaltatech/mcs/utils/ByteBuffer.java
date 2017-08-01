package com.abaltatech.mcs.utils;

public class ByteBuffer {
    private byte[] m_data = null;
    private int m_size = 0;

    public ByteBuffer(int $i0) throws  {
        byte[] $r1 = null;
        if ($i0 > 0) {
            $r1 = new byte[$i0];
        }
        this.m_data = $r1;
        this.m_size = $i0;
    }

    public ByteBuffer(byte[] $r1, int $i0) throws  {
        byte[] $r2 = null;
        this.m_size = $i0;
        if ($i0 > 0) {
            $r2 = new byte[$i0];
        }
        this.m_data = $r2;
        if ($i0 > 0) {
            System.arraycopy($r1, 0, this.m_data, 0, $i0);
        }
    }

    public int getSize() throws  {
        return this.m_size;
    }

    public byte[] getData() throws  {
        return this.m_data;
    }
}
