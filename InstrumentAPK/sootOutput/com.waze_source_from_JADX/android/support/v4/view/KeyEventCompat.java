package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.View;

public final class KeyEventCompat {
    static final KeyEventVersionImpl IMPL;

    interface KeyEventVersionImpl {
        boolean dispatch(KeyEvent keyEvent, Callback callback, Object obj, Object obj2) throws ;

        Object getKeyDispatcherState(View view) throws ;

        boolean isTracking(KeyEvent keyEvent) throws ;

        boolean metaStateHasModifiers(int i, int i2) throws ;

        boolean metaStateHasNoModifiers(int i) throws ;

        int normalizeMetaState(int i) throws ;

        void startTracking(KeyEvent keyEvent) throws ;
    }

    static class BaseKeyEventVersionImpl implements KeyEventVersionImpl {
        private static final int META_ALL_MASK = 247;
        private static final int META_MODIFIER_MASK = 247;

        private static int metaStateFilterDirectionalModifiers(int r1, int r2, int r3, int r4, int r5) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl.metaStateFilterDirectionalModifiers(int, int, int, int, int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 8 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.KeyEventCompat.BaseKeyEventVersionImpl.metaStateFilterDirectionalModifiers(int, int, int, int, int):int");
        }

        public Object getKeyDispatcherState(View view) throws  {
            return null;
        }

        public boolean isTracking(KeyEvent event) throws  {
            return false;
        }

        BaseKeyEventVersionImpl() throws  {
        }

        public int normalizeMetaState(int $i0) throws  {
            if (($i0 & 192) != 0) {
                $i0 |= 1;
            }
            if (($i0 & 48) != 0) {
                $i0 |= 2;
            }
            return $i0 & 247;
        }

        public boolean metaStateHasModifiers(int $i1, int $i0) throws  {
            return metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(normalizeMetaState($i1) & 247, $i0, 1, 64, 128), $i0, 2, 16, 32) == $i0;
        }

        public boolean metaStateHasNoModifiers(int $i0) throws  {
            return (normalizeMetaState($i0) & 247) == 0;
        }

        public void startTracking(KeyEvent event) throws  {
        }

        public boolean dispatch(KeyEvent $r1, Callback $r2, Object state, Object target) throws  {
            return $r1.dispatch($r2);
        }
    }

    static class EclairKeyEventVersionImpl extends BaseKeyEventVersionImpl {
        EclairKeyEventVersionImpl() throws  {
        }

        public void startTracking(KeyEvent $r1) throws  {
            KeyEventCompatEclair.startTracking($r1);
        }

        public boolean isTracking(KeyEvent $r1) throws  {
            return KeyEventCompatEclair.isTracking($r1);
        }

        public Object getKeyDispatcherState(View $r1) throws  {
            return KeyEventCompatEclair.getKeyDispatcherState($r1);
        }

        public boolean dispatch(KeyEvent $r1, Callback $r2, Object $r3, Object $r4) throws  {
            return KeyEventCompatEclair.dispatch($r1, $r2, $r3, $r4);
        }
    }

    static class HoneycombKeyEventVersionImpl extends EclairKeyEventVersionImpl {
        HoneycombKeyEventVersionImpl() throws  {
        }

        public int normalizeMetaState(int $i0) throws  {
            return KeyEventCompatHoneycomb.normalizeMetaState($i0);
        }

        public boolean metaStateHasModifiers(int $i0, int $i1) throws  {
            return KeyEventCompatHoneycomb.metaStateHasModifiers($i0, $i1);
        }

        public boolean metaStateHasNoModifiers(int $i0) throws  {
            return KeyEventCompatHoneycomb.metaStateHasNoModifiers($i0);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombKeyEventVersionImpl();
        } else {
            IMPL = new BaseKeyEventVersionImpl();
        }
    }

    public static int normalizeMetaState(int $i0) throws  {
        return IMPL.normalizeMetaState($i0);
    }

    public static boolean metaStateHasModifiers(int $i0, int $i1) throws  {
        return IMPL.metaStateHasModifiers($i0, $i1);
    }

    public static boolean metaStateHasNoModifiers(int $i0) throws  {
        return IMPL.metaStateHasNoModifiers($i0);
    }

    public static boolean hasModifiers(KeyEvent $r0, int $i0) throws  {
        return IMPL.metaStateHasModifiers($r0.getMetaState(), $i0);
    }

    public static boolean hasNoModifiers(KeyEvent $r0) throws  {
        return IMPL.metaStateHasNoModifiers($r0.getMetaState());
    }

    public static void startTracking(KeyEvent $r0) throws  {
        IMPL.startTracking($r0);
    }

    public static boolean isTracking(KeyEvent $r0) throws  {
        return IMPL.isTracking($r0);
    }

    public static Object getKeyDispatcherState(View $r0) throws  {
        return IMPL.getKeyDispatcherState($r0);
    }

    public static boolean dispatch(KeyEvent $r0, Callback $r1, Object $r2, Object $r3) throws  {
        return IMPL.dispatch($r0, $r1, $r2, $r3);
    }

    private KeyEventCompat() throws  {
    }
}
