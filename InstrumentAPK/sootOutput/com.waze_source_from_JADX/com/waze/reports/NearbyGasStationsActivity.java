package com.waze.reports;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.ResManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.NearbyStation;
import com.waze.settings.SettingValueAdapter;
import com.waze.settings.SettingsValue;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;

public class NearbyGasStationsActivity extends ActivityBase implements OnItemClickListener {
    private SettingValueAdapter mAdapter;
    private DriveToNativeManager mDtnMgr;
    protected NearbyStation[] mNearbyStations;
    private boolean mReturnSelection = false;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDtnMgr = DriveToNativeManager.getInstance();
        setContentView(C1283R.layout.settings_values);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 204);
        this.mAdapter = new SettingValueAdapter(this);
        this.mAdapter.setShowRadio(false);
        this.mAdapter.setSmallIcons(true);
        ListView list = (ListView) findViewById(C1283R.id.settingsValueList);
        list.setAdapter(this.mAdapter);
        list.setOnItemClickListener(this);
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList(list);
        }
        if (getIntent().hasExtra(GasPriceReport.NEARBY_STATIONS)) {
            this.mReturnSelection = true;
            ArrayList<NearbyStation> nearbyStations = getIntent().getParcelableArrayListExtra(GasPriceReport.NEARBY_STATIONS);
            this.mNearbyStations = new NearbyStation[nearbyStations.size()];
            nearbyStations.toArray(this.mNearbyStations);
            setAdapterValues();
            return;
        }
        this.mReturnSelection = false;
        this.mDtnMgr.setUpdateHandler(DriveToNativeManager.UH_NEARBY_STATIONS, this.mHandler);
        this.mDtnMgr.searchNearbyStations();
    }

    protected void onDestroy() {
        this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_NEARBY_STATIONS, this.mHandler);
        super.onDestroy();
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what != DriveToNativeManager.UH_NEARBY_STATIONS) {
            return super.myHandleMessage(msg);
        }
        this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_NEARBY_STATIONS, this.mHandler);
        this.mNearbyStations = (NearbyStation[]) msg.getData().getSerializable(GasPriceReport.NEARBY_STATIONS);
        setAdapterValues();
        return true;
    }

    private void setAdapterValues() {
        SettingsValue[] values = new SettingsValue[this.mNearbyStations.length];
        for (int i = 0; i < this.mNearbyStations.length; i++) {
            values[i] = new SettingsValue(this.mNearbyStations[i].result, this.mNearbyStations[i].result, this.mNearbyStations[i].address, false);
            values[i].icon = ResManager.GetSkinDrawable(this.mNearbyStations[i].icon + ResManager.mImageExtension);
        }
        this.mAdapter.setValues(values);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.mReturnSelection) {
            Intent ret = new Intent();
            ret.putExtra("selection", position);
            setResult(-1, ret);
            finish();
            return;
        }
        Intent intent = new Intent(this, UpdatePriceActivity.class);
        intent.putExtra("index", position);
        startActivityForResult(intent, 1);
    }
}
