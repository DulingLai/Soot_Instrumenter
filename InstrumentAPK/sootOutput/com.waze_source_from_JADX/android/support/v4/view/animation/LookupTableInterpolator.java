package android.support.v4.view.animation;

import android.view.animation.Interpolator;

abstract class LookupTableInterpolator implements Interpolator {
    private final float mStepSize = (1.0f / ((float) (this.mValues.length - 1)));
    private final float[] mValues;

    public LookupTableInterpolator(float[] $r1) throws  {
        this.mValues = $r1;
    }

    public float getInterpolation(float $f0) throws  {
        if ($f0 >= 1.0f) {
            return 1.0f;
        }
        if ($f0 <= 0.0f) {
            return 0.0f;
        }
        int $i0 = Math.min((int) (((float) (this.mValues.length - 1)) * $f0), this.mValues.length - 2);
        return this.mValues[$i0] + ((this.mValues[$i0 + 1] - this.mValues[$i0]) * (($f0 - (((float) $i0) * this.mStepSize)) / this.mStepSize));
    }
}
