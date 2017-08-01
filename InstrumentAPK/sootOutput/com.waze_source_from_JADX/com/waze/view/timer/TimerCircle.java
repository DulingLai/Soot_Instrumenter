package com.waze.view.timer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class TimerCircle extends View {
    private static final int PADDING = 3;
    private int diameter;
    private int height;
    private RectF innerCircle;
    private RectF outerCircle;
    private Paint paint = new Paint();
    private final Path path = new Path();
    private float ratio = 1.0f;
    private int width;

    public TimerCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint.setColor(-13125677);
        this.paint.setAntiAlias(true);
        setRatio(0.9999f);
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }

    private void drawArc(Canvas canvas, Paint paint) {
        this.path.reset();
        float to = -360.0f * this.ratio;
        this.path.arcTo(this.outerCircle, 270.0f, -to);
        this.path.arcTo(this.innerCircle, 270.0f - to, to);
        this.path.close();
        canvas.drawPath(this.path, paint);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(getWidth() == this.width && getHeight() == this.height)) {
            this.width = getWidth();
            this.height = getHeight();
            this.diameter = Math.min(this.width, this.height) - 6;
            int thickness = this.diameter / 9;
            int left = (this.width - this.diameter) / 2;
            int top = (this.height - this.diameter) / 2;
            this.outerCircle = new RectF((float) left, (float) top, (float) (left + this.diameter), (float) (top + this.diameter));
            int innerDiameter = this.diameter - (thickness * 2);
            this.innerCircle = new RectF((float) (left + thickness), (float) (top + thickness), (float) ((left + thickness) + innerDiameter), (float) ((top + thickness) + innerDiameter));
        }
        drawArc(canvas, this.paint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec) * 1, MeasureSpec.getSize(heightMeasureSpec) * 1);
    }
}
