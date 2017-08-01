package android.support.v4.graphics;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import com.waze.FriendsBarFragment;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

public final class ColorUtils {
    private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
    private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
    private static final double XYZ_EPSILON = 0.008856d;
    private static final double XYZ_KAPPA = 903.3d;
    private static final double XYZ_WHITE_REFERENCE_X = 95.047d;
    private static final double XYZ_WHITE_REFERENCE_Y = 100.0d;
    private static final double XYZ_WHITE_REFERENCE_Z = 108.883d;

    private static int constrain(int $i0, int $i2, int $i1) throws  {
        return $i0 < $i2 ? $i2 : $i0 > $i1 ? $i1 : $i0;
    }

    private ColorUtils() throws  {
    }

    public static int compositeColors(@ColorInt int $i0, @ColorInt int $i1) throws  {
        int $i2 = Color.alpha($i1);
        int $i3 = Color.alpha($i0);
        int $i4 = compositeAlpha($i3, $i2);
        return Color.argb($i4, compositeComponent(Color.red($i0), $i3, Color.red($i1), $i2, $i4), compositeComponent(Color.green($i0), $i3, Color.green($i1), $i2, $i4), compositeComponent(Color.blue($i0), $i3, Color.blue($i1), $i2, $i4));
    }

    private static int compositeAlpha(int $i0, int $i1) throws  {
        return 255 - (((255 - $i1) * (255 - $i0)) / 255);
    }

    private static int compositeComponent(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        return $i4 == 0 ? 0 : ((($i0 * 255) * $i1) + (($i2 * $i3) * (255 - $i1))) / ($i4 * 255);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public static double calculateLuminance(@ColorInt int $i0) throws  {
        double[] $r0 = getTempDouble3Array();
        colorToXYZ($i0, $r0);
        return $r0[1] / XYZ_WHITE_REFERENCE_Y;
    }

    public static double calculateContrast(@ColorInt int $i1, @ColorInt int $i0) throws  {
        if (Color.alpha($i0) != 255) {
            throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString($i0));
        }
        if (Color.alpha($i1) < 255) {
            $i1 = compositeColors($i1, $i0);
        }
        double $d0 = calculateLuminance($i1) + 0.05d;
        double $d1 = calculateLuminance($i0) + 0.05d;
        return Math.max($d0, $d1) / Math.min($d0, $d1);
    }

