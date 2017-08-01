package com.waze.view.popups;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ChatNotificationManager;
import com.waze.ChatNotificationManager.ChatHandler;
import com.waze.LayoutManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolMessage;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.CarpoolMessagesReceiveCompleteListener;
import com.waze.carpool.CarpoolRideMessages;
import com.waze.ifs.async.UpdateHandlers.MicroHandler;
import com.waze.ifs.async.UpdateHandlers.MicroHandler.MicroHandlerCallback;
import com.waze.navbar.NavBar;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;

public class UpcomingCarpoolBar extends PopUp implements MicroHandlerCallback {
    private ChatHandler mChatHandler;
    CarpoolDrive mDrive = null;
    MicroHandler mHandler = new MicroHandler(this);
    private UpcomingCarpoolBar mInstance;
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;
    private boolean mShouldShow = false;
    int rideState = -99;

    class C32391 implements OnClickListener {
        C32391() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TICKER_CLICK, "TYPE|RIDE_ID", "" + UpcomingCarpoolBar.this.rideState + "|" + UpcomingCarpoolBar.this.mDrive.getRide().getId());
            CarpoolNativeManager.getInstance().openCarpoolTakeover();
        }
    }

    class C32412 implements ChatHandler {

        class C32401 implements Runnable {
            C32401() {
            }

            public void run() {
                UpcomingCarpoolBar.this.getMessages();
            }
        }

        C32412() {
        }

        public boolean onChatMessage(String message) {
            return !UpcomingCarpoolBar.this.mLayoutManager.isPaused();
        }

        public void onMessagesLoaded() {
            if (!UpcomingCarpoolBar.this.mLayoutManager.isPaused()) {
                UpcomingCarpoolBar.this.post(new C32401());
            }
        }

        public void onMessageSent(boolean success) {
        }

        public void onMessageRead(String msgId) {
        }
    }

    class C32423 implements CarpoolMessagesReceiveCompleteListener {
        C32423() {
        }

        public void onComplete(CarpoolRideMessages crm) {
            if (crm != null && crm.hasMessages()) {
                CarpoolMessage message = crm.lastMessage();
                if (!message.from_me && message.unread) {
                    UpcomingCarpoolBar.this.mLayoutManager.showRiderMessagePopup(UpcomingCarpoolBar.this.mDrive.getRide(), UpcomingCarpoolBar.this.mDrive.getRider(), message);
                }
            }
        }
    }

    class C32445 extends AnimationEndListener {
        C32445() {
        }

        public void onAnimationEnd(Animation animation) {
            UpcomingCarpoolBar.this.dismiss();
            NavBar navBar = UpcomingCarpoolBar.this.mLayoutManager.getNavBar();
            if (navBar != null) {
                navBar.setNextEnabled(true);
            }
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    public void dismiss() {
        this.mIsShown = false;
        this.mLayoutManager.dismiss(this);
        this.mLayoutManager.onCarpoolTickerClosed();
    }

    private void close() {
        dismiss();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.upcoming_carpool_bar, this);
    }

    public boolean shouldShow() {
        return this.mShouldShow;
    }

    public void setShouldShow(boolean shouldShow) {
        this.mShouldShow = shouldShow;
    }

    public UpcomingCarpoolBar(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public void reshow() {
        show(this.mDrive);
    }

    public void show(CarpoolDrive drive) {
        if (drive != null && drive.getRide() != null && drive.getRide().getId() != null) {
            if ((this.mIsShown && drive != null && this.mDrive.getRide() != null && this.mDrive.getRide().getId() != null && this.mDrive.getRide().getId().contentEquals(drive.getRide().getId()) && this.mDrive.getRide().state == this.rideState) || this.mLayoutManager.isManualRidePopupShowing()) {
                return;
            }
            if (this.mLayoutManager.isPaused()) {
                this.mShouldShow = true;
                return;
            }
            this.mDrive = drive;
            this.rideState = this.mDrive.getRide().state;
            setStateAndText(this, this.rideState, false);
            setOnClickListener(new C32391());
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.mHandler);
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.mHandler);
            LayoutParams p = new LayoutParams(-1, -2);
            p.addRule(3, C1283R.id.NavBarLayout);
            p.alignWithParent = true;
            this.mIsShown = true;
            this.mShouldShow = true;
            this.mLayoutManager.addView(this, p, true);
            if (this.mLayoutManager.getNavBar() != null) {
                this.mLayoutManager.getNavBar().setNextEnabled(false);
            }
            TranslateAnimation anim = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
            anim.setDuration(300);
            anim.setInterpolator(new DecelerateInterpolator());
            startAnimation(anim);
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TICKER_SHOWN, "TYPE|RIDE_ID", "" + this.rideState + "|" + this.mDrive.getRide().getId());
            setupChatListener();
        }
    }

    void setupChatListener() {
        this.mChatHandler = new C32412();
        ChatNotificationManager.getInstance(true).setChatUpdateHandler(this.mDrive.getRide().getId(), this.mChatHandler);
    }

    void getMessages() {
        CarpoolNativeManager.getInstance().getCarpoolRideMessages(this.mDrive.getRide(), new C32423());
    }

    public static void setStateAndText(View root, int driveState, boolean forceArrived) {
        boolean showLive;
        final View liveRoller = root.findViewById(C1283R.id.upcomingCarpoolBarLiveRoller);
        TextView text = (TextView) root.findViewById(C1283R.id.upcomingCarpoolBarText);
        ImageView button = (ImageView) root.findViewById(C1283R.id.upcomingCarpoolBarButton);
        View bg = root.findViewById(C1283R.id.upcomingCarpoolBarBg);
        if (driveState == 8) {
            showLive = true;
            text.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_BAR_DROP_OFF));
        } else if (forceArrived || driveState == 16) {
            showLive = true;
            text.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_BAR_CONFIRM_PICKUP));
        } else if (driveState == 10) {
            showLive = true;
            text.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_BAR_PICKUP));
        } else {
            showLive = false;
            text.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_BAR_UPCOMING));
        }
        if (showLive) {
            text.setTextColor(root.getResources().getColor(C1283R.color.DarkShade));
            button.setImageResource(C1283R.drawable.carpool_live_strip_arrow_active);
            bg.setBackgroundResource(C1283R.drawable.carpool_strip_live);
            liveRoller.setVisibility(0);
            final Drawable drawable = root.getResources().getDrawable(C1283R.drawable.rs_requst_status_livedrive_tile);
            root.post(new Runnable() {
                public void run() {
                    TranslateAnimation liveRollerAnimation = new TranslateAnimation(0.0f, (float) drawable.getIntrinsicWidth(), 0.0f, 0.0f);
                    liveRollerAnimation.setDuration(2000);
                    liveRollerAnimation.setInterpolator(new LinearInterpolator());
                    liveRollerAnimation.setRepeatMode(1);
                    liveRollerAnimation.setRepeatCount(-1);
                    liveRoller.startAnimation(liveRollerAnimation);
                }
            });
            return;
        }
        liveRoller.setVisibility(8);
        liveRoller.clearAnimation();
        text.setTextColor(root.getResources().getColor(C1283R.color.ActiveGreen));
        button.setImageResource(C1283R.drawable.carpool_live_strip_arrow_disabled);
        bg.setBackgroundResource(C1283R.drawable.carpool_strip_base);
    }

    public void hide() {
        if (this.mIsShown) {
            this.mIsShown = false;
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.mHandler);
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, this.mHandler);
            TranslateAnimation anim = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
            anim.setDuration(300);
            anim.setFillAfter(true);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.setAnimationListener(new C32445());
            startAnimation(anim);
            ChatNotificationManager.getInstance(true).unsetChatUpdateHandler(this.mDrive.getRide().getId(), this.mChatHandler);
            this.mLayoutManager.onCarpoolTickerClosed();
        }
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public void handleMessage(Message msg) {
        if (this.mIsShown) {
            CarpoolDrive drive;
            if (msg.what == CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED) {
                CarpoolDrive[] list = (CarpoolDrive[]) msg.getData().getParcelableArray("drives");
                if (list != null) {
                    for (CarpoolDrive drive2 : list) {
                        if (!(drive2 == null || drive2.getId() == null || this.mDrive == null || this.mDrive.getId() == null || !this.mDrive.getId().equals(drive2.getId()))) {
                            this.mDrive = drive2;
                            setStateAndText(this, this.mDrive.getRide().state, false);
                        }
                    }
                } else {
                    return;
                }
            }
            if (msg.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED) {
                drive2 = (CarpoolDrive) msg.getData().getParcelable(CarpoolDrive.class.getSimpleName());
                if (drive2 != null && drive2.getRide().getId() != null && drive2.getId().equals(this.mDrive.getRide().getId())) {
                    this.mDrive = drive2;
                    setStateAndText(this, this.mDrive.getRide().state, false);
                }
            }
        }
    }
}
