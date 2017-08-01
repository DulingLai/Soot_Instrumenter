package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.widget.ActivityChooserModel.ActivityChooserModelClient;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import dalvik.annotation.Signature;

public class ActivityChooserView extends ViewGroup implements ActivityChooserModelClient {
    private static final String LOG_TAG = "ActivityChooserView";
    private final LinearLayoutCompat mActivityChooserContent;
    private final Drawable mActivityChooserContentBackground;
    private final ActivityChooserViewAdapter mAdapter;
    private final Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    private final FrameLayout mDefaultActivityButton;
    private final ImageView mDefaultActivityButtonImage;
    private final FrameLayout mExpandActivityOverflowButton;
    private final ImageView mExpandActivityOverflowButtonImage;
    private int mInitialActivityCount;
    private boolean mIsAttachedToWindow;
    private boolean mIsSelectingDefaultActivity;
    private final int mListPopupMaxWidth;
    private ListPopupWindow mListPopupWindow;
    private final DataSetObserver mModelDataSetOberver;
    private OnDismissListener mOnDismissListener;
    private final OnGlobalLayoutListener mOnGlobalLayoutListener;
    ActionProvider mProvider;

    class C02141 extends DataSetObserver {
        C02141() throws  {
        }

        public void onChanged() throws  {
            super.onChanged();
            ActivityChooserView.this.mAdapter.notifyDataSetChanged();
        }

        public void onInvalidated() throws  {
            super.onInvalidated();
            ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
        }
    }

    class C02152 implements OnGlobalLayoutListener {
        C02152() throws  {
        }

        public void onGlobalLayout() throws  {
            if (!ActivityChooserView.this.isShowingPopup()) {
                return;
            }
            if (ActivityChooserView.this.isShown()) {
                ActivityChooserView.this.getListPopupWindow().show();
                if (ActivityChooserView.this.mProvider != null) {
                    ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                    return;
                }
                return;
            }
            ActivityChooserView.this.getListPopupWindow().dismiss();
        }
    }

    class C02174 extends DataSetObserver {
        C02174() throws  {
        }

        public void onChanged() throws  {
            super.onChanged();
            ActivityChooserView.this.updateAppearance();
        }
    }

    private class ActivityChooserViewAdapter extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
        private static final int ITEM_VIEW_TYPE_COUNT = 3;
        private static final int ITEM_VIEW_TYPE_FOOTER = 1;
        public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
        public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
        private ActivityChooserModel mDataModel;
        private boolean mHighlightDefaultActivity;
        private int mMaxActivityCount;
        private boolean mShowDefaultActivity;
        private boolean mShowFooterView;

        public int getViewTypeCount() throws  {
            return 3;
        }

        private ActivityChooserViewAdapter() throws  {
            this.mMaxActivityCount = 4;
        }

        public void setDataModel(ActivityChooserModel $r1) throws  {
            ActivityChooserModel $r4 = ActivityChooserView.this.mAdapter.getDataModel();
            if ($r4 != null && ActivityChooserView.this.isShown()) {
                $r4.unregisterObserver(ActivityChooserView.this.mModelDataSetOberver);
            }
            this.mDataModel = $r1;
            if ($r1 != null && ActivityChooserView.this.isShown()) {
                $r1.registerObserver(ActivityChooserView.this.mModelDataSetOberver);
            }
            notifyDataSetChanged();
        }

        public int getItemViewType(int $i0) throws  {
            return (this.mShowFooterView && $i0 == getCount() - 1) ? 1 : 0;
        }

        public int getCount() throws  {
            int $i0 = this.mDataModel.getActivityCount();
            int $i1 = $i0;
            if (!(this.mShowDefaultActivity || this.mDataModel.getDefaultActivity() == null)) {
                $i1 = $i0 - 1;
            }
            $i1 = Math.min($i1, this.mMaxActivityCount);
            if (this.mShowFooterView) {
                return $i1 + 1;
            }
            return $i1;
        }

