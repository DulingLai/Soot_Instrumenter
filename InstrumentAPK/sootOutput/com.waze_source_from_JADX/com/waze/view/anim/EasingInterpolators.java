package com.waze.view.anim;

import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;
import com.waze.map.CanvasFont;
import com.waze.view.popups.BottomSheet;

public class EasingInterpolators {
    public static final Interpolator BOUNCE_IN = PathInterpolatorCompat.create(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 0.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, -0.5f);
    public static final Interpolator BOUNCE_OUT = PathInterpolatorCompat.create(0.0f, 1.5f, 0.25f, 1.0f);
    public static final Interpolator EASE_IN_EASE_OUT = PathInterpolatorCompat.create(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 0.0f, 0.25f, 1.0f);
    public static final Interpolator SMOOTH_EASE_IN = PathInterpolatorCompat.create(1.0f, 0.0f, 0.7f, 1.0f);
    public static final Interpolator SMOOTH_EASE_OUT = PathInterpolatorCompat.create(BottomSheet.DISABLED_ALPHA, 0.0f, 0.0f, 1.0f);
    public static final Interpolator SOFT_BOUNCE_OUT = PathInterpolatorCompat.create(0.2f, 1.25f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f);
    public static final Interpolator STRONG_EASE_IN = PathInterpolatorCompat.create(1.0f, 0.0f, 0.9f, 1.0f);
    public static final Interpolator STRONG_EASE_OUT = PathInterpolatorCompat.create(0.0f, 0.6f, 0.0f, 1.0f);
}
