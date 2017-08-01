package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuBuilder.Callback;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.ContentFrameLayout.OnAttachListener;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.FitWindowsViewGroup;
import android.support.v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

class AppCompatDelegateImplV7 extends AppCompatDelegateImplBase implements LayoutInflaterFactory, Callback {
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private AppCompatViewInflater mAppCompatViewInflater;
    private boolean mClosingActionMenu;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim = null;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private int mInvalidatePanelMenuFeatures;
    private boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new C01761();
    private boolean mLongPressBackDown;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private TextView mTitleView;

    class C01761 implements Runnable {
        C01761() throws  {
        }

        public void run() throws  {
            if ((AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures & 1) != 0) {
                AppCompatDelegateImplV7.this.doInvalidatePanelMenu(0);
            }
            if ((AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures & 4096) != 0) {
                AppCompatDelegateImplV7.this.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImplV7.this.mInvalidatePanelMenuPosted = false;
            AppCompatDelegateImplV7.this.mInvalidatePanelMenuFeatures = 0;
        }
    }

    class C01772 implements OnApplyWindowInsetsListener {
        C01772() throws  {
        }

        public WindowInsetsCompat onApplyWindowInsets(View $r1, WindowInsetsCompat $r2) throws  {
            int $i0 = $r2.getSystemWindowInsetTop();
            int $i1 = AppCompatDelegateImplV7.this.updateStatusGuard($i0);
            if ($i0 != $i1) {
                $r2 = $r2.replaceSystemWindowInsets($r2.getSystemWindowInsetLeft(), $i1, $r2.getSystemWindowInsetRight(), $r2.getSystemWindowInsetBottom());
            }
            return ViewCompat.onApplyWindowInsets($r1, $r2);
        }
    }

    class C01783 implements OnFitSystemWindowsListener {
        C01783() throws  {
        }

        public void onFitSystemWindows(Rect $r1) throws  {
            $r1.top = AppCompatDelegateImplV7.this.updateStatusGuard($r1.top);
        }
    }

    class C01794 implements OnAttachListener {
        C01794() throws  {
        }

        public void onAttachedFromWindow() throws  {
        }

        public void onDetachedFromWindow() throws  {
            AppCompatDelegateImplV7.this.dismissPopups();
        }
    }

    class C01815 implements Runnable {

        class C01801 extends ViewPropertyAnimatorListenerAdapter {
            C01801() throws  {
            }

            public void onAnimationEnd(View view) throws  {
                ViewCompat.setAlpha(AppCompatDelegateImplV7.this.mActionModeView, 1.0f);
                AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
                AppCompatDelegateImplV7.this.mFadeAnim = null;
            }

            public void onAnimationStart(View view) throws  {
                AppCompatDelegateImplV7.this.mActionModeView.setVisibility(0);
            }
        }

        C01815() throws  {
        }

        public void run() throws  {
            AppCompatDelegateImplV7.this.mActionModePopup.showAtLocation(AppCompatDelegateImplV7.this.mActionModeView, 55, 0, 0);
            AppCompatDelegateImplV7.this.endOnGoingFadeAnimation();
            ViewCompat.setAlpha(AppCompatDelegateImplV7.this.mActionModeView, 0.0f);
            AppCompatDelegateImplV7.this.mFadeAnim = ViewCompat.animate(AppCompatDelegateImplV7.this.mActionModeView).alpha(1.0f);
            AppCompatDelegateImplV7.this.mFadeAnim.setListener(new C01801());
        }
    }

    class C01826 extends ViewPropertyAnimatorListenerAdapter {
        C01826() throws  {
        }

        public void onAnimationEnd(View view) throws  {
            ViewCompat.setAlpha(AppCompatDelegateImplV7.this.mActionModeView, 1.0f);
            AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
            AppCompatDelegateImplV7.this.mFadeAnim = null;
        }

        public void onAnimationStart(View view) throws  {
            AppCompatDelegateImplV7.this.mActionModeView.setVisibility(0);
            AppCompatDelegateImplV7.this.mActionModeView.sendAccessibilityEvent(32);
            if (AppCompatDelegateImplV7.this.mActionModeView.getParent() != null) {
                ViewCompat.requestApplyInsets((View) AppCompatDelegateImplV7.this.mActionModeView.getParent());
            }
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() throws  {
        }

        public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
            Window.Callback $r2 = AppCompatDelegateImplV7.this.getWindowCallback();
            if ($r2 != null) {
                $r2.onMenuOpened(108, $r1);
            }
            return true;
        }

        public void onCloseMenu(MenuBuilder $r1, boolean allMenusAreClosing) throws  {
            AppCompatDelegateImplV7.this.checkCloseActionMenu($r1);
        }
    }

    class ActionModeCallbackWrapperV7 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        class C01831 extends ViewPropertyAnimatorListenerAdapter {
            C01831() throws  {
            }

            public void onAnimationEnd(View view) throws  {
                AppCompatDelegateImplV7.this.mActionModeView.setVisibility(8);
                if (AppCompatDelegateImplV7.this.mActionModePopup != null) {
                    AppCompatDelegateImplV7.this.mActionModePopup.dismiss();
                } else if (AppCompatDelegateImplV7.this.mActionModeView.getParent() instanceof View) {
                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImplV7.this.mActionModeView.getParent());
                }
                AppCompatDelegateImplV7.this.mActionModeView.removeAllViews();
                AppCompatDelegateImplV7.this.mFadeAnim.setListener(null);
                AppCompatDelegateImplV7.this.mFadeAnim = null;
            }
        }

        public ActionModeCallbackWrapperV7(ActionMode.Callback $r2) throws  {
            this.mWrapped = $r2;
        }

        public boolean onCreateActionMode(ActionMode $r1, Menu $r2) throws  {
            return this.mWrapped.onCreateActionMode($r1, $r2);
        }

        public boolean onPrepareActionMode(ActionMode $r1, Menu $r2) throws  {
            return this.mWrapped.onPrepareActionMode($r1, $r2);
        }

        public boolean onActionItemClicked(ActionMode $r1, MenuItem $r2) throws  {
            return this.mWrapped.onActionItemClicked($r1, $r2);
        }

