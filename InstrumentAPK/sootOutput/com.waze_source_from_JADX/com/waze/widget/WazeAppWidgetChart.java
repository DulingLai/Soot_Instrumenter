package com.waze.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.support.v4.internal.view.SupportMenu;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.WazeLang;
import com.waze.widget.routing.RouteScore;
import com.waze.widget.routing.RouteScoreType;
import com.waze.widget.routing.RoutingRequest;
import com.waze.widget.routing.RoutingResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.MathHelper;

public class WazeAppWidgetChart {
    private static final long MINUTE = 60000;

    protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(20.0f);
        renderer.setChartTitleTextSize(20.0f);
        renderer.setLabelsTextSize(20.0f);
        renderer.setLegendTextSize(1520.0f);
        renderer.setPointSize(5.0f);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    protected XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            TimeSeries series = new TimeSeries(titles[i]);
            Date[] xV = (Date[]) xValues.get(i);
            double[] yV = (double[]) yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    public Intent execute(Context context, RoutingResponse rsp, long timeStamp) {
        int i;
        String[] titles = new String[]{"Now", "NowTraingle", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ALL, "Red", "Orange", "Green"};
        long now = new Date().getTime();
        WazeAppWidgetLog.m45d("WidgetChart execute timeStamp=: " + timeStamp);
        List<Date[]> x = new ArrayList();
        x.add(new Date[]{new Date(now), new Date(1 + now)});
        x.add(new Date[]{new Date(now)});
        for (i = 1; i < titles.length; i++) {
            Object dates = new Date[rsp.getNumResults()];
            for (int j = 0; j < rsp.getNumResults(); j++) {
                dates[j] = new Date(((((long) (j * 10)) * MINUTE) + timeStamp) - (600000 * ((long) RoutingRequest.getNowLocation())));
            }
            x.add(dates);
        }
        List<double[]> values = new ArrayList();
        double[] AllArray = rsp.getList();
        int seriesLength = AllArray.length;
        Object RedArray = new double[seriesLength];
        Object NowArray = new double[2];
        Object NowTraiangleArray = new double[1];
        Object OrangeArray = new double[seriesLength];
        Object GreenArray = new double[seriesLength];
        for (int k = 0; k < seriesLength; k++) {
            RedArray[k] = MathHelper.NULL_VALUE;
            GreenArray[k] = MathHelper.NULL_VALUE;
            OrangeArray[k] = MathHelper.NULL_VALUE;
            if (AllArray[k] != MathHelper.NULL_VALUE) {
                RouteScoreType score = RouteScore.getScore((int) AllArray[k], rsp.getAveragetTime() / 60);
                if (score == RouteScoreType.ROUTE_GOOD) {
                    GreenArray[k] = AllArray[k];
                } else if (score == RouteScoreType.ROUTE_BAD) {
                    RedArray[k] = AllArray[k];
                } else {
                    OrangeArray[k] = AllArray[k];
                }
            }
        }
        NowArray[0] = (double) ((rsp.getMaxValue() / 60) + 100);
        NowArray[1] = (double) ((rsp.getMinValue() / 60) - 100);
        values.add(NowArray);
        NowTraiangleArray[0] = (double) ((rsp.getMinValue() / 60) - 9);
        values.add(NowTraiangleArray);
        values.add(AllArray);
        values.add(RedArray);
        values.add(OrangeArray);
        values.add(GreenArray);
        XYMultipleSeriesRenderer renderer = buildRenderer(new int[]{Color.parseColor("#77676767"), SupportMenu.CATEGORY_MASK, -1, Color.parseColor("#d62525"), Color.parseColor("#ea8124"), Color.parseColor("#5f8e44")}, new PointStyle[]{PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE});
        int length = renderer.getSeriesRendererCount();
        for (i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(0)).setLineWidth(30.0f);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(3)).setLineWidth(0.0f);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(2)).setLineWidth(7.0f);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(3)).setLineWidth(0.0f);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(4)).setLineWidth(0.0f);
        ((XYSeriesRenderer) renderer.getSeriesRendererAt(5)).setLineWidth(0.0f);
        setChartSettings(renderer, WazeLang.getLang("Your Commute-O-Meter"), "Time", "Min", (double) (((Date[]) x.get(2))[0].getTime() - 600000), (double) (((Date[]) x.get(2))[rsp.getNumResults() - 1].getTime() + 600000), (double) ((long) ((((double) rsp.getMinValue()) / 60.0d) * 0.9d)), (double) ((long) ((((double) rsp.getMaxValue()) / 60.0d) * 1.1d)), DefaultRenderer.TEXT_COLOR, DefaultRenderer.TEXT_COLOR);
        renderer.setXLabels(7);
        renderer.setYLabels(8);
        renderer.setAntialiasing(true);
        renderer.setPointSize(11.0f);
        renderer.setShowGrid(true);
        renderer.setChartTitleTextSize(26.0f);
        renderer.setShowLegend(false);
        renderer.setMargins(new int[]{50, 60, 90, 40});
        renderer.setXLabelsAlign(Align.CENTER);
        renderer.setYLabelsAlign(Align.RIGHT);
        return ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, x, values), renderer, "h:mm");
    }
}
