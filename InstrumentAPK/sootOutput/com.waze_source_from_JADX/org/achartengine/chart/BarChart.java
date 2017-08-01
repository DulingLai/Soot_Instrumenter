package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.waze.FriendsBarFragment;
import com.waze.LayoutManager;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class BarChart extends XYChart {
    private static final int SHAPE_WIDTH = 12;
    protected Type mType = Type.DEFAULT;

    public enum Type {
        DEFAULT,
        STACKED
    }

    public BarChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        this.mType = type;
    }

    public void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i) {
        int seriesCount = this.mDataset.getSeriesCount();
        int length = fArr.length;
        paint.setColor(simpleSeriesRenderer.getColor());
        paint.setStyle(Style.FILL);
        float halfDiffX = getHalfDiffX(fArr, length, seriesCount);
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f2 = fArr[i2];
            float f3 = fArr[i2 + 1];
            if (this.mType == Type.STACKED) {
                canvas.drawRect(f2 - halfDiffX, f3, f2 + halfDiffX, f, paint);
            } else {
                float f4 = (((float) (i * 2)) * halfDiffX) + (f2 - (((float) seriesCount) * halfDiffX));
                canvas.drawRect(f4, f3, f4 + (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * halfDiffX), f, paint);
            }
        }
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries xYSeries, Paint paint, float[] fArr, int i) {
        int seriesCount = this.mDataset.getSeriesCount();
        float halfDiffX = getHalfDiffX(fArr, fArr.length, seriesCount);
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            float f = fArr[i2];
            if (this.mType == Type.DEFAULT) {
                f += (((float) (i * 2)) * halfDiffX) - ((((float) seriesCount) - 1.5f) * halfDiffX);
            }
            drawText(canvas, getLabel(xYSeries.getY(i2 / 2)), f, fArr[i2 + 1] - 3.5f, paint, 0.0f);
        }
    }

    public int getLegendShapeWidth() {
        return 12;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint) {
        canvas.drawRect(f, f2 - 6.0f, f + 12.0f, f2 + 6.0f, paint);
    }

    protected float getHalfDiffX(float[] fArr, int i, int i2) {
        float f = (fArr[i - 2] - fArr[0]) / ((float) i);
        if (f == 0.0f) {
            f = 10.0f;
        }
        if (this.mType != Type.STACKED) {
            f /= (float) i2;
        }
        return (float) (((double) f) / (((double) getCoeficient()) * (FriendsBarFragment.END_LOCATION_POSITION + this.mRenderer.getBarsSpacing())));
    }

    protected float getCoeficient() {
        return 1.0f;
    }

    public double getDefaultMinimum() {
        return 0.0d;
    }
}
