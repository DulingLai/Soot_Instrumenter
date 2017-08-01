package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;

class TintResources extends ResourcesWrapper {
    private final WeakReference<Context> mContextRef;

    public TintResources(@NonNull Context $r1, @NonNull Resources $r2) throws  {
        super($r2);
        this.mContextRef = new WeakReference($r1);
    }

    public Drawable getDrawable(int $i0) throws NotFoundException {
        Drawable $r2 = super.getDrawable($i0);
        Context $r4 = (Context) this.mContextRef.get();
        if ($r2 == null || $r4 == null) {
            return $r2;
        }
        AppCompatDrawableManager.get();
        AppCompatDrawableManager.tintDrawableUsingColorFilter($r4, $i0, $r2);
        return $r2;
    }
}
