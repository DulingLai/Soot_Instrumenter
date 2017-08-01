package com.waze;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import com.waze.rtalerts.RTAlertsAlertData;
import com.waze.rtalerts.RTAlertsCommentData;
import com.waze.rtalerts.RTAlertsThumbsUpData;
import com.waze.user.FriendUserData;
import com.waze.view.pages.LinePageIndicator;
import com.waze.view.popups.AlertPopUp;
import com.waze.view.popups.BeepPopUp;
import com.waze.view.popups.CommentPopUP;
import com.waze.view.popups.FriendsOnlinePopUp;
import com.waze.view.popups.MyPageAdapter;
import com.waze.view.popups.PingPopUP;
import com.waze.view.popups.PoiPopUp;
import com.waze.view.popups.PopUp;
import com.waze.view.popups.PopupCloseReason;
import com.waze.view.popups.SharePopup;
import com.waze.view.popups.SpotifyPopup;
import com.waze.view.popups.ThumbsUpPopUP;

public class PopupsFragment extends Fragment {
    private MyPageAdapter adapter;
    private boolean bIsPopUpShow = false;
    private boolean bRefreshPageTime = true;
    private Context mContext;
    private View mCurPage = null;
    private int mDotsAnimationOffCount;
    private int mDotsAnimationOnCount;
    private int mInterruptTime = -1;
    private volatile int mLastPageNumber = -1;
    private LayoutManager mLayoutManager;
    private ViewPager mPager;
    private Runnable mRefreshPageRunnable;
    private PopUp[] mViews = new PopUp[5];
    private int nNumberOfViews = 0;
    private int nPageNumber = 0;

    class C12723 implements Runnable {
        C12723() throws  {
        }

        public void run() throws  {
            if (!PopupsFragment.this.bIsPopUpShow) {
                return;
            }
            if (PopupsFragment.this.nNumberOfViews == 1 && PopupsFragment.this.bRefreshPageTime) {
                PopupsFragment.this.mLayoutManager.callCloseAllPopups(0, PopupCloseReason.popup_close_reason_complete.ordinal());
            } else if (!PopupsFragment.this.bRefreshPageTime) {
                PopupsFragment.this.bRefreshPageTime = true;
                PopupsFragment.this.mPager.postDelayed(this, (long) (PopupsFragment.this.mInterruptTime * 1000));
            } else if (PopupsFragment.this.nNumberOfViews <= PopupsFragment.this.nPageNumber + 1) {
                PopupsFragment.this.mLayoutManager.callCloseAllPopups(0);
            } else {
                PopupsFragment.this.nPageNumber = PopupsFragment.this.nPageNumber + 1;
                if (PopupsFragment.this.mViews[PopupsFragment.this.nPageNumber] == null) {
                    PopupsFragment.this.mLayoutManager.callCloseAllPopups(0);
                    return;
                }
                PopupsFragment.this.mPager.setCurrentItem(PopupsFragment.this.nPageNumber);
                PopupsFragment.this.mPager.postDelayed(this, (long) (PopupsFragment.this.mViews[PopupsFragment.this.nPageNumber].GetTimer() * 1000));
            }
        }
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle savedInstanceState) throws  {
        View $r4 = $r1.inflate(C1283R.layout.fragment_swipe_popups, $r2, false);
        initPages($r4);
        return $r4;
    }

    public boolean onBackPressed() throws  {
        if (this.nNumberOfViews <= 0) {
            return false;
        }
        if (this.mViews[this.nPageNumber] == null) {
            return false;
        }
        this.mViews[this.nPageNumber].onBackPressed();
        return true;
    }

    public void setLayoutManager(Context $r1, LayoutManager $r2) throws  {
        this.mLayoutManager = $r2;
        this.mContext = $r1;
    }

