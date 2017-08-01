package com.waze.view.listitems;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class ListItemSnapScrollView extends HorizontalScrollView {
    private boolean mIsTouchDown;
    private boolean mScrollAboutToStart;
    private boolean mScrollEnabled;
    private boolean mScrollEndedInvoked;
    private ListItemSnapScrollerListener mScrollListener;
    private int mScrollSpeed;

    public interface ListItemSnapScrollerListener {
        void onScrollChanged(int i);

        void onScrollEnded(int i);

        void onScrollStarted();
    }

    public ListItemSnapScrollView(Context context) {
        this(context, null);
    }

    public ListItemSnapScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListItemSnapScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        this.mScrollSpeed = Math.abs(l - oldl);
        if (this.mScrollAboutToStart) {
            this.mScrollAboutToStart = false;
            if (this.mScrollListener != null) {
                this.mScrollListener.onScrollStarted();
            }
        }
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollChanged(l);
        }
        if (this.mScrollSpeed > 1) {
            this.mScrollEndedInvoked = false;
        }
        if (this.mScrollSpeed <= 1 && !this.mScrollEndedInvoked && !this.mIsTouchDown && this.mScrollListener != null) {
            this.mScrollListener.onScrollEnded(l);
            this.mScrollEndedInvoked = true;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z = false;
        if (!this.mScrollEnabled) {
            return false;
        }
        if (ev.getAction() == 0 || ev.getAction() == 2) {
            z = true;
        }
        this.mIsTouchDown = z;
        if (ev.getAction() == 0) {
            this.mScrollAboutToStart = true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mScrollEnabled) {
            return false;
        }
        boolean z = ev.getAction() == 0 || ev.getAction() == 2;
        this.mIsTouchDown = z;
        if (ev.getAction() == 0) {
            this.mScrollAboutToStart = true;
        }
        if ((ev.getAction() == 1 || ev.getAction() == 3) && this.mScrollSpeed < 10) {
            this.mScrollEndedInvoked = false;
            onScrollChanged(getScrollX(), 0, getScrollX() + 1, 0);
        }
        return super.onTouchEvent(ev);
    }

    public boolean isUserInteracting() {
        return this.mIsTouchDown;
    }

    public void setScrollListener(ListItemSnapScrollerListener scrollListener) {
        this.mScrollListener = scrollListener;
    }
}
