package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.internal.view.SupportMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

class MenuWrapperICS extends BaseMenuWrapper<SupportMenu> implements Menu {
    MenuWrapperICS(Context $r1, SupportMenu $r2) throws  {
        super($r1, $r2);
    }

    public MenuItem add(CharSequence $r1) throws  {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add($r1));
    }

    public MenuItem add(int $i0) throws  {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add($i0));
    }

    public MenuItem add(int $i0, int $i1, int $i2, CharSequence $r1) throws  {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add($i0, $i1, $i2, $r1));
    }

    public MenuItem add(int $i0, int $i1, int $i2, int $i3) throws  {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add($i0, $i1, $i2, $i3));
    }

    public SubMenu addSubMenu(CharSequence $r1) throws  {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu($r1));
    }

    public SubMenu addSubMenu(int $i0) throws  {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu($i0));
    }

    public SubMenu addSubMenu(int $i0, int $i1, int $i2, CharSequence $r1) throws  {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu($i0, $i1, $i2, $r1));
    }

    public SubMenu addSubMenu(int $i0, int $i1, int $i2, int $i3) throws  {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu($i0, $i1, $i2, $i3));
    }

    public int addIntentOptions(int $i0, int $i1, int $i2, ComponentName $r1, Intent[] $r2, Intent $r3, int $i3, MenuItem[] $r4) throws  {
        MenuItem[] $r5 = null;
        if ($r4 != null) {
            $r5 = new MenuItem[$r4.length];
        }
        $i1 = ((SupportMenu) this.mWrappedObject).addIntentOptions($i0, $i1, $i2, $r1, $r2, $r3, $i3, $r5);
        if ($r5 == null) {
            return $i1;
        }
        $i0 = $r5.length;
        for ($i2 = 0; $i2 < $i0; $i2++) {
            $r4[$i2] = getMenuItemWrapper($r5[$i2]);
        }
        return $i1;
    }

    public void removeItem(int $i0) throws  {
        internalRemoveItem($i0);
        ((SupportMenu) this.mWrappedObject).removeItem($i0);
    }

    public void removeGroup(int $i0) throws  {
        internalRemoveGroup($i0);
        ((SupportMenu) this.mWrappedObject).removeGroup($i0);
    }

    public void clear() throws  {
        internalClear();
        ((SupportMenu) this.mWrappedObject).clear();
    }

    public void setGroupCheckable(int $i0, boolean $z0, boolean $z1) throws  {
        ((SupportMenu) this.mWrappedObject).setGroupCheckable($i0, $z0, $z1);
    }

    public void setGroupVisible(int $i0, boolean $z0) throws  {
        ((SupportMenu) this.mWrappedObject).setGroupVisible($i0, $z0);
    }

    public void setGroupEnabled(int $i0, boolean $z0) throws  {
        ((SupportMenu) this.mWrappedObject).setGroupEnabled($i0, $z0);
    }

    public boolean hasVisibleItems() throws  {
        return ((SupportMenu) this.mWrappedObject).hasVisibleItems();
    }

    public MenuItem findItem(int $i0) throws  {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).findItem($i0));
    }

    public int size() throws  {
        return ((SupportMenu) this.mWrappedObject).size();
    }

    public MenuItem getItem(int $i0) throws  {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).getItem($i0));
    }

    public void close() throws  {
        ((SupportMenu) this.mWrappedObject).close();
    }

    public boolean performShortcut(int $i0, KeyEvent $r1, int $i1) throws  {
        return ((SupportMenu) this.mWrappedObject).performShortcut($i0, $r1, $i1);
    }

    public boolean isShortcutKey(int $i0, KeyEvent $r1) throws  {
        return ((SupportMenu) this.mWrappedObject).isShortcutKey($i0, $r1);
    }

    public boolean performIdentifierAction(int $i0, int $i1) throws  {
        return ((SupportMenu) this.mWrappedObject).performIdentifierAction($i0, $i1);
    }

    public void setQwertyMode(boolean $z0) throws  {
        ((SupportMenu) this.mWrappedObject).setQwertyMode($z0);
    }
}
