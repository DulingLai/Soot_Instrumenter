package com.abaltatech.mcp.weblink.utils;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import com.abaltatech.mcp.weblink.core.commandhandling.MouseCommand;
import com.waze.map.CanvasFont;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashSet;

@SuppressLint({"InlinedApi"})
public final class WLEventUtils {
    private static final SparseArray<KeyData> KEY_MAPPING;

    private static final class KeyData {
        String m_characters;
        int m_keyCode;
        int m_metaState;

        KeyData(int $i0, int $i1) throws  {
            this.m_keyCode = $i0;
            this.m_metaState = $i1;
        }

        KeyData(String $r1) throws  {
            this.m_characters = $r1;
        }

        KeyEvent createEvent(boolean $z0) throws  {
            long $l2 = SystemClock.uptimeMillis();
            if (this.m_characters != null) {
                return $z0 ? new KeyEvent($l2, this.m_characters, 0, 0) : null;
            } else {
                return new KeyEvent($l2, $l2, $z0 ? 0 : 1, this.m_keyCode, 0, this.m_metaState);
            }
        }
    }

    public static final class MotionContext {
        private boolean m_btnDown = false;
        private long m_downTime = 0;

        public void reset() throws  {
            this.m_btnDown = false;
            this.m_downTime = 0;
        }
    }

