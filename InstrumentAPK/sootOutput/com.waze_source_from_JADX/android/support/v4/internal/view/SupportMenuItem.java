package android.support.v4.internal.view;

import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.view.MenuItem;
import android.view.View;

public interface SupportMenuItem extends MenuItem {
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    boolean collapseActionView() throws ;

    boolean expandActionView() throws ;

    View getActionView() throws ;

    ActionProvider getSupportActionProvider() throws ;

    boolean isActionViewExpanded() throws ;

    MenuItem setActionView(int i) throws ;

    MenuItem setActionView(View view) throws ;

    void setShowAsAction(int i) throws ;

    MenuItem setShowAsActionFlags(int i) throws ;

    SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) throws ;

    SupportMenuItem setSupportOnActionExpandListener(OnActionExpandListener onActionExpandListener) throws ;
}
