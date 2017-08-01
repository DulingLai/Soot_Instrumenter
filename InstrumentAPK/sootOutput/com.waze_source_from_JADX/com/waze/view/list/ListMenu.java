package com.waze.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.waze.C1283R;

public class ListMenu extends RelativeLayout {
    private ListView mListView = ((ListView) findViewById(C1283R.id.listmenu_listview));

    public ListMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C1283R.layout.listmenu, this);
    }

    public void setAdapter(ListMenuAdapter adapter) {
        this.mListView.setAdapter(adapter);
    }

    public ListView getListView() {
        return this.mListView;
    }
}
