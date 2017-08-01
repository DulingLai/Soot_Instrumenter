package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import java.io.Serializable;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

public abstract class AbstractChart implements Serializable {
    public abstract void draw(Canvas canvas, int i, int i2, int i3, int i4, Paint paint);

    public abstract void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, Paint paint);

    public abstract int getLegendShapeWidth();

    protected void drawBackground(DefaultRenderer defaultRenderer, Canvas canvas, int i, int i2, int i3, int i4, Paint paint, boolean z, int i5) {
        if (defaultRenderer.isApplyBackgroundColor() || z) {
            if (z) {
                paint.setColor(i5);
            } else {
                paint.setColor(defaultRenderer.getBackgroundColor());
            }
            paint.setStyle(Style.FILL);
            Canvas canvas2 = canvas;
            canvas2.drawRect((float) i, (float) i2, (float) (i + i3), (float) (i2 + i4), paint);
        }
    }

    protected void drawLegend(Canvas canvas, DefaultRenderer defaultRenderer, String[] strArr, int i, int i2, int i3, int i4, int i5, int i6, Paint paint) {
        if (defaultRenderer.isShowLegend()) {
            float f = (float) i;
            float f2 = (float) (((i3 + i5) - i6) + 32);
            float legendShapeWidth = (float) getLegendShapeWidth();
            paint.setTextAlign(Align.LEFT);
            paint.setTextSize(defaultRenderer.getLegendTextSize());
            int min = Math.min(strArr.length, defaultRenderer.getSeriesRendererCount());
            for (int i7 = 0; i7 < min; i7++) {
                String str;
                String str2 = strArr[i7];
                if (strArr.length == defaultRenderer.getSeriesRendererCount()) {
                    paint.setColor(defaultRenderer.getSeriesRendererAt(i7).getColor());
                } else {
                    paint.setColor(DefaultRenderer.TEXT_COLOR);
                }
                float[] fArr = new float[str2.length()];
                paint.getTextWidths(str2, fArr);
                float f3 = 0.0f;
                for (float f4 : fArr) {
                    f3 += f4;
                }
                float f42 = (10.0f + legendShapeWidth) + f3;
                float f5 = f + f42;
                if (i7 > 0 && getExceed(f5, defaultRenderer, i2, i4)) {
                    f = (float) i;
                    f2 += defaultRenderer.getLegendTextSize();
                    f5 = f + f42;
                }
                if (getExceed(f5, defaultRenderer, i2, i4)) {
                    f5 = ((((float) i2) - f) - legendShapeWidth) - 10.0f;
                    if (isVertical(defaultRenderer)) {
                        f5 = ((((float) i4) - f) - legendShapeWidth) - 10.0f;
                    }
                    str = str2.substring(0, paint.breakText(str2, true, f5, fArr)) + "...";
                } else {
                    str = str2;
                }
                drawLegendShape(canvas, defaultRenderer.getSeriesRendererAt(i7), f, f2, paint);
                canvas.drawText(str, (f + legendShapeWidth) + 5.0f, 5.0f + f2, paint);
                f += f42;
            }
        }
    }

    private boolean getExceed(float f, DefaultRenderer defaultRenderer, int i, int i2) {
        boolean z;
        if (f > ((float) i)) {
            z = true;
        } else {
            z = false;
        }
        if (isVertical(defaultRenderer)) {
            return f > ((float) i2);
        } else {
            return z;
        }
    }

    private boolean isVertical(DefaultRenderer defaultRenderer) {
        return (defaultRenderer instanceof XYMultipleSeriesRenderer) && ((XYMultipleSeriesRenderer) defaultRenderer).getOrientation() == Orientation.VERTICAL;
    }

    protected void drawPath(Canvas canvas, float[] fArr, Paint paint, boolean z) {
        Path path = new Path();
        path.moveTo(fArr[0], fArr[1]);
        for (int i = 2; i < fArr.length; i += 2) {
            path.lineTo(fArr[i], fArr[i + 1]);
        }
        if (z) {
            path.lineTo(fArr[0], fArr[1]);
        }
        canvas.drawPath(path, paint);
    }
}
