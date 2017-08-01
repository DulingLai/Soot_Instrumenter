package com.waze.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class PixelMeasure {
    private static Resources mResources;

    public static int dp(int $i0) throws  {
        return (int) TypedValue.applyDimension(1, (float) $i0, mResources.getDisplayMetrics());
    }

    public static int dimension(int $i0) throws  {
        return mResources.getDimensionPixelSize($i0);
    }

    public static void setResources(Resources $r0) throws  {
        mResources = $r0;
    }

    public static void setResourceIfUnset(Resources $r0) throws  {
        if (mResources == null) {
            mResources = $r0;
        }
    }
}
