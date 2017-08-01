package com.waze.social;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.user.PersonBase;
import com.waze.utils.ImageRepository;
import java.util.Locale;

public class AddFriendDialog extends Dialog {
    private ActivityBase mActivity;
    private String mButtonText;
    private PersonBase mFriend;
    private IAddFriendDialog mIAddFriendDialog;
    private int mImageResource;
    private NativeManager mNatMgr = AppService.getNativeManager();
    private String mTitle;

    public interface IAddFriendDialog {
        void onNotNowClicked();

        void onSendRequestClicked();
    }

    class C28881 implements OnClickListener {
        C28881() {
        }

        public void onClick(View v) {
            if (AddFriendDialog.this.mIAddFriendDialog != null) {
                AddFriendDialog.this.mIAddFriendDialog.onSendRequestClicked();
            }
        }
    }

    class C28892 implements OnClickListener {
        C28892() {
        }

        public void onClick(View v) {
            AddFriendDialog.this.mIAddFriendDialog.onNotNowClicked();
        }
    }

    public AddFriendDialog(ActivityBase act, PersonBase p, IAddFriendDialog iafd) {
        super(act, C1283R.style.Dialog);
        this.mActivity = act;
        this.mFriend = p;
        this.mIAddFriendDialog = iafd;
    }

    public AddFriendDialog(ActivityBase act, String title, String buttonText, int imageResource, IAddFriendDialog iafd) {
        super(act, C1283R.style.Dialog);
        this.mActivity = act;
        this.mTitle = title;
        this.mButtonText = buttonText;
        this.mImageResource = imageResource;
        this.mIAddFriendDialog = iafd;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(C1283R.layout.add_friend_dialog);
        getWindow().setLayout(-1, -1);
        TextView sendRequestButton = (TextView) findViewById(C1283R.id.addFriendSendRequest);
        ImageView userPic = (ImageView) findViewById(C1283R.id.addFriendUserPic);
        if (this.mFriend != null) {
            ((TextView) findViewById(C1283R.id.addFriendTitle)).setText(String.format(this.mNatMgr.getLanguageString(DisplayStrings.DS_ADD_SP_AS_FRIEND), new Object[]{this.mFriend.getName()}).toUpperCase(Locale.US));
            String name = this.mFriend.getName();
            TextView initialsView = (TextView) findViewById(C1283R.id.addFriendUserInitials);
            if (name != null && name.length() > 0) {
                initialsView.setText(ShareUtility.getInitials(name));
            }
            String imageUrl = this.mFriend.getImage();
            if (!(imageUrl == null || imageUrl.isEmpty())) {
                String scheme = Uri.parse(imageUrl).getScheme();
                if (scheme == null || !scheme.equals("content")) {
                    imageUrl = DriveToNativeManager.getInstance().getFriendImageNTV(this.mFriend.getID(), (int) (80.0f * getContext().getResources().getDisplayMetrics().density));
                }
            }
            ImageRepository.instance.getImage(imageUrl, userPic, this.mActivity);
            sendRequestButton.setText(this.mNatMgr.getLanguageString(DisplayStrings.DS_SEND_FRIEND_REQUEST));
        } else {
            ((TextView) findViewById(C1283R.id.addFriendTitle)).setText(this.mTitle);
            sendRequestButton.setText(this.mButtonText);
            userPic.setImageResource(this.mImageResource);
        }
        sendRequestButton.setOnClickListener(new C28881());
        TextView notNow = (TextView) findViewById(C1283R.id.addFriendNotNow);
        notNow.setText(this.mNatMgr.getLanguageString(DisplayStrings.DS_SKIP));
        notNow.setOnClickListener(new C28892());
    }
}
