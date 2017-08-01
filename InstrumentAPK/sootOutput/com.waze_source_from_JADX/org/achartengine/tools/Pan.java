package org.achartengine.tools;

import android.graphics.PointF;
import org.achartengine.chart.XYChart;

public class Pan extends AbstractTool {
    public Pan(XYChart xYChart) {
        super(xYChart);
    }

    public void apply(float f, float f2, float f3, float f4) {
        double[] range = getRange();
        double[] panLimits = this.mRenderer.getPanLimits();
        Object obj = (panLimits == null || panLimits.length != 4) ? null : 1;
        double[] calcRange = this.mChart.getCalcRange();
        if (range[0] != range[1] || calcRange[0] != calcRange[1]) {
            if (range[2] != range[3] || calcRange[2] != calcRange[3]) {
                checkRange(range);
                PointF toRealPoint = this.mChart.toRealPoint(f, f2);
                PointF toRealPoint2 = this.mChart.toRealPoint(f3, f4);
                double d = (double) (toRealPoint.x - toRealPoint2.x);
                double d2 = (double) (toRealPoint.y - toRealPoint2.y);
                if (this.mRenderer.isPanXEnabled()) {
                    if (obj == null) {
                        setXRange(range[0] + d, d + range[1]);
                    } else if (panLimits[0] > range[0] + d) {
                        setXRange(panLimits[0], panLimits[0] + (range[1] - range[0]));
                    } else if (panLimits[1] < range[1] + d) {
                        setXRange(panLimits[1] - (range[1] - range[0]), panLimits[1]);
                    } else {
                        setXRange(range[0] + d, d + range[1]);
                    }
                }
                if (!this.mRenderer.isPanYEnabled()) {
                    return;
                }
                if (obj == null) {
                    setYRange(range[2] + d2, range[3] + d2);
                } else if (panLimits[2] > range[2] + d2) {
                    setYRange(panLimits[2], (range[3] - range[2]) + panLimits[2]);
                } else if (panLimits[3] < range[3] + d2) {
                    setYRange(panLimits[3] - (range[3] - range[2]), panLimits[3]);
                } else {
                    setYRange(range[2] + d2, range[3] + d2);
                }
            }
        }
    }
}
