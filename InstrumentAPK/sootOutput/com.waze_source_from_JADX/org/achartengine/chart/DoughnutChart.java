package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import com.waze.LayoutManager;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class DoughnutChart extends AbstractChart {
    private static final int SHAPE_WIDTH = 10;
    private MultipleCategorySeries mDataset;
    private DefaultRenderer mRenderer;
    private int mStep;

    public DoughnutChart(MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer) {
        this.mDataset = multipleCategorySeries;
        this.mRenderer = defaultRenderer;
    }

    public void draw(Canvas canvas, int i, int i2, int i3, int i4, Paint paint) {
        int i5;
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
        this.mStep = 7;
        int categoriesCount = this.mDataset.getCategoriesCount();
        int min = Math.min(Math.abs(i8 - i6), Math.abs(i9 - i7));
        double d = 0.2d / ((double) categoriesCount);
        int i10 = (int) (0.35d * ((double) min));
        int i11 = (i6 + i8) / 2;
        int i12 = (i9 + i7) / 2;
        float f = ((float) i10) * 1.1f;
        String[] strArr = new String[categoriesCount];
        int i13 = 0;
        float f2 = 0.9f * ((float) i10);
        int i14 = i10;
        while (i13 < categoriesCount) {
            int itemCount = this.mDataset.getItemCount(i13);
            String[] strArr2 = new String[itemCount];
            legendHeight = 0;
            double d2 = 0.0d;
            while (legendHeight < itemCount) {
                double d3 = this.mDataset.getValues(i13)[legendHeight] + d2;
                strArr2[legendHeight] = this.mDataset.getTitles(i13)[legendHeight];
                legendHeight++;
                d2 = d3;
            }
            float f3 = 0.0f;
            RectF rectF = new RectF((float) (i11 - i14), (float) (i12 - i14), (float) (i11 + i14), (float) (i12 + i14));
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 1.0f;
            int i15 = 0;
            while (i15 < itemCount) {
                float f7;
                float f8;
                paint.setColor(this.mRenderer.getSeriesRendererAt(i15).getColor());
                float f9 = (float) ((((double) ((float) this.mDataset.getValues(i13)[i15])) / d2) * 360.0d);
                canvas.drawArc(rectF, f3, f9, true, paint);
                if (this.mRenderer.isShowLabels()) {
                    paint.setColor(this.mRenderer.getLabelsColor());
                    double toRadians = Math.toRadians((double) (90.0f - ((f9 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + f3)));
                    double sin = Math.sin(toRadians);
                    double cos = Math.cos(toRadians);
                    int round = Math.round(((float) i11) + ((float) (((double) f2) * sin)));
                    int round2 = Math.round(((float) i12) + ((float) (((double) f2) * cos)));
                    int round3 = Math.round(((float) i11) + ((float) (((double) f) * sin)));
                    legendHeight = Math.round(((float) i12) + ((float) (((double) f) * cos)));
                    if (Math.sqrt((double) (((((float) round3) - f4) * (((float) round3) - f4)) + ((((float) legendHeight) - f5) * (((float) legendHeight) - f5)))) <= ((double) 20.0f)) {
                        float f10 = (float) (((double) f6) * 1.1d);
                        round3 = Math.round(((float) i11) + ((float) (((double) (f * f10)) * sin)));
                        legendHeight = Math.round(((float) i12) + ((float) (((double) (f * f10)) * cos)));
                        i7 = round3;
                        f6 = f10;
                    } else {
                        i7 = round3;
                        f6 = 1.0f;
                    }
                    canvas.drawLine((float) round, (float) round2, (float) i7, (float) legendHeight, paint);
                    round3 = 10;
                    paint.setTextAlign(Align.LEFT);
                    if (round > i7) {
                        round3 = -10;
                        paint.setTextAlign(Align.RIGHT);
                    }
                    round = round3;
                    canvas.drawLine((float) i7, (float) legendHeight, (float) (i7 + round), (float) legendHeight, paint);
                    canvas.drawText(this.mDataset.getTitles(i13)[i15], (float) (i7 + round), (float) (legendHeight + 5), paint);
                    f7 = (float) i7;
                    f8 = (float) legendHeight;
                } else {
                    f8 = f5;
                    f7 = f4;
                }
                i15++;
                f5 = f8;
                f4 = f7;
                f3 = f9 + f3;
            }
            int i16 = (int) (((double) i14) - (((double) min) * d));
            f5 = (float) (((double) f2) - ((((double) min) * d) - 2.0d));
            if (this.mRenderer.getBackgroundColor() != 0) {
                paint.setColor(this.mRenderer.getBackgroundColor());
            } else {
                paint.setColor(-1);
            }
            paint.setStyle(Style.FILL);
            canvas.drawArc(new RectF((float) (i11 - i16), (float) (i12 - i16), (float) (i11 + i16), (float) (i12 + i16)), 0.0f, 360.0f, true, paint);
            int i17 = i16 - 1;
            strArr[i13] = this.mDataset.getCategory(i13);
            i13++;
            f2 = f5;
            i14 = i17;
        }
        drawLegend(canvas, this.mRenderer, strArr, i6, i8, i2, i3, i4, i5, paint);
    }

    public int getLegendShapeWidth() {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint) {
        this.mStep--;
        canvas.drawCircle((10.0f + f) - ((float) this.mStep), f2, (float) this.mStep, paint);
    }
}
