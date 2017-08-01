package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.DialRenderer.Type;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.util.MathHelper;

public class DialChart extends AbstractChart {
    private static final int NEEDLE_RADIUS = 10;
    private static final int SHAPE_WIDTH = 10;
    private CategorySeries mDataset;
    private DialRenderer mRenderer;

    public DialChart(CategorySeries categorySeries, DialRenderer dialRenderer) {
        this.mDataset = categorySeries;
        this.mRenderer = dialRenderer;
    }

    public void draw(Canvas canvas, int i, int i2, int i3, int i4, Paint paint) {
        int i5;
        double d;
        double value;
        double d2;
        paint.setAntiAlias(this.mRenderer.isAntialiasing());
        paint.setStyle(Style.FILL);
        paint.setTextSize(this.mRenderer.getLabelsTextSize());
        int legendHeight = this.mRenderer.getLegendHeight();
        if (this.mRenderer.isShowLegend() && legendHeight == 0) {
            i5 = i4 / 5;
        } else {
            i5 = legendHeight;
        }
        int i6 = i + 15;
        int i7 = i2 + 5;
        int i8 = (i + i3) - 5;
        int i9 = (i2 + i4) - i5;
        drawBackground(this.mRenderer, canvas, i, i2, i3, i4, paint, false, 0);
        int itemCount = this.mDataset.getItemCount();
        double d3 = 0.0d;
        String[] strArr = new String[itemCount];
        for (legendHeight = 0; legendHeight < itemCount; legendHeight++) {
            d3 += this.mDataset.getValue(legendHeight);
            strArr[legendHeight] = this.mDataset.getCategory(legendHeight);
        }
        int min = (int) (((double) Math.min(Math.abs(i8 - i6), Math.abs(i9 - i7))) * 0.35d);
        int i10 = (i6 + i8) / 2;
        i7 = (i9 + i7) / 2;
        float f = ((float) min) * 0.9f;
        float f2 = ((float) min) * 1.1f;
        d3 = this.mRenderer.getMinValue();
        double maxValue = this.mRenderer.getMaxValue();
        double angleMin = this.mRenderer.getAngleMin();
        double angleMax = this.mRenderer.getAngleMax();
        if (!(this.mRenderer.isMinValueSet() && this.mRenderer.isMaxValueSet())) {
            i9 = this.mRenderer.getSeriesRendererCount();
            d = d3;
            d3 = maxValue;
            for (int i11 = 0; i11 < i9; i11++) {
                value = this.mDataset.getValue(i11);
                if (!this.mRenderer.isMinValueSet()) {
                    d = Math.min(d, value);
                }
                if (!this.mRenderer.isMaxValueSet()) {
                    d3 = Math.max(d3, value);
                }
            }
            maxValue = d3;
            d3 = d;
        }
        if (d3 == maxValue) {
            d3 *= 0.5d;
            maxValue *= 1.5d;
        }
        paint.setColor(this.mRenderer.getLabelsColor());
        value = this.mRenderer.getMinorTicksSpacing();
        d = this.mRenderer.getMajorTicksSpacing();
        if (value == MathHelper.NULL_VALUE) {
            value = (maxValue - d3) / 30.0d;
        }
        if (d == MathHelper.NULL_VALUE) {
            d2 = (maxValue - d3) / 10.0d;
        } else {
            d2 = d;
        }
        drawTicks(canvas, d3, maxValue, angleMin, angleMax, i10, i7, (double) f2, (double) min, value, paint, false);
        drawTicks(canvas, d3, maxValue, angleMin, angleMax, i10, i7, (double) f2, (double) f, d2, paint, true);
        itemCount = this.mRenderer.getSeriesRendererCount();
        for (legendHeight = 0; legendHeight < itemCount; legendHeight++) {
            double angleForValue = getAngleForValue(this.mDataset.getValue(legendHeight), angleMin, angleMax, d3, maxValue);
            paint.setColor(this.mRenderer.getSeriesRendererAt(legendHeight).getColor());
            drawNeedle(canvas, angleForValue, i10, i7, (double) f, this.mRenderer.getVisualTypeForIndex(legendHeight) == Type.ARROW, paint);
        }
        drawLegend(canvas, this.mRenderer, strArr, i6, i8, i2, i3, i4, i5, paint);
    }

    private double getAngleForValue(double d, double d2, double d3, double d4, double d5) {
        return Math.toRadians((((d3 - d2) * (d - d4)) / (d5 - d4)) + d2);
    }

    private void drawTicks(Canvas canvas, double d, double d2, double d3, double d4, int i, int i2, double d5, double d6, double d7, Paint paint, boolean z) {
        double d8 = d;
        while (d8 <= d2) {
            double angleForValue = getAngleForValue(d8, d3, d4, d, d2);
            double sin = Math.sin(angleForValue);
            angleForValue = Math.cos(angleForValue);
            int round = Math.round(((float) i) + ((float) (d6 * sin)));
            int round2 = Math.round(((float) i2) + ((float) (d6 * angleForValue)));
            int round3 = Math.round(((float) (sin * d5)) + ((float) i));
            Canvas canvas2 = canvas;
            canvas2.drawLine((float) round, (float) round2, (float) round3, (float) Math.round(((float) (angleForValue * d5)) + ((float) i2)), paint);
            if (z) {
                paint.setTextAlign(Align.LEFT);
                if (round <= round3) {
                    paint.setTextAlign(Align.RIGHT);
                }
                String str = d8 + "";
                if (Math.round(d8) == ((long) d8)) {
                    str = ((long) d8) + "";
                }
                canvas.drawText(str, (float) round, (float) round2, paint);
            }
            d8 += d7;
        }
    }

    private void drawNeedle(Canvas canvas, double d, int i, int i2, double d2, boolean z, Paint paint) {
        float[] fArr;
        double toRadians = Math.toRadians(90.0d);
        int sin = (int) (10.0d * Math.sin(d - toRadians));
        int cos = (int) (Math.cos(d - toRadians) * 10.0d);
        int sin2 = i + ((int) (Math.sin(d) * d2));
        int cos2 = i2 + ((int) (Math.cos(d) * d2));
        if (z) {
            int sin3 = ((int) ((0.85d * d2) * Math.sin(d))) + i;
            int cos3 = ((int) ((0.85d * d2) * Math.cos(d))) + i2;
            float[] fArr2 = new float[]{(float) (sin3 - sin), (float) (cos3 - cos), (float) sin2, (float) cos2, (float) (sin3 + sin), (float) (cos + cos3)};
            float strokeWidth = paint.getStrokeWidth();
            paint.setStrokeWidth(5.0f);
            canvas.drawLine((float) i, (float) i2, (float) sin2, (float) cos2, paint);
            paint.setStrokeWidth(strokeWidth);
            fArr = fArr2;
        } else {
            fArr = new float[]{(float) (i - sin), (float) (i2 - cos), (float) sin2, (float) cos2, (float) (sin + i), (float) (cos + i2)};
        }
        drawPath(canvas, fArr, paint, true);
    }

    public int getLegendShapeWidth() {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint) {
        canvas.drawRect(f, f2 - 5.0f, f + 10.0f, f2 + 5.0f, paint);
    }
}
