package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import java.lang.ref.WeakReference;

public class VectorEnabledTintResources extends Resources {
    public static final int MAX_SDK_WHERE_REQUIRED = 20;
    private final WeakReference<Context> mContextRef;

    public static boolean shouldBeUsed() throws  {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled() && VERSION.SDK_INT <= 20;
    }

    public VectorEnabledTintResources(@NonNull Context $r1, @NonNull Resources $r2) throws  {
        super($r2.getAssets(), $r2.getDisplayMetrics(), $r2.getConfiguration());
        this.mContextRef = new WeakReference($r1);
    }

    public Drawable getDrawable(int $i0) throws NotFoundException {
        Context $r3 = (Context) this.mContextRef.get();
        if ($r3 != null) {
            return AppCompatDrawableManager.get().onDrawableLoadedFromResources($r3, this, $i0);
        }
        return super.getDrawable($i0);
    }

    final Drawable superGetDrawable(int $i0) throws  {
        return super.getDrawable($i0);
    }
}
