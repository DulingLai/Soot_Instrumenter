package com.waze.view.popups;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.rtalerts.RTAlertsThumbsUpData;
import com.waze.strings.DisplayStrings;

public class ThumbsUpPopUP extends GenericTakeover {
    private RTAlertsThumbsUpData mThumbsUpData;
    private String mUserImageUrl = null;

    class C32241 implements Runnable {
        C32241() {
        }

        public void run() {
            AppService.getNativeManager().sendCommentNTV(ThumbsUpPopUP.this.mThumbsUpData.mAlertID);
        }
    }

    class C32252 implements OnClickListener {
        C32252() {
        }

        public void onClick(View v) {
            ThumbsUpPopUP.this.onReply();
        }
    }

    private void onReply() {
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C32241());
    }

    private void setButton() {
        setButton1((int) C1283R.drawable.reply_icon_blue, "", true, new C32252());
        setButton2Gone();
        setButton3Gone();
    }

    protected void init() {
        super.init();
        setLine1(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_THANKS));
        setLine2(null);
        setLine3(null);
        setTime(null);
        setButton();
    }

    public ThumbsUpPopUP(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mContext = context;
        this.mLayoutManager = layoutManager;
    }

    private void fillPopup() {
        setLine1(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_THANKS));
        setLine2(this.mThumbsUpData.mOrigAlertDescrition);
        setLine3(null);
        setIcon(this.mThumbsUpData.mIcon);
        setUser(this.mThumbsUpData.mFrom);
        setUserImageBottom(this.mUserImageUrl, this.mThumbsUpData.mMood, this.mThumbsUpData.mFrom);
        setTime((long) this.mThumbsUpData.mTime);
    }

    public void initView(RTAlertsThumbsUpData thumbsUpData, String UserImageUrl) {
        this.mThumbsUpData = thumbsUpData;
        this.mUserImageUrl = UserImageUrl;
        super.initView();
        fillPopup();
    }
}
