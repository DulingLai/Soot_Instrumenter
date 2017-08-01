package org.achartengine.renderer;

import android.graphics.Color;
import com.waze.view.text.AutoResizeTextView;
import java.util.HashMap;
import java.util.Map;
import org.achartengine.util.MathHelper;

public class XYMultipleSeriesRenderer extends DefaultRenderer {
    private double[] initialRange = new double[]{this.mMinX, this.mMaxX, this.mMinY, this.mMaxY};
    private float mAxisTitleTextSize = 12.0f;
    private double mBarSpacing = 0.0d;
    private String mChartTitle = "";
    private float mChartTitleTextSize = AutoResizeTextView.MIN_TEXT_SIZE;
    private float mChartValuesTextSize = 10.0f;
    private boolean mDisplayChartValues;
    private int mGridColor = Color.argb(75, 200, 200, 200);
    private int mMarginsColor = 0;
    private double mMaxX = -1.7976931348623157E308d;
    private double mMaxY = -1.7976931348623157E308d;
    private double mMinX = MathHelper.NULL_VALUE;
    private double mMinY = MathHelper.NULL_VALUE;
    private Orientation mOrientation = Orientation.HORIZONTAL;
    private double[] mPanLimits;
    private boolean mPanXEnabled = true;
    private boolean mPanYEnabled = true;
    private float mPointSize = 3.0f;
    private int mXLabels = 5;
    private float mXLabelsAngle;
    private Map<Double, String> mXTextLabels = new HashMap();
    private String mXTitle = "";
    private int mYLabels = 5;
    private float mYLabelsAngle;
    private String mYTitle = "";
    private double[] mZoomLimits;
    private float mZoomRate = 1.5f;
    private boolean mZoomXEnabled = true;
    private boolean mZoomYEnabled = true;

    public enum Orientation {
        HORIZONTAL(0),
        VERTICAL(90);
        
        private int mAngle;

        private Orientation(int i) {
            this.mAngle = 0;
            this.mAngle = i;
        }

        public int getAngle() {
            return this.mAngle;
        }
    }

    public Orientation getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(Orientation orientation) {
        this.mOrientation = orientation;
    }

    public String getChartTitle() {
        return this.mChartTitle;
    }

    public void setChartTitle(String str) {
        this.mChartTitle = str;
    }

    public float getChartTitleTextSize() {
        return this.mChartTitleTextSize;
    }

    public void setChartTitleTextSize(float f) {
        this.mChartTitleTextSize = f;
    }

    public String getXTitle() {
        return this.mXTitle;
    }

    public void setXTitle(String str) {
        this.mXTitle = str;
    }

    public String getYTitle() {
        return this.mYTitle;
    }

    public void setYTitle(String str) {
        this.mYTitle = str;
    }

    public float getAxisTitleTextSize() {
        return this.mAxisTitleTextSize;
    }

    public void setAxisTitleTextSize(float f) {
        this.mAxisTitleTextSize = f;
    }

    public double getXAxisMin() {
        return this.mMinX;
    }

    public void setXAxisMin(double d) {
        if (!isMinXSet()) {
            this.initialRange[0] = d;
        }
        this.mMinX = d;
    }

    public boolean isMinXSet() {
        return this.mMinX != MathHelper.NULL_VALUE;
    }

    public double getXAxisMax() {
        return this.mMaxX;
    }

    public void setXAxisMax(double d) {
        if (!isMaxXSet()) {
            this.initialRange[1] = d;
        }
        this.mMaxX = d;
    }

    public boolean isMaxXSet() {
        return this.mMaxX != -1.7976931348623157E308d;
    }

    public double getYAxisMin() {
        return this.mMinY;
    }

    public void setYAxisMin(double d) {
        if (!isMinYSet()) {
            this.initialRange[2] = d;
        }
        this.mMinY = d;
    }

    public boolean isMinYSet() {
        return this.mMinY != MathHelper.NULL_VALUE;
    }

