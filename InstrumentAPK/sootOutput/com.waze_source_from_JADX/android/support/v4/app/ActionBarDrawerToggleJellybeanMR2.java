package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

class ActionBarDrawerToggleJellybeanMR2 {
    private static final String TAG = "ActionBarDrawerToggleImplJellybeanMR2";
    private static final int[] THEME_ATTRS = new int[]{16843531};

    ActionBarDrawerToggleJellybeanMR2() throws  {
    }

    public static Object setActionBarUpIndicator(Object $r0, Activity $r1, Drawable $r2, int $i0) throws  {
        ActionBar $r3 = $r1.getActionBar();
        if ($r3 == null) {
            return $r0;
        }
        $r3.setHomeAsUpIndicator($r2);
        $r3.setHomeActionContentDescription($i0);
        return $r0;
    }

    public static Object setActionBarDescription(Object $r0, Activity $r1, int $i0) throws  {
        ActionBar $r2 = $r1.getActionBar();
        if ($r2 == null) {
            return $r0;
        }
        $r2.setHomeActionContentDescription($i0);
        return $r0;
    }

    public static Drawable getThemeUpIndicator(Activity $r0) throws  {
        Context $r3;
        ActionBar $r2 = $r0.getActionBar();
        if ($r2 != null) {
            $r3 = $r2.getThemedContext();
        } else {
            $r3 = $r0;
        }
        TypedArray $r4 = $r3.obtainStyledAttributes(null, THEME_ATTRS, 16843470, 0);
        Drawable $r5 = $r4.getDrawable(0);
        $r4.recycle();
        return $r5;
    }
}
