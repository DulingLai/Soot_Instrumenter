package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import com.waze.LayoutManager;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class PieChart extends AbstractChart {
    private static final int SHAPE_WIDTH = 10;
    private CategorySeries mDataset;
    private DefaultRenderer mRenderer;

    public PieChart(CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        this.mDataset = categorySeries;
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
        int itemCount = this.mDataset.getItemCount();
        String[] strArr = new String[itemCount];
        legendHeight = 0;
        double d = 0.0d;
        while (legendHeight < itemCount) {
            double value = this.mDataset.getValue(legendHeight) + d;
            strArr[legendHeight] = this.mDataset.getCategory(legendHeight);
            legendHeight++;
            d = value;
        }
        float f = 0.0f;
        legendHeight = (int) (((double) Math.min(Math.abs(i8 - i6), Math.abs(i9 - i7))) * 0.35d);
        int i10 = (i6 + i8) / 2;
        int i11 = (i9 + i7) / 2;
        float f2 = ((float) legendHeight) * 0.9f;
        float f3 = ((float) legendHeight) * 1.1f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 1.0f;
        RectF rectF = new RectF((float) (i10 - legendHeight), (float) (i11 - legendHeight), (float) (i10 + legendHeight), (float) (legendHeight + i11));
        int i12 = 0;
        while (i12 < itemCount) {
            float f7;
            float f8;
            paint.setColor(this.mRenderer.getSeriesRendererAt(i12).getColor());
            float value2 = (float) ((((double) ((float) this.mDataset.getValue(i12))) / d) * 360.0d);
            canvas.drawArc(rectF, f, value2, true, paint);
            if (this.mRenderer.isShowLabels()) {
                paint.setColor(this.mRenderer.getLabelsColor());
                double toRadians = Math.toRadians((double) (90.0f - ((value2 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + f)));
                double sin = Math.sin(toRadians);
                double cos = Math.cos(toRadians);
                int round = Math.round(((float) i10) + ((float) (((double) f2) * sin)));
                int round2 = Math.round(((float) i11) + ((float) (((double) f2) * cos)));
                int round3 = Math.round(((float) i10) + ((float) (((double) f3) * sin)));
                legendHeight = Math.round(((float) i11) + ((float) (((double) f3) * cos)));
                if (Math.sqrt((double) (((((float) round3) - f4) * (((float) round3) - f4)) + ((((float) legendHeight) - f5) * (((float) legendHeight) - f5)))) <= ((double) 20.0f)) {
                    float f9 = (float) (((double) f6) * 1.1d);
                    round3 = Math.round(((float) i10) + ((float) (((double) (f3 * f9)) * sin)));
                    legendHeight = Math.round(((float) i11) + ((float) (((double) (f3 * f9)) * cos)));
                    i7 = round3;
                    f6 = f9;
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
                canvas.drawText(this.mDataset.getCategory(i12), (float) (i7 + round), (float) (legendHeight + 5), paint);
                f7 = (float) i7;
                f8 = (float) legendHeight;
            } else {
                f8 = f5;
                f7 = f4;
            }
            i12++;
            f5 = f8;
            f4 = f7;
            f = value2 + f;
        }
        drawLegend(canvas, this.mRenderer, strArr, i6, i8, i2, i3, i4, i5, paint);
    }

    public int getLegendShapeWidth() {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint) {
        canvas.drawRect(f, f2 - 5.0f, f + 10.0f, f2 + 5.0f, paint);
    }
}
