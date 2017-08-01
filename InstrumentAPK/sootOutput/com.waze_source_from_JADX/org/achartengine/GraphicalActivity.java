package org.achartengine;

import android.app.Activity;
import android.os.Bundle;
import org.achartengine.chart.AbstractChart;

public class GraphicalActivity extends Activity {
    private AbstractChart mChart;
    private GraphicalView mView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        this.mChart = (AbstractChart) extras.getSerializable(ChartFactory.CHART);
        this.mView = new GraphicalView(this, this.mChart);
        CharSequence string = extras.getString("title");
        if (string == null) {
            requestWindowFeature(1);
        } else if (string.length() > 0) {
            setTitle(string);
        }
        setContentView(this.mView);
    }
}
