package android.support.v4.view;

import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;

class MenuItemCompatIcs {

    interface SupportActionExpandProxy {
        boolean onMenuItemActionCollapse(MenuItem menuItem) throws ;

        boolean onMenuItemActionExpand(MenuItem menuItem) throws ;
    }

    static class OnActionExpandListenerWrapper implements OnActionExpandListener {
        private SupportActionExpandProxy mWrapped;

        public OnActionExpandListenerWrapper(SupportActionExpandProxy $r1) throws  {
            this.mWrapped = $r1;
        }

        public boolean onMenuItemActionExpand(MenuItem $r1) throws  {
            return this.mWrapped.onMenuItemActionExpand($r1);
        }

        public boolean onMenuItemActionCollapse(MenuItem $r1) throws  {
            return this.mWrapped.onMenuItemActionCollapse($r1);
        }
    }

    MenuItemCompatIcs() throws  {
    }

    public static boolean expandActionView(MenuItem $r0) throws  {
        return $r0.expandActionView();
    }

    public static boolean collapseActionView(MenuItem $r0) throws  {
        return $r0.collapseActionView();
    }

    public static boolean isActionViewExpanded(MenuItem $r0) throws  {
        return $r0.isActionViewExpanded();
    }

    public static MenuItem setOnActionExpandListener(MenuItem $r0, SupportActionExpandProxy $r1) throws  {
        return $r0.setOnActionExpandListener(new OnActionExpandListenerWrapper($r1));
    }
}
