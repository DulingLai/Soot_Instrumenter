package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.C0195R;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemTouchUIUtilImpl {

    static class Gingerbread implements ItemTouchUIUtil {
        Gingerbread() throws  {
        }

        private void draw(Canvas $r1, RecyclerView $r2, View $r3, float $f0, float $f1) throws  {
            $r1.save();
            $r1.translate($f0, $f1);
            $r2.drawChild($r1, $r3, 0);
            $r1.restore();
        }

        public void clearView(View $r1) throws  {
            $r1.setVisibility(0);
        }

        public void onSelected(View $r1) throws  {
            $r1.setVisibility(4);
        }

        public void onDraw(Canvas $r1, RecyclerView $r2, View $r3, float $f0, float $f1, int $i0, boolean isCurrentlyActive) throws  {
            if ($i0 != 2) {
                draw($r1, $r2, $r3, $f0, $f1);
            }
        }

        public void onDrawOver(Canvas $r1, RecyclerView $r2, View $r3, float $f0, float $f1, int $i0, boolean isCurrentlyActive) throws  {
            if ($i0 == 2) {
                draw($r1, $r2, $r3, $f0, $f1);
            }
        }
    }

    static class Honeycomb implements ItemTouchUIUtil {
        Honeycomb() throws  {
        }

        public void clearView(View $r1) throws  {
            ViewCompat.setTranslationX($r1, 0.0f);
            ViewCompat.setTranslationY($r1, 0.0f);
        }

        public void onSelected(View view) throws  {
        }

        public void onDraw(Canvas c, RecyclerView recyclerView, View $r3, float $f0, float $f1, int actionState, boolean isCurrentlyActive) throws  {
            ViewCompat.setTranslationX($r3, $f0);
            ViewCompat.setTranslationY($r3, $f1);
        }

        public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) throws  {
        }
    }

    static class Lollipop extends Honeycomb {
        Lollipop() throws  {
        }

        public void onDraw(Canvas $r1, RecyclerView $r2, View $r3, float $f0, float $f1, int $i0, boolean $z0) throws  {
            if ($z0 && $r3.getTag(C0195R.id.item_touch_helper_previous_elevation) == null) {
                Float $r5 = Float.valueOf(ViewCompat.getElevation($r3));
                ViewCompat.setElevation($r3, 1.0f + findMaxElevation($r2, $r3));
                $r3.setTag(C0195R.id.item_touch_helper_previous_elevation, $r5);
            }
            super.onDraw($r1, $r2, $r3, $f0, $f1, $i0, $z0);
        }

        private float findMaxElevation(RecyclerView $r1, View $r2) throws  {
            int $i1 = $r1.getChildCount();
            float $f0 = 0.0f;
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                View $r3 = $r1.getChildAt($i2);
                if ($r3 != $r2) {
                    float $f1 = ViewCompat.getElevation($r3);
                    if ($f1 > $f0) {
                        $f0 = $f1;
                    }
                }
            }
            return $f0;
        }

        public void clearView(View $r1) throws  {
            Object $r2 = $r1.getTag(C0195R.id.item_touch_helper_previous_elevation);
            if ($r2 != null && ($r2 instanceof Float)) {
                ViewCompat.setElevation($r1, ((Float) $r2).floatValue());
            }
            $r1.setTag(C0195R.id.item_touch_helper_previous_elevation, null);
            super.clearView($r1);
        }
    }

    ItemTouchUIUtilImpl() throws  {
    }
}
