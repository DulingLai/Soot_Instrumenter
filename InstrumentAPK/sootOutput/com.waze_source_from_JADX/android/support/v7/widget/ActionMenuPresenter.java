package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.v4.view.GravityCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.ActionMenuItemView.PopupCallback;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

class ActionMenuPresenter extends BaseMenuPresenter implements SubUiVisibilityListener {
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    private ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    private OverflowMenuButton mOverflowButton;
    private OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    private OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private View mScrapActionButtonView;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    private class ActionButtonSubmenu extends MenuPopupHelper {
        private SubMenuBuilder mSubMenu;

        public ActionButtonSubmenu(Context $r2, SubMenuBuilder $r3) throws  {
            super($r2, $r3, null, false, C0192R.attr.actionOverflowMenuStyle);
            this.mSubMenu = $r3;
            if (!((MenuItemImpl) $r3.getItem()).isActionButton()) {
                setAnchorView(ActionMenuPresenter.this.mOverflowButton == null ? (View) ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mOverflowButton);
            }
            setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
            boolean $z0 = false;
            int $i0 = $r3.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                MenuItem $r4 = $r3.getItem($i1);
                if ($r4.isVisible() && $r4.getIcon() != null) {
                    $z0 = true;
                    break;
                }
            }
            setForceShowIcon($z0);
        }

