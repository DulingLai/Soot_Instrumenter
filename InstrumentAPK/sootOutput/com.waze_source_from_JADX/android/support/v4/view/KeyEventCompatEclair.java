package android.support.v4.view;

import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.KeyEvent.DispatcherState;
import android.view.View;

class KeyEventCompatEclair {
    KeyEventCompatEclair() throws  {
    }

    public static Object getKeyDispatcherState(View $r0) throws  {
        return $r0.getKeyDispatcherState();
    }

    public static boolean dispatch(KeyEvent $r0, Callback $r1, Object $r3, Object $r2) throws  {
        return $r0.dispatch($r1, (DispatcherState) $r3, $r2);
    }

    public static void startTracking(KeyEvent $r0) throws  {
        $r0.startTracking();
    }

    public static boolean isTracking(KeyEvent $r0) throws  {
        return $r0.isTracking();
    }
}
