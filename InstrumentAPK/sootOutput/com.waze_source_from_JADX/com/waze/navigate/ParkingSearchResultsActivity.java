package com.waze.navigate;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.NavigateNativeManager.ParkingResult;
import com.waze.reports.VenueData;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;

public class ParkingSearchResultsActivity extends SearchResultsActivityBase {
    private static final long NETWORK_TIMEOUT = 10000;
    private int[] ETASecs = null;
    private String[] ETAvenueIds = null;
    private String mContext;
    private VenueData mOriginalVenue = null;
    private long mRequestETAtime;
    private String mSearchCategoryGroup = null;
    private Runnable mTimeoutRunnable;
    private NavigateNativeManager navMgr;

    class C21481 implements Runnable {

        class C21471 implements OnClickListener {
            C21471() throws  {
            }

            public void onClick(DialogInterface dialog, int which) throws  {
                ParkingSearchResultsActivity.this.finish();
            }
        }

        C21481() throws  {
        }

        public void run() throws  {
            MsgBox.openMessageBoxTimeout(ParkingSearchResultsActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_UHHOHE), ParkingSearchResultsActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C21471());
        }
    }

    private class ParkingResultsAdapter extends SearchResultsAdapter {
        private ParkingResultsAdapter() throws  {
            super();
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int $i0) throws  {
            if ($i0 != 0) {
                return new SearchResultViewHolder(new ParkingResultItemView(ParkingSearchResultsActivity.this));
            }
            ParkingSearchResultsActivity.this.mEmptySpaceViewHolder = new EmptySpaceViewHolder(new View(ParkingSearchResultsActivity.this));
            return ParkingSearchResultsActivity.this.mEmptySpaceViewHolder;
        }
    }

    public static class ParkingSuggestionAddressItem extends AddressItem {
        public int ETA;
        private final boolean best;
        public boolean firstTitle;
        private final boolean popular;
        public boolean secondTitle;
        public final int walkingDistance;

        public ParkingSuggestionAddressItem(ParkingResult $r1) throws  {
            String $r16;
            AddressItem $r2 = $r1.ai;
            Integer $r3 = $r2.getLocationX();
            $r2 = $r1.ai;
            Integer $r4 = $r2.getLocationY();
            $r2 = $r1.ai;
            String $r5 = $r2.getTitle();
            $r2 = $r1.ai;
            String $r6 = $r2.getSecondaryTitle();
            $r2 = $r1.ai;
            String $r7 = $r2.getAddress();
            $r2 = $r1.ai;
            String $r8 = $r2.getDistance();
            $r2 = $r1.ai;
            Boolean $r9 = $r2.getMoreAction();
            $r2 = $r1.ai;
            Integer $r10 = $r2.getImage();
            $r2 = $r1.ai;
            Integer $r11 = $r2.getCategory();
            $r2 = $r1.ai;
            String $r12 = $r2.getId();
            $r2 = $r1.ai;
            Integer $r13 = Integer.valueOf($r2.getType());
            $r2 = $r1.ai;
            String $r14 = $r2.getUrl();
            $r2 = $r1.ai;
            String $r15 = $r2.getSpecialUrl();
            $r2 = $r1.ai;
            if ($r2.hasIcon()) {
                $r2 = $r1.ai;
                $r16 = $r2.getIcon();
            } else {
                $r16 = null;
            }
            String $r17 = $r1.ai;
            String $r22 = $r17;
            String $r172 = $r17.VanueID;
            $r2 = $r1.ai;
            String $r18 = $r2.getCountry();
            $r2 = $r1.ai;
            String $r19 = $r2.getState();
            $r2 = $r1.ai;
            String $r20 = $r2.getCity();
            $r2 = $r1.ai;
            String $r21 = $r2.getStreet();
            $r2 = $r1.ai;
            String $r222 = $r2.getHouse();
            VenueData $r23 = $r1.ai;
            VenueData $r24 = $r23;
            VenueData $r232 = $r23.venueData;
            $r17 = $r1.ai;
            $r22 = $r17;
            super($r3, $r4, $r5, $r6, $r7, $r8, $r9, $r10, $r11, $r12, $r13, $r14, $r15, $r16, $r172, $r18, $r19, $r20, $r21, $r222, $r232, $r17.routingContext);
            this.ETA = -1;
            this.firstTitle = false;
            this.secondTitle = false;
            boolean z = $r1.showAsAd;
            boolean $z0 = z;
            this.sponsored = z;
            int i = $r1.walkingDistance;
            int $i0 = i;
            this.walkingDistance = i;
            z = $r1.popular;
            $z0 = z;
            this.popular = z;
            z = $r1.best;
            $z0 = z;
            this.best = z;
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mMapViewWrapper.getMapView().setNativeTag(getString(C1283R.string.nativeTagPreviewCanvas));
        this.mMapViewWrapper.getMapView().setRenderer();
        this.mTitleBar.setTitle(DisplayStrings.displayString(DisplayStrings.DS_SEARCH_RESULTS_PARKING_TITLE));
        findViewById(C1283R.id.pagerTabStripContainer).setVisibility(8);
        this.mOriginalVenue = (VenueData) getIntent().getExtras().getSerializable(PublicMacros.PREVIEW_PARKING_VENUE);
        this.mSearchCategoryGroup = getIntent().getExtras().getString(PublicMacros.SEARCH_CATEGORY_GROUP);
        this.mContext = getIntent().getExtras().getString(PublicMacros.PREVIEW_PARKING_CONTEXT, "");
        this.navMgr = NavigateNativeManager.instance();
        this.mTimeoutRunnable = new C21481();
        postDelayed(this.mTimeoutRunnable, NETWORK_TIMEOUT);
        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
        this.navMgr.setUpdateHandler(NavigateNativeManager.UH_SUGGEST_ALL_PARKING, this.mHandler);
        if (this.mOriginalVenue != null) {
            this.navMgr.suggestParkingRequestSuggestions(this.mOriginalVenue);
            return;
        }
        cancel(this.mTimeoutRunnable);
        Runnable $r12 = this.mTimeoutRunnable;
        $r12.run();
        this.mTimeoutRunnable = null;
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == NavigateNativeManager.UH_SUGGEST_ALL_PARKING) {
            NativeManager.getInstance().CloseProgressPopup();
            this.navMgr.unsetUpdateHandler(NavigateNativeManager.UH_SUGGEST_ALL_PARKING, this.mHandler);
            Bundle $r8 = $r1.getData();
            if (this.mTimeoutRunnable != null) {
                cancel(this.mTimeoutRunnable);
                this.mTimeoutRunnable = null;
            }
            ParkingResult[] $r11 = (ParkingResult[]) $r8.getSerializable("results");
            if ($r11 == null || $r11.length <= 0) {
                findViewById(C1283R.id.searchResultsNoResultsLayout).setVisibility(0);
                ((TextView) findViewById(C1283R.id.searchResultsNoResultsText)).setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_NO_RESULTS));
            } else {
                VenueData[] $r4 = new VenueData[$r11.length];
                AddressItem[] $r2 = new AddressItem[$r11.length];
                Object obj = null;
                int $i1 = 0;
                while ($i1 < $r11.length) {
                    ParkingSuggestionAddressItem parkingSuggestionAddressItem = new ParkingSuggestionAddressItem($r11[$i1]);
                    $r2[$i1] = parkingSuggestionAddressItem;
                    $r2[$i1].index = $i1;
                    if ($i1 == 0 && $r11[$i1].popular) {
                        obj = 1;
                        parkingSuggestionAddressItem.firstTitle = true;
                    } else if ($i1 == 1 && r18 != null) {
                        parkingSuggestionAddressItem.secondTitle = true;
                        obj = null;
                    }
                    $r4[$i1] = $r2[$i1].venueData;
                    $i1++;
                }
                this.mRequestETAtime = System.currentTimeMillis();
                this.navMgr.setUpdateHandler(NavigateNativeManager.UH_CALC_MULTI_ETA, this.mHandler);
                this.navMgr.calculateMultiETA($r4, null);
                setAddressItems($r2);
                if (this.ETAvenueIds != null) {
                    updateETAs();
                }
                applyMapPins();
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_SUGGESTIONS_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_AD_SHOWN_AT_TOP, $r11[0].showAsAd ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_POPULAR_SHOWN_AT_TOP, $r11[0].popular ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_NUM_RESULTS, (long) $r11.length).send();
            }
            return true;
        } else if ($r1.what != NavigateNativeManager.UH_CALC_MULTI_ETA) {
            return super.myHandleMessage($r1);
        } else {
            this.navMgr.unsetUpdateHandler(NavigateNativeManager.UH_CALC_MULTI_ETA, this.mHandler);
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_LOT_ETA_SHOWN_LATENCY).addParam("TIME", System.currentTimeMillis() - this.mRequestETAtime).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "PARKING_LOTS_LIST").send();
            this.ETAvenueIds = $r1.getData().getStringArray("ids");
            this.ETASecs = $r1.getData().getIntArray("etas");
            if (this.mAddressItems != null) {
                AddressItem[] $r22 = this.mAddressItems;
                if ($r22.length > 0) {
                    updateETAs();
                }
            }
            return true;
        }
    }

    private void updateETAs() throws  {
        int $i0;
        if (this.ETAvenueIds == null || this.ETASecs == null) {
            for (AddressItem $r4 : this.mAddressItems) {
                ((ParkingSuggestionAddressItem) $r4).ETA = 0;
            }
        } else {
            for ($i0 = 0; $i0 < this.ETAvenueIds.length; $i0++) {
                for (AddressItem $r42 : this.mAddressItems) {
                    if ($r42.VanueID.equals(this.ETAvenueIds[$i0])) {
                        ((ParkingSuggestionAddressItem) $r42).ETA = this.ETASecs[$i0];
                        break;
                    }
                }
            }
        }
        RecyclerView $r8 = this.mSearchResultsRecycler;
        $r8.getAdapter().notifyDataSetChanged();
        this.ETAvenueIds = null;
        this.ETASecs = null;
    }

    public void onPause() throws  {
        super.onPause();
        this.navMgr.ClearPreviews();
    }

    public void onResume() throws  {
        super.onResume();
        applyMapPins();
    }

    protected void applyMapPins() throws  {
        if (this.mAddressItems != null) {
            this.navMgr.SetPreviewPoiPosition(this.mOriginalVenue.longitude, this.mOriginalVenue.latitude, this.mOriginalVenue.name, true);
            if (this.mAddressItems != null && this.mAddressItems.length > 0) {
                int $i1 = this.mOriginalVenue.longitude;
                int $i0 = $i1;
                int $i2 = $i1;
                int $i3 = this.mOriginalVenue.latitude;
                $i1 = $i3;
                for (AddressItem $r2 : this.mAddressItems) {
                    ParkingSuggestionAddressItem parkingSuggestionAddressItem = (ParkingSuggestionAddressItem) $r2;
                    this.navMgr.SetParkingPoiPosition($r2.venueData, parkingSuggestionAddressItem.ETA, parkingSuggestionAddressItem.walkingDistance, this.mOriginalVenue, parkingSuggestionAddressItem.popular, $r2.hasIcon() ? $r2.getIcon() : null, parkingSuggestionAddressItem.sponsored, this.mContext);
                    $i2 = Math.max($i2, $r2.getLocationX().intValue());
                    $i0 = Math.min($i0, $r2.getLocationX().intValue());
                    $i3 = Math.max($i3, $r2.getLocationY().intValue());
                    $i1 = Math.min($i1, $r2.getLocationY().intValue());
                }
                $i0 = Math.max($i2 - $i0, $i1 - $i3) / 3;
                NavigateNativeManager $r4 = this.navMgr;
                $i1 = this.mOriginalVenue.longitude;
                int $i22 = this.mOriginalVenue.latitude;
                $r4.PreviewCanvasFocusOn($i1, $i22, $i0);
            }
        }
    }

    public void onSearchResultClick(AddressItem $r1) throws  {
        ParkingSuggestionAddressItem $r3 = (ParkingSuggestionAddressItem) $r1;
        getClickEvent().addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_INDEX, (long) $r1.index).addParam(AnalyticsEvents.ANALYTICS_EVENTS_IS_AD_DISPLAYED, this.mAdDisplayed ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).addParam("POPULAR", $r3.popular ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).addParam("ACTION", "SELECT").send();
        Intent $r2 = r7;
        Intent r7 = new Intent(this, AddressPreviewActivity.class);
        $r2.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
        $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
        $r2.putExtra(PublicMacros.PREVIEW_PARKING_MODE, true);
        $r2.putExtra(PublicMacros.PREVIEW_PARKING_DISTANCE, $r3.walkingDistance);
        $r2.putExtra(PublicMacros.f84PREVIEW_PARKING_ETA, $r3.ETA);
        $r2.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, this.mOriginalVenue);
        $r2.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, "MORE");
        $r2.putExtra(PublicMacros.PREVIEW_PARKING_POPULAR, $r3.popular);
        startActivityForResult($r2, 0);
    }

    protected SearchResultsAdapter createResultAdapter() throws  {
        return new ParkingResultsAdapter();
    }

    protected int getDefaultMapHeight() throws  {
        return PixelMeasure.dimension(C1283R.dimen.parking_results_map_default_height);
    }

    public boolean isHighlighted(int position, AddressItem $r1) throws  {
        return $r1.sponsored;
    }

    protected AnalyticsBuilder getClickEvent() throws  {
        return AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_SUGGESTIONS_CLICK).addParam(AnalyticsEvents.ANALYTICS_EVENT_CATEGORICAL_SEARCH, this.mSearchCategoryGroup != null ? this.mSearchCategoryGroup : "").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ROUTING, NavigateNativeManager.instance().isNavigating() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CONTEXT, this.mContext);
    }
}
