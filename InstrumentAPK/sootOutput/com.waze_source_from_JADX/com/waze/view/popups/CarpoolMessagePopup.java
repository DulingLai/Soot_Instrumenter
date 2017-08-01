package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolMessage;
import com.waze.carpool.CarpoolMessagingActivity;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolRide;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CarpoolUtils;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.navbar.NavBar;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import com.waze.view.anim.EasingInterpolators;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.button.PillButton;

public class CarpoolMessagePopup extends PopUp {
    private View mContent = null;
    private final Context mContext;
    private CarpoolNativeManager mCpnm;
    View mFillerView;
    private boolean mIsShown;
    private final LayoutManager mLayoutManager;
    private CarpoolRide mRide;
    private CarpoolUserData mRider;
    private PillButton pbClose;

    class C30862 implements Runnable {
        C30862() {
        }

        public void run() {
            CarpoolMessagePopup.this.mFillerView.setVisibility(8);
        }
    }

    class C30873 implements OnClickListener {
        C30873() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MESSAGING_TAKEOVER_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_TEXT).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolMessagePopup.this.mRide.getId()).send();
            Intent intent = new Intent(CarpoolMessagePopup.this.getContext(), CarpoolMessagingActivity.class);
            intent.putExtra("rider", CarpoolMessagePopup.this.mRider);
            intent.putExtra("ride", CarpoolMessagePopup.this.mRide);
            AppService.getActiveActivity().startActivityForResult(intent, 0);
            CarpoolMessagePopup.this.hide(true);
        }
    }

    class C30884 implements OnClickListener {
        C30884() {
        }

        public void onClick(View view) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MESSAGING_TAKEOVER_CLICK).addParam("ACTION", "CLOSE").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolMessagePopup.this.mRide.getId()).send();
            CarpoolMessagePopup.this.hide(true);
        }
    }

    class C30895 implements OnClickListener {
        C30895() {
        }

        public void onClick(View view) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MESSAGING_TAKEOVER_CLICK).addParam("ACTION", "CALL").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolMessagePopup.this.mRide.getId()).send();
            if (CarpoolUtils.isRideInvalid(CarpoolMessagePopup.this.mRide) || CarpoolMessagePopup.this.mRider == null || !CarpoolMessagePopup.this.mCpnm.isContactRiderAllowed(null, CarpoolMessagePopup.this.mRide) || !ConfigValues.getBoolValue(5) || CarpoolMessagePopup.this.mRide.getProxyNumber() == null || CarpoolMessagePopup.this.mRide.getProxyNumber().isEmpty()) {
                CarpoolUtils.DisplayErrorMsgBox();
                return;
            }
            CarpoolMessagePopup.this.mContext.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + CarpoolMessagePopup.this.mRide.getProxyNumber())));
            CarpoolMessagePopup.this.hide(true);
        }
    }

    class C30917 implements Runnable {
        C30917() {
        }

        public void run() {
        }
    }

    public CarpoolMessagePopup(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mContext = context;
        this.mLayoutManager = layoutManager;
        this.mCpnm = CarpoolNativeManager.getInstance();
        init();
    }

    public void init() {
        this.mIsShown = false;
        if (this.mContent != null) {
            removeView(this.mContent);
            this.mContent = null;
        }
    }

    public boolean onBackPressed() {
        hide(true);
        return true;
    }

    public void hide() {
        hide(false);
    }

    public void hide(final boolean collapse) {
        if (this.mIsShown) {
            this.mIsShown = false;
            this.pbClose.stopTimer();
            setTranslationY(0.0f);
            this.mFillerView.setVisibility(0);
            ViewPropertyAnimatorHelper.initAnimation(this, 300, EasingInterpolators.BOUNCE_IN).translationY((float) (-PixelMeasure.dp(150))).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new Runnable() {
                public void run() {
                    CarpoolMessagePopup.this.mFillerView.setVisibility(8);
                    NavBar navBar = CarpoolMessagePopup.this.mLayoutManager.getNavBar();
                    if (navBar != null) {
                        navBar.setAlertMode(false);
                    }
                    if (collapse) {
                        CarpoolMessagePopup.this.collapseToTicker();
                    }
                    CarpoolMessagePopup.this.dismiss();
                }
            }));
        }
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public void dismiss() {
        this.mLayoutManager.dismiss(this);
    }

    public void show(CarpoolRide ride, CarpoolUserData user, CarpoolMessage msg) {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MESSAGING_TAKEOVER_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, ride.getId()).send();
        if (this.mContent == null) {
            this.mContent = LayoutInflater.from(getContext()).inflate(C1283R.layout.carpool_message_popup, this, false);
            addView(this.mContent);
            this.mFillerView = findViewById(C1283R.id.layoutFiller);
            setClipChildren(false);
            setClipToPadding(false);
        }
        this.mRide = ride;
        this.mRider = user;
        boolean valid = initRide(msg);
        if (!this.mIsShown && valid) {
            addViewToLayoutManager();
        }
        this.mIsShown = valid;
        NavBar navBar = this.mLayoutManager.getNavBar();
        if (navBar != null) {
            navBar.setAlertMode(true, true);
        }
        setTranslationY((float) (-PixelMeasure.dp(150)));
        this.mFillerView.setVisibility(0);
        ViewPropertyAnimatorHelper.initAnimation(this, 300, EasingInterpolators.BOUNCE_OUT).translationY(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C30862()));
    }

    public void addViewToLayoutManager() {
        LayoutParams p = new LayoutParams(-1, -2);
        p.addRule(3, C1283R.id.NavBarLayout);
        this.mLayoutManager.addView(this, p);
    }

    private boolean initRide(CarpoolMessage msg) {
        if (this.mRide == null) {
            return false;
        }
        CharSequence firstName;
        TextView textView = (TextView) findViewById(C1283R.id.cpMsgPuRiderName);
        if (this.mRider != null) {
            firstName = this.mRider.getFirstName();
        } else {
            firstName = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        }
        textView.setText(firstName);
        setRiderImage(this.mRider.getImage());
        TextView message = (TextView) findViewById(C1283R.id.cpMsgPuMessageText);
        message.setText(msg.text);
        message.setOnClickListener(new C30873());
        ((TextView) findViewById(C1283R.id.cpMsgPuMessageTime)).setText(DisplayUtils.getTimeString(getContext(), msg.sent_seconds * 1000));
        TextView badge = (TextView) findViewById(C1283R.id.cpMsgPuMessageBadge);
        int numMessages = this.mCpnm.getUnreadChatMessageCount(this.mRide);
        if (numMessages > 0) {
            badge.setVisibility(0);
            badge.setText(NativeManager.getInstance().intToString(numMessages));
        } else {
            badge.setVisibility(8);
        }
        this.pbClose = (PillButton) findViewById(C1283R.id.cpMsgPuMoreButtonClose);
        this.pbClose.setOnClickListener(new C30884());
        this.pbClose.setTimeoutMilliSec(10000);
        this.pbClose.startTimer();
        findViewById(C1283R.id.cpMsgPuMoreButtonCall).setOnClickListener(new C30895());
        return true;
    }

    private void setRiderImage(String image_url) {
        final ImageView image = (ImageView) findViewById(C1283R.id.cpMsgPuRiderImage);
        image.setImageDrawable(new CircleFrameDrawable(BitmapFactory.decodeResource(getContext().getResources(), C1283R.drawable.ridecard_profilepic_placeholder), 0, 1));
        image.setLayerType(1, null);
        if (image_url != null && !image_url.isEmpty()) {
            VolleyManager.getInstance().loadImageFromUrl(image_url, new VolleyManager$ImageRequestListener() {
                public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
                    image.setImageDrawable(new CircleFrameDrawable(bitmap, 0, 1));
                }

                public void onImageLoadFailed(Object token, long duration) {
                }
            }, null, image.getWidth(), image.getHeight(), null);
        }
    }

    private void collapseToTicker() {
        if (this.mLayoutManager.shouldShowCarpoolBar()) {
            AppService.Post(new C30917(), 600);
            this.mCpnm.setManualRideTickerOpen(true);
        }
    }

    private void close() {
        dismiss();
    }
}
