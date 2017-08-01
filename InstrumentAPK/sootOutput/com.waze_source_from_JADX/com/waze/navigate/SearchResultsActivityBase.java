package com.waze.navigate;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.MapView;
import com.waze.map.MapViewWrapper;
import com.waze.navigate.SearchResultItemViewBase.SearchResultItemListener;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.title.TitleBar;

public abstract class SearchResultsActivityBase extends ActivityBase implements SearchResultItemListener {
    public static final int MAXIMUM_FOV = 300000;
    public static final int MINIMUM_FOV = 5000;
    private static final int SINGLE_ITEM_MAP_PADDING = 7500;
    protected boolean mAdDisplayed;
    private boolean[] mAddressItemSeenMap;
    protected AddressItem[] mAddressItems;
    private RelativeLayout mBackToListButton;
    protected EmptySpaceViewHolder mEmptySpaceViewHolder;
    protected String mIcon;
    private boolean mIsDragging;
    private boolean mIsDraggingDownwards;
    protected boolean mIsMapFullScreen;
    private float mLastTouchY = -1.0f;
    protected MapViewWrapper mMapViewWrapper;
    protected NativeManager mNm = NativeManager.getInstance();
    private FrameLayout mSearchResultsContainer;
    protected RecyclerView mSearchResultsRecycler;
    protected TitleBar mTitleBar;
    private boolean mbLoadingShown = false;
    protected int resultsSize;

    protected class SearchResultsAdapter extends Adapter {
        protected SearchResultsAdapter() throws  {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int $i0) throws  {
            if ($i0 != 0) {
                return new SearchResultViewHolder(new SearchResultItemView(SearchResultsActivityBase.this));
            }
            SearchResultsActivityBase.this.mEmptySpaceViewHolder = new EmptySpaceViewHolder(new View(SearchResultsActivityBase.this));
            return SearchResultsActivityBase.this.mEmptySpaceViewHolder;
        }

        public void onBindViewHolder(ViewHolder $r1, int $i0) throws  {
            boolean $z0 = $r1;
            $r1 = $z0;
            if ($z0 instanceof SearchResultViewHolder) {
                SearchResultViewHolder $r3 = (SearchResultViewHolder) $r1;
                $i0 -= SearchResultsActivityBase.this.isPortrait() ? (byte) 1 : (byte) 0;
                AddressItem $r2 = SearchResultsActivityBase.this.mAddressItems[$i0];
                Log.i("SearchResultsActivity", "About to call notifyAddressItemShown with position: " + $i0);
                if (SearchResultsActivityBase.this.mAddressItemSeenMap != null && $i0 >= 0 && $i0 < SearchResultsActivityBase.this.mAddressItemSeenMap.length) {
                    DriveToNativeManager.getInstance().notifyAddressItemShown($r2.index, SearchResultsActivityBase.this.mAddressItemSeenMap[$i0]);
                    SearchResultsActivityBase.this.mAddressItemSeenMap[$i0] = true;
                }
                $r3.bindView($r2, $i0);
            }
        }

        public int getItemCount() throws  {
            int $i1;
            byte $b0 = (byte) 0;
            if (SearchResultsActivityBase.this.mAddressItems != null) {
                $i1 = SearchResultsActivityBase.this.mAddressItems.length;
            } else {
                $i1 = 0;
            }
            if (SearchResultsActivityBase.this.isPortrait()) {
                $b0 = (byte) 1;
            }
            return $b0 + $i1;
        }

        public int getItemViewType(int $i0) throws  {
            return ($i0 == 0 && SearchResultsActivityBase.this.isPortrait()) ? 0 : 1;
        }
    }

    class C21641 implements OnClickListener {
        C21641() throws  {
        }

        public void onClick(View v) throws  {
            SearchResultsActivityBase.this.getClickEvent().addParam("ACTION", "X").send();
            SearchResultsActivityBase.this.setResult(-1);
            SearchResultsActivityBase.this.finish();
        }
    }

    class C21652 extends RunnableExecutor {
        C21652() throws  {
        }

        public void event() throws  {
            SearchResultsActivityBase.this.applyMapPins();
        }
    }

