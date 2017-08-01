package org.achartengine.tools;

import org.achartengine.chart.XYChart;

public class Zoom extends AbstractTool {
    private boolean mZoomIn;
    private float mZoomRate;

    public Zoom(XYChart xYChart, boolean z, float f) {
        super(xYChart);
        this.mZoomIn = z;
        setZoomRate(f);
    }

    public void setZoomRate(float f) {
        this.mZoomRate = f;
    }

    public void apply() {
        double[] range = getRange();
        checkRange(range);
        double[] zoomLimits = this.mRenderer.getZoomLimits();
        Object obj = (zoomLimits == null || zoomLimits.length != 4) ? null : 1;
        double d = (range[0] + range[1]) / 2.0d;
        double d2 = (range[2] + range[3]) / 2.0d;
        double d3 = range[1] - range[0];
        double d4 = range[3] - range[2];
        double d5;
        if (this.mZoomIn) {
            if (this.mRenderer.isZoomXEnabled()) {
                d3 /= (double) this.mZoomRate;
            }
            if (this.mRenderer.isZoomYEnabled()) {
                d5 = d4 / ((double) this.mZoomRate);
                d4 = d3;
                d3 = d5;
            }
            d5 = d4;
            d4 = d3;
            d3 = d5;
        } else {
            if (this.mRenderer.isZoomXEnabled()) {
                d3 *= (double) this.mZoomRate;
            }
            if (this.mRenderer.isZoomYEnabled()) {
                d5 = d4 * ((double) this.mZoomRate);
                d4 = d3;
                d3 = d5;
            }
            d5 = d4;
            d4 = d3;
            d3 = d5;
        }
        if (this.mRenderer.isZoomXEnabled()) {
            double d6 = d - (d4 / 2.0d);
            d4 = (d4 / 2.0d) + d;
            if (obj == null || (zoomLimits[0] <= d6 && zoomLimits[1] >= d4)) {
                setXRange(d6, d4);
            }
        }
        if (this.mRenderer.isZoomYEnabled()) {
            d4 = d2 - (d3 / 2.0d);
            d3 = (d3 / 2.0d) + d2;
            if (obj == null || (zoomLimits[2] <= d4 && zoomLimits[3] >= d3)) {
                setYRange(d4, d3);
            }
        }
    }
}
