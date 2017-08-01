package com.waze.reports;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ResManager;
import com.waze.mywaze.Group;
import com.waze.strings.DisplayStrings;

public class ReportGroupAdapter extends BaseAdapter {
    private Group[] groups;
    private LayoutInflater inflater;

    public ReportGroupAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.groups == null) {
            return 0;
        }
        return this.groups.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(C1283R.layout.settings_item, null);
        }
        Group item = this.groups[position];
        CheckedTextView name = (CheckedTextView) convertView.findViewById(C1283R.id.itemText);
        ImageView iconView = (ImageView) convertView.findViewById(C1283R.id.itemIcon);
        Drawable icon = ResManager.GetSkinDrawable(item.icon + ResManager.mImageExtension);
        if (iconView == null || icon == null) {
            iconView.setVisibility(8);
        } else {
            iconView.setImageDrawable(icon);
            iconView.setVisibility(0);
        }
        if (item.name.equals("")) {
            name.setText(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_NO_GROUP));
        } else {
            name.setText(item.name);
        }
        name.setChecked(item.isSelected);
        View container = convertView.findViewById(C1283R.id.itemContainer);
        if (position == 0) {
            if (position == this.groups.length - 1) {
                container.setBackgroundResource(C1283R.drawable.item_selector_single);
            } else {
                container.setBackgroundResource(C1283R.drawable.item_selector_top);
            }
        } else if (position == this.groups.length - 1) {
            container.setBackgroundResource(C1283R.drawable.item_selector_bottom);
        } else {
            container.setBackgroundResource(C1283R.drawable.item_selector_middle);
        }
        container.setPadding(0, 0, 0, 0);
        return convertView;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
    }
}
