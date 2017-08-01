package com.waze.ifs.ui;

import android.content.Context;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;

public class FullScreenTooltip {
    private float density;
    private ImageView mArrow;
    private Paint mArrowHeadPaint;
    private Paint mArrowLinePaint;
    private CheckBox mCheckBox;
    private ImageView mCircle;
    private Paint mCirclePaint;
    private Paint mClearPaint;
    private ImageButton mClose;
    protected Context mContext;
    private boolean mDismissCalled = false;
    private int mHeight = 0;
    private ImageView mHole;
    private IToolTipClicked mIToolTipClicked;
    private int mLeft = 0;
    private boolean mOnLeft;
    private boolean mOnTop;
    private boolean mShowAgain = false;
    private TextView mTextView;
    private TextView mTitleView;
    private int mTop = 0;
    protected View mView;
    private int mWidth = 0;
    protected PopupWindow mWindow;

    class C17232 implements OnClickListener {
        C17232() throws  {
        }

        public void onClick(View v) throws  {
            if (FullScreenTooltip.this.mIToolTipClicked != null) {
                FullScreenTooltip.this.mIToolTipClicked.onCloseClicked();
            }
        }
    }

    class C17243 implements OnCheckedChangeListener {
        C17243() throws  {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean $z0) throws  {
            FullScreenTooltip.this.mShowAgain = !$z0;
        }
    }

    class C17254 implements OnClickListener {
        C17254() throws  {
        }

        public void onClick(View v) throws  {
            if (FullScreenTooltip.this.mIToolTipClicked != null) {
                FullScreenTooltip.this.mIToolTipClicked.onBackgroundClicked();
            }
        }
    }

    class C17265 implements OnClickListener {
        C17265() throws  {
        }

        public void onClick(View v) throws  {
            if (FullScreenTooltip.this.mIToolTipClicked != null) {
                FullScreenTooltip.this.mIToolTipClicked.onHighLightClicked();
            }
        }
    }

