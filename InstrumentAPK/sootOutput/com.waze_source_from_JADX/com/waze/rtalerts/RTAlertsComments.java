package com.waze.rtalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.ifs.ui.ActivityBase;
import com.waze.rtalerts.RTAlertsNativeManager.IAlertsCommentDataHandler;
import com.waze.view.title.TitleBar;

public class RTAlertsComments extends ActivityBase {
    protected static RTAlertsAlertData mAlertData = null;
    protected static RTAlertsComments mThis = null;
    protected RTAlertsCommentsAdapter mAdapter = null;

    class C25862 implements OnClickListener {
        C25862() {
        }

        public void onClick(View v) {
            Intent data = new Intent();
            data.putExtra(RTAlertsConsts.RTALERTS_POPUP_ALERT_ID, RTAlertsComments.mAlertData.mID);
            RTAlertsComments.this.setResult(1001, data);
            RTAlertsComments.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.rtalerts_comments);
        mThis = this;
        if (mAlertData == null) {
            Log.e(Logger.TAG, "Cannot open alerts comments this way. Please use show function and provide alert data!");
            return;
        }
        RTAlertsNativeManager mgr = RTAlertsNativeManager.getInstance();
        this.mAdapter = new RTAlertsCommentsAdapter(this);
        ((TitleBar) findViewById(C1283R.id.rtalterts_comments_title_bar)).init((Activity) this, mgr.getLangStr(C1283R.string.rtalerts_comments_title));
        setAddCommentButton(mAlertData.mID);
        setCommentsList();
    }

    public static void updateFbImage(int alertId, int commentId, byte[] image, int width, int height) {
        if (mThis != null && mAlertData != null && mAlertData.mID == alertId) {
            View commentView = ((LinearLayout) mThis.findViewById(C1283R.id.rtalterts_list_item_comments_list_container)).findViewById(commentId);
            if (commentView != null) {
                mThis.mAdapter.updateFbImage(commentView, image, width, height);
            }
        }
    }

    public static void show(Activity contextForResult, RTAlertsAlertData alertData) {
        mAlertData = alertData;
        contextForResult.startActivityForResult(new Intent(contextForResult, RTAlertsComments.class), MainActivity.RTALERTS_REQUEST_CODE);
    }

    private void setAddCommentButton(final int alertId) {
        final RTAlertsNativeManager mgr = RTAlertsNativeManager.getInstance();
        View btnAddComment = findViewById(C1283R.id.rtalterts_comments_add_button);
        ((TextView) btnAddComment.findViewById(C1283R.id.rtalterts_comments_add_button_text)).setText(mgr.getLangStr(C1283R.string.rtalerts_comments_add_comment));
        btnAddComment.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mgr.postCommentValidate(RTAlertsComments.this, alertId);
            }
        });
    }

    private void setCommentsList() {
        RTAlertsNativeManager mgr = RTAlertsNativeManager.getInstance();
        View alertListItem = findViewById(C1283R.id.rtalerts_list_item_container);
        RTAlertsListAdapter.getView((Activity) this, alertListItem, mAlertData);
        final LinearLayout commentsListContainer = (LinearLayout) alertListItem.findViewById(C1283R.id.rtalterts_list_item_comments_list_container);
        commentsListContainer.setVisibility(0);
        alertListItem.setClickable(true);
        alertListItem.setOnClickListener(new C25862());
        mgr.getAlertsCommentData(mAlertData.mID, new IAlertsCommentDataHandler() {
            public void handler(RTAlertsCommentData[] data) {
                if (data != null) {
                    RTAlertsComments.this.mAdapter.update(data);
                    for (int i = 0; i < RTAlertsComments.this.mAdapter.getCount(); i++) {
                        if (i > 0) {
                            View dividerView = new View(RTAlertsComments.this);
                            dividerView.setBackgroundResource(C1283R.color.listmenu_separator);
                            commentsListContainer.addView(dividerView, new LayoutParams(-1, 1));
                        }
                        commentsListContainer.addView(RTAlertsComments.this.mAdapter.getView(i, null, commentsListContainer));
                    }
                    commentsListContainer.setPadding(0, 0, 0, 0);
                    commentsListContainer.requestLayout();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            setResult(1002);
            finish();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        mThis = null;
    }
}