    class C21663 implements Runnable {
        C21663() throws  {
        }

        public void run() throws  {
            SearchResultsActivityBase.this.mMapViewWrapper.getMapView().setTranslationY(SearchResultsActivityBase.this.getMaxMapTranslation());
        }
    }

    class C21674 implements OnClickListener {
        C21674() throws  {
        }

        public void onClick(View v) throws  {
            SearchResultsActivityBase.this.cancelDragDown();
            SearchResultsActivityBase.this.getClickEvent().addParam("ACTION", "BACK_TO_LIST").send();
        }
    }

    private class DragDownLinearLayoutManager extends LinearLayoutManager {
        public DragDownLinearLayoutManager(Context $r2) throws  {
            super($r2);
        }

        public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
            int $i1 = super.scrollVerticallyBy($i0, $r1, $r2);
            if ($i0 < 0 && $i1 == 0 && SearchResultsActivityBase.this.isPortrait()) {
                SearchResultsActivityBase.this.mIsDragging = true;
                return $i1;
            } else if (!SearchResultsActivityBase.this.isPortrait() || (SearchResultsActivityBase.this.mSearchResultsRecycler.getChildAt(0) instanceof SearchResultItemViewBase)) {
                return $i1;
            } else {
                SearchResultsActivityBase.this.mEmptySpaceViewHolder.applyScrollRatio(((float) getDecoratedBottom(SearchResultsActivityBase.this.mSearchResultsRecycler.getChildAt(0))) / ((float) SearchResultsActivityBase.this.getDefaultMapHeight()));
                return $i1;
            }
        }
    }

    protected class EmptySpaceViewHolder extends ViewHolder {
        public EmptySpaceViewHolder(View $r2) throws  {
            super($r2);
            $r2.setBackgroundColor(1879048192);
            $r2.setAlpha(0.0f);
            $r2.setLayoutParams(new LayoutParams(-1, SearchResultsActivityBase.this.getDefaultMapHeight()));
            $r2.setOnTouchListener(new OnTouchListener(SearchResultsActivityBase.this) {
                public boolean onTouch(View v, MotionEvent $r2) throws  {
                    if ($r2.getAction() == 1) {
                        SearchResultsActivityBase.this.completeDragDown();
                    }
                    MapView $r7 = SearchResultsActivityBase.this.mMapViewWrapper.getMapView();
                    int[] $r3 = new int[2];
                    $r7.getLocationOnScreen($r3);
                    $r2.setLocation($r2.getRawX() + ((float) $r3[0]), $r2.getRawY() + ((float) $r3[1]));
                    $r7.onTouchEvent($r2);
                    return true;
                }
            });
        }

        public void applyScrollRatio(float $f0) throws  {
            this.itemView.setAlpha(1.0f - $f0);
        }
    }

    protected class SearchResultViewHolder extends ViewHolder {
        private SearchResultItemViewBase mSearchResultItemView;

        public SearchResultViewHolder(SearchResultItemViewBase $r2) throws  {
            super($r2);
            this.mSearchResultItemView = $r2;
            this.mSearchResultItemView.setLayoutParams(new LayoutParams(-1, -2));
            this.mSearchResultItemView.setListener(SearchResultsActivityBase.this);
        }

        public void bindView(AddressItem $r1, int $i0) throws  {
            this.mSearchResultItemView.setIcon(SearchResultsActivityBase.this.mIcon);
            this.mSearchResultItemView.setAddressItem($r1);
            boolean $z0 = SearchResultsActivityBase.this.isHighlighted($i0, $r1);
            this.mSearchResultItemView.setIsFirstAdItem($z0, $i0);
            if (SearchResultsActivityBase.this.mAddressItems.length == 1 || $z0) {
                this.mSearchResultItemView.setOnlyItem();
            } else if ($i0 == 0) {
                this.mSearchResultItemView.setFirstItem();
            } else if ($i0 == SearchResultsActivityBase.this.mAddressItems.length - 1) {
                this.mSearchResultItemView.setLastItem();
            } else {
                this.mSearchResultItemView.setMiddleItem();
            }
        }
    }

    protected abstract void applyMapPins() throws ;

    protected abstract AnalyticsBuilder getClickEvent() throws ;

    public boolean isHighlighted(int $i0, AddressItem $r1) throws  {
        return $i0 == 0 && $r1.sponsored;
    }

    protected int getDefaultMapHeight() throws  {
        return PixelMeasure.dimension(C1283R.dimen.search_results_map_default_height);
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.search_results_layout);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.titleBar);
        this.mTitleBar.setOnClickCloseListener(new C21641());
        this.mMapViewWrapper = (MapViewWrapper) findViewById(C1283R.id.searchMapPreview);
        this.mMapViewWrapper.getMapView().setHandleKeys(false);
        this.mMapViewWrapper.getMapView().registerOnMapReadyCallback(new C21652());
        if (isPortrait()) {
            postDelayed(new C21663(), 300);
        }
        this.mSearchResultsContainer = (FrameLayout) findViewById(C1283R.id.searchResultsContainer);
        this.mBackToListButton = (RelativeLayout) findViewById(C1283R.id.btnBackToList);
        this.mBackToListButton.setOnClickListener(new C21674());
        this.mSearchResultsRecycler = (RecyclerView) findViewById(C1283R.id.searchResultsRecycler);
        this.mSearchResultsRecycler.setLayoutManager(new DragDownLinearLayoutManager(this));
        this.mSearchResultsRecycler.setAdapter(createResultAdapter());
        ((TextView) findViewById(C1283R.id.btnBackToListText)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_SEARCH_RESULTS_BACK_TO_LIST));
        if (isPortrait()) {
            View $r2 = findViewById(C1283R.id.searchResultsContainerBackground);
            ViewGroup.LayoutParams $r19 = (FrameLayout.LayoutParams) $r2.getLayoutParams();
            $r19.setMargins($r19.leftMargin, getDefaultMapHeight(), $r19.rightMargin, $r19.bottomMargin);
            $r2.setLayoutParams($r19);
        }
    }

    protected SearchResultsAdapter createResultAdapter() throws  {
        return new SearchResultsAdapter();
    }

    protected float getMaxMapTranslation() throws  {
        return ((float) ((-this.mMapViewWrapper.getMapView().getMeasuredHeight()) / 2)) + (((float) getDefaultMapHeight()) * 0.75f);
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        if (!this.mIsDragging) {
            return super.dispatchTouchEvent($r1);
        }
        if ($r1.getAction() == 2) {
            if (this.mLastTouchY < 0.0f) {
                this.mLastTouchY = $r1.getY() - 1.0f;
            }
            float $f1 = this.mSearchResultsContainer.getTranslationY();
            this.mSearchResultsContainer.setTranslationY(($r1.getY() - this.mLastTouchY) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
            this.mIsDraggingDownwards = this.mSearchResultsContainer.getTranslationY() > $f1;
            this.mMapViewWrapper.getMapView().setTranslationY(getMaxMapTranslation() * (1.0f - (this.mSearchResultsContainer.getTranslationY() / ((float) this.mSearchResultsContainer.getMeasuredHeight()))));
            if (this.mSearchResultsContainer.getTranslationY() > 0.0f) {
                return true;
            }
            cancelDragDown();
            return true;
        } else if ($r1.getAction() != 3 && $r1.getAction() != 1) {
            return true;
        } else {
            if (this.mIsDraggingDownwards) {
                completeDragDown();
                return true;
            }
            cancelDragDown();
            return true;
        }
    }

    private void cancelDragDown() throws  {
        ViewPropertyAnimatorHelper.initAnimation(this.mSearchResultsContainer).translationY(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this.mMapViewWrapper.getMapView()).translationY(getMaxMapTranslation());
        this.mIsDragging = false;
        this.mLastTouchY = -1.0f;
        this.mIsMapFullScreen = false;
        this.mMapViewWrapper.hidePopupIfShown();
        if (this.mBackToListButton.getVisibility() != 8) {
            ViewPropertyAnimatorHelper.initAnimation(this.mBackToListButton).translationY((float) PixelMeasure.dp(56)).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mBackToListButton));
            applyMapPins();
        }
    }

    protected void completeDragDown() throws  {
        getClickEvent().addParam("ACTION", "MAP").send();
        ViewPropertyAnimatorHelper.initAnimation(this.mSearchResultsContainer).translationY((float) this.mSearchResultsContainer.getMeasuredHeight());
        ViewPropertyAnimatorHelper.initAnimation(this.mMapViewWrapper.getMapView()).translationY(0.0f);
        this.mIsDragging = false;
        this.mLastTouchY = -1.0f;
        this.mBackToListButton.setVisibility(0);
        this.mBackToListButton.setTranslationY((float) PixelMeasure.dp(56));
        ViewPropertyAnimatorHelper.initAnimation(this.mBackToListButton).translationY(0.0f).setListener(null);
        this.mIsMapFullScreen = true;
        applyMapPins();
    }

    protected void onPause() throws  {
        this.mMapViewWrapper.onPause();
        super.onPause();
    }

    protected void onResume() throws  {
        this.mMapViewWrapper.onResume();
        super.onResume();
    }

    public void refreshAdressItemsIcons(int $i0, String $r1) throws  {
        if (this.mAddressItems != null && $i0 >= 0 && $i0 < this.mAddressItems.length) {
            this.mAddressItems[$i0].setIcon($r1);
            this.mSearchResultsRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    protected void showLoadingPopup() throws  {
        if (!this.mbLoadingShown) {
            this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
            this.mSearchResultsRecycler.setVisibility(4);
            this.mbLoadingShown = true;
        }
    }

    protected void hideLoadingPopup() throws  {
        if (this.mbLoadingShown) {
            this.mNm.CloseProgressPopup();
            this.mSearchResultsRecycler.setVisibility(0);
            this.mbLoadingShown = false;
        }
    }

    protected void setAddressItems(AddressItem[] $r1) throws  {
        this.mAddressItems = $r1;
        if (this.mAddressItems != null) {
            this.mAddressItemSeenMap = new boolean[this.mAddressItems.length];
            this.mSearchResultsRecycler.scrollToPosition(0);
            if (this.mEmptySpaceViewHolder != null) {
                this.mEmptySpaceViewHolder.applyScrollRatio(1.0f);
            }
            findViewById(C1283R.id.searchResultsNoResultsLayout).setVisibility(this.mAddressItems.length > 0 ? (byte) 8 : (byte) 0);
            hideLoadingPopup();
        }
        this.mAdDisplayed = false;
        if (this.mAddressItems != null) {
            for (AddressItem $r6 : this.mAddressItems) {
                if ($r6.sponsored) {
                    this.mAdDisplayed = true;
                    return;
                }
            }
        }
    }

    private boolean processFirstRect(Rect $r1, boolean $z0) throws  {
        if ($r1 == null) {
            return false;
        }
        AddressItem[] $r3 = this.mAddressItems;
        int $i0 = $r3.length;
        int $i1 = 0;
        while ($i1 < $i0) {
            AddressItem $r2 = $r3[$i1];
            if ($r2.getLocationX() == null || $r2.getLocationY() == null || ($z0 && $r2.sponsored)) {
                $i1++;
            } else {
                $i0 = $r2.getLocationX().intValue();
                $r1.left = $i0;
                $r1.right = $i0;
                $i0 = $r2.getLocationY().intValue();
                $r1.bottom = $i0;
                $r1.top = $i0;
                $r1.left -= 7500;
                $r1.right += SINGLE_ITEM_MAP_PADDING;
                $r1.top += SINGLE_ITEM_MAP_PADDING;
                $r1.bottom -= 7500;
                return true;
            }
        }
        return false;
    }

    protected void loadResultsCanvas(int maxFov, float $f0, float $f1, boolean onlyFirstItem) throws  {
        NavigateNativeManager.instance().LoadResultsCanvas($f0, $f1);
    }

    public void onBackPressed() throws  {
        getClickEvent().addParam("ACTION", "BACK").send();
        super.onBackPressed();
    }
}
