package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            this.gravity = -1;
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0192R.styleable.LinearLayoutCompat_Layout);
            this.weight = $r4.getFloat(C0192R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = $r4.getInt(C0192R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            $r4.recycle();
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int $i0, int $i1, float $f0) throws  {
            super($i0, $i1);
            this.gravity = -1;
            this.weight = $f0;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
            this.gravity = -1;
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super($r1);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super($r1);
            this.gravity = -1;
            this.weight = $r1.weight;
            this.gravity = $r1.gravity;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    int getChildrenSkipCount(View child, int index) throws  {
        return 0;
    }

    int getLocationOffset(View child) throws  {
        return 0;
    }

    int getNextLocationOffset(View child) throws  {
        return 0;
    }

    void measureHorizontal(int r45, int r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:83:0x0332
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r44 = this;
        r7 = 0;
        r0 = r44;
        r0.mTotalLength = r7;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 1;
        r13 = 0;
        r0 = r44;
        r14 = r0.getVirtualChildCount();
        r0 = r45;
        r15 = android.view.View.MeasureSpec.getMode(r0);
        r0 = r46;
        r16 = android.view.View.MeasureSpec.getMode(r0);
        r17 = 0;
        r18 = 0;
        r0 = r44;
        r0 = r0.mMaxAscent;
        r19 = r0;
        if (r19 == 0) goto L_0x0031;
    L_0x0029:
        r0 = r44;
        r0 = r0.mMaxDescent;
        r19 = r0;
        if (r19 != 0) goto L_0x0043;
    L_0x0031:
        r7 = 4;
        r0 = new int[r7];
        r19 = r0;
        r1 = r44;
        r1.mMaxAscent = r0;
        r7 = 4;
        r0 = new int[r7];
        r19 = r0;
        r1 = r44;
        r1.mMaxDescent = r0;
    L_0x0043:
        r0 = r44;
        r0 = r0.mMaxAscent;
        r19 = r0;
        r0 = r44;
        r0 = r0.mMaxDescent;
        r20 = r0;
        r7 = 3;
        r21 = -1;
        r19[r7] = r21;
        r7 = 2;
        r21 = -1;
        r19[r7] = r21;
        r7 = 1;
        r21 = -1;
        r19[r7] = r21;
        r7 = 0;
        r21 = -1;
        r19[r7] = r21;
        r7 = 3;
        r21 = -1;
        r20[r7] = r21;
        r7 = 2;
        r21 = -1;
        r20[r7] = r21;
        r7 = 1;
        r21 = -1;
        r20[r7] = r21;
        r7 = 0;
        r21 = -1;
        r20[r7] = r21;
        r0 = r44;
        r0 = r0.mBaselineAligned;
        r22 = r0;
        r0 = r44;
        r0 = r0.mUseLargestChild;
        r23 = r0;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r15 != r7) goto L_0x00b9;
    L_0x0088:
        r24 = 1;
    L_0x008a:
        r25 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r26 = 0;
    L_0x008f:
        r0 = r26;
        if (r0 >= r14) goto L_0x035a;
    L_0x0093:
        r0 = r44;
        r1 = r26;
        r27 = r0.getVirtualChildAt(r1);
        if (r27 != 0) goto L_0x00bc;
    L_0x009d:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r0 = r44;
        r1 = r26;
        r29 = r0.measureNullChild(r1);
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
    L_0x00b6:
        r26 = r26 + 1;
        goto L_0x008f;
    L_0x00b9:
        r24 = 0;
        goto L_0x008a;
    L_0x00bc:
        r0 = r27;
        r28 = r0.getVisibility();
        r7 = 8;
        r0 = r28;
        if (r0 != r7) goto L_0x00da;
    L_0x00c8:
        r0 = r44;
        r1 = r27;
        r2 = r26;
        r28 = r0.getChildrenSkipCount(r1, r2);
        r0 = r26;
        r1 = r28;
        r0 = r0 + r1;
        r26 = r0;
        goto L_0x00b6;
    L_0x00da:
        r0 = r44;
        r1 = r26;
        r30 = r0.hasDividerBeforeChildAt(r1);
        if (r30 == 0) goto L_0x00fb;
    L_0x00e4:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r0 = r44;
        r0 = r0.mDividerWidth;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
    L_0x00fb:
        r0 = r27;
        r31 = r0.getLayoutParams();
        r33 = r31;
        r33 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r33;
        r32 = r33;
        r0 = r32;
        r0 = r0.weight;
        r34 = r0;
        r13 = r13 + r0;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r15 != r7) goto L_0x0258;
    L_0x0113:
        r0 = r32;
        r0 = r0.width;
        r28 = r0;
        if (r28 != 0) goto L_0x0258;
    L_0x011b:
        r0 = r32;
        r0 = r0.weight;
        r34 = r0;
        r36 = 0;
        r35 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x0258;
    L_0x0127:
        if (r24 == 0) goto L_0x0224;
    L_0x0129:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r0 = r32;
        r0 = r0.leftMargin;
        r29 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
    L_0x014d:
        if (r22 == 0) goto L_0x0255;
    L_0x014f:
        r7 = 0;
        r21 = 0;
        r0 = r21;
        r28 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r0);
        r0 = r27;
        r1 = r28;
        r2 = r28;
        r0.measure(r1, r2);
    L_0x0161:
        r30 = 0;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r16;
        if (r0 == r7) goto L_0x0179;
    L_0x016a:
        r0 = r32;
        r0 = r0.height;
        r28 = r0;
        r7 = -1;
        r0 = r28;
        if (r0 != r7) goto L_0x0179;
    L_0x0175:
        r17 = 1;
        r30 = 1;
    L_0x0179:
        r0 = r32;
        r0 = r0.topMargin;
        r28 = r0;
        r0 = r32;
        r0 = r0.bottomMargin;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r0 = r27;
        r29 = r0.getMeasuredHeight();
        r0 = r29;
        r1 = r28;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r27;
        r37 = android.support.v4.view.ViewCompat.getMeasuredState(r0);
        r0 = r37;
        r9 = android.support.v7.widget.ViewUtils.combineMeasuredStates(r9, r0);
        if (r22 == 0) goto L_0x01e6;
    L_0x01a7:
        r0 = r27;
        r37 = r0.getBaseline();
        r7 = -1;
        r0 = r37;
        if (r0 == r7) goto L_0x01e6;
    L_0x01b2:
        r0 = r32;
        r0 = r0.gravity;
        r38 = r0;
        if (r38 >= 0) goto L_0x0336;
    L_0x01ba:
        r0 = r44;
        r0 = r0.mGravity;
        r38 = r0;
    L_0x01c0:
        r38 = r38 & 112;
        r38 = r38 >> 4;
        r38 = r38 & -2;
        r38 = r38 >> 1;
        r39 = r19[r38];
        r0 = r39;
        r1 = r37;
        r39 = java.lang.Math.max(r0, r1);
        r19[r38] = r39;
        r39 = r20[r38];
        r37 = r29 - r37;
        goto L_0x01dc;
    L_0x01d9:
        goto L_0x0161;
    L_0x01dc:
        r0 = r39;
        r1 = r37;
        r37 = java.lang.Math.max(r0, r1);
        r20[r38] = r37;
    L_0x01e6:
        r0 = r29;
        r8 = java.lang.Math.max(r8, r0);
        if (r12 == 0) goto L_0x0341;
    L_0x01ee:
        r0 = r32;
        r0 = r0.height;
        r37 = r0;
        r7 = -1;
        r0 = r37;
        if (r0 != r7) goto L_0x0341;
    L_0x01f9:
        r12 = 1;
    L_0x01fa:
        r0 = r32;
        r0 = r0.weight;
        r34 = r0;
        r36 = 0;
        r35 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x034a;
    L_0x0206:
        if (r30 == 0) goto L_0x0347;
    L_0x0208:
        r0 = r28;
        r11 = java.lang.Math.max(r11, r0);
    L_0x020e:
        r0 = r44;
        r1 = r27;
        r2 = r26;
        r28 = r0.getChildrenSkipCount(r1, r2);
        goto L_0x021c;
    L_0x0219:
        goto L_0x00b6;
    L_0x021c:
        r0 = r26;
        r1 = r28;
        r0 = r0 + r1;
        r26 = r0;
        goto L_0x0219;
    L_0x0224:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r0 = r32;
        r0 = r0.leftMargin;
        r29 = r0;
        r1 = r28;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r28 = java.lang.Math.max(r0, r1);
        goto L_0x024e;
    L_0x024b:
        goto L_0x014d;
    L_0x024e:
        r0 = r28;
        r1 = r44;
        r1.mTotalLength = r0;
        goto L_0x024b;
    L_0x0255:
        r18 = 1;
        goto L_0x01d9;
    L_0x0258:
        r28 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r32;
        r0 = r0.width;
        r29 = r0;
        if (r29 != 0) goto L_0x0276;
    L_0x0263:
        r0 = r32;
        r0 = r0.weight;
        r34 = r0;
        r36 = 0;
        r35 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x0276;
    L_0x026f:
        r28 = 0;
        r7 = -2;
        r0 = r32;
        r0.width = r7;
    L_0x0276:
        r36 = 0;
        r35 = (r13 > r36 ? 1 : (r13 == r36 ? 0 : -1));
        if (r35 != 0) goto L_0x02ef;
    L_0x027c:
        r0 = r44;
        r0 = r0.mTotalLength;
        r29 = r0;
    L_0x0282:
        r7 = 0;
        r0 = r44;
        r1 = r27;
        r2 = r26;
        r3 = r45;
        r4 = r29;
        r5 = r46;
        r6 = r7;
        r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6);
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r28;
        if (r0 == r7) goto L_0x02a0;
    L_0x029a:
        r0 = r28;
        r1 = r32;
        r1.width = r0;
    L_0x02a0:
        r0 = r27;
        r28 = r0.getMeasuredWidth();
        if (r24 == 0) goto L_0x02f2;
    L_0x02a8:
        r0 = r44;
        r0 = r0.mTotalLength;
        r29 = r0;
        r0 = r32;
        r0 = r0.leftMargin;
        r37 = r0;
        r1 = r28;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r38 = r0;
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r44;
        r1 = r27;
        r38 = r0.getNextLocationOffset(r1);
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
    L_0x02e0:
        if (r23 == 0) goto L_0x0161;
    L_0x02e2:
        goto L_0x02e6;
    L_0x02e3:
        goto L_0x0161;
    L_0x02e6:
        r0 = r28;
        r1 = r25;
        r25 = java.lang.Math.max(r0, r1);
        goto L_0x02e3;
    L_0x02ef:
        r29 = 0;
        goto L_0x0282;
    L_0x02f2:
        r0 = r44;
        r0 = r0.mTotalLength;
        r29 = r0;
        r37 = r29 + r28;
        r0 = r32;
        r0 = r0.leftMargin;
        r38 = r0;
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r38 = r0;
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r44;
        r1 = r27;
        r38 = r0.getNextLocationOffset(r1);
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r29 = java.lang.Math.max(r0, r1);
        r0 = r29;
        r1 = r44;
        r1.mTotalLength = r0;
        goto L_0x02e0;
        goto L_0x0336;
    L_0x0333:
        goto L_0x01c0;
    L_0x0336:
        r0 = r32;
        r0 = r0.gravity;
        r38 = r0;
        goto L_0x0333;
        goto L_0x0341;
    L_0x033e:
        goto L_0x01fa;
    L_0x0341:
        r12 = 0;
        goto L_0x033e;
        goto L_0x0347;
    L_0x0344:
        goto L_0x0208;
    L_0x0347:
        r28 = r29;
        goto L_0x0344;
    L_0x034a:
        if (r30 == 0) goto L_0x0357;
    L_0x034c:
        goto L_0x0350;
    L_0x034d:
        goto L_0x020e;
    L_0x0350:
        r0 = r28;
        r10 = java.lang.Math.max(r10, r0);
        goto L_0x034d;
    L_0x0357:
        r28 = r29;
        goto L_0x0350;
    L_0x035a:
        r0 = r44;
        r0 = r0.mTotalLength;
        r26 = r0;
        if (r26 <= 0) goto L_0x0381;
    L_0x0362:
        r0 = r44;
        r30 = r0.hasDividerBeforeChildAt(r14);
        if (r30 == 0) goto L_0x0381;
    L_0x036a:
        r0 = r44;
        r0 = r0.mTotalLength;
        r26 = r0;
        r0 = r44;
        r0 = r0.mDividerWidth;
        r28 = r0;
        r0 = r26;
        r1 = r28;
        r0 = r0 + r1;
        r26 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
    L_0x0381:
        r7 = 1;
        r26 = r19[r7];
        r7 = -1;
        r0 = r26;
        if (r0 != r7) goto L_0x03a1;
    L_0x0389:
        r7 = 0;
        r26 = r19[r7];
        r7 = -1;
        r0 = r26;
        if (r0 != r7) goto L_0x03a1;
    L_0x0391:
        r7 = 2;
        r26 = r19[r7];
        r7 = -1;
        r0 = r26;
        if (r0 != r7) goto L_0x03a1;
    L_0x0399:
        r7 = 3;
        r26 = r19[r7];
        r7 = -1;
        r0 = r26;
        if (r0 == r7) goto L_0x03f4;
    L_0x03a1:
        r7 = 3;
        r26 = r19[r7];
        r7 = 0;
        r28 = r19[r7];
        r7 = 1;
        r29 = r19[r7];
        r7 = 2;
        r37 = r19[r7];
        r0 = r29;
        r1 = r37;
        r29 = java.lang.Math.max(r0, r1);
        r0 = r28;
        r1 = r29;
        r28 = java.lang.Math.max(r0, r1);
        r0 = r26;
        r1 = r28;
        r26 = java.lang.Math.max(r0, r1);
        r7 = 3;
        r28 = r20[r7];
        r7 = 0;
        r29 = r20[r7];
        r7 = 1;
        r37 = r20[r7];
        r7 = 2;
        r38 = r20[r7];
        r0 = r37;
        r1 = r38;
        r37 = java.lang.Math.max(r0, r1);
        r0 = r29;
        r1 = r37;
        r29 = java.lang.Math.max(r0, r1);
        r0 = r28;
        r1 = r29;
        r28 = java.lang.Math.max(r0, r1);
        r0 = r26;
        r1 = r28;
        r0 = r0 + r1;
        r26 = r0;
        r8 = java.lang.Math.max(r8, r0);
    L_0x03f4:
        if (r23 == 0) goto L_0x04d7;
    L_0x03f6:
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r15 == r7) goto L_0x03fd;
    L_0x03fb:
        if (r15 != 0) goto L_0x04d7;
    L_0x03fd:
        r7 = 0;
        r0 = r44;
        r0.mTotalLength = r7;
        r26 = 0;
    L_0x0404:
        r0 = r26;
        if (r0 >= r14) goto L_0x04d7;
    L_0x0408:
        r0 = r44;
        r1 = r26;
        r27 = r0.getVirtualChildAt(r1);
        if (r27 != 0) goto L_0x042e;
    L_0x0412:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r0 = r44;
        r1 = r26;
        r29 = r0.measureNullChild(r1);
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
    L_0x042b:
        r26 = r26 + 1;
        goto L_0x0404;
    L_0x042e:
        r0 = r27;
        r28 = r0.getVisibility();
        r7 = 8;
        r0 = r28;
        if (r0 != r7) goto L_0x044c;
    L_0x043a:
        r0 = r44;
        r1 = r27;
        r2 = r26;
        r28 = r0.getChildrenSkipCount(r1, r2);
        r0 = r26;
        r1 = r28;
        r0 = r0 + r1;
        r26 = r0;
        goto L_0x042b;
    L_0x044c:
        r0 = r27;
        r31 = r0.getLayoutParams();
        r40 = r31;
        r40 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r40;
        r32 = r40;
        if (r24 == 0) goto L_0x0497;
    L_0x045a:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r0 = r32;
        r0 = r0.leftMargin;
        r29 = r0;
        r1 = r25;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        goto L_0x047c;
    L_0x0479:
        goto L_0x042b;
    L_0x047c:
        r0 = r44;
        r1 = r27;
        r37 = r0.getNextLocationOffset(r1);
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
        goto L_0x042b;
    L_0x0497:
        r0 = r44;
        r0 = r0.mTotalLength;
        r28 = r0;
        r29 = r28 + r25;
        r0 = r32;
        r0 = r0.leftMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r44;
        r1 = r27;
        r37 = r0.getNextLocationOffset(r1);
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r28 = java.lang.Math.max(r0, r1);
        r0 = r28;
        r1 = r44;
        r1.mTotalLength = r0;
        goto L_0x0479;
    L_0x04d7:
        r0 = r44;
        r0 = r0.mTotalLength;
        r26 = r0;
        r0 = r44;
        r28 = r0.getPaddingLeft();
        r0 = r44;
        r29 = r0.getPaddingRight();
        r0 = r28;
        r1 = r29;
        r0 = r0 + r1;
        r28 = r0;
        r0 = r26;
        r1 = r28;
        r0 = r0 + r1;
        r26 = r0;
        r1 = r44;
        r1.mTotalLength = r0;
        r0 = r44;
        r0 = r0.mTotalLength;
        r26 = r0;
        r0 = r44;
        r28 = r0.getSuggestedMinimumWidth();
        r0 = r26;
        r1 = r28;
        r26 = java.lang.Math.max(r0, r1);
        r7 = 0;
        r0 = r26;
        r1 = r45;
        r26 = android.support.v4.view.ViewCompat.resolveSizeAndState(r0, r1, r7);
        r7 = 16777215; // 0xffffff float:2.3509886E-38 double:8.2890456E-317;
        r28 = r26 & r7;
        r0 = r44;
        r0 = r0.mTotalLength;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        r0 = r0 - r1;
        r28 = r0;
        if (r18 != 0) goto L_0x0534;
    L_0x052c:
        if (r28 == 0) goto L_0x0830;
    L_0x052e:
        r36 = 0;
        r35 = (r13 > r36 ? 1 : (r13 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x0830;
    L_0x0534:
        r0 = r44;
        r0 = r0.mWeightSum;
        r34 = r0;
        r36 = 0;
        r35 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x058f;
    L_0x0540:
        r0 = r44;
        r13 = r0.mWeightSum;
    L_0x0544:
        r7 = 3;
        r21 = -1;
        r19[r7] = r21;
        r7 = 2;
        r21 = -1;
        r19[r7] = r21;
        r7 = 1;
        r21 = -1;
        r19[r7] = r21;
        r7 = 0;
        r21 = -1;
        r19[r7] = r21;
        r7 = 3;
        r21 = -1;
        r20[r7] = r21;
        r7 = 2;
        r21 = -1;
        r20[r7] = r21;
        r7 = 1;
        r21 = -1;
        r20[r7] = r21;
        r7 = 0;
        r21 = -1;
        r20[r7] = r21;
        r8 = -1;
        r7 = 0;
        r0 = r44;
        r0.mTotalLength = r7;
        r25 = 0;
    L_0x0574:
        r0 = r25;
        if (r0 >= r14) goto L_0x076d;
    L_0x0578:
        r0 = r44;
        r1 = r25;
        r27 = r0.getVirtualChildAt(r1);
        if (r27 == 0) goto L_0x058c;
    L_0x0582:
        r0 = r27;
        r11 = r0.getVisibility();
        r7 = 8;
        if (r11 != r7) goto L_0x0590;
    L_0x058c:
        r25 = r25 + 1;
        goto L_0x0574;
    L_0x058f:
        goto L_0x0544;
    L_0x0590:
        r0 = r27;
        r31 = r0.getLayoutParams();
        r41 = r31;
        r41 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r41;
        r32 = r41;
        r0 = r32;
        r0 = r0.weight;
        r34 = r0;
        r36 = 0;
        r35 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x0631;
    L_0x05a8:
        r0 = r28;
        r0 = (float) r0;
        r42 = r0;
        r1 = r34;
        r0 = r0 * r1;
        r42 = r0;
        r0 = r0 / r13;
        r42 = r0;
        r11 = (int) r0;
        r29 = r11;
        r0 = r34;
        r13 = r13 - r0;
        r0 = r28;
        r0 = r0 - r11;
        r28 = r0;
        r0 = r44;
        r37 = r0.getPaddingTop();
        r0 = r44;
        r38 = r0.getPaddingBottom();
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r32;
        r0 = r0.topMargin;
        r38 = r0;
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r32;
        r0 = r0.bottomMargin;
        r38 = r0;
        r0 = r37;
        r1 = r38;
        r0 = r0 + r1;
        r37 = r0;
        r0 = r32;
        r0 = r0.height;
        r38 = r0;
        r0 = r46;
        r1 = r37;
        r2 = r38;
        r37 = getChildMeasureSpec(r0, r1, r2);
        r0 = r32;
        r0 = r0.width;
        r38 = r0;
        if (r38 != 0) goto L_0x060a;
    L_0x0605:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r15 == r7) goto L_0x06ff;
    L_0x060a:
        r0 = r27;
        r29 = r0.getMeasuredWidth();
        r11 = r29 + r11;
        if (r11 >= 0) goto L_0x0615;
    L_0x0614:
        r11 = 0;
    L_0x0615:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r11 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r7);
        r0 = r27;
        r1 = r37;
        r0.measure(r11, r1);
    L_0x0623:
        r0 = r27;
        r11 = android.support.v4.view.ViewCompat.getMeasuredState(r0);
        r7 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r11 = r11 & r7;
        r9 = android.support.v7.widget.ViewUtils.combineMeasuredStates(r9, r11);
    L_0x0631:
        if (r24 == 0) goto L_0x071d;
    L_0x0633:
        r0 = r44;
        r11 = r0.mTotalLength;
        r0 = r27;
        r29 = r0.getMeasuredWidth();
        r0 = r32;
        r0 = r0.leftMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r44;
        r1 = r27;
        r37 = r0.getNextLocationOffset(r1);
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r11 = r11 + r0;
        r0 = r44;
        r0.mTotalLength = r11;
    L_0x066b:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r16;
        if (r0 == r7) goto L_0x075e;
    L_0x0672:
        r0 = r32;
        r11 = r0.height;
        r7 = -1;
        if (r11 != r7) goto L_0x075e;
    L_0x0679:
        r18 = 1;
    L_0x067b:
        r0 = r32;
        r11 = r0.topMargin;
        r0 = r32;
        r0 = r0.bottomMargin;
        r29 = r0;
        r29 = r11 + r29;
        r0 = r27;
        r11 = r0.getMeasuredHeight();
        r0 = r29;
        r11 = r11 + r0;
        goto L_0x0694;
    L_0x0691:
        goto L_0x0623;
    L_0x0694:
        r8 = java.lang.Math.max(r8, r11);
        if (r18 == 0) goto L_0x0761;
    L_0x069a:
        r0 = r29;
        r10 = java.lang.Math.max(r10, r0);
        if (r12 == 0) goto L_0x0764;
    L_0x06a2:
        r0 = r32;
        r0 = r0.height;
        r29 = r0;
        r7 = -1;
        r0 = r29;
        if (r0 != r7) goto L_0x0764;
    L_0x06ad:
        r12 = 1;
    L_0x06ae:
        if (r22 == 0) goto L_0x058c;
    L_0x06b0:
        r0 = r27;
        r29 = r0.getBaseline();
        r7 = -1;
        r0 = r29;
        if (r0 == r7) goto L_0x058c;
    L_0x06bb:
        r0 = r32;
        r0 = r0.gravity;
        r37 = r0;
        if (r37 >= 0) goto L_0x0766;
    L_0x06c3:
        r0 = r44;
        r0 = r0.mGravity;
        r37 = r0;
    L_0x06c9:
        r37 = r37 & 112;
        r37 = r37 >> 4;
        r37 = r37 & -2;
        goto L_0x06d3;
    L_0x06d0:
        goto L_0x0691;
    L_0x06d3:
        r37 = r37 >> 1;
        r38 = r19[r37];
        r0 = r38;
        r1 = r29;
        r38 = java.lang.Math.max(r0, r1);
        goto L_0x06e3;
    L_0x06e0:
        goto L_0x066b;
    L_0x06e3:
        r19[r37] = r38;
        r38 = r20[r37];
        goto L_0x06eb;
    L_0x06e8:
        goto L_0x067b;
    L_0x06eb:
        r0 = r29;
        r11 = r11 - r0;
        r0 = r38;
        r11 = java.lang.Math.max(r0, r11);
        goto L_0x06f8;
    L_0x06f5:
        goto L_0x058c;
    L_0x06f8:
        r20[r37] = r11;
        goto L_0x06f5;
        goto L_0x06ff;
    L_0x06fc:
        goto L_0x069a;
    L_0x06ff:
        if (r11 <= 0) goto L_0x0716;
    L_0x0701:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r29;
        r11 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        goto L_0x070e;
    L_0x070b:
        goto L_0x06ae;
    L_0x070e:
        r0 = r27;
        r1 = r37;
        r0.measure(r11, r1);
        goto L_0x06d0;
    L_0x0716:
        r29 = 0;
        goto L_0x0701;
        goto L_0x071d;
    L_0x071a:
        goto L_0x06c9;
    L_0x071d:
        r0 = r44;
        r11 = r0.mTotalLength;
        r0 = r27;
        r29 = r0.getMeasuredWidth();
        r0 = r29;
        r0 = r0 + r11;
        r29 = r0;
        r0 = r32;
        r0 = r0.leftMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r32;
        r0 = r0.rightMargin;
        r37 = r0;
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r0 = r44;
        r1 = r27;
        r37 = r0.getNextLocationOffset(r1);
        r0 = r29;
        r1 = r37;
        r0 = r0 + r1;
        r29 = r0;
        r11 = java.lang.Math.max(r11, r0);
        r0 = r44;
        r0.mTotalLength = r11;
        goto L_0x06e0;
    L_0x075e:
        r18 = 0;
        goto L_0x06e8;
    L_0x0761:
        r29 = r11;
        goto L_0x06fc;
    L_0x0764:
        r12 = 0;
        goto L_0x070b;
    L_0x0766:
        r0 = r32;
        r0 = r0.gravity;
        r37 = r0;
        goto L_0x071a;
    L_0x076d:
        r0 = r44;
        r15 = r0.mTotalLength;
        r0 = r44;
        r25 = r0.getPaddingLeft();
        r0 = r44;
        r11 = r0.getPaddingRight();
        r0 = r25;
        r0 = r0 + r11;
        r25 = r0;
        r15 = r15 + r0;
        r0 = r44;
        r0.mTotalLength = r15;
        r7 = 1;
        r15 = r19[r7];
        r7 = -1;
        if (r15 != r7) goto L_0x079f;
    L_0x078d:
        r7 = 0;
        r15 = r19[r7];
        r7 = -1;
        if (r15 != r7) goto L_0x079f;
    L_0x0793:
        r7 = 2;
        r15 = r19[r7];
        r7 = -1;
        if (r15 != r7) goto L_0x079f;
    L_0x0799:
        r7 = 3;
        r15 = r19[r7];
        r7 = -1;
        if (r15 == r7) goto L_0x07e4;
    L_0x079f:
        r7 = 3;
        r15 = r19[r7];
        r7 = 0;
        r25 = r19[r7];
        r7 = 1;
        r11 = r19[r7];
        r7 = 2;
        r28 = r19[r7];
        r0 = r28;
        r11 = java.lang.Math.max(r11, r0);
        r0 = r25;
        r25 = java.lang.Math.max(r0, r11);
        r0 = r25;
        r15 = java.lang.Math.max(r15, r0);
        r7 = 3;
        r25 = r20[r7];
        r7 = 0;
        r11 = r20[r7];
        r7 = 1;
        r28 = r20[r7];
        r7 = 2;
        r29 = r20[r7];
        r0 = r28;
        r1 = r29;
        r28 = java.lang.Math.max(r0, r1);
        r0 = r28;
        r11 = java.lang.Math.max(r11, r0);
        r0 = r25;
        r25 = java.lang.Math.max(r0, r11);
        r0 = r25;
        r15 = r15 + r0;
        r8 = java.lang.Math.max(r8, r15);
    L_0x07e4:
        if (r12 != 0) goto L_0x07ee;
    L_0x07e6:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r16;
        if (r0 == r7) goto L_0x07ee;
    L_0x07ed:
        r8 = r10;
    L_0x07ee:
        r0 = r44;
        r16 = r0.getPaddingTop();
        r0 = r44;
        r10 = r0.getPaddingBottom();
        r0 = r16;
        r0 = r0 + r10;
        r16 = r0;
        r16 = r8 + r16;
        r0 = r44;
        r8 = r0.getSuggestedMinimumHeight();
        r0 = r16;
        r16 = java.lang.Math.max(r0, r8);
        r7 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r8 = r7 & r9;
        r0 = r26;
        r8 = r8 | r0;
        r9 = r9 << 16;
        r0 = r16;
        r1 = r46;
        r46 = android.support.v4.view.ViewCompat.resolveSizeAndState(r0, r1, r9);
        r0 = r44;
        r1 = r46;
        r0.setMeasuredDimension(r8, r1);
        if (r17 == 0) goto L_0x0889;
    L_0x0828:
        r0 = r44;
        r1 = r45;
        r0.forceUniformHeight(r14, r1);
        return;
    L_0x0830:
        r10 = java.lang.Math.max(r10, r11);
        if (r23 == 0) goto L_0x07e4;
    L_0x0836:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r15 == r7) goto L_0x07e4;
    L_0x083b:
        r15 = 0;
    L_0x083c:
        if (r15 >= r14) goto L_0x07e4;
    L_0x083e:
        r0 = r44;
        r27 = r0.getVirtualChildAt(r15);
        if (r27 == 0) goto L_0x0850;
    L_0x0846:
        r0 = r27;
        r11 = r0.getVisibility();
        r7 = 8;
        if (r11 != r7) goto L_0x0853;
    L_0x0850:
        r15 = r15 + 1;
        goto L_0x083c;
    L_0x0853:
        r0 = r27;
        r31 = r0.getLayoutParams();
        r43 = r31;
        r43 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r43;
        r32 = r43;
        r0 = r32;
        r13 = r0.weight;
        r36 = 0;
        r35 = (r13 > r36 ? 1 : (r13 == r36 ? 0 : -1));
        if (r35 <= 0) goto L_0x0850;
    L_0x0869:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r25;
        r11 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r0 = r27;
        r28 = r0.getMeasuredHeight();
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r28;
        r28 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r0 = r27;
        r1 = r28;
        r0.measure(r11, r1);
        goto L_0x0850;
    L_0x0889:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutCompat.measureHorizontal(int, int):void");
    }

    int measureNullChild(int childIndex) throws  {
        return 0;
    }

    public boolean shouldDelayChildPressedState() throws  {
        return false;
    }

    public LinearLayoutCompat(Context $r1) throws  {
        this($r1, null);
    }

    public LinearLayoutCompat(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public LinearLayoutCompat(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray $r4 = TintTypedArray.obtainStyledAttributes($r1, $r2, C0192R.styleable.LinearLayoutCompat, $i0, 0);
        $i0 = $r4.getInt(C0192R.styleable.LinearLayoutCompat_android_orientation, -1);
        if ($i0 >= 0) {
            setOrientation($i0);
        }
        $i0 = $r4.getInt(C0192R.styleable.LinearLayoutCompat_android_gravity, -1);
        if ($i0 >= 0) {
            setGravity($i0);
        }
        boolean $z0 = $r4.getBoolean(C0192R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!$z0) {
            setBaselineAligned($z0);
        }
        this.mWeightSum = $r4.getFloat(C0192R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = $r4.getInt(C0192R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = $r4.getBoolean(C0192R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable($r4.getDrawable(C0192R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = $r4.getInt(C0192R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = $r4.getDimensionPixelSize(C0192R.styleable.LinearLayoutCompat_dividerPadding, 0);
        $r4.recycle();
    }

    public void setShowDividers(int $i0) throws  {
        if ($i0 != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = $i0;
    }

    public int getShowDividers() throws  {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() throws  {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable $r1) throws  {
        boolean $z0 = false;
        if ($r1 != this.mDivider) {
            this.mDivider = $r1;
            if ($r1 != null) {
                this.mDividerWidth = $r1.getIntrinsicWidth();
                this.mDividerHeight = $r1.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            if ($r1 == null) {
                $z0 = true;
            }
            setWillNotDraw($z0);
            requestLayout();
        }
    }

    public void setDividerPadding(int $i0) throws  {
        this.mDividerPadding = $i0;
    }

    public int getDividerPadding() throws  {
        return this.mDividerPadding;
    }

    public int getDividerWidth() throws  {
        return this.mDividerWidth;
    }

    protected void onDraw(Canvas $r1) throws  {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical($r1);
            } else {
                drawDividersHorizontal($r1);
            }
        }
    }

    void drawDividersVertical(Canvas $r1) throws  {
        int $i1 = getVirtualChildCount();
        int $i2 = 0;
        while ($i2 < $i1) {
            View $r2 = getVirtualChildAt($i2);
            if (!($r2 == null || $r2.getVisibility() == 8 || !hasDividerBeforeChildAt($i2))) {
                drawHorizontalDivider($r1, ($r2.getTop() - ((LayoutParams) $r2.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
            $i2++;
        }
        if (hasDividerBeforeChildAt($i1)) {
            $r2 = getVirtualChildAt($i1 - 1);
            if ($r2 == null) {
                $i1 = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                $i1 = $r2.getBottom() + ((LayoutParams) $r2.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider($r1, $i1);
        }
    }

    void drawDividersHorizontal(Canvas $r1) throws  {
        int $i0 = getVirtualChildCount();
        boolean $z0 = ViewUtils.isLayoutRtl(this);
        int $i1 = 0;
        while ($i1 < $i0) {
            LayoutParams $r4;
            View $r2 = getVirtualChildAt($i1);
            if (!($r2 == null || $r2.getVisibility() == 8 || !hasDividerBeforeChildAt($i1))) {
                int $i2;
                $r4 = (LayoutParams) $r2.getLayoutParams();
                if ($z0) {
                    $i2 = $r2.getRight() + $r4.rightMargin;
                } else {
                    $i2 = ($r2.getLeft() - $r4.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider($r1, $i2);
            }
            $i1++;
        }
        if (hasDividerBeforeChildAt($i0)) {
            $r2 = getVirtualChildAt($i0 - 1);
            if ($r2 != null) {
                $r4 = (LayoutParams) $r2.getLayoutParams();
                if ($z0) {
                    $i0 = ($r2.getLeft() - $r4.leftMargin) - this.mDividerWidth;
                } else {
                    $i0 = $r2.getRight() + $r4.rightMargin;
                }
            } else if ($z0) {
                $i0 = getPaddingLeft();
            } else {
                $i0 = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider($r1, $i0);
        }
    }

    void drawHorizontalDivider(Canvas $r1, int $i0) throws  {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, $i0, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + $i0);
        this.mDivider.draw($r1);
    }

    void drawVerticalDivider(Canvas $r1, int $i0) throws  {
        this.mDivider.setBounds($i0, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + $i0, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw($r1);
    }

    public boolean isBaselineAligned() throws  {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean $z0) throws  {
        this.mBaselineAligned = $z0;
    }

    public boolean isMeasureWithLargestChildEnabled() throws  {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean $z0) throws  {
        this.mUseLargestChild = $z0;
    }

    public int getBaseline() throws  {
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View $r2 = getChildAt(this.mBaselineAlignedChildIndex);
        int $i1 = $r2.getBaseline();
        if ($i1 != -1) {
            int $i2 = this.mBaselineChildTop;
            if (this.mOrientation == 1) {
                int $i0 = this.mGravity & 112;
                if ($i0 != 48) {
                    switch ($i0) {
                        case 16:
                            $i2 += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
                            break;
                        case 80:
                            $i2 = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                            break;
                        default:
                            break;
                    }
                }
            }
            return (((LayoutParams) $r2.getLayoutParams()).topMargin + $i2) + $i1;
        } else if (this.mBaselineAlignedChildIndex == 0) {
            return -1;
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
    }

    public int getBaselineAlignedChildIndex() throws  {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int $i0) throws  {
        if ($i0 < 0 || $i0 >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = $i0;
    }

    View getVirtualChildAt(int $i0) throws  {
        return getChildAt($i0);
    }

    int getVirtualChildCount() throws  {
        return getChildCount();
    }

    public float getWeightSum() throws  {
        return this.mWeightSum;
    }

    public void setWeightSum(float $f0) throws  {
        this.mWeightSum = Math.max(0.0f, $f0);
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        if (this.mOrientation == 1) {
            measureVertical($i0, $i1);
        } else {
            measureHorizontal($i0, $i1);
        }
    }

    protected boolean hasDividerBeforeChildAt(int $i0) throws  {
        if ($i0 == 0) {
            if ((this.mShowDividers & 1) != 0) {
                return true;
            }
            return false;
        } else if ($i0 == getChildCount()) {
            if ((this.mShowDividers & 4) == 0) {
                return false;
            }
            return true;
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            boolean $z0 = false;
            for ($i0--; $i0 >= 0; $i0--) {
                if (getChildAt($i0).getVisibility() != 8) {
                    $z0 = true;
                    break;
                }
            }
            return $z0;
        }
    }

    void measureVertical(int $i0, int $i1) throws  {
        int $i12;
        this.mTotalLength = 0;
        int $i3 = 0;
        int $i4 = 0;
        int $i5 = 0;
        int $i6 = 0;
        boolean $z0 = true;
        float $f0 = 0.0f;
        int $i7 = getVirtualChildCount();
        int $i8 = MeasureSpec.getMode($i0);
        int mode = MeasureSpec.getMode($i1);
        Object obj = null;
        Object obj2 = null;
        int $i2 = this.mBaselineAlignedChildIndex;
        boolean $z3 = this.mUseLargestChild;
        int $i10 = Integer.MIN_VALUE;
        int $i11 = 0;
        while ($i11 < $i7) {
            int i;
            android.view.ViewGroup.LayoutParams $r3;
            int $i13;
            View $r1 = getVirtualChildAt($i11);
            if ($r1 == null) {
                $i12 = this.mTotalLength + measureNullChild($i11);
                i = $i12;
                this.mTotalLength = $i12;
            } else if ($r1.getVisibility() == 8) {
                $i11 += getChildrenSkipCount($r1, $i11);
            } else {
                if (hasDividerBeforeChildAt($i11)) {
                    $i12 = this.mTotalLength + this.mDividerHeight;
                    i = $i12;
                    this.mTotalLength = $i12;
                }
                $r3 = (LayoutParams) $r1.getLayoutParams();
                float $f1 = $r3.weight;
                $f0 += $f1;
                if (mode == 1073741824 && $r3.height == 0 && $r3.weight > 0.0f) {
                    i = this.mTotalLength;
                    $i12 = $r3.topMargin;
                    this.mTotalLength = Math.max(i, ($i12 + i) + $r3.bottomMargin);
                    obj2 = 1;
                } else {
                    i = Integer.MIN_VALUE;
                    if ($r3.height == 0 && $r3.weight > 0.0f) {
                        i = 0;
                        $r3.height = -2;
                    }
                    if ($f0 == 0.0f) {
                        $i13 = this.mTotalLength;
                    } else {
                        $i13 = 0;
                    }
                    measureChildBeforeLayout($r1, $i11, $i0, 0, $i1, $i13);
                    if (i != Integer.MIN_VALUE) {
                        $r3.height = i;
                    }
                    i = $r1.getMeasuredHeight();
                    $i13 = this.mTotalLength;
                    this.mTotalLength = Math.max($i13, ((($i13 + i) + $r3.topMargin) + $r3.bottomMargin) + getNextLocationOffset($r1));
                    if ($z3) {
                        $i10 = Math.max(i, $i10);
                    }
                }
                if ($i2 >= 0 && $i2 == $i11 + 1) {
                    $i12 = this.mTotalLength;
                    i = $i12;
                    this.mBaselineChildTop = $i12;
                }
                if ($i11 >= $i2 || $r3.weight <= 0.0f) {
                    boolean $z4 = false;
                    if ($i8 != 1073741824 && $r3.width == -1) {
                        obj = 1;
                        $z4 = true;
                    }
                    i = $r3.leftMargin + $r3.rightMargin;
                    $i12 = $r1.getMeasuredWidth() + i;
                    $i13 = $i12;
                    $i3 = Math.max($i3, $i12);
                    $i4 = ViewUtils.combineMeasuredStates($i4, ViewCompat.getMeasuredState($r1));
                    if ($z0 && $r3.width == -1) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($r3.weight > 0.0f) {
                        if (!$z4) {
                            i = $i13;
                        }
                        $i6 = Math.max($i6, i);
                    } else {
                        if (!$z4) {
                            i = $i13;
                        }
                        $i5 = Math.max($i5, i);
                    }
                    $i11 += getChildrenSkipCount($r1, $i11);
                } else {
                    throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                }
            }
            $i11++;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt($i7)) {
            $i12 = this.mTotalLength + this.mDividerHeight;
            $i2 = $i12;
            this.mTotalLength = $i12;
        }
        if ($z3 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0;
            $i2 = 0;
            while ($i2 < $i7) {
                $r1 = getVirtualChildAt($i2);
                if ($r1 == null) {
                    $i12 = this.mTotalLength + measureNullChild($i2);
                    $i11 = $i12;
                    this.mTotalLength = $i12;
                } else if ($r1.getVisibility() == 8) {
                    $i2 += getChildrenSkipCount($r1, $i2);
                } else {
                    MarginLayoutParams $r32 = (LayoutParams) $r1.getLayoutParams();
                    $i11 = this.mTotalLength;
                    this.mTotalLength = Math.max($i11, ((($i11 + $i10) + $r32.topMargin) + $r32.bottomMargin) + getNextLocationOffset($r1));
                }
                $i2++;
            }
        }
        $i12 = this.mTotalLength + (getPaddingTop() + getPaddingBottom());
        $i2 = $i12;
        this.mTotalLength = $i12;
        $i2 = ViewCompat.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), $i1, 0);
        $i11 = ($i2 & ViewCompat.MEASURED_SIZE_MASK) - this.mTotalLength;
        if (obj2 != null || ($i11 != 0 && $f0 > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                $f0 = this.mWeightSum;
            }
            this.mTotalLength = 0;
            for ($i10 = 0; $i10 < $i7; $i10++) {
                $r1 = getVirtualChildAt($i10);
                if ($r1.getVisibility() != 8) {
                    $r3 = (LayoutParams) $r1.getLayoutParams();
                    float $f12 = $r3.weight;
                    if ($f12 > 0.0f) {
                        $f1 = (float) $i11;
                        $f1 *= $f12;
                        float f = $f1;
                        $f1 /= $f0;
                        f = $f1;
                        $i6 = (int) $f1;
                        i = $i6;
                        $f0 -= $f12;
                        $i11 -= $i6;
                        $i13 = getChildMeasureSpec($i0, ((getPaddingLeft() + getPaddingRight()) + $r3.leftMargin) + $r3.rightMargin, $r3.width);
                        if ($r3.height == 0 && mode == 1073741824) {
                            if ($i6 <= 0) {
                                i = 0;
                            }
                            $r1.measure($i13, MeasureSpec.makeMeasureSpec(i, 1073741824));
                        } else {
                            $i6 = $r1.getMeasuredHeight() + $i6;
                            if ($i6 < 0) {
                                $i6 = 0;
                            }
                            $r1.measure($i13, MeasureSpec.makeMeasureSpec($i6, 1073741824));
                        }
                        $i4 = ViewUtils.combineMeasuredStates($i4, ViewCompat.getMeasuredState($r1) & InputDeviceCompat.SOURCE_ANY);
                    }
                    $i6 = $r3.leftMargin;
                    $i12 = $r3.rightMargin;
                    $i6 += $i12;
                    $i12 = $r1.getMeasuredWidth() + $i6;
                    i = $i12;
                    $i3 = Math.max($i3, $i12);
                    if ($i8 == 1073741824 || $r3.width != -1) {
                        obj2 = null;
                    } else {
                        obj2 = 1;
                    }
                    if (obj2 == null) {
                        $i6 = i;
                    }
                    $i5 = Math.max($i5, $i6);
                    if ($z0 && $r3.width == -1) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    $i6 = this.mTotalLength;
                    $i12 = ((($r1.getMeasuredHeight() + $i6) + $r3.topMargin) + $r3.bottomMargin) + getNextLocationOffset($r1);
                    i = $i12;
                    this.mTotalLength = Math.max($i6, $i12);
                }
            }
            $i12 = this.mTotalLength + (getPaddingTop() + getPaddingBottom());
            mode = $i12;
            this.mTotalLength = $i12;
        } else {
            $i5 = Math.max($i5, $i6);
            if ($z3 && mode != 1073741824) {
                for (mode = 0; mode < $i7; mode++) {
                    $r1 = getVirtualChildAt(mode);
                    if (!($r1 == null || $r1.getVisibility() == 8 || ((LayoutParams) $r1.getLayoutParams()).weight <= 0.0f)) {
                        $r1.measure(MeasureSpec.makeMeasureSpec($r1.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec($i10, 1073741824));
                    }
                }
            }
        }
        if (!($z0 || $i8 == 1073741824)) {
            $i3 = $i5;
        }
        setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.max($i3 + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), $i0, $i4), $i2);
        if (obj != null) {
            forceUniformWidth($i7, $i1);
        }
    }

    private void forceUniformWidth(int $i0, int $i1) throws  {
        int $i3 = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int $i4 = 0; $i4 < $i0; $i4++) {
            View $r1 = getVirtualChildAt($i4);
            if ($r1.getVisibility() != 8) {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                if ($r3.width == -1) {
                    int $i2 = $r3.height;
                    $r3.height = $r1.getMeasuredHeight();
                    measureChildWithMargins($r1, $i3, 0, $i1, 0);
                    $r3.height = $i2;
                }
            }
        }
    }

    private void forceUniformHeight(int $i0, int $i1) throws  {
        int $i3 = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int $i4 = 0; $i4 < $i0; $i4++) {
            View $r1 = getVirtualChildAt($i4);
            if ($r1.getVisibility() != 8) {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                if ($r3.height == -1) {
                    int $i2 = $r3.width;
                    $r3.width = $r1.getMeasuredWidth();
                    measureChildWithMargins($r1, $i1, 0, $i3, 0);
                    $r3.width = $i2;
                }
            }
        }
    }

    void measureChildBeforeLayout(View $r1, int childIndex, int $i1, int $i2, int $i3, int $i4) throws  {
        measureChildWithMargins($r1, $i1, $i2, $i3, $i4);
    }

    protected void onLayout(boolean changed, int $i0, int $i1, int $i2, int $i3) throws  {
        if (this.mOrientation == 1) {
            layoutVertical($i0, $i1, $i2, $i3);
        } else {
            layoutHorizontal($i0, $i1, $i2, $i3);
        }
    }

    void layoutVertical(int $i0, int $i1, int $i2, int $i3) throws  {
        int $i4;
        int $i6 = getPaddingLeft();
        $i2 -= $i0;
        $i0 = $i2 - getPaddingRight();
        $i2 = ($i2 - $i6) - getPaddingRight();
        int $i7 = getVirtualChildCount();
        int $i5 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (this.mGravity & 112) {
            case 16:
                $i4 = getPaddingTop() + ((($i3 - $i1) - this.mTotalLength) / 2);
                break;
            case 80:
                $i4 = ((getPaddingTop() + $i3) - $i1) - this.mTotalLength;
                break;
            default:
                $i4 = getPaddingTop();
                break;
        }
        $i1 = 0;
        while ($i1 < $i7) {
            View $r1 = getVirtualChildAt($i1);
            if ($r1 == null) {
                $i4 += measureNullChild($i1);
            } else if ($r1.getVisibility() != 8) {
                int $i10;
                int $i8 = $r1.getMeasuredWidth();
                $i3 = $r1.getMeasuredHeight();
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                int $i9 = $r3.gravity;
                if ($i9 < 0) {
                    $i9 = $i5;
                }
                switch (GravityCompat.getAbsoluteGravity($i9, ViewCompat.getLayoutDirection(this)) & 7) {
                    case 1:
                        $i9 = (((($i2 - $i8) / 2) + $i6) + $r3.leftMargin) - $r3.rightMargin;
                        break;
                    case 5:
                        $i9 = ($i0 - $i8) - $r3.rightMargin;
                        break;
                    default:
                        $i9 = $i6 + $r3.leftMargin;
                        break;
                }
                if (hasDividerBeforeChildAt($i1)) {
                    $i10 = this.mDividerHeight;
                    $i4 += $i10;
                }
                $i10 = $r3.topMargin;
                $i4 += $i10;
                setChildFrame($r1, $i9, $i4 + getLocationOffset($r1), $i8, $i3);
                $i10 = ($r3.bottomMargin + $i3) + getNextLocationOffset($r1);
                $i3 = $i10;
                $i4 += $i10;
                $i1 += getChildrenSkipCount($r1, $i1);
            }
            $i1++;
        }
    }

    void layoutHorizontal(int $i0, int $i1, int $i2, int $i3) throws  {
        int $i9;
        boolean $z1 = ViewUtils.isLayoutRtl(this);
        int $i7 = getPaddingTop();
        $i3 -= $i1;
        $i1 = $i3 - getPaddingBottom();
        $i3 = ($i3 - $i7) - getPaddingBottom();
        int $i8 = getVirtualChildCount();
        int $i4 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int $i6 = this.mGravity & 112;
        boolean $z0 = this.mBaselineAligned;
        int[] $r1 = this.mMaxAscent;
        int[] $r2 = this.mMaxDescent;
        switch (GravityCompat.getAbsoluteGravity($i4, ViewCompat.getLayoutDirection(this))) {
            case 1:
                $i9 = getPaddingLeft() + ((($i2 - $i0) - this.mTotalLength) / 2);
                break;
            case 5:
                $i9 = ((getPaddingLeft() + $i2) - $i0) - this.mTotalLength;
                break;
            default:
                $i9 = getPaddingLeft();
                break;
        }
        $i0 = 0;
        int i = 1;
        if ($z1) {
            $i0 = $i8 - 1;
            i = -1;
        }
        $i2 = 0;
        while ($i2 < $i8) {
            $i4 = $i0 + (i * $i2);
            View $r3 = getVirtualChildAt($i4);
            if ($r3 == null) {
                $i9 += measureNullChild($i4);
            } else if ($r3.getVisibility() != 8) {
                int $i5;
                int $i11 = $r3.getMeasuredWidth();
                int $i12 = $r3.getMeasuredHeight();
                int $i52 = -1;
                LayoutParams $r5 = (LayoutParams) $r3.getLayoutParams();
                if ($z0 && $r5.height != -1) {
                    $i52 = $r3.getBaseline();
                }
                int $i13 = $r5.gravity;
                int $i14 = $i13;
                if ($i13 < 0) {
                    $i14 = $i6;
                }
                switch ($i14 & 112) {
                    case 16:
                        $i13 = (((($i3 - $i12) / 2) + $i7) + $r5.topMargin) - $r5.bottomMargin;
                        break;
                    case 48:
                        $i13 = $i7 + $r5.topMargin;
                        if ($i52 != -1) {
                            $i13 += $r1[1] - $i52;
                            break;
                        }
                        break;
                    case 80:
                        $i13 = ($i1 - $i12) - $r5.bottomMargin;
                        if ($i52 != -1) {
                            $i13 -= $r2[2] - ($r3.getMeasuredHeight() - $i52);
                            break;
                        }
                        break;
                    default:
                        $i13 = $i7;
                        break;
                }
                if (hasDividerBeforeChildAt($i4)) {
                    $i5 = this.mDividerWidth;
                    $i9 += $i5;
                }
                $i5 = $r5.leftMargin;
                $i9 += $i5;
                setChildFrame($r3, $i9 + getLocationOffset($r3), $i13, $i11, $i12);
                $i5 = ($r5.rightMargin + $i11) + getNextLocationOffset($r3);
                $i11 = $i5;
                $i9 += $i5;
                $i2 += getChildrenSkipCount($r3, $i4);
            }
            $i2++;
        }
    }

    private void setChildFrame(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        $r1.layout($i0, $i1, $i0 + $i2, $i1 + $i3);
    }

    public void setOrientation(int $i0) throws  {
        if (this.mOrientation != $i0) {
            this.mOrientation = $i0;
            requestLayout();
        }
    }

    public int getOrientation() throws  {
        return this.mOrientation;
    }

    public void setGravity(int $i0) throws  {
        if (this.mGravity != $i0) {
            if ((GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK & $i0) == 0) {
                $i0 |= GravityCompat.START;
            }
            if (($i0 & 112) == 0) {
                $i0 |= 48;
            }
            this.mGravity = $i0;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int $i0) throws  {
        $i0 &= GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) != $i0) {
            this.mGravity = (this.mGravity & -8388616) | $i0;
            requestLayout();
        }
    }

    public void setVerticalGravity(int $i0) throws  {
        $i0 &= 112;
        if ((this.mGravity & 112) != $i0) {
            this.mGravity = (this.mGravity & -113) | $i0;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    protected LayoutParams generateDefaultLayoutParams() throws  {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        return this.mOrientation == 1 ? new LayoutParams(-1, -2) : null;
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return new LayoutParams($r1);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return $r1 instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent($r1);
            $r1.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo $r1) throws  {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo($r1);
            $r1.setClassName(LinearLayoutCompat.class.getName());
        }
    }
}
