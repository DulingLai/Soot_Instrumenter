package org.achartengine.model;

import java.util.Date;

public class TimeSeries extends XYSeries {
    public TimeSeries(String str) {
        super(str);
    }

    public synchronized void add(Date date, double d) {
        super.add((double) date.getTime(), d);
    }
}
