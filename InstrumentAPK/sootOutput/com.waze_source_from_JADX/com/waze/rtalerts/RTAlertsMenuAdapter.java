package com.waze.rtalerts;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ResManager;
import java.util.ArrayList;

public class RTAlertsMenuAdapter extends BaseAdapter {
    private final Activity mContext;
    private final ArrayList<RowData> mRows = new ArrayList();

    public static final class RowData {
        public int mCounter;
        public int mIconResId;
        public int mId;
        public String mLabel;

        public RowData(int id, String icon_res, String label, int counter) {
            this.mId = id;
            this.mIconResId = ResManager.GetDrawableId(icon_res.toLowerCase());
            this.mLabel = label;
            this.mCounter = counter;
        }
    }

    private static class RowHolder {
        RelativeLayout container;
        TextView counter;
        ImageView icon;
        TextView label;

        private RowHolder() {
        }
    }

    public RTAlertsMenuAdapter(Activity context) {
        this.mContext = context;
    }

    public void updateList(RTAlertsMenuData[] data) {
        this.mRows.clear();
        for (int i = 0; i < data.length; i++) {
            if (NativeManager.getInstance().isEnforcementPoliceEnabledNTV() == 1) {
                this.mRows.add(new RowData(data[i].mId, data[i].mIcon, data[i].mLabel, data[i].mCounter));
            } else if (2 != data[i].mId) {
                this.mRows.add(new RowData(data[i].mId, data[i].mIcon, data[i].mLabel, data[i].mCounter));
            }
        }
    }

    public int getCount() {
        return this.mRows.size();
    }

    public Object getItem(int position) {
        return this.mRows.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.mRows.size() == 0) {
            return convertView;
        }
        RowHolder holder;
        RowData rowData = (RowData) this.mRows.get(position);
        if (convertView == null) {
            convertView = this.mContext.getLayoutInflater().inflate(C1283R.layout.rtalerts_menu_row, null, true);
            holder = new RowHolder();
            holder.container = (RelativeLayout) convertView.findViewById(C1283R.id.rtalerts_menu_row_container);
            holder.icon = (ImageView) convertView.findViewById(C1283R.id.rtalerts_menu_row_image);
            holder.label = (TextView) convertView.findViewById(C1283R.id.rtalerts_menu_row_label);
            holder.counter = (TextView) convertView.findViewById(C1283R.id.rtalerts_menu_row_counter);
            convertView.setTag(holder);
        } else {
            holder = (RowHolder) convertView.getTag();
        }
        holder.icon.setImageResource(rowData.mIconResId);
        holder.label.setText(rowData.mLabel);
        holder.counter.setText(Integer.toString(rowData.mCounter));
        if (this.mRows.size() == 1) {
            holder.container.setBackgroundResource(C1283R.drawable.item_selector_single);
        } else if (position == 0) {
            holder.container.setBackgroundResource(C1283R.drawable.item_selector_top);
        } else if (position == this.mRows.size() - 1) {
            holder.container.setBackgroundResource(C1283R.drawable.item_selector_middle);
        } else {
            holder.container.setBackgroundResource(C1283R.drawable.item_selector_middle);
        }
        holder.container.setPadding(0, 0, 0, 0);
        return convertView;
    }
}
