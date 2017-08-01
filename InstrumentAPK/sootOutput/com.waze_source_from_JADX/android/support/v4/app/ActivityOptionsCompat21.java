package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

class ActivityOptionsCompat21 {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity $r0, View $r1, String $r2) throws  {
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation($r0, $r1, $r2));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity $r0, View[] $r1, String[] $r2) throws  {
        Pair[] $r3 = null;
        if ($r1 != null) {
            $r3 = new Pair[$r1.length];
            for (int $i0 = 0; $i0 < $r3.length; $i0++) {
                $r3[$i0] = Pair.create($r1[$i0], $r2[$i0]);
            }
        }
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation($r0, $r3));
    }

    private ActivityOptionsCompat21(ActivityOptions $r1) throws  {
        this.mActivityOptions = $r1;
    }

    public Bundle toBundle() throws  {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat21 $r1) throws  {
        this.mActivityOptions.update($r1.mActivityOptions);
    }
}
