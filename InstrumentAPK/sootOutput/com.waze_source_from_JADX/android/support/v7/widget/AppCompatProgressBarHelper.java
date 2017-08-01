package android.support.v7.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.widget.ProgressBar;

class AppCompatProgressBarHelper {
    private static final int[] TINT_ATTRS = new int[]{16843067, 16843068};
    final AppCompatDrawableManager mDrawableManager;
    private Bitmap mSampleTile;
    private final ProgressBar mView;

    AppCompatProgressBarHelper(ProgressBar $r1, AppCompatDrawableManager $r2) throws  {
        this.mView = $r1;
        this.mDrawableManager = $r2;
    }

    void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        TintTypedArray $r5 = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), $r1, TINT_ATTRS, $i0, 0);
        Drawable $r6 = $r5.getDrawableIfKnown(0);
        if ($r6 != null) {
            this.mView.setIndeterminateDrawable(tileifyIndeterminate($r6));
        }
        $r6 = $r5.getDrawableIfKnown(1);
        if ($r6 != null) {
            this.mView.setProgressDrawable(tileify($r6, false));
        }
        $r5.recycle();
    }

    private Drawable tileify(Drawable $r1, boolean $z0) throws  {
        if ($r1 instanceof DrawableWrapper) {
            Drawable $r5 = ((DrawableWrapper) $r1).getWrappedDrawable();
            if ($r5 != null) {
                ((DrawableWrapper) $r1).setWrappedDrawable(tileify($r5, $z0));
            }
        } else if ($r1 instanceof LayerDrawable) {
            int $i1;
            LayerDrawable $r7 = (LayerDrawable) $r1;
            int $i0 = $r7.getNumberOfLayers();
            Drawable[] $r3 = new Drawable[$i0];
            for ($i1 = 0; $i1 < $i0; $i1++) {
                int $i2 = $r7.getId($i1);
                $r1 = $r7.getDrawable($i1);
                $z0 = $i2 == 16908301 || $i2 == 16908303;
                $r3[$i1] = tileify($r1, $z0);
            }
            LayerDrawable $r6 = r0;
            LayerDrawable layerDrawable = new LayerDrawable($r3);
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r6.setId($i1, $r7.getId($i1));
            }
            return $r6;
        } else if ($r1 instanceof BitmapDrawable) {
            BitmapDrawable $r8 = (BitmapDrawable) $r1;
            Bitmap $r9 = $r8.getBitmap();
            if (this.mSampleTile == null) {
                this.mSampleTile = $r9;
            }
            $r1 = r0;
            Drawable shapeDrawable = new ShapeDrawable(getDrawableShape());
            ((ShapeDrawable) $r1).getPaint().setShader(new BitmapShader($r9, TileMode.REPEAT, TileMode.CLAMP));
            ((ShapeDrawable) $r1).getPaint().setColorFilter($r8.getPaint().getColorFilter());
            if ($z0) {
                $r1 = new ClipDrawable($r1, 3, 1);
            }
            return $r1;
        }
        return $r1;
    }

    private Drawable tileifyIndeterminate(Drawable $r2) throws  {
        if (!($r2 instanceof AnimationDrawable)) {
            return $r2;
        }
        AnimationDrawable $r3 = (AnimationDrawable) $r2;
        int $i0 = $r3.getNumberOfFrames();
        AnimationDrawable $r1 = new AnimationDrawable();
        $r1.setOneShot($r3.isOneShot());
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2 = tileify($r3.getFrame($i1), true);
            $r2.setLevel(10000);
            $r1.addFrame($r2, $r3.getDuration($i1));
        }
        $r1.setLevel(10000);
        return $r1;
    }

    private Shape getDrawableShape() throws  {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    Bitmap getSampleTime() throws  {
        return this.mSampleTile;
    }
}