    public double getYAxisMax() {
        return this.mMaxY;
    }

    public void setYAxisMax(double d) {
        if (!isMaxYSet()) {
            this.initialRange[3] = d;
        }
        this.mMaxY = d;
    }

    public boolean isMaxYSet() {
        return this.mMaxY != -1.7976931348623157E308d;
    }

    public int getXLabels() {
        return this.mXLabels;
    }

    public void setXLabels(int i) {
        this.mXLabels = i;
    }

    public void addTextLabel(double d, String str) {
        this.mXTextLabels.put(Double.valueOf(d), str);
    }

    public String getXTextLabel(Double d) {
        return (String) this.mXTextLabels.get(d);
    }

    public Double[] getXTextLabelLocations() {
        return (Double[]) this.mXTextLabels.keySet().toArray(new Double[0]);
    }

    public int getYLabels() {
        return this.mYLabels;
    }

    public void setYLabels(int i) {
        this.mYLabels = i;
    }

    public boolean isDisplayChartValues() {
        return this.mDisplayChartValues;
    }

    public void setDisplayChartValues(boolean z) {
        this.mDisplayChartValues = z;
    }

    public float getChartValuesTextSize() {
        return this.mChartValuesTextSize;
    }

    public void setChartValuesTextSize(float f) {
        this.mChartValuesTextSize = f;
    }

    public boolean isPanXEnabled() {
        return this.mPanXEnabled;
    }

    public boolean isPanYEnabled() {
        return this.mPanYEnabled;
    }

    public void setPanEnabled(boolean z, boolean z2) {
        this.mPanXEnabled = z;
        this.mPanYEnabled = z2;
    }

    public boolean isZoomXEnabled() {
        return this.mZoomXEnabled;
    }

    public boolean isZoomYEnabled() {
        return this.mZoomYEnabled;
    }

    public void setZoomEnabled(boolean z, boolean z2) {
        this.mZoomXEnabled = z;
        this.mZoomYEnabled = z2;
    }

    public float getZoomRate() {
        return this.mZoomRate;
    }

    public void setZoomRate(float f) {
        this.mZoomRate = f;
    }

    public double getBarsSpacing() {
        return this.mBarSpacing;
    }

    public void setBarSpacing(double d) {
        this.mBarSpacing = d;
    }

    public int getMarginsColor() {
        return this.mMarginsColor;
    }

    public void setMarginsColor(int i) {
        this.mMarginsColor = i;
    }

    public int getGridColor() {
        return this.mGridColor;
    }

    public void setGridColor(int i) {
        this.mGridColor = i;
    }

    public double[] getPanLimits() {
        return this.mPanLimits;
    }

    public void setPanLimits(double[] dArr) {
        this.mPanLimits = dArr;
    }

    public double[] getZoomLimits() {
        return this.mZoomLimits;
    }

    public void setZoomLimits(double[] dArr) {
        this.mZoomLimits = dArr;
    }

    public float getXLabelsAngle() {
        return this.mXLabelsAngle;
    }

    public void setXLabelsAngle(float f) {
        this.mXLabelsAngle = f;
    }

    public float getYLabelsAngle() {
        return this.mYLabelsAngle;
    }

    public void setYLabelsAngle(float f) {
        this.mYLabelsAngle = f;
    }

    public float getPointSize() {
        return this.mPointSize;
    }

    public void setPointSize(float f) {
        this.mPointSize = f;
    }

    public void setRange(double[] dArr) {
        setXAxisMin(dArr[0]);
        setXAxisMax(dArr[1]);
        setYAxisMin(dArr[2]);
        setYAxisMax(dArr[3]);
    }

    public boolean isInitialRangeSet() {
        return isMinXSet() && isMaxXSet() && isMinYSet() && isMaxYSet();
    }

    public void setInitialRange(double[] dArr) {
        this.initialRange = dArr;
    }

    public double[] getInitialRange() {
        return this.initialRange;
    }
}
