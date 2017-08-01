package android.support.v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.view.ActionProvider.VisibilityListener;
import android.view.MenuItem;
import android.view.View;

@TargetApi(16)
class MenuItemWrapperJB extends MenuItemWrapperICS {

    class ActionProviderWrapperJB extends ActionProviderWrapper implements VisibilityListener {
        ActionProvider.VisibilityListener mListener;

        public ActionProviderWrapperJB(Context $r2, android.view.ActionProvider $r3) throws  {
            super($r2, $r3);
        }

        public View onCreateActionView(MenuItem $r1) throws  {
            return this.mInner.onCreateActionView($r1);
        }

        public boolean overridesItemVisibility() throws  {
            return this.mInner.overridesItemVisibility();
        }

        public boolean isVisible() throws  {
            return this.mInner.isVisible();
        }

        public void refreshVisibility() throws  {
            this.mInner.refreshVisibility();
        }

        public void setVisibilityListener(ActionProvider.VisibilityListener $r0) throws  {
            $r2.mListener = $r0;
            android.view.ActionProvider $r1 = $r2.mInner;
            if ($r0 == null) {
                $r2 = null;
            }
            $r1.setVisibilityListener($r2);
        }

        public void onActionProviderVisibilityChanged(boolean $z0) throws  {
            if (this.mListener != null) {
                this.mListener.onActionProviderVisibilityChanged($z0);
            }
        }
    }

    MenuItemWrapperJB(Context $r1, SupportMenuItem $r2) throws  {
        super($r1, $r2);
    }

    ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider $r1) throws  {
        return new ActionProviderWrapperJB(this.mContext, $r1);
    }
}
