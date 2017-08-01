package android.support.v4.view;

import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenuItem;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public final class MenuItemCompat {
    static final MenuVersionImpl IMPL;
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
    private static final String TAG = "MenuItemCompat";

    interface MenuVersionImpl {
        boolean collapseActionView(MenuItem menuItem) throws ;

        boolean expandActionView(MenuItem menuItem) throws ;

        View getActionView(MenuItem menuItem) throws ;

        boolean isActionViewExpanded(MenuItem menuItem) throws ;

        MenuItem setActionView(MenuItem menuItem, int i) throws ;

        MenuItem setActionView(MenuItem menuItem, View view) throws ;

        MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) throws ;

        void setShowAsAction(MenuItem menuItem, int i) throws ;
    }

    static class BaseMenuVersionImpl implements MenuVersionImpl {
        public boolean collapseActionView(MenuItem item) throws  {
            return false;
        }

        public boolean expandActionView(MenuItem item) throws  {
            return false;
        }

        public View getActionView(MenuItem item) throws  {
            return null;
        }

        public boolean isActionViewExpanded(MenuItem item) throws  {
            return false;
        }

        BaseMenuVersionImpl() throws  {
        }

        public void setShowAsAction(MenuItem item, int actionEnum) throws  {
        }

        public MenuItem setActionView(MenuItem $r1, View view) throws  {
            return $r1;
        }

        public MenuItem setActionView(MenuItem $r1, int resId) throws  {
            return $r1;
        }

        public MenuItem setOnActionExpandListener(MenuItem $r1, OnActionExpandListener listener) throws  {
            return $r1;
        }
    }

    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        public boolean collapseActionView(MenuItem item) throws  {
            return false;
        }

        public boolean expandActionView(MenuItem item) throws  {
            return false;
        }

        public boolean isActionViewExpanded(MenuItem item) throws  {
            return false;
        }

        HoneycombMenuVersionImpl() throws  {
        }

        public void setShowAsAction(MenuItem $r1, int $i0) throws  {
            MenuItemCompatHoneycomb.setShowAsAction($r1, $i0);
        }

        public MenuItem setActionView(MenuItem $r1, View $r2) throws  {
            return MenuItemCompatHoneycomb.setActionView($r1, $r2);
        }

        public MenuItem setActionView(MenuItem $r1, int $i0) throws  {
            return MenuItemCompatHoneycomb.setActionView($r1, $i0);
        }

        public View getActionView(MenuItem $r1) throws  {
            return MenuItemCompatHoneycomb.getActionView($r1);
        }

        public MenuItem setOnActionExpandListener(MenuItem $r1, OnActionExpandListener listener) throws  {
            return $r1;
        }
    }

    static class IcsMenuVersionImpl extends HoneycombMenuVersionImpl {
        IcsMenuVersionImpl() throws  {
        }

        public boolean expandActionView(MenuItem $r1) throws  {
            return MenuItemCompatIcs.expandActionView($r1);
        }

        public boolean collapseActionView(MenuItem $r1) throws  {
            return MenuItemCompatIcs.collapseActionView($r1);
        }

        public boolean isActionViewExpanded(MenuItem $r1) throws  {
            return MenuItemCompatIcs.isActionViewExpanded($r1);
        }

        public MenuItem setOnActionExpandListener(MenuItem $r1, final OnActionExpandListener $r2) throws  {
            if ($r2 == null) {
                return MenuItemCompatIcs.setOnActionExpandListener($r1, null);
            }
            return MenuItemCompatIcs.setOnActionExpandListener($r1, new SupportActionExpandProxy() {
                public boolean onMenuItemActionExpand(MenuItem $r1) throws  {
                    return $r2.onMenuItemActionExpand($r1);
                }

                public boolean onMenuItemActionCollapse(MenuItem $r1) throws  {
                    return $r2.onMenuItemActionCollapse($r1);
                }
            });
        }
    }

    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(MenuItem menuItem) throws ;

        boolean onMenuItemActionExpand(MenuItem menuItem) throws ;
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 14) {
            IMPL = new IcsMenuVersionImpl();
        } else if ($i0 >= 11) {
            IMPL = new HoneycombMenuVersionImpl();
        } else {
            IMPL = new BaseMenuVersionImpl();
        }
    }

    public static void setShowAsAction(MenuItem $r0, int $i0) throws  {
        if ($r0 instanceof SupportMenuItem) {
            ((SupportMenuItem) $r0).setShowAsAction($i0);
        } else {
            IMPL.setShowAsAction($r0, $i0);
        }
    }

    public static MenuItem setActionView(MenuItem $r1, View $r0) throws  {
        if ($r1 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r1).setActionView($r0);
        }
        return IMPL.setActionView($r1, $r0);
    }

    public static MenuItem setActionView(MenuItem $r0, int $i0) throws  {
        if ($r0 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r0).setActionView($i0);
        }
        return IMPL.setActionView($r0, $i0);
    }

    public static View getActionView(MenuItem $r0) throws  {
        if ($r0 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r0).getActionView();
        }
        return IMPL.getActionView($r0);
    }

    public static MenuItem setActionProvider(MenuItem $r2, ActionProvider $r0) throws  {
        if ($r2 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r2).setSupportActionProvider($r0);
        }
        Log.w(TAG, "setActionProvider: item does not implement SupportMenuItem; ignoring");
        return $r2;
    }

    public static ActionProvider getActionProvider(MenuItem $r1) throws  {
        if ($r1 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r1).getSupportActionProvider();
        }
        Log.w(TAG, "getActionProvider: item does not implement SupportMenuItem; returning null");
        return null;
    }

    public static boolean expandActionView(MenuItem $r0) throws  {
        if ($r0 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r0).expandActionView();
        }
        return IMPL.expandActionView($r0);
    }

    public static boolean collapseActionView(MenuItem $r0) throws  {
        if ($r0 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r0).collapseActionView();
        }
        return IMPL.collapseActionView($r0);
    }

    public static boolean isActionViewExpanded(MenuItem $r0) throws  {
        if ($r0 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r0).isActionViewExpanded();
        }
        return IMPL.isActionViewExpanded($r0);
    }

    public static MenuItem setOnActionExpandListener(MenuItem $r1, OnActionExpandListener $r0) throws  {
        if ($r1 instanceof SupportMenuItem) {
            return ((SupportMenuItem) $r1).setSupportOnActionExpandListener($r0);
        }
        return IMPL.setOnActionExpandListener($r1, $r0);
    }

    private MenuItemCompat() throws  {
    }
}
