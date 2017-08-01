package android.support.v7.widget;

class RtlSpacingHelper {
    public static final int UNDEFINED = Integer.MIN_VALUE;
    private int mEnd = Integer.MIN_VALUE;
    private int mExplicitLeft = 0;
    private int mExplicitRight = 0;
    private boolean mIsRelative = false;
    private boolean mIsRtl = false;
    private int mLeft = 0;
    private int mRight = 0;
    private int mStart = Integer.MIN_VALUE;

    RtlSpacingHelper() throws  {
    }

    public int getLeft() throws  {
        return this.mLeft;
    }

    public int getRight() throws  {
        return this.mRight;
    }

    public int getStart() throws  {
        return this.mIsRtl ? this.mRight : this.mLeft;
    }

    public int getEnd() throws  {
        return this.mIsRtl ? this.mLeft : this.mRight;
    }

    public void setRelative(int $i0, int $i1) throws  {
        this.mStart = $i0;
        this.mEnd = $i1;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if ($i1 != Integer.MIN_VALUE) {
                this.mLeft = $i1;
            }
            if ($i0 != Integer.MIN_VALUE) {
                this.mRight = $i0;
                return;
            }
            return;
        }
        if ($i0 != Integer.MIN_VALUE) {
            this.mLeft = $i0;
        }
        if ($i1 != Integer.MIN_VALUE) {
            this.mRight = $i1;
        }
    }

    public void setAbsolute(int $i0, int $i1) throws  {
        this.mIsRelative = false;
        if ($i0 != Integer.MIN_VALUE) {
            this.mExplicitLeft = $i0;
            this.mLeft = $i0;
        }
        if ($i1 != Integer.MIN_VALUE) {
            this.mExplicitRight = $i1;
            this.mRight = $i1;
        }
    }

    public void setDirection(boolean $z0) throws  {
        if ($z0 != this.mIsRtl) {
            this.mIsRtl = $z0;
            if (!this.mIsRelative) {
                this.mLeft = this.mExplicitLeft;
                this.mRight = this.mExplicitRight;
            } else if ($z0) {
                this.mLeft = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitLeft;
                this.mRight = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitRight;
            } else {
                this.mLeft = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitLeft;
                this.mRight = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitRight;
            }
        }
    }
}
