package com.waze.social;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultCode;
import com.waze.Utils;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.EndDriveListener;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.social.EndDriveData;
import com.waze.share.ShareUtility;
import com.waze.share.UserDetailsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import com.waze.view.anim.MaterialDesignImageAnimationHelper;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.listitems.ListItemSnapScrollView;
import com.waze.view.listitems.ListItemSnapScrollView.ListItemSnapScrollerListener;
import com.waze.view.text.WazeTextView;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FriendItemView extends FrameLayout {
    private static FriendItemView openedItemView;
    private FrameLayout mAcceptFriendButton;
    private ImageView mAcceptGadient;
    private FrameLayout mBeepButton;
    private FrameLayout mDeclineFriendButton;
    private ImageView mDeclineGradient;
    private ImageView mFriendActionButton;
    private View mFriendIndicator;
    private RelativeLayout mFriendItemContent;
    private ImageContainer mImageContainer;
    private boolean mImageReceived;
    private TextView mInitialsLabel;
    private FriendItemViewListener mListener;
    private FriendUserData mModel;
    private ImageView mProfileIcon;
    private ImageView mProfileImage;
    private ListItemSnapScrollView mScroller;
    private LinearLayout mScrollerContent;
    private View mSeparatorView;
    private TextView mUserInfoLabel;
    private TextView mUserNameLabel;

    class C28911 implements ListItemSnapScrollerListener {
        C28911() {
        }

        public void onScrollChanged(int currentX) {
            if (FriendItemView.this.mAcceptFriendButton.getVisibility() == 0 && FriendItemView.this.mDeclineFriendButton.getVisibility() == 0) {
                FriendItemView.this.adjustAcceptDeclineButtonsToScroll(currentX);
            } else if (FriendItemView.this.mBeepButton.getVisibility() == 0) {
                FriendItemView.this.adjustBeepButtonToScroll(currentX);
            }
            if (FriendItemView.openedItemView != FriendItemView.this && FriendItemView.openedItemView != null && FriendItemView.this.mScroller.isUserInteracting()) {
                FriendItemView.openedItemView.mScroller.fullScroll(17);
                FriendItemView.openedItemView = FriendItemView.this;
            }
        }

        public void onScrollEnded(final int currentX) {
            int scrollerWidth = 0;
            if (FriendItemView.this.mBeepButton.getVisibility() == 0) {
                scrollerWidth = 0 + FriendItemView.this.mBeepButton.getMeasuredWidth();
            }
            if (FriendItemView.this.mAcceptFriendButton.getVisibility() == 0) {
                scrollerWidth += FriendItemView.this.mAcceptFriendButton.getMeasuredWidth();
            }
            if (FriendItemView.this.mDeclineFriendButton.getVisibility() == 0) {
                scrollerWidth += FriendItemView.this.mDeclineFriendButton.getMeasuredWidth();
            }
            final int maxScroll = scrollerWidth;
            FriendItemView.this.post(new Runnable() {
                public void run() {
                    if (currentX > 0 && currentX <= maxScroll / 2) {
                        FriendItemView.this.mScroller.fullScroll(17);
                        if (FriendItemView.openedItemView == FriendItemView.this) {
                            FriendItemView.openedItemView = null;
                        }
                    } else if (currentX < maxScroll && currentX > maxScroll / 2) {
                        FriendItemView.this.mScroller.fullScroll(66);
                    } else if (currentX == 0 && FriendItemView.openedItemView == FriendItemView.this) {
                        FriendItemView.openedItemView = null;
                    }
                }
            });
        }

        public void onScrollStarted() {
            if (!(FriendItemView.openedItemView == null || FriendItemView.openedItemView == FriendItemView.this)) {
                FriendItemView.openedItemView.mScroller.fullScroll(17);
            }
            FriendItemView.openedItemView = FriendItemView.this;
        }
    }

    class C28932 implements OnClickListener {

        class C28921 implements IResultCode {
            C28921() {
            }

            public void onResult(int res) {
                Log.i("FriendItemView", "Beep Beep completed with result: " + res);
            }
        }

        C28932() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SendBeepBeep(FriendItemView.this.mModel.mLongitude, FriendItemView.this.mModel.mLatitude, FriendItemView.this.mModel.mAzimuth, FriendItemView.this.mModel.mID, new C28921());
        }
    }

    class C28953 implements OnClickListener {

        class C28941 implements Runnable {
            C28941() {
            }

            public void run() {
                if (FriendItemView.this.mListener != null) {
                    FriendItemView.this.mListener.refreshList();
                }
            }
        }

        C28953() {
        }

        public void onClick(View v) {
            MyWazeNativeManager.getInstance().sendSocialAddFriends(new int[]{FriendItemView.this.mModel.getID()}, 1, null);
            FriendItemView.this.mScroller.fullScroll(17);
            FriendItemView.this.postDelayed(new C28941(), 500);
        }
    }

    class C28974 implements OnClickListener {

        class C28961 implements Runnable {
            C28961() {
            }

            public void run() {
                if (FriendItemView.this.mListener != null) {
                    FriendItemView.this.mListener.refreshList();
                }
            }
        }

        C28974() {
        }

        public void onClick(View v) {
            MyWazeNativeManager.getInstance().sendSocialRemoveFriends(new int[]{FriendItemView.this.mModel.getID()}, 1, null);
            FriendItemView.this.mScroller.fullScroll(17);
            FriendItemView.this.postDelayed(new C28961(), 500);
        }
    }

    class C28985 implements OnClickListener {
        C28985() {
        }

        public void onClick(View v) {
            if (!TextUtils.isEmpty(FriendItemView.this.mModel.mMeetingIdSharedWithMe) || !TextUtils.isEmpty(FriendItemView.this.mModel.mMeetingIdSharedByMe) || !TextUtils.isEmpty(FriendItemView.this.mModel.arrivedShareText)) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_LIST_CLICK, AnalyticsEvents.ANALYTICS_EVENT_VALUE_LIST, "ON_THE_WAY");
            } else if (FriendItemView.this.mModel.mIsFriend) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_LIST_CLICK, AnalyticsEvents.ANALYTICS_EVENT_VALUE_LIST, "FRIENDS");
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_LIST_CLICK, AnalyticsEvents.ANALYTICS_EVENT_VALUE_LIST, "CONTACTS");
            }
            Intent userDetailsIntent = new Intent(FriendItemView.this.getContext(), UserDetailsActivity.class);
            userDetailsIntent.putExtra(PublicMacros.FriendUserData, FriendItemView.this.mModel);
            AppService.getActiveActivity().startActivity(userDetailsIntent);
        }
    }

    class C28996 implements OnClickListener {
        C28996() {
        }

        public void onClick(View v) {
            FriendItemView.this.mScroller.fullScroll(66);
        }
    }

    class C29007 implements EndDriveListener {
        C29007() {
        }

        public void onComplete(EndDriveData data) {
            if (data != null) {
                String eta;
                if (data.myEtaSeconds >= 0) {
                    eta = String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FRIEND_SHARED_ETA_PS), new Object[]{Utils.formatTimeRelative(FriendItemView.this.getContext(), data.myEtaSeconds)});
                } else {
                    eta = NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FRIEND_SHARED_ETA_UNKNOWN);
                }
                FriendItemView.this.mUserInfoLabel.setText(eta);
                FriendItemView.this.mUserInfoLabel.setTextColor(FriendItemView.this.getResources().getColor(C1283R.color.ActiveGreen));
            }
        }
    }

    class C29018 implements Runnable {
        C29018() {
        }

        public void run() {
            if (!FriendItemView.this.mImageReceived) {
                FriendItemView.this.showInitials();
            }
        }
    }

    class C29029 implements VolleyManager$ImageRequestListener {
        C29029() {
        }

        public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
            if (token == FriendItemView.this.mModel) {
                FriendItemView.this.mImageReceived = true;
                Drawable csd = new CircleShaderDrawable(bitmap, 0);
                FriendItemView.this.mProfileImage.setImageDrawable(csd);
                FriendItemView.this.mProfileImage.setVisibility(0);
                if (duration > 300) {
                    MaterialDesignImageAnimationHelper.animateImageEntrance(csd, 1500);
                    FriendItemView.this.hideInitials(300);
                } else {
                    FriendItemView.this.hideInitials(0);
                }
                FriendItemView.this.mImageContainer = null;
            }
        }

        public void onImageLoadFailed(Object token, long duration) {
            if (token == FriendItemView.this.mModel) {
                FriendItemView.this.showInitials();
                FriendItemView.this.mImageContainer = null;
            }
        }
    }

    public interface FriendItemViewListener {
        int getRequiredPadding();

        void refreshList();
    }

    public FriendItemView(Context context) {
        this(context, null);
    }

    public FriendItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.friend_item_view, null);
        this.mScroller = (ListItemSnapScrollView) content.findViewById(C1283R.id.friendItemScroller);
        this.mProfileImage = (ImageView) content.findViewById(C1283R.id.imgFriendProfilePic);
        this.mProfileIcon = (ImageView) content.findViewById(C1283R.id.imgFriendProfileIcon);
        this.mUserNameLabel = (TextView) content.findViewById(C1283R.id.lblFriendName);
        this.mUserInfoLabel = (TextView) content.findViewById(C1283R.id.lblFriendInfo);
        this.mFriendIndicator = content.findViewById(C1283R.id.friendRightIndicator);
        this.mFriendActionButton = (ImageView) content.findViewById(C1283R.id.btnFriendAction);
        this.mBeepButton = (FrameLayout) content.findViewById(C1283R.id.btnBeepBeep);
        this.mDeclineFriendButton = (FrameLayout) content.findViewById(C1283R.id.btnDeclineFriendRequest);
        this.mAcceptFriendButton = (FrameLayout) content.findViewById(C1283R.id.btnAcceptFriendRequest);
        this.mScrollerContent = (LinearLayout) content.findViewById(C1283R.id.scrollContent);
        this.mFriendItemContent = (RelativeLayout) content.findViewById(C1283R.id.friendItemViewContent);
        this.mAcceptGadient = (ImageView) content.findViewById(C1283R.id.acceptGradient);
        this.mDeclineGradient = (ImageView) content.findViewById(C1283R.id.declineGradient);
        this.mInitialsLabel = (TextView) content.findViewById(C1283R.id.initialsLabel);
        this.mSeparatorView = content.findViewById(C1283R.id.separatorView);
        this.mScroller.setScrollListener(new C28911());
        this.mScroller.setScrollEnabled(true);
        this.mBeepButton.setOnClickListener(new C28932());
        this.mAcceptFriendButton.setOnClickListener(new C28953());
        this.mDeclineFriendButton.setOnClickListener(new C28974());
        if (VERSION.SDK_INT < 21) {
            this.mFriendItemContent.setBackgroundColor(getResources().getColor(C1283R.color.White));
        } else {
            this.mFriendItemContent.setBackground(new RippleDrawable(ColorStateList.valueOf(getResources().getColor(C1283R.color.BlueWhaleLight)), new ColorDrawable(getResources().getColor(C1283R.color.White)), null));
            this.mBeepButton.setBackground(new RippleDrawable(ColorStateList.valueOf(-6377808), new ColorDrawable(getResources().getColor(C1283R.color.BlueSky)), null));
            this.mDeclineFriendButton.setBackground(new RippleDrawable(ColorStateList.valueOf(-3196611), new ColorDrawable(getResources().getColor(C1283R.color.RedSweet)), null));
            this.mAcceptFriendButton.setBackground(new RippleDrawable(ColorStateList.valueOf(-3196611), new ColorDrawable(getResources().getColor(C1283R.color.BlueSky)), null));
        }
        this.mFriendItemContent.setPadding(0, 0, 0, 0);
        this.mFriendItemContent.setOnClickListener(new C28985());
        content.setLayoutParams(new LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.friendItemHeight)));
        setLayoutParams(new RecyclerView.LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.friendItemHeight)));
        addView(content);
    }

    public static void snapShutOpenCell() {
        if (openedItemView != null) {
            openedItemView.mScroller.fullScroll(17);
            openedItemView = null;
        }
    }

    public void setListener(FriendItemViewListener listener) {
        this.mListener = listener;
    }

    private void adjustAcceptDeclineButtonsToScroll(int scrollX) {
        int maxScroll = this.mAcceptFriendButton.getMeasuredWidth() + this.mDeclineFriendButton.getMeasuredWidth();
        float ratio = 1.0f - (((float) scrollX) / ((float) maxScroll));
        this.mDeclineFriendButton.setPivotX(0.0f);
        this.mDeclineFriendButton.setPivotY((float) (this.mDeclineFriendButton.getMeasuredHeight() / 2));
        this.mAcceptFriendButton.setPivotX((float) this.mAcceptFriendButton.getMeasuredWidth());
        this.mAcceptFriendButton.setPivotY((float) (this.mAcceptFriendButton.getMeasuredHeight() / 2));
        this.mAcceptFriendButton.setTranslationX((float) (scrollX - maxScroll));
        this.mAcceptFriendButton.setRotationY(-90.0f * ratio);
        this.mDeclineFriendButton.setRotationY(90.0f * ratio);
        this.mDeclineGradient.setAlpha(1.0f - ((float) Math.cos((((double) ratio) * 3.141592653589793d) / 2.0d)));
        this.mAcceptGadient.setAlpha(1.0f - ((float) Math.cos((((double) ratio) * 3.141592653589793d) / 2.0d)));
    }

    private void adjustBeepButtonToScroll(int scrollX) {
        float ratio = 1.0f - (((float) scrollX) / ((float) this.mBeepButton.getMeasuredWidth()));
        this.mBeepButton.setPivotX(0.0f);
        this.mBeepButton.setPivotY((float) (this.mBeepButton.getMeasuredHeight() / 2));
        this.mBeepButton.setRotationY(90.0f * ratio);
    }

    public void setModel(FriendUserData model) {
        this.mModel = model;
        setFields();
    }

    private void setFields() {
        if (this.mModel.mIsPendingMy) {
            this.mAcceptFriendButton.setVisibility(0);
            this.mDeclineFriendButton.setVisibility(0);
            this.mBeepButton.setVisibility(8);
            this.mFriendActionButton.setVisibility(0);
            this.mFriendActionButton.setImageResource(C1283R.drawable.friend_request_button);
            this.mFriendActionButton.setOnClickListener(new C28996());
        } else {
            this.mBeepButton.setVisibility(this.mModel.mAllowBeepBeep ? 0 : 8);
            this.mAcceptFriendButton.setVisibility(8);
            this.mDeclineFriendButton.setVisibility(8);
            this.mFriendActionButton.setVisibility(8);
            this.mFriendActionButton.setOnClickListener(null);
        }
        if (!TextUtils.isEmpty(this.mModel.arrivedShareText)) {
            this.mFriendIndicator.setBackgroundColor(getResources().getColor(C1283R.color.BlueLagoon));
            this.mFriendIndicator.setVisibility(0);
        } else if (this.mModel.isOnline) {
            this.mFriendIndicator.setBackgroundColor(getResources().getColor(C1283R.color.BrightGreen));
            this.mFriendIndicator.setVisibility(0);
        } else {
            this.mFriendIndicator.setVisibility(8);
        }
        this.mUserNameLabel.setText(this.mModel.getName());
        if (TextUtils.isEmpty(this.mModel.mMeetingIdSharedWithMe)) {
            if (!TextUtils.isEmpty(this.mModel.mMeetingIdSharedByMe)) {
                this.mUserInfoLabel.setText(DriveToNativeManager.getInstance().getShareStatusTextNTV(this.mModel.getID()));
                this.mUserInfoLabel.setTextColor(getResources().getColor(C1283R.color.ActiveGreen));
                ((WazeTextView) this.mUserInfoLabel).setFont(5, 0);
            } else if (TextUtils.isEmpty(this.mModel.arrivedShareText)) {
                this.mUserInfoLabel.setText(this.mModel.statusLine);
                this.mUserInfoLabel.setTextColor(getResources().getColor(C1283R.color.Light));
                ((WazeTextView) this.mUserInfoLabel).setFont(4, 0);
            } else {
                this.mUserInfoLabel.setText(this.mModel.arrivedShareText);
                this.mUserInfoLabel.setTextColor(getResources().getColor(C1283R.color.Light));
                ((WazeTextView) this.mUserInfoLabel).setFont(4, 0);
            }
        } else if (this.mModel.mEtaSeconds >= 0) {
            TimeZone tz = Calendar.getInstance().getTimeZone();
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
            timeFormat.setTimeZone(tz);
            String etaString = timeFormat.format(new Date(System.currentTimeMillis() + ((long) (this.mModel.mEtaSeconds * 1000))));
            this.mUserInfoLabel.setText(String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FRIEND_SHARED_ETA_PS), new Object[]{etaString}));
            this.mUserInfoLabel.setTextColor(getResources().getColor(C1283R.color.ActiveGreen));
        } else {
            DriveToNativeManager.getInstance().getFriendsDriveData(new C29007(), this.mModel.mMeetingIdSharedWithMe);
        }
        this.mUserInfoLabel.setVisibility(TextUtils.isEmpty(this.mUserInfoLabel.getText()) ? 8 : 0);
        this.mProfileImage.setImageDrawable(null);
        this.mProfileImage.setVisibility(4);
        this.mInitialsLabel.setText(ShareUtility.getInitials(this.mModel.getName()));
        this.mImageReceived = false;
        postDelayed(new C29018(), 150);
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
        }
        if (TextUtils.isEmpty(this.mModel.getImage())) {
            showInitials();
        } else {
            this.mImageContainer = VolleyManager.getInstance().loadImageFromUrl(this.mModel.getImage(), new C29029(), this.mModel, PixelMeasure.dp(40), PixelMeasure.dp(40));
        }
    }

    public void setSeparatorVisibility(boolean visible) {
        this.mSeparatorView.setVisibility(visible ? 0 : 8);
    }

    private void showInitials() {
        this.mInitialsLabel.setAlpha(1.0f);
        this.mInitialsLabel.setVisibility(0);
    }

    private void hideInitials(long duration) {
        ViewPropertyAnimatorHelper.initAnimation(this.mInitialsLabel, duration).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mInitialsLabel));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getResources().getDisplayMetrics().widthPixels - this.mListener.getRequiredPadding();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, 1073741824);
        this.mScroller.getLayoutParams().width = width;
        this.mScroller.getLayoutParams().height = PixelMeasure.dimension(C1283R.dimen.friendItemHeight);
        this.mFriendItemContent.getLayoutParams().width = width;
        this.mFriendItemContent.getLayoutParams().height = PixelMeasure.dimension(C1283R.dimen.friendItemHeight);
        this.mScrollerContent.getLayoutParams().width = width + (PixelMeasure.dimension(C1283R.dimen.listItemButtonSize) * 2);
        this.mScrollerContent.getLayoutParams().height = PixelMeasure.dimension(C1283R.dimen.friendItemHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void animateSize(int targetSize) {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{this.mFriendItemContent.getMeasuredWidth(), targetSize});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                FriendItemView.this.mFriendItemContent.getLayoutParams().width = ((Integer) animation.getAnimatedValue()).intValue();
                FriendItemView.this.mFriendItemContent.setLayoutParams(FriendItemView.this.mFriendItemContent.getLayoutParams());
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        animator.start();
    }
}
