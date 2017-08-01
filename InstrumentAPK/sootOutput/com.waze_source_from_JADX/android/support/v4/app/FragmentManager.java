package android.support.v4.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment.SavedState;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;

    public interface BackStackEntry {
        CharSequence getBreadCrumbShortTitle() throws ;

        @StringRes
        int getBreadCrumbShortTitleRes() throws ;

        CharSequence getBreadCrumbTitle() throws ;

        @StringRes
        int getBreadCrumbTitleRes() throws ;

        int getId() throws ;

        String getName() throws ;
    }

    public interface OnBackStackChangedListener {
        void onBackStackChanged() throws ;
    }

    public abstract void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) throws ;

    public abstract FragmentTransaction beginTransaction() throws ;

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws ;

    public abstract boolean executePendingTransactions() throws ;

    public abstract Fragment findFragmentById(@IdRes int i) throws ;

    public abstract Fragment findFragmentByTag(String str) throws ;

    public abstract BackStackEntry getBackStackEntryAt(int i) throws ;

    public abstract int getBackStackEntryCount() throws ;

    public abstract Fragment getFragment(Bundle bundle, String str) throws ;

    public abstract List<Fragment> getFragments() throws ;

    public abstract boolean isDestroyed() throws ;

    public abstract void popBackStack() throws ;

    public abstract void popBackStack(int i, int i2) throws ;

    public abstract void popBackStack(String str, int i) throws ;

    public abstract boolean popBackStackImmediate() throws ;

    public abstract boolean popBackStackImmediate(int i, int i2) throws ;

    public abstract boolean popBackStackImmediate(String str, int i) throws ;

    public abstract void putFragment(Bundle bundle, String str, Fragment fragment) throws ;

    public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) throws ;

    public abstract SavedState saveFragmentInstanceState(Fragment fragment) throws ;

    @Deprecated
    public FragmentTransaction openTransaction() throws  {
        return beginTransaction();
    }

    public static void enableDebugLogging(boolean $z0) throws  {
        FragmentManagerImpl.DEBUG = $z0;
    }
}