    @android.annotation.SuppressLint({"Recycle"})
    public static final android.view.MotionEvent createMotionEvent(com.abaltatech.mcp.weblink.utils.WLEventUtils.MotionContext r41, com.abaltatech.mcp.weblink.core.commandhandling.TouchCommand r42, int r43, int r44, float r45, float r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:11:0x008a
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
        if (r41 == 0) goto L_0x0152;
    L_0x0002:
        r16 = android.os.SystemClock.uptimeMillis();
        r0 = r41;
        r18 = r0.m_downTime;
        r20 = -1;
        r0 = r42;
        r21 = r0.getEventType();
        switch(r21) {
            case 0: goto L_0x00fb;
            case 1: goto L_0x0106;
            case 2: goto L_0x0109;
            default: goto L_0x0017;
        };
    L_0x0017:
        goto L_0x0018;
    L_0x0018:
        r22 = -1;
        r0 = r20;
        r1 = r22;
        if (r0 == r1) goto L_0x0155;
    L_0x0020:
        r0 = r42;
        r21 = r0.getCount();
        r0 = r21;
        r0 = new android.view.MotionEvent.PointerProperties[r0];
        r23 = r0;
        r0 = r21;
        r0 = new android.view.MotionEvent.PointerCoords[r0];
        r24 = r0;
        r25 = 0;
    L_0x0034:
        r0 = r25;
        r1 = r21;
        if (r0 >= r1) goto L_0x011f;
    L_0x003a:
        r0 = r42;
        r1 = r25;
        r26 = r0.getTouchPoint(r1);
        r27 = new android.view.MotionEvent$PointerProperties;
        r0 = r27;
        r0.<init>();
        r23[r25] = r27;
        r28 = new android.view.MotionEvent$PointerCoords;
        r0 = r28;
        r0.<init>();
        r24[r25] = r28;
        r27 = r23[r25];
        r0 = r26;
        r0 = r0.m_id;
        r29 = r0;
        r1 = r27;
        r1.id = r0;
        r27 = r23[r25];
        r22 = 1;
        r0 = r22;
        r1 = r27;
        r1.toolType = r0;
        r28 = r24[r25];
        r0 = r26;
        r0 = r0.m_posX;
        r29 = r0;
        r0 = (float) r0;
        r30 = r0;
        r1 = r45;
        r0 = r0 * r1;
        r30 = r0;
        r0 = r43;
        r0 = (float) r0;
        r31 = r0;
        r0 = r30;
        r1 = r31;
        r0 = r0 - r1;
        r30 = r0;
        goto L_0x0092;
    L_0x0087:
        goto L_0x0018;
        goto L_0x0092;
    L_0x008b:
        goto L_0x0018;
        goto L_0x0092;
    L_0x008f:
        goto L_0x0018;
    L_0x0092:
        r22 = 0;
        r0 = r28;
        r1 = r22;
        r2 = r30;
        r0.setAxisValue(r1, r2);
        goto L_0x00a1;
    L_0x009e:
        goto L_0x0034;
    L_0x00a1:
        r28 = r24[r25];
        r0 = r26;
        r0 = r0.m_posY;
        r29 = r0;
        r0 = (float) r0;
        r30 = r0;
        r1 = r46;
        r0 = r0 * r1;
        r30 = r0;
        r0 = r44;
        r0 = (float) r0;
        r31 = r0;
        r0 = r30;
        r1 = r31;
        r0 = r0 - r1;
        r30 = r0;
        r22 = 1;
        r0 = r28;
        r1 = r22;
        r2 = r30;
        r0.setAxisValue(r1, r2);
        r28 = r24[r25];
        r32 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r32;
        r1 = r28;
        r1.pressure = r0;
        r28 = r24[r25];
        r32 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r32;
        r1 = r28;
        r1.size = r0;
        r22 = 2;
        r0 = r20;
        r1 = r22;
        if (r0 != r1) goto L_0x00f8;
    L_0x00e6:
        r0 = r26;
        r0 = r0.m_state;
        r29 = r0;
        r22 = 1;
        r0 = r29;
        r1 = r22;
        if (r0 != r1) goto L_0x010c;
    L_0x00f4:
        r20 = r25 << 8;
        r20 = r20 + 5;
    L_0x00f8:
        r25 = r25 + 1;
        goto L_0x009e;
    L_0x00fb:
        r0 = r41;
        r1 = r16;
        r18 = r0.m_downTime = r1;
        r20 = 0;
        goto L_0x0087;
    L_0x0106:
        r20 = 2;
        goto L_0x008b;
    L_0x0109:
        r20 = 1;
        goto L_0x008f;
    L_0x010c:
        r0 = r26;
        r0 = r0.m_state;
        r29 = r0;
        r22 = 8;
        r0 = r29;
        r1 = r22;
        if (r0 != r1) goto L_0x00f8;
    L_0x011a:
        r20 = r25 << 8;
        r20 = r20 + 6;
        goto L_0x00f8;
    L_0x011f:
        r22 = 0;
        r34 = 0;
        r32 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r35 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r36 = 0;
        r37 = 0;
        r38 = 0;
        r39 = 0;
        r0 = r18;
        r2 = r16;
        r4 = r20;
        r5 = r21;
        r6 = r23;
        r7 = r24;
        r8 = r22;
        r9 = r34;
        r10 = r32;
        r11 = r35;
        r12 = r36;
        r13 = r37;
        r14 = r38;
        r15 = r39;
        r33 = android.view.MotionEvent.obtain(r0, r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);
        return r33;
    L_0x0152:
        r40 = 0;
        return r40;
    L_0x0155:
        r40 = 0;
        return r40;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.utils.WLEventUtils.createMotionEvent(com.abaltatech.mcp.weblink.utils.WLEventUtils$MotionContext, com.abaltatech.mcp.weblink.core.commandhandling.TouchCommand, int, int, float, float):android.view.MotionEvent");
    }

    public static final KeyEvent createKeyEvent(int $i0, int $i1) throws  {
        KeyData $r2 = (KeyData) KEY_MAPPING.get($i1);
        if ($r2 == null) {
            $r2 = new KeyData(new String(Character.toChars($i1)));
        }
        if ($r2 == null) {
            return null;
        }
        boolean $z0;
        if ($i0 == 2) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        return $r2.createEvent($z0);
    }

