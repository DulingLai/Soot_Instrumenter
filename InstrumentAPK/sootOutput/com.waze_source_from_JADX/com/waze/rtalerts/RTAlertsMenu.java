package com.waze.rtalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.ifs.ui.ActivityBase;
import com.waze.rtalerts.RTAlertsMenuAdapter.RowData;
import com.waze.rtalerts.RTAlertsNativeManager.IAlertsListDataHandler;
import com.waze.rtalerts.RTAlertsNativeManager.IAlertsListDataHandler.Data;
import com.waze.rtalerts.RTAlertsNativeManager.IAlertsMenuDataHandler;
import com.waze.view.title.TitleBar;

public class RTAlertsMenu extends ActivityBase {
    public static final String RTALERTS_TYPE_ID_MASK_EXTRA = "Type Id";
    public static final String RTALERTS_TYPE_NAME_EXTRA = "Type Name";

    class C25931 implements IAlertsMenuDataHandler {
        C25931() {
        }

        public void handler(String title, RTAlertsMenuData[] data) {
            RTAlertsMenu.this.updateMenu(title, data);
        }
    }

    class C25942 implements IAlertsListDataHandler {
        C25942() {
        }

        public void handler(Data data) {
            RTAlertsList.updateListData(data.mAlertsData);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RTAlertsNativeManager mgr = RTAlertsNativeManager.getInstance();
        setContentView(C1283R.layout.rtalerts_menu);
        mgr.getAlertsMenuData(new C25931());
        mgr.getAlertsListData(new C25942());
        final RTAlertsMenuAdapter adapter = new RTAlertsMenuAdapter(this);
        ListView listView = (ListView) findViewById(C1283R.id.rtalterts_menu_listmenu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                RowData row = (RowData) adapter.getItem(position);
                Intent intent = new Intent(RTAlertsMenu.this, RTAlertsList.class);
                intent.putExtra(RTAlertsMenu.RTALERTS_TYPE_ID_MASK_EXTRA, row.mId);
                intent.putExtra(RTAlertsMenu.RTALERTS_TYPE_NAME_EXTRA, row.mLabel);
                RTAlertsMenu.this.startActivityForResult(intent, MainActivity.RTALERTS_REQUEST_CODE);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        RTAlertsList.updateListData(null);
    }

    public void updateMenu(String title, RTAlertsMenuData[] data) {
        ((TitleBar) findViewById(C1283R.id.rtalterts_menu_title_bar)).init((Activity) this, title);
        RTAlertsMenuAdapter adapter = (RTAlertsMenuAdapter) ((ListView) findViewById(C1283R.id.rtalterts_menu_listmenu)).getAdapter();
        adapter.updateList(data);
        adapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001 || resultCode == 1002 || resultCode == -1) {
            setResult(resultCode, data);
            finish();
        }
    }
}
