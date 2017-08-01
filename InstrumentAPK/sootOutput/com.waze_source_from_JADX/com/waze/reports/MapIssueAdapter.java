package com.waze.reports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.rtalerts.MapProblem;

public class MapIssueAdapter extends BaseAdapter implements ListAdapter {
    private LayoutInflater inflater;
    private MapProblem[] problems;
    private int selected;

    public MapIssueAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.problems == null) {
            return 0;
        }
        return this.problems.length;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            convertView = this.inflater.inflate(C1283R.layout.map_issue_item, null);
        }
        CheckedTextView name = (CheckedTextView) convertView.findViewById(C1283R.id.itemText);
        name.setText(NativeManager.getInstance().getLanguageString(this.problems[position].description));
        if (position == this.selected) {
            name.setChecked(true);
        } else {
            name.setChecked(false);
        }
        return convertView;
    }

    public void setSelected(int position) {
        this.selected = position;
    }

    public int getSelected() {
        return this.selected;
    }

    public void setProblems(MapProblem[] problems) {
        this.problems = problems;
    }
}
