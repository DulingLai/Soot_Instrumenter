package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LineChart extends XYChart {
    private static final int SHAPE_WIDTH = 30;
    private ScatterChart pointsChart;

    public LineChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.pointsChart = new ScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        int length = fArr.length;
        XYSeriesRenderer xYSeriesRenderer = (XYSeriesRenderer) simpleSeriesRenderer;
        float strokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(xYSeriesRenderer.getLineWidth());
        if (xYSeriesRenderer.isFillBelowLine()) {
            paint.setColor(xYSeriesRenderer.getFillBelowLineColor());
            Object obj = new float[(fArr.length + 4)];
            System.arraycopy(fArr, 0, obj, 0, length);
            obj[0] = fArr[0] + 1.0f;
            obj[length] = obj[length - 2];
            obj[length + 1] = f;
            obj[length + 2] = obj[0];
            obj[length + 3] = obj[length + 1];
            paint.setStyle(Style.FILL);
            drawPath(canvas, obj, paint, true);
        }
        paint.setColor(simpleSeriesRenderer.getColor());
        paint.setStyle(Style.STROKE);
        drawPath(canvas, fArr, paint, false);
        paint.setStrokeWidth(strokeWidth);
    }

    public int getLegendShapeWidth() {
        return 30;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint) {
        canvas.drawLine(f, f2, f + 30.0f, f2, paint);
        if (isRenderPoints(simpleSeriesRenderer)) {
            this.pointsChart.drawLegendShape(canvas, simpleSeriesRenderer, f + 5.0f, f2, paint);
        }
    }

    public boolean isRenderPoints(SimpleSeriesRenderer simpleSeriesRenderer) {
        return ((XYSeriesRenderer) simpleSeriesRenderer).getPointStyle() != PointStyle.POINT;
    }

    public ScatterChart getPointsChart() {
        return this.pointsChart;
    }
}
