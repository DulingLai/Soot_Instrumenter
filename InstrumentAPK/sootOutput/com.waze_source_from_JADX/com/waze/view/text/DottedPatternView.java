package com.waze.view.text;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.waze.R;

public class DottedPatternView extends View {
    int dotFillColor = -10053121;
    int dotRadius = -1;
    int dotSpacing = -1;
    int dotXSize = -1;
    int dotYSize = -1;
    boolean drawPartialDots = Boolean.FALSE.booleanValue();
    private int mLastHeight;
    private int mLastWidth;
    private Paint mPaint;
    RectF rect = new RectF();

    public void setDotXSize(int dotXSizePx) {
        this.dotXSize = dotXSizePx;
    }

    public void setDotYSize(int dotYSizePx) {
        this.dotYSize = dotYSizePx;
    }

    public void setDotRadius(int dotRadiusPx) {
        this.dotRadius = dotRadiusPx;
    }

    public void setDotSpacing(int dotSpacingPx) {
        this.dotSpacing = dotSpacingPx;
    }

    public void setDotFillColor(int dotFillColor) {
        this.dotFillColor = dotFillColor;
    }

    public void setDrawPartialDots(boolean drawPartialDots) {
        this.drawPartialDots = drawPartialDots;
    }

    public DottedPatternView(Context context) {
        super(context);
        init(context);
    }

    public DottedPatternView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        init(context);
    }

    public DottedPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init(context);
    }

    @TargetApi(21)
    public DottedPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributes(context, attrs);
        init(context);
    }

    void getAttributes(Context context, AttributeSet attrs) {
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.DottedPatternView);
        this.dotXSize = attrArray.getDimensionPixelSize(2, this.dotXSize);
        this.dotYSize = attrArray.getDimensionPixelSize(3, this.dotYSize);
        this.dotRadius = attrArray.getDimensionPixelSize(4, this.dotRadius);
        this.dotSpacing = attrArray.getDimensionPixelSize(5, this.dotSpacing);
        this.dotFillColor = attrArray.getColor(1, this.dotFillColor);
        this.drawPartialDots = attrArray.getBoolean(0, this.drawPartialDots);
        attrArray.recycle();
    }

    void init(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (this.dotXSize < 0) {
            this.dotXSize = (int) (density * 5.0f);
        }
        if (this.dotYSize < 0) {
            this.dotYSize = (int) (density * 5.0f);
        }
        if (this.dotRadius < 0) {
            this.dotRadius = (int) (density * 5.0f);
        }
        if (this.dotSpacing < 0) {
            this.dotSpacing = (int) (density * 5.0f);
        }
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(this.dotFillColor);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mLastWidth != w || this.mLastHeight != h) {
            this.mLastWidth = w;
            this.mLastHeight = h;
            postInvalidate();
        }
    }

    private void makeBitmap(int w, int h) {
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mLastWidth > 0 && this.mLastHeight > 0) {
            int leftMargin = 0;
            int topMargin = 0;
            if (!this.drawPartialDots) {
                int dotUnitX = this.dotXSize + this.dotSpacing;
                int dotUnitY = this.dotYSize + this.dotSpacing;
                int realWidth = (((this.mLastWidth + this.dotSpacing) / dotUnitX) * dotUnitX) - this.dotSpacing;
                int realHeight = (((this.mLastHeight + this.dotSpacing) / dotUnitY) * dotUnitY) - this.dotSpacing;
                if (realWidth > 0) {
                    leftMargin = (this.mLastWidth - realWidth) / 2;
                }
                if (realHeight > 0) {
                    topMargin = (this.mLastHeight - realHeight) / 2;
                }
            }
            this.rect.left = (float) leftMargin;
            this.rect.right = (float) (this.dotYSize + leftMargin);
            while (true) {
                if ((this.drawPartialDots ? this.rect.left : this.rect.right) <= ((float) (this.mLastWidth - leftMargin))) {
                    RectF rectF;
                    this.rect.top = (float) topMargin;
                    this.rect.bottom = (float) (this.dotYSize + topMargin);
                    while (true) {
                        if ((this.drawPartialDots ? this.rect.top : this.rect.bottom) > ((float) (this.mLastHeight - topMargin))) {
                            break;
                        }
                        canvas.drawRoundRect(this.rect, (float) this.dotRadius, (float) this.dotRadius, this.mPaint);
                        rectF = this.rect;
                        rectF.top += (float) (this.dotSpacing + this.dotYSize);
                        rectF = this.rect;
                        rectF.bottom += (float) (this.dotSpacing + this.dotYSize);
                    }
                    rectF = this.rect;
                    rectF.left += (float) (this.dotSpacing + this.dotXSize);
                    rectF = this.rect;
                    rectF.right += (float) (this.dotSpacing + this.dotXSize);
                } else {
                    return;
                }
            }
        }
    }
}
