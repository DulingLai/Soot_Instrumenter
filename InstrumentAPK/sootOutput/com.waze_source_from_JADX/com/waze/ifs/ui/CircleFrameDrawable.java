package com.waze.ifs.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.waze.utils.PixelMeasure;

public class CircleFrameDrawable extends CircleShaderDrawable {
    private static final int SHADOW_DP = 2;
    private final Paint mFramePaint;
    private final int mStrokeWidthDp;

    public CircleFrameDrawable(Bitmap $r1, int $i0, int $i1) throws  {
        this($r1, $i0, $i1, 2);
    }

    public CircleFrameDrawable(Bitmap $r1, int $i0, int $i1, int shadow_dp) throws  {
        super($r1, PixelMeasure.dp($i1 + 2) + $i0);
        this.mStrokeWidthDp = $i1;
        this.mFramePaint = new Paint();
        this.mFramePaint.setAntiAlias(true);
        this.mFramePaint.setStrokeWidth((float) PixelMeasure.dp(this.mStrokeWidthDp));
        this.mFramePaint.setColor(-1);
        this.mFramePaint.setStyle(Style.STROKE);
        this.mFramePaint.setShadowLayer((float) PixelMeasure.dp(2), 0.0f, 0.0f, 1996488704);
    }

    void setFrameColor(int $i0) throws  {
        this.mFramePaint.setColor($i0);
    }

    public void draw(Canvas $r1) throws  {
        super.draw($r1);
        $r1.drawCircle(this.mCenterX, this.mCenterY, this.mRadius - ((float) (PixelMeasure.dp(this.mStrokeWidthDp) / 2)), this.mFramePaint);
    }
}