    class C17276 implements AnimationListener {
        C17276() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            FullScreenTooltip.this.mWindow.dismiss();
        }
    }

    public interface IToolTipClicked {
        void onBackgroundClicked() throws ;

        void onCloseClicked() throws ;

        void onHighLightClicked() throws ;
    }

    public void initTooltip(android.view.View r36) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:65:0x048f
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
        r35 = this;
        r4 = 2;
        r3 = new int[r4];
        r0 = r36;
        r0.getLocationInWindow(r3);
        r4 = 0;
        r5 = r3[r4];
        r4 = 1;
        r6 = r3[r4];
        r0 = r36;
        r7 = r0.getWidth();
        r0 = r36;
        r8 = r0.getHeight();
        if (r7 >= r8) goto L_0x0457;
    L_0x001c:
        r0 = r35;
        r9 = r0.density;
        r10 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r9 = r10 * r9;
        r11 = (int) r9;
        r8 = r11 + r8;
        r0 = r35;
        r0.mHeight = r8;
        r0 = r35;
        r8 = r0.mHeight;
        r0 = r35;
        r0.mWidth = r8;
        r0 = r35;
        r9 = r0.density;
        r10 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r9 = r10 * r9;
        r8 = (int) r9;
        r6 = r6 - r8;
        r0 = r35;
        r0.mTop = r6;
        r0 = r35;
        r6 = r0.mWidth;
        r6 = r6 - r7;
        r6 = r6 / 2;
        r5 = r5 - r6;
        r0 = r35;
        r0.mLeft = r5;
    L_0x004f:
        r0 = r35;
        r12 = r0.mContext;
        r13 = r12.getResources();
        r14 = r13.getDisplayMetrics();
        r5 = r14.widthPixels;
        r0 = r35;
        r12 = r0.mContext;
        r13 = r12.getResources();
        r14 = r13.getDisplayMetrics();
        r6 = r14.heightPixels;
        r0 = r35;
        r0 = r0.mView;
        r36 = r0;
        r4 = 2131690764; // 0x7f0f050c float:1.901058E38 double:1.053195174E-314;
        r0 = r36;
        r36 = r0.findViewById(r4);
        r0 = r36;
        r15 = r0.getLayoutParams();
        r17 = r15;
        r17 = (android.widget.RelativeLayout.LayoutParams) r17;
        r16 = r17;
        r0 = r35;
        r6 = r0.mTop;
        r0 = r16;
        r0.height = r6;
        r0 = r36;
        r1 = r16;
        r0.setLayoutParams(r1);
        r0 = r35;
        r0 = r0.mView;
        r36 = r0;
        r4 = 2131690765; // 0x7f0f050d float:1.9010583E38 double:1.0531951745E-314;
        r0 = r36;
        r36 = r0.findViewById(r4);
        r0 = r36;
        r15 = r0.getLayoutParams();
        r18 = r15;
        r18 = (android.widget.RelativeLayout.LayoutParams) r18;
        r16 = r18;
        r0 = r35;
        r6 = r0.mLeft;
        r0 = r16;
        r0.width = r6;
        r0 = r36;
        r1 = r16;
        r0.setLayoutParams(r1);
        r0 = r35;
        r0 = r0.mCircle;
        r19 = r0;
        r15 = r0.getLayoutParams();
        r20 = r15;
        r20 = (android.widget.RelativeLayout.LayoutParams) r20;
        r16 = r20;
        r0 = r35;
        r6 = r0.mWidth;
        r0 = r16;
        r0.width = r6;
        r0 = r35;
        r6 = r0.mHeight;
        r0 = r16;
        r0.height = r6;
        r0 = r35;
        r0 = r0.mCircle;
        r19 = r0;
        r1 = r16;
        r0.setLayoutParams(r1);
        r0 = r35;
        r0 = r0.mHole;
        r19 = r0;
        r15 = r0.getLayoutParams();
        r21 = r15;
        r21 = (android.widget.RelativeLayout.LayoutParams) r21;
        r16 = r21;
        r0 = r35;
        r6 = r0.mWidth;
        r0 = r16;
        r0.width = r6;
        r0 = r35;
        r6 = r0.mHeight;
        r0 = r16;
        r0.height = r6;
        r0 = r35;
        r0 = r0.mHole;
        r19 = r0;
        r1 = r16;
        r0.setLayoutParams(r1);
        r0 = r35;
        r6 = r0.mHeight;
        r9 = (float) r6;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r9 = r9 / r10;
        r0 = r35;
        r6 = r0.mWidth;
        r0 = r35;
        r8 = r0.mHeight;
        r22 = android.graphics.Bitmap.Config.ARGB_8888;
        r0 = r22;
        r23 = android.graphics.Bitmap.createBitmap(r6, r8, r0);
        r24 = new android.graphics.Canvas;
        r0 = r24;
        r1 = r23;
        r0.<init>(r1);
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r25 = r9 - r10;
        r0 = r35;
        r0 = r0.mCirclePaint;
        r26 = r0;
        r0 = r24;
        r1 = r25;
        r2 = r26;
        r0.drawCircle(r9, r9, r1, r2);
        r0 = r35;
        r0 = r0.mCircle;
        r19 = r0;
        r1 = r23;
        r0.setImageBitmap(r1);
        r0 = r35;
        r6 = r0.mWidth;
        r0 = r35;
        r8 = r0.mHeight;
        r22 = android.graphics.Bitmap.Config.ARGB_8888;
        r0 = r22;
        r23 = android.graphics.Bitmap.createBitmap(r6, r8, r0);
        r0 = r35;
        r12 = r0.mContext;
        r13 = r12.getResources();
        r4 = 2131624063; // 0x7f0e007f float:1.8875295E38 double:1.0531622194E-314;
        r6 = r13.getColor(r4);
        r0 = r23;
        r0.eraseColor(r6);
        r24 = new android.graphics.Canvas;
        r0 = r24;
        r1 = r23;
        r0.<init>(r1);
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r25 = r9 - r10;
        r0 = r35;
        r0 = r0.mClearPaint;
        r26 = r0;
        r0 = r24;
        r1 = r25;
        r2 = r26;
        r0.drawCircle(r9, r9, r1, r2);
        r0 = r35;
        r0 = r0.mHole;
        r19 = r0;
        r1 = r23;
        r0.setImageBitmap(r1);
        r0 = r35;
        r6 = r0.mTop;
        r9 = (float) r6;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r0 = r25;
        r0 = r0 * r10;
        r25 = r0;
        r27 = (r9 > r25 ? 1 : (r9 == r25 ? 0 : -1));
        if (r27 <= 0) goto L_0x0493;
    L_0x01b9:
        r28 = 1;
    L_0x01bb:
        r0 = r28;
        r1 = r35;
        r1.mOnTop = r0;
        r0 = r35;
        r6 = r0.mLeft;
        r7 = r7 / 2;
        r7 = r6 + r7;
        r5 = r5 / 2;
        if (r7 <= r5) goto L_0x049a;
    L_0x01cd:
        r28 = 1;
    L_0x01cf:
        r0 = r28;
        r1 = r35;
        r1.mOnLeft = r0;
        r0 = r35;
        r0 = r0.mArrow;
        r19 = r0;
        r15 = r0.getLayoutParams();
        r29 = r15;
        r29 = (android.widget.RelativeLayout.LayoutParams) r29;
        r16 = r29;
        r0 = r35;
        r0 = r0.mOnTop;
        r28 = r0;
        if (r28 == 0) goto L_0x04a1;
    L_0x01ed:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
    L_0x01f0:
        r4 = 2;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnTop;
        r28 = r0;
        if (r28 == 0) goto L_0x04a7;
    L_0x01fe:
        r7 = 0;
    L_0x01ff:
        r4 = 3;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x04af;
    L_0x020d:
        r7 = 0;
    L_0x020e:
        r4 = 5;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x04b7;
    L_0x021c:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
    L_0x021f:
        r4 = 7;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x04b9;
    L_0x022d:
        r7 = 0;
    L_0x022e:
        r0 = r16;
        r0.leftMargin = r7;
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x04cd;
    L_0x023a:
        r0 = r35;
        r7 = r0.mWidth;
        r0 = r16;
        r5 = r0.width;
        r7 = r7 - r5;
        r7 = r7 / 2;
    L_0x0245:
        r0 = r16;
        r0.rightMargin = r7;
        r0 = r35;
        r0 = r0.mArrow;
        r19 = r0;
        r1 = r16;
        r0.setLayoutParams(r1);
        r0 = r16;
        r7 = r0.width;
        r0 = r16;
        r5 = r0.height;
        r22 = android.graphics.Bitmap.Config.ARGB_8888;
        r0 = r22;
        r23 = android.graphics.Bitmap.createBitmap(r7, r5, r0);
        r24 = new android.graphics.Canvas;
        r0 = r24;
        r1 = r23;
        r0.<init>(r1);
        r30 = new android.graphics.Path;
        r0 = r30;
        r0.<init>();
        r0 = r35;
        r0 = r0.mOnTop;
        r28 = r0;
        if (r28 == 0) goto L_0x04f1;
    L_0x027c:
        r0 = r35;
        r9 = r0.density;
        r10 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r9 = r10 * r9;
        r7 = (int) r9;
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x04cf;
    L_0x028e:
        r0 = r35;
        r9 = r0.density;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r9 = r10 * r9;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.moveTo(r9, r1);
    L_0x02a1:
        r0 = r16;
        r5 = r0.width;
        r5 = r5 / 2;
        r9 = (float) r5;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        r9 = (float) r5;
        r0 = r16;
        r7 = r0.height;
        r0 = (float) r7;
        r25 = r0;
        r0 = r35;
        r0 = r0.density;
        r31 = r0;
        r10 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r31 = r10 * r31;
        r0 = r25;
        r1 = r31;
        r0 = r0 - r1;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
    L_0x02d3:
        r0 = r35;
        r0 = r0.mArrowLinePaint;
        r26 = r0;
        r0 = r24;
        r1 = r30;
        r2 = r26;
        r0.drawPath(r1, r2);
        r30 = new android.graphics.Path;
        r0 = r30;
        r0.<init>();
        r0 = r35;
        r0 = r0.mOnTop;
        r28 = r0;
        if (r28 == 0) goto L_0x0562;
    L_0x02f1:
        r0 = r16;
        r7 = r0.height;
        r9 = (float) r7;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 - r0;
        r7 = (int) r9;
        r0 = r16;
        r5 = r0.width;
        r5 = r5 / 2;
        r9 = (float) r5;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.moveTo(r9, r1);
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 - r0;
        r0 = (float) r7;
        r25 = r0;
        r0 = r35;
        r0 = r0.density;
        r31 = r0;
        r10 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r31 = r10 * r31;
        r0 = r25;
        r1 = r31;
        r0 = r0 - r1;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 + r0;
        r0 = (float) r7;
        r25 = r0;
        r0 = r35;
        r0 = r0.density;
        r31 = r0;
        r10 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r31 = r10 * r31;
        r0 = r25;
        r1 = r31;
        r0 = r0 - r1;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        r9 = (float) r5;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
    L_0x0377:
        r0 = r35;
        r0 = r0.mArrowHeadPaint;
        r26 = r0;
        r0 = r24;
        r1 = r30;
        r2 = r26;
        r0.drawPath(r1, r2);
        r0 = r35;
        r0 = r0.mArrow;
        r19 = r0;
        r1 = r23;
        r0.setImageBitmap(r1);
        r0 = r35;
        r0 = r0.mTitleView;
        r32 = r0;
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x05e7;
    L_0x039f:
        r27 = 5;
    L_0x03a1:
        r0 = r32;
        r1 = r27;
        r0.setGravity(r1);
        r0 = r35;
        r0 = r0.mTitleView;
        r32 = r0;
        r15 = r0.getLayoutParams();
        r33 = r15;
        r33 = (android.widget.RelativeLayout.LayoutParams) r33;
        r16 = r33;
        r0 = r35;
        r0 = r0.mOnTop;
        r28 = r0;
        if (r28 == 0) goto L_0x05ee;
    L_0x03c0:
        r7 = 2131690767; // 0x7f0f050f float:1.9010587E38 double:1.0531951755E-314;
    L_0x03c3:
        r4 = 6;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnTop;
        r28 = r0;
        if (r28 == 0) goto L_0x05f4;
    L_0x03d1:
        r7 = 0;
    L_0x03d2:
        r4 = 8;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x05fc;
    L_0x03e1:
        r7 = 0;
    L_0x03e2:
        r4 = 1;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x0604;
    L_0x03f0:
        r7 = 2131690767; // 0x7f0f050f float:1.9010587E38 double:1.0531951755E-314;
    L_0x03f3:
        r4 = 0;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mTitleView;
        r32 = r0;
        r1 = r16;
        r0.setLayoutParams(r1);
        r0 = r35;
        r0 = r0.mTextView;
        r32 = r0;
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x060a;
    L_0x0412:
        r27 = 5;
    L_0x0414:
        r0 = r32;
        r1 = r27;
        r0.setGravity(r1);
        r0 = r35;
        r0 = r0.mTextView;
        r32 = r0;
        r15 = r0.getLayoutParams();
        r34 = r15;
        r34 = (android.widget.RelativeLayout.LayoutParams) r34;
        r16 = r34;
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x0611;
    L_0x0433:
        r7 = 0;
    L_0x0434:
        r4 = 5;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x0619;
    L_0x0442:
        r7 = 2131690768; // 0x7f0f0510 float:1.9010589E38 double:1.053195176E-314;
    L_0x0445:
        r4 = 7;
        r0 = r16;
        r0.addRule(r4, r7);
        r0 = r35;
        r0 = r0.mTextView;
        r32 = r0;
        r1 = r16;
        r0.setLayoutParams(r1);
        return;
    L_0x0457:
        r0 = r35;
        r9 = r0.density;
        r10 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r9 = r10 * r9;
        r11 = (int) r9;
        r11 = r11 + r7;
        r0 = r35;
        r0.mWidth = r11;
        r0 = r35;
        r11 = r0.mWidth;
        r0 = r35;
        r0.mHeight = r11;
        r0 = r35;
        r9 = r0.density;
        r10 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r9 = r10 * r9;
        r11 = (int) r9;
        r5 = r5 - r11;
        r0 = r35;
        r0.mLeft = r5;
        r0 = r35;
        r5 = r0.mHeight;
        r5 = r5 - r8;
        r5 = r5 / 2;
        r5 = r6 - r5;
        goto L_0x048a;
    L_0x0487:
        goto L_0x004f;
    L_0x048a:
        r0 = r35;
        r0.mTop = r5;
        goto L_0x0487;
        goto L_0x0493;
    L_0x0490:
        goto L_0x01bb;
    L_0x0493:
        r28 = 0;
        goto L_0x0490;
        goto L_0x049a;
    L_0x0497:
        goto L_0x01cf;
    L_0x049a:
        r28 = 0;
        goto L_0x0497;
        goto L_0x04a1;
    L_0x049e:
        goto L_0x01f0;
    L_0x04a1:
        r7 = 0;
        goto L_0x049e;
        goto L_0x04a7;
    L_0x04a4:
        goto L_0x01ff;
    L_0x04a7:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
        goto L_0x04a4;
        goto L_0x04af;
    L_0x04ac:
        goto L_0x020e;
    L_0x04af:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
        goto L_0x04ac;
        goto L_0x04b7;
    L_0x04b4:
        goto L_0x021f;
    L_0x04b7:
        r7 = 0;
        goto L_0x04b4;
    L_0x04b9:
        r0 = r35;
        r7 = r0.mWidth;
        r0 = r16;
        r5 = r0.width;
        r7 = r7 - r5;
        goto L_0x04c6;
    L_0x04c3:
        goto L_0x022e;
    L_0x04c6:
        r7 = r7 / 2;
        goto L_0x04c3;
        goto L_0x04cd;
    L_0x04ca:
        goto L_0x0245;
    L_0x04cd:
        r7 = 0;
        goto L_0x04ca;
    L_0x04cf:
        r0 = r16;
        r5 = r0.width;
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 - r0;
        r0 = (float) r7;
        r25 = r0;
        goto L_0x04e9;
    L_0x04e6:
        goto L_0x02a1;
    L_0x04e9:
        r0 = r30;
        r1 = r25;
        r0.moveTo(r9, r1);
        goto L_0x04e6;
    L_0x04f1:
        r0 = r16;
        r7 = r0.height;
        r0 = r35;
        r9 = r0.density;
        r10 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r9 = r10 * r9;
        r5 = (int) r9;
        r7 = r7 - r5;
        r0 = r35;
        r0 = r0.mOnLeft;
        r28 = r0;
        if (r28 == 0) goto L_0x0544;
    L_0x0508:
        r0 = r35;
        r9 = r0.density;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r9 = r10 * r9;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.moveTo(r9, r1);
    L_0x051b:
        r0 = r16;
        r5 = r0.width;
        r5 = r5 / 2;
        r9 = (float) r5;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r25 = r10 * r25;
        goto L_0x053c;
    L_0x0539:
        goto L_0x02d3;
    L_0x053c:
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        goto L_0x0539;
    L_0x0544:
        r0 = r16;
        r5 = r0.width;
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 - r0;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.moveTo(r9, r1);
        goto L_0x051b;
    L_0x0562:
        r0 = r35;
        r9 = r0.density;
        r10 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r9 = r10 * r9;
        r7 = (int) r9;
        r0 = r16;
        r5 = r0.width;
        r5 = r5 / 2;
        r9 = (float) r5;
        r0 = (float) r7;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.moveTo(r9, r1);
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 - r0;
        r0 = (float) r7;
        r25 = r0;
        r0 = r35;
        r0 = r0.density;
        r31 = r0;
        r10 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r31 = r10 * r31;
        r0 = r25;
        r1 = r31;
        r0 = r0 + r1;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        r9 = (float) r5;
        r0 = r35;
        r0 = r0.density;
        r25 = r0;
        r10 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r25 = r10 * r25;
        r0 = r25;
        r9 = r9 + r0;
        r0 = (float) r7;
        r25 = r0;
        r0 = r35;
        r0 = r0.density;
        r31 = r0;
        r10 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r31 = r10 * r31;
        r0 = r25;
        r1 = r31;
        r0 = r0 + r1;
        r25 = r0;
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        r9 = (float) r5;
        r0 = (float) r7;
        r25 = r0;
        goto L_0x05db;
    L_0x05d8:
        goto L_0x0377;
    L_0x05db:
        r0 = r30;
        r1 = r25;
        r0.lineTo(r9, r1);
        goto L_0x05d8;
        goto L_0x05e7;
    L_0x05e4:
        goto L_0x03a1;
    L_0x05e7:
        r27 = 3;
        goto L_0x05e4;
        goto L_0x05ee;
    L_0x05eb:
        goto L_0x03c3;
    L_0x05ee:
        r7 = 0;
        goto L_0x05eb;
        goto L_0x05f4;
    L_0x05f1:
        goto L_0x03d2;
    L_0x05f4:
        r7 = 2131690767; // 0x7f0f050f float:1.9010587E38 double:1.0531951755E-314;
        goto L_0x05f1;
        goto L_0x05fc;
    L_0x05f9:
        goto L_0x03e2;
    L_0x05fc:
        r7 = 2131690767; // 0x7f0f050f float:1.9010587E38 double:1.0531951755E-314;
        goto L_0x05f9;
        goto L_0x0604;
    L_0x0601:
        goto L_0x03f3;
    L_0x0604:
        r7 = 0;
        goto L_0x0601;
        goto L_0x060a;
    L_0x0607:
        goto L_0x0414;
    L_0x060a:
        r27 = 3;
        goto L_0x0607;
        goto L_0x0611;
    L_0x060e:
        goto L_0x0434;
    L_0x0611:
        r7 = 2131690768; // 0x7f0f0510 float:1.9010589E38 double:1.053195176E-314;
        goto L_0x060e;
        goto L_0x0619;
    L_0x0616:
        goto L_0x0445;
    L_0x0619:
        r7 = 0;
        goto L_0x0616;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.FullScreenTooltip.initTooltip(android.view.View):void");
    }

    public FullScreenTooltip(Context $r1, String $r2, String $r3) throws  {
        FullScreenTooltip fullScreenTooltip = this;
        this.mContext = $r1;
        this.density = this.mContext.getResources().getDisplayMetrics().density;
        this.mView = ((LayoutInflater) $r1.getSystemService("layout_inflater")).inflate(C1283R.layout.full_screen_tooltip, null);
        this.mTitleView = (TextView) this.mView.findViewById(C1283R.id.fullScreenTooltipTitle);
        this.mTextView = (TextView) this.mView.findViewById(C1283R.id.fullScreenTooltipText);
        this.mCircle = (ImageView) this.mView.findViewById(C1283R.id.fullScreenTooltipCircle);
        this.mHole = (ImageView) this.mView.findViewById(C1283R.id.fullScreenTooltipHole);
        this.mArrow = (ImageView) this.mView.findViewById(C1283R.id.fullScreenTooltipArrow);
        this.mClose = (ImageButton) this.mView.findViewById(C1283R.id.fullScreenTooltipClose);
        this.mCheckBox = (CheckBox) this.mView.findViewById(C1283R.id.fullScreenTooltipCheckbox);
        this.mTitleView.setText($r2);
        this.mTextView.setText($r3);
        Xfermode porterDuffXfermode = new PorterDuffXfermode(Mode.MULTIPLY);
        this.mClearPaint = new Paint();
        this.mClearPaint.setColor(ViewCompat.MEASURED_SIZE_MASK);
        this.mClearPaint.setAlpha(0);
        Paint paint = this.mClearPaint;
        Paint $r16 = paint;
        paint.setXfermode(porterDuffXfermode);
        this.mClearPaint.setAntiAlias(true);
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setColor(-1);
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setShadowLayer(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * this.density, 0.0f, 0.0f, -16777216);
        this.mCirclePaint.setStyle(Style.STROKE);
        this.mCirclePaint.setStrokeWidth(1.5f * this.density);
        this.mArrowLinePaint = new Paint();
        this.mArrowLinePaint.setColor(-1);
        this.mArrowLinePaint.setStrokeWidth(this.density);
        this.mArrowLinePaint.setStyle(Style.STROKE);
        this.mArrowLinePaint.setStrokeJoin(Join.ROUND);
        this.mArrowLinePaint.setStrokeCap(Cap.ROUND);
        this.mArrowLinePaint.setPathEffect(new ComposePathEffect(new DashPathEffect(new float[]{4.0f * this.density, 4.0f * this.density}, 0.0f), new CornerPathEffect(5.0f * this.density)));
        this.mArrowHeadPaint = new Paint();
        this.mArrowHeadPaint.setColor(-1);
        this.mArrowHeadPaint.setAntiAlias(true);
        this.mArrowHeadPaint.setPathEffect(new CornerPathEffect(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * this.density));
        this.mWindow = new PopupWindow($r1) {
            public void dismiss() throws  {
                if (FullScreenTooltip.this.mDismissCalled) {
                    super.dismiss();
                } else if (FullScreenTooltip.this.mIToolTipClicked != null) {
                    FullScreenTooltip.this.mIToolTipClicked.onCloseClicked();
                }
            }
        };
        this.mWindow.setContentView(this.mView);
        this.mWindow.setWidth(-1);
        this.mWindow.setHeight(-1);
        this.mWindow.setTouchable(true);
        this.mWindow.setFocusable(true);
        this.mWindow.setOutsideTouchable(true);
        this.mWindow.setBackgroundDrawable(new BitmapDrawable());
        this.mWindow.setContentView(this.mView);
        this.mClose.setOnClickListener(new C17232());
        this.mCheckBox.setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_DONT_SHOW_AGAIN));
        this.mCheckBox.setOnCheckedChangeListener(new C17243());
        this.mView.setOnClickListener(new C17254());
        this.mCircle.setOnClickListener(new C17265());
    }

    public void setOnClickListeners(IToolTipClicked $r1) throws  {
        this.mIToolTipClicked = $r1;
    }

    public void show(View $r1) throws  {
        initTooltip($r1);
        this.mDismissCalled = false;
        this.mWindow.showAtLocation($r1, 0, 0, 0);
        AnimationSet $r3 = new AnimationSet(true);
        $r3.addAnimation(new ScaleAnimation(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, 1.0f, LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        $r3.setInterpolator(new DecelerateInterpolator());
        $r3.setDuration(200);
        ImageView $r7 = this.mCircle;
        $r7.startAnimation($r3);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setFillBefore(true);
        alphaAnimation.setFillAfter(true);
        View view = this.mView;
        $r1 = view;
        view.startAnimation(alphaAnimation);
    }

    public void dismissTooltip() throws  {
        this.mDismissCalled = true;
        AlphaAnimation $r1 = new AlphaAnimation(1.0f, 0.0f);
        $r1.setDuration(100);
        $r1.setStartOffset(50);
        $r1.setFillBefore(true);
        $r1.setFillAfter(true);
        this.mView.startAnimation($r1);
        $r1.setAnimationListener(new C17276());
    }

    public boolean shouldShowAgain() throws  {
        return this.mShowAgain;
    }

    public void setShowAgainChecked(boolean $z0) throws  {
        this.mShowAgain = $z0;
        this.mCheckBox.setChecked(!$z0);
    }

    public boolean isShowing() throws  {
        return this.mWindow.isShowing();
    }
}