    public void openPoi(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, boolean $z0, String $r1, int $i6, int $i7, String $r2, String $r3) throws  {
        if (this.nNumberOfViews <= 4) {
            if (this.mLayoutManager.mPoiPopUp == null) {
                this.mLayoutManager.mPoiPopUp = new PoiPopUp(this.mContext, this.mLayoutManager);
            }
            PopUp[] $r4 = this.mViews;
            int $i8 = this.nNumberOfViews;
            PoiPopUp poiPopUp = this.mLayoutManager.mPoiPopUp;
            PoiPopUp $r7 = poiPopUp;
            $r4[$i8] = poiPopUp.GetView($i0, $i1, $i2, $i3, $i4, $i5, $z0, $r1, $i6, $i7, $r2, $r3);
            poiPopUp = this.mLayoutManager.mPoiPopUp;
            $r7 = poiPopUp;
            poiPopUp.setPopUpTimer($i5);
            this.nNumberOfViews++;
        }
    }

    public boolean isPoiShowing() throws  {
        return this.mLayoutManager.mPoiPopUp == null ? false : this.mLayoutManager.mPoiPopUp.isPoiShowing();
    }

    private void initPages(View r) throws  {
    }

    public boolean hasSwipePopups() throws  {
        if (this.mViews != null) {
            return this.mViews[0] != null;
        } else {
            return false;
        }
    }

    public boolean openSwipePopups() throws  {
        if (this.mViews == null || this.mViews[0] == null) {
            return false;
        }
        this.bIsPopUpShow = true;
        this.mLastPageNumber = 0;
        this.bRefreshPageTime = true;
        openPopUps();
        return true;
    }

