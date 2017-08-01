package android.support.v7.app;

import android.app.UiModeManager;
import android.content.Context;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Window;

class AppCompatDelegateImplV23 extends AppCompatDelegateImplV14 {
    private final UiModeManager mUiModeManager;

    class AppCompatWindowCallbackV23 extends AppCompatWindowCallbackV14 {
        public ActionMode onWindowStartingActionMode(Callback callback) throws  {
            return null;
        }

        AppCompatWindowCallbackV23(Window.Callback $r2) throws  {
            super($r2);
        }

        public ActionMode onWindowStartingActionMode(Callback $r1, int $i0) throws  {
            if (AppCompatDelegateImplV23.this.isHandleNativeActionModesEnabled()) {
                switch ($i0) {
                    case 0:
                        return startAsSupportActionMode($r1);
                    default:
                        break;
                }
            }
            return super.onWindowStartingActionMode($r1, $i0);
        }
    }

    AppCompatDelegateImplV23(Context $r1, Window $r2, AppCompatCallback $r3) throws  {
        super($r1, $r2, $r3);
        this.mUiModeManager = (UiModeManager) $r1.getSystemService("uimode");
    }

    Window.Callback wrapWindowCallback(Window.Callback $r1) throws  {
        return new AppCompatWindowCallbackV23($r1);
    }

    int mapNightMode(int $i0) throws  {
        return ($i0 == 0 && this.mUiModeManager.getNightMode() == 0) ? -1 : super.mapNightMode($i0);
    }
}
