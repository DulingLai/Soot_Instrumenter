package com.waze.reports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.map.MapView;
import com.waze.navigate.DriveToNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.timer.TimerBar;
import com.waze.view.title.TitleBar;

public final class ClosureMap extends ActivityBase {
    protected static final long MORE_ANIMATION_TICK = 20;
    protected static final int MORE_OFFSET = -100;
    private static LayoutManager layout_manager;
    private static ClosureReport reportForm;
    private static boolean sServerRequest = false;
    private static int selectedPin = -1;
    private DriveToNativeManager driveToNativeManager;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C24365();
    private final RunnableExecutor mNativeCanvasReadyEventReopen = new C24376();
    private MapView mapView;
    private NativeManager nativeManager;

    class C24343 implements OnClickListener {
        C24343() {
        }

        public void onClick(View v) {
            ClosureMap.layout_manager.setClosureRunning(false);
            ClosureMap.reportForm.toStage2();
            ClosureMap.this.setResult(-1);
            ClosureMap.this.finish();
        }
    }

    class C24354 implements OnClickListener {
        C24354() {
        }

        public void onClick(View v) {
            ClosureMap.this.setResult(-1);
            ClosureMap.this.finish();
            ClosureMap.this.stop();
            if (ClosureMap.reportForm != null) {
                ClosureMap.reportForm.onLater();
            }
        }
    }

    class C24365 extends RunnableExecutor {
        C24365() {
        }

        public void event() {
            if (ClosureMap.sServerRequest) {
                ClosureMap.this.nativeManager.StartClosureObject(false, 0, ClosureMap.sServerRequest);
            } else {
                ClosureMap.this.nativeManager.StartClosureObject(false, -1, ClosureMap.sServerRequest);
            }
        }
    }

    class C24376 extends RunnableExecutor {
        C24376() {
        }

        public void event() {
            ClosureMap.this.nativeManager.StartClosureObject(true, ClosureMap.selectedPin, false);
        }
    }

    public static void launch(final Context context, final ClosureReport closureReport, final LayoutManager layoutManager, final boolean bIsServerRequest) {
        NativeManager.Post(new RunnableUICallback() {
            private boolean hasClosureData;

            public void event() {
                this.hasClosureData = NativeManager.getInstance().HasClosureDataNTV();
            }

            public void callback() {
                if (this.hasClosureData) {
                    ClosureMap.reportForm = closureReport;
                    ClosureMap.layout_manager = layoutManager;
                    ClosureMap.sServerRequest = bIsServerRequest;
                    AppService.getMainActivity().startActivityForResult(new Intent(context, ClosureMap.class), 1);
                }
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.driveToNativeManager = DriveToNativeManager.getInstance();
        this.nativeManager = AppService.getNativeManager();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ROAD_CLOSURE_DETECTED_POPUP_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_FROM_SERVER, sServerRequest ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE);
        setUpActivity();
    }

    public static void SetReportForm(ClosureReport report) {
        reportForm = report;
    }

    public static void SetLayoutMgr(LayoutManager lmgr) {
        layout_manager = lmgr;
    }

    private void setUpActivity() {
        setContentView(C1283R.layout.closure_map);
        final TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.init((Activity) this, (int) DisplayStrings.DS_CLOSURE);
        titleBar.setOnClickCloseListener(new OnClickListener() {
            public void onClick(View v) {
                ClosureMap.this.closeClosureForm();
                titleBar.onCloseClicked();
            }
        });
        findViewById(C1283R.id.reportSend).setOnClickListener(new C24343());
        findViewById(C1283R.id.reportLater).setOnClickListener(new C24354());
        this.mapView = (MapView) findViewById(C1283R.id.searchMapView);
        if (layout_manager.IsClosureRunning()) {
            this.mapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEventReopen);
        } else {
            this.mapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
            selectedPin = -1;
        }
        if (sServerRequest) {
            ((TextView) findViewById(C1283R.id.reportSendText)).setText(this.nativeManager.getLanguageString(357));
            ((TextView) findViewById(C1283R.id.TipText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_CONFIRM_THE_LOCATION_OF));
        } else {
            ((TextView) findViewById(C1283R.id.reportSendText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_NEXT));
            ((TextView) findViewById(C1283R.id.TipText)).setText(this.nativeManager.getLanguageString(222));
        }
        ((TextView) findViewById(C1283R.id.reportLaterText)).setText(this.nativeManager.getLanguageString(292));
        EnableNextButton(selectedPin);
        layout_manager.SetClosureMap(this);
        if (!layout_manager.IsClosureRunning()) {
            start();
        }
        layout_manager.setClosureRunning(true);
    }

    public void onPause() {
        super.onPause();
        this.mapView.onPause();
        stop();
        this.nativeManager.ClearClosureObject();
    }

    public void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    public void increaseMapClicked(View v) {
        this.mapView.setHandleTouch(false);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mapView.onPause();
        this.nativeManager.ClearClosureObject();
        setUpActivity();
        this.mapView.onResume();
    }

    public void EnableNextButton(int Index) {
        stop();
        View sendBut = findViewById(C1283R.id.reportSend);
        if (Index >= 0) {
            sendBut.setEnabled(true);
            sendBut.setAlpha(1.0f);
        } else {
            sendBut.setEnabled(false);
            sendBut.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        }
        selectedPin = Index;
    }

    public void start() {
        TimerBar timerView = (TimerBar) findViewById(C1283R.id.reportLaterTimer);
        if (timerView != null) {
            timerView.start();
            timerView.setVisibility(0);
        }
    }

    public void stop() {
        TimerBar timerView = (TimerBar) findViewById(C1283R.id.reportLaterTimer);
        if (timerView != null) {
            timerView.stop();
            timerView.setVisibility(8);
        }
    }

    private void closeClosureForm() {
        if (reportForm != null) {
            reportForm.stop();
            reportForm.close();
        }
    }

    public void onBackPressed() {
        closeClosureForm();
        super.onBackPressed();
    }
}
