package android.support.v7.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView.ItemView;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public class ListMenuPresenter implements MenuPresenter, OnItemClickListener {
    private static final String TAG = "ListMenuPresenter";
    public static final String VIEWS_TAG = "android:menu:list";
    MenuAdapter mAdapter;
    private Callback mCallback;
    Context mContext;
    private int mId;
    LayoutInflater mInflater;
    private int mItemIndexOffset;
    int mItemLayoutRes;
    MenuBuilder mMenu;
    ExpandedMenuView mMenuView;
    int mThemeRes;

    private class MenuAdapter extends BaseAdapter {
        private int mExpandedIndex = -1;

        public MenuAdapter() throws  {
            findExpandedIndex();
        }

        public int getCount() throws  {
            int $i0 = ListMenuPresenter.this.mMenu.getNonActionItems().size() - ListMenuPresenter.this.mItemIndexOffset;
            return this.mExpandedIndex < 0 ? $i0 : $i0 - 1;
        }

        public MenuItemImpl getItem(int $i0) throws  {
            ArrayList $r3 = ListMenuPresenter.this.mMenu.getNonActionItems();
            $i0 += ListMenuPresenter.this.mItemIndexOffset;
            if (this.mExpandedIndex >= 0 && $i0 >= this.mExpandedIndex) {
                $i0++;
            }
            return (MenuItemImpl) $r3.get($i0);
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }

        public View getView(int $i0, View $r2, ViewGroup $r1) throws  {
            if ($r2 == null) {
                $r2 = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, $r1, false);
            }
            ((ItemView) $r2).initialize(getItem($i0), 0);
            return $r2;
        }

        void findExpandedIndex() throws  {
            MenuItemImpl $r3 = ListMenuPresenter.this.mMenu.getExpandedItem();
            if ($r3 != null) {
                ArrayList $r4 = ListMenuPresenter.this.mMenu.getNonActionItems();
                int $i0 = $r4.size();
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    if (((MenuItemImpl) $r4.get($i1)) == $r3) {
                        this.mExpandedIndex = $i1;
                        return;
                    }
                }
            }
            this.mExpandedIndex = -1;
        }

        public void notifyDataSetChanged() throws  {
            findExpandedIndex();
            super.notifyDataSetChanged();
        }
    }

    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) throws  {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) throws  {
        return false;
    }

    public boolean flagActionItems() throws  {
        return false;
    }

    public ListMenuPresenter(Context $r1, int $i0) throws  {
        this($i0, 0);
        this.mContext = $r1;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public ListMenuPresenter(int $i0, int $i1) throws  {
        this.mItemLayoutRes = $i0;
        this.mThemeRes = $i1;
    }

    public void initForMenu(Context $r1, MenuBuilder $r2) throws  {
        if (this.mThemeRes != 0) {
            this.mContext = new ContextThemeWrapper($r1, this.mThemeRes);
            this.mInflater = LayoutInflater.from(this.mContext);
        } else if (this.mContext != null) {
            this.mContext = $r1;
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(this.mContext);
            }
        }
        this.mMenu = $r2;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public MenuView getMenuView(ViewGroup $r1) throws  {
        if (this.mMenuView == null) {
            this.mMenuView = (ExpandedMenuView) this.mInflater.inflate(C0192R.layout.abc_expanded_menu_layout, $r1, false);
            if (this.mAdapter == null) {
                this.mAdapter = new MenuAdapter();
            }
            this.mMenuView.setAdapter(this.mAdapter);
            this.mMenuView.setOnItemClickListener(this);
        }
        return this.mMenuView;
    }

    public ListAdapter getAdapter() throws  {
        if (this.mAdapter == null) {
            this.mAdapter = new MenuAdapter();
        }
        return this.mAdapter;
    }

    public void updateMenuView(boolean cleared) throws  {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setCallback(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    public boolean onSubMenuSelected(SubMenuBuilder $r1) throws  {
        if (!$r1.hasVisibleItems()) {
            return false;
        }
        new MenuDialogHelper($r1).show(null);
        if (this.mCallback != null) {
            this.mCallback.onOpenSubMenu($r1);
        }
        return true;
    }

    public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu($r1, $z0);
        }
    }

    int getItemIndexOffset() throws  {
        return this.mItemIndexOffset;
    }

    public void setItemIndexOffset(int $i0) throws  {
        this.mItemIndexOffset = $i0;
        if (this.mMenuView != null) {
            updateMenuView(false);
        }
    }

    public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
        this.mMenu.performItemAction(this.mAdapter.getItem($i0), this, 0);
    }

    public void saveHierarchyState(Bundle $r1) throws  {
        SparseArray $r2 = new SparseArray();
        if (this.mMenuView != null) {
            this.mMenuView.saveHierarchyState($r2);
        }
        $r1.putSparseParcelableArray(VIEWS_TAG, $r2);
    }

    public void restoreHierarchyState(Bundle $r1) throws  {
        SparseArray $r2 = $r1.getSparseParcelableArray(VIEWS_TAG);
        if ($r2 != null) {
            this.mMenuView.restoreHierarchyState($r2);
        }
    }

    public void setId(int $i0) throws  {
        this.mId = $i0;
    }

    public int getId() throws  {
        return this.mId;
    }

    public Parcelable onSaveInstanceState() throws  {
        if (this.mMenuView == null) {
            return null;
        }
        Bundle $r2 = new Bundle();
        saveHierarchyState($r2);
        return $r2;
    }

    public void onRestoreInstanceState(Parcelable $r1) throws  {
        restoreHierarchyState((Bundle) $r1);
    }
}
