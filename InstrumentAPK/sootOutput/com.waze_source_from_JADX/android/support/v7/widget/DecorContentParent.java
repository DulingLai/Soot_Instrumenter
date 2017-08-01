package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.util.SparseArray;
import android.view.Menu;
import android.view.Window;
import dalvik.annotation.Signature;

public interface DecorContentParent {
    boolean canShowOverflowMenu() throws ;

    void dismissPopups() throws ;

    CharSequence getTitle() throws ;

    boolean hasIcon() throws ;

    boolean hasLogo() throws ;

    boolean hideOverflowMenu() throws ;

    void initFeature(int i) throws ;

    boolean isOverflowMenuShowPending() throws ;

    boolean isOverflowMenuShowing() throws ;

    void restoreToolbarHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> sparseArray) throws ;

    void saveToolbarHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> sparseArray) throws ;

    void setIcon(int i) throws ;

    void setIcon(Drawable drawable) throws ;

    void setLogo(int i) throws ;

    void setMenu(Menu menu, Callback callback) throws ;

    void setMenuPrepared() throws ;

    void setUiOptions(int i) throws ;

    void setWindowCallback(Window.Callback callback) throws ;

    void setWindowTitle(CharSequence charSequence) throws ;

    boolean showOverflowMenu() throws ;
}
