package android.support.v4.view;

import android.view.View;

public interface ViewPropertyAnimatorListener {
    void onAnimationCancel(View view) throws ;

    void onAnimationEnd(View view) throws ;

    void onAnimationStart(View view) throws ;
}
