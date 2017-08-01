package android.support.v7.view.menu;

import android.graphics.drawable.Drawable;

public interface MenuView {

    public interface ItemView {
        MenuItemImpl getItemData() throws ;

        void initialize(MenuItemImpl menuItemImpl, int i) throws ;

        boolean prefersCondensedTitle() throws ;

        void setCheckable(boolean z) throws ;

        void setChecked(boolean z) throws ;

        void setEnabled(boolean z) throws ;

        void setIcon(Drawable drawable) throws ;

        void setShortcut(boolean z, char c) throws ;

        void setTitle(CharSequence charSequence) throws ;

        boolean showsIcon() throws ;
    }

    int getWindowAnimations() throws ;

    void initialize(MenuBuilder menuBuilder) throws ;
}
