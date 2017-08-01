package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.PopupWindow.OnDismissListener;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public class MenuPopupHelper implements MenuPresenter, OnKeyListener, OnGlobalLayoutListener, OnItemClickListener, OnDismissListener {
    static final int ITEM_LAYOUT = C0192R.layout.abc_popup_menu_item_layout;
    private static final String TAG = "MenuPopupHelper";
    private final MenuAdapter mAdapter;
    private View mAnchorView;
    private int mContentWidth;
    private final Context mContext;
    private int mDropDownGravity;
    boolean mForceShowIcon;
    private boolean mHasContentWidth;
    private final LayoutInflater mInflater;
    private ViewGroup mMeasureParent;
    private final MenuBuilder mMenu;
    private final boolean mOverflowOnly;
    private ListPopupWindow mPopup;
    private final int mPopupMaxWidth;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private Callback mPresenterCallback;
    private ViewTreeObserver mTreeObserver;

    private class MenuAdapter extends BaseAdapter {
        private MenuBuilder mAdapterMenu;
        private int mExpandedIndex = -1;

        public MenuAdapter(MenuBuilder $r2) throws  {
            this.mAdapterMenu = $r2;
            findExpandedIndex();
        }

        public int getCount() throws  {
            ArrayList $r3 = MenuPopupHelper.this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
            if (this.mExpandedIndex < 0) {
                return $r3.size();
            }
            return $r3.size() - 1;
        }

        public MenuItemImpl getItem(int $i0) throws  {
            ArrayList $r3 = MenuPopupHelper.this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
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
                $r2 = MenuPopupHelper.this.mInflater.inflate(MenuPopupHelper.ITEM_LAYOUT, $r1, false);
            }
            ItemView $r5 = (ItemView) $r2;
            if (MenuPopupHelper.this.mForceShowIcon) {
                ((ListMenuItemView) $r2).setForceShowIcon(true);
            }
            $r5.initialize(getItem($i0), 0);
            return $r2;
        }

        void findExpandedIndex() throws  {
            MenuItemImpl $r3 = MenuPopupHelper.this.mMenu.getExpandedItem();
            if ($r3 != null) {
                ArrayList $r4 = MenuPopupHelper.this.mMenu.getNonActionItems();
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

    public int getId() throws  {
        return 0;
    }

    public Parcelable onSaveInstanceState() throws  {
        return null;
    }

    public MenuPopupHelper(Context $r1, MenuBuilder $r2) throws  {
        this($r1, $r2, null, false, C0192R.attr.popupMenuStyle);
    }

    public MenuPopupHelper(Context $r1, MenuBuilder $r2, View $r3) throws  {
        this($r1, $r2, $r3, false, C0192R.attr.popupMenuStyle);
    }

    public MenuPopupHelper(Context $r1, MenuBuilder $r2, View $r3, boolean $z0, int $i0) throws  {
        this($r1, $r2, $r3, $z0, $i0, 0);
    }

    public MenuPopupHelper(Context $r1, MenuBuilder $r2, View $r3, boolean $z0, int $i0, int $i1) throws  {
        this.mDropDownGravity = 0;
        this.mContext = $r1;
        this.mInflater = LayoutInflater.from($r1);
        this.mMenu = $r2;
        this.mAdapter = new MenuAdapter(this.mMenu);
        this.mOverflowOnly = $z0;
        this.mPopupStyleAttr = $i0;
        this.mPopupStyleRes = $i1;
        Resources $r7 = $r1.getResources();
        this.mPopupMaxWidth = Math.max($r7.getDisplayMetrics().widthPixels / 2, $r7.getDimensionPixelSize(C0192R.dimen.abc_config_prefDialogWidth));
        this.mAnchorView = $r3;
        $r2.addMenuPresenter(this, $r1);
    }

    public void setAnchorView(View $r1) throws  {
        this.mAnchorView = $r1;
    }

    public void setForceShowIcon(boolean $z0) throws  {
        this.mForceShowIcon = $z0;
    }

    public void setGravity(int $i0) throws  {
        this.mDropDownGravity = $i0;
    }

    public int getGravity() throws  {
        return this.mDropDownGravity;
    }

    public void show() throws  {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public ListPopupWindow getPopup() throws  {
        return this.mPopup;
    }

    public boolean tryShow() throws  {
        boolean $z0 = false;
        this.mPopup = new ListPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        this.mPopup.setOnDismissListener(this);
        this.mPopup.setOnItemClickListener(this);
        this.mPopup.setAdapter(this.mAdapter);
        this.mPopup.setModal(true);
        View $r1 = this.mAnchorView;
        if ($r1 == null) {
            return false;
        }
        if (this.mTreeObserver == null) {
            $z0 = true;
        }
        this.mTreeObserver = $r1.getViewTreeObserver();
        if ($z0) {
            this.mTreeObserver.addOnGlobalLayoutListener(this);
        }
        this.mPopup.setAnchorView($r1);
        this.mPopup.setDropDownGravity(this.mDropDownGravity);
        if (!this.mHasContentWidth) {
            this.mContentWidth = measureContentWidth();
            this.mHasContentWidth = true;
        }
        this.mPopup.setContentWidth(this.mContentWidth);
        this.mPopup.setInputMethodMode(2);
        this.mPopup.show();
        this.mPopup.getListView().setOnKeyListener(this);
        return true;
    }

    public void dismiss() throws  {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public void onDismiss() throws  {
        this.mPopup = null;
        this.mMenu.close();
        if (this.mTreeObserver != null) {
            if (!this.mTreeObserver.isAlive()) {
                this.mTreeObserver = this.mAnchorView.getViewTreeObserver();
            }
            this.mTreeObserver.removeGlobalOnLayoutListener(this);
            this.mTreeObserver = null;
        }
    }

    public boolean isShowing() throws  {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
        MenuAdapter $r3 = this.mAdapter;
        $r3.mAdapterMenu.performItemAction($r3.getItem($i0), 0);
    }

    public boolean onKey(View v, int $i0, KeyEvent $r2) throws  {
        if ($r2.getAction() != 1 || $i0 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    private int measureContentWidth() throws  {
        int $i0 = 0;
        View $r3 = null;
        int $i1 = 0;
        MenuAdapter $r1 = this.mAdapter;
        int $i2 = MeasureSpec.makeMeasureSpec(0, 0);
        int $i3 = MeasureSpec.makeMeasureSpec(0, 0);
        int $i4 = $r1.getCount();
        for (int $i5 = 0; $i5 < $i4; $i5++) {
            int $i6 = $r1.getItemViewType($i5);
            if ($i6 != $i1) {
                $i1 = $i6;
                $r3 = null;
            }
            if (this.mMeasureParent == null) {
                this.mMeasureParent = new FrameLayout(this.mContext);
            }
            View $r6 = $r1.getView($i5, $r3, this.mMeasureParent);
            $r3 = $r6;
            $r6.measure($i2, $i3);
            $i6 = $r6.getMeasuredWidth();
            int $i7 = this.mPopupMaxWidth;
            if ($i6 >= $i7) {
                $i0 = this.mPopupMaxWidth;
                this = this;
                return $i0;
            }
            if ($i6 > $i0) {
                $i0 = $i6;
            }
        }
        return $i0;
    }

    public void onGlobalLayout() throws  {
        if (isShowing()) {
            View $r1 = this.mAnchorView;
            if ($r1 == null || !$r1.isShown()) {
                dismiss();
            } else if (isShowing()) {
                this.mPopup.show();
            }
        }
    }

    public void initForMenu(Context context, MenuBuilder menu) throws  {
    }

    public MenuView getMenuView(ViewGroup root) throws  {
        throw new UnsupportedOperationException("MenuPopupHelpers manage their own views");
    }

    public void updateMenuView(boolean cleared) throws  {
        this.mHasContentWidth = false;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setCallback(Callback $r1) throws  {
        this.mPresenterCallback = $r1;
    }

    public boolean onSubMenuSelected(SubMenuBuilder $r1) throws  {
        if ($r1.hasVisibleItems()) {
            MenuPopupHelper $r2 = new MenuPopupHelper(this.mContext, $r1, this.mAnchorView);
            $r2.setCallback(this.mPresenterCallback);
            boolean $z0 = false;
            int $i0 = $r1.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                MenuItem $r6 = $r1.getItem($i1);
                if ($r6.isVisible() && $r6.getIcon() != null) {
                    $z0 = true;
                    break;
                }
            }
            $r2.setForceShowIcon($z0);
            if ($r2.tryShow()) {
                if (this.mPresenterCallback != null) {
                    this.mPresenterCallback.onOpenSubMenu($r1);
                }
                return true;
            }
        }
        return false;
    }

    public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
        if ($r1 == this.mMenu) {
            dismiss();
            if (this.mPresenterCallback != null) {
                this.mPresenterCallback.onCloseMenu($r1, $z0);
            }
        }
    }

    public void onRestoreInstanceState(Parcelable state) throws  {
    }
}
