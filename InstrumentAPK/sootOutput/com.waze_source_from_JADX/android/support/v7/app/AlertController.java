package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.appcompat.C0192R;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;

class AlertController {
    private ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final OnClickListener mButtonHandler = new C01661();
    private Button mButtonNegative;
    private Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    private Button mButtonNeutral;
    private Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    private Button mButtonPositive;
    private Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private int mCheckedItem = -1;
    private final Context mContext;
    private View mCustomTitleView;
    private final AppCompatDialog mDialog;
    private Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    private int mListItemLayout;
    private int mListLayout;
    private ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    private int mMultiChoiceItemLayout;
    private NestedScrollView mScrollView;
    private int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    private final Window mWindow;

    class C01661 implements OnClickListener {
        C01661() throws  {
        }

        public void onClick(View $r1) throws  {
            Message $r4;
            if ($r1 == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null) {
                $r4 = Message.obtain(AlertController.this.mButtonPositiveMessage);
            } else if ($r1 == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null) {
                $r4 = Message.obtain(AlertController.this.mButtonNegativeMessage);
            } else if ($r1 != AlertController.this.mButtonNeutral || AlertController.this.mButtonNeutralMessage == null) {
                $r4 = null;
            } else {
                $r4 = Message.obtain(AlertController.this.mButtonNeutralMessage);
            }
            if ($r4 != null) {
                $r4.sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialog).sendToTarget();
        }
    }

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean mCancelable;
        public int mCheckedItem = -1;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public int mIconAttrId = 0;
        public int mIconId = 0;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public OnCancelListener mOnCancelListener;
        public OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public OnDismissListener mOnDismissListener;
        public OnItemSelectedListener mOnItemSelectedListener;
        public OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public boolean mRecycleOnMeasure = true;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        class C01711 extends ArrayAdapter<CharSequence> {
            final /* synthetic */ ListView val$listView;

            C01711(Context $r2, int $i0, int $i1, CharSequence[] $r3, ListView $r4) throws  {
                this.val$listView = $r4;
                super($r2, $i0, $i1, $r3);
            }

            public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
                $r1 = super.getView($i0, $r1, $r2);
                if (AlertParams.this.mCheckedItems == null || !AlertParams.this.mCheckedItems[$i0]) {
                    return $r1;
                }
                this.val$listView.setItemChecked($i0, true);
                return $r1;
            }
        }

        class C01722 extends CursorAdapter {
            private final int mIsCheckedIndex;
            private final int mLabelIndex;
            final /* synthetic */ AlertController val$dialog;
            final /* synthetic */ ListView val$listView;

            C01722(Context $r2, Cursor $r3, boolean $z0, ListView $r4, AlertController $r5) throws  {
                this.val$listView = $r4;
                this.val$dialog = $r5;
                super($r2, $r3, $z0);
                $r3 = getCursor();
                this.mLabelIndex = $r3.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                this.mIsCheckedIndex = $r3.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
            }

            public void bindView(View $r1, Context context, Cursor $r3) throws  {
                boolean $z0 = true;
                ((CheckedTextView) $r1.findViewById(16908308)).setText($r3.getString(this.mLabelIndex));
                ListView $r6 = this.val$listView;
                int $i0 = $r3.getPosition();
                if ($r3.getInt(this.mIsCheckedIndex) != 1) {
                    $z0 = false;
                }
                $r6.setItemChecked($i0, $z0);
            }

            public View newView(Context context, Cursor cursor, ViewGroup $r3) throws  {
                return AlertParams.this.mInflater.inflate(this.val$dialog.mMultiChoiceItemLayout, $r3, false);
            }
        }

        class C01733 implements OnItemClickListener {
            final /* synthetic */ AlertController val$dialog;

            C01733(AlertController $r2) throws  {
                this.val$dialog = $r2;
            }

            public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View v, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                AlertParams.this.mOnClickListener.onClick(this.val$dialog.mDialog, $i0);
                if (!AlertParams.this.mIsSingleChoice) {
                    this.val$dialog.mDialog.dismiss();
                }
            }
        }

        class C01744 implements OnItemClickListener {
            final /* synthetic */ AlertController val$dialog;
            final /* synthetic */ ListView val$listView;

            C01744(ListView $r2, AlertController $r3) throws  {
                this.val$listView = $r2;
                this.val$dialog = $r3;
            }

            public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View v, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                if (AlertParams.this.mCheckedItems != null) {
                    AlertParams.this.mCheckedItems[$i0] = this.val$listView.isItemChecked($i0);
                }
                AlertParams.this.mOnCheckboxClickListener.onClick(this.val$dialog.mDialog, $i0, this.val$listView.isItemChecked($i0));
            }
        }

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView) throws ;
        }

        private void createListView(android.support.v7.app.AlertController r34) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:23:0x00b9
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r33 = this;
            r0 = r33;
            r7 = r0.mInflater;
            r0 = r34;
            r8 = r0.mListLayout;
            r10 = 0;
            r9 = r7.inflate(r8, r10);
            r12 = r9;
            r12 = (android.widget.ListView) r12;
            r11 = r12;
            r0 = r33;
            r13 = r0.mIsMultiChoice;
            if (r13 == 0) goto L_0x00cf;
        L_0x0019:
            r0 = r33;
            r14 = r0.mCursor;
            if (r14 != 0) goto L_0x00a7;
        L_0x001f:
            r15 = new android.support.v7.app.AlertController$AlertParams$1;
            r16 = r15;
            r0 = r33;
            r0 = r0.mContext;
            r17 = r0;
            r0 = r34;
            r8 = r0.mMultiChoiceItemLayout;
            r0 = r33;
            r0 = r0.mItems;
            r18 = r0;
            r19 = 16908308; // 0x1020014 float:2.3877285E-38 double:8.353814E-317;
            r0 = r15;
            r1 = r33;
            r2 = r17;
            r3 = r8;
            r4 = r19;
            r5 = r18;
            r6 = r11;
            r0.<init>(r2, r3, r4, r5, r6);
        L_0x0046:
            r0 = r33;
            r0 = r0.mOnPrepareListViewListener;
            r20 = r0;
            if (r20 == 0) goto L_0x0057;
        L_0x004e:
            r0 = r33;
            r0 = r0.mOnPrepareListViewListener;
            r20 = r0;
            r0.onPrepareListView(r11);
        L_0x0057:
            r0 = r34;
            r1 = r16;
            r0.mAdapter = r1;
            r0 = r33;
            r8 = r0.mCheckedItem;
            r0 = r34;
            r0.mCheckedItem = r8;
            r0 = r33;
            r0 = r0.mOnClickListener;
            r21 = r0;
            if (r21 == 0) goto L_0x0167;
        L_0x006f:
            r22 = new android.support.v7.app.AlertController$AlertParams$3;
            r0 = r22;
            r1 = r33;
            r2 = r34;
            r0.<init>(r2);
            r0 = r22;
            r11.setOnItemClickListener(r0);
        L_0x007f:
            r0 = r33;
            r0 = r0.mOnItemSelectedListener;
            r23 = r0;
            if (r23 == 0) goto L_0x0094;
        L_0x0087:
            goto L_0x008b;
        L_0x0088:
            goto L_0x0046;
        L_0x008b:
            r0 = r33;
            r0 = r0.mOnItemSelectedListener;
            r23 = r0;
            r11.setOnItemSelectedListener(r0);
        L_0x0094:
            r0 = r33;
            r13 = r0.mIsSingleChoice;
            if (r13 == 0) goto L_0x0180;
        L_0x009a:
            r19 = 1;
            r0 = r19;
            r11.setChoiceMode(r0);
        L_0x00a1:
            r0 = r34;
            r0.mListView = r11;
            return;
        L_0x00a7:
            r24 = new android.support.v7.app.AlertController$AlertParams$2;
            r16 = r24;
            r0 = r33;
            r0 = r0.mContext;
            r17 = r0;
            r0 = r33;
            r14 = r0.mCursor;
            goto L_0x00bd;
        L_0x00b6:
            goto L_0x0046;
            goto L_0x00bd;
        L_0x00ba:
            goto L_0x0046;
        L_0x00bd:
            r19 = 0;
            r0 = r24;
            r1 = r33;
            r2 = r17;
            r3 = r14;
            r4 = r19;
            r5 = r11;
            r6 = r34;
            r0.<init>(r2, r3, r4, r5, r6);
            goto L_0x0088;
        L_0x00cf:
            r0 = r33;
            r13 = r0.mIsSingleChoice;
            if (r13 == 0) goto L_0x012a;
        L_0x00d5:
            r0 = r34;
            r8 = r0.mSingleChoiceItemLayout;
        L_0x00db:
            r0 = r33;
            r14 = r0.mCursor;
            if (r14 == 0) goto L_0x0131;
        L_0x00e1:
            r25 = new android.widget.SimpleCursorAdapter;
            r16 = r25;
            r0 = r33;
            r0 = r0.mContext;
            r17 = r0;
            r0 = r33;
            r14 = r0.mCursor;
            r19 = 1;
            r0 = r19;
            r0 = new java.lang.String[r0];
            r26 = r0;
            goto L_0x00ff;
        L_0x00f8:
            goto L_0x007f;
            goto L_0x00ff;
        L_0x00fc:
            goto L_0x00ba;
        L_0x00ff:
            r0 = r33;
            r0 = r0.mLabelColumn;
            r27 = r0;
            r19 = 0;
            r26[r19] = r27;
            r19 = 1;
            r0 = r19;
            r0 = new int[r0];
            r28 = r0;
            goto L_0x0115;
        L_0x0112:
            goto L_0x00a1;
        L_0x0115:
            r19 = 0;
            r29 = 16908308; // 0x1020014 float:2.3877285E-38 double:8.353814E-317;
            r28[r19] = r29;
            r0 = r25;
            r1 = r17;
            r2 = r8;
            r3 = r14;
            r4 = r26;
            r5 = r28;
            r0.<init>(r1, r2, r3, r4, r5);
            goto L_0x00b6;
        L_0x012a:
            r0 = r34;
            r8 = r0.mListItemLayout;
            goto L_0x00db;
        L_0x0131:
            r0 = r33;
            r0 = r0.mAdapter;
            r16 = r0;
            goto L_0x013b;
        L_0x0138:
            goto L_0x00f8;
        L_0x013b:
            if (r16 == 0) goto L_0x0144;
        L_0x013d:
            r0 = r33;
            r0 = r0.mAdapter;
            r16 = r0;
            goto L_0x00fc;
        L_0x0144:
            r30 = new android.support.v7.app.AlertController$CheckedItemAdapter;
            r16 = r30;
            r0 = r33;
            r0 = r0.mContext;
            r17 = r0;
            r0 = r33;
            r0 = r0.mItems;
            r18 = r0;
            goto L_0x0158;
        L_0x0155:
            goto L_0x0046;
        L_0x0158:
            r19 = 16908308; // 0x1020014 float:2.3877285E-38 double:8.353814E-317;
            r0 = r30;
            r1 = r17;
            r2 = r19;
            r3 = r18;
            r0.<init>(r1, r8, r2, r3);
            goto L_0x0155;
        L_0x0167:
            r0 = r33;
            r0 = r0.mOnCheckboxClickListener;
            r31 = r0;
            if (r31 == 0) goto L_0x007f;
        L_0x016f:
            r32 = new android.support.v7.app.AlertController$AlertParams$4;
            r0 = r32;
            r1 = r33;
            r2 = r34;
            r0.<init>(r11, r2);
            r0 = r32;
            r11.setOnItemClickListener(r0);
            goto L_0x0138;
        L_0x0180:
            r0 = r33;
            r13 = r0.mIsMultiChoice;
            if (r13 == 0) goto L_0x00a1;
        L_0x0186:
            r19 = 2;
            r0 = r19;
            r11.setChoiceMode(r0);
            goto L_0x0112;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AlertController.AlertParams.createListView(android.support.v7.app.AlertController):void");
        }

        public AlertParams(Context $r1) throws  {
            this.mContext = $r1;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
        }

        public void apply(AlertController $r1) throws  {
            if (this.mCustomTitleView != null) {
                $r1.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    $r1.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    $r1.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    $r1.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    $r1.setIcon($r1.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                $r1.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                $r1.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null) {
                $r1.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null) {
                $r1.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null);
            }
            if (!(this.mItems == null && this.mCursor == null && this.mAdapter == null)) {
                createListView($r1);
            }
            if (this.mView != null) {
                if (this.mViewSpacingSpecified) {
                    View $r2 = this.mView;
                    $r1.setView($r2, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                    return;
                }
                $r1.setView(this.mView);
            } else if (this.mViewLayoutResId != 0) {
                $r1.setView(this.mViewLayoutResId);
            }
        }
    }

    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface $r1) throws  {
            this.mDialog = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case -3:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) $r1.obj).onClick((DialogInterface) this.mDialog.get(), $r1.what);
                    return;
                case 0:
                    break;
                case 1:
                    ((DialogInterface) $r1.obj).dismiss();
                    return;
                default:
                    break;
            }
        }
    }

    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public boolean hasStableIds() throws  {
            return true;
        }

        public CheckedItemAdapter(Context $r1, int $i0, int $i1, CharSequence[] $r2) throws  {
            super($r1, $i0, $i1, $r2);
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }
    }

    private void setupCustomContent(android.view.ViewGroup r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:14:0x004f in {2, 4, 7, 8, 13, 15, 16, 20, 26, 27} preds:[]
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
        r22 = this;
        r2 = 0;
        r0 = r22;
        r3 = r0.mView;
        if (r3 == 0) goto L_0x007b;
    L_0x0007:
        r0 = r22;
        r3 = r0.mView;
    L_0x000b:
        if (r3 == 0) goto L_0x000e;
    L_0x000d:
        r2 = 1;
    L_0x000e:
        if (r2 == 0) goto L_0x0016;
    L_0x0010:
        r4 = canTextInput(r3);
        if (r4 != 0) goto L_0x0023;
    L_0x0016:
        r0 = r22;
        r5 = r0.mWindow;
        r6 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r7 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r5.setFlags(r6, r7);
    L_0x0023:
        if (r2 == 0) goto L_0x009b;
    L_0x0025:
        r0 = r22;
        r5 = r0.mWindow;
        r8 = android.support.v7.appcompat.C0192R.id.custom;
        r9 = r5.findViewById(r8);
        r11 = r9;
        r11 = (android.widget.FrameLayout) r11;
        r10 = r11;
        r12 = new android.view.ViewGroup$LayoutParams;
        r6 = -1;
        r7 = -1;
        r12.<init>(r6, r7);
        r10.addView(r3, r12);
        r0 = r22;
        r2 = r0.mViewSpacingSpecified;
        if (r2 == 0) goto L_0x005e;
    L_0x0043:
        r0 = r22;
        r13 = r0.mViewSpacingLeft;
        r0 = r22;
        r14 = r0.mViewSpacingTop;
        goto L_0x0053;
    L_0x004c:
        goto L_0x000b;
        goto L_0x0053;
    L_0x0050:
        goto L_0x000b;
    L_0x0053:
        r0 = r22;
        r15 = r0.mViewSpacingRight;
        r0 = r22;
        r8 = r0.mViewSpacingBottom;
        r10.setPadding(r13, r14, r15, r8);
    L_0x005e:
        r0 = r22;
        r0 = r0.mListView;
        r16 = r0;
        if (r16 == 0) goto L_0x00a3;
    L_0x0066:
        r0 = r23;
        r12 = r0.getLayoutParams();
        r18 = r12;
        r18 = (android.widget.LinearLayout.LayoutParams) r18;
        r17 = r18;
        r19 = 0;
        r0 = r19;
        r1 = r17;
        r1.weight = r0;
        return;
    L_0x007b:
        r0 = r22;
        r8 = r0.mViewLayoutResId;
        if (r8 == 0) goto L_0x0099;
    L_0x0081:
        r0 = r22;
        r0 = r0.mContext;
        r20 = r0;
        r21 = android.view.LayoutInflater.from(r0);
        r0 = r22;
        r8 = r0.mViewLayoutResId;
        r6 = 0;
        r0 = r21;
        r1 = r23;
        r3 = r0.inflate(r8, r1, r6);
        goto L_0x004c;
    L_0x0099:
        r3 = 0;
        goto L_0x0050;
    L_0x009b:
        r6 = 8;
        r0 = r23;
        r0.setVisibility(r6);
        return;
    L_0x00a3:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AlertController.setupCustomContent(android.view.ViewGroup):void");
    }

    public AlertController(Context $r1, AppCompatDialog $r2, Window $r3) throws  {
        this.mContext = $r1;
        this.mDialog = $r2;
        this.mWindow = $r3;
        this.mHandler = new ButtonHandler($r2);
        TypedArray $r7 = $r1.obtainStyledAttributes(null, C0192R.styleable.AlertDialog, C0192R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = $r7.getResourceId(C0192R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = $r7.getResourceId(C0192R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.mListLayout = $r7.getResourceId(C0192R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = $r7.getResourceId(C0192R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = $r7.getResourceId(C0192R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = $r7.getResourceId(C0192R.styleable.AlertDialog_listItemLayout, 0);
        $r7.recycle();
        $r2.supportRequestWindowFeature(1);
    }

    static boolean canTextInput(View $r0) throws  {
        if ($r0.onCheckIsTextEditor()) {
            return true;
        }
        if (!($r0 instanceof ViewGroup)) {
            return false;
        }
        ViewGroup $r1 = (ViewGroup) $r0;
        int $i0 = $r1.getChildCount();
        while ($i0 > 0) {
            $i0--;
            if (canTextInput($r1.getChildAt($i0))) {
                return true;
            }
        }
        return false;
    }

    public void installContent() throws  {
        this.mDialog.setContentView(selectContentView());
        setupView();
    }

    private int selectContentView() throws  {
        if (this.mButtonPanelSideLayout == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mTitle = $r1;
        if (this.mTitleView != null) {
            this.mTitleView.setText($r1);
        }
    }

    public void setCustomTitle(View $r1) throws  {
        this.mCustomTitleView = $r1;
    }

    public void setMessage(CharSequence $r1) throws  {
        this.mMessage = $r1;
        if (this.mMessageView != null) {
            this.mMessageView.setText($r1);
        }
    }

    public void setView(int $i0) throws  {
        this.mView = null;
        this.mViewLayoutResId = $i0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View $r1) throws  {
        this.mView = $r1;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        this.mView = $r1;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = $i0;
        this.mViewSpacingTop = $i1;
        this.mViewSpacingRight = $i2;
        this.mViewSpacingBottom = $i3;
    }

    public void setButtonPanelLayoutHint(int $i0) throws  {
        this.mButtonPanelLayoutHint = $i0;
    }

    public void setButton(int $i0, CharSequence $r1, DialogInterface.OnClickListener $r2, Message $r3) throws  {
        if ($r3 == null && $r2 != null) {
            $r3 = this.mHandler.obtainMessage($i0, $r2);
        }
        switch ($i0) {
            case -3:
                this.mButtonNeutralText = $r1;
                this.mButtonNeutralMessage = $r3;
                return;
            case -2:
                this.mButtonNegativeText = $r1;
                this.mButtonNegativeMessage = $r3;
                return;
            case -1:
                this.mButtonPositiveText = $r1;
                this.mButtonPositiveMessage = $r3;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setIcon(int $i0) throws  {
        this.mIcon = null;
        this.mIconId = $i0;
        if (this.mIconView == null) {
            return;
        }
        if ($i0 != 0) {
            this.mIconView.setVisibility(0);
            this.mIconView.setImageResource(this.mIconId);
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public void setIcon(Drawable $r1) throws  {
        this.mIcon = $r1;
        this.mIconId = 0;
        if (this.mIconView == null) {
            return;
        }
        if ($r1 != null) {
            this.mIconView.setVisibility(0);
            this.mIconView.setImageDrawable($r1);
            return;
        }
        this.mIconView.setVisibility(8);
    }

    public int getIconAttributeResId(int $i0) throws  {
        TypedValue $r1 = new TypedValue();
        this.mContext.getTheme().resolveAttribute($i0, $r1, true);
        return $r1.resourceId;
    }

    public ListView getListView() throws  {
        return this.mListView;
    }

    public Button getButton(int $i0) throws  {
        switch ($i0) {
            case -3:
                return this.mButtonNeutral;
            case -2:
                return this.mButtonNegative;
            case -1:
                return this.mButtonPositive;
            default:
                return null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent $r1) throws  {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent($r1);
    }

    public boolean onKeyUp(int keyCode, KeyEvent $r1) throws  {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent($r1);
    }

    @Nullable
    private ViewGroup resolvePanel(@Nullable View $r1, @Nullable View $r2) throws  {
        if ($r1 == null) {
            if ($r2 instanceof ViewStub) {
                $r2 = ((ViewStub) $r2).inflate();
            }
            return (ViewGroup) $r2;
        }
        if ($r2 != null) {
            ViewParent $r5 = $r2.getParent();
            if ($r5 instanceof ViewGroup) {
                ((ViewGroup) $r5).removeView($r2);
            }
        }
        if ($r1 instanceof ViewStub) {
            $r1 = ((ViewStub) $r1).inflate();
        }
        return (ViewGroup) $r1;
    }

    private void setupView() throws  {
        View $r2 = this.mWindow.findViewById(C0192R.id.parentPanel);
        View $r3 = $r2.findViewById(C0192R.id.topPanel);
        View $r4 = $r2.findViewById(C0192R.id.contentPanel);
        View $r5 = $r2.findViewById(C0192R.id.buttonPanel);
        ViewGroup $r6 = (ViewGroup) $r2.findViewById(C0192R.id.customPanel);
        setupCustomContent($r6);
        $r2 = $r6.findViewById(C0192R.id.topPanel);
        View $r7 = $r6.findViewById(C0192R.id.contentPanel);
        View $r8 = $r6.findViewById(C0192R.id.buttonPanel);
        ViewGroup $r9 = resolvePanel($r2, $r3);
        ViewGroup $r10 = resolvePanel($r7, $r4);
        ViewGroup $r11 = resolvePanel($r8, $r5);
        setupContent($r10);
        setupButtons($r11);
        setupTitle($r9);
        Object obj = ($r6 == null || $r6.getVisibility() == 8) ? null : 1;
        Object obj2 = ($r9 == null || $r9.getVisibility() == 8) ? null : 1;
        Object obj3 = ($r11 == null || $r11.getVisibility() == 8) ? null : 1;
        if (obj3 == null && $r10 != null) {
            $r3 = $r10.findViewById(C0192R.id.textSpacerNoButtons);
            if ($r3 != null) {
                $r3.setVisibility(0);
            }
        }
        if (!(obj2 == null || this.mScrollView == null)) {
            this.mScrollView.setClipToPadding(true);
        }
        if (obj == null) {
            if (this.mListView != null) {
                $r6 = this.mListView;
            } else {
                $r6 = this.mScrollView;
            }
            if ($r6 != null) {
                byte b;
                int i;
                if (obj2 != null) {
                    b = (byte) 1;
                } else {
                    b = (byte) 0;
                }
                if (obj3 != null) {
                    i = 2;
                } else {
                    i = 0;
                }
                setScrollIndicators($r10, $r6, b | i, 3);
            }
        }
        ListView $r13 = this.mListView;
        if ($r13 != null && this.mAdapter != null) {
            $r13.setAdapter(this.mAdapter);
            int $i0 = this.mCheckedItem;
            if ($i0 > -1) {
                $r13.setItemChecked($i0, true);
                $r13.setSelection($i0);
            }
        }
    }

    private void setScrollIndicators(ViewGroup $r1, View $r2, int $i0, int $i1) throws  {
        View $r4 = this.mWindow.findViewById(C0192R.id.scrollIndicatorUp);
        View $r5 = $r4;
        View $r6 = this.mWindow.findViewById(C0192R.id.scrollIndicatorDown);
        View $r7 = $r6;
        if (VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators($r2, $i0, $i1);
            if ($r4 != null) {
                $r1.removeView($r4);
            }
            if ($r6 != null) {
                $r1.removeView($r6);
                return;
            }
            return;
        }
        if ($r4 != null && ($i0 & 1) == 0) {
            $r1.removeView($r4);
            $r5 = null;
        }
        if ($r6 != null && ($i0 & 2) == 0) {
            $r1.removeView($r6);
            $r7 = null;
        }
        if ($r5 != null || $r7 != null) {
            $r2 = $r7;
            if (this.mMessage != null) {
                this.mScrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
                    public void onScrollChange(NestedScrollView $r1, int scrollX, int scrollY, int oldScrollX, int oldScrollY) throws  {
                        AlertController.manageScrollIndicators($r1, $r5, $r7);
                    }
                });
                this.mScrollView.post(new Runnable() {
                    public void run() throws  {
                        AlertController.manageScrollIndicators(AlertController.this.mScrollView, $r5, $r7);
                    }
                });
            } else if (this.mListView != null) {
                this.mListView.setOnScrollListener(new OnScrollListener() {
                    public void onScrollStateChanged(AbsListView view, int scrollState) throws  {
                    }

                    public void onScroll(AbsListView $r1, int firstVisibleItem, int visibleItemCount, int totalItemCount) throws  {
                        AlertController.manageScrollIndicators($r1, $r5, $r7);
                    }
                });
                this.mListView.post(new Runnable() {
                    public void run() throws  {
                        AlertController.manageScrollIndicators(AlertController.this.mListView, $r5, $r7);
                    }
                });
            } else {
                if ($r5 != null) {
                    $r1.removeView($r5);
                }
                if ($r2 != null) {
                    $r1.removeView($r2);
                }
            }
        }
    }

    private void setupTitle(ViewGroup $r1) throws  {
        boolean $z0 = false;
        if (this.mCustomTitleView != null) {
            LayoutParams $r2 = new LayoutParams(-1, -2);
            $r1.addView(this.mCustomTitleView, 0, $r2);
            this.mWindow.findViewById(C0192R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (ImageView) this.mWindow.findViewById(16908294);
        if (!TextUtils.isEmpty(this.mTitle)) {
            $z0 = true;
        }
        if ($z0) {
            this.mTitleView = (TextView) this.mWindow.findViewById(C0192R.id.alertTitle);
            this.mTitleView.setText(this.mTitle);
            if (this.mIconId != 0) {
                this.mIconView.setImageResource(this.mIconId);
                return;
            } else if (this.mIcon != null) {
                ImageView $r5 = this.mIconView;
                Drawable $r8 = this.mIcon;
                $r5.setImageDrawable($r8);
                return;
            } else {
                this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                this.mIconView.setVisibility(8);
                return;
            }
        }
        this.mWindow.findViewById(C0192R.id.title_template).setVisibility(8);
        this.mIconView.setVisibility(8);
        $r1.setVisibility(8);
    }

    private void setupContent(ViewGroup $r1) throws  {
        this.mScrollView = (NestedScrollView) this.mWindow.findViewById(C0192R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        this.mMessageView = (TextView) $r1.findViewById(16908299);
        if (this.mMessageView != null) {
            if (this.mMessage != null) {
                this.mMessageView.setText(this.mMessage);
                return;
            }
            this.mMessageView.setVisibility(8);
            this.mScrollView.removeView(this.mMessageView);
            if (this.mListView != null) {
                $r1 = (ViewGroup) this.mScrollView.getParent();
                int $i0 = $r1.indexOfChild(this.mScrollView);
                $r1.removeViewAt($i0);
                $r1.addView(this.mListView, $i0, new LayoutParams(-1, -1));
                return;
            }
            $r1.setVisibility(8);
        }
    }

    private static void manageScrollIndicators(View $r0, View $r1, View $r2) throws  {
        byte $b0 = (byte) 0;
        if ($r1 != null) {
            $r1.setVisibility(ViewCompat.canScrollVertically($r0, -1) ? (byte) 0 : (byte) 4);
        }
        if ($r2 != null) {
            if (!ViewCompat.canScrollVertically($r0, 1)) {
                $b0 = (byte) 4;
            }
            $r2.setVisibility($b0);
        }
    }

    private void setupButtons(ViewGroup $r1) throws  {
        boolean $z0 = false;
        byte $b0 = (byte) 0;
        this.mButtonPositive = (Button) $r1.findViewById(16908313);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            $b0 = (byte) 0 | (byte) 1;
        }
        this.mButtonNegative = (Button) $r1.findViewById(16908314);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            $b0 |= (byte) 2;
        }
        this.mButtonNeutral = (Button) $r1.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            $b0 |= (byte) 4;
        }
        if ($b0 != (byte) 0) {
            $z0 = true;
        }
        if (!$z0) {
            $r1.setVisibility(8);
        }
    }
}