    @SuppressLint({"Recycle"})
    public static final MotionEvent createMotionEvent(MotionEvent $r0, int $i0, int $i1) throws  {
        if ($r0 == null) {
            return null;
        }
        int $i2 = $r0.getPointerCount();
        PointerProperties[] $r1 = new PointerProperties[$i2];
        PointerCoords[] $r2 = new PointerCoords[$i2];
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            PointerCoords pointerCoords = new PointerCoords();
            $r1[$i3] = new PointerProperties();
            $r0.getPointerProperties($i3, $r1[$i3]);
            $r0.getPointerCoords($i3, pointerCoords);
            float $f0 = pointerCoords.x + ((float) $i0);
            float f = $f0;
            pointerCoords.x = $f0;
            $f0 = pointerCoords.y + ((float) $i1);
            f = $f0;
            pointerCoords.y = $f0;
            $r2[$i3] = pointerCoords;
        }
        return MotionEvent.obtain($r0.getDownTime(), $r0.getEventTime(), $r0.getAction(), $i2, $r1, $r2, $r0.getMetaState(), $r0.getButtonState(), $r0.getXPrecision(), $r0.getYPrecision(), $r0.getDeviceId(), $r0.getEdgeFlags(), $r0.getSource(), $r0.getFlags());
    }

    @SuppressLint({"Recycle"})
    public static final MotionEvent createMotionEvent(MotionEvent $r0, int $i0, int $i1, float $f0, float $f1) throws  {
        if ($r0 == null) {
            return null;
        }
        int $i2 = $r0.getPointerCount();
        PointerProperties[] $r1 = new PointerProperties[$i2];
        PointerCoords[] $r2 = new PointerCoords[$i2];
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            PointerCoords pointerCoords = new PointerCoords();
            $r1[$i3] = new PointerProperties();
            $r0.getPointerProperties($i3, $r1[$i3]);
            $r0.getPointerCoords($i3, pointerCoords);
            float $f2 = pointerCoords.x;
            $f2 = ($f2 * $f0) + ((float) $i0);
            float f = $f2;
            pointerCoords.x = $f2;
            $f2 = pointerCoords.y;
            $f2 = ($f2 * $f1) + ((float) $i1);
            f = $f2;
            pointerCoords.y = $f2;
            $r2[$i3] = pointerCoords;
        }
        return MotionEvent.obtain($r0.getDownTime(), $r0.getEventTime(), $r0.getAction(), $i2, $r1, $r2, $r0.getMetaState(), $r0.getButtonState(), $r0.getXPrecision(), $r0.getYPrecision(), $r0.getDeviceId(), $r0.getEdgeFlags(), $r0.getSource(), $r0.getFlags());
    }

    @SuppressLint({"Recycle"})
    public static final MotionEvent createMotionEvent(MotionContext $r0, MouseCommand $r1, int $i0, int $i1, float $f0, float $f1) throws  {
        if ($r0 == null) {
            return null;
        }
        long $l2 = SystemClock.uptimeMillis();
        int $i02 = (((float) $r1.getX()) * $f0) - ((float) $i0);
        int i = $i02;
        $i0 = (int) $i02;
        float $f02 = (float) $r1.getY();
        $i02 = ($f02 * $f1) - ((float) $i1);
        i = $i02;
        $i1 = (int) $i02;
        switch ($r1.getActionType()) {
            case 1:
                if (!$r0.m_btnDown) {
                    return null;
                }
                return MotionEvent.obtain($r0.m_downTime, $l2, 2, (float) $i0, (float) $i1, 0);
            case 2:
                $r0.m_btnDown = true;
                $r0.m_downTime = SystemClock.uptimeMillis();
                return MotionEvent.obtain($r0.m_downTime, $l2, 0, (float) $i0, (float) $i1, 0);
            case 3:
                $r0.m_btnDown = false;
                return MotionEvent.obtain($r0.m_downTime, $l2, 1, (float) $i0, (float) $i1, 0);
            default:
                return null;
        }
    }

    static {
        SparseArray $r0 = new SparseArray();
        $r0.put(113, new KeyData(45, 0));
        $r0.put(119, new KeyData(51, 0));
        $r0.put(101, new KeyData(33, 0));
        $r0.put(114, new KeyData(46, 0));
        $r0.put(116, new KeyData(48, 0));
        $r0.put(121, new KeyData(53, 0));
        $r0.put(117, new KeyData(49, 0));
        $r0.put(105, new KeyData(37, 0));
        $r0.put(111, new KeyData(43, 0));
        $r0.put(112, new KeyData(44, 0));
        $r0.put(97, new KeyData(29, 0));
        $r0.put(115, new KeyData(47, 0));
        $r0.put(100, new KeyData(32, 0));
        $r0.put(102, new KeyData(34, 0));
        $r0.put(103, new KeyData(35, 0));
        $r0.put(104, new KeyData(36, 0));
        $r0.put(106, new KeyData(38, 0));
        $r0.put(107, new KeyData(39, 0));
        $r0.put(108, new KeyData(40, 0));
        $r0.put(122, new KeyData(54, 0));
        $r0.put(120, new KeyData(52, 0));
        $r0.put(99, new KeyData(31, 0));
        $r0.put(118, new KeyData(50, 0));
        $r0.put(98, new KeyData(30, 0));
        $r0.put(110, new KeyData(42, 0));
        $r0.put(109, new KeyData(41, 0));
        $r0.put(81, new KeyData(45, 1));
        $r0.put(87, new KeyData(51, 1));
        $r0.put(69, new KeyData(33, 1));
        $r0.put(82, new KeyData(46, 1));
        $r0.put(84, new KeyData(48, 1));
        $r0.put(89, new KeyData(53, 1));
        $r0.put(85, new KeyData(49, 1));
        $r0.put(73, new KeyData(37, 1));
        $r0.put(79, new KeyData(43, 1));
        $r0.put(80, new KeyData(44, 1));
        $r0.put(65, new KeyData(29, 1));
        $r0.put(83, new KeyData(47, 1));
        $r0.put(68, new KeyData(32, 1));
        $r0.put(70, new KeyData(34, 1));
        $r0.put(71, new KeyData(35, 1));
        $r0.put(72, new KeyData(36, 1));
        $r0.put(74, new KeyData(38, 1));
        $r0.put(75, new KeyData(39, 1));
        $r0.put(76, new KeyData(40, 1));
        $r0.put(90, new KeyData(54, 1));
        $r0.put(88, new KeyData(52, 1));
        $r0.put(67, new KeyData(31, 1));
        $r0.put(86, new KeyData(50, 1));
        $r0.put(66, new KeyData(30, 1));
        $r0.put(78, new KeyData(42, 1));
        $r0.put(77, new KeyData(41, 1));
        $r0.put(48, new KeyData(7, 0));
        $r0.put(49, new KeyData(8, 0));
        $r0.put(50, new KeyData(9, 0));
        $r0.put(51, new KeyData(10, 0));
        $r0.put(52, new KeyData(11, 0));
        $r0.put(53, new KeyData(12, 0));
        $r0.put(54, new KeyData(13, 0));
        $r0.put(55, new KeyData(14, 0));
        $r0.put(56, new KeyData(15, 0));
        $r0.put(57, new KeyData(16, 0));
        $r0.put(8, new KeyData(67, 0));
        $r0.put(10, new KeyData(66, 0));
        $r0.put(32, new KeyData(62, 0));
        $r0.put(44, new KeyData(55, 0));
        $r0.put(64, new KeyData(77, 0));
        $r0.put(35, new KeyData(18, 0));
        $r0.put(42, new KeyData(155, 0));
        $r0.put(40, new KeyData(162, 0));
        $r0.put(41, new KeyData(163, 0));
        $r0.put(45, new KeyData(69, 0));
        $r0.put(43, new KeyData(81, 0));
        $r0.put(61, new KeyData(161, 0));
        $r0.put(91, new KeyData(71, 0));
        $r0.put(93, new KeyData(72, 0));
        $r0.put(92, new KeyData(73, 0));
        $r0.put(47, new KeyData(154, 0));
        $r0.put(59, new KeyData(74, 0));
        $r0.put(39, new KeyData(75, 0));
        $r0.put(34, new KeyData("\""));
        $r0.put(63, new KeyData("?"));
        $r0.put(46, new KeyData(56, 0));
        $r0.put(95, new KeyData(69, 1));
        $r0.put(60, new KeyData(55, 1));
        $r0.put(62, new KeyData(56, 1));
        $r0.put(123, new KeyData(71, 1));
        $r0.put(125, new KeyData(72, 1));
        $r0.put(124, new KeyData(73, 1));
        $r0.put(58, new KeyData(74, 1));
        $r0.put(34, new KeyData(75, 1));
        $r0.put(63, new KeyData(76, 1));
        $r0.put(33, new KeyData(8, 1));
        $r0.put(36, new KeyData(11, 1));
        $r0.put(37, new KeyData(12, 1));
        $r0.put(94, new KeyData(13, 1));
        $r0.put(38, new KeyData(14, 1));
        $r0.put(96, new KeyData(68, 0));
        $r0.put(126, new KeyData(68, 1));
        $r0.put(96, new KeyData(68, 0));
        $r0.put(126, new KeyData(68, 1));
        $r0.put(4, new KeyData(23, 0));
        $r0.put(19, new KeyData(19, 0));
        $r0.put(18, new KeyData(21, 0));
        $r0.put(21, new KeyData(20, 0));
        $r0.put(20, new KeyData(22, 0));
        KEY_MAPPING = $r0;
    }

    public static View hittest(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)", "Landroid/view/View;"}) ArrayList<View> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)", "Landroid/view/View;"}) HashSet<View> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)", "Landroid/view/View;"}) float $f0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)", "Landroid/view/View;"}) float $f1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/HashSet", "<", "Landroid/view/View;", ">;FFZ)", "Landroid/view/View;"}) boolean $z0) throws  {
        View $r3 = null;
        if ($r0.isEmpty()) {
            return null;
        }
        View $r5 = (View) $r0.get($r0.size() - 1);
        int[] $r2 = new int[2];
        for (int $i2 = $r0.size() - 1; $i2 >= 0; $i2--) {
            View $r6 = (View) $r0.get($i2);
            if ($r1 == null || !$r1.contains($r6)) {
                $r6.getLocationOnScreen($r2);
            } else {
                int $i1;
                LayoutParams $r8 = (LayoutParams) $r6.getLayoutParams();
                $r2[0] = $r8.x;
                $r2[1] = $r8.y;
                if (!($r6.getPivotX() == 0.0f || $r6.getScaleX() == 1.0f)) {
                    $i1 = (int) (CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR + ($r6.getPivotX() * (1.0f - $r6.getScaleX())));
                    $r2[0] = $r2[0] + $i1;
                }
                if (!($r6.getPivotY() == 0.0f || $r6.getScaleY() == 1.0f)) {
                    $i1 = (int) (CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR + ($r6.getPivotY() * (1.0f - $r6.getScaleY())));
                    $r2[1] = $r2[1] + $i1;
                }
            }
            int $i12 = (int) (((float) $r6.getWidth()) * $r6.getScaleX());
            int $i0 = (int) (((float) $r6.getHeight()) * $r6.getScaleY());
            if ($f0 >= ((float) $r2[0]) && $f0 <= ((float) ($r2[0] + $i12)) && $f1 >= ((float) $r2[1]) && $f1 <= ((float) ($r2[1] + $i0))) {
                $r3 = $r6;
                break;
            }
        }
        if ($r3 == null && $z0) {
            return $r5;
        }
        return $r3;
    }
}
