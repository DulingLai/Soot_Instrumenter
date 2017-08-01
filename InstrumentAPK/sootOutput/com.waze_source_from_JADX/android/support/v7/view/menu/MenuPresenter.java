package android.support.v7.view.menu;

import android.content.Context;
import android.os.Parcelable;
import android.view.ViewGroup;

public interface MenuPresenter {

    public interface Callback {
        void onCloseMenu(MenuBuilder menuBuilder, boolean z) throws ;

        boolean onOpenSubMenu(MenuBuilder menuBuilder) throws ;
    }

    boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) throws ;

    boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) throws ;

    boolean flagActionItems() throws ;

    int getId() throws ;

    MenuView getMenuView(ViewGroup viewGroup) throws ;

    void initForMenu(Context context, MenuBuilder menuBuilder) throws ;

    void onCloseMenu(MenuBuilder menuBuilder, boolean z) throws ;

    void onRestoreInstanceState(Parcelable parcelable) throws ;

    Parcelable onSaveInstanceState() throws ;

    boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) throws ;

    void setCallback(Callback callback) throws ;

    void updateMenuView(boolean z) throws ;
}
