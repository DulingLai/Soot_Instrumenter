package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class Fragment implements ComponentCallbacks, OnCreateContextMenuListener {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    static final Object USE_DEFAULT_TRANSITION = new Object();
    private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap();
    boolean mAdded;
    Boolean mAllowEnterTransitionOverlap;
    Boolean mAllowReturnTransitionOverlap;
    View mAnimatingAway;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    boolean mCheckedForLoaderManager;
    FragmentManagerImpl mChildFragmentManager;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    Object mEnterTransition = null;
    SharedElementCallback mEnterTransitionCallback = null;
    Object mExitTransition = null;
    SharedElementCallback mExitTransitionCallback = null;
    int mFragmentId;
    FragmentManagerImpl mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    FragmentHostCallback mHost;
    boolean mInLayout;
    int mIndex = -1;
    View mInnerView;
    LoaderManagerImpl mLoaderManager;
    boolean mLoadersStarted;
    boolean mMenuVisible = true;
    int mNextAnim;
    Fragment mParentFragment;
    Object mReenterTransition = USE_DEFAULT_TRANSITION;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Object mReturnTransition = USE_DEFAULT_TRANSITION;
    Bundle mSavedFragmentState;
    SparseArray<Parcelable> mSavedViewState;
    Object mSharedElementEnterTransition = null;
    Object mSharedElementReturnTransition = USE_DEFAULT_TRANSITION;
    int mState = 0;
    int mStateAfterAnimating;
    String mTag;
    Fragment mTarget;
    int mTargetIndex = -1;
    int mTargetRequestCode;
    boolean mUserVisibleHint = true;
    View mView;
    String mWho;

    class C00161 extends FragmentContainer {
        C00161() throws  {
        }

        @Nullable
        public View onFindViewById(int $i0) throws  {
            if (Fragment.this.mView != null) {
                return Fragment.this.mView.findViewById($i0);
            }
            throw new IllegalStateException("Fragment does not have a view");
        }

        public boolean onHasView() throws  {
            return Fragment.this.mView != null;
        }
    }

    public static class InstantiationException extends RuntimeException {
        public InstantiationException(String $r1, Exception $r2) throws  {
            super($r1, $r2);
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C00171();
        final Bundle mState;

        static class C00171 implements Creator<SavedState> {
            C00171() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1, null);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        SavedState(Bundle $r1) throws  {
            this.mState = $r1;
        }

        SavedState(Parcel $r1, ClassLoader $r2) throws  {
            this.mState = $r1.readBundle();
            if ($r2 != null && this.mState != null) {
                this.mState.setClassLoader($r2);
            }
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            $r1.writeBundle(this.mState);
        }
    }

    public static android.support.v4.app.Fragment instantiate(android.content.Context r15, java.lang.String r16, @android.support.annotation.Nullable android.os.Bundle r17) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0020 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = sClassMap;
        r0 = r16;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r2 = r1.get(r0);	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r4 = r2;
        r4 = (java.lang.Class) r4;
        r3 = r4;
        if (r3 != 0) goto L_0x0020;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
    L_0x000e:
        r5 = r15.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r0 = r16;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r6 = r5.loadClass(r0);	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r3 = r6;
        r1 = sClassMap;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r0 = r16;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r1.put(r0, r6);	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
    L_0x0020:
        r2 = r3.newInstance();	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r8 = r2;
        r8 = (android.support.v4.app.Fragment) r8;
        r7 = r8;
        if (r17 == 0) goto L_0x00ba;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
    L_0x002a:
        r3 = r7.getClass();	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r5 = r3.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r0 = r17;	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r0.setClassLoader(r5);	 Catch:{ ClassNotFoundException -> 0x003c, InstantiationException -> 0x0066, IllegalAccessException -> 0x0090 }
        r0 = r17;
        r7.mArguments = r0;
        return r7;
    L_0x003c:
        r9 = move-exception;
        r10 = new android.support.v4.app.Fragment$InstantiationException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "Unable to instantiate fragment ";
        r11 = r11.append(r12);
        r0 = r16;
        r11 = r11.append(r0);
        r12 = ": make sure class name exists, is public, and has an";
        r11 = r11.append(r12);
        r12 = " empty constructor that is public";
        r11 = r11.append(r12);
        r16 = r11.toString();
        r0 = r16;
        r10.<init>(r0, r9);
        throw r10;
    L_0x0066:
        r13 = move-exception;
        r10 = new android.support.v4.app.Fragment$InstantiationException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "Unable to instantiate fragment ";
        r11 = r11.append(r12);
        r0 = r16;
        r11 = r11.append(r0);
        r12 = ": make sure class name exists, is public, and has an";
        r11 = r11.append(r12);
        r12 = " empty constructor that is public";
        r11 = r11.append(r12);
        r16 = r11.toString();
        r0 = r16;
        r10.<init>(r0, r13);
        throw r10;
    L_0x0090:
        r14 = move-exception;
        r10 = new android.support.v4.app.Fragment$InstantiationException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "Unable to instantiate fragment ";
        r11 = r11.append(r12);
        r0 = r16;
        r11 = r11.append(r0);
        r12 = ": make sure class name exists, is public, and has an";
        r11 = r11.append(r12);
        r12 = " empty constructor that is public";
        r11 = r11.append(r12);
        r16 = r11.toString();
        r0 = r16;
        r10.<init>(r0, r14);
        throw r10;
    L_0x00ba:
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.Fragment.instantiate(android.content.Context, java.lang.String, android.os.Bundle):android.support.v4.app.Fragment");
    }

    static boolean isSupportFragmentClass(android.content.Context r9, java.lang.String r10) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001a in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = sClassMap;
        r1 = r0.get(r10);	 Catch:{ ClassNotFoundException -> 0x0021 }
        r3 = r1;
        r3 = (java.lang.Class) r3;
        r2 = r3;
        if (r2 != 0) goto L_0x001a;	 Catch:{ ClassNotFoundException -> 0x0021 }
    L_0x000c:
        r4 = r9.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x0021 }
        r5 = r4.loadClass(r10);	 Catch:{ ClassNotFoundException -> 0x0021 }
        r2 = r5;
        r0 = sClassMap;	 Catch:{ ClassNotFoundException -> 0x0021 }
        r0.put(r10, r5);	 Catch:{ ClassNotFoundException -> 0x0021 }
    L_0x001a:
        r5 = android.support.v4.app.Fragment.class;	 Catch:{ ClassNotFoundException -> 0x0021 }
        r6 = r5.isAssignableFrom(r2);	 Catch:{ ClassNotFoundException -> 0x0021 }
        return r6;
    L_0x0021:
        r7 = move-exception;
        r8 = 0;
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.Fragment.isSupportFragmentClass(android.content.Context, java.lang.String):boolean");
    }

    public boolean onContextItemSelected(MenuItem item) throws  {
        return false;
    }

    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) throws  {
        return null;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) throws  {
        return null;
    }

    public boolean onOptionsItemSelected(MenuItem item) throws  {
        return false;
    }

    public static Fragment instantiate(Context $r0, String $r1) throws  {
        return instantiate($r0, $r1, null);
    }

    final void restoreViewState(Bundle $r1) throws  {
        if (this.mSavedViewState != null) {
            this.mInnerView.restoreHierarchyState(this.mSavedViewState);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        onViewStateRestored($r1);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
        }
    }

    final void setIndex(int $i0, Fragment $r1) throws  {
        this.mIndex = $i0;
        if ($r1 != null) {
            this.mWho = $r1.mWho + ":" + this.mIndex;
        } else {
            this.mWho = "android:fragment:" + this.mIndex;
        }
    }

    final boolean isInBackStack() throws  {
        return this.mBackStackNesting > 0;
    }

    public final boolean equals(Object $r1) throws  {
        return super.equals($r1);
    }

    public final int hashCode() throws  {
        return super.hashCode();
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder(128);
        DebugUtils.buildShortClassTag(this, $r1);
        if (this.mIndex >= 0) {
            $r1.append(" #");
            $r1.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            $r1.append(" id=0x");
            $r1.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            $r1.append(" ");
            $r1.append(this.mTag);
        }
        $r1.append('}');
        return $r1.toString();
    }

    public final int getId() throws  {
        return this.mFragmentId;
    }

    public final String getTag() throws  {
        return this.mTag;
    }

    public void setArguments(Bundle $r1) throws  {
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        this.mArguments = $r1;
    }

    public final Bundle getArguments() throws  {
        return this.mArguments;
    }

    public void setInitialSavedState(SavedState $r1) throws  {
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        Bundle $r3;
        if ($r1 == null || $r1.mState == null) {
            $r3 = null;
        } else {
            $r3 = $r1.mState;
        }
        this.mSavedFragmentState = $r3;
    }

    public void setTargetFragment(Fragment $r1, int $i0) throws  {
        this.mTarget = $r1;
        this.mTargetRequestCode = $i0;
    }

    public final Fragment getTargetFragment() throws  {
        return this.mTarget;
    }

    public final int getTargetRequestCode() throws  {
        return this.mTargetRequestCode;
    }

    public Context getContext() throws  {
        return this.mHost == null ? null : this.mHost.getContext();
    }

    public final FragmentActivity getActivity() throws  {
        return this.mHost == null ? null : (FragmentActivity) this.mHost.getActivity();
    }

    public final Object getHost() throws  {
        return this.mHost == null ? null : this.mHost.onGetHost();
    }

    public final Resources getResources() throws  {
        if (this.mHost != null) {
            return this.mHost.getContext().getResources();
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public final CharSequence getText(@StringRes int $i0) throws  {
        return getResources().getText($i0);
    }

    public final String getString(@StringRes int $i0) throws  {
        return getResources().getString($i0);
    }

    public final String getString(@StringRes int $i0, Object... $r1) throws  {
        return getResources().getString($i0, $r1);
    }

    public final FragmentManager getFragmentManager() throws  {
        return this.mFragmentManager;
    }

    public final FragmentManager getChildFragmentManager() throws  {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            if (this.mState >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (this.mState >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (this.mState >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (this.mState >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        return this.mChildFragmentManager;
    }

    public final Fragment getParentFragment() throws  {
        return this.mParentFragment;
    }

    public final boolean isAdded() throws  {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isDetached() throws  {
        return this.mDetached;
    }

    public final boolean isRemoving() throws  {
        return this.mRemoving;
    }

    public final boolean isInLayout() throws  {
        return this.mInLayout;
    }

    public final boolean isResumed() throws  {
        return this.mState >= 5;
    }

    public final boolean isVisible() throws  {
        return (!isAdded() || isHidden() || this.mView == null || this.mView.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true;
    }

    public final boolean isHidden() throws  {
        return this.mHidden;
    }

    public final boolean hasOptionsMenu() throws  {
        return this.mHasMenu;
    }

    public final boolean isMenuVisible() throws  {
        return this.mMenuVisible;
    }

    public void onHiddenChanged(boolean hidden) throws  {
    }

    public void setRetainInstance(boolean $z0) throws  {
        if (!$z0 || this.mParentFragment == null) {
            this.mRetainInstance = $z0;
            return;
        }
        throw new IllegalStateException("Can't retain fragements that are nested in other fragments");
    }

    public final boolean getRetainInstance() throws  {
        return this.mRetainInstance;
    }

    public void setHasOptionsMenu(boolean $z0) throws  {
        if (this.mHasMenu != $z0) {
            this.mHasMenu = $z0;
            if (isAdded() && !isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setMenuVisibility(boolean $z0) throws  {
        if (this.mMenuVisible != $z0) {
            this.mMenuVisible = $z0;
            if (this.mHasMenu && isAdded() && !isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setUserVisibleHint(boolean $z0) throws  {
        if (!this.mUserVisibleHint && $z0 && this.mState < 4) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = $z0;
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        this.mDeferStart = $z0;
    }

    public boolean getUserVisibleHint() throws  {
        return this.mUserVisibleHint;
    }

    public LoaderManager getLoaderManager() throws  {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    public void startActivity(Intent $r1) throws  {
        startActivity($r1, null);
    }

    public void startActivity(Intent $r1, @Nullable Bundle $r2) throws  {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onStartActivityFromFragment(this, $r1, -1, $r2);
    }

    public void startActivityForResult(Intent $r1, int $i0) throws  {
        startActivityForResult($r1, $i0, null);
    }

    public void startActivityForResult(Intent $r1, int $i0, @Nullable Bundle $r2) throws  {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onStartActivityFromFragment(this, $r1, $i0, $r2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) throws  {
    }

    public final void requestPermissions(@NonNull String[] $r1, int $i0) throws  {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.onRequestPermissionsFromFragment(this, $r1, $i0);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) throws  {
    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String $r1) throws  {
        if (this.mHost != null) {
            return this.mHost.onShouldShowRequestPermissionRationale($r1);
        }
        return false;
    }

    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) throws  {
        LayoutInflater $r3 = this.mHost.onGetLayoutInflater();
        getChildFragmentManager();
        LayoutInflaterCompat.setFactory($r3, this.mChildFragmentManager.getLayoutInflaterFactory());
        return $r3;
    }

    public void onInflate(Context context, AttributeSet $r2, Bundle $r3) throws  {
        this.mCalled = true;
        Activity $r4 = this.mHost == null ? null : this.mHost.getActivity();
        if ($r4 != null) {
            this.mCalled = false;
            onInflate($r4, $r2, $r3);
        }
    }

    @Deprecated
    public void onInflate(@Deprecated Activity activity, @Deprecated AttributeSet attrs, @Deprecated Bundle savedInstanceState) throws  {
        this.mCalled = true;
    }

    public void onAttach(Context context) throws  {
        this.mCalled = true;
        Activity $r2 = this.mHost == null ? null : this.mHost.getActivity();
        if ($r2 != null) {
            this.mCalled = false;
            onAttach($r2);
        }
    }

    @Deprecated
    public void onAttach(@Deprecated Activity activity) throws  {
        this.mCalled = true;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) throws  {
        this.mCalled = true;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) throws  {
    }

    @Nullable
    public View getView() throws  {
        return this.mView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws  {
        this.mCalled = true;
    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState) throws  {
        this.mCalled = true;
    }

    public void onStart() throws  {
        this.mCalled = true;
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doStart();
            }
        }
    }

    public void onResume() throws  {
        this.mCalled = true;
    }

    public void onSaveInstanceState(Bundle outState) throws  {
    }

    public void onConfigurationChanged(Configuration newConfig) throws  {
        this.mCalled = true;
    }

    public void onPause() throws  {
        this.mCalled = true;
    }

    public void onStop() throws  {
        this.mCalled = true;
    }

    public void onLowMemory() throws  {
        this.mCalled = true;
    }

    public void onDestroyView() throws  {
        this.mCalled = true;
    }

    public void onDestroy() throws  {
        this.mCalled = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doDestroy();
        }
    }

    void initState() throws  {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
        this.mLoaderManager = null;
        this.mLoadersStarted = false;
        this.mCheckedForLoaderManager = false;
    }

    public void onDetach() throws  {
        this.mCalled = true;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) throws  {
    }

    public void onPrepareOptionsMenu(Menu menu) throws  {
    }

    public void onDestroyOptionsMenu() throws  {
    }

    public void onOptionsMenuClosed(Menu menu) throws  {
    }

    public void onCreateContextMenu(ContextMenu $r1, View $r2, ContextMenuInfo $r3) throws  {
        getActivity().onCreateContextMenu($r1, $r2, $r3);
    }

    public void registerForContextMenu(View $r1) throws  {
        $r1.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(View $r1) throws  {
        $r1.setOnCreateContextMenuListener(null);
    }

    public void setEnterSharedElementCallback(SharedElementCallback $r1) throws  {
        this.mEnterTransitionCallback = $r1;
    }

    public void setExitSharedElementCallback(SharedElementCallback $r1) throws  {
        this.mExitTransitionCallback = $r1;
    }

    public void setEnterTransition(Object $r1) throws  {
        this.mEnterTransition = $r1;
    }

    public Object getEnterTransition() throws  {
        return this.mEnterTransition;
    }

    public void setReturnTransition(Object $r1) throws  {
        this.mReturnTransition = $r1;
    }

    public Object getReturnTransition() throws  {
        return this.mReturnTransition == USE_DEFAULT_TRANSITION ? getEnterTransition() : this.mReturnTransition;
    }

    public void setExitTransition(Object $r1) throws  {
        this.mExitTransition = $r1;
    }

    public Object getExitTransition() throws  {
        return this.mExitTransition;
    }

    public void setReenterTransition(Object $r1) throws  {
        this.mReenterTransition = $r1;
    }

    public Object getReenterTransition() throws  {
        return this.mReenterTransition == USE_DEFAULT_TRANSITION ? getExitTransition() : this.mReenterTransition;
    }

    public void setSharedElementEnterTransition(Object $r1) throws  {
        this.mSharedElementEnterTransition = $r1;
    }

    public Object getSharedElementEnterTransition() throws  {
        return this.mSharedElementEnterTransition;
    }

    public void setSharedElementReturnTransition(Object $r1) throws  {
        this.mSharedElementReturnTransition = $r1;
    }

    public Object getSharedElementReturnTransition() throws  {
        return this.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : this.mSharedElementReturnTransition;
    }

    public void setAllowEnterTransitionOverlap(boolean $z0) throws  {
        this.mAllowEnterTransitionOverlap = Boolean.valueOf($z0);
    }

    public boolean getAllowEnterTransitionOverlap() throws  {
        return this.mAllowEnterTransitionOverlap == null ? true : this.mAllowEnterTransitionOverlap.booleanValue();
    }

    public void setAllowReturnTransitionOverlap(boolean $z0) throws  {
        this.mAllowReturnTransitionOverlap = Boolean.valueOf($z0);
    }

    public boolean getAllowReturnTransitionOverlap() throws  {
        return this.mAllowReturnTransitionOverlap == null ? true : this.mAllowReturnTransitionOverlap.booleanValue();
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        $r3.print($r1);
        $r3.print("mFragmentId=#");
        $r3.print(Integer.toHexString(this.mFragmentId));
        $r3.print(" mContainerId=#");
        $r3.print(Integer.toHexString(this.mContainerId));
        $r3.print(" mTag=");
        $r3.println(this.mTag);
        $r3.print($r1);
        $r3.print("mState=");
        $r3.print(this.mState);
        $r3.print(" mIndex=");
        $r3.print(this.mIndex);
        $r3.print(" mWho=");
        $r3.print(this.mWho);
        $r3.print(" mBackStackNesting=");
        $r3.println(this.mBackStackNesting);
        $r3.print($r1);
        $r3.print("mAdded=");
        $r3.print(this.mAdded);
        $r3.print(" mRemoving=");
        $r3.print(this.mRemoving);
        $r3.print(" mFromLayout=");
        $r3.print(this.mFromLayout);
        $r3.print(" mInLayout=");
        $r3.println(this.mInLayout);
        $r3.print($r1);
        $r3.print("mHidden=");
        $r3.print(this.mHidden);
        $r3.print(" mDetached=");
        $r3.print(this.mDetached);
        $r3.print(" mMenuVisible=");
        $r3.print(this.mMenuVisible);
        $r3.print(" mHasMenu=");
        $r3.println(this.mHasMenu);
        $r3.print($r1);
        $r3.print("mRetainInstance=");
        $r3.print(this.mRetainInstance);
        $r3.print(" mRetaining=");
        $r3.print(this.mRetaining);
        $r3.print(" mUserVisibleHint=");
        $r3.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            $r3.print($r1);
            $r3.print("mFragmentManager=");
            $r3.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            $r3.print($r1);
            $r3.print("mHost=");
            $r3.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            $r3.print($r1);
            $r3.print("mParentFragment=");
            $r3.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            $r3.print($r1);
            $r3.print("mArguments=");
            $r3.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            $r3.print($r1);
            $r3.print("mSavedFragmentState=");
            $r3.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            $r3.print($r1);
            $r3.print("mSavedViewState=");
            $r3.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            $r3.print($r1);
            $r3.print("mTarget=");
            $r3.print(this.mTarget);
            $r3.print(" mTargetRequestCode=");
            $r3.println(this.mTargetRequestCode);
        }
        if (this.mNextAnim != 0) {
            $r3.print($r1);
            $r3.print("mNextAnim=");
            $r3.println(this.mNextAnim);
        }
        if (this.mContainer != null) {
            $r3.print($r1);
            $r3.print("mContainer=");
            $r3.println(this.mContainer);
        }
        if (this.mView != null) {
            $r3.print($r1);
            $r3.print("mView=");
            $r3.println(this.mView);
        }
        if (this.mInnerView != null) {
            $r3.print($r1);
            $r3.print("mInnerView=");
            $r3.println(this.mView);
        }
        if (this.mAnimatingAway != null) {
            $r3.print($r1);
            $r3.print("mAnimatingAway=");
            $r3.println(this.mAnimatingAway);
            $r3.print($r1);
            $r3.print("mStateAfterAnimating=");
            $r3.println(this.mStateAfterAnimating);
        }
        if (this.mLoaderManager != null) {
            $r3.print($r1);
            $r3.println("Loader Manager:");
            this.mLoaderManager.dump($r1 + "  ", $r2, $r3, $r4);
        }
        if (this.mChildFragmentManager != null) {
            $r3.print($r1);
            $r3.println("Child " + this.mChildFragmentManager + ":");
            this.mChildFragmentManager.dump($r1 + "  ", $r2, $r3, $r4);
        }
    }

    Fragment findFragmentByWho(String $r0) throws  {
        if ($r0.equals(this.mWho)) {
            return this;
        }
        return this.mChildFragmentManager != null ? this.mChildFragmentManager.findFragmentByWho($r0) : null;
    }

    void instantiateChildFragmentManager() throws  {
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mChildFragmentManager.attachController(this.mHost, new C00161(), this);
    }

    void performCreate(Bundle $r1) throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 1;
        this.mCalled = false;
        onCreate($r1);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
        } else if ($r1 != null) {
            Parcelable $r6 = $r1.getParcelable("android:support:fragments");
            if ($r6 != null) {
                if (this.mChildFragmentManager == null) {
                    instantiateChildFragmentManager();
                }
                this.mChildFragmentManager.restoreAllState($r6, null);
                this.mChildFragmentManager.dispatchCreate();
            }
        }
    }

    View performCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle $r3) throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        return onCreateView($r1, $r2, $r3);
    }

    void performActivityCreated(Bundle $r1) throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 2;
        this.mCalled = false;
        onActivityCreated($r1);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
        } else if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchActivityCreated();
        }
    }

    void performStart() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        onStart();
        if (this.mCalled) {
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.dispatchStart();
            }
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doReportStart();
                return;
            }
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
    }

    void performResume() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        onResume();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
        } else if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchResume();
            this.mChildFragmentManager.execPendingActions();
        }
    }

    void performConfigurationChanged(Configuration $r1) throws  {
        onConfigurationChanged($r1);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchConfigurationChanged($r1);
        }
    }

    void performLowMemory() throws  {
        onLowMemory();
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchLowMemory();
        }
    }

    boolean performCreateOptionsMenu(Menu $r1, MenuInflater $r2) throws  {
        boolean $z1 = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            $z1 = true;
            onCreateOptionsMenu($r1, $r2);
        }
        if (this.mChildFragmentManager != null) {
            return $z1 | this.mChildFragmentManager.dispatchCreateOptionsMenu($r1, $r2);
        }
        return $z1;
    }

    boolean performPrepareOptionsMenu(Menu $r1) throws  {
        boolean $z1 = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            $z1 = true;
            onPrepareOptionsMenu($r1);
        }
        if (this.mChildFragmentManager != null) {
            return $z1 | this.mChildFragmentManager.dispatchPrepareOptionsMenu($r1);
        }
        return $z1;
    }

    boolean performOptionsItemSelected(MenuItem $r1) throws  {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected($r1)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected($r1)) {
                return true;
            }
        }
        return false;
    }

    boolean performContextItemSelected(MenuItem $r1) throws  {
        if (!this.mHidden) {
            if (onContextItemSelected($r1)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected($r1)) {
                return true;
            }
        }
        return false;
    }

    void performOptionsMenuClosed(Menu $r1) throws  {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                onOptionsMenuClosed($r1);
            }
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.dispatchOptionsMenuClosed($r1);
            }
        }
    }

    void performSaveInstanceState(Bundle $r1) throws  {
        onSaveInstanceState($r1);
        if (this.mChildFragmentManager != null) {
            Parcelable $r2 = this.mChildFragmentManager.saveAllState();
            if ($r2 != null) {
                $r1.putParcelable("android:support:fragments", $r2);
            }
        }
    }

    void performPause() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        onPause();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
        }
    }

    void performStop() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStop();
        }
        this.mState = 3;
        this.mCalled = false;
        onStop();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
        }
    }

    void performReallyStop() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchReallyStop();
        }
        this.mState = 2;
        if (this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager == null) {
                return;
            }
            if (this.mHost.getRetainLoaders()) {
                this.mLoaderManager.doRetain();
            } else {
                this.mLoaderManager.doStop();
            }
        }
    }

    void performDestroyView() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        onDestroyView();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
        } else if (this.mLoaderManager != null) {
            this.mLoaderManager.doReportNextStart();
        }
    }

    void performDestroy() throws  {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroy();
        }
        this.mState = 0;
        this.mCalled = false;
        onDestroy();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
        }
    }
}
