package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public class ActionMenuItem implements SupportMenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int NO_ICON = 0;
    private final int mCategoryOrder;
    private OnMenuItemClickListener mClickListener;
    private Context mContext;
    private int mFlags = 16;
    private final int mGroup;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private final int mId;
    private Intent mIntent;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private char mShortcutNumericChar;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;

    public boolean collapseActionView() throws  {
        return false;
    }

    public boolean expandActionView() throws  {
        return false;
    }

    public View getActionView() throws  {
        return null;
    }

    public ContextMenuInfo getMenuInfo() throws  {
        return null;
    }

    public SubMenu getSubMenu() throws  {
        return null;
    }

    public ActionProvider getSupportActionProvider() throws  {
        return null;
    }

    public boolean hasSubMenu() throws  {
        return false;
    }

    public boolean isActionViewExpanded() throws  {
        return false;
    }

    public ActionMenuItem(Context $r1, int $i0, int $i1, int $i2, int $i3, CharSequence $r2) throws  {
        this.mContext = $r1;
        this.mId = $i1;
        this.mGroup = $i0;
        this.mCategoryOrder = $i2;
        this.mOrdering = $i3;
        this.mTitle = $r2;
    }

    public char getAlphabeticShortcut() throws  {
        return this.mShortcutAlphabeticChar;
    }

    public int getGroupId() throws  {
        return this.mGroup;
    }

    public Drawable getIcon() throws  {
        return this.mIconDrawable;
    }

    public Intent getIntent() throws  {
        return this.mIntent;
    }

    public int getItemId() throws  {
        return this.mId;
    }

    public char getNumericShortcut() throws  {
        return this.mShortcutNumericChar;
    }

    public int getOrder() throws  {
        return this.mOrdering;
    }

    public CharSequence getTitle() throws  {
        return this.mTitle;
    }

    public CharSequence getTitleCondensed() throws  {
        return this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle;
    }

    public boolean isCheckable() throws  {
        return (this.mFlags & 1) != 0;
    }

    public boolean isChecked() throws  {
        return (this.mFlags & 2) != 0;
    }

    public boolean isEnabled() throws  {
        return (this.mFlags & 16) != 0;
    }

    public boolean isVisible() throws  {
        return (this.mFlags & 8) == 0;
    }

    public MenuItem setAlphabeticShortcut(char $c0) throws  {
        this.mShortcutAlphabeticChar = $c0;
        return this;
    }

    public MenuItem setCheckable(boolean $z0) throws  {
        this.mFlags = ($z0 ? (byte) 1 : (byte) 0) | (this.mFlags & -2);
        return this;
    }

    public ActionMenuItem setExclusiveCheckable(boolean $z0) throws  {
        this.mFlags = ($z0 ? (byte) 4 : (byte) 0) | (this.mFlags & -5);
        return this;
    }

    public MenuItem setChecked(boolean $z0) throws  {
        this.mFlags = ($z0 ? (byte) 2 : (byte) 0) | (this.mFlags & -3);
        return this;
    }

    public MenuItem setEnabled(boolean $z0) throws  {
        this.mFlags = ($z0 ? (byte) 16 : (byte) 0) | (this.mFlags & -17);
        return this;
    }

    public MenuItem setIcon(Drawable $r1) throws  {
        this.mIconDrawable = $r1;
        this.mIconResId = 0;
        return this;
    }

    public MenuItem setIcon(int $i0) throws  {
        this.mIconResId = $i0;
        this.mIconDrawable = ContextCompat.getDrawable(this.mContext, $i0);
        return this;
    }

    public MenuItem setIntent(Intent $r1) throws  {
        this.mIntent = $r1;
        return this;
    }

    public MenuItem setNumericShortcut(char $c0) throws  {
        this.mShortcutNumericChar = $c0;
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener $r1) throws  {
        this.mClickListener = $r1;
        return this;
    }

    public MenuItem setShortcut(char $c0, char $c1) throws  {
        this.mShortcutNumericChar = $c0;
        this.mShortcutAlphabeticChar = $c1;
        return this;
    }

    public MenuItem setTitle(CharSequence $r1) throws  {
        this.mTitle = $r1;
        return this;
    }

    public MenuItem setTitle(int $i0) throws  {
        this.mTitle = this.mContext.getResources().getString($i0);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence $r1) throws  {
        this.mTitleCondensed = $r1;
        return this;
    }

    public MenuItem setVisible(boolean $z0) throws  {
        this.mFlags = ($z0 ? (byte) 0 : (byte) 8) | (this.mFlags & 8);
        return this;
    }

    public boolean invoke() throws  {
        if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
            return true;
        }
        if (this.mIntent == null) {
            return false;
        }
        this.mContext.startActivity(this.mIntent);
        return true;
    }

    public void setShowAsAction(int show) throws  {
    }

    public SupportMenuItem setActionView(View actionView) throws  {
        throw new UnsupportedOperationException();
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) throws  {
        throw new UnsupportedOperationException();
    }

    public android.view.ActionProvider getActionProvider() throws  {
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setActionView(int resId) throws  {
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) throws  {
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setShowAsActionFlags(int $i0) throws  {
        setShowAsAction($i0);
        return this;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener listener) throws  {
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener listener) throws  {
        return this;
    }
}
