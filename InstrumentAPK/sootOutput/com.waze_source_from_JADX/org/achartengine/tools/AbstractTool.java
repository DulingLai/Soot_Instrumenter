package org.achartengine.tools;

import org.achartengine.chart.XYChart;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public abstract class AbstractTool {
    protected XYChart mChart;
    protected XYMultipleSeriesRenderer mRenderer;

    public AbstractTool(XYChart xYChart) {
        this.mChart = xYChart;
        this.mRenderer = xYChart.getRenderer();
    }

    public double[] getRange() {
        double xAxisMin = this.mRenderer.getXAxisMin();
        double xAxisMax = this.mRenderer.getXAxisMax();
        double yAxisMin = this.mRenderer.getYAxisMin();
        double yAxisMax = this.mRenderer.getYAxisMax();
        return new double[]{xAxisMin, xAxisMax, yAxisMin, yAxisMax};
    }

    public void checkRange(double[] dArr) {
        double[] calcRange = this.mChart.getCalcRange();
        if (!this.mRenderer.isMinXSet()) {
            dArr[0] = calcRange[0];
            this.mRenderer.setXAxisMin(dArr[0]);
        }
        if (!this.mRenderer.isMaxXSet()) {
            dArr[1] = calcRange[1];
            this.mRenderer.setXAxisMax(dArr[1]);
        }
        if (!this.mRenderer.isMinYSet()) {
            dArr[2] = calcRange[2];
            this.mRenderer.setYAxisMin(dArr[2]);
        }
        if (!this.mRenderer.isMaxYSet()) {
            dArr[3] = calcRange[3];
            this.mRenderer.setYAxisMax(dArr[3]);
        }
    }

    protected void setXRange(double d, double d2) {
        this.mRenderer.setXAxisMin(d);
        this.mRenderer.setXAxisMax(d2);
    }

    protected void setYRange(double d, double d2) {
        this.mRenderer.setYAxisMin(d);
        this.mRenderer.setYAxisMax(d2);
    }
}
