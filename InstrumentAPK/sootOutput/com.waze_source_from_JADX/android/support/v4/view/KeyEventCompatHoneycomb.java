package android.support.v4.view;

import android.view.KeyEvent;

class KeyEventCompatHoneycomb {
    KeyEventCompatHoneycomb() throws  {
    }

    public static int normalizeMetaState(int $i0) throws  {
        return KeyEvent.normalizeMetaState($i0);
    }

    public static boolean metaStateHasModifiers(int $i0, int $i1) throws  {
        return KeyEvent.metaStateHasModifiers($i0, $i1);
    }

    public static boolean metaStateHasNoModifiers(int $i0) throws  {
        return KeyEvent.metaStateHasNoModifiers($i0);
    }
}
