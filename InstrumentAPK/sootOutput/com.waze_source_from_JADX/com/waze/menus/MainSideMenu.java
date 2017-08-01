package com.waze.menus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.NativeCanvasRenderer;
import com.waze.menus.SideMenuAutoCompleteRecycler.AutoCompleteAdHandler;
import com.waze.navigate.AddressItem;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.layout.SwipeableLayout.SwipeableLayoutActionProvider;
import com.waze.view.layout.SwipeableLayout.SwipeableLayoutListener;
import java.util.List;

public class MainSideMenu extends FrameLayout implements AutoCompleteAdHandler, SwipeableLayoutListener {
    private boolean mIsShowingAd;
    private boolean mOpenedToAutocomplete;
    private SideMenuAddressItemRecycler mRecentItemsRecyclerView;
    private RelativeLayout mRootContainer;
    private int mSearchBarTranslation;
    private SideMenuAutoCompleteRecycler mSearchResultsList;
    private SwipeableLayoutActionProvider mSwipeableLayoutActionProvider;

    class C18921 implements OnClickListener {
        C18921() throws  {
        }

        public void onClick(View v) throws  {
            MainSideMenu.this.mRecentItemsRecyclerView.smoothScrollToPosition(0);
        }
    }

    public interface MainSideMenuActionProvider {
        void close() throws ;

        void loadSearchResults(String str) throws ;

        void openSearchScreen() throws ;

        void transitionToFullScreen(int i, boolean z) throws ;

        void transitionToNormalMode(int i) throws ;
    }

    class C18962 implements MainSideMenuActionProvider {

        class C18931 implements AnimatorUpdateListener {
            C18931() throws  {
            }

            public void onAnimationUpdate(ValueAnimator $r1) throws  {
                MainSideMenu.this.mRootContainer.setPadding(0, 0, ((Integer) $r1.getAnimatedValue()).intValue(), 0);
            }
        }

        class C18942 implements AnimatorUpdateListener {
            C18942() throws  {
            }

            public void onAnimationUpdate(ValueAnimator $r1) throws  {
                MainSideMenu.this.mRootContainer.setPadding(0, 0, ((Integer) $r1.getAnimatedValue()).intValue(), 0);
            }
        }

        class C18953 extends AnimatorListenerAdapter {
            C18953() throws  {
            }

            public void onAnimationEnd(Animator animation) throws  {
                MainSideMenu.this.mOpenedToAutocomplete = false;
                C18962.this.close();
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().getLayoutMgr().closeSwipeableLayout();
                }
            }
        }

        C18962() throws  {
        }

