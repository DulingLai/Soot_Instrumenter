package com.waze.ifs.ui;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;

public class FullScreenTooltipShape {
    public static int CIRCLE = 0;
    public static int RECTANGLE = 1;
    private float density;
    private boolean mAlignLeft = true;
    private Paint mClearPaint;
    protected Activity mContext;
    private boolean mDismissCalled = false;
    private float mDx;
    private float mDy;
    private ImageView mHole;
    private IToolTipClicked mIToolTipClicked;
    private float mPadding;
    private int mShapeType = CIRCLE;
    private TextView mTextView;
    private TextView mTitleView;
    private View mView;
    private PopupWindow mWindow;

    public interface IToolTipClicked {
        void onBack() throws ;

        void onBackgroundClicked() throws ;

        void onHighLightClicked() throws ;
    }

    class C17292 implements OnClickListener {
        C17292() throws  {
        }

        public void onClick(View v) throws  {
            if (FullScreenTooltipShape.this.mIToolTipClicked != null) {
                FullScreenTooltipShape.this.mIToolTipClicked.onBackgroundClicked();
            }
        }
    }

    class C17303 implements OnClickListener {
        C17303() throws  {
        }

        public void onClick(View v) throws  {
            if (FullScreenTooltipShape.this.mIToolTipClicked != null) {
                FullScreenTooltipShape.this.mIToolTipClicked.onHighLightClicked();
            }
        }
    }

