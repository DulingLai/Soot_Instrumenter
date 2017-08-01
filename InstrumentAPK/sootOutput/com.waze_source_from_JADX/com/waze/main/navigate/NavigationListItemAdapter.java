package com.waze.main.navigate;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.navbar.InstructionGeometryDrawable;
import com.waze.navbar.NavBar;
import com.waze.navigate.NavigateNativeManager;
import com.waze.strings.DisplayStrings;

public class NavigationListItemAdapter extends BaseAdapter {
    private boolean driveOnLeft;
    private LayoutInflater inflater;
    private NavigationItem[] items;

    public Object getItem(int arg0) throws  {
        return null;
    }

    public long getItemId(int position) throws  {
        return 0;
    }

    public NavigationListItemAdapter(Context $r1, boolean $z0) throws  {
        this.inflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
        this.driveOnLeft = $z0;
    }

    public int getCount() throws  {
        return this.items == null ? 0 : this.items.length;
    }

    public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
        if (this.items == null) {
            return null;
        }
        Resources $r6 = $r2.getResources();
        final NavigationItem $r3 = this.items[$i0];
        View $r5 = $r1;
        if ($r1 == null) {
            $r5 = this.inflater.inflate(C1283R.layout.navigation_list_item, $r2, false);
        }
        $r5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                NavigateNativeManager.instance().centerOnPosition($r3.lat, $r3.lon, $r3.rotation);
                AppService.getMainActivity().getLayoutMgr().getNavBar().toggleNavListFragment();
            }
        });
        ImageView $r9 = (ImageView) $r5.findViewById(C1283R.id.navListItemImage);
        if ($r3.instructionGeometry != null) {
            $r9.setImageDrawable(new InstructionGeometryDrawable($r3.instructionGeometry));
        } else {
            $r9.setImageResource(this.driveOnLeft ? NavBar.instImagesLeft[$r3.instruction] : NavBar.instImagesRight[$r3.instruction]);
        }
        TextView $r12 = (TextView) $r5.findViewById(C1283R.id.navListItemDistance);
        String $r13 = $r3.distance;
        $r12.setText($r13);
        $r12 = (TextView) $r5.findViewById(C1283R.id.navListItemName);
        String $r132 = $r3.address;
        if ($r132.equalsIgnoreCase(DisplayStrings.displayString(287)) || $r132.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_HOME))) {
            $r132 = DisplayStrings.displayString(287);
        } else if ($r132.equalsIgnoreCase(DisplayStrings.displayString(288)) || $r132.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_WORK))) {
            $r132 = DisplayStrings.displayString(288);
        }
        $r12.setText($r132);
        $r12.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                boolean $z0 = true;
                $r12.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if ($r12.getLineCount() <= 1) {
                    $z0 = false;
                }
                $r12.setTextSize(2, $z0 ? 22.0f : 26.0f);
            }
        });
        $r12 = (TextView) $r5.findViewById(C1283R.id.lblExitNumber);
        if ($r3.instructionExit > 0) {
            $r12.setVisibility(0);
            int $i1 = $r3.instructionExit;
            $r12.setText(String.valueOf($i1));
        } else {
            $r12.setVisibility(8);
        }
        $r1 = $r5.findViewById(C1283R.id.navListItemContainer);
        if ($i0 % 2 == 0) {
            $r1.setBackgroundColor($r6.getColor(C1283R.color.DarkBlueAlt));
            return $r5;
        }
        $r1.setBackgroundColor($r6.getColor(C1283R.color.DarkBlue));
        return $r5;
    }

    public void setItems(NavigationItem[] $r1) throws  {
        this.items = $r1;
    }
}
