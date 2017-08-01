package com.abaltatech.weblink.core;

import java.util.Arrays;

public class DataBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataBuffer.class.desiredAssertionStatus());
    private int m_capacity = 0;
    private byte[] m_data = null;
    private int m_endPos = 0;
    private int m_initialPos = 0;
    private int m_startPos = 0;

    public DataBuffer(int $i0) throws  {
        resize($i0);
    }

    public DataBuffer(byte[] $r1, int $i0, int $i1) throws  {
        if ($assertionsDisabled || ($r1 != null && $i0 + $i1 <= $r1.length)) {
            this.m_data = $r1;
            this.m_initialPos = $i0;
            this.m_startPos = $i0;
            this.m_endPos = $i0 + $i1;
            this.m_capacity = $i1;
            return;
        }
        throw new AssertionError();
    }

    public DataBuffer copy() throws  {
        int $i2 = getSize();
        if ($i2 > 0) {
            return new DataBuffer(Arrays.copyOfRange(this.m_data, this.m_startPos, this.m_endPos), 0, $i2);
        }
        return new DataBuffer();
    }

    public int getSize() throws  {
        return this.m_endPos - this.m_startPos;
    }

    public boolean isEmpty() throws  {
        return this.m_endPos > this.m_startPos && this.m_data != null;
    }

    public int getPos() throws  {
        return this.m_startPos;
    }

    public byte[] getData() throws  {
        return this.m_data;
    }

    public void reset() throws  {
        int $i0 = this.m_initialPos;
        this.m_endPos = $i0;
        this.m_startPos = $i0;
    }

    public void normalizeBuffer() throws  {
        if (this.m_data != null && this.m_startPos > this.m_initialPos) {
            int $i1;
            if (this.m_startPos == this.m_endPos) {
                $i1 = this.m_initialPos;
                this.m_endPos = $i1;
                this.m_startPos = $i1;
                return;
            }
            $i1 = getSize();
            System.arraycopy(this.m_data, this.m_startPos, this.m_data, this.m_initialPos, $i1);
            this.m_startPos = this.m_initialPos;
            this.m_endPos = this.m_startPos + $i1;
        }
    }

    public boolean resize(int $i0) throws  {
        if ($i0 <= getSize()) {
            this.m_endPos = this.m_startPos + $i0;
            return true;
        } else if ($i0 <= this.m_capacity) {
            normalizeBuffer();
            this.m_endPos = this.m_startPos + $i0;
            return true;
        } else {
            if (this.m_data == null) {
                this.m_data = new byte[$i0];
            } else {
                try {
                    this.m_data = Arrays.copyOfRange(this.m_data, this.m_startPos, this.m_startPos + $i0);
                } catch (OutOfMemoryError e) {
                    return false;
                }
            }
            this.m_initialPos = 0;
            this.m_startPos = 0;
            this.m_endPos = $i0;
            this.m_capacity = $i0;
            return true;
        }
    }

    public int discardBytesFromStart(int $i1) throws  {
        int $i0 = getSize();
        if ($i1 > $i0) {
            $i1 = $i0;
        }
        this.m_startPos += $i1;
        return $i1;
    }

    public int getBytes(byte[] $r1, int $i0, int $i1) throws  {
        $i0 = peekBytes($r1, $i0, $i1);
        discardBytesFromStart($i0);
        return $i0;
    }

    public int peekBytes(byte[] $r1, int $i0, int $i2) throws  {
        if ($assertionsDisabled || ($r1 != null && $i0 + $i2 <= $r1.length)) {
            int $i1 = getSize();
            if ($i2 > $i1) {
                $i2 = $i1;
            }
            if ($i2 <= 0) {
                return $i2;
            }
            System.arraycopy(this.m_data, this.m_startPos, $r1, $i0, $i2);
            return $i2;
        }
        throw new AssertionError();
    }

    public boolean addBytes(byte[] $r1, int $i0, int $i1) throws  {
        if ($assertionsDisabled || ($r1 != null && $i0 + $i1 <= $r1.length)) {
            boolean $z0 = resize(getSize() + $i1);
            if (!$z0) {
                return $z0;
            }
            System.arraycopy($r1, $i0, this.m_data, this.m_endPos - $i1, $i1);
            return $z0;
        }
        throw new AssertionError();
    }

    public byte getByte(int $i0) throws  {
        if ($assertionsDisabled || ($i0 >= 0 && $i0 < getSize())) {
            return ($i0 < 0 || $i0 >= getSize()) ? (byte) 0 : this.m_data[this.m_startPos + $i0];
        } else {
            throw new AssertionError();
        }
    }

    public boolean putByte(int $i0, byte $b1) throws  {
        boolean $z0 = $i0 >= 0 && $i0 < getSize();
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        } else if (!$z0) {
            return $z0;
        } else {
            this.m_data[this.m_startPos + $i0] = $b1;
            return $z0;
        }
    }

    public short getShort(int $i0) throws  {
        if (!$assertionsDisabled && ($i0 < 0 || $i0 >= getSize() - 1)) {
            throw new AssertionError();
        } else if ($i0 < 0) {
            return (short) 0;
        } else {
            if ($i0 >= getSize() - 1) {
                return (short) 0;
            }
            $i0 += this.m_startPos;
            return (short) ((this.m_data[$i0 + 1] << 8) | (this.m_data[$i0] & (short) 255));
        }
    }

    public boolean putShort(int $i1, short $s0) throws  {
        boolean $z0 = $i1 >= 0 && $i1 < getSize() - 1;
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        } else if (!$z0) {
            return $z0;
        } else {
            int $i2 = $i1 + this.m_startPos;
            $i1 = $i2 + 1;
            this.m_data[$i2] = (byte) (($s0 >> (short) 0) & (short) 255);
            this.m_data[$i1] = (byte) (($s0 >> (short) 8) & (short) 255);
            return $z0;
        }
    }

    public int getInt(int $i0) throws  {
        if (!$assertionsDisabled && ($i0 < 0 || $i0 >= getSize() - 3)) {
            throw new AssertionError();
        } else if ($i0 < 0) {
            return 0;
        } else {
            if ($i0 >= getSize() - 3) {
                return 0;
            }
            $i0 += this.m_startPos;
            int $i1 = $i0 + 1;
            $i0 = $i1 + 1;
            return ((((this.m_data[$i0] & (short) 255) << 0) | ((this.m_data[$i1] & (short) 255) << 8)) | ((this.m_data[$i0] & (short) 255) << 16)) | ((this.m_data[$i0 + 1] & (short) 255) << 24);
        }
    }

    public boolean putInt(int $i2, int $i0) throws  {
        boolean $z0 = $i2 >= 0 && $i2 < getSize() - 3;
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        } else if (!$z0) {
            return $z0;
        } else {
            int $i1 = $i2 + this.m_startPos;
            $i2 = $i1 + 1;
            this.m_data[$i1] = (byte) (($i0 >> 0) & 255);
            $i1 = $i2 + 1;
            this.m_data[$i2] = (byte) (($i0 >> 8) & 255);
            $i2 = $i1 + 1;
            this.m_data[$i1] = (byte) (($i0 >> 16) & 255);
            this.m_data[$i2] = (byte) (($i0 >> 24) & 255);
            return $z0;
        }
    }

    public long getLong(int $i0) throws  {
        if (!$assertionsDisabled && ($i0 < 0 || $i0 >= getSize() - 3)) {
            throw new AssertionError();
        } else if ($i0 < 0) {
            return 0;
        } else {
            if ($i0 >= getSize() - 3) {
                return 0;
            }
            int $i2 = $i0 + this.m_startPos;
            $i0 = $i2 + 1;
            $i2 = $i0 + 1;
            $i0 = $i2 + 1;
            $i2 = $i0 + 1;
            $i0 = $i2 + 1;
            $i2 = $i0 + 1;
            return ((((((((((long) this.m_data[$i2]) & 255) << null) | ((((long) this.m_data[$i0]) & 255) << 8)) | ((((long) this.m_data[$i2]) & 255) << 16)) | ((((long) this.m_data[$i0]) & 255) << 24)) | ((((long) this.m_data[$i2]) & 255) << 32)) | ((((long) this.m_data[$i0]) & 255) << 40)) | ((((long) this.m_data[$i2]) & 255) << 48)) | ((((long) this.m_data[$i2 + 1]) & 255) << 56);
        }
    }

    public boolean putLong(int $i2, long $l0) throws  {
        boolean $z0;
        if ($i2 < 0 || $i2 >= getSize() - 3) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        } else if (!$z0) {
            return $z0;
        } else {
            int $i1 = $i2 + this.m_startPos;
            $i2 = $i1 + 1;
            this.m_data[$i1] = (byte) ((int) (($l0 >> null) & 255));
            $i1 = $i2 + 1;
            this.m_data[$i2] = (byte) ((int) (($l0 >> 8) & 255));
            $i2 = $i1 + 1;
            this.m_data[$i1] = (byte) ((int) (($l0 >> 16) & 255));
            $i1 = $i2 + 1;
            this.m_data[$i2] = (byte) ((int) (($l0 >> 24) & 255));
            $i2 = $i1 + 1;
            this.m_data[$i1] = (byte) ((int) (($l0 >> 32) & 255));
            $i1 = $i2 + 1;
            this.m_data[$i2] = (byte) ((int) (($l0 >> 40) & 255));
            $i2 = $i1 + 1;
            this.m_data[$i1] = (byte) ((int) (($l0 >> 48) & 255));
            this.m_data[$i2] = (byte) ((int) (($l0 >> 56) & 255));
            return $z0;
        }
    }

    public float getFloat(int $i0) throws  {
        if (!$assertionsDisabled && ($i0 < 0 || $i0 >= getSize() - 3)) {
            throw new AssertionError();
        } else if ($i0 < 0) {
            return 0.0f;
        } else {
            if ($i0 >= getSize() - 3) {
                return 0.0f;
            }
            $i0 += this.m_startPos;
            int $i1 = $i0 + 1;
            $i0 = $i1 + 1;
            return Float.intBitsToFloat(((((this.m_data[$i0] & (short) 255) << 0) | ((this.m_data[$i1] & (short) 255) << 8)) | ((this.m_data[$i0] & (short) 255) << 16)) | ((this.m_data[$i0 + 1] & (short) 255) << 24));
        }
    }

    public boolean putFloat(int $i0, float $f0) throws  {
        boolean $z0 = $i0 >= 0 && $i0 < getSize() - 3;
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        } else if (!$z0) {
            return $z0;
        } else {
            int $i1 = Float.floatToIntBits($f0);
            int $i2 = $i0 + this.m_startPos;
            $i0 = $i2 + 1;
            this.m_data[$i2] = (byte) (($i1 >> 0) & 255);
            $i2 = $i0 + 1;
            this.m_data[$i0] = (byte) (($i1 >> 8) & 255);
            $i0 = $i2 + 1;
            this.m_data[$i2] = (byte) (($i1 >> 16) & 255);
            this.m_data[$i0] = (byte) (($i1 >> 24) & 255);
            return $z0;
        }
    }
}
