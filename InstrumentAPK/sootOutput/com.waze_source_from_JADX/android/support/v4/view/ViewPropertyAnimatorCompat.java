package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class ViewPropertyAnimatorCompat {
    static final ViewPropertyAnimatorCompatImpl IMPL;
    static final int LISTENER_TAG_ID = 2113929216;
    private static final String TAG = "ViewAnimatorCompat";
    private Runnable mEndAction = null;
    private int mOldLayerType = -1;
    private Runnable mStartAction = null;
    private WeakReference<View> mView;

    interface ViewPropertyAnimatorCompatImpl {
        void alpha(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void alphaBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void cancel(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) throws ;

        long getDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) throws ;

        Interpolator getInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) throws ;

        long getStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) throws ;

        void rotation(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void rotationBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void rotationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void rotationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void rotationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void rotationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void scaleX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void scaleXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void scaleY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void scaleYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void setDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j) throws ;

        void setInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Interpolator interpolator) throws ;

        void setListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) throws ;

        void setStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j) throws ;

        void setUpdateListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) throws ;

        void start(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) throws ;

        void translationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void translationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void translationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void translationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void translationZ(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void translationZBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void withEndAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) throws ;

        void withLayer(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) throws ;

        void withStartAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) throws ;

        void mo763x(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void xBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void mo765y(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void yBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void mo767z(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;

        void zBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) throws ;
    }

    static class BaseViewPropertyAnimatorCompatImpl implements ViewPropertyAnimatorCompatImpl {
        WeakHashMap<View, Runnable> mStarterMap = null;

        class Starter implements Runnable {
            WeakReference<View> mViewRef;
            ViewPropertyAnimatorCompat mVpa;

            private Starter(ViewPropertyAnimatorCompat $r2, View $r3) throws  {
                this.mViewRef = new WeakReference($r3);
                this.mVpa = $r2;
            }

            public void run() throws  {
                View $r4 = (View) this.mViewRef.get();
                if ($r4 != null) {
                    BaseViewPropertyAnimatorCompatImpl.this.startAnimation(this.mVpa, $r4);
                }
            }
        }

        public long getDuration(ViewPropertyAnimatorCompat vpa, View view) throws  {
            return 0;
        }

        public Interpolator getInterpolator(ViewPropertyAnimatorCompat vpa, View view) throws  {
            return null;
        }

        public long getStartDelay(ViewPropertyAnimatorCompat vpa, View view) throws  {
            return 0;
        }

        BaseViewPropertyAnimatorCompatImpl() throws  {
        }

        public void setDuration(ViewPropertyAnimatorCompat vpa, View view, long value) throws  {
        }

        public void alpha(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void translationX(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void translationY(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void withEndAction(ViewPropertyAnimatorCompat $r1, View $r2, Runnable $r3) throws  {
            $r1.mEndAction = $r3;
            postStartMessage($r1, $r2);
        }

        public void setInterpolator(ViewPropertyAnimatorCompat vpa, View view, Interpolator value) throws  {
        }

        public void setStartDelay(ViewPropertyAnimatorCompat vpa, View view, long value) throws  {
        }

        public void alphaBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void rotation(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void rotationBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void rotationX(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void rotationXBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void rotationY(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void rotationYBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void scaleX(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void scaleXBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void scaleY(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void scaleYBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void cancel(ViewPropertyAnimatorCompat $r1, View $r2) throws  {
            postStartMessage($r1, $r2);
        }

        public void mo763x(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void xBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void mo765y(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void yBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void mo767z(ViewPropertyAnimatorCompat vpa, View view, float value) throws  {
        }

        public void zBy(ViewPropertyAnimatorCompat vpa, View view, float value) throws  {
        }

        public void translationXBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void translationYBy(ViewPropertyAnimatorCompat $r1, View $r2, float value) throws  {
            postStartMessage($r1, $r2);
        }

        public void translationZ(ViewPropertyAnimatorCompat vpa, View view, float value) throws  {
        }

        public void translationZBy(ViewPropertyAnimatorCompat vpa, View view, float value) throws  {
        }

        public void start(ViewPropertyAnimatorCompat $r1, View $r2) throws  {
            removeStartMessage($r2);
            startAnimation($r1, $r2);
        }

        public void withLayer(ViewPropertyAnimatorCompat vpa, View view) throws  {
        }

        public void withStartAction(ViewPropertyAnimatorCompat $r1, View $r2, Runnable $r3) throws  {
            $r1.mStartAction = $r3;
            postStartMessage($r1, $r2);
        }

        public void setListener(ViewPropertyAnimatorCompat vpa, View $r2, ViewPropertyAnimatorListener $r3) throws  {
            $r2.setTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID, $r3);
        }

        public void setUpdateListener(ViewPropertyAnimatorCompat vpa, View view, ViewPropertyAnimatorUpdateListener listener) throws  {
        }

        private void startAnimation(ViewPropertyAnimatorCompat $r1, View $r2) throws  {
            Object $r3 = $r2.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener $r4 = null;
            if ($r3 instanceof ViewPropertyAnimatorListener) {
                $r4 = (ViewPropertyAnimatorListener) $r3;
            }
            Runnable $r5 = $r1.mStartAction;
            Runnable $r6 = $r1.mEndAction;
            $r1.mStartAction = null;
            $r1.mEndAction = null;
            if ($r5 != null) {
                $r5.run();
            }
            if ($r4 != null) {
                $r4.onAnimationStart($r2);
                $r4.onAnimationEnd($r2);
            }
            if ($r6 != null) {
                $r6.run();
            }
            if (this.mStarterMap != null) {
                this.mStarterMap.remove($r2);
            }
        }

        private void removeStartMessage(View $r1) throws  {
            if (this.mStarterMap != null) {
                Runnable $r4 = (Runnable) this.mStarterMap.get($r1);
                if ($r4 != null) {
                    $r1.removeCallbacks($r4);
                }
            }
        }

        private void postStartMessage(ViewPropertyAnimatorCompat $r1, View $r2) throws  {
            Runnable $r4 = null;
            if (this.mStarterMap != null) {
                $r4 = (Runnable) this.mStarterMap.get($r2);
            }
            if ($r4 == null) {
                $r4 = r6;
                Starter r6 = new Starter($r1, $r2);
                if (this.mStarterMap == null) {
                    this.mStarterMap = new WeakHashMap();
                }
                this.mStarterMap.put($r2, $r4);
            }
            $r2.removeCallbacks($r4);
            $r2.post($r4);
        }
    }

    static class ICSViewPropertyAnimatorCompatImpl extends BaseViewPropertyAnimatorCompatImpl {
        WeakHashMap<View, Integer> mLayerMap = null;

        static class MyVpaListener implements ViewPropertyAnimatorListener {
            boolean mAnimEndCalled;
            ViewPropertyAnimatorCompat mVpa;

            MyVpaListener(ViewPropertyAnimatorCompat $r1) throws  {
                this.mVpa = $r1;
            }

            public void onAnimationStart(View $r1) throws  {
                this.mAnimEndCalled = false;
                if (this.mVpa.mOldLayerType >= 0) {
                    ViewCompat.setLayerType($r1, 2, null);
                }
                if (this.mVpa.mStartAction != null) {
                    Runnable $r3 = this.mVpa.mStartAction;
                    this.mVpa.mStartAction = null;
                    $r3.run();
                }
                Object $r4 = $r1.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                ViewPropertyAnimatorListener $r5 = null;
                if ($r4 instanceof ViewPropertyAnimatorListener) {
                    $r5 = (ViewPropertyAnimatorListener) $r4;
                }
                if ($r5 != null) {
                    $r5.onAnimationStart($r1);
                }
            }

            public void onAnimationEnd(View $r1) throws  {
                if (this.mVpa.mOldLayerType >= 0) {
                    ViewCompat.setLayerType($r1, this.mVpa.mOldLayerType, null);
                    this.mVpa.mOldLayerType = -1;
                }
                if (VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
                    if (this.mVpa.mEndAction != null) {
                        Runnable $r3 = this.mVpa.mEndAction;
                        this.mVpa.mEndAction = null;
                        $r3.run();
                    }
                    Object $r4 = $r1.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                    ViewPropertyAnimatorListener $r5 = null;
                    if ($r4 instanceof ViewPropertyAnimatorListener) {
                        $r5 = (ViewPropertyAnimatorListener) $r4;
                    }
                    if ($r5 != null) {
                        $r5.onAnimationEnd($r1);
                    }
                    this.mAnimEndCalled = true;
                }
            }

            public void onAnimationCancel(View $r1) throws  {
                Object $r2 = $r1.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                ViewPropertyAnimatorListener $r3 = null;
                if ($r2 instanceof ViewPropertyAnimatorListener) {
                    $r3 = (ViewPropertyAnimatorListener) $r2;
                }
                if ($r3 != null) {
                    $r3.onAnimationCancel($r1);
                }
            }
        }

        ICSViewPropertyAnimatorCompatImpl() throws  {
        }

        public void setDuration(ViewPropertyAnimatorCompat vpa, View $r2, long $l0) throws  {
            ViewPropertyAnimatorCompatICS.setDuration($r2, $l0);
        }

        public void alpha(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.alpha($r2, $f0);
        }

        public void translationX(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.translationX($r2, $f0);
        }

        public void translationY(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.translationY($r2, $f0);
        }

        public long getDuration(ViewPropertyAnimatorCompat vpa, View $r2) throws  {
            return ViewPropertyAnimatorCompatICS.getDuration($r2);
        }

        public void setInterpolator(ViewPropertyAnimatorCompat vpa, View $r2, Interpolator $r3) throws  {
            ViewPropertyAnimatorCompatICS.setInterpolator($r2, $r3);
        }

        public void setStartDelay(ViewPropertyAnimatorCompat vpa, View $r2, long $l0) throws  {
            ViewPropertyAnimatorCompatICS.setStartDelay($r2, $l0);
        }

        public long getStartDelay(ViewPropertyAnimatorCompat vpa, View $r2) throws  {
            return ViewPropertyAnimatorCompatICS.getStartDelay($r2);
        }

        public void alphaBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.alphaBy($r2, $f0);
        }

        public void rotation(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.rotation($r2, $f0);
        }

        public void rotationBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.rotationBy($r2, $f0);
        }

        public void rotationX(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.rotationX($r2, $f0);
        }

        public void rotationXBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.rotationXBy($r2, $f0);
        }

        public void rotationY(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.rotationY($r2, $f0);
        }

        public void rotationYBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.rotationYBy($r2, $f0);
        }

        public void scaleX(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.scaleX($r2, $f0);
        }

        public void scaleXBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.scaleXBy($r2, $f0);
        }

        public void scaleY(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.scaleY($r2, $f0);
        }

        public void scaleYBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.scaleYBy($r2, $f0);
        }

        public void cancel(ViewPropertyAnimatorCompat vpa, View $r2) throws  {
            ViewPropertyAnimatorCompatICS.cancel($r2);
        }

        public void mo763x(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.m12x($r2, $f0);
        }

        public void xBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.xBy($r2, $f0);
        }

        public void mo765y(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.m13y($r2, $f0);
        }

        public void yBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.yBy($r2, $f0);
        }

        public void translationXBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.translationXBy($r2, $f0);
        }

        public void translationYBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatICS.translationYBy($r2, $f0);
        }

        public void start(ViewPropertyAnimatorCompat vpa, View $r2) throws  {
            ViewPropertyAnimatorCompatICS.start($r2);
        }

        public void setListener(ViewPropertyAnimatorCompat $r1, View $r2, ViewPropertyAnimatorListener $r3) throws  {
            $r2.setTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID, $r3);
            ViewPropertyAnimatorCompatICS.setListener($r2, new MyVpaListener($r1));
        }

        public void withEndAction(ViewPropertyAnimatorCompat $r1, View $r2, Runnable $r3) throws  {
            ViewPropertyAnimatorCompatICS.setListener($r2, new MyVpaListener($r1));
            $r1.mEndAction = $r3;
        }

        public void withStartAction(ViewPropertyAnimatorCompat $r1, View $r2, Runnable $r3) throws  {
            ViewPropertyAnimatorCompatICS.setListener($r2, new MyVpaListener($r1));
            $r1.mStartAction = $r3;
        }

        public void withLayer(ViewPropertyAnimatorCompat $r1, View $r2) throws  {
            $r1.mOldLayerType = ViewCompat.getLayerType($r2);
            ViewPropertyAnimatorCompatICS.setListener($r2, new MyVpaListener($r1));
        }
    }

    static class JBViewPropertyAnimatorCompatImpl extends ICSViewPropertyAnimatorCompatImpl {
        JBViewPropertyAnimatorCompatImpl() throws  {
        }

        public void setListener(ViewPropertyAnimatorCompat vpa, View $r2, ViewPropertyAnimatorListener $r3) throws  {
            ViewPropertyAnimatorCompatJB.setListener($r2, $r3);
        }

        public void withStartAction(ViewPropertyAnimatorCompat vpa, View $r2, Runnable $r3) throws  {
            ViewPropertyAnimatorCompatJB.withStartAction($r2, $r3);
        }

        public void withEndAction(ViewPropertyAnimatorCompat vpa, View $r2, Runnable $r3) throws  {
            ViewPropertyAnimatorCompatJB.withEndAction($r2, $r3);
        }

        public void withLayer(ViewPropertyAnimatorCompat vpa, View $r2) throws  {
            ViewPropertyAnimatorCompatJB.withLayer($r2);
        }
    }

    static class JBMr2ViewPropertyAnimatorCompatImpl extends JBViewPropertyAnimatorCompatImpl {
        JBMr2ViewPropertyAnimatorCompatImpl() throws  {
        }

        public Interpolator getInterpolator(ViewPropertyAnimatorCompat vpa, View $r2) throws  {
            return ViewPropertyAnimatorCompatJellybeanMr2.getInterpolator($r2);
        }
    }

    static class KitKatViewPropertyAnimatorCompatImpl extends JBMr2ViewPropertyAnimatorCompatImpl {
        KitKatViewPropertyAnimatorCompatImpl() throws  {
        }

        public void setUpdateListener(ViewPropertyAnimatorCompat vpa, View $r2, ViewPropertyAnimatorUpdateListener $r3) throws  {
            ViewPropertyAnimatorCompatKK.setUpdateListener($r2, $r3);
        }
    }

    static class LollipopViewPropertyAnimatorCompatImpl extends KitKatViewPropertyAnimatorCompatImpl {
        LollipopViewPropertyAnimatorCompatImpl() throws  {
        }

        public void translationZ(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatLollipop.translationZ($r2, $f0);
        }

        public void translationZBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatLollipop.translationZBy($r2, $f0);
        }

        public void mo767z(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatLollipop.m14z($r2, $f0);
        }

        public void zBy(ViewPropertyAnimatorCompat vpa, View $r2, float $f0) throws  {
            ViewPropertyAnimatorCompatLollipop.zBy($r2, $f0);
        }
    }

    ViewPropertyAnimatorCompat(View $r1) throws  {
        this.mView = new WeakReference($r1);
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 21) {
            IMPL = new LollipopViewPropertyAnimatorCompatImpl();
        } else if ($i0 >= 19) {
            IMPL = new KitKatViewPropertyAnimatorCompatImpl();
        } else if ($i0 >= 18) {
            IMPL = new JBMr2ViewPropertyAnimatorCompatImpl();
        } else if ($i0 >= 16) {
            IMPL = new JBViewPropertyAnimatorCompatImpl();
        } else if ($i0 >= 14) {
            IMPL = new ICSViewPropertyAnimatorCompatImpl();
        } else {
            IMPL = new BaseViewPropertyAnimatorCompatImpl();
        }
    }

    public ViewPropertyAnimatorCompat setDuration(long $l0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.setDuration(this, $r3, $l0);
        return this;
    }

    public ViewPropertyAnimatorCompat alpha(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.alpha(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.alphaBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat translationX(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.translationX(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.translationY(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable $r1) throws  {
        View $r4 = (View) this.mView.get();
        if ($r4 == null) {
            return this;
        }
        IMPL.withEndAction(this, $r4, $r1);
        return this;
    }

    public long getDuration() throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 != null) {
            return IMPL.getDuration(this, $r3);
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator $r1) throws  {
        View $r4 = (View) this.mView.get();
        if ($r4 == null) {
            return this;
        }
        IMPL.setInterpolator(this, $r4, $r1);
        return this;
    }

    public Interpolator getInterpolator() throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 != null) {
            return IMPL.getInterpolator(this, $r3);
        }
        return null;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long $l0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.setStartDelay(this, $r3, $l0);
        return this;
    }

    public long getStartDelay() throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 != null) {
            return IMPL.getStartDelay(this, $r3);
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat rotation(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.rotation(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.rotationBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.rotationX(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.rotationXBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.rotationY(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.rotationYBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.scaleX(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.scaleXBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.scaleY(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.scaleYBy(this, $r3, $f0);
        return this;
    }

    public void cancel() throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 != null) {
            IMPL.cancel(this, $r3);
        }
    }

    public ViewPropertyAnimatorCompat m9x(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.mo763x(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.xBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat m10y(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.mo765y(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.yBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.translationXBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.translationYBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.translationZBy(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.translationZ(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat m11z(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.mo767z(this, $r3, $f0);
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float $f0) throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.zBy(this, $r3, $f0);
        return this;
    }

    public void start() throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 != null) {
            IMPL.start(this, $r3);
        }
    }

    public ViewPropertyAnimatorCompat withLayer() throws  {
        View $r3 = (View) this.mView.get();
        if ($r3 == null) {
            return this;
        }
        IMPL.withLayer(this, $r3);
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable $r1) throws  {
        View $r4 = (View) this.mView.get();
        if ($r4 == null) {
            return this;
        }
        IMPL.withStartAction(this, $r4, $r1);
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener $r1) throws  {
        View $r4 = (View) this.mView.get();
        if ($r4 == null) {
            return this;
        }
        IMPL.setListener(this, $r4, $r1);
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener $r1) throws  {
        View $r4 = (View) this.mView.get();
        if ($r4 == null) {
            return this;
        }
        IMPL.setUpdateListener(this, $r4, $r1);
        return this;
    }
}
