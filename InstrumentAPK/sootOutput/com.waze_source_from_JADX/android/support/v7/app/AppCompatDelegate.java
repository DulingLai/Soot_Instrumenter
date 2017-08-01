package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class AppCompatDelegate {
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
    public static final int FEATURE_SUPPORT_ACTION_BAR = 108;
    public static final int FEATURE_SUPPORT_ACTION_BAR_OVERLAY = 109;
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_FOLLOW_SYSTEM = -1;
    public static final int MODE_NIGHT_NO = 1;
    static final int MODE_NIGHT_UNSPECIFIED = -100;
    public static final int MODE_NIGHT_YES = 2;
    static final String TAG = "AppCompatDelegate";
    private static boolean sCompatVectorFromResourcesEnabled = false;
    private static int sDefaultNightMode = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NightMode {
    }

    public abstract void addContentView(View view, LayoutParams layoutParams) throws ;

    public abstract boolean applyDayNight() throws ;

    public abstract View createView(@Nullable View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) throws ;

    @Nullable
    public abstract View findViewById(@IdRes int i) throws ;

    @Nullable
    public abstract Delegate getDrawerToggleDelegate() throws ;

    public abstract MenuInflater getMenuInflater() throws ;

    @Nullable
    public abstract ActionBar getSupportActionBar() throws ;

    public abstract boolean hasWindowFeature(int i) throws ;

    public abstract void installViewFactory() throws ;

    public abstract void invalidateOptionsMenu() throws ;

    public abstract boolean isHandleNativeActionModesEnabled() throws ;

    public abstract void onConfigurationChanged(Configuration configuration) throws ;

    public abstract void onCreate(Bundle bundle) throws ;

    public abstract void onDestroy() throws ;

    public abstract void onPostCreate(Bundle bundle) throws ;

    public abstract void onPostResume() throws ;

    public abstract void onSaveInstanceState(Bundle bundle) throws ;

    public abstract void onStop() throws ;

    public abstract boolean requestWindowFeature(int i) throws ;

    public abstract void setContentView(@LayoutRes int i) throws ;

    public abstract void setContentView(View view) throws ;

    public abstract void setContentView(View view, LayoutParams layoutParams) throws ;

    public abstract void setHandleNativeActionModesEnabled(boolean z) throws ;

    public abstract void setLocalNightMode(int i) throws ;

    public abstract void setSupportActionBar(@Nullable Toolbar toolbar) throws ;

    public abstract void setTitle(@Nullable CharSequence charSequence) throws ;

    @Nullable
    public abstract ActionMode startSupportActionMode(@NonNull Callback callback) throws ;

    public static AppCompatDelegate create(Activity $r0, AppCompatCallback $r1) throws  {
        return create($r0, $r0.getWindow(), $r1);
    }

    public static AppCompatDelegate create(Dialog $r0, AppCompatCallback $r1) throws  {
        return create($r0.getContext(), $r0.getWindow(), $r1);
    }

    private static AppCompatDelegate create(Context $r0, Window $r1, AppCompatCallback $r2) throws  {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 23) {
            return new AppCompatDelegateImplV23($r0, $r1, $r2);
        }
        if ($i0 >= 14) {
            return new AppCompatDelegateImplV14($r0, $r1, $r2);
        }
        if ($i0 >= 11) {
            return new AppCompatDelegateImplV11($r0, $r1, $r2);
        }
        return new AppCompatDelegateImplV7($r0, $r1, $r2);
    }

    AppCompatDelegate() throws  {
    }

    public static void setDefaultNightMode(int $i0) throws  {
        switch ($i0) {
            case -1:
            case 0:
            case 1:
            case 2:
                sDefaultNightMode = $i0;
                return;
            default:
                Log.d(TAG, "setDefaultNightMode() called with an unknown mode");
                return;
        }
    }

    public static int getDefaultNightMode() throws  {
        return sDefaultNightMode;
    }

    public static void setCompatVectorFromResourcesEnabled(boolean $z0) throws  {
        sCompatVectorFromResourcesEnabled = $z0;
    }

    public static boolean isCompatVectorFromResourcesEnabled() throws  {
        return sCompatVectorFromResourcesEnabled;
    }
}