    public void openPopUps() throws  {
        this.adapter = new MyPageAdapter(this.nNumberOfViews, (View[]) this.mViews);
        this.mPager = (ViewPager) getView().findViewById(C1283R.id.swipePopupsPager);
        this.mPager.setVisibility(0);
        this.mPager.setAdapter(this.adapter);
        this.mPager.setCurrentItem(0);
        if (!(this.nNumberOfViews == 1 && (this.mViews[0] instanceof PoiPopUp) && this.mViews[0].GetTimer() <= 0)) {
            NativeManager.getInstance().PopupAction(PopupAction.popup_shown.ordinal(), 0, 0);
            this.mLastPageNumber = 0;
            LayoutManager $r8 = this.mLayoutManager;
            $r8.CloseAlertTicker();
        }
        if (this.mViews[0] != null) {
            LinePageIndicator $r9 = (LinePageIndicator) getView().findViewById(C1283R.id.swipePopupsIndicator);
            if (this.nNumberOfViews > 1) {
                $r9.setVisibility(0);
                $r9.setViewPager(this.mPager);
                if (this.mViews[0].GetTimer() > 0) {
                    switchPagePeriodically();
                }
            } else {
                $r9.setVisibility(8);
                if (this.mViews[0].GetTimer() > 0) {
                    switchPagePeriodically();
                }
            }
            int $i0 = 0;
            while (true) {
                int $i1 = this.nNumberOfViews;
                if ($i0 < $i1) {
                    boolean $z0;
                    PopUp $r5 = this.mViews[0];
                    if (this.nNumberOfViews > 1) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    $r5.setPageIndicatorShown($z0);
                    $i0++;
                } else {
                    final LinePageIndicator linePageIndicator = $r9;
                    $r9.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        public void onGlobalLayout() throws  {
                            linePageIndicator.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            linePageIndicator.onPageScrolled(0, 0.0f, 0);
                        }
                    });
                    linePageIndicator = $r9;
                    this.mPager.setOnPageChangeListener(new OnPageChangeListener() {
                        public void onPageSelected(int $i0) throws  {
                            if (PopupsFragment.this.mLastPageNumber != -1) {
                                if ($i0 != PopupsFragment.this.mLastPageNumber && PopupsFragment.this.mLastPageNumber >= 0) {
                                    NativeManager.getInstance().PopupAction(PopupAction.popup_hidden.ordinal(), PopupsFragment.this.mLastPageNumber, 0);
                                    PopupsFragment.this.mLastPageNumber = -1;
                                }
                                NativeManager.getInstance().PopupAction(PopupAction.popup_shown.ordinal(), $i0, 0);
                                PopupsFragment.this.mLayoutManager.CloseAlertTicker();
                                PopupsFragment.this.mLastPageNumber = $i0;
                                PopupsFragment.this.nPageNumber = $i0;
                            }
                        }

                        public void onPageScrolled(int $i0, float $f0, int $i1) throws  {
                            linePageIndicator.onPageScrolled($i0, $f0, $i1);
                            if (PopupsFragment.this.mViews[$i0] != null) {
                                PopupsFragment.this.mViews[$i0].setShiftEffect(true, $f0);
                            }
                            if ($i0 < PopupsFragment.this.nNumberOfViews - 1 && PopupsFragment.this.mViews[$i0 + 1] != null) {
                                PopupsFragment.this.mViews[$i0 + 1].setShiftEffect(false, 1.0f - $f0);
                            }
                        }

                        public void onPageScrollStateChanged(int $i0) throws  {
                            if ($i0 == 1) {
                                PopupsFragment.this.bRefreshPageTime = false;
                            }
                        }
                    });
                    return;
                }
            }
        }
    }

    public void switchPagePeriodically() throws  {
        if (this.bIsPopUpShow) {
            this.mRefreshPageRunnable = new C12723();
            this.mPager.postDelayed(this.mRefreshPageRunnable, (long) (this.mViews[this.nPageNumber].GetTimer() * 1000));
        }
    }

    public void RunDotOnAnimation(final ImageView $r1, final int[] $r2) throws  {
        this.mDotsAnimationOnCount = 0;
        final Handler $r3 = new Handler();
        $r3.postDelayed(new Runnable() {
            public void run() throws  {
                if (PopupsFragment.this.mDotsAnimationOnCount < $r2.length) {
                    $r1.setImageResource($r2[PopupsFragment.this.mDotsAnimationOnCount]);
                    PopupsFragment.this.mDotsAnimationOnCount = PopupsFragment.this.mDotsAnimationOnCount + 1;
                    $r3.postDelayed(this, 100);
                    return;
                }
                PopupsFragment.this.mDotsAnimationOnCount = 0;
            }
        }, 100);
    }

    public void RunDotOffAnimation(final ImageView $r1, final int[] $r2) throws  {
        this.mDotsAnimationOffCount = 0;
        final Handler $r3 = new Handler();
        $r3.postDelayed(new Runnable() {
            public void run() throws  {
                if (PopupsFragment.this.mDotsAnimationOffCount < $r2.length) {
                    $r1.setImageResource($r2[($r2.length - 1) - PopupsFragment.this.mDotsAnimationOffCount]);
                    PopupsFragment.this.mDotsAnimationOffCount = PopupsFragment.this.mDotsAnimationOffCount + 1;
                    $r3.postDelayed(this, 30);
                    return;
                }
                PopupsFragment.this.mDotsAnimationOffCount = 0;
            }
        }, 30);
    }

    public void setPopUpInterrupt(boolean $z0) throws  {
        if (!$z0) {
            this.bRefreshPageTime = false;
        } else if (this.mRefreshPageRunnable != null) {
            this.mPager.removeCallbacks(this.mRefreshPageRunnable);
            this.mRefreshPageRunnable = null;
        }
    }

    public int getLastPageNumber() throws  {
        return this.mLastPageNumber;
    }

    public void setLastPageNumber(int $i0) throws  {
        this.mLastPageNumber = $i0;
    }

    public void doneCloseAllPopups() throws  {
        this.bIsPopUpShow = false;
        if (this.mRefreshPageRunnable != null) {
            this.mPager.removeCallbacks(this.mRefreshPageRunnable);
            this.mRefreshPageRunnable = null;
        }
        if (this.adapter != null) {
            this.adapter.detachAllItems();
        }
        this.mPager.setAdapter(null);
        this.adapter = null;
        this.nPageNumber = 0;
        if (this.nNumberOfViews > 0) {
            for (int $i0 = 0; $i0 < this.nNumberOfViews; $i0++) {
                this.mViews[$i0].hide();
                this.mViews[$i0] = null;
            }
            this.nNumberOfViews = 0;
        }
    }

    public boolean openAlertPopup(RTAlertsAlertData $r1, int $i0, int $i1, String $r2, int $i2) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        AlertPopUp $r3 = new AlertPopUp(this.mContext, this.mLayoutManager);
        $r3.initView($r1, $i0, $i1, $r2);
        $r3.setPopUpTimer($i2);
        this.mViews[this.nNumberOfViews] = $r3;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openSpotifyPopup() throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        SpotifyPopup $r1 = SpotifyPopup.getInstance(this.mContext, this.mLayoutManager);
        $r1.initView();
        this.mViews[this.nNumberOfViews] = $r1;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openPingPopup(RTAlertsAlertData $r1, boolean $z0, String $r2, int $i0) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        PingPopUP $r3 = new PingPopUP(this.mContext, this.mLayoutManager);
        $r3.initView($r1, $z0, $r2);
        $r3.setPopUpTimer($i0);
        this.mViews[this.nNumberOfViews] = $r3;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openCommentPopup(RTAlertsCommentData $r1, String $r2, int $i0) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        CommentPopUP $r3 = new CommentPopUP(this.mContext, this.mLayoutManager);
        $r3.initView($r1, $r2);
        $r3.setPopUpTimer($i0);
        this.mViews[this.nNumberOfViews] = $r3;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openThumbsUpPopup(RTAlertsThumbsUpData $r1, String $r2, int $i0) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        ThumbsUpPopUP $r3 = new ThumbsUpPopUP(this.mContext, this.mLayoutManager);
        $r3.initView($r1, $r2);
        $r3.setPopUpTimer($i0);
        this.mViews[this.nNumberOfViews] = $r3;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openBeepPopup(RTAlertsThumbsUpData $r1, String $r2, int $i0) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        BeepPopUp $r3 = new BeepPopUp(this.mContext, this.mLayoutManager);
        $r3.initView($r1, $r2);
        $r3.setPopUpTimer($i0);
        this.mViews[this.nNumberOfViews] = $r3;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openSharePopup(FriendUserData $r1, int $i0, String $r2, String $r3) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        SharePopup $r4 = new SharePopup(this.mContext, this.mLayoutManager);
        $r4.initView($r1, $i0, $r2, $r3);
        $r4.setPopUpTimer(0);
        this.mViews[this.nNumberOfViews] = $r4;
        this.nNumberOfViews++;
        return true;
    }

    public boolean openFriendsOnlinePopUp(int $i0) throws  {
        if (this.nNumberOfViews > 4) {
            return false;
        }
        FriendsOnlinePopUp $r1 = new FriendsOnlinePopUp(this.mContext, this.mLayoutManager);
        this.mViews[this.nNumberOfViews] = $r1.initView();
        $r1.setPopUpTimer($i0);
        this.nNumberOfViews++;
        return true;
    }

    public void setInterruptTime(int $i0) throws  {
        if ($i0 > 0) {
            this.mInterruptTime = $i0;
        }
    }

    public void onOrientationChanged() throws  {
        for (int $i0 = 0; $i0 < this.nNumberOfViews; $i0++) {
            this.mViews[$i0].onOrientationChanged();
            this.mViews[$i0].setPageIndicatorShown(this.nNumberOfViews > 1);
        }
    }

    public int getPagerBottom() throws  {
        int $i0 = ((ViewPager) getView().findViewById(C1283R.id.swipePopupsPager)).getCurrentItem();
        if ($i0 < 0) {
            return 0;
        }
        if ($i0 >= this.nNumberOfViews) {
            return 0;
        }
        PopUp $r1 = this.mViews[$i0];
        return $r1 != null ? $r1.GetHeight() : 0;
    }

    public Rect getPageRect() throws  {
        int $i0 = ((ViewPager) getView().findViewById(C1283R.id.swipePopupsPager)).getCurrentItem();
        if ($i0 < 0) {
            return null;
        }
        if ($i0 >= this.nNumberOfViews) {
            return null;
        }
        PopUp $r1 = this.mViews[$i0];
        return $r1 != null ? $r1.getRect() : null;
    }

    @Nullable
    public PopUp findPopupOfType(Class $r1) throws  {
        for (int $i0 = 0; $i0 < this.nNumberOfViews; $i0++) {
            if (this.mViews[$i0].getClass().isAssignableFrom($r1)) {
                return this.mViews[$i0];
            }
        }
        return null;
    }
}
