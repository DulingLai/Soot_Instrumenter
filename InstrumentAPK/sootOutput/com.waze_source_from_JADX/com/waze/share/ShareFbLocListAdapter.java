package com.waze.share;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import java.util.ArrayList;
import java.util.Collections;

public class ShareFbLocListAdapter extends BaseAdapter {
    private final ActivityBase mContext;
    private ArrayList<ShareFbLocation> mItems;

    private static class ItemHolder {
        TextView address;
        TextView distance;
        TextView name;

        private ItemHolder() {
        }
    }

    public ShareFbLocListAdapter(ActivityBase context) {
        this.mContext = context;
    }

    public void updateList(ArrayList<ShareFbLocation> data, int limit) {
        this.mItems = data;
        Collections.sort(this.mItems);
        for (int i = limit; i < this.mItems.size(); i++) {
            this.mItems.remove(i);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.mItems == null) {
            return 0;
        }
        return this.mItems.size();
    }

    public Object getItem(int position) {
        return this.mItems.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ShareFbLocation itemData = (ShareFbLocation) this.mItems.get(position);
        if (convertView == null) {
            convertView = this.mContext.getLayoutInflater().inflate(C1283R.layout.share_fb_location_item, parent, false);
        }
        return getView(this.mContext, convertView, itemData);
    }

    public static View getView(ActivityBase context, View convertView, ShareFbLocation itemData) {
        if (convertView == null) {
            return null;
        }
        String units;
        ItemHolder holder = (ItemHolder) convertView.getTag();
        if (holder == null) {
            holder = new ItemHolder();
            holder.name = (TextView) convertView.findViewById(C1283R.id.shareFbLocItemTextName);
            holder.address = (TextView) convertView.findViewById(C1283R.id.shareFbLocItemTextAddress);
            holder.distance = (TextView) convertView.findViewById(C1283R.id.shareFbLocItemTextDistance);
            convertView.setTag(holder);
        }
        NativeManager nm = NativeManager.getInstance();
        String away = nm.getLanguageString(333);
        holder.name.setText(itemData.name);
        holder.address.setText(itemData.address);
        if (ShareNativeManager.getInstance().isMetricUnits()) {
            units = nm.getLanguageString(133);
        } else {
            units = nm.getLanguageString(DisplayStrings.DS_MILE);
        }
        holder.distance.setText(itemData.distance + " " + units + " " + away);
        return convertView;
    }
}
