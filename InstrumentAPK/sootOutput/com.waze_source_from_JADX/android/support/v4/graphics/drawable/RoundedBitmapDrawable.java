package android.support.v4.graphics.drawable;

import android.content.res.Resources;
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
import android.util.DisplayMetrics;
import com.waze.map.CanvasFont;

public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    private boolean mApplyGravity = true;
    final Bitmap mBitmap;
    private int mBitmapHeight;
    private final BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private float mCornerRadius;
    final Rect mDstRect = new Rect();
    private final RectF mDstRectF = new RectF();
    private int mGravity = 119;
    private boolean mIsCircular;
    private final Paint mPaint = new Paint(3);
    private final Matrix mShaderMatrix = new Matrix();
    private int mTargetDensity = 160;

    private static boolean isGreaterThanZero(float $f0) throws  {
        return $f0 > 0.05f;
    }

    public final Paint getPaint() throws  {
        return this.mPaint;
    }

    public final Bitmap getBitmap() throws  {
        return this.mBitmap;
    }

    private void computeBitmapSize() throws  {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
    }

    public void setTargetDensity(Canvas $r1) throws  {
        setTargetDensity($r1.getDensity());
    }

    public void setTargetDensity(DisplayMetrics $r1) throws  {
        setTargetDensity($r1.densityDpi);
    }

    public void setTargetDensity(int $i0) throws  {
        if (this.mTargetDensity != $i0) {
            if ($i0 == 0) {
                $i0 = 160;
            }
            this.mTargetDensity = $i0;
            if (this.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public int getGravity() throws  {
        return this.mGravity;
    }

    public void setGravity(int $i0) throws  {
        if (this.mGravity != $i0) {
            this.mGravity = $i0;
            this.mApplyGravity = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean mipMap) throws  {
        throw new UnsupportedOperationException();
    }

    public boolean hasMipMap() throws  {
        throw new UnsupportedOperationException();
    }

    public void setAntiAlias(boolean $z0) throws  {
        this.mPaint.setAntiAlias($z0);
        invalidateSelf();
    }

    public boolean hasAntiAlias() throws  {
        return this.mPaint.isAntiAlias();
    }

    public void setFilterBitmap(boolean $z0) throws  {
        this.mPaint.setFilterBitmap($z0);
        invalidateSelf();
    }

    public void setDither(boolean $z0) throws  {
        this.mPaint.setDither($z0);
        invalidateSelf();
    }

    void gravityCompatApply(int gravity, int bitmapWidth, int bitmapHeight, Rect bounds, Rect outRect) throws  {
        throw new UnsupportedOperationException();
    }

    void updateDstRect() throws  {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int $i0 = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                gravityCompatApply(this.mGravity, $i0, $i0, getBounds(), this.mDstRect);
                $i0 = Math.min(this.mDstRect.width(), this.mDstRect.height());
                this.mDstRect.inset(Math.max(0, (this.mDstRect.width() - $i0) / 2), Math.max(0, (this.mDstRect.height() - $i0) / 2));
                this.mCornerRadius = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR * ((float) $i0);
            } else {
                gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect);
            }
            this.mDstRectF.set(this.mDstRect);
            if (this.mBitmapShader != null) {
                this.mShaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
                Matrix $r5 = this.mShaderMatrix;
                float $f0 = this.mDstRectF.width();
                Bitmap $r6 = this.mBitmap;
                float $f1 = (float) $r6.getWidth();
                $f0 /= $f1;
                float $f12 = this.mDstRectF.height();
                $r6 = this.mBitmap;
                $r5.preScale($f0, $f12 / ((float) $r6.getHeight()));
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }

    public void draw(Canvas $r1) throws  {
        Bitmap $r2 = this.mBitmap;
        if ($r2 != null) {
            updateDstRect();
            if (this.mPaint.getShader() == null) {
                $r1.drawBitmap($r2, null, this.mDstRect, this.mPaint);
            } else {
                $r1.drawRoundRect(this.mDstRectF, this.mCornerRadius, this.mCornerRadius, this.mPaint);
            }
        }
    }

    public void setAlpha(int $i0) throws  {
        if ($i0 != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha($i0);
            invalidateSelf();
        }
    }

    public int getAlpha() throws  {
        return this.mPaint.getAlpha();
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mPaint.setColorFilter($r1);
        invalidateSelf();
    }

    public ColorFilter getColorFilter() throws  {
        return this.mPaint.getColorFilter();
    }

    public void setCircular(boolean $z0) throws  {
        this.mIsCircular = $z0;
        this.mApplyGravity = true;
        if ($z0) {
            updateCircularCornerRadius();
            this.mPaint.setShader(this.mBitmapShader);
            invalidateSelf();
            return;
        }
        setCornerRadius(0.0f);
    }

    private void updateCircularCornerRadius() throws  {
        this.mCornerRadius = (float) (Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2);
    }

    public boolean isCircular() throws  {
        return this.mIsCircular;
    }

    public void setCornerRadius(float $f0) throws  {
        if (this.mCornerRadius != $f0) {
            this.mIsCircular = false;
            if (isGreaterThanZero($f0)) {
                this.mPaint.setShader(this.mBitmapShader);
            } else {
                this.mPaint.setShader(null);
            }
            this.mCornerRadius = $f0;
            invalidateSelf();
        }
    }

    protected void onBoundsChange(Rect $r1) throws  {
        super.onBoundsChange($r1);
        if (this.mIsCircular) {
            updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    public float getCornerRadius() throws  {
        return this.mCornerRadius;
    }

    public int getIntrinsicWidth() throws  {
        return this.mBitmapWidth;
    }

    public int getIntrinsicHeight() throws  {
        return this.mBitmapHeight;
    }

    public int getOpacity() throws  {
        if (this.mGravity != 119) {
            return -3;
        }
        if (this.mIsCircular) {
            return -3;
        }
        Bitmap $r1 = this.mBitmap;
        if ($r1 == null) {
            return -3;
        }
        if ($r1.hasAlpha()) {
            return -3;
        }
        if (this.mPaint.getAlpha() >= 255) {
            return !isGreaterThanZero(this.mCornerRadius) ? -1 : -3;
        } else {
            return -3;
        }
    }

    RoundedBitmapDrawable(Resources $r1, Bitmap $r2) throws  {
        if ($r1 != null) {
            this.mTargetDensity = $r1.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = $r2;
        if (this.mBitmap != null) {
            computeBitmapSize();
            this.mBitmapShader = new BitmapShader(this.mBitmap, TileMode.CLAMP, TileMode.CLAMP);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
        this.mBitmapShader = null;
    }
}
