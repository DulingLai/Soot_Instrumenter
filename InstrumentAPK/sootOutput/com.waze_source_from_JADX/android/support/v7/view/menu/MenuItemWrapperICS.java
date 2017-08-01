package android.support.v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.CollapsibleActionView;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

@TargetApi(14)
public class MenuItemWrapperICS extends BaseMenuWrapper<SupportMenuItem> implements MenuItem {
    static final String LOG_TAG = "MenuItemWrapper";
    private Method mSetExclusiveCheckableMethod;

    class ActionProviderWrapper extends ActionProvider {
        final android.view.ActionProvider mInner;

        public ActionProviderWrapper(Context $r2, android.view.ActionProvider $r3) throws  {
            super($r2);
            this.mInner = $r3;
        }

        public View onCreateActionView() throws  {
            return this.mInner.onCreateActionView();
        }

        public boolean onPerformDefaultAction() throws  {
            return this.mInner.onPerformDefaultAction();
        }

        public boolean hasSubMenu() throws  {
            return this.mInner.hasSubMenu();
        }

        public void onPrepareSubMenu(SubMenu $r1) throws  {
            this.mInner.onPrepareSubMenu(MenuItemWrapperICS.this.getSubMenuWrapper($r1));
        }
    }

    static class CollapsibleActionViewWrapper extends FrameLayout implements CollapsibleActionView {
        final android.view.CollapsibleActionView mWrappedView;

        CollapsibleActionViewWrapper(View $r1) throws  {
            super($r1.getContext());
            this.mWrappedView = (android.view.CollapsibleActionView) $r1;
            addView($r1);
        }

        public void onActionViewExpanded() throws  {
            this.mWrappedView.onActionViewExpanded();
        }

        public void onActionViewCollapsed() throws  {
            this.mWrappedView.onActionViewCollapsed();
        }

        View getWrappedView() throws  {
            return (View) this.mWrappedView;
        }
    }

    private class OnActionExpandListenerWrapper extends BaseWrapper<OnActionExpandListener> implements MenuItemCompat.OnActionExpandListener {
        OnActionExpandListenerWrapper(OnActionExpandListener $r2) throws  {
            super($r2);
        }

        public boolean onMenuItemActionExpand(MenuItem $r1) throws  {
            return ((OnActionExpandListener) this.mWrappedObject).onMenuItemActionExpand(MenuItemWrapperICS.this.getMenuItemWrapper($r1));
        }

        public boolean onMenuItemActionCollapse(MenuItem $r1) throws  {
            return ((OnActionExpandListener) this.mWrappedObject).onMenuItemActionCollapse(MenuItemWrapperICS.this.getMenuItemWrapper($r1));
        }
    }

    private class OnMenuItemClickListenerWrapper extends BaseWrapper<OnMenuItemClickListener> implements OnMenuItemClickListener {
        OnMenuItemClickListenerWrapper(OnMenuItemClickListener $r2) throws  {
            super($r2);
        }

        public boolean onMenuItemClick(MenuItem $r1) throws  {
            return ((OnMenuItemClickListener) this.mWrappedObject).onMenuItemClick(MenuItemWrapperICS.this.getMenuItemWrapper($r1));
        }
    }

    MenuItemWrapperICS(Context $r1, SupportMenuItem $r2) throws  {
        super($r1, $r2);
    }

