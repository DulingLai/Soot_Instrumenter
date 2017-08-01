package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView.ItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class BaseMenuPresenter implements MenuPresenter {
    private Callback mCallback;
    protected Context mContext;
    private int mId;
    protected LayoutInflater mInflater;
    private int mItemLayoutRes;
    protected MenuBuilder mMenu;
    private int mMenuLayoutRes;
    protected MenuView mMenuView;
    protected Context mSystemContext;
    protected LayoutInflater mSystemInflater;

    public abstract void bindItemView(MenuItemImpl menuItemImpl, ItemView itemView) throws ;

    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) throws  {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) throws  {
        return false;
    }

    public boolean flagActionItems() throws  {
        return false;
    }

    public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) throws  {
        return true;
    }

    public BaseMenuPresenter(Context $r1, int $i0, int $i1) throws  {
        this.mSystemContext = $r1;
        this.mSystemInflater = LayoutInflater.from($r1);
        this.mMenuLayoutRes = $i0;
        this.mItemLayoutRes = $i1;
    }

    public void initForMenu(Context $r1, MenuBuilder $r2) throws  {
        this.mContext = $r1;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mMenu = $r2;
    }

    public MenuView getMenuView(ViewGroup $r1) throws  {
        if (this.mMenuView == null) {
            this.mMenuView = (MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, $r1, false);
            this.mMenuView.initialize(this.mMenu);
            updateMenuView(true);
        }
        return this.mMenuView;
    }

    public void updateMenuView(boolean cleared) throws  {
        ViewGroup $r2 = (ViewGroup) this.mMenuView;
        if ($r2 != null) {
            int $i0 = 0;
            if (this.mMenu != null) {
                this.mMenu.flagActionItems();
                ArrayList $r4 = this.mMenu.getVisibleItems();
                int $i1 = $r4.size();
                for (int $i2 = 0; $i2 < $i1; $i2++) {
                    MenuItemImpl $r6 = (MenuItemImpl) $r4.get($i2);
                    if (shouldIncludeItem($i0, $r6)) {
                        View $r7 = $r2.getChildAt($i0);
                        MenuItemImpl $r9 = $r7 instanceof ItemView ? ((ItemView) $r7).getItemData() : null;
                        View $r10 = getItemView($r6, $r7, $r2);
                        if ($r6 != $r9) {
                            $r10.setPressed(false);
                            ViewCompat.jumpDrawablesToCurrentState($r10);
                        }
                        if ($r10 != $r7) {
                            addItemView($r10, $i0);
                        }
                        $i0++;
                    }
                }
            }
            while ($i0 < $r2.getChildCount()) {
                if (!filterLeftoverView($r2, $i0)) {
                    $i0++;
                }
            }
        }
    }

    protected void addItemView(View $r1, int $i0) throws  {
        ViewGroup $r3 = (ViewGroup) $r1.getParent();
        if ($r3 != null) {
            $r3.removeView($r1);
        }
        ((ViewGroup) this.mMenuView).addView($r1, $i0);
    }

    protected boolean filterLeftoverView(ViewGroup $r1, int $i0) throws  {
        $r1.removeViewAt($i0);
        return true;
    }

    public void setCallback(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    public Callback getCallback() throws  {
        return this.mCallback;
    }

    public ItemView createItemView(ViewGroup $r1) throws  {
        return (ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, $r1, false);
    }

    public View getItemView(MenuItemImpl $r1, View $r2, ViewGroup $r3) throws  {
        ItemView $r4;
        if ($r2 instanceof ItemView) {
            $r4 = (ItemView) $r2;
        } else {
            $r4 = createItemView($r3);
        }
        bindItemView($r1, $r4);
        return (View) $r4;
    }

    public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu($r1, $z0);
        }
    }

    public boolean onSubMenuSelected(SubMenuBuilder $r1) throws  {
        if (this.mCallback != null) {
            return this.mCallback.onOpenSubMenu($r1);
        }
        return false;
    }

    public int getId() throws  {
        return this.mId;
    }

    public void setId(int $i0) throws  {
        this.mId = $i0;
    }
}
