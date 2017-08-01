package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.FriendsActivity;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.messages.UserMessage;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.social.FriendsListData;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.utils.ImageRepository;
import java.util.Arrays;
import java.util.Comparator;

public class FriendsOnlinePopUp extends PopUp {
    protected FriendsListData data;
    private View[] mContainers;
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;

    class C31411 implements Runnable {
        C31411() {
        }

        public void run() {
            AppService.getNativeManager().FriendsOnlinePopUpClosedNTV();
        }
    }

    class C31422 implements OnClickListener {
        C31422() {
        }

        public void onClick(View v) {
            AppService.getActiveActivity().startActivity(new Intent(AppService.getAppContext(), FriendsActivity.class));
        }
    }

    class C31443 implements FriendsListListener {

        class C31431 implements Comparator<FriendUserData> {
            C31431() {
            }

            public int compare(FriendUserData o1, FriendUserData o2) {
                if (!o1.isOnline && o2.isOnline) {
                    return 1;
                }
                if (o1.isOnline && !o2.isOnline) {
                    return -1;
                }
                if (o1.mStatusTimeInSeconds == 0 && o2.mStatusTimeInSeconds == 0) {
                    return 0;
                }
                if (o1.mStatusTimeInSeconds == 0) {
                    return 1;
                }
                if (o2.mStatusTimeInSeconds == 0) {
                    return -1;
                }
                if (o1.mStatusTimeInSeconds < o2.mStatusTimeInSeconds) {
                    return -1;
                }
                if (o1.mStatusTimeInSeconds <= o2.mStatusTimeInSeconds) {
                    return 0;
                }
                return 1;
            }
        }

        C31443() {
        }

        public void onComplete(FriendsListData aData) {
            FriendsOnlinePopUp.this.data = aData;
            Arrays.sort(FriendsOnlinePopUp.this.data.friends, new C31431());
            for (View v : FriendsOnlinePopUp.this.mContainers) {
                v.setVisibility(8);
            }
            int counter = 0;
            for (FriendUserData friend : FriendsOnlinePopUp.this.data.friends) {
                if (friend.isOnline) {
                    FriendsOnlinePopUp.this.friendsListAddFriend(friend, FriendsOnlinePopUp.this.mContainers[counter]);
                    counter++;
                    if (counter >= FriendsOnlinePopUp.this.mContainers.length) {
                        break;
                    }
                }
            }
            if (counter > 0) {
                FriendsOnlinePopUp.this.findViewById(C1283R.id.friendsTakeOverUsers).setVisibility(0);
            }
        }
    }

    class C31465 implements Runnable {
        C31465() {
        }

        public void run() {
            FriendsOnlinePopUp.this.findViewById(C1283R.id.friendsTakeOverUsers).setVisibility(8);
        }
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public FriendsOnlinePopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public void dismiss() {
        this.mIsShown = false;
        NativeManager.Post(new C31411());
        findViewById(C1283R.id.friendsTakeOverUsers).setVisibility(0);
        findViewById(C1283R.id.friendsTakeOverUserButtons).setVisibility(8);
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.friends_takeover, this);
        findViewById(C1283R.id.friendsTakeOverUsers).setVisibility(4);
        findViewById(C1283R.id.friendsTakeOverUserButtons).setVisibility(8);
        this.mContainers = new View[]{findViewById(C1283R.id.friendsTakeOverUserImageContainer1), findViewById(C1283R.id.friendsTakeOverUserImageContainer2), findViewById(C1283R.id.friendsTakeOverUserImageContainer3)};
        findViewById(C1283R.id.friendsTakeOverRight1Button).setOnClickListener(new C31422());
    }

    private void setTitle(String title) {
        ((TextView) findViewById(C1283R.id.friendsTakeOverLine1)).setText(title);
    }

    public void fillPopUpData() {
        setTitle(NativeManager.getInstance().getLanguageString(245));
        DriveToNativeManager.getInstance().getFriendsListData(new C31443());
    }