    public int getItemId() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getItemId();
    }

    public int getGroupId() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getGroupId();
    }

    public int getOrder() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getOrder();
    }

    public MenuItem setTitle(CharSequence $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setTitle($r1);
        return this;
    }

    public MenuItem setTitle(int $i0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setTitle($i0);
        return this;
    }

    public CharSequence getTitle() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getTitle();
    }

    public MenuItem setTitleCondensed(CharSequence $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setTitleCondensed($r1);
        return this;
    }

    public CharSequence getTitleCondensed() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getTitleCondensed();
    }

    public MenuItem setIcon(Drawable $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setIcon($r1);
        return this;
    }

    public MenuItem setIcon(int $i0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setIcon($i0);
        return this;
    }

    public Drawable getIcon() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getIcon();
    }

    public MenuItem setIntent(Intent $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setIntent($r1);
        return this;
    }

    public Intent getIntent() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getIntent();
    }

    public MenuItem setShortcut(char $c0, char $c1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setShortcut($c0, $c1);
        return this;
    }

    public MenuItem setNumericShortcut(char $c0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setNumericShortcut($c0);
        return this;
    }

    public char getNumericShortcut() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getNumericShortcut();
    }

    public MenuItem setAlphabeticShortcut(char $c0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setAlphabeticShortcut($c0);
        return this;
    }

    public char getAlphabeticShortcut() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getAlphabeticShortcut();
    }

    public MenuItem setCheckable(boolean $z0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setCheckable($z0);
        return this;
    }

    public boolean isCheckable() throws  {
        return ((SupportMenuItem) this.mWrappedObject).isCheckable();
    }

    public MenuItem setChecked(boolean $z0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setChecked($z0);
        return this;
    }

    public boolean isChecked() throws  {
        return ((SupportMenuItem) this.mWrappedObject).isChecked();
    }

    public MenuItem setVisible(boolean $z0) throws  {
        return ((SupportMenuItem) this.mWrappedObject).setVisible($z0);
    }

    public boolean isVisible() throws  {
        return ((SupportMenuItem) this.mWrappedObject).isVisible();
    }

    public MenuItem setEnabled(boolean $z0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setEnabled($z0);
        return this;
    }

    public boolean isEnabled() throws  {
        return ((SupportMenuItem) this.mWrappedObject).isEnabled();
    }

    public boolean hasSubMenu() throws  {
        return ((SupportMenuItem) this.mWrappedObject).hasSubMenu();
    }

    public SubMenu getSubMenu() throws  {
        return getSubMenuWrapper(((SupportMenuItem) this.mWrappedObject).getSubMenu());
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setOnMenuItemClickListener($r1 != null ? new OnMenuItemClickListenerWrapper($r1) : null);
        return this;
    }

    public ContextMenuInfo getMenuInfo() throws  {
        return ((SupportMenuItem) this.mWrappedObject).getMenuInfo();
    }

    public void setShowAsAction(int $i0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setShowAsAction($i0);
    }

    public MenuItem setShowAsActionFlags(int $i0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setShowAsActionFlags($i0);
        return this;
    }

    public MenuItem setActionView(View $r2) throws  {
        if ($r2 instanceof android.view.CollapsibleActionView) {
            $r2 = new CollapsibleActionViewWrapper($r2);
        }
        ((SupportMenuItem) this.mWrappedObject).setActionView($r2);
        return this;
    }

    public MenuItem setActionView(int $i0) throws  {
        ((SupportMenuItem) this.mWrappedObject).setActionView($i0);
        View $r2 = ((SupportMenuItem) this.mWrappedObject).getActionView();
        if (!($r2 instanceof android.view.CollapsibleActionView)) {
            return this;
        }
        ((SupportMenuItem) this.mWrappedObject).setActionView(new CollapsibleActionViewWrapper($r2));
        return this;
    }

    public View getActionView() throws  {
        View $r3 = ((SupportMenuItem) this.mWrappedObject).getActionView();
        if ($r3 instanceof CollapsibleActionViewWrapper) {
            return ((CollapsibleActionViewWrapper) $r3).getWrappedView();
        }
        return $r3;
    }

    public MenuItem setActionProvider(android.view.ActionProvider $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setSupportActionProvider($r1 != null ? createActionProviderWrapper($r1) : null);
        return this;
    }

    public android.view.ActionProvider getActionProvider() throws  {
        ActionProvider $r1 = ((SupportMenuItem) this.mWrappedObject).getSupportActionProvider();
        if ($r1 instanceof ActionProviderWrapper) {
            return ((ActionProviderWrapper) $r1).mInner;
        }
        return null;
    }

    public boolean expandActionView() throws  {
        return ((SupportMenuItem) this.mWrappedObject).expandActionView();
    }

    public boolean collapseActionView() throws  {
        return ((SupportMenuItem) this.mWrappedObject).collapseActionView();
    }

    public boolean isActionViewExpanded() throws  {
        return ((SupportMenuItem) this.mWrappedObject).isActionViewExpanded();
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener $r1) throws  {
        ((SupportMenuItem) this.mWrappedObject).setSupportOnActionExpandListener($r1 != null ? new OnActionExpandListenerWrapper($r1) : null);
        return this;
    }

    public void setExclusiveCheckable(boolean $z0) throws  {
        try {
            if (this.mSetExclusiveCheckableMethod == null) {
                this.mSetExclusiveCheckableMethod = ((SupportMenuItem) this.mWrappedObject).getClass().getDeclaredMethod("setExclusiveCheckable", new Class[]{Boolean.TYPE});
            }
            this.mSetExclusiveCheckableMethod.invoke(this.mWrappedObject, new Object[]{Boolean.valueOf($z0)});
        } catch (Exception $r1) {
            Log.w(LOG_TAG, "Error while calling setExclusiveCheckable", $r1);
        }
    }

    ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider $r1) throws  {
        return new ActionProviderWrapper(this.mContext, $r1);
    }
}
