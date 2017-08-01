package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SpinnerAdapter;
import dalvik.annotation.Signature;

public interface DecorToolbar {
    void animateToVisibility(int i) throws ;

    boolean canShowOverflowMenu() throws ;

    void collapseActionView() throws ;

    void dismissPopupMenus() throws ;

    Context getContext() throws ;

    View getCustomView() throws ;

    int getDisplayOptions() throws ;

    int getDropdownItemCount() throws ;

    int getDropdownSelectedPosition() throws ;

    int getHeight() throws ;

    Menu getMenu() throws ;

    int getNavigationMode() throws ;

    CharSequence getSubtitle() throws ;

    CharSequence getTitle() throws ;

    ViewGroup getViewGroup() throws ;

    int getVisibility() throws ;

    boolean hasEmbeddedTabs() throws ;

    boolean hasExpandedActionView() throws ;

    boolean hasIcon() throws ;

    boolean hasLogo() throws ;

    boolean hideOverflowMenu() throws ;

    void initIndeterminateProgress() throws ;

    void initProgress() throws ;

    boolean isOverflowMenuShowPending() throws ;

    boolean isOverflowMenuShowing() throws ;

    boolean isTitleTruncated() throws ;

    void restoreHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> sparseArray) throws ;

    void saveHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> sparseArray) throws ;

    void setBackgroundDrawable(Drawable drawable) throws ;

    void setCollapsible(boolean z) throws ;

    void setCustomView(View view) throws ;

    void setDefaultNavigationContentDescription(int i) throws ;

    void setDefaultNavigationIcon(Drawable drawable) throws ;

    void setDisplayOptions(int i) throws ;

    void setDropdownParams(SpinnerAdapter spinnerAdapter, OnItemSelectedListener onItemSelectedListener) throws ;

    void setDropdownSelectedPosition(int i) throws ;

    void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) throws ;

    void setHomeButtonEnabled(boolean z) throws ;

    void setIcon(int i) throws ;

    void setIcon(Drawable drawable) throws ;

    void setLogo(int i) throws ;

    void setLogo(Drawable drawable) throws ;

    void setMenu(Menu menu, Callback callback) throws ;

    void setMenuCallbacks(Callback callback, MenuBuilder.Callback callback2) throws ;

    void setMenuPrepared() throws ;

    void setNavigationContentDescription(int i) throws ;

    void setNavigationContentDescription(CharSequence charSequence) throws ;

    void setNavigationIcon(int i) throws ;

    void setNavigationIcon(Drawable drawable) throws ;

    void setNavigationMode(int i) throws ;

    void setSubtitle(CharSequence charSequence) throws ;

    void setTitle(CharSequence charSequence) throws ;

    void setVisibility(int i) throws ;

    void setWindowCallback(Window.Callback callback) throws ;

    void setWindowTitle(CharSequence charSequence) throws ;

    ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) throws ;

    boolean showOverflowMenu() throws ;
}
