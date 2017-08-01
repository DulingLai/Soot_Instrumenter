package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.view.SupportActionModeWrapper.CallbackWrapper;
import android.util.Log;
import android.view.ActionMode;
import android.view.Window;
import android.view.Window.Callback;

class AppCompatDelegateImplV14 extends AppCompatDelegateImplV11 {
    private static final String KEY_LOCAL_NIGHT_MODE = "appcompat:local_night_mode";
    private static TwilightManager sTwilightManager;
    private boolean mApplyDayNightCalled;
    private boolean mHandleNativeActionModes = true;
    private int mLocalNightMode = -100;

    class AppCompatWindowCallbackV14 extends AppCompatWindowCallbackBase {
        AppCompatWindowCallbackV14(Callback $r2) throws  {
            super($r2);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback $r1) throws  {
            if (AppCompatDelegateImplV14.this.isHandleNativeActionModesEnabled()) {
                return startAsSupportActionMode($r1);
            }
            return super.onWindowStartingActionMode($r1);
        }

        final ActionMode startAsSupportActionMode(ActionMode.Callback $r1) throws  {
            CallbackWrapper $r2 = new CallbackWrapper(AppCompatDelegateImplV14.this.mContext, $r1);
            android.support.v7.view.ActionMode $r5 = AppCompatDelegateImplV14.this.startSupportActionMode($r2);
            if ($r5 != null) {
                return $r2.getActionModeWrapper($r5);
            }
            return null;
        }
    }

    AppCompatDelegateImplV14(Context $r1, Window $r2, AppCompatCallback $r3) throws  {
        super($r1, $r2, $r3);
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if ($r1 != null && this.mLocalNightMode == -100) {
            this.mLocalNightMode = $r1.getInt(KEY_LOCAL_NIGHT_MODE, -100);
        }
    }

    Callback wrapWindowCallback(Callback $r1) throws  {
        return new AppCompatWindowCallbackV14($r1);
    }

    public void setHandleNativeActionModesEnabled(boolean $z0) throws  {
        this.mHandleNativeActionModes = $z0;
    }

    public boolean isHandleNativeActionModesEnabled() throws  {
        return this.mHandleNativeActionModes;
    }

    public boolean applyDayNight() throws  {
        this.mApplyDayNightCalled = true;
        int $i0 = mapNightMode(this.mLocalNightMode == -100 ? AppCompatDelegate.getDefaultNightMode() : this.mLocalNightMode);
        if ($i0 != -1) {
            return updateConfigurationForNightMode($i0);
        }
        return false;
    }

    public void setLocalNightMode(int $i0) throws  {
        switch ($i0) {
            case -1:
            case 0:
            case 1:
            case 2:
                if (this.mLocalNightMode != $i0) {
                    this.mLocalNightMode = $i0;
                    if (this.mApplyDayNightCalled) {
                        applyDayNight();
                        return;
                    }
                    return;
                }
                return;
            default:
                Log.d("AppCompatDelegate", "setLocalNightMode() called with an unknown mode");
                return;
        }
    }

    int mapNightMode(int $i0) throws  {
        switch ($i0) {
            case -100:
                return -1;
            case 0:
                return getTwilightManager().isNight() ? 2 : 1;
            default:
                return $i0;
        }
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        if (this.mLocalNightMode != -100) {
            $r1.putInt(KEY_LOCAL_NIGHT_MODE, this.mLocalNightMode);
        }
    }

    private boolean updateConfigurationForNightMode(int $i0) throws  {
        byte $b2;
        Resources $r3 = this.mContext.getResources();
        Configuration $r4 = $r3.getConfiguration();
        byte $i1 = $r4.uiMode & 48;
        if ($i0 == 2) {
            $b2 = (byte) 32;
        } else {
            $b2 = (byte) 16;
        }
        if ($i1 == $b2) {
            return false;
        }
        Configuration $r1 = new Configuration($r4);
        $r1.uiMode = ($r1.uiMode & -49) | $b2;
        $r3.updateConfiguration($r1, null);
        return true;
    }

    private TwilightManager getTwilightManager() throws  {
        if (sTwilightManager == null) {
            sTwilightManager = new TwilightManager(this.mContext.getApplicationContext());
        }
        return sTwilightManager;
    }
}
