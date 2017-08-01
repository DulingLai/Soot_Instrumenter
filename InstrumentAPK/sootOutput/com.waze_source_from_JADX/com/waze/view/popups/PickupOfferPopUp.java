package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.carpool.CarpoolUtils;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navbar.NavBar;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.button.PillButton;

public class PickupOfferPopUp extends PopUp {
    CarpoolDrive mDrive;
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;
    private String mRideId;

    class C31802 implements AnimationListener {
        C31802() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            PickupOfferPopUp.this.mLayoutManager.dismiss(PickupOfferPopUp.this);
            NavBar navBar = PickupOfferPopUp.this.mLayoutManager.getNavBar();
            if (navBar != null) {
                navBar.setNextEnabled(true);
            }
            PickupOfferPopUp.this.mDrive = null;
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class C31813 implements OnClickListener {
        C31813() {
        }

        public void onClick(View v) {
            PickupOfferPopUp.this.onClose();
        }
    }

    public PickupOfferPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    private void setupButton(NativeManager nm, final String rideId) {
        OnClickListener l = new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "TAP|" + rideId);
                if (PickupOfferPopUp.this.mDrive == null) {
                    Logger.e("PickupOfferPopUp: ride is null! cannot view ride details");
                    MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
                } else {
                    Intent openRide = new Intent(AppService.getActiveActivity(), CarpoolRideDetailsActivity.class);
                    openRide.putExtra(CarpoolDrive.class.getSimpleName(), PickupOfferPopUp.this.mDrive);
                    AppService.getActiveActivity().startActivityForResult(openRide, 0);
                }
                PickupOfferPopUp.this.stopCloseTimer();
                PickupOfferPopUp.this.hide();
            }
        };
        setOnClickListener(l);
        ((PillButton) findViewById(C1283R.id.puOfferPopupDetailsButton)).setOnClickListener(l);
    }

    private void setRiderImageUrl(String imageUrl, ActivityBase ab) {
        ImageView riderImage = (ImageView) findViewById(C1283R.id.puOfferPopupRiderImage);
        riderImage.setImageResource(C1283R.drawable.ridecard_profilepic_placeholder);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            ImageRepository.instance.getImage(imageUrl, 2, riderImage, null, ab);
        }
    }

    private void stopCloseTimer() {
        ((PillButton) findViewById(C1283R.id.puOfferPopupCloseButton)).stopTimer();
    }

    private void setCloseTimer(int iTimeOut) {
        ((PillButton) findViewById(C1283R.id.puOfferPopupCloseButton)).setTimeoutMilliSec(iTimeOut * 1000).startTimer();
    }

    public void hide() {
        if (this.mIsShown) {
            this.mIsShown = false;
            if (((PillButton) findViewById(C1283R.id.puOfferPopupCloseButton)).didTimeOut()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "TO|" + this.mRideId);
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "CLOSE|" + this.mRideId);
            }
            stopCloseTimer();
            TranslateAnimation ta = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
            ta.setInterpolator(new DecelerateInterpolator());
            ta.setDuration(300);
            ta.setAnimationListener(new C31802());
            startAnimation(ta);
        }
    }

    private void onClose() {
        hide();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.pickup_offer_popup, this);
        findViewById(C1283R.id.puOfferPopupCloseButton).setOnClickListener(new C31813());
    }

    public void show(CarpoolDrive drive, ActivityBase ab, int timeout) {
        if (this.mIsShown) {
            hide();
        }
        this.mDrive = drive;
        if (this.mDrive != null) {
            this.mRideId = this.mDrive.getId();
        } else {
            this.mRideId = "UNKNOWN";
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_SHOWN, "TYPE|RIDE_ID|STATUS", "BANNER|" + this.mRideId + "|" + CarpoolUtils.getBoardedState());
        NativeManager nm = NativeManager.getInstance();
        if (ConfigValues.getIntValue(11) == 0) {
            ((TextView) findViewById(C1283R.id.puOfferPopupTitle)).setText(String.format(nm.getLanguageString(DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_TITLE_A_PS), new Object[]{this.mDrive.getRider().getFirstName()}));
            ((TextView) findViewById(C1283R.id.puOfferPopupRewardAndDetour)).setText(Html.fromHtml(String.format(nm.getLanguageString(DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_VARIANT_A_PS), new Object[]{this.mDrive.getRewardString(ab), this.mDrive.getDetourString(DisplayStrings.DS_RIDE_REQ_EXTRA_TIME_PD)})));
        } else {
            ((TextView) findViewById(C1283R.id.puOfferPopupTitle)).setText(nm.getLanguageString(DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_TITLE_B));
            ((TextView) findViewById(C1283R.id.puOfferPopupRewardAndDetour)).setText(String.format(nm.getLanguageString(DisplayStrings.DS_CARPOOL_LIVE_OFFER_REWARD_PS), new Object[]{this.mDrive.getRewardString(ab)}));
        }
        setRiderImageUrl(this.mDrive.getRider().getImage(), ab);
        setupButton(nm, this.mDrive.getId());
        LayoutParams p = new LayoutParams(-1, -2);
        p.addRule(3, C1283R.id.NavBarLayout);
        p.alignWithParent = true;
        this.mIsShown = true;
        this.mLayoutManager.addView(this, p);
        setCloseTimer(timeout);
        if (this.mLayoutManager.getNavBar() != null) {
            this.mLayoutManager.getNavBar().setNextEnabled(false);
        }
        TranslateAnimation ta = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        ta.setInterpolator(new DecelerateInterpolator());
        ta.setDuration(300);
        startAnimation(ta);
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public CarpoolDrive getDrive() {
        return this.mDrive;
    }
}
