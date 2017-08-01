package com.waze.navigate;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.GetTitleListener;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.map.CanvasFont;
import com.waze.menus.SideMenuCategoryBar;
import com.waze.navigate.DriveToNativeManager.SortPreferencesListener;
import com.waze.phone.PhoneVerifyYourAccountActivity;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.view.tabs.SlidingTabBar;
import com.waze.view.tabs.SlidingTabBar.SlidingTabBarProvider;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SearchResultsActivity extends SearchResultsActivityBase implements DriveToNavigateCallback, DriveToSearchCallback, SlidingTabBarProvider {
    private static final String BRAND_SEARCH_PROVIDER = "search_by_brand";
    public static final String CATEGORY_GROUP_SEARCH_PROVIDER = "search_by_category_group";
    private static final String CONTACT_ENGINE = "_contact";
    private static final int PLANNED_DRIVE_MODE_NONE = 0;
    private static final int PLANNED_DRIVE_MODE_SELECT_DESTINATION = 2;
    private static final int PLANNED_DRIVE_MODE_SELECT_ORIGIN = 1;
    private static final long RETRY_DELAY = 6000;
    private static final int RQ_ADDRESS_PREVIEW = 100;
    private static final int SEARCH_RESULTS_RETRY = 5;
    private static final String WAZE_ENGINE = "waze";
    private boolean mIsGetTimeOffRoute;
    private boolean mMayReceiveDebugDialog;
    private int mPlannedDriveMode;
    private int mReloadResultsRetry = 0;
    private boolean mResultsArrived;
    private AddressItem mSearchAddressItem;
    private boolean mSearchBrandFromZeroState;
    private String mSearchBrandId;
    private String mSearchCategory;
    private String mSearchCategoryGroup;
    private List<SearchEngine> mSearchEngines;
    private int mSearchMode;
    private String mSearchTerm;
    private int mSelectedSearchEngineIndex;
    private SlidingTabBar mSlidingTabStrip;
    private SortPreferences mSortPreferences;
    private String mTitle;

    class C21551 implements GetTitleListener {
        C21551() throws  {
        }

        public void onGetTitle(String $r1) throws  {
            if ($r1 != null) {
                $r1 = NativeManager.getInstance().getLanguageString($r1);
            } else {
                $r1 = NativeManager.getInstance().getLanguageString(283);
            }
            SearchResultsActivity.this.mTitleBar.setTitle($r1);
        }
    }

    class C21562 implements DriveToGetSearchEnginesCallback {
        C21562() throws  {
        }

        public void getSearchEnginesCallback(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/navigate/SearchEngine;", ">;)V"}) List<SearchEngine> $r1) throws  {
            SearchResultsActivity.this.mSearchEngines = $r1;
            if (SearchResultsActivity.this.mSearchEngines.size() == 0) {
                SearchResultsActivity.this.displayZeroSearchEnginesError();
                return;
            }
            Collections.sort(SearchResultsActivity.this.mSearchEngines, new ProviderComparator());
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_ETA, SearchResultsActivity.this.mHandler);
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, SearchResultsActivity.this.mHandler);
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_FINALIZE, SearchResultsActivity.this.mHandler);
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_FAIL, SearchResultsActivity.this.mHandler);
            SearchResultsActivity.this.mSlidingTabStrip.setProvider(SearchResultsActivity.this);
            SearchResultsActivity.this.loadSearchResults();
            SearchResultsActivity.this.applyMapPins();
            if (TextUtils.isEmpty(SearchResultsActivity.this.mSearchBrandId)) {
                SearchResultsActivity.this.reloadFirstResults();
            }
        }
    }

    class C21573 implements SortPreferencesListener {
        C21573() throws  {
        }

        public void onComplete(SortPreferences $r1) throws  {
            SearchResultsActivity.this.mSortPreferences = $r1;
        }
    }

    class C21584 implements OnClickListener {
        C21584() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
            SearchResultsActivity.this.finish();
        }
    }

    class C21595 implements Runnable {
        C21595() throws  {
        }

        public void run() throws  {
            if (SearchResultsActivity.this.mResultsArrived) {
                SearchResultsActivity.this.mReloadResultsRetry = 0;
                return;
            }
            SearchResultsActivity.this.mReloadResultsRetry = SearchResultsActivity.this.mReloadResultsRetry + 1;
            SearchResultsActivity.this.mSelectedSearchEngineIndex = SearchResultsActivity.this.mSelectedSearchEngineIndex + 1;
            if (SearchResultsActivity.this.mSearchEngines != null && SearchResultsActivity.this.mSelectedSearchEngineIndex >= SearchResultsActivity.this.mSearchEngines.size()) {
                SearchResultsActivity.this.mSelectedSearchEngineIndex = 0;
            }
            SearchResultsActivity.this.loadSearchResults();
            SearchResultsActivity.this.applyMapPins();
            if (SearchResultsActivity.this.mReloadResultsRetry >= 5) {
                Logger.m38e("SearchResultsActivity: exeuted reloadFirstResults 5 times without success. Stopping");
                SearchResultsActivity.this.mReloadResultsRetry = 0;
                return;
            }
            SearchResultsActivity.this.reloadFirstResults();
        }
    }

    class C21606 implements Runnable {
        C21606() throws  {
        }

        public void run() throws  {
            SearchResultsActivity.this.setResult(-1);
            SearchResultsActivity.this.finish();
        }
    }

    private class ProviderComparator implements Comparator<SearchEngine> {
        private ProviderComparator() throws  {
        }

        public int compare(SearchEngine $r1, SearchEngine $r2) throws  {
            int $i0 = getSearchEngineValue($r1);
            int $i1 = getSearchEngineValue($r2);
            if ($i0 < $i1) {
                return -1;
            }
            return $i0 > $i1 ? 1 : 0;
        }

        private int getSearchEngineValue(SearchEngine $r1) throws  {
            if ($r1.getProvider() == null) {
                return 5;
            }
            if ($r1.getProvider().equals("Venues") || $r1.getProvider().equals(SearchResultsActivity.WAZE_ENGINE)) {
                return 0;
            }
            if ($r1.getProvider().equals("googlePlacesComposite")) {
                return 1;
            }
            if ($r1.getProvider().equals("googlePlaces")) {
                return 2;
            }
            if ($r1.getProvider().equals("yellowpages")) {
                return 3;
            }
            return $r1.getProvider().equals("foursquareexplore") ? 4 : 5;
        }
    }

    protected void onCreate(android.os.Bundle r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:23:0x01b2 in {2, 8, 9, 12, 16, 17, 20, 22, 24, 30, 33, 35, 37, 38, 40} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r25 = this;
        r0 = r25;
        r1 = r26;
        super.onCreate(r1);
        r0 = r25;
        r2 = r0.mMapViewWrapper;
        r3 = r2.getMapView();
        r5 = 2131231209; // 0x7f0801e9 float:1.8078493E38 double:1.0529681237E-314;
        r0 = r25;
        r4 = r0.getString(r5);
        r3.setNativeTag(r4);
        r0 = r25;
        r2 = r0.mMapViewWrapper;
        r3 = r2.getMapView();
        r3.setRenderer();
        r5 = 2131691996; // 0x7f0f09dc float:1.901308E38 double:1.0531957827E-314;
        r0 = r25;
        r6 = r0.findViewById(r5);
        r8 = r6;
        r8 = (com.waze.view.tabs.SlidingTabBar) r8;
        r7 = r8;
        r0 = r25;
        r0.mSlidingTabStrip = r7;
        r9 = android.os.Build.VERSION.SDK_INT;
        r5 = 21;
        if (r9 < r5) goto L_0x0053;
    L_0x003d:
        r0 = r25;
        r10 = r0.getWindow();
        r0 = r25;
        r11 = r0.getResources();
        r5 = 2131623937; // 0x7f0e0001 float:1.887504E38 double:1.053162157E-314;
        r9 = r11.getColor(r5);
        r10.setStatusBarColor(r9);
    L_0x0053:
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "SearchCategory";
        r0 = r26;
        r4 = r0.getString(r13);
        r0 = r25;
        r0.mSearchCategory = r4;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "SearchCategoryGroup";
        r0 = r26;
        r4 = r0.getString(r13);
        r0 = r25;
        r0.mSearchCategoryGroup = r4;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "SearchTitle";
        r0 = r26;
        r4 = r0.getString(r13);
        r0 = r25;
        r0.mTitle = r4;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "SearchStr";
        r0 = r26;
        r4 = r0.getString(r13);
        r0 = r25;
        r0.mSearchTerm = r4;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "SearchMode";
        r0 = r26;
        r9 = r0.getInt(r13);
        r0 = r25;
        r0.mSearchMode = r9;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "Icon";
        r0 = r26;
        r4 = r0.getString(r13);
        r0 = r25;
        r0.mIcon = r4;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "SearchBrandId";
        r14 = "";
        r0 = r26;
        r4 = r0.getString(r13, r14);
        r0 = r25;
        r0.mSearchBrandId = r4;
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "search_by_category_group";
        r5 = 0;
        r0 = r26;
        r15 = r0.getBoolean(r13, r5);
        r0 = r25;
        r0.mSearchBrandFromZeroState = r15;
        r0 = r25;
        r4 = r0.mSearchBrandId;
        r15 = android.text.TextUtils.isEmpty(r4);
        if (r15 == 0) goto L_0x011c;
    L_0x0110:
        r0 = r25;
        r4 = r0.mSearchCategory;
        if (r4 != 0) goto L_0x011c;
    L_0x0116:
        r0 = r25;
        r4 = r0.mSearchCategoryGroup;
        if (r4 == 0) goto L_0x012a;
    L_0x011c:
        r5 = 2131691991; // 0x7f0f09d7 float:1.901307E38 double:1.0531957803E-314;
        r0 = r25;
        r6 = r0.findViewById(r5);
        r5 = 8;
        r6.setVisibility(r5);
    L_0x012a:
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "mode";
        r14 = "";
        r0 = r26;
        r4 = r0.getString(r13, r14);
        r13 = "planned_drive_origin";
        r15 = r4.equals(r13);
        if (r15 == 0) goto L_0x0211;
    L_0x0146:
        r5 = 1;
        r0 = r25;
        r0.mPlannedDriveMode = r5;
    L_0x014b:
        r0 = r25;
        r9 = r0.mSearchMode;
        r5 = 6;
        if (r9 == r5) goto L_0x015a;
    L_0x0152:
        r0 = r25;
        r9 = r0.mSearchMode;
        r5 = 9;
        if (r9 != r5) goto L_0x0178;
    L_0x015a:
        r0 = r25;
        r12 = r0.getIntent();
        r26 = r12.getExtras();
        r13 = "AddressItem";
        r0 = r26;
        r16 = r0.getSerializable(r13);
        r18 = r16;
        r18 = (com.waze.navigate.AddressItem) r18;
        r17 = r18;
        r0 = r17;
        r1 = r25;
        r1.mSearchAddressItem = r0;
    L_0x0178:
        r0 = r25;
        r4 = r0.mSearchCategory;
        if (r4 == 0) goto L_0x0225;
    L_0x017e:
        r19 = com.waze.NativeManager.getInstance();
        r0 = r25;
        r4 = r0.mSearchCategory;
        r20 = new com.waze.navigate.SearchResultsActivity$1;
        r0 = r20;
        r1 = r25;
        r0.<init>();
        r0 = r19;
        r1 = r20;
        r0.GetTitle(r4, r1);
    L_0x0196:
        r21 = com.waze.navigate.DriveToNativeManager.getInstance();
        r0 = r25;
        r4 = r0.mSearchCategory;
        r22 = new com.waze.navigate.SearchResultsActivity$2;
        r0 = r22;
        r1 = r25;
        r0.<init>();
        r0 = r21;
        r1 = r22;
        r0.getSearchEngines(r4, r1);
        goto L_0x01b6;
    L_0x01af:
        goto L_0x014b;
        goto L_0x01b6;
    L_0x01b3:
        goto L_0x014b;
    L_0x01b6:
        r0 = r25;
        r4 = r0.mSearchCategory;
        if (r4 != 0) goto L_0x01ce;
    L_0x01bc:
        r0 = r25;
        r4 = r0.mSearchCategoryGroup;
        if (r4 == 0) goto L_0x01f8;
    L_0x01c2:
        r0 = r25;
        r4 = r0.mSearchCategoryGroup;
        r13 = "gas_station";
        r15 = r4.equals(r13);
        if (r15 == 0) goto L_0x01f8;
    L_0x01ce:
        r0 = r25;
        r4 = r0.mSearchCategory;
        if (r4 != 0) goto L_0x01dc;
    L_0x01d4:
        r0 = r25;
        r4 = r0.mSearchCategoryGroup;
        r4 = r4.toUpperCase();
    L_0x01dc:
        r21 = com.waze.navigate.DriveToNativeManager.getInstance();
        r23 = new com.waze.navigate.SearchResultsActivity$3;
        goto L_0x01e6;
    L_0x01e3:
        goto L_0x0196;
    L_0x01e6:
        r0 = r23;
        r1 = r25;
        r0.<init>();
        goto L_0x01f1;
    L_0x01ee:
        goto L_0x0196;
    L_0x01f1:
        r0 = r21;
        r1 = r23;
        r0.getSortPreferences(r4, r1);
    L_0x01f8:
        r0 = r25;
        r0.showLoadingPopup();
        r21 = com.waze.navigate.DriveToNativeManager.getInstance();
        r19 = com.waze.NativeManager.getInstance();
        r0 = r19;
        r15 = r0.isNavigatingNTV();
        r0 = r21;
        r0.setStopPoint(r15);
        return;
    L_0x0211:
        r13 = "planned_drive_destination";
        r15 = r4.equals(r13);
        if (r15 == 0) goto L_0x021f;
    L_0x0219:
        r5 = 2;
        r0 = r25;
        r0.mPlannedDriveMode = r5;
        goto L_0x01af;
    L_0x021f:
        r5 = 0;
        r0 = r25;
        r0.mPlannedDriveMode = r5;
        goto L_0x01b3;
    L_0x0225:
        r0 = r25;
        r4 = r0.mTitle;
        if (r4 == 0) goto L_0x023b;
    L_0x022b:
        r0 = r25;
        r0 = r0.mTitleBar;
        r24 = r0;
        r0 = r25;
        r4 = r0.mTitle;
        r0 = r24;
        r0.setTitle(r4);
        goto L_0x01e3;
    L_0x023b:
        r0 = r25;
        r0 = r0.mTitleBar;
        r24 = r0;
        r5 = 3516; // 0xdbc float:4.927E-42 double:1.737E-320;
        r4 = com.waze.strings.DisplayStrings.displayString(r5);
        r0 = r24;
        r0.setTitle(r4);
        goto L_0x01ee;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.SearchResultsActivity.onCreate(android.os.Bundle):void");
    }

    private void displayZeroSearchEnginesError() throws  {
        hideLoadingPopup();
        this.mResultsArrived = true;
        Log.e("SearchResultsActivity", "Search Engines came back with 0 items! Category = " + this.mSearchCategory);
        NativeManager $r4 = NativeManager.getInstance();
        MsgBox.openMessageBoxWithCallback($r4.getLanguageString((int) DisplayStrings.DS_UHHOHE), $r4.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false, new C21584());
    }

    private void reloadFirstResults() throws  {
        postDelayed(new C21595(), 6000);
    }

    protected void applyMapPins() throws  {
        if (this.mSearchEngines != null && this.mSearchEngines.size() > 0) {
            float $f0;
            float $f1;
            boolean $z0;
            String $r4 = ((SearchEngine) this.mSearchEngines.get(this.mSelectedSearchEngineIndex)).getProvider();
            if (!TextUtils.isEmpty(this.mSearchBrandId)) {
                $r4 = BRAND_SEARCH_PROVIDER;
            }
            if (this.mSearchCategoryGroup != null) {
                $r4 = CATEGORY_GROUP_SEARCH_PROVIDER;
            }
            String $r5 = this.mSearchCategory;
            if (($r5 == null || $r5.isEmpty()) && this.mSearchCategoryGroup != null && this.mSearchCategoryGroup.equals("gas_station")) {
                $r5 = this.mSearchCategoryGroup.toUpperCase();
            }
            DriveToNativeManager.getInstance().setSearchResultPins($r5, this.mSearchCategoryGroup, $r4);
            short $s1 = this.mIsMapFullScreen ? (short) 0 : (short) 5000;
            if (this.mIsMapFullScreen) {
                $f0 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            } else {
                $f0 = 1.0f;
            }
            if (this.mIsMapFullScreen) {
                $f1 = 1.0f;
            } else {
                $f1 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            }
            if (this.mIsMapFullScreen) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            loadResultsCanvas($s1, $f0, $f1, $z0);
        }
    }

    public int getTotalTabs() throws  {
        return this.mSearchEngines != null ? this.mSearchEngines.size() : 0;
    }

    public String getTitleForTab(int $i0) throws  {
        return (this.mSearchEngines == null || this.mSearchEngines.size() == 0) ? "" : ((SearchEngine) this.mSearchEngines.get($i0)).getName();
    }

    public void onTabClick(int $i0) throws  {
        if (TextUtils.isEmpty(this.mSearchBrandId)) {
            this.mSelectedSearchEngineIndex = $i0;
            loadSearchResults();
            applyMapPins();
            if (this.mAddressItems == null || this.mAddressItems.length == 0) {
                showLoadingPopup();
            }
        }
    }

    private void loadSearchResults() throws  {
        boolean $z1 = true;
        if (this.mSearchEngines != null && this.mSearchEngines.size() != 0) {
            SearchEngine $r3 = (SearchEngine) this.mSearchEngines.get(this.mSelectedSearchEngineIndex);
            DriveToNativeManager $r5;
            if (!TextUtils.isEmpty(this.mSearchBrandId)) {
                $r5 = DriveToNativeManager.getInstance();
                if (this.mResultsArrived) {
                    $z1 = false;
                } else {
                    $z1 = true;
                }
                $r5.searchBrands($z1, this.mSearchBrandId, this.mSearchBrandFromZeroState);
                $r3.setSearched(true);
                setAddressItems(null);
            } else if ($r3.getSearched() && $r3.getErrorState() != 2) {
                getAddressItems();
            } else if (!$r3.getSearching()) {
                setAddressItems(null);
                $r3.setSearching(true);
                if (this.mSearchTerm != null && (this.mSearchTerm.startsWith("#") || this.mSearchTerm.equalsIgnoreCase("2##2"))) {
                    this.mMayReceiveDebugDialog = true;
                    MsgBox.getInstance().setHijackingDialogActivity(AppService.getMainActivity());
                }
                $r5 = DriveToNativeManager.getInstance();
                String $r4 = this.mSearchCategory;
                String $r8 = this.mSearchCategoryGroup;
                String $r9 = $r3.getProvider();
                String $r10 = this.mSearchTerm;
                if (this.mResultsArrived) {
                    $z1 = false;
                }
                $r5.search($r4, $r8, $r9, $r10, $z1, this);
            }
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 1237 && $i1 == -1 && $r1 != null) {
            AddressItem $r4 = (AddressItem) $r1.getExtras().get("ai");
            if (this.mSearchMode == 10 || this.mSearchMode == 3) {
                $r4.setSecondaryTitle($r4.getTitle());
                $r4.setTitle("Home");
            } else {
                $r4.setSecondaryTitle($r4.getTitle());
                $r4.setTitle("Work");
            }
            $r4.setCategory(Integer.valueOf(1));
            $r1.putExtra("ai", $r4);
            setResult(-1, $r1);
            finish();
        } else if ($i1 == MainActivity.VERIFY_EVENT_CODE) {
            setResult(MainActivity.VERIFY_EVENT_CODE, $r1);
            finish();
        } else if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    public void getAddressItems() throws  {
        boolean $z0 = true;
        if (this.mSearchEngines != null && this.mSearchEngines.size() != 0) {
            SearchEngine $r4 = (SearchEngine) this.mSearchEngines.get(this.mSelectedSearchEngineIndex);
            if ($r4.getProvider().equals(WAZE_ENGINE) && this.mSearchMode == 5 && $r4.getResults().length == 1) {
                AddressItem $r1 = $r4.getResults()[0];
                if ($r1.getCategory().intValue() == 1) {
                    $z0 = false;
                }
                DriveToNativeManager.getInstance().navigate($r1, this, false, $z0);
                return;
            }
            setAddressItems($r4.getResults());
            this.mSearchResultsRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        Bundle $r2;
        if ($r1.what == DriveToNativeManager.UH_ETA) {
            $r2 = $r1.getData();
            updateEta($r2.getString("provider"), $r2.getString("distance"), $r2.getString("id"));
        } else if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            $r2 = $r1.getData();
            searchAddResult($r2.getString("provider"), (AddressItem) $r2.getSerializable("address_item"));
        } else if ($r1.what == DriveToNativeManager.UH_SEARCH_FINALIZE) {
            finalizeSearch($r1.getData().getString("provider"));
        } else if ($r1.what == DriveToNativeManager.UH_SEARCH_FAIL) {
            $r2 = $r1.getData();
            searchFail($r2.getString("provider"), $r2.getString("errMsg"), $r2.getBoolean("canRetry"));
        }
        return super.myHandleMessage($r1);
    }

    void searchAddResult(String $r1, AddressItem $r2) throws  {
        SearchEngine $r3 = getEngineByProvider($r1);
        if ($r3 == null) {
            return;
        }
        if ($r1 == null || !$r1.equals(CONTACT_ENGINE)) {
            if (this.mSearchEngines.get(this.mSelectedSearchEngineIndex) == $r3) {
                View $r6 = findViewById(C1283R.id.searchResultsNoResultsLayout);
                if ($r6 != null) {
                    $r6.setVisibility(8);
                }
            }
            $r3.addResult($r2);
        }
    }

    void updateEta(String $r1, String $r2, String $r3) throws  {
        Log.d(Logger.TAG, "updateEta: " + $r1 + ", " + $r2 + ", " + $r3);
        SearchEngine $r7 = getEngineByProvider($r1);
        if ($r7 != null) {
            for (AddressItem $r4 : $r7.getResults()) {
                if ($r4 != null && $r4.getId() != null && $r4.getId().equals($r3)) {
                    $r4.setDistance($r2);
                    this.mIsGetTimeOffRoute = true;
                    break;
                }
            }
        }
        this.mSearchResultsRecycler.getAdapter().notifyDataSetChanged();
    }

    void finalizeSearch(String $r1) throws  {
        if (this.mSearchEngines != null) {
            Log.d(Logger.TAG, "Finalizing search. Active provider: " + $r1);
            boolean $z0 = (this.mSearchCategory != null && this.mSearchCategory.equals("GAS_STATION")) || (this.mSearchCategoryGroup != null && this.mSearchCategoryGroup.equals("gas_station"));
            for (SearchEngine $r7 : this.mSearchEngines) {
                if ($z0) {
                    $r7.sortResults(this.mSortPreferences);
                }
                $r7.finalizeSearch();
            }
            SearchEngine $r72 = this.mResultsArrived ? this.mSearchEngines.size() > 0 ? (SearchEngine) this.mSearchEngines.get(this.mSelectedSearchEngineIndex) : null : getEngineByProvider($r1);
            if (!(!TextUtils.isEmpty(this.mSearchBrandId) || $r72 == null || $r1 == null)) {
                if (!$r1.equals(CONTACT_ENGINE)) {
                    this.mSlidingTabStrip.setSelectedIndex(this.mSearchEngines.indexOf($r72));
                    this.mResultsArrived = true;
                    if (!$r72.requestedResultEta && $r72.getResults().length > 0) {
                        $r72.requestedResultEta = true;
                        $r1 = $r72.getProvider();
                        if (this.mSearchCategoryGroup != null) {
                            $r1 = CATEGORY_GROUP_SEARCH_PROVIDER;
                        }
                        DriveToNativeManager.getInstance().getSearchResultsEta($r1);
                    }
                }
            }
            if ($r72 != null) {
                String $r12 = this.mSearchBrandId;
                if (!TextUtils.isEmpty($r12)) {
                    this.mSlidingTabStrip.setSelectedIndex(this.mSearchEngines.indexOf($r72));
                    DriveToNativeManager.getInstance().getSearchResultsEta($r72.getProvider());
                    setAddressItems($r72.getResults());
                    this.mResultsArrived = true;
                }
            }
            applyMapPins();
        }
    }

    void searchFail(String $r1, String $r2, boolean $z0) throws  {
        for (String $r3 : $r1.split(",")) {
            SearchEngine $r5 = getEngineByProvider($r3);
            if (!($r5 == null || $r1.equals(CONTACT_ENGINE))) {
                engineFail($z0, $r5, $r2);
            }
        }
    }

    private void engineFail(boolean $z0, SearchEngine $r1, String $r2) throws  {
        $r1.setErrorState($z0 ? (byte) 2 : (byte) 1);
        if (this.mSearchEngines.get(this.mSelectedSearchEngineIndex) == $r1) {
            if ($r1.getResults().length == 0) {
                findViewById(C1283R.id.searchResultsNoResultsLayout).setVisibility(0);
                ((TextView) findViewById(C1283R.id.searchResultsNoResultsText)).setText(NativeManager.getInstance().getLanguageString($r2));
            } else {
                findViewById(C1283R.id.searchResultsNoResultsLayout).setVisibility(8);
            }
        }
        $r1.finalizeSearch();
        if (this.mSearchEngines != null && this.mSearchEngines.size() > 0 && $r1 == this.mSearchEngines.get(this.mSelectedSearchEngineIndex)) {
            setAddressItems($r1.getResults());
            this.mResultsArrived = true;
            this.mSearchResultsRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    private SearchEngine getEngineByProvider(String $r1) throws  {
        if (($r1 != null && $r1.equals(BRAND_SEARCH_PROVIDER)) || this.mSearchCategoryGroup != null) {
            return (SearchEngine) this.mSearchEngines.get(0);
        }
        for (SearchEngine $r5 : this.mSearchEngines) {
            if ($r5.getProvider().equals($r1)) {
                return $r5;
            }
        }
        return null;
    }

    protected AnalyticsBuilder getClickEvent() throws  {
        AnalyticsBuilder $r1;
        if (this.mSearchMode == 3 || this.mSearchMode == 4 || this.mSearchMode == 10 || this.mSearchMode == 11) {
            boolean $z1;
            boolean $z0 = this.mSearchMode == 3 || this.mSearchMode == 10;
            if (this.mSearchMode == 11 || this.mSearchMode == 10) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            $r1 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SEARCH_RESULTS_CLICK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_TYPE, $z0 ? "HOME" : "WORK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_STATUS, $z1 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_SET : "EDIT");
        } else {
            $r1 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_RESULTS_CLICK);
        }
        String $r2 = (this.mSearchCategoryGroup == null || !this.mSearchCategoryGroup.equals(SideMenuCategoryBar.PARKING_CATEGORY_GROUP)) ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE;
        $r1.addParam("PARKING", $r2).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ROUTING, NavigateNativeManager.instance().isNavigating() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE).addParam(AnalyticsEvents.ANALYTICS_EVENT_CATEGORICAL_SEARCH, this.mSearchCategoryGroup != null ? this.mSearchCategoryGroup : "");
        return $r1;
    }

    public void searchCallback(int $i0) throws  {
        if ($i0 == 0 && !this.mResultsArrived && this.mMayReceiveDebugDialog) {
            this.mResultsArrived = true;
            setResult(-1);
            finish();
        } else if ($i0 != 0 && this.mMayReceiveDebugDialog) {
            this.mMayReceiveDebugDialog = false;
            MsgBox.getInstance().setHijackingDialogActivity(null);
        }
    }

    public void navigateCallback(int rc) throws  {
    }

    private int getIndexOfAddressItem(AddressItem $r1) throws  {
        if (this.mAddressItems != null) {
            for (int $i0 = 0; $i0 < this.mAddressItems.length; $i0++) {
                if (this.mAddressItems[$i0] == $r1) {
                    return $i0;
                }
            }
        }
        return -1;
    }

    private void addToFavorites(AddressItem $r1) throws  {
        NameFavoriteView.showNameFavorite(this, $r1, new C21606());
    }

    private void checkAddToFavorites(final AddressItem $r1, final DriveToNativeManager $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private int danger;

            class C21611 implements OnClickListener {
                C21611() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        SearchResultsActivity.this.addToFavorites($r1);
                        $r2.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                        return;
                    }
                    $r2.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                }
            }

            class C21622 implements OnCancelListener {
                C21622() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    $r2.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, "BACK");
                }
            }

            public void event() throws  {
                this.danger = $r2.isInDangerZoneNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue());
            }

            public void callback() throws  {
                if (this.danger >= 0) {
                    NativeManager $r3 = NativeManager.getInstance();
                    MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava($r3.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), $r3.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE), false, new C21611(), $r3.getLanguageString(DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE_BUTTON), $r3.getLanguageString(344), -1, "dangerous_zone_icon", new C21622(), true, true);
                    return;
                }
                SearchResultsActivity.this.addToFavorites($r1);
            }
        });
    }

    public void onSearchResultClick(AddressItem $r1) throws  {
        if (this.mSearchEngines != null && this.mSearchEngines.size() != 0) {
            DriveToNativeManager $r4 = DriveToNativeManager.getInstance();
            int $i0 = getIndexOfAddressItem($r1);
            getClickEvent().addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_INDEX, (long) $r1.index).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RESULT_ID, $r1.getResultId()).addParam(AnalyticsEvents.ANALYTICS_EVENTS_IS_AD_DISPLAYED, String.valueOf(this.mAdDisplayed).toUpperCase(Locale.US)).addParam("ACTION", "SELECT").send();
            if ($r1.getCategory().intValue() == 4) {
                return;
            }
            if ($r1.getCategory().intValue() == 5) {
                this.mSearchTerm = $r1.getAddress();
                if (this.mSearchMode != 1) {
                    this.mSearchMode = 5;
                }
                getEngineByProvider(WAZE_ENGINE);
                this.mSlidingTabStrip.setSelectedIndex(this.mSearchEngines.indexOf(getEngineByProvider(WAZE_ENGINE)));
            } else if (this.mPlannedDriveMode == 1) {
                PlannedDriveActivity.setFromAddressItem($r1);
                finish();
            } else if (this.mPlannedDriveMode == 2) {
                PlannedDriveActivity.setNavigationAddressItem($r1);
                finish();
            } else {
                Intent intent;
                ArrayList arrayList;
                switch (this.mSearchMode) {
                    case 1:
                        checkAddToFavorites($r1, $r4);
                        return;
                    case 2:
                    case 5:
                        if ($r4.isStopPointSearchNTV()) {
                            $r4.requestStopPoint($r1.index);
                        }
                        $r4.notifyAddressItemClicked($r1.index);
                        intent = new Intent(this, AddressPreviewActivity.class);
                        arrayList = new ArrayList(Arrays.asList(((SearchEngine) this.mSearchEngines.get(this.mSelectedSearchEngineIndex)).getResults()));
                        intent.putExtra(PublicMacros.ADDRESS_ITEM_LIST, arrayList);
                        intent.putExtra(PublicMacros.ADDRESS_ITEM_SELECTED, $i0);
                        intent.putExtra(PublicMacros.ACTION_BUTTON, 1);
                        intent.putExtra(PublicMacros.CLEAR_ADS_CONTEXT, true);
                        startActivityForResult(intent, 100);
                        return;
                    case 3:
                    case 4:
                    case 10:
                    case 11:
                    case 12:
                        if (getIntent().getExtras().getBoolean(PublicMacros.SKIP_PREVIEW, false)) {
                            intent = new Intent();
                            $r1.setCategory(Integer.valueOf(1));
                            if (this.mSearchMode == 10 || this.mSearchMode == 3) {
                                $r1.setTitle("Home");
                            } else {
                                $r1.setTitle("Work");
                            }
                            intent.putExtra("ai", $r1);
                            setResult(-1, intent);
                            finish();
                            return;
                        }
                        String $r6 = AutocompleteSearchActivity.getSearchMode(this.mSearchMode);
                        intent = new Intent(this, AddressPreviewActivity.class);
                        intent.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
                        intent.putExtra(AddressPreviewActivity.COMMUTE_MODE, $r6);
                        startActivityForResult(intent, 1237);
                        return;
                    case 6:
                        if ($r4.isStopPointSearchNTV()) {
                            $r4.requestStopPoint($r1.index);
                        }
                        $r1.setCategory(Integer.valueOf(6));
                        AddressItem $r15 = this.mSearchAddressItem;
                        $r1.setTitle($r15.getTitle());
                        $r15 = this.mSearchAddressItem;
                        $r1.setMeetingId($r15.getMeetingId());
                        $r4.notifyAddressItemClicked($r1.index);
                        intent = new Intent(this, AddressPreviewActivity.class);
                        arrayList = new ArrayList(Arrays.asList(((SearchEngine) this.mSearchEngines.get(this.mSelectedSearchEngineIndex)).getResults()));
                        intent.putExtra(PublicMacros.ADDRESS_ITEM_LIST, arrayList);
                        intent.putExtra(PublicMacros.ADDRESS_ITEM_SELECTED, $i0);
                        intent = intent;
                        intent.putExtra(PublicMacros.ADDRESS_ITEM, this.mSearchAddressItem);
                        intent.putExtra(PublicMacros.ACTION_BUTTON, 1);
                        intent.putExtra(PublicMacros.CLEAR_ADS_CONTEXT, true);
                        startActivityForResult(intent, 100);
                        return;
                    case 7:
                        intent = new Intent(this, PhoneVerifyYourAccountActivity.class);
                        intent.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
                        setResult(-1, intent);
                        finish();
                        return;
                    case 8:
                        ShareUtility.OpenShareActivity(this, ShareType.ShareType_ShareSelection, null, $r1, null);
                        return;
                    case 9:
                        $r1.setCategory(Integer.valueOf(7));
                        intent = new Intent();
                        intent.putExtra("ai", $r1);
                        setResult(-1, intent);
                        finish();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected void onDestroy() throws  {
        DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_ETA, this.mHandler);
        DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_FINALIZE, this.mHandler);
        DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_FAIL, this.mHandler);
        DriveToNativeManager.getInstance().unsetSearchMapView();
        NativeManager.getInstance().setSearchResults(null);
        super.onDestroy();
    }

    public void finish() throws  {
        super.finish();
    }
}