        public void onDestroyActionMode(ActionMode $r1) throws  {
            this.mWrapped.onDestroyActionMode($r1);
            if (AppCompatDelegateImplV7.this.mActionModePopup != null) {
                AppCompatDelegateImplV7.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV7.this.mShowActionModePopup);
            }
            if (AppCompatDelegateImplV7.this.mActionModeView != null) {
                AppCompatDelegateImplV7.this.endOnGoingFadeAnimation();
                AppCompatDelegateImplV7.this.mFadeAnim = ViewCompat.animate(AppCompatDelegateImplV7.this.mActionModeView).alpha(0.0f);
                AppCompatDelegateImplV7.this.mFadeAnim.setListener(new C01831());
            }
            if (AppCompatDelegateImplV7.this.mAppCompatCallback != null) {
                AppCompatDelegateImplV7.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImplV7.this.mActionMode);
            }
            AppCompatDelegateImplV7.this.mActionMode = null;
        }
    }

    private class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context $r2) throws  {
            super($r2);
        }

        public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
            return AppCompatDelegateImplV7.this.dispatchKeyEvent($r1) || super.dispatchKeyEvent($r1);
        }

        public boolean onInterceptTouchEvent(MotionEvent $r1) throws  {
            if ($r1.getAction() != 0 || !isOutOfBounds((int) $r1.getX(), (int) $r1.getY())) {
                return super.onInterceptTouchEvent($r1);
            }
            AppCompatDelegateImplV7.this.closePanel(0);
            return true;
        }

        public void setBackgroundResource(int $i0) throws  {
            setBackgroundDrawable(AppCompatDrawableManager.get().getDrawable(getContext(), $i0));
        }

        private boolean isOutOfBounds(int $i0, int $i1) throws  {
            return $i0 < -5 || $i1 < -5 || $i0 > getWidth() + 5 || $i1 > getHeight() + 5;
        }
    }

    private static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;
        int f6x;
        int f7y;

        private static class SavedState implements Parcelable {
            public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new C01841());
            int featureId;
            boolean isOpen;
            Bundle menuState;

            static class C01841 implements ParcelableCompatCreatorCallbacks<SavedState> {
                C01841() throws  {
                }

                public SavedState createFromParcel(Parcel $r1, ClassLoader $r2) throws  {
                    return SavedState.readFromParcel($r1, $r2);
                }

                public SavedState[] newArray(int $i0) throws  {
                    return new SavedState[$i0];
                }
            }

            public int describeContents() throws  {
                return 0;
            }

            private SavedState() throws  {
            }

            public void writeToParcel(Parcel $r1, int flags) throws  {
                $r1.writeInt(this.featureId);
                $r1.writeInt(this.isOpen ? (byte) 1 : (byte) 0);
                if (this.isOpen) {
                    $r1.writeBundle(this.menuState);
                }
            }

            private static SavedState readFromParcel(Parcel $r0, ClassLoader $r1) throws  {
                boolean $z0 = true;
                SavedState $r2 = new SavedState();
                $r2.featureId = $r0.readInt();
                if ($r0.readInt() != 1) {
                    $z0 = false;
                }
                $r2.isOpen = $z0;
                if (!$r2.isOpen) {
                    return $r2;
                }
                $r2.menuState = $r0.readBundle($r1);
                return $r2;
            }
        }

        PanelFeatureState(int $i0) throws  {
            this.featureId = $i0;
        }

        public boolean hasPanelItems() throws  {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView == null) {
                return this.listMenuPresenter.getAdapter().getCount() > 0;
            } else {
                return true;
            }
        }

        public void clearMenuPresenters() throws  {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        void setStyle(Context $r1) throws  {
            TypedValue $r3 = new TypedValue();
            Theme $r5 = $r1.getResources().newTheme();
            $r5.setTo($r1.getTheme());
            $r5.resolveAttribute(C0192R.attr.actionBarPopupTheme, $r3, true);
            if ($r3.resourceId != 0) {
                $r5.applyStyle($r3.resourceId, true);
            }
            $r5.resolveAttribute(C0192R.attr.panelMenuListTheme, $r3, true);
            if ($r3.resourceId != 0) {
                $r5.applyStyle($r3.resourceId, true);
            } else {
                $r5.applyStyle(C0192R.style.Theme_AppCompat_CompactMenu, true);
            }
            ContextThemeWrapper $r2 = new ContextThemeWrapper($r1, 0);
            $r2.getTheme().setTo($r5);
            this.listPresenterContext = $r2;
            TypedArray $r8 = $r2.obtainStyledAttributes(C0192R.styleable.AppCompatTheme);
            this.background = $r8.getResourceId(C0192R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = $r8.getResourceId(C0192R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            $r8.recycle();
        }

        void setMenu(MenuBuilder $r1) throws  {
            if ($r1 != this.menu) {
                if (this.menu != null) {
                    this.menu.removeMenuPresenter(this.listMenuPresenter);
                }
                this.menu = $r1;
                if ($r1 != null && this.listMenuPresenter != null) {
                    $r1.addMenuPresenter(this.listMenuPresenter);
                }
            }
        }

        MenuView getListMenuView(MenuPresenter.Callback $r1) throws  {
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, C0192R.layout.abc_list_menu_item_layout);
                this.listMenuPresenter.setCallback($r1);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        Parcelable onSaveInstanceState() throws  {
            SavedState $r1 = new SavedState();
            $r1.featureId = this.featureId;
            $r1.isOpen = this.isOpen;
            if (this.menu == null) {
                return $r1;
            }
            $r1.menuState = new Bundle();
            this.menu.savePresenterStates($r1.menuState);
            return $r1;
        }

        void onRestoreInstanceState(Parcelable $r1) throws  {
            SavedState $r2 = (SavedState) $r1;
            this.featureId = $r2.featureId;
            this.wasLastOpen = $r2.isOpen;
            this.frozenMenuState = $r2.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        void applyFrozenState() throws  {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }
    }

    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        private PanelMenuPresenterCallback() throws  {
        }

        public void onCloseMenu(MenuBuilder $r1, boolean $z0) throws  {
            boolean $z1;
            MenuBuilder $r2 = $r1.getRootMenu();
            if ($r2 != $r1) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            AppCompatDelegateImplV7 $r3 = AppCompatDelegateImplV7.this;
            if ($z1) {
                $r1 = $r2;
            }
            PanelFeatureState $r4 = $r3.findMenuPanel($r1);
            if ($r4 == null) {
                return;
            }
            if ($z1) {
                AppCompatDelegateImplV7.this.callOnPanelClosed($r4.featureId, $r4, $r2);
                AppCompatDelegateImplV7.this.closePanel($r4, true);
                return;
            }
            AppCompatDelegateImplV7.this.closePanel($r4, $z0);
        }

        public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
            if ($r1 == null && AppCompatDelegateImplV7.this.mHasActionBar) {
                Window.Callback $r3 = AppCompatDelegateImplV7.this.getWindowCallback();
                if (!($r3 == null || AppCompatDelegateImplV7.this.isDestroyed())) {
                    $r3.onMenuOpened(108, $r1);
                }
            }
            return true;
        }
    }

    android.support.v7.view.ActionMode startSupportActionModeFromWindow(android.support.v7.view.ActionMode.Callback r41) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:43:0x01ee
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
        r40 = this;
        r0 = r40;
        r0.endOnGoingFadeAnimation();
        r0 = r40;
        r3 = r0.mActionMode;
        if (r3 == 0) goto L_0x0012;
    L_0x000b:
        r0 = r40;
        r3 = r0.mActionMode;
        r3.finish();
    L_0x0012:
        r4 = new android.support.v7.app.AppCompatDelegateImplV7$ActionModeCallbackWrapperV7;
        r5 = r4;
        r0 = r40;
        r1 = r41;
        r4.<init>(r1);
        r3 = 0;
        r0 = r40;
        r6 = r0.mAppCompatCallback;
        if (r6 == 0) goto L_0x0033;
    L_0x0023:
        r0 = r40;
        r7 = r0.isDestroyed();
        if (r7 != 0) goto L_0x0033;
    L_0x002b:
        r0 = r40;
        r6 = r0.mAppCompatCallback;
        r3 = r6.onWindowStartingSupportActionMode(r5);	 Catch:{ AbstractMethodError -> 0x0246 }
    L_0x0033:
        if (r3 == 0) goto L_0x0055;
    L_0x0035:
        r0 = r40;
        r0.mActionMode = r3;
    L_0x0039:
        r0 = r40;
        r3 = r0.mActionMode;
        if (r3 == 0) goto L_0x0050;
    L_0x003f:
        r0 = r40;
        r6 = r0.mAppCompatCallback;
        if (r6 == 0) goto L_0x0050;
    L_0x0045:
        r0 = r40;
        r6 = r0.mAppCompatCallback;
        r0 = r40;
        r3 = r0.mActionMode;
        r6.onSupportActionModeStarted(r3);
    L_0x0050:
        r0 = r40;
        r3 = r0.mActionMode;
        return r3;
    L_0x0055:
        r0 = r40;
        r8 = r0.mActionModeView;
        if (r8 != 0) goto L_0x0132;
    L_0x005b:
        r0 = r40;
        r7 = r0.mIsFloating;
        if (r7 == 0) goto L_0x01fb;
    L_0x0061:
        r9 = new android.util.TypedValue;
        r10 = r9;
        r9.<init>();
        r0 = r40;
        r11 = r0.mContext;
        r12 = r11.getTheme();
        r13 = android.support.v7.appcompat.C0192R.attr.actionBarTheme;
        r14 = 1;
        r12.resolveAttribute(r13, r10, r14);
        r13 = r10.resourceId;
        if (r13 == 0) goto L_0x01f6;
    L_0x0079:
        r0 = r40;
        r11 = r0.mContext;
        r15 = r11.getResources();
        r16 = r15.newTheme();
        r0 = r16;
        r0.setTo(r12);
        r13 = r10.resourceId;
        r14 = 1;
        r0 = r16;
        r0.applyStyle(r13, r14);
        r17 = new android.support.v7.view.ContextThemeWrapper;
        r11 = r17;
        r0 = r40;
        r0 = r0.mContext;
        r18 = r0;
        r14 = 0;
        r0 = r17;
        r1 = r18;
        r0.<init>(r1, r14);
        r12 = r11.getTheme();
        r0 = r16;
        r12.setTo(r0);
    L_0x00ad:
        r19 = new android.support.v7.widget.ActionBarContextView;
        r0 = r19;
        r0.<init>(r11);
        r0 = r19;
        r1 = r40;
        r1.mActionModeView = r0;
        r20 = new android.widget.PopupWindow;
        r13 = android.support.v7.appcompat.C0192R.attr.actionModePopupWindowStyle;
        r21 = 0;
        r0 = r20;
        r1 = r21;
        r0.<init>(r11, r1, r13);
        r0 = r20;
        r1 = r40;
        r1.mActionModePopup = r0;
        r0 = r40;
        r0 = r0.mActionModePopup;
        r22 = r0;
        r14 = 2;
        r0 = r22;
        android.support.v4.widget.PopupWindowCompat.setWindowLayoutType(r0, r14);
        r0 = r40;
        r0 = r0.mActionModePopup;
        r22 = r0;
        r0 = r40;
        r8 = r0.mActionModeView;
        r0 = r22;
        r0.setContentView(r8);
        r0 = r40;
        r0 = r0.mActionModePopup;
        r22 = r0;
        r14 = -1;
        r0 = r22;
        r0.setWidth(r14);
        r16 = r11.getTheme();
        r13 = android.support.v7.appcompat.C0192R.attr.actionBarSize;
        r14 = 1;
        r0 = r16;
        r0.resolveAttribute(r13, r10, r14);
        r13 = r10.data;
        r15 = r11.getResources();
        r23 = r15.getDisplayMetrics();
        r0 = r23;
        r13 = android.util.TypedValue.complexToDimensionPixelSize(r13, r0);
        r0 = r40;
        r8 = r0.mActionModeView;
        r8.setContentHeight(r13);
        r0 = r40;
        r0 = r0.mActionModePopup;
        r22 = r0;
        r14 = -2;
        r0 = r22;
        r0.setHeight(r14);
        r24 = new android.support.v7.app.AppCompatDelegateImplV7$5;
        r0 = r24;
        r1 = r40;
        r0.<init>();
        r0 = r24;
        r1 = r40;
        r1.mShowActionModePopup = r0;
    L_0x0132:
        r0 = r40;
        r8 = r0.mActionModeView;
        if (r8 == 0) goto L_0x0039;
    L_0x0138:
        r0 = r40;
        r0.endOnGoingFadeAnimation();
        r0 = r40;
        r8 = r0.mActionModeView;
        r8.killMode();
        r25 = new android.support.v7.view.StandaloneActionMode;
        r26 = r25;
        r0 = r40;
        r8 = r0.mActionModeView;
        r11 = r8.getContext();
        r0 = r40;
        r8 = r0.mActionModeView;
        r0 = r40;
        r0 = r0.mActionModePopup;
        r22 = r0;
        if (r22 != 0) goto L_0x0233;
    L_0x015c:
        r7 = 1;
    L_0x015d:
        r0 = r25;
        r0.<init>(r11, r8, r5, r7);
        r0 = r26;
        r27 = r0.getMenu();
        r0 = r41;
        r1 = r26;
        r2 = r27;
        r7 = r0.onCreateActionMode(r1, r2);
        if (r7 == 0) goto L_0x0239;
    L_0x0174:
        r0 = r26;
        r0.invalidate();
        r0 = r40;
        r8 = r0.mActionModeView;
        r0 = r26;
        r8.initForMode(r0);
        r0 = r26;
        r1 = r40;
        r1.mActionMode = r0;
        r0 = r40;
        r8 = r0.mActionModeView;
        r28 = 0;
        r0 = r28;
        android.support.v4.view.ViewCompat.setAlpha(r8, r0);
        r0 = r40;
        r8 = r0.mActionModeView;
        r29 = android.support.v4.view.ViewCompat.animate(r8);
        r28 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r29;
        r1 = r28;
        r29 = r0.alpha(r1);
        r0 = r29;
        r1 = r40;
        r1.mFadeAnim = r0;
        goto L_0x01b0;
    L_0x01ad:
        goto L_0x0132;
    L_0x01b0:
        r0 = r40;
        r0 = r0.mFadeAnim;
        r29 = r0;
        r30 = new android.support.v7.app.AppCompatDelegateImplV7$6;
        r0 = r30;
        r1 = r40;
        r0.<init>();
        r0 = r29;
        r1 = r30;
        r0.setListener(r1);
        goto L_0x01ca;
    L_0x01c7:
        goto L_0x015d;
    L_0x01ca:
        r0 = r40;
        r0 = r0.mActionModePopup;
        r22 = r0;
        if (r22 == 0) goto L_0x0039;
    L_0x01d2:
        r0 = r40;
        r0 = r0.mWindow;
        r31 = r0;
        r32 = r0.getDecorView();
        r0 = r40;
        r0 = r0.mShowActionModePopup;
        r33 = r0;
        goto L_0x01e6;
    L_0x01e3:
        goto L_0x0039;
    L_0x01e6:
        r0 = r32;
        r1 = r33;
        r0.post(r1);
        goto L_0x01e3;
        goto L_0x01f2;
    L_0x01ef:
        goto L_0x01ad;
    L_0x01f2:
        goto L_0x01f6;
    L_0x01f3:
        goto L_0x00ad;
    L_0x01f6:
        r0 = r40;
        r11 = r0.mContext;
        goto L_0x01f3;
    L_0x01fb:
        r0 = r40;
        r0 = r0.mSubDecor;
        r34 = r0;
        r13 = android.support.v7.appcompat.C0192R.id.action_mode_bar_stub;
        r0 = r34;
        r32 = r0.findViewById(r13);
        r36 = r32;
        r36 = (android.support.v7.widget.ViewStubCompat) r36;
        r35 = r36;
        if (r35 == 0) goto L_0x0132;
    L_0x0211:
        r0 = r40;
        r11 = r0.getActionBarThemedContext();
        r37 = android.view.LayoutInflater.from(r11);
        r0 = r35;
        r1 = r37;
        r0.setLayoutInflater(r1);
        r0 = r35;
        r32 = r0.inflate();
        r38 = r32;
        r38 = (android.support.v7.widget.ActionBarContextView) r38;
        r8 = r38;
        r0 = r40;
        r0.mActionModeView = r8;
        goto L_0x01ef;
    L_0x0233:
        r7 = 0;
        goto L_0x01c7;
        goto L_0x0239;
    L_0x0236:
        goto L_0x0039;
    L_0x0239:
        r21 = 0;
        r0 = r21;
        r1 = r40;
        r1.mActionMode = r0;
        goto L_0x0236;
        goto L_0x0246;
    L_0x0243:
        goto L_0x0033;
    L_0x0246:
        r39 = move-exception;
        goto L_0x0243;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatDelegateImplV7.startSupportActionModeFromWindow(android.support.v7.view.ActionMode$Callback):android.support.v7.view.ActionMode");
    }

    AppCompatDelegateImplV7(Context $r1, Window $r2, AppCompatCallback $r3) throws  {
        super($r1, $r2, $r3);
    }

    public void onCreate(Bundle savedInstanceState) throws  {
        if ((this.mOriginalWindowCallback instanceof Activity) && NavUtils.getParentActivityName((Activity) this.mOriginalWindowCallback) != null) {
            ActionBar $r5 = peekSupportActionBar();
            if ($r5 == null) {
                this.mEnableDefaultActionBarUp = true;
            } else {
                $r5.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void onPostCreate(Bundle savedInstanceState) throws  {
        ensureSubDecor();
    }

    public void initWindowDecorActionBar() throws  {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            if (this.mOriginalWindowCallback instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) this.mOriginalWindowCallback, this.mOverlayActionBar);
            } else if (this.mOriginalWindowCallback instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) this.mOriginalWindowCallback);
            }
            if (this.mActionBar != null) {
                this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    public void setSupportActionBar(Toolbar $r1) throws  {
        if (this.mOriginalWindowCallback instanceof Activity) {
            ActionBar $r4 = getSupportActionBar();
            if ($r4 instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.mMenuInflater = null;
            if ($r4 != null) {
                $r4.onDestroy();
            }
            if ($r1 != null) {
                ToolbarActionBar $r2 = new ToolbarActionBar($r1, ((Activity) this.mContext).getTitle(), this.mAppCompatWindowCallback);
                this.mActionBar = $r2;
                this.mWindow.setCallback($r2.getWrappedWindowCallback());
            } else {
                this.mActionBar = null;
                this.mWindow.setCallback(this.mAppCompatWindowCallback);
            }
            invalidateOptionsMenu();
        }
    }

    @Nullable
    public View findViewById(@IdRes int $i0) throws  {
        ensureSubDecor();
        return this.mWindow.findViewById($i0);
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        if (this.mHasActionBar && this.mSubDecorInstalled) {
            ActionBar $r2 = getSupportActionBar();
            if ($r2 != null) {
                $r2.onConfigurationChanged($r1);
            }
        }
        applyDayNight();
    }

    public void onStop() throws  {
        ActionBar $r1 = getSupportActionBar();
        if ($r1 != null) {
            $r1.setShowHideAnimationEnabled(false);
        }
    }

    public void onPostResume() throws  {
        ActionBar $r1 = getSupportActionBar();
        if ($r1 != null) {
            $r1.setShowHideAnimationEnabled(true);
        }
    }

    public void setContentView(View $r1) throws  {
        ensureSubDecor();
        ViewGroup $r3 = (ViewGroup) this.mSubDecor.findViewById(16908290);
        $r3.removeAllViews();
        $r3.addView($r1);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void setContentView(int $i0) throws  {
        ensureSubDecor();
        ViewGroup $r1 = (ViewGroup) this.mSubDecor.findViewById(16908290);
        $r1.removeAllViews();
        LayoutInflater.from(this.mContext).inflate($i0, $r1);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void setContentView(View $r1, LayoutParams $r2) throws  {
        ensureSubDecor();
        ViewGroup $r4 = (ViewGroup) this.mSubDecor.findViewById(16908290);
        $r4.removeAllViews();
        $r4.addView($r1, $r2);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void addContentView(View $r1, LayoutParams $r2) throws  {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(16908290)).addView($r1, $r2);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void onDestroy() throws  {
        super.onDestroy();
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy();
        }
    }

    private void ensureSubDecor() throws  {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = createSubDecor();
            CharSequence $r2 = getTitle();
            if (!TextUtils.isEmpty($r2)) {
                onTitleChanged($r2);
            }
            applyFixedSizeWindow();
            onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            PanelFeatureState $r3 = getPanelState(0, false);
            if (!isDestroyed()) {
                if ($r3 == null || $r3.menu == null) {
                    invalidatePanelMenu(108);
                }
            }
        }
    }

    private ViewGroup createSubDecor() throws  {
        TypedArray $r4 = this.mContext.obtainStyledAttributes(C0192R.styleable.AppCompatTheme);
        if ($r4.hasValue(C0192R.styleable.AppCompatTheme_windowActionBar)) {
            if ($r4.getBoolean(C0192R.styleable.AppCompatTheme_windowNoTitle, false)) {
                requestWindowFeature(1);
            } else if ($r4.getBoolean(C0192R.styleable.AppCompatTheme_windowActionBar, false)) {
                requestWindowFeature(108);
            }
            if ($r4.getBoolean(C0192R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
                requestWindowFeature(109);
            }
            if ($r4.getBoolean(C0192R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
                requestWindowFeature(10);
            }
            this.mIsFloating = $r4.getBoolean(C0192R.styleable.AppCompatTheme_android_windowIsFloating, false);
            $r4.recycle();
            this.mWindow.getDecorView();
            LayoutInflater $r7 = LayoutInflater.from(this.mContext);
            ViewGroup $r8 = null;
            if (this.mWindowNoTitle) {
                if (this.mOverlayActionMode) {
                    $r8 = (ViewGroup) $r7.inflate(C0192R.layout.abc_screen_simple_overlay_action_mode, null);
                } else {
                    $r8 = (ViewGroup) $r7.inflate(C0192R.layout.abc_screen_simple, null);
                }
                if (VERSION.SDK_INT >= 21) {
                    ViewCompat.setOnApplyWindowInsetsListener($r8, new C01772());
                } else {
                    ((FitWindowsViewGroup) $r8).setOnFitSystemWindowsListener(new C01783());
                }
            } else if (this.mIsFloating) {
                $r8 = (ViewGroup) $r7.inflate(C0192R.layout.abc_dialog_title_material, null);
                this.mOverlayActionBar = false;
                this.mHasActionBar = false;
            } else if (this.mHasActionBar) {
                Context $r2;
                TypedValue $r1 = r0;
                TypedValue typedValue = new TypedValue();
                this.mContext.getTheme().resolveAttribute(C0192R.attr.actionBarTheme, $r1, true);
                if ($r1.resourceId != 0) {
                    $r2 = r0;
                    Context contextThemeWrapper = new ContextThemeWrapper(this.mContext, $r1.resourceId);
                } else {
                    $r2 = this.mContext;
                }
                $r8 = (ViewGroup) LayoutInflater.from($r2).inflate(C0192R.layout.abc_screen_toolbar, null);
                this.mDecorContentParent = (DecorContentParent) $r8.findViewById(C0192R.id.decor_content_parent);
                this.mDecorContentParent.setWindowCallback(getWindowCallback());
                if (this.mOverlayActionBar) {
                    this.mDecorContentParent.initFeature(109);
                }
                if (this.mFeatureProgress) {
                    this.mDecorContentParent.initFeature(2);
                }
                if (this.mFeatureIndeterminateProgress) {
                    this.mDecorContentParent.initFeature(5);
                }
            }
            if ($r8 == null) {
                StringBuilder $r11 = new StringBuilder().append("AppCompat does not support the current theme features: { windowActionBar: ");
                throw new IllegalArgumentException($r11.append(this.mHasActionBar).append(", windowActionBarOverlay: ").append(this.mOverlayActionBar).append(", android:windowIsFloating: ").append(this.mIsFloating).append(", windowActionModeOverlay: ").append(this.mOverlayActionMode).append(", windowNoTitle: ").append(this.mWindowNoTitle).append(" }").toString());
            }
            if (this.mDecorContentParent == null) {
                this.mTitleView = (TextView) $r8.findViewById(C0192R.id.title);
            }
            ViewUtils.makeOptionalFitsSystemWindows($r8);
            ContentFrameLayout $r21 = (ContentFrameLayout) $r8.findViewById(C0192R.id.action_bar_activity_content);
            ViewGroup $r22 = (ViewGroup) this.mWindow.findViewById(16908290);
            if ($r22 != null) {
                while ($r22.getChildCount() > 0) {
                    View $r9 = $r22.getChildAt(0);
                    $r22.removeViewAt(0);
                    $r21.addView($r9);
                }
                $r22.setId(-1);
                $r21.setId(16908290);
                if ($r22 instanceof FrameLayout) {
                    ((FrameLayout) $r22).setForeground(null);
                }
            }
            this.mWindow.setContentView($r8);
            $r21.setAttachListener(new C01794());
            return $r8;
        }
        $r4.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    void onSubDecorInstalled(ViewGroup subDecor) throws  {
    }

    private void applyFixedSizeWindow() throws  {
        ContentFrameLayout $r3 = (ContentFrameLayout) this.mSubDecor.findViewById(16908290);
        View $r2 = this.mWindow.getDecorView();
        $r3.setDecorPadding($r2.getPaddingLeft(), $r2.getPaddingTop(), $r2.getPaddingRight(), $r2.getPaddingBottom());
        TypedArray $r7 = this.mContext.obtainStyledAttributes(C0192R.styleable.AppCompatTheme);
        $r7.getValue(C0192R.styleable.AppCompatTheme_windowMinWidthMajor, $r3.getMinWidthMajor());
        $r7.getValue(C0192R.styleable.AppCompatTheme_windowMinWidthMinor, $r3.getMinWidthMinor());
        if ($r7.hasValue(C0192R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            $r7.getValue(C0192R.styleable.AppCompatTheme_windowFixedWidthMajor, $r3.getFixedWidthMajor());
        }
        if ($r7.hasValue(C0192R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            $r7.getValue(C0192R.styleable.AppCompatTheme_windowFixedWidthMinor, $r3.getFixedWidthMinor());
        }
        if ($r7.hasValue(C0192R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            $r7.getValue(C0192R.styleable.AppCompatTheme_windowFixedHeightMajor, $r3.getFixedHeightMajor());
        }
        if ($r7.hasValue(C0192R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            $r7.getValue(C0192R.styleable.AppCompatTheme_windowFixedHeightMinor, $r3.getFixedHeightMinor());
        }
        $r7.recycle();
        $r3.requestLayout();
    }

    public boolean requestWindowFeature(int $i0) throws  {
        $i0 = sanitizeWindowFeatureId($i0);
        if (this.mWindowNoTitle && $i0 == 108) {
            return false;
        }
        if (this.mHasActionBar && $i0 == 1) {
            this.mHasActionBar = false;
        }
        switch ($i0) {
            case 1:
                throwFeatureRequestIfSubDecorInstalled();
                this.mWindowNoTitle = true;
                return true;
            case 2:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            case 5:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            case 10:
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionMode = true;
                return true;
            case 108:
                throwFeatureRequestIfSubDecorInstalled();
                this.mHasActionBar = true;
                return true;
            case 109:
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionBar = true;
                return true;
            default:
                return this.mWindow.requestFeature($i0);
        }
    }

    public boolean hasWindowFeature(int $i0) throws  {
        $i0 = sanitizeWindowFeatureId($i0);
        switch ($i0) {
            case 1:
                return this.mWindowNoTitle;
            case 2:
                return this.mFeatureProgress;
            case 5:
                return this.mFeatureIndeterminateProgress;
            case 10:
                return this.mOverlayActionMode;
            case 108:
                return this.mHasActionBar;
            case 109:
                return this.mOverlayActionBar;
            default:
                return this.mWindow.hasFeature($i0);
        }
    }

    void onTitleChanged(CharSequence $r1) throws  {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle($r1);
        } else if (peekSupportActionBar() != null) {
            peekSupportActionBar().setWindowTitle($r1);
        } else if (this.mTitleView != null) {
            this.mTitleView.setText($r1);
        }
    }

    void onPanelClosed(int $i0, Menu menu) throws  {
        if ($i0 == 108) {
            ActionBar $r2 = getSupportActionBar();
            if ($r2 != null) {
                $r2.dispatchMenuVisibilityChanged(false);
            }
        } else if ($i0 == 0) {
            PanelFeatureState $r3 = getPanelState($i0, true);
            if ($r3.isOpen) {
                closePanel($r3, false);
            }
        }
    }

    boolean onMenuOpened(int $i0, Menu menu) throws  {
        if ($i0 != 108) {
            return false;
        }
        ActionBar $r2 = getSupportActionBar();
        if ($r2 == null) {
            return true;
        }
        $r2.dispatchMenuVisibilityChanged(true);
        return true;
    }

    public boolean onMenuItemSelected(MenuBuilder $r1, MenuItem $r2) throws  {
        Window.Callback $r3 = getWindowCallback();
        if (!($r3 == null || isDestroyed())) {
            PanelFeatureState $r4 = findMenuPanel($r1.getRootMenu());
            if ($r4 != null) {
                return $r3.onMenuItemSelected($r4.featureId, $r2);
            }
        }
        return false;
    }

    public void onMenuModeChange(MenuBuilder $r1) throws  {
        reopenMenu($r1, true);
    }

    public ActionMode startSupportActionMode(ActionMode.Callback $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        ActionModeCallbackWrapperV7 $r2 = new ActionModeCallbackWrapperV7($r1);
        ActionBar $r5 = getSupportActionBar();
        if ($r5 != null) {
            this.mActionMode = $r5.startActionMode($r2);
            if (!(this.mActionMode == null || this.mAppCompatCallback == null)) {
                this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
            }
        }
        if (this.mActionMode == null) {
            this.mActionMode = startSupportActionModeFromWindow($r2);
        }
        return this.mActionMode;
    }

    public void invalidateOptionsMenu() throws  {
        ActionBar $r1 = getSupportActionBar();
        if ($r1 == null || !$r1.invalidateOptionsMenu()) {
            invalidatePanelMenu(0);
        }
    }

    private void endOnGoingFadeAnimation() throws  {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.cancel();
        }
    }

    boolean onBackPressed() throws  {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
            return true;
        }
        ActionBar $r2 = getSupportActionBar();
        return $r2 != null && $r2.collapseActionView();
    }

    boolean onKeyShortcut(int $i0, KeyEvent $r1) throws  {
        ActionBar $r2 = getSupportActionBar();
        if ($r2 != null && $r2.onKeyShortcut($i0, $r1)) {
            return true;
        }
        if (this.mPreparedPanel == null || !performPanelShortcut(this.mPreparedPanel, $r1.getKeyCode(), $r1, 1)) {
            if (this.mPreparedPanel == null) {
                PanelFeatureState $r3 = getPanelState(0, true);
                preparePanel($r3, $r1);
                boolean $z0 = performPanelShortcut($r3, $r1.getKeyCode(), $r1, 1);
                $r3.isPrepared = false;
                if ($z0) {
                    return true;
                }
            }
            return false;
        } else if (this.mPreparedPanel == null) {
            return true;
        } else {
            this.mPreparedPanel.isHandled = true;
            return true;
        }
    }

    boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        if ($r1.getKeyCode() == 82 && this.mOriginalWindowCallback.dispatchKeyEvent($r1)) {
            return true;
        }
        boolean $z0;
        int $i0 = $r1.getKeyCode();
        if ($r1.getAction() == 0) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        return $z0 ? onKeyDown($i0, $r1) : onKeyUp($i0, $r1);
    }

    boolean onKeyUp(int $i0, KeyEvent $r1) throws  {
        switch ($i0) {
            case 4:
                boolean $z0 = this.mLongPressBackDown;
                this.mLongPressBackDown = false;
                PanelFeatureState $r2 = getPanelState(0, false);
                if ($r2 == null || !$r2.isOpen) {
                    if (onBackPressed()) {
                        return true;
                    }
                } else if ($z0) {
                    return true;
                } else {
                    closePanel($r2, true);
                    return true;
                }
                break;
            case 82:
                onKeyUpPanel(0, $r1);
                return true;
            default:
                break;
        }
        return false;
    }

    boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        boolean $z0 = true;
        switch ($i0) {
            case 4:
                if (($r1.getFlags() & 128) == 0) {
                    $z0 = false;
                }
                this.mLongPressBackDown = $z0;
                break;
            case 82:
                onKeyDownPanel(0, $r1);
                return true;
            default:
                break;
        }
        if (VERSION.SDK_INT < 11) {
            onKeyShortcut($i0, $r1);
        }
        return false;
    }

    public View createView(View $r1, String $r2, @NonNull Context $r3, @NonNull AttributeSet $r4) throws  {
        boolean $z1;
        boolean $z0 = VERSION.SDK_INT < 21;
        if (this.mAppCompatViewInflater == null) {
            this.mAppCompatViewInflater = new AppCompatViewInflater();
        }
        if ($z0 && shouldInheritContext((ViewParent) $r1)) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        return this.mAppCompatViewInflater.createView($r1, $r2, $r3, $r4, $z1, $z0, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        ViewParent $r3 = this.mWindow.getDecorView();
        while ($r1 != null) {
            if ($r1 == $r3 || !($r1 instanceof View) || ViewCompat.isAttachedToWindow((View) $r1)) {
                return false;
            }
            $r1 = $r1.getParent();
        }
        return true;
    }

    public void installViewFactory() throws  {
        LayoutInflater $r2 = LayoutInflater.from(this.mContext);
        if ($r2.getFactory() == null) {
            LayoutInflaterCompat.setFactory($r2, this);
        } else if (!(LayoutInflaterCompat.getFactory($r2) instanceof AppCompatDelegateImplV7)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final View onCreateView(View $r1, String $r2, Context $r3, AttributeSet $r4) throws  {
        View $r5 = callActivityOnCreateView($r1, $r2, $r3, $r4);
        return $r5 != null ? $r5 : createView($r1, $r2, $r3, $r4);
    }

    View callActivityOnCreateView(View parent, String $r2, Context $r3, AttributeSet $r4) throws  {
        if (this.mOriginalWindowCallback instanceof Factory) {
            parent = ((Factory) this.mOriginalWindowCallback).onCreateView($r2, $r3, $r4);
            if (parent != null) {
                return parent;
            }
        }
        return null;
    }

    private void openPanel(PanelFeatureState $r1, KeyEvent $r2) throws  {
        if (!$r1.isOpen && !isDestroyed()) {
            if ($r1.featureId == 0) {
                Context $r3 = this.mContext;
                boolean $z0 = ($r3.getResources().getConfiguration().screenLayout & 15) == 4;
                boolean $z1 = $r3.getApplicationInfo().targetSdkVersion >= 11;
                if ($z0 && $z1) {
                    return;
                }
            }
            Window.Callback $r7 = getWindowCallback();
            if ($r7 != null) {
                if (!$r7.onMenuOpened($r1.featureId, $r1.menu)) {
                    closePanel($r1, true);
                    return;
                }
            }
            WindowManager $r10 = (WindowManager) this.mContext.getSystemService("window");
            if ($r10 != null && preparePanel($r1, $r2)) {
                LayoutParams layoutParams;
                int i = -2;
                View $r12;
                LayoutParams $r13;
                if ($r1.decorView == null || $r1.refreshDecorView) {
                    ViewGroup $r11;
                    if ($r1.decorView == null) {
                        if (!initializePanelDecor($r1) || $r1.decorView == null) {
                            return;
                        }
                    } else if ($r1.refreshDecorView) {
                        $r11 = $r1.decorView;
                        if ($r11.getChildCount() > 0) {
                            $r11 = $r1.decorView;
                            $r11.removeAllViews();
                        }
                    }
                    if (initializePanelContent($r1) && $r1.hasPanelItems()) {
                        $r12 = $r1.shownPanelView;
                        $r13 = $r12.getLayoutParams();
                        LayoutParams $r14 = $r13;
                        if ($r13 == null) {
                            layoutParams = new LayoutParams(-2, -2);
                        }
                        int $i0 = $r1.background;
                        $r11 = $r1.decorView;
                        $r11.setBackgroundResource($i0);
                        $r12 = $r1.shownPanelView;
                        ViewParent $r15 = $r12.getParent();
                        if ($r15 != null && ($r15 instanceof ViewGroup)) {
                            ((ViewGroup) $r15).removeView($r1.shownPanelView);
                        }
                        $r1.decorView.addView($r1.shownPanelView, $r14);
                        $r12 = $r1.shownPanelView;
                        if (!$r12.hasFocus()) {
                            $r12 = $r1.shownPanelView;
                            $r12.requestFocus();
                        }
                    } else {
                        return;
                    }
                } else if ($r1.createdPanelView != null) {
                    $r12 = $r1.createdPanelView;
                    $r13 = $r12.getLayoutParams();
                    if ($r13 != null && $r13.width == -1) {
                        i = -1;
                    }
                }
                $r1.isHandled = false;
                layoutParams = new WindowManager.LayoutParams(i, -2, $r1.f6x, $r1.f7y, 1002, 8519680, -3);
                layoutParams.gravity = $r1.gravity;
                layoutParams.windowAnimations = $r1.windowAnimations;
                $r10.addView($r1.decorView, layoutParams);
                $r1.isOpen = true;
            }
        }
    }

    private boolean initializePanelDecor(PanelFeatureState $r1) throws  {
        $r1.setStyle(getActionBarThemedContext());
        $r1.decorView = new ListMenuDecorView($r1.listPresenterContext);
        $r1.gravity = 81;
        return true;
    }

    private void reopenMenu(MenuBuilder menu, boolean $z0) throws  {
        if (this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || (ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext)) && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState $r9 = getPanelState(0, true);
            $r9.refreshDecorView = true;
            closePanel($r9, false);
            openPanel($r9, null);
            return;
        }
        Window.Callback $r5 = getWindowCallback();
        if (this.mDecorContentParent.isOverflowMenuShowing() && $z0) {
            this.mDecorContentParent.hideOverflowMenu();
            if (!isDestroyed()) {
                $r5.onPanelClosed(108, getPanelState(0, true).menu);
            }
        } else if ($r5 != null && !isDestroyed()) {
            if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                this.mInvalidatePanelMenuRunnable.run();
            }
            $r9 = getPanelState(0, true);
            if ($r9.menu != null && !$r9.refreshMenuContent && $r5.onPreparePanel(0, $r9.createdPanelView, $r9.menu)) {
                $r5.onMenuOpened(108, $r9.menu);
                this.mDecorContentParent.showOverflowMenu();
            }
        }
    }

    private boolean initializePanelMenu(PanelFeatureState $r1) throws  {
        Context $r5 = this.mContext;
        if (($r1.featureId == 0 || $r1.featureId == 108) && this.mDecorContentParent != null) {
            Theme $r10;
            TypedValue $r4 = new TypedValue();
            Theme $r7 = $r5.getTheme();
            $r7.resolveAttribute(C0192R.attr.actionBarTheme, $r4, true);
            Theme $r8 = null;
            if ($r4.resourceId != 0) {
                $r10 = $r5.getResources().newTheme();
                $r8 = $r10;
                $r10.setTo($r7);
                $r10.applyStyle($r4.resourceId, true);
                $r10.resolveAttribute(C0192R.attr.actionBarWidgetTheme, $r4, true);
            } else {
                $r7.resolveAttribute(C0192R.attr.actionBarWidgetTheme, $r4, true);
            }
            if ($r4.resourceId != 0) {
                if ($r8 == null) {
                    $r10 = $r5.getResources().newTheme();
                    $r8 = $r10;
                    $r10.setTo($r7);
                }
                $r8.applyStyle($r4.resourceId, true);
            }
            if ($r8 != null) {
                Context $r2 = new ContextThemeWrapper($r5, 0);
                $r2.getTheme().setTo($r8);
                $r5 = $r2;
            }
        }
        MenuBuilder $r3 = new MenuBuilder($r5);
        $r3.setCallback(this);
        $r1.setMenu($r3);
        return true;
    }

    private boolean initializePanelContent(PanelFeatureState $r1) throws  {
        if ($r1.createdPanelView != null) {
            $r1.shownPanelView = $r1.createdPanelView;
            return true;
        } else if ($r1.menu == null) {
            return false;
        } else {
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            $r1.shownPanelView = (View) $r1.getListMenuView(this.mPanelMenuPresenterCallback);
            return $r1.shownPanelView != null;
        }
    }

    private boolean preparePanel(PanelFeatureState $r1, KeyEvent $r2) throws  {
        if (isDestroyed()) {
            return false;
        }
        boolean $z0 = $r1;
        $r1 = $z0;
        if ($z0.isPrepared) {
            return true;
        }
        if (!(this.mPreparedPanel == null || this.mPreparedPanel == $r1)) {
            closePanel(this.mPreparedPanel, false);
        }
        Window.Callback $r5 = getWindowCallback();
        if ($r5 != null) {
            $r1.createdPanelView = $r5.onCreatePanelView($r1.featureId);
        }
        boolean $z02 = $r1.featureId == 0 || $r1.featureId == 108;
        if ($z02 && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if ($r1.createdPanelView == null && !($z02 && (peekSupportActionBar() instanceof ToolbarActionBar))) {
            if ($r1.menu == null || $r1.refreshMenuContent) {
                if ($r1.menu == null) {
                    if (!initializePanelMenu($r1)) {
                        return false;
                    }
                    if ($r1.menu == null) {
                        return false;
                    }
                }
                if ($z02 && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu($r1.menu, this.mActionMenuPresenterCallback);
                }
                $r1.menu.stopDispatchingItemsChanged();
                if ($r5.onCreatePanelMenu($r1.featureId, $r1.menu)) {
                    $r1.refreshMenuContent = false;
                } else {
                    $r1.setMenu(null);
                    if (!$z02) {
                        return false;
                    }
                    if (this.mDecorContentParent == null) {
                        return false;
                    }
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    return false;
                }
            }
            $r1.menu.stopDispatchingItemsChanged();
            if ($r1.frozenActionViewState != null) {
                $r1.menu.restoreActionViewStates($r1.frozenActionViewState);
                $r1.frozenActionViewState = null;
            }
            if ($r5.onPreparePanel(0, $r1.createdPanelView, $r1.menu)) {
                if (KeyCharacterMap.load($r2 != null ? $r2.getDeviceId() : -1).getKeyboardType() != 1) {
                    $z02 = true;
                } else {
                    $z02 = false;
                }
                $r1.qwertyMode = $z02;
                MenuBuilder $r9 = $r1.menu;
                $z0 = $r1;
                $r1 = $z0;
                $r9.setQwertyMode($z0.qwertyMode);
                $r1.menu.startDispatchingItemsChanged();
            } else {
                if ($z02 && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                $r1.menu.startDispatchingItemsChanged();
                return false;
            }
        }
        $r1.isPrepared = true;
        $r1.isHandled = false;
        this.mPreparedPanel = $r1;
        return true;
    }

    private void checkCloseActionMenu(MenuBuilder $r1) throws  {
        if (!this.mClosingActionMenu) {
            this.mClosingActionMenu = true;
            this.mDecorContentParent.dismissPopups();
            Window.Callback $r3 = getWindowCallback();
            if (!($r3 == null || isDestroyed())) {
                $r3.onPanelClosed(108, $r1);
            }
            this.mClosingActionMenu = false;
        }
    }

    private void closePanel(int $i0) throws  {
        closePanel(getPanelState($i0, true), true);
    }

    private void closePanel(PanelFeatureState $r1, boolean $z0) throws  {
        if ($z0 && $r1.featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu($r1.menu);
            return;
        }
        WindowManager $r6 = (WindowManager) this.mContext.getSystemService("window");
        if (!($r6 == null || !$r1.isOpen || $r1.decorView == null)) {
            $r6.removeView($r1.decorView);
            if ($z0) {
                callOnPanelClosed($r1.featureId, $r1, null);
            }
        }
        $r1.isPrepared = false;
        $r1.isHandled = false;
        $r1.isOpen = false;
        $r1.shownPanelView = null;
        $r1.refreshDecorView = true;
        if (this.mPreparedPanel == $r1) {
            this.mPreparedPanel = null;
        }
    }

    private boolean onKeyDownPanel(int $i0, KeyEvent $r1) throws  {
        if ($r1.getRepeatCount() == 0) {
            PanelFeatureState $r2 = getPanelState($i0, true);
            if (!$r2.isOpen) {
                return preparePanel($r2, $r1);
            }
        }
        return false;
    }

    private boolean onKeyUpPanel(int $i0, KeyEvent $r1) throws  {
        if (this.mActionMode != null) {
            return false;
        }
        boolean $z0 = false;
        PanelFeatureState $r3 = getPanelState($i0, true);
        if ($i0 != 0 || this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext))) {
            if ($r3.isOpen || $r3.isHandled) {
                $z0 = $r3.isOpen;
                closePanel($r3, true);
            } else if ($r3.isPrepared) {
                boolean $z1 = true;
                if ($r3.refreshMenuContent) {
                    $r3.isPrepared = false;
                    $z1 = preparePanel($r3, $r1);
                }
                if ($z1) {
                    openPanel($r3, $r1);
                    $z0 = true;
                }
            }
        } else if (this.mDecorContentParent.isOverflowMenuShowing()) {
            $z0 = this.mDecorContentParent.hideOverflowMenu();
        } else if (!isDestroyed() && preparePanel($r3, $r1)) {
            $z0 = this.mDecorContentParent.showOverflowMenu();
        }
        if (!$z0) {
            return $z0;
        }
        AudioManager $r8 = (AudioManager) this.mContext.getSystemService("audio");
        if ($r8 != null) {
            $r8.playSoundEffect(0);
            return $z0;
        }
        Log.w("AppCompatDelegate", "Couldn't get audio manager");
        return $z0;
    }

    private void callOnPanelClosed(int $i0, PanelFeatureState $r1, Menu $r2) throws  {
        if ($r2 == null) {
            if ($r1 == null && $i0 >= 0 && $i0 < this.mPanels.length) {
                $r1 = this.mPanels[$i0];
            }
            if ($r1 != null) {
                $r2 = $r1.menu;
            }
        }
        if (($r1 == null || $r1.isOpen) && !isDestroyed()) {
            this.mOriginalWindowCallback.onPanelClosed($i0, $r2);
        }
    }

    private PanelFeatureState findMenuPanel(Menu $r1) throws  {
        int $i0;
        PanelFeatureState[] $r2 = this.mPanels;
        if ($r2 != null) {
            $i0 = $r2.length;
        } else {
            $i0 = 0;
        }
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            PanelFeatureState $r4 = $r2[$i1];
            if ($r4 != null && $r4.menu == $r1) {
                return $r4;
            }
        }
        return null;
    }

    private PanelFeatureState getPanelState(int $i0, boolean required) throws  {
        PanelFeatureState[] $r2 = this.mPanels;
        if ($r2 == null || $r2.length <= $i0) {
            PanelFeatureState[] $r1 = new PanelFeatureState[($i0 + 1)];
            if ($r2 != null) {
                System.arraycopy($r2, 0, $r1, 0, $r2.length);
            }
            $r2 = $r1;
            this.mPanels = $r1;
        }
        PanelFeatureState $r3 = $r2[$i0];
        if ($r3 != null) {
            return $r3;
        }
        $r3 = new PanelFeatureState($i0);
        $r2[$i0] = $r3;
        return $r3;
    }

    private boolean performPanelShortcut(PanelFeatureState $r1, int $i0, KeyEvent $r2, int $i1) throws  {
        if ($r2.isSystem()) {
            return false;
        }
        boolean $z0 = false;
        if (($r1.isPrepared || preparePanel($r1, $r2)) && $r1.menu != null) {
            $z0 = $r1.menu.performShortcut($i0, $r2, $i1);
        }
        if (!$z0 || ($i1 & 1) != 0 || this.mDecorContentParent != null) {
            return $z0;
        }
        closePanel($r1, true);
        return $z0;
    }

    private void invalidatePanelMenu(int $i0) throws  {
        this.mInvalidatePanelMenuFeatures |= 1 << $i0;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    private void doInvalidatePanelMenu(int $i0) throws  {
        PanelFeatureState $r1 = getPanelState($i0, true);
        if ($r1.menu != null) {
            Bundle $r3 = new Bundle();
            $r1.menu.saveActionViewStates($r3);
            if ($r3.size() > 0) {
                $r1.frozenActionViewState = $r3;
            }
            $r1.menu.stopDispatchingItemsChanged();
            $r1.menu.clear();
        }
        $r1.refreshMenuContent = true;
        $r1.refreshDecorView = true;
        if (($i0 == 108 || $i0 == 0) && this.mDecorContentParent != null) {
            $r1 = getPanelState(0, false);
            if ($r1 != null) {
                $r1.isPrepared = false;
                preparePanel($r1, null);
            }
        }
    }

    private int updateStatusGuard(int $i0) throws  {
        byte $b1 = (byte) 0;
        boolean $z0 = false;
        if (this.mActionModeView != null && (this.mActionModeView.getLayoutParams() instanceof MarginLayoutParams)) {
            MarginLayoutParams $r5 = (MarginLayoutParams) this.mActionModeView.getLayoutParams();
            boolean $z1 = false;
            if (this.mActionModeView.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect $r1 = this.mTempRect1;
                Rect $r2 = this.mTempRect2;
                $r1.set(0, $i0, 0, 0);
                ViewUtils.computeFitSystemWindows(this.mSubDecor, $r1, $r2);
                int $i2 = $r2.top == 0 ? $i0 : 0;
                int i = $r5.topMargin;
                int $i3 = i;
                if (i != $i2) {
                    $z1 = true;
                    $r5.topMargin = $i0;
                    if (this.mStatusGuard == null) {
                        this.mStatusGuard = new View(this.mContext);
                        View $r7 = this.mStatusGuard;
                        Context $r8 = this.mContext;
                        $r7.setBackgroundColor($r8.getResources().getColor(C0192R.color.abc_input_method_navigation_guard));
                        this.mSubDecor.addView(this.mStatusGuard, -1, new LayoutParams(-1, $i0));
                    } else {
                        View $r72 = this.mStatusGuard;
                        LayoutParams $r4 = $r72.getLayoutParams();
                        i = $r4.height;
                        $i2 = i;
                        if (i != $i0) {
                            $r4.height = $i0;
                            $r72 = this.mStatusGuard;
                            $r72.setLayoutParams($r4);
                        }
                    }
                }
                if (this.mStatusGuard != null) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                if (!this.mOverlayActionMode && $z0) {
                    $i0 = 0;
                }
            } else if ($r5.topMargin != 0) {
                $z1 = true;
                $r5.topMargin = 0;
            }
            if ($z1) {
                this.mActionModeView.setLayoutParams($r5);
            }
        }
        if (this.mStatusGuard == null) {
            return $i0;
        }
        $r7 = this.mStatusGuard;
        if (!$z0) {
            $b1 = (byte) 8;
        }
        $r7.setVisibility($b1);
        return $i0;
    }

    private void throwFeatureRequestIfSubDecorInstalled() throws  {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int sanitizeWindowFeatureId(int $i0) throws  {
        if ($i0 == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if ($i0 != 9) {
            return $i0;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    ViewGroup getSubDecor() throws  {
        return this.mSubDecor;
    }

    private void dismissPopups() throws  {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
            this.mActionModePopup = null;
        }
        endOnGoingFadeAnimation();
        PanelFeatureState $r6 = getPanelState(0, false);
        if ($r6 != null && $r6.menu != null) {
            $r6.menu.close();
        }
    }
}
