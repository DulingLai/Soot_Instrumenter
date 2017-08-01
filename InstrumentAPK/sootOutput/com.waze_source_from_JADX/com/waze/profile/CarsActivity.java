package com.waze.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.MapCarsCallback;
import com.waze.settings.WazeSettingsView;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class CarsActivity extends ActivityBase {
    private final int MAP_CAR_ICON_HEIGHT_DP = 42;
    private WazeSettingsView _curSelection;
    private CarListAdapter adapter;
    MapCarItem[] carItems;
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();

    class C23561 implements MapCarsCallback {
        C23561() {
        }

        public void onMapCars(MapCarItem[] carItems, String carIdCurrent) {
            CarsActivity.this.updateData(carItems, carIdCurrent);
        }
    }

    private class CarListAdapter extends ArrayAdapter<MapCarItem> {
        private NativeManager nativeManager = AppService.getNativeManager();
        private String selection;

        public CarListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            WazeSettingsView row = (WazeSettingsView) convertView;
            if (row == null) {
                row = new WazeSettingsView(parent.getContext());
                row.setType(3);
                row.setClickable(false);
                row.setBackgroundDrawable(null);
            }
            if (CarsActivity.this._curSelection == row) {
                CarsActivity.this._curSelection = null;
            }
            if (getCount() == 1) {
                row.setPosition(3);
            } else if (position == 0) {
                row.setPosition(1);
            } else if (position == getCount() - 1) {
                row.setPosition(2);
            } else {
                row.setPosition(0);
            }
            row.setIcon(ResManager.GetSkinDrawable(((MapCarItem) getItem(position)).carResource));
            row.setIconHeight(42);
            row.setText(((MapCarItem) getItem(position)).carLabel);
            boolean equals = this.selection.equals(((MapCarItem) getItem(position)).carId);
            row.setRadioValue(equals, false);
            if (equals) {
                CarsActivity.this._curSelection = row;
            }
            return row;
        }

        public void setSelection(String selection) {
            this.selection = selection;
            notifyDataSetChanged();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.cars);
        ((TitleBar) findViewById(C1283R.id.carsTitle)).init((Activity) this, 113);
        MapCarsCallback cb = new C23561();
        init();
        this.mywazeManager.registerMapCarsDataCallback(cb);
        this.mywazeManager.getMapCars(cb);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mywazeManager.unregisterMapCarsDataCallback();
    }

    private void updateData(MapCarItem[] carItems, String carIdCurrent) {
        if (this.adapter != null && carItems != null && isAlive()) {
            this.adapter.clear();
            this.adapter.addAll(carItems);
            this.adapter.setSelection(carIdCurrent);
            this.adapter.notifyDataSetChanged();
            this.carItems = carItems;
        }
    }

    private void init() {
        this.adapter = new CarListAdapter(getApplicationContext(), C1283R.layout.car_item);
        final ListView list = (ListView) findViewById(C1283R.id.carList);
        list.setAdapter(this.adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                String carId = CarsActivity.this.carItems[position].carId;
                if (list.getFirstVisiblePosition() <= position && list.getLastVisiblePosition() >= position && CarsActivity.this._curSelection != null && CarsActivity.this._curSelection != v) {
                    CarsActivity.this._curSelection.setRadioValue(false, true);
                }
                if (CarsActivity.this._curSelection != v) {
                    CarsActivity.this._curSelection = (WazeSettingsView) v;
                    CarsActivity.this._curSelection.setRadioValue(true, true);
                }
                CarsActivity.this.mywazeManager.setUserCar(carId);
                CarsActivity.this.setResult(0);
                CarsActivity.this.finish();
            }
        });
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
    }
}
