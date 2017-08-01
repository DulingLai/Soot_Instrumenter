package android.support.v7.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class ActionMode {
    private Object mTag;
    private boolean mTitleOptionalHint;

    public interface Callback {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) throws ;

        boolean onCreateActionMode(ActionMode actionMode, Menu menu) throws ;

        void onDestroyActionMode(ActionMode actionMode) throws ;

        boolean onPrepareActionMode(ActionMode actionMode, Menu menu) throws ;
    }

    public abstract void finish() throws ;

    public abstract View getCustomView() throws ;

    public abstract Menu getMenu() throws ;

    public abstract MenuInflater getMenuInflater() throws ;

    public abstract CharSequence getSubtitle() throws ;

    public abstract CharSequence getTitle() throws ;

    public abstract void invalidate() throws ;

    public boolean isTitleOptional() throws  {
        return false;
    }

    public boolean isUiFocusable() throws  {
        return true;
    }

    public abstract void setCustomView(View view) throws ;

    public abstract void setSubtitle(int i) throws ;

    public abstract void setSubtitle(CharSequence charSequence) throws ;

    public abstract void setTitle(int i) throws ;

    public abstract void setTitle(CharSequence charSequence) throws ;

    public void setTag(Object $r1) throws  {
        this.mTag = $r1;
    }

    public Object getTag() throws  {
        return this.mTag;
    }

    public void setTitleOptionalHint(boolean $z0) throws  {
        this.mTitleOptionalHint = $z0;
    }

    public boolean getTitleOptionalHint() throws  {
        return this.mTitleOptionalHint;
    }
}
