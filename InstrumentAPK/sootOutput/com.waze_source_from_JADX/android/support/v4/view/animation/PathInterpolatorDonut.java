package android.support.v4.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorDonut implements Interpolator {
    private static final float PRECISION = 0.002f;
    private final float[] mX;
    private final float[] mY;

    public PathInterpolatorDonut(Path $r1) throws  {
        PathMeasure $r2 = new PathMeasure($r1, false);
        float $f1 = $r2.getLength();
        int $i0 = ((int) ($f1 / PRECISION)) + 1;
        this.mX = new float[$i0];
        this.mY = new float[$i0];
        float[] $r3 = new float[2];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2.getPosTan((((float) $i1) * $f1) / ((float) ($i0 - 1)), $r3, null);
            this.mX[$i1] = $r3[0];
            this.mY[$i1] = $r3[1];
        }
    }

    public PathInterpolatorDonut(float $f0, float $f1) throws  {
        this(createQuad($f0, $f1));
    }

    public PathInterpolatorDonut(float $f0, float $f1, float $f2, float $f3) throws  {
        this(createCubic($f0, $f1, $f2, $f3));
    }

    public float getInterpolation(float $f0) throws  {
        if ($f0 <= 0.0f) {
            return 0.0f;
        }
        if ($f0 >= 1.0f) {
            return 1.0f;
        }
        int $i2 = 0;
        int $i3 = this.mX.length - 1;
        while ($i3 - $i2 > 1) {
            int $i0 = ($i2 + $i3) / 2;
            if ($f0 < this.mX[$i0]) {
                $i3 = $i0;
            } else {
                $i2 = $i0;
            }
        }
        float $f2 = this.mX[$i3] - this.mX[$i2];
        if ($f2 == 0.0f) {
            return this.mY[$i2];
        }
        $f2 = ($f0 - this.mX[$i2]) / $f2;
        $f0 = this.mY[$i2];
        return ((this.mY[$i3] - $f0) * $f2) + $f0;
    }

    private static Path createQuad(float $f0, float $f1) throws  {
        Path $r0 = new Path();
        $r0.moveTo(0.0f, 0.0f);
        $r0.quadTo($f0, $f1, 1.0f, 1.0f);
        return $r0;
    }

    private static Path createCubic(float $f0, float $f1, float $f2, float $f3) throws  {
        Path $r0 = new Path();
        $r0.moveTo(0.0f, 0.0f);
        $r0.cubicTo($f0, $f1, $f2, $f3, 1.0f, 1.0f);
        return $r0;
    }
}
