package com.waze.ifs.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

public class BalloonTooltip {
    private View mAnchor;
    private Rect mAnchorRect;
    private int mAnchorWidth;
    protected Context mContext;
    private final float mDensity;
    private long mLastProgressUpdate;
    private int mLastTopLocation;
    private int mLastTopVelocity;
    private boolean mOnTop;
    private int mPeakVelocity = 0;
    private int mProgress;
    private int mRootHeight;
    private TextView mTextView;
    private View mTipBg;
    protected View mView;
    protected PopupWindow mWindow;

    class C17071 implements AnimationListener {

        class C17061 implements Runnable {
            C17061() throws  {
            }

            public void run() throws  {
                BalloonTooltip.this.mWindow.dismiss();
                BalloonTooltip.this.mAnchor = null;
            }
        }

        C17071() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            BalloonTooltip.this.mView.post(new C17061());
        }
    }

    public void show(android.view.View r40) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:17:0x019c in {1, 4, 9, 12, 15, 16, 18, 20, 23} preds:[]
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
        r39 = this;
        if (r40 != 0) goto L_0x0003;
    L_0x0002:
        return;
    L_0x0003:
        r0 = r40;
        r1 = r39;
        r1.mAnchor = r0;
        r10 = 2;
        r9 = new int[r10];
        r0 = r40;
        r0.getLocationInWindow(r9);
        r0 = r40;
        r11 = r0.getWidth();
        r0 = r39;
        r12 = r0.mDensity;
        r13 = 1117257728; // 0x42980000 float:76.0 double:5.51998661E-315;
        r12 = r12 * r13;
        r14 = (int) r12;
        r11 = r11 - r14;
        r0 = r39;
        r0.mAnchorWidth = r11;
        r15 = new android.graphics.Rect;
        r10 = 0;
        r11 = r9[r10];
        r10 = 1;
        r14 = r9[r10];
        r10 = 0;
        r16 = r9[r10];
        r0 = r39;
        r0 = r0.mAnchorWidth;
        r17 = r0;
        r0 = r16;
        r1 = r17;
        r0 = r0 + r1;
        r16 = r0;
        r10 = 1;
        r17 = r9[r10];
        r0 = r40;
        r18 = r0.getHeight();
        r0 = r17;
        r1 = r18;
        r0 = r0 + r1;
        r17 = r0;
        r0 = r16;
        r1 = r17;
        r15.<init>(r11, r14, r0, r1);
        r0 = r39;
        r0.mAnchorRect = r15;
        r0 = r39;
        r0 = r0.mView;
        r19 = r0;
        r10 = -2;
        r20 = -2;
        r0 = r19;
        r1 = r20;
        r0.measure(r10, r1);
        r0 = r39;
        r0 = r0.mView;
        r19 = r0;
        r11 = r0.getMeasuredHeight();
        r0 = r39;
        r0.mRootHeight = r11;
        r0 = r39;
        r0 = r0.mView;
        r19 = r0;
        r0.getMeasuredWidth();
        r0 = r39;
        r11 = r0.mAnchorWidth;
        r0 = r39;
        r14 = r0.mProgress;
        r11 = r11 * r14;
        r11 = r11 / 100;
        r0 = r39;
        r0 = r0.mContext;
        r21 = r0;
        r22 = r0.getResources();
        r0 = r22;
        r23 = r0.getDisplayMetrics();
        r0 = r23;
        r14 = r0.widthPixels;
        r0 = r39;
        r0 = r0.mContext;
        r21 = r0;
        r22 = r0.getResources();
        r0 = r22;
        r23 = r0.getDisplayMetrics();
        r0 = r23;
        r14 = r0.heightPixels;
        r0 = r39;
        r15 = r0.mAnchorRect;
        r14 = r15.top;
        r0 = r39;
        r0 = r0.mRootHeight;
        r16 = r0;
        r14 = r14 - r0;
        r0 = r39;
        r12 = r0.mDensity;
        r13 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
        r12 = r12 * r13;
        r0 = (int) r12;
        r16 = r0;
        r14 = r14 + r0;
        r10 = 1;
        r0 = r39;
        r0.mOnTop = r10;
        if (r14 >= 0) goto L_0x00e9;
    L_0x00d2:
        r0 = r39;
        r15 = r0.mAnchorRect;
        r14 = r15.bottom;
        r0 = r39;
        r12 = r0.mDensity;
        r13 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
        r12 = r12 * r13;
        r0 = (int) r12;
        r16 = r0;
        r14 = r14 - r0;
        r10 = 0;
        r0 = r39;
        r0.mOnTop = r10;
    L_0x00e9:
        r0 = r39;
        r15 = r0.mAnchorRect;
        r0 = r15.left;
        r16 = r0;
        r11 = r16 + r11;
        r0 = r39;
        r12 = r0.mDensity;
        r13 = 1104674816; // 0x41d80000 float:27.0 double:5.457818764E-315;
        r12 = r12 * r13;
        r0 = (int) r12;
        r16 = r0;
        r11 = r11 - r0;
        r0 = r39;
        r0 = r0.mView;
        r19 = r0;
        r10 = 2131690005; // 0x7f0f0215 float:1.9009041E38 double:1.053194799E-314;
        r0 = r19;
        r19 = r0.findViewById(r10);
        r0 = r19;
        r1 = r39;
        r1.mTipBg = r0;
        r0 = r39;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 != 0) goto L_0x011c;
    L_0x011c:
        r0 = r39;
        r0 = r0.mTipBg;
        r19 = r0;
        r0 = r39;
        r12 = r0.mDensity;
        r13 = 1097859072; // 0x41700000 float:15.0 double:5.424144515E-315;
        r12 = r12 * r13;
        r0 = (int) r12;
        r16 = r0;
        r0 = r39;
        r12 = r0.mDensity;
        r0 = r39;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x0216;
    L_0x0139:
        r25 = 0;
    L_0x013b:
        r0 = r25;
        r0 = (float) r0;
        r26 = r0;
        r12 = r26 * r12;
        r0 = (int) r12;
        r17 = r0;
        r0 = r39;
        r12 = r0.mDensity;
        r13 = 1097859072; // 0x41700000 float:15.0 double:5.424144515E-315;
        r12 = r12 * r13;
        r0 = (int) r12;
        r18 = r0;
        r0 = r39;
        r12 = r0.mDensity;
        r0 = r39;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x0219;
    L_0x015c:
        r25 = 10;
    L_0x015e:
        r0 = r25;
        r0 = (float) r0;
        r26 = r0;
        r12 = r26 * r12;
        r0 = (int) r12;
        r27 = r0;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r3 = r18;
        r4 = r27;
        r0.setPadding(r1, r2, r3, r4);
        r0 = r39;
        r0 = r0.mWindow;
        r28 = r0;
        r10 = 0;
        r0 = r28;
        r1 = r40;
        r0.showAtLocation(r1, r10, r11, r14);
        r29 = new android.view.animation.AnimationSet;
        r10 = 0;
        r0 = r29;
        r0.<init>(r10);
        r30 = new android.view.animation.ScaleAnimation;
        r0 = r39;
        r0 = r0.mOnTop;
        r24 = r0;
        if (r24 == 0) goto L_0x021c;
    L_0x0195:
        r12 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        goto L_0x01a0;
    L_0x0199:
        goto L_0x013b;
        goto L_0x01a0;
    L_0x019d:
        goto L_0x015e;
    L_0x01a0:
        r13 = 0;
        r31 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r32 = 0;
        r33 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 1;
        r34 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r20 = 1;
        r0 = r30;
        r1 = r13;
        r2 = r31;
        r3 = r32;
        r4 = r33;
        r5 = r10;
        r6 = r34;
        r7 = r20;
        r8 = r12;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);
        r35 = 100;
        r0 = r30;
        r1 = r35;
        r0.setDuration(r1);
        r37 = new android.view.animation.OvershootInterpolator;
        r0 = r37;
        r0.<init>();
        r0 = r30;
        r1 = r37;
        r0.setInterpolator(r1);
        r0 = r29;
        r1 = r30;
        r0.addAnimation(r1);
        r38 = new android.view.animation.AlphaAnimation;
        r13 = 1045220557; // 0x3e4ccccd float:0.2 double:5.164075695E-315;
        r31 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r38;
        r1 = r31;
        r0.<init>(r13, r1);
        r35 = 100;
        r0 = r38;
        r1 = r35;
        r0.setDuration(r1);
        r10 = 1;
        r0 = r38;
        r0.setFillBefore(r10);
        r10 = 1;
        r0 = r38;
        r0.setFillAfter(r10);
        r0 = r29;
        r1 = r38;
        r0.addAnimation(r1);
        r0 = r39;
        r0 = r0.mTipBg;
        r40 = r0;
        r1 = r29;
        r0.startAnimation(r1);
        return;
    L_0x0216:
        r25 = 10;
        goto L_0x0199;
    L_0x0219:
        r25 = 0;
        goto L_0x019d;
    L_0x021c:
        r12 = 0;
        goto L_0x01a0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.BalloonTooltip.show(android.view.View):void");
    }

    public BalloonTooltip(Context $r1) throws  {
        this.mContext = $r1;
        this.mView = ((LayoutInflater) $r1.getSystemService("layout_inflater")).inflate(C1283R.layout.balloon_tooltip, null);
        this.mTextView = (TextView) this.mView.findViewById(C1283R.id.balloonTooltipText);
        this.mWindow = new PopupWindow($r1);
        this.mWindow.setContentView(this.mView);
        this.mWindow.setWidth(-2);
        this.mWindow.setHeight(-2);
        this.mWindow.setTouchable(false);
        this.mWindow.setFocusable(false);
        this.mWindow.setOutsideTouchable(false);
        this.mWindow.setBackgroundDrawable(null);
        this.mWindow.setClippingEnabled(false);
        this.mWindow.setContentView(this.mView);
        this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
    }

    public void setProgress(int $i0) throws  {
        this.mTextView.setText("" + $i0);
        long $l3 = System.currentTimeMillis();
        if (isShowing()) {
            int i;
            int $i2 = this.mDensity * 27.0f;
            int i2 = $i2;
            $i2 = (int) $i2;
            int $i1 = (this.mAnchorRect.left + ((this.mAnchorWidth * $i0) / 100)) - $i2;
            if (this.mOnTop) {
                $i2 = this.mDensity * 30.0f;
                i2 = $i2;
                i = (this.mAnchorRect.top - this.mRootHeight) + ((int) $i2);
            } else {
                $i2 = this.mDensity * 30.0f;
                i2 = $i2;
                i = this.mAnchorRect.bottom - ((int) $i2);
            }
            this.mWindow.update($i1, i, -1, -1);
            long $l5 = ($l3 - this.mLastProgressUpdate) + 1;
            long j = $l5;
            $i1 = ((this.mProgress - $i0) * 1000) / ((int) $l5);
            i = Math.abs($i1);
            $i2 = this.mPeakVelocity;
            int i3 = $i2;
            if (i > Math.abs($i2)) {
                this.mPeakVelocity = $i1;
                i = $i1 / 20;
                Log.e("randebug", "setProgress velocity=" + $i1 + " angle=" + i);
                if (i > 35) {
                    i = 35;
                }
                if (i < -35) {
                    i = -35;
                }
                Animation rotateAnimation = new RotateAnimation((float) i, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, this.mOnTop ? 1.0f : 0.0f);
                rotateAnimation.setDuration(750);
                rotateAnimation.setStartOffset(50);
                rotateAnimation.setInterpolator(new OvershootInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
                View view = this.mTipBg;
                View $r8 = view;
                view.startAnimation(rotateAnimation);
            } else {
                this.mPeakVelocity = ((this.mPeakVelocity * 9) + $i1) / 10;
            }
        } else {
            this.mLastTopLocation = $i0;
        }
        this.mProgress = $i0;
        this.mLastProgressUpdate = $l3;
    }

    public void dismiss(boolean $z0) throws  {
        if ($z0) {
            AnimationSet $r3 = new AnimationSet(false);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, this.mOnTop ? 1.0f : 0.0f);
            scaleAnimation.setDuration(100);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            $r3.addAnimation(scaleAnimation);
            Animation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
            alphaAnimation.setDuration(100);
            alphaAnimation.setStartOffset(50);
            alphaAnimation.setFillBefore(true);
            alphaAnimation.setFillAfter(true);
            $r3.addAnimation(alphaAnimation);
            View $r6 = this.mTipBg;
            $r6.startAnimation($r3);
            $r3.setAnimationListener(new C17071());
            return;
        }
        this.mWindow.dismiss();
        this.mAnchor = null;
    }

    public boolean isShowing() throws  {
        return this.mWindow.isShowing();
    }
}
