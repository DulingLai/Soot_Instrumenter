package android.support.v4.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.ActivityCompatApi23.RequestPermissionsRequestCodeValidator;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class FragmentActivity extends BaseFragmentActivityHoneycomb implements OnRequestPermissionsResultCallback, RequestPermissionsRequestCodeValidator {
    static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
    static final String FRAGMENTS_TAG = "android:support:fragments";
    private static final int HONEYCOMB = 11;
    static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
    static final int MSG_REALLY_STOPPED = 1;
    static final int MSG_RESUME_PENDING = 2;
    static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
    static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
    private static final String TAG = "FragmentActivity";
    boolean mCreated;
    final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
    final Handler mHandler = new C00181();
    MediaControllerCompat mMediaController;
    int mNextCandidateRequestIndex;
    boolean mOptionsMenuInvalidated;
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    boolean mReallyStopped;
    boolean mRequestedPermissionsFromFragment;
    boolean mResumed;
    boolean mRetaining;
    boolean mStartedActivityFromFragment;
    boolean mStopped;

    class C00181 extends Handler {
        C00181() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 1:
                    if (FragmentActivity.this.mStopped) {
                        FragmentActivity.this.doReallyStop(false);
                        return;
                    }
                    return;
                case 2:
                    FragmentActivity.this.onResumeFragments();
                    FragmentActivity.this.mFragments.execPendingActions();
                    return;
                default:
                    super.handleMessage($r1);
                    return;
            }
        }
    }

    class HostCallbacks extends FragmentHostCallback<FragmentActivity> {
        public HostCallbacks() throws  {
            super(FragmentActivity.this);
        }

        public void onDump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
            FragmentActivity.this.dump($r1, $r2, $r3, $r4);
        }

        public boolean onShouldSaveFragmentState(Fragment fragment) throws  {
            return !FragmentActivity.this.isFinishing();
        }

        public LayoutInflater onGetLayoutInflater() throws  {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
        }

        public FragmentActivity onGetHost() throws  {
            return FragmentActivity.this;
        }

        public void onSupportInvalidateOptionsMenu() throws  {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }

        public void onStartActivityFromFragment(Fragment $r1, Intent $r2, int $i0) throws  {
            FragmentActivity.this.startActivityFromFragment($r1, $r2, $i0);
        }

        public void onStartActivityFromFragment(Fragment $r1, Intent $r2, int $i0, @Nullable Bundle $r3) throws  {
            FragmentActivity.this.startActivityFromFragment($r1, $r2, $i0, $r3);
        }

        public void onRequestPermissionsFromFragment(@NonNull Fragment $r1, @NonNull String[] $r2, int $i0) throws  {
            FragmentActivity.this.requestPermissionsFromFragment($r1, $r2, $i0);
        }

        public boolean onShouldShowRequestPermissionRationale(@NonNull String $r1) throws  {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, $r1);
        }

        public boolean onHasWindowAnimations() throws  {
            return FragmentActivity.this.getWindow() != null;
        }

        public int onGetWindowAnimations() throws  {
            Window $r2 = FragmentActivity.this.getWindow();
            if ($r2 == null) {
                return 0;
            }
            return $r2.getAttributes().windowAnimations;
        }

        public void onAttachFragment(Fragment $r1) throws  {
            FragmentActivity.this.onAttachFragment($r1);
        }

        @Nullable
        public View onFindViewById(int $i0) throws  {
            return FragmentActivity.this.findViewById($i0);
        }

        public boolean onHasView() throws  {
            Window $r2 = FragmentActivity.this.getWindow();
            return ($r2 == null || $r2.peekDecorView() == null) ? false : true;
        }
    }

    static final class NonConfigurationInstances {
        Object custom;
        List<Fragment> fragments;
        SimpleArrayMap<String, LoaderManager> loaders;

        NonConfigurationInstances() throws  {
        }
    }

    private static java.lang.String viewToString(android.view.View r15) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:56:0x0119
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
        r0 = 70;
        r1 = 46;
        r2 = new java.lang.StringBuilder;
        r3 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r2.<init>(r3);
        r4 = r15.getClass();
        r5 = r4.getName();
        r2.append(r5);
        r3 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r2.append(r3);
        r6 = java.lang.System.identityHashCode(r15);
        r5 = java.lang.Integer.toHexString(r6);
        r2.append(r5);
        r3 = 32;
        r2.append(r3);
        r6 = r15.getVisibility();
        switch(r6) {
            case 0: goto L_0x0174;
            case 4: goto L_0x017a;
            case 8: goto L_0x0180;
            default: goto L_0x0032;
        };
    L_0x0032:
        goto L_0x0033;
    L_0x0033:
        r3 = 46;
        r2.append(r3);
    L_0x0038:
        r7 = r15.isFocusable();
        if (r7 == 0) goto L_0x0186;
    L_0x003e:
        r8 = 70;
    L_0x0040:
        r2.append(r8);
        r7 = r15.isEnabled();
        if (r7 == 0) goto L_0x0189;
    L_0x0049:
        r8 = 69;
    L_0x004b:
        r2.append(r8);
        r7 = r15.willNotDraw();
        if (r7 == 0) goto L_0x0190;
    L_0x0054:
        r8 = 46;
    L_0x0056:
        r2.append(r8);
        r7 = r15.isHorizontalScrollBarEnabled();
        if (r7 == 0) goto L_0x0193;
    L_0x005f:
        r8 = 72;
    L_0x0061:
        r2.append(r8);
        r7 = r15.isVerticalScrollBarEnabled();
        if (r7 == 0) goto L_0x019a;
    L_0x006a:
        r8 = 86;
    L_0x006c:
        r2.append(r8);
        r7 = r15.isClickable();
        if (r7 == 0) goto L_0x019d;
    L_0x0075:
        r8 = 67;
    L_0x0077:
        r2.append(r8);
        r7 = r15.isLongClickable();
        if (r7 == 0) goto L_0x01a4;
    L_0x0080:
        r8 = 76;
    L_0x0082:
        r2.append(r8);
        r3 = 32;
        r2.append(r3);
        r7 = r15.isFocused();
        if (r7 == 0) goto L_0x01a7;
    L_0x0090:
        r2.append(r0);
        r7 = r15.isSelected();
        if (r7 == 0) goto L_0x01aa;
    L_0x0099:
        r0 = 83;
    L_0x009b:
        r2.append(r0);
        r7 = r15.isPressed();
        if (r7 == 0) goto L_0x00a6;
    L_0x00a4:
        r1 = 80;
    L_0x00a6:
        r2.append(r1);
        r3 = 32;
        r2.append(r3);
        r6 = r15.getLeft();
        goto L_0x00b6;
    L_0x00b3:
        goto L_0x0038;
    L_0x00b6:
        r2.append(r6);
        goto L_0x00bd;
    L_0x00ba:
        goto L_0x0038;
    L_0x00bd:
        r3 = 44;
        r2.append(r3);
        goto L_0x00c6;
    L_0x00c3:
        goto L_0x0038;
    L_0x00c6:
        r6 = r15.getTop();
        r2.append(r6);
        goto L_0x00d1;
    L_0x00ce:
        goto L_0x0040;
    L_0x00d1:
        r3 = 45;
        r2.append(r3);
        goto L_0x00da;
    L_0x00d7:
        goto L_0x004b;
    L_0x00da:
        r6 = r15.getRight();
        r2.append(r6);
        r3 = 44;
        r2.append(r3);
        r6 = r15.getBottom();
        r2.append(r6);
        goto L_0x00f1;
    L_0x00ee:
        goto L_0x0061;
    L_0x00f1:
        r6 = r15.getId();
        r3 = -1;
        if (r6 == r3) goto L_0x0161;
    L_0x00f8:
        r9 = " #";
        r2.append(r9);
        r5 = java.lang.Integer.toHexString(r6);
        r2.append(r5);
        goto L_0x0108;
    L_0x0105:
        goto L_0x0077;
    L_0x0108:
        r10 = r15.getResources();
        goto L_0x0110;
    L_0x010d:
        goto L_0x00b3;
    L_0x0110:
        if (r6 == 0) goto L_0x0161;
    L_0x0112:
        goto L_0x0116;
    L_0x0113:
        goto L_0x00ba;
    L_0x0116:
        if (r10 == 0) goto L_0x0161;
    L_0x0118:
        goto L_0x0120;
        goto L_0x011d;
    L_0x011a:
        goto L_0x0090;
    L_0x011d:
        goto L_0x00c3;
    L_0x0120:
        r3 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r11 = r3 & r6;
        goto L_0x0129;
    L_0x0126:
        goto L_0x009b;
    L_0x0129:
        goto L_0x0131;
    L_0x012a:
        goto L_0x00ce;
        goto L_0x0131;
    L_0x012e:
        goto L_0x00d7;
    L_0x0131:
        switch(r11) {
            case 16777216: goto L_0x01b0;
            case 2130706432: goto L_0x01ad;
            default: goto L_0x0134;
        };
    L_0x0134:
        goto L_0x0135;
    L_0x0135:
        r5 = r10.getResourcePackageName(r6);	 Catch:{ NotFoundException -> 0x01b3 }
    L_0x0139:
        r12 = r10.getResourceTypeName(r6);	 Catch:{ NotFoundException -> 0x01b3 }
        goto L_0x0141;	 Catch:{ NotFoundException -> 0x01b3 }
    L_0x013e:
        goto L_0x00ee;	 Catch:{ NotFoundException -> 0x01b3 }
    L_0x0141:
        r13 = r10.getResourceEntryName(r6);	 Catch:{ NotFoundException -> 0x01b3 }
        r9 = " ";	 Catch:{ NotFoundException -> 0x01b3 }
        r2.append(r9);	 Catch:{ NotFoundException -> 0x01b3 }
        r2.append(r5);	 Catch:{ NotFoundException -> 0x01b3 }
        r9 = ":";	 Catch:{ NotFoundException -> 0x01b3 }
        r2.append(r9);	 Catch:{ NotFoundException -> 0x01b3 }
        goto L_0x0156;	 Catch:{ NotFoundException -> 0x01b3 }
    L_0x0153:
        goto L_0x0105;	 Catch:{ NotFoundException -> 0x01b3 }
    L_0x0156:
        r2.append(r12);	 Catch:{ NotFoundException -> 0x01b3 }
        r9 = "/";	 Catch:{ NotFoundException -> 0x01b3 }
        r2.append(r9);	 Catch:{ NotFoundException -> 0x01b3 }
        r2.append(r13);	 Catch:{ NotFoundException -> 0x01b3 }
    L_0x0161:
        r9 = "}";
        r2.append(r9);
        goto L_0x016b;
    L_0x0168:
        goto L_0x011a;
    L_0x016b:
        r5 = r2.toString();
        goto L_0x0173;
    L_0x0170:
        goto L_0x0126;
    L_0x0173:
        return r5;
    L_0x0174:
        r3 = 86;
        r2.append(r3);
        goto L_0x010d;
    L_0x017a:
        r3 = 73;
        r2.append(r3);
        goto L_0x0113;
    L_0x0180:
        r3 = 71;
        r2.append(r3);
        goto L_0x011d;
    L_0x0186:
        r8 = 46;
        goto L_0x012a;
    L_0x0189:
        r8 = 46;
        goto L_0x012e;
        goto L_0x0190;
    L_0x018d:
        goto L_0x0056;
    L_0x0190:
        r8 = 68;
        goto L_0x018d;
    L_0x0193:
        r8 = 46;
        goto L_0x013e;
        goto L_0x019a;
    L_0x0197:
        goto L_0x006c;
    L_0x019a:
        r8 = 46;
        goto L_0x0197;
    L_0x019d:
        r8 = 46;
        goto L_0x0153;
        goto L_0x01a4;
    L_0x01a1:
        goto L_0x0082;
    L_0x01a4:
        r8 = 46;
        goto L_0x01a1;
    L_0x01a7:
        r0 = 46;
        goto L_0x0168;
    L_0x01aa:
        r0 = 46;
        goto L_0x0170;
    L_0x01ad:
        r5 = "app";
        goto L_0x0139;
    L_0x01b0:
        r5 = "android";
        goto L_0x0139;
    L_0x01b3:
        r14 = move-exception;
        goto L_0x0161;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentActivity.viewToString(android.view.View):java.lang.String");
    }

    public Object onRetainCustomNonConfigurationInstance() throws  {
        return null;
    }

    public /* bridge */ /* synthetic */ View onCreateView(View $r1, String $r2, Context $r3, AttributeSet $r4) throws  {
        return super.onCreateView($r1, $r2, $r3, $r4);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String $r1, Context $r2, AttributeSet $r3) throws  {
        return super.onCreateView($r1, $r2, $r3);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        this.mFragments.noteStateNotSaved();
        int $i2 = $i0 >> 16;
        if ($i2 != 0) {
            $i2--;
            String $r5 = (String) this.mPendingFragmentActivityResults.get($i2);
            this.mPendingFragmentActivityResults.remove($i2);
            if ($r5 == null) {
                Log.w(TAG, "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment $r6 = this.mFragments.findFragmentByWho($r5);
            if ($r6 == null) {
                Log.w(TAG, "Activity result no fragment exists for who: " + $r5);
                return;
            }
            $r6.onActivityResult(SupportMenu.USER_MASK & $i0, $i1, $r1);
            return;
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    public void onBackPressed() throws  {
        if (!this.mFragments.getSupportFragmentManager().popBackStackImmediate()) {
            onBackPressedNotHandled();
        }
    }

    public final void setSupportMediaController(MediaControllerCompat $r1) throws  {
        this.mMediaController = $r1;
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.setMediaController(this, $r1.getMediaController());
        }
    }

    public final MediaControllerCompat getSupportMediaController() throws  {
        return this.mMediaController;
    }

    public void supportFinishAfterTransition() throws  {
        ActivityCompat.finishAfterTransition(this);
    }

    public void setEnterSharedElementCallback(SharedElementCallback $r1) throws  {
        ActivityCompat.setEnterSharedElementCallback(this, $r1);
    }

    public void setExitSharedElementCallback(SharedElementCallback $r1) throws  {
        ActivityCompat.setExitSharedElementCallback(this, $r1);
    }

    public void supportPostponeEnterTransition() throws  {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() throws  {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        this.mFragments.dispatchConfigurationChanged($r1);
    }

    protected void onCreate(@Nullable Bundle $r1) throws  {
        List $r2 = null;
        this.mFragments.attachHost(null);
        super.onCreate($r1);
        NonConfigurationInstances $r5 = (NonConfigurationInstances) getLastNonConfigurationInstance();
        if ($r5 != null) {
            this.mFragments.restoreLoaderNonConfig($r5.loaders);
        }
        if ($r1 != null) {
            Parcelable $r7 = $r1.getParcelable(FRAGMENTS_TAG);
            FragmentController $r3 = this.mFragments;
            if ($r5 != null) {
                $r2 = $r5.fragments;
            }
            $r3.restoreAllState($r7, $r2);
            if ($r1.containsKey(NEXT_CANDIDATE_REQUEST_INDEX_TAG)) {
                this.mNextCandidateRequestIndex = $r1.getInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG);
                int[] $r8 = $r1.getIntArray(ALLOCATED_REQUEST_INDICIES_TAG);
                String[] $r9 = $r1.getStringArray(REQUEST_FRAGMENT_WHO_TAG);
                if ($r8 == null || $r9 == null || $r8.length != $r9.length) {
                    Log.w(TAG, "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.mPendingFragmentActivityResults = new SparseArrayCompat($r8.length);
                    for (int $i0 = 0; $i0 < $r8.length; $i0++) {
                        this.mPendingFragmentActivityResults.put($r8[$i0], $r9[$i0]);
                    }
                }
            }
        }
        if (this.mPendingFragmentActivityResults == null) {
            this.mPendingFragmentActivityResults = new SparseArrayCompat();
            this.mNextCandidateRequestIndex = 0;
        }
        this.mFragments.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int $i0, Menu $r1) throws  {
        if ($i0 != 0) {
            return super.onCreatePanelMenu($i0, $r1);
        }
        return VERSION.SDK_INT >= 11 ? super.onCreatePanelMenu($i0, $r1) | this.mFragments.dispatchCreateOptionsMenu($r1, getMenuInflater()) : true;
    }

    final View dispatchFragmentsOnCreateView(View $r1, String $r2, Context $r3, AttributeSet $r4) throws  {
        return this.mFragments.onCreateView($r1, $r2, $r3, $r4);
    }

    protected void onDestroy() throws  {
        super.onDestroy();
        doReallyStop(false);
        this.mFragments.dispatchDestroy();
        this.mFragments.doLoaderDestroy();
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        if (VERSION.SDK_INT >= 5 || $i0 != 4 || $r1.getRepeatCount() != 0) {
            return super.onKeyDown($i0, $r1);
        }
        onBackPressed();
        return true;
    }

    public void onLowMemory() throws  {
        super.onLowMemory();
        this.mFragments.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int $i0, MenuItem $r1) throws  {
        if (super.onMenuItemSelected($i0, $r1)) {
            return true;
        }
        switch ($i0) {
            case 0:
                return this.mFragments.dispatchOptionsItemSelected($r1);
            case 6:
                return this.mFragments.dispatchContextItemSelected($r1);
            default:
                return false;
        }
    }

    public void onPanelClosed(int $i0, Menu $r1) throws  {
        switch ($i0) {
            case 0:
                this.mFragments.dispatchOptionsMenuClosed($r1);
                break;
            default:
                break;
        }
        super.onPanelClosed($i0, $r1);
    }

    protected void onPause() throws  {
        super.onPause();
        this.mResumed = false;
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
            onResumeFragments();
        }
        this.mFragments.dispatchPause();
    }

    protected void onNewIntent(Intent $r1) throws  {
        super.onNewIntent($r1);
        this.mFragments.noteStateNotSaved();
    }

    public void onStateNotSaved() throws  {
        this.mFragments.noteStateNotSaved();
    }

    protected void onResume() throws  {
        super.onResume();
        this.mHandler.sendEmptyMessage(2);
        this.mResumed = true;
        this.mFragments.execPendingActions();
    }

    protected void onPostResume() throws  {
        super.onPostResume();
        this.mHandler.removeMessages(2);
        onResumeFragments();
        this.mFragments.execPendingActions();
    }

    protected void onResumeFragments() throws  {
        this.mFragments.dispatchResume();
    }

    public boolean onPreparePanel(int $i0, View $r1, Menu $r2) throws  {
        if ($i0 != 0 || $r2 == null) {
            return super.onPreparePanel($i0, $r1, $r2);
        }
        if (this.mOptionsMenuInvalidated) {
            this.mOptionsMenuInvalidated = false;
            $r2.clear();
            onCreatePanelMenu($i0, $r2);
        }
        return onPrepareOptionsPanel($r1, $r2) | this.mFragments.dispatchPrepareOptionsMenu($r2);
    }

    protected boolean onPrepareOptionsPanel(View $r1, Menu $r2) throws  {
        return super.onPreparePanel(0, $r1, $r2);
    }

    public final Object onRetainNonConfigurationInstance() throws  {
        if (this.mStopped) {
            doReallyStop(true);
        }
        Object $r1 = onRetainCustomNonConfigurationInstance();
        List $r3 = this.mFragments.retainNonConfig();
        SimpleArrayMap $r4 = this.mFragments.retainLoaderNonConfig();
        if ($r3 == null && $r4 == null && $r1 == null) {
            return null;
        }
        NonConfigurationInstances $r5 = new NonConfigurationInstances();
        $r5.custom = $r1;
        $r5.fragments = $r3;
        $r5.loaders = $r4;
        return $r5;
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        Parcelable $r5 = this.mFragments.saveAllState();
        if ($r5 != null) {
            $r1.putParcelable(FRAGMENTS_TAG, $r5);
        }
        if (this.mPendingFragmentActivityResults.size() > 0) {
            $r1.putInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG, this.mNextCandidateRequestIndex);
            int[] $r3 = new int[this.mPendingFragmentActivityResults.size()];
            String[] $r2 = new String[this.mPendingFragmentActivityResults.size()];
            for (int $i0 = 0; $i0 < this.mPendingFragmentActivityResults.size(); $i0++) {
                $r3[$i0] = this.mPendingFragmentActivityResults.keyAt($i0);
                $r2[$i0] = (String) this.mPendingFragmentActivityResults.valueAt($i0);
            }
            $r1.putIntArray(ALLOCATED_REQUEST_INDICIES_TAG, $r3);
            $r1.putStringArray(REQUEST_FRAGMENT_WHO_TAG, $r2);
        }
    }

    protected void onStart() throws  {
        super.onStart();
        this.mStopped = false;
        this.mReallyStopped = false;
        this.mHandler.removeMessages(1);
        if (!this.mCreated) {
            this.mCreated = true;
            this.mFragments.dispatchActivityCreated();
        }
        this.mFragments.noteStateNotSaved();
        this.mFragments.execPendingActions();
        this.mFragments.doLoaderStart();
        this.mFragments.dispatchStart();
        this.mFragments.reportLoaderStart();
    }

    protected void onStop() throws  {
        super.onStop();
        this.mStopped = true;
        this.mHandler.sendEmptyMessage(1);
        this.mFragments.dispatchStop();
    }

    public Object getLastCustomNonConfigurationInstance() throws  {
        NonConfigurationInstances $r2 = (NonConfigurationInstances) getLastNonConfigurationInstance();
        return $r2 != null ? $r2.custom : null;
    }

    public void supportInvalidateOptionsMenu() throws  {
        if (VERSION.SDK_INT >= 11) {
            ActivityCompatHoneycomb.invalidateOptionsMenu(this);
        } else {
            this.mOptionsMenuInvalidated = true;
        }
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        String $r5;
        if (VERSION.SDK_INT >= 11) {
            $r3.print($r1);
            $r3.print("Local FragmentActivity ");
            $r3.print(Integer.toHexString(System.identityHashCode(this)));
            $r3.println(" State:");
            $r5 = $r1 + "  ";
            $r3.print($r5);
            $r3.print("mCreated=");
            $r3.print(this.mCreated);
            $r3.print("mResumed=");
            $r3.print(this.mResumed);
            $r3.print(" mStopped=");
            $r3.print(this.mStopped);
            $r3.print(" mReallyStopped=");
            $r3.println(this.mReallyStopped);
            this.mFragments.dumpLoaders($r5, $r2, $r3, $r4);
            this.mFragments.getSupportFragmentManager().dump($r1, $r2, $r3, $r4);
            $r3.print($r1);
            $r3.println("View Hierarchy:");
            dumpViewHierarchy($r1 + "  ", $r3, getWindow().getDecorView());
        } else {
            $r3.print($r1);
            $r3.print("Local FragmentActivity ");
            $r3.print(Integer.toHexString(System.identityHashCode(this)));
            $r3.println(" State:");
            $r5 = $r1 + "  ";
            $r3.print($r5);
            $r3.print("mCreated=");
            $r3.print(this.mCreated);
            $r3.print("mResumed=");
            $r3.print(this.mResumed);
            $r3.print(" mStopped=");
            $r3.print(this.mStopped);
            $r3.print(" mReallyStopped=");
            $r3.println(this.mReallyStopped);
            this.mFragments.dumpLoaders($r5, $r2, $r3, $r4);
            this.mFragments.getSupportFragmentManager().dump($r1, $r2, $r3, $r4);
            $r3.print($r1);
            $r3.println("View Hierarchy:");
            dumpViewHierarchy($r1 + "  ", $r3, getWindow().getDecorView());
        }
    }

    private void dumpViewHierarchy(String $r3, PrintWriter $r1, View $r2) throws  {
        $r1.print($r3);
        if ($r2 == null) {
            $r1.println("null");
            return;
        }
        $r1.println(viewToString($r2));
        if ($r2 instanceof ViewGroup) {
            ViewGroup $r5 = (ViewGroup) $r2;
            int $i0 = $r5.getChildCount();
            if ($i0 > 0) {
                $r3 = $r3 + "  ";
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    dumpViewHierarchy($r3, $r1, $r5.getChildAt($i1));
                }
            }
        }
    }

    void doReallyStop(boolean $z0) throws  {
        if (!this.mReallyStopped) {
            this.mReallyStopped = true;
            this.mRetaining = $z0;
            this.mHandler.removeMessages(1);
            onReallyStop();
        }
    }

    void onReallyStop() throws  {
        this.mFragments.doLoaderStop(this.mRetaining);
        this.mFragments.dispatchReallyStop();
    }

    public void onAttachFragment(Fragment fragment) throws  {
    }

    public FragmentManager getSupportFragmentManager() throws  {
        return this.mFragments.getSupportFragmentManager();
    }

    public LoaderManager getSupportLoaderManager() throws  {
        return this.mFragments.getSupportLoaderManager();
    }

    public void startActivityForResult(Intent $r1, int $i0) throws  {
        if (this.mStartedActivityFromFragment || $i0 == -1 || (SupportMenu.CATEGORY_MASK & $i0) == 0) {
            super.startActivityForResult($r1, $i0);
            return;
        }
        throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
    }

    public final void validateRequestPermissionsRequestCode(int $i0) throws  {
        if (!this.mRequestedPermissionsFromFragment && $i0 != -1 && (SupportMenu.CATEGORY_MASK & $i0) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    public void onRequestPermissionsResult(int $i0, @NonNull String[] $r1, @NonNull int[] $r2) throws  {
        int $i1 = ($i0 >> 16) & SupportMenu.USER_MASK;
        if ($i1 != 0) {
            $i1--;
            String $r5 = (String) this.mPendingFragmentActivityResults.get($i1);
            this.mPendingFragmentActivityResults.remove($i1);
            if ($r5 == null) {
                Log.w(TAG, "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment $r7 = this.mFragments.findFragmentByWho($r5);
            if ($r7 == null) {
                Log.w(TAG, "Activity result no fragment exists for who: " + $r5);
                return;
            }
            $r7.onRequestPermissionsResult($i0 & SupportMenu.USER_MASK, $r1, $r2);
        }
    }

    public void startActivityFromFragment(Fragment $r1, Intent $r2, int $i0) throws  {
        startActivityFromFragment($r1, $r2, $i0, null);
    }

    public void startActivityFromFragment(Fragment $r1, Intent $r2, int $i0, @Nullable Bundle $r3) throws  {
        this.mStartedActivityFromFragment = true;
        if ($i0 == -1) {
            try {
                ActivityCompat.startActivityForResult(this, $r2, -1, $r3);
            } finally {
                this.mStartedActivityFromFragment = false;
            }
        } else if ((SupportMenu.CATEGORY_MASK & $i0) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        } else {
            ActivityCompat.startActivityForResult(this, $r2, ((allocateRequestIndex($r1) + 1) << 16) + (SupportMenu.USER_MASK & $i0), $r3);
            this.mStartedActivityFromFragment = false;
        }
    }

    private int allocateRequestIndex(Fragment $r1) throws  {
        if (this.mPendingFragmentActivityResults.size() >= MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS) {
            throw new IllegalStateException("Too many pending Fragment activity results.");
        }
        while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0) {
            this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS;
        }
        int $i0 = this.mNextCandidateRequestIndex;
        this.mPendingFragmentActivityResults.put($i0, $r1.mWho);
        this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS;
        return $i0;
    }

    private void requestPermissionsFromFragment(Fragment $r1, String[] $r2, int $i0) throws  {
        if ($i0 == -1) {
            ActivityCompat.requestPermissions(this, $r2, $i0);
        } else if ((SupportMenu.CATEGORY_MASK & $i0) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        } else {
            try {
                this.mRequestedPermissionsFromFragment = true;
                ActivityCompat.requestPermissions(this, $r2, ((allocateRequestIndex($r1) + 1) << 16) + (SupportMenu.USER_MASK & $i0));
            } finally {
                this.mRequestedPermissionsFromFragment = false;
            }
        }
    }
}
