package com.waze.view.popups;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.rtalerts.RTAlertsCommentData;

public class CommentPopUP extends GenericTakeover {
    private RTAlertsCommentData mCommentData;
    private String mUserImageUrl;

    class C31001 implements Runnable {
        C31001() {
        }

        public void run() {
            AppService.getNativeManager().sendCommentNTV(CommentPopUP.this.mCommentData.mAlertID);
        }
    }

    class C31012 implements Runnable {
        C31012() {
        }

        public void run() {
            AppService.getNativeManager().ReportAbusNTV(CommentPopUP.this.mCommentData.mAlertID, CommentPopUP.this.mCommentData.mCommentID);
        }
    }

    class C31023 implements OnClickListener {
        C31023() {
        }

        public void onClick(View v) {
            CommentPopUP.this.onReply();
        }
    }

    class C31034 implements OnClickListener {
        C31034() {
        }

        public void onClick(View v) {
            CommentPopUP.this.onFlag();
        }
    }

    private void onReply() {
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C31001());
    }

    private void onFlag() {
        this.mLayoutManager.callCloseAllPopups(1);
        NativeManager.Post(new C31012());
    }

    public CommentPopUP(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mContext = context;
        this.mLayoutManager = layoutManager;
    }

    private void fillPopup() {
        setLine1(AppService.getNativeManager().getLanguageString(1));
        setLine2(this.mCommentData.mDescription);
        setLine3(this.mCommentData.mOrigAlertDescrition.replace(",  ", ","));
        setTime(this.mCommentData.m64Time);
        setUser(this.mCommentData.mReportedBy);
        setUserImageBottom(this.mUserImageUrl, this.mCommentData.mMood, this.mCommentData.mReportedBy);
        setIcon(this.mCommentData.mIcon);
        setSmallIcon((int) C1283R.drawable.comment_small_icon);
        setButton1((int) C1283R.drawable.reply_icon_blue, "", true, new C31023());
        setButton2((int) C1283R.drawable.flag_icon_red, "", true, new C31034());
        setButton3Gone();
    }

    public void initView(RTAlertsCommentData commentsData, String UserImageUrl) {
        this.mUserImageUrl = UserImageUrl;
        this.mCommentData = commentsData;
        super.initView();
        fillPopup();
    }
}
