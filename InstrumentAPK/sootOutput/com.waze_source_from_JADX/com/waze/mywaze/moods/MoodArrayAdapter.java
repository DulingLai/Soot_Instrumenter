package com.waze.mywaze.moods;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MoodManager;

public class MoodArrayAdapter extends ArrayAdapter<MoodItem> {
    private MoodManager moodManager = MoodManager.getInstance();

    public MoodArrayAdapter(Context $r1, int $i0, MoodItem[] $r2) throws  {
        super($r1, $i0, $r2);
    }

    public boolean isEnabled(int $i0) throws  {
        return ((MoodItem) getItem($i0)).enabled;
    }

    public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
        Log.d(Logger.TAG, "Getting mood view for item no." + $i0);
        MoodItem $r6 = (MoodItem) getItem($i0);
        Log.d(Logger.TAG, "Item is " + $r6.caption);
        return $r6.title ? getTitleView($r6.caption, $r1, $r2) : getItemView($r6, $r1, $r2);
    }

    private View getTitleView(String $r1, View $r2, ViewGroup $r3) throws  {
        View $r4 = $r2;
        if ($r2 == null || $r2.getId() != C1283R.id.moodTitleView) {
            $r4 = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.mood_title, $r3, false);
        }
        ((TextView) $r4.findViewById(C1283R.id.moodTitle)).setText($r1);
        return $r4;
    }

    private View getItemView(MoodItem $r1, View $r2, ViewGroup $r3) throws  {
        View $r4 = $r2;
        if ($r2 == null || $r2.getId() != C1283R.id.moodItemView) {
            $r4 = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.mood_item, $r3, false);
        }
        ((ImageView) $r4.findViewById(C1283R.id.moodIcon)).setImageDrawable($r1.image);
        CheckedTextView $r10 = (CheckedTextView) $r4.findViewById(C1283R.id.moodName);
        String $r11 = $r1.caption;
        $r10.setText($r11);
        String $r112 = $r1.mood;
        MoodManager $r12 = this.moodManager;
        $r10.setChecked($r112.equals($r12.getWazerMood()));
        $r10.setTextColor(getContext().getResources().getColor($r1.enabled ? C1283R.color.solid_black : C1283R.color.grayed_out));
        $r2 = $r4.findViewById(C1283R.id.moodItemContainer);
        if ($r1.last) {
            if ($r1.first) {
                $r2.setBackgroundResource(C1283R.drawable.item_selector_single);
            } else {
                $r2.setBackgroundResource(C1283R.drawable.item_selector_bottom);
            }
        } else if ($r1.first) {
            $r2.setBackgroundResource(C1283R.drawable.item_selector_top);
        } else {
            $r2.setBackgroundResource(C1283R.drawable.item_selector_middle);
        }
        $r2.setPadding(0, 0, 0, 0);
        return $r4;
    }
}
