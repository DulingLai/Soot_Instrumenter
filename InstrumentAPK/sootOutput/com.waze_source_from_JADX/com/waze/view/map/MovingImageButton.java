package com.waze.view.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import com.waze.C1283R;

public class MovingImageButton extends ImageButton {
    public MovingImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void start() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), C1283R.anim.move_right_left));
    }

    public void stop() {
        clearAnimation();
    }
}
