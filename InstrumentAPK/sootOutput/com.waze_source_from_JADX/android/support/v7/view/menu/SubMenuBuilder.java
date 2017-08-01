package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class SubMenuBuilder extends MenuBuilder implements SubMenu {
    private MenuItemImpl mItem;
    private MenuBuilder mParentMenu;

    public SubMenuBuilder(Context $r1, MenuBuilder $r2, MenuItemImpl $r3) throws  {
        super($r1);
        this.mParentMenu = $r2;
        this.mItem = $r3;
    }

    public void setQwertyMode(boolean $z0) throws  {
        this.mParentMenu.setQwertyMode($z0);
    }

    public boolean isQwertyMode() throws  {
        return this.mParentMenu.isQwertyMode();
    }

    public void setShortcutsVisible(boolean $z0) throws  {
        this.mParentMenu.setShortcutsVisible($z0);
    }

    public boolean isShortcutsVisible() throws  {
        return this.mParentMenu.isShortcutsVisible();
    }

    public Menu getParentMenu() throws  {
        return this.mParentMenu;
    }

    public MenuItem getItem() throws  {
        return this.mItem;
    }

    public void setCallback(Callback $r1) throws  {
        this.mParentMenu.setCallback($r1);
    }

    public MenuBuilder getRootMenu() throws  {
        return this.mParentMenu;
    }

    boolean dispatchMenuItemSelected(MenuBuilder $r1, MenuItem $r2) throws  {
        return super.dispatchMenuItemSelected($r1, $r2) || this.mParentMenu.dispatchMenuItemSelected($r1, $r2);
    }

    public SubMenu setIcon(Drawable $r1) throws  {
        this.mItem.setIcon($r1);
        return this;
    }

    public SubMenu setIcon(int $i0) throws  {
        this.mItem.setIcon($i0);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable $r1) throws  {
        super.setHeaderIconInt($r1);
        return this;
    }

    public SubMenu setHeaderIcon(int $i0) throws  {
        super.setHeaderIconInt(ContextCompat.getDrawable(getContext(), $i0));
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence $r1) throws  {
        super.setHeaderTitleInt($r1);
        return this;
    }

    public SubMenu setHeaderTitle(int $i0) throws  {
        super.setHeaderTitleInt(getContext().getResources().getString($i0));
        return this;
    }

    public SubMenu setHeaderView(View $r1) throws  {
        super.setHeaderViewInt($r1);
        return this;
    }

    public boolean expandItemActionView(MenuItemImpl $r1) throws  {
        return this.mParentMenu.expandItemActionView($r1);
    }

    public boolean collapseItemActionView(MenuItemImpl $r1) throws  {
        return this.mParentMenu.collapseItemActionView($r1);
    }

    public String getActionViewStatesKey() throws  {
        int $i0 = this.mItem != null ? this.mItem.getItemId() : 0;
        return $i0 == 0 ? null : super.getActionViewStatesKey() + ":" + $i0;
    }
}
