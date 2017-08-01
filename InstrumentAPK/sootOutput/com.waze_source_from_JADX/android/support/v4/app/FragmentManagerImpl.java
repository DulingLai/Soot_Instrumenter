package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import com.waze.map.CanvasFont;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflaterFactory {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(CanvasFont.OUTLINE_STROKE_WIDTH);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(CanvasFont.OUTLINE_STROKE_WIDTH);
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField = null;
    ArrayList<Fragment> mActive;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    FragmentController mController;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new C00191();
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<Runnable> mPendingActions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;

    /* compiled from: FragmentManager */
    class C00191 implements Runnable {
        C00191() throws  {
        }

        public void run() throws  {
            FragmentManagerImpl.this.execPendingActions();
        }
    }

    /* compiled from: FragmentManager */
    class C00202 implements Runnable {
        C00202() throws  {
        }

        public void run() throws  {
            FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), null, -1, 0);
        }
    }

    /* compiled from: FragmentManager */
    static class AnimateOnHWLayerIfNeededListener implements AnimationListener {
        private AnimationListener mOrignalListener = null;
        private boolean mShouldRunOnHWLayer = false;
        private View mView = null;

        /* compiled from: FragmentManager */
        class C00241 implements Runnable {
            C00241() throws  {
            }

            public void run() throws  {
                ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.mView, 2, null);
            }
        }

        /* compiled from: FragmentManager */
        class C00252 implements Runnable {
            C00252() throws  {
            }

            public void run() throws  {
                ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.mView, 0, null);
            }
        }

        public AnimateOnHWLayerIfNeededListener(View $r1, Animation $r2) throws  {
            if ($r1 != null && $r2 != null) {
                this.mView = $r1;
            }
        }

        public AnimateOnHWLayerIfNeededListener(View $r1, Animation $r2, AnimationListener $r3) throws  {
            if ($r1 != null && $r2 != null) {
                this.mOrignalListener = $r3;
                this.mView = $r1;
            }
        }

        @CallSuper
        public void onAnimationStart(Animation $r1) throws  {
            if (this.mView != null) {
                this.mShouldRunOnHWLayer = FragmentManagerImpl.shouldRunOnHWLayer(this.mView, $r1);
                if (this.mShouldRunOnHWLayer) {
                    this.mView.post(new C00241());
                }
            }
            if (this.mOrignalListener != null) {
                this.mOrignalListener.onAnimationStart($r1);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation $r1) throws  {
            if (this.mView != null && this.mShouldRunOnHWLayer) {
                this.mView.post(new C00252());
            }
            if (this.mOrignalListener != null) {
                this.mOrignalListener.onAnimationEnd($r1);
            }
        }

        public void onAnimationRepeat(Animation $r1) throws  {
            if (this.mOrignalListener != null) {
                this.mOrignalListener.onAnimationRepeat($r1);
            }
        }
    }

    /* compiled from: FragmentManager */
    class C00235 extends AnimateOnHWLayerIfNeededListener {
        final /* synthetic */ Fragment val$fragment;

        C00235(View $r2, Animation $r3, Fragment $r4) throws  {
            this.val$fragment = $r4;
            super($r2, $r3);
        }

        public void onAnimationEnd(Animation $r1) throws  {
            super.onAnimationEnd($r1);
            if (this.val$fragment.mAnimatingAway != null) {
                this.val$fragment.mAnimatingAway = null;
                FragmentManagerImpl.this.moveToState(this.val$fragment, this.val$fragment.mStateAfterAnimating, 0, 0, false);
            }
        }
    }

    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] Fragment = new int[]{16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() throws  {
        }
    }

    void moveToState(android.support.v4.app.Fragment r34, int r35, int r36, int r37, boolean r38) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:128:0x0460 in {3, 6, 11, 18, 25, 28, 31, 35, 38, 43, 48, 51, 55, 56, 59, 62, 69, 72, 73, 78, 87, 92, 96, 97, 100, 101, 104, 105, 111, 112, 113, 119, 120, 123, 126, 129, 136, 146, 150, 156, 157, 162, 163, 168, 169, 174, 181, 190, 192, 193, 195, 199, 202, 206, 207, 212, 215, 217} preds:[]
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
        r0 = r34;
        r6 = r0.mAdded;
        if (r6 == 0) goto L_0x000c;
    L_0x0006:
        r0 = r34;
        r6 = r0.mDetached;
        if (r6 == 0) goto L_0x0013;
    L_0x000c:
        r7 = 1;
        r0 = r35;
        if (r0 <= r7) goto L_0x0013;
    L_0x0011:
        r35 = 1;
    L_0x0013:
        r0 = r34;
        r6 = r0.mRemoving;
        if (r6 == 0) goto L_0x0027;
    L_0x0019:
        r0 = r34;
        r8 = r0.mState;
        r0 = r35;
        if (r0 <= r8) goto L_0x0027;
    L_0x0021:
        r0 = r34;
        r0 = r0.mState;
        r35 = r0;
    L_0x0027:
        r0 = r34;
        r6 = r0.mDeferStart;
        if (r6 == 0) goto L_0x003b;
    L_0x002d:
        r0 = r34;
        r8 = r0.mState;
        r7 = 4;
        if (r8 >= r7) goto L_0x003b;
    L_0x0034:
        r7 = 3;
        r0 = r35;
        if (r0 <= r7) goto L_0x003b;
    L_0x0039:
        r35 = 3;
    L_0x003b:
        r0 = r34;
        r8 = r0.mState;
        r0 = r35;
        if (r8 >= r0) goto L_0x047f;
    L_0x0043:
        r0 = r34;
        r0 = r0.mFromLayout;
        r38 = r0;
        if (r38 == 0) goto L_0x0054;
    L_0x004b:
        r0 = r34;
        r0 = r0.mInLayout;
        r38 = r0;
        if (r38 != 0) goto L_0x0054;
    L_0x0053:
        return;
    L_0x0054:
        r0 = r34;
        r9 = r0.mAnimatingAway;
        if (r9 == 0) goto L_0x0071;
    L_0x005a:
        r10 = 0;
        r0 = r34;
        r0.mAnimatingAway = r10;
        r0 = r34;
        r8 = r0.mStateAfterAnimating;
        r7 = 0;
        r11 = 0;
        r12 = 1;
        r0 = r33;
        r1 = r34;
        r2 = r8;
        r3 = r7;
        r4 = r11;
        r5 = r12;
        r0.moveToState(r1, r2, r3, r4, r5);
    L_0x0071:
        r0 = r34;
        r8 = r0.mState;
        switch(r8) {
            case 0: goto L_0x00c6;
            case 1: goto L_0x0285;
            case 2: goto L_0x03e2;
            case 3: goto L_0x03e2;
            case 4: goto L_0x040e;
            default: goto L_0x0078;
        };
    L_0x0078:
        goto L_0x0079;
    L_0x0079:
        r0 = r34;
        r0 = r0.mState;
        r36 = r0;
        r1 = r35;
        if (r0 == r1) goto L_0x0695;
    L_0x0083:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "moveToState: Fragment state for ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r14 = " not updated inline; ";
        r13 = r13.append(r14);
        r14 = "expected state ";
        r13 = r13.append(r14);
        r0 = r35;
        r13 = r13.append(r0);
        r14 = " found ";
        r13 = r13.append(r14);
        r0 = r34;
        r0 = r0.mState;
        r36 = r0;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.w(r14, r15);
        r0 = r35;
        r1 = r34;
        r1.mState = r0;
        return;
    L_0x00c6:
        r38 = DEBUG;
        if (r38 == 0) goto L_0x00e4;
    L_0x00ca:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "moveto CREATED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x00e4:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        if (r16 == 0) goto L_0x0177;
    L_0x00ec:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r33;
        r0 = r0.mHost;
        r17 = r0;
        r18 = r0.getContext();
        r0 = r18;
        r19 = r0.getClassLoader();
        r0 = r16;
        r1 = r19;
        r0.setClassLoader(r1);
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r14 = "android:view_state";
        r0 = r16;
        r20 = r0.getSparseParcelableArray(r14);
        r0 = r20;
        r1 = r34;
        r1.mSavedViewState = r0;
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r14 = "android:target_state";
        r0 = r33;
        r1 = r16;
        r21 = r0.getFragment(r1, r14);
        r0 = r21;
        r1 = r34;
        r1.mTarget = r0;
        r0 = r34;
        r0 = r0.mTarget;
        r21 = r0;
        if (r21 == 0) goto L_0x014e;
    L_0x013b:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r14 = "android:target_req_state";
        r7 = 0;
        r0 = r16;
        r8 = r0.getInt(r14, r7);
        r0 = r34;
        r0.mTargetRequestCode = r8;
    L_0x014e:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r14 = "android:user_visible_hint";
        r7 = 1;
        r0 = r16;
        r38 = r0.getBoolean(r14, r7);
        r0 = r38;
        r1 = r34;
        r1.mUserVisibleHint = r0;
        r0 = r34;
        r0 = r0.mUserVisibleHint;
        r38 = r0;
        if (r38 != 0) goto L_0x0177;
    L_0x016b:
        r7 = 1;
        r0 = r34;
        r0.mDeferStart = r7;
        r7 = 3;
        r0 = r35;
        if (r0 <= r7) goto L_0x0177;
    L_0x0175:
        r35 = 3;
    L_0x0177:
        r0 = r33;
        r0 = r0.mHost;
        r17 = r0;
        r1 = r34;
        r1.mHost = r0;
        r0 = r33;
        r0 = r0.mParent;
        r21 = r0;
        r1 = r34;
        r1.mParentFragment = r0;
        r0 = r33;
        r0 = r0.mParent;
        r21 = r0;
        if (r21 == 0) goto L_0x01e4;
    L_0x0193:
        r0 = r33;
        r0 = r0.mParent;
        r21 = r0;
        r0 = r0.mChildFragmentManager;
        r22 = r0;
    L_0x019d:
        r0 = r22;
        r1 = r34;
        r1.mFragmentManager = r0;
        r7 = 0;
        r0 = r34;
        r0.mCalled = r7;
        r0 = r33;
        r0 = r0.mHost;
        r17 = r0;
        r18 = r0.getContext();
        r0 = r34;
        r1 = r18;
        r0.onAttach(r1);
        r0 = r34;
        r0 = r0.mCalled;
        r38 = r0;
        if (r38 != 0) goto L_0x01ef;
    L_0x01c1:
        r23 = new android.support.v4.app.SuperNotCalledException;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "Fragment ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r14 = " did not call through to super.onAttach()";
        r13 = r13.append(r14);
        r15 = r13.toString();
        r0 = r23;
        r0.<init>(r15);
        throw r23;
    L_0x01e4:
        r0 = r33;
        r0 = r0.mHost;
        r17 = r0;
        r22 = r0.getFragmentManagerImpl();
        goto L_0x019d;
    L_0x01ef:
        r0 = r34;
        r0 = r0.mParentFragment;
        r21 = r0;
        if (r21 != 0) goto L_0x0202;
    L_0x01f7:
        r0 = r33;
        r0 = r0.mHost;
        r17 = r0;
        r1 = r34;
        r0.onAttachFragment(r1);
    L_0x0202:
        r0 = r34;
        r0 = r0.mRetaining;
        r38 = r0;
        if (r38 != 0) goto L_0x0217;
    L_0x020a:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r0.performCreate(r1);
    L_0x0217:
        r7 = 0;
        r0 = r34;
        r0.mRetaining = r7;
        r0 = r34;
        r0 = r0.mFromLayout;
        r38 = r0;
        if (r38 == 0) goto L_0x0285;
    L_0x0224:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r24 = r0.getLayoutInflater(r1);
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r10 = 0;
        r0 = r34;
        r1 = r24;
        r2 = r16;
        r9 = r0.performCreateView(r1, r10, r2);
        r0 = r34;
        r0.mView = r9;
        r0 = r34;
        r9 = r0.mView;
        if (r9 == 0) goto L_0x0464;
    L_0x024d:
        r0 = r34;
        r9 = r0.mView;
        r0 = r34;
        r0.mInnerView = r9;
        r8 = android.os.Build.VERSION.SDK_INT;
        r7 = 11;
        if (r8 < r7) goto L_0x044d;
    L_0x025b:
        r0 = r34;
        r9 = r0.mView;
        r7 = 0;
        android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r9, r7);
    L_0x0263:
        r0 = r34;
        r0 = r0.mHidden;
        r38 = r0;
        if (r38 == 0) goto L_0x0274;
    L_0x026b:
        r0 = r34;
        r9 = r0.mView;
        r7 = 8;
        r9.setVisibility(r7);
    L_0x0274:
        r0 = r34;
        r9 = r0.mView;
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r0.onViewCreated(r9, r1);
    L_0x0285:
        r7 = 1;
        r0 = r35;
        if (r0 <= r7) goto L_0x03e2;
    L_0x028a:
        r38 = DEBUG;
        if (r38 == 0) goto L_0x02a8;
    L_0x028e:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "moveto ACTIVITY_CREATED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x02a8:
        r0 = r34;
        r0 = r0.mFromLayout;
        r38 = r0;
        if (r38 != 0) goto L_0x03bd;
    L_0x02b0:
        r25 = 0;
        r0 = r34;
        r8 = r0.mContainerId;
        if (r8 == 0) goto L_0x0327;
    L_0x02b8:
        r0 = r33;
        r0 = r0.mContainer;
        r26 = r0;
        r0 = r34;
        r8 = r0.mContainerId;
        r0 = r26;
        r9 = r0.onFindViewById(r8);
        r27 = r9;
        r27 = (android.view.ViewGroup) r27;
        r25 = r27;
        if (r25 != 0) goto L_0x0327;
    L_0x02d0:
        r0 = r34;
        r0 = r0.mRestored;
        r38 = r0;
        if (r38 != 0) goto L_0x0327;
    L_0x02d8:
        r28 = new java.lang.IllegalArgumentException;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "No view found for id 0x";
        r13 = r13.append(r14);
        r0 = r34;
        r8 = r0.mContainerId;
        r15 = java.lang.Integer.toHexString(r8);
        r13 = r13.append(r15);
        r14 = " (";
        r13 = r13.append(r14);
        r0 = r34;
        r29 = r0.getResources();
        r0 = r34;
        r8 = r0.mContainerId;
        r0 = r29;
        r15 = r0.getResourceName(r8);
        r13 = r13.append(r15);
        r14 = ") for fragment ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r0 = r28;
        r0.<init>(r15);
        r0 = r33;
        r1 = r28;
        r0.throwException(r1);
    L_0x0327:
        r0 = r25;
        r1 = r34;
        r1.mContainer = r0;
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r24 = r0.getLayoutInflater(r1);
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r24;
        r2 = r25;
        r3 = r16;
        r9 = r0.performCreateView(r1, r2, r3);
        r0 = r34;
        r0.mView = r9;
        r0 = r34;
        r9 = r0.mView;
        if (r9 == 0) goto L_0x0479;
    L_0x0357:
        r0 = r34;
        r9 = r0.mView;
        r0 = r34;
        r0.mInnerView = r9;
        r8 = android.os.Build.VERSION.SDK_INT;
        r7 = 11;
        if (r8 < r7) goto L_0x046a;
    L_0x0365:
        r0 = r34;
        r9 = r0.mView;
        r7 = 0;
        android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r9, r7);
    L_0x036d:
        if (r25 == 0) goto L_0x039b;
    L_0x036f:
        r7 = 1;
        r0 = r33;
        r1 = r34;
        r2 = r36;
        r3 = r37;
        r30 = r0.loadAnimation(r1, r2, r7, r3);
        if (r30 == 0) goto L_0x0392;
    L_0x037e:
        r0 = r34;
        r9 = r0.mView;
        r0 = r33;
        r1 = r30;
        r0.setHWLayerAnimListenerIfAlpha(r9, r1);
        r0 = r34;
        r9 = r0.mView;
        r0 = r30;
        r9.startAnimation(r0);
    L_0x0392:
        r0 = r34;
        r9 = r0.mView;
        r0 = r25;
        r0.addView(r9);
    L_0x039b:
        r0 = r34;
        r0 = r0.mHidden;
        r38 = r0;
        if (r38 == 0) goto L_0x03ac;
    L_0x03a3:
        r0 = r34;
        r9 = r0.mView;
        r7 = 8;
        r9.setVisibility(r7);
    L_0x03ac:
        r0 = r34;
        r9 = r0.mView;
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r0.onViewCreated(r9, r1);
    L_0x03bd:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r0.performActivityCreated(r1);
        r0 = r34;
        r9 = r0.mView;
        if (r9 == 0) goto L_0x03dd;
    L_0x03d0:
        r0 = r34;
        r0 = r0.mSavedFragmentState;
        r16 = r0;
        r0 = r34;
        r1 = r16;
        r0.restoreViewState(r1);
    L_0x03dd:
        r10 = 0;
        r0 = r34;
        r0.mSavedFragmentState = r10;
    L_0x03e2:
        r7 = 3;
        r0 = r35;
        if (r0 <= r7) goto L_0x040e;
    L_0x03e7:
        r38 = DEBUG;
        if (r38 == 0) goto L_0x0409;
    L_0x03eb:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        goto L_0x03f4;
    L_0x03f1:
        goto L_0x036d;
    L_0x03f4:
        r14 = "moveto STARTED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x0409:
        r0 = r34;
        r0.performStart();
    L_0x040e:
        r7 = 4;
        r0 = r35;
        if (r0 <= r7) goto L_0x0079;
    L_0x0413:
        r38 = DEBUG;
        if (r38 == 0) goto L_0x0439;
    L_0x0417:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        goto L_0x0420;
    L_0x041d:
        goto L_0x03bd;
    L_0x0420:
        r14 = "moveto RESUMED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
        goto L_0x0439;
    L_0x0436:
        goto L_0x03f1;
    L_0x0439:
        r0 = r34;
        r0.performResume();
        r10 = 0;
        r0 = r34;
        r0.mSavedFragmentState = r10;
        goto L_0x0447;
    L_0x0444:
        goto L_0x0079;
    L_0x0447:
        r10 = 0;
        r0 = r34;
        r0.mSavedViewState = r10;
        goto L_0x0444;
    L_0x044d:
        r0 = r34;
        r9 = r0.mView;
        r25 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r9);
        goto L_0x0459;
    L_0x0456:
        goto L_0x0263;
    L_0x0459:
        r0 = r25;
        r1 = r34;
        r1.mView = r0;
        goto L_0x0456;
        goto L_0x0464;
    L_0x0461:
        goto L_0x0285;
    L_0x0464:
        r10 = 0;
        r0 = r34;
        r0.mInnerView = r10;
        goto L_0x0461;
    L_0x046a:
        r0 = r34;
        r9 = r0.mView;
        r31 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r9);
        r0 = r31;
        r1 = r34;
        r1.mView = r0;
        goto L_0x0436;
    L_0x0479:
        r10 = 0;
        r0 = r34;
        r0.mInnerView = r10;
        goto L_0x041d;
    L_0x047f:
        r0 = r34;
        r8 = r0.mState;
        r0 = r35;
        if (r8 <= r0) goto L_0x0079;
    L_0x0487:
        r0 = r34;
        r8 = r0.mState;
        goto L_0x048f;
    L_0x048c:
        goto L_0x0079;
    L_0x048f:
        switch(r8) {
            case 1: goto L_0x0494;
            case 2: goto L_0x053c;
            case 3: goto L_0x0514;
            case 4: goto L_0x04ec;
            case 5: goto L_0x04c4;
            default: goto L_0x0492;
        };
    L_0x0492:
        goto L_0x0493;
    L_0x0493:
        goto L_0x048c;
    L_0x0494:
        r7 = 1;
        r0 = r35;
        if (r0 >= r7) goto L_0x0079;
    L_0x0499:
        r0 = r33;
        r6 = r0.mDestroyed;
        if (r6 == 0) goto L_0x04b1;
    L_0x049f:
        r0 = r34;
        r9 = r0.mAnimatingAway;
        if (r9 == 0) goto L_0x04b1;
    L_0x04a5:
        r0 = r34;
        r9 = r0.mAnimatingAway;
        r10 = 0;
        r0 = r34;
        r0.mAnimatingAway = r10;
        r9.clearAnimation();
    L_0x04b1:
        r0 = r34;
        r9 = r0.mAnimatingAway;
        if (r9 == 0) goto L_0x0604;
    L_0x04b7:
        r0 = r35;
        r1 = r34;
        r1.mStateAfterAnimating = r0;
        goto L_0x04c1;
    L_0x04be:
        goto L_0x0079;
    L_0x04c1:
        r35 = 1;
        goto L_0x04be;
    L_0x04c4:
        r7 = 5;
        r0 = r35;
        if (r0 >= r7) goto L_0x04ec;
    L_0x04c9:
        r6 = DEBUG;
        if (r6 == 0) goto L_0x04e7;
    L_0x04cd:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "movefrom RESUMED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x04e7:
        r0 = r34;
        r0.performPause();
    L_0x04ec:
        r7 = 4;
        r0 = r35;
        if (r0 >= r7) goto L_0x0514;
    L_0x04f1:
        r6 = DEBUG;
        if (r6 == 0) goto L_0x050f;
    L_0x04f5:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "movefrom STARTED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x050f:
        r0 = r34;
        r0.performStop();
    L_0x0514:
        r7 = 3;
        r0 = r35;
        if (r0 >= r7) goto L_0x053c;
    L_0x0519:
        r6 = DEBUG;
        if (r6 == 0) goto L_0x0537;
    L_0x051d:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "movefrom STOPPED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x0537:
        r0 = r34;
        r0.performReallyStop();
    L_0x053c:
        r7 = 2;
        r0 = r35;
        if (r0 >= r7) goto L_0x0494;
    L_0x0541:
        r6 = DEBUG;
        if (r6 == 0) goto L_0x055f;
    L_0x0545:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "movefrom ACTIVITY_CREATED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x055f:
        r0 = r34;
        r9 = r0.mView;
        if (r9 == 0) goto L_0x0582;
    L_0x0565:
        r0 = r33;
        r0 = r0.mHost;
        r17 = r0;
        r1 = r34;
        r6 = r0.onShouldSaveFragmentState(r1);
        if (r6 == 0) goto L_0x0582;
    L_0x0573:
        r0 = r34;
        r0 = r0.mSavedViewState;
        r20 = r0;
        if (r20 != 0) goto L_0x0582;
    L_0x057b:
        r0 = r33;
        r1 = r34;
        r0.saveFragmentViewState(r1);
    L_0x0582:
        r0 = r34;
        r0.performDestroyView();
        r0 = r34;
        r9 = r0.mView;
        if (r9 == 0) goto L_0x05f0;
    L_0x058d:
        r0 = r34;
        r0 = r0.mContainer;
        r25 = r0;
        if (r25 == 0) goto L_0x05f0;
    L_0x0595:
        r30 = 0;
        r0 = r33;
        r8 = r0.mCurState;
        if (r8 <= 0) goto L_0x05b0;
    L_0x059d:
        r0 = r33;
        r6 = r0.mDestroyed;
        if (r6 != 0) goto L_0x05b0;
    L_0x05a3:
        r7 = 0;
        r0 = r33;
        r1 = r34;
        r2 = r36;
        r3 = r37;
        r30 = r0.loadAnimation(r1, r2, r7, r3);
    L_0x05b0:
        if (r30 == 0) goto L_0x05e1;
    L_0x05b2:
        r0 = r34;
        r9 = r0.mView;
        r0 = r34;
        r0.mAnimatingAway = r9;
        r0 = r35;
        r1 = r34;
        r1.mStateAfterAnimating = r0;
        r0 = r34;
        r9 = r0.mView;
        r32 = new android.support.v4.app.FragmentManagerImpl$5;
        r0 = r32;
        r1 = r33;
        r2 = r30;
        r3 = r34;
        r0.<init>(r9, r2, r3);
        r0 = r30;
        r1 = r32;
        r0.setAnimationListener(r1);
        r0 = r34;
        r9 = r0.mView;
        r0 = r30;
        r9.startAnimation(r0);
    L_0x05e1:
        r0 = r34;
        r0 = r0.mContainer;
        r25 = r0;
        r0 = r34;
        r9 = r0.mView;
        r0 = r25;
        r0.removeView(r9);
    L_0x05f0:
        r10 = 0;
        r0 = r34;
        r0.mContainer = r10;
        r10 = 0;
        r0 = r34;
        r0.mView = r10;
        goto L_0x05fe;
    L_0x05fb:
        goto L_0x0494;
    L_0x05fe:
        r10 = 0;
        r0 = r34;
        r0.mInnerView = r10;
        goto L_0x05fb;
    L_0x0604:
        r6 = DEBUG;
        if (r6 == 0) goto L_0x0622;
    L_0x0608:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "movefrom CREATED: ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r15 = r13.toString();
        r14 = "FragmentManager";
        android.util.Log.v(r14, r15);
    L_0x0622:
        r0 = r34;
        r6 = r0.mRetaining;
        if (r6 != 0) goto L_0x0660;
    L_0x0628:
        r0 = r34;
        r0.performDestroy();
    L_0x062d:
        r7 = 0;
        r0 = r34;
        r0.mCalled = r7;
        r0 = r34;
        r0.onDetach();
        r0 = r34;
        r6 = r0.mCalled;
        if (r6 != 0) goto L_0x0666;
    L_0x063d:
        r23 = new android.support.v4.app.SuperNotCalledException;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "Fragment ";
        r13 = r13.append(r14);
        r0 = r34;
        r13 = r13.append(r0);
        r14 = " did not call through to super.onDetach()";
        r13 = r13.append(r14);
        r15 = r13.toString();
        r0 = r23;
        r0.<init>(r15);
        throw r23;
    L_0x0660:
        r7 = 0;
        r0 = r34;
        r0.mState = r7;
        goto L_0x062d;
    L_0x0666:
        if (r38 != 0) goto L_0x0079;
    L_0x0668:
        r0 = r34;
        r0 = r0.mRetaining;
        r38 = r0;
        if (r38 != 0) goto L_0x067c;
    L_0x0670:
        goto L_0x0674;
    L_0x0671:
        goto L_0x0079;
    L_0x0674:
        r0 = r33;
        r1 = r34;
        r0.makeInactive(r1);
        goto L_0x0671;
    L_0x067c:
        r10 = 0;
        r0 = r34;
        r0.mHost = r10;
        r10 = 0;
        r0 = r34;
        r0.mParentFragment = r10;
        r10 = 0;
        r0 = r34;
        r0.mFragmentManager = r10;
        goto L_0x068f;
    L_0x068c:
        goto L_0x0079;
    L_0x068f:
        r10 = 0;
        r0 = r34;
        r0.mChildFragmentManager = r10;
        goto L_0x068c;
    L_0x0695:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    public android.view.View onCreateView(android.view.View r30, java.lang.String r31, android.content.Context r32, android.util.AttributeSet r33) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:48:0x01b6 in {3, 6, 10, 17, 18, 21, 24, 28, 31, 35, 36, 42, 43, 47, 49, 55, 57, 61, 65, 68, 70, 72} preds:[]
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
        r6 = "fragment";
        r0 = r31;
        r7 = r6.equals(r0);
        if (r7 != 0) goto L_0x000c;
    L_0x000a:
        r8 = 0;
        return r8;
    L_0x000c:
        r8 = 0;
        r9 = "class";
        r0 = r33;
        r6 = r0.getAttributeValue(r8, r9);
        r31 = r6;
        r10 = android.support.v4.app.FragmentManagerImpl.FragmentTag.Fragment;
        r0 = r32;
        r1 = r33;
        r11 = r0.obtainStyledAttributes(r1, r10);
        if (r6 != 0) goto L_0x0028;
    L_0x0023:
        r12 = 0;
        r31 = r11.getString(r12);
    L_0x0028:
        r12 = 1;
        r14 = -1;
        r13 = r11.getResourceId(r12, r14);
        r12 = 2;
        r6 = r11.getString(r12);
        r11.recycle();
        r0 = r29;
        r15 = r0.mHost;
        r16 = r15.getContext();
        r0 = r16;
        r1 = r31;
        r7 = android.support.v4.app.Fragment.isSupportFragmentClass(r0, r1);
        if (r7 == 0) goto L_0x0295;
    L_0x0048:
        if (r30 == 0) goto L_0x008d;
    L_0x004a:
        r0 = r30;
        r17 = r0.getId();
    L_0x0050:
        r12 = -1;
        r0 = r17;
        if (r0 != r12) goto L_0x0090;
    L_0x0055:
        r12 = -1;
        if (r13 != r12) goto L_0x0090;
    L_0x0058:
        if (r6 != 0) goto L_0x0090;
    L_0x005a:
        r18 = new java.lang.IllegalArgumentException;
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r0 = r33;
        r6 = r0.getPositionDescription();
        r0 = r19;
        r19 = r0.append(r6);
        r9 = ": Must specify unique android:id, android:tag, or have a parent with an id for ";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r1 = r31;
        r19 = r0.append(r1);
        r0 = r19;
        r31 = r0.toString();
        r0 = r18;
        r1 = r31;
        r0.<init>(r1);
        throw r18;
    L_0x008d:
        r17 = 0;
        goto L_0x0050;
    L_0x0090:
        r12 = -1;
        if (r13 == r12) goto L_0x01ba;
    L_0x0093:
        r0 = r29;
        r20 = r0.findFragmentById(r13);
    L_0x0099:
        if (r20 != 0) goto L_0x00a3;
    L_0x009b:
        if (r6 == 0) goto L_0x00a3;
    L_0x009d:
        r0 = r29;
        r20 = r0.findFragmentByTag(r6);
    L_0x00a3:
        if (r20 != 0) goto L_0x00b2;
    L_0x00a5:
        r12 = -1;
        r0 = r17;
        if (r0 == r12) goto L_0x00b2;
    L_0x00aa:
        r0 = r29;
        r1 = r17;
        r20 = r0.findFragmentById(r1);
    L_0x00b2:
        r7 = DEBUG;
        if (r7 == 0) goto L_0x00fe;
    L_0x00b6:
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r9 = "onCreateView: id=0x";
        r0 = r19;
        r19 = r0.append(r9);
        r21 = java.lang.Integer.toHexString(r13);
        r0 = r19;
        r1 = r21;
        r19 = r0.append(r1);
        r9 = " fname=";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r1 = r31;
        r19 = r0.append(r1);
        r9 = " existing=";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r19;
        r21 = r0.toString();
        r9 = "FragmentManager";
        r0 = r21;
        android.util.Log.v(r9, r0);
    L_0x00fe:
        if (r20 != 0) goto L_0x01c0;
    L_0x0100:
        r0 = r32;
        r1 = r31;
        r22 = android.support.v4.app.Fragment.instantiate(r0, r1);
        r20 = r22;
        r12 = 1;
        r0 = r22;
        r0.mFromLayout = r12;
        if (r13 == 0) goto L_0x01bd;
    L_0x0111:
        r23 = r13;
    L_0x0113:
        r0 = r23;
        r1 = r22;
        r1.mFragmentId = r0;
        r0 = r17;
        r1 = r22;
        r1.mContainerId = r0;
        r0 = r22;
        r0.mTag = r6;
        r12 = 1;
        r0 = r22;
        r0.mInLayout = r12;
        r0 = r29;
        r1 = r22;
        r1.mFragmentManager = r0;
        r0 = r29;
        r15 = r0.mHost;
        r0 = r22;
        r0.mHost = r15;
        r0 = r29;
        r15 = r0.mHost;
        r32 = r15.getContext();
        r0 = r22;
        r0 = r0.mSavedFragmentState;
        r24 = r0;
        r0 = r22;
        r1 = r32;
        r2 = r33;
        r3 = r24;
        r0.onInflate(r1, r2, r3);
        r12 = 1;
        r0 = r29;
        r1 = r22;
        r0.addFragment(r1, r12);
    L_0x0157:
        r0 = r29;
        r0 = r0.mCurState;
        r17 = r0;
        r12 = 1;
        r0 = r17;
        if (r0 >= r12) goto L_0x0266;
    L_0x0162:
        r0 = r20;
        r7 = r0.mFromLayout;
        if (r7 == 0) goto L_0x0266;
    L_0x0168:
        goto L_0x016c;
    L_0x0169:
        goto L_0x0113;
    L_0x016c:
        r12 = 1;
        r14 = 0;
        r25 = 0;
        r26 = 0;
        r0 = r29;
        r1 = r20;
        r2 = r12;
        r3 = r14;
        r4 = r25;
        r5 = r26;
        r0.moveToState(r1, r2, r3, r4, r5);
    L_0x017f:
        r0 = r20;
        r0 = r0.mView;
        r30 = r0;
        if (r30 != 0) goto L_0x026e;
    L_0x0187:
        r27 = new java.lang.IllegalStateException;
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r9 = "Fragment ";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r1 = r31;
        r19 = r0.append(r1);
        r9 = " did not create a view.";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r31 = r0.toString();
        r0 = r27;
        r1 = r31;
        r0.<init>(r1);
        throw r27;
        goto L_0x01ba;
    L_0x01b7:
        goto L_0x0099;
    L_0x01ba:
        r20 = 0;
        goto L_0x01b7;
    L_0x01bd:
        r23 = r17;
        goto L_0x0169;
    L_0x01c0:
        r0 = r20;
        r7 = r0.mInLayout;
        if (r7 == 0) goto L_0x0235;
    L_0x01c6:
        r18 = new java.lang.IllegalArgumentException;
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r0 = r33;
        r21 = r0.getPositionDescription();
        r0 = r19;
        r1 = r21;
        r19 = r0.append(r1);
        r9 = ": Duplicate id 0x";
        r0 = r19;
        r19 = r0.append(r9);
        r21 = java.lang.Integer.toHexString(r13);
        r0 = r19;
        r1 = r21;
        r19 = r0.append(r1);
        goto L_0x01f5;
    L_0x01f2:
        goto L_0x017f;
    L_0x01f5:
        r9 = ", tag ";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r19 = r0.append(r6);
        r9 = ", or parent id 0x";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r17;
        r6 = java.lang.Integer.toHexString(r0);
        r0 = r19;
        r19 = r0.append(r6);
        r9 = " with another fragment for ";
        r0 = r19;
        r19 = r0.append(r9);
        r0 = r19;
        r1 = r31;
        r19 = r0.append(r1);
        r0 = r19;
        r31 = r0.toString();
        r0 = r18;
        r1 = r31;
        r0.<init>(r1);
        throw r18;
    L_0x0235:
        r12 = 1;
        r0 = r20;
        r0.mInLayout = r12;
        r0 = r29;
        r15 = r0.mHost;
        r0 = r20;
        r0.mHost = r15;
        r0 = r20;
        r7 = r0.mRetaining;
        if (r7 != 0) goto L_0x0157;
    L_0x0248:
        r0 = r29;
        r15 = r0.mHost;
        r32 = r15.getContext();
        r0 = r20;
        r0 = r0.mSavedFragmentState;
        r24 = r0;
        goto L_0x025a;
    L_0x0257:
        goto L_0x0157;
    L_0x025a:
        r0 = r20;
        r1 = r32;
        r2 = r33;
        r3 = r24;
        r0.onInflate(r1, r2, r3);
        goto L_0x0257;
    L_0x0266:
        r0 = r29;
        r1 = r20;
        r0.moveToState(r1);
        goto L_0x01f2;
    L_0x026e:
        if (r13 == 0) goto L_0x0279;
    L_0x0270:
        r0 = r20;
        r0 = r0.mView;
        r30 = r0;
        r0.setId(r13);
    L_0x0279:
        r0 = r20;
        r0 = r0.mView;
        r30 = r0;
        r28 = r0.getTag();
        if (r28 != 0) goto L_0x028e;
    L_0x0285:
        r0 = r20;
        r0 = r0.mView;
        r30 = r0;
        r0.setTag(r6);
    L_0x028e:
        r0 = r20;
        r0 = r0.mView;
        r30 = r0;
        return r30;
    L_0x0295:
        r8 = 0;
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.onCreateView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet):android.view.View");
    }

    boolean popBackStackState(android.os.Handler r23, java.lang.String r24, int r25, int r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:66:0x016b in {3, 11, 12, 14, 17, 23, 26, 29, 30, 38, 41, 42, 46, 50, 54, 60, 63, 64, 65, 67} preds:[]
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
        r22 = this;
        r0 = r22;
        r2 = r0.mBackStack;
        if (r2 != 0) goto L_0x0008;
    L_0x0006:
        r3 = 0;
        return r3;
    L_0x0008:
        if (r24 != 0) goto L_0x0045;
    L_0x000a:
        if (r25 >= 0) goto L_0x0045;
    L_0x000c:
        r4 = r26 & 1;
        if (r4 != 0) goto L_0x0045;
    L_0x0010:
        r0 = r22;
        r2 = r0.mBackStack;
        r25 = r2.size();
        r25 = r25 + -1;
        if (r25 >= 0) goto L_0x001e;
    L_0x001c:
        r3 = 0;
        return r3;
    L_0x001e:
        r0 = r22;
        r2 = r0.mBackStack;
        r0 = r25;
        r5 = r2.remove(r0);
        r7 = r5;
        r7 = (android.support.v4.app.BackStackRecord) r7;
        r6 = r7;
        r8 = new android.util.SparseArray;
        r8.<init>();
        r9 = new android.util.SparseArray;
        r9.<init>();
        r6.calculateBackFragments(r8, r9);
        r3 = 1;
        r10 = 0;
        r6.popFromBackStack(r3, r10, r8, r9);
        r0 = r22;
        r0.reportBackStackChanged();
    L_0x0043:
        r3 = 1;
        return r3;
    L_0x0045:
        r4 = -1;
        if (r24 != 0) goto L_0x004a;
    L_0x0048:
        if (r25 < 0) goto L_0x00b2;
    L_0x004a:
        r0 = r22;
        r2 = r0.mBackStack;
        r4 = r2.size();
        r4 = r4 + -1;
    L_0x0054:
        if (r4 < 0) goto L_0x0070;
    L_0x0056:
        r0 = r22;
        r2 = r0.mBackStack;
        r5 = r2.get(r4);
        r11 = r5;
        r11 = (android.support.v4.app.BackStackRecord) r11;
        r6 = r11;
        if (r24 == 0) goto L_0x0074;
    L_0x0064:
        r12 = r6.getName();
        r0 = r24;
        r13 = r0.equals(r12);
        if (r13 == 0) goto L_0x0074;
    L_0x0070:
        if (r4 >= 0) goto L_0x007f;
    L_0x0072:
        r3 = 0;
        return r3;
    L_0x0074:
        if (r25 < 0) goto L_0x007c;
    L_0x0076:
        r14 = r6.mIndex;
        r0 = r25;
        if (r0 == r14) goto L_0x0070;
    L_0x007c:
        r4 = r4 + -1;
        goto L_0x0054;
    L_0x007f:
        r26 = r26 & 1;
        if (r26 == 0) goto L_0x00b2;
    L_0x0083:
        r4 = r4 + -1;
    L_0x0085:
        if (r4 < 0) goto L_0x00b2;
    L_0x0087:
        r0 = r22;
        r2 = r0.mBackStack;
        r5 = r2.get(r4);
        r15 = r5;
        r15 = (android.support.v4.app.BackStackRecord) r15;
        r6 = r15;
        if (r24 == 0) goto L_0x00a3;
    L_0x0097:
        r12 = r6.getName();
        r0 = r24;
        r13 = r0.equals(r12);
        if (r13 != 0) goto L_0x00af;
    L_0x00a3:
        if (r25 < 0) goto L_0x00b2;
    L_0x00a5:
        r0 = r6.mIndex;
        r26 = r0;
        r0 = r25;
        r1 = r26;
        if (r0 != r1) goto L_0x00b2;
    L_0x00af:
        r4 = r4 + -1;
        goto L_0x0085;
    L_0x00b2:
        r0 = r22;
        r2 = r0.mBackStack;
        r25 = r2.size();
        r25 = r25 + -1;
        r0 = r25;
        if (r4 != r0) goto L_0x00c2;
    L_0x00c0:
        r3 = 0;
        return r3;
    L_0x00c2:
        r2 = new java.util.ArrayList;
        r2.<init>();
        r0 = r22;
        r0 = r0.mBackStack;
        r16 = r0;
        r25 = r0.size();
        r25 = r25 + -1;
    L_0x00d3:
        r0 = r25;
        if (r0 <= r4) goto L_0x00e9;
    L_0x00d7:
        r0 = r22;
        r0 = r0.mBackStack;
        r16 = r0;
        r1 = r25;
        r5 = r0.remove(r1);
        r2.add(r5);
        r25 = r25 + -1;
        goto L_0x00d3;
    L_0x00e9:
        r25 = r2.size();
        r25 = r25 + -1;
        r8 = new android.util.SparseArray;
        r8.<init>();
        r9 = new android.util.SparseArray;
        r9.<init>();
        r26 = 0;
    L_0x00fb:
        r0 = r26;
        r1 = r25;
        if (r0 > r1) goto L_0x0113;
    L_0x0101:
        r0 = r26;
        r5 = r2.get(r0);
        r17 = r5;
        r17 = (android.support.v4.app.BackStackRecord) r17;
        r6 = r17;
        r6.calculateBackFragments(r8, r9);
        r26 = r26 + 1;
        goto L_0x00fb;
    L_0x0113:
        r18 = 0;
        r26 = 0;
    L_0x0117:
        r0 = r26;
        r1 = r25;
        if (r0 > r1) goto L_0x016f;
    L_0x011d:
        r13 = DEBUG;
        if (r13 == 0) goto L_0x014d;
    L_0x0121:
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r20 = "Popping back stack state: ";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r26;
        r5 = r2.get(r0);
        r0 = r19;
        r19 = r0.append(r5);
        r0 = r19;
        r24 = r0.toString();
        r20 = "FragmentManager";
        r0 = r20;
        r1 = r24;
        android.util.Log.v(r0, r1);
    L_0x014d:
        r0 = r26;
        r5 = r2.get(r0);
        r21 = r5;
        r21 = (android.support.v4.app.BackStackRecord) r21;
        r6 = r21;
        r0 = r26;
        r1 = r25;
        if (r0 != r1) goto L_0x0169;
    L_0x015f:
        r13 = 1;
    L_0x0160:
        r0 = r18;
        r18 = r6.popFromBackStack(r13, r0, r8, r9);
        r26 = r26 + 1;
        goto L_0x0117;
    L_0x0169:
        r13 = 0;
        goto L_0x0160;
        goto L_0x016f;
    L_0x016c:
        goto L_0x0043;
    L_0x016f:
        r0 = r22;
        r0.reportBackStackChanged();
        goto L_0x016c;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.popBackStackState(android.os.Handler, java.lang.String, int, int):boolean");
    }

    public void setBackStackIndex(int r11, android.support.v4.app.BackStackRecord r12) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x007d in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
        r10 = this;
        monitor-enter(r10);
        r0 = r10.mBackStackIndices;
        if (r0 != 0) goto L_0x000c;
    L_0x0005:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r10.mBackStackIndices = r0;
    L_0x000c:
        r0 = r10.mBackStackIndices;
        r1 = r0.size();
        r2 = r1;
        if (r11 >= r1) goto L_0x0042;
    L_0x0015:
        r3 = DEBUG;
        if (r3 == 0) goto L_0x003b;
    L_0x0019:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Setting back stack index ";
        r4 = r4.append(r5);
        r4 = r4.append(r11);
        r5 = " to ";
        r4 = r4.append(r5);
        r4 = r4.append(r12);
        r6 = r4.toString();
        r5 = "FragmentManager";
        android.util.Log.v(r5, r6);
    L_0x003b:
        r0 = r10.mBackStackIndices;
        r0.set(r11, r12);
    L_0x0040:
        monitor-exit(r10);
        return;
    L_0x0042:
        if (r2 >= r11) goto L_0x007d;
    L_0x0044:
        r0 = r10.mBackStackIndices;
        r7 = 0;
        r0.add(r7);
        r0 = r10.mAvailBackStackIndices;
        if (r0 != 0) goto L_0x0055;
    L_0x004e:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r10.mAvailBackStackIndices = r0;
    L_0x0055:
        r3 = DEBUG;
        if (r3 == 0) goto L_0x0071;
    L_0x0059:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Adding available back stack index ";
        r4 = r4.append(r5);
        r4 = r4.append(r2);
        r6 = r4.toString();
        r5 = "FragmentManager";
        android.util.Log.v(r5, r6);
    L_0x0071:
        r0 = r10.mAvailBackStackIndices;
        r8 = java.lang.Integer.valueOf(r2);
        r0.add(r8);
        r2 = r2 + 1;
        goto L_0x0042;
    L_0x007d:
        r3 = DEBUG;
        if (r3 == 0) goto L_0x00a3;
    L_0x0081:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Adding back stack index ";
        r4 = r4.append(r5);
        r4 = r4.append(r11);
        r5 = " with ";
        r4 = r4.append(r5);
        r4 = r4.append(r12);
        r6 = r4.toString();
        r5 = "FragmentManager";
        android.util.Log.v(r5, r6);
    L_0x00a3:
        r0 = r10.mBackStackIndices;
        r0.add(r12);
        goto L_0x0040;
    L_0x00a9:
        r9 = move-exception;
        monitor-exit(r10);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.setBackStackIndex(int, android.support.v4.app.BackStackRecord):void");
    }

    FragmentManagerImpl() throws  {
    }

    static {
        boolean $z0 = false;
        if (VERSION.SDK_INT >= 11) {
            $z0 = true;
        }
        HONEYCOMB = $z0;
    }

    static boolean modifiesAlpha(Animation $r0) throws  {
        if ($r0 instanceof AlphaAnimation) {
            return true;
        }
        if ($r0 instanceof AnimationSet) {
            List $r2 = ((AnimationSet) $r0).getAnimations();
            for (int $i0 = 0; $i0 < $r2.size(); $i0++) {
                if ($r2.get($i0) instanceof AlphaAnimation) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(View $r0, Animation $r1) throws  {
        return VERSION.SDK_INT >= 19 && ViewCompat.getLayerType($r0) == 0 && ViewCompat.hasOverlappingRendering($r0) && modifiesAlpha($r1);
    }

    private void throwException(RuntimeException $r1) throws  {
        Log.e(TAG, $r1.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter $r3 = new PrintWriter(new LogWriter(TAG));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, $r3, new String[0]);
            } catch (Exception $r7) {
                Log.e(TAG, "Failed dumping state", $r7);
            }
        } else {
            try {
                dump("  ", null, $r3, new String[0]);
            } catch (Exception $r8) {
                Log.e(TAG, "Failed dumping state", $r8);
            }
        }
        throw $r1;
    }

    public FragmentTransaction beginTransaction() throws  {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() throws  {
        return execPendingActions();
    }

    public void popBackStack() throws  {
        enqueueAction(new C00202(), false);
    }

    public boolean popBackStackImmediate() throws  {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mHost.getHandler(), null, -1, 0);
    }

    public void popBackStack(final String $r1, final int $i0) throws  {
        enqueueAction(new Runnable() {
            public void run() throws  {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), $r1, -1, $i0);
            }
        }, false);
    }

    public boolean popBackStackImmediate(String $r1, int $i0) throws  {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mHost.getHandler(), $r1, -1, $i0);
    }

    public void popBackStack(final int $i0, final int $i1) throws  {
        if ($i0 < 0) {
            throw new IllegalArgumentException("Bad id: " + $i0);
        }
        enqueueAction(new Runnable() {
            public void run() throws  {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), null, $i0, $i1);
            }
        }, false);
    }

    public boolean popBackStackImmediate(int $i0, int $i1) throws  {
        checkStateLoss();
        executePendingTransactions();
        if ($i0 >= 0) {
            return popBackStackState(this.mHost.getHandler(), null, $i0, $i1);
        }
        throw new IllegalArgumentException("Bad id: " + $i0);
    }

    public int getBackStackEntryCount() throws  {
        return this.mBackStack != null ? this.mBackStack.size() : 0;
    }

    public BackStackEntry getBackStackEntryAt(int $i0) throws  {
        return (BackStackEntry) this.mBackStack.get($i0);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener $r1) throws  {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add($r1);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener $r1) throws  {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove($r1);
        }
    }

    public void putFragment(Bundle $r1, String $r2, Fragment $r3) throws  {
        if ($r3.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + $r3 + " is not currently in the FragmentManager"));
        }
        $r1.putInt($r2, $r3.mIndex);
    }

    public Fragment getFragment(Bundle $r1, String $r2) throws  {
        int $i0 = $r1.getInt($r2, -1);
        if ($i0 == -1) {
            return null;
        }
        if ($i0 >= this.mActive.size()) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + $r2 + ": index " + $i0));
        }
        Fragment $r3 = (Fragment) this.mActive.get($i0);
        if ($r3 != null) {
            return $r3;
        }
        throwException(new IllegalStateException("Fragment no longer exists for key " + $r2 + ": index " + $i0));
        return $r3;
    }

    public List<Fragment> getFragments() throws  {
        return this.mActive;
    }

    public SavedState saveFragmentInstanceState(Fragment $r1) throws  {
        if ($r1.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + $r1 + " is not currently in the FragmentManager"));
        }
        if ($r1.mState <= 0) {
            return null;
        }
        Bundle $r6 = saveFragmentBasicState($r1);
        if ($r6 != null) {
            return new SavedState($r6);
        }
        return null;
    }

    public boolean isDestroyed() throws  {
        return this.mDestroyed;
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder(128);
        $r1.append("FragmentManager{");
        $r1.append(Integer.toHexString(System.identityHashCode(this)));
        $r1.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, $r1);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, $r1);
        }
        $r1.append("}}");
        return $r1.toString();
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        int $i0;
        int $i1;
        Fragment $r10;
        String $r6 = $r1 + "    ";
        if (this.mActive != null) {
            $i0 = this.mActive.size();
            if ($i0 > 0) {
                $r3.print($r1);
                $r3.print("Active Fragments in ");
                $r3.print(Integer.toHexString(System.identityHashCode(this)));
                $r3.println(":");
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    $r10 = (Fragment) this.mActive.get($i1);
                    $r3.print($r1);
                    $r3.print("  #");
                    $r3.print($i1);
                    $r3.print(": ");
                    $r3.println($r10);
                    if ($r10 != null) {
                        $r10.dump($r6, $r2, $r3, $r4);
                    }
                }
            }
        }
        if (this.mAdded != null) {
            $i0 = this.mAdded.size();
            if ($i0 > 0) {
                $r3.print($r1);
                $r3.println("Added Fragments:");
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    $r10 = (Fragment) this.mAdded.get($i1);
                    $r3.print($r1);
                    $r3.print("  #");
                    $r3.print($i1);
                    $r3.print(": ");
                    $r3.println($r10.toString());
                }
            }
        }
        if (this.mCreatedMenus != null) {
            $i0 = this.mCreatedMenus.size();
            if ($i0 > 0) {
                $r3.print($r1);
                $r3.println("Fragments Created Menus:");
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    $r10 = (Fragment) this.mCreatedMenus.get($i1);
                    $r3.print($r1);
                    $r3.print("  #");
                    $r3.print($i1);
                    $r3.print(": ");
                    $r3.println($r10.toString());
                }
            }
        }
        if (this.mBackStack != null) {
            $i0 = this.mBackStack.size();
            if ($i0 > 0) {
                $r3.print($r1);
                $r3.println("Back Stack:");
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    BackStackRecord $r11 = (BackStackRecord) this.mBackStack.get($i1);
                    $r3.print($r1);
                    $r3.print("  #");
                    $r3.print($i1);
                    $r3.print(": ");
                    $r3.println($r11.toString());
                    $r11.dump($r6, $r2, $r3, $r4);
                }
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null) {
                $i0 = this.mBackStackIndices.size();
                if ($i0 > 0) {
                    $r3.print($r1);
                    $r3.println("Back Stack Indices:");
                    for ($i1 = 0; $i1 < $i0; $i1++) {
                        $r11 = (BackStackRecord) this.mBackStackIndices.get($i1);
                        $r3.print($r1);
                        $r3.print("  #");
                        $r3.print($i1);
                        $r3.print(": ");
                        $r3.println($r11);
                    }
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                $r3.print($r1);
                $r3.print("mAvailBackStackIndices: ");
                $r3.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null) {
            $i0 = this.mPendingActions.size();
            if ($i0 > 0) {
                $r3.print($r1);
                $r3.println("Pending Actions:");
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    Runnable $r13 = (Runnable) this.mPendingActions.get($i1);
                    $r3.print($r1);
                    $r3.print("  #");
                    $r3.print($i1);
                    $r3.print(": ");
                    $r3.println($r13);
                }
            }
        }
        $r3.print($r1);
        $r3.println("FragmentManager misc state:");
        $r3.print($r1);
        $r3.print("  mHost=");
        $r3.println(this.mHost);
        $r3.print($r1);
        $r3.print("  mContainer=");
        $r3.println(this.mContainer);
        if (this.mParent != null) {
            $r3.print($r1);
            $r3.print("  mParent=");
            $r3.println(this.mParent);
        }
        $r3.print($r1);
        $r3.print("  mCurState=");
        $r3.print(this.mCurState);
        $r3.print(" mStateSaved=");
        $r3.print(this.mStateSaved);
        $r3.print(" mDestroyed=");
        $r3.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            $r3.print($r1);
            $r3.print("  mNeedMenuInvalidate=");
            $r3.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            $r3.print($r1);
            $r3.print("  mNoTransactionsBecause=");
            $r3.println(this.mNoTransactionsBecause);
        }
        if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
            $r3.print($r1);
            $r3.print("  mAvailIndices: ");
            $r3.println(Arrays.toString(this.mAvailIndices.toArray()));
        }
    }

    static Animation makeOpenCloseAnimation(Context context, float $f0, float $f1, float $f2, float $f3) throws  {
        AnimationSet $r3 = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation($f0, $f1, $f0, $f1, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220);
        $r3.addAnimation(scaleAnimation);
        Animation alphaAnimation = new AlphaAnimation($f2, $f3);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220);
        $r3.addAnimation(alphaAnimation);
        return $r3;
    }

    static Animation makeFadeAnimation(Context context, float $f0, float $f1) throws  {
        AlphaAnimation $r1 = new AlphaAnimation($f0, $f1);
        $r1.setInterpolator(DECELERATE_CUBIC);
        $r1.setDuration(220);
        return $r1;
    }

    Animation loadAnimation(Fragment $r1, int $i0, boolean $z0, int $i2) throws  {
        Animation $r2 = $r1.onCreateAnimation($i0, $z0, $r1.mNextAnim);
        if ($r2 != null) {
            return $r2;
        }
        if ($r1.mNextAnim != 0) {
            $r2 = AnimationUtils.loadAnimation(this.mHost.getContext(), $r1.mNextAnim);
            if ($r2 != null) {
                return $r2;
            }
        }
        if ($i0 == 0) {
            return null;
        }
        $i0 = transitToStyleIndex($i0, $z0);
        if ($i0 < 0) {
            return null;
        }
        switch ($i0) {
            case 1:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            default:
                if ($i2 == 0 && this.mHost.onHasWindowAnimations()) {
                    $i2 = this.mHost.onGetWindowAnimations();
                }
                if ($i2 == 0) {
                    return null;
                }
                return null;
        }
    }

    public void performPendingDeferredStart(Fragment $r1) throws  {
        if (!$r1.mDeferStart) {
            return;
        }
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        $r1.mDeferStart = false;
        moveToState($r1, this.mCurState, 0, 0, false);
    }

    private void setHWLayerAnimListenerIfAlpha(View $r1, Animation $r2) throws  {
        if ($r1 != null && $r2 != null && shouldRunOnHWLayer($r1, $r2)) {
            AnimationListener $r3 = null;
            if (sAnimationListenerField == null) {
                try {
                    sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                    sAnimationListenerField.setAccessible(true);
                } catch (NoSuchFieldException $r8) {
                    Log.e(TAG, "No field with the name mListener is found in Animation class", $r8);
                } catch (IllegalAccessException $r9) {
                    Log.e(TAG, "Cannot access Animation's mListener field", $r9);
                }
            }
            $r3 = (AnimationListener) sAnimationListenerField.get($r2);
            $r2.setAnimationListener(new AnimateOnHWLayerIfNeededListener($r1, $r2, $r3));
        }
    }

    void moveToState(Fragment $r1) throws  {
        moveToState($r1, this.mCurState, 0, 0, false);
    }

    void moveToState(int $i0, boolean $z0) throws  {
        moveToState($i0, 0, 0, $z0);
    }

    void moveToState(int $i0, int $i1, int $i2, boolean $z0) throws  {
        if (this.mHost == null && $i0 != 0) {
            throw new IllegalStateException("No host");
        } else if ($z0 || this.mCurState != $i0) {
            this.mCurState = $i0;
            if (this.mActive != null) {
                $z0 = false;
                for (int $i3 = 0; $i3 < this.mActive.size(); $i3++) {
                    Fragment $r5 = (Fragment) this.mActive.get($i3);
                    if ($r5 != null) {
                        moveToState($r5, $i0, $i1, $i2, false);
                        if ($r5.mLoaderManager != null) {
                            LoaderManagerImpl $r6 = $r5.mLoaderManager;
                            $z0 |= $r6.hasRunningLoaders();
                        }
                    }
                }
                if (!$z0) {
                    startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                    this.mHost.onSupportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    void startPendingDeferredFragments() throws  {
        if (this.mActive != null) {
            for (int $i0 = 0; $i0 < this.mActive.size(); $i0++) {
                Fragment $r3 = (Fragment) this.mActive.get($i0);
                if ($r3 != null) {
                    performPendingDeferredStart($r3);
                }
            }
        }
    }

    void makeActive(Fragment $r1) throws  {
        if ($r1.mIndex < 0) {
            if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
                if (this.mActive == null) {
                    this.mActive = new ArrayList();
                }
                $r1.setIndex(this.mActive.size(), this.mParent);
                this.mActive.add($r1);
            } else {
                $r1.setIndex(((Integer) this.mAvailIndices.remove(this.mAvailIndices.size() - 1)).intValue(), this.mParent);
                this.mActive.set($r1.mIndex, $r1);
            }
            if (DEBUG) {
                Log.v(TAG, "Allocated fragment index " + $r1);
            }
        }
    }

    void makeInactive(Fragment $r1) throws  {
        if ($r1.mIndex >= 0) {
            if (DEBUG) {
                Log.v(TAG, "Freeing fragment index " + $r1);
            }
            this.mActive.set($r1.mIndex, null);
            if (this.mAvailIndices == null) {
                this.mAvailIndices = new ArrayList();
            }
            this.mAvailIndices.add(Integer.valueOf($r1.mIndex));
            this.mHost.inactivateFragment($r1.mWho);
            $r1.initState();
        }
    }

    public void addFragment(Fragment $r1, boolean $z0) throws  {
        if (this.mAdded == null) {
            this.mAdded = new ArrayList();
        }
        if (DEBUG) {
            Log.v(TAG, "add: " + $r1);
        }
        makeActive($r1);
        if (!$r1.mDetached) {
            if (this.mAdded.contains($r1)) {
                throw new IllegalStateException("Fragment already added: " + $r1);
            }
            this.mAdded.add($r1);
            $r1.mAdded = true;
            $r1.mRemoving = false;
            if ($r1.mHasMenu && $r1.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            if ($z0) {
                moveToState($r1);
            }
        }
    }

    public void removeFragment(Fragment $r1, int $i0, int $i1) throws  {
        if (DEBUG) {
            Log.v(TAG, "remove: " + $r1 + " nesting=" + $r1.mBackStackNesting);
        }
        boolean $z0 = !$r1.isInBackStack();
        if (!$r1.mDetached || $z0) {
            byte $b3;
            if (this.mAdded != null) {
                this.mAdded.remove($r1);
            }
            if ($r1.mHasMenu && $r1.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            $r1.mAdded = false;
            $r1.mRemoving = true;
            if ($z0) {
                $b3 = (byte) 0;
            } else {
                $b3 = (byte) 1;
            }
            moveToState($r1, $b3, $i0, $i1, false);
        }
    }

    public void hideFragment(Fragment $r1, int $i0, int $i1) throws  {
        if (DEBUG) {
            Log.v(TAG, "hide: " + $r1);
        }
        if (!$r1.mHidden) {
            $r1.mHidden = true;
            if ($r1.mView != null) {
                Animation $r5 = loadAnimation($r1, $i0, false, $i1);
                if ($r5 != null) {
                    setHWLayerAnimListenerIfAlpha($r1.mView, $r5);
                    $r1.mView.startAnimation($r5);
                }
                $r1.mView.setVisibility(8);
            }
            if ($r1.mAdded && $r1.mHasMenu && $r1.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            $r1.onHiddenChanged(true);
        }
    }

    public void showFragment(Fragment $r1, int $i0, int $i1) throws  {
        if (DEBUG) {
            Log.v(TAG, "show: " + $r1);
        }
        if ($r1.mHidden) {
            $r1.mHidden = false;
            if ($r1.mView != null) {
                Animation $r5 = loadAnimation($r1, $i0, true, $i1);
                if ($r5 != null) {
                    setHWLayerAnimListenerIfAlpha($r1.mView, $r5);
                    $r1.mView.startAnimation($r5);
                }
                $r1.mView.setVisibility(0);
            }
            if ($r1.mAdded && $r1.mHasMenu && $r1.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            $r1.onHiddenChanged(false);
        }
    }

    public void detachFragment(Fragment $r1, int $i0, int $i1) throws  {
        if (DEBUG) {
            Log.v(TAG, "detach: " + $r1);
        }
        if (!$r1.mDetached) {
            $r1.mDetached = true;
            if ($r1.mAdded) {
                if (this.mAdded != null) {
                    if (DEBUG) {
                        Log.v(TAG, "remove from detach: " + $r1);
                    }
                    this.mAdded.remove($r1);
                }
                if ($r1.mHasMenu && $r1.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                $r1.mAdded = false;
                moveToState($r1, 1, $i0, $i1, false);
            }
        }
    }

    public void attachFragment(Fragment $r1, int $i0, int $i1) throws  {
        if (DEBUG) {
            Log.v(TAG, "attach: " + $r1);
        }
        if ($r1.mDetached) {
            $r1.mDetached = false;
            if (!$r1.mAdded) {
                if (this.mAdded == null) {
                    this.mAdded = new ArrayList();
                }
                if (this.mAdded.contains($r1)) {
                    throw new IllegalStateException("Fragment already added: " + $r1);
                }
                if (DEBUG) {
                    Log.v(TAG, "add from attach: " + $r1);
                }
                this.mAdded.add($r1);
                $r1.mAdded = true;
                if ($r1.mHasMenu && $r1.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                moveToState($r1, this.mCurState, $i0, $i1, false);
            }
        }
    }

    public Fragment findFragmentById(int $i0) throws  {
        int $i1;
        Fragment $r3;
        if (this.mAdded != null) {
            for ($i1 = this.mAdded.size() - 1; $i1 >= 0; $i1--) {
                $r3 = (Fragment) this.mAdded.get($i1);
                if ($r3 != null && $r3.mFragmentId == $i0) {
                    return $r3;
                }
            }
        }
        if (this.mActive != null) {
            for ($i1 = this.mActive.size() - 1; $i1 >= 0; $i1--) {
                $r3 = (Fragment) this.mActive.get($i1);
                if ($r3 != null && $r3.mFragmentId == $i0) {
                    return $r3;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String $r1) throws  {
        int $i0;
        Fragment $r4;
        if (!(this.mAdded == null || $r1 == null)) {
            for ($i0 = this.mAdded.size() - 1; $i0 >= 0; $i0--) {
                $r4 = (Fragment) this.mAdded.get($i0);
                if ($r4 != null && $r1.equals($r4.mTag)) {
                    return $r4;
                }
            }
        }
        if (!(this.mActive == null || $r1 == null)) {
            for ($i0 = this.mActive.size() - 1; $i0 >= 0; $i0--) {
                $r4 = (Fragment) this.mActive.get($i0);
                if ($r4 != null && $r1.equals($r4.mTag)) {
                    return $r4;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String $r1) throws  {
        if (!(this.mActive == null || $r1 == null)) {
            for (int $i0 = this.mActive.size() - 1; $i0 >= 0; $i0--) {
                Fragment $r4 = (Fragment) this.mActive.get($i0);
                if ($r4 != null) {
                    $r4 = $r4.findFragmentByWho($r1);
                    if ($r4 != null) {
                        return $r4;
                    }
                }
            }
        }
        return null;
    }

    private void checkStateLoss() throws  {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    public void enqueueAction(Runnable $r1, boolean $z0) throws  {
        if (!$z0) {
            checkStateLoss();
        }
        synchronized (this) {
            if (this.mDestroyed || this.mHost == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList();
            }
            this.mPendingActions.add($r1);
            if (this.mPendingActions.size() == 1) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(BackStackRecord $r1) throws  {
        synchronized (this) {
            int $i0;
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList();
                }
                $i0 = this.mBackStackIndices.size();
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + $i0 + " to " + $r1);
                }
                this.mBackStackIndices.add($r1);
                return $i0;
            }
            $i0 = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
            if (DEBUG) {
                Log.v(TAG, "Adding back stack index " + $i0 + " with " + $r1);
            }
            this.mBackStackIndices.set($i0, $r1);
            return $i0;
        }
    }

    public void freeBackStackIndex(int $i0) throws  {
        synchronized (this) {
            this.mBackStackIndices.set($i0, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList();
            }
            if (DEBUG) {
                Log.v(TAG, "Freeing back stack index " + $i0);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf($i0));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean execPendingActions() throws  {
        /*
        r23 = this;
        r0 = r23;
        r2 = r0.mExecutingActions;
        if (r2 == 0) goto L_0x000e;
    L_0x0006:
        r3 = new java.lang.IllegalStateException;
        r4 = "Recursive entry to executePendingTransactions";
        r3.<init>(r4);
        throw r3;
    L_0x000e:
        r5 = android.os.Looper.myLooper();
        r0 = r23;
        r6 = r0.mHost;
        r7 = r6.getHandler();
        r8 = r7.getLooper();
        if (r5 == r8) goto L_0x0028;
    L_0x0020:
        r3 = new java.lang.IllegalStateException;
        r4 = "Must be called from main thread of process";
        r3.<init>(r4);
        throw r3;
    L_0x0028:
        r2 = 0;
    L_0x0029:
        monitor-enter(r23);
        r0 = r23;
        r9 = r0.mPendingActions;	 Catch:{ Throwable -> 0x00df }
        if (r9 == 0) goto L_0x003a;
    L_0x0030:
        r0 = r23;
        r9 = r0.mPendingActions;	 Catch:{ Throwable -> 0x00df }
        r10 = r9.size();	 Catch:{ Throwable -> 0x00df }
        if (r10 != 0) goto L_0x006f;
    L_0x003a:
        monitor-exit(r23);	 Catch:{ Throwable -> 0x00df }
        r0 = r23;
        r11 = r0.mHavePendingDeferredStart;
        if (r11 == 0) goto L_0x00fc;
    L_0x0041:
        r11 = 0;
        r10 = 0;
    L_0x0043:
        r0 = r23;
        r9 = r0.mActive;
        r12 = r9.size();
        if (r10 >= r12) goto L_0x00ec;
    L_0x004d:
        r0 = r23;
        r9 = r0.mActive;
        r13 = r9.get(r10);
        r15 = r13;
        r15 = (android.support.v4.app.Fragment) r15;
        r14 = r15;
        if (r14 == 0) goto L_0x006c;
    L_0x005b:
        r0 = r14.mLoaderManager;
        r16 = r0;
        if (r16 == 0) goto L_0x006c;
    L_0x0061:
        r0 = r14.mLoaderManager;
        r16 = r0;
        r17 = r0.hasRunningLoaders();
        r0 = r17;
        r11 = r11 | r0;
    L_0x006c:
        r10 = r10 + 1;
        goto L_0x0043;
    L_0x006f:
        r0 = r23;
        r9 = r0.mPendingActions;	 Catch:{ Throwable -> 0x00df }
        r10 = r9.size();	 Catch:{ Throwable -> 0x00df }
        r0 = r23;
        r0 = r0.mTmpActions;	 Catch:{ Throwable -> 0x00df }
        r18 = r0;
        if (r18 == 0) goto L_0x008c;
    L_0x007f:
        r0 = r23;
        r0 = r0.mTmpActions;	 Catch:{ Throwable -> 0x00df }
        r18 = r0;
        goto L_0x0089;
    L_0x0086:
        goto L_0x0029;
    L_0x0089:
        r12 = r0.length;	 Catch:{ Throwable -> 0x00df }
        if (r12 >= r10) goto L_0x0094;
    L_0x008c:
        r0 = new java.lang.Runnable[r10];	 Catch:{ Throwable -> 0x00df }
        r18 = r0;
        r1 = r23;
        r1.mTmpActions = r0;	 Catch:{ Throwable -> 0x00df }
    L_0x0094:
        r0 = r23;
        r9 = r0.mPendingActions;	 Catch:{ Throwable -> 0x00df }
        r0 = r23;
        r0 = r0.mTmpActions;	 Catch:{ Throwable -> 0x00df }
        r18 = r0;
        r9.toArray(r0);	 Catch:{ Throwable -> 0x00df }
        r0 = r23;
        r9 = r0.mPendingActions;	 Catch:{ Throwable -> 0x00df }
        r9.clear();	 Catch:{ Throwable -> 0x00df }
        r0 = r23;
        r6 = r0.mHost;	 Catch:{ Throwable -> 0x00df }
        r7 = r6.getHandler();	 Catch:{ Throwable -> 0x00df }
        r0 = r23;
        r0 = r0.mExecCommit;	 Catch:{ Throwable -> 0x00df }
        r19 = r0;
        r7.removeCallbacks(r0);	 Catch:{ Throwable -> 0x00df }
        monitor-exit(r23);	 Catch:{ Throwable -> 0x00df }
        r20 = 1;
        r0 = r20;
        r1 = r23;
        r1.mExecutingActions = r0;
        r12 = 0;
    L_0x00c3:
        if (r12 >= r10) goto L_0x00e2;
    L_0x00c5:
        r0 = r23;
        r0 = r0.mTmpActions;
        r18 = r0;
        r19 = r18[r12];
        r0 = r19;
        r0.run();
        r0 = r23;
        r0 = r0.mTmpActions;
        r18 = r0;
        r21 = 0;
        r18[r12] = r21;
        r12 = r12 + 1;
        goto L_0x00c3;
    L_0x00df:
        r22 = move-exception;
        monitor-exit(r23);	 Catch:{ Throwable -> 0x00df }
        throw r22;
    L_0x00e2:
        r20 = 0;
        r0 = r20;
        r1 = r23;
        r1.mExecutingActions = r0;
        r2 = 1;
        goto L_0x0086;
    L_0x00ec:
        if (r11 != 0) goto L_0x00fd;
    L_0x00ee:
        r20 = 0;
        r0 = r20;
        r1 = r23;
        r1.mHavePendingDeferredStart = r0;
        r0 = r23;
        r0.startPendingDeferredFragments();
        return r2;
    L_0x00fc:
        return r2;
    L_0x00fd:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.execPendingActions():boolean");
    }

    void reportBackStackChanged() throws  {
        if (this.mBackStackChangeListeners != null) {
            for (int $i0 = 0; $i0 < this.mBackStackChangeListeners.size(); $i0++) {
                ((OnBackStackChangedListener) this.mBackStackChangeListeners.get($i0)).onBackStackChanged();
            }
        }
    }

    void addBackStackState(BackStackRecord $r1) throws  {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add($r1);
        reportBackStackChanged();
    }

    ArrayList<Fragment> retainNonConfig() throws  {
        ArrayList $r1 = null;
        if (this.mActive == null) {
            return null;
        }
        for (int $i0 = 0; $i0 < this.mActive.size(); $i0++) {
            Fragment $r4 = (Fragment) this.mActive.get($i0);
            if ($r4 != null && $r4.mRetainInstance) {
                if ($r1 == null) {
                    $r1 = new ArrayList();
                }
                $r1.add($r4);
                $r4.mRetaining = true;
                $r4.mTargetIndex = $r4.mTarget != null ? $r4.mTarget.mIndex : -1;
                if (DEBUG) {
                    Log.v(TAG, "retainNonConfig: keeping retained " + $r4);
                }
            }
        }
        return $r1;
    }

    void saveFragmentViewState(Fragment $r1) throws  {
        if ($r1.mInnerView != null) {
            if (this.mStateArray == null) {
                this.mStateArray = new SparseArray();
            } else {
                this.mStateArray.clear();
            }
            $r1.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                $r1.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    Bundle saveFragmentBasicState(Fragment $r1) throws  {
        Bundle $r2 = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        $r1.performSaveInstanceState(this.mStateBundle);
        if (!this.mStateBundle.isEmpty()) {
            $r2 = this.mStateBundle;
            this.mStateBundle = null;
        }
        if ($r1.mView != null) {
            saveFragmentViewState($r1);
        }
        if ($r1.mSavedViewState != null) {
            if ($r2 == null) {
                $r2 = new Bundle();
            }
            $r2.putSparseParcelableArray(VIEW_STATE_TAG, $r1.mSavedViewState);
        }
        if ($r1.mUserVisibleHint) {
            return $r2;
        }
        if ($r2 == null) {
            $r2 = new Bundle();
        }
        $r2.putBoolean(USER_VISIBLE_HINT_TAG, $r1.mUserVisibleHint);
        return $r2;
    }

    Parcelable saveAllState() throws  {
        execPendingActions();
        if (HONEYCOMB) {
            this.mStateSaved = true;
        }
        if (this.mActive == null) {
            return null;
        }
        if (this.mActive.size() <= 0) {
            return null;
        }
        int $i1;
        int $i0 = this.mActive.size();
        FragmentState[] $r1 = new FragmentState[$i0];
        boolean $z0 = false;
        for ($i1 = 0; $i1 < $i0; $i1++) {
            Fragment $r6 = (Fragment) this.mActive.get($i1);
            if ($r6 != null) {
                if ($r6.mIndex < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + $r6 + " has cleared index: " + $r6.mIndex));
                }
                $z0 = true;
                FragmentState fragmentState = new FragmentState($r6);
                $r1[$i1] = fragmentState;
                if ($r6.mState <= 0 || fragmentState.mSavedFragmentState != null) {
                    Bundle bundle = $r6.mSavedFragmentState;
                    Bundle $r10 = bundle;
                    fragmentState.mSavedFragmentState = bundle;
                } else {
                    fragmentState.mSavedFragmentState = saveFragmentBasicState($r6);
                    if ($r6.mTarget != null) {
                        Fragment $r11 = $r6.mTarget;
                        if ($r11.mIndex < 0) {
                            throwException(new IllegalStateException("Failure saving state: " + $r6 + " has target not in fragment manager: " + $r6.mTarget));
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, $r6.mTarget);
                        if ($r6.mTargetRequestCode != 0) {
                            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, $r6.mTargetRequestCode);
                        }
                    }
                }
                if (DEBUG) {
                    Log.v(TAG, "Saved state of " + $r6 + ": " + fragmentState.mSavedFragmentState);
                }
            }
        }
        if ($z0) {
            int[] $r12 = null;
            BackStackState[] $r13 = null;
            if (this.mAdded != null) {
                $i0 = this.mAdded.size();
                if ($i0 > 0) {
                    $r12 = new int[$i0];
                    for ($i1 = 0; $i1 < $i0; $i1++) {
                        $r12[$i1] = ((Fragment) this.mAdded.get($i1)).mIndex;
                        if ($r12[$i1] < 0) {
                            throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get($i1) + " has cleared index: " + $r12[$i1]));
                        }
                        if (DEBUG) {
                            Log.v(TAG, "saveAllState: adding fragment #" + $i1 + ": " + this.mAdded.get($i1));
                        }
                    }
                }
            }
            if (this.mBackStack != null) {
                $i0 = this.mBackStack.size();
                if ($i0 > 0) {
                    $r13 = new BackStackState[$i0];
                    for ($i1 = 0; $i1 < $i0; $i1++) {
                        $r13[$i1] = new BackStackState((BackStackRecord) this.mBackStack.get($i1));
                        if (DEBUG) {
                            Log.v(TAG, "saveAllState: adding back stack #" + $i1 + ": " + this.mBackStack.get($i1));
                        }
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.mActive = $r1;
            fragmentManagerState.mAdded = $r12;
            fragmentManagerState.mBackStack = $r13;
            return fragmentManagerState;
        } else if (!DEBUG) {
            return null;
        } else {
            Log.v(TAG, "saveAllState: no fragments!");
            return null;
        }
    }

    void restoreAllState(@Signature({"(", "Landroid/os/Parcelable;", "Ljava/util/List", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) Parcelable $r1, @Signature({"(", "Landroid/os/Parcelable;", "Ljava/util/List", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) List<Fragment> $r2) throws  {
        if ($r1 != null) {
            FragmentManagerState $r5 = (FragmentManagerState) $r1;
            if ($r5.mActive != null) {
                int $i0;
                Fragment $r8;
                FragmentState $r11;
                ArrayList $r17;
                if ($r2 != null) {
                    for ($i0 = 0; $i0 < $r2.size(); $i0++) {
                        $r8 = (Fragment) $r2.get($i0);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: re-attaching retained " + $r8);
                        }
                        $r11 = $r5.mActive[$r8.mIndex];
                        $r11.mInstance = $r8;
                        $r8.mSavedViewState = null;
                        $r8.mBackStackNesting = 0;
                        $r8.mInLayout = false;
                        $r8.mAdded = false;
                        $r8.mTarget = null;
                        if ($r11.mSavedFragmentState != null) {
                            Bundle $r12 = $r11.mSavedFragmentState;
                            FragmentHostCallback $r13 = this.mHost;
                            $r12.setClassLoader($r13.getContext().getClassLoader());
                            $r8.mSavedViewState = $r11.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            Bundle $r122 = $r11.mSavedFragmentState;
                            $r8.mSavedFragmentState = $r122;
                        }
                    }
                }
                this.mActive = new ArrayList($r5.mActive.length);
                if (this.mAvailIndices != null) {
                    $r17 = this.mAvailIndices;
                    $r17.clear();
                }
                for ($i0 = 0; $i0 < $r5.mActive.length; $i0++) {
                    $r11 = $r5.mActive[$i0];
                    if ($r11 != null) {
                        $r8 = $r11.instantiate(this.mHost, this.mParent);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: active #" + $i0 + ": " + $r8);
                        }
                        $r17 = this.mActive;
                        $r17.add($r8);
                        $r11.mInstance = null;
                    } else {
                        this.mActive.add(null);
                        if (this.mAvailIndices == null) {
                            this.mAvailIndices = new ArrayList();
                        }
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: avail #" + $i0);
                        }
                        this.mAvailIndices.add(Integer.valueOf($i0));
                    }
                }
                if ($r2 != null) {
                    for ($i0 = 0; $i0 < $r2.size(); $i0++) {
                        $r8 = (Fragment) $r2.get($i0);
                        if ($r8.mTargetIndex >= 0) {
                            int $i1 = $r8.mTargetIndex;
                            $r17 = this.mActive;
                            if ($i1 < $r17.size()) {
                                $r8.mTarget = (Fragment) this.mActive.get($r8.mTargetIndex);
                            } else {
                                Log.w(TAG, "Re-attaching retained fragment " + $r8 + " target no longer exists: " + $r8.mTargetIndex);
                                $r8.mTarget = null;
                            }
                        }
                    }
                }
                if ($r5.mAdded != null) {
                    int[] $r20 = $r5.mAdded;
                    this.mAdded = new ArrayList($r20.length);
                    $i0 = 0;
                    while (true) {
                        $r20 = $r5.mAdded;
                        if ($i0 >= $r20.length) {
                            break;
                        }
                        $r8 = (Fragment) this.mActive.get($r5.mAdded[$i0]);
                        if ($r8 == null) {
                            throwException(new IllegalStateException("No instantiated fragment for index #" + $r5.mAdded[$i0]));
                        }
                        $r8.mAdded = true;
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: added #" + $i0 + ": " + $r8);
                        }
                        $r17 = this.mAdded;
                        if ($r17.contains($r8)) {
                            throw new IllegalStateException("Already added!");
                        }
                        $r17 = this.mAdded;
                        $r17.add($r8);
                        $i0++;
                    }
                } else {
                    this.mAdded = null;
                }
                if ($r5.mBackStack != null) {
                    BackStackState[] $r22 = $r5.mBackStack;
                    this.mBackStack = new ArrayList($r22.length);
                    $i0 = 0;
                    while (true) {
                        $r22 = $r5.mBackStack;
                        if ($i0 < $r22.length) {
                            BackStackRecord $r24 = $r5.mBackStack[$i0].instantiate(this);
                            if (DEBUG) {
                                Log.v(TAG, "restoreAllState: back stack #" + $i0 + " (index " + $r24.mIndex + "): " + $r24);
                                PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
                                $r24.dump("  ", printWriter, false);
                            }
                            $r17 = this.mBackStack;
                            ArrayList $r172 = $r17;
                            $r17.add($r24);
                            if ($r24.mIndex >= 0) {
                                setBackStackIndex($r24.mIndex, $r24);
                            }
                            $i0++;
                        } else {
                            return;
                        }
                    }
                }
                this.mBackStack = null;
            }
        }
    }

    public void attachController(FragmentHostCallback $r1, FragmentContainer $r2, Fragment $r3) throws  {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = $r1;
        this.mContainer = $r2;
        this.mParent = $r3;
    }

    public void noteStateNotSaved() throws  {
        this.mStateSaved = false;
    }

    public void dispatchCreate() throws  {
        this.mStateSaved = false;
        moveToState(1, false);
    }

    public void dispatchActivityCreated() throws  {
        this.mStateSaved = false;
        moveToState(2, false);
    }

    public void dispatchStart() throws  {
        this.mStateSaved = false;
        moveToState(4, false);
    }

    public void dispatchResume() throws  {
        this.mStateSaved = false;
        moveToState(5, false);
    }

    public void dispatchPause() throws  {
        moveToState(4, false);
    }

    public void dispatchStop() throws  {
        this.mStateSaved = true;
        moveToState(3, false);
    }

    public void dispatchReallyStop() throws  {
        moveToState(2, false);
    }

    public void dispatchDestroyView() throws  {
        moveToState(1, false);
    }

    public void dispatchDestroy() throws  {
        this.mDestroyed = true;
        execPendingActions();
        moveToState(0, false);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchConfigurationChanged(Configuration $r1) throws  {
        if (this.mAdded != null) {
            for (int $i0 = 0; $i0 < this.mAdded.size(); $i0++) {
                Fragment $r4 = (Fragment) this.mAdded.get($i0);
                if ($r4 != null) {
                    $r4.performConfigurationChanged($r1);
                }
            }
        }
    }

    public void dispatchLowMemory() throws  {
        if (this.mAdded != null) {
            for (int $i0 = 0; $i0 < this.mAdded.size(); $i0++) {
                Fragment $r3 = (Fragment) this.mAdded.get($i0);
                if ($r3 != null) {
                    $r3.performLowMemory();
                }
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu $r1, MenuInflater $r2) throws  {
        int $i0;
        Fragment $r6;
        boolean $z0 = false;
        ArrayList $r3 = null;
        if (this.mAdded != null) {
            for ($i0 = 0; $i0 < this.mAdded.size(); $i0++) {
                $r6 = (Fragment) this.mAdded.get($i0);
                if ($r6 != null && $r6.performCreateOptionsMenu($r1, $r2)) {
                    $z0 = true;
                    if ($r3 == null) {
                        $r3 = new ArrayList();
                    }
                    $r3.add($r6);
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for ($i0 = 0; $i0 < this.mCreatedMenus.size(); $i0++) {
                $r6 = (Fragment) this.mCreatedMenus.get($i0);
                if ($r3 == null || !$r3.contains($r6)) {
                    $r6.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = $r3;
        return $z0;
    }

    public boolean dispatchPrepareOptionsMenu(Menu $r1) throws  {
        boolean $z0 = false;
        if (this.mAdded == null) {
            return false;
        }
        for (int $i0 = 0; $i0 < this.mAdded.size(); $i0++) {
            Fragment $r4 = (Fragment) this.mAdded.get($i0);
            if ($r4 != null && $r4.performPrepareOptionsMenu($r1)) {
                $z0 = true;
            }
        }
        return $z0;
    }

    public boolean dispatchOptionsItemSelected(MenuItem $r1) throws  {
        if (this.mAdded != null) {
            for (int $i0 = 0; $i0 < this.mAdded.size(); $i0++) {
                Fragment $r4 = (Fragment) this.mAdded.get($i0);
                if ($r4 != null && $r4.performOptionsItemSelected($r1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem $r1) throws  {
        if (this.mAdded != null) {
            for (int $i0 = 0; $i0 < this.mAdded.size(); $i0++) {
                Fragment $r4 = (Fragment) this.mAdded.get($i0);
                if ($r4 != null && $r4.performContextItemSelected($r1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu $r1) throws  {
        if (this.mAdded != null) {
            for (int $i0 = 0; $i0 < this.mAdded.size(); $i0++) {
                Fragment $r4 = (Fragment) this.mAdded.get($i0);
                if ($r4 != null) {
                    $r4.performOptionsMenuClosed($r1);
                }
            }
        }
    }

    public static int reverseTransit(int $i0) throws  {
        switch ($i0) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return 8194;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
            case 8194:
                return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
            default:
                return 0;
        }
    }

    public static int transitToStyleIndex(int $i0, boolean $z0) throws  {
        switch ($i0) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return $z0 ? 1 : 2;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return $z0 ? 5 : 6;
            case 8194:
                return $z0 ? 3 : 4;
            default:
                return -1;
        }
    }

    LayoutInflaterFactory getLayoutInflaterFactory() throws  {
        return this;
    }
}
