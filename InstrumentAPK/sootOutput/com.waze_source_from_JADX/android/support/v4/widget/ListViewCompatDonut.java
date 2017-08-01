package android.support.v4.widget;

import android.view.View;
import android.widget.ListView;

class ListViewCompatDonut {
    ListViewCompatDonut() throws  {
    }

    static void scrollListBy(ListView $r0, int $i0) throws  {
        int $i1 = $r0.getFirstVisiblePosition();
        if ($i1 != -1) {
            View $r1 = $r0.getChildAt(0);
            if ($r1 != null) {
                $r0.setSelectionFromTop($i1, $r1.getTop() - $i0);
            }
        }
    }
}
