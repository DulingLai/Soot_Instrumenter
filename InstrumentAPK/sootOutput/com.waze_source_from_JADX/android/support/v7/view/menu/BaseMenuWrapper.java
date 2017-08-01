package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.Map;

abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
    final Context mContext;
    private Map<SupportMenuItem, MenuItem> mMenuItems;
    private Map<SupportSubMenu, SubMenu> mSubMenus;

    BaseMenuWrapper(@Signature({"(", "Landroid/content/Context;", "TT;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "TT;)V"}) T $r2) throws  {
        super($r2);
        this.mContext = $r1;
    }

    final MenuItem getMenuItemWrapper(MenuItem $r1) throws  {
        if (!($r1 instanceof SupportMenuItem)) {
            return $r1;
        }
        SupportMenuItem $r2 = (SupportMenuItem) $r1;
        if (this.mMenuItems == null) {
            this.mMenuItems = new ArrayMap();
        }
        $r1 = (MenuItem) this.mMenuItems.get($r1);
        if ($r1 != null) {
            return $r1;
        }
        $r1 = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, $r2);
        this.mMenuItems.put($r2, $r1);
        return $r1;
    }

    final SubMenu getSubMenuWrapper(SubMenu $r1) throws  {
        if (!($r1 instanceof SupportSubMenu)) {
            return $r1;
        }
        SupportSubMenu $r2 = (SupportSubMenu) $r1;
        if (this.mSubMenus == null) {
            this.mSubMenus = new ArrayMap();
        }
        $r1 = (SubMenu) this.mSubMenus.get($r2);
        if ($r1 != null) {
            return $r1;
        }
        $r1 = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, $r2);
        this.mSubMenus.put($r2, $r1);
        return $r1;
    }

    final void internalClear() throws  {
        if (this.mMenuItems != null) {
            this.mMenuItems.clear();
        }
        if (this.mSubMenus != null) {
            this.mSubMenus.clear();
        }
    }

    final void internalRemoveGroup(int $i0) throws  {
        if (this.mMenuItems != null) {
            Iterator $r3 = this.mMenuItems.keySet().iterator();
            while ($r3.hasNext()) {
                if ($i0 == ((MenuItem) $r3.next()).getGroupId()) {
                    $r3.remove();
                }
            }
        }
    }

    final void internalRemoveItem(int $i0) throws  {
        if (this.mMenuItems != null) {
            Iterator $r3 = this.mMenuItems.keySet().iterator();
            while ($r3.hasNext()) {
                if ($i0 == ((MenuItem) $r3.next()).getItemId()) {
                    $r3.remove();
                    return;
                }
            }
        }
    }
}
