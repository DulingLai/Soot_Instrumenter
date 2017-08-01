package com.waze.settings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SettingValueAdapter extends BaseAdapter {
    private boolean bLargeIcons = false;
    private boolean bShowRadio = true;
    private boolean bSmallIcons = false;
    private SettingsValue mItemSelected = null;
    private WazeSettingsView mViewSelected = null;
    protected SettingsValue[] values;

    public SettingValueAdapter(Context context) {
    }

    public SettingsValue[] getValues() {
        return this.values;
    }

    public int getCount() {
        if (this.values == null) {
            return 0;
        }
        return this.values.length;
    }

    public SettingsValue getSelectedItem() {
        return this.mItemSelected;
    }

    public Object getItem(int arg0) {
        return this.values == null ? null : this.values[arg0];
    }

    public int getItemViewType(int position) {
        if (!this.values[position].isHeader && this.values[position].custom == null) {
            return 0;
        }
        return -1;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean z = true;
        if (!(convertView instanceof WazeSettingsView)) {
            convertView = null;
        }
        if (this.values[position].custom != null) {
            return this.values[position].custom;
        }
        if (this.values[position].isHeader) {
            Context context = parent.getContext();
            if (position != 0) {
                z = false;
            }
            View title = new SettingsTitleText(context, z);
            title.setText(this.values[position].display);
            return title;
        }
        View row = (WazeSettingsView) convertView;
        if (row == null) {
            int i;
            row = new WazeSettingsView(parent.getContext());
            if (this.bShowRadio) {
                i = 3;
            } else {
                i = 0;
            }
            row.setType(i);
            row.setBackgroundDrawable(null);
            row.setClickable(false);
        }
        if (this.mViewSelected == row) {
            this.mViewSelected = null;
        }
        SettingsValue item = this.values[position];
        if (item == null || item.icon == null) {
            row.setIcon(null);
        } else {
            row.setIcon(item.icon, this.bLargeIcons);
            if (this.bSmallIcons) {
                row.setSmallIcon();
            }
        }
        if (item.display2 == null || item.display2.equals("")) {
            row.setText(item.display);
        } else {
            row.setKeyText(item.display);
            row.setValueText(item.display2);
        }
        if (this.bShowRadio) {
            row.setRadioValue(item.isSelected, false);
        }
        if (item.isSelected) {
            this.mItemSelected = item;
            this.mViewSelected = row;
        }
        if (position == 0) {
            if (position == this.values.length - 1) {
                row.setPosition(3);
            } else {
                row.setPosition(1);
            }
        } else if (position == this.values.length - 1) {
            row.setPosition(2);
        } else {
            row.setPosition(0);
        }
        return row;
    }

    public void setValues(SettingsValue[] values) {
        this.values = values;
        notifyDataSetChanged();
    }

    public void setSelected(View v, int position) {
        if (this.bShowRadio && this.mViewSelected != v) {
            if (this.mViewSelected != null) {
                this.mViewSelected.setRadioValue(false, true);
            }
            this.mViewSelected = (WazeSettingsView) v;
            this.mViewSelected.setRadioValue(true, true);
        }
    }

    public void setSelection(int position) {
        if (this.bShowRadio) {
            this.mItemSelected.isSelected = false;
            this.mItemSelected = this.values[position];
            this.mItemSelected.isSelected = true;
            this.mViewSelected = null;
            notifyDataSetChanged();
        }
    }

    public void setLargeIcons(boolean b) {
        this.bLargeIcons = b;
    }

    public void setSmallIcons(boolean b) {
        this.bSmallIcons = b;
    }

    public void setShowRadio(boolean b) {
        this.bShowRadio = b;
    }
}