        public void transitionToFullScreen(int $i0, boolean immediate) throws  {
            MainSideMenu.this.mSwipeableLayoutActionProvider.extend();
            ValueAnimator $r4 = ValueAnimator.ofInt(new int[]{PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding), 0});
            $r4.addUpdateListener(new C18931());
            $r4.setDuration(300);
            $r4.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
            $r4.start();
            MainSideMenu.this.mSearchBarTranslation = $i0;
            float $f0 = (float) (-MainSideMenu.this.mSearchBarTranslation);
            ViewPropertyAnimatorHelper.initAnimation(MainSideMenu.this.mRecentItemsRecyclerView).translationY($f0);
            MainSideMenu.this.mSearchResultsList.setAlpha(1.0f);
            MainSideMenu.this.mSearchResultsList.setVisibility(0);
            MainSideMenu.this.mSearchResultsList.setTranslationY((float) $i0);
            MainSideMenu.this.mSearchResultsList.loadHistory();
            ViewPropertyAnimatorHelper.initAnimation(MainSideMenu.this.mSearchResultsList).translationY(0.0f).setListener(null);
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "SEARCH");
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_SHOWN).addParam("TYPE", "MAIN_MENU").addParam("ADD_STOP", MainSideMenu.this.mOpenedToAutocomplete ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
        }

        public void transitionToNormalMode(int startOffset) throws  {
            long $l1 = 300;
            if (MainSideMenu.this.mOpenedToAutocomplete) {
                $l1 = 0;
                MainSideMenu.this.mSwipeableLayoutActionProvider.snapClosed();
                MainSideMenu.this.mRecentItemsRecyclerView.hideClosebutton();
            } else {
                MainSideMenu.this.mSwipeableLayoutActionProvider.retract();
            }
            ValueAnimator $r5 = ValueAnimator.ofInt(new int[]{0, PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding)});
            $r5.addUpdateListener(new C18942());
            $r5.setDuration($l1);
            $r5.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
            if (MainSideMenu.this.mOpenedToAutocomplete) {
                $r5.addListener(new C18953());
            }
            $r5.start();
            ViewPropertyAnimatorHelper.initAnimation(MainSideMenu.this.mRecentItemsRecyclerView, $l1).translationY(0.0f);
            float $f0 = (float) MainSideMenu.this.mSearchBarTranslation;
            ViewPropertyAnimatorHelper.initAnimation(MainSideMenu.this.mSearchResultsList, $l1).translationY($f0).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(MainSideMenu.this.mSearchResultsList));
        }

        public void loadSearchResults(String $r1) throws  {
            MainSideMenu.this.mSearchResultsList.beginSearchTerm($r1);
        }

        public void openSearchScreen() throws  {
            MainSideMenu.this.mSearchResultsList.openSearchScreen();
        }

        public void close() throws  {
            MainSideMenu.this.mSwipeableLayoutActionProvider.close();
        }
    }

    class C18973 implements Runnable {
        C18973() throws  {
        }

        public void run() throws  {
            MainSideMenu.this.mRecentItemsRecyclerView.transitionToNormalMode();
        }
    }

    class C18984 implements Runnable {
        C18984() throws  {
        }

        public void run() throws  {
            MainSideMenu.this.mRecentItemsRecyclerView.reloadData();
        }
    }

    public MainSideMenu(Context $r1) throws  {
        this($r1, null);
    }

    public MainSideMenu(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public MainSideMenu(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mOpenedToAutocomplete = false;
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
            return;
        }
        View $r4 = LayoutInflater.from(getContext()).inflate(C1283R.layout.main_side_menu, this);
        this.mRecentItemsRecyclerView = (SideMenuAddressItemRecycler) $r4.findViewById(C1283R.id.recyclerView);
        this.mRootContainer = (RelativeLayout) $r4.findViewById(C1283R.id.rootContainer);
        this.mSearchResultsList = (SideMenuAutoCompleteRecycler) $r4.findViewById(C1283R.id.searchItemsList);
        ImageView imageView = (ImageView) $r4.findViewById(C1283R.id.searchBarDropShadow);
        LinearLayout $r10 = (LinearLayout) $r4.findViewById(C1283R.id.backToTop);
        $r10.setOnClickListener(new C18921());
        this.mSearchResultsList.setAdHandler(this);
        this.mRecentItemsRecyclerView.setDropShadowImage(imageView);
        this.mRecentItemsRecyclerView.setBackToTopButton($r10);
        if (!isInEditMode()) {
            this.mRecentItemsRecyclerView.setMainSideMenuActionProvider(new C18962());
            ((TextView) $r10.findViewById(C1283R.id.backToTopText)).setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_BACK_TO_TOP_BUTTON));
        }
    }

    public void unsetSearchAddHandler() throws  {
        this.mSearchResultsList.unsetSearchAddHandler();
    }

    public void informProfilePictureChanged() throws  {
        this.mRecentItemsRecyclerView.informProfilePictureChanged();
    }

    public void updateUserData() throws  {
        this.mRecentItemsRecyclerView.updateUserData();
    }

    public void updateUserOnlineFriends(int $i0) throws  {
        this.mRecentItemsRecyclerView.updateUserOnlineFriends($i0);
    }

    public boolean canReactToBackButton() throws  {
        return getVisibility() == 0 && this.mRecentItemsRecyclerView.isInSearchMode();
    }

    public boolean reactToBackButton() throws  {
        if (!canReactToBackButton()) {
            return false;
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK, "ACTION", "BACK");
        post(new C18973());
        return true;
    }

    public void snapToNormalMode() throws  {
        this.mRecentItemsRecyclerView.snapToNormalMode();
        this.mRecentItemsRecyclerView.setIsFullyVisible(false);
        this.mSwipeableLayoutActionProvider.snapClosed();
        this.mRootContainer.setPadding(0, 0, PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding), 0);
        if (this.mIsShowingAd) {
            this.mIsShowingAd = false;
            this.mSearchResultsList.setTranslationY(0.0f);
        }
        this.mRecentItemsRecyclerView.setTranslationY(0.0f);
        this.mSearchResultsList.setVisibility(8);
        setVisibility(8);
    }

    public void setVisibility(int $i0) throws  {
        if ($i0 == 0 && getVisibility() != 0) {
            NativeCanvasRenderer.OnMainCanvasOverlayShown();
            postDelayed(new C18984(), 300);
        }
        if ($i0 != 0 && getVisibility() == 0) {
            NativeCanvasRenderer.OnMainCanvasOverlayHidden();
            this.mRecentItemsRecyclerView.smoothScrollToPosition(0);
        }
        super.setVisibility($i0);
    }

    public boolean isNavListReadyForDisplay() throws  {
        return this.mRecentItemsRecyclerView.isFirstLoadCompleted();
    }

    public void onSwipeChanged(float $f0) throws  {
        boolean $z0 = true;
        boolean $z1 = ((double) $f0) > 0.01d;
        if (!$z1 && getVisibility() == 0) {
            setVisibility(8);
        } else if ($z1 && getVisibility() != 0) {
            setVisibility(0);
        }
        if ($f0 < 0.0f) {
            $f0 = 0.0f;
        }
        setTranslationX(((float) (-PixelMeasure.dimension(C1283R.dimen.sideMenuInitialTranslation))) * (1.0f - $f0));
        this.mRecentItemsRecyclerView.onButtonsOpened(null);
        SideMenuAddressItemRecycler $r1 = this.mRecentItemsRecyclerView;
        if ($f0 < 1.0f) {
            $z0 = false;
        }
        $r1.setIsFullyVisible($z0);
    }

    public void setSwipeableLayoutActionProvider(SwipeableLayoutActionProvider $r1) throws  {
        this.mSwipeableLayoutActionProvider = $r1;
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        if ($z0) {
            this.mRecentItemsRecyclerView.snapCloseAllButtons();
        }
    }

    public void hideKeyboard() throws  {
        this.mRecentItemsRecyclerView.closeKeyboard();
    }

    public void setSearchTerm(String $r1, boolean $z0) throws  {
        this.mRecentItemsRecyclerView.transitionToSearchMode($z0, false);
        this.mRecentItemsRecyclerView.setSearchTerm($r1, $z0);
    }

    public void closeSideMenu() throws  {
        hideKeyboard();
        this.mRecentItemsRecyclerView.setIsFullyVisible(false);
        this.mSwipeableLayoutActionProvider.close();
    }

    public void refreshRecentsNavigationList() throws  {
        this.mRecentItemsRecyclerView.reloadData();
    }

    public List<AddressItem> getRecents() throws  {
        return this.mRecentItemsRecyclerView.getFavoriteItems();
    }

    public void openAutocomplete(boolean $z0) throws  {
        this.mOpenedToAutocomplete = $z0;
        this.mRecentItemsRecyclerView.transitionToSearchMode(false, true);
    }

    public void showCarpoolPromo() throws  {
        this.mRecentItemsRecyclerView.showCarpoolPromo();
    }

    public void refreshAddressItems() throws  {
        this.mRecentItemsRecyclerView.onRefreshed(null);
    }
}