        public Object getItem(int $i0) throws  {
            switch (getItemViewType($i0)) {
                case 0:
                    if (!(this.mShowDefaultActivity || this.mDataModel.getDefaultActivity() == null)) {
                        $i0++;
                    }
                    return this.mDataModel.getActivity($i0);
                case 1:
                    return null;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }

        public View getView(int $i0, View $r2, ViewGroup $r1) throws  {
            switch (getItemViewType($i0)) {
                case 0:
                    if ($r2 == null || $r2.getId() != C0192R.id.list_item) {
                        $r2 = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C0192R.layout.abc_activity_chooser_view_list_item, $r1, false);
                    }
                    PackageManager $r10 = ActivityChooserView.this.getContext().getPackageManager();
                    ResolveInfo $r13 = (ResolveInfo) getItem($i0);
                    ((ImageView) $r2.findViewById(C0192R.id.icon)).setImageDrawable($r13.loadIcon($r10));
                    ((TextView) $r2.findViewById(C0192R.id.title)).setText($r13.loadLabel($r10));
                    if (this.mShowDefaultActivity && $i0 == 0 && this.mHighlightDefaultActivity) {
                        ViewCompat.setActivated($r2, true);
                    } else {
                        ViewCompat.setActivated($r2, false);
                    }
                    return $r2;
                case 1:
                    if ($r2 == null || $r2.getId() != 1) {
                        View $r7 = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(C0192R.layout.abc_activity_chooser_view_list_item, $r1, false);
                        $r2 = $r7;
                        $r7.setId(1);
                        ((TextView) $r7.findViewById(C0192R.id.title)).setText(ActivityChooserView.this.getContext().getString(C0192R.string.abc_activity_chooser_view_see_all));
                    }
                    return $r2;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public int measureContentWidth() throws  {
            int $i0 = this.mMaxActivityCount;
            this.mMaxActivityCount = MAX_ACTIVITY_COUNT_UNLIMITED;
            int $i1 = 0;
            View $r1 = null;
            int $i2 = MeasureSpec.makeMeasureSpec(0, 0);
            int $i3 = MeasureSpec.makeMeasureSpec(0, 0);
            int $i4 = getCount();
            for (int $i5 = 0; $i5 < $i4; $i5++) {
                View $r2 = getView($i5, $r1, null);
                $r1 = $r2;
                $r2.measure($i2, $i3);
                $i1 = Math.max($i1, $r2.getMeasuredWidth());
            }
            this.mMaxActivityCount = $i0;
            return $i1;
        }

        public void setMaxActivityCount(int $i0) throws  {
            if (this.mMaxActivityCount != $i0) {
                this.mMaxActivityCount = $i0;
                notifyDataSetChanged();
            }
        }

        public ResolveInfo getDefaultActivity() throws  {
            return this.mDataModel.getDefaultActivity();
        }

        public void setShowFooterView(boolean $z0) throws  {
            if (this.mShowFooterView != $z0) {
                this.mShowFooterView = $z0;
                notifyDataSetChanged();
            }
        }

        public int getActivityCount() throws  {
            return this.mDataModel.getActivityCount();
        }

        public int getHistorySize() throws  {
            return this.mDataModel.getHistorySize();
        }

        public ActivityChooserModel getDataModel() throws  {
            return this.mDataModel;
        }

        public void setShowDefaultActivity(boolean $z0, boolean $z1) throws  {
            if (this.mShowDefaultActivity != $z0 || this.mHighlightDefaultActivity != $z1) {
                this.mShowDefaultActivity = $z0;
                this.mHighlightDefaultActivity = $z1;
                notifyDataSetChanged();
            }
        }

        public boolean getShowDefaultActivity() throws  {
            return this.mShowDefaultActivity;
        }
    }

    private class Callbacks implements OnClickListener, OnLongClickListener, OnItemClickListener, OnDismissListener {
        private Callbacks() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> $r1, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i1, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            switch (((ActivityChooserViewAdapter) $r1.getAdapter()).getItemViewType($i1)) {
                case 0:
                    ActivityChooserView.this.dismissPopup();
                    if (!ActivityChooserView.this.mIsSelectingDefaultActivity) {
                        if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                            $i1++;
                        }
                        Intent $r8 = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity($i1);
                        if ($r8 != null) {
                            $r8.addFlags(524288);
                            ActivityChooserView.this.getContext().startActivity($r8);
                            return;
                        }
                        return;
                    } else if ($i1 > 0) {
                        ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity($i1);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    ActivityChooserView.this.showPopupUnchecked(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void onClick(View $r1) throws  {
            if ($r1 == ActivityChooserView.this.mDefaultActivityButton) {
                ActivityChooserView.this.dismissPopup();
                Intent $r7 = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex(ActivityChooserView.this.mAdapter.getDefaultActivity()));
                if ($r7 != null) {
                    $r7.addFlags(524288);
                    ActivityChooserView.this.getContext().startActivity($r7);
                }
            } else if ($r1 == ActivityChooserView.this.mExpandActivityOverflowButton) {
                ActivityChooserView.this.mIsSelectingDefaultActivity = false;
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public boolean onLongClick(View $r1) throws  {
            if ($r1 != ActivityChooserView.this.mDefaultActivityButton) {
                throw new IllegalArgumentException();
            } else if (ActivityChooserView.this.mAdapter.getCount() <= 0) {
                return true;
            } else {
                ActivityChooserView.this.mIsSelectingDefaultActivity = true;
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount);
                return true;
            }
        }

        public void onDismiss() throws  {
            notifyOnDismissListener();
            if (ActivityChooserView.this.mProvider != null) {
                ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
            }
        }

        private void notifyOnDismissListener() throws  {
            if (ActivityChooserView.this.mOnDismissListener != null) {
                ActivityChooserView.this.mOnDismissListener.onDismiss();
            }
        }
    }

    public static class InnerLayout extends LinearLayoutCompat {
        private static final int[] TINT_ATTRS = new int[]{16842964};

        public InnerLayout(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            TintTypedArray $r4 = TintTypedArray.obtainStyledAttributes($r1, $r2, TINT_ATTRS);
            setBackgroundDrawable($r4.getDrawable(0));
            $r4.recycle();
        }
    }

    public ActivityChooserView(Context $r1) throws  {
        this($r1, null);
    }

    public ActivityChooserView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public ActivityChooserView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mModelDataSetOberver = new C02141();
        this.mOnGlobalLayoutListener = new C02152();
        this.mInitialActivityCount = 4;
        TypedArray $r6 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ActivityChooserView, $i0, 0);
        this.mInitialActivityCount = $r6.getInt(C0192R.styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable $r7 = $r6.getDrawable(C0192R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        $r6.recycle();
        LayoutInflater.from(getContext()).inflate(C0192R.layout.abc_activity_chooser_view, this, true);
        ActivityChooserView activityChooserView = this;
        this.mCallbacks = new Callbacks();
        this.mActivityChooserContent = (LinearLayoutCompat) findViewById(C0192R.id.activity_chooser_view_content);
        this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
        this.mDefaultActivityButton = (FrameLayout) findViewById(C0192R.id.default_activity_button);
        this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
        this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
        this.mDefaultActivityButtonImage = (ImageView) this.mDefaultActivityButton.findViewById(C0192R.id.image);
        View $r14 = (FrameLayout) findViewById(C0192R.id.expand_activities_button);
        $r14.setOnClickListener(this.mCallbacks);
        $r14.setOnTouchListener(new ForwardingListener($r14) {
            public ListPopupWindow getPopup() throws  {
                return ActivityChooserView.this.getListPopupWindow();
            }

            protected boolean onForwardingStarted() throws  {
                ActivityChooserView.this.showPopup();
                return true;
            }

            protected boolean onForwardingStopped() throws  {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = $r14;
        this.mExpandActivityOverflowButtonImage = (ImageView) $r14.findViewById(C0192R.id.image);
        ImageView $r15 = this.mExpandActivityOverflowButtonImage;
        $r15.setImageDrawable($r7);
        ActivityChooserView activityChooserView2 = this;
        this.mAdapter = new ActivityChooserViewAdapter();
        this.mAdapter.registerDataSetObserver(new C02174());
        Resources $r19 = $r1.getResources();
        this.mListPopupMaxWidth = Math.max($r19.getDisplayMetrics().widthPixels / 2, $r19.getDimensionPixelSize(C0192R.dimen.abc_config_prefDialogWidth));
    }

    public void setActivityChooserModel(ActivityChooserModel $r1) throws  {
        this.mAdapter.setDataModel($r1);
        if (isShowingPopup()) {
            dismissPopup();
            showPopup();
        }
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable $r1) throws  {
        this.mExpandActivityOverflowButtonImage.setImageDrawable($r1);
    }

    public void setExpandActivityOverflowButtonContentDescription(int $i0) throws  {
        this.mExpandActivityOverflowButtonImage.setContentDescription(getContext().getString($i0));
    }

    public void setProvider(ActionProvider $r1) throws  {
        this.mProvider = $r1;
    }

    public boolean showPopup() throws  {
        if (isShowingPopup()) {
            return false;
        }
        if (!this.mIsAttachedToWindow) {
            return false;
        }
        this.mIsSelectingDefaultActivity = false;
        showPopupUnchecked(this.mInitialActivityCount);
        return true;
    }

    private void showPopupUnchecked(int $i0) throws  {
        if (this.mAdapter.getDataModel() == null) {
            throw new IllegalStateException("No data model. Did you call #setDataModel?");
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        boolean $z0 = this.mDefaultActivityButton.getVisibility() == 0;
        int $i1 = this.mAdapter.getActivityCount();
        byte $b2;
        if ($z0) {
            $b2 = (byte) 1;
        } else {
            $b2 = (byte) 0;
        }
        if ($i0 == Integer.MAX_VALUE || $i1 <= $i0 + $b2) {
            this.mAdapter.setShowFooterView(false);
            this.mAdapter.setMaxActivityCount($i0);
        } else {
            this.mAdapter.setShowFooterView(true);
            this.mAdapter.setMaxActivityCount($i0 - 1);
        }
        ListPopupWindow $r7 = getListPopupWindow();
        if (!$r7.isShowing()) {
            if (this.mIsSelectingDefaultActivity || !$z0) {
                this.mAdapter.setShowDefaultActivity(true, $z0);
            } else {
                this.mAdapter.setShowDefaultActivity(false, false);
            }
            $r7.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
            $r7.show();
            if (this.mProvider != null) {
                this.mProvider.subUiVisibilityChanged(true);
            }
            $r7.getListView().setContentDescription(getContext().getString(C0192R.string.abc_activitychooserview_choose_application));
        }
    }

    public boolean dismissPopup() throws  {
        if (isShowingPopup()) {
            getListPopupWindow().dismiss();
            ViewTreeObserver $r2 = getViewTreeObserver();
            if ($r2.isAlive()) {
                $r2.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
            }
        }
        return true;
    }

    public boolean isShowingPopup() throws  {
        return getListPopupWindow().isShowing();
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        ActivityChooserModel $r1 = this.mAdapter.getDataModel();
        if ($r1 != null) {
            $r1.registerObserver(this.mModelDataSetOberver);
        }
        this.mIsAttachedToWindow = true;
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        ActivityChooserModel $r2 = this.mAdapter.getDataModel();
        if ($r2 != null) {
            $r2.unregisterObserver(this.mModelDataSetOberver);
        }
        ViewTreeObserver $r4 = getViewTreeObserver();
        if ($r4.isAlive()) {
            $r4.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (isShowingPopup()) {
            dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        LinearLayoutCompat $r1 = this.mActivityChooserContent;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            $i1 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize($i1), 1073741824);
        }
        measureChild($r1, $i0, $i1);
        setMeasuredDimension($r1.getMeasuredWidth(), $r1.getMeasuredHeight());
    }

    protected void onLayout(boolean changed, int $i0, int $i1, int $i2, int $i3) throws  {
        this.mActivityChooserContent.layout(0, 0, $i2 - $i0, $i3 - $i1);
        if (!isShowingPopup()) {
            dismissPopup();
        }
    }

    public ActivityChooserModel getDataModel() throws  {
        return this.mAdapter.getDataModel();
    }

    public void setOnDismissListener(OnDismissListener $r1) throws  {
        this.mOnDismissListener = $r1;
    }

    public void setInitialActivityCount(int $i0) throws  {
        this.mInitialActivityCount = $i0;
    }

    public void setDefaultActionButtonContentDescription(int $i0) throws  {
        this.mDefaultActionButtonContentDescription = $i0;
    }

    private ListPopupWindow getListPopupWindow() throws  {
        if (this.mListPopupWindow == null) {
            this.mListPopupWindow = new ListPopupWindow(getContext());
            this.mListPopupWindow.setAdapter(this.mAdapter);
            this.mListPopupWindow.setAnchorView(this);
            this.mListPopupWindow.setModal(true);
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        }
        return this.mListPopupWindow;
    }

    private void updateAppearance() throws  {
        if (this.mAdapter.getCount() > 0) {
            this.mExpandActivityOverflowButton.setEnabled(true);
        } else {
            this.mExpandActivityOverflowButton.setEnabled(false);
        }
        int $i0 = this.mAdapter.getActivityCount();
        int $i1 = this.mAdapter.getHistorySize();
        if ($i0 == 1 || ($i0 > 1 && $i1 > 0)) {
            this.mDefaultActivityButton.setVisibility(0);
            ResolveInfo $r3 = this.mAdapter.getDefaultActivity();
            PackageManager $r5 = getContext().getPackageManager();
            this.mDefaultActivityButtonImage.setImageDrawable($r3.loadIcon($r5));
            if (this.mDefaultActionButtonContentDescription != 0) {
                CharSequence $r8 = $r3.loadLabel($r5);
                this.mDefaultActivityButton.setContentDescription(getContext().getString(this.mDefaultActionButtonContentDescription, new Object[]{$r8}));
            }
        } else {
            this.mDefaultActivityButton.setVisibility(8);
        }
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
        } else {
            this.mActivityChooserContent.setBackgroundDrawable(null);
        }
    }
}
