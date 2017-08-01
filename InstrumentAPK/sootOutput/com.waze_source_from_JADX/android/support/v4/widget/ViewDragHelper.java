package android.support.v4.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import com.waze.map.CanvasFont;
import java.util.Arrays;

public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new C01631();
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private ScrollerCompat mScroller;
    private final Runnable mSetIdleRunnable = new C01642();
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    public static abstract class Callback {
        public int clampViewPositionHorizontal(View child, int left, int dx) throws  {
            return 0;
        }

        public int clampViewPositionVertical(View child, int top, int dy) throws  {
            return 0;
        }

        public int getViewHorizontalDragRange(View child) throws  {
            return 0;
        }

        public int getViewVerticalDragRange(View child) throws  {
            return 0;
        }

        public boolean onEdgeLock(int edgeFlags) throws  {
            return false;
        }

        public abstract boolean tryCaptureView(View view, int i) throws ;

        public void onViewDragStateChanged(int state) throws  {
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) throws  {
        }

        public void onViewCaptured(View capturedChild, int activePointerId) throws  {
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) throws  {
        }

        public void onEdgeTouched(int edgeFlags, int pointerId) throws  {
        }

        public void onEdgeDragStarted(int edgeFlags, int pointerId) throws  {
        }

        public int getOrderedChildIndex(int $i0) throws  {
            return $i0;
        }
    }

    static class C01631 implements Interpolator {
        C01631() throws  {
        }

        public float getInterpolation(float $f0) throws  {
            $f0 -= 1.0f;
            return (((($f0 * $f0) * $f0) * $f0) * $f0) + 1.0f;
        }
    }

    class C01642 implements Runnable {
        C01642() throws  {
        }

        public void run() throws  {
            ViewDragHelper.this.setDragState(0);
        }
    }

    private void clearMotionHistory(int r1) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.widget.ViewDragHelper.clearMotionHistory(int):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.clearMotionHistory(int):void");
    }

    public boolean shouldInterceptTouchEvent(android.view.MotionEvent r25) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:72:0x01db in {2, 5, 8, 12, 17, 19, 23, 24, 43, 48, 54, 59, 60, 62, 68, 70, 73, 76} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r24 = this;
        r0 = r25;
        r3 = android.support.v4.view.MotionEventCompat.getActionMasked(r0);
        r0 = r25;
        r4 = android.support.v4.view.MotionEventCompat.getActionIndex(r0);
        if (r3 != 0) goto L_0x0013;
    L_0x000e:
        r0 = r24;
        r0.cancel();
    L_0x0013:
        r0 = r24;
        r5 = r0.mVelocityTracker;
        if (r5 != 0) goto L_0x0021;
    L_0x0019:
        r5 = android.view.VelocityTracker.obtain();
        r0 = r24;
        r0.mVelocityTracker = r5;
    L_0x0021:
        r0 = r24;
        r5 = r0.mVelocityTracker;
        r0 = r25;
        r5.addMovement(r0);
        switch(r3) {
            case 0: goto L_0x0037;
            case 1: goto L_0x01df;
            case 2: goto L_0x00df;
            case 3: goto L_0x01df;
            case 4: goto L_0x002e;
            case 5: goto L_0x008c;
            case 6: goto L_0x01cb;
            default: goto L_0x002d;
        };
    L_0x002d:
        goto L_0x002e;
    L_0x002e:
        r0 = r24;
        r3 = r0.mDragState;
        r6 = 1;
        if (r3 != r6) goto L_0x01e5;
    L_0x0035:
        r6 = 1;
        return r6;
    L_0x0037:
        r0 = r25;
        r7 = r0.getX();
        r0 = r25;
        r8 = r0.getY();
        r6 = 0;
        r0 = r25;
        r3 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r6);
        r0 = r24;
        r0.saveInitialMotion(r7, r8, r3);
        r4 = (int) r7;
        r9 = (int) r8;
        r0 = r24;
        r10 = r0.findTopChildUnder(r4, r9);
        r0 = r24;
        r11 = r0.mCapturedView;
        if (r10 != r11) goto L_0x0069;
    L_0x005d:
        r0 = r24;
        r4 = r0.mDragState;
        r6 = 2;
        if (r4 != r6) goto L_0x0069;
    L_0x0064:
        r0 = r24;
        r0.tryCaptureViewForDrag(r10, r3);
    L_0x0069:
        r0 = r24;
        r12 = r0.mInitialEdgesTouched;
        r4 = r12[r3];
        r0 = r24;
        r9 = r0.mTrackingEdges;
        goto L_0x0077;
    L_0x0074:
        goto L_0x002e;
    L_0x0077:
        r9 = r9 & r4;
        if (r9 == 0) goto L_0x002e;
    L_0x007a:
        r0 = r24;
        r13 = r0.mCallback;
        r0 = r24;
        r9 = r0.mTrackingEdges;
        goto L_0x0086;
    L_0x0083:
        goto L_0x002e;
    L_0x0086:
        r4 = r9 & r4;
        r13.onEdgeTouched(r4, r3);
        goto L_0x002e;
    L_0x008c:
        r0 = r25;
        r3 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r4);
        r0 = r25;
        r7 = android.support.v4.view.MotionEventCompat.getX(r0, r4);
        r0 = r25;
        r8 = android.support.v4.view.MotionEventCompat.getY(r0, r4);
        r0 = r24;
        r0.saveInitialMotion(r7, r8, r3);
        r0 = r24;
        r4 = r0.mDragState;
        if (r4 != 0) goto L_0x00c4;
    L_0x00a9:
        r0 = r24;
        r12 = r0.mInitialEdgesTouched;
        r4 = r12[r3];
        r0 = r24;
        r9 = r0.mTrackingEdges;
        r9 = r9 & r4;
        if (r9 == 0) goto L_0x002e;
    L_0x00b6:
        r0 = r24;
        r13 = r0.mCallback;
        r0 = r24;
        r9 = r0.mTrackingEdges;
        r4 = r9 & r4;
        r13.onEdgeTouched(r4, r3);
        goto L_0x0074;
    L_0x00c4:
        r0 = r24;
        r4 = r0.mDragState;
        r6 = 2;
        if (r4 != r6) goto L_0x002e;
    L_0x00cb:
        r4 = (int) r7;
        r9 = (int) r8;
        r0 = r24;
        r10 = r0.findTopChildUnder(r4, r9);
        r0 = r24;
        r11 = r0.mCapturedView;
        if (r10 != r11) goto L_0x002e;
    L_0x00d9:
        r0 = r24;
        r0.tryCaptureViewForDrag(r10, r3);
        goto L_0x0083;
    L_0x00df:
        r0 = r24;
        r14 = r0.mInitialMotionX;
        if (r14 == 0) goto L_0x002e;
    L_0x00e5:
        r0 = r24;
        r14 = r0.mInitialMotionY;
        if (r14 == 0) goto L_0x002e;
    L_0x00eb:
        r0 = r25;
        r3 = android.support.v4.view.MotionEventCompat.getPointerCount(r0);
        r4 = 0;
    L_0x00f2:
        if (r4 >= r3) goto L_0x01a2;
    L_0x00f4:
        r0 = r25;
        r9 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r4);
        r0 = r24;
        r15 = r0.isValidPointerForActionMove(r9);
        if (r15 != 0) goto L_0x0105;
    L_0x0102:
        r4 = r4 + 1;
        goto L_0x00f2;
    L_0x0105:
        r0 = r25;
        r7 = android.support.v4.view.MotionEventCompat.getX(r0, r4);
        r0 = r25;
        r8 = android.support.v4.view.MotionEventCompat.getY(r0, r4);
        r0 = r24;
        r14 = r0.mInitialMotionX;
        r16 = r14[r9];
        r16 = r7 - r16;
        r0 = r24;
        r14 = r0.mInitialMotionY;
        r17 = r14[r9];
        r17 = r8 - r17;
        r0 = (int) r7;
        r18 = r0;
        r0 = (int) r8;
        r19 = r0;
        r0 = r24;
        r1 = r18;
        r2 = r19;
        r10 = r0.findTopChildUnder(r1, r2);
        if (r10 == 0) goto L_0x01aa;
    L_0x0133:
        r0 = r24;
        r1 = r16;
        r2 = r17;
        r15 = r0.checkTouchSlop(r10, r1, r2);
        if (r15 == 0) goto L_0x01aa;
    L_0x013f:
        r15 = 1;
    L_0x0140:
        if (r15 == 0) goto L_0x01ac;
    L_0x0142:
        r18 = r10.getLeft();
        r0 = r16;
        r0 = (int) r0;
        r19 = r0;
        r19 = r18 + r19;
        r0 = r24;
        r13 = r0.mCallback;
        r0 = r16;
        r0 = (int) r0;
        r20 = r0;
        r0 = r19;
        r1 = r20;
        r19 = r13.clampViewPositionHorizontal(r10, r0, r1);
        r20 = r10.getTop();
        r0 = r17;
        r0 = (int) r0;
        r21 = r0;
        r21 = r20 + r21;
        r0 = r24;
        r13 = r0.mCallback;
        r0 = r17;
        r0 = (int) r0;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r21 = r13.clampViewPositionVertical(r10, r0, r1);
        r0 = r24;
        r13 = r0.mCallback;
        r22 = r13.getViewHorizontalDragRange(r10);
        r0 = r24;
        r13 = r0.mCallback;
        r23 = r13.getViewVerticalDragRange(r10);
        if (r22 == 0) goto L_0x0194;
    L_0x018c:
        if (r22 <= 0) goto L_0x01ac;
    L_0x018e:
        r0 = r19;
        r1 = r18;
        if (r0 != r1) goto L_0x01ac;
    L_0x0194:
        if (r23 == 0) goto L_0x01a2;
    L_0x0196:
        if (r23 <= 0) goto L_0x01ac;
    L_0x0198:
        r0 = r21;
        r1 = r20;
        if (r0 != r1) goto L_0x01ac;
    L_0x019e:
        goto L_0x01a2;
    L_0x019f:
        goto L_0x002e;
    L_0x01a2:
        r0 = r24;
        r1 = r25;
        r0.saveLastMotion(r1);
        goto L_0x019f;
    L_0x01aa:
        r15 = 0;
        goto L_0x0140;
    L_0x01ac:
        r0 = r24;
        r1 = r16;
        r2 = r17;
        r0.reportNewEdgeDrags(r1, r2, r9);
        r0 = r24;
        r0 = r0.mDragState;
        r18 = r0;
        r6 = 1;
        r0 = r18;
        if (r0 == r6) goto L_0x01a2;
    L_0x01c0:
        if (r15 == 0) goto L_0x0102;
    L_0x01c2:
        r0 = r24;
        r15 = r0.tryCaptureViewForDrag(r10, r9);
        if (r15 == 0) goto L_0x0102;
    L_0x01ca:
        goto L_0x01a2;
    L_0x01cb:
        r0 = r25;
        r3 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r4);
        goto L_0x01d5;
    L_0x01d2:
        goto L_0x002e;
    L_0x01d5:
        r0 = r24;
        r0.clearMotionHistory(r3);
        goto L_0x01d2;
        goto L_0x01df;
    L_0x01dc:
        goto L_0x002e;
    L_0x01df:
        r0 = r24;
        r0.cancel();
        goto L_0x01dc;
    L_0x01e5:
        r6 = 0;
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public static ViewDragHelper create(ViewGroup $r0, Callback $r1) throws  {
        return new ViewDragHelper($r0.getContext(), $r0, $r1);
    }

    public static ViewDragHelper create(ViewGroup $r0, float $f0, Callback $r1) throws  {
        ViewDragHelper $r2 = create($r0, $r1);
        $r2.mTouchSlop = (int) (((float) $r2.mTouchSlop) * (1.0f / $f0));
        return $r2;
    }

    private ViewDragHelper(Context $r1, ViewGroup $r2, Callback $r3) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if ($r3 == null) {
            throw new IllegalArgumentException("Callback may not be null");
        } else {
            this.mParentView = $r2;
            this.mCallback = $r3;
            ViewConfiguration $r6 = ViewConfiguration.get($r1);
            this.mEdgeSize = (int) ((20.0f * $r1.getResources().getDisplayMetrics().density) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            this.mTouchSlop = $r6.getScaledTouchSlop();
            this.mMaxVelocity = (float) $r6.getScaledMaximumFlingVelocity();
            this.mMinVelocity = (float) $r6.getScaledMinimumFlingVelocity();
            this.mScroller = ScrollerCompat.create($r1, sInterpolator);
        }
    }

    public void setMinVelocity(float $f0) throws  {
        this.mMinVelocity = $f0;
    }

    public float getMinVelocity() throws  {
        return this.mMinVelocity;
    }

    public int getViewDragState() throws  {
        return this.mDragState;
    }

    public void setEdgeTrackingEnabled(int $i0) throws  {
        this.mTrackingEdges = $i0;
    }

    public int getEdgeSize() throws  {
        return this.mEdgeSize;
    }

    public void captureChildView(View $r1, int $i0) throws  {
        if ($r1.getParent() != this.mParentView) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
        }
        this.mCapturedView = $r1;
        this.mActivePointerId = $i0;
        this.mCallback.onViewCaptured($r1, $i0);
        setDragState(1);
    }

    public View getCapturedView() throws  {
        return this.mCapturedView;
    }

    public int getActivePointerId() throws  {
        return this.mActivePointerId;
    }

    public int getTouchSlop() throws  {
        return this.mTouchSlop;
    }

    public void cancel() throws  {
        this.mActivePointerId = -1;
        clearMotionHistory();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void abort() throws  {
        cancel();
        if (this.mDragState == 2) {
            int $i0 = this.mScroller.getCurrX();
            int $i1 = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int $i2 = this.mScroller.getCurrX();
            int $i3 = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, $i2, $i3, $i2 - $i0, $i3 - $i1);
        }
        setDragState(0);
    }

    public boolean smoothSlideViewTo(View $r1, int $i0, int $i1) throws  {
        this.mCapturedView = $r1;
        this.mActivePointerId = -1;
        boolean $z0 = forceSettleCapturedViewAt($i0, $i1, 0, 0);
        if ($z0 || this.mDragState != 0 || this.mCapturedView == null) {
            return $z0;
        }
        this.mCapturedView = null;
        return $z0;
    }

    public boolean settleCapturedViewAt(int $i0, int $i1) throws  {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt($i0, $i1, (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    private boolean forceSettleCapturedViewAt(int $i0, int $i1, int $i2, int $i3) throws  {
        int $i4 = this.mCapturedView.getLeft();
        int $i5 = this.mCapturedView.getTop();
        $i0 -= $i4;
        $i1 -= $i5;
        if ($i0 == 0 && $i1 == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        this.mScroller.startScroll($i4, $i5, $i0, $i1, computeSettleDuration(this.mCapturedView, $i0, $i1, $i2, $i3));
        setDragState(2);
        return true;
    }

    private int computeSettleDuration(View $r1, int $i0, int $i1, int $i4, int $i5) throws  {
        float $f0;
        float $f1;
        $i4 = clampMag($i4, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        $i5 = clampMag($i5, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int $i6 = Math.abs($i0);
        int $i7 = Math.abs($i1);
        int $i8 = Math.abs($i4);
        int $i9 = Math.abs($i5);
        int $i3 = $i8 + $i9;
        int $i2 = $i6 + $i7;
        if ($i4 != 0) {
            $f0 = ((float) $i8) / ((float) $i3);
        } else {
            $f0 = ((float) $i6) / ((float) $i2);
        }
        if ($i5 != 0) {
            $f1 = ((float) $i9) / ((float) $i3);
        } else {
            $f1 = ((float) $i7) / ((float) $i2);
        }
        return (int) ((((float) computeAxisDuration($i0, $i4, this.mCallback.getViewHorizontalDragRange($r1))) * $f0) + (((float) computeAxisDuration($i1, $i5, this.mCallback.getViewVerticalDragRange($r1))) * $f1));
    }

    private int computeAxisDuration(int $i0, int $i3, int $i1) throws  {
        if ($i0 == 0) {
            return 0;
        }
        int $i4 = this.mParentView.getWidth();
        int $i2 = $i4 / 2;
        float $f0 = ((float) $i2) + (((float) $i2) * distanceInfluenceForSnapDuration(Math.min(1.0f, ((float) Math.abs($i0)) / ((float) $i4))));
        $i3 = Math.abs($i3);
        if ($i3 > 0) {
            $i0 = Math.round(1000.0f * Math.abs($f0 / ((float) $i3))) * 4;
        } else {
            $i0 = (int) (((((float) Math.abs($i0)) / ((float) $i1)) + 1.0f) * 256.0f);
        }
        return Math.min($i0, 600);
    }

    private int clampMag(int $i0, int $i1, int $i3) throws  {
        int $i2 = Math.abs($i0);
        if ($i2 < $i1) {
            return 0;
        }
        if ($i2 > $i3) {
            return $i0 <= 0 ? -$i3 : $i3;
        } else {
            return $i0;
        }
    }

    private float clampMag(float $f0, float $f1, float $f3) throws  {
        float $f2 = Math.abs($f0);
        if ($f2 < $f1) {
            return 0.0f;
        }
        if ($f2 > $f3) {
            return $f0 <= 0.0f ? -$f3 : $f3;
        } else {
            return $f0;
        }
    }

    private float distanceInfluenceForSnapDuration(float $f0) throws  {
        return (float) Math.sin((double) ((float) (((double) ($f0 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) * 0.4712389167638204d)));
    }

    public void flingCapturedView(int $i0, int $i1, int $i2, int $i3) throws  {
        if (this.mReleaseInProgress) {
            this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), $i0, $i2, $i1, $i3);
            setDragState(2);
            return;
        }
        throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
    }

    public boolean continueSettling(boolean $z0) throws  {
        if (this.mDragState == 2) {
            boolean $z1 = this.mScroller.computeScrollOffset();
            boolean $z2 = $z1;
            int $i3 = this.mScroller.getCurrX();
            int $i2 = this.mScroller.getCurrY();
            int $i0 = $i3 - this.mCapturedView.getLeft();
            int $i1 = $i2 - this.mCapturedView.getTop();
            if ($i0 != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, $i0);
            }
            if ($i1 != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, $i1);
            }
            if (!($i0 == 0 && $i1 == 0)) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, $i3, $i2, $i0, $i1);
            }
            if ($z1 && $i3 == this.mScroller.getFinalX() && $i2 == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                $z2 = false;
            }
            if (!$z2) {
                if ($z0) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    setDragState(0);
                }
            }
        }
        return this.mDragState == 2;
    }

    private void dispatchViewReleased(float $f0, float $f1) throws  {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, $f0, $f1);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    private void clearMotionHistory() throws  {
        if (this.mInitialMotionX != null) {
            Arrays.fill(this.mInitialMotionX, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
    }

    private void ensureMotionHistorySizeForId(int $i0) throws  {
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= $i0) {
            float[] $r4 = new float[($i0 + 1)];
            float[] $r5 = new float[($i0 + 1)];
            float[] $r6 = new float[($i0 + 1)];
            float[] $r7 = new float[($i0 + 1)];
            int[] $r3 = new int[($i0 + 1)];
            int[] $r1 = new int[($i0 + 1)];
            int[] $r2 = new int[($i0 + 1)];
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, $r4, 0, this.mInitialMotionX.length);
                System.arraycopy(this.mInitialMotionY, 0, $r5, 0, this.mInitialMotionY.length);
                System.arraycopy(this.mLastMotionX, 0, $r6, 0, this.mLastMotionX.length);
                System.arraycopy(this.mLastMotionY, 0, $r7, 0, this.mLastMotionY.length);
                System.arraycopy(this.mInitialEdgesTouched, 0, $r3, 0, this.mInitialEdgesTouched.length);
                System.arraycopy(this.mEdgeDragsInProgress, 0, $r1, 0, this.mEdgeDragsInProgress.length);
                System.arraycopy(this.mEdgeDragsLocked, 0, $r2, 0, this.mEdgeDragsLocked.length);
            }
            this.mInitialMotionX = $r4;
            this.mInitialMotionY = $r5;
            this.mLastMotionX = $r6;
            this.mLastMotionY = $r7;
            this.mInitialEdgesTouched = $r3;
            this.mEdgeDragsInProgress = $r1;
            this.mEdgeDragsLocked = $r2;
        }
    }

    private void saveInitialMotion(float $f0, float $f1, int $i0) throws  {
        ensureMotionHistorySizeForId($i0);
        float[] $r1 = this.mInitialMotionX;
        this.mLastMotionX[$i0] = $f0;
        $r1[$i0] = $f0;
        $r1 = this.mInitialMotionY;
        this.mLastMotionY[$i0] = $f1;
        $r1[$i0] = $f1;
        this.mInitialEdgesTouched[$i0] = getEdgesTouched((int) $f0, (int) $f1);
        this.mPointersDown |= 1 << $i0;
    }

    private void saveLastMotion(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getPointerCount($r1);
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            int $i2 = MotionEventCompat.getPointerId($r1, $i1);
            float $f0 = MotionEventCompat.getX($r1, $i1);
            float $f1 = MotionEventCompat.getY($r1, $i1);
            this.mLastMotionX[$i2] = $f0;
            this.mLastMotionY[$i2] = $f1;
        }
    }

    public boolean isPointerDown(int $i0) throws  {
        return (this.mPointersDown & (1 << $i0)) != 0;
    }

    void setDragState(int $i0) throws  {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != $i0) {
            this.mDragState = $i0;
            this.mCallback.onViewDragStateChanged($i0);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    boolean tryCaptureViewForDrag(View $r1, int $i0) throws  {
        if ($r1 == this.mCapturedView && this.mActivePointerId == $i0) {
            return true;
        }
        if ($r1 == null || !this.mCallback.tryCaptureView($r1, $i0)) {
            return false;
        }
        this.mActivePointerId = $i0;
        captureChildView($r1, $i0);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected boolean canScroll(android.view.View r19, boolean r20, int r21, int r22, int r23, int r24) throws  {
        /*
        r18 = this;
        r0 = r19;
        r7 = r0 instanceof android.view.ViewGroup;
        if (r7 == 0) goto L_0x006c;
    L_0x0006:
        r9 = r19;
        r9 = (android.view.ViewGroup) r9;
        r8 = r9;
        r0 = r19;
        r10 = r0.getScrollX();
        r0 = r19;
        r11 = r0.getScrollY();
        r12 = r8.getChildCount();
        r12 = r12 + -1;
    L_0x001d:
        if (r12 < 0) goto L_0x006c;
    L_0x001f:
        r13 = r8.getChildAt(r12);
        r14 = r23 + r10;
        r15 = r13.getLeft();
        if (r14 < r15) goto L_0x0069;
    L_0x002b:
        r14 = r23 + r10;
        r15 = r13.getRight();
        if (r14 >= r15) goto L_0x0069;
    L_0x0033:
        r14 = r24 + r11;
        r15 = r13.getTop();
        if (r14 < r15) goto L_0x0069;
    L_0x003b:
        r14 = r24 + r11;
        r15 = r13.getBottom();
        if (r14 >= r15) goto L_0x0069;
    L_0x0043:
        r14 = r23 + r10;
        r15 = r13.getLeft();
        r14 = r14 - r15;
        r15 = r24 + r11;
        r16 = r13.getTop();
        r0 = r16;
        r15 = r15 - r0;
        r17 = 1;
        r0 = r18;
        r1 = r13;
        r2 = r17;
        r3 = r21;
        r4 = r22;
        r5 = r14;
        r6 = r15;
        r7 = r0.canScroll(r1, r2, r3, r4, r5, r6);
        if (r7 == 0) goto L_0x0069;
    L_0x0066:
        r17 = 1;
        return r17;
    L_0x0069:
        r12 = r12 + -1;
        goto L_0x001d;
    L_0x006c:
        if (r20 == 0) goto L_0x008f;
    L_0x006e:
        r0 = r21;
        r0 = -r0;
        r21 = r0;
        r0 = r19;
        r1 = r21;
        r20 = android.support.v4.view.ViewCompat.canScrollHorizontally(r0, r1);
        if (r20 != 0) goto L_0x008c;
    L_0x007d:
        r0 = r22;
        r0 = -r0;
        r21 = r0;
        r0 = r19;
        r1 = r21;
        r20 = android.support.v4.view.ViewCompat.canScrollVertically(r0, r1);
        if (r20 == 0) goto L_0x008f;
    L_0x008c:
        r17 = 1;
        return r17;
    L_0x008f:
        r17 = 0;
        return r17;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.canScroll(android.view.View, boolean, int, int, int, int):boolean");
    }

    public void processTouchEvent(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionMasked($r1);
        int $i1 = MotionEventCompat.getActionIndex($r1);
        if ($i0 == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement($r1);
        float $f2;
        float $f3;
        View $r3;
        int $i2;
        switch ($i0) {
            case 0:
                $f2 = $r1.getX();
                $f3 = $r1.getY();
                $i0 = MotionEventCompat.getPointerId($r1, 0);
                $r3 = findTopChildUnder((int) $f2, (int) $f3);
                saveInitialMotion($f2, $f3, $i0);
                tryCaptureViewForDrag($r3, $i0);
                $i1 = this.mInitialEdgesTouched[$i0];
                if ((this.mTrackingEdges & $i1) != 0) {
                    this.mCallback.onEdgeTouched(this.mTrackingEdges & $i1, $i0);
                    return;
                }
                return;
            case 1:
                if (this.mDragState == 1) {
                    releaseViewForPointerUp();
                }
                cancel();
                return;
            case 2:
                if (this.mDragState == 1) {
                    if (isValidPointerForActionMove(this.mActivePointerId)) {
                        $i0 = MotionEventCompat.findPointerIndex($r1, this.mActivePointerId);
                        $f3 = MotionEventCompat.getX($r1, $i0);
                        $f2 = MotionEventCompat.getY($r1, $i0);
                        $i0 = (int) ($f3 - this.mLastMotionX[this.mActivePointerId]);
                        $i1 = (int) ($f2 - this.mLastMotionY[this.mActivePointerId]);
                        dragTo(this.mCapturedView.getLeft() + $i0, this.mCapturedView.getTop() + $i1, $i0, $i1);
                        saveLastMotion($r1);
                        return;
                    }
                    return;
                }
                $i0 = MotionEventCompat.getPointerCount($r1);
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    $i2 = MotionEventCompat.getPointerId($r1, $i1);
                    if (isValidPointerForActionMove($i2)) {
                        $f2 = MotionEventCompat.getX($r1, $i1);
                        $f3 = MotionEventCompat.getY($r1, $i1);
                        float $f0 = $f2 - this.mInitialMotionX[$i2];
                        float $f1 = $f3 - this.mInitialMotionY[$i2];
                        reportNewEdgeDrags($f0, $f1, $i2);
                        if (this.mDragState != 1) {
                            $r3 = findTopChildUnder((int) $f2, (int) $f3);
                            if (checkTouchSlop($r3, $f0, $f1) && tryCaptureViewForDrag($r3, $i2)) {
                            }
                        }
                        saveLastMotion($r1);
                        return;
                    }
                }
                saveLastMotion($r1);
                return;
            case 3:
                if (this.mDragState == 1) {
                    dispatchViewReleased(0.0f, 0.0f);
                }
                cancel();
                return;
            case 4:
                break;
            case 5:
                $i0 = MotionEventCompat.getPointerId($r1, $i1);
                $f2 = MotionEventCompat.getX($r1, $i1);
                $f3 = MotionEventCompat.getY($r1, $i1);
                saveInitialMotion($f2, $f3, $i0);
                if (this.mDragState == 0) {
                    tryCaptureViewForDrag(findTopChildUnder((int) $f2, (int) $f3), $i0);
                    $i1 = this.mInitialEdgesTouched[$i0];
                    if ((this.mTrackingEdges & $i1) != 0) {
                        this.mCallback.onEdgeTouched(this.mTrackingEdges & $i1, $i0);
                        return;
                    }
                    return;
                } else if (isCapturedViewUnder((int) $f2, (int) $f3)) {
                    tryCaptureViewForDrag(this.mCapturedView, $i0);
                    return;
                } else {
                    return;
                }
            case 6:
                $i0 = MotionEventCompat.getPointerId($r1, $i1);
                if (this.mDragState == 1 && $i0 == this.mActivePointerId) {
                    $i1 = -1;
                    $i2 = MotionEventCompat.getPointerCount($r1);
                    for (int $i3 = 0; $i3 < $i2; $i3++) {
                        int $i4 = MotionEventCompat.getPointerId($r1, $i3);
                        if ($i4 != this.mActivePointerId) {
                            $f2 = MotionEventCompat.getX($r1, $i3);
                            $r3 = findTopChildUnder((int) $f2, (int) MotionEventCompat.getY($r1, $i3));
                            View $r7 = this.mCapturedView;
                            if ($r3 == $r7) {
                                if (tryCaptureViewForDrag(this.mCapturedView, $i4)) {
                                    $i1 = this.mActivePointerId;
                                    if ($i1 == -1) {
                                        releaseViewForPointerUp();
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if ($i1 == -1) {
                        releaseViewForPointerUp();
                    }
                }
                clearMotionHistory($i0);
                return;
            default:
                break;
        }
    }

    private void reportNewEdgeDrags(float $f0, float $f1, int $i0) throws  {
        byte $b1 = (byte) 0;
        if (checkNewEdgeDrag($f0, $f1, $i0, 1)) {
            $b1 = (byte) 0 | (byte) 1;
        }
        if (checkNewEdgeDrag($f1, $f0, $i0, 4)) {
            $b1 |= (byte) 4;
        }
        if (checkNewEdgeDrag($f0, $f1, $i0, 2)) {
            $b1 |= (byte) 2;
        }
        if (checkNewEdgeDrag($f1, $f0, $i0, 8)) {
            $b1 |= (byte) 8;
        }
        if ($b1 != (byte) 0) {
            int[] $r1 = this.mEdgeDragsInProgress;
            $r1[$i0] = $r1[$i0] | $b1;
            this.mCallback.onEdgeDragStarted($b1, $i0);
        }
    }

    private boolean checkNewEdgeDrag(float $f0, float $f1, int $i0, int $i1) throws  {
        $f0 = Math.abs($f0);
        $f1 = Math.abs($f1);
        if ((this.mInitialEdgesTouched[$i0] & $i1) != $i1) {
            return false;
        }
        if ((this.mTrackingEdges & $i1) == 0) {
            return false;
        }
        if ((this.mEdgeDragsLocked[$i0] & $i1) == $i1) {
            return false;
        }
        if ((this.mEdgeDragsInProgress[$i0] & $i1) == $i1) {
            return false;
        }
        if ($f0 <= ((float) this.mTouchSlop) && $f1 <= ((float) this.mTouchSlop)) {
            return false;
        }
        if ($f0 < CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR * $f1 && this.mCallback.onEdgeLock($i1)) {
            int[] $r1 = this.mEdgeDragsLocked;
            $r1[$i0] = $r1[$i0] | $i1;
            return false;
        } else if ((this.mEdgeDragsInProgress[$i0] & $i1) == 0) {
            return $f0 > ((float) this.mTouchSlop);
        } else {
            return false;
        }
    }

    private boolean checkTouchSlop(View $r1, float $f0, float $f1) throws  {
        if ($r1 == null) {
            return false;
        }
        boolean $z0;
        boolean $z1;
        if (this.mCallback.getViewHorizontalDragRange($r1) > 0) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if (this.mCallback.getViewVerticalDragRange($r1) > 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        if ($z0 && $z1) {
            if (($f0 * $f0) + ($f1 * $f1) <= ((float) (this.mTouchSlop * this.mTouchSlop))) {
                return false;
            }
            return true;
        } else if ($z0) {
            if (Math.abs($f0) <= ((float) this.mTouchSlop)) {
                return false;
            }
            return true;
        } else if ($z1) {
            return Math.abs($f1) > ((float) this.mTouchSlop);
        } else {
            return false;
        }
    }

    public boolean checkTouchSlop(int $i0) throws  {
        int $i1 = this.mInitialMotionX.length;
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            if (checkTouchSlop($i0, $i2)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int $i0, int $i1) throws  {
        if (!isPointerDown($i1)) {
            return false;
        }
        boolean $z1;
        boolean $z0 = ($i0 & 1) == 1;
        if (($i0 & 2) == 2) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        float $f0 = this.mLastMotionX[$i1] - this.mInitialMotionX[$i1];
        float $f1 = this.mLastMotionY[$i1] - this.mInitialMotionY[$i1];
        if ($z0 && $z1) {
            if (($f0 * $f0) + ($f1 * $f1) <= ((float) (this.mTouchSlop * this.mTouchSlop))) {
                return false;
            }
            return true;
        } else if ($z0) {
            if (Math.abs($f0) <= ((float) this.mTouchSlop)) {
                return false;
            }
            return true;
        } else if ($z1) {
            return Math.abs($f1) > ((float) this.mTouchSlop);
        } else {
            return false;
        }
    }

    public boolean isEdgeTouched(int $i0) throws  {
        int $i1 = this.mInitialEdgesTouched.length;
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            if (isEdgeTouched($i0, $i2)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdgeTouched(int $i0, int $i1) throws  {
        return isPointerDown($i1) && (this.mInitialEdgesTouched[$i1] & $i0) != 0;
    }

    private void releaseViewForPointerUp() throws  {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        dispatchViewReleased(clampMag(VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void dragTo(int $i0, int $i1, int $i2, int $i3) throws  {
        int $i4 = $i0;
        int $i5 = $i1;
        int $i6 = this.mCapturedView.getLeft();
        int $i7 = this.mCapturedView.getTop();
        if ($i2 != 0) {
            $i0 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, $i0, $i2);
            $i4 = $i0;
            ViewCompat.offsetLeftAndRight(this.mCapturedView, $i0 - $i6);
        }
        if ($i3 != 0) {
            $i0 = this.mCallback.clampViewPositionVertical(this.mCapturedView, $i1, $i3);
            $i5 = $i0;
            ViewCompat.offsetTopAndBottom(this.mCapturedView, $i0 - $i7);
        }
        if ($i2 != 0 || $i3 != 0) {
            $i0 = $i4 - $i6;
            $i1 = $i5 - $i7;
            this.mCallback.onViewPositionChanged(this.mCapturedView, $i4, $i5, $i0, $i1);
        }
    }

    public boolean isCapturedViewUnder(int $i0, int $i1) throws  {
        return isViewUnder(this.mCapturedView, $i0, $i1);
    }

    public boolean isViewUnder(View $r1, int $i0, int $i1) throws  {
        if ($r1 == null) {
            return false;
        }
        if ($i0 < $r1.getLeft()) {
            return false;
        }
        if ($i0 >= $r1.getRight()) {
            return false;
        }
        if ($i1 >= $r1.getTop()) {
            return $i1 < $r1.getBottom();
        } else {
            return false;
        }
    }

    public View findTopChildUnder(int $i0, int $i1) throws  {
        for (int $i2 = this.mParentView.getChildCount() - 1; $i2 >= 0; $i2--) {
            View $r3 = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex($i2));
            if ($i0 >= $r3.getLeft() && $i0 < $r3.getRight() && $i1 >= $r3.getTop() && $i1 < $r3.getBottom()) {
                return $r3;
            }
        }
        return null;
    }

    private int getEdgesTouched(int $i0, int $i1) throws  {
        byte $b2 = (byte) 0;
        if ($i0 < this.mParentView.getLeft() + this.mEdgeSize) {
            $b2 = (byte) 0 | (byte) 1;
        }
        if ($i1 < this.mParentView.getTop() + this.mEdgeSize) {
            $b2 |= (byte) 4;
        }
        if ($i0 > this.mParentView.getRight() - this.mEdgeSize) {
            $b2 |= (byte) 2;
        }
        if ($i1 > this.mParentView.getBottom() - this.mEdgeSize) {
            return $b2 | (byte) 8;
        }
        return $b2;
    }

    private boolean isValidPointerForActionMove(int $i0) throws  {
        if (isPointerDown($i0)) {
            return true;
        }
        Log.e(TAG, "Ignoring pointerId=" + $i0 + " because ACTION_DOWN was not received " + "for this pointer before ACTION_MOVE. It likely happened because " + " ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }
}
