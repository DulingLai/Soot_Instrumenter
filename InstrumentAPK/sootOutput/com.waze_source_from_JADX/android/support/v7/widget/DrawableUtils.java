package android.support.v7.widget;

import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableWrapper;

public class DrawableUtils {
    public static final Rect INSETS_NONE = new Rect();
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz = Class.forName("android.graphics.Insets");

    public static android.graphics.Rect getOpticalBounds(android.graphics.drawable.Drawable r19) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x009b in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = sInsetsClazz;
        if (r1 == 0) goto L_0x0083;
    L_0x0004:
        r0 = r19;	 Catch:{ Exception -> 0x0079 }
        r19 = android.support.v4.graphics.drawable.DrawableCompat.unwrap(r0);	 Catch:{ Exception -> 0x0079 }
        r0 = r19;	 Catch:{ Exception -> 0x0079 }
        r1 = r0.getClass();	 Catch:{ Exception -> 0x0079 }
        r3 = 0;	 Catch:{ Exception -> 0x0079 }
        r2 = new java.lang.Class[r3];	 Catch:{ Exception -> 0x0079 }
        r5 = "getOpticalInsets";	 Catch:{ Exception -> 0x0079 }
        r4 = r1.getMethod(r5, r2);	 Catch:{ Exception -> 0x0079 }
        r3 = 0;	 Catch:{ Exception -> 0x0079 }
        r6 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0079 }
        r0 = r19;	 Catch:{ Exception -> 0x0079 }
        r7 = r4.invoke(r0, r6);	 Catch:{ Exception -> 0x0079 }
        if (r7 == 0) goto L_0x0083;
    L_0x0024:
        r8 = new android.graphics.Rect;	 Catch:{ Exception -> 0x0079 }
        r8.<init>();	 Catch:{ Exception -> 0x0079 }
        r1 = sInsetsClazz;	 Catch:{ Exception -> 0x0079 }
        r9 = r1.getFields();	 Catch:{ Exception -> 0x0079 }
        r10 = r9.length;	 Catch:{ Exception -> 0x0079 }
        r11 = 0;
    L_0x0031:
        if (r11 >= r10) goto L_0x009b;	 Catch:{ Exception -> 0x0079 }
    L_0x0033:
        r12 = r9[r11];	 Catch:{ Exception -> 0x0079 }
        r13 = r12.getName();	 Catch:{ Exception -> 0x0079 }
        r14 = -1;	 Catch:{ Exception -> 0x0079 }
        r15 = r13.hashCode();	 Catch:{ Exception -> 0x0079 }
        switch(r15) {
            case -1383228885: goto L_0x0068;
            case 115029: goto L_0x0053;
            case 3317767: goto L_0x0049;
            case 108511772: goto L_0x005e;
            default: goto L_0x0041;
        };	 Catch:{ Exception -> 0x0079 }
    L_0x0041:
        goto L_0x0042;	 Catch:{ Exception -> 0x0079 }
    L_0x0042:
        switch(r14) {
            case 0: goto L_0x0072;
            case 1: goto L_0x0086;
            case 2: goto L_0x008d;
            case 3: goto L_0x0094;
            default: goto L_0x0045;
        };	 Catch:{ Exception -> 0x0079 }
    L_0x0045:
        goto L_0x0046;	 Catch:{ Exception -> 0x0079 }
    L_0x0046:
        r11 = r11 + 1;
        goto L_0x0031;
    L_0x0049:
        r5 = "left";	 Catch:{ Exception -> 0x0079 }
        r16 = r13.equals(r5);	 Catch:{ Exception -> 0x0079 }
        if (r16 == 0) goto L_0x0042;
    L_0x0051:
        r14 = 0;
        goto L_0x0042;
    L_0x0053:
        r5 = "top";	 Catch:{ Exception -> 0x0079 }
        r16 = r13.equals(r5);	 Catch:{ Exception -> 0x0079 }
        if (r16 == 0) goto L_0x0042;
    L_0x005c:
        r14 = 1;
        goto L_0x0042;
    L_0x005e:
        r5 = "right";	 Catch:{ Exception -> 0x0079 }
        r16 = r13.equals(r5);	 Catch:{ Exception -> 0x0079 }
        if (r16 == 0) goto L_0x0042;
    L_0x0066:
        r14 = 2;
        goto L_0x0042;
    L_0x0068:
        r5 = "bottom";	 Catch:{ Exception -> 0x0079 }
        r16 = r13.equals(r5);	 Catch:{ Exception -> 0x0079 }
        if (r16 == 0) goto L_0x0042;
    L_0x0070:
        r14 = 3;
        goto L_0x0042;
    L_0x0072:
        r15 = r12.getInt(r7);	 Catch:{ Exception -> 0x0079 }
        r8.left = r15;	 Catch:{ Exception -> 0x0079 }
        goto L_0x0046;
    L_0x0079:
        r17 = move-exception;
        r5 = "DrawableUtils";
        r18 = "Couldn't obtain the optical insets. Ignoring.";
        r0 = r18;
        android.util.Log.e(r5, r0);
    L_0x0083:
        r8 = INSETS_NONE;
        return r8;
    L_0x0086:
        r15 = r12.getInt(r7);	 Catch:{ Exception -> 0x0079 }
        r8.top = r15;	 Catch:{ Exception -> 0x0079 }
        goto L_0x0046;
    L_0x008d:
        r15 = r12.getInt(r7);	 Catch:{ Exception -> 0x0079 }
        r8.right = r15;	 Catch:{ Exception -> 0x0079 }
        goto L_0x0046;
    L_0x0094:
        r15 = r12.getInt(r7);	 Catch:{ Exception -> 0x0079 }
        r8.bottom = r15;	 Catch:{ Exception -> 0x0079 }
        goto L_0x0046;
    L_0x009b:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.DrawableUtils.getOpticalBounds(android.graphics.drawable.Drawable):android.graphics.Rect");
    }

    static {
        if (VERSION.SDK_INT >= 18) {
            try {
            } catch (ClassNotFoundException e) {
            }
        }
    }

    private DrawableUtils() throws  {
    }

    static void fixDrawable(@NonNull Drawable $r0) throws  {
        if (VERSION.SDK_INT == 21 && VECTOR_DRAWABLE_CLAZZ_NAME.equals($r0.getClass().getName())) {
            fixVectorDrawableTinting($r0);
        }
    }

    public static boolean canSafelyMutateDrawable(@NonNull Drawable $r0) throws  {
        if ($r0 instanceof LayerDrawable) {
            if (VERSION.SDK_INT >= 16) {
                return true;
            }
            return false;
        } else if ($r0 instanceof InsetDrawable) {
            if (VERSION.SDK_INT < 14) {
                return false;
            }
            return true;
        } else if ($r0 instanceof StateListDrawable) {
            if (VERSION.SDK_INT < 8) {
                return false;
            }
            return true;
        } else if ($r0 instanceof GradientDrawable) {
            if (VERSION.SDK_INT < 14) {
                return false;
            }
            return true;
        } else if ($r0 instanceof DrawableContainer) {
            ConstantState $r1 = $r0.getConstantState();
            if (!($r1 instanceof DrawableContainerState)) {
                return true;
            }
            for (Drawable $r02 : ((DrawableContainerState) $r1).getChildren()) {
                if (!canSafelyMutateDrawable($r02)) {
                    return false;
                }
            }
            return true;
        } else if ($r02 instanceof DrawableWrapper) {
            return canSafelyMutateDrawable(((DrawableWrapper) $r02).getWrappedDrawable());
        } else {
            if ($r02 instanceof android.support.v7.graphics.drawable.DrawableWrapper) {
                return canSafelyMutateDrawable(((android.support.v7.graphics.drawable.DrawableWrapper) $r02).getWrappedDrawable());
            }
            return $r02 instanceof ScaleDrawable ? canSafelyMutateDrawable(((ScaleDrawable) $r02).getDrawable()) : true;
        }
    }

    private static void fixVectorDrawableTinting(Drawable $r0) throws  {
        int[] $r1 = $r0.getState();
        if ($r1 == null || $r1.length == 0) {
            $r0.setState(ThemeUtils.CHECKED_STATE_SET);
        } else {
            $r0.setState(ThemeUtils.EMPTY_STATE_SET);
        }
        $r0.setState($r1);
    }

    static Mode parseTintMode(int $i0, Mode $r0) throws  {
        switch ($i0) {
            case 3:
                return Mode.SRC_OVER;
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return VERSION.SDK_INT >= 11 ? Mode.valueOf("ADD") : $r0;
            default:
                break;
        }
        return $r0;
    }
}
