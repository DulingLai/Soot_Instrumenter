package com.waze.carpool;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.map.MapView;
import com.waze.navigate.DriveToNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class CarpoolRideDetailsMapActivity extends ActivityBase {
    public static final int MAXIMUM_FOV = 0;
    public static final int MINIMUM_FOV = 3500;
    private CarpoolDrive mDrive;
    private DriveToNativeManager mDtnm;
    private MapView mMapView;
    private TitleBar mTitleBar;
    private CarpoolLocation mZoomTarget;

    class C15211 implements OnClickListener {
        C15211() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolRideDetailsMapActivity.this.setResult(0);
            CarpoolRideDetailsMapActivity.this.finish();
            CarpoolRideDetailsMapActivity.this.overridePendingTransition(0, 0);
        }
    }

    class C15222 extends RunnableExecutor {
        C15222() throws  {
        }

        public void event() throws  {
            short $s0;
            float $f1;
            float $f0 = 1.0f;
            boolean $z0 = CarpoolRideDetailsMapActivity.this.mZoomTarget == null;
            CarpoolRideDetailsMapActivity.this.mDtnm.setCarpoolPins(CarpoolRideDetailsMapActivity.this.mDrive, $z0);
            if ($z0) {
                $s0 = (short) 0;
            } else {
                $s0 = (short) 3500;
            }
            if ($z0) {
                $f1 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            } else {
                $f1 = 1.0f;
            }
            if (!$z0) {
                $f0 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            }
            if ($z0) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            CarpoolRideDetailsActivity.loadCanvas(CarpoolRideDetailsMapActivity.this.mDrive.getRide(), $s0, $f1, $f0, $z0, CarpoolRideDetailsMapActivity.this.mZoomTarget);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mDtnm = DriveToNativeManager.getInstance();
        this.mDrive = (CarpoolDrive) getIntent().getParcelableExtra(CarpoolDrive.class.getSimpleName());
        this.mZoomTarget = (CarpoolLocation) getIntent().getParcelableExtra("ZoomTarget");
        requestWindowFeature(1);
        setContentView(C1283R.layout.ride_details_activity_map);
        getWindow().setSoftInputMode(3);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        this.mTitleBar.init(this, DisplayStrings.DS_);
        this.mTitleBar.setCloseVisibility(false);
        this.mTitleBar.setTitle(getIntent().getStringExtra("title"));
        this.mTitleBar.setBackgroundResource(getIntent().getIntExtra("titleBackground", C1283R.drawable.carpool_ride_details_request));
        this.mTitleBar.setTextColor(getIntent().getIntExtra("titleTextColor", C1283R.drawable.carpool_ride_details_request));
        this.mTitleBar.setUpButtonResource(getIntent().getIntExtra("titleBack", C1283R.drawable.white_back));
        this.mMapView = (MapView) findViewById(C1283R.id.rideRequestMap);
        this.mMapView.setHandleTouch(true);
        TextView $r13 = (TextView) findViewById(C1283R.id.rideRequestMapClose);
        $r13.setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_RIDE_DETAILS_CLOSE_MAP_BUTTON));
        $r13.setOnClickListener(new C15211());
        this.mMapView.registerOnMapReadyCallback(new C15222());
    }

    protected void onDestroy() throws  {
        this.mDtnm.unsetSearchMapView();
        super.onDestroy();
    }

    protected void onPause() throws  {
        if (this.mMapView != null) {
            this.mMapView.onPause();
        }
        super.onPause();
    }
}
