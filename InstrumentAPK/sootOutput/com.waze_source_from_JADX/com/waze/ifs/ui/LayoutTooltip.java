package com.waze.ifs.ui;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.PopupWindow;
import com.waze.LayoutManager;

public class LayoutTooltip {
    private static int BOTTOM = 45;
    private static int TOP = 25;
    private static int WIDTH_PADDING = 20;
    protected View mAnchor;
    protected FrameLayout mBg;
    protected Context mContext;
    private int mDX = 0;
    private int mDY = 0;
    protected float mDensity;
    private int mHideAnimRes = -1;
    private boolean mOnTop;
    private Paint mPaint;
    protected boolean mPreferLeft = true;
    protected boolean mPreferTop = true;
    private int mShowAnimRes = -1;
    private View mTipBubble;
    protected View mView;
    protected PopupWindow mWindow;
    private int mXPos;
    private int mYPos;

    class C17341 implements OnTouchListener {
        C17341() throws  {
        }

        public boolean onTouch(View v, MotionEvent event) throws  {
            LayoutTooltip.this.dismiss();
            return false;
        }
    }

    class C17352 implements AnimationListener {
        C17352() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            LayoutTooltip.this.mWindow.dismiss();
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    private void createBgBitmap(android.view.View r29, int r30, int r31, boolean r32, int r33) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:20:0x0180
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
        r28 = this;
        r3 = android.os.Build.VERSION.SDK_INT;
        r4 = 17;
        if (r3 >= r4) goto L_0x0163;
    L_0x0006:
        r5 = android.graphics.Bitmap.Config.ARGB_8888;
        r0 = r30;
        r1 = r31;
        r6 = android.graphics.Bitmap.createBitmap(r0, r1, r5);
        r7 = r6;
        r0 = r29;
        r8 = r0.getContext();
        r9 = r8.getResources();
        r10 = r9.getDisplayMetrics();
        r3 = r10.densityDpi;
        r6.setDensity(r3);
    L_0x0024:
        r11 = new android.graphics.Canvas;
        r11.<init>(r7);
        r12 = new android.graphics.Path;
        r12.<init>();
        r12.reset();
        r0 = r28;
        r13 = r0.mDensity;
        if (r32 == 0) goto L_0x0184;
    L_0x0037:
        r14 = 15;
    L_0x0039:
        r15 = (float) r14;
        r13 = r13 * r15;
        r0 = r31;
        r15 = (float) r0;
        r0 = r28;
        r0 = r0.mDensity;
        r16 = r0;
        if (r32 == 0) goto L_0x018b;
    L_0x0046:
        r14 = 35;
    L_0x0048:
        r0 = (float) r14;
        r17 = r0;
        r16 = r17 * r16;
        r0 = r16;
        r15 = r15 - r0;
        r16 = r15 - r13;
        r0 = r28;
        r0 = r0.mDensity;
        r17 = r0;
        r18 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r0 = r17;
        r1 = r18;
        r0 = r0 * r1;
        r17 = r0;
        r17 = r16 + r17;
        r0 = r30;
        r0 = (float) r0;
        r19 = r0;
        r1 = r16;
        r0 = r0 - r1;
        r19 = r0;
        r0 = r28;
        r0 = r0.mDensity;
        r20 = r0;
        r18 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r0 = r20;
        r1 = r18;
        r0 = r0 * r1;
        r20 = r0;
        r21 = r19 - r20;
        r0 = r28;
        r0 = r0.mDensity;
        r19 = r0;
        r18 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r20 = r19 * r18;
        r0 = r28;
        r0 = r0.mDensity;
        r19 = r0;
        r18 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r0 = r19;
        r1 = r18;
        r0 = r0 * r1;
        r19 = r0;
        r0 = r17;
        r12.moveTo(r0, r13);
        if (r32 != 0) goto L_0x00ca;
    L_0x00a2:
        r0 = r33;
        r0 = (float) r0;
        r22 = r0;
        r1 = r19;
        r0 = r0 - r1;
        r22 = r0;
        r12.lineTo(r0, r13);
        r0 = r33;
        r0 = (float) r0;
        r22 = r0;
        r23 = r13 - r20;
        r0 = r22;
        r1 = r23;
        r12.lineTo(r0, r1);
        r0 = r33;
        r0 = (float) r0;
        r22 = r0;
        r1 = r19;
        r0 = r0 + r1;
        r22 = r0;
        r12.lineTo(r0, r13);
    L_0x00ca:
        r0 = r21;
        r12.lineTo(r0, r13);
        r24 = new android.graphics.RectF;
        r22 = r21 + r16;
        r0 = r24;
        r1 = r21;
        r2 = r22;
        r0.<init>(r1, r13, r2, r15);
        r18 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r25 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r0 = r24;
        r1 = r18;
        r2 = r25;
        r12.arcTo(r0, r1, r2);
        if (r32 == 0) goto L_0x0114;
    L_0x00ed:
        r0 = r33;
        r0 = (float) r0;
        r21 = r0;
        r1 = r19;
        r0 = r0 - r1;
        r21 = r0;
        r12.lineTo(r0, r15);
        r0 = r33;
        r0 = (float) r0;
        r21 = r0;
        r20 = r15 + r20;
        r0 = r21;
        r1 = r20;
        r12.lineTo(r0, r1);
        r0 = r33;
        r0 = (float) r0;
        r20 = r0;
        r19 = r20 + r19;
        r0 = r19;
        r12.lineTo(r0, r15);
    L_0x0114:
        r0 = r28;
        r0 = r0.mDensity;
        r19 = r0;
        r18 = 1097859072; // 0x41700000 float:15.0 double:5.424144515E-315;
        r0 = r19;
        r1 = r18;
        r0 = r0 * r1;
        r19 = r0;
        r12.lineTo(r0, r15);
        r16 = r17 - r16;
        r0 = r24;
        r1 = r16;
        r2 = r17;
        r0.set(r1, r13, r2, r15);
        r18 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r25 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r0 = r24;
        r1 = r18;
        r2 = r25;
        r12.arcTo(r0, r1, r2);
        r0 = r28;
        r0 = r0.mPaint;
        r26 = r0;
        r11.drawPath(r12, r0);
        r27 = new android.graphics.drawable.BitmapDrawable;
        r0 = r29;
        r8 = r0.getContext();
        r9 = r8.getResources();
        r0 = r27;
        r0.<init>(r9, r7);
        r0 = r29;
        r1 = r27;
        r0.setBackgroundDrawable(r1);
        return;
    L_0x0163:
        r0 = r29;
        r8 = r0.getContext();
        r9 = r8.getResources();
        r10 = r9.getDisplayMetrics();
        r5 = android.graphics.Bitmap.Config.ARGB_8888;
        goto L_0x0177;
    L_0x0174:
        goto L_0x0024;
    L_0x0177:
        r0 = r30;
        r1 = r31;
        r7 = android.graphics.Bitmap.createBitmap(r10, r0, r1, r5);
        goto L_0x0174;
        goto L_0x0184;
    L_0x0181:
        goto L_0x0039;
    L_0x0184:
        r14 = 25;
        goto L_0x0181;
        goto L_0x018b;
    L_0x0188:
        goto L_0x0048;
    L_0x018b:
        r14 = 25;
        goto L_0x0188;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.LayoutTooltip.createBgBitmap(android.view.View, int, int, boolean, int):void");
    }

