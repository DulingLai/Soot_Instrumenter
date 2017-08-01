package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;
import dalvik.annotation.Signature;

public class ActivityOptionsCompat {

    private static class ActivityOptionsImpl21 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat21 mImpl;

        ActivityOptionsImpl21(ActivityOptionsCompat21 $r1) throws  {
            this.mImpl = $r1;
        }

        public Bundle toBundle() throws  {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat $r1) throws  {
            if ($r1 instanceof ActivityOptionsImpl21) {
                this.mImpl.update(((ActivityOptionsImpl21) $r1).mImpl);
            }
        }
    }

    private static class ActivityOptionsImplJB extends ActivityOptionsCompat {
        private final ActivityOptionsCompatJB mImpl;

        ActivityOptionsImplJB(ActivityOptionsCompatJB $r1) throws  {
            this.mImpl = $r1;
        }

        public Bundle toBundle() throws  {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat $r1) throws  {
            if ($r1 instanceof ActivityOptionsImplJB) {
                this.mImpl.update(((ActivityOptionsImplJB) $r1).mImpl);
            }
        }
    }

    public Bundle toBundle() throws  {
        return null;
    }

    public static ActivityOptionsCompat makeCustomAnimation(Context $r0, int $i0, int $i1) throws  {
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeCustomAnimation($r0, $i0, $i1));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeScaleUpAnimation(View $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeScaleUpAnimation($r0, $i0, $i1, $i2, $i3));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View $r0, Bitmap $r1, int $i0, int $i1) throws  {
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeThumbnailScaleUpAnimation($r0, $r1, $i0, $i1));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity $r0, View $r1, String $r2) throws  {
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeSceneTransitionAnimation($r0, $r1, $r2));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(@Signature({"(", "Landroid/app/Activity;", "[", "Landroid/support/v4/util/Pair", "<", "Landroid/view/View;", "Ljava/lang/String;", ">;)", "Landroid/support/v4/app/ActivityOptionsCompat;"}) Activity $r0, @Signature({"(", "Landroid/app/Activity;", "[", "Landroid/support/v4/util/Pair", "<", "Landroid/view/View;", "Ljava/lang/String;", ">;)", "Landroid/support/v4/app/ActivityOptionsCompat;"}) Pair<View, String>... $r1) throws  {
        if (VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        View[] $r3 = null;
        String[] $r4 = null;
        if ($r1 != null) {
            $r3 = new View[$r1.length];
            $r4 = new String[$r1.length];
            for (int $i0 = 0; $i0 < $r1.length; $i0++) {
                $r3[$i0] = (View) $r1[$i0].first;
                $r4[$i0] = (String) $r1[$i0].second;
            }
        }
        return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeSceneTransitionAnimation($r0, $r3, $r4));
    }

    protected ActivityOptionsCompat() throws  {
    }

    public void update(ActivityOptionsCompat otherOptions) throws  {
    }
}
