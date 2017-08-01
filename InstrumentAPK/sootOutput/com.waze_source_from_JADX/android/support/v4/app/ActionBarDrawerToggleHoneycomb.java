package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggleHoneycomb {
    private static final String TAG = "ActionBarDrawerToggleHoneycomb";
    private static final int[] THEME_ATTRS = new int[]{16843531};

    private static class SetIndicatorInfo {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        SetIndicatorInfo(Activity $r1) throws  {
            SetIndicatorInfo setIndicatorInfo = this;
            try {
                this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[]{Drawable.class});
                Class $r2 = ActionBar.class;
                Class[] $r3 = new Class[1];
                $r3[0] = Integer.TYPE;
                this.setHomeActionContentDescription = $r2.getDeclaredMethod("setHomeActionContentDescription", $r3);
            } catch (NoSuchMethodException e) {
                View $r7 = $r1.findViewById(16908332);
                if ($r7 != null) {
                    ViewGroup $r9 = (ViewGroup) $r7.getParent();
                    if ($r9.getChildCount() == 2) {
                        $r7 = $r9.getChildAt(0);
                        View $r10 = $r9.getChildAt(1);
                        if ($r7.getId() == 16908332) {
                            $r7 = $r10;
                        }
                        if ($r7 instanceof ImageView) {
                            this.upIndicatorView = (ImageView) $r7;
                        }
                    }
                }
            }
        }
    }

    ActionBarDrawerToggleHoneycomb() throws  {
    }

    public static Object setActionBarUpIndicator(Object $r3, Activity $r0, Drawable $r1, int $i0) throws  {
        SetIndicatorInfo $r4;
        if ($r3 == null) {
            $r3 = $r4;
            $r4 = new SetIndicatorInfo($r0);
        }
        $r4 = (SetIndicatorInfo) $r3;
        if ($r4.setHomeAsUpIndicator != null) {
            try {
                ActionBar $r6 = $r0.getActionBar();
                $r4.setHomeAsUpIndicator.invoke($r6, new Object[]{$r1});
                $r4.setHomeActionContentDescription.invoke($r6, new Object[]{Integer.valueOf($i0)});
                return $r3;
            } catch (Exception $r2) {
                Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", $r2);
                return $r3;
            }
        } else if ($r4.upIndicatorView != null) {
            $r4.upIndicatorView.setImageDrawable($r1);
            return $r3;
        } else {
            Log.w(TAG, "Couldn't set home-as-up indicator");
            return $r3;
        }
    }

    public static Object setActionBarDescription(Object $r2, Activity $r0, int $i0) throws  {
        SetIndicatorInfo $r3;
        if ($r2 == null) {
            $r2 = $r3;
            $r3 = new SetIndicatorInfo($r0);
        }
        $r3 = (SetIndicatorInfo) $r2;
        if ($r3.setHomeAsUpIndicator == null) {
            return $r2;
        }
        try {
            ActionBar $r5 = $r0.getActionBar();
            $r3.setHomeActionContentDescription.invoke($r5, new Object[]{Integer.valueOf($i0)});
            if (VERSION.SDK_INT > 19) {
                return $r2;
            }
            $r5.setSubtitle($r5.getSubtitle());
            return $r2;
        } catch (Exception $r1) {
            Log.w(TAG, "Couldn't set content description via JB-MR2 API", $r1);
            return $r2;
        }
    }

    public static Drawable getThemeUpIndicator(Activity $r0) throws  {
        TypedArray $r2 = $r0.obtainStyledAttributes(THEME_ATTRS);
        Drawable $r3 = $r2.getDrawable(0);
        $r2.recycle();
        return $r3;
    }
}
