package com.waze.menus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Message;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolMessagingActivity;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.carpool.CarpoolUtils;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers.MicroHandler;
import com.waze.ifs.async.UpdateHandlers.MicroHandler.MicroHandlerCallback;
import com.waze.menus.AddressItemOptionsUtil.AddressItemOptionListener;
import com.waze.menus.AddressItemView.AddressItemViewListener;
import com.waze.menus.MainSideMenu.MainSideMenuActionProvider;
import com.waze.menus.SideMenuSearchBar.SearchBarActionListener;
import com.waze.mywaze.social.FacebookEventActivity;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.NameFavoriteView;
import com.waze.navigate.ParkingSearchResultsActivity;
import com.waze.navigate.PublicMacros;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.planned_drive.PlannedDriveListListener;
import com.waze.settings.SettingsCalendarActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SideMenuAddressItemRecycler extends RecyclerView implements MicroHandlerCallback, AddressItemOptionListener, AddressItemViewListener {
    private static final int ADDRESS_ITEM_TYPE = 3;
    private static final int BANNER_ITEM_TYPE = 1;
    private static final int HEADER_ITEM_TYPE = 0;
    private static final int INITIAL_PRELOADED_ADDRESS_ITEM_VIEWS = 15;
    private static final int INITIAL_RECYCLER_POOL_SIZE = 30;
    public static final int RQ_ADD_FAVORITE = 1002;
    public static final int RQ_ADD_HOME_WORK = 1001;
    private static final int SEARCH_ITEM_TYPE = 2;
    private static final int TRANSITION_ANIMATION_OFFSET_DELTA = 25;
    private static final Object rideAndRiderMutex = new Object();
    private SideMenuAdapter mAdapter;
    private LinearLayout mBackToTopButton;
    private boolean mBannerSeen;
    private View mBannerView;
    private DriveToNativeManager mDriveToNativeManager;
    private ImageView mDropShadow;
    private List<AddressItem> mFavoriteItems;
    private boolean mFirstLoadCompleted;
    private DriveToGetAddressItemArrayCallback mGetTopTenFavoritesCallback;
    private MicroHandler mHandler;
    private float mHeaderTranslation;
    private SideMenuHeaderView mHeaderView;
    private boolean mIsFullyVisible;
    private boolean mIsInSearchMode;
    private boolean mIsTransitioningModes;
    private SideMenuLayoutManager mLayoutManager;
    private final Object mListMutex;
    private int mRootHeight;
    private SideMenuSearchBar mSearchBar;
    private float mSearchDropShadowAlpha;
    private boolean mShowBanner;
    private MainSideMenuActionProvider mSideMenuActionProvider;
    private boolean mSkipLayoutChildren;
    private boolean mTooltipSessionNumberValid;
    private boolean mTooltipWasShown;

    private interface CarpoolDriveAction {
        void onDriveReceived(CarpoolDrive carpoolDrive) throws ;
    }

    class C19081 implements SearchBarActionListener {
        C19081() throws  {
        }

        public void onCancelClick() throws  {
            SideMenuAddressItemRecycler.this.transitionToNormalMode();
        }

        public void onSpeechButtonClick() throws  {
        }

        public void onSearchTextChanged(String $r1) throws  {
            if (SideMenuAddressItemRecycler.this.mSideMenuActionProvider != null) {
                SideMenuAddressItemRecycler.this.mSideMenuActionProvider.loadSearchResults($r1);
            }
        }

        public void onSearchButtonClick() throws  {
            if (SideMenuAddressItemRecycler.this.mSideMenuActionProvider != null) {
                SideMenuAddressItemRecycler.this.mSideMenuActionProvider.openSearchScreen();
            }
        }

        public void onAddClick() throws  {
        }
    }

    class C19122 implements OnClickListener {
        C19122() throws  {
        }

        public void onClick(View v) throws  {
            SideMenuAddressItemRecycler.this.transitionToSearchMode(false, false);
        }
    }

    class C19143 implements DriveToGetAddressItemArrayCallback {
        C19143() throws  {
        }

        public void getAddressItemArrayCallback(final AddressItem[] $r1) throws  {
            synchronized (SideMenuAddressItemRecycler.this.mListMutex) {
                SideMenuAddressItemRecycler.this.post(new Runnable() {
                    public void run() throws  {
                        SideMenuAddressItemRecycler $r3 = C19143.this;
                        SideMenuAddressItemRecycler $r2 = $r3;
                        $r3 = SideMenuAddressItemRecycler.this;
                        $r3.mFavoriteItems.clear();
                        $r3 = C19143.this;
                        $r2 = $r3;
                        $r3 = SideMenuAddressItemRecycler.this;
                        List $r4 = $r3.mFavoriteItems;
                        AddressItem[] $r5 = $r1;
                        $r4.addAll(Arrays.asList($r5));
                        $r3 = C19143.this;
                        $r2 = $r3;
                        $r3 = SideMenuAddressItemRecycler.this;
                        if ($r3.mFavoriteItems.size() >= 2) {
                            AddressItem addressItem = new AddressItem(Integer.valueOf(0), Integer.valueOf(0), NativeManager.getInstance().getLanguageString(16), null, null, null, Boolean.valueOf(false), Integer.valueOf(C1283R.drawable.list_icon_favorites), Integer.valueOf(1), "", Integer.valueOf(5), null, null, null, null, null, null, null, null, null, null, null);
                            $r3 = C19143.this;
                            $r2 = $r3;
                            $r3 = SideMenuAddressItemRecycler.this;
                            $r3.mFavoriteItems.add(2, addressItem);
                            addressItem = new AddressItem(Integer.valueOf(0), Integer.valueOf(0), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NAVIGATION_LIST_FUTURE_DRIVES), null, null, null, Boolean.valueOf(false), Integer.valueOf(C1283R.drawable.list_icon_later_folder), Integer.valueOf(9), "", Integer.valueOf(16), null, null, null, null, null, null, null, null, null, null, null);
                            $r3 = C19143.this;
                            $r2 = $r3;
                            $r3 = SideMenuAddressItemRecycler.this;
                            $r3.mFavoriteItems.add(3, addressItem);
                        }
                        $r3 = C19143.this;
                        $r2 = $r3;
                        SideMenuAddressItemRecycler.this.mFirstLoadCompleted = true;
                        $r3 = C19143.this;
                        $r2 = $r3;
                        $r3 = SideMenuAddressItemRecycler.this;
                        $r3.mLayoutManager.mLastAdapterSize = -1;
                        $r3 = C19143.this;
                        $r2 = $r3;
                        $r3 = SideMenuAddressItemRecycler.this;
                        $r3.mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    class C19154 implements Runnable {
        C19154() throws  {
        }

        public void run() throws  {
            SideMenuAddressItemRecycler.this.mIsTransitioningModes = false;
            SideMenuAddressItemRecycler.this.setLayoutFrozen(true);
        }
    }

    class C19165 implements Runnable {
        C19165() throws  {
        }

        public void run() throws  {
            SideMenuAddressItemRecycler.this.mIsTransitioningModes = false;
            SideMenuAddressItemRecycler.this.setLayoutFrozen(false);
        }
    }

    class C19176 implements Comparator<AddressItemView> {
        C19176() throws  {
        }

        public int compare(AddressItemView $r1, AddressItemView $r2) throws  {
            if (SideMenuAddressItemRecycler.this.mLayoutManager.getDecoratedTop($r1) < SideMenuAddressItemRecycler.this.mLayoutManager.getDecoratedTop($r2)) {
                return -1;
            }
            return SideMenuAddressItemRecycler.this.mLayoutManager.getDecoratedTop($r1) > SideMenuAddressItemRecycler.this.mLayoutManager.getDecoratedTop($r2) ? 1 : 0;
        }
    }

    class C19187 implements Runnable {
        C19187() throws  {
        }

        public void run() throws  {
            AddressItemView.totalPlannedDrives = SideMenuAddressItemRecycler.this.mDriveToNativeManager.getTotalEventsNTV();
            SideMenuAddressItemRecycler.this.mDriveToNativeManager.getTopTenFavorites(SideMenuAddressItemRecycler.this.mGetTopTenFavoritesCallback);
        }
    }

    class C19198 implements DriveToNavigateCallback {
        C19198() throws  {
        }

        public void navigateCallback(int $i0) throws  {
            if ($i0 == 0) {
                SideMenuAddressItemRecycler.this.mSideMenuActionProvider.close();
            }
        }
    }

    class C19209 implements DriveToNavigateCallback {
        C19209() throws  {
        }

        public void navigateCallback(int $i0) throws  {
            if ($i0 == 0) {
                SideMenuAddressItemRecycler.this.mSideMenuActionProvider.close();
            }
        }
    }

    private class AddressItemViewHolder extends ViewHolder {
        public AddressItemView mAddressItemView;
        public int position;

        public AddressItemViewHolder(AddressItemView $r2) throws  {
            super($r2);
            $r2.setTag(this);
            this.mAddressItemView = $r2;
            this.mAddressItemView.setListener(SideMenuAddressItemRecycler.this);
            this.mAddressItemView.getMainContentView().setOnClickListener(new OnClickListener(SideMenuAddressItemRecycler.this) {
                public void onClick(View v) throws  {
                    SideMenuAddressItemRecycler.this.onItemClick(AddressItemViewHolder.this.mAddressItemView.getAddressItem(), AddressItemViewHolder.this.position);
                }
            });
        }

        public AddressItem getAddressItem() throws  {
            return this.mAddressItemView.getAddressItem();
        }

        public void setAddressItem(AddressItem $r1, int $i0) throws  {
            this.mAddressItemView.setAddressItem($r1);
            this.position = $i0;
        }
    }

    private class SideMenuAdapter extends Adapter<ViewHolder> {

        class C19221 extends ViewOutlineProvider {
            C19221() throws  {
            }

            public void getOutline(View $r1, Outline $r2) throws  {
                $r2.setRect(0, $r1.getMeasuredHeight() / 2, $r1.getMeasuredWidth(), $r1.getMeasuredHeight());
            }
        }

        private SideMenuAdapter() throws  {
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int $i0) throws  {
            if ($i0 == 0) {
                return new StaticItemViewHolder(SideMenuAddressItemRecycler.this.mHeaderView);
            }
            if ($i0 == 1) {
                return new StaticItemViewHolder(SideMenuAddressItemRecycler.this.mBannerView);
            }
            if ($i0 == 2) {
                return new StaticItemViewHolder(SideMenuAddressItemRecycler.this.mSearchBar);
            }
            return new AddressItemViewHolder(new AddressItemView(SideMenuAddressItemRecycler.this.getContext()));
        }

        public void onBindViewHolder(ViewHolder $r1, int $i0) throws  {
            this = this;
            synchronized (SideMenuAddressItemRecycler.this.mListMutex) {
                this = this;
                if ($i0 >= SideMenuAddressItemRecycler.this.getFirstAddressItemPosition()) {
                    int $i1 = $i0 - SideMenuAddressItemRecycler.this.getFirstAddressItemPosition();
                    this = this;
                    AddressItemViewHolder $r7 = (AddressItemViewHolder) $r1;
                    $r7.setAddressItem((AddressItem) SideMenuAddressItemRecycler.this.mFavoriteItems.get($i1), $i0);
                    if (VERSION.SDK_INT >= 21) {
                        this = this;
                        if ($i1 == SideMenuAddressItemRecycler.this.mFavoriteItems.size() - 1) {
                            $r7.mAddressItemView.setElevation((float) PixelMeasure.dp(8));
                            $r7.mAddressItemView.setOutlineProvider(new C19221());
                        } else {
                            $r7.mAddressItemView.setElevation(0.0f);
                            $r7.mAddressItemView.setOutlineProvider(ViewOutlineProvider.BOUNDS);
                        }
                    }
                }
            }
        }

        public int getItemCount() throws  {
            int $i0;
            synchronized (SideMenuAddressItemRecycler.this.mListMutex) {
                $i0 = SideMenuAddressItemRecycler.this.getFirstAddressItemPosition() + (SideMenuAddressItemRecycler.this.mFavoriteItems != null ? SideMenuAddressItemRecycler.this.mFavoriteItems.size() : 0);
            }
            return $i0;
        }

        public int getItemViewType(int $i0) throws  {
            if ($i0 == 0) {
                return 0;
            }
            if (SideMenuAddressItemRecycler.this.mShowBanner) {
                if ($i0 == 1) {
                    return 1;
                }
                $i0--;
            }
            return $i0 == 1 ? 2 : 3;
        }
    }

    private class SideMenuLayoutManager extends LayoutManager {
        private int mAddressItemHeight;
        private int mCurrentScrollOffset = 0;
        private boolean mIsHidingTopButton;
        private boolean mIsShowingTopButton;
        private int mLastAdapterSize = -1;
        private int mLastLayoutHeight = -1;
        private int mLastLayoutWidth = -1;
        private LinearSmoothScroller mSmoothScroller;

        class C19242 implements Runnable {
            C19242() throws  {
            }

            public void run() throws  {
                SideMenuAddressItemRecycler.this.mBackToTopButton.setVisibility(8);
                SideMenuLayoutManager.this.mIsHidingTopButton = false;
            }
        }

        public void onLayoutChildren(android.support.v7.widget.RecyclerView.Recycler r31, android.support.v7.widget.RecyclerView.State r32) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:43:0x01c2
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r30 = this;
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r7 = r6.getMeasuredWidth();
            r0 = r30;
            r8 = r0.mLastLayoutWidth;
            if (r7 != r8) goto L_0x003e;
        L_0x000e:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r7 = r6.getMeasuredHeight();
            r0 = r30;
            r8 = r0.mLastLayoutHeight;
            if (r7 != r8) goto L_0x003e;
        L_0x001c:
            r0 = r30;
            r7 = r0.mLastAdapterSize;
            r0 = r30;
            r8 = r0.getItemCount();
            if (r7 != r8) goto L_0x003e;
        L_0x0028:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r9 = r6.mHeaderView;
            r10 = r9.doesRequireLayout();
            if (r10 != 0) goto L_0x003e;
        L_0x0036:
            r11 = "SideMenuRecycler";
            r12 = "onLayoutChildren skipped!";
            android.util.Log.i(r11, r12);
            return;
        L_0x003e:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r9 = r6.mHeaderView;
            r9.setLayoutPerformed();
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r7 = r6.getMeasuredWidth();
            r0 = r30;
            r0.mLastLayoutWidth = r7;
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r7 = r6.getMeasuredHeight();
            r0 = r30;
            r0.mLastLayoutHeight = r7;
            r0 = r30;
            r7 = r0.getItemCount();
            r0 = r30;
            r0.mLastAdapterSize = r7;
            r11 = "SideMenuRecycler";
            r12 = "onLayoutChildren called!";
            android.util.Log.i(r11, r12);
            r0 = r30;
            r1 = r31;
            r0.detachAndScrapAttachedViews(r1);
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r13 = -1;
            r6.mRootHeight = r13;
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r7 = r6.getItemWidth();
            r13 = 0;
            r0 = r31;
            r14 = r0.getViewForPosition(r13);
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r10 = r6.mShowBanner;
            if (r10 == 0) goto L_0x01c6;
        L_0x009a:
            r13 = 1;
            r0 = r31;
            r15 = r0.getViewForPosition(r13);
        L_0x00a1:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r10 = r6.mShowBanner;
            if (r10 == 0) goto L_0x01cc;
        L_0x00ab:
            r16 = 2;
        L_0x00ad:
            r0 = r31;
            r1 = r16;
            r17 = r0.getViewForPosition(r1);
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r8 = r6.getMeasuredWidth();
            r13 = -1;
            r18 = 0;
            r19 = 1;
            r0 = r30;
            r1 = r14;
            r2 = r13;
            r3 = r8;
            r4 = r18;
            r5 = r19;
            r0.addAndLayoutView(r1, r2, r3, r4, r5);
            if (r15 == 0) goto L_0x00ee;
        L_0x00d0:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r8 = r6.getMeasuredWidth();
            r13 = 2131361937; // 0x7f0a0091 float:1.834364E38 double:1.053032712E-314;
            r20 = com.waze.utils.PixelMeasure.dimension(r13);
            r13 = 0;
            r18 = 1;
            r0 = r30;
            r1 = r15;
            r2 = r13;
            r3 = r8;
            r4 = r20;
            r5 = r18;
            r0.addAndLayoutView(r1, r2, r3, r4, r5);
        L_0x00ee:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r10 = r6.mIsInSearchMode;
            if (r10 != 0) goto L_0x0109;
        L_0x00f8:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r10 = r6.mIsTransitioningModes;
            if (r10 != 0) goto L_0x0109;
        L_0x0102:
            r21 = 0;
            r0 = r21;
            r14.setTranslationY(r0);
        L_0x0109:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r22 = r6.mDropShadow;
            if (r22 == 0) goto L_0x0124;
        L_0x0113:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r22 = r6.mDropShadow;
            r21 = 0;
            r0 = r22;
            r1 = r21;
            r0.setAlpha(r1);
        L_0x0124:
            r8 = android.os.Build.VERSION.SDK_INT;
            r13 = 21;
            if (r8 < r13) goto L_0x013b;
        L_0x012a:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r23 = r6.mSearchBar;
            r21 = 0;
            r0 = r23;
            r1 = r21;
            r0.setElevation(r1);
        L_0x013b:
            r0 = r30;
            r8 = r0.getDecoratedMeasuredHeight(r14);
            if (r15 != 0) goto L_0x01cf;
        L_0x0143:
            r20 = 0;
        L_0x0145:
            r24 = r8 + r20;
            r13 = 2131361941; // 0x7f0a0095 float:1.8343649E38 double:1.053032714E-314;
            r25 = com.waze.utils.PixelMeasure.dimension(r13);
            r0 = r24;
            r1 = r25;
            r0 = r0 + r1;
            r24 = r0;
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r25 = r6.getFirstAddressItemPosition();
        L_0x015d:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r26 = r6.getRootHeight();
            r0 = r26;
            r1 = r20;
            r0 = r0 + r1;
            r26 = r0;
            r0 = r24;
            r1 = r26;
            if (r0 >= r1) goto L_0x01d6;
        L_0x0172:
            r0 = r30;
            r26 = r0.getItemCount();
            r0 = r25;
            r1 = r26;
            if (r0 >= r1) goto L_0x01d6;
        L_0x017e:
            r0 = r31;
            r1 = r25;
            r14 = r0.getViewForPosition(r1);
            goto L_0x018a;
        L_0x0187:
            goto L_0x0145;
        L_0x018a:
            r13 = 1;
            r18 = 1;
            r0 = r30;
            r1 = r14;
            r2 = r13;
            r3 = r7;
            r4 = r24;
            r5 = r18;
            r0.addAndLayoutView(r1, r2, r3, r4, r5);
            r0 = r30;
            r0.adjustAddressItemVisibility(r14);
            r0 = r30;
            r26 = r0.getDecoratedMeasuredHeight(r14);
            r0 = r24;
            r1 = r26;
            r0 = r0 + r1;
            r24 = r0;
            r25 = r25 + 1;
            r0 = r30;
            r0 = r0.mAddressItemHeight;
            r26 = r0;
            if (r26 != 0) goto L_0x015d;
        L_0x01b5:
            r0 = r30;
            r26 = r0.getDecoratedMeasuredHeight(r14);
            r0 = r26;
            r1 = r30;
            r1.mAddressItemHeight = r0;
            goto L_0x015d;
            goto L_0x01c6;
        L_0x01c3:
            goto L_0x00a1;
        L_0x01c6:
            r15 = 0;
            goto L_0x01c3;
            goto L_0x01cc;
        L_0x01c9:
            goto L_0x00ad;
        L_0x01cc:
            r16 = 1;
            goto L_0x01c9;
        L_0x01cf:
            r0 = r30;
            r20 = r0.getDecoratedMeasuredHeight(r15);
            goto L_0x0187;
        L_0x01d6:
            r0 = r30;
            r7 = r0.getChildCount();
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r24 = r6.getMeasuredWidth();
            r0 = r20;
            r8 = r8 + r0;
            r13 = 1;
            r0 = r30;
            r1 = r17;
            r2 = r7;
            r3 = r24;
            r4 = r8;
            r5 = r13;
            r0.addAndLayoutView(r1, r2, r3, r4, r5);
            r0 = r30;
            r7 = r0.mCurrentScrollOffset;
            r0 = r30;
            r8 = r0.mCurrentScrollOffset;
            r13 = 0;
            r0 = r30;
            r0.mCurrentScrollOffset = r13;
            r13 = 2131361941; // 0x7f0a0095 float:1.8343649E38 double:1.053032714E-314;
            r20 = com.waze.utils.PixelMeasure.dimension(r13);
        L_0x0208:
            if (r8 <= 0) goto L_0x021f;
        L_0x020a:
            r0 = r20;
            r24 = java.lang.Math.min(r8, r0);
            r0 = r30;
            r1 = r24;
            r2 = r31;
            r3 = r32;
            r0.scrollVerticallyBy(r1, r2, r3);
            r0 = r24;
            r8 = r8 - r0;
            goto L_0x0208;
        L_0x021f:
            r0 = r30;
            r8 = r0.mCurrentScrollOffset;
            if (r7 <= r8) goto L_0x025a;
        L_0x0225:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r10 = r6.mIsInSearchMode;
            if (r10 == 0) goto L_0x025b;
        L_0x022f:
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r9 = r6.mHeaderView;
            r0 = r30;
            r6 = com.waze.menus.SideMenuAddressItemRecycler.this;
            r27 = r6.mHeaderView;
            r0 = r27;
            r28 = r0.getTranslationY();
            r0 = r30;
            r8 = r0.mCurrentScrollOffset;
            r7 = r7 - r8;
            r7 = r7 / 2;
            r0 = (float) r7;
            r29 = r0;
            r0 = r28;
            r1 = r29;
            r0 = r0 - r1;
            r28 = r0;
            r9.setTranslationY(r0);
            return;
        L_0x025a:
            return;
        L_0x025b:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.SideMenuAddressItemRecycler.SideMenuLayoutManager.onLayoutChildren(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):void");
        }

        public SideMenuLayoutManager() throws  {
            this.mSmoothScroller = new LinearSmoothScroller(SideMenuAddressItemRecycler.this.getContext(), SideMenuAddressItemRecycler.this) {
                public PointF computeScrollVectorForPosition(int $i0) throws  {
                    return new PointF(0.0f, (float) SideMenuLayoutManager.this.calculateCurrentDistanceToPosition($i0));
                }

                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) throws  {
                    return 1.0f / ((float) PixelMeasure.dp(15));
                }
            };
        }

        private int calculateCurrentDistanceToPosition(int $i0) throws  {
            int $i1 = 0;
            if ($i0 > 0) {
                $i1 = 0 + PixelMeasure.dimension(C1283R.dimen.sideMenuHeaderHeight);
            }
            if ($i0 > 1) {
                $i1 += PixelMeasure.dimension(C1283R.dimen.sideMenuSearchBarHeight);
            }
            if ($i0 > 2) {
                $i1 += this.mAddressItemHeight * ($i0 - 2);
            }
            return $i1 - this.mCurrentScrollOffset;
        }

        public LayoutParams generateDefaultLayoutParams() throws  {
            return new LayoutParams(SideMenuAddressItemRecycler.this.getResources().getDisplayMetrics().widthPixels - PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding), -2);
        }

        private void measureChildInternal(View $r1) throws  {
            if (($r1 instanceof AddressItemView) || $r1 == SideMenuAddressItemRecycler.this.mBannerView) {
                $r1.measure(MeasureSpec.makeMeasureSpec(SideMenuAddressItemRecycler.this.getItemWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            } else {
                measureChildWithMargins($r1, 0, 0);
            }
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int $i0) throws  {
            this.mSmoothScroller.setTargetPosition($i0);
            startSmoothScroll(this.mSmoothScroller);
        }

        public boolean canScrollVertically() throws  {
            return (SideMenuAddressItemRecycler.this.mIsInSearchMode || SideMenuAddressItemRecycler.this.mIsTransitioningModes) ? false : true;
        }

        private View getBottomView() throws  {
            View $r1 = getChildAt(0);
            for (int $i0 = 1; $i0 < getChildCount(); $i0++) {
                if (getDecoratedBottom($r1) < getDecoratedBottom(getChildAt($i0))) {
                    $r1 = getChildAt($i0);
                }
            }
            return $r1;
        }

        private View getTopAddressItem() throws  {
            View $r1 = null;
            for (int $i0 = 0; $i0 < getChildCount(); $i0++) {
                View $r2 = getChildAt($i0);
                if (!($r2 == SideMenuAddressItemRecycler.this.mHeaderView || $r2 == SideMenuAddressItemRecycler.this.mBannerView || $r2 == SideMenuAddressItemRecycler.this.mSearchBar || ($r1 != null && getDecoratedTop($r2) >= getDecoratedTop($r1)))) {
                    $r1 = $r2;
                }
            }
            return $r1;
        }

        private int getPositionInternal(View $r1) throws  {
            if ($r1 == SideMenuAddressItemRecycler.this.mHeaderView) {
                return 0;
            }
            if ($r1 == SideMenuAddressItemRecycler.this.mBannerView) {
                return 1;
            }
            if ($r1 != SideMenuAddressItemRecycler.this.mSearchBar) {
                return ((AddressItemViewHolder) $r1.getTag()).position;
            }
            if (SideMenuAddressItemRecycler.this.mShowBanner) {
                return 2;
            }
            return 1;
        }

        private void adjustSearchBarShadow(float $f0) throws  {
            if ($f0 < 0.0f) {
                $f0 = 0.0f;
            }
            if ($f0 > 1.0f) {
                $f0 = 1.0f;
            }
            if (VERSION.SDK_INT < 21) {
                SideMenuAddressItemRecycler.this.mDropShadow.setAlpha($f0);
            } else {
                SideMenuAddressItemRecycler.this.mSearchBar.setElevation(((float) PixelMeasure.dimension(C1283R.dimen.sideMenuSearchBarElevation)) * $f0);
            }
        }

        private void adjustAddressItemVisibility(View $r1) throws  {
            if (!($r1 instanceof AddressItemView)) {
                return;
            }
            if (SideMenuAddressItemRecycler.this.mIsInSearchMode) {
                $r1.setVisibility(4);
                $r1.setTranslationX((float) (-SideMenuAddressItemRecycler.this.getMeasuredWidth()));
                return;
            }
            $r1.setVisibility(0);
            $r1.setTranslationX(0.0f);
        }

        private void addAndLayoutView(View $r1, int $i0, int $i1, int $i2, boolean $z0) throws  {
            addView($r1, $i0);
            measureChildInternal($r1);
            if ($z0) {
                $i0 = $i2;
            } else {
                $i0 = $i2 - getDecoratedMeasuredHeight($r1);
            }
            if ($z0) {
                $i2 += getDecoratedMeasuredHeight($r1);
            }
            layoutDecorated($r1, 0, $i0, $i1, $i2);
            if (!SideMenuAddressItemRecycler.this.mTooltipWasShown && SideMenuAddressItemRecycler.this.mTooltipSessionNumberValid && SideMenuAddressItemRecycler.this.mIsFullyVisible && ($r1 instanceof AddressItemView)) {
                SideMenuAddressItemRecycler.this.attemptToShowTooltip((AddressItemView) $r1);
            }
        }

        public int scrollVerticallyBy(int $i1, Recycler $r1, State $r2) throws  {
            int $i2 = 0;
            SideMenuHeaderView $r4 = SideMenuAddressItemRecycler.this.mHeaderView;
            View $r5 = getBottomView();
            int $i3 = SideMenuAddressItemRecycler.this.getItemWidth();
            int $i4 = (SideMenuAddressItemRecycler.this.mBannerView == null || !SideMenuAddressItemRecycler.this.mShowBanner) ? 0 : SideMenuAddressItemRecycler.this.mBannerView.getMeasuredHeight();
            if (getDecoratedBottom($r5) - getDecoratedTop($r4) < SideMenuAddressItemRecycler.this.getRootHeight()) {
                return 0;
            }
            View $r6 = getTopAddressItem();
            int $i0 = this.mAddressItemHeight;
            if ($i1 > $i0) {
                $i2 = $i1 - $i0;
                $i1 = $i0;
            } else if ($i1 < (-$i0)) {
                $i2 = $i1 + $i0;
                $i1 = -$i0;
            }
            if ($i1 > 0) {
                if (getPositionInternal($r5) >= getItemCount() - 1 && getDecoratedBottom($r5) - $i1 < SideMenuAddressItemRecycler.this.getRootHeight() - PixelMeasure.dp(16)) {
                    $i1 = (getDecoratedBottom($r5) - SideMenuAddressItemRecycler.this.getRootHeight()) + PixelMeasure.dp(16);
                    $i2 = 0;
                }
            } else if (getPositionInternal($r4) == 0 && getDecoratedTop($r4) - $i1 > 0) {
                $i1 = getDecoratedTop($r4);
                $i2 = 0;
            }
            for ($i0 = 0; $i0 < getChildCount(); $i0++) {
                $r5 = getChildAt($i0);
                int $i7 = getDecoratedTop($r5);
                int $i6 = $i7;
                int $i8 = getDecoratedBottom($r5);
                int $i5 = SideMenuAddressItemRecycler.this.getMeasuredWidth();
                if ($r5 == SideMenuAddressItemRecycler.this.mHeaderView) {
                    $i6 = $i7 - $i1;
                    $i7 = $i8 - $i1;
                    if (!(SideMenuAddressItemRecycler.this.mIsInSearchMode || SideMenuAddressItemRecycler.this.mIsTransitioningModes)) {
                        float $f0 = (float) ((-$i6) / 2);
                        SideMenuAddressItemRecycler.this.mHeaderView.setTranslationY($f0);
                        if (SideMenuAddressItemRecycler.this.mBannerView != null) {
                            SideMenuAddressItemRecycler.this.mBannerView.setTranslationY((float) ((-$i6) / 2));
                        }
                    }
                } else if ($r5 == SideMenuAddressItemRecycler.this.mSearchBar) {
                    if ($i1 > 0) {
                        $i6 = $i7 - $i1;
                        if ($i6 < 0) {
                            $i6 = 0;
                        }
                    } else if ($r6 != null && getPositionInternal($r6) == SideMenuAddressItemRecycler.this.getFirstAddressItemPosition() && getDecoratedTop($r6) > $r5.getMeasuredHeight()) {
                        $i6 = getDecoratedTop($r6) - $r5.getMeasuredHeight();
                    }
                    $i7 = $i6 + $r5.getMeasuredHeight();
                } else {
                    $i6 = $i7 - $i1;
                    $i7 = $i8 - $i1;
                    if ($r5 instanceof AddressItemView) {
                        ((AddressItemView) $r5).closeSideButtons();
                        $i5 = $i3;
                    }
                }
                layoutDecorated($r5, 0, $i6, $i5, $i7);
            }
            if ($r6 == null || getPositionInternal($r6) != SideMenuAddressItemRecycler.this.getFirstAddressItemPosition()) {
                adjustSearchBarShadow(1.0f);
            } else {
                adjustSearchBarShadow(1.0f - (((float) getDecoratedTop($r6)) / ((float) getDecoratedMeasuredHeight(SideMenuAddressItemRecycler.this.mSearchBar))));
            }
            for ($i0 = 0; $i0 < getChildCount(); $i0++) {
                $r6 = getChildAt($i0);
                if (!($r6 == SideMenuAddressItemRecycler.this.mHeaderView || $r6 == SideMenuAddressItemRecycler.this.mBannerView || $r6 == SideMenuAddressItemRecycler.this.mSearchBar)) {
                    $i5 = getDecoratedTop($r6);
                    if (getDecoratedBottom($r6) < 0 || $i5 > SideMenuAddressItemRecycler.this.getRootHeight()) {
                        removeAndRecycleView($r6, $r1);
                    }
                }
            }
            this.mCurrentScrollOffset += $i1;
            $r6 = getBottomView();
            $r5 = getTopAddressItem();
            if ($i1 > 0) {
                $i0 = getPositionInternal($r6) + 1;
                $i5 = getDecoratedBottom($r6);
                while (true) {
                    int $i62 = SideMenuAddressItemRecycler.this.getRootHeight() + $i4;
                    $i6 = $i62;
                    if ($i5 >= $i62 || $i0 >= getItemCount()) {
                        break;
                    }
                    $r6 = $r1.getViewForPosition($i0);
                    addAndLayoutView($r6, 1, $i3, $i5, true);
                    adjustAddressItemVisibility($r6);
                    $i5 += getDecoratedMeasuredHeight($r6);
                    $i0++;
                }
            } else if ($i1 < 0) {
                $i4 = getDecoratedTop($r5);
                $i0 = getPositionInternal($r5) - 1;
                while ($i4 > 0 && $i0 >= SideMenuAddressItemRecycler.this.getFirstAddressItemPosition()) {
                    $r6 = $r1.getViewForPosition($i0);
                    addAndLayoutView($r6, 1, $i3, $i4, false);
                    adjustAddressItemVisibility($r6);
                    $i4 -= getDecoratedMeasuredHeight($r6);
                    $i0--;
                }
            }
            if ($i2 != 0) {
                return scrollVerticallyBy($i2, $r1, $r2) + $i1;
            }
            adjustBackToTopButton();
            return $i1;
        }

        private void adjustBackToTopButton() throws  {
            int $i0 = (int) (((float) (PixelMeasure.dimension(C1283R.dimen.sideMenuHeaderHeight) + PixelMeasure.dimension(C1283R.dimen.sideMenuSearchBarHeight))) + (1.5f * ((float) PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight))));
            if (!this.mIsShowingTopButton && this.mCurrentScrollOffset >= $i0 && !SideMenuAddressItemRecycler.this.mIsInSearchMode && !SideMenuAddressItemRecycler.this.mIsTransitioningModes) {
                revealBackToTopButton();
            } else if (this.mIsShowingTopButton && this.mCurrentScrollOffset < $i0) {
                hideBackToTopButton();
            }
        }

        private void revealBackToTopButton() throws  {
            if (!this.mIsShowingTopButton) {
                this.mIsShowingTopButton = true;
                SideMenuAddressItemRecycler.this.mBackToTopButton.setVisibility(0);
                SideMenuAddressItemRecycler.this.mBackToTopButton.setTranslationY((float) (-PixelMeasure.dp(30)));
                SideMenuAddressItemRecycler.this.mBackToTopButton.setAlpha(0.0f);
                ViewPropertyAnimatorHelper.initAnimation(SideMenuAddressItemRecycler.this.mBackToTopButton).translationY(0.0f).alpha(1.0f).setListener(null);
                this.mIsHidingTopButton = false;
            }
        }

        private void hideBackToTopButton() throws  {
            if (this.mIsShowingTopButton && !this.mIsHidingTopButton) {
                this.mIsHidingTopButton = true;
                this.mIsShowingTopButton = false;
                SideMenuAddressItemRecycler.this.mBackToTopButton.setTranslationY(0.0f);
                SideMenuAddressItemRecycler.this.mBackToTopButton.setAlpha(1.0f);
                ViewPropertyAnimatorHelper.initAnimation(SideMenuAddressItemRecycler.this.mBackToTopButton).translationY((float) (-PixelMeasure.dp(30))).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C19242()));
            }
        }
    }

    private class StaticItemViewHolder extends ViewHolder {
        public StaticItemViewHolder(View $r2) throws  {
            super($r2);
        }
    }

    private void onItemClick(com.waze.navigate.AddressItem r46, int r47) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:16:0x00e6
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
        r45 = this;
        r0 = r46;
        r12 = r0.getType();
        r13 = java.lang.Integer.valueOf(r12);
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "On Item Click! Item Type = ";
        r14 = r14.append(r15);
        r14 = r14.append(r13);
        r15 = ", addess = ";
        r14 = r14.append(r15);
        r0 = r46;
        r16 = r0.getAddress();
        r0 = r16;
        r14 = r14.append(r0);
        r16 = r14.toString();
        r15 = "MainSideMenu";
        r0 = r16;
        android.util.Log.i(r15, r0);
        r0 = r46;
        r12 = r0.getType();
        r17 = 8;
        r0 = r17;
        if (r12 != r0) goto L_0x009a;
    L_0x0042:
        r0 = r46;
        r16 = r0.getMeetingId();
        if (r16 == 0) goto L_0x009a;
    L_0x004a:
        r0 = r46;
        com.waze.planned_drive.PlannedDriveActivity.setNavigationAddressItem(r0);
        r18 = com.waze.NativeManager.getInstance();
        r0 = r46;
        r16 = r0.getId();
        r0 = r46;
        r0 = r0.VanueID;
        r19 = r0;
        r0 = r46;
        r20 = r0.getMeetingId();
        r21 = 0;
        r22 = 0;
        r17 = 1;
        r23 = 0;
        r24 = 1;
        r25 = 0;
        r26 = 0;
        r27 = 0;
        r0 = r18;
        r1 = r16;
        r2 = r19;
        r3 = r21;
        r4 = r22;
        r5 = r20;
        r6 = r17;
        r7 = r23;
        r8 = r24;
        r9 = r25;
        r10 = r26;
        r11 = r27;
        r0.AutoCompletePlaceClicked(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11);
        r0 = r45;
        r0 = r0.mSideMenuActionProvider;
        r28 = r0;
        r0.close();
        return;
    L_0x009a:
        r16 = "";
        r12 = r13.intValue();
        switch(r12) {
            case 0: goto L_0x0139;
            case 1: goto L_0x013c;
            case 2: goto L_0x013f;
            case 3: goto L_0x0142;
            case 4: goto L_0x0145;
            case 5: goto L_0x0148;
            case 6: goto L_0x014b;
            case 7: goto L_0x014e;
            case 8: goto L_0x0151;
            case 9: goto L_0x0154;
            case 10: goto L_0x0157;
            case 11: goto L_0x015a;
            case 12: goto L_0x015d;
            case 13: goto L_0x0160;
            case 14: goto L_0x0163;
            case 15: goto L_0x0166;
            case 16: goto L_0x0169;
            case 17: goto L_0x00a4;
            case 18: goto L_0x00a4;
            case 19: goto L_0x00a4;
            case 20: goto L_0x016c;
            default: goto L_0x00a3;
        };
    L_0x00a3:
        goto L_0x00a4;
    L_0x00a4:
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r0 = r16;
        r14 = r14.append(r0);
        r15 = "|";
        r14 = r14.append(r15);
        r0 = r47;
        r14 = r14.append(r0);
        r16 = r14.toString();
        r15 = "NAV_LIST_CLICK";
        r29 = "TYPE|INDEX";
        r0 = r29;
        r1 = r16;
        com.waze.analytics.Analytics.log(r15, r0, r1);
        if (r13 == 0) goto L_0x0381;
    L_0x00cd:
        r47 = r13.intValue();
        r17 = 2;
        r0 = r47;
        r1 = r17;
        if (r0 == r1) goto L_0x00f5;
    L_0x00d9:
        r47 = r13.intValue();
        r17 = 4;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x016f;
    L_0x00e5:
        goto L_0x00f5;
        goto L_0x00ea;
    L_0x00e7:
        goto L_0x00a4;
    L_0x00ea:
        goto L_0x00f2;
    L_0x00eb:
        goto L_0x00a4;
        goto L_0x00f2;
    L_0x00ef:
        goto L_0x00a4;
    L_0x00f2:
        goto L_0x00a4;
    L_0x00f5:
        r47 = r13.intValue();
        goto L_0x00fd;
    L_0x00fa:
        goto L_0x00a4;
    L_0x00fd:
        goto L_0x0101;
    L_0x00fe:
        goto L_0x00a4;
    L_0x0101:
        goto L_0x0109;
        goto L_0x0106;
    L_0x0103:
        goto L_0x00a4;
    L_0x0106:
        goto L_0x00a4;
    L_0x0109:
        goto L_0x0111;
    L_0x010a:
        goto L_0x00a4;
        goto L_0x0111;
    L_0x010e:
        goto L_0x00a4;
    L_0x0111:
        goto L_0x0129;
        goto L_0x0116;
    L_0x0113:
        goto L_0x00a4;
    L_0x0116:
        goto L_0x011a;
    L_0x0117:
        goto L_0x00a4;
    L_0x011a:
        goto L_0x011e;
    L_0x011b:
        goto L_0x00a4;
    L_0x011e:
        goto L_0x00a4;
        goto L_0x0125;
    L_0x0122:
        goto L_0x00a4;
    L_0x0125:
        goto L_0x0129;
    L_0x0126:
        goto L_0x00a4;
    L_0x0129:
        r0 = r45;
        r1 = r47;
        r0.addHomeWork(r1);
        goto L_0x0134;
    L_0x0131:
        goto L_0x00a4;
    L_0x0134:
        goto L_0x0138;
    L_0x0135:
        goto L_0x00a4;
    L_0x0138:
        return;
    L_0x0139:
        r16 = "UNKNOWN";
        goto L_0x00e7;
    L_0x013c:
        r16 = "HOME";
        goto L_0x00eb;
    L_0x013f:
        r16 = "HOME_EMPTY";
        goto L_0x00f2;
    L_0x0142:
        r16 = "WORK";
        goto L_0x00ef;
    L_0x0145:
        r16 = "WORK_EMPTY";
        goto L_0x00fe;
    L_0x0148:
        r16 = "FAVORITE";
        goto L_0x0106;
    L_0x014b:
        r16 = "FAVORITE_EMPTY";
        goto L_0x010a;
    L_0x014e:
        r16 = "SEARCH";
        goto L_0x00fa;
    L_0x0151:
        r16 = "HISTORY";
        goto L_0x0113;
    L_0x0154:
        r16 = "EVENT";
        goto L_0x0117;
    L_0x0157:
        r16 = "FB_ENC";
        goto L_0x011b;
    L_0x015a:
        r16 = "CALENDAR";
        goto L_0x0103;
    L_0x015d:
        r16 = "CALENDAR_ENC";
        goto L_0x0122;
    L_0x0160:
        r16 = "SHARED";
        goto L_0x0126;
    L_0x0163:
        r16 = "DROPOFF";
        goto L_0x011e;
    L_0x0166:
        r16 = "PICKUP";
        goto L_0x010e;
    L_0x0169:
        r16 = "PLANNED";
        goto L_0x0131;
    L_0x016c:
        r16 = "PARKING";
        goto L_0x0135;
    L_0x016f:
        r47 = r13.intValue();
        r17 = 10;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x01a4;
    L_0x017b:
        r15 = "FACEBOOK_CONNECT_CLICK";
        r29 = "VAUE";
        r30 = "NAVIGATE_SCREEN";
        r0 = r29;
        r1 = r30;
        com.waze.analytics.Analytics.log(r15, r0, r1);
        r31 = com.waze.social.facebook.FacebookManager.getInstance();
        r32 = com.waze.AppService.getActiveActivity();
        r33 = com.waze.social.facebook.FacebookManager.FacebookLoginType.SetToken;
        r17 = 1;
        r24 = 1;
        r0 = r31;
        r1 = r32;
        r2 = r33;
        r3 = r17;
        r4 = r24;
        r0.loginWithFacebook(r1, r2, r3, r4);
        return;
    L_0x01a4:
        r47 = r13.intValue();
        r17 = 12;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x01ba;
    L_0x01b0:
        r18 = com.waze.NativeManager.getInstance();
        r0 = r18;
        r0.CalendaRequestAccessNTV();
        return;
    L_0x01ba:
        r47 = r13.intValue();
        r17 = 8;
        r0 = r47;
        r1 = r17;
        if (r0 == r1) goto L_0x01ea;
    L_0x01c6:
        r47 = r13.intValue();
        r17 = 1;
        r0 = r47;
        r1 = r17;
        if (r0 == r1) goto L_0x01ea;
    L_0x01d2:
        r47 = r13.intValue();
        r17 = 3;
        r0 = r47;
        r1 = r17;
        if (r0 == r1) goto L_0x01ea;
    L_0x01de:
        r47 = r13.intValue();
        r17 = 13;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x020e;
    L_0x01ea:
        r17 = 2;
        r0 = r17;
        r13 = java.lang.Integer.valueOf(r0);
        r0 = r46;
        r0.setCategory(r13);
        r34 = com.waze.navigate.DriveToNativeManager.getInstance();
        r35 = new com.waze.menus.SideMenuAddressItemRecycler$8;
        r0 = r35;
        r1 = r45;
        r0.<init>();
        r0 = r34;
        r1 = r46;
        r2 = r35;
        r0.navigate(r1, r2);
        return;
    L_0x020e:
        r47 = r13.intValue();
        r17 = 11;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x0272;
    L_0x021a:
        r0 = r46;
        r36 = r0.getIsValidate();
        r0 = r36;
        r37 = r0.booleanValue();
        if (r37 == 0) goto L_0x0247;
    L_0x0228:
        r0 = r46;
        r37 = r0.hasLocation();
        if (r37 == 0) goto L_0x0247;
    L_0x0230:
        r34 = com.waze.navigate.DriveToNativeManager.getInstance();
        r38 = new com.waze.menus.SideMenuAddressItemRecycler$9;
        r0 = r38;
        r1 = r45;
        r0.<init>();
        r0 = r34;
        r1 = r46;
        r2 = r38;
        r0.navigate(r1, r2);
        return;
    L_0x0247:
        r39 = new android.content.Intent;
        r32 = com.waze.AppService.getActiveActivity();
        r40 = com.waze.navigate.AddressPreviewActivity.class;
        r0 = r39;
        r1 = r32;
        r2 = r40;
        r0.<init>(r1, r2);
        r15 = "AddressItem";
        r0 = r39;
        r1 = r46;
        r0.putExtra(r15, r1);
        r32 = com.waze.AppService.getActiveActivity();
        r17 = 32789; // 0x8015 float:4.5947E-41 double:1.62E-319;
        r0 = r32;
        r1 = r39;
        r2 = r17;
        r0.startActivityForResult(r1, r2);
        return;
    L_0x0272:
        r47 = r13.intValue();
        r17 = 5;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x02a1;
    L_0x027e:
        r39 = new android.content.Intent;
        r0 = r45;
        r41 = r0.getContext();
        r40 = com.waze.navigate.FavoritesActivity.class;
        r0 = r39;
        r1 = r41;
        r2 = r40;
        r0.<init>(r1, r2);
        r32 = com.waze.AppService.getActiveActivity();
        r17 = 1;
        r0 = r32;
        r1 = r39;
        r2 = r17;
        r0.startActivityForResult(r1, r2);
        return;
    L_0x02a1:
        r47 = r13.intValue();
        r17 = 16;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x02cc;
    L_0x02ad:
        r39 = new android.content.Intent;
        r0 = r45;
        r41 = r0.getContext();
        r40 = com.waze.planned_drive.PlannedDriveListActivity.class;
        r0 = r39;
        r1 = r41;
        r2 = r40;
        r0.<init>(r1, r2);
        r32 = com.waze.AppService.getActiveActivity();
        r0 = r32;
        r1 = r39;
        r0.startActivity(r1);
        return;
    L_0x02cc:
        r47 = r13.intValue();
        r17 = 9;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x034c;
    L_0x02d8:
        r0 = r46;
        r36 = r0.getIsValidate();
        r0 = r36;
        r37 = r0.booleanValue();
        if (r37 != 0) goto L_0x02f8;
    L_0x02e6:
        r0 = r46;
        r13 = r0.getCategory();
        r47 = r13.intValue();
        r17 = 9;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x0317;
    L_0x02f8:
        r0 = r46;
        r37 = r0.hasLocation();
        if (r37 == 0) goto L_0x0317;
    L_0x0300:
        r34 = com.waze.navigate.DriveToNativeManager.getInstance();
        r42 = new com.waze.menus.SideMenuAddressItemRecycler$10;
        r0 = r42;
        r1 = r45;
        r0.<init>();
        r0 = r34;
        r1 = r46;
        r2 = r42;
        r0.navigate(r1, r2);
        return;
    L_0x0317:
        r39 = new android.content.Intent;
        r0 = r45;
        r41 = r0.getContext();
        r40 = com.waze.mywaze.social.FacebookEventActivity.class;
        r0 = r39;
        r1 = r41;
        r2 = r40;
        r0.<init>(r1, r2);
        r15 = "AddressItem";
        r0 = r39;
        r1 = r46;
        r0.putExtra(r15, r1);
        r32 = com.waze.AppService.getActiveActivity();
        r17 = 1;
        r0 = r32;
        r1 = r39;
        r2 = r17;
        r0.startActivityForResult(r1, r2);
        r0 = r45;
        r0 = r0.mSideMenuActionProvider;
        r28 = r0;
        r0.close();
        return;
    L_0x034c:
        r47 = r13.intValue();
        r17 = 15;
        r0 = r47;
        r1 = r17;
        if (r0 == r1) goto L_0x0364;
    L_0x0358:
        r47 = r13.intValue();
        r17 = 14;
        r0 = r47;
        r1 = r17;
        if (r0 != r1) goto L_0x0382;
    L_0x0364:
        r43 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r0 = r46;
        r16 = r0.getMeetingId();
        r44 = new com.waze.menus.SideMenuAddressItemRecycler$11;
        r0 = r44;
        r1 = r45;
        r0.<init>();
        r0 = r43;
        r1 = r16;
        r2 = r44;
        r0.getDriveInfoByMeetingId(r1, r2);
        return;
    L_0x0381:
        return;
    L_0x0382:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.SideMenuAddressItemRecycler.onItemClick(com.waze.navigate.AddressItem, int):void");
    }

    public SideMenuAddressItemRecycler(Context $r1) throws  {
        this($r1, null);
    }

    public SideMenuAddressItemRecycler(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SideMenuAddressItemRecycler(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mShowBanner = false;
        this.mBannerSeen = false;
        this.mListMutex = new Object();
        this.mHandler = new MicroHandler(this);
        this.mRootHeight = -1;
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mHeaderView = new SideMenuHeaderView(getContext());
        this.mBannerView = LayoutInflater.from(getContext()).inflate(C1283R.layout.main_side_menu_banner, null, false);
        this.mSearchBar = new SideMenuSearchBar(getContext());
        this.mHeaderView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.sideMenuHeaderHeight)));
        this.mBannerView.setLayoutParams(new LayoutParams(-1, -2));
        this.mSearchBar.setLayoutParams(new LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.sideMenuSearchBarHeight)));
        this.mSearchBar.disableFocus();
        this.mSearchBar.setSpeechButtonVisibility(true);
        if (!isInEditMode()) {
            this.mSearchBar.setHint(NativeManager.getInstance().getLanguageString(25));
        }
        this.mFavoriteItems = new ArrayList();
        this.mSearchBar.setSearchBarActionListener(new C19081());
        this.mSearchBar.setOnClickListener(new C19122());
        if (VERSION.SDK_INT >= 21) {
            this.mSearchBar.setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }
        SideMenuAddressItemRecycler sideMenuAddressItemRecycler = this;
        this.mAdapter = new SideMenuAdapter();
        this.mLayoutManager = new SideMenuLayoutManager();
        setAdapter(this.mAdapter);
        setLayoutManager(this.mLayoutManager);
        getRecycledViewPool().setMaxRecycledViews(3, 30);
        for (int $i0 = 0; $i0 < 15; $i0++) {
            getRecycledViewPool().putRecycledView(this.mAdapter.createViewHolder(null, 3));
        }
        this.mGetTopTenFavoritesCallback = new C19143();
    }

    int getFirstAddressItemPosition() throws  {
        return this.mShowBanner ? 3 : 2;
    }

    public List<AddressItem> getFavoriteItems() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.addAll(this.mFavoriteItems);
        return $r1;
    }

    private int getRootHeight() throws  {
        if (isInEditMode()) {
            return 0;
        }
        if (this.mRootHeight == -1) {
            Window $r3 = AppService.getActiveActivity().getWindow();
            Rect $r1 = new Rect();
            $r3.getDecorView().getWindowVisibleDisplayFrame($r1);
            this.mRootHeight = $r1.height();
        }
        return this.mRootHeight;
    }

    public void setIsFullyVisible(boolean $z0) throws  {
        if (this.mIsFullyVisible != $z0) {
            this.mIsFullyVisible = $z0;
            if (this.mIsFullyVisible && !this.mTooltipWasShown && this.mTooltipSessionNumberValid) {
                for (AddressItemView attemptToShowTooltip : getOrderedAddressItems()) {
                    attemptToShowTooltip(attemptToShowTooltip);
                    if (this.mTooltipWasShown) {
                        break;
                    }
                }
            }
            if (this.mShowBanner && !this.mBannerSeen) {
                this.mBannerSeen = true;
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_PROMO_MENU_SHOWN).send();
                CarpoolNativeManager.getInstance().navMenuPromoSeenThisSession();
            }
        }
    }

    private void attemptToShowTooltip(AddressItemView $r1) throws  {
        if (!this.mTooltipWasShown && this.mIsFullyVisible && this.mTooltipSessionNumberValid) {
            View $r2 = $r1.getInfoButtonIfVisible();
            if ($r2 != null && NavListMenuTooltipView.showNavListTooltip(AppService.getActiveActivity(), $r2, getMeasuredHeight())) {
                this.mTooltipWasShown = true;
                ConfigManager.getInstance().setConfigValueBool(312, true);
            }
        }
    }

    public void informProfilePictureChanged() throws  {
        this.mHeaderView.informProfilePictureChanged();
    }

    public void updateUserData() throws  {
        this.mHeaderView.setUserData();
        reloadData();
    }

    public void updateUserOnlineFriends(int $i0) throws  {
        this.mHeaderView.updateOnlineFriends($i0);
        reloadData();
    }

    public boolean isInSearchMode() throws  {
        return this.mIsInSearchMode;
    }

    public void closeKeyboard() throws  {
        this.mSearchBar.hideKeyboard();
    }

    public void setSearchTerm(String $r1, boolean $z0) throws  {
        this.mSearchBar.setSearchTerm($r1, $z0);
    }

    public boolean isFirstLoadCompleted() throws  {
        return this.mFirstLoadCompleted;
    }

    public void transitionToSearchMode(boolean $z0, boolean $z1) throws  {
        if (!this.mIsInSearchMode && !this.mIsTransitioningModes) {
            SideMenuSearchBar $r9;
            C19154 c19154;
            long $l4;
            MainSideMenuActionProvider mainSideMenuActionProvider;
            MainSideMenuActionProvider $r11;
            this.mIsInSearchMode = true;
            this.mIsTransitioningModes = true;
            for (AddressItemView visibility : getOrderedAddressItems()) {
                visibility.setVisibility(4);
            }
            int $i0 = this.mHeaderView.getMeasuredHeight() + (this.mShowBanner ? this.mBannerView.getMeasuredHeight() : 0);
            int $i1 = 0;
            SideMenuLayoutManager $r7 = this.mLayoutManager;
            if ($r7.mCurrentScrollOffset < $i0) {
                $r7 = this.mLayoutManager;
                $i1 = $i0 - $r7.mCurrentScrollOffset;
            }
            ViewPropertyAnimatorHelper.initAnimation(this.mHeaderView).alpha(0.0f);
            SideMenuSearchBar $r92 = this.mSearchBar;
            ViewPropertyAnimator $r8 = ViewPropertyAnimatorHelper.initAnimation($r92);
            if (VERSION.SDK_INT >= 21) {
                $r92 = this.mSearchBar;
                if ($r92.getElevation() > 0.0f) {
                    $r92 = this.mSearchBar;
                    $r9 = $r92;
                    $r8.translationZ(-$r92.getElevation());
                    c19154 = new C19154();
                    if ($z1) {
                        $l4 = 300;
                    } else {
                        $l4 = 0;
                    }
                    postDelayed(c19154, $l4);
                    mainSideMenuActionProvider = this.mSideMenuActionProvider;
                    $r11 = mainSideMenuActionProvider;
                    mainSideMenuActionProvider.transitionToFullScreen($i1, $z1);
                    this.mSearchBar.showCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
                    $r92 = this.mSearchBar;
                    $r9 = $r92;
                    $r92.enableFocus($z0);
                    getAdapter().notifyDataSetChanged();
                }
            }
            if (VERSION.SDK_INT < 21) {
                ImageView $r14 = this.mDropShadow;
                if ($r14.getAlpha() > 0.0f) {
                    $r14 = this.mDropShadow;
                    this.mSearchDropShadowAlpha = $r14.getAlpha();
                    $r14 = this.mDropShadow;
                    ViewPropertyAnimatorHelper.initAnimation($r14).alpha(0.0f);
                }
            }
            c19154 = new C19154();
            if ($z1) {
                $l4 = 300;
            } else {
                $l4 = 0;
            }
            postDelayed(c19154, $l4);
            mainSideMenuActionProvider = this.mSideMenuActionProvider;
            $r11 = mainSideMenuActionProvider;
            mainSideMenuActionProvider.transitionToFullScreen($i1, $z1);
            this.mSearchBar.showCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
            $r92 = this.mSearchBar;
            $r9 = $r92;
            $r92.enableFocus($z0);
            getAdapter().notifyDataSetChanged();
        }
    }

    public void transitionToNormalMode() throws  {
        if (this.mIsInSearchMode && !this.mIsTransitioningModes) {
            this.mIsTransitioningModes = true;
            this.mIsInSearchMode = false;
            for (AddressItemView visibility : getOrderedAddressItems()) {
                visibility.setVisibility(0);
            }
            ViewPropertyAnimatorHelper.initAnimation(this.mHeaderView).alpha(1.0f);
            ViewPropertyAnimator $r6 = ViewPropertyAnimatorHelper.initAnimation(this.mSearchBar);
            if (VERSION.SDK_INT >= 21 && this.mSearchBar.getElevation() > 0.0f) {
                $r6.translationZ(0.0f);
            } else if (VERSION.SDK_INT < 21 && this.mSearchDropShadowAlpha > 0.0f) {
                ImageView $r12 = this.mDropShadow;
                $r6 = ViewPropertyAnimatorHelper.initAnimation($r12);
                float $f0 = this.mSearchDropShadowAlpha;
                $r6.alpha($f0);
            }
            postDelayed(new C19165(), 300);
            this.mSideMenuActionProvider.transitionToNormalMode(300);
            this.mSearchBar.hideCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
            this.mSearchBar.disableFocus();
            getAdapter().notifyDataSetChanged();
        }
    }

    public void snapToNormalMode() throws  {
        this.mIsInSearchMode = false;
        for (AddressItemView visibility : getOrderedAddressItems()) {
            visibility.setVisibility(0);
        }
        if (VERSION.SDK_INT >= 21 && this.mSearchBar.getElevation() > 0.0f) {
            this.mSearchBar.setTranslationZ(0.0f);
        } else if (VERSION.SDK_INT < 21 && this.mSearchDropShadowAlpha > 0.0f) {
            this.mDropShadow.setAlpha(this.mSearchDropShadowAlpha);
        }
        this.mHeaderView.setAlpha(1.0f);
        this.mSearchBar.hideCancelButton(0, null);
        this.mSearchBar.disableFocus();
        setLayoutFrozen(false);
        getAdapter().notifyDataSetChanged();
    }

    public void setMainSideMenuActionProvider(MainSideMenuActionProvider $r1) throws  {
        this.mSideMenuActionProvider = $r1;
    }

    private int getItemWidth() throws  {
        return getResources().getDisplayMetrics().widthPixels - PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding);
    }

    private List<AddressItemView> getOrderedAddressItems() throws  {
        ArrayList $r1 = new ArrayList();
        for (int $i0 = 0; $i0 < getChildCount(); $i0++) {
            View $r2 = getChildAt($i0);
            if ($r2 instanceof AddressItemView) {
                $r1.add((AddressItemView) $r2);
            }
        }
        Collections.sort($r1, new C19176());
        return $r1;
    }

    public void setDropShadowImage(ImageView $r1) throws  {
        this.mDropShadow = $r1;
    }

    public void setBackToTopButton(LinearLayout $r1) throws  {
        this.mBackToTopButton = $r1;
    }

    public void reloadData() throws  {
        boolean $z0 = true;
        if (this.mDriveToNativeManager == null) {
            this.mDriveToNativeManager = DriveToNativeManager.getInstance();
        }
        boolean $z1 = ConfigManager.getInstance().getConfigValueBool(312) || !ConfigManager.getInstance().getConfigValueBool(313);
        this.mTooltipWasShown = $z1;
        if (AppService.getMainActivity() != null) {
            if (AppService.getMainActivity().getCurrentSessionNumber() < 3) {
                $z0 = false;
            }
            this.mTooltipSessionNumberValid = $z0;
        } else {
            this.mTooltipSessionNumberValid = false;
        }
        NativeManager.Post(new C19187());
        this.mHeaderView.setUserData();
    }

    public void onInfoActionClicked(AddressItem $r1) throws  {
        if ($r1.getType() == 14 || $r1.getType() == 15) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NAVLIST_RIDE_INFO_CLICKED, "TYPE|RIDE_ID", ($r1.getType() == 14 ? "DROPOFF" : "PICKUP") + "|" + $r1.getMeetingId());
            CarpoolNativeManager.getInstance().getDriveInfoByMeetingId($r1.getMeetingId(), new IResultObj<CarpoolDrive>() {
                public void onResult(CarpoolDrive $r1) throws  {
                    Intent $r2 = new Intent(AppService.getActiveActivity(), CarpoolRideDetailsActivity.class);
                    if ($r1.getRide() == null) {
                        Logger.m38e("SideMenuAddressItemRecycler: ride is null! cannot view ride details");
                        MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
                        return;
                    }
                    $r2.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
                    AppService.getActiveActivity().startActivityForResult($r2, 1);
                }
            });
        } else if ($r1.getType() != 8 || $r1.getMeetingId() == null) {
            Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
            if (!($r1.VanueID == null || $r1.VanueID.isEmpty())) {
                intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
            }
            AppService.getActiveActivity().startActivityForResult(intent, 1);
        } else {
            DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            NativeManager.getInstance().AutoCompletePlaceClicked(null, $r1.VanueID, null, null, $r1.getMeetingId(), false, null, true, 0, null, null);
        }
    }

    public void onShowOptionsClick(AddressItem $r1) throws  {
        AddressItemOptionsUtil.showNavItemOptions(AppService.getActiveActivity(), $r1, this);
    }

    public void onDeleteActionClicked(AddressItem $r1) throws  {
        if ($r1.getType() == 9 && $r1.getCategory().intValue() == 9) {
            this.mDriveToNativeManager.removedPlannedDrive($r1.getMeetingId(), new PlannedDriveListListener() {

                class C19071 implements Runnable {
                    C19071() throws  {
                    }

                    public void run() throws  {
                        SideMenuAddressItemRecycler.this.reloadData();
                    }
                }

                public void onPlannedDriveRemoveSuccess() throws  {
                    SideMenuAddressItemRecycler.this.post(new C19071());
                }

                public void onPlannedDriveRemoveFailed() throws  {
                }

                public void onPlannedDriveEtaResponse(String eventId, int driveTime) throws  {
                }
            });
        } else if ($r1.getCategory().intValue() != 1 || $r1.getType() == 1 || $r1.getType() == 3) {
            if ($r1.getType() == 11) {
                this.mDriveToNativeManager.removeEvent($r1.getMeetingId(), false);
            }
            this.mDriveToNativeManager.eraseAddressItem($r1);
            postDelayed(new Runnable() {
                public void run() throws  {
                    SideMenuAddressItemRecycler.this.reloadData();
                }
            }, 200);
        } else {
            $r1.setCategory(Integer.valueOf(150));
            this.mDriveToNativeManager.eraseAddressItem($r1);
            postDelayed(new Runnable() {
                public void run() throws  {
                    SideMenuAddressItemRecycler.this.reloadData();
                }
            }, 200);
        }
    }

    public void onButtonsOpened(AddressItemView $r1) throws  {
        for (int $i0 = 0; $i0 < getChildCount(); $i0++) {
            View $r2 = getChildAt($i0);
            if ($r2 != $r1 && ($r2 instanceof AddressItemView)) {
                ((AddressItemView) $r2).closeSideButtons();
            }
        }
    }

    public void onGrabStart(AddressItem addressItem) throws  {
    }

    public void onRefreshed(AddressItem addressItem) throws  {
        getAdapter().notifyDataSetChanged();
        this.mHeaderView.setUserData();
    }

    public void snapCloseAllButtons() throws  {
        for (int $i0 = 0; $i0 < getChildCount(); $i0++) {
            View $r1 = getChildAt($i0);
            if ($r1 instanceof AddressItemView) {
                ((AddressItemView) $r1).snapCloseSideButtons();
            }
        }
    }

    public void handleMessage(Message $r1) throws  {
        if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            NativeManager.getInstance().CloseProgressPopup();
            AddressItem $r8 = (AddressItem) $r1.getData().getSerializable("address_item");
            Intent $r2 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r8);
            AppService.getActiveActivity().startActivityForResult($r2, 1);
        }
    }

    public void onAddressItemOptionClicked(AddressItem $r1, int $i0) throws  {
        switch ($i0) {
            case 0:
                onAddFavorite($r1);
                return;
            case 1:
                onRemoveFavorite($r1);
                return;
            case 2:
                onChangeLocation($r1);
                return;
            case 3:
                Intent $r2 = r6;
                Intent r6 = new Intent(AppService.getActiveActivity(), ParkingSearchResultsActivity.class);
                $r2.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, $r1.getVenueDataForParking());
                $r2.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, "NAV_LIST");
                AppService.getActiveActivity().startActivityForResult($r2, 0);
                return;
            case 4:
                onCreatePlannedDrive($r1);
                return;
            case 5:
                openCalendarSettings();
                return;
            case 6:
                onInfoActionClicked($r1);
                return;
            case 7:
                ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareSelection, null, $r1, null);
                return;
            case 8:
                onOpenRoutes($r1);
                return;
            case 9:
            case 10:
                addHomeWork($r1.getType());
                return;
            case 11:
                verifyEventAddress($r1);
                return;
            case 12:
                onDeleteActionClicked($r1);
                return;
            case 13:
                setStartPoint($r1);
                return;
            case 14:
                onRenameFavorite($r1);
                return;
            case 15:
                onShowRideDetails($r1);
                return;
            case 16:
                onCancelRide($r1);
                return;
            case 17:
                onSendCarpoolMessage($r1);
                return;
            default:
                return;
        }
    }

    private void onOpenRoutes(AddressItem $r1) throws  {
        NativeManager.getInstance().setShowRoutesWhenNavigationStarts(true);
        DriveToNativeManager.getInstance().navigate($r1, null);
    }

    private void onChangeLocation(AddressItem $r1) throws  {
        Intent $r2 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
        $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
        $r2.putExtra(AddressPreviewActivity.OPEN_SET_LOCATION, true);
        AppService.getActiveActivity().startActivity($r2);
    }

    private void onCancelRide(AddressItem $r1) throws  {
        loadRideAndRiderAndPerformAction($r1, new CarpoolDriveAction() {
            public void onDriveReceived(CarpoolDrive $r1) throws  {
                CarpoolUtils.cancelDriveAfterAccepted($r1, null, SideMenuAddressItemRecycler.this.getContext());
            }
        });
    }

    private void onShowRideDetails(AddressItem $r1) throws  {
        loadRideAndRiderAndPerformAction($r1, new CarpoolDriveAction() {
            public void onDriveReceived(CarpoolDrive $r1) throws  {
                Intent $r2 = new Intent(AppService.getActiveActivity(), CarpoolRideDetailsActivity.class);
                $r2.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
                AppService.getActiveActivity().startActivity($r2);
            }
        });
    }

    private void addHomeWork(int $i0) throws  {
        Intent $r1 = new Intent(AppService.getActiveActivity(), AddHomeWorkActivity.class);
        $r1.putExtra(PublicMacros.ADDRESS_TYPE, $i0);
        AppService.getActiveActivity().startActivityForResult($r1, 1001);
    }

    private void onSendCarpoolMessage(AddressItem $r1) throws  {
        loadRideAndRiderAndPerformAction($r1, new CarpoolDriveAction() {
            public void onDriveReceived(CarpoolDrive $r1) throws  {
                Intent $r2 = new Intent(AppService.getActiveActivity(), CarpoolMessagingActivity.class);
                $r2.putExtra("ride", $r1.getRide());
                $r2.putExtra("rider", $r1.getRider());
                AppService.getActiveActivity().startActivity($r2);
            }
        });
    }

    private void loadRideAndRiderAndPerformAction(AddressItem $r1, final CarpoolDriveAction $r2) throws  {
        if (!TextUtils.isEmpty($r1.getMeetingId())) {
            CarpoolNativeManager.getInstance().getDriveInfoByMeetingId($r1.getMeetingId(), new IResultObj<CarpoolDrive>() {
                public void onResult(CarpoolDrive $r1) throws  {
                    synchronized (SideMenuAddressItemRecycler.rideAndRiderMutex) {
                        if ($r2 != null) {
                            $r2.onDriveReceived($r1);
                        }
                    }
                }
            });
        }
    }

    public void hideClosebutton() throws  {
        this.mSearchBar.hideCancelButton(0, null);
    }

    public void showCarpoolPromo() throws  {
        ((TextView) this.mBannerView.findViewById(C1283R.id.carpoolPromoTitle)).setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_PROMO_MENU_TITLE));
        ((TextView) this.mBannerView.findViewById(C1283R.id.carpoolPromoText)).setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_PROMO_MENU_TEXT));
        this.mBannerView.findViewById(C1283R.id.carpoolPromoClose).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_PROMO_MENU_CLOSE).send();
                CarpoolNativeManager.getInstance().navMenuPromoClosed();
                SideMenuAddressItemRecycler.this.mShowBanner = false;
                SideMenuAddressItemRecycler.this.getAdapter().notifyItemRemoved(1);
                SideMenuAddressItemRecycler.this.getAdapter().notifyItemRangeChanged(2, SideMenuAddressItemRecycler.this.mFavoriteItems.size());
            }
        });
        this.mBannerView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_PROMO_MENU_ENTER).send();
                final MainActivity $r3 = AppService.getMainActivity();
                if ($r3 != null) {
                    $r3.closeMenus();
                    $r3.postDelayed(new Runnable() {
                        public void run() throws  {
                            $r3.openCarpoolSideMenu();
                        }
                    }, 350);
                }
            }
        });
        this.mShowBanner = true;
        getAdapter().notifyDataSetChanged();
    }

    private void verifyEventAddress(AddressItem $r1) throws  {
        Intent $r2;
        if ($r1.getType() == 9) {
            $r2 = new Intent(getContext(), FacebookEventActivity.class);
            $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
            AppService.getActiveActivity().startActivityForResult($r2, 1);
        } else if ($r1.getType() == 11) {
            $r2 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
            AppService.getActiveActivity().startActivityForResult($r2, MainActivity.CALENDAR_REQUEST_CODE);
        }
    }

    private void onRemoveFavorite(AddressItem $r1) throws  {
        DriveToNativeManager.getInstance().convertFavoriteToRecent($r1.getId(), new Runnable() {
            public void run() throws  {
                SideMenuAddressItemRecycler.this.reloadData();
            }
        });
    }

    private void onRenameFavorite(AddressItem $r1) throws  {
        NameFavoriteView.showNameFavorite(AppService.getActiveActivity(), $r1, new Runnable() {
            public void run() throws  {
                SideMenuAddressItemRecycler.this.reloadData();
            }
        }).setRenameMode(true);
    }

    private void setStartPoint(AddressItem $r1) throws  {
        DriveToNativeManager.getInstance().setStartPoint($r1);
    }

    private void openCalendarSettings() throws  {
        AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsCalendarActivity.class), 1);
    }

    private void onCreatePlannedDrive(AddressItem $r1) throws  {
        PlannedDriveActivity.setNavigationAddressItem($r1);
        AppService.getActiveActivity().startActivity(new Intent(getContext(), PlannedDriveActivity.class));
    }

    private void addFavorite(AddressItem $r1) throws  {
        NameFavoriteView.showNameFavorite(AppService.getActiveActivity(), $r1, new Runnable() {
            public void run() throws  {
                SideMenuAddressItemRecycler.this.reloadData();
            }
        });
    }

    private void onAddFavorite(final AddressItem $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private int danger;

            class C19101 implements DialogInterface.OnClickListener {
                C19101() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        SideMenuAddressItemRecycler.this.addFavorite($r1);
                        SideMenuAddressItemRecycler.this.mDriveToNativeManager.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                        return;
                    }
                    SideMenuAddressItemRecycler.this.mDriveToNativeManager.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                }
            }

            class C19112 implements OnCancelListener {
                C19112() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    SideMenuAddressItemRecycler.this.mDriveToNativeManager.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, "BACK");
                }
            }

            public void event() throws  {
                this.danger = SideMenuAddressItemRecycler.this.mDriveToNativeManager.isInDangerZoneNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue());
            }

            public void callback() throws  {
                NativeManager $r3 = NativeManager.getInstance();
                if (this.danger >= 0) {
                    MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava($r3.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), $r3.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE), false, new C19101(), $r3.getLanguageString(DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE_BUTTON), $r3.getLanguageString(344), -1, "dangerous_zone_icon", new C19112(), true, true);
                    return;
                }
                SideMenuAddressItemRecycler.this.addFavorite($r1);
            }
        });
    }
}
