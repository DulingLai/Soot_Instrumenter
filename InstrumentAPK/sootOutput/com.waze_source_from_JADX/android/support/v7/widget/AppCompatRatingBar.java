package android.support.v7.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.widget.RatingBar;

public class AppCompatRatingBar extends RatingBar {
    private AppCompatProgressBarHelper mAppCompatProgressBarHelper;
    private AppCompatDrawableManager mDrawableManager;

    public AppCompatRatingBar(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatRatingBar(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.ratingBarStyle);
    }

    public AppCompatRatingBar(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mDrawableManager = AppCompatDrawableManager.get();
        this.mAppCompatProgressBarHelper = new AppCompatProgressBarHelper(this, this.mDrawableManager);
        this.mAppCompatProgressBarHelper.loadFromAttributes($r2, $i0);
    }

    protected synchronized void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        Bitmap $r2 = this.mAppCompatProgressBarHelper.getSampleTime();
        if ($r2 != null) {
            setMeasuredDimension(ViewCompat.resolveSizeAndState($r2.getWidth() * getNumStars(), $i0, 0), getMeasuredHeight());
        }
    }
}
