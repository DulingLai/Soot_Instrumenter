package com.waze.reports;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.map.MapView;
import com.waze.navigate.NavigateNativeManager;
import com.waze.navigate.NavigateNativeManager.Position;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class EditMapLocationFragment extends Fragment {
    private boolean mAvoiderPin = false;
    private boolean mDoneEnabled = true;
    Handler mHandler = new C24413();
    private int mInstructionDS = DisplayStrings.DS_MOVE_THE_MAP_TO_ADJUST_PIN_LOCATION;
    private int mLat;
    private int mLon;
    private MapView mMapView;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C24391();
    private NavigateNativeManager mNavNtvMgr;
    private NativeManager mNm;
    private TitleBar mTitleBar;
    private int mTitleDS = DisplayStrings.DS_LOCATION;
    private View f91r;

    public interface IEditMap {
        void doneEditMap(int i, int i2);
    }

    class C24391 extends RunnableExecutor {
        C24391() {
        }

        public void event() {
            EditMapLocationFragment.this.mNavNtvMgr.locationPickerCanvasSet(EditMapLocationFragment.this.mLon, EditMapLocationFragment.this.mLat, EditMapLocationFragment.this.mLon, EditMapLocationFragment.this.mLat, EditMapLocationFragment.this.mNm.getEditPlaceLocationRadius(), EditMapLocationFragment.this.mAvoiderPin);
        }
    }

    class C24402 implements OnClickListener {
        C24402() {
        }

        public void onClick(View v) {
            if (EditMapLocationFragment.this.mDoneEnabled) {
                EditMapLocationFragment.this.mNavNtvMgr.setUpdateHandler(NavigateNativeManager.UH_MAP_CENTER, EditMapLocationFragment.this.mHandler);
                EditMapLocationFragment.this.mNavNtvMgr.getMapCenter();
            }
        }
    }

    class C24413 extends Handler {
        C24413() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == NavigateNativeManager.UH_MAP_CENTER) {
                EditMapLocationFragment.this.mNavNtvMgr.unsetUpdateHandler(NavigateNativeManager.UH_MAP_CENTER, EditMapLocationFragment.this.mHandler);
                Bundle b = msg.getData();
                b.setClassLoader(getClass().getClassLoader());
                Position pos = (Position) b.getSerializable("position");
                ((IEditMap) EditMapLocationFragment.this.getActivity()).doneEditMap(pos.longitude, pos.latitude);
            } else if (msg.what != NavigateNativeManager.UH_LOCATION_PICKER_STATE) {
                super.handleMessage(msg);
            } else if (msg.arg1 != 0 && !EditMapLocationFragment.this.mDoneEnabled) {
                EditMapLocationFragment.this.mTitleBar.setCloseImageResource(C1283R.drawable.confirm_icon);
                EditMapLocationFragment.this.mDoneEnabled = true;
            } else if (msg.arg1 == 0 && EditMapLocationFragment.this.mDoneEnabled) {
                EditMapLocationFragment.this.mTitleBar.setCloseImageResource(C1283R.drawable.v);
                EditMapLocationFragment.this.mDoneEnabled = false;
            }
        }
    }

    public void setLonLat(int lon, int lat) {
        this.mLon = lon;
        this.mLat = lat;
    }

    public void setTitle(int ds) {
        this.mTitleDS = ds;
    }

    public void setInstruction(int ds) {
        this.mInstructionDS = ds;
    }

    public void setAvoiderPin(boolean on) {
        this.mAvoiderPin = on;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mNm = NativeManager.getInstance();
        this.mNavNtvMgr = NavigateNativeManager.instance();
        this.f91r = inflater.inflate(C1283R.layout.edit_place_location, container, false);
        setUpFragment();
        this.mNavNtvMgr.setUpdateHandler(NavigateNativeManager.UH_LOCATION_PICKER_STATE, this.mHandler);
        return this.f91r;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EditMapLocationFragment.class.getName() + ".mLon", this.mLon);
        outState.putInt(EditMapLocationFragment.class.getName() + ".mLat", this.mLat);
        outState.putInt(EditMapLocationFragment.class.getName() + ".mTitle", this.mTitleDS);
        outState.putInt(EditMapLocationFragment.class.getName() + ".mInstruction", this.mInstructionDS);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mLon = savedInstanceState.getInt(EditMapLocationFragment.class.getName() + ".mLon");
            this.mLat = savedInstanceState.getInt(EditMapLocationFragment.class.getName() + ".mLat");
            this.mTitleDS = savedInstanceState.getInt(EditMapLocationFragment.class.getName() + ".mTitle");
            this.mInstructionDS = savedInstanceState.getInt(EditMapLocationFragment.class.getName() + ".mInstruction");
        }
    }

    public void onDestroy() {
        this.mNavNtvMgr.unsetUpdateHandler(NavigateNativeManager.UH_LOCATION_PICKER_STATE, this.mHandler);
        this.mNavNtvMgr.locationPickerCanvasUnset();
        super.onDestroy();
    }

    private void setUpFragment() {
        this.mTitleBar = (TitleBar) this.f91r.findViewById(C1283R.id.theTitleBar);
        this.mTitleBar.init(getActivity(), this.mNm.getLanguageString(this.mTitleDS), 0);
        this.mTitleBar.setCloseImageResource(C1283R.drawable.confirm_icon);
        this.mTitleBar.setOnClickCloseListener(new C24402());
        ((WazeTextView) this.f91r.findViewById(C1283R.id.editPlaceLocationText)).setText(this.mNm.getLanguageString(this.mInstructionDS));
        this.mMapView = (MapView) this.f91r.findViewById(C1283R.id.editPlaceLocationMap);
        this.mMapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mMapView.onPause();
        setUpFragment();
        this.mMapView.onResume();
    }

    public void onPause() {
        this.mMapView.onPause();
        super.onPause();
    }

    public void onResume() {
        this.mMapView.onResume();
        super.onResume();
    }
}
