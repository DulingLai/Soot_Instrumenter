package android.support.v7.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.appcompat.C0192R;
import android.view.ViewConfiguration;

public class ActionBarPolicy {
    private Context mContext;

    public static ActionBarPolicy get(Context $r0) throws  {
        return new ActionBarPolicy($r0);
    }

    private ActionBarPolicy(Context $r1) throws  {
        this.mContext = $r1;
    }

    public int getMaxActionButtons() throws  {
        return this.mContext.getResources().getInteger(C0192R.integer.abc_max_action_buttons);
    }

    public boolean showsOverflowMenuButton() throws  {
        if (VERSION.SDK_INT >= 19) {
            return true;
        }
        return !ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext));
    }

    public int getEmbeddedMenuWidthLimit() throws  {
        return this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public boolean hasEmbeddedTabs() throws  {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 16) {
            return this.mContext.getResources().getBoolean(C0192R.bool.abc_action_bar_embed_tabs);
        }
        return this.mContext.getResources().getBoolean(C0192R.bool.abc_action_bar_embed_tabs_pre_jb);
    }

    public int getTabContainerHeight() throws  {
        TypedArray $r3 = this.mContext.obtainStyledAttributes(null, C0192R.styleable.ActionBar, C0192R.attr.actionBarStyle, 0);
        int $i0 = $r3.getLayoutDimension(C0192R.styleable.ActionBar_height, 0);
        int $i1 = $i0;
        Resources $r4 = this.mContext.getResources();
        if (!hasEmbeddedTabs()) {
            $i1 = Math.min($i0, $r4.getDimensionPixelSize(C0192R.dimen.abc_action_bar_stacked_max_height));
        }
        $r3.recycle();
        return $i1;
    }

    public boolean enableHomeButtonByDefault() throws  {
        return this.mContext.getApplicationInfo().targetSdkVersion < 14;
    }

    public int getStackedTabMaxWidth() throws  {
        return this.mContext.getResources().getDimensionPixelSize(C0192R.dimen.abc_action_bar_stacked_tab_max_width);
    }
}
