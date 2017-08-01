package com.abaltatech.mcs.utils;

import java.util.concurrent.Semaphore;

public class BufferQueue {
    private Semaphore appendsem = new Semaphore(1, true);
    private byte[] buff;
    private volatile int count;
    private Semaphore countsem = new Semaphore(1, true);
    private volatile int head;
    private Semaphore readsem = new Semaphore(1, true);
    private volatile int size;
    private volatile int tail;

    public BufferQueue(int $i0) throws  {
        this.buff = new byte[$i0];
        this.size = $i0;
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    public int getCount() throws  {
        return this.count;
    }

    public void append(byte[] $r1) throws  {
        if ($r1 != null) {
            append($r1, 0, $r1.length);
        }
    }

    public void append(byte[] $r1, int $i0, int $i1) throws  {
        if ($r1 != null) {
            if ($r1.length < $i0 + $i1) {
                throw new RuntimeException("array index out of bounds. offset + length extends beyond the length of the array.");
            }
            try {
                this.appendsem.acquire();
                for (int $i2 = 0; $i2 < $i1; $i2++) {
                    this.buff[(this.tail + $i2) % this.size] = $r1[$i2 + $i0];
                }
                this.tail = (this.tail + $i1) % this.size;
                try {
                    this.countsem.acquire();
                    this.count += $i1;
                    if (this.count > this.size) {
                        throw new RuntimeException("Buffer overflow error.");
                    }
                    this.countsem.release();
                    this.appendsem.release();
                } catch (InterruptedException e) {
                }
            } catch (InterruptedException e2) {
            }
        }
    }

    public int read(byte[] $r1) throws  {
        if ($r1 != null) {
            return read($r1, 0, $r1.length);
        }
        return 0;
    }

    public int read(byte[] $r1, int offset, int $i1) throws  {
        if ($r1 == null) {
            return 0;
        }
        if ($r1.length < offset + $i1) {
            throw new RuntimeException("array index out of bounds. offset + length extends beyond the length of the array.");
        }
        int $i3 = 0;
        try {
            this.readsem.acquire();
            int $i4 = 0;
            while ($i4 < $i1 && $i4 != this.count) {
                $r1[$i4 + offset] = this.buff[(this.head + $i4) % this.size];
                $i3++;
                $i4++;
            }
            int $i0 = this.head;
            $i0 = ($i0 + $i3) % this.size;
            offset = $i0;
            this.head = $i0;
            try {
                this.countsem.acquire();
                $i0 = this.count;
                $i0 -= $i3;
                this.count = $i0;
                this.countsem.release();
                this.readsem.release();
                return $i3;
            } catch (InterruptedException e) {
                this.readsem.release();
                return 0;
            }
        } catch (InterruptedException e2) {
            return 0;
        }
    }

    public int peek(byte[] $r1) throws  {
        if ($r1 != null) {
            return peek($r1, 0, $r1.length);
        }
        return 0;
    }

    public int peek(byte[] $r1, int $i0, int $i1) throws  {
        if ($r1 == null) {
            return 0;
        }
        if ($r1.length < $i0 + $i1) {
            throw new RuntimeException("array index out of bounds. offset + length extends beyond the length of the array.");
        }
        int $i3 = 0;
        try {
            this.readsem.acquire();
            int $i4 = 0;
            while ($i4 < $i1 && $i4 != this.count) {
                $r1[$i4 + $i0] = this.buff[(this.head + $i4) % this.size];
                $i3++;
                $i4++;
            }
            this.readsem.release();
            return $i3;
        } catch (InterruptedException e) {
            return 0;
        }
    }

    public byte[] readBytes() throws  {
        byte[] $r1 = new byte[this.count];
        try {
            read($r1);
            return $r1;
        } catch (Exception e) {
            return $r1;
        }
    }
}
