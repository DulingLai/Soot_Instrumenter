package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.waze.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.util.MathHelper;

public abstract class XYChart extends AbstractChart {
    private double[] calcRange = new double[4];
    private PointF mCenter;
    protected XYMultipleSeriesDataset mDataset;
    protected XYMultipleSeriesRenderer mRenderer;
    private float mScale;
    private float mTranslate;
    private Rect screenR;

    public abstract void drawSeries(Canvas canvas, Paint paint, float[] fArr, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i);

    public XYChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        this.mDataset = xYMultipleSeriesDataset;
        this.mRenderer = xYMultipleSeriesRenderer;
    }

    public void draw(Canvas canvas, int i, int i2, int i3, int i4, Paint paint) {
        int i5;
        int i6;
        Object obj;
        double d;
        double d2;
        int i7;
        Object obj2;
        paint.setAntiAlias(this.mRenderer.isAntialiasing());
        int legendHeight = this.mRenderer.getLegendHeight();
        if (this.mRenderer.isShowLegend() && legendHeight == 0) {
            i5 = i4 / 5;
        } else {
            i5 = legendHeight;
        }
        int[] margins = this.mRenderer.getMargins();
        int i8 = i + margins[1];
        int i9 = i2 + margins[0];
        int i10 = (i + i3) - margins[3];
        int i11 = ((i2 + i4) - margins[2]) - i5;
        if (this.screenR == null) {
            this.screenR = new Rect();
        }
        this.screenR.set(i8, i9, i10, i11);
        drawBackground(this.mRenderer, canvas, i, i2, i3, i4, paint, false, 0);
        if (!(paint.getTypeface() != null && paint.getTypeface().toString().equals(this.mRenderer.getTextTypefaceName()) && paint.getTypeface().getStyle() == this.mRenderer.getTextTypefaceStyle())) {
            paint.setTypeface(Typeface.create(this.mRenderer.getTextTypefaceName(), this.mRenderer.getTextTypefaceStyle()));
        }
        Orientation orientation = this.mRenderer.getOrientation();
        if (orientation == Orientation.VERTICAL) {
            i11 += i5 - 20;
            i6 = i10 - i5;
        } else {
            i6 = i10;
        }
        int angle = orientation.getAngle();
        if (angle == 90) {
            obj = 1;
        } else {
            obj = null;
        }
        this.mScale = ((float) i4) / ((float) i3);
        this.mTranslate = (float) (Math.abs(i3 - i4) / 2);
        if (this.mScale < 1.0f) {
            this.mTranslate *= -1.0f;
        }
        this.mCenter = new PointF((float) ((i + i3) / 2), (float) ((i2 + i4) / 2));
        if (obj != null) {
            transform(canvas, (float) angle, false);
        }
        double xAxisMin = this.mRenderer.getXAxisMin();
        double xAxisMax = this.mRenderer.getXAxisMax();
        double yAxisMin = this.mRenderer.getYAxisMin();
        double yAxisMax = this.mRenderer.getYAxisMax();
        boolean isMinXSet = this.mRenderer.isMinXSet();
        boolean isMaxXSet = this.mRenderer.isMaxXSet();
        boolean isMinYSet = this.mRenderer.isMinYSet();
        boolean isMaxYSet = this.mRenderer.isMaxYSet();
        int seriesCount = this.mDataset.getSeriesCount();
        String[] strArr = new String[seriesCount];
        int i12 = 0;
        while (i12 < seriesCount) {
            double d3;
            double d4;
            double d5;
            XYSeries seriesAt = this.mDataset.getSeriesAt(i12);
            strArr[i12] = seriesAt.getTitle();
            if (seriesAt.getItemCount() == 0) {
                d3 = yAxisMax;
                d4 = yAxisMin;
                d5 = xAxisMax;
            } else {
                if (!isMinXSet) {
                    xAxisMin = Math.min(xAxisMin, seriesAt.getMinX());
                    this.calcRange[0] = xAxisMin;
                }
                if (isMaxXSet) {
                    d5 = xAxisMax;
                } else {
                    d5 = Math.max(xAxisMax, seriesAt.getMaxX());
                    this.calcRange[1] = d5;
                }
                if (isMinYSet) {
                    d4 = yAxisMin;
                } else {
                    d4 = Math.min(yAxisMin, (double) ((float) seriesAt.getMinY()));
                    this.calcRange[2] = d4;
                }
                if (isMaxYSet) {
                    d3 = yAxisMax;
                } else {
                    d3 = Math.max(yAxisMax, (double) ((float) seriesAt.getMaxY()));
                    this.calcRange[3] = d3;
                }
            }
            i12++;
            yAxisMax = d3;
            yAxisMin = d4;
            xAxisMax = d5;
        }
        if (xAxisMax - xAxisMin != 0.0d) {
            d = ((double) (i6 - i8)) / (xAxisMax - xAxisMin);
        } else {
            d = 0.0d;
        }
        if (yAxisMax - yAxisMin != 0.0d) {
            d2 = (double) ((float) (((double) (i11 - i9)) / (yAxisMax - yAxisMin)));
        } else {
            d2 = 0.0d;
        }
        Object obj3 = null;
        for (i7 = 0; i7 < seriesCount; i7++) {
            int itemCount;
            XYSeries seriesAt2 = this.mDataset.getSeriesAt(i7);
            if (seriesAt2.getItemCount() != 0) {
                SimpleSeriesRenderer seriesRendererAt = this.mRenderer.getSeriesRendererAt(i7);
                itemCount = seriesAt2.getItemCount() * 2;
                List arrayList = new ArrayList();
                for (i10 = 0; i10 < itemCount; i10 += 2) {
                    legendHeight = i10 / 2;
                    d4 = seriesAt2.getY(legendHeight);
                    if (d4 != MathHelper.NULL_VALUE) {
                        arrayList.add(Float.valueOf((float) (((double) i8) + ((seriesAt2.getX(legendHeight) - xAxisMin) * d))));
                        arrayList.add(Float.valueOf((float) (((double) i11) - ((d4 - yAxisMin) * d2))));
                    } else if (arrayList.size() > 0) {
                        drawSeries(seriesAt2, canvas, paint, arrayList, seriesRendererAt, Math.min((float) i11, (float) (((double) i11) + (d2 * yAxisMin))), i7, orientation);
                        arrayList.clear();
                    }
                }
                if (arrayList.size() > 0) {
                    drawSeries(seriesAt2, canvas, paint, arrayList, seriesRendererAt, Math.min((float) i11, (float) (((double) i11) + (d2 * yAxisMin))), i7, orientation);
                }
                legendHeight = 1;
            }
        }
        drawBackground(this.mRenderer, canvas, i, i11, i3, i4 - i11, paint, true, this.mRenderer.getMarginsColor());
        drawBackground(this.mRenderer, canvas, i, i2, i3, margins[0], paint, true, this.mRenderer.getMarginsColor());
        if (orientation == Orientation.HORIZONTAL) {
            drawBackground(this.mRenderer, canvas, i, i2, i8 - i, i4 - i2, paint, true, this.mRenderer.getMarginsColor());
            drawBackground(this.mRenderer, canvas, i6, i2, margins[3], i4 - i2, paint, true, this.mRenderer.getMarginsColor());
        } else if (orientation == Orientation.VERTICAL) {
            drawBackground(this.mRenderer, canvas, i6, i2, i3 - i6, i4 - i2, paint, true, this.mRenderer.getMarginsColor());
            drawBackground(this.mRenderer, canvas, i, i2, i8 - i, i4 - i2, paint, true, this.mRenderer.getMarginsColor());
        }
        if (!this.mRenderer.isShowLabels() || r4 == null) {
            obj2 = null;
        } else {
            obj2 = 1;
        }
        boolean isShowGrid = this.mRenderer.isShowGrid();
        if (obj2 != null || isShowGrid) {
            int labelsTextSize;
            double doubleValue;
            float f;
            float axisTitleTextSize;
            List validLabels = getValidLabels(MathHelper.getLabels(xAxisMin, xAxisMax, this.mRenderer.getXLabels()));
            List validLabels2 = getValidLabels(MathHelper.getLabels(yAxisMin, yAxisMax, this.mRenderer.getYLabels()));
            if (obj2 != null) {
                paint.setColor(this.mRenderer.getLabelsColor());
                paint.setTextSize(this.mRenderer.getLabelsTextSize());
                paint.setTextAlign(this.mRenderer.getXLabelsAlign());
                if (this.mRenderer.getXLabelsAlign() == Align.LEFT) {
                    labelsTextSize = (int) (((float) i8) + (this.mRenderer.getLabelsTextSize() / 4.0f));
                    drawXLabels(validLabels, this.mRenderer.getXTextLabelLocations(), canvas, paint, labelsTextSize, i9, i11, d, xAxisMin);
                    paint.setTextAlign(this.mRenderer.getYLabelsAlign());
                    itemCount = validLabels2.size();
                    for (i7 = 0; i7 < itemCount; i7++) {
                        doubleValue = ((Double) validLabels2.get(i7)).doubleValue();
                        f = (float) (((double) i11) - ((doubleValue - yAxisMin) * d2));
                        if (orientation == Orientation.HORIZONTAL) {
                            if (obj2 != null) {
                                paint.setColor(this.mRenderer.getLabelsColor());
                                canvas.drawLine((float) (i8 - 4), f, (float) i8, f, paint);
                                drawText(canvas, getLabel(doubleValue), (float) (i8 - 2), f - LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, paint, this.mRenderer.getYLabelsAngle());
                            }
                            if (isShowGrid) {
                                paint.setColor(this.mRenderer.getGridColor());
                                canvas.drawLine((float) i8, f, (float) i6, f, paint);
                            }
                        } else if (orientation != Orientation.VERTICAL) {
                            if (obj2 != null) {
                                paint.setColor(this.mRenderer.getLabelsColor());
                                canvas.drawLine((float) (i6 + 4), f, (float) i6, f, paint);
                                drawText(canvas, getLabel(doubleValue), (float) (i6 + 10), f - LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, paint, this.mRenderer.getYLabelsAngle());
                            }
                            if (isShowGrid) {
                                paint.setColor(this.mRenderer.getGridColor());
                                canvas.drawLine((float) i6, f, (float) i8, f, paint);
                            }
                        }
                    }
                    if (obj2 != null) {
                        paint.setColor(this.mRenderer.getLabelsColor());
                        axisTitleTextSize = this.mRenderer.getAxisTitleTextSize();
                        paint.setTextSize(axisTitleTextSize);
                        paint.setTextAlign(Align.CENTER);
                        if (orientation == Orientation.HORIZONTAL) {
                            drawText(canvas, this.mRenderer.getXTitle(), (float) ((i3 / 2) + i), (((float) i11) + ((this.mRenderer.getLabelsTextSize() * 4.0f) / 3.0f)) + axisTitleTextSize, paint, 0.0f);
                            drawText(canvas, this.mRenderer.getYTitle(), ((float) i) + axisTitleTextSize, (float) ((i4 / 2) + i2), paint, -90.0f);
                            paint.setTextSize(this.mRenderer.getChartTitleTextSize());
                            drawText(canvas, this.mRenderer.getChartTitle(), (float) ((i3 / 2) + i), ((float) i2) + this.mRenderer.getChartTitleTextSize(), paint, 0.0f);
                        } else if (orientation == Orientation.VERTICAL) {
                            drawText(canvas, this.mRenderer.getXTitle(), (float) ((i3 / 2) + i), ((float) (i2 + i4)) - axisTitleTextSize, paint, -90.0f);
                            drawText(canvas, this.mRenderer.getYTitle(), (float) (i6 + 20), (float) ((i4 / 2) + i2), paint, 0.0f);
                            paint.setTextSize(this.mRenderer.getChartTitleTextSize());
                            drawText(canvas, this.mRenderer.getChartTitle(), ((float) i) + axisTitleTextSize, (float) ((i4 / 2) + i9), paint, 0.0f);
                        }
                    }
                }
            }
            labelsTextSize = i8;
            drawXLabels(validLabels, this.mRenderer.getXTextLabelLocations(), canvas, paint, labelsTextSize, i9, i11, d, xAxisMin);
            paint.setTextAlign(this.mRenderer.getYLabelsAlign());
            itemCount = validLabels2.size();
            for (i7 = 0; i7 < itemCount; i7++) {
                doubleValue = ((Double) validLabels2.get(i7)).doubleValue();
                f = (float) (((double) i11) - ((doubleValue - yAxisMin) * d2));
                if (orientation == Orientation.HORIZONTAL) {
                    if (obj2 != null) {
                        paint.setColor(this.mRenderer.getLabelsColor());
                        canvas.drawLine((float) (i8 - 4), f, (float) i8, f, paint);
                        drawText(canvas, getLabel(doubleValue), (float) (i8 - 2), f - LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, paint, this.mRenderer.getYLabelsAngle());
                    }
                    if (isShowGrid) {
                        paint.setColor(this.mRenderer.getGridColor());
                        canvas.drawLine((float) i8, f, (float) i6, f, paint);
                    }
                } else if (orientation != Orientation.VERTICAL) {
                    if (obj2 != null) {
                        paint.setColor(this.mRenderer.getLabelsColor());
                        canvas.drawLine((float) (i6 + 4), f, (float) i6, f, paint);
                        drawText(canvas, getLabel(doubleValue), (float) (i6 + 10), f - LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, paint, this.mRenderer.getYLabelsAngle());
                    }
                    if (isShowGrid) {
                        paint.setColor(this.mRenderer.getGridColor());
                        canvas.drawLine((float) i6, f, (float) i8, f, paint);
                    }
                }
            }
            if (obj2 != null) {
                paint.setColor(this.mRenderer.getLabelsColor());
                axisTitleTextSize = this.mRenderer.getAxisTitleTextSize();
                paint.setTextSize(axisTitleTextSize);
                paint.setTextAlign(Align.CENTER);
                if (orientation == Orientation.HORIZONTAL) {
                    drawText(canvas, this.mRenderer.getXTitle(), (float) ((i3 / 2) + i), (((float) i11) + ((this.mRenderer.getLabelsTextSize() * 4.0f) / 3.0f)) + axisTitleTextSize, paint, 0.0f);
                    drawText(canvas, this.mRenderer.getYTitle(), ((float) i) + axisTitleTextSize, (float) ((i4 / 2) + i2), paint, -90.0f);
                    paint.setTextSize(this.mRenderer.getChartTitleTextSize());
                    drawText(canvas, this.mRenderer.getChartTitle(), (float) ((i3 / 2) + i), ((float) i2) + this.mRenderer.getChartTitleTextSize(), paint, 0.0f);
                } else if (orientation == Orientation.VERTICAL) {
                    drawText(canvas, this.mRenderer.getXTitle(), (float) ((i3 / 2) + i), ((float) (i2 + i4)) - axisTitleTextSize, paint, -90.0f);
                    drawText(canvas, this.mRenderer.getYTitle(), (float) (i6 + 20), (float) ((i4 / 2) + i2), paint, 0.0f);
                    paint.setTextSize(this.mRenderer.getChartTitleTextSize());
                    drawText(canvas, this.mRenderer.getChartTitle(), ((float) i) + axisTitleTextSize, (float) ((i4 / 2) + i9), paint, 0.0f);
                }
            }
        }
        if (orientation == Orientation.HORIZONTAL) {
            drawLegend(canvas, this.mRenderer, strArr, i8, i6, i2, i3, i4, i5, paint);
        } else if (orientation == Orientation.VERTICAL) {
            transform(canvas, (float) angle, true);
            drawLegend(canvas, this.mRenderer, strArr, i8, i6, i2, i3, i4, i5, paint);
            transform(canvas, (float) angle, false);
        }
        if (this.mRenderer.isShowAxes()) {
            paint.setColor(this.mRenderer.getAxesColor());
            canvas.drawLine((float) i8, (float) i11, (float) i6, (float) i11, paint);
            if (orientation == Orientation.HORIZONTAL) {
                canvas.drawLine((float) i8, (float) i9, (float) i8, (float) i11, paint);
            } else if (orientation == Orientation.VERTICAL) {
                canvas.drawLine((float) i6, (float) i9, (float) i6, (float) i11, paint);
            }
        }
        if (obj != null) {
            transform(canvas, (float) angle, true);
        }
    }

    private List<Double> getValidLabels(List<Double> list) {
        List<Double> arrayList = new ArrayList(list);
        for (Double d : list) {
            if (d.isNaN()) {
                arrayList.remove(d);
            }
        }
        return arrayList;
    }

    private void drawSeries(XYSeries xYSeries, Canvas canvas, Paint paint, List<Float> list, SimpleSeriesRenderer simpleSeriesRenderer, float f, int i, Orientation orientation) {
        float[] floats = MathHelper.getFloats(list);
        drawSeries(canvas, paint, floats, simpleSeriesRenderer, f, i);
        if (isRenderPoints(simpleSeriesRenderer)) {
            ScatterChart pointsChart = getPointsChart();
            if (pointsChart != null) {
                pointsChart.drawSeries(canvas, paint, floats, simpleSeriesRenderer, 0.0f, i);
            }
        }
        paint.setTextSize(this.mRenderer.getChartValuesTextSize());
        if (orientation == Orientation.HORIZONTAL) {
            paint.setTextAlign(Align.CENTER);
        } else {
            paint.setTextAlign(Align.LEFT);
        }
        if (this.mRenderer.isDisplayChartValues()) {
            drawChartValuesText(canvas, xYSeries, paint, floats, i);
        }
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries xYSeries, Paint paint, float[] fArr, int i) {
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            drawText(canvas, getLabel(xYSeries.getY(i2 / 2)), fArr[i2], fArr[i2 + 1] - 3.5f, paint, 0.0f);
        }
    }

    protected void drawText(Canvas canvas, String str, float f, float f2, Paint paint, float f3) {
        float f4 = ((float) (-this.mRenderer.getOrientation().getAngle())) + f3;
        if (f4 != 0.0f) {
            canvas.rotate(f4, f, f2);
        }
        canvas.drawText(str, f, f2, paint);
        if (f4 != 0.0f) {
            canvas.rotate(-f4, f, f2);
        }
    }

    private void transform(Canvas canvas, float f, boolean z) {
        if (z) {
            canvas.scale(1.0f / this.mScale, this.mScale);
            canvas.translate(this.mTranslate, -this.mTranslate);
            canvas.rotate(-f, this.mCenter.x, this.mCenter.y);
            return;
        }
        canvas.rotate(f, this.mCenter.x, this.mCenter.y);
        canvas.translate(-this.mTranslate, this.mTranslate);
        canvas.scale(this.mScale, 1.0f / this.mScale);
    }

    protected String getLabel(double d) {
        String str = "";
        if (d == ((double) Math.round(d))) {
            return Math.round(d) + "";
        }
        return d + "";
    }

    protected void drawXLabels(List<Double> list, Double[] dArr, Canvas canvas, Paint paint, int i, int i2, int i3, double d, double d2) {
        int size = list.size();
        boolean isShowLabels = this.mRenderer.isShowLabels();
        boolean isShowGrid = this.mRenderer.isShowGrid();
        for (int i4 = 0; i4 < size; i4++) {
            double doubleValue = ((Double) list.get(i4)).doubleValue();
            float f = (float) (((double) i) + ((doubleValue - d2) * d));
            if (isShowLabels) {
                paint.setColor(this.mRenderer.getLabelsColor());
                canvas.drawLine(f, (float) i3, f, ((float) i3) + (this.mRenderer.getLabelsTextSize() / 3.0f), paint);
                drawText(canvas, getLabel(doubleValue), f, ((float) i3) + ((this.mRenderer.getLabelsTextSize() * 4.0f) / 3.0f), paint, this.mRenderer.getXLabelsAngle());
            }
            if (isShowGrid) {
                paint.setColor(this.mRenderer.getGridColor());
                canvas.drawLine(f, (float) i3, f, (float) i2, paint);
            }
        }
        if (isShowLabels) {
            paint.setColor(this.mRenderer.getLabelsColor());
            for (Double d3 : dArr) {
                f = (float) (((double) i) + ((d3.doubleValue() - d2) * d));
                canvas.drawLine(f, (float) i3, f, (float) (i3 + 4), paint);
                drawText(canvas, this.mRenderer.getXTextLabel(d3), f, ((float) i3) + this.mRenderer.getLabelsTextSize(), paint, this.mRenderer.getXLabelsAngle());
            }
        }
    }

    public XYMultipleSeriesRenderer getRenderer() {
        return this.mRenderer;
    }

    public XYMultipleSeriesDataset getDataset() {
        return this.mDataset;
    }

    public double[] getCalcRange() {
        return this.calcRange;
    }

    public PointF toRealPoint(float f, float f2) {
        double xAxisMin = this.mRenderer.getXAxisMin();
        double xAxisMax = this.mRenderer.getXAxisMax();
        double yAxisMin = this.mRenderer.getYAxisMin();
        return new PointF((float) (xAxisMin + (((xAxisMax - xAxisMin) * ((double) (f - ((float) this.screenR.left)))) / ((double) this.screenR.width()))), (float) (((((double) (((float) (this.screenR.top + this.screenR.height())) - f2)) * (this.mRenderer.getYAxisMax() - yAxisMin)) / ((double) this.screenR.height())) + yAxisMin));
    }

    public PointF toScreenPoint(PointF pointF) {
        double xAxisMin = this.mRenderer.getXAxisMin();
        double xAxisMax = this.mRenderer.getXAxisMax();
        double yAxisMin = this.mRenderer.getYAxisMin();
        double yAxisMax = this.mRenderer.getYAxisMax();
        return new PointF((float) ((((((double) pointF.x) - xAxisMin) * ((double) this.screenR.width())) / (xAxisMax - xAxisMin)) + ((double) this.screenR.left)), (float) ((((yAxisMax - ((double) pointF.y)) * ((double) this.screenR.height())) / (yAxisMax - yAxisMin)) + ((double) this.screenR.top)));
    }

    public boolean isRenderPoints(SimpleSeriesRenderer simpleSeriesRenderer) {
        return false;
    }

    public double getDefaultMinimum() {
        return MathHelper.NULL_VALUE;
    }

    public ScatterChart getPointsChart() {
        return null;
    }
}
