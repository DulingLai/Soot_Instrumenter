package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;

public class PopupMenu implements Callback, MenuPresenter.Callback {
    private View mAnchor;
    private Context mContext;
    private OnDismissListener mDismissListener;
    private OnTouchListener mDragListener;
    private MenuBuilder mMenu;
    private OnMenuItemClickListener mMenuItemClickListener;
    private MenuPopupHelper mPopup;

    public interface OnDismissListener {
        void onDismiss(PopupMenu popupMenu) throws ;
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem) throws ;
    }

    public PopupMenu(Context $r1, View $r2) throws  {
        this($r1, $r2, 0);
    }

    public PopupMenu(Context $r1, View $r2, int $i0) throws  {
        this($r1, $r2, $i0, C0192R.attr.popupMenuStyle, 0);
    }

    public PopupMenu(Context $r1, View $r2, int $i0, int $i1, int $i2) throws  {
        this.mContext = $r1;
        this.mMenu = new MenuBuilder($r1);
        this.mMenu.setCallback(this);
        this.mAnchor = $r2;
        this.mPopup = new MenuPopupHelper($r1, this.mMenu, $r2, false, $i1, $i2);
        this.mPopup.setGravity($i0);
        this.mPopup.setCallback(this);
    }

    public void setGravity(int $i0) throws  {
        this.mPopup.setGravity($i0);
    }

    public int getGravity() throws  {
        return this.mPopup.getGravity();
    }

    public OnTouchListener getDragToOpenListener() throws  {
        if (this.mDragListener == null) {
            this.mDragListener = new ForwardingListener(this.mAnchor) {
                protected boolean onForwardingStarted() throws  {
                    PopupMenu.this.show();
                    return true;
                }

                protected boolean onForwardingStopped() throws  {
                    PopupMenu.this.dismiss();
                    return true;
                }

                public ListPopupWindow getPopup() throws  {
                    return PopupMenu.this.mPopup.getPopup();
                }
            };
        }
        return this.mDragListener;
    }

    public Menu getMenu() throws  {
        return this.mMenu;
    }

    public MenuInflater getMenuInflater() throws  {
        return new SupportMenuInflater(this.mContext);
    }

    public void inflate(@MenuRes int $i0) throws  {
        getMenuInflater().inflate($i0, this.mMenu);
    }

    public void show() throws  {
        this.mPopup.show();
    }

    public void dismiss() throws  {
        this.mPopup.dismiss();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener $r1) throws  {
        this.mMenuItemClickListener = $r1;
    }

    public void setOnDismissListener(OnDismissListener $r1) throws  {
        this.mDismissListener = $r1;
    }

    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem $r2) throws  {
        if (this.mMenuItemClickListener != null) {
            return this.mMenuItemClickListener.onMenuItemClick($r2);
        }
        return false;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) throws  {
        if (this.mDismissListener != null) {
            this.mDismissListener.onDismiss(this);
        }
    }

    public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if (!$r1.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.mContext, $r1, this.mAnchor).show();
        return true;
    }

    public void onCloseSubMenu(SubMenuBuilder menu) throws  {
    }

    public void onMenuModeChange(MenuBuilder menu) throws  {
    }
}
