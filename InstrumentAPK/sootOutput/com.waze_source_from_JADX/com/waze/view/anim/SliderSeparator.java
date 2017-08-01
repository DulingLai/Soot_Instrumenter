package com.waze.view.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.waze.C1283R;
import com.waze.utils.PixelMeasure;

public class SliderSeparator extends View {
    private static final int TIMER_TICK = 25;
    private ObjectAnimator _animator;
    private int _duration = 200;
    private int _indent;
    private int _lineColor = -6184026;
    private Paint _linePaint;
    private Path _linePath;
    private float _position;
    private int _strokeWidth;
    private int height;

    class C29641 extends AnimatorListenerAdapter {
        C29641() {
        }

        public void onAnimationEnd(Animator animation) {
        }
    }

    public SliderSeparator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._strokeWidth = isInEditMode() ? 3 : PixelMeasure.dp(1);
        this._position = 0.0f;
        this._indent = 0;
        this.height = (int) getResources().getDimension(C1283R.dimen.gasEditSeparatorHeight);
        this._strokeWidth = (int) Math.ceil((double) context.getResources().getDisplayMetrics().density);
        this._linePath = new Path();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createLinePath();
        this._linePaint = new Paint();
        this._linePaint.setStyle(Style.STROKE);
        this._linePaint.setStrokeWidth((float) this._strokeWidth);
        this._linePaint.setColor(this._lineColor);
        this._linePaint.setAntiAlias(true);
    }

    private void createLinePath() {
        int w = getWidth();
        this._linePath.reset();
        this._linePath.moveTo(-1.0f, (float) this.height);
        if (this._indent > 0) {
            this._linePath.lineTo(this._position - ((float) this._indent), (float) this.height);
            this._linePath.lineTo(this._position, (float) (this.height - this._indent));
            this._linePath.lineTo(this._position + ((float) this._indent), (float) this.height);
        }
        this._linePath.lineTo((float) w, (float) this.height);
    }

    public void setDuration(int ms) {
        this._duration = ms;
    }

    public void setIndent(int value) {
        this._indent = value;
        createLinePath();
        invalidate();
    }

    public void setPosition(float position) {
        this._position = position;
        createLinePath();
        invalidate();
    }

    public void animateUnselected() {
        if (this._animator != null) {
            this._animator.cancel();
        }
        this._animator = ObjectAnimator.ofInt(this, "indent", new int[]{this.height, 0});
        this._animator.setDuration((long) this._duration);
        this._animator.setInterpolator(new AccelerateDecelerateInterpolator());
        this._animator.start();
        this._animator.addListener(new C29641());
    }

    public void animateSelected(float newPos) {
        if (this._animator != null) {
            this._animator.cancel();
        }
        if (this._position == 0.0f) {
            this._position = newPos;
            this._animator = ObjectAnimator.ofInt(this, "indent", new int[]{0, this.height});
            this._animator.setDuration((long) this._duration);
            this._animator.setInterpolator(new AccelerateDecelerateInterpolator());
            this._animator.start();
            return;
        }
        this._indent = this.height;
        this._animator = ObjectAnimator.ofFloat(this, "position", new float[]{this._position, newPos});
        this._animator.setDuration((long) this._duration);
        this._animator.setInterpolator(new AccelerateDecelerateInterpolator());
        this._animator.start();
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(this._linePath, this._linePaint);
    }
}
