package org.achartengine.model;

import java.util.ArrayList;
import java.util.List;
import org.achartengine.util.MathHelper;

public class XYValueSeries extends XYSeries {
    private double mMaxValue = -1.7976931348623157E308d;
    private double mMinValue = MathHelper.NULL_VALUE;
    private List<Double> mValue = new ArrayList();

    public XYValueSeries(String str) {
        super(str);
    }

    public synchronized void add(double d, double d2, double d3) {
        super.add(d, d2);
        this.mValue.add(Double.valueOf(d3));
        updateRange(d3);
    }

    private void initRange() {
        this.mMinValue = MathHelper.NULL_VALUE;
        this.mMaxValue = MathHelper.NULL_VALUE;
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            updateRange(getValue(i));
        }
    }

    private void updateRange(double d) {
        this.mMinValue = Math.min(this.mMinValue, d);
        this.mMaxValue = Math.max(this.mMaxValue, d);
    }

    public synchronized void add(double d, double d2) {
        add(d, d2, 0.0d);
    }

    public synchronized void remove(int i) {
        super.remove(i);
        double doubleValue = ((Double) this.mValue.remove(i)).doubleValue();
        if (doubleValue == this.mMinValue || doubleValue == this.mMaxValue) {
            initRange();
        }
    }

    public synchronized void clear() {
        super.clear();
        this.mValue.clear();
        initRange();
    }

    public synchronized double getValue(int i) {
        return ((Double) this.mValue.get(i)).doubleValue();
    }

    public double getMinValue() {
        return this.mMinValue;
    }

    public double getMaxValue() {
        return this.mMaxValue;
    }
}