    class C17314 extends AnimationEndListener {
        C17314() throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            FullScreenTooltipShape.this.setStatusBarColor();
        }
    }

    class C17336 implements AnimationListener {
        C17336() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            FullScreenTooltipShape.this.mWindow.dismiss();
        }
    }

    private void initTooltip(android.view.View r40, android.view.View r41) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:57:0x030f
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
        r39 = this;
        r4 = 2;
        r3 = new int[r4];
        r0 = r41;
        r0.getLocationInWindow(r3);
        r4 = 2;
        r5 = new int[r4];
        r0 = r40;
        r0.getLocationInWindow(r5);
        r4 = 0;
        r6 = r5[r4];
        r4 = 1;
        r7 = r5[r4];
        r4 = 1;
        r8 = r3[r4];
        r9 = r7 - r8;
        r0 = r40;
        r10 = r0.getWidth();
        r0 = r40;
        r11 = r0.getHeight();
        r0 = r39;
        r7 = r0.mShapeType;
        r8 = CIRCLE;
        if (r7 != r8) goto L_0x025d;
    L_0x002f:
        if (r10 >= r11) goto L_0x023a;
    L_0x0031:
        r0 = r39;
        r12 = r0.density;
        r13 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r12 = r13 * r12;
        r7 = (int) r12;
        r7 = r11 + r7;
        r8 = r7;
        r0 = r39;
        r12 = r0.density;
        r13 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r12 = r13 * r12;
        r11 = (int) r12;
        r9 = r9 - r11;
        r10 = r7 - r10;
        r10 = r10 / 2;
        r10 = r6 - r10;
    L_0x004f:
        r12 = (float) r9;
        r0 = r39;
        r14 = r0.mDy;
        r12 = r12 + r14;
        r6 = (int) r12;
        r12 = (float) r10;
        r0 = r39;
        r14 = r0.mDx;
        r12 = r12 + r14;
        r9 = (int) r12;
        r0 = r39;
        r15 = r0.mContext;
        r16 = r15.getResources();
        r0 = r16;
        r17 = r0.getDisplayMetrics();
        r0 = r17;
        r10 = r0.widthPixels;
        r0 = r39;
        r15 = r0.mContext;
        r16 = r15.getResources();
        r0 = r16;
        r17 = r0.getDisplayMetrics();
        r0 = r17;
        r10 = r0.heightPixels;
        r0 = r39;
        r0 = r0.mView;
        r41 = r0;
        r4 = 2131690764; // 0x7f0f050c float:1.901058E38 double:1.053195174E-314;
        r0 = r41;
        r41 = r0.findViewById(r4);
        r0 = r41;
        r18 = r0.getLayoutParams();
        r20 = r18;
        r20 = (android.widget.RelativeLayout.LayoutParams) r20;
        r19 = r20;
        r0 = r19;
        r0.height = r6;
        r0 = r41;
        r1 = r19;
        r0.setLayoutParams(r1);
        r0 = r39;
        r0 = r0.mView;
        r41 = r0;
        r4 = 2131690765; // 0x7f0f050d float:1.9010583E38 double:1.0531951745E-314;
        r0 = r41;
        r41 = r0.findViewById(r4);
        r0 = r41;
        r18 = r0.getLayoutParams();
        r21 = r18;
        r21 = (android.widget.RelativeLayout.LayoutParams) r21;
        r19 = r21;
        r0 = r19;
        r0.width = r9;
        r0 = r41;
        r1 = r19;
        r0.setLayoutParams(r1);
        r0 = r39;
        r0 = r0.mHole;
        r22 = r0;
        r18 = r0.getLayoutParams();
        r23 = r18;
        r23 = (android.widget.RelativeLayout.LayoutParams) r23;
        r19 = r23;
        r0 = r19;
        r0.width = r8;
        r0 = r19;
        r0.height = r7;
        r0 = r39;
        r0 = r0.mHole;
        r22 = r0;
        r1 = r19;
        r0.setLayoutParams(r1);
        r9 = android.os.Build.VERSION.SDK_INT;
        r4 = 17;
        if (r9 >= r4) goto L_0x0291;
    L_0x00f6:
        r24 = android.graphics.Bitmap.Config.ARGB_8888;
        r0 = r24;
        r25 = android.graphics.Bitmap.createBitmap(r8, r7, r0);
        r26 = r25;
        r0 = r40;
        r16 = r0.getResources();
        r0 = r16;
        r17 = r0.getDisplayMetrics();
        r0 = r17;
        r9 = r0.densityDpi;
        r0 = r25;
        r0.setDensity(r9);
    L_0x0115:
        r0 = r39;
        r15 = r0.mContext;
        r16 = r15.getResources();
        r4 = 2131624063; // 0x7f0e007f float:1.8875295E38 double:1.0531622194E-314;
        r0 = r16;
        r9 = r0.getColor(r4);
        r0 = r26;
        r0.eraseColor(r9);
        r27 = new android.graphics.Canvas;
        r0 = r27;
        r1 = r26;
        r0.<init>(r1);
        r0 = r39;
        r9 = r0.mShapeType;
        r10 = CIRCLE;
        if (r9 != r10) goto L_0x02ac;
    L_0x013c:
        r12 = (float) r7;
        r13 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r12 = r12 / r13;
        r13 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r14 = r12 - r13;
        r0 = r39;
        r0 = r0.mClearPaint;
        r28 = r0;
        r0 = r27;
        r1 = r28;
        r0.drawCircle(r12, r12, r14, r1);
    L_0x0153:
        r0 = r39;
        r0 = r0.mHole;
        r22 = r0;
        r1 = r26;
        r0.setImageBitmap(r1);
        r12 = (float) r6;
        r0 = r39;
        r14 = r0.density;
        r13 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r14 = r14 * r13;
        r29 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r29 <= 0) goto L_0x0313;
    L_0x016b:
        r30 = 1;
    L_0x016d:
        r0 = r39;
        r0 = r0.mTextView;
        r31 = r0;
        r0 = r39;
        r0 = r0.mAlignLeft;
        r32 = r0;
        if (r32 == 0) goto L_0x031a;
    L_0x017b:
        r29 = 3;
    L_0x017d:
        r0 = r31;
        r1 = r29;
        r0.setGravity(r1);
        r0 = r39;
        r0 = r0.mTextView;
        r31 = r0;
        r18 = r0.getLayoutParams();
        r33 = r18;
        r33 = (android.widget.RelativeLayout.LayoutParams) r33;
        r19 = r33;
        if (r30 == 0) goto L_0x0321;
    L_0x0196:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
    L_0x0199:
        r4 = 2;
        r0 = r19;
        r0.addRule(r4, r7);
        if (r30 == 0) goto L_0x0327;
    L_0x01a1:
        r7 = 0;
    L_0x01a2:
        r4 = 3;
        r0 = r19;
        r0.addRule(r4, r7);
        r0 = r39;
        r0 = r0.mAlignLeft;
        r32 = r0;
        if (r32 == 0) goto L_0x032f;
    L_0x01b0:
        r7 = 0;
    L_0x01b1:
        r4 = 7;
        r0 = r19;
        r0.addRule(r4, r7);
        r0 = r39;
        r0 = r0.mAlignLeft;
        r32 = r0;
        if (r32 == 0) goto L_0x0337;
    L_0x01bf:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
    L_0x01c2:
        r4 = 5;
        r0 = r19;
        r0.addRule(r4, r7);
        r0 = r39;
        r0 = r0.mTextView;
        r31 = r0;
        r1 = r19;
        r0.setLayoutParams(r1);
        r0 = r39;
        r0 = r0.mTitleView;
        r31 = r0;
        r0 = r39;
        r0 = r0.mAlignLeft;
        r32 = r0;
        if (r32 == 0) goto L_0x033d;
    L_0x01e1:
        r29 = 3;
    L_0x01e3:
        r0 = r31;
        r1 = r29;
        r0.setGravity(r1);
        r0 = r39;
        r0 = r0.mTitleView;
        r31 = r0;
        r18 = r0.getLayoutParams();
        r34 = r18;
        r34 = (android.widget.RelativeLayout.LayoutParams) r34;
        r19 = r34;
        if (r30 == 0) goto L_0x0344;
    L_0x01fc:
        r7 = 2131690769; // 0x7f0f0511 float:1.901059E38 double:1.0531951765E-314;
    L_0x01ff:
        r4 = 2;
        r0 = r19;
        r0.addRule(r4, r7);
        if (r30 == 0) goto L_0x034a;
    L_0x0207:
        r7 = 0;
    L_0x0208:
        r4 = 3;
        r0 = r19;
        r0.addRule(r4, r7);
        r0 = r39;
        r0 = r0.mAlignLeft;
        r30 = r0;
        if (r30 == 0) goto L_0x0352;
    L_0x0216:
        r7 = 0;
    L_0x0217:
        r4 = 7;
        r0 = r19;
        r0.addRule(r4, r7);
        r0 = r39;
        r0 = r0.mAlignLeft;
        r30 = r0;
        if (r30 == 0) goto L_0x035a;
    L_0x0225:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
    L_0x0228:
        r4 = 5;
        r0 = r19;
        r0.addRule(r4, r7);
        r0 = r39;
        r0 = r0.mTitleView;
        r31 = r0;
        r1 = r19;
        r0.setLayoutParams(r1);
        return;
    L_0x023a:
        r0 = r39;
        r12 = r0.density;
        r13 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r12 = r13 * r12;
        r7 = (int) r12;
        r8 = r10 + r7;
        r7 = r8;
        r0 = r39;
        r12 = r0.density;
        r13 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r12 = r13 * r12;
        r10 = (int) r12;
        r10 = r6 - r10;
        r6 = r8 - r11;
        r6 = r6 / 2;
        goto L_0x025b;
    L_0x0258:
        goto L_0x004f;
    L_0x025b:
        r9 = r9 - r6;
        goto L_0x0258;
    L_0x025d:
        r0 = r39;
        r12 = r0.density;
        r13 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r12 = r13 * r12;
        r7 = (int) r12;
        r7 = r11 + r7;
        r0 = r39;
        r12 = r0.density;
        r13 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r12 = r13 * r12;
        r8 = (int) r12;
        r8 = r10 + r8;
        r0 = r39;
        r12 = r0.density;
        r13 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r12 = r13 * r12;
        r10 = (int) r12;
        r9 = r9 - r10;
        r0 = r39;
        r12 = r0.density;
        r13 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r12 = r13 * r12;
        r10 = (int) r12;
        goto L_0x028e;
    L_0x028b:
        goto L_0x004f;
    L_0x028e:
        r10 = r6 - r10;
        goto L_0x028b;
    L_0x0291:
        r0 = r40;
        r16 = r0.getResources();
        r0 = r16;
        r17 = r0.getDisplayMetrics();
        r24 = android.graphics.Bitmap.Config.ARGB_8888;
        goto L_0x02a3;
    L_0x02a0:
        goto L_0x0115;
    L_0x02a3:
        r0 = r17;
        r1 = r24;
        r26 = android.graphics.Bitmap.createBitmap(r0, r8, r7, r1);
        goto L_0x02a0;
    L_0x02ac:
        r35 = new android.graphics.RectF;
        r0 = r39;
        r12 = r0.mPadding;
        r0 = r39;
        r14 = r0.mPadding;
        r0 = (float) r8;
        r36 = r0;
        r0 = r39;
        r0 = r0.mPadding;
        r37 = r0;
        r13 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r37 = r13 * r37;
        r0 = r36;
        r1 = r37;
        r0 = r0 - r1;
        r36 = r0;
        r0 = (float) r7;
        r37 = r0;
        r0 = r39;
        r0 = r0.mPadding;
        r38 = r0;
        r13 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r38 = r13 * r38;
        r0 = r37;
        r1 = r38;
        r0 = r0 - r1;
        r37 = r0;
        r0 = r35;
        r1 = r36;
        r2 = r37;
        r0.<init>(r12, r14, r1, r2);
        r0 = r39;
        r12 = r0.density;
        r13 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r12 = r13 * r12;
        r0 = r39;
        r14 = r0.density;
        r13 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r14 = r13 * r14;
        r0 = r39;
        r0 = r0.mClearPaint;
        r28 = r0;
        goto L_0x0305;
    L_0x0302:
        goto L_0x0153;
    L_0x0305:
        r0 = r27;
        r1 = r35;
        r2 = r28;
        r0.drawRoundRect(r1, r12, r14, r2);
        goto L_0x0302;
        goto L_0x0313;
    L_0x0310:
        goto L_0x016d;
    L_0x0313:
        r30 = 0;
        goto L_0x0310;
        goto L_0x031a;
    L_0x0317:
        goto L_0x017d;
    L_0x031a:
        r29 = 5;
        goto L_0x0317;
        goto L_0x0321;
    L_0x031e:
        goto L_0x0199;
    L_0x0321:
        r7 = 0;
        goto L_0x031e;
        goto L_0x0327;
    L_0x0324:
        goto L_0x01a2;
    L_0x0327:
        r7 = 2131690768; // 0x7f0f0510 float:1.9010589E38 double:1.053195176E-314;
        goto L_0x0324;
        goto L_0x032f;
    L_0x032c:
        goto L_0x01b1;
    L_0x032f:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
        goto L_0x032c;
        goto L_0x0337;
    L_0x0334:
        goto L_0x01c2;
    L_0x0337:
        r7 = 0;
        goto L_0x0334;
        goto L_0x033d;
    L_0x033a:
        goto L_0x01e3;
    L_0x033d:
        r29 = 5;
        goto L_0x033a;
        goto L_0x0344;
    L_0x0341:
        goto L_0x01ff;
    L_0x0344:
        r7 = 0;
        goto L_0x0341;
        goto L_0x034a;
    L_0x0347:
        goto L_0x0208;
    L_0x034a:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
        goto L_0x0347;
        goto L_0x0352;
    L_0x034f:
        goto L_0x0217;
    L_0x0352:
        r7 = 2131690766; // 0x7f0f050e float:1.9010585E38 double:1.053195175E-314;
        goto L_0x034f;
        goto L_0x035a;
    L_0x0357:
        goto L_0x0228;
    L_0x035a:
        r7 = 0;
        goto L_0x0357;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.FullScreenTooltipShape.initTooltip(android.view.View, android.view.View):void");
    }

    public FullScreenTooltipShape(Activity $r1, String $r2, String $r3) throws  {
        FullScreenTooltipShape fullScreenTooltipShape = this;
        if ($r1 != null) {
            this.mContext = $r1;
            this.density = this.mContext.getResources().getDisplayMetrics().density;
            this.mView = ((LayoutInflater) $r1.getSystemService("layout_inflater")).inflate(C1283R.layout.full_screen_tooltip_shape, null);
            this.mTitleView = (TextView) this.mView.findViewById(C1283R.id.fullScreenTooltipTitle);
            this.mTextView = (TextView) this.mView.findViewById(C1283R.id.fullScreenTooltipText);
            this.mHole = (ImageView) this.mView.findViewById(C1283R.id.fullScreenTooltipHole);
            this.mTitleView.setText($r2);
            this.mTextView.setText($r3);
            Xfermode porterDuffXfermode = new PorterDuffXfermode(Mode.MULTIPLY);
            this.mClearPaint = new Paint();
            this.mClearPaint.setColor(ViewCompat.MEASURED_SIZE_MASK);
            this.mClearPaint.setAlpha(0);
            Paint paint = this.mClearPaint;
            Paint $r15 = paint;
            paint.setXfermode(porterDuffXfermode);
            this.mClearPaint.setAntiAlias(true);
            this.mClearPaint.setMaskFilter(new BlurMaskFilter(8.0f, Blur.NORMAL));
            this.mHole.setLayerType(1, null);
            this.mWindow = new PopupWindow($r1) {
                public void dismiss() throws  {
                    if (FullScreenTooltipShape.this.mDismissCalled) {
                        super.dismiss();
                    } else if (FullScreenTooltipShape.this.mIToolTipClicked != null) {
                        FullScreenTooltipShape.this.mIToolTipClicked.onBack();
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
            this.mView.setOnClickListener(new C17292());
            this.mHole.setOnClickListener(new C17303());
        }
    }

    public void setOnClickListeners(IToolTipClicked $r1) throws  {
        this.mIToolTipClicked = $r1;
    }

    public void show(View $r1, View $r2) throws  {
        if (this.mContext != null && this.mView != null && this.mWindow != null) {
            initTooltip($r1, $r2);
            this.mDismissCalled = false;
            this.mWindow.showAtLocation($r1, 0, 0, 0);
            AlphaAnimation $r3 = r9;
            AlphaAnimation r9 = new AlphaAnimation(0.0f, 1.0f);
            $r3.setDuration(300);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setFillBefore(true);
            $r3.setFillAfter(true);
            View $r12 = this.mView;
            $r12.startAnimation($r3);
            $r3.setAnimationListener(new C17314());
        }
    }

    private void setStatusBarColor() throws  {
        if (VERSION.SDK_INT >= 21) {
            final Window $r2 = this.mContext.getWindow();
            final int $i0 = $r2.getStatusBarColor();
            $r2.setStatusBarColor(-16777216);
            this.mWindow.setOnDismissListener(new OnDismissListener() {
                public void onDismiss() throws  {
                    $r2.setStatusBarColor($i0);
                }
            });
        }
    }

    public void dismissTooltip() throws  {
        if (this.mContext != null && this.mView != null && this.mWindow != null) {
            this.mDismissCalled = true;
            AlphaAnimation $r1 = new AlphaAnimation(1.0f, 0.0f);
            $r1.setDuration(100);
            $r1.setStartOffset(50);
            $r1.setFillBefore(true);
            $r1.setFillAfter(true);
            this.mView.startAnimation($r1);
            $r1.setAnimationListener(new C17336());
        }
    }

    public boolean isShowing() throws  {
        return this.mWindow != null && this.mWindow.isShowing();
    }

    public void setShapeType(int $i0) throws  {
        this.mShapeType = $i0;
    }

    public void setOffsets(float $f0, float $f1, float $f2) throws  {
        this.mPadding = $f0;
        this.mDx = $f1;
        this.mDy = $f2;
    }

    public void setAlignLeft(boolean $z0) throws  {
        this.mAlignLeft = $z0;
    }
}
