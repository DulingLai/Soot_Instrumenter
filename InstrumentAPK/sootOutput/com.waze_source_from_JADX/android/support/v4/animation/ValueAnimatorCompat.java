package android.support.v4.animation;

import android.view.View;

public interface ValueAnimatorCompat {
    void addListener(AnimatorListenerCompat animatorListenerCompat) throws ;

    void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListenerCompat) throws ;

    void cancel() throws ;

    float getAnimatedFraction() throws ;

    void setDuration(long j) throws ;

    void setTarget(View view) throws ;

    void start() throws ;
}
