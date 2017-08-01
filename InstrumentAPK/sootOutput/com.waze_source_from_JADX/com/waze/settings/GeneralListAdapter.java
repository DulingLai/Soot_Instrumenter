package com.waze.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import com.waze.C1283R;

public class GeneralListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private SettingsValue[] values;

    public GeneralListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.values == null) {
            return 0;
        }
        return this.values.length;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(C1283R.layout.settings_item, null);
        }
        SettingsValue item = this.values[position];
        CheckedTextView name = (CheckedTextView) convertView.findViewById(C1283R.id.itemText);
        ImageView iconView = (ImageView) convertView.findViewById(C1283R.id.itemIcon);
        if (iconView == null || item == null || item.icon == null) {
            iconView.setVisibility(8);
        } else {
            iconView.setImageDrawable(item.icon);
            iconView.setVisibility(0);
        }
        name.setText(item.display);
        name.setChecked(item.isSelected);
        View container = convertView.findViewById(C1283R.id.itemContainer);
        if (position == 0) {
            if (position == this.values.length - 1) {
                container.setBackgroundResource(C1283R.drawable.item_navigate_single);
            } else {
                container.setBackgroundResource(C1283R.drawable.item_navigate_top);
            }
        } else if (position == this.values.length - 1) {
            container.setBackgroundResource(C1283R.drawable.item_navigate_bottom);
        } else {
            container.setBackgroundResource(C1283R.drawable.item_navigate_middle);
        }
        container.setPadding(0, 0, 0, 0);
        return convertView;
    }

    public void setValues(SettingsValue[] values) {
        this.values = values;
        notifyDataSetChanged();
    }
}
