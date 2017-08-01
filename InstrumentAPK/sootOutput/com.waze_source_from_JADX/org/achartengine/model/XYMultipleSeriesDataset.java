package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XYMultipleSeriesDataset implements Serializable {
    private List<XYSeries> mSeries = new ArrayList();

    public synchronized void addSeries(XYSeries xYSeries) {
        this.mSeries.add(xYSeries);
    }

    public synchronized void removeSeries(int i) {
        this.mSeries.remove(i);
    }

    public synchronized void removeSeries(XYSeries xYSeries) {
        this.mSeries.remove(xYSeries);
    }

    public synchronized XYSeries getSeriesAt(int i) {
        return (XYSeries) this.mSeries.get(i);
    }

    public synchronized int getSeriesCount() {
        return this.mSeries.size();
    }

    public synchronized XYSeries[] getSeries() {
        return (XYSeries[]) this.mSeries.toArray(new XYSeries[0]);
    }
}
