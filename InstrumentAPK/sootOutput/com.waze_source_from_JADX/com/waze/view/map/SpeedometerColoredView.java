package com.waze.view.map;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.waze.C1283R;

public class SpeedometerColoredView extends ImageView {
    public static final int GREY = 1;
    private static final int GREY_COLOR = -1715550260;
    private static final int PADDING = 3;
    public static final int RED = 2;
    private final int RED_COLOR;
    private int diameter;
    private int height;
    private RectF innerCircle;
    private boolean isFull;
    private RectF outerCircle;
    private Paint paint;
    private Paint paintFull;
    private final Path path;
    private final Path pathFull;
    private float sweep;
    private int width;

    public SpeedometerColoredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.RED_COLOR = getResources().getColor(C1283R.color.RedSweet);
        this.sweep = 0.0f;
        this.path = new Path();
        this.pathFull = new Path();
        this.isFull = false;
        this.paint = new Paint();
        new Options().inSampleSize = 4;
        this.paint.setAntiAlias(true);
        this.paint.setColor(this.RED_COLOR);
        this.paintFull = new Paint();
        this.paintFull.setAntiAlias(true);
        this.paintFull.setColor(GREY_COLOR);
        if (isInEditMode()) {
            setSweep(120.0f);
        }
    }

    public void setSweep(float sweep) {
        this.sweep = sweep;
    }

    public void setColor(int color) {
        if (color == 2) {
            this.paint.setColor(this.RED_COLOR);
        } else if (color == 1) {
            this.paint.setColor(GREY_COLOR);
        }
    }

    public void setFull(boolean isFull, int color) {
        this.isFull = isFull;
        if (!isFull) {
            return;
        }
        if (color == 2) {
            this.paintFull.setColor(this.RED_COLOR);
        } else if (color == 1) {
            this.paintFull.setColor(GREY_COLOR);
        }
    }

    private void drawArc(Canvas canvas, Paint paint) {
        this.path.reset();
        this.path.arcTo(this.outerCircle, 90.0f, this.sweep);
        this.path.arcTo(this.innerCircle, this.sweep + 90.0f, -this.sweep);
        this.path.close();
        canvas.drawPath(this.path, paint);
    }

    private void drawFullArc(Canvas canvas, Paint paint) {
        if (this.isFull) {
            this.pathFull.reset();
            this.pathFull.addOval(this.outerCircle, Direction.CW);
            this.pathFull.addOval(this.innerCircle, Direction.CCW);
            this.pathFull.close();
            canvas.drawPath(this.pathFull, paint);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(getWidth() == this.width && getHeight() == this.height)) {
            this.width = getWidth();
            this.height = getHeight();
            this.diameter = Math.min(this.width, this.height) - 6;
            int thickness = this.diameter / 16;
            int left = (this.width - this.diameter) / 2;
            int top = (this.height - this.diameter) / 2;
            this.outerCircle = new RectF((float) left, (float) top, (float) (left + this.diameter), (float) (top + this.diameter));
            int innerDiameter = this.diameter - (thickness * 2);
            this.innerCircle = new RectF((float) (left + thickness), (float) (top + thickness), (float) ((left + thickness) + innerDiameter), (float) ((top + thickness) + innerDiameter));
        }
        if (this.outerCircle != null && this.innerCircle != null) {
            drawFullArc(canvas, this.paintFull);
            drawArc(canvas, this.paint);
        }
    }
}
