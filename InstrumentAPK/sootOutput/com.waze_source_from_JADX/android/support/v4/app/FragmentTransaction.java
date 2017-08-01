package android.support.v4.app;

import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;

public abstract class FragmentTransaction {
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;

    public abstract FragmentTransaction add(@IdRes int i, Fragment fragment) throws ;

    public abstract FragmentTransaction add(@IdRes int i, Fragment fragment, @Nullable String str) throws ;

    public abstract FragmentTransaction add(Fragment fragment, String str) throws ;

    public abstract FragmentTransaction addSharedElement(View view, String str) throws ;

    public abstract FragmentTransaction addToBackStack(@Nullable String str) throws ;

    public abstract FragmentTransaction attach(Fragment fragment) throws ;

    public abstract int commit() throws ;

    public abstract int commitAllowingStateLoss() throws ;

    public abstract FragmentTransaction detach(Fragment fragment) throws ;

    public abstract FragmentTransaction disallowAddToBackStack() throws ;

    public abstract FragmentTransaction hide(Fragment fragment) throws ;

    public abstract boolean isAddToBackStackAllowed() throws ;

    public abstract boolean isEmpty() throws ;

    public abstract FragmentTransaction remove(Fragment fragment) throws ;

    public abstract FragmentTransaction replace(@IdRes int i, Fragment fragment) throws ;

    public abstract FragmentTransaction replace(@IdRes int i, Fragment fragment, @Nullable String str) throws ;

    public abstract FragmentTransaction setBreadCrumbShortTitle(@StringRes int i) throws ;

    public abstract FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) throws ;

    public abstract FragmentTransaction setBreadCrumbTitle(@StringRes int i) throws ;

    public abstract FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) throws ;

    public abstract FragmentTransaction setCustomAnimations(@AnimRes int i, @AnimRes int i2) throws ;

    public abstract FragmentTransaction setCustomAnimations(@AnimRes int i, @AnimRes int i2, @AnimRes int i3, @AnimRes int i4) throws ;

    public abstract FragmentTransaction setTransition(int i) throws ;

    public abstract FragmentTransaction setTransitionStyle(@StyleRes int i) throws ;

    public abstract FragmentTransaction show(Fragment fragment) throws ;
}
