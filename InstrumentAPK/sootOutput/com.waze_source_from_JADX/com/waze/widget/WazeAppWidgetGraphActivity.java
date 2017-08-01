package com.waze.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.waze.C1283R;

public class WazeAppWidgetGraphActivity extends Activity {

    private final class OnCloseListener implements OnClickListener {
        private OnCloseListener() {
        }

        public void onClick(View aView) {
            WazeAppWidgetGraphActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.app_widget_graph);
        ((ImageButton) findViewById(C1283R.id.btn_graph_close)).setOnClickListener(new OnCloseListener());
    }
}
