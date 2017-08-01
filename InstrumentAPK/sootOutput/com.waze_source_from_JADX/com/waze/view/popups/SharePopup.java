package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.social.ShareDrivingFriendsActivity;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SharePopup extends GenericTakeover {
    private String mLocationText = null;
    private String mMeetingID = null;
    private int mType = 0;
    private FriendUserData mUser;

    class C32071 implements Runnable {
        C32071() {
        }

        public void run() {
            if (SharePopup.this.mType == 3) {
                DriveToNativeManager.getInstance().InitMeeting(SharePopup.this.mMeetingID);
                return;
            }
            Intent intent = new Intent(SharePopup.this.mContext, ShareDrivingFriendsActivity.class);
            intent.putExtra("meeting", SharePopup.this.mMeetingID);
            intent.putExtra("user", SharePopup.this.mUser);
            SharePopup.this.mContext.startActivity(intent);
        }
    }

    class C32082 implements OnClickListener {
        C32082() {
        }

        public void onClick(View v) {
            SharePopup.this.onReply();
        }
    }

    private void onReply() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_POPUP_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SHARED_DRIVE);
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C32071());
    }

    private void setButton() {
        setButton1((int) C1283R.drawable.accessory_icon_blue, "", true, new C32082());
        setButton2Gone();
        setButton3Gone();
    }

    public SharePopup(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
    }

    private void fillPopup() {
        String titleTxt;
        String line2format;
        NativeManager nm = AppService.getNativeManager();
        if (this.mType == 2) {
            titleTxt = nm.getLanguageString(DisplayStrings.DS_Z_SPEED_SENT_DRIVE_TITLE);
            line2format = nm.getLanguageString(DisplayStrings.DS_Z_SPEED_SENT_DRIVE_TEXT);
        } else {
            titleTxt = nm.getLanguageString(DisplayStrings.DS_Z_SPEED_SENT_LOCATION_TITLE);
            line2format = nm.getLanguageString(DisplayStrings.DS_Z_SPEED_SENT_LOCATION_TEXT);
        }
        setLine1(titleTxt);
        if (this.mUser != null) {
            setLine2(String.format(line2format, new Object[]{ShareUtility.getShortened(this.mUser.getName()), this.mLocationText}));
            setUserImageMain(this.mUser.getImage(), this.mUser.getName(), this.mUser.mMood);
            TimeZone tz = Calendar.getInstance().getTimeZone();
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this.mContext);
            timeFormat.setTimeZone(tz);
            long curTime = System.currentTimeMillis();
            String etaString = timeFormat.format(new Date(curTime + ((long) (this.mUser.mEtaSeconds * 1000))));
            setLine3(String.format(nm.getLanguageString(DisplayStrings.DS_FRIEND_SHARED_ETA_PS), new Object[]{etaString}));
            setTime((curTime / 1000) - ((long) this.mUser.mStatusTimeInSeconds));
        } else {
            setLine2(null);
            setLine3(null);
            setTime(null);
            setUserImageMain("", "", "");
        }
        setSmallIcon((int) C1283R.drawable.share_small_icon);
        setBottomUserGone();
        if (this.mType == 2) {
            setButton();
        } else {
            setButton();
        }
    }

    public void initView(FriendUserData thumbsUpData, int nType, String MeetingID, String LocationText) {
        this.mUser = thumbsUpData;
        this.mType = nType;
        this.mMeetingID = MeetingID;
        this.mLocationText = LocationText;
        if (this.mLocationText.equals(DisplayStrings.displayString(287))) {
            this.mLocationText = DisplayStrings.displayString(DisplayStrings.DS_TO_HOME);
        } else if (this.mLocationText.equals(DisplayStrings.displayString(288))) {
            this.mLocationText = DisplayStrings.displayString(DisplayStrings.DS_TO_WORK);
        } else {
            this.mLocationText = DisplayStrings.displayStringF(DisplayStrings.DS_TO_LOCATION_PS, this.mLocationText);
        }
        super.initView();
        fillPopup();
    }
}
