package com.waze.view.popups;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.rtalerts.RTAlertsAlertData;
import com.waze.strings.DisplayStrings;

public class PingPopUP extends GenericTakeover {
    private RTAlertsAlertData mAlertData;
    private boolean mIsPrivatePing;
    private String mUserImageUrl;

    class C31821 implements Runnable {
        C31821() {
        }

        public void run() {
            AppService.getNativeManager().sendCommentNTV(PingPopUP.this.mAlertData.mID);
        }
    }

    class C31832 implements Runnable {
        C31832() {
        }

        public void run() {
            AppService.getNativeManager().ReportAbusNTV(PingPopUP.this.mAlertData.mID, -1);
        }
    }

    class C31843 implements OnClickListener {
        C31843() {
        }

        public void onClick(View v) {
            PingPopUP.this.onReply();
        }
    }

    class C31854 implements OnClickListener {
        C31854() {
        }

        public void onClick(View v) {
            PingPopUP.this.onFlag();
        }
    }

    private void onReply() {
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C31821());
    }

    private void onFlag() {
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C31832());
    }

    public PingPopUP(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
    }

    public void fillPopUpData(RTAlertsAlertData alertData) {
        String titleTxt;
        NativeManager nm = NativeManager.getInstance();
        if (this.mIsPrivatePing) {
            titleTxt = nm.getLanguageString(DisplayStrings.DS_INCOMING_MESSAGE___);
        } else {
            titleTxt = nm.getLanguageString(437);
        }
        setLine1(titleTxt);
        setLine2(this.mAlertData.mReportedBy);
        setDescription("\"" + this.mAlertData.mDescription + "\"");
        setUserImageMain(this.mUserImageUrl, "", this.mAlertData.mMoodName);
        setSmallIcon((int) C1283R.drawable.message_small_icon);
        setBottomUserGone();
        setTime(this.mAlertData.mTimeRelative);
        setButton1((int) C1283R.drawable.reply_icon_blue, "", true, new C31843());
        setButton2((int) C1283R.drawable.flag_icon_red, "", true, new C31854());
        setButton3Gone();
    }

    public void initView(RTAlertsAlertData alertData, boolean bIsPrivatePing, String sUserImageUrl) {
        this.mAlertData = alertData;
        this.mIsPrivatePing = bIsPrivatePing;
        this.mUserImageUrl = sUserImageUrl;
        super.initView();
        fillPopUpData(alertData);
    }
}
