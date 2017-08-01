package android.support.v4.view;

import android.view.MenuItem;
import android.view.View;

class MenuItemCompatHoneycomb {
    MenuItemCompatHoneycomb() throws  {
    }

    public static void setShowAsAction(MenuItem $r0, int $i0) throws  {
        $r0.setShowAsAction($i0);
    }

    public static MenuItem setActionView(MenuItem $r0, View $r1) throws  {
        return $r0.setActionView($r1);
    }

    public static MenuItem setActionView(MenuItem $r0, int $i0) throws  {
        return $r0.setActionView($i0);
    }

    public static View getActionView(MenuItem $r0) throws  {
        return $r0.getActionView();
    }
}
