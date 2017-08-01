package android.support.v7.view;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewPropertyAnimatorCompatSet {
    private final ArrayList<ViewPropertyAnimatorCompat> mAnimators = new ArrayList();
    private long mDuration = -1;
    private Interpolator mInterpolator;
    private boolean mIsStarted;
    private ViewPropertyAnimatorListener mListener;
    private final ViewPropertyAnimatorListenerAdapter mProxyListener = new C02021();

    class C02021 extends ViewPropertyAnimatorListenerAdapter {
        private int mProxyEndCount = 0;
        private boolean mProxyStarted = false;

        C02021() throws  {
        }

        public void onAnimationStart(View view) throws  {
            if (!this.mProxyStarted) {
                this.mProxyStarted = true;
                if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                    ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null);
                }
            }
        }

        void onEnd() throws  {
            this.mProxyEndCount = 0;
            this.mProxyStarted = false;
            ViewPropertyAnimatorCompatSet.this.onAnimationsEnded();
        }

        public void onAnimationEnd(View view) throws  {
            int $i0 = this.mProxyEndCount + 1;
            this.mProxyEndCount = $i0;
            if ($i0 == ViewPropertyAnimatorCompatSet.this.mAnimators.size()) {
                if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                    ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null);
                }
                onEnd();
            }
        }
    }

    public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat $r1) throws  {
        if (this.mIsStarted) {
            return this;
        }
        this.mAnimators.add($r1);
        return this;
    }

    public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat $r1, ViewPropertyAnimatorCompat $r2) throws  {
        this.mAnimators.add($r1);
        $r2.setStartDelay($r1.getDuration());
        this.mAnimators.add($r2);
        return this;
    }

    public void start() throws  {
        if (!this.mIsStarted) {
            Iterator $r2 = this.mAnimators.iterator();
            while ($r2.hasNext()) {
                ViewPropertyAnimatorCompat $r4 = (ViewPropertyAnimatorCompat) $r2.next();
                if (this.mDuration >= 0) {
                    $r4.setDuration(this.mDuration);
                }
                if (this.mInterpolator != null) {
                    $r4.setInterpolator(this.mInterpolator);
                }
                if (this.mListener != null) {
                    $r4.setListener(this.mProxyListener);
                }
                $r4.start();
            }
            this.mIsStarted = true;
        }
    }

    private void onAnimationsEnded() throws  {
        this.mIsStarted = false;
    }

    public void cancel() throws  {
        if (this.mIsStarted) {
            Iterator $r2 = this.mAnimators.iterator();
            while ($r2.hasNext()) {
                ((ViewPropertyAnimatorCompat) $r2.next()).cancel();
            }
            this.mIsStarted = false;
        }
    }

    public ViewPropertyAnimatorCompatSet setDuration(long $l0) throws  {
        if (this.mIsStarted) {
            return this;
        }
        this.mDuration = $l0;
        return this;
    }

    public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator $r1) throws  {
        if (this.mIsStarted) {
            return this;
        }
        this.mInterpolator = $r1;
        return this;
    }

    public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener $r1) throws  {
        if (this.mIsStarted) {
            return this;
        }
        this.mListener = $r1;
        return this;
    }
}
