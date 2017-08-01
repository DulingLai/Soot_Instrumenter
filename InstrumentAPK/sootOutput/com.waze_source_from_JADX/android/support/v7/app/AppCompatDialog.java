package android.support.v7.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class AppCompatDialog extends Dialog implements AppCompatCallback {
    private AppCompatDelegate mDelegate;

    @Nullable
    public ActionMode onWindowStartingSupportActionMode(Callback callback) throws  {
        return null;
    }

    public AppCompatDialog(Context $r1) throws  {
        this($r1, 0);
    }

    public AppCompatDialog(Context $r1, int $i0) throws  {
        super($r1, getThemeResId($r1, $i0));
        getDelegate().onCreate(null);
        getDelegate().applyDayNight();
    }

    protected AppCompatDialog(Context $r1, boolean $z0, OnCancelListener $r2) throws  {
        super($r1, $z0, $r2);
    }

    protected void onCreate(Bundle $r1) throws  {
        getDelegate().installViewFactory();
        super.onCreate($r1);
        getDelegate().onCreate($r1);
    }

    public ActionBar getSupportActionBar() throws  {
        return getDelegate().getSupportActionBar();
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

    @Nullable
    public View findViewById(@IdRes int $i0) throws  {
        return getDelegate().findViewById($i0);
    }

    public void setTitle(CharSequence $r1) throws  {
        super.setTitle($r1);
        getDelegate().setTitle($r1);
    }

    public void setTitle(int $i0) throws  {
        super.setTitle($i0);
        getDelegate().setTitle(getContext().getString($i0));
    }

    public void addContentView(View $r1, LayoutParams $r2) throws  {
        getDelegate().addContentView($r1, $r2);
    }

    protected void onStop() throws  {
        super.onStop();
        getDelegate().onStop();
    }

    public boolean supportRequestWindowFeature(int $i0) throws  {
        return getDelegate().requestWindowFeature($i0);
    }

    public void invalidateOptionsMenu() throws  {
        getDelegate().invalidateOptionsMenu();
    }

    public AppCompatDelegate getDelegate() throws  {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create((Dialog) this, (AppCompatCallback) this);
        }
        return this.mDelegate;
    }

    private static int getThemeResId(Context $r0, int $i0) throws  {
        if ($i0 != 0) {
            return $i0;
        }
        TypedValue $r1 = new TypedValue();
        $r0.getTheme().resolveAttribute(C0192R.attr.dialogTheme, $r1, true);
        return $r1.resourceId;
    }

    public void onSupportActionModeStarted(ActionMode mode) throws  {
    }

    public void onSupportActionModeFinished(ActionMode mode) throws  {
    }
}
