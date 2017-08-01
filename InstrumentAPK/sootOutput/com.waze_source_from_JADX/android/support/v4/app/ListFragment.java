package android.support.v4.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import dalvik.annotation.Signature;

public class ListFragment extends Fragment {
    static final int INTERNAL_EMPTY_ID = 16711681;
    static final int INTERNAL_LIST_CONTAINER_ID = 16711683;
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 16711682;
    ListAdapter mAdapter;
    CharSequence mEmptyText;
    View mEmptyView;
    private final Handler mHandler = new Handler();
    ListView mList;
    View mListContainer;
    boolean mListShown;
    private final OnItemClickListener mOnClickListener = new C00352();
    View mProgressContainer;
    private final Runnable mRequestFocus = new C00341();
    TextView mStandardEmptyView;

    class C00341 implements Runnable {
        C00341() throws  {
        }

        public void run() throws  {
            ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList);
        }
    }

    class C00352 implements OnItemClickListener {
        C00352() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> $r1, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long $l1) throws  {
            ListFragment.this.onListItemClick((ListView) $r1, $r2, $i0, $l1);
        }
    }

    private void ensureList() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:27:0x00aa in {2, 6, 9, 12, 14, 17, 19, 21, 26, 28, 29, 31} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r21 = this;
        r0 = r21;
        r2 = r0.mList;
        if (r2 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = r21;
        r3 = r0.getView();
        if (r3 != 0) goto L_0x0017;
    L_0x000f:
        r4 = new java.lang.IllegalStateException;
        r5 = "Content view not yet created";
        r4.<init>(r5);
        throw r4;
    L_0x0017:
        r6 = r3 instanceof android.widget.ListView;
        if (r6 == 0) goto L_0x0053;
    L_0x001b:
        r7 = r3;
        r7 = (android.widget.ListView) r7;
        r2 = r7;
        r0 = r21;
        r0.mList = r2;
    L_0x0023:
        r8 = 1;
        r0 = r21;
        r0.mListShown = r8;
        r0 = r21;
        r2 = r0.mList;
        r0 = r21;
        r9 = r0.mOnClickListener;
        r2.setOnItemClickListener(r9);
        r0 = r21;
        r10 = r0.mAdapter;
        if (r10 == 0) goto L_0x00ff;
    L_0x0039:
        r0 = r21;
        r10 = r0.mAdapter;
        r11 = 0;
        r0 = r21;
        r0.mAdapter = r11;
        r0 = r21;
        r0.setListAdapter(r10);
    L_0x0047:
        r0 = r21;
        r12 = r0.mHandler;
        r0 = r21;
        r13 = r0.mRequestFocus;
        r12.post(r13);
        return;
    L_0x0053:
        r8 = 16711681; // 0xff0001 float:2.3418053E-38 double:8.2566675E-317;
        r14 = r3.findViewById(r8);
        r16 = r14;
        r16 = (android.widget.TextView) r16;
        r15 = r16;
        r0 = r21;
        r0.mStandardEmptyView = r15;
        r0 = r21;
        r15 = r0.mStandardEmptyView;
        if (r15 != 0) goto L_0x00ae;
    L_0x006a:
        r8 = 16908292; // 0x1020004 float:2.387724E-38 double:8.353806E-317;
        r14 = r3.findViewById(r8);
        r0 = r21;
        r0.mEmptyView = r14;
    L_0x0075:
        r8 = 16711682; // 0xff0002 float:2.3418054E-38 double:8.256668E-317;
        r14 = r3.findViewById(r8);
        goto L_0x0080;
    L_0x007d:
        goto L_0x0023;
    L_0x0080:
        r0 = r21;
        r0.mProgressContainer = r14;
        r8 = 16711683; // 0xff0003 float:2.3418056E-38 double:8.2566685E-317;
        r14 = r3.findViewById(r8);
        r0 = r21;
        r0.mListContainer = r14;
        goto L_0x0093;
    L_0x0090:
        goto L_0x0023;
    L_0x0093:
        r8 = 16908298; // 0x102000a float:2.3877257E-38 double:8.353809E-317;
        r3 = r3.findViewById(r8);
        r6 = r3 instanceof android.widget.ListView;
        if (r6 != 0) goto L_0x00c2;
    L_0x009e:
        if (r3 != 0) goto L_0x00b8;
    L_0x00a0:
        r17 = new java.lang.RuntimeException;
        r5 = "Your content must have a ListView whose id attribute is 'android.R.id.list'";
        r0 = r17;
        r0.<init>(r5);
        throw r17;
        goto L_0x00ae;
    L_0x00ab:
        goto L_0x0047;
    L_0x00ae:
        r0 = r21;
        r15 = r0.mStandardEmptyView;
        r8 = 8;
        r15.setVisibility(r8);
        goto L_0x0075;
    L_0x00b8:
        r17 = new java.lang.RuntimeException;
        r5 = "Content has view with id attribute 'android.R.id.list' that is not a ListView class";
        r0 = r17;
        r0.<init>(r5);
        throw r17;
    L_0x00c2:
        r18 = r3;
        r18 = (android.widget.ListView) r18;
        r2 = r18;
        r0 = r21;
        r0.mList = r2;
        r0 = r21;
        r3 = r0.mEmptyView;
        if (r3 == 0) goto L_0x00de;
    L_0x00d2:
        r0 = r21;
        r2 = r0.mList;
        r0 = r21;
        r3 = r0.mEmptyView;
        r2.setEmptyView(r3);
        goto L_0x007d;
    L_0x00de:
        r0 = r21;
        r0 = r0.mEmptyText;
        r19 = r0;
        if (r19 == 0) goto L_0x0023;
    L_0x00e6:
        r0 = r21;
        r15 = r0.mStandardEmptyView;
        r0 = r21;
        r0 = r0.mEmptyText;
        r19 = r0;
        r15.setText(r0);
        r0 = r21;
        r2 = r0.mList;
        r0 = r21;
        r15 = r0.mStandardEmptyView;
        r2.setEmptyView(r15);
        goto L_0x0090;
    L_0x00ff:
        r0 = r21;
        r3 = r0.mProgressContainer;
        if (r3 == 0) goto L_0x0047;
    L_0x0105:
        r8 = 0;
        r20 = 0;
        r0 = r21;
        r1 = r20;
        r0.setListShown(r8, r1);
        goto L_0x00ab;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.ListFragment.ensureList():void");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) throws  {
        FragmentActivity $r10 = getActivity();
        FrameLayout $r8 = new FrameLayout($r10);
        LinearLayout $r6 = new LinearLayout($r10);
        $r6.setId(INTERNAL_PROGRESS_CONTAINER_ID);
        $r6.setOrientation(1);
        $r6.setVisibility(8);
        $r6.setGravity(17);
        $r6.addView(new ProgressBar($r10, null, 16842874), new LayoutParams(-2, -2));
        $r8.addView($r6, new LayoutParams(-1, -1));
        FrameLayout $r4 = new FrameLayout($r10);
        $r4.setId(INTERNAL_LIST_CONTAINER_ID);
        TextView $r9 = new TextView(getActivity());
        $r9.setId(INTERNAL_EMPTY_ID);
        $r9.setGravity(17);
        $r4.addView($r9, new LayoutParams(-1, -1));
        ListView $r5 = new ListView(getActivity());
        $r5.setId(16908298);
        $r5.setDrawSelectorOnTop(false);
        $r4.addView($r5, new LayoutParams(-1, -1));
        $r8.addView($r4, new LayoutParams(-1, -1));
        $r8.setLayoutParams(new LayoutParams(-1, -1));
        return $r8;
    }

    public void onViewCreated(View $r1, Bundle $r2) throws  {
        super.onViewCreated($r1, $r2);
        ensureList();
    }

    public void onDestroyView() throws  {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mList = null;
        this.mListShown = false;
        this.mListContainer = null;
        this.mProgressContainer = null;
        this.mEmptyView = null;
        this.mStandardEmptyView = null;
        super.onDestroyView();
    }

    public void onListItemClick(ListView l, View v, int position, long id) throws  {
    }

    public void setListAdapter(ListAdapter $r1) throws  {
        boolean $z0 = false;
        boolean $z1 = this.mAdapter != null;
        this.mAdapter = $r1;
        if (this.mList != null) {
            this.mList.setAdapter($r1);
            if (!this.mListShown && !$z1) {
                if (getView().getWindowToken() != null) {
                    $z0 = true;
                }
                setListShown(true, $z0);
            }
        }
    }

    public void setSelection(int $i0) throws  {
        ensureList();
        this.mList.setSelection($i0);
    }

    public int getSelectedItemPosition() throws  {
        ensureList();
        return this.mList.getSelectedItemPosition();
    }

    public long getSelectedItemId() throws  {
        ensureList();
        return this.mList.getSelectedItemId();
    }

    public ListView getListView() throws  {
        ensureList();
        return this.mList;
    }

    public void setEmptyText(CharSequence $r1) throws  {
        ensureList();
        if (this.mStandardEmptyView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        this.mStandardEmptyView.setText($r1);
        if (this.mEmptyText == null) {
            this.mList.setEmptyView(this.mStandardEmptyView);
        }
        this.mEmptyText = $r1;
    }

    public void setListShown(boolean $z0) throws  {
        setListShown($z0, true);
    }

    public void setListShownNoAnimation(boolean $z0) throws  {
        setListShown($z0, false);
    }

    private void setListShown(boolean $z0, boolean $z1) throws  {
        ensureList();
        if (this.mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        } else if (this.mListShown != $z0) {
            this.mListShown = $z0;
            if ($z0) {
                if ($z1) {
                    this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), 17432577));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), 17432576));
                } else {
                    this.mProgressContainer.clearAnimation();
                    this.mListContainer.clearAnimation();
                }
                this.mProgressContainer.setVisibility(8);
                this.mListContainer.setVisibility(0);
                return;
            }
            if ($z1) {
                this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), 17432576));
                this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), 17432577));
            } else {
                this.mProgressContainer.clearAnimation();
                this.mListContainer.clearAnimation();
            }
            this.mProgressContainer.setVisibility(0);
            this.mListContainer.setVisibility(8);
        }
    }

    public ListAdapter getListAdapter() throws  {
        return this.mAdapter;
    }
}
