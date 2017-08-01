package android.support.v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources.Theme;
import android.support.annotation.StyleRes;
import android.support.v7.appcompat.C0192R;
import android.view.LayoutInflater;

public class ContextThemeWrapper extends ContextWrapper {
    private LayoutInflater mInflater;
    private Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper(Context $r1, @StyleRes int $i0) throws  {
        super($r1);
        this.mThemeResource = $i0;
    }

    public ContextThemeWrapper(Context $r1, Theme $r2) throws  {
        super($r1);
        this.mTheme = $r2;
    }

    public void setTheme(int $i0) throws  {
        if (this.mThemeResource != $i0) {
            this.mThemeResource = $i0;
            initializeTheme();
        }
    }

    public int getThemeResId() throws  {
        return this.mThemeResource;
    }

    public Theme getTheme() throws  {
        if (this.mTheme != null) {
            return this.mTheme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = C0192R.style.Theme_AppCompat_Light;
        }
        initializeTheme();
        return this.mTheme;
    }

    public Object getSystemService(String $r1) throws  {
        if (!"layout_inflater".equals($r1)) {
            return getBaseContext().getSystemService($r1);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    protected void onApplyThemeResource(Theme $r1, int $i0, boolean first) throws  {
        $r1.applyStyle($i0, true);
    }

    private void initializeTheme() throws  {
        boolean $z0 = this.mTheme == null;
        if ($z0) {
            this.mTheme = getResources().newTheme();
            Theme $r1 = getBaseContext().getTheme();
            if ($r1 != null) {
                this.mTheme.setTo($r1);
            }
        }
        onApplyThemeResource(this.mTheme, this.mThemeResource, $z0);
    }
}
