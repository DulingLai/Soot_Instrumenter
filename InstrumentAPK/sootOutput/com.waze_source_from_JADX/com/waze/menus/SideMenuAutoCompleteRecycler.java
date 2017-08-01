package com.waze.menus;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.DistanceAndUnits;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.PlaceData;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers.MicroHandler;
import com.waze.ifs.async.UpdateHandlers.MicroHandler.MicroHandlerCallback;
import com.waze.menus.AutoCompleteRequest.AutoCompleteResultHandler;
import com.waze.mywaze.MyStoreModel;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.FavoritesActivity;
import com.waze.navigate.HistoryActivity;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.planned_drive.PlannedDriveAlternateFromActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class SideMenuAutoCompleteRecycler extends RecyclerView implements MicroHandlerCallback, AutoCompleteResultHandler {
    public static final String ANALYTICS_LINE_NUMBER = "LINE_NUMBER";
    private static int ITEM_TYPE_AD = 1;
    private static int ITEM_TYPE_CATEOGRIES_BAR = 2;
    private static int ITEM_TYPE_REGULAR = 0;
    private AutoCompleteAdHandler mAdHandler;
    private WebView mAdWebView;
    private AutoCompleteRequest mAutoCompleteRequest;
    private List<AutoCompleteItemModel> mAutoCompleteResults;
    private String mCurrentSearchTerm;
    private List<AutoCompleteItemModel> mDefaultItemModels;
    private DriveToGetAddressItemArrayCallback mGetHistoryCallback;
    private MicroHandler mHandler;
    private boolean mHideCategoryBar;
    private AddressItem[] mHistoryAddressItems;
    private boolean mIsDisplayingAd;
    private boolean mIsDisplayingCategoryBar;
    private boolean mIsDisplayingDefaults;
    private boolean mIsSearchOnMap;
    private Mode mMode;
    private SideMenuCategoryBar menuCategoryBar;

    public interface AutoCompleteAdHandler {
        void closeSideMenu() throws ;

        void hideKeyboard() throws ;

        void setSearchTerm(String str, boolean z) throws ;
    }

    class C19292 implements DriveToGetAddressItemArrayCallback {
        C19292() throws  {
        }

        public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
            SideMenuAutoCompleteRecycler.this.mHistoryAddressItems = $r1;
        }
    }

    class C19323 extends WebChromeClient {
        C19323() throws  {
        }

        public boolean onJsConfirm(WebView view, String url, String $r3, final JsResult $r4) throws  {
            new Builder(AppService.getActiveActivity(), C1283R.style.CustomPopup).setTitle("").setMessage($r3).setPositiveButton(17039370, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) throws  {
                    $r4.confirm();
                }
            }).setNegativeButton(17039360, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) throws  {
                    $r4.cancel();
                }
            }).create().show();
            return true;
        }
    }

    class C19344 extends WebViewClient {

        class C19331 implements Runnable {
            C19331() throws  {
            }

            public void run() throws  {
                SideMenuAutoCompleteRecycler.this.requestLayout();
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) throws  {
            return false;
        }

        C19344() throws  {
        }

        public void onPageFinished(WebView $r1, String $r2) throws  {
            super.onPageFinished($r1, $r2);
            SideMenuAutoCompleteRecycler.this.postDelayed(new C19331(), 100);
        }
    }

    class C19355 implements Runnable {
        C19355() throws  {
        }

        public void run() throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK, "ACTION", "HISTORY");
            AppService.getActiveActivity().startActivityForResult(new Intent(SideMenuAutoCompleteRecycler.this.getContext(), HistoryActivity.class), 1);
        }
    }

    class C19366 implements OnTouchListener {
        C19366() throws  {
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            switch ($r2.getAction()) {
                case 1:
                    PlaceData placeData = (PlaceData) SideMenuAutoCompleteRecycler.this.mAdWebView.getTag();
                    if (placeData != null) {
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_CLICK).addParam("TYPE", AnalyticsEvents.ANALYTICS_AUTOCOMPLETE_ADV).addParam("LINE_NUMBER", 0).addParam(AnalyticsEvents.ANALYTICS_EVENTS_IS_AD_DISPLAYED, String.valueOf(SideMenuAutoCompleteRecycler.this.mIsDisplayingAd).toUpperCase()).addParam("QUERY", SideMenuAutoCompleteRecycler.this.mCurrentSearchTerm).send();
                        SideMenuAutoCompleteRecycler.this.mAdHandler.hideKeyboard();
                        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                        DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, SideMenuAutoCompleteRecycler.this.mHandler);
                        if (DriveToNativeManager.getInstance().isAutocompleteServerAds()) {
                            Analytics.adsContextSearchInit(AnalyticsEvents.ANALYTICS_EVENT_INFO_ADS_AUTOCOMPLETE_SEARCH, -1, -1, 0, false, SideMenuAutoCompleteRecycler.this.mCurrentSearchTerm, "", placeData.mVenueId, placeData.mVenueContext);
                        } else {
                            NativeManager.getInstance().AutoCompleteAdsClicked(placeData.mVenueId, SideMenuAutoCompleteRecycler.this.mCurrentSearchTerm, 0);
                        }
                        NativeManager.getInstance().AutoCompletePlaceClicked(null, placeData.mVenueId, placeData.mReference, null, null, false, SideMenuAutoCompleteRecycler.this.mCurrentSearchTerm, false, 0, null, null);
                        break;
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    class C19377 implements Runnable {
        C19377() throws  {
        }

        public void run() throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK, "ACTION", "FAVORITES");
            Intent $r1 = new Intent(SideMenuAutoCompleteRecycler.this.getContext(), FavoritesActivity.class);
            if (SideMenuAutoCompleteRecycler.this.mMode == Mode.PlannedDriveSelectOrigin) {
                $r1.putExtra(PlannedDriveAlternateFromActivity.MODE, "planned_drive_origin");
            } else if (SideMenuAutoCompleteRecycler.this.mMode == Mode.PlannedDriveSelectDestination) {
                $r1.putExtra(PlannedDriveAlternateFromActivity.MODE, "planned_drive_destination");
            }
            AppService.getActiveActivity().startActivityForResult($r1, 1);
        }
    }

    class C19388 implements Runnable {
        C19388() throws  {
        }

        public void run() throws  {
            PlannedDriveActivity.setFromAddressItem(null);
            AppService.getActiveActivity().finish();
        }
    }

    private class AutoCompleteAdapter extends Adapter<AutoCompleteViewHolder> {
        private AutoCompleteAdapter() throws  {
        }

        public AutoCompleteViewHolder onCreateViewHolder(ViewGroup parent, int $i0) throws  {
            if ($i0 == SideMenuAutoCompleteRecycler.ITEM_TYPE_REGULAR) {
                return new AutoCompleteViewHolder(new AutoCompleteItemView(SideMenuAutoCompleteRecycler.this.getContext()));
            }
            if ($i0 == SideMenuAutoCompleteRecycler.ITEM_TYPE_CATEOGRIES_BAR) {
                return new AutoCompleteViewHolder(SideMenuAutoCompleteRecycler.this.getCategoryBar(), PixelMeasure.dp(100));
            }
            return new AutoCompleteViewHolder(SideMenuAutoCompleteRecycler.this.mAdWebView, PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight));
        }

        public void onBindViewHolder(AutoCompleteViewHolder $r1, int $i0) throws  {
            this = this;
            if (SideMenuAutoCompleteRecycler.this.mIsDisplayingAd) {
                $i0--;
            }
            this = this;
            if (SideMenuAutoCompleteRecycler.this.mIsDisplayingCategoryBar) {
                $i0--;
            }
            if ($i0 >= 0) {
                AutoCompleteItemModel $r5;
                this = this;
                if (SideMenuAutoCompleteRecycler.this.mIsDisplayingDefaults) {
                    $r5 = (AutoCompleteItemModel) SideMenuAutoCompleteRecycler.this.mDefaultItemModels.get($i0);
                } else {
                    $r5 = (AutoCompleteItemModel) SideMenuAutoCompleteRecycler.this.mAutoCompleteResults.get($i0);
                }
                if ($r5.mIsBrand) {
                    long $l1 = (long) $i0;
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_STORES_BRAND_DISPLAYED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $r5.mVenueId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_INDEX, $l1).send();
                    if ($r5.mIsAd) {
                        MyWazeNativeManager.getInstance().sendAdBrandStats(AnalyticsEvents.ANALYTICS_EVENT_MY_STORES_ADS_BRAND_DISPLAYED, AnalyticsEvents.ANALYTICS_EVENT_INFO_ADS_ZERO_STATE_SEARCH, $r5.getMyStoreModel().getId(), $i0);
                    }
                }
                $r1.setModel($r5);
            }
        }

        public int getItemCount() throws  {
            int $i1;
            byte $b0 = (byte) 1;
            if (SideMenuAutoCompleteRecycler.this.mIsDisplayingDefaults) {
                $i1 = SideMenuAutoCompleteRecycler.this.mDefaultItemModels.size();
            } else {
                $i1 = SideMenuAutoCompleteRecycler.this.mAutoCompleteResults.size() + (SideMenuAutoCompleteRecycler.this.mIsDisplayingAd ? (byte) 1 : (byte) 0);
            }
            if (!SideMenuAutoCompleteRecycler.this.mIsDisplayingCategoryBar) {
                $b0 = (byte) 0;
            }
            return $i1 + $b0;
        }

        public int getItemViewType(int $i0) throws  {
            if (SideMenuAutoCompleteRecycler.this.mIsDisplayingCategoryBar) {
                if ($i0 == 0) {
                    return SideMenuAutoCompleteRecycler.ITEM_TYPE_CATEOGRIES_BAR;
                }
                $i0--;
            }
            if (SideMenuAutoCompleteRecycler.this.mIsDisplayingAd && $i0 == 0) {
                return SideMenuAutoCompleteRecycler.ITEM_TYPE_AD;
            }
            return SideMenuAutoCompleteRecycler.ITEM_TYPE_REGULAR;
        }
    }

    public class AutoCompleteItemModel {
        private Runnable mAction;
        private AddressItem mAddressItem;
        private int mBoldLength;
        private int mBoldStart;
        private DistanceAndUnits mDistance;
        private boolean mHasPrefillButton;
        private String mIconName;
        private int mIconResourceId;
        private int mIndex;
        private boolean mIsAd;
        private boolean mIsBrand;
        private MyStoreModel mStoreModel;
        private String mSubTitle;
        private String mTitle;
        private int mType;
        private String mVenueId;

        public AutoCompleteItemModel(final AddressItem $r2) throws  {
            this.mTitle = $r2.getTitle();
            this.mSubTitle = TextUtils.isEmpty($r2.getSecondaryTitle()) ? $r2.getAddress() : $r2.getSecondaryTitle();
            if (TextUtils.isEmpty(this.mTitle)) {
                this.mTitle = this.mSubTitle;
                this.mSubTitle = "";
            }
            if ($r2.venueData != null) {
                this.mVenueId = $r2.venueData.id;
            }
            switch ($r2.getType()) {
                case 1:
                    this.mIconResourceId = C1283R.drawable.autocomplete_home;
                    break;
                case 2:
                case 4:
                case 5:
                case 6:
                case 7:
                case 10:
                    break;
                case 3:
                    this.mIconResourceId = C1283R.drawable.autocomplete_work;
                    break;
                case 8:
                    this.mIconResourceId = C1283R.drawable.autocomplete_history;
                    break;
                case 9:
                case 11:
                    this.mIconResourceId = C1283R.drawable.list_icon_calendar;
                    break;
                default:
                    break;
            }
            this.mIconResourceId = $r2.getImage().intValue();
            this.mAddressItem = $r2;
            this.mAction = new Runnable(SideMenuAutoCompleteRecycler.this) {
                public void run() throws  {
                    int $i0 = AutoCompleteItemModel.this.mAddressItem.getType();
                    if ($r2.getType() != 8 || $r2.getMeetingId() == null) {
                        if ($i0 == 8 || $i0 == 1 || $i0 == 3 || $i0 == 13) {
                            $r2.setCategory(Integer.valueOf(2));
                        }
                        DriveToNativeManager.getInstance().navigate($r2, null);
                        AppService.getMainActivity().getLayoutMgr().closeSwipeableLayout();
                        return;
                    }
                    PlannedDriveActivity.setNavigationAddressItem($r2);
                    NativeManager.getInstance().AutoCompletePlaceClicked($r2.getId(), $r2.VanueID, null, null, $r2.getMeetingId(), true, null, true, 0, null, null);
                    AppService.getMainActivity().getLayoutMgr().closeSwipeableLayout();
                }
            };
            this.mType = $r2.getType();
            this.mDistance = NativeManager.getInstance().mathDistanceAndUnitsToCurrentLocation($r2, true, 0);
        }

        public AutoCompleteItemModel(final PlaceData $r2, int boldStart, int $i1, int $i2) throws  {
            this.mBoldLength = $i1;
            this.mTitle = $r2.mTitle;
            this.mVenueId = $r2.mVenueId;
            this.mIndex = $i2;
            if (TextUtils.isEmpty(this.mTitle.trim())) {
                this.mTitle = $r2.mSecondaryTitle;
                this.mSubTitle = "";
            } else {
                this.mSubTitle = $r2.mSecondaryTitle;
            }
            this.mHasPrefillButton = true;
            if (this.mBoldLength > this.mTitle.length()) {
                this.mBoldLength = this.mTitle.length();
            }
            this.mIconResourceId = SideMenuAutoCompleteRecycler.this.chooseIconResourceId($r2, this.mIconResourceId);
            final boolean $z0 = $r2.mVenueId != null ? $r2.mVenueId.toLowerCase().contains("googleplaces.") : false;
            this.mAction = new Runnable(SideMenuAutoCompleteRecycler.this) {
                public void run() throws  {
                    int $i0;
                    PlaceData $r5;
                    int $i02;
                    String $r1;
                    boolean $z0 = false;
                    AnalyticsBuilder $r2 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_CLICK).addParam("TYPE", SideMenuAutoCompleteRecycler.this.getAnalyticsType($r2)).addParam("LINE_NUMBER", (long) AutoCompleteItemModel.this.mIndex);
                    SideMenuAutoCompleteRecycler $r4 = SideMenuAutoCompleteRecycler.this;
                    $r2 = $r2.addParam(AnalyticsEvents.ANALYTICS_EVENTS_IS_AD_DISPLAYED, String.valueOf($r4.mIsDisplayingAd).toUpperCase());
                    $r4 = SideMenuAutoCompleteRecycler.this;
                    $r2 = $r2.addParam("QUERY", $r4.mCurrentSearchTerm);
                    if (AutoCompleteItemModel.this.mDistance != null) {
                        $r2.addParam(AnalyticsEvents.ANALYTICS_AUTOCOMPLETE_DISTANCE, AutoCompleteItemModel.this.mDistance.distance);
                    }
                    $r2.send();
                    $r4 = SideMenuAutoCompleteRecycler.this;
                    if ($r4.mHistoryAddressItems != null) {
                        $i0 = $r2;
                        $r5 = $i0;
                        if ($i0.mLocalIndex >= 0) {
                            $i0 = $r2;
                            $r5 = $i0;
                            $i02 = $i0.mLocalIndex;
                            $r4 = SideMenuAutoCompleteRecycler.this;
                            if ($i02 < $r4.mHistoryAddressItems.length) {
                                $r4 = SideMenuAutoCompleteRecycler.this;
                                AddressItem[] $r7 = $r4.mHistoryAddressItems;
                                $i0 = $r2;
                                $r5 = $i0;
                                AddressItem $r8 = $r7[$i0.mLocalIndex];
                                $r4 = SideMenuAutoCompleteRecycler.this;
                                if ($r4.mMode == Mode.Normal) {
                                    Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
                                    intent.putExtra(PublicMacros.ADDRESS_ITEM, $r8);
                                    $r1 = $r8.VanueID;
                                    if (!TextUtils.isEmpty($r1)) {
                                        $z0 = true;
                                    }
                                    intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, $z0);
                                    AppService.getActiveActivity().startActivityForResult(intent, 1);
                                    return;
                                }
                                $r4 = SideMenuAutoCompleteRecycler.this;
                                if ($r4.mMode == Mode.PlannedDriveSelectOrigin) {
                                    PlannedDriveActivity.setFromAddressItem($r8);
                                    AppService.getActiveActivity().finish();
                                    return;
                                }
                                $r4 = SideMenuAutoCompleteRecycler.this;
                                if ($r4.mMode == Mode.PlannedDriveSelectDestination) {
                                    PlannedDriveActivity.setNavigationAddressItem($r8);
                                    AppService.getActiveActivity().finish();
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    PlaceData $r52 = $r2;
                    if (!$r52.hasLocation() || $z0) {
                        NativeManager.getInstance().OpenProgressPopup(NativeManager.getInstance().getLanguageString(290));
                        DriveToNativeManager $r15 = DriveToNativeManager.getInstance();
                        $i02 = DriveToNativeManager.UH_SEARCH_ADD_RESULT;
                        $r4 = SideMenuAutoCompleteRecycler.this;
                        $r15.setUpdateHandler($i02, $r4.mHandler);
                        NativeManager $r13 = NativeManager.getInstance();
                        $r1 = $r2;
                        String $r53 = $r1;
                        String $r17 = $r1.mVenueId;
                        $r1 = $r2;
                        $r53 = $r1;
                        String $r18 = $r1.mReference;
                        String $r19 = AutoCompleteItemModel.this.mTitle;
                        $i0 = $r2;
                        $r5 = $i0;
                        $i02 = $i0.mfeature;
                        $r1 = $r2;
                        $r53 = $r1;
                        String $r12 = $r1.mResponse;
                        $r4 = SideMenuAutoCompleteRecycler.this;
                        $r13.AutoCompletePlaceClicked(null, $r17, $r18, null, null, false, $r19, false, $i02, $r12, $r4.mCurrentSearchTerm);
                        return;
                    }
                    AddressItem addressItem = new AddressItem($r2);
                    $r4 = SideMenuAutoCompleteRecycler.this;
                    if ($r4.mMode == Mode.Normal) {
                        intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
                        intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
                        intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
                        AppService.getActiveActivity().startActivityForResult(intent, 1);
                        return;
                    }
                    $r4 = SideMenuAutoCompleteRecycler.this;
                    if ($r4.mMode == Mode.PlannedDriveSelectOrigin) {
                        PlannedDriveActivity.setFromAddressItem(addressItem);
                        AppService.getActiveActivity().finish();
                        return;
                    }
                    $r4 = SideMenuAutoCompleteRecycler.this;
                    if ($r4.mMode == Mode.PlannedDriveSelectDestination) {
                        PlannedDriveActivity.setNavigationAddressItem(addressItem);
                        AppService.getActiveActivity().finish();
                    }
                }
            };
            if ($r2.mLocX == -1 || $r2.mLocY == -1) {
                this.mDistance = null;
            } else {
                this.mDistance = NativeManager.getInstance().mathDistanceAndUnitsToCurrentLocation($r2.mLocX, $r2.mLocY, true, 0);
            }
        }

        public AutoCompleteItemModel(String $r2, int $i0) throws  {
            this.mTitle = $r2;
            this.mSubTitle = null;
            this.mBoldStart = 0;
            this.mBoldLength = this.mTitle.length();
            this.mIconResourceId = $i0;
            this.mHasPrefillButton = false;
        }

        public AutoCompleteItemModel(final SideMenuAutoCompleteRecycler $r1, final MyStoreModel $r2) throws  {
            String $r3 = null;
            SideMenuAutoCompleteRecycler.this = $r1;
            this.mTitle = $r2.getName();
            this.mSubTitle = null;
            this.mBoldStart = 0;
            this.mBoldLength = this.mTitle.length();
            this.mHasPrefillButton = false;
            this.mIconName = $r2.getCorrectIcon();
            this.mVenueId = $r2.getId();
            this.mIsAd = $r2.isAdvertiser();
            this.mIsBrand = true;
            if (!TextUtils.isEmpty($r2.getDistance())) {
                $r3 = String.format(DisplayStrings.displayString(DisplayStrings.DS_MY_STORES_NEAREST_STORE_DISTANCE), new Object[]{$r2.getDistance()});
            }
            this.mSubTitle = $r3;
            this.mStoreModel = $r2;
            setAction(new Runnable() {
                public void run() throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_STORES_BRAND_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AutoCompleteItemModel.this.mVenueId).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_INDEX, (long) SideMenuAutoCompleteRecycler.this.mDefaultItemModels.indexOf(AutoCompleteItemModel.this)).send();
                    if (AutoCompleteItemModel.this.mIsAd) {
                        MyWazeNativeManager.getInstance().sendAdBrandStats(AnalyticsEvents.ANALYTICS_EVENT_MY_STORES_ADS_BRAND_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_ADS_ZERO_STATE_SEARCH, AutoCompleteItemModel.this.getMyStoreModel().getId(), SideMenuAutoCompleteRecycler.this.mDefaultItemModels.indexOf(AutoCompleteItemModel.this));
                    }
                    SideMenuAutoCompleteRecycler.this.openSearchScreenForBrandId(AutoCompleteItemModel.this.mVenueId, $r2.getName());
                }
            });
        }

        public void prefill() throws  {
            SideMenuAutoCompleteRecycler.this.mAdHandler.setSearchTerm(this.mTitle, true);
        }

        public boolean hasPrefillButton() throws  {
            return this.mHasPrefillButton;
        }

        public Runnable getAction() throws  {
            return this.mAction;
        }

        public void setAction(Runnable $r1) throws  {
            this.mAction = $r1;
        }

        public String getTitle() throws  {
            return this.mTitle;
        }

        public String getVenueId() throws  {
            return this.mVenueId;
        }

        public String getSubTitle() throws  {
            return this.mSubTitle;
        }

        public int getIconResourceId() throws  {
            return this.mIconResourceId;
        }

        public int getBoldLength() throws  {
            return this.mBoldLength;
        }

        public int getBoldStart() throws  {
            return this.mBoldStart;
        }

        public String getIconName() throws  {
            return this.mIconName;
        }

        public MyStoreModel getMyStoreModel() throws  {
            return this.mStoreModel;
        }

        public DistanceAndUnits getDistance() throws  {
            return this.mDistance;
        }

        public AddressItem getAddressItem() throws  {
            return this.mAddressItem;
        }
    }

    private class AutoCompleteViewHolder extends ViewHolder {
        private AutoCompleteItemView mAutoCompleteItemView;

        public AutoCompleteViewHolder(AutoCompleteItemView $r2) throws  {
            super($r2);
            this.mAutoCompleteItemView = $r2;
            this.mAutoCompleteItemView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight)));
        }

        public AutoCompleteViewHolder(View $r2, int $i0) throws  {
            super($r2);
            this.mAutoCompleteItemView = null;
            LayoutParams $r3 = new LayoutParams(-1, $i0);
            $r3.bottomMargin = PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight) - $i0;
            $r2.setLayoutParams($r3);
        }

        public void setModel(AutoCompleteItemModel $r1) throws  {
            if (this.mAutoCompleteItemView != null) {
                this.mAutoCompleteItemView.setModel($r1);
            }
        }

        public AutoCompleteItemModel getModel() throws  {
            return this.mAutoCompleteItemView.getModel();
        }
    }

    public enum Mode {
        Normal,
        PlannedDriveSelectOrigin,
        PlannedDriveSelectDestination
    }

    private SideMenuCategoryBar getCategoryBar() throws  {
        if (this.menuCategoryBar == null) {
            this.menuCategoryBar = new SideMenuCategoryBar(getContext());
            if (this.menuCategoryBar.isAvailable()) {
                this.mIsDisplayingCategoryBar = true;
            } else {
                this.mIsDisplayingCategoryBar = false;
            }
        }
        return this.menuCategoryBar;
    }

    public SideMenuAutoCompleteRecycler(Context $r1) throws  {
        this($r1, null);
    }

    public SideMenuAutoCompleteRecycler(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SideMenuAutoCompleteRecycler(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mAutoCompleteRequest = null;
        this.mIsDisplayingCategoryBar = true;
        this.mHideCategoryBar = false;
        this.menuCategoryBar = null;
        this.mMode = Mode.Normal;
        this.mHandler = new MicroHandler(this);
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mDefaultItemModels = new ArrayList();
        this.mAutoCompleteResults = new ArrayList();
        this.mIsDisplayingDefaults = true;
        this.mCurrentSearchTerm = "";
        setLayoutManager(new LinearLayoutManager(getContext()) {
            public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
                if (SideMenuAutoCompleteRecycler.this.mAdHandler != null) {
                    SideMenuAutoCompleteRecycler.this.mAdHandler.hideKeyboard();
                }
                return super.scrollVerticallyBy($i0, $r1, $r2);
            }
        });
        SideMenuAutoCompleteRecycler sideMenuAutoCompleteRecycler = this;
        setAdapter(new AutoCompleteAdapter());
        this.mGetHistoryCallback = new C19292();
        this.mAdWebView = new WebView(getContext());
        if (!isInEditMode()) {
            this.mAdWebView.setWebChromeClient(new C19323());
            this.mAdWebView.setWebViewClient(new C19344());
            this.mAdWebView.getSettings().setJavaScriptEnabled(true);
            List $r11 = this.mDefaultItemModels;
            addFavoritesItem($r11.size());
            $r11 = this.mDefaultItemModels;
            int $i0 = $r11.size();
            this.mDefaultItemModels.add(new AutoCompleteItemModel(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MAIN_MENU_SEARCH_HISTORY), C1283R.drawable.autocomplete_history));
            $r11 = this.mDefaultItemModels;
            List $r112 = $r11;
            ((AutoCompleteItemModel) $r11.get($i0)).setAction(new C19355());
            this.mAdWebView.loadUrl(NativeManager.getInstance().GetWazeAutocompleteAdsUrl());
            this.mAdWebView.setOnTouchListener(new C19366());
        }
    }

    private void addFavoritesItem(int $i0) throws  {
        this.mDefaultItemModels.add($i0, new AutoCompleteItemModel(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MAIN_MENU_SEARCH_FAVORITES), C1283R.drawable.autocomplete_favorites));
        ((AutoCompleteItemModel) this.mDefaultItemModels.get($i0)).setAction(new C19377());
    }

    public void setMode(final Mode $r1) throws  {
        this.mMode = $r1;
        if (this.mMode == Mode.PlannedDriveSelectOrigin || this.mMode == Mode.PlannedDriveSelectDestination) {
            this.mDefaultItemModels.clear();
            if (this.mMode == Mode.PlannedDriveSelectOrigin) {
                this.mDefaultItemModels.add(0, new AutoCompleteItemModel(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_AUTOCOMPLETE_CURRENT_LOCATION), C1283R.drawable.ac_location_icon));
                ((AutoCompleteItemModel) this.mDefaultItemModels.get(0)).setAction(new C19388());
            }
            DriveToNativeManager.getInstance().getTopTenFavorites(new DriveToGetAddressItemArrayCallback() {
                public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
                    int $i0 = 0;
                    if ($r1.length > 0 && $r1[0].getType() == 1) {
                        SideMenuAutoCompleteRecycler.this.addPlannedDriveItem($r1[0], $r1);
                        $i0 = 0 + 1;
                    }
                    if ($r1.length > $i0 && $r1[$i0].getType() == 3) {
                        SideMenuAutoCompleteRecycler.this.addPlannedDriveItem($r1[$i0], $r1);
                        $i0++;
                    }
                    while ($i0 < $r1.length && SideMenuAutoCompleteRecycler.this.mDefaultItemModels.size() < 12) {
                        if ($r1[$i0].getType() == 8) {
                            SideMenuAutoCompleteRecycler.this.addPlannedDriveItem($r1[$i0], $r1);
                        }
                        $i0++;
                    }
                    SideMenuAutoCompleteRecycler.this.addFavoritesItem(SideMenuAutoCompleteRecycler.this.mDefaultItemModels.size());
                }
            });
        }
    }

    private void addPlannedDriveItem(final AddressItem $r1, final Mode $r2) throws  {
        int $i0 = this.mDefaultItemModels.size();
        this.mDefaultItemModels.add($i0, new AutoCompleteItemModel($r1));
        ((AutoCompleteItemModel) this.mDefaultItemModels.get($i0)).setAction(new Runnable() {
            public void run() throws  {
                if ($r2 == Mode.PlannedDriveSelectDestination) {
                    SideMenuAutoCompleteRecycler.this.tryPlanDriveToAddress($r1);
                } else if ($r2 == Mode.PlannedDriveSelectOrigin) {
                    PlannedDriveActivity.setFromAddressItem($r1);
                    AppService.getActiveActivity().finish();
                }
            }
        });
    }

    public void setIsSearchOnMap(boolean $z0) throws  {
        this.mIsSearchOnMap = $z0;
    }

    public void setRecents(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/navigate/AddressItem;", ">;)V"}) List<AddressItem> $r1) throws  {
        if ($r1 != null) {
            this.mDefaultItemModels.clear();
            for (AddressItem $r5 : $r1) {
                if ($r5.getType() == 1 || $r5.getType() == 3 || $r5.getType() == 8 || $r5.getType() == 11 || $r5.getType() == 9) {
                    this.mDefaultItemModels.add(new AutoCompleteItemModel($r5));
                }
            }
            int $i0 = 0;
            List $r12 = this.mDefaultItemModels;
            if ($r12.size() > 0 && ((AutoCompleteItemModel) this.mDefaultItemModels.get(0)).mType == 1) {
                $i0 = 0 + 1;
            }
            $r12 = this.mDefaultItemModels;
            if ($r12.size() > $i0) {
                $r12 = this.mDefaultItemModels;
                if (((AutoCompleteItemModel) $r12.get($i0)).mType == 3) {
                    $i0++;
                }
            }
            this.mDefaultItemModels.add($i0, new AutoCompleteItemModel(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MAIN_MENU_SEARCH_GAS_STATIONS), C1283R.drawable.autocomplete_gas));
            $r12 = this.mDefaultItemModels;
            ((AutoCompleteItemModel) $r12.get($i0)).setAction(new Runnable() {
                public void run() throws  {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK, "ACTION", "GAS");
                    Intent $r1 = new Intent(SideMenuAutoCompleteRecycler.this.getContext(), SearchResultsActivity.class);
                    $r1.putExtra(PublicMacros.SEARCH_CATEGORY, "GAS_STATION");
                    $r1.putExtra(PublicMacros.SEARCH_MODE, 2);
                    if (SideMenuAutoCompleteRecycler.this.mMode == Mode.PlannedDriveSelectOrigin) {
                        $r1.putExtra(PlannedDriveAlternateFromActivity.MODE, "planned_drive_origin");
                    } else if (SideMenuAutoCompleteRecycler.this.mMode == Mode.PlannedDriveSelectDestination) {
                        $r1.putExtra(PlannedDriveAlternateFromActivity.MODE, "planned_drive_destination");
                    }
                    AppService.getActiveActivity().startActivityForResult($r1, 1);
                }
            });
            addFavoritesItem($i0 + 1);
            getAdapter().notifyDataSetChanged();
        }
    }

    public void setAdHandler(AutoCompleteAdHandler $r1) throws  {
        this.mAdHandler = $r1;
    }

    public void loadHistory() throws  {
        this.mAdWebView.loadUrl(NativeManager.getInstance().GetWazeAutocompleteAdsUrl());
        DriveToNativeManager.getInstance().getAutoCompleteData(this.mGetHistoryCallback);
        getCategoryBar().appearify();
        boolean $z0 = ConfigManager.getInstance().getConfigValueBool(409);
        if (this.mMode == Mode.Normal && $z0) {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    final MyStoreModel[] $r3 = MyWazeNativeManager.getInstance().getNearbyStoresNTV();
                    if ($r3 != null) {
                        SideMenuAutoCompleteRecycler.this.post(new Runnable() {
                            public void run() throws  {
                                SideMenuAutoCompleteRecycler.this.processNearbyStores($r3);
                            }
                        });
                    }
                }
            });
        }
    }

    private void processNearbyStores(MyStoreModel[] $r1) throws  {
        for (MyStoreModel $r3 : $r1) {
            int $i2 = 0;
            while ($i2 < this.mDefaultItemModels.size()) {
                if (((AutoCompleteItemModel) this.mDefaultItemModels.get($i2)).getVenueId() != null && ((AutoCompleteItemModel) this.mDefaultItemModels.get($i2)).getVenueId().equals($r3.getId())) {
                    this.mDefaultItemModels.remove($i2);
                    break;
                }
                $i2++;
            }
            this.mDefaultItemModels.add(new AutoCompleteItemModel(this, $r3));
        }
        getAdapter().notifyDataSetChanged();
    }

    public void appearifyCategoryMenu() throws  {
        if (this.menuCategoryBar.isAvailable()) {
            this.menuCategoryBar.appearify();
        }
    }

    public void beginSearchTerm(String $r1) throws  {
        if (TextUtils.isEmpty($r1) && (this.mAutoCompleteRequest != null || !this.mIsDisplayingDefaults)) {
            this.mIsDisplayingDefaults = true;
            this.mCurrentSearchTerm = "";
            if (getCategoryBar().isAvailable() && !this.mHideCategoryBar) {
                this.mIsDisplayingCategoryBar = true;
                getCategoryBar().appearify();
            }
            this.mAutoCompleteResults.clear();
            onNoAdReceived();
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_TEXT_DELETED, null, null);
            if (this.mAutoCompleteRequest != null) {
                this.mAutoCompleteRequest.cancel(true);
                this.mAutoCompleteRequest = null;
            }
            getAdapter().notifyDataSetChanged();
        } else if (!TextUtils.isEmpty($r1) && !this.mCurrentSearchTerm.equals($r1)) {
            this.mCurrentSearchTerm = $r1;
            beginLoadingSearchTerm();
            this.mIsDisplayingCategoryBar = false;
            getAdapter().notifyDataSetChanged();
            getCategoryBar().setVisibility(8);
        }
    }

    private void beginLoadingSearchTerm() throws  {
        if (this.mAutoCompleteRequest == null || !this.mAutoCompleteRequest.getSearchTerm().equals(this.mCurrentSearchTerm)) {
            if (!(this.mAutoCompleteRequest == null || this.mAutoCompleteRequest.getSearchTerm().equals(this.mCurrentSearchTerm))) {
                this.mAutoCompleteRequest.cancel(true);
            }
            this.mAutoCompleteRequest = new AutoCompleteRequest(this.mCurrentSearchTerm, this.mHistoryAddressItems, this);
            this.mAutoCompleteRequest.execute(new Void[0]);
        }
    }

    private void onAdReceived(PlaceData $r1, int position) throws  {
        if (this.mCurrentSearchTerm != null && this.mCurrentSearchTerm.length() >= 3) {
            String $r2;
            this.mIsDisplayingAd = true;
            this.mAdWebView.setTag($r1);
            if ($r1.mAdsIsServer) {
                $r2 = $r1.mAdsUrl;
            } else {
                $r2 = "javascript:window.W.adios.setQueryString(\"" + $r1.mAdsUrl + "\")";
            }
            if (VERSION.SDK_INT >= 19) {
                this.mAdWebView.evaluateJavascript($r2, null);
            } else {
                this.mAdWebView.loadUrl($r2);
            }
            $r2 = this.mCurrentSearchTerm;
            String $r3 = $r1.mVenueId;
            String $r4 = $r1.mVenueContext;
            Analytics.logAdsSearchEvent(AnalyticsEvents.ANALYTICS_EVENT_ADS_DISPLAYED, AnalyticsEvents.ANALYTICS_EVENT_INFO_ADS_AUTOCOMPLETE_SEARCH, -1, -1, 0, false, $r2, "", $r3, $r4);
        }
    }

    private void onNoAdReceived() throws  {
        this.mIsDisplayingAd = false;
    }

    public void onAutoCompleteResult(@Signature({"(", "Lcom/waze/menus/AutoCompleteRequest;", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;I)V"}) AutoCompleteRequest $r1, @Signature({"(", "Lcom/waze/menus/AutoCompleteRequest;", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;I)V"}) List<PlaceData> $r2, @Signature({"(", "Lcom/waze/menus/AutoCompleteRequest;", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;I)V"}) int $i0) throws  {
        if ($r1 == this.mAutoCompleteRequest) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_SHOWN, "QUERY|NUM_RESULTS|ERROR_CODE", String.valueOf(this.mCurrentSearchTerm) + "|" + ($r2.size() - 1) + "|" + $i0);
            this.mAutoCompleteRequest = null;
            this.mIsDisplayingDefaults = false;
            this.mAutoCompleteResults.clear();
            boolean $z0 = false;
            for ($i0 = 0; $i0 < $r2.size(); $i0++) {
                if (((PlaceData) $r2.get($i0)).mIsAds && this.mCurrentSearchTerm != null && this.mCurrentSearchTerm.length() >= 3) {
                    onAdReceived((PlaceData) $r2.get($i0), $i0);
                    $z0 = true;
                } else if (!((PlaceData) $r2.get($i0)).mIsAds) {
                    int $i1;
                    String $r6 = ((PlaceData) $r2.get($i0)).mTitle;
                    String $r10 = this.mCurrentSearchTerm;
                    if ($r6.contains($r10)) {
                        $r6 = ((PlaceData) $r2.get($i0)).mTitle;
                        $r10 = this.mCurrentSearchTerm;
                        $i1 = $r6.indexOf($r10);
                    } else {
                        $i1 = 0;
                    }
                    AutoCompleteItemModel autoCompleteItemModel = new AutoCompleteItemModel((PlaceData) $r2.get($i0), $i1, this.mCurrentSearchTerm.length(), $i0);
                    PlaceData $r9 = (PlaceData) $r2.get($i0);
                    if ($r9.mSearchTerm != null) {
                        final PlaceData placeData = $r9;
                        final int i = $i0;
                        final AutoCompleteItemModel autoCompleteItemModel2 = autoCompleteItemModel;
                        autoCompleteItemModel.setAction(new Runnable() {
                            public void run() throws  {
                                String str = "LINE_NUMBER";
                                str = "QUERY";
                                AnalyticsBuilder $r1 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_CLICK).addParam("TYPE", SideMenuAutoCompleteRecycler.this.getAnalyticsType(placeData)).addParam(str, (long) i).addParam(AnalyticsEvents.ANALYTICS_EVENTS_IS_AD_DISPLAYED, String.valueOf(SideMenuAutoCompleteRecycler.this.mIsDisplayingAd).toUpperCase()).addParam(str, SideMenuAutoCompleteRecycler.this.mCurrentSearchTerm);
                                if (autoCompleteItemModel2.getDistance() != null) {
                                    $r1.addParam(AnalyticsEvents.ANALYTICS_AUTOCOMPLETE_DISTANCE, autoCompleteItemModel2.getDistance().distance);
                                }
                                $r1.send();
                                SideMenuAutoCompleteRecycler.this.openSearchScreen(placeData.mSearchTerm);
                            }
                        });
                    }
                    this.mAutoCompleteResults.add(autoCompleteItemModel);
                }
            }
            if (!$z0) {
                onNoAdReceived();
            }
            getAdapter().notifyDataSetChanged();
        }
    }

    public void openSearchScreen() throws  {
        openSearchScreen(this.mCurrentSearchTerm);
    }

    public void openSearchScreen(String $r1) throws  {
        String $r4 = NativeManager.getInstance().isCategorySearchNTV($r1);
        this.mAdHandler.hideKeyboard();
        if ($r1.startsWith("#test#")) {
            DriveToNativeManager.getInstance().search($r4, null, null, $r1, true, null);
            this.mAdHandler.closeSideMenu();
            return;
        }
        Intent intent = new Intent(getContext(), SearchResultsActivity.class);
        if ($r4 == null || $r4.equals("")) {
            intent.putExtra(PublicMacros.SEARCH_STRING_KEY, $r1);
        } else {
            intent.putExtra(PublicMacros.SEARCH_CATEGORY, $r4);
        }
        if (this.mMode == Mode.PlannedDriveSelectOrigin) {
            intent.putExtra(PlannedDriveAlternateFromActivity.MODE, "planned_drive_origin");
        } else {
            if (this.mMode == Mode.PlannedDriveSelectDestination) {
                intent.putExtra(PlannedDriveAlternateFromActivity.MODE, "planned_drive_destination");
            }
        }
        intent.putExtra(PublicMacros.SEARCH_MODE, 2);
        AppService.getActiveActivity().startActivityForResult(intent, 1);
    }

    public void openSearchScreenForBrandId(String $r1, String $r2) throws  {
        Intent $r3 = new Intent(getContext(), SearchResultsActivity.class);
        $r3.putExtra(PublicMacros.SEARCH_BRAND_ID, $r1);
        $r3.putExtra(SearchResultsActivity.CATEGORY_GROUP_SEARCH_PROVIDER, true);
        $r3.putExtra(PublicMacros.SEARCH_MODE, 2);
        $r3.putExtra(PublicMacros.SEARCH_TITLE, $r2);
        AppService.getActiveActivity().startActivityForResult($r3, 1);
    }

    public void unsetSearchAddHandler() throws  {
        DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
    }

    public synchronized void handleMessage(Message $r1) throws  {
        int $i0 = $r1;
        $r1 = $i0;
        if ($i0.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            NativeManager.getInstance().CloseProgressPopup();
            AddressItem $r8 = (AddressItem) $r1.getData().getSerializable("address_item");
            if (this.mMode == Mode.Normal) {
                Intent $r2 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
                $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r8);
                AppService.getActiveActivity().startActivityForResult($r2, 1);
            } else if (this.mMode == Mode.PlannedDriveSelectOrigin) {
                PlannedDriveActivity.setFromAddressItem($r8);
                AppService.getActiveActivity().finish();
            } else if (this.mMode == Mode.PlannedDriveSelectDestination) {
                tryPlanDriveToAddress($r8);
            }
        }
    }

    private void tryPlanDriveToAddress(final AddressItem $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private int danger;

            class C19261 implements OnClickListener {
                C19261() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        DriveToNativeManager.getInstance().addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                        PlannedDriveActivity.setNavigationAddressItem($r1);
                        AppService.getActiveActivity().finish();
                        return;
                    }
                    DriveToNativeManager.getInstance().addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                }
            }

            class C19272 implements OnCancelListener {
                C19272() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    DriveToNativeManager.getInstance().addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, "BACK");
                }
            }

            public void event() throws  {
                this.danger = DriveToNativeManager.getInstance().isInDangerZoneNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue());
            }

            public void callback() throws  {
                if (this.danger >= 0) {
                    NativeManager.getInstance();
                    DriveToNativeManager.getInstance();
                    MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(NativeManager.getInstance().getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), NativeManager.getInstance().getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_GO), false, new C19261(), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_KEEP_DRIVE), NativeManager.getInstance().getLanguageString(344), -1, "dangerous_zone_icon", new C19272(), true, true);
                    return;
                }
                AddressItem $r9 = $r1;
                PlannedDriveActivity.setNavigationAddressItem($r9);
                AppService.getActiveActivity().finish();
            }
        });
    }

    private String getAnalyticsType(PlaceData $r1) throws  {
        if (this.mHistoryAddressItems == null || $r1.mLocalIndex < 0 || $r1.mLocalIndex >= this.mHistoryAddressItems.length) {
            if ($r1.mLocalIndex == -1) {
                return "PLACE";
            }
            if ($r1.mLocalIndex == -5) {
                return "CONTACT";
            }
            return ($r1.mLocalIndex == -3 || TextUtils.isEmpty($r1.mVenueId)) ? "SEARCH" : "PLACE";
        } else if (this.mHistoryAddressItems[$r1.mLocalIndex].getType() == 5) {
            return "FAVORITE";
        } else {
            if (this.mHistoryAddressItems[$r1.mLocalIndex].getType() == 8) {
                return "HISTORY";
            }
            return this.mHistoryAddressItems[$r1.mLocalIndex].getImage() != null ? "PLACE" : "SEARCH";
        }
    }

    private int chooseIconResourceId(PlaceData $r1, int $i0) throws  {
        if (this.mHistoryAddressItems == null || $r1.mLocalIndex < 0 || $r1.mLocalIndex >= this.mHistoryAddressItems.length) {
            if ($r1.mLocalIndex == -1) {
                return C1283R.drawable.autocomplete_location;
            }
            if ($r1.mLocalIndex == -5) {
                return C1283R.drawable.autocomplete_contact;
            }
            return ($r1.mLocalIndex == -3 || TextUtils.isEmpty($r1.mVenueId)) ? C1283R.drawable.autocomplete_more_results : C1283R.drawable.autocomplete_places;
        } else if (this.mHistoryAddressItems[$r1.mLocalIndex].getType() == 5) {
            return C1283R.drawable.autocomplete_favorites;
        } else {
            if (this.mHistoryAddressItems[$r1.mLocalIndex].getType() == 8) {
                return C1283R.drawable.autocomplete_history;
            }
            if (this.mHistoryAddressItems[$r1.mLocalIndex].getImage() != null) {
                return this.mHistoryAddressItems[$r1.mLocalIndex].getImage().intValue();
            }
            return $i0;
        }
    }

    public boolean shouldDisplayCategoryBar() throws  {
        return this.menuCategoryBar != null && this.menuCategoryBar.isAvailable();
    }

    public void setDisplayingCategoryBar(boolean $z0) throws  {
        boolean $z1;
        if ($z0) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        this.mHideCategoryBar = $z1;
        this.mIsDisplayingCategoryBar = $z0;
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }
}
