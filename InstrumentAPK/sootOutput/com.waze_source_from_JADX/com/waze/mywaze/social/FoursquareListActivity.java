package com.waze.mywaze.social;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;

public class FoursquareListActivity extends ActivityBase {
    private MyWazeNativeManager mywazeManager = MyWazeNativeManager.getInstance();

    class C20161 implements OnItemClickListener {
        C20161() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View v, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            FoursquareListActivity.this.mywazeManager.selectFoursquareVenue($i0);
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.foursquare_list);
        ((TitleBar) findViewById(C1283R.id.title)).init(this, 162);
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            String[] $r7 = $r1.getStringArray("com.waze.mywaze.foursquare.descriptions");
            String[] $r8 = $r1.getStringArray("com.waze.mywaze.foursquare.addresses");
            String[] $r9 = $r1.getStringArray("com.waze.mywaze.foursquare.distances");
            FoursquareVenue[] $r2 = new FoursquareVenue[$r7.length];
            for (int $i0 = 0; $i0 < $r2.length; $i0++) {
                $r2[$i0] = new FoursquareVenue($r7[$i0], $r8[$i0], $r9[$i0]);
            }
            ListView $r13 = (ListView) findViewById(C1283R.id.list);
            $r13.setAdapter(new FoursquareListAdapter(getApplicationContext(), C1283R.layout.foursquare_item, $r2));
            $r13.setOnItemClickListener(new C20161());
        }
    }
}
