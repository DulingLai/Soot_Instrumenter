package android.support.v4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar extends ProgressBar {
    private static final int MIN_DELAY = 500;
    private static final int MIN_SHOW_TIME = 500;
    private final Runnable mDelayedHide;
    private final Runnable mDelayedShow;
    private boolean mDismissed;
    private boolean mPostedHide;
    private boolean mPostedShow;
    private long mStartTime;

    class C01391 implements Runnable {
        C01391() throws  {
        }

        public void run() throws  {
            ContentLoadingProgressBar.this.mPostedHide = false;
            ContentLoadingProgressBar.this.mStartTime = -1;
            ContentLoadingProgressBar.this.setVisibility(8);
        }
    }

    class C01402 implements Runnable {
        C01402() throws  {
        }

        public void run() throws  {
            ContentLoadingProgressBar.this.mPostedShow = false;
            if (!ContentLoadingProgressBar.this.mDismissed) {
                ContentLoadingProgressBar.this.mStartTime = System.currentTimeMillis();
                ContentLoadingProgressBar.this.setVisibility(0);
            }
        }
    }

    public ContentLoadingProgressBar(Context $r1) throws  {
        this($r1, null);
    }

    public ContentLoadingProgressBar(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2, 0);
        this.mStartTime = -1;
        this.mPostedHide = false;
        this.mPostedShow = false;
        this.mDismissed = false;
        this.mDelayedHide = new C01391();
        this.mDelayedShow = new C01402();
    }

    public void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    public void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() throws  {
        removeCallbacks(this.mDelayedHide);
        removeCallbacks(this.mDelayedShow);
    }

    public void hide() throws  {
        this.mDismissed = true;
        removeCallbacks(this.mDelayedShow);
        long $l0 = System.currentTimeMillis() - this.mStartTime;
        if ($l0 >= 500 || this.mStartTime == -1) {
            setVisibility(8);
        } else if (!this.mPostedHide) {
            postDelayed(this.mDelayedHide, 500 - $l0);
            this.mPostedHide = true;
        }
    }

    public void show() throws  {
        this.mStartTime = -1;
        this.mDismissed = false;
        removeCallbacks(this.mDelayedHide);
        if (!this.mPostedShow) {
            postDelayed(this.mDelayedShow, 500);
            this.mPostedShow = true;
        }
    }
}
