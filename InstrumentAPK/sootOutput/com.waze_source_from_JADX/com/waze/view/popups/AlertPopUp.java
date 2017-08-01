package com.waze.view.popups;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.rtalerts.RTAlertsAlertData;
import com.waze.rtalerts.RTAlertsComments;
import com.waze.rtalerts.RTAlertsNativeManager;

public class AlertPopUp extends GenericTakeover {
    private RTAlertsAlertData mAlertData;
    private String sImageUrl;

    class C30401 implements Runnable {
        C30401() {
        }

        public void run() {
            AppService.getNativeManager().AlertPopUpClosedNTV();
        }
    }

    class C30422 implements OnClickListener {

        class C30411 implements Runnable {
            C30411() {
            }

            public void run() {
                AppService.getNativeManager().sendThumbsUpNTV(AlertPopUp.this.mAlertData.mID);
            }
        }

        C30422() {
        }

        public void onClick(View v) {
            v.setEnabled(false);
            RTAlertsAlertData access$000 = AlertPopUp.this.mAlertData;
            access$000.mNumThumbsUp++;
            AlertPopUp.this.setButton1((int) C1283R.drawable.like_icon_blue, AlertPopUp.this.mAlertData.mNumThumbsUp, false, (OnClickListener) this);
            NativeManager.Post(new C30411());
        }
    }

    class C30433 implements OnClickListener {
        C30433() {
        }

        public void onClick(View v) {
            if (AlertPopUp.this.mAlertData.mNumComments > 0) {
                RTAlertsComments.show(AppService.getMainActivity(), AlertPopUp.this.mAlertData);
            } else {
                RTAlertsNativeManager.getInstance().postCommentValidate(AppService.getMainActivity(), AlertPopUp.this.mAlertData.mID);
            }
        }
    }

    public AlertPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
    }

    public void dismiss() {
        super.dismiss();
        NativeManager.Post(new C30401());
    }

    void setLocation(String locationStr) {
        setLine3(locationStr);
    }

    public void fillPopUpData() {
        boolean commentsEnabled;
        boolean thumbUpEnabled;
        if (this.mAlertData.mIsAutoJam) {
            commentsEnabled = false;
        } else {
            commentsEnabled = true;
        }
        if (this.mAlertData.mIsAlertByMe || this.mAlertData.mIsThumbsUpByMe) {
            thumbUpEnabled = false;
        } else {
            thumbUpEnabled = true;
        }
        setUserImageBottom(this.sImageUrl, this.mAlertData.mMoodName, this.mAlertData.mReportedBy);
        if (TextUtils.isEmpty(this.mAlertData.mImageURL)) {
            setIcon(this.mAlertData.mIcon);
        } else {
            setPicture(this.mAlertData.mImageURL);
            setSmallIcon(this.mAlertData.mIcon);
        }
        setDistance(this.mAlertData.mDistanceStr, this.mAlertData.mUnit);
        setImage(this.mAlertData.mImageURL, this.mAlertData.mIcon);
        if (this.mAlertData.mDescription == null || this.mAlertData.mDescription.isEmpty()) {
            setLocation(this.mAlertData.mLocationStr);
            setLine2(this.mAlertData.mTitle);
        } else {
            if (this.mAlertData.mLocationStr == null || this.mAlertData.mLocationStr.isEmpty()) {
                setLine2(this.mAlertData.mTitle);
            } else {
                setLine2(this.mAlertData.mTitle + ", " + this.mAlertData.mLocationStr);
            }
            setDescription(this.mAlertData.mDescription);
        }
        setUser(this.mAlertData.mReportedBy);
        setTime(this.mAlertData.mTimeRelative);
        setButton1((int) C1283R.drawable.like_icon_blue, this.mAlertData.mNumThumbsUp, thumbUpEnabled, new C30422());
        setButton2((int) C1283R.drawable.comment_icon_blue, this.mAlertData.mNumComments, commentsEnabled, new C30433());
        setButton3Gone();
    }

    public void initView(RTAlertsAlertData alertData, int x, int y, String sUserImageUrl) {
        this.mAlertData = alertData;
        this.sImageUrl = sUserImageUrl;
        super.initView();
        fillPopUpData();
    }
}
