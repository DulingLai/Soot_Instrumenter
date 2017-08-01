package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCanceledListener;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* compiled from: LoaderManager */
class LoaderManagerImpl extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    boolean mCreatingLoader;
    private FragmentHostCallback mHost;
    final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat();
    final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final String mWho;

    /* compiled from: LoaderManager */
    final class LoaderInfo implements OnLoadCompleteListener<Object>, OnLoadCanceledListener<Object> {
        final Bundle mArgs;
        LoaderCallbacks<Object> mCallbacks;
        Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        Loader<Object> mLoader;
        LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;

        public LoaderInfo(@Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)V"}) int $i0, @Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)V"}) Bundle $r2, @Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)V"}) LoaderCallbacks<Object> $r3) throws  {
            this.mId = $i0;
            this.mArgs = $r2;
            this.mCallbacks = $r3;
        }

        void start() throws  {
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
            } else if (!this.mStarted) {
                this.mStarted = true;
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Starting: " + this);
                }
                if (this.mLoader == null && this.mCallbacks != null) {
                    this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
                }
                if (this.mLoader == null) {
                    return;
                }
                if (!this.mLoader.getClass().isMemberClass() || Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                    if (!this.mListenerRegistered) {
                        this.mLoader.registerListener(this.mId, this);
                        this.mLoader.registerOnLoadCanceledListener(this);
                        this.mListenerRegistered = true;
                    }
                    this.mLoader.startLoading();
                    return;
                }
                throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
            }
        }

        void retain() throws  {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Retaining: " + this);
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        void finishRetain() throws  {
            if (this.mRetaining) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Finished Retaining: " + this);
                }
                this.mRetaining = false;
                if (!(this.mStarted == this.mRetainingStarted || this.mStarted)) {
                    stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        void reportStart() throws  {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData) {
                    callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        void stop() throws  {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Stopping: " + this);
            }
            this.mStarted = false;
            if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                this.mLoader.unregisterListener(this);
                this.mLoader.unregisterOnLoadCanceledListener(this);
                this.mLoader.stopLoading();
            }
        }

        void cancel() throws  {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Canceling: " + this);
            }
            if (this.mStarted && this.mLoader != null && this.mListenerRegistered && !this.mLoader.cancelLoad()) {
                onLoadCanceled(this.mLoader);
            }
        }

        void destroy() throws  {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Destroying: " + this);
            }
            this.mDestroyed = true;
            boolean $z0 = this.mDeliveredData;
            this.mDeliveredData = false;
            if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && $z0) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Reseting: " + this);
                }
                String $r2 = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    $r2 = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
                }
                try {
                    this.mCallbacks.onLoaderReset(this.mLoader);
                } finally {
                    if (LoaderManagerImpl.this.mHost != null) {
                        LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = $r2;
                    }
                }
            }
            this.mCallbacks = null;
            this.mData = null;
            this.mHaveData = false;
            if (this.mLoader != null) {
                if (this.mListenerRegistered) {
                    this.mListenerRegistered = false;
                    this.mLoader.unregisterListener(this);
                    this.mLoader.unregisterOnLoadCanceledListener(this);
                }
                this.mLoader.reset();
            }
            if (this.mPendingLoader != null) {
                this.mPendingLoader.destroy();
            }
        }

        public void onLoadCanceled(@Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Object;", ">;)V"}) Loader<Object> loader) throws  {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "onLoadCanceled: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- destroyed");
                }
            } else if (LoaderManagerImpl.this.mLoaders.get(this.mId) == this) {
                LoaderInfo $r2 = this.mPendingLoader;
                if ($r2 != null) {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + $r2);
                    }
                    this.mPendingLoader = null;
                    LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                    destroy();
                    LoaderManagerImpl.this.installLoader($r2);
                }
            } else if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- not active");
            }
        }

        public void onLoadComplete(@Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Loader<Object> $r1, @Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Object $r2) throws  {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "onLoadComplete: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
                }
            } else if (LoaderManagerImpl.this.mLoaders.get(this.mId) == this) {
                LoaderInfo $r3 = this.mPendingLoader;
                if ($r3 != null) {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + $r3);
                    }
                    this.mPendingLoader = null;
                    LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                    destroy();
                    LoaderManagerImpl.this.installLoader($r3);
                    return;
                }
                if (!(this.mData == $r2 && this.mHaveData)) {
                    this.mData = $r2;
                    this.mHaveData = true;
                    if (this.mStarted) {
                        callOnLoadFinished($r1, $r2);
                    }
                }
                $r3 = (LoaderInfo) LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
                if (!($r3 == null || $r3 == this)) {
                    $r3.mDeliveredData = false;
                    $r3.destroy();
                    LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
                }
                if (LoaderManagerImpl.this.mHost != null && !LoaderManagerImpl.this.hasRunningLoaders()) {
                    FragmentManagerImpl $r10 = LoaderManagerImpl.this.mHost.mFragmentManager;
                    $r10.startPendingDeferredFragments();
                }
            } else if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
            }
        }

        void callOnLoadFinished(@Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Loader<Object> $r1, @Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Object $r2) throws  {
            if (this.mCallbacks != null) {
                String $r4 = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    $r4 = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
                }
                try {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.v(LoaderManagerImpl.TAG, "  onLoadFinished in " + $r1 + ": " + $r1.dataToString($r2));
                    }
                    this.mCallbacks.onLoadFinished($r1, $r2);
                    this.mDeliveredData = true;
                } finally {
                    if (LoaderManagerImpl.this.mHost != null) {
                        LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = $r4;
                    }
                }
            }
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder(64);
            $r1.append("LoaderInfo{");
            $r1.append(Integer.toHexString(System.identityHashCode(this)));
            $r1.append(" #");
            $r1.append(this.mId);
            $r1.append(" : ");
            DebugUtils.buildShortClassTag(this.mLoader, $r1);
            $r1.append("}}");
            return $r1.toString();
        }

        public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
            $r3.print($r1);
            $r3.print("mId=");
            $r3.print(this.mId);
            $r3.print(" mArgs=");
            $r3.println(this.mArgs);
            $r3.print($r1);
            $r3.print("mCallbacks=");
            $r3.println(this.mCallbacks);
            $r3.print($r1);
            $r3.print("mLoader=");
            $r3.println(this.mLoader);
            if (this.mLoader != null) {
                this.mLoader.dump($r1 + "  ", $r2, $r3, $r4);
            }
            if (this.mHaveData || this.mDeliveredData) {
                $r3.print($r1);
                $r3.print("mHaveData=");
                $r3.print(this.mHaveData);
                $r3.print("  mDeliveredData=");
                $r3.println(this.mDeliveredData);
                $r3.print($r1);
                $r3.print("mData=");
                $r3.println(this.mData);
            }
            $r3.print($r1);
            $r3.print("mStarted=");
            $r3.print(this.mStarted);
            $r3.print(" mReportNextStart=");
            $r3.print(this.mReportNextStart);
            $r3.print(" mDestroyed=");
            $r3.println(this.mDestroyed);
            $r3.print($r1);
            $r3.print("mRetaining=");
            $r3.print(this.mRetaining);
            $r3.print(" mRetainingStarted=");
            $r3.print(this.mRetainingStarted);
            $r3.print(" mListenerRegistered=");
            $r3.println(this.mListenerRegistered);
            if (this.mPendingLoader != null) {
                $r3.print($r1);
                $r3.println("Pending Loader ");
                $r3.print(this.mPendingLoader);
                $r3.println(":");
                this.mPendingLoader.dump($r1 + "  ", $r2, $r3, $r4);
            }
        }
    }

    LoaderManagerImpl(String $r1, FragmentHostCallback $r2, boolean $z0) throws  {
        this.mWho = $r1;
        this.mHost = $r2;
        this.mStarted = $z0;
    }

    void updateHostController(FragmentHostCallback $r1) throws  {
        this.mHost = $r1;
    }

    private LoaderInfo createLoader(@Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;"}) int $i0, @Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;"}) Bundle $r1, @Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;"}) LoaderCallbacks<Object> $r2) throws  {
        LoaderInfo $r3 = new LoaderInfo($i0, $r1, $r2);
        $r3.mLoader = $r2.onCreateLoader($i0, $r1);
        return $r3;
    }

    private LoaderInfo createAndInstallLoader(@Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;"}) int $i0, @Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;"}) Bundle $r1, @Signature({"(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<", "Ljava/lang/Object;", ">;)", "Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;"}) LoaderCallbacks<Object> $r2) throws  {
        try {
            this.mCreatingLoader = true;
            LoaderInfo $r3 = createLoader($i0, $r1, $r2);
            installLoader($r3);
            return $r3;
        } finally {
            this.mCreatingLoader = false;
        }
    }

    void installLoader(LoaderInfo $r1) throws  {
        this.mLoaders.put($r1.mId, $r1);
        if (this.mStarted) {
            $r1.start();
        }
    }

    public <D> Loader<D> initLoader(@Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) int $i0, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) Bundle $r1, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) LoaderCallbacks<D> $r2) throws  {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo $r6 = (LoaderInfo) this.mLoaders.get($i0);
        if (DEBUG) {
            Log.v(TAG, "initLoader in " + this + ": args=" + $r1);
        }
        if ($r6 == null) {
            LoaderInfo $r9 = createAndInstallLoader($i0, $r1, $r2);
            $r6 = $r9;
            if (DEBUG) {
                Log.v(TAG, "  Created new loader " + $r9);
            }
        } else {
            if (DEBUG) {
                Log.v(TAG, "  Re-using existing loader " + $r6);
            }
            $r6.mCallbacks = $r2;
        }
        if ($r6.mHaveData && this.mStarted) {
            $r6.callOnLoadFinished($r6.mLoader, $r6.mData);
        }
        return $r6.mLoader;
    }

    public <D> Loader<D> restartLoader(@Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) int $i0, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) Bundle $r1, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) LoaderCallbacks<D> $r2) throws  {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo $r6 = (LoaderInfo) this.mLoaders.get($i0);
        if (DEBUG) {
            Log.v(TAG, "restartLoader in " + this + ": args=" + $r1);
        }
        if ($r6 != null) {
            LoaderInfo $r9 = (LoaderInfo) this.mInactiveLoaders.get($i0);
            Loader $r10;
            if ($r9 == null) {
                if (DEBUG) {
                    Log.v(TAG, "  Making last loader inactive: " + $r6);
                }
                $r10 = $r6.mLoader;
                $r10.abandon();
                this.mInactiveLoaders.put($i0, $r6);
            } else if ($r6.mHaveData) {
                if (DEBUG) {
                    Log.v(TAG, "  Removing last inactive loader: " + $r6);
                }
                $r9.mDeliveredData = false;
                $r9.destroy();
                $r10 = $r6.mLoader;
                $r10.abandon();
                this.mInactiveLoaders.put($i0, $r6);
            } else if ($r6.mStarted) {
                if (DEBUG) {
                    Log.v(TAG, "  Current loader is running; attempting to cancel");
                }
                $r6.cancel();
                if ($r6.mPendingLoader != null) {
                    if (DEBUG) {
                        Log.v(TAG, "  Removing pending loader: " + $r6.mPendingLoader);
                    }
                    $r6.mPendingLoader.destroy();
                    $r6.mPendingLoader = null;
                }
                if (DEBUG) {
                    Log.v(TAG, "  Enqueuing as new pending loader");
                }
                $r6.mPendingLoader = createLoader($i0, $r1, $r2);
                return $r6.mPendingLoader.mLoader;
            } else {
                if (DEBUG) {
                    Log.v(TAG, "  Current loader is stopped; replacing");
                }
                this.mLoaders.put($i0, null);
                $r6.destroy();
            }
        }
        return createAndInstallLoader($i0, $r1, $r2).mLoader;
    }

    public void destroyLoader(int $i0) throws  {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (DEBUG) {
            Log.v(TAG, "destroyLoader in " + this + " of " + $i0);
        }
        int $i1 = this.mLoaders.indexOfKey($i0);
        if ($i1 >= 0) {
            LoaderInfo $r6 = (LoaderInfo) this.mLoaders.valueAt($i1);
            this.mLoaders.removeAt($i1);
            $r6.destroy();
        }
        $i0 = this.mInactiveLoaders.indexOfKey($i0);
        if ($i0 >= 0) {
            $r6 = (LoaderInfo) this.mInactiveLoaders.valueAt($i0);
            this.mInactiveLoaders.removeAt($i0);
            $r6.destroy();
        }
        if (this.mHost != null && !hasRunningLoaders()) {
            this.mHost.mFragmentManager.startPendingDeferredFragments();
        }
    }

    public <D> Loader<D> getLoader(@Signature({"<D:", "Ljava/lang/Object;", ">(I)", "Landroid/support/v4/content/Loader", "<TD;>;"}) int $i0) throws  {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo $r4 = (LoaderInfo) this.mLoaders.get($i0);
        if ($r4 == null) {
            return null;
        }
        if ($r4.mPendingLoader != null) {
            return $r4.mPendingLoader.mLoader;
        }
        return $r4.mLoader;
    }

    void doStart() throws  {
        if (DEBUG) {
            Log.v(TAG, "Starting in " + this);
        }
        if (this.mStarted) {
            RuntimeException $r1 = new RuntimeException("here");
            $r1.fillInStackTrace();
            Log.w(TAG, "Called doStart when already started: " + this, $r1);
            return;
        }
        this.mStarted = true;
        for (int $i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
            ((LoaderInfo) this.mLoaders.valueAt($i0)).start();
        }
    }

    void doStop() throws  {
        if (DEBUG) {
            Log.v(TAG, "Stopping in " + this);
        }
        if (this.mStarted) {
            for (int $i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
                ((LoaderInfo) this.mLoaders.valueAt($i0)).stop();
            }
            this.mStarted = false;
            return;
        }
        RuntimeException $r1 = new RuntimeException("here");
        $r1.fillInStackTrace();
        Log.w(TAG, "Called doStop when not started: " + this, $r1);
    }

    void doRetain() throws  {
        if (DEBUG) {
            Log.v(TAG, "Retaining in " + this);
        }
        if (this.mStarted) {
            this.mRetaining = true;
            this.mStarted = false;
            for (int $i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
                ((LoaderInfo) this.mLoaders.valueAt($i0)).retain();
            }
            return;
        }
        RuntimeException $r1 = new RuntimeException("here");
        $r1.fillInStackTrace();
        Log.w(TAG, "Called doRetain when not started: " + this, $r1);
    }

    void finishRetain() throws  {
        if (this.mRetaining) {
            if (DEBUG) {
                Log.v(TAG, "Finished Retaining in " + this);
            }
            this.mRetaining = false;
            for (int $i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
                ((LoaderInfo) this.mLoaders.valueAt($i0)).finishRetain();
            }
        }
    }

    void doReportNextStart() throws  {
        for (int $i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
            ((LoaderInfo) this.mLoaders.valueAt($i0)).mReportNextStart = true;
        }
    }

    void doReportStart() throws  {
        for (int $i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
            ((LoaderInfo) this.mLoaders.valueAt($i0)).reportStart();
        }
    }

    void doDestroy() throws  {
        int $i0;
        if (!this.mRetaining) {
            if (DEBUG) {
                Log.v(TAG, "Destroying Active in " + this);
            }
            for ($i0 = this.mLoaders.size() - 1; $i0 >= 0; $i0--) {
                ((LoaderInfo) this.mLoaders.valueAt($i0)).destroy();
            }
            this.mLoaders.clear();
        }
        if (DEBUG) {
            Log.v(TAG, "Destroying Inactive in " + this);
        }
        for ($i0 = this.mInactiveLoaders.size() - 1; $i0 >= 0; $i0--) {
            ((LoaderInfo) this.mInactiveLoaders.valueAt($i0)).destroy();
        }
        this.mInactiveLoaders.clear();
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder(128);
        $r1.append("LoaderManager{");
        $r1.append(Integer.toHexString(System.identityHashCode(this)));
        $r1.append(" in ");
        DebugUtils.buildShortClassTag(this.mHost, $r1);
        $r1.append("}}");
        return $r1.toString();
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        String $r7;
        int $i0;
        if (this.mLoaders.size() > 0) {
            $r3.print($r1);
            $r3.println("Active Loaders:");
            $r7 = $r1 + "    ";
            for ($i0 = 0; $i0 < this.mLoaders.size(); $i0++) {
                LoaderInfo $r9 = (LoaderInfo) this.mLoaders.valueAt($i0);
                $r3.print($r1);
                $r3.print("  #");
                $r3.print(this.mLoaders.keyAt($i0));
                $r3.print(": ");
                $r3.println($r9.toString());
                $r9.dump($r7, $r2, $r3, $r4);
            }
        }
        if (this.mInactiveLoaders.size() > 0) {
            $r3.print($r1);
            $r3.println("Inactive Loaders:");
            $r7 = $r1 + "    ";
            for ($i0 = 0; $i0 < this.mInactiveLoaders.size(); $i0++) {
                $r9 = (LoaderInfo) this.mInactiveLoaders.valueAt($i0);
                $r3.print($r1);
                $r3.print("  #");
                $r3.print(this.mInactiveLoaders.keyAt($i0));
                $r3.print(": ");
                $r3.println($r9.toString());
                $r9.dump($r7, $r2, $r3, $r4);
            }
        }
    }

    public boolean hasRunningLoaders() throws  {
        boolean $z0 = false;
        int $i0 = this.mLoaders.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            LoaderInfo $r3 = (LoaderInfo) this.mLoaders.valueAt($i1);
            boolean $z1 = $r3.mStarted && !$r3.mDeliveredData;
            $z0 |= $z1;
        }
        return $z0;
    }
}
