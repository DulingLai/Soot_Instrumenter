package com.waze.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.ifs.ui.ActivityBase;

public class WazeAppWidgetNoDataActivity extends ActivityBase {
    private LinearLayout mBtnClose;
    private LinearLayout mBtnEnter;
    private TextView mBtnTextClose;
    private TextView mBtnTextEnter;
    private TextView mTitleText;
    private TextView mTxtInfo1;
    private TextView mTxtInfo2;

    class C33181 implements OnClickListener {
        C33181() {
        }

        public void onClick(View v) {
            WazeAppWidgetNoDataActivity.this.setResult(-1);
            WazeAppWidgetNoDataActivity.this.finish();
        }
    }

    class C33192 implements OnClickListener {
        C33192() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(WazeAppWidgetNoDataActivity.this, MainActivity.class);
            intent.setFlags(402653184);
            WazeAppWidgetNoDataActivity.this.startActivityForResult(intent, 1);
            WazeAppWidgetNoDataActivity.this.setResult(-1);
            WazeAppWidgetNoDataActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        WazeAppWidgetLog.m45d("WazeAppWidgetNoDataActivity.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.app_widget_nodata);
        initLayout();
        setOnCLickListeners();
    }

    private void setOnCLickListeners() {
        this.mBtnClose.setOnClickListener(new C33181());
        this.mBtnEnter.setOnClickListener(new C33192());
    }

    private void initLayout() {
        this.mBtnTextClose = (TextView) findViewById(C1283R.id.text_btn_close);
        this.mBtnClose = (LinearLayout) findViewById(C1283R.id.btn_close);
        this.mBtnTextEnter = (TextView) findViewById(C1283R.id.text_btn_enter);
        this.mBtnEnter = (LinearLayout) findViewById(C1283R.id.btn_enter);
        this.mTitleText = (TextView) findViewById(C1283R.id.text_title);
    }
}
