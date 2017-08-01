package com.waze.view.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import com.waze.C1283R;
import com.waze.map.CanvasFont;

public class OneStarRatingView extends FrameLayout {
    public static final int DURATION_MILLIS = 300;
    private final View fullStarView = findViewById(C1283R.id.star);

    class C29591 implements AnimationListener {
        C29591() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            ScaleAnimation animation2 = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            animation2.setInterpolator(new LinearInterpolator());
            animation2.setDuration(150);
            OneStarRatingView.this.fullStarView.startAnimation(animation2);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public OneStarRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(C1283R.layout.one_star_rating, this, true);
    }

    public void animateOut() {
        ScaleAnimation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(300);
        animation.setFillAfter(true);
        this.fullStarView.startAnimation(animation);
    }

    public void animateIn(long offset) {
        this.fullStarView.setVisibility(0);
        ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setStartOffset(offset);
        animation.setDuration(300);
        animation.setFillAfter(true);
        this.fullStarView.startAnimation(animation);
    }

    public void animateSame() {
        ScaleAnimation animation1 = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        animation1.setInterpolator(new LinearInterpolator());
        animation1.setDuration(150);
        animation1.setAnimationListener(new C29591());
        this.fullStarView.startAnimation(animation1);
    }
}
