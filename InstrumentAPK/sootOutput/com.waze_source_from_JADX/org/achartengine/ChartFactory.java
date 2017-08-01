package org.achartengine;

import android.content.Context;
import android.content.Intent;
import java.io.Serializable;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.DialChart;
import org.achartengine.chart.DoughnutChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PieChart;
import org.achartengine.chart.RangeBarChart;
import org.achartengine.chart.ScatterChart;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class ChartFactory {
    public static final String CHART = "chart";
    public static final String TITLE = "title";

    private ChartFactory() {
    }

    public static final GraphicalView getLineChartView(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new GraphicalView(context, new LineChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer));
    }

    public static final GraphicalView getScatterChartView(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new GraphicalView(context, new ScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer));
    }

    public static final GraphicalView getBubbleChartView(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new GraphicalView(context, new BubbleChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer));
    }

    public static final GraphicalView getTimeChartView(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        AbstractChart timeChart = new TimeChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        timeChart.setDateFormat(str);
        return new GraphicalView(context, timeChart);
    }

    public static final GraphicalView getBarChartView(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new GraphicalView(context, new BarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type));
    }

    public static final GraphicalView getRangeBarChartView(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        return new GraphicalView(context, new RangeBarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type));
    }

    public static final GraphicalView getPieChartView(Context context, CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        checkParameters(categorySeries, defaultRenderer);
        return new GraphicalView(context, new PieChart(categorySeries, defaultRenderer));
    }

    public static final GraphicalView getDialChartView(Context context, CategorySeries categorySeries, DialRenderer dialRenderer) {
        checkParameters(categorySeries, (DefaultRenderer) dialRenderer);
        return new GraphicalView(context, new DialChart(categorySeries, dialRenderer));
    }

    public static final GraphicalView getDoughnutChartView(Context context, MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer) {
        checkParameters(multipleCategorySeries, defaultRenderer);
        return new GraphicalView(context, new DoughnutChart(multipleCategorySeries, defaultRenderer));
    }

    public static final Intent getLineChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        return getLineChartIntent(context, xYMultipleSeriesDataset, xYMultipleSeriesRenderer, "");
    }

    public static final Intent getScatterChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        return getScatterChartIntent(context, xYMultipleSeriesDataset, xYMultipleSeriesRenderer, "");
    }

    public static final Intent getBubbleChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        return getBubbleChartIntent(context, xYMultipleSeriesDataset, xYMultipleSeriesRenderer, "");
    }

    public static final Intent getTimeChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str) {
        return getTimeChartIntent(context, xYMultipleSeriesDataset, xYMultipleSeriesRenderer, str, "");
    }

    public static final Intent getBarChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type) {
        return getBarChartIntent(context, xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type, "");
    }

    public static final Intent getLineChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new LineChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getScatterChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new ScatterChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getBubbleChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new BubbleChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getTimeChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, String str, String str2) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        Serializable timeChart = new TimeChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        timeChart.setDateFormat(str);
        intent.putExtra(CHART, timeChart);
        intent.putExtra("title", str2);
        return intent;
    }

    public static final Intent getBarChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new BarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getRangeBarChartIntent(Context context, XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer, Type type, String str) {
        checkParameters(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new RangeBarChart(xYMultipleSeriesDataset, xYMultipleSeriesRenderer, type));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getPieChartIntent(Context context, CategorySeries categorySeries, DefaultRenderer defaultRenderer, String str) {
        checkParameters(categorySeries, defaultRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new PieChart(categorySeries, defaultRenderer));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getDoughnutChartIntent(Context context, MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer, String str) {
        checkParameters(multipleCategorySeries, defaultRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new DoughnutChart(multipleCategorySeries, defaultRenderer));
        intent.putExtra("title", str);
        return intent;
    }

    public static final Intent getDialChartIntent(Context context, CategorySeries categorySeries, DialRenderer dialRenderer, String str) {
        checkParameters(categorySeries, (DefaultRenderer) dialRenderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        intent.putExtra(CHART, new DialChart(categorySeries, dialRenderer));
        intent.putExtra("title", str);
        return intent;
    }

    private static void checkParameters(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        if (xYMultipleSeriesDataset == null || xYMultipleSeriesRenderer == null || xYMultipleSeriesDataset.getSeriesCount() != xYMultipleSeriesRenderer.getSeriesRendererCount()) {
            throw new IllegalArgumentException("Dataset and renderer should be not null and should have the same number of series");
        }
    }

    private static void checkParameters(CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        if (categorySeries == null || defaultRenderer == null || categorySeries.getItemCount() != defaultRenderer.getSeriesRendererCount()) {
            throw new IllegalArgumentException("Dataset and renderer should be not null and the dataset number of items should be equal to the number of series renderers");
        }
    }

    private static void checkParameters(MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer) {
        if (multipleCategorySeries == null || defaultRenderer == null || !checkMultipleSeriesItems(multipleCategorySeries, defaultRenderer.getSeriesRendererCount())) {
            throw new IllegalArgumentException("Titles and values should be not null and the dataset number of items should be equal to the number of series renderers");
        }
    }

    private static boolean checkMultipleSeriesItems(MultipleCategorySeries multipleCategorySeries, int i) {
        int categoriesCount = multipleCategorySeries.getCategoriesCount();
        boolean z = true;
        for (int i2 = 0; i2 < categoriesCount && z; i2++) {
            if (multipleCategorySeries.getValues(i2).length == multipleCategorySeries.getTitles(i2).length) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }
}
