package android.support.v7.view;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window.Callback;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;

public class WindowCallbackWrapper implements Callback {
    final Callback mWrapped;

    public WindowCallbackWrapper(Callback $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("Window callback may not be null");
        }
        this.mWrapped = $r1;
    }

    public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        return this.mWrapped.dispatchKeyEvent($r1);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent $r1) throws  {
        return this.mWrapped.dispatchKeyShortcutEvent($r1);
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        return this.mWrapped.dispatchTouchEvent($r1);
    }

    public boolean dispatchTrackballEvent(MotionEvent $r1) throws  {
        return this.mWrapped.dispatchTrackballEvent($r1);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent $r1) throws  {
        return this.mWrapped.dispatchGenericMotionEvent($r1);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent $r1) throws  {
        return this.mWrapped.dispatchPopulateAccessibilityEvent($r1);
    }

    public View onCreatePanelView(int $i0) throws  {
        return this.mWrapped.onCreatePanelView($i0);
    }

    public boolean onCreatePanelMenu(int $i0, Menu $r1) throws  {
        return this.mWrapped.onCreatePanelMenu($i0, $r1);
    }

    public boolean onPreparePanel(int $i0, View $r1, Menu $r2) throws  {
        return this.mWrapped.onPreparePanel($i0, $r1, $r2);
    }

    public boolean onMenuOpened(int $i0, Menu $r1) throws  {
        return this.mWrapped.onMenuOpened($i0, $r1);
    }

    public boolean onMenuItemSelected(int $i0, MenuItem $r1) throws  {
        return this.mWrapped.onMenuItemSelected($i0, $r1);
    }

    public void onWindowAttributesChanged(LayoutParams $r1) throws  {
        this.mWrapped.onWindowAttributesChanged($r1);
    }

    public void onContentChanged() throws  {
        this.mWrapped.onContentChanged();
    }

    public void onWindowFocusChanged(boolean $z0) throws  {
        this.mWrapped.onWindowFocusChanged($z0);
    }

    public void onAttachedToWindow() throws  {
        this.mWrapped.onAttachedToWindow();
    }

    public void onDetachedFromWindow() throws  {
        this.mWrapped.onDetachedFromWindow();
    }

    public void onPanelClosed(int $i0, Menu $r1) throws  {
        this.mWrapped.onPanelClosed($i0, $r1);
    }

    public boolean onSearchRequested(SearchEvent $r1) throws  {
        return this.mWrapped.onSearchRequested($r1);
    }

    public boolean onSearchRequested() throws  {
        return this.mWrapped.onSearchRequested();
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback $r1) throws  {
        return this.mWrapped.onWindowStartingActionMode($r1);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback $r1, int $i0) throws  {
        return this.mWrapped.onWindowStartingActionMode($r1, $i0);
    }

    public void onActionModeStarted(ActionMode $r1) throws  {
        this.mWrapped.onActionModeStarted($r1);
    }

    public void onActionModeFinished(ActionMode $r1) throws  {
        this.mWrapped.onActionModeFinished($r1);
    }
}
