package android.support.v4.app;

import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

class ActivityOptionsCompatJB {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompatJB makeCustomAnimation(Context $r0, int $i0, int $i1) throws  {
        return new ActivityOptionsCompatJB(ActivityOptions.makeCustomAnimation($r0, $i0, $i1));
    }

    public static ActivityOptionsCompatJB makeScaleUpAnimation(View $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        return new ActivityOptionsCompatJB(ActivityOptions.makeScaleUpAnimation($r0, $i0, $i1, $i2, $i3));
    }

    public static ActivityOptionsCompatJB makeThumbnailScaleUpAnimation(View $r0, Bitmap $r1, int $i0, int $i1) throws  {
        return new ActivityOptionsCompatJB(ActivityOptions.makeThumbnailScaleUpAnimation($r0, $r1, $i0, $i1));
    }

    private ActivityOptionsCompatJB(ActivityOptions $r1) throws  {
        this.mActivityOptions = $r1;
    }

    public Bundle toBundle() throws  {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompatJB $r1) throws  {
        this.mActivityOptions.update($r1.mActivityOptions);
    }
}
