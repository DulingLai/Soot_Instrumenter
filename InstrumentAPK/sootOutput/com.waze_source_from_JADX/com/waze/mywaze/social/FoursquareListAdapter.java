package com.waze.mywaze.social;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.waze.C1283R;

public class FoursquareListAdapter extends ArrayAdapter<FoursquareVenue> {
    public FoursquareListAdapter(Context $r1, int $i0, FoursquareVenue[] $r2) throws  {
        super($r1, $i0, $r2);
    }

    public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
        View $r3 = $r1;
        if ($r1 == null) {
            $r3 = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.foursquare_item, $r2, false);
        }
        $r1 = $r3.findViewById(C1283R.id.itemContainer);
        View $r7 = $r3.findViewById(C1283R.id.topMargin);
        View $r8 = $r3.findViewById(C1283R.id.bottomMargin);
        if (getCount() == 1) {
            $r1.setBackgroundResource(C1283R.drawable.item_selector_single);
            $r7.setVisibility(0);
            $r8.setVisibility(0);
        } else if ($i0 == 0) {
            $r1.setBackgroundResource(C1283R.drawable.item_selector_top);
            $r7.setVisibility(0);
            $r8.setVisibility(8);
        } else if ($i0 == getCount() - 1) {
            $r1.setBackgroundResource(C1283R.drawable.item_selector_bottom);
            $r7.setVisibility(8);
            $r8.setVisibility(0);
        } else {
            $r1.setBackgroundResource(C1283R.drawable.item_selector_middle);
            $r7.setVisibility(8);
            $r8.setVisibility(8);
        }
        $r1.setPadding(0, 0, 0, 0);
        TextView $r9 = (TextView) $r3.findViewById(C1283R.id.description);
        String $r11 = ((FoursquareVenue) getItem($i0)).description;
        $r9.setText($r11);
        $r9 = (TextView) $r3.findViewById(C1283R.id.address);
        $r11 = ((FoursquareVenue) getItem($i0)).address;
        $r9.setText($r11);
        $r9 = (TextView) $r3.findViewById(C1283R.id.distance);
        $r11 = ((FoursquareVenue) getItem($i0)).distance;
        $r9.setText($r11);
        return $r3;
    }
}
