package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class BubbleChart extends XYChart {
    private static final int MAX_BUBBLE_SIZE = 20;
    private static final int MIN_BUBBLE_SIZE = 2;
    private static final int SHAPE_WIDTH = 10;

    public BubbleChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        paint.setColor(((XYSeriesRenderer) simpleSeriesRenderer).getColor());
        paint.setStyle(Style.FILL);
        int length = fArr.length;
        XYValueSeries xYValueSeries = (XYValueSeries) this.mDataset.getSeriesAt(i);
        double maxValue = 20.0d / xYValueSeries.getMaxValue();
        for (int i2 = 0; i2 < length; i2 += 2) {
            double value = (xYValueSeries.getValue(i2 / 2) * maxValue) + 2.0d;
            drawCircle(canvas, paint, fArr[i2], fArr[i2 + 1], (float) value);
        }
    }

    public int getLegendShapeWidth() {
        return 10;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint) {
        paint.setStyle(Style.FILL);
        drawCircle(canvas, paint, f + 10.0f, f2, 3.0f);
    }

    private void drawCircle(Canvas canvas, Paint paint, float f, float f2, float f3) {
        canvas.drawCircle(f, f2, f3, paint);
    }
}
