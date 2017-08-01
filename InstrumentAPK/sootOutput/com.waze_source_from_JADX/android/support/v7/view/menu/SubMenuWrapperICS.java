package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

class SubMenuWrapperICS extends MenuWrapperICS implements SubMenu {
    SubMenuWrapperICS(Context $r1, SupportSubMenu $r2) throws  {
        super($r1, $r2);
    }

    public SupportSubMenu getWrappedObject() throws  {
        return (SupportSubMenu) this.mWrappedObject;
    }

    public SubMenu setHeaderTitle(int $i0) throws  {
        getWrappedObject().setHeaderTitle($i0);
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence $r1) throws  {
        getWrappedObject().setHeaderTitle($r1);
        return this;
    }

    public SubMenu setHeaderIcon(int $i0) throws  {
        getWrappedObject().setHeaderIcon($i0);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable $r1) throws  {
        getWrappedObject().setHeaderIcon($r1);
        return this;
    }

    public SubMenu setHeaderView(View $r1) throws  {
        getWrappedObject().setHeaderView($r1);
        return this;
    }

    public void clearHeader() throws  {
        getWrappedObject().clearHeader();
    }

    public SubMenu setIcon(int $i0) throws  {
        getWrappedObject().setIcon($i0);
        return this;
    }

    public SubMenu setIcon(Drawable $r1) throws  {
        getWrappedObject().setIcon($r1);
        return this;
    }

    public MenuItem getItem() throws  {
        return getMenuItemWrapper(getWrappedObject().getItem());
    }
}
