package com.waze.rtalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.view.tabs.TabBar;
import com.waze.view.tabs.TabBar.IOnTabClickListener;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;

public class RTAlertsList extends ActivityBase {
    private static RTAlertsAlertData mAlertSelected = null;
    private static RTAlertsAlertData[] mAlertsData;
    private int mAlertsTypeIdMask = 0;
    private int mAroundMeTab = 0;
    private final OnItemClickListener mListClickListener = new C25912();
    private int mMyGroupsTab = 2;
    private int mOnRouteTab = 1;
    private int mSelectedTab = 0;
    private String mTitle = null;

    class C25901 implements IOnTabClickListener {
        C25901() {
        }

        public void onClick(int tabId) {
            RTAlertsList.this.mSelectedTab = tabId;
            RTAlertsList.this.updateList(RTAlertsList.mAlertsData);
        }
    }

    class C25912 implements OnItemClickListener {
        C25912() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            RTAlertsList.mAlertSelected = (RTAlertsAlertData) ((RTAlertsListAdapter) parent.getAdapter()).getItem(position);
            if (RTAlertsList.mAlertSelected.mNumComments > 0) {
                RTAlertsComments.show(RTAlertsList.this, RTAlertsList.mAlertSelected);
                return;
            }
            Intent data = new Intent();
            data.putExtra(RTAlertsConsts.RTALERTS_POPUP_ALERT_ID, RTAlertsList.mAlertSelected.mID);
            RTAlertsList.this.setResult(1001, data);
            RTAlertsList.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.rtalerts_list);
        ListView listView = (ListView) findViewById(C1283R.id.rtalterts_list_listview);
        listView.setAdapter(new RTAlertsListAdapter(this));
        listView.setOnItemClickListener(this.mListClickListener);
        TabBar tabBar = (TabBar) findViewById(C1283R.id.ReportTabBar);
        tabBar.setText(0, NativeManager.getInstance().getLanguageString(228));
        tabBar.setText(1, NativeManager.getInstance().getLanguageString(232));
        tabBar.setText(2, NativeManager.getInstance().getLanguageString(231));
        tabBar.setSelected(0);
        if (getIntent().hasExtra(RTAlertsMenu.RTALERTS_TYPE_NAME_EXTRA)) {
            this.mTitle = getIntent().getStringExtra(RTAlertsMenu.RTALERTS_TYPE_NAME_EXTRA);
        }
        if (getIntent().hasExtra(RTAlertsMenu.RTALERTS_TYPE_ID_MASK_EXTRA)) {
            this.mAlertsTypeIdMask = getIntent().getIntExtra(RTAlertsMenu.RTALERTS_TYPE_ID_MASK_EXTRA, 0);
        }
        ((TitleBar) findViewById(C1283R.id.rtalterts_list_title_bar)).init((Activity) this, this.mTitle);
        tabBar.setListener(new C25901());
        updateList(mAlertsData);
        mAlertSelected = null;
    }

    public static void updateListData(RTAlertsAlertData[] alertsData) {
        mAlertsData = alertsData;
    }

    public static RTAlertsAlertData getAlertSelected() {
        return mAlertSelected;
    }

    public static RTAlertsAlertData[] getAlertsData() {
        return mAlertsData;
    }

    private void updateList(RTAlertsAlertData[] data) {
        if (data != null) {
            ArrayList<RTAlertsAlertData> filteredList = new ArrayList();
            int i = 0;
            while (i < data.length) {
                if ((data[i].mAlertsTypesMask & this.mAlertsTypeIdMask) > 0 && (this.mSelectedTab == this.mAroundMeTab || ((this.mSelectedTab == this.mOnRouteTab && data[i].mIsAlertOnRoute) || (this.mSelectedTab == this.mMyGroupsTab && data[i].mGroupRelevance)))) {
                    filteredList.add(data[i]);
                }
                i++;
            }
            RTAlertsListAdapter adapter = (RTAlertsListAdapter) ((ListView) findViewById(C1283R.id.rtalterts_list_listview)).getAdapter();
            adapter.updateList(filteredList);
            adapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002 || resultCode == -1 || resultCode == 1001) {
            setResult(resultCode, data);
            finish();
        }
    }
}
