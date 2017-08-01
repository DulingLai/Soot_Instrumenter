package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class RangeBarChart extends BarChart {
    public RangeBarChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type);
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        int seriesCount = this.mDataset.getSeriesCount();
        int length = fArr.length;
        paint.setColor(simpleSeriesRenderer.getColor());
        paint.setStyle(Style.FILL);
        float halfDiffX = getHalfDiffX(fArr, length, seriesCount);
        for (int i2 = 0; i2 < length; i2 += 4) {
            float f2 = fArr[i2];
            float f3 = fArr[i2 + 1];
            float f4 = fArr[i2 + 2];
            float f5 = fArr[i2 + 3];
            if (this.mType == Type.STACKED) {
                canvas.drawRect(f2 - halfDiffX, f5, f4 + halfDiffX, f3, paint);
            } else {
                float f6 = (((float) (i * 2)) * halfDiffX) + (f2 - (((float) seriesCount) * halfDiffX));
                canvas.drawRect(f6, f5, f6 + (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * halfDiffX), f3, paint);
            }
        }
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries xYSeries, Paint paint, float[] fArr, int i) {
        int seriesCount = this.mDataset.getSeriesCount();
        float halfDiffX = getHalfDiffX(fArr, fArr.length, seriesCount);
        for (int i2 = 0; i2 < fArr.length; i2 += 4) {
            float f = fArr[i2];
            if (this.mType == Type.DEFAULT) {
                f += (((float) (i * 2)) * halfDiffX) - ((((float) seriesCount) - 1.5f) * halfDiffX);
            }
            drawText(canvas, getLabel(xYSeries.getY((i2 / 2) + 1)), f, fArr[i2 + 3] - 3.0f, paint, 0.0f);
            drawText(canvas, getLabel(xYSeries.getY(i2 / 2)), f, fArr[i2 + 1] + 7.5f, paint, 0.0f);
        }
    }

    protected float getCoeficient() {
        return CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
    }
}
