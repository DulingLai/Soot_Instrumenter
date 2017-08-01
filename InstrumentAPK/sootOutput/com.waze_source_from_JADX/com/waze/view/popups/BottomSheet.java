package com.waze.view.popups;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.utils.PixelMeasure;

public class BottomSheet extends Dialog implements ITrackOrientation {
    public static final int DEFAULT_GRID_CELLS = 3;
    public static final float DISABLED_ALPHA = 0.3f;
    public static final float ENABLED_ALPHA = 1.0f;
    public static final int MIN_GRID_WIDTH_DP = 110;
    public static final int ROWS_MAX_PERCENT = 40;
    private OnCancelListener cancelListener;
    ColorMatrix cm;
    ColorMatrixColorFilter disableFilter;
    private float downY;
    private boolean isMagnetic;
    private final Context mCtx;
    private View mExtraView;
    private ListAdapter mGridAdapter;
    private GridView mGv;
    private final LayoutInflater mInflater;
    private int mLayoutId;
    private int mNumCol;
    private boolean mSingleColumn;
    private String mTitle2;
    private final int mTitleDs;
    private String mTitleStr;
    private boolean mTracked;
    private Adapter mWrappedAdapter;
    private int setSelected;

    public interface Adapter {
        int getCount();

        void onClick(int i);

        void onConfigItem(int i, ItemDetails itemDetails);
    }

    class C30661 implements OnCancelListener {
        C30661() {
        }

        public void onCancel(DialogInterface dialog) {
            if (BottomSheet.this.cancelListener != null) {
                BottomSheet.this.cancelListener.onCancel(BottomSheet.this);
            }
            if (BottomSheet.this.mTracked) {
                AppService.getMainActivity().removeOrientationTracker(BottomSheet.this);
            }
        }
    }

    class C30672 implements OnClickListener {
        C30672() {
        }

        public void onClick(View v) {
            BottomSheet.this.dismiss();
        }
    }

    class C30683 implements Runnable {
        C30683() {
        }

        public void run() {
            BottomSheet.this.mGv.setSelection(BottomSheet.this.setSelected);
        }
    }

    class C30704 implements OnScrollListener {
        int lastFirstVisibleItem = -1;

