package com.waze.planned_drive;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.waze.C1283R;
import com.waze.planned_drive.PlannedDriveActivity.OptionViewHolder;
import com.waze.utils.PixelMeasure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlannedDriveRecycler extends RecyclerView {
    private boolean mIsContentScrollingUp;
    private PlannedDriveLayoutManager mLayoutManager;
    private PlannedDriveRecyclerListener mListener;
    private int mSelectedPosition;
    private Runnable mSuddenStopScrollEvent;
    private PointF mTouchDownPosition;

    public interface PlannedDriveRecyclerListener {
        void onRecyclerScrollStop();
    }

    class C23461 implements Runnable {
        C23461() {
        }

        public void run() {
            if (PlannedDriveRecycler.this.getScrollState() != 2 && PlannedDriveGraphView.haltAllGraphAnimations && PlannedDriveRecycler.this.mLayoutManager.mLastScrollDelta >= PixelMeasure.dp(16)) {
                PlannedDriveGraphView.haltAllGraphAnimations = false;
                PlannedDriveRecycler.this.restoreGraphAnimations();
            }
        }
    }

    private class PlannedDriveLayoutManager extends LinearLayoutManager {
        private int mCurrentScrollY;
        private int mLastScrollDelta;

        class C23471 implements Comparator<View> {
            C23471() {
            }

            public int compare(View lhs, View rhs) {
                if (PlannedDriveLayoutManager.this.getDecoratedTop(lhs) < PlannedDriveLayoutManager.this.getDecoratedTop(rhs)) {
                    return -1;
                }
                if (PlannedDriveLayoutManager.this.getDecoratedTop(lhs) > PlannedDriveLayoutManager.this.getDecoratedTop(rhs)) {
                    return 1;
                }
                return 0;
            }
        }

        public PlannedDriveLayoutManager(Context context) {
            super(context);
        }

        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
            if (state == 0) {
                PlannedDriveRecycler.this.smoothScrollToPosition(PlannedDriveRecycler.this.mSelectedPosition);
                if (PlannedDriveRecycler.this.mListener != null) {
                    PlannedDriveRecycler.this.mListener.onRecyclerScrollStop();
                }
            }
            if (state != 2 && PlannedDriveGraphView.haltAllGraphAnimations && this.mLastScrollDelta < PixelMeasure.dp(8)) {
                PlannedDriveGraphView.haltAllGraphAnimations = false;
                PlannedDriveRecycler.this.restoreGraphAnimations();
            }
        }

        private List<View> getOrderedViews() {
            List<View> result = new ArrayList();
            for (int i = 0; i < getChildCount(); i++) {
                result.add(getChildAt(i));
            }
            Collections.sort(result, new C23471());
            return result;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(PlannedDriveRecycler.this.getContext()) {
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return new PointF(0.0f, (float) ((targetPosition * PixelMeasure.dimension(C1283R.dimen.planDriveCellHeight)) - PlannedDriveLayoutManager.this.mCurrentScrollY));
                }

                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 20.0f / ((float) PixelMeasure.dimension(C1283R.dimen.planDriveCellHeight));
                }
            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }

        public PointF computeScrollVectorForPosition(int targetPosition) {
            return super.computeScrollVectorForPosition(targetPosition);
        }

        public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
            boolean z;
            int result = super.scrollVerticallyBy(dy, recycler, state);
            PlannedDriveRecycler plannedDriveRecycler = PlannedDriveRecycler.this;
            if (dy > 0) {
                z = true;
            } else {
                z = false;
            }
            plannedDriveRecycler.mIsContentScrollingUp = z;
            int centerPosition = PlannedDriveRecycler.this.getMeasuredHeight() / 2;
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (centerPosition > getDecoratedTop(view) && centerPosition < getDecoratedBottom(view)) {
                    ((OptionViewHolder) PlannedDriveRecycler.this.getChildViewHolder(view)).showDetails();
                    PlannedDriveRecycler.this.mSelectedPosition = getPosition(view);
                    break;
                }
            }
            this.mLastScrollDelta = Math.abs(result);
            this.mCurrentScrollY += result;
            if (result == 0 && PlannedDriveRecycler.this.mListener != null) {
                PlannedDriveRecycler.this.mListener.onRecyclerScrollStop();
            }
            if (PlannedDriveGraphView.haltAllGraphAnimations && (findLastCompletelyVisibleItemPosition() == PlannedDriveRecycler.this.getAdapter().getItemCount() - 1 || findFirstCompletelyVisibleItemPosition() == 0 || this.mLastScrollDelta < PixelMeasure.dp(8))) {
                PlannedDriveGraphView.haltAllGraphAnimations = false;
                PlannedDriveRecycler.this.restoreGraphAnimations();
            }
            return result;
        }
    }

    public PlannedDriveRecycler(Context context) {
        this(context, null);
    }

    public PlannedDriveRecycler(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlannedDriveRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSuddenStopScrollEvent = new C23461();
        this.mTouchDownPosition = new PointF();
        init();
    }

    private void init() {
        this.mLayoutManager = new PlannedDriveLayoutManager(getContext());
        setLayoutManager(this.mLayoutManager);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0 && PlannedDriveGraphView.haltAllGraphAnimations) {
            this.mTouchDownPosition.set(ev.getX(), ev.getY());
            postDelayed(this.mSuddenStopScrollEvent, 50);
        } else if (ev.getAction() == 2 && ((float) Math.sqrt((double) (((this.mTouchDownPosition.x - ev.getX()) * (this.mTouchDownPosition.x - ev.getX())) + ((this.mTouchDownPosition.y - ev.getY()) * (this.mTouchDownPosition.y - ev.getY()))))) > ((float) PixelMeasure.dp(4))) {
            removeCallbacks(this.mSuddenStopScrollEvent);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setListener(PlannedDriveRecyclerListener listener) {
        this.mListener = listener;
    }

    public boolean isAnyGraphAnimating() {
        for (int i = 0; i < getChildCount(); i++) {
            if (((OptionViewHolder) getChildViewHolder(getChildAt(i))).isGraphAnimating()) {
                return true;
            }
        }
        return false;
    }

    private void restoreGraphAnimations() {
        long now = System.currentTimeMillis();
        PlannedDriveActivity.nextGraphRevealTime = now;
        boolean animateFromTop = this.mIsContentScrollingUp;
        List<View> orderedChildList = this.mLayoutManager.getOrderedViews();
        int i = 0;
        while (i < orderedChildList.size()) {
            ((OptionViewHolder) getChildViewHolder((View) orderedChildList.get(animateFromTop ? i : (orderedChildList.size() - 1) - i))).animateGraph(animateFromTop, PlannedDriveActivity.nextGraphRevealTime - now);
            PlannedDriveActivity.nextGraphRevealTime += 100;
            i++;
        }
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public void cancelGraphAnimations() {
        for (int i = 0; i < getChildCount(); i++) {
            ((OptionViewHolder) getChildViewHolder(getChildAt(i))).cancelPendingGraphAnimation();
        }
    }
}