    public static int calculateMinimumAlpha(@ColorInt int $i0, @ColorInt int $i1, float $f0) throws  {
        if (Color.alpha($i1) != 255) {
            throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString($i1));
        } else if (calculateContrast(setAlphaComponent($i0, 255), $i1) < ((double) $f0)) {
            return -1;
        } else {
            int $i6 = 0;
            int $i3 = 255;
            for (int $i5 = 0; $i5 <= 10 && $i3 - $i6 > 1; $i5++) {
                int $i2 = ($i6 + $i3) / 2;
                if (calculateContrast(setAlphaComponent($i0, $i2), $i1) < ((double) $f0)) {
                    $i6 = $i2;
                } else {
                    $i3 = $i2;
                }
            }
            return $i3;
        }
    }

    public static void RGBToHSL(@IntRange(from = 0, to = 255) int $i0, @IntRange(from = 0, to = 255) int $i1, @IntRange(from = 0, to = 255) int $i2, @NonNull float[] $r0) throws  {
        float $f4 = ((float) $i0) / 255.0f;
        float $f2 = ((float) $i1) / 255.0f;
        float $f0 = ((float) $i2) / 255.0f;
        float $f5 = Math.max($f4, Math.max($f2, $f0));
        float $f6 = Math.min($f4, Math.min($f2, $f0));
        float $f1 = $f5 - $f6;
        float $f3 = ($f5 + $f6) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
        if ($f5 == $f6) {
            $f1 = 0.0f;
            $f4 = 0.0f;
        } else {
            if ($f5 == $f4) {
                $f4 = (($f2 - $f0) / $f1) % 6.0f;
            } else if ($f5 == $f2) {
                $f4 = (($f0 - $f4) / $f1) + LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            } else {
                $f4 = (($f4 - $f2) / $f1) + 4.0f;
            }
            $f1 /= 1.0f - Math.abs((LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * $f3) - 1.0f);
        }
        $f4 = (60.0f * $f4) % 360.0f;
        if ($f4 < 0.0f) {
            $f4 += 360.0f;
        }
        $r0[0] = constrain($f4, 0.0f, 360.0f);
        $r0[1] = constrain($f1, 0.0f, 1.0f);
        $r0[2] = constrain($f3, 0.0f, 1.0f);
    }

    public static void colorToHSL(@ColorInt int $i0, @NonNull float[] $r0) throws  {
        RGBToHSL(Color.red($i0), Color.green($i0), Color.blue($i0), $r0);
    }

    @ColorInt
    public static int HSLToColor(@NonNull float[] $r0) throws  {
        float $f1 = $r0[0];
        float $f0 = $r0[1];
        float $f2 = $r0[2];
        $f0 = (1.0f - Math.abs((LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * $f2) - 1.0f)) * $f0;
        $f2 -= CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR * $f0;
        float $f3 = $f0 * (1.0f - Math.abs((($f1 / 60.0f) % LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) - 1.0f));
        int $i1 = 0;
        int $i2 = 0;
        int $i3 = 0;
        switch (((int) $f1) / 60) {
            case 0:
                $i1 = Math.round(255.0f * ($f0 + $f2));
                $i2 = Math.round(255.0f * ($f3 + $f2));
                $i3 = Math.round(255.0f * $f2);
                break;
            case 1:
                $i1 = Math.round(255.0f * ($f3 + $f2));
                $i2 = Math.round(255.0f * ($f0 + $f2));
                $i3 = Math.round(255.0f * $f2);
                break;
            case 2:
                $i1 = Math.round(255.0f * $f2);
                $i2 = Math.round(255.0f * ($f0 + $f2));
                $i3 = Math.round(255.0f * ($f3 + $f2));
                break;
            case 3:
                $i1 = Math.round(255.0f * $f2);
                $i2 = Math.round(255.0f * ($f3 + $f2));
                $i3 = Math.round(255.0f * ($f0 + $f2));
                break;
            case 4:
                $i1 = Math.round(255.0f * ($f3 + $f2));
                $i2 = Math.round(255.0f * $f2);
                $i3 = Math.round(255.0f * ($f0 + $f2));
                break;
            case 5:
            case 6:
                $i1 = Math.round(255.0f * ($f0 + $f2));
                $i2 = Math.round(255.0f * $f2);
                $i3 = Math.round(255.0f * ($f3 + $f2));
                break;
            default:
                break;
        }
        return Color.rgb(constrain($i1, 0, 255), constrain($i2, 0, 255), constrain($i3, 0, 255));
    }

    @ColorInt
    public static int setAlphaComponent(@ColorInt int $i0, @IntRange(from = 0, to = 255) int $i1) throws  {
        if ($i1 >= 0 && $i1 <= 255) {
            return (ViewCompat.MEASURED_SIZE_MASK & $i0) | ($i1 << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }

    public static void colorToLAB(@ColorInt int $i0, @NonNull double[] $r0) throws  {
        RGBToLAB(Color.red($i0), Color.green($i0), Color.blue($i0), $r0);
    }

    public static void RGBToLAB(@IntRange(from = 0, to = 255) int $i0, @IntRange(from = 0, to = 255) int $i1, @IntRange(from = 0, to = 255) int $i2, @NonNull double[] $r0) throws  {
        RGBToXYZ($i0, $i1, $i2, $r0);
        XYZToLAB($r0[0], $r0[1], $r0[2], $r0);
    }

    public static void colorToXYZ(@ColorInt int $i0, @NonNull double[] $r0) throws  {
        RGBToXYZ(Color.red($i0), Color.green($i0), Color.blue($i0), $r0);
    }

    public static void RGBToXYZ(@IntRange(from = 0, to = 255) int $i0, @IntRange(from = 0, to = 255) int $i1, @IntRange(from = 0, to = 255) int $i2, @NonNull double[] $r0) throws  {
        if ($r0.length != 3) {
            throw new IllegalArgumentException("outXyz must have a length of 3.");
        }
        double $d0 = ((double) $i0) / 255.0d;
        if ($d0 < 0.04045d) {
            $d0 /= 12.92d;
        } else {
            $d0 = Math.pow((0.055d + $d0) / 1.055d, 2.4d);
        }
        double $d1 = ((double) $i1) / 255.0d;
        if ($d1 < 0.04045d) {
            $d1 /= 12.92d;
        } else {
            $d1 = Math.pow((0.055d + $d1) / 1.055d, 2.4d);
        }
        double $d2 = ((double) $i2) / 255.0d;
        if ($d2 < 0.04045d) {
            $d2 /= 12.92d;
        } else {
            $d2 = Math.pow((0.055d + $d2) / 1.055d, 2.4d);
        }
        $r0[0] = XYZ_WHITE_REFERENCE_Y * (((0.4124d * $d0) + (0.3576d * $d1)) + (0.1805d * $d2));
        $r0[1] = XYZ_WHITE_REFERENCE_Y * (((0.2126d * $d0) + (0.7152d * $d1)) + (0.0722d * $d2));
        $r0[2] = XYZ_WHITE_REFERENCE_Y * (((0.0193d * $d0) + (0.1192d * $d1)) + (0.9505d * $d2));
    }

    public static void XYZToLAB(@FloatRange(from = 0.0d, to = 95.047d) double $d0, @FloatRange(from = 0.0d, to = 100.0d) double $d1, @FloatRange(from = 0.0d, to = 108.883d) double $d2, @NonNull double[] $r0) throws  {
        if ($r0.length != 3) {
            throw new IllegalArgumentException("outLab must have a length of 3.");
        }
        $d0 = pivotXyzComponent($d0 / XYZ_WHITE_REFERENCE_X);
        $d1 = pivotXyzComponent($d1 / XYZ_WHITE_REFERENCE_Y);
        $d2 = pivotXyzComponent($d2 / XYZ_WHITE_REFERENCE_Z);
        $r0[0] = Math.max(0.0d, (116.0d * $d1) - 16.0d);
        $r0[1] = 500.0d * ($d0 - $d1);
        $r0[2] = 200.0d * ($d1 - $d2);
    }

    public static void LABToXYZ(@FloatRange(from = 0.0d, to = 100.0d) double $d0, @FloatRange(from = -128.0d, to = 127.0d) double $d1, @FloatRange(from = -128.0d, to = 127.0d) double $d2, @NonNull double[] $r0) throws  {
        double $d4 = (16.0d + $d0) / 116.0d;
        double $d3 = ($d1 / 500.0d) + $d4;
        $d1 = $d4 - ($d2 / 200.0d);
        $d2 = Math.pow($d3, 3.0d);
        if ($d2 <= XYZ_EPSILON) {
            $d2 = ((116.0d * $d3) - 16.0d) / XYZ_KAPPA;
        }
        if ($d0 > 7.9996247999999985d) {
            $d0 = Math.pow($d4, 3.0d);
        } else {
            $d0 /= XYZ_KAPPA;
        }
        $d4 = Math.pow($d1, 3.0d);
        if ($d4 > XYZ_EPSILON) {
            $d1 = $d4;
        } else {
            $d1 = ((116.0d * $d1) - 16.0d) / XYZ_KAPPA;
        }
        $r0[0] = XYZ_WHITE_REFERENCE_X * $d2;
        $r0[1] = XYZ_WHITE_REFERENCE_Y * $d0;
        $r0[2] = XYZ_WHITE_REFERENCE_Z * $d1;
    }

    @ColorInt
    public static int XYZToColor(@FloatRange(from = 0.0d, to = 95.047d) double $d0, @FloatRange(from = 0.0d, to = 100.0d) double $d1, @FloatRange(from = 0.0d, to = 108.883d) double $d2) throws  {
        double $d3 = (((3.2406d * $d0) + (-1.5372d * $d1)) + (-0.4986d * $d2)) / XYZ_WHITE_REFERENCE_Y;
        double $d4 = (((-0.9689d * $d0) + (1.8758d * $d1)) + (0.0415d * $d2)) / XYZ_WHITE_REFERENCE_Y;
        $d0 = (((0.0557d * $d0) + (-0.204d * $d1)) + (1.057d * $d2)) / XYZ_WHITE_REFERENCE_Y;
        if ($d3 > 0.0031308d) {
            $d1 = (1.055d * Math.pow($d3, 0.4166666666666667d)) - 0.055d;
        } else {
            $d1 = $d3 * 12.92d;
        }
        if ($d4 > 0.0031308d) {
            $d2 = (1.055d * Math.pow($d4, 0.4166666666666667d)) - 0.055d;
        } else {
            $d2 = $d4 * 12.92d;
        }
        if ($d0 > 0.0031308d) {
            $d0 = (1.055d * Math.pow($d0, 0.4166666666666667d)) - 0.055d;
        } else {
            $d0 *= 12.92d;
        }
        return Color.rgb(constrain((int) Math.round(255.0d * $d1), 0, 255), constrain((int) Math.round(255.0d * $d2), 0, 255), constrain((int) Math.round(255.0d * $d0), 0, 255));
    }

    @ColorInt
    public static int LABToColor(@FloatRange(from = 0.0d, to = 100.0d) double $d0, @FloatRange(from = -128.0d, to = 127.0d) double $d1, @FloatRange(from = -128.0d, to = 127.0d) double $d2) throws  {
        double[] $r0 = getTempDouble3Array();
        LABToXYZ($d0, $d1, $d2, $r0);
        return XYZToColor($r0[0], $r0[1], $r0[2]);
    }

    public static double distanceEuclidean(@NonNull double[] $r0, @NonNull double[] $r1) throws  {
        return Math.sqrt((Math.pow($r0[0] - $r1[0], 2.0d) + Math.pow($r0[1] - $r1[1], 2.0d)) + Math.pow($r0[2] - $r1[2], 2.0d));
    }

    private static float constrain(float $f0, float $f2, float $f1) throws  {
        if ($f0 < $f2) {
            return $f2;
        }
        return $f0 > $f1 ? $f1 : $f0;
    }

    private static double pivotXyzComponent(double $d0) throws  {
        return $d0 > XYZ_EPSILON ? Math.pow($d0, 0.3333333333333333d) : ((XYZ_KAPPA * $d0) + 16.0d) / 116.0d;
    }

    @ColorInt
    public static int blendARGB(@ColorInt int $i0, @ColorInt int $i1, @FloatRange(from = 0.0d, to = 1.0d) float $f0) throws  {
        float $f3 = 1.0f - $f0;
        return Color.argb((int) ((((float) Color.alpha($i0)) * $f3) + (((float) Color.alpha($i1)) * $f0)), (int) ((((float) Color.red($i0)) * $f3) + (((float) Color.red($i1)) * $f0)), (int) ((((float) Color.green($i0)) * $f3) + (((float) Color.green($i1)) * $f0)), (int) ((((float) Color.blue($i0)) * $f3) + (((float) Color.blue($i1)) * $f0)));
    }

    public static void blendHSL(@NonNull float[] $r0, @NonNull float[] $r1, @FloatRange(from = 0.0d, to = 1.0d) float $f0, @NonNull float[] $r2) throws  {
        if ($r2.length != 3) {
            throw new IllegalArgumentException("result must have a length of 3.");
        }
        float $f1 = 1.0f - $f0;
        $r2[0] = circularInterpolate($r0[0], $r1[0], $f0);
        $r2[1] = ($r0[1] * $f1) + ($r1[1] * $f0);
        $r2[2] = ($r0[2] * $f1) + ($r1[2] * $f0);
    }

    public static void blendLAB(@NonNull double[] $r0, @NonNull double[] $r1, @FloatRange(from = 0.0d, to = 1.0d) double $d0, @NonNull double[] $r2) throws  {
        if ($r2.length != 3) {
            throw new IllegalArgumentException("outResult must have a length of 3.");
        }
        double $d1 = FriendsBarFragment.END_LOCATION_POSITION - $d0;
        $r2[0] = ($r0[0] * $d1) + ($r1[0] * $d0);
        $r2[1] = ($r0[1] * $d1) + ($r1[1] * $d0);
        $r2[2] = ($r0[2] * $d1) + ($r1[2] * $d0);
    }

    @VisibleForTesting
    static float circularInterpolate(float $f2, float $f3, float $f0) throws  {
        if (Math.abs($f3 - $f2) > 180.0f) {
            if ($f3 > $f2) {
                $f2 += 360.0f;
            } else {
                $f3 += 360.0f;
            }
        }
        return ((($f3 - $f2) * $f0) + $f2) % 360.0f;
    }

    private static double[] getTempDouble3Array() throws  {
        double[] $r2 = (double[]) TEMP_ARRAY.get();
        if ($r2 != null) {
            return $r2;
        }
        $r2 = new double[3];
        TEMP_ARRAY.set($r2);
        return $r2;
    }
}
