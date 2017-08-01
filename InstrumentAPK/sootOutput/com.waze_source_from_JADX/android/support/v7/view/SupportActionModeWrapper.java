package android.support.v7.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

@TargetApi(11)
public class SupportActionModeWrapper extends ActionMode {
    final Context mContext;
    final ActionMode mWrappedObject;

    public static class CallbackWrapper implements Callback {
        final ArrayList<SupportActionModeWrapper> mActionModes = new ArrayList();
        final Context mContext;
        final SimpleArrayMap<Menu, Menu> mMenus = new SimpleArrayMap();
        final ActionMode.Callback mWrappedCallback;

        public CallbackWrapper(Context $r1, ActionMode.Callback $r2) throws  {
            this.mContext = $r1;
            this.mWrappedCallback = $r2;
        }

        public boolean onCreateActionMode(ActionMode $r1, Menu $r2) throws  {
            return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper($r1), getMenuWrapper($r2));
        }

        public boolean onPrepareActionMode(ActionMode $r1, Menu $r2) throws  {
            return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper($r1), getMenuWrapper($r2));
        }

        public boolean onActionItemClicked(ActionMode $r1, MenuItem $r2) throws  {
            return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper($r1), MenuWrapperFactory.wrapSupportMenuItem(this.mContext, (SupportMenuItem) $r2));
        }

        public void onDestroyActionMode(ActionMode $r1) throws  {
            this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper($r1));
        }

        private Menu getMenuWrapper(Menu $r1) throws  {
            Menu $r5 = (Menu) this.mMenus.get($r1);
            if ($r5 != null) {
                return $r5;
            }
            $r5 = MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu) $r1);
            this.mMenus.put($r1, $r5);
            return $r5;
        }

        public ActionMode getActionModeWrapper(ActionMode $r1) throws  {
            SupportActionModeWrapper $r4;
            int $i1 = this.mActionModes.size();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                $r4 = (SupportActionModeWrapper) this.mActionModes.get($i0);
                if ($r4 != null && $r4.mWrappedObject == $r1) {
                    return $r4;
                }
            }
            $r4 = new SupportActionModeWrapper(this.mContext, $r1);
            this.mActionModes.add($r4);
            return $r4;
        }
    }

    public SupportActionModeWrapper(Context $r1, ActionMode $r2) throws  {
        this.mContext = $r1;
        this.mWrappedObject = $r2;
    }

    public Object getTag() throws  {
        return this.mWrappedObject.getTag();
    }

    public void setTag(Object $r1) throws  {
        this.mWrappedObject.setTag($r1);
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mWrappedObject.setTitle($r1);
    }

    public void setSubtitle(CharSequence $r1) throws  {
        this.mWrappedObject.setSubtitle($r1);
    }

    public void invalidate() throws  {
        this.mWrappedObject.invalidate();
    }

    public void finish() throws  {
        this.mWrappedObject.finish();
    }

    public Menu getMenu() throws  {
        return MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu) this.mWrappedObject.getMenu());
    }

    public CharSequence getTitle() throws  {
        return this.mWrappedObject.getTitle();
    }

    public void setTitle(int $i0) throws  {
        this.mWrappedObject.setTitle($i0);
    }

    public CharSequence getSubtitle() throws  {
        return this.mWrappedObject.getSubtitle();
    }

    public void setSubtitle(int $i0) throws  {
        this.mWrappedObject.setSubtitle($i0);
    }

    public View getCustomView() throws  {
        return this.mWrappedObject.getCustomView();
    }

    public void setCustomView(View $r1) throws  {
        this.mWrappedObject.setCustomView($r1);
    }

    public MenuInflater getMenuInflater() throws  {
        return this.mWrappedObject.getMenuInflater();
    }

    public boolean getTitleOptionalHint() throws  {
        return this.mWrappedObject.getTitleOptionalHint();
    }

    public void setTitleOptionalHint(boolean $z0) throws  {
        this.mWrappedObject.setTitleOptionalHint($z0);
    }

    public boolean isTitleOptional() throws  {
        return this.mWrappedObject.isTitleOptional();
    }
}
