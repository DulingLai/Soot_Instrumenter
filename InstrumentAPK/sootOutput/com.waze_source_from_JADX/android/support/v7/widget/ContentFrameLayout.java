package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class ContentFrameLayout extends FrameLayout {
    private OnAttachListener mAttachListener;
    private final Rect mDecorPadding;
    private TypedValue mFixedHeightMajor;
    private TypedValue mFixedHeightMinor;
    private TypedValue mFixedWidthMajor;
    private TypedValue mFixedWidthMinor;
    private TypedValue mMinWidthMajor;
    private TypedValue mMinWidthMinor;

    public interface OnAttachListener {
        void onAttachedFromWindow() throws ;

        void onDetachedFromWindow() throws ;
    }

    protected void onMeasure(int r20, int r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:38:0x00d7 in {2, 6, 12, 14, 18, 24, 27, 29, 30, 31, 39, 41, 50, 51, 54, 57, 60, 70, 73, 74} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r19 = this;
        r0 = r19;
        r3 = r0.getContext();
        r4 = r3.getResources();
        r5 = r4.getDisplayMetrics();
        r6 = r5.widthPixels;
        r7 = r5.heightPixels;
        if (r6 >= r7) goto L_0x0126;
    L_0x0014:
        r8 = 1;
    L_0x0015:
        r0 = r20;
        r6 = android.view.View.MeasureSpec.getMode(r0);
        r0 = r21;
        r7 = android.view.View.MeasureSpec.getMode(r0);
        r9 = 0;
        r10 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r6 != r10) goto L_0x0066;
    L_0x0027:
        if (r8 == 0) goto L_0x0128;
    L_0x0029:
        r0 = r19;
        r11 = r0.mFixedWidthMinor;
    L_0x002d:
        if (r11 == 0) goto L_0x0066;
    L_0x002f:
        r12 = r11.type;
        if (r12 == 0) goto L_0x0066;
    L_0x0033:
        r12 = 0;
        r13 = r11.type;
        r10 = 5;
        if (r13 != r10) goto L_0x012d;
    L_0x0039:
        r14 = r11.getDimension(r5);
        r12 = (int) r14;
    L_0x003e:
        if (r12 <= 0) goto L_0x0066;
    L_0x0040:
        r0 = r19;
        r15 = r0.mDecorPadding;
        r13 = r15.left;
        r0 = r19;
        r15 = r0.mDecorPadding;
        r0 = r15.right;
        r16 = r0;
        r13 = r13 + r0;
        r12 = r12 - r13;
        r0 = r20;
        r20 = android.view.View.MeasureSpec.getSize(r0);
        r0 = r20;
        r20 = java.lang.Math.min(r12, r0);
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r20;
        r20 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r10);
        r9 = 1;
    L_0x0066:
        r10 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r7 != r10) goto L_0x00b3;
    L_0x006b:
        if (r8 == 0) goto L_0x0140;
    L_0x006d:
        r0 = r19;
        r11 = r0.mFixedHeightMajor;
    L_0x0071:
        if (r11 == 0) goto L_0x00b3;
    L_0x0073:
        r7 = r11.type;
        if (r7 == 0) goto L_0x00b3;
    L_0x0077:
        r7 = 0;
        r12 = r11.type;
        r10 = 5;
        if (r12 != r10) goto L_0x0145;
    L_0x007d:
        r14 = r11.getDimension(r5);
        r7 = (int) r14;
    L_0x0082:
        if (r7 <= 0) goto L_0x00b3;
    L_0x0084:
        r0 = r19;
        r15 = r0.mDecorPadding;
        r12 = r15.top;
        goto L_0x008e;
    L_0x008b:
        goto L_0x0015;
    L_0x008e:
        r0 = r19;
        r15 = r0.mDecorPadding;
        r13 = r15.bottom;
        r12 = r12 + r13;
        r7 = r7 - r12;
        r0 = r21;
        r21 = android.view.View.MeasureSpec.getSize(r0);
        goto L_0x00a0;
    L_0x009d:
        goto L_0x002d;
    L_0x00a0:
        r0 = r21;
        r21 = java.lang.Math.min(r7, r0);
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r21;
        r21 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r10);
        goto L_0x00b3;
    L_0x00b0:
        goto L_0x003e;
    L_0x00b3:
        r0 = r19;
        r1 = r20;
        r2 = r21;
        super.onMeasure(r1, r2);
        r0 = r19;
        r20 = r0.getMeasuredWidth();
        r17 = 0;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r20;
        r7 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r10);
        if (r9 != 0) goto L_0x011c;
    L_0x00cf:
        r10 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r6 != r10) goto L_0x011c;
    L_0x00d4:
        if (r8 == 0) goto L_0x0158;
    L_0x00d6:
        goto L_0x00de;
        goto L_0x00db;
    L_0x00d8:
        goto L_0x0071;
    L_0x00db:
        goto L_0x008b;
    L_0x00de:
        r0 = r19;
        r11 = r0.mMinWidthMinor;
    L_0x00e2:
        if (r11 == 0) goto L_0x011c;
    L_0x00e4:
        goto L_0x00e8;
    L_0x00e5:
        goto L_0x009d;
    L_0x00e8:
        r6 = r11.type;
        if (r6 == 0) goto L_0x011c;
    L_0x00ec:
        r6 = 0;
        r12 = r11.type;
        r10 = 5;
        if (r12 != r10) goto L_0x015d;
    L_0x00f2:
        goto L_0x00f6;
    L_0x00f3:
        goto L_0x0082;
    L_0x00f6:
        r14 = r11.getDimension(r5);
        r6 = (int) r14;
        goto L_0x00ff;
    L_0x00fc:
        goto L_0x00b0;
    L_0x00ff:
        if (r6 <= 0) goto L_0x010f;
    L_0x0101:
        r0 = r19;
        r15 = r0.mDecorPadding;
        r12 = r15.left;
        r0 = r19;
        r15 = r0.mDecorPadding;
        r13 = r15.right;
        r12 = r12 + r13;
        r6 = r6 - r12;
    L_0x010f:
        r0 = r20;
        if (r0 >= r6) goto L_0x011c;
    L_0x0113:
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r10);
        r17 = 1;
    L_0x011c:
        if (r17 == 0) goto L_0x0170;
    L_0x011e:
        r0 = r19;
        r1 = r21;
        super.onMeasure(r7, r1);
        return;
    L_0x0126:
        r8 = 0;
        goto L_0x00db;
    L_0x0128:
        r0 = r19;
        r11 = r0.mFixedWidthMajor;
        goto L_0x00e5;
    L_0x012d:
        r13 = r11.type;
        r10 = 6;
        if (r13 != r10) goto L_0x003e;
    L_0x0132:
        r12 = r5.widthPixels;
        r14 = (float) r12;
        r12 = r5.widthPixels;
        r0 = (float) r12;
        r18 = r0;
        r14 = r11.getFraction(r14, r0);
        r12 = (int) r14;
        goto L_0x00fc;
    L_0x0140:
        r0 = r19;
        r11 = r0.mFixedHeightMinor;
        goto L_0x00d8;
    L_0x0145:
        r12 = r11.type;
        r10 = 6;
        if (r12 != r10) goto L_0x0082;
    L_0x014a:
        r7 = r5.heightPixels;
        r14 = (float) r7;
        r7 = r5.heightPixels;
        r0 = (float) r7;
        r18 = r0;
        r14 = r11.getFraction(r14, r0);
        r7 = (int) r14;
        goto L_0x00f3;
    L_0x0158:
        r0 = r19;
        r11 = r0.mMinWidthMajor;
        goto L_0x00e2;
    L_0x015d:
        r12 = r11.type;
        r10 = 6;
        if (r12 != r10) goto L_0x00ff;
    L_0x0162:
        r6 = r5.widthPixels;
        r14 = (float) r6;
        r6 = r5.widthPixels;
        r0 = (float) r6;
        r18 = r0;
        r14 = r11.getFraction(r14, r0);
        r6 = (int) r14;
        goto L_0x00ff;
    L_0x0170:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ContentFrameLayout.onMeasure(int, int):void");
    }

    public ContentFrameLayout(Context $r1) throws  {
        this($r1, null);
    }

    public ContentFrameLayout(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public ContentFrameLayout(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mDecorPadding = new Rect();
    }

    public void dispatchFitSystemWindows(Rect $r1) throws  {
        fitSystemWindows($r1);
    }

    public void setAttachListener(OnAttachListener $r1) throws  {
        this.mAttachListener = $r1;
    }

    public void setDecorPadding(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mDecorPadding.set($i0, $i1, $i2, $i3);
        if (ViewCompat.isLaidOut(this)) {
            requestLayout();
        }
    }

    public TypedValue getMinWidthMajor() throws  {
        if (this.mMinWidthMajor == null) {
            this.mMinWidthMajor = new TypedValue();
        }
        return this.mMinWidthMajor;
    }

    public TypedValue getMinWidthMinor() throws  {
        if (this.mMinWidthMinor == null) {
            this.mMinWidthMinor = new TypedValue();
        }
        return this.mMinWidthMinor;
    }

    public TypedValue getFixedWidthMajor() throws  {
        if (this.mFixedWidthMajor == null) {
            this.mFixedWidthMajor = new TypedValue();
        }
        return this.mFixedWidthMajor;
    }

    public TypedValue getFixedWidthMinor() throws  {
        if (this.mFixedWidthMinor == null) {
            this.mFixedWidthMinor = new TypedValue();
        }
        return this.mFixedWidthMinor;
    }

    public TypedValue getFixedHeightMajor() throws  {
        if (this.mFixedHeightMajor == null) {
            this.mFixedHeightMajor = new TypedValue();
        }
        return this.mFixedHeightMajor;
    }

    public TypedValue getFixedHeightMinor() throws  {
        if (this.mFixedHeightMinor == null) {
            this.mFixedHeightMinor = new TypedValue();
        }
        return this.mFixedHeightMinor;
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        if (this.mAttachListener != null) {
            this.mAttachListener.onAttachedFromWindow();
        }
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.mAttachListener != null) {
            this.mAttachListener.onDetachedFromWindow();
        }
    }
}
