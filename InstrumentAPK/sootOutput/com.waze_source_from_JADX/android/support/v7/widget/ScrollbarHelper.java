package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

class ScrollbarHelper {
    ScrollbarHelper() throws  {
    }

    static int computeScrollOffset(State $r0, OrientationHelper $r1, View $r2, View $r3, LayoutManager $r4, boolean $z0, boolean $z1) throws  {
        if ($r4.getChildCount() == 0) {
            return 0;
        }
        if ($r0.getItemCount() == 0) {
            return 0;
        }
        if ($r2 == null) {
            return 0;
        }
        if ($r3 == null) {
            return 0;
        }
        int $i1 = $z1 ? Math.max(0, ($r0.getItemCount() - Math.max($r4.getPosition($r2), $r4.getPosition($r3))) - 1) : Math.max(0, Math.min($r4.getPosition($r2), $r4.getPosition($r3)));
        if (!$z0) {
            return $i1;
        }
        return Math.round((((float) $i1) * (((float) Math.abs($r1.getDecoratedEnd($r3) - $r1.getDecoratedStart($r2))) / ((float) (Math.abs($r4.getPosition($r2) - $r4.getPosition($r3)) + 1)))) + ((float) ($r1.getStartAfterPadding() - $r1.getDecoratedStart($r2))));
    }

    static int computeScrollExtent(State $r0, OrientationHelper $r1, View $r2, View $r3, LayoutManager $r4, boolean $z0) throws  {
        if ($r4.getChildCount() == 0 || $r0.getItemCount() == 0 || $r2 == null || $r3 == null) {
            return 0;
        }
        if (!$z0) {
            return Math.abs($r4.getPosition($r2) - $r4.getPosition($r3)) + 1;
        }
        return Math.min($r1.getTotalSpace(), $r1.getDecoratedEnd($r3) - $r1.getDecoratedStart($r2));
    }

    static int computeScrollRange(State $r0, OrientationHelper $r1, View $r2, View $r3, LayoutManager $r4, boolean $z0) throws  {
        if ($r4.getChildCount() == 0 || $r0.getItemCount() == 0 || $r2 == null || $r3 == null) {
            return 0;
        }
        if (!$z0) {
            return $r0.getItemCount();
        }
        return (int) ((((float) ($r1.getDecoratedEnd($r3) - $r1.getDecoratedStart($r2))) / ((float) (Math.abs($r4.getPosition($r2) - $r4.getPosition($r3)) + 1))) * ((float) $r0.getItemCount()));
    }
}
