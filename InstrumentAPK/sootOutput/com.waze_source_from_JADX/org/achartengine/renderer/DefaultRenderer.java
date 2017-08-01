package org.achartengine.renderer;

import android.graphics.Paint.Align;
import android.graphics.Typeface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefaultRenderer implements Serializable {
    public static final int BACKGROUND_COLOR = -16777216;
    public static final int NO_COLOR = 0;
    private static final Typeface REGULAR_TEXT_FONT = Typeface.create(Typeface.SERIF, 0);
    public static final int TEXT_COLOR = -3355444;
    private boolean antialiasing = true;
    private boolean mApplyBackgroundColor;
    private int mAxesColor = TEXT_COLOR;
    private int mBackgroundColor;
    private int mLabelsColor = TEXT_COLOR;
    private float mLabelsTextSize = 10.0f;
    private int mLegendHeight = 0;
    private float mLegendTextSize = 12.0f;
    private int[] mMargins = new int[]{20, 30, 10, 0};
    private List<SimpleSeriesRenderer> mRenderers = new ArrayList();
    private boolean mShowAxes = true;
    private boolean mShowGrid = false;
    private boolean mShowLabels = true;
    private boolean mShowLegend = true;
    private String textTypefaceName = REGULAR_TEXT_FONT.toString();
    private int textTypefaceStyle = 0;
    private Align xLabelsAlign = Align.CENTER;
    private Align yLabelsAlign = Align.CENTER;

    public void addSeriesRenderer(SimpleSeriesRenderer simpleSeriesRenderer) {
        this.mRenderers.add(simpleSeriesRenderer);
    }

    public void removeSeriesRenderer(SimpleSeriesRenderer simpleSeriesRenderer) {
        this.mRenderers.remove(simpleSeriesRenderer);
    }

    public SimpleSeriesRenderer getSeriesRendererAt(int i) {
        return (SimpleSeriesRenderer) this.mRenderers.get(i);
    }

    public int getSeriesRendererCount() {
        return this.mRenderers.size();
    }

    public SimpleSeriesRenderer[] getSeriesRenderers() {
        return (SimpleSeriesRenderer[]) this.mRenderers.toArray(new SimpleSeriesRenderer[0]);
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public boolean isApplyBackgroundColor() {
        return this.mApplyBackgroundColor;
    }

    public void setApplyBackgroundColor(boolean z) {
        this.mApplyBackgroundColor = z;
    }

    public int getAxesColor() {
        return this.mAxesColor;
    }

    public void setAxesColor(int i) {
        this.mAxesColor = i;
    }

    public int getLabelsColor() {
        return this.mLabelsColor;
    }

    public void setLabelsColor(int i) {
        this.mLabelsColor = i;
    }

    public float getLabelsTextSize() {
        return this.mLabelsTextSize;
    }

    public void setLabelsTextSize(float f) {
        this.mLabelsTextSize = f;
    }

    public boolean isShowAxes() {
        return this.mShowAxes;
    }

    public void setShowAxes(boolean z) {
        this.mShowAxes = z;
    }

    public boolean isShowLabels() {
        return this.mShowLabels;
    }

    public void setShowLabels(boolean z) {
        this.mShowLabels = z;
    }

    public boolean isShowGrid() {
        return this.mShowGrid;
    }

    public void setShowGrid(boolean z) {
        this.mShowGrid = z;
    }

    public boolean isShowLegend() {
        return this.mShowLegend;
    }

    public void setShowLegend(boolean z) {
        this.mShowLegend = z;
    }

    public String getTextTypefaceName() {
        return this.textTypefaceName;
    }

    public int getTextTypefaceStyle() {
        return this.textTypefaceStyle;
    }

    public float getLegendTextSize() {
        return this.mLegendTextSize;
    }

    public void setLegendTextSize(float f) {
        this.mLegendTextSize = f;
    }

    public void setTextTypeface(String str, int i) {
        this.textTypefaceName = str;
        this.textTypefaceStyle = i;
    }

    public boolean isAntialiasing() {
        return this.antialiasing;
    }

    public void setAntialiasing(boolean z) {
        this.antialiasing = z;
    }

    public Align getXLabelsAlign() {
        return this.xLabelsAlign;
    }

    public void setXLabelsAlign(Align align) {
        this.xLabelsAlign = align;
    }

    public Align getYLabelsAlign() {
        return this.yLabelsAlign;
    }

    public void setYLabelsAlign(Align align) {
        this.yLabelsAlign = align;
    }

    public int getLegendHeight() {
        return this.mLegendHeight;
    }

    public void setLegendHeight(int i) {
        this.mLegendHeight = i;
    }

    public int[] getMargins() {
        return this.mMargins;
    }

    public void setMargins(int[] iArr) {
        this.mMargins = iArr;
    }
}
