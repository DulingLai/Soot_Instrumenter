package android.support.v7.widget.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.animation.AnimatorListenerCompat;
import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.C0195R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ChildDrawingOrderCallback;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import com.waze.map.CanvasFont;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper extends ItemDecoration implements OnChildAttachStateChangeListener {
    private static final int ACTION_MODE_DRAG_MASK = 16711680;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    private static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    private static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    private static final boolean DEBUG = false;
    private static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    private static final String TAG = "ItemTouchHelper";
    public static final int UP = 1;
    int mActionState = 0;
    int mActivePointerId = -1;
    Callback mCallback;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    private GestureDetectorCompat mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    float mMaxSwipeVelocity;
    private final OnItemTouchListener mOnItemTouchListener = new C02822();
    private View mOverdrawChild = null;
    private int mOverdrawChildPosition = -1;
    final List<View> mPendingCleanup = new ArrayList();
    List<RecoverAnimation> mRecoverAnimations = new ArrayList();
    private RecyclerView mRecyclerView;
    private final Runnable mScrollRunnable = new C02811();
    ViewHolder mSelected = null;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private List<ViewHolder> mSwapTargets;
    float mSwipeEscapeVelocity;
    private final float[] mTmpPosition = new float[2];
    private Rect mTmpRect;
    private VelocityTracker mVelocityTracker;

    public interface ViewDropHandler {
        void prepareForDrop(View view, View view2, int i, int i2) throws ;
    }

    class C02811 implements Runnable {
        C02811() throws  {
        }

        public void run() throws  {
            if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
                if (ItemTouchHelper.this.mSelected != null) {
                    ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
                }
                ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                ViewCompat.postOnAnimation(ItemTouchHelper.this.mRecyclerView, this);
            }
        }
    }

    class C02822 implements OnItemTouchListener {
        C02822() throws  {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent $r2) throws  {
            ItemTouchHelper.this.mGestureDetector.onTouchEvent($r2);
            int $i0 = MotionEventCompat.getActionMasked($r2);
            if ($i0 == 0) {
                ItemTouchHelper.this.mActivePointerId = MotionEventCompat.getPointerId($r2, 0);
                ItemTouchHelper.this.mInitialTouchX = $r2.getX();
                ItemTouchHelper.this.mInitialTouchY = $r2.getY();
                ItemTouchHelper.this.obtainVelocityTracker();
                if (ItemTouchHelper.this.mSelected == null) {
                    RecoverAnimation $r6 = ItemTouchHelper.this.findAnimation($r2);
                    if ($r6 != null) {
                        ItemTouchHelper $r3 = ItemTouchHelper.this;
                        $r3.mInitialTouchX -= $r6.mX;
                        $r3 = ItemTouchHelper.this;
                        $r3.mInitialTouchY -= $r6.mY;
                        ItemTouchHelper.this.endRecoverAnimation($r6.mViewHolder, true);
                        if (ItemTouchHelper.this.mPendingCleanup.remove($r6.mViewHolder.itemView)) {
                            ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, $r6.mViewHolder);
                        }
                        ItemTouchHelper.this.select($r6.mViewHolder, $r6.mActionState);
                        ItemTouchHelper.this.updateDxDy($r2, ItemTouchHelper.this.mSelectedFlags, 0);
                    }
                }
            } else if ($i0 == 3 || $i0 == 1) {
                ItemTouchHelper.this.mActivePointerId = -1;
                ItemTouchHelper.this.select(null, 0);
            } else if (ItemTouchHelper.this.mActivePointerId != -1) {
                int $i1 = MotionEventCompat.findPointerIndex($r2, ItemTouchHelper.this.mActivePointerId);
                if ($i1 >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe($i0, $r2, $i1);
                }
            }
            if (ItemTouchHelper.this.mVelocityTracker != null) {
                ItemTouchHelper.this.mVelocityTracker.addMovement($r2);
            }
            if (ItemTouchHelper.this.mSelected != null) {
                return true;
            }
            return false;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onTouchEvent(android.support.v7.widget.RecyclerView r13, android.view.MotionEvent r14) throws  {
            /*
            r12 = this;
            r0 = 0;
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r2 = r1.mGestureDetector;
            r2.onTouchEvent(r14);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r3 = r1.mVelocityTracker;
            if (r3 == 0) goto L_0x001b;
        L_0x0012:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r3 = r1.mVelocityTracker;
            r3.addMovement(r14);
        L_0x001b:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r4 = r1.mActivePointerId;
            r5 = -1;
            if (r4 != r5) goto L_0x0023;
        L_0x0022:
            return;
        L_0x0023:
            r4 = android.support.v4.view.MotionEventCompat.getActionMasked(r14);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r6 = r1.mActivePointerId;
            r6 = android.support.v4.view.MotionEventCompat.findPointerIndex(r14, r6);
            if (r6 < 0) goto L_0x0036;
        L_0x0031:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r1.checkSelectForSwipe(r4, r14, r6);
        L_0x0036:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r7 = r1.mSelected;
            if (r7 == 0) goto L_0x00b5;
        L_0x003c:
            switch(r4) {
                case 1: goto L_0x0041;
                case 2: goto L_0x004e;
                case 3: goto L_0x0080;
                case 4: goto L_0x0040;
                case 5: goto L_0x0040;
                case 6: goto L_0x0092;
                default: goto L_0x003f;
            };
        L_0x003f:
            goto L_0x0040;
        L_0x0040:
            return;
        L_0x0041:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r8 = 0;
            r5 = 0;
            r1.select(r8, r5);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r5 = -1;
            r1.mActivePointerId = r5;
            return;
        L_0x004e:
            if (r6 < 0) goto L_0x00b6;
        L_0x0050:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r9 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r4 = r9.mSelectedFlags;
            r1.updateDxDy(r14, r4, r6);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r1.moveIfNecessary(r7);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r13 = r1.mRecyclerView;
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r10 = r1.mScrollRunnable;
            r13.removeCallbacks(r10);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r10 = r1.mScrollRunnable;
            r10.run();
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r13 = r1.mRecyclerView;
            r13.invalidate();
            return;
        L_0x0080:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r3 = r1.mVelocityTracker;
            if (r3 == 0) goto L_0x0041;
        L_0x0088:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r3 = r1.mVelocityTracker;
            r3.clear();
            goto L_0x0041;
        L_0x0092:
            r4 = android.support.v4.view.MotionEventCompat.getActionIndex(r14);
            r6 = android.support.v4.view.MotionEventCompat.getPointerId(r14, r4);
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r11 = r1.mActivePointerId;
            if (r6 != r11) goto L_0x00b7;
        L_0x00a0:
            if (r4 != 0) goto L_0x00a3;
        L_0x00a2:
            r0 = 1;
        L_0x00a3:
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r6 = android.support.v4.view.MotionEventCompat.getPointerId(r14, r0);
            r1.mActivePointerId = r6;
            r1 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r9 = android.support.v7.widget.helper.ItemTouchHelper.this;
            r6 = r9.mSelectedFlags;
            r1.updateDxDy(r14, r6, r4);
            return;
        L_0x00b5:
            return;
        L_0x00b6:
            return;
        L_0x00b7:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.2.onTouchEvent(android.support.v7.widget.RecyclerView, android.view.MotionEvent):void");
        }

        public void onRequestDisallowInterceptTouchEvent(boolean $z0) throws  {
            if ($z0) {
                ItemTouchHelper.this.select(null, 0);
            }
        }
    }

    private class RecoverAnimation implements AnimatorListenerCompat {
        final int mActionState;
        private final int mAnimationType;
        private boolean mEnded = false;
        private float mFraction;
        public boolean mIsPendingCleanup;
        boolean mOverridden = false;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        private final ValueAnimatorCompat mValueAnimator;
        final ViewHolder mViewHolder;
        float mX;
        float mY;

        public RecoverAnimation(ViewHolder $r2, int $i0, int $i1, float $f0, float $f1, float $f2, float $f3) throws  {
            this.mActionState = $i1;
            this.mAnimationType = $i0;
            this.mViewHolder = $r2;
            this.mStartDx = $f0;
            this.mStartDy = $f1;
            this.mTargetX = $f2;
            this.mTargetY = $f3;
            this.mValueAnimator = AnimatorCompatHelper.emptyValueAnimator();
            this.mValueAnimator.addUpdateListener(new AnimatorUpdateListenerCompat(ItemTouchHelper.this) {
                public void onAnimationUpdate(ValueAnimatorCompat $r1) throws  {
                    RecoverAnimation.this.setFraction($r1.getAnimatedFraction());
                }
            });
            this.mValueAnimator.setTarget($r2.itemView);
            this.mValueAnimator.addListener(this);
            setFraction(0.0f);
        }

        public void setDuration(long $l0) throws  {
            this.mValueAnimator.setDuration($l0);
        }

        public void start() throws  {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.start();
        }

        public void cancel() throws  {
            this.mValueAnimator.cancel();
        }

        public void setFraction(float $f0) throws  {
            this.mFraction = $f0;
        }

        public void update() throws  {
            if (this.mStartDx == this.mTargetX) {
                this.mX = ViewCompat.getTranslationX(this.mViewHolder.itemView);
            } else {
                this.mX = this.mStartDx + (this.mFraction * (this.mTargetX - this.mStartDx));
            }
            if (this.mStartDy == this.mTargetY) {
                this.mY = ViewCompat.getTranslationY(this.mViewHolder.itemView);
            } else {
                this.mY = this.mStartDy + (this.mFraction * (this.mTargetY - this.mStartDy));
            }
        }

        public void onAnimationStart(ValueAnimatorCompat animation) throws  {
        }

        public void onAnimationEnd(ValueAnimatorCompat animation) throws  {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        public void onAnimationCancel(ValueAnimatorCompat animation) throws  {
            setFraction(1.0f);
        }

        public void onAnimationRepeat(ValueAnimatorCompat animation) throws  {
        }
    }

    class C02833 extends RecoverAnimation {
        final /* synthetic */ ViewHolder val$prevSelected;
        final /* synthetic */ int val$swipeDir;

        C02833(ViewHolder $r2, int $i0, int $i1, float $f0, float $f1, float $f2, float $f3, int $i2, ViewHolder $r3) throws  {
            this.val$swipeDir = $i2;
            this.val$prevSelected = $r3;
            super($r2, $i0, $i1, $f0, $f1, $f2, $f3);
        }

        public void onAnimationEnd(ValueAnimatorCompat $r1) throws  {
            super.onAnimationEnd($r1);
            if (!this.mOverridden) {
                if (this.val$swipeDir <= 0) {
                    ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, this.val$prevSelected);
                } else {
                    ItemTouchHelper.this.mPendingCleanup.add(this.val$prevSelected.itemView);
                    this.mIsPendingCleanup = true;
                    if (this.val$swipeDir > 0) {
                        ItemTouchHelper.this.postDispatchSwipe(this, this.val$swipeDir);
                    }
                }
                if (ItemTouchHelper.this.mOverdrawChild == this.val$prevSelected.itemView) {
                    ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(this.val$prevSelected.itemView);
                }
            }
        }
    }

    class C02855 implements ChildDrawingOrderCallback {
        C02855() throws  {
        }

        public int onGetChildDrawingOrder(int $i0, int $i1) throws  {
            if (ItemTouchHelper.this.mOverdrawChild == null) {
                return $i1;
            }
            int $i2 = ItemTouchHelper.this.mOverdrawChildPosition;
            int $i3 = $i2;
            if ($i2 == -1) {
                $i2 = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
                $i3 = $i2;
                ItemTouchHelper.this.mOverdrawChildPosition = $i2;
            }
            if ($i1 == $i0 - 1) {
                return $i3;
            }
            return $i1 >= $i3 ? $i1 + 1 : $i1;
        }
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new C02861();
        private static final Interpolator sDragViewScrollCapInterpolator = new C02872();
        private static final ItemTouchUIUtil sUICallback;
        private int mCachedMaxScrollSpeed = -1;

        static class C02861 implements Interpolator {
            C02861() throws  {
            }

            public float getInterpolation(float $f0) throws  {
                return ((($f0 * $f0) * $f0) * $f0) * $f0;
            }
        }

        static class C02872 implements Interpolator {
            C02872() throws  {
            }

            public float getInterpolation(float $f0) throws  {
                $f0 -= 1.0f;
                return (((($f0 * $f0) * $f0) * $f0) * $f0) + 1.0f;
            }
        }

        public static int convertToRelativeDirection(int r1, int r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.helper.ItemTouchHelper.Callback.convertToRelativeDirection(int, int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
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
	... 8 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.Callback.convertToRelativeDirection(int, int):int");
        }

        public boolean canDropOver(RecyclerView recyclerView, ViewHolder current, ViewHolder target) throws  {
            return true;
        }

        public int convertToAbsoluteDirection(int r1, int r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.helper.ItemTouchHelper.Callback.convertToAbsoluteDirection(int, int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
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
	... 8 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.Callback.convertToAbsoluteDirection(int, int):int");
        }

        public int getBoundingBoxMargin() throws  {
            return 0;
        }

        public float getMoveThreshold(ViewHolder viewHolder) throws  {
            return CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }

        public abstract int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) throws ;

        public float getSwipeThreshold(ViewHolder viewHolder) throws  {
            return CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }

        public boolean isItemViewSwipeEnabled() throws  {
            return true;
        }

        public boolean isLongPressDragEnabled() throws  {
            return true;
        }

        public abstract boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) throws ;

        public abstract void onSwiped(ViewHolder viewHolder, int i) throws ;

        static {
            if (VERSION.SDK_INT >= 21) {
                sUICallback = new Lollipop();
            } else if (VERSION.SDK_INT >= 11) {
                sUICallback = new Honeycomb();
            } else {
                sUICallback = new Gingerbread();
            }
        }

        public static ItemTouchUIUtil getDefaultUIUtil() throws  {
            return sUICallback;
        }

        public static int makeMovementFlags(int $i0, int $i1) throws  {
            return (makeFlag(0, $i1 | $i0) | makeFlag(1, $i1)) | makeFlag(2, $i0);
        }

        public static int makeFlag(int $i0, int $i1) throws  {
            return $i1 << ($i0 * 8);
        }

        final int getAbsoluteMovementFlags(RecyclerView $r1, ViewHolder $r2) throws  {
            return convertToAbsoluteDirection(getMovementFlags($r1, $r2), ViewCompat.getLayoutDirection($r1));
        }

        private boolean hasDragFlag(RecyclerView $r1, ViewHolder $r2) throws  {
            return (ItemTouchHelper.ACTION_MODE_DRAG_MASK & getAbsoluteMovementFlags($r1, $r2)) != 0;
        }

        private boolean hasSwipeFlag(RecyclerView $r1, ViewHolder $r2) throws  {
            return (65280 & getAbsoluteMovementFlags($r1, $r2)) != 0;
        }

        public float getSwipeEscapeVelocity(float $f0) throws  {
            return $f0;
        }

        public float getSwipeVelocityThreshold(float $f0) throws  {
            return $f0;
        }

        public ViewHolder chooseDropTarget(@Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;II)", "Landroid/support/v7/widget/RecyclerView$ViewHolder;"}) ViewHolder $r1, @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;II)", "Landroid/support/v7/widget/RecyclerView$ViewHolder;"}) List<ViewHolder> $r2, @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;II)", "Landroid/support/v7/widget/RecyclerView$ViewHolder;"}) int $i0, @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;II)", "Landroid/support/v7/widget/RecyclerView$ViewHolder;"}) int $i1) throws  {
            ViewHolder $r3 = $r1;
            int $i5 = $i0 + $r3.itemView.getWidth();
            $r3 = $r3;
            int $i2 = $i1 + $r3.itemView.getHeight();
            ViewHolder $r4 = null;
            int $i6 = -1;
            $r3 = $r3;
            int $i3 = $i0 - $r3.itemView.getLeft();
            $r3 = $r3;
            $r1 = $r3;
            int $i4 = $i1 - $r3.itemView.getTop();
            int $i7 = $r2.size();
            for (int $i8 = 0; $i8 < $i7; $i8++) {
                int $i9;
                ViewHolder $r6 = (ViewHolder) $r2.get($i8);
                if ($i3 > 0) {
                    $i9 = $r6.itemView.getRight() - $i5;
                    if ($i9 < 0) {
                        $r3 = $r1;
                        $r1 = $r3;
                        if ($r6.itemView.getRight() > $r3.itemView.getRight()) {
                            $i9 = Math.abs($i9);
                            if ($i9 > $i6) {
                                $i6 = $i9;
                                $r4 = $r6;
                            }
                        }
                    }
                }
                if ($i3 < 0) {
                    $i9 = $r6.itemView.getLeft() - $i0;
                    if ($i9 > 0) {
                        $r3 = $r1;
                        $r1 = $r3;
                        if ($r6.itemView.getLeft() < $r3.itemView.getLeft()) {
                            $i9 = Math.abs($i9);
                            if ($i9 > $i6) {
                                $i6 = $i9;
                                $r4 = $r6;
                            }
                        }
                    }
                }
                if ($i4 < 0) {
                    $i9 = $r6.itemView.getTop() - $i1;
                    if ($i9 > 0) {
                        $r3 = $r1;
                        $r1 = $r3;
                        if ($r6.itemView.getTop() < $r3.itemView.getTop()) {
                            $i9 = Math.abs($i9);
                            if ($i9 > $i6) {
                                $i6 = $i9;
                                $r4 = $r6;
                            }
                        }
                    }
                }
                if ($i4 > 0) {
                    $i9 = $r6.itemView.getBottom() - $i2;
                    if ($i9 < 0) {
                        $r3 = $r1;
                        $r1 = $r3;
                        if ($r6.itemView.getBottom() > $r3.itemView.getBottom()) {
                            $i9 = Math.abs($i9);
                            if ($i9 > $i6) {
                                $i6 = $i9;
                                $r4 = $r6;
                            }
                        }
                    }
                }
            }
            return $r4;
        }

        public void onSelectedChanged(ViewHolder $r1, int actionState) throws  {
            if ($r1 != null) {
                sUICallback.onSelected($r1.itemView);
            }
        }

        private int getMaxDragScroll(RecyclerView $r1) throws  {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = $r1.getResources().getDimensionPixelSize(C0195R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public void onMoved(RecyclerView $r1, ViewHolder $r2, int fromPos, ViewHolder $r3, int $i1, int $i2, int $i3) throws  {
            LayoutManager $r4 = $r1.getLayoutManager();
            if ($r4 instanceof ViewDropHandler) {
                ((ViewDropHandler) $r4).prepareForDrop($r2.itemView, $r3.itemView, $i2, $i3);
                return;
            }
            if ($r4.canScrollHorizontally()) {
                if ($r4.getDecoratedLeft($r3.itemView) <= $r1.getPaddingLeft()) {
                    $r1.scrollToPosition($i1);
                }
                if ($r4.getDecoratedRight($r3.itemView) >= $r1.getWidth() - $r1.getPaddingRight()) {
                    $r1.scrollToPosition($i1);
                }
            }
            if ($r4.canScrollVertically()) {
                if ($r4.getDecoratedTop($r3.itemView) <= $r1.getPaddingTop()) {
                    $r1.scrollToPosition($i1);
                }
                if ($r4.getDecoratedBottom($r3.itemView) >= $r1.getHeight() - $r1.getPaddingBottom()) {
                    $r1.scrollToPosition($i1);
                }
            }
        }

        private void onDraw(@Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) Canvas $r1, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) RecyclerView $r2, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) ViewHolder $r3, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) List<RecoverAnimation> $r4, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) int $i0, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) float $f0, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) float $f1) throws  {
            int $i1 = $r4.size();
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                RecoverAnimation $r6 = (RecoverAnimation) $r4.get($i2);
                $r6.update();
                int $i3 = $r1.save();
                onChildDraw($r1, $r2, $r6.mViewHolder, $r6.mX, $r6.mY, $r6.mActionState, false);
                $r1.restoreToCount($i3);
            }
            if ($r3 != null) {
                $i1 = $r1.save();
                onChildDraw($r1, $r2, $r3, $f0, $f1, $i0, true);
                $r1.restoreToCount($i1);
            }
        }

        private void onDrawOver(@Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) Canvas $r1, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) RecyclerView $r2, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) ViewHolder $r3, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) List<RecoverAnimation> $r4, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) int $i0, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) float $f0, @Signature({"(", "Landroid/graphics/Canvas;", "Landroid/support/v7/widget/RecyclerView;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Landroid/support/v7/widget/helper/ItemTouchHelper$RecoverAnimation;", ">;IFF)V"}) float $f1) throws  {
            int $i2;
            int $i1 = $r4.size();
            for ($i2 = 0; $i2 < $i1; $i2++) {
                RecoverAnimation $r6 = (RecoverAnimation) $r4.get($i2);
                int $i3 = $r1.save();
                onChildDrawOver($r1, $r2, $r6.mViewHolder, $r6.mX, $r6.mY, $r6.mActionState, false);
                $r1.restoreToCount($i3);
            }
            if ($r3 != null) {
                $i2 = $r1.save();
                onChildDrawOver($r1, $r2, $r3, $f0, $f1, $i0, true);
                $r1.restoreToCount($i2);
            }
            Object obj = null;
            for ($i0 = $i1 - 1; $i0 >= 0; $i0--) {
                $r6 = (RecoverAnimation) $r4.get($i0);
                if ($r6.mEnded && !$r6.mIsPendingCleanup) {
                    $r4.remove($i0);
                } else if (!$r6.mEnded) {
                    obj = 1;
                }
            }
            if (obj != null) {
                $r2.invalidate();
            }
        }

        public void clearView(RecyclerView recyclerView, ViewHolder $r2) throws  {
            sUICallback.clearView($r2.itemView);
        }

        public void onChildDraw(Canvas $r1, RecyclerView $r2, ViewHolder $r3, float $f0, float $f1, int $i0, boolean $z0) throws  {
            sUICallback.onDraw($r1, $r2, $r3.itemView, $f0, $f1, $i0, $z0);
        }

        public void onChildDrawOver(Canvas $r1, RecyclerView $r2, ViewHolder $r3, float $f0, float $f1, int $i0, boolean $z0) throws  {
            sUICallback.onDrawOver($r1, $r2, $r3.itemView, $f0, $f1, $i0, $z0);
        }

        public long getAnimationDuration(RecyclerView $r1, int $i0, float animateDx, float animateDy) throws  {
            ItemAnimator $r2 = $r1.getItemAnimator();
            return $r2 == null ? $i0 == 8 ? 200 : 250 : $i0 == 8 ? $r2.getMoveDuration() : $r2.getRemoveDuration();
        }

        public int interpolateOutOfBoundsScroll(RecyclerView $r1, int $i0, int $i1, int totalSize, long $l3) throws  {
            float $f1;
            $i0 = (int) (((float) (((int) Math.signum((float) $i1)) * getMaxDragScroll($r1))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (1.0f * ((float) Math.abs($i1))) / ((float) $i0))));
            if ($l3 > DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS) {
                $f1 = 1.0f;
            } else {
                $f1 = ((float) $l3) / 2000.0f;
            }
            $i0 = (int) (((float) $i0) * sDragScrollInterpolator.getInterpolation($f1));
            if ($i0 == 0) {
                return $i1 > 0 ? 1 : -1;
            } else {
                return $i0;
            }
        }
    }

    private class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        public boolean onDown(MotionEvent e) throws  {
            return true;
        }

        private ItemTouchHelperGestureListener() throws  {
        }

        public void onLongPress(MotionEvent $r1) throws  {
            View $r3 = ItemTouchHelper.this.findChildView($r1);
            if ($r3 != null) {
                ViewHolder $r5 = ItemTouchHelper.this.mRecyclerView.getChildViewHolder($r3);
                if ($r5 != null && ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, $r5) && MotionEventCompat.getPointerId($r1, 0) == ItemTouchHelper.this.mActivePointerId) {
                    int $i0 = MotionEventCompat.findPointerIndex($r1, ItemTouchHelper.this.mActivePointerId);
                    float $f0 = MotionEventCompat.getX($r1, $i0);
                    float $f1 = MotionEventCompat.getY($r1, $i0);
                    ItemTouchHelper.this.mInitialTouchX = $f0;
                    ItemTouchHelper.this.mInitialTouchY = $f1;
                    ItemTouchHelper $r2 = ItemTouchHelper.this;
                    ItemTouchHelper.this.mDy = 0.0f;
                    $r2.mDx = 0.0f;
                    if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.select($r5, 2);
                    }
                }
            }
        }
    }

    public static abstract class SimpleCallback extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int $i0, int $i1) throws  {
            this.mDefaultSwipeDirs = $i1;
            this.mDefaultDragDirs = $i0;
        }

        public void setDefaultSwipeDirs(int $i0) throws  {
            this.mDefaultSwipeDirs = $i0;
        }

        public void setDefaultDragDirs(int $i0) throws  {
            this.mDefaultDragDirs = $i0;
        }

        public int getSwipeDirs(RecyclerView recyclerView, ViewHolder viewHolder) throws  {
            return this.mDefaultSwipeDirs;
        }

        public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) throws  {
            return this.mDefaultDragDirs;
        }

        public int getMovementFlags(RecyclerView $r1, ViewHolder $r2) throws  {
            return Callback.makeMovementFlags(getDragDirs($r1, $r2), getSwipeDirs($r1, $r2));
        }
    }

    private void select(android.support.v7.widget.RecyclerView.ViewHolder r38, int r39) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:38:0x01b0
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
        r37 = this;
        r0 = r37;
        r11 = r0.mSelected;
        r0 = r38;
        if (r0 != r11) goto L_0x0011;
    L_0x0008:
        r0 = r37;
        r12 = r0.mActionState;
        r0 = r39;
        if (r0 != r12) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r13 = -9223372036854775808;
        r0 = r37;
        r0.mDragScrollStartTimeInMs = r13;
        r0 = r37;
        r12 = r0.mActionState;
        r15 = 1;
        r0 = r37;
        r1 = r38;
        r0.endRecoverAnimation(r1, r15);
        r0 = r39;
        r1 = r37;
        r1.mActionState = r0;
        r15 = 2;
        r0 = r39;
        if (r0 != r15) goto L_0x0040;
    L_0x0031:
        r0 = r38;
        r0 = r0.itemView;
        r16 = r0;
        r1 = r37;
        r1.mOverdrawChild = r0;
        r0 = r37;
        r0.addChildDrawingOrderCallback();
    L_0x0040:
        r17 = r39 * 8;
        r17 = r17 + 8;
        r15 = 1;
        r17 = r15 << r17;
        r17 = r17 + -1;
        r18 = 0;
        r0 = r37;
        r11 = r0.mSelected;
        if (r11 == 0) goto L_0x00f6;
    L_0x0051:
        r0 = r37;
        r11 = r0.mSelected;
        r0 = r11.itemView;
        r16 = r0;
        r19 = r0.getParent();
        if (r19 == 0) goto L_0x021d;
    L_0x005f:
        r15 = 2;
        if (r12 != r15) goto L_0x01b4;
    L_0x0062:
        r20 = 0;
    L_0x0064:
        r0 = r37;
        r0.releaseVelocityTracker();
        switch(r20) {
            case 1: goto L_0x01e6;
            case 2: goto L_0x01e6;
            case 4: goto L_0x01bb;
            case 8: goto L_0x01bb;
            case 16: goto L_0x01bb;
            case 32: goto L_0x01bb;
            default: goto L_0x006c;
        };
    L_0x006c:
        goto L_0x006d;
    L_0x006d:
        r21 = 0;
        r22 = 0;
    L_0x0071:
        r15 = 2;
        if (r12 != r15) goto L_0x020d;
    L_0x0074:
        r23 = 8;
    L_0x0076:
        r0 = r37;
        r0 = r0.mTmpPosition;
        r24 = r0;
        r0 = r37;
        r1 = r24;
        r0.getSelectedDxDy(r1);
        r0 = r37;
        r0 = r0.mTmpPosition;
        r24 = r0;
        r15 = 0;
        r25 = r24[r15];
        r0 = r37;
        r0 = r0.mTmpPosition;
        r24 = r0;
        r15 = 1;
        r26 = r24[r15];
        r27 = new android.support.v7.widget.helper.ItemTouchHelper$3;
        r0 = r27;
        r1 = r37;
        r2 = r11;
        r3 = r23;
        r4 = r12;
        r5 = r25;
        r6 = r26;
        r7 = r21;
        r8 = r22;
        r9 = r20;
        r10 = r11;
        r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10);
        r0 = r37;
        r0 = r0.mCallback;
        r28 = r0;
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r0 = r21;
        r1 = r25;
        r0 = r0 - r1;
        r21 = r0;
        r0 = r22;
        r1 = r26;
        r0 = r0 - r1;
        r22 = r0;
        r0 = r28;
        r1 = r29;
        r2 = r23;
        r3 = r21;
        r4 = r22;
        r30 = r0.getAnimationDuration(r1, r2, r3, r4);
        r0 = r27;
        r1 = r30;
        r0.setDuration(r1);
        r0 = r37;
        r0 = r0.mRecoverAnimations;
        r32 = r0;
        r1 = r27;
        r0.add(r1);
        r0 = r27;
        r0.start();
        r18 = 1;
    L_0x00ee:
        r33 = 0;
        r0 = r33;
        r1 = r37;
        r1.mSelected = r0;
    L_0x00f6:
        if (r38 == 0) goto L_0x015d;
    L_0x00f8:
        r0 = r37;
        r0 = r0.mCallback;
        r28 = r0;
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r2 = r38;
        r12 = r0.getAbsoluteMovementFlags(r1, r2);
        r0 = r17;
        r12 = r12 & r0;
        r0 = r37;
        r0 = r0.mActionState;
        r17 = r0;
        r17 = r17 * 8;
        r0 = r17;
        r12 = r12 >> r0;
        r0 = r37;
        r0.mSelectedFlags = r12;
        r0 = r38;
        r0 = r0.itemView;
        r16 = r0;
        r12 = r0.getLeft();
        r0 = (float) r12;
        r22 = r0;
        r1 = r37;
        r1.mSelectedStartX = r0;
        r0 = r38;
        r0 = r0.itemView;
        r16 = r0;
        r12 = r0.getTop();
        r0 = (float) r12;
        r22 = r0;
        r1 = r37;
        r1.mSelectedStartY = r0;
        r0 = r38;
        r1 = r37;
        r1.mSelected = r0;
        r15 = 2;
        r0 = r39;
        if (r0 != r15) goto L_0x015d;
    L_0x014d:
        r0 = r37;
        r0 = r0.mSelected;
        r38 = r0;
        r0 = r0.itemView;
        r16 = r0;
        r15 = 0;
        r0 = r16;
        r0.performHapticFeedback(r15);
    L_0x015d:
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r19 = r0.getParent();
        if (r19 == 0) goto L_0x017a;
    L_0x0169:
        r0 = r37;
        r0 = r0.mSelected;
        r38 = r0;
        if (r38 == 0) goto L_0x0240;
    L_0x0171:
        r34 = 1;
    L_0x0173:
        r0 = r19;
        r1 = r34;
        r0.requestDisallowInterceptTouchEvent(r1);
    L_0x017a:
        if (r18 != 0) goto L_0x018b;
    L_0x017c:
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r35 = r0.getLayoutManager();
        r0 = r35;
        r0.requestSimpleAnimationsInNextLayout();
    L_0x018b:
        r0 = r37;
        r0 = r0.mCallback;
        r28 = r0;
        r0 = r37;
        r0 = r0.mSelected;
        r38 = r0;
        r0 = r37;
        r0 = r0.mActionState;
        r39 = r0;
        r0 = r28;
        r1 = r38;
        r2 = r39;
        r0.onSelectedChanged(r1, r2);
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r0.invalidate();
        return;
        goto L_0x01b4;
    L_0x01b1:
        goto L_0x0064;
    L_0x01b4:
        r0 = r37;
        r20 = r0.swipeIfNecessary(r11);
        goto L_0x01b1;
    L_0x01bb:
        r22 = 0;
        r0 = r37;
        r0 = r0.mDx;
        r21 = r0;
        r21 = java.lang.Math.signum(r0);
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r36 = r0.getWidth();
        r0 = r36;
        r0 = (float) r0;
        r26 = r0;
        goto L_0x01de;
    L_0x01d7:
        goto L_0x0071;
        goto L_0x01de;
    L_0x01db:
        goto L_0x0173;
    L_0x01de:
        r0 = r21;
        r1 = r26;
        r0 = r0 * r1;
        r21 = r0;
        goto L_0x01d7;
    L_0x01e6:
        r21 = 0;
        r0 = r37;
        r0 = r0.mDy;
        r22 = r0;
        r22 = java.lang.Math.signum(r0);
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        r36 = r0.getHeight();
        r0 = r36;
        r0 = (float) r0;
        r26 = r0;
        goto L_0x0205;
    L_0x0202:
        goto L_0x0071;
    L_0x0205:
        r0 = r22;
        r1 = r26;
        r0 = r0 * r1;
        r22 = r0;
        goto L_0x0202;
    L_0x020d:
        if (r20 <= 0) goto L_0x021a;
    L_0x020f:
        goto L_0x0213;
    L_0x0210:
        goto L_0x0076;
    L_0x0213:
        r23 = 2;
        goto L_0x0210;
        goto L_0x021a;
    L_0x0217:
        goto L_0x0076;
    L_0x021a:
        r23 = 4;
        goto L_0x0217;
    L_0x021d:
        r0 = r11.itemView;
        r16 = r0;
        r0 = r37;
        r1 = r16;
        r0.removeChildDrawingOrderCallbackIfNecessary(r1);
        r0 = r37;
        r0 = r0.mCallback;
        r28 = r0;
        r0 = r37;
        r0 = r0.mRecyclerView;
        r29 = r0;
        goto L_0x0238;
    L_0x0235:
        goto L_0x00ee;
    L_0x0238:
        r0 = r28;
        r1 = r29;
        r0.clearView(r1, r11);
        goto L_0x0235;
    L_0x0240:
        r34 = 0;
        goto L_0x01db;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.select(android.support.v7.widget.RecyclerView$ViewHolder, int):void");
    }

    public ItemTouchHelper(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    private static boolean hitTest(View $r0, float $f0, float $f1, float $f2, float $f3) throws  {
        return $f0 >= $f2 && $f0 <= ((float) $r0.getWidth()) + $f2 && $f1 >= $f3 && $f1 <= ((float) $r0.getHeight()) + $f3;
    }

    public void attachToRecyclerView(@Nullable RecyclerView $r1) throws  {
        if (this.mRecyclerView != $r1) {
            if (this.mRecyclerView != null) {
                destroyCallbacks();
            }
            this.mRecyclerView = $r1;
            if (this.mRecyclerView != null) {
                Resources $r3 = $r1.getResources();
                this.mSwipeEscapeVelocity = $r3.getDimension(C0195R.dimen.item_touch_helper_swipe_escape_velocity);
                this.mMaxSwipeVelocity = $r3.getDimension(C0195R.dimen.item_touch_helper_swipe_escape_max_velocity);
                setupCallbacks();
            }
        }
    }

    private void setupCallbacks() throws  {
        this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        initGestureDetector();
    }

    private void destroyCallbacks() throws  {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int $i0 = this.mRecoverAnimations.size() - 1; $i0 >= 0; $i0--) {
            this.mCallback.clearView(this.mRecyclerView, ((RecoverAnimation) this.mRecoverAnimations.get(0)).mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        releaseVelocityTracker();
    }

    private void initGestureDetector() throws  {
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), new ItemTouchHelperGestureListener());
        }
    }

    private void getSelectedDxDy(float[] $r1) throws  {
        if ((this.mSelectedFlags & 12) != 0) {
            $r1[0] = (this.mSelectedStartX + this.mDx) - ((float) this.mSelected.itemView.getLeft());
        } else {
            $r1[0] = ViewCompat.getTranslationX(this.mSelected.itemView);
        }
        if ((this.mSelectedFlags & 3) != 0) {
            $r1[1] = (this.mSelectedStartY + this.mDy) - ((float) this.mSelected.itemView.getTop());
        } else {
            $r1[1] = ViewCompat.getTranslationY(this.mSelected.itemView);
        }
    }

    public void onDrawOver(Canvas $r1, RecyclerView $r2, State state) throws  {
        float $f0 = 0.0f;
        float $f1 = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            $f0 = this.mTmpPosition[0];
            $f1 = this.mTmpPosition[1];
        }
        this.mCallback.onDrawOver($r1, $r2, this.mSelected, this.mRecoverAnimations, this.mActionState, $f0, $f1);
    }

    public void onDraw(Canvas $r1, RecyclerView $r2, State state) throws  {
        this.mOverdrawChildPosition = -1;
        float $f0 = 0.0f;
        float $f1 = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            $f0 = this.mTmpPosition[0];
            $f1 = this.mTmpPosition[1];
        }
        this.mCallback.onDraw($r1, $r2, this.mSelected, this.mRecoverAnimations, this.mActionState, $f0, $f1);
    }

    private void postDispatchSwipe(final RecoverAnimation $r1, final int $i0) throws  {
        this.mRecyclerView.post(new Runnable() {
            public void run() throws  {
                if (ItemTouchHelper.this.mRecyclerView != null && ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() && !$r1.mOverridden && $r1.mViewHolder.getAdapterPosition() != -1) {
                    ItemAnimator $r5 = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                    if (($r5 == null || !$r5.isRunning(null)) && !ItemTouchHelper.this.hasRunningRecoverAnim()) {
                        ItemTouchHelper.this.mCallback.onSwiped($r1.mViewHolder, $i0);
                    } else {
                        ItemTouchHelper.this.mRecyclerView.post(this);
                    }
                }
            }
        });
    }

    private boolean hasRunningRecoverAnim() throws  {
        int $i0 = this.mRecoverAnimations.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (!((RecoverAnimation) this.mRecoverAnimations.get($i1)).mEnded) {
                return true;
            }
        }
        return false;
    }

    private boolean scrollIfNecessary() throws  {
        if (this.mSelected == null) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        long $l3;
        int $i0;
        int $i02;
        Rect $r5;
        int $i1;
        long $l2 = System.currentTimeMillis();
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            $l3 = 0;
        } else {
            $l3 = $l2 - this.mDragScrollStartTimeInMs;
        }
        RecyclerView $r3 = this.mRecyclerView;
        LayoutManager $r4 = $r3.getLayoutManager();
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        int $i5 = 0;
        int $i6 = 0;
        $r4.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
        if ($r4.canScrollHorizontally()) {
            $i0 = this.mSelectedStartX + this.mDx;
            int i = $i0;
            $i02 = (int) $i0;
            $i0 = this.mTmpRect;
            $r5 = $i0;
            $i1 = $i02 - $i0.left;
            $r3 = this.mRecyclerView;
            $i1 -= $r3.getPaddingLeft();
            if (this.mDx < 0.0f && $i1 < 0) {
                $i5 = $i1;
            } else if (this.mDx > 0.0f) {
                View $r6 = this.mSelected.itemView;
                $i02 = $r6.getWidth() + $i02;
                $i0 = this.mTmpRect;
                $r5 = $i0;
                $i02 += $i0.right;
                $r3 = this.mRecyclerView;
                $i1 = $r3.getWidth();
                $r3 = this.mRecyclerView;
                $i02 -= $i1 - $r3.getPaddingRight();
                if ($i02 > 0) {
                    $i5 = $i02;
                }
            }
        }
        if ($r4.canScrollVertically()) {
            $i0 = this.mSelectedStartY + this.mDy;
            i = $i0;
            $i02 = (int) $i0;
            Rect $r52 = this.mTmpRect;
            $i1 = $i02 - $r52.top;
            $r3 = this.mRecyclerView;
            $i1 -= $r3.getPaddingTop();
            if (this.mDy < 0.0f && $i1 < 0) {
                $i6 = $i1;
            } else if (this.mDy > 0.0f) {
                $r6 = this.mSelected.itemView;
                $i02 = $r6.getHeight() + $i02;
                $i0 = this.mTmpRect;
                $r5 = $i0;
                $i02 += $i0.bottom;
                $r3 = this.mRecyclerView;
                $i1 = $r3.getHeight();
                $r3 = this.mRecyclerView;
                $i02 -= $i1 - $r3.getPaddingBottom();
                if ($i02 > 0) {
                    $i6 = $i02;
                }
            }
        }
        if ($i5 != 0) {
            Callback $r1 = this.mCallback;
            RecyclerView $r32 = this.mRecyclerView;
            $r6 = this.mSelected.itemView;
            $i02 = $r6.getWidth();
            $r3 = this.mRecyclerView;
            $i5 = $r1.interpolateOutOfBoundsScroll($r32, $i02, $i5, $r3.getWidth(), $l3);
        }
        if ($i6 != 0) {
            $r1 = this.mCallback;
            $r32 = this.mRecyclerView;
            $r6 = this.mSelected.itemView;
            $i02 = $r6.getHeight();
            $r3 = this.mRecyclerView;
            $i6 = $r1.interpolateOutOfBoundsScroll($r32, $i02, $i6, $r3.getHeight(), $l3);
        }
        if ($i5 == 0 && $i6 == 0) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            this.mDragScrollStartTimeInMs = $l2;
        }
        $r3 = this.mRecyclerView;
        $r32 = $r3;
        $r3.scrollBy($i5, $i6);
        return true;
    }

    private List<ViewHolder> findSwapTargets(@Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ")", "Ljava/util/List", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;"}) ViewHolder $r1) throws  {
        if (this.mSwapTargets == null) {
            this.mSwapTargets = new ArrayList();
            this.mDistances = new ArrayList();
        } else {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        }
        int $i0 = this.mCallback.getBoundingBoxMargin();
        int $i4 = Math.round(this.mSelectedStartX + this.mDx) - $i0;
        int $i6 = Math.round(this.mSelectedStartY + this.mDy) - $i0;
        int $i5 = ($r1.itemView.getWidth() + $i4) + ($i0 * 2);
        $i0 = ($r1.itemView.getHeight() + $i6) + ($i0 * 2);
        int $i1 = ($i4 + $i5) / 2;
        int $i2 = ($i6 + $i0) / 2;
        LayoutManager $r7 = this.mRecyclerView.getLayoutManager();
        int $i7 = $r7.getChildCount();
        for (int $i8 = 0; $i8 < $i7; $i8++) {
            View $r5 = $r7.getChildAt($i8);
            View $r8 = $r1.itemView;
            if ($r5 != $r8 && $r5.getBottom() >= $i6 && $r5.getTop() <= $i0 && $r5.getRight() >= $i4 && $r5.getLeft() <= $i5) {
                ViewHolder $r9 = this.mRecyclerView.getChildViewHolder($r5);
                Callback $r4 = this.mCallback;
                RecyclerView $r6 = this.mRecyclerView;
                ViewHolder viewHolder = this.mSelected;
                ViewHolder $r10 = viewHolder;
                if ($r4.canDropOver($r6, viewHolder, $r9)) {
                    int $i3 = Math.abs($i1 - (($r5.getLeft() + $r5.getRight()) / 2));
                    int $i9 = Math.abs($i2 - (($r5.getTop() + $r5.getBottom()) / 2));
                    $i3 = ($i3 * $i3) + ($i9 * $i9);
                    $i9 = 0;
                    int $i10 = this.mSwapTargets.size();
                    int $i11 = 0;
                    while ($i11 < $i10 && $i3 > ((Integer) this.mDistances.get($i11)).intValue()) {
                        $i9++;
                        $i11++;
                    }
                    this.mSwapTargets.add($i9, $r9);
                    this.mDistances.add($i9, Integer.valueOf($i3));
                }
            }
        }
        return this.mSwapTargets;
    }

    private void moveIfNecessary(ViewHolder $r1) throws  {
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            float $f0 = this.mCallback.getMoveThreshold($r1);
            int $i0 = (int) (this.mSelectedStartX + this.mDx);
            int $i1 = (int) (this.mSelectedStartY + this.mDy);
            View $r4 = $r1.itemView;
            float $f1 = (float) Math.abs($i1 - $r4.getTop());
            $r4 = $r1.itemView;
            if ($f1 < ((float) $r4.getHeight()) * $f0) {
                $r4 = $r1.itemView;
                $f1 = (float) Math.abs($i0 - $r4.getLeft());
                $r4 = $r1.itemView;
                if ($f1 < ((float) $r4.getWidth()) * $f0) {
                    return;
                }
            }
            List $r5 = findSwapTargets($r1);
            if ($r5.size() != 0) {
                ViewHolder $r6 = this.mCallback.chooseDropTarget($r1, $r5, $i0, $i1);
                if ($r6 == null) {
                    List $r52 = this.mSwapTargets;
                    $r52.clear();
                    $r52 = this.mDistances;
                    $r52.clear();
                    return;
                }
                int $i2 = $r6.getAdapterPosition();
                int $i4 = $r1.getAdapterPosition();
                if (this.mCallback.onMove(this.mRecyclerView, $r1, $r6)) {
                    this.mCallback.onMoved(this.mRecyclerView, $r1, $i4, $r6, $i2, $i0, $i1);
                }
            }
        }
    }

    public void onChildViewAttachedToWindow(View view) throws  {
    }

    public void onChildViewDetachedFromWindow(View $r1) throws  {
        removeChildDrawingOrderCallbackIfNecessary($r1);
        ViewHolder $r3 = this.mRecyclerView.getChildViewHolder($r1);
        if ($r3 != null) {
            if (this.mSelected == null || $r3 != this.mSelected) {
                endRecoverAnimation($r3, false);
                if (this.mPendingCleanup.remove($r3.itemView)) {
                    this.mCallback.clearView(this.mRecyclerView, $r3);
                    return;
                }
                return;
            }
            select(null, 0);
        }
    }

    private int endRecoverAnimation(ViewHolder $r1, boolean $z0) throws  {
        for (int $i0 = this.mRecoverAnimations.size() - 1; $i0 >= 0; $i0--) {
            RecoverAnimation $r4 = (RecoverAnimation) this.mRecoverAnimations.get($i0);
            if ($r4.mViewHolder == $r1) {
                $r4.mOverridden |= $z0;
                if (!$r4.mEnded) {
                    $r4.cancel();
                }
                this.mRecoverAnimations.remove($i0);
                return $r4.mAnimationType;
            }
        }
        return 0;
    }

    public void getItemOffsets(Rect $r1, View view, RecyclerView parent, State state) throws  {
        $r1.setEmpty();
    }

    private void obtainVelocityTracker() throws  {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    private void releaseVelocityTracker() throws  {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private ViewHolder findSwipedView(MotionEvent $r1) throws  {
        LayoutManager $r3 = this.mRecyclerView.getLayoutManager();
        if (this.mActivePointerId == -1) {
            return null;
        }
        int $i0 = MotionEventCompat.findPointerIndex($r1, this.mActivePointerId);
        float $f1 = MotionEventCompat.getY($r1, $i0) - this.mInitialTouchY;
        float $f0 = Math.abs(MotionEventCompat.getX($r1, $i0) - this.mInitialTouchX);
        $f1 = Math.abs($f1);
        if ($f0 < ((float) this.mSlop) && $f1 < ((float) this.mSlop)) {
            return null;
        }
        if ($f0 > $f1 && $r3.canScrollHorizontally()) {
            return null;
        }
        if ($f1 > $f0 && $r3.canScrollVertically()) {
            return null;
        }
        View $r4 = findChildView($r1);
        return $r4 != null ? this.mRecyclerView.getChildViewHolder($r4) : null;
    }

    private boolean checkSelectForSwipe(int $i0, MotionEvent $r1, int $i1) throws  {
        if (this.mSelected != null || $i0 != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled()) {
            return false;
        }
        if (this.mRecyclerView.getScrollState() == 1) {
            return false;
        }
        ViewHolder $r2 = findSwipedView($r1);
        if ($r2 == null) {
            return false;
        }
        $i0 = (65280 & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, $r2)) >> 8;
        if ($i0 == 0) {
            return false;
        }
        float $f0 = MotionEventCompat.getX($r1, $i1);
        $f0 -= this.mInitialTouchX;
        float $f1 = MotionEventCompat.getY($r1, $i1) - this.mInitialTouchY;
        float $f2 = Math.abs($f0);
        float $f3 = Math.abs($f1);
        if ($f2 < ((float) this.mSlop) && $f3 < ((float) this.mSlop)) {
            return false;
        }
        if ($f2 > $f3) {
            if ($f0 < 0.0f && ($i0 & 4) == 0) {
                return false;
            }
            if ($f0 > 0.0f && ($i0 & 8) == 0) {
                return false;
            }
        } else if ($f1 < 0.0f && ($i0 & 1) == 0) {
            return false;
        } else {
            if ($f1 > 0.0f && ($i0 & 2) == 0) {
                return false;
            }
        }
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.mActivePointerId = MotionEventCompat.getPointerId($r1, 0);
        select($r2, 1);
        return true;
    }

    private View findChildView(MotionEvent $r1) throws  {
        View $r2;
        float $f1 = $r1.getX();
        float $f2 = $r1.getY();
        if (this.mSelected != null) {
            $r2 = this.mSelected.itemView;
            if (hitTest($r2, $f1, $f2, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return $r2;
            }
        }
        for (int $i0 = this.mRecoverAnimations.size() - 1; $i0 >= 0; $i0--) {
            RecoverAnimation $r6 = (RecoverAnimation) this.mRecoverAnimations.get($i0);
            $r2 = $r6.mViewHolder.itemView;
            if (hitTest($r2, $f1, $f2, $r6.mX, $r6.mY)) {
                return $r2;
            }
        }
        return this.mRecyclerView.findChildViewUnder($f1, $f2);
    }

    public void startDrag(ViewHolder $r1) throws  {
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, $r1)) {
            Log.e(TAG, "Start drag has been called but swiping is not enabled");
        } else if ($r1.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select($r1, 2);
        }
    }

    public void startSwipe(ViewHolder $r1) throws  {
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, $r1)) {
            Log.e(TAG, "Start swipe has been called but dragging is not enabled");
        } else if ($r1.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select($r1, 1);
        }
    }

    private RecoverAnimation findAnimation(MotionEvent $r1) throws  {
        if (this.mRecoverAnimations.isEmpty()) {
            return null;
        }
        View $r4 = findChildView($r1);
        for (int $i0 = this.mRecoverAnimations.size() - 1; $i0 >= 0; $i0--) {
            RecoverAnimation $r3 = (RecoverAnimation) this.mRecoverAnimations.get($i0);
            if ($r3.mViewHolder.itemView == $r4) {
                return $r3;
            }
        }
        return null;
    }

    private void updateDxDy(MotionEvent $r1, int $i0, int $i1) throws  {
        float $f0 = MotionEventCompat.getX($r1, $i1);
        float $f1 = MotionEventCompat.getY($r1, $i1);
        this.mDx = $f0 - this.mInitialTouchX;
        this.mDy = $f1 - this.mInitialTouchY;
        if (($i0 & 4) == 0) {
            this.mDx = Math.max(0.0f, this.mDx);
        }
        if (($i0 & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
        }
        if (($i0 & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if (($i0 & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    private int swipeIfNecessary(ViewHolder $r1) throws  {
        if (this.mActionState == 2) {
            return 0;
        }
        int $i1 = this.mCallback.getMovementFlags(this.mRecyclerView, $r1);
        int $i0 = (this.mCallback.convertToAbsoluteDirection($i1, ViewCompat.getLayoutDirection(this.mRecyclerView)) & 65280) >> 8;
        if ($i0 == 0) {
            return 0;
        }
        $i1 = ($i1 & 65280) >> 8;
        int $i3;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            $i3 = checkHorizontalSwipe($r1, $i0);
            if ($i3 <= 0) {
                $i0 = checkVerticalSwipe($r1, $i0);
                if ($i0 > 0) {
                    return $i0;
                }
            } else if (($i1 & $i3) == 0) {
                return Callback.convertToRelativeDirection($i3, ViewCompat.getLayoutDirection(this.mRecyclerView));
            } else {
                return $i3;
            }
        }
        $i3 = checkVerticalSwipe($r1, $i0);
        if ($i3 > 0) {
            return $i3;
        }
        $i0 = checkHorizontalSwipe($r1, $i0);
        if ($i0 > 0) {
            return ($i1 & $i0) == 0 ? Callback.convertToRelativeDirection($i0, ViewCompat.getLayoutDirection(this.mRecyclerView)) : $i0;
        }
        return 0;
    }

    private int checkHorizontalSwipe(ViewHolder $r1, int $i0) throws  {
        if (($i0 & 12) != 0) {
            float $f0;
            byte $b2 = this.mDx > 0.0f ? (byte) 8 : (byte) 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                byte $b3;
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float $f1 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                $f0 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                if ($f1 > 0.0f) {
                    $b3 = (byte) 8;
                } else {
                    $b3 = (byte) 4;
                }
                $f1 = Math.abs($f1);
                if (($b3 & $i0) != 0 && $b2 == $b3 && $f1 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && $f1 > Math.abs($f0)) {
                    return $b3;
                }
            }
            $f0 = ((float) this.mRecyclerView.getWidth()) * this.mCallback.getSwipeThreshold($r1);
            if (($i0 & $b2) != 0 && Math.abs(this.mDx) > $f0) {
                return $b2;
            }
        }
        return 0;
    }

    private int checkVerticalSwipe(ViewHolder $r1, int $i0) throws  {
        if (($i0 & 3) != 0) {
            float $f0;
            byte $b2 = this.mDy > 0.0f ? (byte) 2 : (byte) 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                byte $b3;
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                $f0 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                float $f1 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                if ($f1 > 0.0f) {
                    $b3 = (byte) 2;
                } else {
                    $b3 = (byte) 1;
                }
                $f1 = Math.abs($f1);
                if (($b3 & $i0) != 0 && $b3 == $b2 && $f1 >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && $f1 > Math.abs($f0)) {
                    return $b3;
                }
            }
            $f0 = ((float) this.mRecyclerView.getHeight()) * this.mCallback.getSwipeThreshold($r1);
            if (($i0 & $b2) != 0 && Math.abs(this.mDy) > $f0) {
                return $b2;
            }
        }
        return 0;
    }

    private void addChildDrawingOrderCallback() throws  {
        if (VERSION.SDK_INT < 21) {
            if (this.mChildDrawingOrderCallback == null) {
                this.mChildDrawingOrderCallback = new C02855();
            }
            this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
        }
    }

    private void removeChildDrawingOrderCallbackIfNecessary(View $r1) throws  {
        if ($r1 == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }
}
