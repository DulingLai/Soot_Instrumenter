package com.abaltatech.weblinkserver;

public class WLRect {
    int m_x1;
    int m_x2;
    int m_y1;
    int m_y2;

    public WLRect() throws  {
        this.m_y1 = 0;
        this.m_x1 = 0;
        this.m_y2 = -1;
        this.m_x2 = -1;
    }

    public WLRect(int $i0, int $i1, int $i2, int $i3) throws  {
        this.m_x1 = $i0;
        this.m_x2 = ($i0 + $i2) - 1;
        this.m_y1 = $i1;
        this.m_y2 = ($i1 + $i3) - 1;
    }

    public boolean isNull() throws  {
        return this.m_x2 == this.m_x1 + -1 && this.m_y2 == this.m_y1 - 1;
    }

    public boolean isEmpty() throws  {
        return this.m_x1 > this.m_x2 || this.m_y1 > this.m_y2;
    }

    public boolean isValid() throws  {
        return this.m_x1 <= this.m_x2 && this.m_y1 <= this.m_y2;
    }

    public boolean contains(int $i0, int $i1) throws  {
        return $i0 >= this.m_x1 && $i0 <= this.m_x2 && $i1 >= this.m_y1 && $i1 <= this.m_y2;
    }

    public int left() throws  {
        return this.m_x1;
    }

    public int top() throws  {
        return this.m_y1;
    }

    public int right() throws  {
        return this.m_x2;
    }

    public int bottom() throws  {
        return this.m_y2;
    }

    public int width() throws  {
        return (this.m_x2 - this.m_x1) + 1;
    }

    public int height() throws  {
        return (this.m_y2 - this.m_y1) + 1;
    }

    public WLRect intersected(WLRect $r1) throws  {
        if (isNull() || $r1.isNull()) {
            return new WLRect();
        }
        int $i0 = this.m_x1;
        int $i1 = this.m_x1;
        if ((this.m_x2 - this.m_x1) + 1 < 0) {
            $i0 = this.m_x2;
        } else {
            $i1 = this.m_x2;
        }
        int $i3 = $r1.m_x1;
        int $i2 = $r1.m_x1;
        if (($r1.m_x2 - $r1.m_x1) + 1 < 0) {
            $i3 = $r1.m_x2;
        } else {
            $i2 = $r1.m_x2;
        }
        if ($i0 > $i2 || $i3 > $i1) {
            return new WLRect();
        }
        int $i5 = this.m_y1;
        int $i4 = this.m_y1;
        if ((this.m_y2 - this.m_y1) + 1 < 0) {
            $i5 = this.m_y2;
        } else {
            $i4 = this.m_y2;
        }
        int $i7 = $r1.m_y1;
        int $i6 = $r1.m_y1;
        if (($r1.m_y2 - $r1.m_y1) + 1 < 0) {
            $i7 = $r1.m_y2;
        } else {
            $i6 = $r1.m_y2;
        }
        if ($i5 > $i6 || $i7 > $i4) {
            return new WLRect();
        }
        this = new WLRect();
        this.m_x1 = Math.max($i0, $i3);
        this.m_x2 = Math.min($i1, $i2);
        this.m_y1 = Math.max($i5, $i7);
        this.m_y2 = Math.min($i4, $i6);
        return this;
    }
}
