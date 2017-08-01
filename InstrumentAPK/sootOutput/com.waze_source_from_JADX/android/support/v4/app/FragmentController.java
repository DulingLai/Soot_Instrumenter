package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    public static final FragmentController createController(@Signature({"(", "Landroid/support/v4/app/FragmentHostCallback", "<*>;)", "Landroid/support/v4/app/FragmentController;"}) FragmentHostCallback<?> $r0) throws  {
        return new FragmentController($r0);
    }

    private FragmentController(@Signature({"(", "Landroid/support/v4/app/FragmentHostCallback", "<*>;)V"}) FragmentHostCallback<?> $r1) throws  {
        this.mHost = $r1;
    }

    public FragmentManager getSupportFragmentManager() throws  {
        return this.mHost.getFragmentManagerImpl();
    }

    public LoaderManager getSupportLoaderManager() throws  {
        return this.mHost.getLoaderManagerImpl();
    }

    @Nullable
    Fragment findFragmentByWho(String $r1) throws  {
        return this.mHost.mFragmentManager.findFragmentByWho($r1);
    }

    public int getActiveFragmentsCount() throws  {
        ArrayList $r1 = this.mHost.mFragmentManager.mActive;
        if ($r1 == null) {
            return 0;
        }
        return $r1.size();
    }

    public List<Fragment> getActiveFragments(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/app/Fragment;", ">;)", "Ljava/util/List", "<", "Landroid/support/v4/app/Fragment;", ">;"}) List<Fragment> $r1) throws  {
        if (this.mHost.mFragmentManager.mActive == null) {
            return null;
        }
        List $r12;
        if ($r1 == null) {
            $r12 = $r4;
            ArrayList $r4 = new ArrayList(getActiveFragmentsCount());
        }
        $r12.addAll(this.mHost.mFragmentManager.mActive);
        return $r12;
    }

    public void attachHost(Fragment $r1) throws  {
        this.mHost.mFragmentManager.attachController(this.mHost, this.mHost, $r1);
    }

    public View onCreateView(View $r1, String $r2, Context $r3, AttributeSet $r4) throws  {
        return this.mHost.mFragmentManager.onCreateView($r1, $r2, $r3, $r4);
    }

    public void noteStateNotSaved() throws  {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public Parcelable saveAllState() throws  {
        return this.mHost.mFragmentManager.saveAllState();
    }

    public void restoreAllState(@Signature({"(", "Landroid/os/Parcelable;", "Ljava/util/List", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) Parcelable $r1, @Signature({"(", "Landroid/os/Parcelable;", "Ljava/util/List", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) List<Fragment> $r2) throws  {
        this.mHost.mFragmentManager.restoreAllState($r1, $r2);
    }

    public List<Fragment> retainNonConfig() throws  {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    public void dispatchCreate() throws  {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public void dispatchActivityCreated() throws  {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchStart() throws  {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchResume() throws  {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchPause() throws  {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public void dispatchStop() throws  {
        this.mHost.mFragmentManager.dispatchStop();
    }

    public void dispatchReallyStop() throws  {
        this.mHost.mFragmentManager.dispatchReallyStop();
    }

    public void dispatchDestroyView() throws  {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchDestroy() throws  {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public void dispatchConfigurationChanged(Configuration $r1) throws  {
        this.mHost.mFragmentManager.dispatchConfigurationChanged($r1);
    }

    public void dispatchLowMemory() throws  {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    public boolean dispatchCreateOptionsMenu(Menu $r1, MenuInflater $r2) throws  {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu($r1, $r2);
    }

    public boolean dispatchPrepareOptionsMenu(Menu $r1) throws  {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu($r1);
    }

    public boolean dispatchOptionsItemSelected(MenuItem $r1) throws  {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected($r1);
    }

    public boolean dispatchContextItemSelected(MenuItem $r1) throws  {
        return this.mHost.mFragmentManager.dispatchContextItemSelected($r1);
    }

    public void dispatchOptionsMenuClosed(Menu $r1) throws  {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed($r1);
    }

    public boolean execPendingActions() throws  {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    public void doLoaderStart() throws  {
        this.mHost.doLoaderStart();
    }

    public void doLoaderStop(boolean $z0) throws  {
        this.mHost.doLoaderStop($z0);
    }

    public void doLoaderRetain() throws  {
        this.mHost.doLoaderRetain();
    }

    public void doLoaderDestroy() throws  {
        this.mHost.doLoaderDestroy();
    }

    public void reportLoaderStart() throws  {
        this.mHost.reportLoaderStart();
    }

    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() throws  {
        return this.mHost.retainLoaderNonConfig();
    }

    public void restoreLoaderNonConfig(@Signature({"(", "Landroid/support/v4/util/SimpleArrayMap", "<", "Ljava/lang/String;", "Landroid/support/v4/app/LoaderManager;", ">;)V"}) SimpleArrayMap<String, LoaderManager> $r1) throws  {
        this.mHost.restoreLoaderNonConfig($r1);
    }

    public void dumpLoaders(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        this.mHost.dumpLoaders($r1, $r2, $r3, $r4);
    }
}
