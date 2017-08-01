package android.support.v7.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.TaskStackBuilder.SupportParentable;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;
import android.support.v7.app.ActionBarDrawerToggle.DelegateProvider;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class AppCompatActivity extends FragmentActivity implements SupportParentable, DelegateProvider, AppCompatCallback {
    private AppCompatDelegate mDelegate;
    private boolean mEatKeyUpEvent;
    private Resources mResources;
    private int mThemeId = 0;

    @Nullable
    public ActionMode onWindowStartingSupportActionMode(@NonNull Callback callback) throws  {
        return null;
    }

    protected void onCreate(@Nullable Bundle $r1) throws  {
        AppCompatDelegate $r2 = getDelegate();
        $r2.installViewFactory();
        $r2.onCreate($r1);
        if ($r2.applyDayNight() && this.mThemeId != 0) {
            if (VERSION.SDK_INT >= 23) {
                onApplyThemeResource(getTheme(), this.mThemeId, false);
            } else {
                setTheme(this.mThemeId);
            }
        }
        super.onCreate($r1);
    }

    public void setTheme(@StyleRes int $i0) throws  {
        super.setTheme($i0);
        this.mThemeId = $i0;
    }

    protected void onPostCreate(@Nullable Bundle $r1) throws  {
        super.onPostCreate($r1);
        getDelegate().onPostCreate($r1);
    }

    @Nullable
    public ActionBar getSupportActionBar() throws  {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar $r1) throws  {
        getDelegate().setSupportActionBar($r1);
    }

    public MenuInflater getMenuInflater() throws  {
        return getDelegate().getMenuInflater();
    }

    public void setContentView(@LayoutRes int $i0) throws  {
        getDelegate().setContentView($i0);
    }

    public void setContentView(View $r1) throws  {
        getDelegate().setContentView($r1);
    }

    public void setContentView(View $r1, LayoutParams $r2) throws  {
        getDelegate().setContentView($r1, $r2);
    }

    public void addContentView(View $r1, LayoutParams $r2) throws  {
        getDelegate().addContentView($r1, $r2);
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        getDelegate().onConfigurationChanged($r1);
        if (this.mResources != null) {
            this.mResources.updateConfiguration($r1, super.getResources().getDisplayMetrics());
        }
    }

    protected void onStop() throws  {
        super.onStop();
        getDelegate().onStop();
    }

    protected void onPostResume() throws  {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Nullable
    public View findViewById(@IdRes int $i0) throws  {
        return getDelegate().findViewById($i0);
    }

    public final boolean onMenuItemSelected(int $i0, MenuItem $r1) throws  {
        if (super.onMenuItemSelected($i0, $r1)) {
            return true;
        }
        ActionBar $r2 = getSupportActionBar();
        return ($r1.getItemId() != 16908332 || $r2 == null || ($r2.getDisplayOptions() & 4) == 0) ? false : onSupportNavigateUp();
    }

    protected void onDestroy() throws  {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    protected void onTitleChanged(CharSequence $r1, int $i0) throws  {
        super.onTitleChanged($r1, $i0);
        getDelegate().setTitle($r1);
    }

    public boolean supportRequestWindowFeature(int $i0) throws  {
        return getDelegate().requestWindowFeature($i0);
    }

    public void supportInvalidateOptionsMenu() throws  {
        getDelegate().invalidateOptionsMenu();
    }

    public void invalidateOptionsMenu() throws  {
        getDelegate().invalidateOptionsMenu();
    }

    @CallSuper
    public void onSupportActionModeStarted(@NonNull ActionMode mode) throws  {
    }

    @CallSuper
    public void onSupportActionModeFinished(@NonNull ActionMode mode) throws  {
    }

    @Nullable
    public ActionMode startSupportActionMode(@NonNull Callback $r1) throws  {
        return getDelegate().startSupportActionMode($r1);
    }

    @Deprecated
    public void setSupportProgressBarVisibility(@Deprecated boolean visible) throws  {
    }

    @Deprecated
    public void setSupportProgressBarIndeterminateVisibility(@Deprecated boolean visible) throws  {
    }

    @Deprecated
    public void setSupportProgressBarIndeterminate(@Deprecated boolean indeterminate) throws  {
    }

    @Deprecated
    public void setSupportProgress(@Deprecated int progress) throws  {
    }

    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder $r1) throws  {
        $r1.addParentStack((Activity) this);
    }

    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) throws  {
    }

    public boolean onSupportNavigateUp() throws  {
        Intent $r2 = getSupportParentActivityIntent();
        if ($r2 == null) {
            return false;
        }
        if (supportShouldUpRecreateTask($r2)) {
            TaskStackBuilder $r3 = TaskStackBuilder.create(this);
            onCreateSupportNavigateUpTaskStack($r3);
            onPrepareSupportNavigateUpTaskStack($r3);
            $r3.startActivities();
            try {
                ActivityCompat.finishAffinity(this);
            } catch (IllegalStateException e) {
                finish();
            }
        } else {
            supportNavigateUpTo($r2);
        }
        return true;
    }

    @Nullable
    public Intent getSupportParentActivityIntent() throws  {
        return NavUtils.getParentActivityIntent(this);
    }

    public boolean supportShouldUpRecreateTask(@NonNull Intent $r1) throws  {
        return NavUtils.shouldUpRecreateTask(this, $r1);
    }

    public void supportNavigateUpTo(@NonNull Intent $r1) throws  {
        NavUtils.navigateUpTo(this, $r1);
    }

    public void onContentChanged() throws  {
        onSupportContentChanged();
    }

    @Deprecated
    public void onSupportContentChanged() throws  {
    }

    @Nullable
    public Delegate getDrawerToggleDelegate() throws  {
        return getDelegate().getDrawerToggleDelegate();
    }

    public boolean onMenuOpened(int $i0, Menu $r1) throws  {
        return super.onMenuOpened($i0, $r1);
    }

    public void onPanelClosed(int $i0, Menu $r1) throws  {
        super.onPanelClosed($i0, $r1);
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        getDelegate().onSaveInstanceState($r1);
    }

    @NonNull
    public AppCompatDelegate getDelegate() throws  {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create((Activity) this, (AppCompatCallback) this);
        }
        return this.mDelegate;
    }

    public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        if (KeyEventCompat.hasModifiers($r1, 4096) && $r1.getUnicodeChar($r1.getMetaState() & -28673) == 60) {
            int $i0 = $r1.getAction();
            if ($i0 == 0) {
                ActionBar $r2 = getSupportActionBar();
                if ($r2 != null && $r2.isShowing() && $r2.requestFocus()) {
                    this.mEatKeyUpEvent = true;
                    return true;
                }
            } else if ($i0 == 1 && this.mEatKeyUpEvent) {
                this.mEatKeyUpEvent = false;
                return true;
            }
        }
        return super.dispatchKeyEvent($r1);
    }

    public Resources getResources() throws  {
        if (this.mResources == null && VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = new VectorEnabledTintResources(this, super.getResources());
        }
        return this.mResources == null ? super.getResources() : this.mResources;
    }
}
