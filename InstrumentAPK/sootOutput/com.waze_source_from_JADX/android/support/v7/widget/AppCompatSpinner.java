package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;
import dalvik.annotation.Signature;

public class AppCompatSpinner extends Spinner implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE = new int[]{16843505};
    private static final boolean IS_AT_LEAST_JB;
    private static final boolean IS_AT_LEAST_M;
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatDrawableManager mDrawableManager;
    private int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    private DropdownPopup mPopup;
    private Context mPopupContext;
    private boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    private final Rect mTempRect;

    class C02201 extends ForwardingListener {
        final /* synthetic */ DropdownPopup val$popup;

        C02201(View $r2, DropdownPopup $r3) throws  {
            this.val$popup = $r3;
            super($r2);
        }

        public ListPopupWindow getPopup() throws  {
            return this.val$popup;
        }

        public boolean onForwardingStarted() throws  {
            if (!AppCompatSpinner.this.mPopup.isShowing()) {
                AppCompatSpinner.this.mPopup.show();
            }
            return true;
        }
    }

    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public int getItemViewType(int position) throws  {
            return 0;
        }

        public int getViewTypeCount() throws  {
            return 1;
        }

        public DropDownAdapter(@Nullable SpinnerAdapter $r1, @Nullable Theme $r2) throws  {
            this.mAdapter = $r1;
            if ($r1 instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) $r1;
            }
            if ($r2 == null) {
                return;
            }
            if (AppCompatSpinner.IS_AT_LEAST_M && ($r1 instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter $r4 = (ThemedSpinnerAdapter) $r1;
                if ($r4.getDropDownViewTheme() != $r2) {
                    $r4.setDropDownViewTheme($r2);
                }
            } else if ($r1 instanceof ThemedSpinnerAdapter) {
                ThemedSpinnerAdapter $r6 = (ThemedSpinnerAdapter) $r1;
                if ($r6.getDropDownViewTheme() == null) {
                    $r6.setDropDownViewTheme($r2);
                }
            }
        }

        public int getCount() throws  {
            return this.mAdapter == null ? 0 : this.mAdapter.getCount();
        }

        public Object getItem(int $i0) throws  {
            return this.mAdapter == null ? null : this.mAdapter.getItem($i0);
        }

        public long getItemId(int $i0) throws  {
            return this.mAdapter == null ? -1 : this.mAdapter.getItemId($i0);
        }

        public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
            return getDropDownView($i0, $r1, $r2);
        }

        public View getDropDownView(int $i0, View $r1, ViewGroup $r2) throws  {
            return this.mAdapter == null ? null : this.mAdapter.getDropDownView($i0, $r1, $r2);
        }

        public boolean hasStableIds() throws  {
            return this.mAdapter != null && this.mAdapter.hasStableIds();
        }

        public void registerDataSetObserver(DataSetObserver $r1) throws  {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver($r1);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver $r1) throws  {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver($r1);
            }
        }

        public boolean areAllItemsEnabled() throws  {
            ListAdapter $r1 = this.mListAdapter;
            if ($r1 != null) {
                return $r1.areAllItemsEnabled();
            }
            return true;
        }

        public boolean isEnabled(int $i0) throws  {
            ListAdapter $r1 = this.mListAdapter;
            if ($r1 != null) {
                return $r1.isEnabled($i0);
            }
            return true;
        }

        public boolean isEmpty() throws  {
            return getCount() == 0;
        }
    }

    private class DropdownPopup extends ListPopupWindow {
        private ListAdapter mAdapter;
        private CharSequence mHintText;
        private final Rect mVisibleRect = new Rect();

        class C02222 implements OnGlobalLayoutListener {
            C02222() throws  {
            }

            public void onGlobalLayout() throws  {
                if (DropdownPopup.this.isVisibleToUser(AppCompatSpinner.this)) {
                    DropdownPopup.this.computeContentWidth();
                    super.show();
                    return;
                }
                DropdownPopup.this.dismiss();
            }
        }

        public DropdownPopup(Context $r2, AttributeSet $r3, int $i0) throws  {
            super($r2, $r3, $i0);
            setAnchorView(AppCompatSpinner.this);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new OnItemClickListener(AppCompatSpinner.this) {
                public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                    AppCompatSpinner.this.setSelection($i0);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick($r2, $i0, DropdownPopup.this.mAdapter.getItemId($i0));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        public void setAdapter(ListAdapter $r1) throws  {
            super.setAdapter($r1);
            this.mAdapter = $r1;
        }

        public CharSequence getHintText() throws  {
            return this.mHintText;
        }

        public void setPromptText(CharSequence $r1) throws  {
            this.mHintText = $r1;
        }

        void computeContentWidth() throws  {
            Drawable $r1 = getBackground();
            int $i1 = 0;
            if ($r1 != null) {
                $r1.getPadding(AppCompatSpinner.this.mTempRect);
                $i1 = ViewUtils.isLayoutRtl(AppCompatSpinner.this) ? AppCompatSpinner.this.mTempRect.right : -AppCompatSpinner.this.mTempRect.left;
            } else {
                Rect $r3 = AppCompatSpinner.this.mTempRect;
                AppCompatSpinner.this.mTempRect.right = 0;
                $r3.left = 0;
            }
            int $i2 = AppCompatSpinner.this.getPaddingLeft();
            int $i3 = AppCompatSpinner.this.getPaddingRight();
            int $i4 = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int $i6 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, getBackground());
                int $i5 = $i6;
                int i = (AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left) - AppCompatSpinner.this.mTempRect.right;
                int i2 = i;
                if ($i6 > i) {
                    $i5 = i2;
                }
                setContentWidth(Math.max($i5, ($i4 - $i2) - $i3));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                setContentWidth(($i4 - $i2) - $i3);
            } else {
                setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
                $i1 += ($i4 - $i3) - getWidth();
            } else {
                $i1 += $i2;
            }
            setHorizontalOffset($i1);
        }

        public void show() throws  {
            boolean $z0 = isShowing();
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            getListView().setChoiceMode(1);
            setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (!$z0) {
                ViewTreeObserver $r4 = AppCompatSpinner.this.getViewTreeObserver();
                if ($r4 != null) {
                    final C02222 $r1 = new C02222();
                    $r4.addOnGlobalLayoutListener($r1);
                    setOnDismissListener(new OnDismissListener() {
                        public void onDismiss() throws  {
                            ViewTreeObserver $r1 = AppCompatSpinner.this.getViewTreeObserver();
                            if ($r1 != null) {
                                $r1.removeGlobalOnLayoutListener($r1);
                            }
                        }
                    });
                }
            }
        }

        private boolean isVisibleToUser(View $r1) throws  {
            return ViewCompat.isAttachedToWindow($r1) && $r1.getGlobalVisibleRect(this.mVisibleRect);
        }
    }

    public AppCompatSpinner(android.content.Context r31, android.util.AttributeSet r32, int r33, int r34, android.content.res.Resources.Theme r35) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:49:0x0193 in {2, 13, 15, 18, 21, 24, 26, 30, 34, 35, 37, 43, 47, 48, 50} preds:[]
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
        r30 = this;
        r0 = r30;
        r1 = r31;
        r2 = r32;
        r3 = r33;
        r0.<init>(r1, r2, r3);
        r4 = new android.graphics.Rect;
        r4.<init>();
        r0 = r30;
        r0.mTempRect = r4;
        r5 = android.support.v7.appcompat.C0192R.styleable.Spinner;
        r7 = 0;
        r0 = r31;
        r1 = r32;
        r2 = r33;
        r6 = android.support.v7.widget.TintTypedArray.obtainStyledAttributes(r0, r1, r5, r2, r7);
        r8 = android.support.v7.widget.AppCompatDrawableManager.get();
        r0 = r30;
        r0.mDrawableManager = r8;
        r9 = new android.support.v7.widget.AppCompatBackgroundHelper;
        r0 = r30;
        r8 = r0.mDrawableManager;
        r0 = r30;
        r9.<init>(r0, r8);
        r0 = r30;
        r0.mBackgroundTintHelper = r9;
        if (r35 == 0) goto L_0x014a;
    L_0x003a:
        r10 = new android.support.v7.view.ContextThemeWrapper;
        r0 = r31;
        r1 = r35;
        r10.<init>(r0, r1);
        r0 = r30;
        r0.mPopupContext = r10;
    L_0x0047:
        r0 = r30;
        r11 = r0.mPopupContext;
        if (r11 == 0) goto L_0x00f1;
    L_0x004d:
        r7 = -1;
        r0 = r34;
        if (r0 != r7) goto L_0x007d;
    L_0x0052:
        r12 = android.os.Build.VERSION.SDK_INT;
        r7 = 11;
        if (r12 < r7) goto L_0x0197;
    L_0x0058:
        r13 = 0;
        r5 = ATTRS_ANDROID_SPINNERMODE;
        r7 = 0;	 Catch:{ Exception -> 0x0174 }
        r0 = r31;	 Catch:{ Exception -> 0x0174 }
        r1 = r32;	 Catch:{ Exception -> 0x0174 }
        r2 = r33;	 Catch:{ Exception -> 0x0174 }
        r14 = r0.obtainStyledAttributes(r1, r5, r2, r7);	 Catch:{ Exception -> 0x0174 }
        r13 = r14;	 Catch:{ Exception -> 0x0174 }
        r7 = 0;	 Catch:{ Exception -> 0x0174 }
        r15 = r14.hasValue(r7);	 Catch:{ Exception -> 0x0174 }
        if (r15 == 0) goto L_0x0078;	 Catch:{ Exception -> 0x0174 }
    L_0x006f:
        r7 = 0;	 Catch:{ Exception -> 0x0174 }
        r16 = 0;	 Catch:{ Exception -> 0x0174 }
        r0 = r16;	 Catch:{ Exception -> 0x0174 }
        r34 = r14.getInt(r7, r0);	 Catch:{ Exception -> 0x0174 }
    L_0x0078:
        if (r14 == 0) goto L_0x007d;
    L_0x007a:
        r14.recycle();
    L_0x007d:
        r7 = 1;
        r0 = r34;
        if (r0 != r7) goto L_0x00f1;
    L_0x0082:
        r17 = new android.support.v7.widget.AppCompatSpinner$DropdownPopup;
        r0 = r30;
        r11 = r0.mPopupContext;
        r0 = r17;
        r1 = r30;
        r2 = r32;
        r3 = r33;
        r0.<init>(r11, r2, r3);
        r0 = r30;
        r11 = r0.mPopupContext;
        r5 = android.support.v7.appcompat.C0192R.styleable.Spinner;
        r7 = 0;
        r0 = r32;
        r1 = r33;
        r18 = android.support.v7.widget.TintTypedArray.obtainStyledAttributes(r11, r0, r5, r1, r7);
        r34 = android.support.v7.appcompat.C0192R.styleable.Spinner_android_dropDownWidth;
        r7 = -2;
        r0 = r18;
        r1 = r34;
        r34 = r0.getLayoutDimension(r1, r7);
        r0 = r34;
        r1 = r30;
        r1.mDropDownWidth = r0;
        r34 = android.support.v7.appcompat.C0192R.styleable.Spinner_android_popupBackground;
        r0 = r18;
        r1 = r34;
        r19 = r0.getDrawable(r1);
        r0 = r17;
        r1 = r19;
        r0.setBackgroundDrawable(r1);
        r34 = android.support.v7.appcompat.C0192R.styleable.Spinner_android_prompt;
        r0 = r34;
        r20 = r6.getString(r0);
        r0 = r17;
        r1 = r20;
        r0.setPromptText(r1);
        r0 = r18;
        r0.recycle();
        r0 = r17;
        r1 = r30;
        r1.mPopup = r0;
        r21 = new android.support.v7.widget.AppCompatSpinner$1;
        r0 = r21;
        r1 = r30;
        r2 = r30;
        r3 = r17;
        r0.<init>(r2, r3);
        r0 = r21;
        r1 = r30;
        r1.mForwardingListener = r0;
    L_0x00f1:
        r34 = android.support.v7.appcompat.C0192R.styleable.Spinner_android_entries;
        r0 = r34;
        r22 = r6.getTextArray(r0);
        if (r22 == 0) goto L_0x0119;
    L_0x00fb:
        r23 = new android.widget.ArrayAdapter;
        r7 = 17367048; // 0x1090008 float:2.5162948E-38 double:8.580462E-317;
        r0 = r23;
        r1 = r31;
        r2 = r22;
        r0.<init>(r1, r7, r2);
        r34 = android.support.v7.appcompat.C0192R.layout.support_simple_spinner_dropdown_item;
        r0 = r23;
        r1 = r34;
        r0.setDropDownViewResource(r1);
        r0 = r30;
        r1 = r23;
        r0.setAdapter(r1);
    L_0x0119:
        r6.recycle();
        r7 = 1;
        r0 = r30;
        r0.mPopupSet = r7;
        r0 = r30;
        r0 = r0.mTempAdapter;
        r24 = r0;
        if (r24 == 0) goto L_0x013e;
    L_0x0129:
        r0 = r30;
        r0 = r0.mTempAdapter;
        r24 = r0;
        r0 = r30;
        r1 = r24;
        r0.setAdapter(r1);
        r25 = 0;
        r0 = r25;
        r1 = r30;
        r1.mTempAdapter = r0;
    L_0x013e:
        r0 = r30;
        r9 = r0.mBackgroundTintHelper;
        r0 = r32;
        r1 = r33;
        r9.loadFromAttributes(r0, r1);
        return;
    L_0x014a:
        r12 = android.support.v7.appcompat.C0192R.styleable.Spinner_popupTheme;
        r7 = 0;
        r12 = r6.getResourceId(r12, r7);
        if (r12 == 0) goto L_0x0163;
    L_0x0153:
        r10 = new android.support.v7.view.ContextThemeWrapper;
        r0 = r31;
        r10.<init>(r0, r12);
        goto L_0x015e;
    L_0x015b:
        goto L_0x0047;
    L_0x015e:
        r0 = r30;
        r0.mPopupContext = r10;
        goto L_0x015b;
    L_0x0163:
        r15 = IS_AT_LEAST_M;
        if (r15 != 0) goto L_0x0172;
    L_0x0167:
        r11 = r31;
        goto L_0x016d;
    L_0x016a:
        goto L_0x0047;
    L_0x016d:
        r0 = r30;
        r0.mPopupContext = r11;
        goto L_0x016a;
    L_0x0172:
        r11 = 0;
        goto L_0x016d;
    L_0x0174:
        r26 = move-exception;
        r27 = "AppCompatSpinner";	 Catch:{ Throwable -> 0x018c }
        r28 = "Could not read android:spinnerMode";	 Catch:{ Throwable -> 0x018c }
        r0 = r27;	 Catch:{ Throwable -> 0x018c }
        r1 = r28;	 Catch:{ Throwable -> 0x018c }
        r2 = r26;	 Catch:{ Throwable -> 0x018c }
        android.util.Log.i(r0, r1, r2);	 Catch:{ Throwable -> 0x018c }
        if (r13 == 0) goto L_0x007d;
    L_0x0184:
        goto L_0x0188;
    L_0x0185:
        goto L_0x007d;
    L_0x0188:
        r13.recycle();
        goto L_0x0185;
    L_0x018c:
        r29 = move-exception;
        if (r13 == 0) goto L_0x0192;
    L_0x018f:
        r13.recycle();
    L_0x0192:
        throw r29;
        goto L_0x0197;
    L_0x0194:
        goto L_0x007d;
    L_0x0197:
        r34 = 1;
        goto L_0x0194;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatSpinner.<init>(android.content.Context, android.util.AttributeSet, int, int, android.content.res.Resources$Theme):void");
    }

    static {
        boolean $z0;
        if (VERSION.SDK_INT >= 23) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        IS_AT_LEAST_M = $z0;
        if (VERSION.SDK_INT >= 16) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        IS_AT_LEAST_JB = $z0;
    }

    public AppCompatSpinner(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatSpinner(Context $r1, int $i0) throws  {
        this($r1, null, C0192R.attr.spinnerStyle, $i0);
    }

    public AppCompatSpinner(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context $r1, AttributeSet $r2, int $i0) throws  {
        this($r1, $r2, $i0, -1);
    }

    public AppCompatSpinner(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        this($r1, $r2, $i0, $i1, null);
    }

    public Context getPopupContext() throws  {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        return IS_AT_LEAST_M ? super.getPopupContext() : null;
    }

    public void setPopupBackgroundDrawable(Drawable $r1) throws  {
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable($r1);
        } else if (IS_AT_LEAST_JB) {
            super.setPopupBackgroundDrawable($r1);
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int $i0) throws  {
        setPopupBackgroundDrawable(ContextCompat.getDrawable(getPopupContext(), $i0));
    }

    public Drawable getPopupBackground() throws  {
        if (this.mPopup != null) {
            return this.mPopup.getBackground();
        }
        return IS_AT_LEAST_JB ? super.getPopupBackground() : null;
    }

    public void setDropDownVerticalOffset(int $i0) throws  {
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset($i0);
        } else if (IS_AT_LEAST_JB) {
            super.setDropDownVerticalOffset($i0);
        }
    }

    public int getDropDownVerticalOffset() throws  {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset();
        }
        return IS_AT_LEAST_JB ? super.getDropDownVerticalOffset() : 0;
    }

    public void setDropDownHorizontalOffset(int $i0) throws  {
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset($i0);
        } else if (IS_AT_LEAST_JB) {
            super.setDropDownHorizontalOffset($i0);
        }
    }

    public int getDropDownHorizontalOffset() throws  {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset();
        }
        return IS_AT_LEAST_JB ? super.getDropDownHorizontalOffset() : 0;
    }

    public void setDropDownWidth(int $i0) throws  {
        if (this.mPopup != null) {
            this.mDropDownWidth = $i0;
        } else if (IS_AT_LEAST_JB) {
            super.setDropDownWidth($i0);
        }
    }

    public int getDropDownWidth() throws  {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        return IS_AT_LEAST_JB ? super.getDropDownWidth() : 0;
    }

    public void setAdapter(SpinnerAdapter $r1) throws  {
        if (this.mPopupSet) {
            super.setAdapter($r1);
            if (this.mPopup != null) {
                this.mPopup.setAdapter(new DropDownAdapter($r1, (this.mPopupContext == null ? getContext() : this.mPopupContext).getTheme()));
                return;
            }
            return;
        }
        this.mTempAdapter = $r1;
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        return (this.mForwardingListener == null || !this.mForwardingListener.onTouch(this, $r1)) ? super.onTouchEvent($r1) : true;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        if (this.mPopup != null && MeasureSpec.getMode($i0) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), MeasureSpec.getSize($i0)), getMeasuredHeight());
        }
    }

    public boolean performClick() throws  {
        if (this.mPopup == null) {
            return super.performClick();
        }
        if (!this.mPopup.isShowing()) {
            this.mPopup.show();
        }
        return true;
    }

    public void setPrompt(CharSequence $r1) throws  {
        if (this.mPopup != null) {
            this.mPopup.setPromptText($r1);
        } else {
            super.setPrompt($r1);
        }
    }

    public CharSequence getPrompt() throws  {
        return this.mPopup != null ? this.mPopup.getHintText() : super.getPrompt();
    }

    public void setBackgroundResource(@DrawableRes int $i0) throws  {
        super.setBackgroundResource($i0);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource($i0);
        }
    }

    public void setBackgroundDrawable(Drawable $r1) throws  {
        super.setBackgroundDrawable($r1);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable($r1);
        }
    }

    public void setSupportBackgroundTintList(@Nullable ColorStateList $r1) throws  {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList($r1);
        }
    }

    @Nullable
    public ColorStateList getSupportBackgroundTintList() throws  {
        return this.mBackgroundTintHelper != null ? this.mBackgroundTintHelper.getSupportBackgroundTintList() : null;
    }

    public void setSupportBackgroundTintMode(@Nullable Mode $r1) throws  {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode($r1);
        }
    }

    @Nullable
    public Mode getSupportBackgroundTintMode() throws  {
        return this.mBackgroundTintHelper != null ? this.mBackgroundTintHelper.getSupportBackgroundTintMode() : null;
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }

    private int compatMeasureContentWidth(SpinnerAdapter $r1, Drawable $r2) throws  {
        if ($r1 == null) {
            return 0;
        }
        int $i1 = 0;
        View $r3 = null;
        int $i2 = 0;
        int $i3 = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int $i4 = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int $i6 = Math.max(0, getSelectedItemPosition());
        int $i5 = Math.min($r1.getCount(), $i6 + 15);
        for ($i6 = Math.max(0, $i6 - (15 - ($i5 - $i6))); $i6 < $i5; $i6++) {
            int $i0 = $r1.getItemViewType($i6);
            if ($i0 != $i2) {
                $i2 = $i0;
                $r3 = null;
            }
            View $r4 = $r1.getView($i6, $r3, this);
            $r3 = $r4;
            if ($r4.getLayoutParams() == null) {
                $r4.setLayoutParams(new LayoutParams(-2, -2));
            }
            $r4.measure($i3, $i4);
            $i1 = Math.max($i1, $r4.getMeasuredWidth());
        }
        if ($r2 == null) {
            return $i1;
        }
        $r2.getPadding(this.mTempRect);
        return $i1 + (this.mTempRect.left + this.mTempRect.right);
    }
}
