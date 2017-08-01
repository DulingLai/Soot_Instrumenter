package com.waze.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.share.ShareUtility.ShareType;
import com.waze.view.title.TitleBar;

public final class ShareRecentSearch extends ActivityBase implements DriveToGetAddressItemArrayCallback, DriveToNavigateCallback {
    private DriveToNativeManager nm;

    class C28431 implements OnItemClickListener {
        C28431() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            ShareRecentSearch.this.addressItemClicked(arg1);
        }
    }

    private class HistoryListAdapter extends BaseAdapter {
        private AddressItem[] historyItems;

        public HistoryListAdapter(Context context, int textViewResourceId, AddressItem[] hi) {
            this.historyItems = hi;
        }

        public int getCount() {
            return this.historyItems.length;
        }

        public Object getItem(int position) {
            return this.historyItems[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View view, ViewGroup parent) {
            AddressItem ai = (AddressItem) getItem(position);
            if (view == null) {
                view = ((LayoutInflater) ShareRecentSearch.this.getSystemService("layout_inflater")).inflate(C1283R.layout.address_item, null);
            }
            view.setTag(C1283R.id.addressItem, ai);
            view.findViewById(C1283R.id.shortAddressItemCol).setVisibility(0);
            view.findViewById(C1283R.id.addressItemTouch).setVisibility(8);
            view.findViewById(C1283R.id.addressItemImage).setVisibility(8);
            if (ai != null) {
                TextView name = (TextView) view.findViewById(C1283R.id.addressItemName);
                TextView address = (TextView) view.findViewById(C1283R.id.addressItemAddress);
                if (ai.getTitle().equals("")) {
                    name.setVisibility(8);
                    if (ai.getAddress().equals("")) {
                        address.setVisibility(8);
                    } else {
                        address.setVisibility(0);
                        address.setText(ai.getAddress());
                    }
                } else {
                    name.setVisibility(0);
                    address.setVisibility(8);
                    name.setText(ai.getTitle());
                }
                TextView distance = (TextView) view.findViewById(C1283R.id.addressItemDistance);
                if (ai.getDistance().equals("")) {
                    distance.setVisibility(8);
                } else {
                    distance.setVisibility(0);
                    distance.setText(ai.getDistance());
                }
                RelativeLayout btn = (RelativeLayout) view.findViewById(C1283R.id.moreActionButton);
                btn.setTag(C1283R.id.addressItem, ai);
                btn.setVisibility(8);
                View aiView = view.findViewById(C1283R.id.addressItem);
                switch ((position == 0 ? 1 : 0) | (position == getCount() + -1 ? 2 : 0)) {
                    case 0:
                        aiView.setBackgroundResource(C1283R.drawable.item_selector_middle);
                        break;
                    case 1:
                        aiView.setBackgroundResource(C1283R.drawable.item_selector_top);
                        break;
                    case 2:
                        aiView.setBackgroundResource(C1283R.drawable.item_selector_bottom);
                        break;
                    case 3:
                        aiView.setBackgroundResource(C1283R.drawable.item_selector_single);
                        break;
                }
                aiView.setPadding(0, 0, 0, 0);
            }
            return view;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1283R.layout.history);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 14);
        ((ListView) findViewById(C1283R.id.historyList)).setOnItemClickListener(new C28431());
        this.nm = DriveToNativeManager.getInstance();
        this.nm.getHistory(this);
    }

    public void getAddressItemArrayCallback(AddressItem[] favorites) {
        Log.d(Logger.TAG, "got history len=" + favorites.length);
        ((ListView) findViewById(C1283R.id.historyList)).setAdapter(new HistoryListAdapter(this, C1283R.layout.address_item, favorites));
    }

    public void addressItemClicked(View v) {
        ShareUtility.OpenShareActivityOrLogin(this, ShareType.ShareType_ShareSelection, null, (AddressItem) v.getTag(C1283R.id.addressItem), null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void navigateCallback(int rc) {
        Log.d(Logger.TAG, "navigateCallback:rc=" + rc);
        setResult(-1);
        finish();
    }
}