        public void onDismiss() throws  {
            super.onDismiss();
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
        }
    }

    private class ActionMenuPopupCallback extends PopupCallback {
        private ActionMenuPopupCallback() throws  {
        }

        public ListPopupWindow getPopup() throws  {
            return ActionMenuPresenter.this.mActionButtonPopup != null ? ActionMenuPresenter.this.mActionButtonPopup.getPopup() : null;
        }
    }

    private class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup $r2) throws  {
            this.mPopup = $r2;
        }

        public void run() throws  {
            ActionMenuPresenter.this.mMenu.changeMenuMode();
            View $r4 = (View) ActionMenuPresenter.this.mMenuView;
            if (!($r4 == null || $r4.getWindowToken() == null || !this.mPopup.tryShow())) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class OverflowMenuButton extends AppCompatImageView implements ActionMenuChildView {
        private final float[] mTempPts = new float[2];

        public boolean needsDividerAfter() throws  {
            return false;
        }

        public boolean needsDividerBefore() throws  {
            return false;
        }

        public OverflowMenuButton(Context $r2) throws  {
            super($r2, null, C0192R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            setOnTouchListener(new ForwardingListener(this, ActionMenuPresenter.this) {
                public ListPopupWindow getPopup() throws  {
                    return ActionMenuPresenter.this.mOverflowPopup == null ? null : ActionMenuPresenter.this.mOverflowPopup.getPopup();
                }

                public boolean onForwardingStarted() throws  {
                    ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                public boolean onForwardingStopped() throws  {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        public boolean performClick() throws  {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        protected boolean setFrame(int $i0, int $i1, int $i2, int $i3) throws  {
            boolean $z0 = super.setFrame($i0, $i1, $i2, $i3);
            Drawable $r1 = getDrawable();
            Drawable $r2 = getBackground();
            if ($r1 == null || $r2 == null) {
                return $z0;
            }
            $i2 = getWidth();
            $i1 = getHeight();
            $i0 = Math.max($i2, $i1) / 2;
            $i2 = ($i2 + (getPaddingLeft() - getPaddingRight())) / 2;
            int $i4 = ($i1 + (getPaddingTop() - getPaddingBottom())) / 2;
            DrawableCompat.setHotspotBounds($r2, $i2 - $i0, $i4 - $i0, $i2 + $i0, $i4 + $i0);
            return $z0;
        }
    }

    private class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context $r2, MenuBuilder $r3, View $r4, boolean $z0) throws  {
            super($r2, $r3, $r4, $z0, C0192R.attr.actionOverflowMenuStyle);
            setGravity(GravityCompat.END);
            setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        public void onDismiss() throws  {
            super.onDismiss();
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
        }
    }

    private class PopupPresenterCallback implements Callback {
        private PopupPresenterCallback() throws  {
        }

        public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
            if ($r1 == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) $r1).getItem().getItemId();
            Callback $r5 = ActionMenuPresenter.this.getCallback();
            return $r5 != null ? $r5.onOpenSubMenu($r1) : false;
        }

        public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
            if ($r1 instanceof SubMenuBuilder) {
                ((SubMenuBuilder) $r1).getRootMenu().close(false);
            }
            Callback $r5 = ActionMenuPresenter.this.getCallback();
            if ($r5 != null) {
                $r5.onCloseMenu($r1, $z0);
            }
        }
    }

    private static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C02111();
        public int openSubMenuId;

        static class C02111 implements Creator<SavedState> {
            C02111() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        SavedState() throws  {
        }

        SavedState(Parcel $r1) throws  {
            this.openSubMenuId = $r1.readInt();
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            $r1.writeInt(this.openSubMenuId);
        }
    }

    public boolean flagActionItems() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:97:0x020f in {5, 10, 11, 14, 15, 20, 21, 24, 31, 34, 37, 40, 41, 42, 43, 48, 52, 53, 57, 58, 63, 66, 70, 71, 74, 76, 78, 79, 80, 83, 84, 85, 94, 95, 96, 98, 101} preds:[]
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
        r33 = this;
        r0 = r33;
        r3 = r0.mMenu;
        r4 = r3.getVisibleItems();
        r5 = r4.size();
        r0 = r33;
        r6 = r0.mMaxItems;
        r0 = r33;
        r7 = r0.mActionItemWidthLimit;
        r8 = r7;
        r10 = 0;
        r11 = 0;
        r9 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r11);
        r0 = r33;
        r12 = r0.mMenuView;
        r14 = r12;
        r14 = (android.view.ViewGroup) r14;
        r13 = r14;
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
    L_0x002c:
        r0 = r19;
        if (r0 >= r5) goto L_0x0068;
    L_0x0030:
        r0 = r19;
        r20 = r4.get(r0);
        r22 = r20;
        r22 = (android.support.v7.view.menu.MenuItemImpl) r22;
        r21 = r22;
        r0 = r21;
        r23 = r0.requiresActionButton();
        if (r23 == 0) goto L_0x005a;
    L_0x0044:
        r15 = r15 + 1;
    L_0x0046:
        r0 = r33;
        r0 = r0.mExpandedActionViewsExclusive;
        r23 = r0;
        if (r23 == 0) goto L_0x0057;
    L_0x004e:
        r0 = r21;
        r23 = r0.isActionViewExpanded();
        if (r23 == 0) goto L_0x0057;
    L_0x0056:
        r6 = 0;
    L_0x0057:
        r19 = r19 + 1;
        goto L_0x002c;
    L_0x005a:
        r0 = r21;
        r23 = r0.requestsActionButton();
        if (r23 == 0) goto L_0x0065;
    L_0x0062:
        r16 = r16 + 1;
        goto L_0x0046;
    L_0x0065:
        r18 = 1;
        goto L_0x0046;
    L_0x0068:
        r0 = r33;
        r0 = r0.mReserveOverflow;
        r23 = r0;
        if (r23 == 0) goto L_0x007a;
    L_0x0070:
        if (r18 != 0) goto L_0x0078;
    L_0x0072:
        r16 = r15 + r16;
        r0 = r16;
        if (r0 <= r6) goto L_0x007a;
    L_0x0078:
        r6 = r6 + -1;
    L_0x007a:
        r6 = r6 - r15;
        r0 = r33;
        r0 = r0.mActionButtonGroups;
        r24 = r0;
        r0.clear();
        r16 = 0;
        r15 = 0;
        r0 = r33;
        r0 = r0.mStrictWidthLimit;
        r18 = r0;
        if (r18 == 0) goto L_0x00a8;
    L_0x008f:
        r0 = r33;
        r15 = r0.mMinCellSize;
        r15 = r7 / r15;
        r0 = r33;
        r0 = r0.mMinCellSize;
        r16 = r0;
        r7 = r7 % r0;
        r0 = r33;
        r0 = r0.mMinCellSize;
        r16 = r0;
        r7 = r7 / r15;
        r0 = r16;
        r0 = r0 + r7;
        r16 = r0;
    L_0x00a8:
        r7 = 0;
    L_0x00a9:
        if (r7 >= r5) goto L_0x021a;
    L_0x00ab:
        r20 = r4.get(r7);
        r25 = r20;
        r25 = (android.support.v7.view.menu.MenuItemImpl) r25;
        r21 = r25;
        r0 = r21;
        r18 = r0.requiresActionButton();
        if (r18 == 0) goto L_0x011b;
    L_0x00bd:
        r0 = r33;
        r0 = r0.mScrapActionButtonView;
        r26 = r0;
        r0 = r33;
        r1 = r21;
        r2 = r26;
        r26 = r0.getItemView(r1, r2, r13);
        r0 = r33;
        r0 = r0.mScrapActionButtonView;
        r27 = r0;
        if (r27 != 0) goto L_0x00db;
    L_0x00d5:
        r0 = r26;
        r1 = r33;
        r1.mScrapActionButtonView = r0;
    L_0x00db:
        r0 = r33;
        r0 = r0.mStrictWidthLimit;
        r18 = r0;
        if (r18 == 0) goto L_0x0115;
    L_0x00e3:
        r10 = 0;
        r0 = r26;
        r1 = r16;
        r19 = android.support.v7.widget.ActionMenuView.measureChildForCells(r0, r1, r15, r9, r10);
        r0 = r19;
        r15 = r15 - r0;
    L_0x00ef:
        r0 = r26;
        r19 = r0.getMeasuredWidth();
        r0 = r19;
        r8 = r8 - r0;
        if (r17 != 0) goto L_0x00fc;
    L_0x00fa:
        r17 = r19;
    L_0x00fc:
        r0 = r21;
        r19 = r0.getGroupId();
        if (r19 == 0) goto L_0x010c;
    L_0x0104:
        r10 = 1;
        r0 = r24;
        r1 = r19;
        r0.put(r1, r10);
    L_0x010c:
        r10 = 1;
        r0 = r21;
        r0.setIsActionButton(r10);
    L_0x0112:
        r7 = r7 + 1;
        goto L_0x00a9;
    L_0x0115:
        r0 = r26;
        r0.measure(r9, r9);
        goto L_0x00ef;
    L_0x011b:
        r0 = r21;
        r18 = r0.requestsActionButton();
        if (r18 == 0) goto L_0x0213;
    L_0x0123:
        r0 = r21;
        r19 = r0.getGroupId();
        r0 = r24;
        r1 = r19;
        r18 = r0.get(r1);
        if (r6 > 0) goto L_0x0135;
    L_0x0133:
        if (r18 == 0) goto L_0x01b7;
    L_0x0135:
        if (r8 <= 0) goto L_0x01b7;
    L_0x0137:
        r0 = r33;
        r0 = r0.mStrictWidthLimit;
        r23 = r0;
        if (r23 == 0) goto L_0x0141;
    L_0x013f:
        if (r15 <= 0) goto L_0x01b7;
    L_0x0141:
        r23 = 1;
    L_0x0143:
        if (r23 == 0) goto L_0x019f;
    L_0x0145:
        r0 = r33;
        r0 = r0.mScrapActionButtonView;
        r26 = r0;
        r0 = r33;
        r1 = r21;
        r2 = r26;
        r26 = r0.getItemView(r1, r2, r13);
        r0 = r33;
        r0 = r0.mScrapActionButtonView;
        r27 = r0;
        if (r27 != 0) goto L_0x0167;
    L_0x015d:
        r0 = r26;
        r1 = r33;
        r1.mScrapActionButtonView = r0;
        goto L_0x0167;
    L_0x0164:
        goto L_0x0112;
    L_0x0167:
        r0 = r33;
        r0 = r0.mStrictWidthLimit;
        r28 = r0;
        if (r28 == 0) goto L_0x01ba;
    L_0x016f:
        r10 = 0;
        r0 = r26;
        r1 = r16;
        r29 = android.support.v7.widget.ActionMenuView.measureChildForCells(r0, r1, r15, r9, r10);
        r0 = r29;
        r15 = r15 - r0;
        if (r29 != 0) goto L_0x017f;
    L_0x017d:
        r23 = 0;
    L_0x017f:
        r0 = r26;
        r29 = r0.getMeasuredWidth();
        r0 = r29;
        r8 = r8 - r0;
        if (r17 != 0) goto L_0x018c;
    L_0x018a:
        r17 = r29;
    L_0x018c:
        r0 = r33;
        r0 = r0.mStrictWidthLimit;
        r28 = r0;
        if (r28 == 0) goto L_0x01c3;
    L_0x0194:
        if (r8 < 0) goto L_0x01c0;
    L_0x0196:
        r28 = 1;
    L_0x0198:
        r0 = r23;
        r1 = r28;
        r0 = r0 & r1;
        r23 = r0;
    L_0x019f:
        if (r23 == 0) goto L_0x01d4;
    L_0x01a1:
        if (r19 == 0) goto L_0x01d4;
    L_0x01a3:
        r10 = 1;
        r0 = r24;
        r1 = r19;
        r0.put(r1, r10);
    L_0x01ab:
        if (r23 == 0) goto L_0x01af;
    L_0x01ad:
        r6 = r6 + -1;
    L_0x01af:
        r0 = r21;
        r1 = r23;
        r0.setIsActionButton(r1);
        goto L_0x0164;
    L_0x01b7:
        r23 = 0;
        goto L_0x0143;
    L_0x01ba:
        r0 = r26;
        r0.measure(r9, r9);
        goto L_0x017f;
    L_0x01c0:
        r28 = 0;
        goto L_0x0198;
    L_0x01c3:
        r29 = r8 + r17;
        if (r29 <= 0) goto L_0x01d1;
    L_0x01c7:
        r28 = 1;
    L_0x01c9:
        r0 = r23;
        r1 = r28;
        r0 = r0 & r1;
        r23 = r0;
        goto L_0x019f;
    L_0x01d1:
        r28 = 0;
        goto L_0x01c9;
    L_0x01d4:
        if (r18 == 0) goto L_0x01ab;
    L_0x01d6:
        r10 = 0;
        r0 = r24;
        r1 = r19;
        r0.put(r1, r10);
        r29 = 0;
    L_0x01e0:
        r0 = r29;
        if (r0 >= r7) goto L_0x01ab;
    L_0x01e4:
        r0 = r29;
        r20 = r4.get(r0);
        r31 = r20;
        r31 = (android.support.v7.view.menu.MenuItemImpl) r31;
        r30 = r31;
        r0 = r30;
        r32 = r0.getGroupId();
        r0 = r32;
        r1 = r19;
        if (r0 != r1) goto L_0x020c;
    L_0x01fc:
        r0 = r30;
        r18 = r0.isActionButton();
        if (r18 == 0) goto L_0x0206;
    L_0x0204:
        r6 = r6 + 1;
    L_0x0206:
        r10 = 0;
        r0 = r30;
        r0.setIsActionButton(r10);
    L_0x020c:
        r29 = r29 + 1;
        goto L_0x01e0;
        goto L_0x0213;
    L_0x0210:
        goto L_0x0112;
    L_0x0213:
        r10 = 0;
        r0 = r21;
        r0.setIsActionButton(r10);
        goto L_0x0210;
    L_0x021a:
        r10 = 1;
        return r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionMenuPresenter.flagActionItems():boolean");
    }

    public void updateMenuView(boolean r30) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:33:0x00d1 in {2, 9, 10, 13, 21, 25, 27, 32, 34, 35, 36, 38, 42, 44, 49} preds:[]
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
        r29 = this;
        r0 = r29;
        r3 = r0.mMenuView;
        r5 = r3;
        r5 = (android.view.View) r5;
        r4 = r5;
        r6 = r4.getParent();
        r8 = r6;
        r8 = (android.view.ViewGroup) r8;
        r7 = r8;
        if (r7 == 0) goto L_0x0015;
    L_0x0012:
        android.support.v7.transition.ActionBarTransition.beginDelayedTransition(r7);
    L_0x0015:
        r0 = r29;
        r1 = r30;
        super.updateMenuView(r1);
        r0 = r29;
        r3 = r0.mMenuView;
        r9 = r3;
        r9 = (android.view.View) r9;
        r4 = r9;
        r4.requestLayout();
        r0 = r29;
        r10 = r0.mMenu;
        if (r10 == 0) goto L_0x0056;
    L_0x002d:
        r0 = r29;
        r10 = r0.mMenu;
        r11 = r10.getActionItems();
        r12 = r11.size();
        r13 = 0;
    L_0x003a:
        if (r13 >= r12) goto L_0x0056;
    L_0x003c:
        r14 = r11.get(r13);
        r16 = r14;
        r16 = (android.support.v7.view.menu.MenuItemImpl) r16;
        r15 = r16;
        r17 = r15.getSupportActionProvider();
        if (r17 == 0) goto L_0x0053;
    L_0x004c:
        r0 = r17;
        r1 = r29;
        r0.setSubUiVisibilityListener(r1);
    L_0x0053:
        r13 = r13 + 1;
        goto L_0x003a;
    L_0x0056:
        r0 = r29;
        r10 = r0.mMenu;
        if (r10 == 0) goto L_0x0115;
    L_0x005c:
        r0 = r29;
        r10 = r0.mMenu;
        r11 = r10.getNonActionItems();
    L_0x0064:
        r30 = 0;
        r0 = r29;
        r0 = r0.mReserveOverflow;
        r18 = r0;
        if (r18 == 0) goto L_0x0090;
    L_0x006e:
        if (r11 == 0) goto L_0x0090;
    L_0x0070:
        r12 = r11.size();
        r19 = 1;
        r0 = r19;
        if (r12 != r0) goto L_0x011a;
    L_0x007a:
        r19 = 0;
        r0 = r19;
        r14 = r11.get(r0);
        r20 = r14;
        r20 = (android.support.v7.view.menu.MenuItemImpl) r20;
        r15 = r20;
        r30 = r15.isActionViewExpanded();
        if (r30 != 0) goto L_0x0117;
    L_0x008e:
        r30 = 1;
    L_0x0090:
        if (r30 == 0) goto L_0x0122;
    L_0x0092:
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        if (r21 != 0) goto L_0x00b1;
    L_0x009a:
        r21 = new android.support.v7.widget.ActionMenuPresenter$OverflowMenuButton;
        r0 = r29;
        r0 = r0.mSystemContext;
        r22 = r0;
        r0 = r21;
        r1 = r29;
        r2 = r22;
        r0.<init>(r2);
        r0 = r21;
        r1 = r29;
        r1.mOverflowButton = r0;
    L_0x00b1:
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        goto L_0x00bb;
    L_0x00b8:
        goto L_0x0064;
    L_0x00bb:
        r6 = r0.getParent();
        r23 = r6;
        r23 = (android.view.ViewGroup) r23;
        r7 = r23;
        r0 = r29;
        r3 = r0.mMenuView;
        if (r7 == r3) goto L_0x00fd;
    L_0x00cb:
        if (r7 == 0) goto L_0x00de;
    L_0x00cd:
        goto L_0x00d5;
    L_0x00ce:
        goto L_0x0090;
        goto L_0x00d5;
    L_0x00d2:
        goto L_0x0090;
    L_0x00d5:
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        r7.removeView(r0);
    L_0x00de:
        r0 = r29;
        r3 = r0.mMenuView;
        r25 = r3;
        r25 = (android.support.v7.widget.ActionMenuView) r25;
        r24 = r25;
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        r0 = r24;
        r26 = r0.generateOverflowButtonLayoutParams();
        r0 = r24;
        r1 = r21;
        r2 = r26;
        r0.addView(r1, r2);
    L_0x00fd:
        r0 = r29;
        r3 = r0.mMenuView;
        r27 = r3;
        r27 = (android.support.v7.widget.ActionMenuView) r27;
        r24 = r27;
        r0 = r29;
        r0 = r0.mReserveOverflow;
        r30 = r0;
        r0 = r24;
        r1 = r30;
        r0.setOverflowReserved(r1);
        return;
    L_0x0115:
        r11 = 0;
        goto L_0x00b8;
    L_0x0117:
        r30 = 0;
        goto L_0x00ce;
    L_0x011a:
        if (r12 <= 0) goto L_0x011f;
    L_0x011c:
        r30 = 1;
    L_0x011e:
        goto L_0x00d2;
    L_0x011f:
        r30 = 0;
        goto L_0x011e;
    L_0x0122:
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        if (r21 == 0) goto L_0x00fd;
    L_0x012a:
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        r6 = r0.getParent();
        r0 = r29;
        r3 = r0.mMenuView;
        if (r6 != r3) goto L_0x00fd;
    L_0x013a:
        r0 = r29;
        r3 = r0.mMenuView;
        r28 = r3;
        r28 = (android.view.ViewGroup) r28;
        r7 = r28;
        r0 = r29;
        r0 = r0.mOverflowButton;
        r21 = r0;
        r7.removeView(r0);
        goto L_0x00fd;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionMenuPresenter.updateMenuView(boolean):void");
    }

    public ActionMenuPresenter(Context $r1) throws  {
        super($r1, C0192R.layout.abc_action_menu_layout, C0192R.layout.abc_action_menu_item_layout);
    }

    public void initForMenu(Context $r1, MenuBuilder $r2) throws  {
        super.initForMenu($r1, $r2);
        Resources $r3 = $r1.getResources();
        ActionBarPolicy $r4 = ActionBarPolicy.get($r1);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = $r4.showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = $r4.getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = $r4.getMaxActionButtons();
        }
        int $i0 = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int $i1 = MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure($i1, $i1);
            }
            $i0 -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = $i0;
        this.mMinCellSize = (int) (56.0f * $r3.getDisplayMetrics().density);
        this.mScrapActionButtonView = null;
    }

    public void onConfigurationChanged(Configuration newConfig) throws  {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = this.mContext.getResources().getInteger(C0192R.integer.abc_max_action_buttons);
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void setWidthLimit(int $i0, boolean $z0) throws  {
        this.mWidthLimit = $i0;
        this.mStrictWidthLimit = $z0;
        this.mWidthLimitSet = true;
    }

    public void setReserveOverflow(boolean $z0) throws  {
        this.mReserveOverflow = $z0;
        this.mReserveOverflowSet = true;
    }

    public void setItemLimit(int $i0) throws  {
        this.mMaxItems = $i0;
        this.mMaxItemsSet = true;
    }

    public void setExpandedActionViewsExclusive(boolean $z0) throws  {
        this.mExpandedActionViewsExclusive = $z0;
    }

    public void setOverflowIcon(Drawable $r1) throws  {
        if (this.mOverflowButton != null) {
            this.mOverflowButton.setImageDrawable($r1);
            return;
        }
        this.mPendingOverflowIconSet = true;
        this.mPendingOverflowIcon = $r1;
    }

    public Drawable getOverflowIcon() throws  {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable();
        }
        return this.mPendingOverflowIconSet ? this.mPendingOverflowIcon : null;
    }

    public MenuView getMenuView(ViewGroup $r1) throws  {
        MenuView $r2 = super.getMenuView($r1);
        ((ActionMenuView) $r2).setPresenter(this);
        return $r2;
    }

    public View getItemView(MenuItemImpl $r1, View $r2, ViewGroup $r3) throws  {
        View $r4 = $r1.getActionView();
        View $r5 = $r4;
        if ($r4 == null || $r1.hasCollapsibleActionView()) {
            $r5 = super.getItemView($r1, $r2, $r3);
        }
        $r5.setVisibility($r1.isActionViewExpanded() ? (byte) 8 : (byte) 0);
        ActionMenuView $r6 = (ActionMenuView) $r3;
        LayoutParams $r7 = $r5.getLayoutParams();
        if ($r6.checkLayoutParams($r7)) {
            return $r5;
        }
        $r5.setLayoutParams($r6.generateLayoutParams($r7));
        return $r5;
    }

    public void bindItemView(MenuItemImpl $r1, ItemView $r2) throws  {
        $r2.initialize($r1, 0);
        ActionMenuItemView $r5 = (ActionMenuItemView) $r2;
        $r5.setItemInvoker((ActionMenuView) this.mMenuView);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        $r5.setPopupCallback(this.mPopupCallback);
    }

    public boolean shouldIncludeItem(int childIndex, MenuItemImpl $r1) throws  {
        return $r1.isActionButton();
    }

    public boolean filterLeftoverView(ViewGroup $r1, int $i0) throws  {
        return $r1.getChildAt($i0) == this.mOverflowButton ? false : super.filterLeftoverView($r1, $i0);
    }

    public boolean onSubMenuSelected(SubMenuBuilder $r1) throws  {
        if (!$r1.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder $r3 = $r1;
        while ($r3.getParentMenu() != this.mMenu) {
            $r3 = (SubMenuBuilder) $r3.getParentMenu();
        }
        View $r6 = findViewForItem($r3.getItem());
        View $r7 = $r6;
        if ($r6 == null) {
            if (this.mOverflowButton == null) {
                return false;
            }
            $r7 = this.mOverflowButton;
        }
        this.mOpenSubMenuId = $r1.getItem().getItemId();
        this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, $r1);
        this.mActionButtonPopup.setAnchorView($r7);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected($r1);
        return true;
    }

    private View findViewForItem(MenuItem $r1) throws  {
        ViewGroup $r3 = (ViewGroup) this.mMenuView;
        if ($r3 == null) {
            return null;
        }
        int $i0 = $r3.getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r4 = $r3.getChildAt($i1);
            if (($r4 instanceof ItemView) && ((ItemView) $r4).getItemData() == $r1) {
                return $r4;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() throws  {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
        ((View) this.mMenuView).post(this.mPostedOpenRunnable);
        super.onSubMenuSelected(null);
        return true;
    }

    public boolean hideOverflowMenu() throws  {
        if (this.mPostedOpenRunnable == null || this.mMenuView == null) {
            OverflowPopup $r1 = this.mOverflowPopup;
            if ($r1 == null) {
                return false;
            }
            $r1.dismiss();
            return true;
        }
        ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
        this.mPostedOpenRunnable = null;
        return true;
    }

    public boolean dismissPopupMenus() throws  {
        return hideOverflowMenu() | hideSubMenus();
    }

    public boolean hideSubMenus() throws  {
        if (this.mActionButtonPopup == null) {
            return false;
        }
        this.mActionButtonPopup.dismiss();
        return true;
    }

    public boolean isOverflowMenuShowing() throws  {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
    }

    public boolean isOverflowMenuShowPending() throws  {
        return this.mPostedOpenRunnable != null || isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() throws  {
        return this.mReserveOverflow;
    }

    public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
        dismissPopupMenus();
        super.onCloseMenu($r1, $z0);
    }

    public Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState();
        $r1.openSubMenuId = this.mOpenSubMenuId;
        return $r1;
    }

    public void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            if ($r2.openSubMenuId > 0) {
                MenuItem $r4 = this.mMenu.findItem($r2.openSubMenuId);
                if ($r4 != null) {
                    onSubMenuSelected((SubMenuBuilder) $r4.getSubMenu());
                }
            }
        }
    }

    public void onSubUiVisibilityChanged(boolean $z0) throws  {
        if ($z0) {
            super.onSubMenuSelected(null);
        } else {
            this.mMenu.close(false);
        }
    }

    public void setMenuView(ActionMenuView $r1) throws  {
        this.mMenuView = $r1;
        $r1.initialize(this.mMenu);
    }
}
