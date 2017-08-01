package com.waze.share;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.share.internal.ShareConstants;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.share.ShareFbQueries.IPostCallback;
import com.waze.share.ShareNativeManager.IShareFbMainDataHandler;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class FacebookEventPostActivity extends ActivityBase implements IShareFbMainDataHandler, IPostCallback {
    private boolean bIsAction = true;
    private String mEventId = null;
    private ShareFbMainData mFbMainData = null;
    private ShareFbFriend[] mFriends = null;
    private String mLink = null;
    private ShareFbLocation mLocation = null;
    private EditText mPostText = null;
    private final TextWatcher mPostTextWatcher = new C28114();
    private TextView mTripText = null;
    private String sPostText = null;

    class C28081 implements OnClickListener {
        C28081() {
        }

        public void onClick(View v) {
            if (FacebookEventPostActivity.this.mFbMainData.isNavigating && FacebookEventPostActivity.this.bIsAction) {
                FacebookEventPostActivity.this.postAction();
            } else {
                FacebookEventPostActivity.this.postFeed();
            }
        }
    }

    class C28092 implements DialogInterface.OnClickListener {
        C28092() {
        }

        public void onClick(DialogInterface dialog, int which) {
            FacebookEventPostActivity.this.setResult(-1);
            FacebookEventPostActivity.this.finish();
        }
    }

    class C28103 implements DialogInterface.OnClickListener {
        C28103() {
        }

        public void onClick(DialogInterface dialog, int which) {
            FacebookEventPostActivity.this.setResult(0);
            FacebookEventPostActivity.this.finish();
        }
    }

    class C28114 implements TextWatcher {
        C28114() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            FacebookEventPostActivity.this.setTripText();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareNativeManager nm = ShareNativeManager.getInstance();
        setContentView(C1283R.layout.fb_event_post);
        nm.getShareFbMainData(this, NativeManager.getInstance().isNavigatingNTV());
        this.sPostText = getIntent().getStringExtra(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        this.mEventId = getIntent().getStringExtra("Id");
        this.mLink = getIntent().getStringExtra("link");
        ((TextView) findViewById(C1283R.id.sharefbPostToWalltitle)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_POST_TO_EVENT_WALL));
        ((TextView) findViewById(C1283R.id.sharefbTitleText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_LET_OTHER_ATTENDEES_KNOW_TEXT));
        this.mPostText = (EditText) findViewById(C1283R.id.shareFbMainText);
        this.mPostText.addTextChangedListener(this.mPostTextWatcher);
        this.mTripText = (TextView) findViewById(C1283R.id.shareFbMainUserTripDetails);
        this.mTripText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_POST_EVENT_WALL_WAZE_TEXT));
        if (this.sPostText != null) {
            this.mPostText.setText(this.sPostText);
            this.bIsAction = false;
        }
        findViewById(C1283R.id.shareFbMainPostFbButton).setOnClickListener(new C28081());
    }

    public void onFbMainData(ShareFbMainData data) {
        if (this != null && isAlive()) {
            this.mFbMainData = data;
            ((TitleBar) findViewById(C1283R.id.shareFbMainTitleBar)).init(activity, data.title);
            this.mPostText.setHint(data.hint);
            ((TextView) findViewById(C1283R.id.shareFbMainPostFbButtonText)).setText(data.post_to_fb);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:
                return;
            default:
                setResult(resultCode);
                finish();
                return;
        }
    }

    public void onPostResult(int error_code, String errorString) {
        DialogInterface.OnClickListener onMsgClose = new C28092();
        DialogInterface.OnClickListener onMsgFailedClose = new C28103();
        if (error_code == 0) {
            Log.d(Logger.TAG, "Post has been done successfully");
            MsgBox.openMessageBoxTimeout(this.mFbMainData.postMsgBoxSuccessTitle, this.mFbMainData.postMsgBoxSuccessText, 5, onMsgClose);
            return;
        }
        Log.w(Logger.TAG, "There was an error posting to FB: " + errorString);
        MsgBox.openMessageBoxTimeout(this.mFbMainData.postMsgBoxFailureTitle, this.mFbMainData.postMsgBoxFailureText, 8, onMsgFailedClose);
    }

    private void postFeed() {
        NativeManager nm = NativeManager.getInstance();
        new StringBuilder().append(this.mTripText.getText().toString());
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_POST_EVENT_NOW);
        ShareFbQueries.postEventFeed(this, null, this.mLink, _friends_ids(), this.sPostText, this, true, this.mEventId, NativeManager.getInstance().getLanguageString(DisplayStrings.DS_POST_EVENT_WALL_WAZE_TEXT), "http://www.waze.com/images/facebook/share-image.png");
    }

    private void postAction() {
        ShareFbLocation loc = this.mLocation;
        if (loc == null) {
            loc = new ShareFbLocation();
            loc.id = "-1";
            loc.name = this.mFbMainData.location;
            loc.longitude = 0.0d;
            loc.latitude = 0.0d;
        }
        ShareFbQueries.postAction(this, loc, this.mFbMainData.eta, _friends_ids(), this.mPostText.getText().toString(), this, true);
    }

    private void setTripText() {
    }

    private void _append_highlight_span(SpannableStringBuilder builder, String str) {
        builder.append(str);
        _highlight_span(builder, builder.length() - str.length(), builder.length());
    }

    private void _highlight_span(SpannableStringBuilder builder, int start, int end) {
        builder.setSpan(new TextAppearanceSpan(this, C1283R.style.TextAppearance_ShareTripHighlights), start, end, 33);
    }

    private String[] _friends() {
        if (this.mFriends == null || this.mFriends.length == 0) {
            return null;
        }
        String[] res = new String[this.mFriends.length];
        for (int i = 0; i < this.mFriends.length; i++) {
            res[i] = this.mFriends[i].name;
        }
        return res;
    }

    private String[] _friends_ids() {
        if (this.mFriends == null || this.mFriends.length == 0) {
            return null;
        }
        String[] res = new String[this.mFriends.length];
        for (int i = 0; i < this.mFriends.length; i++) {
            res[i] = String.valueOf(this.mFriends[i].id);
        }
        return res;
    }
}
