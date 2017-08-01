package android.support.v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.menu.MenuBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBarContextView extends AbsActionBarView {
    private static final String TAG = "ActionBarContextView";
    private View mClose;
    private int mCloseItemLayout;
    private View mCustomView;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    protected void onLayout(boolean r17, int r18, int r19, int r20, int r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:22:0x00a4 in {2, 9, 11, 12, 13, 21, 23, 24, 27, 29, 34, 36, 40, 41, 42} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r16 = this;
        r0 = r16;
        r17 = android.support.v7.widget.ViewUtils.isLayoutRtl(r0);
        if (r17 == 0) goto L_0x00f2;
    L_0x0008:
        r6 = r20 - r18;
        r0 = r16;
        r7 = r0.getPaddingRight();
        r7 = r6 - r7;
    L_0x0012:
        r0 = r16;
        r6 = r0.getPaddingTop();
        r19 = r21 - r19;
        r0 = r16;
        r21 = r0.getPaddingTop();
        r0 = r19;
        r1 = r21;
        r0 = r0 - r1;
        r19 = r0;
        r0 = r16;
        r21 = r0.getPaddingBottom();
        r0 = r19;
        r1 = r21;
        r0 = r0 - r1;
        r19 = r0;
        r0 = r16;
        r8 = r0.mClose;
        if (r8 == 0) goto L_0x0082;
    L_0x003a:
        r0 = r16;
        r8 = r0.mClose;
        r21 = r8.getVisibility();
        r9 = 8;
        r0 = r21;
        if (r0 == r9) goto L_0x0082;
    L_0x0048:
        r0 = r16;
        r8 = r0.mClose;
        r10 = r8.getLayoutParams();
        r12 = r10;
        r12 = (android.view.ViewGroup.MarginLayoutParams) r12;
        r11 = r12;
        if (r17 == 0) goto L_0x00f9;
    L_0x0056:
        r13 = r11.rightMargin;
    L_0x0058:
        if (r17 == 0) goto L_0x00fc;
    L_0x005a:
        r0 = r11.leftMargin;
        r21 = r0;
    L_0x005e:
        r0 = r17;
        r7 = android.support.v7.widget.AbsActionBarView.next(r7, r13, r0);
        r0 = r16;
        r8 = r0.mClose;
        r0 = r16;
        r1 = r8;
        r2 = r7;
        r3 = r6;
        r4 = r19;
        r5 = r17;
        r13 = r0.positionChild(r1, r2, r3, r4, r5);
        r7 = r7 + r13;
        r0 = r21;
        r1 = r17;
        r7 = android.support.v7.widget.AbsActionBarView.next(r7, r0, r1);
        goto L_0x0082;
    L_0x007f:
        goto L_0x0012;
    L_0x0082:
        r0 = r16;
        r14 = r0.mTitleLayout;
        if (r14 == 0) goto L_0x00b8;
    L_0x0088:
        r0 = r16;
        r8 = r0.mCustomView;
        if (r8 != 0) goto L_0x00b8;
    L_0x008e:
        r0 = r16;
        r14 = r0.mTitleLayout;
        r21 = r14.getVisibility();
        r9 = 8;
        r0 = r21;
        if (r0 == r9) goto L_0x00b8;
    L_0x009c:
        r0 = r16;
        r14 = r0.mTitleLayout;
        goto L_0x00a8;
    L_0x00a1:
        goto L_0x0058;
        goto L_0x00a8;
    L_0x00a5:
        goto L_0x005e;
    L_0x00a8:
        r0 = r16;
        r1 = r14;
        r2 = r7;
        r3 = r6;
        r4 = r19;
        r5 = r17;
        r21 = r0.positionChild(r1, r2, r3, r4, r5);
        r0 = r21;
        r7 = r7 + r0;
    L_0x00b8:
        r0 = r16;
        r8 = r0.mCustomView;
        if (r8 == 0) goto L_0x00ce;
    L_0x00be:
        r0 = r16;
        r8 = r0.mCustomView;
        r0 = r16;
        r1 = r8;
        r2 = r7;
        r3 = r6;
        r4 = r19;
        r5 = r17;
        r0.positionChild(r1, r2, r3, r4, r5);
    L_0x00ce:
        if (r17 == 0) goto L_0x0101;
    L_0x00d0:
        r0 = r16;
        r18 = r0.getPaddingLeft();
    L_0x00d6:
        r0 = r16;
        r15 = r0.mMenuView;
        if (r15 == 0) goto L_0x0114;
    L_0x00dc:
        r0 = r16;
        r15 = r0.mMenuView;
        if (r17 != 0) goto L_0x0111;
    L_0x00e2:
        r17 = 1;
    L_0x00e4:
        r0 = r16;
        r1 = r15;
        r2 = r18;
        r3 = r6;
        r4 = r19;
        r5 = r17;
        r0.positionChild(r1, r2, r3, r4, r5);
        return;
    L_0x00f2:
        r0 = r16;
        r7 = r0.getPaddingLeft();
        goto L_0x007f;
    L_0x00f9:
        r13 = r11.leftMargin;
        goto L_0x00a1;
    L_0x00fc:
        r0 = r11.rightMargin;
        r21 = r0;
        goto L_0x00a5;
    L_0x0101:
        r18 = r20 - r18;
        r0 = r16;
        r20 = r0.getPaddingRight();
        r0 = r18;
        r1 = r20;
        r0 = r0 - r1;
        r18 = r0;
        goto L_0x00d6;
    L_0x0111:
        r17 = 0;
        goto L_0x00e4;
    L_0x0114:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarContextView.onLayout(boolean, int, int, int, int):void");
    }

    protected void onMeasure(int r25, int r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:66:0x01c9 in {3, 7, 10, 13, 18, 27, 29, 32, 33, 38, 41, 44, 48, 49, 50, 52, 53, 57, 59, 64, 65, 67, 74, 75, 77, 79} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r24 = this;
        r0 = r25;
        r2 = android.view.View.MeasureSpec.getMode(r0);
        r3 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r2 == r3) goto L_0x0035;
    L_0x000b:
        r4 = new java.lang.IllegalStateException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r0 = r24;
        r6 = r0.getClass();
        r7 = r6.getSimpleName();
        r5 = r5.append(r7);
        r8 = " can only be used ";
        r5 = r5.append(r8);
        r8 = "with android:layout_width=\"match_parent\" (or fill_parent)";
        r5 = r5.append(r8);
        r7 = r5.toString();
        r4.<init>(r7);
        throw r4;
    L_0x0035:
        r0 = r26;
        r2 = android.view.View.MeasureSpec.getMode(r0);
        if (r2 != 0) goto L_0x0067;
    L_0x003d:
        r4 = new java.lang.IllegalStateException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r0 = r24;
        r6 = r0.getClass();
        r7 = r6.getSimpleName();
        r5 = r5.append(r7);
        r8 = " can only be used ";
        r5 = r5.append(r8);
        r8 = "with android:layout_height=\"wrap_content\"";
        r5 = r5.append(r8);
        r7 = r5.toString();
        r4.<init>(r7);
        throw r4;
    L_0x0067:
        r0 = r25;
        r25 = android.view.View.MeasureSpec.getSize(r0);
        r0 = r24;
        r2 = r0.mContentHeight;
        if (r2 <= 0) goto L_0x01cd;
    L_0x0073:
        r0 = r24;
        r2 = r0.mContentHeight;
    L_0x0077:
        r0 = r24;
        r26 = r0.getPaddingTop();
        r0 = r24;
        r9 = r0.getPaddingBottom();
        r0 = r26;
        r0 = r0 + r9;
        r26 = r0;
        r0 = r24;
        r9 = r0.getPaddingLeft();
        r9 = r25 - r9;
        r0 = r24;
        r10 = r0.getPaddingRight();
        r11 = r9 - r10;
        r9 = r2 - r26;
        r3 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r10 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r3);
        r0 = r24;
        r12 = r0.mClose;
        if (r12 == 0) goto L_0x00d0;
    L_0x00a7:
        r0 = r24;
        r12 = r0.mClose;
        r3 = 0;
        r0 = r24;
        r11 = r0.measureChildView(r12, r11, r10, r3);
        r0 = r24;
        r12 = r0.mClose;
        r13 = r12.getLayoutParams();
        r15 = r13;
        r15 = (android.view.ViewGroup.MarginLayoutParams) r15;
        r14 = r15;
        r0 = r14.leftMargin;
        r16 = r0;
        r0 = r14.rightMargin;
        r17 = r0;
        r0 = r16;
        r1 = r17;
        r0 = r0 + r1;
        r16 = r0;
        r11 = r11 - r0;
    L_0x00d0:
        r0 = r24;
        r0 = r0.mMenuView;
        r18 = r0;
        if (r18 == 0) goto L_0x00f7;
    L_0x00d8:
        r0 = r24;
        r0 = r0.mMenuView;
        r18 = r0;
        r19 = r0.getParent();
        r0 = r19;
        r1 = r24;
        if (r0 != r1) goto L_0x00f7;
    L_0x00e8:
        r0 = r24;
        r0 = r0.mMenuView;
        r18 = r0;
        r3 = 0;
        r0 = r24;
        r1 = r18;
        r11 = r0.measureChildView(r1, r11, r10, r3);
    L_0x00f7:
        r0 = r24;
        r0 = r0.mTitleLayout;
        r20 = r0;
        if (r20 == 0) goto L_0x0143;
    L_0x00ff:
        r0 = r24;
        r12 = r0.mCustomView;
        if (r12 != 0) goto L_0x0143;
    L_0x0105:
        r0 = r24;
        r0 = r0.mTitleOptional;
        r21 = r0;
        if (r21 == 0) goto L_0x01da;
    L_0x010d:
        r3 = 0;
        r22 = 0;
        r0 = r22;
        r16 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r0);
        r0 = r24;
        r0 = r0.mTitleLayout;
        r20 = r0;
        r1 = r16;
        r0.measure(r1, r10);
        r0 = r24;
        r0 = r0.mTitleLayout;
        r20 = r0;
        r10 = r0.getMeasuredWidth();
        if (r10 > r11) goto L_0x01d4;
    L_0x012d:
        r21 = 1;
    L_0x012f:
        if (r21 == 0) goto L_0x0132;
    L_0x0131:
        r11 = r11 - r10;
    L_0x0132:
        r0 = r24;
        r0 = r0.mTitleLayout;
        r20 = r0;
        if (r21 == 0) goto L_0x01d7;
    L_0x013a:
        r23 = 0;
    L_0x013c:
        r0 = r20;
        r1 = r23;
        r0.setVisibility(r1);
    L_0x0143:
        r0 = r24;
        r12 = r0.mCustomView;
        if (r12 == 0) goto L_0x019e;
    L_0x0149:
        r0 = r24;
        r12 = r0.mCustomView;
        r13 = r12.getLayoutParams();
        r10 = r13.width;
        r3 = -2;
        if (r10 == r3) goto L_0x01ea;
    L_0x0156:
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
    L_0x0159:
        r0 = r13.width;
        r16 = r0;
        if (r16 < 0) goto L_0x01ee;
    L_0x015f:
        r0 = r13.width;
        r16 = r0;
        r11 = java.lang.Math.min(r0, r11);
    L_0x0167:
        r0 = r13.height;
        r16 = r0;
        r3 = -2;
        r0 = r16;
        if (r0 == r3) goto L_0x01ef;
    L_0x0170:
        r16 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
    L_0x0173:
        r0 = r13.height;
        r17 = r0;
        if (r17 < 0) goto L_0x01f3;
    L_0x0179:
        goto L_0x017d;
    L_0x017a:
        goto L_0x012f;
    L_0x017d:
        r0 = r13.height;
        r17 = r0;
        r9 = java.lang.Math.min(r0, r9);
        goto L_0x0189;
    L_0x0186:
        goto L_0x013c;
    L_0x0189:
        r0 = r24;
        r12 = r0.mCustomView;
        r10 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r10);
        goto L_0x0195;
    L_0x0192:
        goto L_0x0143;
    L_0x0195:
        r0 = r16;
        r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r0);
        r12.measure(r10, r9);
    L_0x019e:
        r0 = r24;
        r9 = r0.mContentHeight;
        if (r9 > 0) goto L_0x01fc;
    L_0x01a4:
        goto L_0x01a8;
    L_0x01a5:
        goto L_0x0159;
    L_0x01a8:
        r2 = 0;
        goto L_0x01ad;
    L_0x01aa:
        goto L_0x0167;
    L_0x01ad:
        r0 = r24;
        r9 = r0.getChildCount();
        r10 = 0;
    L_0x01b4:
        if (r10 >= r9) goto L_0x01f4;
    L_0x01b6:
        r0 = r24;
        r12 = r0.getChildAt(r10);
        r11 = r12.getMeasuredHeight();
        r0 = r26;
        r11 = r11 + r0;
        if (r11 <= r2) goto L_0x01c6;
    L_0x01c5:
        r2 = r11;
    L_0x01c6:
        r10 = r10 + 1;
        goto L_0x01b4;
        goto L_0x01cd;
    L_0x01ca:
        goto L_0x0077;
    L_0x01cd:
        r0 = r26;
        r2 = android.view.View.MeasureSpec.getSize(r0);
        goto L_0x01ca;
    L_0x01d4:
        r21 = 0;
        goto L_0x017a;
    L_0x01d7:
        r23 = 8;
        goto L_0x0186;
    L_0x01da:
        r0 = r24;
        r0 = r0.mTitleLayout;
        r20 = r0;
        r3 = 0;
        r0 = r24;
        r1 = r20;
        r11 = r0.measureChildView(r1, r11, r10, r3);
        goto L_0x0192;
    L_0x01ea:
        r10 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        goto L_0x01a5;
    L_0x01ee:
        goto L_0x01aa;
    L_0x01ef:
        r16 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        goto L_0x0173;
    L_0x01f3:
        goto L_0x0189;
    L_0x01f4:
        r0 = r24;
        r1 = r25;
        r0.setMeasuredDimension(r1, r2);
        return;
    L_0x01fc:
        r0 = r24;
        r1 = r25;
        r0.setMeasuredDimension(r1, r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarContextView.onMeasure(int, int):void");
    }

    public boolean shouldDelayChildPressedState() throws  {
        return false;
    }

    public /* bridge */ /* synthetic */ void animateToVisibility(int $i0) throws  {
        super.animateToVisibility($i0);
    }

    public /* bridge */ /* synthetic */ boolean canShowOverflowMenu() throws  {
        return super.canShowOverflowMenu();
    }

    public /* bridge */ /* synthetic */ void dismissPopupMenus() throws  {
        super.dismissPopupMenus();
    }

    public /* bridge */ /* synthetic */ int getAnimatedVisibility() throws  {
        return super.getAnimatedVisibility();
    }

    public /* bridge */ /* synthetic */ int getContentHeight() throws  {
        return super.getContentHeight();
    }

    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowPending() throws  {
        return super.isOverflowMenuShowPending();
    }

    public /* bridge */ /* synthetic */ boolean isOverflowReserved() throws  {
        return super.isOverflowReserved();
    }

    public /* bridge */ /* synthetic */ boolean onHoverEvent(MotionEvent $r1) throws  {
        return super.onHoverEvent($r1);
    }

    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent $r1) throws  {
        return super.onTouchEvent($r1);
    }

    public /* bridge */ /* synthetic */ void postShowOverflowMenu() throws  {
        super.postShowOverflowMenu();
    }

    public /* bridge */ /* synthetic */ void setVisibility(int $i0) throws  {
        super.setVisibility($i0);
    }

    public /* bridge */ /* synthetic */ ViewPropertyAnimatorCompat setupAnimatorToVisibility(int $i0, long $l1) throws  {
        return super.setupAnimatorToVisibility($i0, $l1);
    }

    public ActionBarContextView(Context $r1) throws  {
        this($r1, null);
    }

    public ActionBarContextView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.actionModeStyle);
    }

    public ActionBarContextView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        TintTypedArray $r4 = TintTypedArray.obtainStyledAttributes($r1, $r2, C0192R.styleable.ActionMode, $i0, 0);
        setBackgroundDrawable($r4.getDrawable(C0192R.styleable.ActionMode_background));
        this.mTitleStyleRes = $r4.getResourceId(C0192R.styleable.ActionMode_titleTextStyle, 0);
        this.mSubtitleStyleRes = $r4.getResourceId(C0192R.styleable.ActionMode_subtitleTextStyle, 0);
        this.mContentHeight = $r4.getLayoutDimension(C0192R.styleable.ActionMode_height, 0);
        this.mCloseItemLayout = $r4.getResourceId(C0192R.styleable.ActionMode_closeItemLayout, C0192R.layout.abc_action_mode_close_item_material);
        $r4.recycle();
    }

    public void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    public void setContentHeight(int $i0) throws  {
        this.mContentHeight = $i0;
    }

    public void setCustomView(View $r1) throws  {
        if (this.mCustomView != null) {
            removeView(this.mCustomView);
        }
        this.mCustomView = $r1;
        if (!($r1 == null || this.mTitleLayout == null)) {
            removeView(this.mTitleLayout);
            this.mTitleLayout = null;
        }
        if ($r1 != null) {
            addView($r1);
        }
        requestLayout();
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mTitle = $r1;
        initTitle();
    }

    public void setSubtitle(CharSequence $r1) throws  {
        this.mSubtitle = $r1;
        initTitle();
    }

    public CharSequence getTitle() throws  {
        return this.mTitle;
    }

    public CharSequence getSubtitle() throws  {
        return this.mSubtitle;
    }

    private void initTitle() throws  {
        boolean $z1;
        byte $b2;
        byte $b0 = (byte) 8;
        if (this.mTitleLayout == null) {
            LayoutInflater.from(getContext()).inflate(C0192R.layout.abc_action_bar_title_item, this);
            this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(C0192R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C0192R.id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean $z0 = !TextUtils.isEmpty(this.mTitle);
        if (TextUtils.isEmpty(this.mSubtitle)) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        TextView $r5 = this.mSubtitleView;
        if ($z1) {
            $b2 = (byte) 0;
        } else {
            $b2 = (byte) 8;
        }
        $r5.setVisibility($b2);
        LinearLayout $r1 = this.mTitleLayout;
        if ($z0 || $z1) {
            $b0 = (byte) 0;
        }
        $r1.setVisibility($b0);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    public void initForMode(ActionMode $r1) throws  {
        if (this.mClose == null) {
            this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, this, false);
            addView(this.mClose);
        } else if (this.mClose.getParent() == null) {
            addView(this.mClose);
        }
        final ActionMode actionMode = $r1;
        this.mClose.findViewById(C0192R.id.action_mode_close_button).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                actionMode.finish();
            }
        });
        MenuBuilder $r8 = (MenuBuilder) $r1.getMenu();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
        this.mActionMenuPresenter = new ActionMenuPresenter(getContext());
        this.mActionMenuPresenter.setReserveOverflow(true);
        LayoutParams $r2 = new LayoutParams(-2, -1);
        $r8.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
        this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        this.mMenuView.setBackgroundDrawable(null);
        addView(this.mMenuView, $r2);
    }

    public void closeMode() throws  {
        if (this.mClose == null) {
            killMode();
        }
    }

    public void killMode() throws  {
        removeAllViews();
        this.mCustomView = null;
        this.mMenuView = null;
    }

    public boolean showOverflowMenu() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    public boolean hideOverflowMenu() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    protected LayoutParams generateDefaultLayoutParams() throws  {
        return new MarginLayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new MarginLayoutParams(getContext(), $r1);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
        if (VERSION.SDK_INT < 14) {
            return;
        }
        if ($r1.getEventType() == 32) {
            $r1.setSource(this);
            $r1.setClassName(getClass().getName());
            $r1.setPackageName(getContext().getPackageName());
            $r1.setContentDescription(this.mTitle);
            return;
        }
        super.onInitializeAccessibilityEvent($r1);
    }

    public void setTitleOptional(boolean $z0) throws  {
        if ($z0 != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = $z0;
    }

    public boolean isTitleOptional() throws  {
        return this.mTitleOptional;
    }
}
