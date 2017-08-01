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
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

public class CircleShaderDrawable extends Drawable {
    private int mBitmapHeight;
    RectF mBitmapRect = new RectF();
    private final BitmapShader mBitmapShader;
    private int mBitmapWidth;
    protected float mCenterX;
    protected float mCenterY;
    private final int mMargin;
    private final Paint mPaint;
    protected float mRadius;

    public int getOpacity() throws  {
        return -3;
    }

    public CircleShaderDrawable(Bitmap $r1, int $i0) throws  {
        int $i1 = 0;
        this.mBitmapShader = new BitmapShader($r1, TileMode.CLAMP, TileMode.CLAMP);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setShader(this.mBitmapShader);
        this.mMargin = $i0;
        if ($r1 != null) {
            $i0 = $r1.getWidth();
        } else {
            $i0 = 0;
        }
        this.mBitmapWidth = $i0;
        if ($r1 != null) {
            $i1 = $r1.getHeight();
        }
        this.mBitmapHeight = $i1;
        this.mBitmapRect.set(0.0f, 0.0f, (float) this.mBitmapWidth, (float) this.mBitmapHeight);
    }

    protected void onBoundsChange(Rect $r1) throws  {
        float $f2;
        super.onBoundsChange($r1);
        this.mCenterX = ((float) $r1.width()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        this.mCenterY = ((float) $r1.height()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        this.mRadius = Math.min(this.mCenterX, this.mCenterY) - ((float) this.mMargin);
        RectF $r2 = new RectF();
        $r2.set($r1);
        $r2.inset((float) this.mMargin, (float) this.mMargin);
        Matrix $r3 = new Matrix();
        $r3.reset();
        float $f1 = 0.0f;
        float $f0 = 0.0f;
        if (((float) this.mBitmapWidth) * $r2.height() > $r2.width() * ((float) this.mBitmapHeight)) {
            $f2 = $r2.height() / ((float) this.mBitmapHeight);
            $f1 = ($r2.width() - (((float) this.mBitmapWidth) * $f2)) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        } else {
            $f2 = $r2.width() / ((float) this.mBitmapWidth);
            $f0 = ($r2.height() - (((float) this.mBitmapHeight) * $f2)) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }
        $r3.setScale($f2, $f2);
        $r3.postTranslate((float) (((int) ($f1 + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) + this.mMargin), (float) (((int) ($f0 + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) + this.mMargin));
        this.mBitmapShader.setLocalMatrix($r3);
    }

    public void draw(Canvas $r1) throws  {
        $r1.drawCircle(this.mCenterX, this.mCenterY, this.mRadius, this.mPaint);
    }

    public void setAlpha(int $i0) throws  {
        this.mPaint.setAlpha($i0);
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mPaint.setColorFilter($r1);
        invalidateSelf();
    }
}
