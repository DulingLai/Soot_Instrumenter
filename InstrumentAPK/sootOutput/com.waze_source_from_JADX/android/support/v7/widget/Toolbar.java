package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";
    private Callback mActionMenuPresenterCallback;
    private int mButtonGravity;
    private ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private final RtlSpacingHelper mContentInsets;
    private final AppCompatDrawableManager mDrawableManager;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final android.support.v7.widget.ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem) throws ;
    }

    class C02751 implements android.support.v7.widget.ActionMenuView.OnMenuItemClickListener {
        C02751() throws  {
        }

        public boolean onMenuItemClick(MenuItem $r1) throws  {
            if (Toolbar.this.mOnMenuItemClickListener != null) {
                return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick($r1);
            }
            return false;
        }
    }

    class C02762 implements Runnable {
        C02762() throws  {
        }

        public void run() throws  {
            Toolbar.this.showOverflowMenu();
        }
    }

    class C02773 implements OnClickListener {
        C02773() throws  {
        }

        public void onClick(View v) throws  {
            Toolbar.this.collapseActionView();
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        public boolean flagActionItems() throws  {
            return false;
        }

        public int getId() throws  {
            return 0;
        }

        public MenuView getMenuView(ViewGroup root) throws  {
            return null;
        }

        public Parcelable onSaveInstanceState() throws  {
            return null;
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenu) throws  {
            return false;
        }

        private ExpandedActionViewMenuPresenter() throws  {
        }

        public void initForMenu(Context context, MenuBuilder $r2) throws  {
            if (!(this.mMenu == null || this.mCurrentExpandedItem == null)) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = $r2;
        }

        public void updateMenuView(boolean cleared) throws  {
            if (this.mCurrentExpandedItem != null) {
                cleared = false;
                if (this.mMenu != null) {
                    int $i0 = this.mMenu.size();
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        if (this.mMenu.getItem($i1) == this.mCurrentExpandedItem) {
                            cleared = true;
                            break;
                        }
                    }
                }
                if (!cleared) {
                    collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }

        public void setCallback(Callback cb) throws  {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) throws  {
        }

        public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl $r2) throws  {
            Toolbar.this.ensureCollapseButtonView();
            if (Toolbar.this.mCollapseButtonView.getParent() != Toolbar.this) {
                Toolbar.this.addView(Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = $r2.getActionView();
            this.mCurrentExpandedItem = $r2;
            if (Toolbar.this.mExpandedActionView.getParent() != Toolbar.this) {
                LayoutParams $r8 = Toolbar.this.generateDefaultLayoutParams();
                $r8.gravity = GravityCompat.START | (Toolbar.this.mButtonGravity & 112);
                $r8.mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams($r8);
                Toolbar.this.addView(Toolbar.this.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            $r2.setActionViewExpanded(true);
            if (!(Toolbar.this.mExpandedActionView instanceof CollapsibleActionView)) {
                return true;
            }
            ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewExpanded();
            return true;
        }

        public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl $r2) throws  {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
            Toolbar.this.removeView(Toolbar.this.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            $r2.setActionViewExpanded(false);
            return true;
        }

        public void onRestoreInstanceState(Parcelable state) throws  {
        }
    }

    public static class LayoutParams extends android.support.v7.app.ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType;

        public LayoutParams(@NonNull Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            this.mViewType = 0;
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
            this.mViewType = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int $i0, int $i1, int $i2) throws  {
            super($i0, $i1);
            this.mViewType = 0;
            this.gravity = $i2;
        }

        public LayoutParams(int $i0) throws  {
            this(-2, -1, $i0);
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super((android.support.v7.app.ActionBar.LayoutParams) $r1);
            this.mViewType = 0;
            this.mViewType = $r1.mViewType;
        }

        public LayoutParams(android.support.v7.app.ActionBar.LayoutParams $r1) throws  {
            super($r1);
            this.mViewType = 0;
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super((android.view.ViewGroup.LayoutParams) $r1);
            this.mViewType = 0;
            copyMarginsFromCompat($r1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
            this.mViewType = 0;
        }

        void copyMarginsFromCompat(MarginLayoutParams $r1) throws  {
            this.leftMargin = $r1.leftMargin;
            this.topMargin = $r1.topMargin;
            this.rightMargin = $r1.rightMargin;
            this.bottomMargin = $r1.bottomMargin;
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C02781();
        int expandedMenuItemId;
        boolean isOverflowOpen;

        static class C02781 implements Creator<SavedState> {
            C02781() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        public SavedState(Parcel $r1) throws  {
            super($r1);
            this.expandedMenuItemId = $r1.readInt();
            this.isOverflowOpen = $r1.readInt() != 0;
        }

        public SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeInt(this.expandedMenuItemId);
            $r1.writeInt(this.isOverflowOpen ? (byte) 1 : (byte) 0);
        }
    }

    protected void onLayout(boolean r42, int r43, int r44, int r45, int r46) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:62:0x0306
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
        r41 = this;
        r0 = r41;
        r43 = android.support.v4.view.ViewCompat.getLayoutDirection(r0);
        r4 = 1;
        r0 = r43;
        if (r0 != r4) goto L_0x030a;
    L_0x000b:
        r42 = 1;
    L_0x000d:
        r0 = r41;
        r46 = r0.getWidth();
        r0 = r41;
        r5 = r0.getHeight();
        r0 = r41;
        r44 = r0.getPaddingLeft();
        r0 = r41;
        r45 = r0.getPaddingRight();
        r0 = r41;
        r6 = r0.getPaddingTop();
        r0 = r41;
        r7 = r0.getPaddingBottom();
        r8 = r44;
        r9 = r46 - r45;
        r0 = r41;
        r10 = r0.mTempMargins;
        r4 = 1;
        r11 = 0;
        r10[r4] = r11;
        r4 = 0;
        r11 = 0;
        r10[r4] = r11;
        r0 = r41;
        r43 = android.support.v4.view.ViewCompat.getMinimumHeight(r0);
        r0 = r41;
        r12 = r0.mNavButtonView;
        r0 = r41;
        r13 = r0.shouldLayout(r12);
        if (r13 == 0) goto L_0x0061;
    L_0x0053:
        if (r42 == 0) goto L_0x030d;
    L_0x0055:
        r0 = r41;
        r12 = r0.mNavButtonView;
        r0 = r41;
        r1 = r43;
        r9 = r0.layoutChildRight(r12, r9, r10, r1);
    L_0x0061:
        r0 = r41;
        r12 = r0.mCollapseButtonView;
        r0 = r41;
        r13 = r0.shouldLayout(r12);
        if (r13 == 0) goto L_0x007b;
    L_0x006d:
        if (r42 == 0) goto L_0x0320;
    L_0x006f:
        r0 = r41;
        r12 = r0.mCollapseButtonView;
        r0 = r41;
        r1 = r43;
        r9 = r0.layoutChildRight(r12, r9, r10, r1);
    L_0x007b:
        r0 = r41;
        r14 = r0.mMenuView;
        r0 = r41;
        r13 = r0.shouldLayout(r14);
        if (r13 == 0) goto L_0x0095;
    L_0x0087:
        if (r42 == 0) goto L_0x0331;
    L_0x0089:
        r0 = r41;
        r14 = r0.mMenuView;
        r0 = r41;
        r1 = r43;
        r8 = r0.layoutChildLeft(r14, r8, r10, r1);
    L_0x0095:
        r0 = r41;
        r15 = r0.getContentInsetLeft();
        r15 = r15 - r8;
        r4 = 0;
        r15 = java.lang.Math.max(r4, r15);
        r4 = 0;
        r10[r4] = r15;
        r0 = r41;
        r15 = r0.getContentInsetRight();
        r16 = r46 - r45;
        r0 = r16;
        r0 = r0 - r9;
        r16 = r0;
        r15 = r15 - r0;
        r4 = 0;
        r15 = java.lang.Math.max(r4, r15);
        r4 = 1;
        r10[r4] = r15;
        r0 = r41;
        r15 = r0.getContentInsetLeft();
        r15 = java.lang.Math.max(r8, r15);
        r8 = r15;
        r16 = r46 - r45;
        r0 = r41;
        r17 = r0.getContentInsetRight();
        r0 = r16;
        r1 = r17;
        r0 = r0 - r1;
        r16 = r0;
        r16 = java.lang.Math.min(r9, r0);
        r9 = r16;
        r0 = r41;
        r0 = r0.mExpandedActionView;
        r18 = r0;
        r0 = r41;
        r1 = r18;
        r13 = r0.shouldLayout(r1);
        if (r13 == 0) goto L_0x00ff;
    L_0x00eb:
        if (r42 == 0) goto L_0x0342;
    L_0x00ed:
        r0 = r41;
        r0 = r0.mExpandedActionView;
        r18 = r0;
        r0 = r41;
        r1 = r18;
        r2 = r16;
        r3 = r43;
        r9 = r0.layoutChildRight(r1, r2, r10, r3);
    L_0x00ff:
        r0 = r41;
        r0 = r0.mLogoView;
        r19 = r0;
        r0 = r41;
        r1 = r19;
        r13 = r0.shouldLayout(r1);
        if (r13 == 0) goto L_0x0121;
    L_0x010f:
        if (r42 == 0) goto L_0x0357;
    L_0x0111:
        r0 = r41;
        r0 = r0.mLogoView;
        r19 = r0;
        r0 = r41;
        r1 = r19;
        r2 = r43;
        r9 = r0.layoutChildRight(r1, r9, r10, r2);
    L_0x0121:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r0 = r41;
        r1 = r20;
        r13 = r0.shouldLayout(r1);
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r0 = r41;
        r1 = r20;
        r21 = r0.shouldLayout(r1);
        r15 = 0;
        if (r13 == 0) goto L_0x016b;
    L_0x0140:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r22 = r0.getLayoutParams();
        r24 = r22;
        r24 = (android.support.v7.widget.Toolbar.LayoutParams) r24;
        r23 = r24;
        r0 = r23;
        r15 = r0.topMargin;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r16 = r0.getMeasuredHeight();
        r0 = r16;
        r15 = r15 + r0;
        r0 = r23;
        r0 = r0.bottomMargin;
        r16 = r0;
        r15 = r15 + r0;
        r4 = 0;
        r15 = r4 + r15;
    L_0x016b:
        if (r21 == 0) goto L_0x01a2;
    L_0x016d:
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r22 = r0.getLayoutParams();
        r25 = r22;
        r25 = (android.support.v7.widget.Toolbar.LayoutParams) r25;
        r23 = r25;
        r0 = r23;
        r0 = r0.topMargin;
        r16 = r0;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r17 = r0.getMeasuredHeight();
        r0 = r16;
        r1 = r17;
        r0 = r0 + r1;
        r16 = r0;
        r0 = r23;
        r0 = r0.bottomMargin;
        r17 = r0;
        r0 = r16;
        r1 = r17;
        r0 = r0 + r1;
        r16 = r0;
        r15 = r15 + r0;
    L_0x01a2:
        if (r13 != 0) goto L_0x01a6;
    L_0x01a4:
        if (r21 == 0) goto L_0x02ce;
    L_0x01a6:
        if (r13 == 0) goto L_0x0370;
    L_0x01a8:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
    L_0x01ae:
        if (r21 == 0) goto L_0x037b;
    L_0x01b0:
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r26 = r0;
    L_0x01b6:
        r0 = r20;
        r22 = r0.getLayoutParams();
        r27 = r22;
        r27 = (android.support.v7.widget.Toolbar.LayoutParams) r27;
        r23 = r27;
        r0 = r26;
        r22 = r0.getLayoutParams();
        r29 = r22;
        r29 = (android.support.v7.widget.Toolbar.LayoutParams) r29;
        r28 = r29;
        if (r13 == 0) goto L_0x01dc;
    L_0x01d0:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r16 = r0.getMeasuredWidth();
        if (r16 > 0) goto L_0x01ea;
    L_0x01dc:
        if (r21 == 0) goto L_0x0386;
    L_0x01de:
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r16 = r0.getMeasuredWidth();
        if (r16 <= 0) goto L_0x0386;
    L_0x01ea:
        r30 = 1;
    L_0x01ec:
        r0 = r41;
        r0 = r0.mGravity;
        r16 = r0;
        r16 = r16 & 112;
        switch(r16) {
            case 48: goto L_0x0389;
            case 80: goto L_0x03c6;
            default: goto L_0x01f7;
        };
    L_0x01f7:
        goto L_0x01f8;
    L_0x01f8:
        r16 = r5 - r6;
        r0 = r16;
        r0 = r0 - r7;
        r16 = r0;
        r0 = r0 - r15;
        r16 = r0;
        r16 = r16 / 2;
        r0 = r23;
        r0 = r0.topMargin;
        r17 = r0;
        r0 = r41;
        r0 = r0.mTitleMarginTop;
        r31 = r0;
        r0 = r17;
        r1 = r31;
        r0 = r0 + r1;
        r17 = r0;
        r0 = r16;
        r1 = r17;
        if (r0 >= r1) goto L_0x039e;
    L_0x021d:
        r0 = r23;
        r5 = r0.topMargin;
        r0 = r41;
        r7 = r0.mTitleMarginTop;
        r16 = r5 + r7;
    L_0x0227:
        r0 = r16;
        r6 = r6 + r0;
    L_0x022a:
        if (r42 == 0) goto L_0x03de;
    L_0x022c:
        if (r30 == 0) goto L_0x03dc;
    L_0x022e:
        r0 = r41;
        r5 = r0.mTitleMarginStart;
    L_0x0232:
        r4 = 1;
        r7 = r10[r4];
        r5 = r5 - r7;
        r4 = 0;
        r7 = java.lang.Math.max(r4, r5);
        r9 = r9 - r7;
        r5 = -r5;
        r4 = 0;
        r5 = java.lang.Math.max(r4, r5);
        r4 = 1;
        r10[r4] = r5;
        r7 = r9;
        r5 = r9;
        if (r13 == 0) goto L_0x0285;
    L_0x0249:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r22 = r0.getLayoutParams();
        r32 = r22;
        r32 = (android.support.v7.widget.Toolbar.LayoutParams) r32;
        r23 = r32;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r7 = r0.getMeasuredWidth();
        r7 = r9 - r7;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r15 = r0.getMeasuredHeight();
        r15 = r6 + r15;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r0.layout(r7, r6, r9, r15);
        r0 = r41;
        r6 = r0.mTitleMarginEnd;
        r7 = r7 - r6;
        r0 = r23;
        r6 = r0.bottomMargin;
        r6 = r15 + r6;
    L_0x0285:
        if (r21 == 0) goto L_0x02c8;
    L_0x0287:
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r22 = r0.getLayoutParams();
        r33 = r22;
        r33 = (android.support.v7.widget.Toolbar.LayoutParams) r33;
        r23 = r33;
        r0 = r23;
        r15 = r0.topMargin;
        r6 = r6 + r15;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r15 = r0.getMeasuredWidth();
        r15 = r5 - r15;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r16 = r0.getMeasuredHeight();
        r16 = r6 + r16;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r1 = r16;
        r0.layout(r15, r6, r5, r1);
        r0 = r41;
        r6 = r0.mTitleMarginEnd;
        r5 = r5 - r6;
        r0 = r23;
        r6 = r0.bottomMargin;
    L_0x02c8:
        if (r30 == 0) goto L_0x02ce;
    L_0x02ca:
        r9 = java.lang.Math.min(r7, r5);
    L_0x02ce:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r4 = 3;
        r0 = r41;
        r1 = r34;
        r0.addCustomViewsWithGravity(r1, r4);
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r6 = r0.size();
        r5 = 0;
    L_0x02e7:
        if (r5 >= r6) goto L_0x048c;
    L_0x02e9:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r35 = r0.get(r5);
        r36 = r35;
        r36 = (android.view.View) r36;
        r18 = r36;
        r0 = r41;
        r1 = r18;
        r2 = r43;
        r8 = r0.layoutChildLeft(r1, r8, r10, r2);
        r5 = r5 + 1;
        goto L_0x02e7;
        goto L_0x030a;
    L_0x0307:
        goto L_0x000d;
    L_0x030a:
        r42 = 0;
        goto L_0x0307;
    L_0x030d:
        r0 = r41;
        r12 = r0.mNavButtonView;
        goto L_0x0315;
    L_0x0312:
        goto L_0x0061;
    L_0x0315:
        r0 = r41;
        r1 = r44;
        r2 = r43;
        r8 = r0.layoutChildLeft(r12, r1, r10, r2);
        goto L_0x0312;
    L_0x0320:
        r0 = r41;
        r12 = r0.mCollapseButtonView;
        goto L_0x0328;
    L_0x0325:
        goto L_0x007b;
    L_0x0328:
        r0 = r41;
        r1 = r43;
        r8 = r0.layoutChildLeft(r12, r8, r10, r1);
        goto L_0x0325;
    L_0x0331:
        r0 = r41;
        r14 = r0.mMenuView;
        goto L_0x0339;
    L_0x0336:
        goto L_0x0095;
    L_0x0339:
        r0 = r41;
        r1 = r43;
        r9 = r0.layoutChildRight(r14, r9, r10, r1);
        goto L_0x0336;
    L_0x0342:
        r0 = r41;
        r0 = r0.mExpandedActionView;
        r18 = r0;
        goto L_0x034c;
    L_0x0349:
        goto L_0x00ff;
    L_0x034c:
        r0 = r41;
        r1 = r18;
        r2 = r43;
        r8 = r0.layoutChildLeft(r1, r15, r10, r2);
        goto L_0x0349;
    L_0x0357:
        r0 = r41;
        r0 = r0.mLogoView;
        r19 = r0;
        goto L_0x0361;
    L_0x035e:
        goto L_0x0121;
    L_0x0361:
        r0 = r41;
        r1 = r19;
        r2 = r43;
        r8 = r0.layoutChildLeft(r1, r8, r10, r2);
        goto L_0x035e;
        goto L_0x0370;
    L_0x036d:
        goto L_0x01ae;
    L_0x0370:
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        goto L_0x036d;
        goto L_0x037b;
    L_0x0378:
        goto L_0x01b6;
    L_0x037b:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r26 = r0;
        goto L_0x0378;
        goto L_0x0386;
    L_0x0383:
        goto L_0x01ec;
    L_0x0386:
        r30 = 0;
        goto L_0x0383;
    L_0x0389:
        r0 = r41;
        r6 = r0.getPaddingTop();
        r0 = r23;
        r5 = r0.topMargin;
        r6 = r6 + r5;
        r0 = r41;
        r5 = r0.mTitleMarginTop;
        goto L_0x039c;
    L_0x0399:
        goto L_0x022a;
    L_0x039c:
        r6 = r6 + r5;
        goto L_0x0399;
    L_0x039e:
        r5 = r5 - r7;
        r5 = r5 - r15;
        r0 = r16;
        r5 = r5 - r0;
        r5 = r5 - r6;
        r0 = r23;
        r7 = r0.bottomMargin;
        r0 = r41;
        r15 = r0.mTitleMarginBottom;
        r7 = r7 + r15;
        if (r5 >= r7) goto L_0x0227;
    L_0x03af:
        r0 = r28;
        r7 = r0.bottomMargin;
        r0 = r41;
        r15 = r0.mTitleMarginBottom;
        r7 = r7 + r15;
        r5 = r7 - r5;
        r5 = r16 - r5;
        goto L_0x03c0;
    L_0x03bd:
        goto L_0x0227;
    L_0x03c0:
        r4 = 0;
        r16 = java.lang.Math.max(r4, r5);
        goto L_0x03bd;
    L_0x03c6:
        r6 = r5 - r7;
        r0 = r28;
        r5 = r0.bottomMargin;
        r6 = r6 - r5;
        r0 = r41;
        r5 = r0.mTitleMarginBottom;
        r6 = r6 - r5;
        goto L_0x03d6;
    L_0x03d3:
        goto L_0x022a;
    L_0x03d6:
        r6 = r6 - r15;
        goto L_0x03d3;
        goto L_0x03dc;
    L_0x03d9:
        goto L_0x0232;
    L_0x03dc:
        r5 = 0;
        goto L_0x03d9;
    L_0x03de:
        if (r30 == 0) goto L_0x048a;
    L_0x03e0:
        r0 = r41;
        r5 = r0.mTitleMarginStart;
    L_0x03e4:
        r4 = 0;
        r7 = r10[r4];
        r5 = r5 - r7;
        r4 = 0;
        r7 = java.lang.Math.max(r4, r5);
        r8 = r8 + r7;
        r5 = -r5;
        r4 = 0;
        r5 = java.lang.Math.max(r4, r5);
        r4 = 0;
        r10[r4] = r5;
        r7 = r8;
        r5 = r8;
        if (r13 == 0) goto L_0x043b;
    L_0x03fb:
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r22 = r0.getLayoutParams();
        r37 = r22;
        r37 = (android.support.v7.widget.Toolbar.LayoutParams) r37;
        r23 = r37;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r7 = r0.getMeasuredWidth();
        r7 = r8 + r7;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r15 = r0.getMeasuredHeight();
        r15 = r6 + r15;
        r0 = r41;
        r0 = r0.mTitleTextView;
        r20 = r0;
        r0.layout(r8, r6, r7, r15);
        r0 = r41;
        r6 = r0.mTitleMarginEnd;
        r7 = r7 + r6;
        goto L_0x0435;
    L_0x0432:
        goto L_0x03e4;
    L_0x0435:
        r0 = r23;
        r6 = r0.bottomMargin;
        r6 = r15 + r6;
    L_0x043b:
        if (r21 == 0) goto L_0x047f;
    L_0x043d:
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r22 = r0.getLayoutParams();
        r38 = r22;
        r38 = (android.support.v7.widget.Toolbar.LayoutParams) r38;
        r23 = r38;
        r0 = r23;
        r15 = r0.topMargin;
        r6 = r6 + r15;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r15 = r0.getMeasuredWidth();
        r15 = r5 + r15;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r16 = r0.getMeasuredHeight();
        r16 = r6 + r16;
        r0 = r41;
        r0 = r0.mSubtitleTextView;
        r20 = r0;
        r1 = r16;
        r0.layout(r5, r6, r15, r1);
        r0 = r41;
        r6 = r0.mTitleMarginEnd;
        r5 = r15 + r6;
        r0 = r23;
        r6 = r0.bottomMargin;
    L_0x047f:
        if (r30 == 0) goto L_0x02ce;
    L_0x0481:
        goto L_0x0485;
    L_0x0482:
        goto L_0x02ce;
    L_0x0485:
        r8 = java.lang.Math.max(r7, r5);
        goto L_0x0482;
    L_0x048a:
        r5 = 0;
        goto L_0x0432;
    L_0x048c:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r4 = 5;
        r0 = r41;
        r1 = r34;
        r0.addCustomViewsWithGravity(r1, r4);
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r6 = r0.size();
        r5 = 0;
    L_0x04a5:
        if (r5 >= r6) goto L_0x04c4;
    L_0x04a7:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r35 = r0.get(r5);
        r39 = r35;
        r39 = (android.view.View) r39;
        r18 = r39;
        r0 = r41;
        r1 = r18;
        r2 = r43;
        r9 = r0.layoutChildRight(r1, r9, r10, r2);
        r5 = r5 + 1;
        goto L_0x04a5;
    L_0x04c4:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r4 = 1;
        r0 = r41;
        r1 = r34;
        r0.addCustomViewsWithGravity(r1, r4);
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r0 = r41;
        r1 = r34;
        r6 = r0.getViewListMeasuredWidth(r1, r10);
        r0 = r46;
        r1 = r44;
        r0 = r0 - r1;
        r46 = r0;
        r45 = r46 - r45;
        r45 = r45 / 2;
        r45 = r44 + r45;
        r44 = r6 / 2;
        r44 = r45 - r44;
        r45 = r44 + r6;
        r0 = r44;
        if (r0 >= r8) goto L_0x052c;
    L_0x04f7:
        r44 = r8;
    L_0x04f9:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r45 = r0.size();
        r46 = 0;
    L_0x0505:
        r0 = r46;
        r1 = r45;
        if (r0 >= r1) goto L_0x053d;
    L_0x050b:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r1 = r46;
        r35 = r0.get(r1);
        r40 = r35;
        r40 = (android.view.View) r40;
        r18 = r40;
        r0 = r41;
        r1 = r18;
        r2 = r44;
        r3 = r43;
        r44 = r0.layoutChildLeft(r1, r2, r10, r3);
        r46 = r46 + 1;
        goto L_0x0505;
    L_0x052c:
        r0 = r45;
        if (r0 <= r9) goto L_0x04f9;
    L_0x0530:
        r0 = r45;
        r0 = r0 - r9;
        r45 = r0;
        r0 = r44;
        r1 = r45;
        r0 = r0 - r1;
        r44 = r0;
        goto L_0x04f9;
    L_0x053d:
        r0 = r41;
        r0 = r0.mTempViews;
        r34 = r0;
        r0.clear();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    public Toolbar(Context $r1) throws  {
        this($r1, null);
    }

    public Toolbar(Context $r1, @Nullable AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.toolbarStyle);
    }

    public Toolbar(Context $r1, @Nullable AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mContentInsets = new RtlSpacingHelper();
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList();
        this.mHiddenViews = new ArrayList();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new C02751();
        this.mShowOverflowMenuRunnable = new C02762();
        TintTypedArray $r8 = TintTypedArray.obtainStyledAttributes(getContext(), $r2, C0192R.styleable.Toolbar, $i0, 0);
        this.mTitleTextAppearance = $r8.getResourceId(C0192R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = $r8.getResourceId(C0192R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = $r8.getInteger(C0192R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = 48;
        $i0 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_titleMargins, 0);
        this.mTitleMarginBottom = $i0;
        this.mTitleMarginTop = $i0;
        this.mTitleMarginEnd = $i0;
        this.mTitleMarginStart = $i0;
        $i0 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_titleMarginStart, -1);
        if ($i0 >= 0) {
            this.mTitleMarginStart = $i0;
        }
        $i0 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_titleMarginEnd, -1);
        if ($i0 >= 0) {
            this.mTitleMarginEnd = $i0;
        }
        $i0 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_titleMarginTop, -1);
        if ($i0 >= 0) {
            this.mTitleMarginTop = $i0;
        }
        $i0 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_titleMarginBottom, -1);
        if ($i0 >= 0) {
            this.mTitleMarginBottom = $i0;
        }
        this.mMaxButtonHeight = $r8.getDimensionPixelSize(C0192R.styleable.Toolbar_maxButtonHeight, -1);
        $i0 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int $i1 = $r8.getDimensionPixelOffset(C0192R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        this.mContentInsets.setAbsolute($r8.getDimensionPixelSize(C0192R.styleable.Toolbar_contentInsetLeft, 0), $r8.getDimensionPixelSize(C0192R.styleable.Toolbar_contentInsetRight, 0));
        if (!($i0 == Integer.MIN_VALUE && $i1 == Integer.MIN_VALUE)) {
            this.mContentInsets.setRelative($i0, $i1);
        }
        this.mCollapseIcon = $r8.getDrawable(C0192R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = $r8.getText(C0192R.styleable.Toolbar_collapseContentDescription);
        CharSequence $r10 = $r8.getText(C0192R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty($r10)) {
            setTitle($r10);
        }
        $r10 = $r8.getText(C0192R.styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty($r10)) {
            setSubtitle($r10);
        }
        this.mPopupContext = getContext();
        setPopupTheme($r8.getResourceId(C0192R.styleable.Toolbar_popupTheme, 0));
        Drawable $r9 = $r8.getDrawable(C0192R.styleable.Toolbar_navigationIcon);
        if ($r9 != null) {
            setNavigationIcon($r9);
        }
        $r10 = $r8.getText(C0192R.styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty($r10)) {
            setNavigationContentDescription($r10);
        }
        $r9 = $r8.getDrawable(C0192R.styleable.Toolbar_logo);
        if ($r9 != null) {
            setLogo($r9);
        }
        $r10 = $r8.getText(C0192R.styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty($r10)) {
            setLogoDescription($r10);
        }
        if ($r8.hasValue(C0192R.styleable.Toolbar_titleTextColor)) {
            setTitleTextColor($r8.getColor(C0192R.styleable.Toolbar_titleTextColor, -1));
        }
        if ($r8.hasValue(C0192R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor($r8.getColor(C0192R.styleable.Toolbar_subtitleTextColor, -1));
        }
        $r8.recycle();
        this.mDrawableManager = AppCompatDrawableManager.get();
    }

    public void setPopupTheme(@StyleRes int $i0) throws  {
        if (this.mPopupTheme != $i0) {
            this.mPopupTheme = $i0;
            if ($i0 == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), $i0);
            }
        }
    }

    public int getPopupTheme() throws  {
        return this.mPopupTheme;
    }

    public void onRtlPropertiesChanged(int $i0) throws  {
        boolean $z0 = true;
        if (VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged($i0);
        }
        RtlSpacingHelper $r1 = this.mContentInsets;
        if ($i0 != 1) {
            $z0 = false;
        }
        $r1.setDirection($z0);
    }

    public void setLogo(@DrawableRes int $i0) throws  {
        setLogo(this.mDrawableManager.getDrawable(getContext(), $i0));
    }

    public boolean canShowOverflowMenu() throws  {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() throws  {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() throws  {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() throws  {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() throws  {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    public void setMenu(MenuBuilder $r1, ActionMenuPresenter $r2) throws  {
        if ($r1 != null || this.mMenuView != null) {
            ensureMenuView();
            MenuBuilder $r3 = this.mMenuView.peekMenu();
            if ($r3 != $r1) {
                if ($r3 != null) {
                    $r3.removeMenuPresenter(this.mOuterActionMenuPresenter);
                    $r3.removeMenuPresenter(this.mExpandedMenuPresenter);
                }
                if (this.mExpandedMenuPresenter == null) {
                    this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
                }
                $r2.setExpandedActionViewsExclusive(true);
                if ($r1 != null) {
                    $r1.addMenuPresenter($r2, this.mPopupContext);
                    $r1.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
                } else {
                    $r2.initForMenu(this.mPopupContext, null);
                    this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
                    $r2.updateMenuView(true);
                    this.mExpandedMenuPresenter.updateMenuView(true);
                }
                this.mMenuView.setPopupTheme(this.mPopupTheme);
                this.mMenuView.setPresenter($r2);
                this.mOuterActionMenuPresenter = $r2;
            }
        }
    }

    public void dismissPopupMenus() throws  {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    public boolean isTitleTruncated() throws  {
        if (this.mTitleTextView == null) {
            return false;
        }
        Layout $r2 = this.mTitleTextView.getLayout();
        if ($r2 == null) {
            return false;
        }
        int $i0 = $r2.getLineCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($r2.getEllipsisCount($i1) > 0) {
                return true;
            }
        }
        return false;
    }

    public void setLogo(Drawable $r1) throws  {
        if ($r1 != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable($r1);
        }
    }

    public Drawable getLogo() throws  {
        return this.mLogoView != null ? this.mLogoView.getDrawable() : null;
    }

    public void setLogoDescription(@StringRes int $i0) throws  {
        setLogoDescription(getContext().getText($i0));
    }

    public void setLogoDescription(CharSequence $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription($r1);
        }
    }

    public CharSequence getLogoDescription() throws  {
        return this.mLogoView != null ? this.mLogoView.getContentDescription() : null;
    }

    private void ensureLogoView() throws  {
        if (this.mLogoView == null) {
            this.mLogoView = new ImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() throws  {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void collapseActionView() throws  {
        MenuItemImpl $r2 = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if ($r2 != null) {
            $r2.collapseActionView();
        }
    }

    public CharSequence getTitle() throws  {
        return this.mTitleText;
    }

    public void setTitle(@StringRes int $i0) throws  {
        setTitle(getContext().getText($i0));
    }

    public void setTitle(CharSequence $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            if (this.mTitleTextView == null) {
                Context $r3 = getContext();
                this.mTitleTextView = new TextView($r3);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance($r3, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText($r1);
        }
        this.mTitleText = $r1;
    }

    public CharSequence getSubtitle() throws  {
        return this.mSubtitleText;
    }

    public void setSubtitle(@StringRes int $i0) throws  {
        setSubtitle(getContext().getText($i0));
    }

    public void setSubtitle(CharSequence $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            if (this.mSubtitleTextView == null) {
                Context $r3 = getContext();
                this.mSubtitleTextView = new TextView($r3);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance($r3, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText($r1);
        }
        this.mSubtitleText = $r1;
    }

    public void setTitleTextAppearance(Context $r1, @StyleRes int $i0) throws  {
        this.mTitleTextAppearance = $i0;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance($r1, $i0);
        }
    }

    public void setSubtitleTextAppearance(Context $r1, @StyleRes int $i0) throws  {
        this.mSubtitleTextAppearance = $i0;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance($r1, $i0);
        }
    }

    public void setTitleTextColor(@ColorInt int $i0) throws  {
        this.mTitleTextColor = $i0;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor($i0);
        }
    }

    public void setSubtitleTextColor(@ColorInt int $i0) throws  {
        this.mSubtitleTextColor = $i0;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor($i0);
        }
    }

    @Nullable
    public CharSequence getNavigationContentDescription() throws  {
        return this.mNavButtonView != null ? this.mNavButtonView.getContentDescription() : null;
    }

    public void setNavigationContentDescription(@StringRes int $i0) throws  {
        setNavigationContentDescription($i0 != 0 ? getContext().getText($i0) : null);
    }

    public void setNavigationContentDescription(@Nullable CharSequence $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription($r1);
        }
    }

    public void setNavigationIcon(@DrawableRes int $i0) throws  {
        setNavigationIcon(this.mDrawableManager.getDrawable(getContext(), $i0));
    }

    public void setNavigationIcon(@Nullable Drawable $r1) throws  {
        if ($r1 != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable($r1);
        }
    }

    @Nullable
    public Drawable getNavigationIcon() throws  {
        return this.mNavButtonView != null ? this.mNavButtonView.getDrawable() : null;
    }

    public void setNavigationOnClickListener(OnClickListener $r1) throws  {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener($r1);
    }

    public Menu getMenu() throws  {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public void setOverflowIcon(@Nullable Drawable $r1) throws  {
        ensureMenu();
        this.mMenuView.setOverflowIcon($r1);
    }

    @Nullable
    public Drawable getOverflowIcon() throws  {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    private void ensureMenu() throws  {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder $r2 = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            $r2.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() throws  {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams $r6 = generateDefaultLayoutParams();
            $r6.gravity = GravityCompat.END | (this.mButtonGravity & 112);
            this.mMenuView.setLayoutParams($r6);
            addSystemView(this.mMenuView, false);
        }
    }

    private MenuInflater getMenuInflater() throws  {
        return new SupportMenuInflater(getContext());
    }

    public void inflateMenu(@MenuRes int $i0) throws  {
        getMenuInflater().inflate($i0, getMenu());
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener $r1) throws  {
        this.mOnMenuItemClickListener = $r1;
    }

    public void setContentInsetsRelative(int $i0, int $i1) throws  {
        this.mContentInsets.setRelative($i0, $i1);
    }

    public int getContentInsetStart() throws  {
        return this.mContentInsets.getStart();
    }

    public int getContentInsetEnd() throws  {
        return this.mContentInsets.getEnd();
    }

    public void setContentInsetsAbsolute(int $i0, int $i1) throws  {
        this.mContentInsets.setAbsolute($i0, $i1);
    }

    public int getContentInsetLeft() throws  {
        return this.mContentInsets.getLeft();
    }

    public int getContentInsetRight() throws  {
        return this.mContentInsets.getRight();
    }

    private void ensureNavButtonView() throws  {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new ImageButton(getContext(), null, C0192R.attr.toolbarNavigationButtonStyle);
            LayoutParams $r3 = generateDefaultLayoutParams();
            $r3.gravity = GravityCompat.START | (this.mButtonGravity & 112);
            this.mNavButtonView.setLayoutParams($r3);
        }
    }

    private void ensureCollapseButtonView() throws  {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new ImageButton(getContext(), null, C0192R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams $r5 = generateDefaultLayoutParams();
            $r5.gravity = GravityCompat.START | (this.mButtonGravity & 112);
            $r5.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams($r5);
            this.mCollapseButtonView.setOnClickListener(new C02773());
        }
    }

    private void addSystemView(View $r1, boolean $z0) throws  {
        LayoutParams $r3;
        android.view.ViewGroup.LayoutParams $r2 = $r1.getLayoutParams();
        if ($r2 == null) {
            $r3 = generateDefaultLayoutParams();
        } else if (checkLayoutParams($r2)) {
            $r3 = (LayoutParams) $r2;
        } else {
            $r3 = generateLayoutParams($r2);
        }
        $r3.mViewType = 1;
        if (!$z0 || this.mExpandedActionView == null) {
            addView($r1, $r3);
            return;
        }
        $r1.setLayoutParams($r3);
        this.mHiddenViews.add($r1);
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        if (!(this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null)) {
            $r1.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        $r1.isOverflowOpen = isOverflowMenuShowing();
        return $r1;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            MenuBuilder $r4 = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
            if (!($r2.expandedMenuItemId == 0 || this.mExpandedMenuPresenter == null || $r4 == null)) {
                MenuItem $r6 = $r4.findItem($r2.expandedMenuItemId);
                if ($r6 != null) {
                    MenuItemCompat.expandActionView($r6);
                }
            }
            if ($r2.isOverflowOpen) {
                postShowOverflowMenu();
                return;
            }
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    private void postShowOverflowMenu() throws  {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionMasked($r1);
        if ($i0 == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean $z0 = super.onTouchEvent($r1);
            if ($i0 == 0 && !$z0) {
                this.mEatingTouch = true;
            }
        }
        if ($i0 != 1 && $i0 != 3) {
            return true;
        }
        this.mEatingTouch = false;
        return true;
    }

    public boolean onHoverEvent(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionMasked($r1);
        if ($i0 == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean $z0 = super.onHoverEvent($r1);
            if ($i0 == 9 && !$z0) {
                this.mEatingHover = true;
            }
        }
        if ($i0 != 10 && $i0 != 3) {
            return true;
        }
        this.mEatingHover = false;
        return true;
    }

    private void measureChildConstrained(View $r1, int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        MarginLayoutParams $r3 = (MarginLayoutParams) $r1.getLayoutParams();
        $i0 = getChildMeasureSpec($i0, (((getPaddingLeft() + getPaddingRight()) + $r3.leftMargin) + $r3.rightMargin) + $i1, $r3.width);
        $i1 = getChildMeasureSpec($i2, (((getPaddingTop() + getPaddingBottom()) + $r3.topMargin) + $r3.bottomMargin) + $i3, $r3.height);
        $i2 = $i1;
        $i3 = MeasureSpec.getMode($i1);
        if ($i3 != 1073741824 && $i4 >= 0) {
            $i2 = MeasureSpec.makeMeasureSpec($i3 != 0 ? Math.min(MeasureSpec.getSize($i1), $i4) : $i4, 1073741824);
        }
        $r1.measure($i0, $i2);
    }

    private int measureChildCollapseMargins(View $r1, int $i0, int $i1, int $i2, int $i3, int[] $r2) throws  {
        MarginLayoutParams $r4 = (MarginLayoutParams) $r1.getLayoutParams();
        int $i5 = $r4.leftMargin - $r2[0];
        int $i6 = $r4.rightMargin - $r2[1];
        int $i4 = Math.max(0, $i5) + Math.max(0, $i6);
        $r2[0] = Math.max(0, -$i5);
        $r2[1] = Math.max(0, -$i6);
        $r1.measure(getChildMeasureSpec($i0, ((getPaddingLeft() + getPaddingRight()) + $i4) + $i1, $r4.width), getChildMeasureSpec($i2, (((getPaddingTop() + getPaddingBottom()) + $r4.topMargin) + $r4.bottomMargin) + $i3, $r4.height));
        return $r1.getMeasuredWidth() + $i4;
    }

    private boolean shouldCollapse() throws  {
        if (!this.mCollapsible) {
            return false;
        }
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r1 = getChildAt($i1);
            if (shouldLayout($r1) && $r1.getMeasuredWidth() > 0 && $r1.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        byte $b7;
        byte $b8;
        int i;
        int i2;
        int $i5 = 0;
        int $i6 = 0;
        int[] $r1 = this.mTempMargins;
        if (ViewUtils.isLayoutRtl(this)) {
            $b7 = (byte) 1;
            $b8 = (byte) 0;
        } else {
            $b7 = (byte) 0;
            $b8 = (byte) 1;
        }
        int $i2 = 0;
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, $i0, 0, $i1, 0, this.mMaxButtonHeight);
            $i2 = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
            $i5 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
            $i6 = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState(this.mNavButtonView));
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, $i0, 0, $i1, 0, this.mMaxButtonHeight);
            $i2 = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
            int $i9 = this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView);
            i = $i9;
            $i5 = Math.max($i5, $i9);
            $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState(this.mCollapseButtonView));
        }
        int $i10 = getContentInsetStart();
        i = 0 + Math.max($i10, $i2);
        $r1[$b7] = Math.max(0, $i10 - $i2);
        $i2 = 0;
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, $i0, i, $i1, 0, this.mMaxButtonHeight);
            ActionMenuView $r4 = this.mMenuView;
            $i2 = $r4.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            $r4 = this.mMenuView;
            $i9 = $r4.getMeasuredHeight() + getVerticalMargins(this.mMenuView);
            $i10 = $i9;
            $i5 = Math.max($i5, $i9);
            $r4 = this.mMenuView;
            $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState($r4));
        }
        $i10 = getContentInsetEnd();
        i += Math.max($i10, $i2);
        $r1[$b8] = Math.max(0, $i10 - $i2);
        if (shouldLayout(this.mExpandedActionView)) {
            i += measureChildCollapseMargins(this.mExpandedActionView, $i0, i, $i1, 0, $r1);
            View $r5 = this.mExpandedActionView;
            $i5 = Math.max($i5, $r5.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
            $r5 = this.mExpandedActionView;
            $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState($r5));
        }
        if (shouldLayout(this.mLogoView)) {
            i += measureChildCollapseMargins(this.mLogoView, $i0, i, $i1, 0, $r1);
            ImageView $r6 = this.mLogoView;
            $i5 = Math.max($i5, $r6.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            $r6 = this.mLogoView;
            $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState($r6));
        }
        $i2 = getChildCount();
        for ($i10 = 0; $i10 < $i2; $i10++) {
            View $r52 = getChildAt($i10);
            if (((LayoutParams) $r52.getLayoutParams()).mViewType == 0 && shouldLayout($r52)) {
                i += measureChildCollapseMargins($r52, $i0, i, $i1, 0, $r1);
                $i9 = $r52.getMeasuredHeight() + getVerticalMargins($r52);
                i2 = $i9;
                $i5 = Math.max($i5, $i9);
                $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState($r52));
            }
        }
        $i2 = 0;
        $i10 = 0;
        i2 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int $i4 = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            measureChildCollapseMargins(this.mTitleTextView, $i0, i + $i4, $i1, i2, $r1);
            TextView $r2 = this.mTitleTextView;
            $i2 = $r2.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            $r2 = this.mTitleTextView;
            $i10 = $r2.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            $r2 = this.mTitleTextView;
            $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState($r2));
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            $i2 = Math.max($i2, measureChildCollapseMargins(this.mSubtitleTextView, $i0, i + $i4, $i1, $i10 + i2, $r1));
            $r2 = this.mSubtitleTextView;
            $i10 += $r2.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            $r2 = this.mSubtitleTextView;
            $i6 = ViewUtils.combineMeasuredStates($i6, ViewCompat.getMeasuredState($r2));
        }
        i += $i2;
        $i5 = Math.max($i5, $i10) + (getPaddingTop() + getPaddingBottom());
        $i0 = ViewCompat.resolveSizeAndState(Math.max(i + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), $i0, -16777216 & $i6);
        $i1 = ViewCompat.resolveSizeAndState(Math.max($i5, getSuggestedMinimumHeight()), $i1, $i6 << 16);
        if (shouldCollapse()) {
            $i1 = 0;
        }
        setMeasuredDimension($i0, $i1);
    }

    private int getViewListMeasuredWidth(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;[I)I"}) List<View> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;[I)I"}) int[] $r2) throws  {
        int $i0 = $r2[0];
        int $i1 = $r2[1];
        int $i2 = 0;
        int $i3 = $r1.size();
        for (int $i4 = 0; $i4 < $i3; $i4++) {
            View $r4 = (View) $r1.get($i4);
            LayoutParams $r6 = (LayoutParams) $r4.getLayoutParams();
            $i0 = $r6.leftMargin - $i0;
            $i1 = $r6.rightMargin - $i1;
            int $i6 = Math.max(0, $i0);
            int $i5 = Math.max(0, $i1);
            $i0 = Math.max(0, -$i0);
            $i1 = Math.max(0, -$i1);
            $i2 += ($r4.getMeasuredWidth() + $i6) + $i5;
        }
        return $i2;
    }

    private int layoutChildLeft(View $r1, int $i2, int[] $r2, int $i0) throws  {
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        int $i1 = $r4.leftMargin - $r2[0];
        $i2 += Math.max(0, $i1);
        $r2[0] = Math.max(0, -$i1);
        $i1 = getChildTop($r1, $i0);
        $i0 = $r1.getMeasuredWidth();
        $r1.layout($i2, $i1, $i2 + $i0, $r1.getMeasuredHeight() + $i1);
        return $i2 + ($r4.rightMargin + $i0);
    }

    private int layoutChildRight(View $r1, int $i2, int[] $r2, int $i0) throws  {
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        int $i1 = $r4.rightMargin - $r2[1];
        $i2 -= Math.max(0, $i1);
        $r2[1] = Math.max(0, -$i1);
        $i1 = getChildTop($r1, $i0);
        $i0 = $r1.getMeasuredWidth();
        $r1.layout($i2 - $i0, $i1, $i2, $r1.getMeasuredHeight() + $i1);
        return $i2 - ($r4.leftMargin + $i0);
    }

    private int getChildTop(View $r1, int $i0) throws  {
        LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
        int $i2 = $r1.getMeasuredHeight();
        if ($i0 > 0) {
            $i0 = ($i2 - $i0) / 2;
        } else {
            $i0 = 0;
        }
        switch (getChildVerticalGravity($r3.gravity)) {
            case 48:
                return getPaddingTop() - $i0;
            case 80:
                return (((getHeight() - getPaddingBottom()) - $i2) - $r3.bottomMargin) - $i0;
            default:
                $i0 = getPaddingTop();
                int $i3 = getPaddingBottom();
                int $i4 = getHeight();
                int $i1 = ((($i4 - $i0) - $i3) - $i2) / 2;
                if ($i1 < $r3.topMargin) {
                    $i1 = $r3.topMargin;
                } else {
                    $i2 = ((($i4 - $i3) - $i2) - $i1) - $i0;
                    if ($i2 < $r3.bottomMargin) {
                        $i1 = Math.max(0, $i1 - ($r3.bottomMargin - $i2));
                    }
                }
                return $i0 + $i1;
        }
    }

    private int getChildVerticalGravity(int $i0) throws  {
        $i0 &= 112;
        switch ($i0) {
            case 16:
            case 48:
            case 80:
                return $i0;
            default:
                return this.mGravity & 112;
        }
    }

    private void addCustomViewsWithGravity(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;I)V"}) List<View> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;I)V"}) int $i0) throws  {
        boolean $z0 = true;
        if (ViewCompat.getLayoutDirection(this) != 1) {
            $z0 = false;
        }
        int $i1 = getChildCount();
        $i0 = GravityCompat.getAbsoluteGravity($i0, ViewCompat.getLayoutDirection(this));
        $r1.clear();
        View $r2;
        LayoutParams $r4;
        if ($z0) {
            for ($i1--; $i1 >= 0; $i1--) {
                $r2 = getChildAt($i1);
                $r4 = (LayoutParams) $r2.getLayoutParams();
                if ($r4.mViewType == 0 && shouldLayout($r2) && getChildHorizontalGravity($r4.gravity) == $i0) {
                    $r1.add($r2);
                }
            }
            return;
        }
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            $r2 = getChildAt($i2);
            $r4 = (LayoutParams) $r2.getLayoutParams();
            if ($r4.mViewType == 0 && shouldLayout($r2) && getChildHorizontalGravity($r4.gravity) == $i0) {
                $r1.add($r2);
            }
        }
    }

    private int getChildHorizontalGravity(int $i0) throws  {
        int $i1 = ViewCompat.getLayoutDirection(this);
        $i0 = GravityCompat.getAbsoluteGravity($i0, $i1) & 7;
        switch ($i0) {
            case 1:
            case 3:
            case 5:
                return $i0;
            case 2:
            case 4:
                break;
            default:
                break;
        }
        return $i1 == 1 ? (byte) 5 : (byte) 3;
    }

    private boolean shouldLayout(View $r1) throws  {
        return ($r1 == null || $r1.getParent() != this || $r1.getVisibility() == 8) ? false : true;
    }

    private int getHorizontalMargins(View $r1) throws  {
        MarginLayoutParams $r3 = (MarginLayoutParams) $r1.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart($r3) + MarginLayoutParamsCompat.getMarginEnd($r3);
    }

    private int getVerticalMargins(View $r1) throws  {
        MarginLayoutParams $r3 = (MarginLayoutParams) $r1.getLayoutParams();
        return $r3.topMargin + $r3.bottomMargin;
    }

    public LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        if ($r1 instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) $r1);
        }
        if ($r1 instanceof android.support.v7.app.ActionBar.LayoutParams) {
            return new LayoutParams((android.support.v7.app.ActionBar.LayoutParams) $r1);
        }
        if ($r1 instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) $r1);
        }
        return new LayoutParams($r1);
    }

    protected LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams(-2, -2);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return super.checkLayoutParams($r1) && ($r1 instanceof LayoutParams);
    }

    private static boolean isCustomView(View $r0) throws  {
        return ((LayoutParams) $r0.getLayoutParams()).mViewType == 0;
    }

    public DecorToolbar getWrapper() throws  {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    void removeChildrenForExpandedActionView() throws  {
        for (int $i0 = getChildCount() - 1; $i0 >= 0; $i0--) {
            View $r1 = getChildAt($i0);
            if (!(((LayoutParams) $r1.getLayoutParams()).mViewType == 2 || $r1 == this.mMenuView)) {
                removeViewAt($i0);
                this.mHiddenViews.add($r1);
            }
        }
    }

    void addChildrenForExpandedActionView() throws  {
        for (int $i0 = this.mHiddenViews.size() - 1; $i0 >= 0; $i0--) {
            addView((View) this.mHiddenViews.get($i0));
        }
        this.mHiddenViews.clear();
    }

    private boolean isChildOrHidden(View $r1) throws  {
        return $r1.getParent() == this || this.mHiddenViews.contains($r1);
    }

    public void setCollapsible(boolean $z0) throws  {
        this.mCollapsible = $z0;
        requestLayout();
    }

    public void setMenuCallbacks(Callback $r1, MenuBuilder.Callback $r2) throws  {
        this.mActionMenuPresenterCallback = $r1;
        this.mMenuBuilderCallback = $r2;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks($r1, $r2);
        }
    }
}
