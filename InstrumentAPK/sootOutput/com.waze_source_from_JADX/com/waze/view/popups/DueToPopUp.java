package com.waze.view.popups;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.DriveToNativeManager;

public class DueToPopUp extends GenericNotification {
    private Runnable mRunnable;
    private String mText;

    class C31232 implements OnTouchListener {
        C31232() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENTS_DUE_TO_POPUP_BODY_CLICK, "TYPE", DueToPopUp.this.mText);
            DueToPopUp.this.hide();
            return true;
        }
    }

    class C31243 implements Runnable {
        C31243() {
        }

        public void run() {
            DueToPopUp.this.hide();
        }
    }

    public DueToPopUp(Context context, LayoutManager layoutManager, String text, boolean isTripServerResult) {
        super(context, layoutManager);
        init(text, isTripServerResult);
    }

    public boolean onBackPressed() {
        hide();
        return super.onBackPressed();
    }

    public void setup(String text, boolean isTripServerResult) {
        init(text, isTripServerResult);
    }

    void init(String text, final boolean isTripServerResult) {
        super.init();
        this.mText = text;
        setText(NativeManager.getInstance().getLanguageString(text));
        setIcon((int) C1283R.drawable.notification_dueto_icon);
        setButton1(C1283R.drawable.accessory_icon_white, "", true, new OnClickListener() {

            class C31211 implements Runnable {
                C31211() {
                }

                public void run() {
                    AppService.getNativeManager().navigateMainGetCouponNTV();
                }
            }

            public void onClick(View v) {
                DriveToNativeManager.getInstance().requestRoute(isTripServerResult);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENTS_DUE_TO_POPUP_BUTTON_CLICK, "TYPE", DueToPopUp.this.mText);
                NativeManager.Post(new C31211());
            }
        });
        setButton2Gone();
        setDrawTimerButton1();
    }

    public void show(boolean autoClose) {
        super.show();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENTS_DUE_TO_POPUP_SHOWN, "TYPE", this.mText);
        LayoutManager.isDueToPopupShown = true;
        setOnTouchListener(new C31232());
        if (autoClose) {
            this.mRunnable = new C31243();
            postDelayed(this.mRunnable, 8000);
        }
        this.mLayoutManager.hideSpotifyButton();
        this.mLayoutManager.setDueToShown(true);
        setCloseTimerButton1(WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS);
        DriveToNativeManager.getInstance().playDueToTTS();
    }

    public void hide() {
        LayoutManager.isDueToPopupShown = false;
        removeCallbacks(this.mRunnable);
        this.mRunnable = null;
        super.hide();
        this.mLayoutManager.showSpotifyButton();
        this.mLayoutManager.setDueToShown(false);
    }
}