    private void setUpWindow(android.view.View r30) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:24:0x0267
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
        r29 = this;
        r7 = 2;
        r6 = new int[r7];
        r0 = r30;
        r0.getLocationInWindow(r6);
        r7 = 1;
        r8 = r6[r7];
        r0 = r30;
        r9 = r0.getHeight();
        r9 = r8 + r9;
        r7 = 0;
        r10 = r6[r7];
        r0 = r30;
        r11 = r0.getWidth();
        r11 = r11 / 2;
        r12 = r10 + r11;
        r0 = r29;
        r13 = r0.mContext;
        r14 = r13.getResources();
        r15 = r14.getDisplayMetrics();
        r0 = r15.widthPixels;
        r16 = r0;
        r0 = r29;
        r13 = r0.mContext;
        r14 = r13.getResources();
        r15 = r14.getDisplayMetrics();
        r11 = r15.heightPixels;
        r0 = r16;
        r0 = (float) r0;
        r17 = r0;
        r0 = r29;
        r0 = r0.mDensity;
        r18 = r0;
        r19 = 1114636288; // 0x42700000 float:60.0 double:5.507034975E-315;
        r0 = r18;
        r1 = r19;
        r0 = r0 * r1;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 - r1;
        r17 = r0;
        r10 = (int) r0;
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r10 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r7);
        r0 = (float) r11;
        r17 = r0;
        r0 = r29;
        r0 = r0.mDensity;
        r18 = r0;
        r19 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r0 = r18;
        r1 = r19;
        r0 = r0 * r1;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 - r1;
        r17 = r0;
        r0 = (int) r0;
        r20 = r0;
        r7 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r20;
        r20 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r0 = r29;
        r0 = r0.mView;
        r30 = r0;
        r1 = r20;
        r0.measure(r10, r1);
        r0 = r29;
        r0 = r0.mView;
        r30 = r0;
        r10 = r0.getMeasuredHeight();
        r0 = r29;
        r0 = r0.mView;
        r30 = r0;
        r20 = r0.getMeasuredWidth();
        r21 = r8 - r10;
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r19 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r0 = r17;
        r1 = r19;
        r0 = r0 * r1;
        r17 = r0;
        r0 = (int) r0;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0 = r0 + r1;
        r21 = r0;
        r0 = r29;
        r0 = r0.mDY;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0 = r0 + r1;
        r21 = r0;
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r19 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r0 = r17;
        r1 = r19;
        r0 = r0 * r1;
        r17 = r0;
        r0 = (int) r0;
        r22 = r0;
        r22 = r9 - r22;
        r0 = r29;
        r0 = r0.mDY;
        r23 = r0;
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r0 = r29;
        r0 = r0.mPreferTop;
        r24 = r0;
        if (r24 == 0) goto L_0x026e;
    L_0x00fa:
        if (r21 <= 0) goto L_0x026b;
    L_0x00fc:
        r24 = 1;
    L_0x00fe:
        r0 = r24;
        r1 = r29;
        r1.mOnTop = r0;
    L_0x0104:
        r0 = r29;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x028c;
    L_0x010c:
        r0 = r21;
        r1 = r29;
        r1.mYPos = r0;
    L_0x0112:
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r11 = TOP;
        r21 = BOTTOM;
        r0 = r21;
        r11 = r11 + r0;
        r0 = (float) r11;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 * r1;
        r17 = r0;
        r11 = (int) r0;
        r11 = r10 + r11;
        r21 = r10 / 2;
        r20 = r21 + r20;
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r21 = WIDTH_PADDING;
        r0 = r21;
        r0 = (float) r0;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 * r1;
        r17 = r0;
        r19 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r17;
        r1 = r19;
        r0 = r0 * r1;
        r17 = r0;
        r0 = (int) r0;
        r21 = r0;
        r0 = r20;
        r1 = r21;
        r0 = r0 + r1;
        r20 = r0;
        r21 = r20 / 2;
        r0 = r21;
        r0 = r0 + r12;
        r21 = r0;
        r0 = r29;
        r0 = r0.mDX;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0 = r0 + r1;
        r21 = r0;
        r22 = r20 / 2;
        r22 = r12 - r22;
        r0 = r29;
        r0 = r0.mDX;
        r23 = r0;
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        if (r22 < 0) goto L_0x0293;
    L_0x017f:
        r0 = r21;
        r1 = r16;
        if (r0 > r1) goto L_0x0293;
    L_0x0185:
        r16 = r22;
    L_0x0187:
        r0 = r16;
        r12 = r12 - r0;
        r0 = r29;
        r0 = r0.mDX;
        r21 = r0;
        r12 = r12 + r0;
        r0 = r29;
        r0 = r0.mTipBubble;
        r30 = r0;
        r0 = r29;
        r0 = r0.mOnTop;
        r24 = r0;
        r0 = r29;
        r1 = r30;
        r2 = r20;
        r3 = r11;
        r4 = r24;
        r5 = r12;
        r0.createBgBitmap(r1, r2, r3, r4, r5);
        r0 = r29;
        r0 = r0.mTipBubble;
        r30 = r0;
        r12 = r10 / 4;
        r0 = (float) r12;
        r17 = r0;
        r0 = r29;
        r0 = r0.mDensity;
        r18 = r0;
        r12 = WIDTH_PADDING;
        r0 = (float) r12;
        r25 = r0;
        r0 = r18;
        r1 = r25;
        r0 = r0 * r1;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 + r1;
        r17 = r0;
        r12 = (int) r0;
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r0 = r29;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x02cb;
    L_0x01dd:
        r20 = TOP;
    L_0x01df:
        r0 = r20;
        r0 = (float) r0;
        r18 = r0;
        r17 = r18 * r17;
        r0 = r17;
        r0 = (int) r0;
        r20 = r0;
        r10 = r10 / 4;
        r0 = (float) r10;
        r17 = r0;
        r0 = r29;
        r0 = r0.mDensity;
        r18 = r0;
        r10 = WIDTH_PADDING;
        r0 = (float) r10;
        r25 = r0;
        r0 = r18;
        r1 = r25;
        r0 = r0 * r1;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0 = r0 + r1;
        r17 = r0;
        r10 = (int) r0;
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r0 = r29;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x02dd;
    L_0x0218:
        r21 = BOTTOM;
    L_0x021a:
        r0 = r21;
        r0 = (float) r0;
        r18 = r0;
        r17 = r18 * r17;
        r0 = r17;
        r0 = (int) r0;
        r21 = r0;
        r0 = r30;
        r1 = r20;
        r2 = r21;
        r0.setPadding(r12, r1, r10, r2);
        r0 = r29;
        r0 = r0.mView;
        r30 = r0;
        r26 = r0.getLayoutParams();
        r28 = r26;
        r28 = (android.widget.FrameLayout.LayoutParams) r28;
        r27 = r28;
        r0 = r16;
        r1 = r27;
        r1.leftMargin = r0;
        r0 = r29;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x0262;
    L_0x024d:
        r0 = r29;
        r0 = r0.mDensity;
        r17 = r0;
        r19 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r0 = r17;
        r1 = r19;
        r0 = r0 * r1;
        r17 = r0;
        r9 = (int) r0;
        r9 = r11 - r9;
        r9 = r8 - r9;
    L_0x0262:
        r0 = r27;
        r0.topMargin = r9;
        return;
        goto L_0x026b;
    L_0x0268:
        goto L_0x00fe;
    L_0x026b:
        r24 = 0;
        goto L_0x0268;
    L_0x026e:
        r23 = r22 + r10;
        r0 = r23;
        if (r0 <= r11) goto L_0x0285;
    L_0x0274:
        r24 = 1;
        goto L_0x027a;
    L_0x0277:
        goto L_0x0104;
    L_0x027a:
        r0 = r24;
        r1 = r29;
        r1.mOnTop = r0;
        goto L_0x0277;
        goto L_0x0285;
    L_0x0282:
        goto L_0x021a;
    L_0x0285:
        r24 = 0;
        goto L_0x027a;
        goto L_0x028c;
    L_0x0289:
        goto L_0x0112;
    L_0x028c:
        r0 = r22;
        r1 = r29;
        r1.mYPos = r0;
        goto L_0x0289;
    L_0x0293:
        if (r22 < 0) goto L_0x02b7;
    L_0x0295:
        r0 = r16;
        r1 = r20;
        r0 = r0 - r1;
        r16 = r0;
        r0 = r29;
        r0 = r0.mDX;
        r21 = r0;
        r0 = r16;
        r1 = r21;
        r0 = r0 + r1;
        r16 = r0;
        if (r16 >= 0) goto L_0x02b6;
    L_0x02ab:
        goto L_0x02af;
    L_0x02ac:
        goto L_0x0187;
    L_0x02af:
        r16 = 0;
        goto L_0x02b5;
    L_0x02b2:
        goto L_0x0187;
    L_0x02b5:
        goto L_0x02ac;
    L_0x02b6:
        goto L_0x02b2;
    L_0x02b7:
        r0 = r21;
        r1 = r16;
        if (r0 > r1) goto L_0x02c8;
    L_0x02bd:
        goto L_0x02c1;
    L_0x02be:
        goto L_0x0187;
    L_0x02c1:
        r16 = 0;
        goto L_0x02be;
        goto L_0x02c8;
    L_0x02c5:
        goto L_0x0187;
    L_0x02c8:
        r16 = 0;
        goto L_0x02c5;
    L_0x02cb:
        r20 = TOP;
        r21 = BOTTOM;
        r0 = r20;
        r1 = r21;
        r0 = r0 + r1;
        r20 = r0;
        goto L_0x02da;
    L_0x02d7:
        goto L_0x01df;
    L_0x02da:
        r20 = r20 / 2;
        goto L_0x02d7;
    L_0x02dd:
        r22 = TOP;
        r21 = BOTTOM;
        r21 = r22 + r21;
        r21 = r21 / 2;
        goto L_0x0282;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.LayoutTooltip.setUpWindow(android.view.View):void");
    }

    public LayoutTooltip(Context $r1, int $i0, int $i1) throws  {
        LayoutTooltip layoutTooltip = this;
        this.mContext = $r1;
        this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
        LayoutInflater $r7 = (LayoutInflater) $r1.getSystemService("layout_inflater");
        this.mBg = new FrameLayout($r1);
        this.mBg.setClipChildren(false);
        this.mBg.setClipToPadding(false);
        $r7.inflate($i0, this.mBg);
        this.mView = this.mBg.getChildAt(0);
        LayoutParams $r2 = new LayoutParams(-2, -2);
        $r2.gravity = 51;
        this.mView.setLayoutParams($r2);
        this.mWindow = new PopupWindow($r1);
        this.mWindow.setTouchable(true);
        this.mWindow.setBackgroundDrawable(null);
        this.mWindow.setWindowLayoutMode(-1, -1);
        this.mWindow.setContentView(this.mBg);
        if ($i1 > 0) {
            this.mTipBubble = this.mView.findViewById($i1);
        } else {
            this.mTipBubble = this.mView;
        }
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Style.FILL_AND_STROKE);
        this.mPaint.setColor(-1);
        this.mPaint.setShadowLayer(this.mDensity * 5.0f, 0.0f, this.mDensity * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, 1275068416);
    }

    public void setBackgroundColor(int $i0) throws  {
        this.mWindow.setBackgroundDrawable(new ColorDrawable($i0));
    }

    public void setTouchable(boolean $z0) throws  {
        this.mWindow.setTouchable($z0);
    }

    public void setDismissOnBgTouch(boolean $z0) throws  {
        if ($z0) {
            this.mBg.setOnTouchListener(new C17341());
        } else {
            this.mBg.setOnTouchListener(null);
        }
        this.mWindow.setTouchable(true);
    }

    public View getBackground() throws  {
        return this.mBg;
    }

    public void setColor(int $i0) throws  {
        this.mPaint.setColor($i0);
    }

    public View getView() throws  {
        return this.mView;
    }

    public void setAnchor(View $r1) throws  {
        this.mAnchor = $r1;
    }

    public void show() throws  {
        if (this.mAnchor != null) {
            show(this.mAnchor);
        }
    }

    public void show(View $r1) throws  {
        if ($r1 != null) {
            setUpWindow($r1);
            this.mWindow.showAtLocation($r1, 0, 0, 0);
            if (this.mShowAnimRes < 0) {
                AlphaAnimation $r2 = new AlphaAnimation(0.0f, 1.0f);
                $r2.setDuration(1000);
                this.mView.startAnimation($r2);
            } else if (this.mShowAnimRes > 0) {
                this.mView.startAnimation(AnimationUtils.loadAnimation(this.mView.getContext(), this.mShowAnimRes));
            }
        }
    }

    public void move(View $r1) throws  {
        if ($r1 != null) {
            setUpWindow($r1);
        }
    }

    public void dismiss() throws  {
        this.mWindow.dismiss();
    }

    public void dismiss(boolean $z0) throws  {
        if ($z0) {
            Animation $r2 = null;
            if (this.mHideAnimRes < 0) {
                $r2 = r6;
                Animation r6 = new AlphaAnimation(1.0f, 0.0f);
                $r2.setDuration(1000);
            } else if (this.mHideAnimRes > 0) {
                $r2 = AnimationUtils.loadAnimation(this.mView.getContext(), this.mHideAnimRes);
            }
            if ($r2 == null) {
                this.mWindow.dismiss();
                return;
            }
            $r2.setAnimationListener(new C17352());
            this.mView.startAnimation($r2);
            return;
        }
        this.mWindow.dismiss();
    }

    public boolean isShowing() throws  {
        return this.mWindow.isShowing();
    }

    public void setDelta(int $i0, int $i1) throws  {
        this.mDX = $i0;
        this.mDY = $i1;
    }

    public void setAnimations(int $i0, int $i1) throws  {
        this.mShowAnimRes = $i0;
        this.mHideAnimRes = $i1;
    }
}
