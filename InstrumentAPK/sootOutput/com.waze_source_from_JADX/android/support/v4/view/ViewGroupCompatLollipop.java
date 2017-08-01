package android.support.v4.view;

import android.view.ViewGroup;

class ViewGroupCompatLollipop {
    ViewGroupCompatLollipop() throws  {
    }

    public static void setTransitionGroup(ViewGroup $r0, boolean $z0) throws  {
        $r0.setTransitionGroup($z0);
    }

    public static boolean isTransitionGroup(ViewGroup $r0) throws  {
        return $r0.isTransitionGroup();
    }

    public static int getNestedScrollAxes(ViewGroup $r0) throws  {
        return $r0.getNestedScrollAxes();
    }
}
