package android.support.v7.view.menu;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

class MenuDialogHelper implements OnClickListener, OnDismissListener, OnKeyListener, Callback {
    private AlertDialog mDialog;
    private MenuBuilder mMenu;
    ListMenuPresenter mPresenter;
    private Callback mPresenterCallback;

    public MenuDialogHelper(MenuBuilder $r1) throws  {
        this.mMenu = $r1;
    }

    public void show(IBinder $r1) throws  {
        MenuBuilder menuBuilder = this.mMenu;
        Builder $r2 = new Builder(menuBuilder.getContext());
        this.mPresenter = new ListMenuPresenter($r2.getContext(), C0192R.layout.abc_list_menu_item_layout);
        this.mPresenter.setCallback(this);
        this.mMenu.addMenuPresenter(this.mPresenter);
        $r2.setAdapter(this.mPresenter.getAdapter(), this);
        View $r8 = menuBuilder.getHeaderView();
        if ($r8 != null) {
            $r2.setCustomTitle($r8);
        } else {
            $r2.setIcon(menuBuilder.getHeaderIcon()).setTitle(menuBuilder.getHeaderTitle());
        }
        $r2.setOnKeyListener(this);
        this.mDialog = $r2.create();
        this.mDialog.setOnDismissListener(this);
        LayoutParams $r11 = this.mDialog.getWindow().getAttributes();
        $r11.type = 1003;
        if ($r1 != null) {
            $r11.token = $r1;
        }
        $r11.flags |= 131072;
        this.mDialog.show();
    }

    public boolean onKey(DialogInterface $r1, int $i0, KeyEvent $r2) throws  {
        if ($i0 == 82 || $i0 == 4) {
            Window $r4;
            View $r5;
            DispatcherState $r6;
            if ($r2.getAction() == 0 && $r2.getRepeatCount() == 0) {
                $r4 = this.mDialog.getWindow();
                if ($r4 != null) {
                    $r5 = $r4.getDecorView();
                    if ($r5 != null) {
                        $r6 = $r5.getKeyDispatcherState();
                        if ($r6 != null) {
                            $r6.startTracking($r2, this);
                            return true;
                        }
                    }
                }
            } else if ($r2.getAction() == 1 && !$r2.isCanceled()) {
                $r4 = this.mDialog.getWindow();
                if ($r4 != null) {
                    $r5 = $r4.getDecorView();
                    if ($r5 != null) {
                        $r6 = $r5.getKeyDispatcherState();
                        if ($r6 != null && $r6.isTracking($r2)) {
                            this.mMenu.close(true);
                            $r1.dismiss();
                            return true;
                        }
                    }
                }
            }
        }
        return this.mMenu.performShortcut($i0, $r2, 0);
    }

    public void setPresenterCallback(Callback $r1) throws  {
        this.mPresenterCallback = $r1;
    }

    public void dismiss() throws  {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    public void onDismiss(DialogInterface dialog) throws  {
        this.mPresenter.onCloseMenu(this.mMenu, true);
    }

    public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
        if ($z0 || $r1 == this.mMenu) {
            dismiss();
        }
        if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onCloseMenu($r1, $z0);
        }
    }

    public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
        if (this.mPresenterCallback != null) {
            return this.mPresenterCallback.onOpenSubMenu($r1);
        }
        return false;
    }

    public void onClick(DialogInterface dialog, int $i0) throws  {
        this.mMenu.performItemAction((MenuItemImpl) this.mPresenter.getAdapter().getItem($i0), 0);
    }
}
