package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper extends ContextWrapper {
    private static final ArrayList<WeakReference<TintContextWrapper>> sCache = new ArrayList();
    private Resources mResources;
    private final Theme mTheme;

    public static Context wrap(@NonNull Context $r0) throws  {
        if (!shouldWrap($r0)) {
            return $r0;
        }
        TintContextWrapper $r4;
        int $i1 = sCache.size();
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            WeakReference $r1 = (WeakReference) sCache.get($i0);
            $r4 = $r1 != null ? (TintContextWrapper) $r1.get() : null;
            if ($r4 != null && $r4.getBaseContext() == $r0) {
                return $r4;
            }
        }
        $r4 = new TintContextWrapper($r0);
        sCache.add(new WeakReference($r4));
        return $r4;
    }

    private static boolean shouldWrap(@NonNull Context $r0) throws  {
        if ($r0 instanceof TintContextWrapper) {
            return false;
        }
        if ($r0.getResources() instanceof TintResources) {
            return false;
        }
        if ($r0.getResources() instanceof VectorEnabledTintResources) {
            return false;
        }
        return !AppCompatDelegate.isCompatVectorFromResourcesEnabled() || VERSION.SDK_INT <= 20;
    }

    private TintContextWrapper(@NonNull Context $r1) throws  {
        super($r1);
        if (VectorEnabledTintResources.shouldBeUsed()) {
            this.mTheme = getResources().newTheme();
            this.mTheme.setTo($r1.getTheme());
            return;
        }
        this.mTheme = null;
    }

    public Theme getTheme() throws  {
        return this.mTheme == null ? super.getTheme() : this.mTheme;
    }

    public void setTheme(int $i0) throws  {
        if (this.mTheme == null) {
            super.setTheme($i0);
        } else {
            this.mTheme.applyStyle($i0, true);
        }
    }

    public Resources getResources() throws  {
        if (this.mResources == null) {
            Resources $r1;
            if (this.mTheme == null) {
                $r1 = r4;
                Resources r4 = new TintResources(this, super.getResources());
            } else {
                $r1 = r5;
                Resources r5 = new VectorEnabledTintResources(this, super.getResources());
            }
            this.mResources = $r1;
        }
        return this.mResources;
    }
}
