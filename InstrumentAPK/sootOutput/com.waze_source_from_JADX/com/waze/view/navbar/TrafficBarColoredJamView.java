package com.waze.view.navbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import com.waze.C1283R;
import com.waze.map.CanvasFont;

public class TrafficBarColoredJamView extends View {
    private static final int[] COLORS = new int[]{-9104, -21392, -237472, -6814202};
    private static final int ENFORCEMENT_COLOR = -14624100;
    private static final int PAST_COLOR = -4144960;
    Drawable mBase;
    Bitmap mBmp;
    private Canvas mCanvas;
    int[] mColors;
    float[] mDistances;
    private Paint mPaint;
    private Paint mTrafficPaint;

    public TrafficBarColoredJamView(Context context) {
        super(context);
        init();
    }

    public TrafficBarColoredJamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrafficBarColoredJamView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        this.mPaint.setAntiAlias(true);
        this.mTrafficPaint = new Paint();
        this.mTrafficPaint.setColor(-16776961);
        this.mTrafficPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this.mTrafficPaint.setAntiAlias(true);
        setLayerType(1, null);
        if (isInEditMode()) {
            setColors(new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4}, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5}, 10);
        }
    }

    public void setColors(int[] levels, int[] lengths, int current_percent) {
        if (current_percent == 100) {
            this.mDistances = new float[1];
            this.mDistances[0] = 1.0f;
            this.mColors = new int[1];
            this.mColors[0] = PAST_COLOR;
            if (this.mCanvas != null) {
                colorizeDrawable();
                return;
            }
            return;
        }
        int i;
        int totalDist = 0;
        for (int i2 : lengths) {
            totalDist += i2;
        }
        int preDist = (totalDist * current_percent) / (100 - current_percent);
        totalDist += preDist;
        this.mDistances = new float[(lengths.length + 1)];
        this.mDistances[0] = ((float) preDist) / ((float) totalDist);
        for (i = 0; i < lengths.length; i++) {
            this.mDistances[i + 1] = ((float) lengths[i]) / ((float) totalDist);
        }
        this.mColors = new int[(levels.length + 1)];
        this.mColors[0] = PAST_COLOR;
        for (i = 0; i < levels.length; i++) {
            int colorInd = levels[i];
            if (colorInd < 0 || colorInd >= COLORS.length) {
                colorInd = 2;
            }
            this.mColors[i + 1] = COLORS[colorInd];
        }
        if (this.mCanvas != null) {
            colorizeDrawable();
        }
    }

    public void setColorsEnforcement() {
        this.mDistances = new float[1];
        this.mDistances[0] = 1.0f;
        this.mColors = new int[1];
        this.mColors[0] = ENFORCEMENT_COLOR;
        if (this.mCanvas != null) {
            colorizeDrawable();
        }
    }

    private void colorizeDrawable() {
        float fullLen = (float) this.mCanvas.getHeight();
        float endLen = fullLen + 1.0f;
        for (int i = 0; i < this.mColors.length; i++) {
            this.mTrafficPaint.setColor(this.mColors[i]);
            float startLen = (endLen - (this.mDistances[i] * fullLen)) - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            this.mCanvas.drawRect(0.0f, startLen, 100.0f, endLen + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, this.mTrafficPaint);
            endLen = startLen + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }
        invalidate();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            if (VERSION.SDK_INT < 17) {
                this.mBmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                this.mBmp.setDensity(getResources().getDisplayMetrics().densityDpi);
            } else {
                this.mBmp = Bitmap.createBitmap(getResources().getDisplayMetrics(), w, h, Config.ARGB_8888);
            }
            this.mCanvas = new Canvas(this.mBmp);
            this.mBase = getResources().getDrawable(C1283R.drawable.traffic_bar_traffic);
            this.mBase.setBounds(0, 0, this.mCanvas.getWidth(), this.mCanvas.getHeight());
            this.mBase.draw(this.mCanvas);
            if (this.mColors != null) {
                colorizeDrawable();
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBmp != null) {
            canvas.drawBitmap(this.mBmp, 0.0f, 0.0f, this.mPaint);
        }
    }
}
