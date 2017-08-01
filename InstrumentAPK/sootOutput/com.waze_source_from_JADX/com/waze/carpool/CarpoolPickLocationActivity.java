package com.waze.carpool;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.MapView;
import com.waze.navigate.NavigateNativeManager;
import com.waze.navigate.NavigateNativeManager.Position;
import com.waze.push.Alerter;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class CarpoolPickLocationActivity extends ActivityBase {
    private boolean mAvoiderPin = true;
    private boolean mDoneEnabled = true;
    Handler mHandler = new C14943();
    private int mInstructionDS = DisplayStrings.DS_CARPOOL_SEND_LOC_ADDRESS_TITLE;
    private int mLat;
    private int mLon;
    private MapView mMapView;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C14921();
    private NavigateNativeManager mNavNtvMgr;
    private NativeManager mNm;
    private TitleBar mTitleBar;
    private int mTitleDS = DisplayStrings.DS_CARPOOL_SEND_LOC_TITLE;

    class C14921 extends RunnableExecutor {
        C14921() throws  {
        }

        public void event() throws  {
            CarpoolPickLocationActivity.this.mNavNtvMgr.locationPickerCanvasSet(CarpoolPickLocationActivity.this.mLon, CarpoolPickLocationActivity.this.mLat, CarpoolPickLocationActivity.this.mLon, CarpoolPickLocationActivity.this.mLat, 0, CarpoolPickLocationActivity.this.mAvoiderPin);
        }
    }

    class C14932 implements OnClickListener {
        C14932() throws  {
        }

        public void onClick(View v) throws  {
            if (CarpoolPickLocationActivity.this.mDoneEnabled) {
                Intent $r2 = new Intent();
                $r2.putExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LONGITUDE, CarpoolPickLocationActivity.this.mLon);
                $r2.putExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LATITUDE, CarpoolPickLocationActivity.this.mLat);
                CarpoolPickLocationActivity.this.setResult(-1, $r2);
                CarpoolPickLocationActivity.this.finish();
            }
        }
    }

    class C14943 extends Handler {
        C14943() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            if ($r1.what == NavigateNativeManager.UH_MAP_CENTER) {
                Bundle $r2 = $r1.getData();
                $r2.setClassLoader(getClass().getClassLoader());
                Position $r6 = (Position) $r2.getSerializable("position");
                if ($r6 != null) {
                    CarpoolPickLocationActivity.this.mLon = $r6.longitude;
                    CarpoolPickLocationActivity.this.mLat = $r6.latitude;
                }
            } else if ($r1.what != NavigateNativeManager.UH_LOCATION_PICKER_STATE) {
                super.handleMessage($r1);
            } else if ($r1.arg1 != 0 && !CarpoolPickLocationActivity.this.mDoneEnabled) {
                CarpoolPickLocationActivity.this.mTitleBar.setCloseImageResource(C1283R.drawable.confirm_icon);
                CarpoolPickLocationActivity.this.mDoneEnabled = true;
            } else if ($r1.arg1 == 0 && CarpoolPickLocationActivity.this.mDoneEnabled) {
                CarpoolPickLocationActivity.this.mTitleBar.setCloseImageResource(C1283R.drawable.v);
                CarpoolPickLocationActivity.this.mDoneEnabled = false;
            }
        }
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putInt(CarpoolPickLocationActivity.class.getName() + ".mLon", this.mLon);
        $r1.putInt(CarpoolPickLocationActivity.class.getName() + ".mLat", this.mLat);
        $r1.putInt(CarpoolPickLocationActivity.class.getName() + ".mTitle", this.mTitleDS);
        $r1.putInt(CarpoolPickLocationActivity.class.getName() + ".mInstruction", this.mInstructionDS);
        $r1.putBoolean(CarpoolPickLocationActivity.class.getName() + ".mAvoiderPin", this.mAvoiderPin);
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mNavNtvMgr = NavigateNativeManager.instance();
        this.mNavNtvMgr.setUpdateHandler(NavigateNativeManager.UH_LOCATION_PICKER_STATE, this.mHandler);
        setContentView(C1283R.layout.carpool_pick_location);
        if ($r1 != null) {
            this.mLon = $r1.getInt(CarpoolPickLocationActivity.class.getName() + ".mLon");
            this.mLat = $r1.getInt(CarpoolPickLocationActivity.class.getName() + ".mLat");
            this.mTitleDS = $r1.getInt(CarpoolPickLocationActivity.class.getName() + ".mTitle");
            this.mInstructionDS = $r1.getInt(CarpoolPickLocationActivity.class.getName() + ".mInstruction");
            this.mAvoiderPin = $r1.getBoolean(CarpoolPickLocationActivity.class.getName() + ".mAvoiderPin");
        } else {
            Intent $r8 = getIntent();
            this.mTitleDS = $r8.getIntExtra("title", this.mTitleDS);
            this.mInstructionDS = $r8.getIntExtra("instruction", this.mInstructionDS);
            this.mAvoiderPin = $r8.getBooleanExtra("avoider", true);
            if ($r8.hasExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LONGITUDE)) {
                this.mLon = $r8.getIntExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LONGITUDE, 0);
                this.mLat = $r8.getIntExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LATITUDE, 0);
            } else {
                Location $r9 = Alerter.getBestLocation(this);
                this.mLon = (int) ($r9.getLongitude() * 1000000.0d);
                this.mLat = (int) ($r9.getLatitude() * 1000000.0d);
            }
        }
        setUpActivity();
        this.mNavNtvMgr.setUpdateHandler(NavigateNativeManager.UH_MAP_CENTER, this.mHandler);
        this.mNavNtvMgr.getMapCenter();
    }

    public void onDestroy() throws  {
        this.mNavNtvMgr.unsetUpdateHandler(NavigateNativeManager.UH_LOCATION_PICKER_STATE, this.mHandler);
        this.mNavNtvMgr.locationPickerCanvasUnset();
        this.mNavNtvMgr.unsetUpdateHandler(NavigateNativeManager.UH_MAP_CENTER, this.mHandler);
        super.onDestroy();
    }

    private void setUpActivity() throws  {
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        this.mTitleBar.init(this, this.mNm.getLanguageString(this.mTitleDS), 0);
        this.mTitleBar.setCloseImageResource(C1283R.drawable.confirm_icon);
        this.mTitleBar.setOnClickCloseListener(new C14932());
        ((WazeTextView) findViewById(C1283R.id.cpPickLocTitle)).setText(this.mNm.getLanguageString(this.mInstructionDS));
        this.mMapView = (MapView) findViewById(C1283R.id.cpPickLocMap);
        this.mMapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        this.mMapView.onPause();
        setUpActivity();
        this.mMapView.onResume();
    }

    public void onPause() throws  {
        this.mMapView.onPause();
        super.onPause();
    }

    public void onResume() throws  {
        this.mMapView.onResume();
        super.onResume();
    }
}
