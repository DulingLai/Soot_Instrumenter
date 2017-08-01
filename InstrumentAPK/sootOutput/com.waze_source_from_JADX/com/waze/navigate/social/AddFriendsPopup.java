package com.waze.navigate.social;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AddFriendsListener;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import java.util.HashMap;

public class AddFriendsPopup extends Dialog {
    private AddFriendsData mAddFriendsData = null;
    private Context mContext = null;
    private LinearLayout mFriendsSuggestionLayout;
    private HashMap<Integer, FriendUserData> mFriendsToAdd = new HashMap();
    private NativeManager nativeManager;
    private TextView signButton;

    class C21841 implements AddFriendsListener {
        C21841() throws  {
        }

        public void onComplete(AddFriendsData $r1) throws  {
            AddFriendsPopup.this.mAddFriendsData = $r1;
            if (AddFriendsPopup.this.mAddFriendsData != null) {
                int $i1 = 0;
                if (AddFriendsPopup.this.mAddFriendsData.SuggestionFriends != null) {
                    $i1 = 0 + AddFriendsPopup.this.mAddFriendsData.SuggestionFriends.length;
                }
                if (AddFriendsPopup.this.mAddFriendsData.WaitingForApprovalFriends != null) {
                    $i1 += AddFriendsPopup.this.mAddFriendsData.WaitingForApprovalFriends.length;
                }
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_ADD_FRIENDS_POPUP_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + $i1, true);
                AddFriendsPopup.this.mFriendsSuggestionLayout.removeAllViews();
                for (FriendUserData $r8 : AddFriendsPopup.this.mAddFriendsData.SuggestionFriends) {
                    AddFriendsPopup.this.AddFriendInLayout($r8);
                }
                for (FriendUserData $r82 : AddFriendsPopup.this.mAddFriendsData.WaitingForApprovalFriends) {
                    AddFriendsPopup.this.AddFriendInLayout($r82);
                }
            }
        }
    }

    class C21852 implements OnClickListener {
        C21852() throws  {
        }

        public void onClick(View v) throws  {
            AddFriendsPopup.this.onContinueClicked();
        }
    }

    public AddFriendsPopup(Context $r1) throws  {
        super($r1, C1283R.style.Dialog);
        this.mContext = $r1;
        this.nativeManager = AppService.getNativeManager();
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        initLayout();
    }

    private void readFriendSuggestions() throws  {
        DriveToNativeManager.getInstance().getAddFriendsData(new C21841());
    }

    private void initLayout() throws  {
        setContentView(C1283R.layout.add_friends_popup);
        getWindow().setLayout(-1, -1);
        ((TextView) findViewById(C1283R.id.add_friends_title)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_ADD_FRIENDS));
        ((TextView) findViewById(C1283R.id.add_friends_explanation_text)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_FRIENDS_HEADING_TO_THE_SAME));
        this.mFriendsSuggestionLayout = (LinearLayout) findViewById(C1283R.id.friendsListFriendListLayout);
        this.signButton = (TextView) findViewById(C1283R.id.add_friends_continue);
        this.signButton.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_NEXT));
        this.signButton.setOnClickListener(new C21852());
        readFriendSuggestions();
    }

    private void AddFriendInLayout(FriendUserData $r1) throws  {
        View $r5 = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(C1283R.layout.add_friends_popup_list, null);
        AddFriendsUtils.setNameAndImage(AppService.getMainActivity(), $r5, $r1.getName(), $r1.getImage());
        $r5.findViewById(C1283R.id.addFriendsStatus).setVisibility(8);
        final FriendUserData friendUserData = $r1;
        $r5.setOnClickListener(new OnClickListener() {
            public void onClick(View $r1) throws  {
                if (((CheckBox) $r1.findViewById(C1283R.id.addFriendsCheckbox)).isChecked()) {
                    ((CheckBox) $r1.findViewById(C1283R.id.addFriendsCheckbox)).setChecked(false);
                    AddFriendsPopup.this.mFriendsToAdd.remove(Integer.valueOf(friendUserData.getID()));
                } else {
                    ((CheckBox) $r1.findViewById(C1283R.id.addFriendsCheckbox)).setChecked(true);
                    AddFriendsPopup.this.mFriendsToAdd.put(Integer.valueOf(friendUserData.getID()), friendUserData);
                }
                AddFriendsPopup.this.signButton.setText(AddFriendsPopup.this.nativeManager.getLanguageString(AddFriendsPopup.this.mFriendsToAdd.size() > 0 ? (short) 22 : (short) 488));
            }
        });
        ((CheckBox) $r5.findViewById(C1283R.id.addFriendsCheckbox)).setVisibility(0);
        $r5.findViewById(C1283R.id.addFriendsStatusLayout).setVisibility(8);
        $r5.findViewById(C1283R.id.addFriendsFriendOnline).setVisibility(4);
        $r5.findViewById(C1283R.id.addFriendsWazerImage).setVisibility(8);
        LinearLayout $r12 = this.mFriendsSuggestionLayout;
        $r12.addView($r5);
    }

    public void onBackPressed() throws  {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) throws  {
    }

    private void onContinueClicked() throws  {
        if (this.mFriendsToAdd.size() > 0) {
            Object[] $r5 = this.mFriendsToAdd.values().toArray();
            int[] $r1 = new int[$r5.length];
            int $i0 = 0;
            for (Object $r2 : $r5) {
                $r1[$i0] = ((PersonBase) $r2).getID();
                $i0++;
            }
            MyWazeNativeManager.getInstance().sendSocialAddFriends($r1, $i0, "Added " + $r5.length + " friends.");
        }
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_ADD_FRIENDS_POPUP_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "" + this.mFriendsToAdd.size(), true);
        close();
    }

    protected void close() throws  {
        AppService.getMainActivity().EnableOrientation();
        NativeManager.getInstance().signup_finished();
        dismiss();
    }
}
