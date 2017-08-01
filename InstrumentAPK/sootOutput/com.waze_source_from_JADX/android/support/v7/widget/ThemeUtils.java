package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;

class ThemeUtils {
    static final int[] ACTIVATED_STATE_SET = new int[]{16843518};
    static final int[] CHECKED_STATE_SET = new int[]{16842912};
    static final int[] DISABLED_STATE_SET = new int[]{-16842910};
    static final int[] EMPTY_STATE_SET = new int[0];
    static final int[] FOCUSED_STATE_SET = new int[]{16842908};
    static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[]{-16842919, -16842908};
    static final int[] PRESSED_STATE_SET = new int[]{16842919};
    static final int[] SELECTED_STATE_SET = new int[]{16842913};
    private static final int[] TEMP_ARRAY = new int[1];
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal();

    ThemeUtils() throws  {
    }

    public static ColorStateList createDisabledStateList(int $i0, int $i1) throws  {
        $r1 = new int[2][];
        int[] $r0 = new int[]{DISABLED_STATE_SET, $i1};
        $i1 = 0 + 1;
        $r1[$i1] = EMPTY_STATE_SET;
        $r0[$i1] = $i0;
        return new ColorStateList($r1, $r0);
    }

    public static int getThemeAttrColor(Context $r0, int $i0) throws  {
        TEMP_ARRAY[0] = $i0;
        TypedArray $r2 = $r0.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            $i0 = $r2.getColor(0, 0);
            return $i0;
        } finally {
            $r2.recycle();
        }
    }

    public static ColorStateList getThemeAttrColorStateList(Context $r0, int $i0) throws  {
        TEMP_ARRAY[0] = $i0;
        TypedArray $r2 = $r0.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            ColorStateList $r3 = $r2.getColorStateList(0);
            return $r3;
        } finally {
            $r2.recycle();
        }
    }

    public static int getDisabledThemeAttrColor(Context $r0, int $i0) throws  {
        ColorStateList $r1 = getThemeAttrColorStateList($r0, $i0);
        if ($r1 != null && $r1.isStateful()) {
            return $r1.getColorForState(DISABLED_STATE_SET, $r1.getDefaultColor());
        }
        TypedValue $r3 = getTypedValue();
        $r0.getTheme().resolveAttribute(16842803, $r3, true);
        return getThemeAttrColor($r0, $i0, $r3.getFloat());
    }

    private static TypedValue getTypedValue() throws  {
        TypedValue $r2 = (TypedValue) TL_TYPED_VALUE.get();
        if ($r2 != null) {
            return $r2;
        }
        $r2 = new TypedValue();
        TL_TYPED_VALUE.set($r2);
        return $r2;
    }

    static int getThemeAttrColor(Context $r0, int $i0, float $f0) throws  {
        $i0 = getThemeAttrColor($r0, $i0);
        return ColorUtils.setAlphaComponent($i0, Math.round(((float) Color.alpha($i0)) * $f0));
    }
}
