package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
    private static final int DEFAULT_SIZE = 256;
    private final ByteArrayPool mPool;

    public PoolingByteArrayOutputStream(ByteArrayPool $r1) throws  {
        this($r1, 256);
    }

    public PoolingByteArrayOutputStream(ByteArrayPool $r1, int $i0) throws  {
        this.mPool = $r1;
        this.buf = this.mPool.getBuf(Math.max($i0, 256));
    }

    public void close() throws IOException {
        this.mPool.returnBuf(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() throws  {
        this.mPool.returnBuf(this.buf);
    }

    private void expand(int $i0) throws  {
        if (this.count + $i0 > this.buf.length) {
            byte[] $r1 = this.mPool.getBuf((this.count + $i0) * 2);
            System.arraycopy(this.buf, 0, $r1, 0, this.count);
            this.mPool.returnBuf(this.buf);
            this.buf = $r1;
        }
    }

    public synchronized void write(byte[] $r1, int $i0, int $i1) throws  {
        expand($i1);
        super.write($r1, $i0, $i1);
    }

    public synchronized void write(int $i0) throws  {
        expand(1);
        super.write($i0);
    }
}
