package com.waze.ifs.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import com.waze.map.CanvasFont;

public class RoundedRectangleDrawable extends Drawable {
    private final int mBitmapHeight;
    private final BitmapShader mBitmapShader;
    private final int mBitmapWidth;
    private RectF mBounds = new RectF();
    private int mPaddingBottom;
    private final Paint mPaint;
    private int mRadius;

    public int getOpacity() throws  {
        return -3;
    }

    public RoundedRectangleDrawable(Bitmap $r1, int $i0, int $i1) throws  {
        this.mBitmapShader = new BitmapShader($r1, TileMode.CLAMP, TileMode.CLAMP);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setShader(this.mBitmapShader);
        this.mRadius = $i0;
        this.mPaddingBottom = $i1;
        this.mBitmapWidth = $r1.getWidth();
        this.mBitmapHeight = $r1.getHeight();
    }

    protected void onBoundsChange(Rect $r1) throws  {
        float $f2;
        super.onBoundsChange($r1);
        this.mBounds.set(0.0f, 0.0f, (float) $r1.width(), (float) ($r1.height() + this.mPaddingBottom));
        Matrix $r2 = new Matrix();
        $r2.reset();
        float $f1 = 0.0f;
        float $f0 = 0.0f;
        if (((float) this.mBitmapWidth) * this.mBounds.height() > this.mBounds.width() * ((float) this.mBitmapHeight)) {
            $f2 = this.mBounds.height() / ((float) this.mBitmapHeight);
            $f1 = (this.mBounds.width() - (((float) this.mBitmapWidth) * $f2)) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        } else {
            $f2 = this.mBounds.width() / ((float) this.mBitmapWidth);
            $f0 = (this.mBounds.height() - (((float) this.mBitmapHeight) * $f2)) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }
        $r2.setScale($f2, $f2);
        $r2.postTranslate((float) ((int) ($f1 + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)), (float) ((int) ($f0 + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)));
        this.mBitmapShader.setLocalMatrix($r2);
    }

    public void draw(Canvas $r1) throws  {
        $r1.drawRoundRect(this.mBounds, (float) this.mRadius, (float) this.mRadius, this.mPaint);
    }

    public void setAlpha(int $i0) throws  {
        this.mPaint.setAlpha($i0);
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mPaint.setColorFilter($r1);
        invalidateSelf();
    }
}
