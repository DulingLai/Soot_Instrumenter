package com.waze.navigate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.waze.C1283R;

public class MenuAdapter extends BaseAdapter implements ListAdapter {
    private LayoutInflater inflater;

    static class ViewHolder {
        TextView text;

        ViewHolder() throws  {
        }
    }

    public int getCount() throws  {
        return 2;
    }

    public Object getItem(int position) throws  {
        return null;
    }

    public long getItemId(int position) throws  {
        return 0;
    }

    public MenuAdapter(Context $r1) throws  {
        this.inflater = LayoutInflater.from($r1);
    }

    public View getView(int $i0, View $r2, ViewGroup parent) throws  {
        ViewHolder $r5;
        if ($r2 == null) {
            View $r4 = this.inflater.inflate(C1283R.layout.menu_adapter, null);
            $r2 = $r4;
            $r5 = new ViewHolder();
            $r5.text = (TextView) $r4.findViewById(C1283R.id.menuAdapterText);
            $r4.setTag($r5);
        } else {
            $r5 = (ViewHolder) $r2.getTag();
        }
        $r5.text.setText("Hey " + $i0);
        return $r2;
    }
}