    private void friendsListAddFriend(final FriendUserData friend, final View container) {
        container.setVisibility(0);
        container.clearAnimation();
        setFriendInView(friend, container);
        container.findViewById(C1283R.id.friendsTakeOverUserImageFrame).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FriendsOnlinePopUp.this.fadeOutOnClick(friend, container);
            }
        });
    }

    private void setFriendInView(FriendUserData friend, View container) {
        ((TextView) container.findViewById(C1283R.id.friendsTakeOverUserName)).setText(friend.name);
        TextView initials = (TextView) container.findViewById(C1283R.id.friendsTakeOverUserImageInitials);
        initials.setText(ShareUtility.getInitials(friend.name));
        ImageRepository.instance.getImage(friend.pictureUrl, 2, (ImageView) container.findViewById(C1283R.id.friendsTakeOverUserImage), initials, AppService.getActiveActivity());
    }

    private void fadeOutOnClick(FriendUserData friend, final View container) {
        for (View v : this.mContainers) {
            if (v != container && v.getVisibility() == 0) {
                AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(150);
                animation.setFillAfter(true);
                v.startAnimation(animation);
            }
        }
        postDelayed(new C31465(), 150);
        final View userButtons = findViewById(C1283R.id.friendsTakeOverUserButtons);
        userButtons.setVisibility(0);
        final View userContainer = userButtons.findViewById(C1283R.id.friendsTakeOverUserImageContainer);
        setFriendInView(friend, userContainer);
        friendsSetButtons(friend, userButtons);
        userContainer.bringToFront();
        userContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FriendsOnlinePopUp.this.returnToFriends(container);
            }
        });
        userButtons.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                userButtons.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int i = 2;
                int[] origLocation = new int[]{0, 0};
                i = 2;
                int[] destLocation = new int[]{0, 0};
                userContainer.findViewById(C1283R.id.friendsTakeOverUserImageFrame).getLocationInWindow(origLocation);
                container.findViewById(C1283R.id.friendsTakeOverUserImageFrame).getLocationInWindow(destLocation);
                int center = destLocation[0] + (container.findViewById(C1283R.id.friendsTakeOverUserImageFrame).getWidth() / 2);
                TranslateAnimation ta = new TranslateAnimation((float) (destLocation[0] - origLocation[0]), 0.0f, 0.0f, 0.0f);
                ta.setDuration(250);
                ta.setStartOffset(150);
                ta.setFillBefore(true);
                AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
                aa.setDuration(150);
                AnimationSet as = new AnimationSet(false);
                as.addAnimation(ta);
                as.addAnimation(aa);
                userContainer.startAnimation(as);
                View[] texts = new View[]{userButtons.findViewById(C1283R.id.friendsTakeOverButtonBeepText), userButtons.findViewById(C1283R.id.friendsTakeOverButtonCallText), userButtons.findViewById(C1283R.id.friendsTakeOverButtonMessageText)};
                for (View v : new View[]{userButtons.findViewById(C1283R.id.friendsTakeOverButtonBeep), userButtons.findViewById(C1283R.id.friendsTakeOverButtonCall), userButtons.findViewById(C1283R.id.friendsTakeOverButtonMessage)}) {
                    i = 2;
                    int[] viewLocation = new int[]{0, 0};
                    v.getLocationInWindow(viewLocation);
                    int delta = center - (viewLocation[0] + (v.getWidth() / 2));
                    as = new AnimationSet(false);
                    ta = new TranslateAnimation((float) delta, 0.0f, 0.0f, 0.0f);
                    ta.setFillBefore(true);
                    ta.setStartOffset(150);
                    ta.setDuration(250);
                    as.addAnimation(ta);
                    aa = new AlphaAnimation(0.0f, 1.0f);
                    aa.setDuration(150);
                    as.addAnimation(aa);
                    v.startAnimation(as);
                }
                for (View v2 : texts) {
                    i = 2;
                    viewLocation = new int[]{0, 0};
                    v2.getLocationInWindow(viewLocation);
                    ta = new TranslateAnimation((float) (center - (viewLocation[0] + (v2.getWidth() / 2))), 0.0f, 0.0f, 0.0f);
                    aa = new AlphaAnimation(0.0f, 1.0f);
                    as = new AnimationSet(true);
                    as.addAnimation(ta);
                    as.addAnimation(aa);
                    as.setDuration(250);
                    as.setFillBefore(true);
                    as.setStartOffset(150);
                    v2.startAnimation(as);
                }
            }
        });
    }

    private void returnToFriends(View container) {
        final View userContainer = findViewById(C1283R.id.friendsTakeOverUserButtons).findViewById(C1283R.id.friendsTakeOverUserImageContainer);
        int i = 2;
        int[] origLocation = new int[]{0, 0};
        i = 2;
        int[] destLocation = new int[]{0, 0};
        userContainer.findViewById(C1283R.id.friendsTakeOverUserImageFrame).getLocationInWindow(origLocation);
        container.findViewById(C1283R.id.friendsTakeOverUserImageFrame).getLocationInWindow(destLocation);
        int center = destLocation[0] + (container.findViewById(C1283R.id.friendsTakeOverUserImageFrame).getWidth() / 2);
        TranslateAnimation ta = new TranslateAnimation(0.0f, (float) (destLocation[0] - origLocation[0]), 0.0f, 0.0f);
        ta.setDuration(250);
        userContainer.startAnimation(ta);
        final View view = container;
        ta.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                FriendsOnlinePopUp.this.fadeBackToFriends(userContainer, view);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        View[] texts = new View[]{userButtons.findViewById(C1283R.id.friendsTakeOverButtonBeepText), userButtons.findViewById(C1283R.id.friendsTakeOverButtonCallText), userButtons.findViewById(C1283R.id.friendsTakeOverButtonMessageText)};
        for (View v : new View[]{userButtons.findViewById(C1283R.id.friendsTakeOverButtonBeep), userButtons.findViewById(C1283R.id.friendsTakeOverButtonCall), userButtons.findViewById(C1283R.id.friendsTakeOverButtonMessage)}) {
            int[] iArr = new int[2];
            iArr = new int[]{0, 0};
            v.getLocationInWindow(iArr);
            ta = new TranslateAnimation(0.0f, (float) (center - (iArr[0] + (v.getWidth() / 2))), 0.0f, 0.0f);
            ta.setFillAfter(true);
            ta.setDuration(250);
            v.startAnimation(ta);
        }
        for (View v2 : texts) {
            iArr = new int[2];
            iArr = new int[]{0, 0};
            v2.getLocationInWindow(iArr);
            ta = new TranslateAnimation(0.0f, (float) (center - (iArr[0] + (v2.getWidth() / 2))), 0.0f, 0.0f);
            AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(ta);
            as.addAnimation(aa);
            as.setDuration(250);
            as.setFillAfter(true);
            v2.startAnimation(as);
        }
    }

    private void fadeBackToFriends(View userContainer, View container) {
        findViewById(C1283R.id.friendsTakeOverUsers).setVisibility(0);
        for (View v : this.mContainers) {
            if (v != container && v.getVisibility() == 0) {
                AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(150);
                v.startAnimation(animation);
            }
        }
        final View userButtons = findViewById(C1283R.id.friendsTakeOverUserButtons);
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setDuration(150);
        userContainer.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                userButtons.setVisibility(8);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        for (View v2 : new View[]{userButtons.findViewById(C1283R.id.friendsTakeOverButtonBeep), userButtons.findViewById(C1283R.id.friendsTakeOverButtonCall), userButtons.findViewById(C1283R.id.friendsTakeOverButtonMessage)}) {
            aa = new AlphaAnimation(0.0f, 0.0f);
            aa.setDuration(0);
            aa.setFillAfter(true);
            v2.startAnimation(aa);
        }
    }

    private void friendsSetButtons(final FriendUserData friend, View container) {
        View messageLayout = container.findViewById(C1283R.id.friendsTakeOverButtonMessageLayout);
        View beepLayout = container.findViewById(C1283R.id.friendsTakeOverButtonBeepLayout);
        if (friend.mAllowPrivatePing) {
            messageLayout.setVisibility(0);
            ((TextView) container.findViewById(C1283R.id.friendsTakeOverButtonMessageText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_MESSAGE));
            messageLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    UserMessage.startPrivate(AppService.getMainActivity(), friend);
                }
            });
            if (friend.mAllowBeepBeep) {
                beepLayout.setVisibility(0);
                ((TextView) container.findViewById(C1283R.id.friendsTakeOverButtonBeepText)).setText(NativeManager.getInstance().getLanguageString(238));
                beepLayout.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_BEEP_BEEP_CLICK_FROM, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_FRIENDS_ONLINE);
                        NativeManager.getInstance().SendBeepBeep(friend.mLongitude, friend.mLatitude, friend.mAzimuth, friend.mID, null);
                    }
                });
            } else {
                beepLayout.setVisibility(8);
            }
        } else {
            messageLayout.setVisibility(8);
            beepLayout.setVisibility(8);
        }
        final String phone = friend.getPhone();
        View callLayout = container.findViewById(C1283R.id.friendsTakeOverButtonCallLayout);
        if (phone == null || phone.isEmpty()) {
            callLayout.setVisibility(8);
            return;
        }
        callLayout.setVisibility(0);
        ((TextView) container.findViewById(C1283R.id.friendsTakeOverButtonCallText)).setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CALL));
        callLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.setData(Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + phone));
                AppService.getActiveActivity().startActivityForResult(intent, 0);
            }
        });
    }

    public boolean onBackPressed() {
        this.mLayoutManager.callCloseAllPopups(1);
        return true;
    }

    public PopUp initView() {
        if (this.mIsShown) {
            dismiss();
        }
        this.mIsShown = true;
        fillPopUpData();
        return this;
    }

    public void hide() {
        dismiss();
    }

    public void setShiftEffect(boolean bOnLeft, float shift) {
        super.setShiftEffect(bOnLeft, shift);
        float alpha = 1.0f - (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * shift);
        if (alpha < 0.0f) {
            alpha = 0.0f;
        }
        for (int buttonId : new int[]{C1283R.id.friendsTakeOverUsers, C1283R.id.friendsTakeOverUserButtons}) {
            View button = findViewById(buttonId);
            if (button.getVisibility() == 0) {
                button.setAlpha(alpha);
            }
        }
    }

    public void setPageIndicatorShown(boolean shown) {
        super.setPageIndicatorShown(shown);
        findViewById(C1283R.id.friendsTakeOverLayout).setPadding(0, getResources().getDimensionPixelSize(shown ? C1283R.dimen.takeover_top_padding : C1283R.dimen.takeover_top_padding_no_indicator), 0, 0);
    }

    public void onOrientationChanged() {
        super.onOrientationChanged();
        removeAllViews();
        init();
        fillPopUpData();
    }

    public int GetHeight() {
        return getChildAt(0).getMeasuredHeight();
    }

    public Rect getRect() {
        Rect rect = new Rect();
        getChildAt(0).getHitRect(rect);
        int[] loc = new int[2];
        getLocationInWindow(loc);
        rect.right += loc[0];
        rect.left += loc[0];
        rect.top += loc[1];
        rect.bottom += loc[1];
        return rect;
    }
}
