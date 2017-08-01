package com.waze.menus;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.NativeCanvasRenderer;
import com.waze.menus.SideMenuAutoCompleteRecycler.AutoCompleteAdHandler;
import com.waze.menus.SideMenuSearchBar.SearchBarActionListener;
import com.waze.navigate.AddressItem;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.util.List;

public class SearchOnMapView extends FrameLayout implements AutoCompleteAdHandler, SearchBarActionListener {
    private boolean mFirstShowTimeoutExpired;
    private boolean mHasBeenShownOnce;
    private boolean mIsNavigating;
    private boolean mIsRecyclerVisible;
    private boolean mIsSearchBarVisibile;
    private boolean mIsShowingCarpoolBanner;
    private boolean mIsShowingControls;
    private boolean mIsShowingTopView;
    private boolean mIsWaitingForVoice;
    private LayoutManager mLayoutMgr;
    private SearchOnMapProvider mProvider;
    private SideMenuAutoCompleteRecycler mRecycler;
    private SideMenuSearchBar mSearchBar;
    private View mSeparator;

    public interface SearchOnMapProvider {
        List<AddressItem> getRecents() throws ;
    }

    class C19011 implements OnClickListener {
        C19011() throws  {
        }

        public void onClick(View v) throws  {
            if (SearchOnMapView.this.mIsSearchBarVisibile && !SearchOnMapView.this.mIsRecyclerVisible) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_ON_MAP_CLICKED);
                SearchOnMapView.this.mSearchBar.enableFocus(false);
                SearchOnMapView.this.mSearchBar.showCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
                SearchOnMapView.this.showRecycler();
            }
        }
    }

    class C19022 implements Runnable {
        C19022() throws  {
        }

        public void run() throws  {
            SearchOnMapView.this.mFirstShowTimeoutExpired = true;
            SearchOnMapView.this.adjustSearchBarVisibility();
        }
    }

    class C19033 implements Runnable {
        C19033() throws  {
        }

        public void run() throws  {
            SearchOnMapView.this.mSearchBar.setVisibility(8);
            SearchOnMapView.this.mSearchBar.hideBackground(false);
            SearchOnMapView.this.setVisibility(8);
            if (SearchOnMapView.this.mLayoutMgr != null) {
                SearchOnMapView.this.mLayoutMgr.SpotifyButtonMarginTop(35);
            }
        }
    }

    class C19055 implements Runnable {
        C19055() throws  {
        }

        public void run() throws  {
            SearchOnMapView.this.mRecycler.appearifyCategoryMenu();
        }
    }

    public SearchOnMapView(Context $r1) throws  {
        this($r1, null);
    }

    public SearchOnMapView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SearchOnMapView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        View $r3 = LayoutInflater.from(getContext()).inflate(C1283R.layout.alternate_from_activity_layout, null);
        if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null)) {
            this.mLayoutMgr = AppService.getMainActivity().getLayoutMgr();
        }
        this.mSearchBar = (SideMenuSearchBar) $r3.findViewById(C1283R.id.searchBar);
        this.mRecycler = (SideMenuAutoCompleteRecycler) $r3.findViewById(C1283R.id.autocompleteRecycler);
        if (this.mRecycler.shouldDisplayCategoryBar()) {
            this.mRecycler.setDisplayingCategoryBar(true);
        }
        this.mSeparator = $r3.findViewById(C1283R.id.searchBarSeperator);
        this.mSearchBar.setSearchBarActionListener(this);
        this.mSearchBar.disableFocus();
        this.mSearchBar.setAddButtonVisibility(false);
        this.mSearchBar.setVisibility(8);
        this.mSearchBar.hideBackground(false);
        this.mSearchBar.setDictationMode(1);
        this.mSearchBar.setHint(DisplayStrings.displayString(26));
        this.mSearchBar.setOnClickListener(new C19011());
        this.mRecycler.setVisibility(8);
        this.mRecycler.setAdHandler(this);
        this.mRecycler.setIsSearchOnMap(true);
        postDelayed(new C19022(), 2000);
        addView($r3);
        onDayNightChange();
    }

    public void onDayNightChange() throws  {
        this.mSearchBar.onDayNightChange();
    }

    public void setSearchOnMapProvider(SearchOnMapProvider $r1) throws  {
        this.mProvider = $r1;
    }

    public void setIsShowingCarpoolBanner(boolean $z0) throws  {
        this.mIsShowingCarpoolBanner = $z0;
    }

    public void setIsShowingControls(boolean $z0) throws  {
        this.mIsShowingControls = $z0;
        adjustSearchBarVisibility();
    }

    public void setIsNavigating(boolean $z0) throws  {
        this.mIsNavigating = $z0;
        adjustSearchBarVisibility();
    }

    public void setIsShowingTopView(boolean $z0) throws  {
        this.mIsShowingTopView = $z0;
    }

    public boolean isWaitingForVoice() throws  {
        return this.mIsWaitingForVoice;
    }

    public void cancelVoiceSearch() throws  {
        this.mIsWaitingForVoice = false;
        onCancelClick();
    }

    private void adjustSearchBarVisibility() throws  {
        if (ConfigManager.getInstance().getConfigValueBool(316)) {
            boolean $z0 = NativeManager.getInstance().isMovingNTV();
            boolean $z1 = NativeManager.getInstance().isNavigatingNTV();
            if (!this.mIsRecyclerVisible) {
                if (this.mIsSearchBarVisibile && (!this.mIsShowingControls || this.mIsShowingCarpoolBanner || $z1 || this.mIsShowingTopView || $z0)) {
                    hideSearchBar();
                } else if (!this.mIsSearchBarVisibile) {
                    if ((this.mIsShowingControls || (!this.mHasBeenShownOnce && this.mFirstShowTimeoutExpired)) && !$z1 && !this.mIsShowingTopView && !$z0) {
                        revealSearchBar();
                    }
                }
            }
        } else if (this.mIsSearchBarVisibile) {
            hideSearchBar();
        }
    }

    private void revealSearchBar() throws  {
        boolean $z0 = this.mIsSearchBarVisibile;
        this = this;
        if (!$z0) {
            $z0 = this.mHasBeenShownOnce;
            this = this;
            if (!$z0) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_ON_MAP_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_UP_TIME, AppService.timeSinceCreated()).send();
                this.mHasBeenShownOnce = true;
            }
            this.mIsSearchBarVisibile = true;
            setVisibility(0);
            this.mRecycler.setVisibility(8);
            this.mIsRecyclerVisible = false;
            this.mSearchBar.setVisibility(0);
            this.mSearchBar.hideCancelButton(0, null);
            this.mSearchBar.disableFocus();
            this.mSearchBar.setTranslationY((float) (-PixelMeasure.dimension(C1283R.dimen.sideMenuSearchBarHeight)));
            this.mSearchBar.animate().cancel();
            ViewPropertyAnimatorHelper.initAnimation(this.mSearchBar).translationY(0.0f).setListener(null);
            if (this.mLayoutMgr != null) {
                this.mLayoutMgr.SpotifyButtonMarginTop(65);
            }
        }
    }

    private void hideSearchBar() throws  {
        if (this.mIsSearchBarVisibile) {
            this.mIsSearchBarVisibile = false;
            if (this.mIsRecyclerVisible) {
                hideRecycler();
            }
            this.mSearchBar.animate().cancel();
            ViewPropertyAnimatorHelper.initAnimation(this.mSearchBar).translationY((float) (-PixelMeasure.dimension(C1283R.dimen.sideMenuSearchBarHeight))).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C19033()));
            NativeCanvasRenderer.OnMainCanvasOverlayHidden();
        }
    }

    private void animateStatusBar(final boolean $z0) throws  {
        if (VERSION.SDK_INT >= 21) {
            if (AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null || !AppService.getMainActivity().getLayoutMgr().isShowingNotificationBar()) {
                ValueAnimator $r6 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                $r6.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator $r1) throws  {
                        float $f0 = $r1.getAnimatedFraction();
                        if (!$z0) {
                            $f0 = 1.0f - $f0;
                        }
                        SearchOnMapView.this.applyStatusBarTransition($f0);
                    }
                });
                $r6.setDuration(300);
                $r6.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
                $r6.start();
                return;
            }
            AppService.getActiveActivity().getWindow().setStatusBarColor(0);
        }
    }

    @TargetApi(21)
    private void applyStatusBarTransition(float $f0) throws  {
        int $i0 = getResources().getColor(C1283R.color.BlueDeepLight);
        AppService.getActiveActivity().getWindow().setStatusBarColor(Color.argb(255, Color.red(-16777216) + ((int) (((float) (Color.red($i0) - Color.red(-16777216))) * $f0)), Color.green(-16777216) + ((int) (((float) (Color.green($i0) - Color.green(-16777216))) * $f0)), Color.blue(-16777216) + ((int) (((float) (Color.blue($i0) - Color.blue(-16777216))) * $f0))));
    }

    private void showRecycler() throws  {
        if (!this.mIsRecyclerVisible) {
            if (this.mProvider != null) {
                this.mRecycler.setRecents(this.mProvider.getRecents());
            }
            this.mSearchBar.showBackground(true);
            AppService.getMainActivity().getLayoutMgr().hideSpotifyButton();
            this.mIsRecyclerVisible = true;
            this.mSeparator.setVisibility(0);
            this.mRecycler.loadHistory();
            this.mRecycler.scrollToPosition(0);
            this.mRecycler.beginSearchTerm("");
            this.mRecycler.setVisibility(0);
            this.mRecycler.setTranslationY((float) getMeasuredHeight());
            ViewPropertyAnimatorHelper.initAnimation(this.mRecycler).translationY(0.0f).setListener(null);
            animateStatusBar(true);
            this.mRecycler.postDelayed(new C19055(), 150);
            NativeCanvasRenderer.OnMainCanvasOverlayShown();
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_SHOWN).addParam("TYPE", "SEARCH_ON_MAP").addParam("ADD_STOP", AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
        }
    }

    private void hideRecycler() throws  {
        if (this.mIsRecyclerVisible) {
            this.mIsRecyclerVisible = false;
            this.mSearchBar.disableFocus();
            this.mSearchBar.hideBackground(true);
            this.mSeparator.setVisibility(8);
            AppService.getMainActivity().getLayoutMgr().showSpotifyButton();
            animateStatusBar(false);
            ViewPropertyAnimatorHelper.initAnimation(this.mRecycler).translationY((float) getMeasuredHeight()).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mRecycler));
            AppService.getMainActivity().getLayoutMgr().refreshRecentsNavigationList();
            adjustSearchBarVisibility();
        }
    }

    public boolean isShowingSearchResults() throws  {
        return this.mIsRecyclerVisible;
    }

    public boolean reactToBackButton() throws  {
        if (!this.mIsRecyclerVisible) {
            return false;
        }
        hideRecycler();
        this.mSearchBar.hideKeyboard();
        this.mSearchBar.disableFocus();
        this.mSearchBar.hideCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        return true;
    }

    public void onCancelClick() throws  {
        hideRecycler();
        this.mSearchBar.disableFocus();
        this.mSearchBar.hideCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        this.mSearchBar.hideKeyboard();
    }

    public void onSpeechButtonClick() throws  {
        this.mIsWaitingForVoice = true;
    }

    public void onSearchTextChanged(String $r1) throws  {
        this.mRecycler.beginSearchTerm($r1);
    }

    public void onSearchButtonClick() throws  {
        this.mRecycler.openSearchScreen();
    }

    public void onAddClick() throws  {
    }

    public void hideKeyboard() throws  {
        this.mSearchBar.hideKeyboard();
    }

    public void setSearchTerm(String $r1, boolean $z0) throws  {
        this.mSearchBar.setSearchTerm($r1, $z0);
        this.mSearchBar.enableFocus(false);
        this.mSearchBar.showCancelButton(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        showRecycler();
        this.mIsWaitingForVoice = false;
    }

    public void closeSideMenu() throws  {
    }

    public boolean isSearchBarVisibile() throws  {
        return this.mIsSearchBarVisibile;
    }
}
