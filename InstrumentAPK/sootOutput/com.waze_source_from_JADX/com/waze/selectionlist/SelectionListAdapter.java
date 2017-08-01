package com.waze.selectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import com.waze.C1283R;

public class SelectionListAdapter extends ArrayAdapter<String> {
    private int selection;

    public SelectionListAdapter(Context context, int textViewResourceId, String[] objects, int selection) {
        super(context, textViewResourceId, objects);
        this.selection = selection;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean z;
        View row = convertView;
        if (row == null) {
            row = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.list_item, parent, false);
        }
        View bg = row.findViewById(C1283R.id.itemBg);
        View top = row.findViewById(C1283R.id.topMargin);
        View bottom = row.findViewById(C1283R.id.bottomMargin);
        if (getCount() == 1) {
            bg.setBackgroundResource(C1283R.drawable.item_selector_single);
            top.setVisibility(0);
            bottom.setVisibility(0);
        } else if (position == 0) {
            bg.setBackgroundResource(C1283R.drawable.item_selector_top);
            top.setVisibility(0);
            bottom.setVisibility(8);
        } else if (position == getCount() - 1) {
            bg.setBackgroundResource(C1283R.drawable.item_selector_bottom);
            top.setVisibility(8);
            bottom.setVisibility(0);
        } else {
            bg.setBackgroundResource(C1283R.drawable.item_selector_middle);
            top.setVisibility(8);
            bottom.setVisibility(8);
        }
        bg.setPadding(0, 0, 0, 0);
        CheckedTextView ctv = (CheckedTextView) row.findViewById(C1283R.id.item);
        ctv.setText((CharSequence) getItem(position));
        if (position == this.selection) {
            z = true;
        } else {
            z = false;
        }
        ctv.setChecked(z);
        return row;
    }

    public void select(int position) {
        this.selection = position;
        notifyDataSetChanged();
    }
}
