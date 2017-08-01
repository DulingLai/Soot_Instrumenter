package com.waze.ifs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    boolean mDisabled = false;
    boolean mDisallowIntercept = false;
    private OnScrollListener scrollViewListener = null;

    public interface OnScrollListener {
        void onScrollChanged(ObservableScrollView observableScrollView, int i, int i2, int i3, int i4) throws ;
    }

    public ObservableScrollView(Context $r1) throws  {
        super($r1);
    }

    public ObservableScrollView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public ObservableScrollView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public void setOnScrollListener(OnScrollListener $r1) throws  {
        this.scrollViewListener = $r1;
    }

    protected void onScrollChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onScrollChanged($i0, $i1, $i2, $i3);
        if (this.scrollViewListener != null) {
            this.scrollViewListener.onScrollChanged(this, $i0, $i1, $i2, $i3);
        }
    }

    public void requestDisallowInterceptTouchEventForce(boolean $z0) throws  {
        this.mDisallowIntercept = $z0;
        super.requestDisallowInterceptTouchEvent($z0);
    }

    public void setDisabled(boolean $z0) throws  {
        this.mDisabled = $z0;
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        if (this.mDisabled) {
            return false;
        }
        if (!this.mDisallowIntercept) {
            return super.onTouchEvent($r1);
        }
        this.mDisallowIntercept = false;
        return false;
    }
}