        C30704() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                View itemView = view.getChildAt(0);
                int top = Math.abs(itemView.getTop());
                int bottom = Math.abs(itemView.getBottom());
                int scrollBy = top >= bottom ? bottom : -top;
                if (scrollBy != 0) {
                    smoothScrollDeferred(scrollBy, view);
                }
            }
        }

        private void smoothScrollDeferred(final int scrollByF, final AbsListView viewF) {
            viewF.post(new Runnable() {
                public void run() {
                    viewF.smoothScrollBy(scrollByF, 200);
                }
            });
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (this.lastFirstVisibleItem == -1) {
                this.lastFirstVisibleItem = firstVisibleItem;
            } else if (this.lastFirstVisibleItem < firstVisibleItem) {
                this.lastFirstVisibleItem = firstVisibleItem;
            } else if (this.lastFirstVisibleItem > firstVisibleItem) {
                this.lastFirstVisibleItem = firstVisibleItem;
            }
        }
    }

    private class BaseAdapterWrapper extends BaseAdapter {
        private ItemDetails item = new ItemDetails();
        private int mColumns;
        private int mRealItemCount;
        private final Adapter mWrapped;

        BaseAdapterWrapper(Adapter wrapped, int columns) {
            this.mWrapped = wrapped;
            this.mColumns = columns;
        }

        public int getCount() {
            this.mRealItemCount = this.mWrapped.getCount();
            int roundedCount = (((this.mRealItemCount + this.mColumns) - 1) / this.mColumns) * this.mColumns;
            if (BottomSheet.this.isMagnetic) {
                return roundedCount + 2;
            }
            return roundedCount;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            this.item.reset();
            if (BottomSheet.this.isMagnetic) {
                position--;
            }
            if (position < this.mRealItemCount && position >= 0) {
                if ((this.mWrapped instanceof CustomAdapter) && ((CustomAdapter) this.mWrapped).isCustomItem(position)) {
                    return ((CustomAdapter) this.mWrapped).setupCustomItem(BottomSheet.this.mGv, position, convertView);
                }
                this.mWrapped.onConfigItem(position, this.item);
            }
            if (this.item.isConfigured()) {
                if (convertView != null) {
                    convertView.setEnabled(true);
                }
                return this.item.setupView(convertView, this.mWrapped, position);
            }
            if (this.mColumns != 1) {
                if (convertView == null) {
                    convertView = BottomSheet.this.mInflater.inflate(BottomSheet.this.mLayoutId, BottomSheet.this.mGv, false);
                    BottomSheet.this.setImage(convertView, C1283R.id.bottomSheetItemImage, null);
                    BottomSheet.this.setCopy(convertView, (int) C1283R.id.bottomSheetItemLabel, null);
                    convertView.setEnabled(false);
                    convertView.findViewById(C1283R.id.bottomSheetItem).setBackgroundColor(BottomSheet.this.mCtx.getResources().getColor(C1283R.color.White));
                }
            } else if (BottomSheet.this.isMagnetic) {
                if (convertView == null) {
                    convertView = BottomSheet.this.mInflater.inflate(BottomSheet.this.mLayoutId, BottomSheet.this.mGv, false);
                }
                BottomSheet.this.setImage(convertView, C1283R.id.bottomSheetItemImage, null);
                BottomSheet.this.setCopy(convertView, (int) C1283R.id.bottomSheetItemLabel, null);
                BottomSheet.this.setCopy(convertView, (int) C1283R.id.bottomSheetItemSubtitle, null);
                convertView.setEnabled(false);
            }
            return convertView;
        }
    }

    public interface CustomAdapter extends Adapter {
        boolean isCustomItem(int i);

        View setupCustomItem(GridView gridView, int i, View view);
    }

    public class ItemDetails {
        private boolean mConfigured = false;
        private boolean mDisabled = false;
        private Drawable mImageResource;
        private String mLabel;
        private String mSubtitle;

        public void setItem(int dsLabel) {
            this.mLabel = NativeManager.getInstance().getLanguageString(dsLabel);
            this.mConfigured = true;
        }

        public void setItem(String label) {
            this.mLabel = label;
            this.mConfigured = true;
        }

        public void setItem(int dsLabel, int imageId) {
            this.mImageResource = BottomSheet.this.mCtx.getResources().getDrawable(imageId);
            setItem(dsLabel);
            this.mConfigured = true;
        }

        public void setItem(String label, Drawable imageRes) {
            this.mImageResource = imageRes;
            setItem(label);
            this.mConfigured = true;
        }

        public void setItem(int dsLabel, int imageId, int dsSubtitle) {
            this.mSubtitle = NativeManager.getInstance().getLanguageString(dsSubtitle);
            setItem(dsLabel, imageId);
        }

        public void setItem(String label, Drawable imageRes, String subtitle) {
            this.mSubtitle = subtitle;
            setItem(label, imageRes);
        }

        public void setItem(String label, String subtitle) {
            this.mSubtitle = subtitle;
            setItem(label);
        }

        View setupView(View convertView, final Adapter adapter, final int itemId) {
            int i = 0;
            if (convertView == null) {
                convertView = BottomSheet.this.mInflater.inflate(BottomSheet.this.mLayoutId, BottomSheet.this.mGv, false);
            }
            if (this.mImageResource != null) {
                ColorFilter colorFilter;
                Drawable drawable = this.mImageResource;
                if (this.mDisabled) {
                    colorFilter = BottomSheet.this.disableFilter;
                } else {
                    colorFilter = null;
                }
                drawable.setColorFilter(colorFilter);
                BottomSheet.this.setImage(convertView, C1283R.id.bottomSheetItemImage, this.mImageResource);
            } else {
                BottomSheet.this.setImage(convertView, C1283R.id.bottomSheetItemImage, null);
            }
            if (this.mLabel != null) {
                BottomSheet.this.setCopy(convertView, (int) C1283R.id.bottomSheetItemLabel, this.mLabel);
            }
            View vSubtitle = convertView.findViewById(C1283R.id.bottomSheetItemSubtitle);
            if (vSubtitle != null) {
                int i2;
                if (this.mSubtitle == null) {
                    i2 = 8;
                } else {
                    i2 = 0;
                }
                vSubtitle.setVisibility(i2);
            }
            if (this.mSubtitle != null) {
                BottomSheet.this.setCopy(convertView, (int) C1283R.id.bottomSheetItemSubtitle, this.mSubtitle);
            }
            View item = convertView.findViewById(C1283R.id.bottomSheetItem);
            if (this.mDisabled) {
                item.setOnClickListener(null);
            } else {
                item.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        adapter.onClick(itemId);
                    }
                });
            }
            int[] ids = new int[]{C1283R.id.bottomSheetItemImage, C1283R.id.bottomSheetItemSubtitle, C1283R.id.bottomSheetItemLabel};
            int length = ids.length;
            while (i < length) {
                View v = convertView.findViewById(ids[i]);
                if (v != null) {
                    v.setAlpha(this.mDisabled ? BottomSheet.DISABLED_ALPHA : 1.0f);
                }
                i++;
            }
            return convertView;
        }

        public void reset() {
            this.mLabel = null;
            this.mSubtitle = null;
            this.mImageResource = null;
            this.mConfigured = false;
            this.mDisabled = false;
        }

        boolean isConfigured() {
            return this.mConfigured;
        }

        public void setDisabled(boolean disabled) {
            this.mDisabled = disabled;
        }
    }

    public enum Mode {
        COLUMN_TEXT(true, C1283R.layout.bottom_sheet_simple_row_item),
        COLUMN_TEXT_ICON(true, C1283R.layout.bottom_sheet_row_item),
        GRID_SMALL(false, C1283R.layout.bottom_sheet_grid_item),
        GRID_LARGE(false, C1283R.layout.bottom_sheet_large_grid_item);
        
        private final int mLayoutId;
        private boolean mSingleColumn;

        private Mode(boolean singleColumn, int layoutId) {
            this.mSingleColumn = singleColumn;
            this.mLayoutId = layoutId;
        }

        public int getLayoutId() {
            return this.mLayoutId;
        }

        public int getColumns(int defCol) {
            return this.mSingleColumn ? 1 : defCol;
        }
    }

    public void onOrientationChanged(int orientation) {
        ((FrameLayout) findViewById(C1283R.id.bottomSheetExtraView)).removeAllViews();
        initLayout();
    }

    public void addExtraView(View v) {
        this.mExtraView = v;
    }

    public BottomSheet(Context context, int titleDs, Mode mode) {
        this(context, titleDs, mode, false);
    }

    public BottomSheet(Context context, int titleDs, Mode mode, boolean showCancelButton) {
        super(context, C1283R.style.SlideUpDialog);
        this.mTitleStr = null;
        this.mTitle2 = null;
        this.mNumCol = 3;
        this.isMagnetic = false;
        this.setSelected = -1;
        this.mTitleDs = titleDs;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mCtx = context;
        this.mNumCol = mode.getColumns(this.mNumCol);
        this.mSingleColumn = mode.mSingleColumn;
        this.mLayoutId = mode.mLayoutId;
    }

    public BottomSheet(Context context, int titleDs, String title2, Mode mode) {
        super(context, C1283R.style.SlideUpDialog);
        this.mTitleStr = null;
        this.mTitle2 = null;
        this.mNumCol = 3;
        this.isMagnetic = false;
        this.setSelected = -1;
        this.mTitleDs = titleDs;
        this.mTitle2 = title2;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mCtx = context;
        this.mNumCol = mode.getColumns(this.mNumCol);
        this.mSingleColumn = mode.mSingleColumn;
        this.mLayoutId = mode.mLayoutId;
    }

    public BottomSheet(Context context, int titleDs, String title2, int numCol, boolean singleColumn, int layoutId) {
        super(context, C1283R.style.SlideUpDialog);
        this.mTitleStr = null;
        this.mTitle2 = null;
        this.mNumCol = 3;
        this.isMagnetic = false;
        this.setSelected = -1;
        this.mTitleDs = titleDs;
        this.mTitle2 = title2;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mCtx = context;
        this.mNumCol = numCol;
        this.mSingleColumn = singleColumn;
        this.mLayoutId = layoutId;
    }

    public void setSubtitle(String subtitle) {
        this.mTitle2 = subtitle;
    }

    public void setAdapter(Adapter adapter) {
        this.mWrappedAdapter = adapter;
    }

    public void setTitleStr(String titleStr) {
        this.mTitleStr = titleStr;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.downY = event.getY();
        } else if (event.getAction() == 2 && event.getY() - this.downY > ((float) PixelMeasure.dp(20))) {
            dismiss();
        }
        return false;
    }

    public void setCancelListener(OnCancelListener listener) {
        this.cancelListener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        ActivityBase mainActivity = AppService.getMainActivity();
        if (mainActivity != null && AppService.getActiveActivity() == mainActivity) {
            mainActivity.addOrientationTracker(this);
            this.mTracked = true;
        }
        setCanceledOnTouchOutside(true);
        setOnCancelListener(new C30661());
    }

    private void setCopy(View parent, int viewId, String copy) {
        TextView tv = (TextView) parent.findViewById(viewId);
        if (tv == null) {
            Logger.w("BottomSheet - failed to findViewById");
        } else {
            tv.setText(copy);
        }
    }

    private void setCopy(int viewId, int dsID, String titleStr) {
        TextView tv = (TextView) findViewById(viewId);
        if (tv == null) {
            Logger.w("BottomSheet - failed to findViewById");
        } else if (titleStr == null) {
            String text = NativeManager.getInstance().getLanguageString(dsID);
            if (text == null || text.isEmpty()) {
                Logger.w("BottomSheet - failed to getLanguageString");
            } else {
                tv.setText(text);
            }
        } else {
            tv.setText(titleStr);
        }
    }

    private void setImage(View parent, int viewId, Drawable resource) {
        ImageView iv = (ImageView) parent.findViewById(viewId);
        if (iv == null) {
            Logger.w("BottomSheet - failed to findViewById");
        } else {
            iv.setImageDrawable(resource);
        }
    }

    private void initLayout() {
        this.cm = new ColorMatrix();
        this.cm.setSaturation(0.0f);
        this.disableFilter = new ColorMatrixColorFilter(this.cm);
        LayoutParams lp = new LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.gravity = 80;
        int widthPixels = this.mCtx.getResources().getDisplayMetrics().widthPixels;
        int fittingCells = widthPixels / PixelMeasure.dp(110);
        int neededCells = this.mWrappedAdapter.getCount();
        boolean portrait = this.mCtx.getResources().getConfiguration().orientation == 1;
        if (this.mNumCol > 1) {
            int neededRows = ((neededCells + fittingCells) - 1) / fittingCells;
            if (neededRows > 1) {
                this.mNumCol = fittingCells;
            } else {
                this.mNumCol = neededCells;
            }
            int cellHeight = widthPixels / this.mNumCol;
            lp.height = PixelMeasure.dp(110) + cellHeight;
            if (neededRows > 1 && !portrait) {
                lp.height += cellHeight / 2;
            } else if (neededRows > 2 && portrait) {
                lp.height += (cellHeight * 3) / 2;
            } else if (neededRows == 2 && portrait) {
                lp.height += cellHeight;
            }
        } else if (this.isMagnetic) {
            lp.height = PixelMeasure.dp(320);
        } else {
            int maxHeight = ((this.mCtx.getResources().getDisplayMetrics().heightPixels * 40) / 100) + PixelMeasure.dp(60);
            if (PixelMeasure.dp(((neededCells * 70) + 50) + 60) <= maxHeight || !portrait) {
                lp.height = -2;
            } else {
                int brokenCell = (maxHeight - PixelMeasure.dp(110)) % PixelMeasure.dp(70);
                if (brokenCell < PixelMeasure.dp(10)) {
                    maxHeight += PixelMeasure.dp(20);
                }
                if (brokenCell > PixelMeasure.dp(60)) {
                    maxHeight -= PixelMeasure.dp(20);
                }
                lp.height = maxHeight;
            }
        }
        if (this.mNumCol == 1 && !this.mSingleColumn) {
            this.mNumCol = 3;
            lp.height = PixelMeasure.dp(110) + (widthPixels / this.mNumCol);
        }
        if (this.mNumCol >= fittingCells || this.mNumCol <= 1) {
            lp.width = -1;
        } else {
            lp.width = PixelMeasure.dp(110) * this.mNumCol;
        }
        window.setAttributes(lp);
        setContentView(C1283R.layout.bottom_sheet);
        setCopy(C1283R.id.bottomSheetTitle, this.mTitleDs, this.mTitleStr);
        if (this.mTitle2 != null) {
            TextView tvTitle2 = (TextView) findViewById(C1283R.id.bottomSheetTitle2);
            tvTitle2.setVisibility(0);
            tvTitle2.setText(this.mTitle2);
        }
        this.mGridAdapter = new BaseAdapterWrapper(this.mWrappedAdapter, this.mNumCol);
        this.mGv = (GridView) findViewById(C1283R.id.bottomSheetGrid);
        this.mGv.setAdapter(this.mGridAdapter);
        this.mGv.setNumColumns(this.mNumCol);
        this.mGv.setHorizontalSpacing(PixelMeasure.dp(1));
        this.mGv.setVerticalSpacing(PixelMeasure.dp(1));
        if (this.mExtraView != null) {
            ((FrameLayout) findViewById(C1283R.id.bottomSheetExtraView)).addView(this.mExtraView);
        }
        findViewById(C1283R.id.bottomSheetTopSpace).setOnClickListener(new C30672());
        if (this.isMagnetic) {
            setupMagneticScroll();
            findViewById(C1283R.id.bottomSheetGridForeground).setBackgroundResource(C1283R.drawable.selector_gradient);
        } else {
            findViewById(C1283R.id.bottomSheetGridForeground).setVisibility(8);
        }
        if (this.setSelected >= 0) {
            this.mGv.post(new C30683());
        }
    }

    private void setupMagneticScroll() {
        this.mGv.setOnScrollListener(new C30704());
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            Rect dialogBounds = new Rect();
            getWindow().getDecorView().getHitRect(dialogBounds);
            if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
                if (this.cancelListener != null) {
                    this.cancelListener.onCancel(this);
                }
                dismiss();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setMagneticChooser() {
        this.isMagnetic = true;
    }

    public void setSelected(int selected) {
        this.setSelected = selected;
    }

    public void show() {
        super.show();
        if (this.mGv == null) {
            return;
        }
        if (this.isMagnetic) {
            this.mGv.setScrollbarFadingEnabled(false);
        } else {
            this.mGv.setVerticalScrollBarEnabled(false);
        }
    }
}
