package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E> extends FragmentContainer {
    private final Activity mActivity;
    private SimpleArrayMap<String, LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    private LoaderManagerImpl mLoaderManager;
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    @Nullable
    public View onFindViewById(int id) throws  {
        return null;
    }

    @Nullable
    public abstract E onGetHost() throws ;

    public boolean onHasView() throws  {
        return true;
    }

    public boolean onHasWindowAnimations() throws  {
        return true;
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) throws  {
        return true;
    }

    public boolean onShouldShowRequestPermissionRationale(@NonNull String permission) throws  {
        return false;
    }

    public FragmentHostCallback(Context $r1, Handler $r2, int $i0) throws  {
        this(null, $r1, $r2, $i0);
    }

    FragmentHostCallback(FragmentActivity $r1) throws  {
        this($r1, $r1, $r1.mHandler, 0);
    }

    FragmentHostCallback(Activity $r1, Context $r2, Handler $r3, int $i0) throws  {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = $r1;
        this.mContext = $r2;
        this.mHandler = $r3;
        this.mWindowAnimations = $i0;
    }

    public void onDump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) throws  {
    }

    public LayoutInflater onGetLayoutInflater() throws  {
        return (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void onSupportInvalidateOptionsMenu() throws  {
    }

    public void onStartActivityFromFragment(Fragment $r1, Intent $r2, int $i0) throws  {
        onStartActivityFromFragment($r1, $r2, $i0, null);
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent $r2, int $i0, @Nullable Bundle options) throws  {
        if ($i0 != -1) {
            throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivity($r2);
    }

    public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] permissions, int requestCode) throws  {
    }

    public int onGetWindowAnimations() throws  {
        return this.mWindowAnimations;
    }

    Activity getActivity() throws  {
        return this.mActivity;
    }

    Context getContext() throws  {
        return this.mContext;
    }

    Handler getHandler() throws  {
        return this.mHandler;
    }

    FragmentManagerImpl getFragmentManagerImpl() throws  {
        return this.mFragmentManager;
    }

    LoaderManagerImpl getLoaderManagerImpl() throws  {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    void inactivateFragment(String $r1) throws  {
        if (this.mAllLoaderManagers != null) {
            LoaderManagerImpl $r4 = (LoaderManagerImpl) this.mAllLoaderManagers.get($r1);
            if ($r4 != null && !$r4.mRetaining) {
                $r4.doDestroy();
                this.mAllLoaderManagers.remove($r1);
            }
        }
    }

    void onAttachFragment(Fragment fragment) throws  {
    }

    boolean getRetainLoaders() throws  {
        return this.mRetainLoaders;
    }

    void doLoaderStart() throws  {
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doStart();
            } else if (!this.mCheckedForLoaderManager) {
                this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
                if (!(this.mLoaderManager == null || this.mLoaderManager.mStarted)) {
                    this.mLoaderManager.doStart();
                }
            }
            this.mCheckedForLoaderManager = true;
        }
    }

    void doLoaderStop(boolean $z0) throws  {
        this.mRetainLoaders = $z0;
        if (this.mLoaderManager != null && this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if ($z0) {
                this.mLoaderManager.doRetain();
            } else {
                this.mLoaderManager.doStop();
            }
        }
    }

    void doLoaderRetain() throws  {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doRetain();
        }
    }

    void doLoaderDestroy() throws  {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doDestroy();
        }
    }

    void reportLoaderStart() throws  {
        if (this.mAllLoaderManagers != null) {
            int $i1;
            int $i0 = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] $r2 = new LoaderManagerImpl[$i0];
            for ($i1 = $i0 - 1; $i1 >= 0; $i1--) {
                $r2[$i1] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt($i1);
            }
            for ($i1 = 0; $i1 < $i0; $i1++) {
                LoaderManagerImpl $r1 = $r2[$i1];
                $r1.finishRetain();
                $r1.doReportStart();
            }
        }
    }

    LoaderManagerImpl getLoaderManager(String $r1, boolean $z0, boolean $z1) throws  {
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new SimpleArrayMap();
        }
        LoaderManagerImpl $r4 = (LoaderManagerImpl) this.mAllLoaderManagers.get($r1);
        if ($r4 != null) {
            $r4.updateHostController(this);
            return $r4;
        } else if (!$z1) {
            return $r4;
        } else {
            $r4 = new LoaderManagerImpl($r1, this, $z0);
            this.mAllLoaderManagers.put($r1, $r4);
            return $r4;
        }
    }

    SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() throws  {
        boolean $z0 = false;
        if (this.mAllLoaderManagers != null) {
            int $i1;
            int $i0 = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] $r2 = new LoaderManagerImpl[$i0];
            for ($i1 = $i0 - 1; $i1 >= 0; $i1--) {
                $r2[$i1] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt($i1);
            }
            for ($i1 = 0; $i1 < $i0; $i1++) {
                LoaderManagerImpl $r1 = $r2[$i1];
                if ($r1.mRetaining) {
                    $z0 = true;
                } else {
                    $r1.doDestroy();
                    this.mAllLoaderManagers.remove($r1.mWho);
                }
            }
        }
        if ($z0) {
            return this.mAllLoaderManagers;
        }
        return null;
    }

    void restoreLoaderNonConfig(@Signature({"(", "Landroid/support/v4/util/SimpleArrayMap", "<", "Ljava/lang/String;", "Landroid/support/v4/app/LoaderManager;", ">;)V"}) SimpleArrayMap<String, LoaderManager> $r1) throws  {
        this.mAllLoaderManagers = $r1;
    }

    void dumpLoaders(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        $r3.print($r1);
        $r3.print("mLoadersStarted=");
        $r3.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            $r3.print($r1);
            $r3.print("Loader Manager ");
            $r3.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            $r3.println(":");
            this.mLoaderManager.dump($r1 + "  ", $r2, $r3, $r4);
        }
    }
}
