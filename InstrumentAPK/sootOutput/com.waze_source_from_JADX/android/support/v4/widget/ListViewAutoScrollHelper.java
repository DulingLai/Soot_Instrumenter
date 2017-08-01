package android.support.v4.widget;

import android.widget.ListView;

public class ListViewAutoScrollHelper extends AutoScrollHelper {
    private final ListView mTarget;

    public boolean canTargetScrollHorizontally(int direction) throws  {
        return false;
    }

    public ListViewAutoScrollHelper(ListView $r1) throws  {
        super($r1);
        this.mTarget = $r1;
    }

    public void scrollTargetBy(int deltaX, int $i1) throws  {
        ListViewCompat.scrollListBy(this.mTarget, $i1);
    }

    public boolean canTargetScrollVertically(int $i0) throws  {
        ListView $r1 = this.mTarget;
        int $i2 = $r1.getCount();
        if ($i2 == 0) {
            return false;
        }
        int $i3 = $r1.getChildCount();
        int $i4 = $r1.getFirstVisiblePosition();
        int $i1 = $i4 + $i3;
        if ($i0 > 0) {
            if ($i1 >= $i2 && $r1.getChildAt($i3 - 1).getBottom() <= $r1.getHeight()) {
                return false;
            }
        } else if ($i0 >= 0) {
            return false;
        } else {
            if ($i4 <= 0 && $r1.getChildAt(0).getTop() >= 0) {
                return false;
            }
        }
        return true;
    }
}
