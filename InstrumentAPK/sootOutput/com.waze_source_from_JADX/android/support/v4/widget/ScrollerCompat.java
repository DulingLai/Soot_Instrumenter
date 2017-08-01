package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public final class ScrollerCompat {
    static final int CHASE_FRAME_TIME = 16;
    private static final String TAG = "ScrollerCompat";
    ScrollerCompatImpl mImpl;
    Object mScroller;

    interface ScrollerCompatImpl {
        void abortAnimation(Object obj) throws ;

        boolean computeScrollOffset(Object obj) throws ;

        Object createScroller(Context context, Interpolator interpolator) throws ;

        void fling(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws ;

        void fling(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) throws ;

        float getCurrVelocity(Object obj) throws ;

        int getCurrX(Object obj) throws ;

        int getCurrY(Object obj) throws ;

        int getFinalX(Object obj) throws ;

        int getFinalY(Object obj) throws ;

        boolean isFinished(Object obj) throws ;

        boolean isOverScrolled(Object obj) throws ;

        void notifyHorizontalEdgeReached(Object obj, int i, int i2, int i3) throws ;

        void notifyVerticalEdgeReached(Object obj, int i, int i2, int i3) throws ;

        boolean springBack(Object obj, int i, int i2, int i3, int i4, int i5, int i6) throws ;

        void startScroll(Object obj, int i, int i2, int i3, int i4) throws ;

        void startScroll(Object obj, int i, int i2, int i3, int i4, int i5) throws ;
    }

    static class ScrollerCompatImplBase implements ScrollerCompatImpl {
        public float getCurrVelocity(Object scroller) throws  {
            return 0.0f;
        }

        public boolean isOverScrolled(Object scroller) throws  {
            return false;
        }

        public boolean springBack(Object scroller, int startX, int startY, int minX, int maxX, int minY, int maxY) throws  {
            return false;
        }

        ScrollerCompatImplBase() throws  {
        }

        public Object createScroller(Context $r1, Interpolator $r2) throws  {
            return $r2 != null ? new Scroller($r1, $r2) : new Scroller($r1);
        }

        public boolean isFinished(Object $r1) throws  {
            return ((Scroller) $r1).isFinished();
        }

        public int getCurrX(Object $r1) throws  {
            return ((Scroller) $r1).getCurrX();
        }

        public int getCurrY(Object $r1) throws  {
            return ((Scroller) $r1).getCurrY();
        }

        public boolean computeScrollOffset(Object $r1) throws  {
            return ((Scroller) $r1).computeScrollOffset();
        }

        public void startScroll(Object $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            ((Scroller) $r1).startScroll($i0, $i1, $i2, $i3);
        }

        public void startScroll(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
            ((Scroller) $r1).startScroll($i0, $i1, $i2, $i3, $i4);
        }

        public void fling(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7) throws  {
            ((Scroller) $r1).fling($i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7);
        }

        public void fling(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7, int overX, int overY) throws  {
            ((Scroller) $r1).fling($i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7);
        }

        public void abortAnimation(Object $r1) throws  {
            ((Scroller) $r1).abortAnimation();
        }

        public void notifyHorizontalEdgeReached(Object scroller, int startX, int finalX, int overX) throws  {
        }

        public void notifyVerticalEdgeReached(Object scroller, int startY, int finalY, int overY) throws  {
        }

        public int getFinalX(Object $r1) throws  {
            return ((Scroller) $r1).getFinalX();
        }

        public int getFinalY(Object $r1) throws  {
            return ((Scroller) $r1).getFinalY();
        }
    }

    static class ScrollerCompatImplGingerbread implements ScrollerCompatImpl {
        public float getCurrVelocity(Object scroller) throws  {
            return 0.0f;
        }

        ScrollerCompatImplGingerbread() throws  {
        }

        public Object createScroller(Context $r1, Interpolator $r2) throws  {
            return ScrollerCompatGingerbread.createScroller($r1, $r2);
        }

        public boolean isFinished(Object $r1) throws  {
            return ScrollerCompatGingerbread.isFinished($r1);
        }

        public int getCurrX(Object $r1) throws  {
            return ScrollerCompatGingerbread.getCurrX($r1);
        }

        public int getCurrY(Object $r1) throws  {
            return ScrollerCompatGingerbread.getCurrY($r1);
        }

        public boolean computeScrollOffset(Object $r1) throws  {
            return ScrollerCompatGingerbread.computeScrollOffset($r1);
        }

        public void startScroll(Object $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            ScrollerCompatGingerbread.startScroll($r1, $i0, $i1, $i2, $i3);
        }

        public void startScroll(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
            ScrollerCompatGingerbread.startScroll($r1, $i0, $i1, $i2, $i3, $i4);
        }

        public void fling(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7) throws  {
            ScrollerCompatGingerbread.fling($r1, $i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7);
        }

        public void fling(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7, int $i8, int $i9) throws  {
            ScrollerCompatGingerbread.fling($r1, $i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7, $i8, $i9);
        }

        public void abortAnimation(Object $r1) throws  {
            ScrollerCompatGingerbread.abortAnimation($r1);
        }

        public void notifyHorizontalEdgeReached(Object $r1, int $i0, int $i1, int $i2) throws  {
            ScrollerCompatGingerbread.notifyHorizontalEdgeReached($r1, $i0, $i1, $i2);
        }

        public void notifyVerticalEdgeReached(Object $r1, int $i0, int $i1, int $i2) throws  {
            ScrollerCompatGingerbread.notifyVerticalEdgeReached($r1, $i0, $i1, $i2);
        }

        public boolean isOverScrolled(Object $r1) throws  {
            return ScrollerCompatGingerbread.isOverScrolled($r1);
        }

        public int getFinalX(Object $r1) throws  {
            return ScrollerCompatGingerbread.getFinalX($r1);
        }

        public int getFinalY(Object $r1) throws  {
            return ScrollerCompatGingerbread.getFinalY($r1);
        }

        public boolean springBack(Object $r1, int $i0, int $i1, int $i2, int $i3, int $i4, int $i5) throws  {
            return ScrollerCompatGingerbread.springBack($r1, $i0, $i1, $i2, $i3, $i4, $i5);
        }
    }

    static class ScrollerCompatImplIcs extends ScrollerCompatImplGingerbread {
        ScrollerCompatImplIcs() throws  {
        }

        public float getCurrVelocity(Object $r1) throws  {
            return ScrollerCompatIcs.getCurrVelocity($r1);
        }
    }

    public static ScrollerCompat create(Context $r0) throws  {
        return create($r0, null);
    }

    public static ScrollerCompat create(Context $r0, Interpolator $r1) throws  {
        return new ScrollerCompat(VERSION.SDK_INT, $r0, $r1);
    }

    private ScrollerCompat(int $i0, Context $r1, Interpolator $r2) throws  {
        if ($i0 >= 14) {
            this.mImpl = new ScrollerCompatImplIcs();
        } else if ($i0 >= 9) {
            this.mImpl = new ScrollerCompatImplGingerbread();
        } else {
            this.mImpl = new ScrollerCompatImplBase();
        }
        this.mScroller = this.mImpl.createScroller($r1, $r2);
    }

    public boolean isFinished() throws  {
        return this.mImpl.isFinished(this.mScroller);
    }

    public int getCurrX() throws  {
        return this.mImpl.getCurrX(this.mScroller);
    }

    public int getCurrY() throws  {
        return this.mImpl.getCurrY(this.mScroller);
    }

    public int getFinalX() throws  {
        return this.mImpl.getFinalX(this.mScroller);
    }

    public int getFinalY() throws  {
        return this.mImpl.getFinalY(this.mScroller);
    }

    public float getCurrVelocity() throws  {
        return this.mImpl.getCurrVelocity(this.mScroller);
    }

    public boolean computeScrollOffset() throws  {
        return this.mImpl.computeScrollOffset(this.mScroller);
    }

    public void startScroll(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mImpl.startScroll(this.mScroller, $i0, $i1, $i2, $i3);
    }

    public void startScroll(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        this.mImpl.startScroll(this.mScroller, $i0, $i1, $i2, $i3, $i4);
    }

    public void fling(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7) throws  {
        this.mImpl.fling(this.mScroller, $i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7);
    }

    public void fling(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7, int $i8, int $i9) throws  {
        this.mImpl.fling(this.mScroller, $i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7, $i8, $i9);
    }

    public boolean springBack(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5) throws  {
        return this.mImpl.springBack(this.mScroller, $i0, $i1, $i2, $i3, $i4, $i5);
    }

    public void abortAnimation() throws  {
        this.mImpl.abortAnimation(this.mScroller);
    }

    public void notifyHorizontalEdgeReached(int $i0, int $i1, int $i2) throws  {
        this.mImpl.notifyHorizontalEdgeReached(this.mScroller, $i0, $i1, $i2);
    }

    public void notifyVerticalEdgeReached(int $i0, int $i1, int $i2) throws  {
        this.mImpl.notifyVerticalEdgeReached(this.mScroller, $i0, $i1, $i2);
    }

    public boolean isOverScrolled() throws  {
        return this.mImpl.isOverScrolled(this.mScroller);
    }
}
