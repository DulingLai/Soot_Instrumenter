package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.C0192R;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder implements SupportMenu {
    private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
    private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
    private static final String PRESENTER_KEY = "android:menu:presenters";
    private static final String TAG = "MenuBuilder";
    private static final int[] sCategoryToOrder = new int[]{1, 4, 5, 3, 2, 0};
    private ArrayList<MenuItemImpl> mActionItems;
    private Callback mCallback;
    private final Context mContext;
    private ContextMenuInfo mCurrentMenuInfo;
    private int mDefaultShowAsAction = 0;
    private MenuItemImpl mExpandedItem;
    private SparseArray<Parcelable> mFrozenViewStates;
    Drawable mHeaderIcon;
    CharSequence mHeaderTitle;
    View mHeaderView;
    private boolean mIsActionItemsStale;
    private boolean mIsClosing = false;
    private boolean mIsVisibleItemsStale;
    private ArrayList<MenuItemImpl> mItems;
    private boolean mItemsChangedWhileDispatchPrevented = false;
    private ArrayList<MenuItemImpl> mNonActionItems;
    private boolean mOptionalIconsVisible = false;
    private boolean mOverrideVisibleItems;
    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList();
    private boolean mPreventDispatchingItemsChanged = false;
    private boolean mQwertyMode;
    private final Resources mResources;
    private boolean mShortcutsVisible;
    private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList();
    private ArrayList<MenuItemImpl> mVisibleItems;

    public interface Callback {
        boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) throws ;

        void onMenuModeChange(MenuBuilder menuBuilder) throws ;
    }

    public interface ItemInvoker {
        boolean invokeItem(MenuItemImpl menuItemImpl) throws ;
    }

    protected String getActionViewStatesKey() throws  {
        return ACTION_VIEW_STATES_KEY;
    }

    public MenuBuilder(Context $r1) throws  {
        this.mContext = $r1;
        this.mResources = $r1.getResources();
        this.mItems = new ArrayList();
        this.mVisibleItems = new ArrayList();
        this.mIsVisibleItemsStale = true;
        this.mActionItems = new ArrayList();
        this.mNonActionItems = new ArrayList();
        this.mIsActionItemsStale = true;
        setShortcutsVisibleInner(true);
    }

    public MenuBuilder setDefaultShowAsAction(int $i0) throws  {
        this.mDefaultShowAsAction = $i0;
        return this;
    }

    public void addMenuPresenter(MenuPresenter $r1) throws  {
        addMenuPresenter($r1, this.mContext);
    }

    public void addMenuPresenter(MenuPresenter $r1, Context $r2) throws  {
        this.mPresenters.add(new WeakReference($r1));
        $r1.initForMenu($r2, this);
        this.mIsActionItemsStale = true;
    }

    public void removeMenuPresenter(MenuPresenter $r1) throws  {
        Iterator $r3 = this.mPresenters.iterator();
        while ($r3.hasNext()) {
            WeakReference $r5 = (WeakReference) $r3.next();
            MenuPresenter $r6 = (MenuPresenter) $r5.get();
            if ($r6 == null || $r6 == $r1) {
                this.mPresenters.remove($r5);
            }
        }
    }

    private void dispatchPresenterUpdate(boolean $z0) throws  {
        if (!this.mPresenters.isEmpty()) {
            stopDispatchingItemsChanged();
            Iterator $r2 = this.mPresenters.iterator();
            while ($r2.hasNext()) {
                WeakReference $r4 = (WeakReference) $r2.next();
                MenuPresenter $r5 = (MenuPresenter) $r4.get();
                if ($r5 == null) {
                    this.mPresenters.remove($r4);
                } else {
                    $r5.updateMenuView($z0);
                }
            }
            startDispatchingItemsChanged();
        }
    }

    private boolean dispatchSubMenuSelected(SubMenuBuilder $r1, MenuPresenter $r2) throws  {
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean $z0 = false;
        if ($r2 != null) {
            $z0 = $r2.onSubMenuSelected($r1);
        }
        Iterator $r4 = this.mPresenters.iterator();
        while ($r4.hasNext()) {
            WeakReference $r6 = (WeakReference) $r4.next();
            $r2 = (MenuPresenter) $r6.get();
            if ($r2 == null) {
                this.mPresenters.remove($r6);
            } else if (!$z0) {
                $z0 = $r2.onSubMenuSelected($r1);
            }
        }
        return $z0;
    }

    private void dispatchSaveInstanceState(Bundle $r1) throws  {
        if (!this.mPresenters.isEmpty()) {
            SparseArray $r2 = new SparseArray();
            Iterator $r4 = this.mPresenters.iterator();
            while ($r4.hasNext()) {
                WeakReference $r6 = (WeakReference) $r4.next();
                MenuPresenter $r7 = (MenuPresenter) $r6.get();
                if ($r7 == null) {
                    this.mPresenters.remove($r6);
                } else {
                    int $i0 = $r7.getId();
                    if ($i0 > 0) {
                        Parcelable $r8 = $r7.onSaveInstanceState();
                        if ($r8 != null) {
                            $r2.put($i0, $r8);
                        }
                    }
                }
            }
            $r1.putSparseParcelableArray(PRESENTER_KEY, $r2);
        }
    }

    private void dispatchRestoreInstanceState(Bundle $r1) throws  {
        SparseArray $r2 = $r1.getSparseParcelableArray(PRESENTER_KEY);
        if ($r2 != null && !this.mPresenters.isEmpty()) {
            Iterator $r4 = this.mPresenters.iterator();
            while ($r4.hasNext()) {
                WeakReference $r6 = (WeakReference) $r4.next();
                MenuPresenter $r7 = (MenuPresenter) $r6.get();
                if ($r7 == null) {
                    this.mPresenters.remove($r6);
                } else {
                    int $i0 = $r7.getId();
                    if ($i0 > 0) {
                        Parcelable $r8 = (Parcelable) $r2.get($i0);
                        if ($r8 != null) {
                            $r7.onRestoreInstanceState($r8);
                        }
                    }
                }
            }
        }
    }

    public void savePresenterStates(Bundle $r1) throws  {
        dispatchSaveInstanceState($r1);
    }

    public void restorePresenterStates(Bundle $r1) throws  {
        dispatchRestoreInstanceState($r1);
    }

    public void saveActionViewStates(Bundle $r1) throws  {
        SparseArray $r2 = null;
        int $i0 = size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            MenuItem $r3 = getItem($i1);
            View $r4 = MenuItemCompat.getActionView($r3);
            if (!($r4 == null || $r4.getId() == -1)) {
                if ($r2 == null) {
                    $r2 = new SparseArray();
                }
                $r4.saveHierarchyState($r2);
                if (MenuItemCompat.isActionViewExpanded($r3)) {
                    $r1.putInt(EXPANDED_ACTION_VIEW_ID, $r3.getItemId());
                }
            }
            if ($r3.hasSubMenu()) {
                ((SubMenuBuilder) $r3.getSubMenu()).saveActionViewStates($r1);
            }
        }
        if ($r2 != null) {
            $r1.putSparseParcelableArray(getActionViewStatesKey(), $r2);
        }
    }

    public void restoreActionViewStates(Bundle $r1) throws  {
        if ($r1 != null) {
            MenuItem $r4;
            SparseArray $r3 = $r1.getSparseParcelableArray(getActionViewStatesKey());
            int $i0 = size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r4 = getItem($i1);
                View $r5 = MenuItemCompat.getActionView($r4);
                if (!($r5 == null || $r5.getId() == -1)) {
                    $r5.restoreHierarchyState($r3);
                }
                if ($r4.hasSubMenu()) {
                    ((SubMenuBuilder) $r4.getSubMenu()).restoreActionViewStates($r1);
                }
            }
            $i0 = $r1.getInt(EXPANDED_ACTION_VIEW_ID);
            if ($i0 > 0) {
                $r4 = findItem($i0);
                if ($r4 != null) {
                    MenuItemCompat.expandActionView($r4);
                }
            }
        }
    }

    public void setCallback(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    protected MenuItem addInternal(int $i0, int $i1, int $i2, CharSequence $r1) throws  {
        int $i4 = getOrdering($i2);
        MenuItemImpl $r2 = createNewMenuItem($i0, $i1, $i2, $i4, $r1, this.mDefaultShowAsAction);
        if (this.mCurrentMenuInfo != null) {
            $r2.setMenuInfo(this.mCurrentMenuInfo);
        }
        this.mItems.add(findInsertIndex(this.mItems, $i4), $r2);
        onItemsChanged(true);
        return $r2;
    }

    private MenuItemImpl createNewMenuItem(int $i0, int $i1, int $i2, int $i3, CharSequence $r1, int $i4) throws  {
        return new MenuItemImpl(this, $i0, $i1, $i2, $i3, $r1, $i4);
    }

    public MenuItem add(CharSequence $r1) throws  {
        return addInternal(0, 0, 0, $r1);
    }

    public MenuItem add(int $i0) throws  {
        return addInternal(0, 0, 0, this.mResources.getString($i0));
    }

    public MenuItem add(int $i0, int $i1, int $i2, CharSequence $r1) throws  {
        return addInternal($i0, $i1, $i2, $r1);
    }

    public MenuItem add(int $i0, int $i1, int $i2, int $i3) throws  {
        return addInternal($i0, $i1, $i2, this.mResources.getString($i3));
    }

    public SubMenu addSubMenu(CharSequence $r1) throws  {
        return addSubMenu(0, 0, 0, $r1);
    }

    public SubMenu addSubMenu(int $i0) throws  {
        return addSubMenu(0, 0, 0, this.mResources.getString($i0));
    }

    public SubMenu addSubMenu(int $i0, int $i1, int $i2, CharSequence $r1) throws  {
        MenuItemImpl $r5 = (MenuItemImpl) addInternal($i0, $i1, $i2, $r1);
        SubMenuBuilder $r2 = new SubMenuBuilder(this.mContext, this, $r5);
        $r5.setSubMenu($r2);
        return $r2;
    }

    public SubMenu addSubMenu(int $i0, int $i1, int $i2, int $i3) throws  {
        return addSubMenu($i0, $i1, $i2, this.mResources.getString($i3));
    }

    public int addIntentOptions(int $i0, int $i1, int $i2, ComponentName $r1, Intent[] $r2, Intent $r3, int $i3, MenuItem[] $r4) throws  {
        PackageManager $r7 = this.mContext.getPackageManager();
        List $r8 = $r7.queryIntentActivityOptions($r1, $r2, $r3, 0);
        int $i4 = $r8 != null ? $r8.size() : 0;
        if (($i3 & 1) == 0) {
            removeGroup($i0);
        }
        for ($i3 = 0; $i3 < $i4; $i3++) {
            Intent $r11;
            ResolveInfo $r10 = (ResolveInfo) $r8.get($i3);
            if ($r10.specificIndex < 0) {
                $r11 = $r3;
            } else {
                $r11 = $r2[$r10.specificIndex];
            }
            Intent $r5 = new Intent($r11);
            PackageItemInfo $r13 = $r10.activityInfo;
            PackageItemInfo $r12 = $r13;
            String $r14 = $r13.applicationInfo;
            String $r132 = $r14;
            String $r142 = $r14.packageName;
            $r14 = $r10.activityInfo;
            String $r122 = $r14;
            $r5.setComponent(new ComponentName($r142, $r14.name));
            MenuItem $r17 = add($i0, $i1, $i2, $r10.loadLabel($r7)).setIcon($r10.loadIcon($r7)).setIntent($r5);
            if ($r4 != null && $r10.specificIndex >= 0) {
                $r4[$r10.specificIndex] = $r17;
            }
        }
        return $i4;
    }

    public void removeItem(int $i0) throws  {
        removeItemAtInt(findItemIndex($i0), true);
    }

    public void removeGroup(int $i0) throws  {
        int $i2 = findGroupIndex($i0);
        if ($i2 >= 0) {
            int $i1 = this.mItems.size() - $i2;
            int $i3 = 0;
            while (true) {
                int $i4 = $i3 + 1;
                if ($i3 >= $i1 || ((MenuItemImpl) this.mItems.get($i2)).getGroupId() != $i0) {
                    onItemsChanged(true);
                } else {
                    removeItemAtInt($i2, false);
                    $i3 = $i4;
                }
            }
            onItemsChanged(true);
        }
    }

    private void removeItemAtInt(int $i0, boolean $z0) throws  {
        if ($i0 >= 0 && $i0 < this.mItems.size()) {
            this.mItems.remove($i0);
            if ($z0) {
                onItemsChanged(true);
            }
        }
    }

    public void removeItemAt(int $i0) throws  {
        removeItemAtInt($i0, true);
    }

    public void clearAll() throws  {
        this.mPreventDispatchingItemsChanged = true;
        clear();
        clearHeader();
        this.mPreventDispatchingItemsChanged = false;
        this.mItemsChangedWhileDispatchPrevented = false;
        onItemsChanged(true);
    }

    public void clear() throws  {
        if (this.mExpandedItem != null) {
            collapseItemActionView(this.mExpandedItem);
        }
        this.mItems.clear();
        onItemsChanged(true);
    }

    void setExclusiveItemChecked(MenuItem $r1) throws  {
        int $i0 = $r1.getGroupId();
        int $i1 = this.mItems.size();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            MenuItem $r4 = (MenuItemImpl) this.mItems.get($i2);
            if ($r4.getGroupId() == $i0 && $r4.isExclusiveCheckable() && $r4.isCheckable()) {
                boolean $z0;
                if ($r4 == $r1) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                $r4.setCheckedInt($z0);
            }
        }
    }

    public void setGroupCheckable(int $i0, boolean $z0, boolean $z1) throws  {
        int $i1 = this.mItems.size();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            MenuItemImpl $r3 = (MenuItemImpl) this.mItems.get($i2);
            if ($r3.getGroupId() == $i0) {
                $r3.setExclusiveCheckable($z1);
                $r3.setCheckable($z0);
            }
        }
    }

    public void setGroupVisible(int $i0, boolean $z0) throws  {
        int $i1 = this.mItems.size();
        boolean $z1 = false;
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            MenuItemImpl $r3 = (MenuItemImpl) this.mItems.get($i2);
            if ($r3.getGroupId() == $i0 && $r3.setVisibleInt($z0)) {
                $z1 = true;
            }
        }
        if ($z1) {
            onItemsChanged(true);
        }
    }

    public void setGroupEnabled(int $i0, boolean $z0) throws  {
        int $i1 = this.mItems.size();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            MenuItemImpl $r3 = (MenuItemImpl) this.mItems.get($i2);
            if ($r3.getGroupId() == $i0) {
                $r3.setEnabled($z0);
            }
        }
    }

    public boolean hasVisibleItems() throws  {
        if (this.mOverrideVisibleItems) {
            return true;
        }
        int $i0 = size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (((MenuItemImpl) this.mItems.get($i1)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public MenuItem findItem(int $i0) throws  {
        int $i1 = size();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            MenuItemImpl $r3 = (MenuItemImpl) this.mItems.get($i2);
            if ($r3.getItemId() == $i0) {
                return $r3;
            }
            if ($r3.hasSubMenu()) {
                MenuItem $r5 = $r3.getSubMenu().findItem($i0);
                if ($r5 != null) {
                    return $r5;
                }
            }
        }
        return null;
    }

    public int findItemIndex(int $i0) throws  {
        int $i1 = size();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            if (((MenuItemImpl) this.mItems.get($i2)).getItemId() == $i0) {
                return $i2;
            }
        }
        return -1;
    }

    public int findGroupIndex(int $i0) throws  {
        return findGroupIndex($i0, 0);
    }

    public int findGroupIndex(int $i0, int $i1) throws  {
        int $i2 = size();
        if ($i1 < 0) {
            $i1 = 0;
        }
        while ($i1 < $i2) {
            if (((MenuItemImpl) this.mItems.get($i1)).getGroupId() == $i0) {
                return $i1;
            }
            $i1++;
        }
        return -1;
    }

    public int size() throws  {
        return this.mItems.size();
    }

    public MenuItem getItem(int $i0) throws  {
        return (MenuItem) this.mItems.get($i0);
    }

    public boolean isShortcutKey(int $i0, KeyEvent $r1) throws  {
        return findItemWithShortcutForKey($i0, $r1) != null;
    }

    public void setQwertyMode(boolean $z0) throws  {
        this.mQwertyMode = $z0;
        onItemsChanged(false);
    }

    private static int getOrdering(int $i0) throws  {
        int $i1 = (SupportMenu.CATEGORY_MASK & $i0) >> 16;
        if ($i1 >= 0 && $i1 < sCategoryToOrder.length) {
            return (sCategoryToOrder[$i1] << 16) | (SupportMenu.USER_MASK & $i0);
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    boolean isQwertyMode() throws  {
        return this.mQwertyMode;
    }

    public void setShortcutsVisible(boolean $z0) throws  {
        if (this.mShortcutsVisible != $z0) {
            setShortcutsVisibleInner($z0);
            onItemsChanged(false);
        }
    }

    private void setShortcutsVisibleInner(boolean $z0) throws  {
        boolean $z1 = true;
        if (!($z0 && this.mResources.getConfiguration().keyboard != 1 && this.mResources.getBoolean(C0192R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent))) {
            $z1 = false;
        }
        this.mShortcutsVisible = $z1;
    }

    public boolean isShortcutsVisible() throws  {
        return this.mShortcutsVisible;
    }

    Resources getResources() throws  {
        return this.mResources;
    }

    public Context getContext() throws  {
        return this.mContext;
    }

    boolean dispatchMenuItemSelected(MenuBuilder $r1, MenuItem $r2) throws  {
        return this.mCallback != null && this.mCallback.onMenuItemSelected($r1, $r2);
    }

    public void changeMenuMode() throws  {
        if (this.mCallback != null) {
            this.mCallback.onMenuModeChange(this);
        }
    }

    private static int findInsertIndex(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/support/v7/view/menu/MenuItemImpl;", ">;I)I"}) ArrayList<MenuItemImpl> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/support/v7/view/menu/MenuItemImpl;", ">;I)I"}) int $i0) throws  {
        for (int $i1 = $r0.size() - 1; $i1 >= 0; $i1--) {
            if (((MenuItemImpl) $r0.get($i1)).getOrdering() <= $i0) {
                return $i1 + 1;
            }
        }
        return 0;
    }

    public boolean performShortcut(int $i0, KeyEvent $r1, int $i1) throws  {
        MenuItemImpl $r2 = findItemWithShortcutForKey($i0, $r1);
        boolean $z0 = false;
        if ($r2 != null) {
            $z0 = performItemAction($r2, $i1);
        }
        if (($i1 & 2) == 0) {
            return $z0;
        }
        close(true);
        return $z0;
    }

    void findItemsWithShortcutForKey(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/view/menu/MenuItemImpl;", ">;I", "Landroid/view/KeyEvent;", ")V"}) List<MenuItemImpl> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/view/menu/MenuItemImpl;", ">;I", "Landroid/view/KeyEvent;", ")V"}) int $i0, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/view/menu/MenuItemImpl;", ">;I", "Landroid/view/KeyEvent;", ")V"}) KeyEvent $r2) throws  {
        boolean $z0 = isQwertyMode();
        int $i1 = $r2.getMetaState();
        KeyData $r3 = new KeyData();
        if ($r2.getKeyData($r3) || $i0 == 67) {
            int $i2 = this.mItems.size();
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                MenuItemImpl $r6 = (MenuItemImpl) this.mItems.get($i3);
                if ($r6.hasSubMenu()) {
                    ((MenuBuilder) $r6.getSubMenu()).findItemsWithShortcutForKey($r1, $i0, $r2);
                }
                char $c4 = $z0 ? $r6.getAlphabeticShortcut() : $r6.getNumericShortcut();
                if (($i1 & 5) == 0 && $c4 != '\u0000' && (($c4 == $r3.meta[0] || $c4 == $r3.meta[2] || ($z0 && $c4 == '\b' && $i0 == 67)) && $r6.isEnabled())) {
                    $r1.add($r6);
                }
            }
        }
    }

    MenuItemImpl findItemWithShortcutForKey(int $i0, KeyEvent $r1) throws  {
        ArrayList $r2 = this.mTempShortcutItemList;
        $r2.clear();
        findItemsWithShortcutForKey($r2, $i0, $r1);
        if ($r2.isEmpty()) {
            return null;
        }
        int $i1 = $r1.getMetaState();
        KeyData $r3 = new KeyData();
        $r1.getKeyData($r3);
        int $i2 = $r2.size();
        if ($i2 == 1) {
            return (MenuItemImpl) $r2.get(0);
        }
        boolean $z0 = isQwertyMode();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            MenuItemImpl $r4 = (MenuItemImpl) $r2.get($i3);
            char $c4 = $z0 ? $r4.getAlphabeticShortcut() : $r4.getNumericShortcut();
            if (($c4 == $r3.meta[0] && ($i1 & 2) == 0) || (($c4 == $r3.meta[2] && ($i1 & 2) != 0) || ($z0 && $c4 == '\b' && $i0 == 67))) {
                return $r4;
            }
        }
        return null;
    }

    public boolean performIdentifierAction(int $i0, int $i1) throws  {
        return performItemAction(findItem($i0), $i1);
    }

    public boolean performItemAction(MenuItem $r1, int $i0) throws  {
        return performItemAction($r1, null, $i0);
    }

    public boolean performItemAction(MenuItem $r1, MenuPresenter $r2, int $i0) throws  {
        MenuItemImpl $r3 = (MenuItemImpl) $r1;
        if ($r3 == null || !$r3.isEnabled()) {
            return false;
        }
        boolean $z1;
        boolean $z0 = $r3.invoke();
        ActionProvider $r4 = $r3.getSupportActionProvider();
        if ($r4 == null || !$r4.hasSubMenu()) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        if ($r3.hasCollapsibleActionView()) {
            $z0 |= $r3.expandActionView();
            if (!$z0) {
                return $z0;
            }
            close(true);
            return $z0;
        } else if ($r3.hasSubMenu() || $z1) {
            close(false);
            if (!$r3.hasSubMenu()) {
                $r3.setSubMenu(new SubMenuBuilder(getContext(), this, $r3));
            }
            SubMenuBuilder $r5 = (SubMenuBuilder) $r3.getSubMenu();
            if ($z1) {
                $r4.onPrepareSubMenu($r5);
            }
            $z0 |= dispatchSubMenuSelected($r5, $r2);
            if ($z0) {
                return $z0;
            }
            close(true);
            return $z0;
        } else if (($i0 & 1) != 0) {
            return $z0;
        } else {
            close(true);
            return $z0;
        }
    }

    public final void close(boolean $z0) throws  {
        if (!this.mIsClosing) {
            this.mIsClosing = true;
            Iterator $r2 = this.mPresenters.iterator();
            while ($r2.hasNext()) {
                WeakReference $r4 = (WeakReference) $r2.next();
                MenuPresenter $r5 = (MenuPresenter) $r4.get();
                if ($r5 == null) {
                    this.mPresenters.remove($r4);
                } else {
                    $r5.onCloseMenu(this, $z0);
                }
            }
            this.mIsClosing = false;
        }
    }

    public void close() throws  {
        close(true);
    }

    public void onItemsChanged(boolean $z0) throws  {
        if (this.mPreventDispatchingItemsChanged) {
            this.mItemsChangedWhileDispatchPrevented = true;
            return;
        }
        if ($z0) {
            this.mIsVisibleItemsStale = true;
            this.mIsActionItemsStale = true;
        }
        dispatchPresenterUpdate($z0);
    }

    public void stopDispatchingItemsChanged() throws  {
        if (!this.mPreventDispatchingItemsChanged) {
            this.mPreventDispatchingItemsChanged = true;
            this.mItemsChangedWhileDispatchPrevented = false;
        }
    }

    public void startDispatchingItemsChanged() throws  {
        this.mPreventDispatchingItemsChanged = false;
        if (this.mItemsChangedWhileDispatchPrevented) {
            this.mItemsChangedWhileDispatchPrevented = false;
            onItemsChanged(true);
        }
    }

    void onItemVisibleChanged(MenuItemImpl item) throws  {
        this.mIsVisibleItemsStale = true;
        onItemsChanged(true);
    }

    void onItemActionRequestChanged(MenuItemImpl item) throws  {
        this.mIsActionItemsStale = true;
        onItemsChanged(true);
    }

    public ArrayList<MenuItemImpl> getVisibleItems() throws  {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems;
        }
        this.mVisibleItems.clear();
        int $i0 = this.mItems.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            MenuItemImpl $r3 = (MenuItemImpl) this.mItems.get($i1);
            if ($r3.isVisible()) {
                this.mVisibleItems.add($r3);
            }
        }
        this.mIsVisibleItemsStale = false;
        this.mIsActionItemsStale = true;
        return this.mVisibleItems;
    }

    public void flagActionItems() throws  {
        ArrayList $r1 = getVisibleItems();
        if (this.mIsActionItemsStale) {
            boolean $z0 = false;
            Iterator $r3 = this.mPresenters.iterator();
            while ($r3.hasNext()) {
                WeakReference $r5 = (WeakReference) $r3.next();
                MenuPresenter $r6 = (MenuPresenter) $r5.get();
                if ($r6 == null) {
                    this.mPresenters.remove($r5);
                } else {
                    $z0 |= $r6.flagActionItems();
                }
            }
            if ($z0) {
                this.mActionItems.clear();
                this.mNonActionItems.clear();
                int $i0 = $r1.size();
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) $r1.get($i1);
                    if (menuItemImpl.isActionButton()) {
                        this.mActionItems.add(menuItemImpl);
                    } else {
                        this.mNonActionItems.add(menuItemImpl);
                    }
                }
            } else {
                this.mActionItems.clear();
                this.mNonActionItems.clear();
                this.mNonActionItems.addAll(getVisibleItems());
            }
            this.mIsActionItemsStale = false;
        }
    }

    public ArrayList<MenuItemImpl> getActionItems() throws  {
        flagActionItems();
        return this.mActionItems;
    }

    public ArrayList<MenuItemImpl> getNonActionItems() throws  {
        flagActionItems();
        return this.mNonActionItems;
    }

    public void clearHeader() throws  {
        this.mHeaderIcon = null;
        this.mHeaderTitle = null;
        this.mHeaderView = null;
        onItemsChanged(false);
    }

    private void setHeaderInternal(int $i0, CharSequence $r1, int $i1, Drawable $r2, View $r3) throws  {
        Resources $r4 = getResources();
        if ($r3 != null) {
            this.mHeaderView = $r3;
            this.mHeaderTitle = null;
            this.mHeaderIcon = null;
        } else {
            if ($i0 > 0) {
                this.mHeaderTitle = $r4.getText($i0);
            } else if ($r1 != null) {
                this.mHeaderTitle = $r1;
            }
            if ($i1 > 0) {
                this.mHeaderIcon = ContextCompat.getDrawable(getContext(), $i1);
            } else if ($r2 != null) {
                this.mHeaderIcon = $r2;
            }
            this.mHeaderView = null;
        }
        onItemsChanged(false);
    }

    protected MenuBuilder setHeaderTitleInt(CharSequence $r1) throws  {
        setHeaderInternal(0, $r1, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(int $i0) throws  {
        setHeaderInternal($i0, null, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderIconInt(Drawable $r1) throws  {
        setHeaderInternal(0, null, 0, $r1, null);
        return this;
    }

    protected MenuBuilder setHeaderIconInt(int $i0) throws  {
        setHeaderInternal(0, null, $i0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderViewInt(View $r1) throws  {
        setHeaderInternal(0, null, 0, null, $r1);
        return this;
    }

    public CharSequence getHeaderTitle() throws  {
        return this.mHeaderTitle;
    }

    public Drawable getHeaderIcon() throws  {
        return this.mHeaderIcon;
    }

    public View getHeaderView() throws  {
        return this.mHeaderView;
    }

    public MenuBuilder getRootMenu() throws  {
        return this;
    }

    public void setCurrentMenuInfo(ContextMenuInfo $r1) throws  {
        this.mCurrentMenuInfo = $r1;
    }

    void setOptionalIconsVisible(boolean $z0) throws  {
        this.mOptionalIconsVisible = $z0;
    }

    boolean getOptionalIconsVisible() throws  {
        return this.mOptionalIconsVisible;
    }

    public boolean expandItemActionView(MenuItemImpl $r1) throws  {
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean $z0 = false;
        stopDispatchingItemsChanged();
        Iterator $r3 = this.mPresenters.iterator();
        while ($r3.hasNext()) {
            WeakReference $r5 = (WeakReference) $r3.next();
            MenuPresenter $r6 = (MenuPresenter) $r5.get();
            if ($r6 == null) {
                this.mPresenters.remove($r5);
            } else {
                boolean $z1 = $r6.expandItemActionView(this, $r1);
                $z0 = $z1;
                if ($z1) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (!$z0) {
            return $z0;
        }
        this.mExpandedItem = $r1;
        return $z0;
    }

    public boolean collapseItemActionView(MenuItemImpl $r1) throws  {
        if (this.mPresenters.isEmpty() || this.mExpandedItem != $r1) {
            return false;
        }
        boolean $z0 = false;
        stopDispatchingItemsChanged();
        Iterator $r4 = this.mPresenters.iterator();
        while ($r4.hasNext()) {
            WeakReference $r6 = (WeakReference) $r4.next();
            MenuPresenter $r7 = (MenuPresenter) $r6.get();
            if ($r7 == null) {
                this.mPresenters.remove($r6);
            } else {
                boolean $z1 = $r7.collapseItemActionView(this, $r1);
                $z0 = $z1;
                if ($z1) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (!$z0) {
            return $z0;
        }
        this.mExpandedItem = null;
        return $z0;
    }

    public MenuItemImpl getExpandedItem() throws  {
        return this.mExpandedItem;
    }

    public void setOverrideVisibleItems(boolean $z0) throws  {
        this.mOverrideVisibleItems = $z0;
    }
}
