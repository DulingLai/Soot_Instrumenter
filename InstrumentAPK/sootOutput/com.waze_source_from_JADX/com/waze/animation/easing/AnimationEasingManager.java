package com.waze.animation.easing;

import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import dalvik.annotation.Signature;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class AnimationEasingManager {
    static final int FPS = 60;
    static final int FRAME_TIME = 16;
    static final Handler mHandler = new Handler();
    long mBase;
    int mDuration;
    AnimationEasing mEasing;
    EasingCallback mEasingCallback;
    double mEndValue;
    boolean mInverted;
    Method mMethod;
    boolean mRunning;
    double mStartValue;
    Ticker mTicker;
    String mToken = String.valueOf(System.currentTimeMillis());
    double mValue;

    public interface EasingCallback {
        void onFinished(double d) throws ;

        void onStarted(double d) throws ;

        void onValueChanged(double d, double d2) throws ;
    }

    public enum EaseType {
        EaseIn,
        EaseOut,
        EaseInOut,
        EaseNone
    }

    public static class EasingInterpolator implements Interpolator {
        AnimationEasing mEasing;
        Method mMethod;

        public EasingInterpolator(AnimationEasing $r1, Method $r2) throws  {
            this.mMethod = $r2;
            this.mEasing = $r1;
        }

        public float getInterpolation(float $f0) throws  {
            double $d0 = 0.0d;
            Method $r2 = this.mMethod;
            AnimationEasing $r1 = this.mEasing;
            Object[] $r3 = new Object[4];
            try {
                $r3[0] = Float.valueOf($f0);
                $r3[1] = Integer.valueOf(0);
                $r3[2] = Integer.valueOf(1);
                $r3[3] = Integer.valueOf(1);
                $d0 = ((Double) $r2.invoke($r1, $r3)).doubleValue();
            } catch (IllegalAccessException $r8) {
                $r8.printStackTrace();
            } catch (IllegalArgumentException $r9) {
                $r9.printStackTrace();
            } catch (InvocationTargetException $r10) {
                $r10.printStackTrace();
            }
            return (float) $d0;
        }
    }

    class Ticker implements Runnable {
        Ticker() throws  {
        }

        public void run() throws  {
            long $l0 = AnimationEasingManager.this.mBase;
            long $l1 = SystemClock.uptimeMillis() - $l0;
            double $d0 = AnimationEasingManager.this.mValue;
            Method $r2 = AnimationEasingManager.this.mMethod;
            AnimationEasing $r3 = AnimationEasingManager.this.mEasing;
            Object[] $r4 = new Object[4];
            try {
                $r4[0] = Long.valueOf($l1);
                double $d1 = AnimationEasingManager.this.mStartValue;
                $r4[1] = Double.valueOf($d1);
                $d1 = AnimationEasingManager.this.mEndValue;
                $r4[2] = Double.valueOf($d1);
                int $i2 = AnimationEasingManager.this.mDuration;
                $r4[3] = Integer.valueOf($i2);
                double $d12 = ((Double) $r2.invoke($r3, $r4)).doubleValue();
                double d = $d12;
                AnimationEasingManager.this.mValue = $d12;
                long $l3 = (long) ((((int) ($l1 / 16)) + 1) * 16);
                $l0 += $l3;
                $l3 = AnimationEasingManager.this.mDuration;
                int $i22 = $l3;
                if ($l1 < ((long) $l3)) {
                    EasingCallback $r9 = AnimationEasingManager.this.mEasingCallback;
                    if (AnimationEasingManager.this.mInverted) {
                        $d1 = AnimationEasingManager.this.mEndValue;
                        d = $d1 - $d12;
                    }
                    $r9.onValueChanged(d, $d0);
                    AnimationEasingManager.mHandler.postAtTime(this, AnimationEasingManager.this.mToken, $l0);
                    return;
                }
                AnimationEasingManager.this.mEasingCallback.onFinished(AnimationEasingManager.this.mInverted ? AnimationEasingManager.this.mEndValue : AnimationEasingManager.this.mStartValue);
                AnimationEasingManager.this.mRunning = false;
            } catch (IllegalArgumentException $r12) {
                $r12.printStackTrace();
            } catch (IllegalAccessException $r13) {
                $r13.printStackTrace();
            } catch (InvocationTargetException $r14) {
                $r14.printStackTrace();
            }
        }
    }

    class TickerStart implements Runnable {
        double mValue;

        public TickerStart(double $d0) throws  {
            this.mValue = $d0;
        }

        public void run() throws  {
            AnimationEasingManager.this.mEasingCallback.onStarted(this.mValue);
        }
    }

    public AnimationEasingManager(EasingCallback $r1) throws  {
        this.mEasingCallback = $r1;
    }

    public void start(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDI)V"}) Class<? extends AnimationEasing> $r1, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDI)V"}) EaseType $r2, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDI)V"}) double $d0, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDI)V"}) double $d1, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDI)V"}) int $i0) throws  {
        start($r1, $r2, $d0, $d1, $i0, 0);
    }

    public void start(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDIJ)V"}) Class<? extends AnimationEasing> $r1, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDIJ)V"}) EaseType $r2, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDIJ)V"}) double $d0, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDIJ)V"}) double $d1, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDIJ)V"}) int $i0, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", "DDIJ)V"}) long $l1) throws  {
        if (!this.mRunning) {
            this.mEasing = createInstance($r1);
            if (this.mEasing != null) {
                this.mMethod = getEasingMethod(this.mEasing, $r2);
                if (this.mMethod != null) {
                    this.mInverted = $d0 > $d1;
                    if (this.mInverted) {
                        this.mStartValue = $d1;
                        this.mEndValue = $d0;
                    } else {
                        this.mStartValue = $d0;
                        this.mEndValue = $d1;
                    }
                    double d = this.mStartValue;
                    $d1 = d;
                    this.mValue = d;
                    this.mDuration = $i0;
                    this.mBase = SystemClock.uptimeMillis() + $l1;
                    this.mRunning = true;
                    this.mTicker = new Ticker();
                    long $l2 = (SystemClock.uptimeMillis() + 16) + $l1;
                    if ($l1 == 0) {
                        EasingCallback easingCallback = this.mEasingCallback;
                        EasingCallback $r6 = easingCallback;
                        easingCallback.onStarted($d0);
                    } else {
                        mHandler.postAtTime(new TickerStart($d0), this.mToken, $l2 - 16);
                    }
                    mHandler.postAtTime(this.mTicker, this.mToken, $l2);
                }
            }
        }
    }

    public void stop() throws  {
        this.mRunning = false;
        mHandler.removeCallbacks(this.mTicker, this.mToken);
    }

    static AnimationEasing createInstance(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;)", "Lcom/waze/animation/easing/AnimationEasing;"}) Class<? extends AnimationEasing> $r0) throws  {
        try {
            return (AnimationEasing) $r0.newInstance();
        } catch (IllegalAccessException $r3) {
            $r3.printStackTrace();
            return null;
        } catch (InstantiationException $r4) {
            $r4.printStackTrace();
            return null;
        }
    }

    static Method getEasingMethod(AnimationEasing $r0, EaseType $r1) throws  {
        String $r2 = getMethodName($r1);
        if ($r2 == null) {
            return null;
        }
        try {
            Class $r3 = $r0.getClass();
            Class[] $r4 = new Class[4];
            $r4[0] = Double.TYPE;
            $r4[1] = Double.TYPE;
            $r4[2] = Double.TYPE;
            $r4[3] = Double.TYPE;
            return $r3.getMethod($r2, $r4);
        } catch (SecurityException $r7) {
            $r7.printStackTrace();
            return null;
        } catch (NoSuchMethodException $r8) {
            $r8.printStackTrace();
            return null;
        }
    }

    static String getMethodName(EaseType $r0) throws  {
        switch ($r0) {
            case EaseIn:
                return "easeIn";
            case EaseInOut:
                return "easeInOut";
            case EaseNone:
                return "easeNone";
            case EaseOut:
                return "easeOut";
            default:
                return null;
        }
    }

    public static Interpolator getInterpolator(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", ")", "Landroid/view/animation/Interpolator;"}) Class<? extends AnimationEasing> $r0, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/waze/animation/easing/AnimationEasing;", ">;", "Lcom/waze/animation/easing/AnimationEasingManager$EaseType;", ")", "Landroid/view/animation/Interpolator;"}) EaseType $r1) throws  {
        AnimationEasing $r3 = createInstance($r0);
        if ($r3 == null) {
            return null;
        }
        Method $r4 = getEasingMethod($r3, $r1);
        return $r4 != null ? new EasingInterpolator($r3, $r4) : null;
    }

    public static Interpolator getElasticInterpolator(EaseType $r0, double $d0, double $d1) throws  {
        Elastic $r1 = new Elastic($d0, $d1);
        Method $r2 = getEasingMethod($r1, $r0);
        return $r2 == null ? null : new EasingInterpolator($r1, $r2);
    }
}
