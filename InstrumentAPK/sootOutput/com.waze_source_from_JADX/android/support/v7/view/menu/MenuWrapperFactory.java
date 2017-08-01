package android.support.v7.view.menu;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public final class MenuWrapperFactory {
    private MenuWrapperFactory() throws  {
    }

    public static Menu wrapSupportMenu(Context $r0, SupportMenu $r1) throws  {
        if (VERSION.SDK_INT >= 14) {
            return new MenuWrapperICS($r0, $r1);
        }
        throw new UnsupportedOperationException();
    }

    public static MenuItem wrapSupportMenuItem(Context $r0, SupportMenuItem $r1) throws  {
        if (VERSION.SDK_INT >= 16) {
            return new MenuItemWrapperJB($r0, $r1);
        }
        if (VERSION.SDK_INT >= 14) {
            return new MenuItemWrapperICS($r0, $r1);
        }
        throw new UnsupportedOperationException();
    }

    public static SubMenu wrapSupportSubMenu(Context $r0, SupportSubMenu $r1) throws  {
        if (VERSION.SDK_INT >= 14) {
            return new SubMenuWrapperICS($r0, $r1);
        }
        throw new UnsupportedOperationException();
    }
}
