package org.achartengine.tools;

import org.achartengine.chart.XYChart;
import org.achartengine.model.XYSeries;

public class FitZoom extends AbstractTool {
    public FitZoom(XYChart xYChart) {
        super(xYChart);
    }

    public void apply() {
        if (this.mChart.getDataset() != null) {
            if (this.mRenderer.isInitialRangeSet()) {
                this.mRenderer.setRange(this.mRenderer.getInitialRange());
                return;
            }
            XYSeries[] series = this.mChart.getDataset().getSeries();
            int length = series.length;
            if (length > 0) {
                double[] dArr = new double[]{series[0].getMinX(), series[0].getMaxX(), Math.min(this.mChart.getDefaultMinimum(), series[0].getMinY()), series[0].getMaxY()};
                for (int i = 1; i < length; i++) {
                    dArr[0] = Math.min(dArr[0], series[i].getMinX());
                    dArr[1] = Math.max(dArr[1], series[i].getMaxX());
                    dArr[2] = Math.min(dArr[2], series[i].getMinY());
                    dArr[3] = Math.max(dArr[3], series[i].getMaxY());
                }
                double abs = Math.abs(dArr[1] - dArr[0]) / 40.0d;
                double abs2 = Math.abs(dArr[3] - dArr[2]) / 40.0d;
                this.mRenderer.setRange(new double[]{dArr[0] - abs, abs + dArr[1], dArr[2] - abs2, abs2 + dArr[3]});
            }
        }
    }
}
