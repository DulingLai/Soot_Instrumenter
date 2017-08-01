package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView extends LinearLayoutCompat implements ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter() throws ;

        boolean needsDividerBefore() throws ;
    }

    private class ActionMenuPresenterCallback implements Callback {
        public boolean onOpenSubMenu(MenuBuilder subMenu) throws  {
            return false;
        }

        private ActionMenuPresenterCallback() throws  {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) throws  {
        }
    }

    public static class LayoutParams extends android.support.v7.widget.LinearLayoutCompat.LayoutParams {
        @ExportedProperty
        public int cellsUsed;
        @ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ExportedProperty
        public int extraPixels;
        @ExportedProperty
        public boolean isOverflowButton;
        @ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super((android.view.ViewGroup.LayoutParams) $r1);
            this.isOverflowButton = $r1.isOverflowButton;
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
            this.isOverflowButton = false;
        }

        LayoutParams(int $i0, int $i1, boolean $z0) throws  {
            super($i0, $i1);
            this.isOverflowButton = $z0;
        }
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        private MenuBuilderCallback() throws  {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem $r2) throws  {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick($r2);
        }

        public void onMenuModeChange(MenuBuilder $r1) throws  {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange($r1);
            }
        }
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem) throws ;
    }

    private void onMeasureExactFormat(int r48, int r49) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:107:0x02f2
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
        r47 = this;
        r0 = r49;
        r4 = android.view.View.MeasureSpec.getMode(r0);
        r0 = r48;
        r5 = android.view.View.MeasureSpec.getSize(r0);
        r0 = r49;
        r48 = android.view.View.MeasureSpec.getSize(r0);
        r0 = r47;
        r6 = r0.getPaddingLeft();
        r0 = r47;
        r7 = r0.getPaddingRight();
        r6 = r6 + r7;
        r0 = r47;
        r7 = r0.getPaddingTop();
        r0 = r47;
        r8 = r0.getPaddingBottom();
        r9 = r7 + r8;
        r10 = -2;
        r0 = r49;
        r49 = getChildMeasureSpec(r0, r9, r10);
        r5 = r5 - r6;
        r0 = r47;
        r6 = r0.mMinCellSize;
        r7 = r5 / r6;
        r0 = r47;
        r6 = r0.mMinCellSize;
        r8 = r5 % r6;
        if (r7 != 0) goto L_0x004a;
    L_0x0043:
        r10 = 0;
        r0 = r47;
        r0.setMeasuredDimension(r5, r10);
        return;
    L_0x004a:
        r0 = r47;
        r6 = r0.mMinCellSize;
        r8 = r8 / r7;
        r6 = r6 + r8;
        r11 = r7;
        r8 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r0 = r47;
        r7 = r0.getChildCount();
        r18 = 0;
    L_0x0060:
        r0 = r18;
        if (r0 >= r7) goto L_0x013c;
    L_0x0064:
        r0 = r47;
        r1 = r18;
        r19 = r0.getChildAt(r1);
        r0 = r19;
        r20 = r0.getVisibility();
        r10 = 8;
        r0 = r20;
        if (r0 != r10) goto L_0x007b;
    L_0x0078:
        r18 = r18 + 1;
        goto L_0x0060;
    L_0x007b:
        r0 = r19;
        r0 = r0 instanceof android.support.v7.view.menu.ActionMenuItemView;
        r21 = r0;
        r14 = r14 + 1;
        if (r21 == 0) goto L_0x009f;
    L_0x0085:
        r0 = r47;
        r0 = r0.mGeneratedItemPadding;
        r20 = r0;
        r0 = r47;
        r0 = r0.mGeneratedItemPadding;
        r22 = r0;
        r10 = 0;
        r23 = 0;
        r0 = r19;
        r1 = r20;
        r2 = r22;
        r3 = r23;
        r0.setPadding(r1, r10, r2, r3);
    L_0x009f:
        r0 = r19;
        r24 = r0.getLayoutParams();
        r26 = r24;
        r26 = (android.support.v7.widget.ActionMenuView.LayoutParams) r26;
        r25 = r26;
        r10 = 0;
        r0 = r25;
        r0.expanded = r10;
        r10 = 0;
        r0 = r25;
        r0.extraPixels = r10;
        r10 = 0;
        r0 = r25;
        r0.cellsUsed = r10;
        r10 = 0;
        r0 = r25;
        r0.expandable = r10;
        r10 = 0;
        r0 = r25;
        r0.leftMargin = r10;
        r10 = 0;
        r0 = r25;
        r0.rightMargin = r10;
        if (r21 == 0) goto L_0x0136;
    L_0x00cb:
        r28 = r19;
        r28 = (android.support.v7.view.menu.ActionMenuItemView) r28;
        r27 = r28;
        goto L_0x00d5;
    L_0x00d2:
        goto L_0x0078;
    L_0x00d5:
        r0 = r27;
        r21 = r0.hasText();
        if (r21 == 0) goto L_0x0136;
    L_0x00dd:
        r21 = 1;
    L_0x00df:
        r0 = r21;
        r1 = r25;
        r1.preventEdgeOffset = r0;
        r0 = r25;
        r0 = r0.isOverflowButton;
        r21 = r0;
        if (r21 == 0) goto L_0x0139;
    L_0x00ed:
        r20 = 1;
    L_0x00ef:
        r0 = r19;
        r1 = r20;
        r2 = r49;
        r20 = measureChildForCells(r0, r6, r1, r2, r9);
        r0 = r20;
        r12 = java.lang.Math.max(r12, r0);
        r0 = r25;
        r0 = r0.expandable;
        r21 = r0;
        if (r21 == 0) goto L_0x0109;
    L_0x0107:
        r13 = r13 + 1;
    L_0x0109:
        r0 = r25;
        r0 = r0.isOverflowButton;
        r21 = r0;
        if (r21 == 0) goto L_0x0112;
    L_0x0111:
        r15 = 1;
    L_0x0112:
        r0 = r20;
        r11 = r11 - r0;
        r0 = r19;
        r22 = r0.getMeasuredHeight();
        r0 = r22;
        r8 = java.lang.Math.max(r8, r0);
        r10 = 1;
        r0 = r20;
        if (r0 != r10) goto L_0x0078;
    L_0x0126:
        r10 = 1;
        r20 = r10 << r18;
        r0 = r20;
        r0 = (long) r0;
        r29 = r0;
        r0 = r16;
        r2 = r29;
        r0 = r0 | r2;
        r16 = r0;
        goto L_0x00d2;
    L_0x0136:
        r21 = 0;
        goto L_0x00df;
    L_0x0139:
        r20 = r11;
        goto L_0x00ef;
    L_0x013c:
        if (r15 == 0) goto L_0x0170;
    L_0x013e:
        r10 = 2;
        if (r14 != r10) goto L_0x0170;
    L_0x0141:
        r31 = 1;
    L_0x0143:
        r21 = 0;
    L_0x0145:
        if (r13 <= 0) goto L_0x01b5;
    L_0x0147:
        if (r11 <= 0) goto L_0x01b5;
    L_0x0149:
        r18 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r29 = 0;
        r20 = 0;
        r9 = 0;
    L_0x0151:
        if (r9 >= r7) goto L_0x01aa;
    L_0x0153:
        r0 = r47;
        r19 = r0.getChildAt(r9);
        r0 = r19;
        r24 = r0.getLayoutParams();
        r32 = r24;
        r32 = (android.support.v7.widget.ActionMenuView.LayoutParams) r32;
        r25 = r32;
        r0 = r25;
        r0 = r0.expandable;
        r33 = r0;
        if (r33 != 0) goto L_0x0173;
    L_0x016d:
        r9 = r9 + 1;
        goto L_0x0151;
    L_0x0170:
        r31 = 0;
        goto L_0x0143;
    L_0x0173:
        r0 = r25;
        r0 = r0.cellsUsed;
        r22 = r0;
        r1 = r18;
        if (r0 >= r1) goto L_0x018e;
    L_0x017d:
        r0 = r25;
        r0 = r0.cellsUsed;
        r18 = r0;
        r10 = 1;
        r20 = r10 << r9;
        r0 = r20;
        r0 = (long) r0;
        r29 = r0;
        r20 = 1;
        goto L_0x016d;
    L_0x018e:
        r0 = r25;
        r0 = r0.cellsUsed;
        r22 = r0;
        r1 = r18;
        if (r0 != r1) goto L_0x016d;
    L_0x0198:
        r10 = 1;
        r22 = r10 << r9;
        r0 = r22;
        r0 = (long) r0;
        r34 = r0;
        r0 = r29;
        r2 = r34;
        r0 = r0 | r2;
        r29 = r0;
        r20 = r20 + 1;
        goto L_0x016d;
    L_0x01aa:
        r0 = r16;
        r2 = r29;
        r0 = r0 | r2;
        r16 = r0;
        r0 = r20;
        if (r0 <= r11) goto L_0x0261;
    L_0x01b5:
        if (r15 != 0) goto L_0x02fd;
    L_0x01b7:
        r10 = 1;
        if (r14 != r10) goto L_0x02fd;
    L_0x01ba:
        r15 = 1;
    L_0x01bb:
        if (r11 <= 0) goto L_0x0368;
    L_0x01bd:
        r37 = 0;
        r36 = (r16 > r37 ? 1 : (r16 == r37 ? 0 : -1));
        if (r36 == 0) goto L_0x0368;
    L_0x01c3:
        r13 = r14 + -1;
        if (r11 < r13) goto L_0x01cc;
    L_0x01c7:
        if (r15 != 0) goto L_0x01cc;
    L_0x01c9:
        r10 = 1;
        if (r12 <= r10) goto L_0x0368;
    L_0x01cc:
        r0 = r16;
        r12 = java.lang.Long.bitCount(r0);
        r0 = (float) r12;
        r39 = r0;
        if (r15 != 0) goto L_0x023b;
    L_0x01d7:
        r37 = 1;
        r29 = r37 & r16;
        r37 = 0;
        r36 = (r29 > r37 ? 1 : (r29 == r37 ? 0 : -1));
        if (r36 == 0) goto L_0x0204;
    L_0x01e1:
        r10 = 0;
        r0 = r47;
        r19 = r0.getChildAt(r10);
        r0 = r19;
        r24 = r0.getLayoutParams();
        r40 = r24;
        r40 = (android.support.v7.widget.ActionMenuView.LayoutParams) r40;
        r25 = r40;
        r0 = r25;
        r15 = r0.preventEdgeOffset;
        if (r15 != 0) goto L_0x0204;
    L_0x01fa:
        r41 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r39;
        r1 = r41;
        r0 = r0 - r1;
        r39 = r0;
    L_0x0204:
        r12 = r7 + -1;
        r10 = 1;
        r12 = r10 << r12;
        r0 = (long) r12;
        r29 = r0;
        r2 = r16;
        r0 = r0 & r2;
        r29 = r0;
        r37 = 0;
        r36 = (r29 > r37 ? 1 : (r29 == r37 ? 0 : -1));
        if (r36 == 0) goto L_0x023b;
    L_0x0217:
        r12 = r7 + -1;
        r0 = r47;
        r19 = r0.getChildAt(r12);
        r0 = r19;
        r24 = r0.getLayoutParams();
        r42 = r24;
        r42 = (android.support.v7.widget.ActionMenuView.LayoutParams) r42;
        r25 = r42;
        r0 = r25;
        r15 = r0.preventEdgeOffset;
        if (r15 != 0) goto L_0x023b;
    L_0x0231:
        r41 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r39;
        r1 = r41;
        r0 = r0 - r1;
        r39 = r0;
    L_0x023b:
        r41 = 0;
        r36 = (r39 > r41 ? 1 : (r39 == r41 ? 0 : -1));
        if (r36 <= 0) goto L_0x02ff;
    L_0x0241:
        r11 = r11 * r6;
        r0 = (float) r11;
        r43 = r0;
        r39 = r43 / r39;
        r0 = r39;
        r11 = (int) r0;
    L_0x024a:
        r12 = 0;
    L_0x024b:
        if (r12 >= r7) goto L_0x0368;
    L_0x024d:
        r10 = 1;
        r13 = r10 << r12;
        r0 = (long) r13;
        r29 = r0;
        r2 = r16;
        r0 = r0 & r2;
        r29 = r0;
        r37 = 0;
        r36 = (r29 > r37 ? 1 : (r29 == r37 ? 0 : -1));
        if (r36 != 0) goto L_0x0301;
    L_0x025e:
        r12 = r12 + 1;
        goto L_0x024b;
    L_0x0261:
        r9 = r18 + 1;
        r18 = 0;
    L_0x0265:
        r0 = r18;
        if (r0 >= r7) goto L_0x02f6;
    L_0x0269:
        r0 = r47;
        r1 = r18;
        r19 = r0.getChildAt(r1);
        r0 = r19;
        r24 = r0.getLayoutParams();
        r44 = r24;
        r44 = (android.support.v7.widget.ActionMenuView.LayoutParams) r44;
        r25 = r44;
        r10 = 1;
        r20 = r10 << r18;
        r0 = r20;
        r0 = (long) r0;
        r34 = r0;
        r2 = r29;
        r0 = r0 & r2;
        r34 = r0;
        r37 = 0;
        r36 = (r34 > r37 ? 1 : (r34 == r37 ? 0 : -1));
        if (r36 != 0) goto L_0x02ae;
    L_0x0290:
        r0 = r25;
        r0 = r0.cellsUsed;
        r20 = r0;
        if (r0 != r9) goto L_0x02ab;
    L_0x0298:
        r10 = 1;
        r20 = r10 << r18;
        r0 = r20;
        r0 = (long) r0;
        r34 = r0;
        goto L_0x02a4;
    L_0x02a1:
        goto L_0x024a;
    L_0x02a4:
        r0 = r16;
        r2 = r34;
        r0 = r0 | r2;
        r16 = r0;
    L_0x02ab:
        r18 = r18 + 1;
        goto L_0x0265;
    L_0x02ae:
        if (r31 == 0) goto L_0x02dc;
    L_0x02b0:
        r0 = r25;
        r0 = r0.preventEdgeOffset;
        r21 = r0;
        if (r21 == 0) goto L_0x02dc;
    L_0x02b8:
        r10 = 1;
        if (r11 != r10) goto L_0x02dc;
    L_0x02bb:
        r0 = r47;
        r0 = r0.mGeneratedItemPadding;
        r20 = r0;
        r0 = r0 + r6;
        r20 = r0;
        goto L_0x02c8;
    L_0x02c5:
        goto L_0x025e;
    L_0x02c8:
        r0 = r47;
        r0 = r0.mGeneratedItemPadding;
        r22 = r0;
        r10 = 0;
        r23 = 0;
        r0 = r19;
        r1 = r20;
        r2 = r22;
        r3 = r23;
        r0.setPadding(r1, r10, r2, r3);
    L_0x02dc:
        r0 = r25;
        r0 = r0.cellsUsed;
        r20 = r0;
        r20 = r20 + 1;
        r0 = r20;
        r1 = r25;
        r1.cellsUsed = r0;
        r10 = 1;
        r0 = r25;
        r0.expanded = r10;
        r11 = r11 + -1;
        goto L_0x02ab;
        goto L_0x02f6;
    L_0x02f3:
        goto L_0x0145;
    L_0x02f6:
        r21 = 1;
        goto L_0x02f3;
        goto L_0x02fd;
    L_0x02fa:
        goto L_0x01bb;
    L_0x02fd:
        r15 = 0;
        goto L_0x02fa;
    L_0x02ff:
        r11 = 0;
        goto L_0x02a1;
    L_0x0301:
        r0 = r47;
        r19 = r0.getChildAt(r12);
        r0 = r19;
        r24 = r0.getLayoutParams();
        r45 = r24;
        r45 = (android.support.v7.widget.ActionMenuView.LayoutParams) r45;
        r25 = r45;
        r0 = r19;
        r15 = r0 instanceof android.support.v7.view.menu.ActionMenuItemView;
        if (r15 == 0) goto L_0x0334;
    L_0x0319:
        r0 = r25;
        r0.extraPixels = r11;
        r10 = 1;
        r0 = r25;
        r0.expanded = r10;
        if (r12 != 0) goto L_0x0331;
    L_0x0324:
        r0 = r25;
        r15 = r0.preventEdgeOffset;
        if (r15 != 0) goto L_0x0331;
    L_0x032a:
        r13 = -r11;
        r13 = r13 / 2;
        r0 = r25;
        r0.leftMargin = r13;
    L_0x0331:
        r21 = 1;
        goto L_0x02c5;
    L_0x0334:
        r0 = r25;
        r15 = r0.isOverflowButton;
        if (r15 == 0) goto L_0x0351;
    L_0x033a:
        r0 = r25;
        r0.extraPixels = r11;
        r10 = 1;
        r0 = r25;
        r0.expanded = r10;
        r13 = -r11;
        r13 = r13 / 2;
        r0 = r25;
        r0.rightMargin = r13;
        goto L_0x034e;
    L_0x034b:
        goto L_0x025e;
    L_0x034e:
        r21 = 1;
        goto L_0x034b;
    L_0x0351:
        if (r12 == 0) goto L_0x0359;
    L_0x0353:
        r13 = r11 / 2;
        r0 = r25;
        r0.leftMargin = r13;
    L_0x0359:
        r13 = r7 + -1;
        if (r12 == r13) goto L_0x025e;
    L_0x035d:
        r13 = r11 / 2;
        goto L_0x0363;
    L_0x0360:
        goto L_0x025e;
    L_0x0363:
        r0 = r25;
        r0.rightMargin = r13;
        goto L_0x0360;
    L_0x0368:
        if (r21 == 0) goto L_0x03a1;
    L_0x036a:
        r11 = 0;
    L_0x036b:
        if (r11 >= r7) goto L_0x03a1;
    L_0x036d:
        r0 = r47;
        r19 = r0.getChildAt(r11);
        r0 = r19;
        r24 = r0.getLayoutParams();
        r46 = r24;
        r46 = (android.support.v7.widget.ActionMenuView.LayoutParams) r46;
        r25 = r46;
        r0 = r25;
        r15 = r0.expanded;
        if (r15 != 0) goto L_0x0388;
    L_0x0385:
        r11 = r11 + 1;
        goto L_0x036b;
    L_0x0388:
        r0 = r25;
        r12 = r0.cellsUsed;
        r12 = r12 * r6;
        r0 = r25;
        r13 = r0.extraPixels;
        r12 = r12 + r13;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r12 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r10);
        r0 = r19;
        r1 = r49;
        r0.measure(r12, r1);
        goto L_0x0385;
    L_0x03a1:
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r4 == r10) goto L_0x03a8;
    L_0x03a6:
        r48 = r8;
    L_0x03a8:
        r0 = r47;
        r1 = r48;
        r0.setMeasuredDimension(r5, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionMenuView.onMeasureExactFormat(int, int):void");
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return $r1 != null && ($r1 instanceof LayoutParams);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) throws  {
        return false;
    }

    public int getWindowAnimations() throws  {
        return 0;
    }

    public ActionMenuView(Context $r1) throws  {
        this($r1, null);
    }

    public ActionMenuView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        setBaselineAligned(false);
        float $f0 = $r1.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * $f0);
        this.mGeneratedItemPadding = (int) (4.0f * $f0);
        this.mPopupContext = $r1;
        this.mPopupTheme = 0;
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

    public void setPresenter(ActionMenuPresenter $r1) throws  {
        this.mPresenter = $r1;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        if (VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged($r1);
        }
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener $r1) throws  {
        this.mOnMenuItemClickListener = $r1;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        boolean $z0 = this.mFormatItems;
        this.mFormatItems = MeasureSpec.getMode($i0) == 1073741824;
        if ($z0 != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int $i2 = MeasureSpec.getSize($i0);
        if (!(!this.mFormatItems || this.mMenu == null || $i2 == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = $i2;
            this.mMenu.onItemsChanged(true);
        }
        $i2 = getChildCount();
        if (!this.mFormatItems || $i2 <= 0) {
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                LayoutParams $r4 = (LayoutParams) getChildAt($i3).getLayoutParams();
                $r4.rightMargin = 0;
                $r4.leftMargin = 0;
            }
            super.onMeasure($i0, $i1);
            return;
        }
        onMeasureExactFormat($i0, $i1);
    }

    static int measureChildForCells(View $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        LayoutParams $r2 = (LayoutParams) $r0.getLayoutParams();
        $i2 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize($i2) - $i3, MeasureSpec.getMode($i2));
        ActionMenuItemView $r3 = $r0 instanceof ActionMenuItemView ? (ActionMenuItemView) $r0 : null;
        boolean $z0 = $r3 != null && $r3.hasText();
        $i3 = 0;
        if ($i1 > 0 && (!$z0 || $i1 >= 2)) {
            $r0.measure(MeasureSpec.makeMeasureSpec($i0 * $i1, Integer.MIN_VALUE), $i2);
            $i1 = $r0.getMeasuredWidth();
            $i3 = $i1 / $i0;
            if ($i1 % $i0 != 0) {
                $i3++;
            }
            if ($z0 && $i3 < 2) {
                $i3 = 2;
            }
        }
        $z0 = !$r2.isOverflowButton && $z0;
        $r2.expandable = $z0;
        $r2.cellsUsed = $i3;
        $r0.measure(MeasureSpec.makeMeasureSpec($i3 * $i0, 1073741824), $i2);
        return $i3;
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        if (this.mFormatItems) {
            int $i9;
            View $r1;
            LayoutParams $r3;
            int $i6 = getChildCount();
            $i1 = ($i3 - $i1) / 2;
            $i3 = getDividerWidth();
            int $i7 = 0;
            int $i8 = (($i2 - $i0) - getPaddingRight()) - getPaddingLeft();
            boolean $z1 = false;
            $z0 = ViewUtils.isLayoutRtl(this);
            for ($i9 = 0; $i9 < $i6; $i9++) {
                $r1 = getChildAt($i9);
                if ($r1.getVisibility() != 8) {
                    $r3 = (LayoutParams) $r1.getLayoutParams();
                    int $i5;
                    if ($r3.isOverflowButton) {
                        int paddingLeft;
                        int $i11;
                        int $i4 = $r1.getMeasuredWidth();
                        $i5 = $i4;
                        if (hasSupportDividerBeforeChildAt($i9)) {
                            $i5 = $i4 + $i3;
                        }
                        $i4 = $r1.getMeasuredHeight();
                        if ($z0) {
                            paddingLeft = getPaddingLeft() + $r3.leftMargin;
                            $i11 = paddingLeft + $i5;
                        } else {
                            $i11 = (getWidth() - getPaddingRight()) - $r3.rightMargin;
                            paddingLeft = $i11 - $i5;
                        }
                        int $i12 = $i1 - ($i4 / 2);
                        $r1.layout(paddingLeft, $i12, $i11, $i12 + $i4);
                        $i8 -= $i5;
                        $z1 = true;
                    } else {
                        $i5 = $r1.getMeasuredWidth();
                        int $i42 = $r3.leftMargin;
                        $i5 += $i42;
                        $i42 = $r3.rightMargin;
                        $i8 -= $i5 + $i42;
                        $i7 = hasSupportDividerBeforeChildAt($i9) ? $i7 + 1 : $i7 + 1;
                    }
                }
            }
            if ($i6 != 1 || $z1) {
                int i;
                if ($z1) {
                    i = 0;
                } else {
                    i = 1;
                }
                $i0 = $i7 - i;
                if ($i0 > 0) {
                    $i0 = $i8 / $i0;
                } else {
                    $i0 = 0;
                }
                $i0 = Math.max(0, $i0);
                if ($z0) {
                    $i3 = getWidth() - getPaddingRight();
                    for ($i2 = 0; $i2 < $i6; $i2++) {
                        $r1 = getChildAt($i2);
                        $r3 = (LayoutParams) $r1.getLayoutParams();
                        if (!($r1.getVisibility() == 8 || $r3.isOverflowButton)) {
                            $i3 -= $r3.rightMargin;
                            $i7 = $r1.getMeasuredWidth();
                            $i8 = $r1.getMeasuredHeight();
                            $i9 = $i1 - ($i8 / 2);
                            $r1.layout($i3 - $i7, $i9, $i3, $i9 + $i8);
                            $i3 -= ($r3.leftMargin + $i7) + $i0;
                        }
                    }
                    return;
                }
                $i3 = getPaddingLeft();
                for ($i2 = 0; $i2 < $i6; $i2++) {
                    $r1 = getChildAt($i2);
                    $r3 = (LayoutParams) $r1.getLayoutParams();
                    if (!($r1.getVisibility() == 8 || $r3.isOverflowButton)) {
                        $i3 += $r3.leftMargin;
                        $i7 = $r1.getMeasuredWidth();
                        $i8 = $r1.getMeasuredHeight();
                        $i9 = $i1 - ($i8 / 2);
                        $r1.layout($i3, $i9, $i3 + $i7, $i9 + $i8);
                        $i3 += ($r3.rightMargin + $i7) + $i0;
                    }
                }
                return;
            }
            $r1 = getChildAt(0);
            $i6 = $r1.getMeasuredWidth();
            $i3 = $r1.getMeasuredHeight();
            $i0 = (($i2 - $i0) / 2) - ($i6 / 2);
            $i1 -= $i3 / 2;
            $r1.layout($i0, $i1, $i0 + $i6, $i1 + $i3);
            return;
        }
        super.onLayout($z0, $i0, $i1, $i2, $i3);
    }

    public void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(@Nullable Drawable $r1) throws  {
        getMenu();
        this.mPresenter.setOverflowIcon($r1);
    }

    @Nullable
    public Drawable getOverflowIcon() throws  {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public boolean isOverflowReserved() throws  {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean $z0) throws  {
        this.mReserveOverflow = $z0;
    }

    protected LayoutParams generateDefaultLayoutParams() throws  {
        LayoutParams $r1 = new LayoutParams(-2, -2);
        $r1.gravity = 16;
        return $r1;
    }

    public LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r2) throws  {
        if ($r2 == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams $r1 = $r2 instanceof LayoutParams ? new LayoutParams((LayoutParams) $r2) : new LayoutParams($r2);
        if ($r1.gravity > 0) {
            return $r1;
        }
        $r1.gravity = 16;
        return $r1;
    }

    public LayoutParams generateOverflowButtonLayoutParams() throws  {
        LayoutParams $r1 = generateDefaultLayoutParams();
        $r1.isOverflowButton = true;
        return $r1;
    }

    public boolean invokeItem(MenuItemImpl $r1) throws  {
        return this.mMenu.performItemAction($r1, 0);
    }

    public void initialize(MenuBuilder $r1) throws  {
        this.mMenu = $r1;
    }

    public Menu getMenu() throws  {
        if (this.mMenu == null) {
            Callback $r5;
            Context $r1 = getContext();
            this.mMenu = new MenuBuilder($r1);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter($r1);
            this.mPresenter.setReserveOverflow(true);
            ActionMenuPresenter $r4 = this.mPresenter;
            if (this.mActionMenuPresenterCallback != null) {
                $r5 = this.mActionMenuPresenterCallback;
            } else {
                Object $r52 = r9;
                ActionMenuPresenterCallback r9 = new ActionMenuPresenterCallback();
            }
            $r4.setCallback($r5);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public void setMenuCallbacks(Callback $r1, MenuBuilder.Callback $r2) throws  {
        this.mActionMenuPresenterCallback = $r1;
        this.mMenuBuilderCallback = $r2;
    }

    public MenuBuilder peekMenu() throws  {
        return this.mMenu;
    }

    public boolean showOverflowMenu() throws  {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() throws  {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() throws  {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() throws  {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() throws  {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    protected boolean hasSupportDividerBeforeChildAt(int $i0) throws  {
        if ($i0 == 0) {
            return false;
        }
        View $r1 = getChildAt($i0 - 1);
        View $r2 = getChildAt($i0);
        boolean $z0 = false;
        if ($i0 < getChildCount() && ($r1 instanceof ActionMenuChildView)) {
            $z0 = false | ((ActionMenuChildView) $r1).needsDividerAfter();
        }
        return ($i0 <= 0 || !($r2 instanceof ActionMenuChildView)) ? $z0 : $z0 | ((ActionMenuChildView) $r2).needsDividerBefore();
    }

    public void setExpandedActionViewsExclusive(boolean $z0) throws  {
        this.mPresenter.setExpandedActionViewsExclusive($z0);
    }
}
