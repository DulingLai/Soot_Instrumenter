package android.support.v4.view;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public abstract class ActionProvider {
    private static final String TAG = "ActionProvider(support)";
    private final Context mContext;
    private SubUiVisibilityListener mSubUiVisibilityListener;
    private VisibilityListener mVisibilityListener;

    public interface SubUiVisibilityListener {
        void onSubUiVisibilityChanged(boolean z) throws ;
    }

    public interface VisibilityListener {
        void onActionProviderVisibilityChanged(boolean z) throws ;
    }

    public boolean hasSubMenu() throws  {
        return false;
    }

    public boolean isVisible() throws  {
        return true;
    }

    public abstract View onCreateActionView() throws ;

    public boolean onPerformDefaultAction() throws  {
        return false;
    }

    public boolean overridesItemVisibility() throws  {
        return false;
    }

    public ActionProvider(Context $r1) throws  {
        this.mContext = $r1;
    }

    public Context getContext() throws  {
        return this.mContext;
    }

    public View onCreateActionView(MenuItem forItem) throws  {
        return onCreateActionView();
    }

    public void refreshVisibility() throws  {
        if (this.mVisibilityListener != null && overridesItemVisibility()) {
            this.mVisibilityListener.onActionProviderVisibilityChanged(isVisible());
        }
    }

    public void onPrepareSubMenu(SubMenu subMenu) throws  {
    }

    public void subUiVisibilityChanged(boolean $z0) throws  {
        if (this.mSubUiVisibilityListener != null) {
            this.mSubUiVisibilityListener.onSubUiVisibilityChanged($z0);
        }
    }

    public void setSubUiVisibilityListener(SubUiVisibilityListener $r1) throws  {
        this.mSubUiVisibilityListener = $r1;
    }

    public void setVisibilityListener(VisibilityListener $r1) throws  {
        if (!(this.mVisibilityListener == null || $r1 == null)) {
            Log.w(TAG, "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.mVisibilityListener = $r1;
    }

    public void reset() throws  {
        this.mVisibilityListener = null;
        this.mSubUiVisibilityListener = null;
    }
}
