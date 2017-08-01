package com.waze.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public final class MySavedLocationActivity extends ActivityBase implements DriveToGetAddressItemArrayCallback, DriveToNavigateCallback {
    private DriveToNativeManager nm;

    class C28121 implements OnItemClickListener {
        C28121() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            MySavedLocationActivity.this.addressItemClicked(arg1);
        }
    }

    private class FavListAdapter extends BaseAdapter {
        private AddressItem[] favoriteItems;

        public FavListAdapter(Context context, int textViewResourceId, AddressItem[] fi) {
            this.favoriteItems = fi;
        }

        public int getCount() {
            return this.favoriteItems.length;
        }

        public Object getItem(int position) {
            return this.favoriteItems[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = ((LayoutInflater) MySavedLocationActivity.this.getSystemService("layout_inflater")).inflate(C1283R.layout.address_item, null);
            }
            view.setTag(C1283R.id.addressItem, this.favoriteItems[position]);
            view.findViewById(C1283R.id.shortAddressItemCol).setVisibility(0);
            if (this.favoriteItems[position] != null) {
                int i;
                view.findViewById(C1283R.id.addressItemAddress).setVisibility(8);
                view.findViewById(C1283R.id.addressItemDistance).setVisibility(8);
                view.findViewById(C1283R.id.addressItemTouch).setVisibility(8);
                ((TextView) view.findViewById(C1283R.id.addressItemName)).setText(Html.fromHtml(this.favoriteItems[position].getTitle()));
                Integer img = this.favoriteItems[position].getImage();
                if (img != null) {
                    ((ImageView) view.findViewById(C1283R.id.addressItemImage)).setImageResource(img.intValue());
                    view.findViewById(C1283R.id.addressItemImage).setVisibility(0);
                } else {
                    view.findViewById(C1283R.id.addressItemImage).setVisibility(8);
                }
                RelativeLayout btn = (RelativeLayout) view.findViewById(C1283R.id.moreActionButton);
                btn.setTag(C1283R.id.addressItem, this.favoriteItems[position]);
                btn.setVisibility(4);
                View aiView = view.findViewById(C1283R.id.addressItem);
                if (position == 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                switch (i | (position == getCount() + -1 ? 2 : 0)) {
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
        setContentView(C1283R.layout.favorites);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 16);
        ((ListView) findViewById(C1283R.id.favList)).setOnItemClickListener(new C28121());
        this.nm = DriveToNativeManager.getInstance();
        this.nm.getFavorites(this, false);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getAddressItemArrayCallback(AddressItem[] favorites) {
        if (favorites.length > 0) {
            ((ListView) findViewById(C1283R.id.favList)).setAdapter(new FavListAdapter(this, C1283R.layout.address_item, favorites));
        }
    }

    public void addressItemClicked(View v) {
        ShareUtility.OpenShareActivityOrLogin(this, ShareType.ShareType_ShareSelection, null, (AddressItem) v.getTag(C1283R.id.addressItem), null);
    }

    public void navigateCallback(int rc) {
        Log.d(Logger.TAG, "navigateCallback:rc=" + rc);
        setResult(-1);
        finish();
    }
}
