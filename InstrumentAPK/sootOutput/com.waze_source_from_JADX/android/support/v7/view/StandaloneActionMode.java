package android.support.v7.view;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

public class StandaloneActionMode extends ActionMode implements Callback {
    private ActionMode.Callback mCallback;
    private Context mContext;
    private ActionBarContextView mContextView;
    private WeakReference<View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;
    private MenuBuilder mMenu;

    public StandaloneActionMode(Context $r1, ActionBarContextView $r2, ActionMode.Callback $r3, boolean $z0) throws  {
        this.mContext = $r1;
        this.mContextView = $r2;
        this.mCallback = $r3;
        this.mMenu = new MenuBuilder($r2.getContext()).setDefaultShowAsAction(1);
        this.mMenu.setCallback(this);
        this.mFocusable = $z0;
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mContextView.setTitle($r1);
    }

    public void setSubtitle(CharSequence $r1) throws  {
        this.mContextView.setSubtitle($r1);
    }

    public void setTitle(int $i0) throws  {
        setTitle(this.mContext.getString($i0));
    }

    public void setSubtitle(int $i0) throws  {
        setSubtitle(this.mContext.getString($i0));
    }

    public void setTitleOptionalHint(boolean $z0) throws  {
        super.setTitleOptionalHint($z0);
        this.mContextView.setTitleOptional($z0);
    }

    public boolean isTitleOptional() throws  {
        return this.mContextView.isTitleOptional();
    }

    public void setCustomView(View $r1) throws  {
        this.mContextView.setCustomView($r1);
        this.mCustomView = $r1 != null ? new WeakReference($r1) : null;
    }

    public void invalidate() throws  {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    public void finish() throws  {
        if (!this.mFinished) {
            this.mFinished = true;
            this.mContextView.sendAccessibilityEvent(32);
            this.mCallback.onDestroyActionMode(this);
        }
    }

    public Menu getMenu() throws  {
        return this.mMenu;
    }

    public CharSequence getTitle() throws  {
        return this.mContextView.getTitle();
    }

    public CharSequence getSubtitle() throws  {
        return this.mContextView.getSubtitle();
    }

    public View getCustomView() throws  {
        return this.mCustomView != null ? (View) this.mCustomView.get() : null;
    }

    public MenuInflater getMenuInflater() throws  {
        return new SupportMenuInflater(this.mContextView.getContext());
    }

    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem $r2) throws  {
        return this.mCallback.onActionItemClicked(this, $r2);
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) throws  {
    }

    public boolean onSubMenuSelected(SubMenuBuilder $r1) throws  {
        if (!$r1.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.mContextView.getContext(), $r1).show();
        return true;
    }

    public void onCloseSubMenu(SubMenuBuilder menu) throws  {
    }

    public void onMenuModeChange(MenuBuilder menu) throws  {
        invalidate();
        this.mContextView.showOverflowMenu();
    }

    public boolean isUiFocusable() throws  {
        return this.mFocusable;
    }
}
