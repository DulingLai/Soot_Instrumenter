package android.support.v4.animation;

public interface AnimatorListenerCompat {
    void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat) throws ;

    void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) throws ;

    void onAnimationRepeat(ValueAnimatorCompat valueAnimatorCompat) throws ;

    void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat) throws ;
}
