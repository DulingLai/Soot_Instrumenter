package android.support.v7.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.VisibilityListener;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;

public final class MenuItemImpl implements SupportMenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int IS_ACTION = 32;
    static final int NO_ICON = 0;
    private static final int SHOW_AS_ACTION_MASK = 3;
    private static final String TAG = "MenuItemImpl";
    private static String sDeleteShortcutLabel;
    private static String sEnterShortcutLabel;
    private static String sPrependShortcutLabel;
    private static String sSpaceShortcutLabel;
    private ActionProvider mActionProvider;
    private View mActionView;
    private final int mCategoryOrder;
    private OnMenuItemClickListener mClickListener;
    private int mFlags = 16;
    private final int mGroup;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private final int mId;
    private Intent mIntent;
    private boolean mIsActionViewExpanded = false;
    private Runnable mItemCallback;
    private MenuBuilder mMenu;
    private ContextMenuInfo mMenuInfo;
    private OnActionExpandListener mOnActionExpandListener;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private char mShortcutNumericChar;
    private int mShowAsAction = 0;
    private SubMenuBuilder mSubMenu;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;

    class C02031 implements VisibilityListener {
        C02031() throws  {
        }

        public void onActionProviderVisibilityChanged(boolean isVisible) throws  {
            MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
        }
    }

    MenuItemImpl(MenuBuilder $r1, int $i0, int $i1, int $i2, int $i3, CharSequence $r2, int $i4) throws  {
        this.mMenu = $r1;
        this.mId = $i1;
        this.mGroup = $i0;
        this.mCategoryOrder = $i2;
        this.mOrdering = $i3;
        this.mTitle = $r2;
        this.mShowAsAction = $i4;
    }

    public boolean invoke() throws  {
        if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
            return true;
        }
        if (this.mMenu.dispatchMenuItemSelected(this.mMenu.getRootMenu(), this)) {
            return true;
        }
        if (this.mItemCallback != null) {
            this.mItemCallback.run();
            return true;
        }
        if (this.mIntent != null) {
            try {
                this.mMenu.getContext().startActivity(this.mIntent);
                return true;
            } catch (ActivityNotFoundException $r1) {
                Log.e(TAG, "Can't find activity to handle intent; ignoring", $r1);
            }
        }
        return this.mActionProvider != null && this.mActionProvider.onPerformDefaultAction();
    }

    public boolean isEnabled() throws  {
        return (this.mFlags & 16) != 0;
    }

    public MenuItem setEnabled(boolean $z0) throws  {
        if ($z0) {
            this.mFlags |= 16;
        } else {
            this.mFlags &= -17;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public int getGroupId() throws  {
        return this.mGroup;
    }

    @CapturedViewProperty
    public int getItemId() throws  {
        return this.mId;
    }

    public int getOrder() throws  {
        return this.mCategoryOrder;
    }

    public int getOrdering() throws  {
        return this.mOrdering;
    }

    public Intent getIntent() throws  {
        return this.mIntent;
    }

    public MenuItem setIntent(Intent $r1) throws  {
        this.mIntent = $r1;
        return this;
    }

    Runnable getCallback() throws  {
        return this.mItemCallback;
    }

    public MenuItem setCallback(Runnable $r1) throws  {
        this.mItemCallback = $r1;
        return this;
    }

    public char getAlphabeticShortcut() throws  {
        return this.mShortcutAlphabeticChar;
    }

    public MenuItem setAlphabeticShortcut(char $c0) throws  {
        if (this.mShortcutAlphabeticChar == $c0) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase($c0);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public char getNumericShortcut() throws  {
        return this.mShortcutNumericChar;
    }

    public MenuItem setNumericShortcut(char $c0) throws  {
        if (this.mShortcutNumericChar == $c0) {
            return this;
        }
        this.mShortcutNumericChar = $c0;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setShortcut(char $c0, char $c1) throws  {
        this.mShortcutNumericChar = $c0;
        this.mShortcutAlphabeticChar = Character.toLowerCase($c1);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    char getShortcut() throws  {
        return this.mMenu.isQwertyMode() ? this.mShortcutAlphabeticChar : this.mShortcutNumericChar;
    }

    String getShortcutLabel() throws  {
        char $c0 = getShortcut();
        if ($c0 == '\u0000') {
            return "";
        }
        StringBuilder $r1 = new StringBuilder(sPrependShortcutLabel);
        switch ($c0) {
            case '\b':
                $r1.append(sDeleteShortcutLabel);
                break;
            case '\n':
                $r1.append(sEnterShortcutLabel);
                break;
            case ' ':
                $r1.append(sSpaceShortcutLabel);
                break;
            default:
                $r1.append($c0);
                break;
        }
        return $r1.toString();
    }

    boolean shouldShowShortcut() throws  {
        return this.mMenu.isShortcutsVisible() && getShortcut() != '\u0000';
    }

    public SubMenu getSubMenu() throws  {
        return this.mSubMenu;
    }

    public boolean hasSubMenu() throws  {
        return this.mSubMenu != null;
    }

    public void setSubMenu(SubMenuBuilder $r1) throws  {
        this.mSubMenu = $r1;
        $r1.setHeaderTitle(getTitle());
    }

    @CapturedViewProperty
    public CharSequence getTitle() throws  {
        return this.mTitle;
    }

    CharSequence getTitleForItemView(ItemView $r1) throws  {
        return ($r1 == null || !$r1.prefersCondensedTitle()) ? getTitle() : getTitleCondensed();
    }

    public MenuItem setTitle(CharSequence $r1) throws  {
        this.mTitle = $r1;
        this.mMenu.onItemsChanged(false);
        if (this.mSubMenu == null) {
            return this;
        }
        this.mSubMenu.setHeaderTitle($r1);
        return this;
    }

    public MenuItem setTitle(int $i0) throws  {
        return setTitle(this.mMenu.getContext().getString($i0));
    }

    public CharSequence getTitleCondensed() throws  {
        CharSequence $r2 = this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle;
        if (VERSION.SDK_INT >= 18 || $r2 == null || ($r2 instanceof String)) {
            return $r2;
        }
        return $r2.toString();
    }

    public MenuItem setTitleCondensed(CharSequence $r2) throws  {
        this.mTitleCondensed = $r2;
        if ($r2 == null) {
            this.mMenu.onItemsChanged(false);
        } else {
            this.mMenu.onItemsChanged(false);
        }
        return this;
    }

    public Drawable getIcon() throws  {
        if (this.mIconDrawable != null) {
            return this.mIconDrawable;
        }
        if (this.mIconResId == 0) {
            return null;
        }
        Drawable $r1 = AppCompatDrawableManager.get().getDrawable(this.mMenu.getContext(), this.mIconResId);
        this.mIconResId = 0;
        this.mIconDrawable = $r1;
        return $r1;
    }

    public MenuItem setIcon(Drawable $r1) throws  {
        this.mIconResId = 0;
        this.mIconDrawable = $r1;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIcon(int $i0) throws  {
        this.mIconDrawable = null;
        this.mIconResId = $i0;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public boolean isCheckable() throws  {
        return (this.mFlags & 1) == 1;
    }

    public MenuItem setCheckable(boolean $z0) throws  {
        int $i0 = this.mFlags;
        this.mFlags = ($z0 ? (byte) 1 : (byte) 0) | (this.mFlags & -2);
        if ($i0 == this.mFlags) {
            return this;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public void setExclusiveCheckable(boolean $z0) throws  {
        this.mFlags = ($z0 ? (byte) 4 : (byte) 0) | (this.mFlags & -5);
    }

    public boolean isExclusiveCheckable() throws  {
        return (this.mFlags & 4) != 0;
    }

    public boolean isChecked() throws  {
        return (this.mFlags & 2) == 2;
    }

    public MenuItem setChecked(boolean $z0) throws  {
        if ((this.mFlags & 4) != 0) {
            this.mMenu.setExclusiveItemChecked(this);
            return this;
        }
        setCheckedInt($z0);
        return this;
    }

    void setCheckedInt(boolean $z0) throws  {
        byte $b2;
        int $i0 = this.mFlags;
        int $i1 = this.mFlags & -3;
        if ($z0) {
            $b2 = (byte) 2;
        } else {
            $b2 = (byte) 0;
        }
        this.mFlags = $b2 | $i1;
        if ($i0 != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
    }

    public boolean isVisible() throws  {
        return (this.mActionProvider == null || !this.mActionProvider.overridesItemVisibility()) ? (this.mFlags & 8) == 0 : (this.mFlags & 8) == 0 && this.mActionProvider.isVisible();
    }

    boolean setVisibleInt(boolean $z0) throws  {
        int $i0 = this.mFlags;
        this.mFlags = ($z0 ? (byte) 0 : (byte) 8) | (this.mFlags & -9);
        if ($i0 != this.mFlags) {
            return true;
        }
        return false;
    }

    public MenuItem setVisible(boolean $z0) throws  {
        if (!setVisibleInt($z0)) {
            return this;
        }
        this.mMenu.onItemVisibleChanged(this);
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener $r1) throws  {
        this.mClickListener = $r1;
        return this;
    }

    public String toString() throws  {
        return this.mTitle != null ? this.mTitle.toString() : null;
    }

    void setMenuInfo(ContextMenuInfo $r1) throws  {
        this.mMenuInfo = $r1;
    }

    public ContextMenuInfo getMenuInfo() throws  {
        return this.mMenuInfo;
    }

    public void actionFormatChanged() throws  {
        this.mMenu.onItemActionRequestChanged(this);
    }

    public boolean shouldShowIcon() throws  {
        return this.mMenu.getOptionalIconsVisible();
    }

    public boolean isActionButton() throws  {
        return (this.mFlags & 32) == 32;
    }

    public boolean requestsActionButton() throws  {
        return (this.mShowAsAction & 1) == 1;
    }

    public boolean requiresActionButton() throws  {
        return (this.mShowAsAction & 2) == 2;
    }

    public void setIsActionButton(boolean $z0) throws  {
        if ($z0) {
            this.mFlags |= 32;
        } else {
            this.mFlags &= -33;
        }
    }

    public boolean showsTextAsAction() throws  {
        return (this.mShowAsAction & 4) == 4;
    }

    public void setShowAsAction(int $i0) throws  {
        switch ($i0 & 3) {
            case 0:
            case 1:
            case 2:
                this.mShowAsAction = $i0;
                this.mMenu.onItemActionRequestChanged(this);
                return;
            default:
                throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
    }

    public SupportMenuItem setActionView(View $r1) throws  {
        this.mActionView = $r1;
        this.mActionProvider = null;
        if ($r1 != null && $r1.getId() == -1 && this.mId > 0) {
            $r1.setId(this.mId);
        }
        this.mMenu.onItemActionRequestChanged(this);
        return this;
    }

    public SupportMenuItem setActionView(int $i0) throws  {
        Context $r2 = this.mMenu.getContext();
        setActionView(LayoutInflater.from($r2).inflate($i0, new LinearLayout($r2), false));
        return this;
    }

    public View getActionView() throws  {
        if (this.mActionView != null) {
            return this.mActionView;
        }
        if (this.mActionProvider == null) {
            return null;
        }
        this.mActionView = this.mActionProvider.onCreateActionView(this);
        return this.mActionView;
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) throws  {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public android.view.ActionProvider getActionProvider() throws  {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    public ActionProvider getSupportActionProvider() throws  {
        return this.mActionProvider;
    }

    public SupportMenuItem setSupportActionProvider(ActionProvider $r1) throws  {
        if (this.mActionProvider != null) {
            this.mActionProvider.reset();
        }
        this.mActionView = null;
        this.mActionProvider = $r1;
        this.mMenu.onItemsChanged(true);
        if (this.mActionProvider == null) {
            return this;
        }
        this.mActionProvider.setVisibilityListener(new C02031());
        return this;
    }

    public SupportMenuItem setShowAsActionFlags(int $i0) throws  {
        setShowAsAction($i0);
        return this;
    }

    public boolean expandActionView() throws  {
        if (hasCollapsibleActionView()) {
            return (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionExpand(this)) ? this.mMenu.expandItemActionView(this) : false;
        } else {
            return false;
        }
    }

    public boolean collapseActionView() throws  {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null) {
            return true;
        }
        return (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionCollapse(this)) ? this.mMenu.collapseItemActionView(this) : false;
    }

    public SupportMenuItem setSupportOnActionExpandListener(OnActionExpandListener $r1) throws  {
        this.mOnActionExpandListener = $r1;
        return this;
    }

    public boolean hasCollapsibleActionView() throws  {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null && this.mActionProvider != null) {
            this.mActionView = this.mActionProvider.onCreateActionView(this);
        }
        return this.mActionView != null;
    }

    public void setActionViewExpanded(boolean $z0) throws  {
        this.mIsActionViewExpanded = $z0;
        this.mMenu.onItemsChanged(false);
    }

    public boolean isActionViewExpanded() throws  {
        return this.mIsActionViewExpanded;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) throws  {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setOnActionExpandListener()");
    }
}
