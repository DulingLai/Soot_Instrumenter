package com.waze.view.popups;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.rtalerts.RTAlertsThumbsUpData;

public class BeepPopUp extends GenericTakeover {
    private RTAlertsThumbsUpData mThumbsUpData;
    private String mUserImageUrl = null;

    class C30601 implements Runnable {
        C30601() {
        }

        public void run() {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_BEEP_BACK);
            AppService.getNativeManager().sendBeepBackNTV(BeepPopUp.this.mThumbsUpData.mAlertID);
        }
    }

    class C30612 implements OnClickListener {
        C30612() {
        }

        public void onClick(View v) {
            BeepPopUp.this.onReply();
        }
    }

    private void onReply() {
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C30601());
    }

    private void setButton() {
        setButton1((int) C1283R.drawable.beep_icon_blue, "", true, new C30612());
        setButton2Gone();
        setButton3Gone();
    }

    protected void init() {
        super.init();
        setLine1(AppService.getNativeManager().getLanguageString(238));
        setLine2(null);
        setLine3(null);
        setButton();
        setBottomUserGone();
    }

    public BeepPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
    }

    private void fillPopup() {
        setLine1(AppService.getNativeManager().getLanguageString(238));
        setLine2(this.mThumbsUpData.mFrom);
        long timeSec = (System.currentTimeMillis() / 1000) - ((long) this.mThumbsUpData.mTime);
        setLine3(NativeManager.getInstance().getRelativeTimeStringNTV(timeSec, true));
        setTime(timeSec);
        setBottomUserGone();
        setUserImageMain(this.mUserImageUrl, "", this.mThumbsUpData.mMood);
        setSmallIcon((int) C1283R.drawable.beep_small_icon);
        setButton();
    }

    public void initView(RTAlertsThumbsUpData thumbsUpData, String UserImageUrl) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_BEEP_RECEIVED);
        this.mThumbsUpData = thumbsUpData;
        this.mUserImageUrl = UserImageUrl;
        super.initView();
        fillPopup();
    }
}
