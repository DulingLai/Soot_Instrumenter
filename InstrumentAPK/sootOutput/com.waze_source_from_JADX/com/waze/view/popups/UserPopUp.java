package com.waze.view.popups;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.MoodManager;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultCode;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.map.CanvasFont;
import com.waze.map.MapViewWrapper;
import com.waze.messages.UserMessage;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.social.FriendsListData;
import com.waze.share.UserDetailsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.UserData;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import java.io.IOException;
import java.net.URL;

public class UserPopUp extends PopUp {
    private boolean bIsInitialized = false;
    private final ActivityBase mContext;
    private UserData mData;
    private DestructionRunnable mDestructionRunnable;
    private boolean mHiding;
    private boolean mIsShown = false;
    private final LayoutManager mLayoutManager;
    private MapViewWrapper mMapWrapper = null;
    private boolean mShowing;
    private int mX;
    private int mY;

    class C32501 extends AnimationEndListener {
        C32501() {
        }

        public void onAnimationEnd(Animation animation) {
            UserPopUp.this.hideNow();
        }
    }

    class C32512 implements VolleyManager$ImageRequestListener {
        C32512() {
        }

        public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
            ((ImageView) UserPopUp.this.findViewById(C1283R.id.userDetailsFrieldProfile)).setImageDrawable(new CircleShaderDrawable(bitmap, 0));
        }

        public void onImageLoadFailed(Object token, long duration) {
        }
    }

    class C32533 implements FriendsListListener {
        C32533() {
        }

        public void onComplete(FriendsListData data) {
            for (final FriendUserData friend : data.friends) {
                if (friend.mContactId == UserPopUp.this.mData.mContactId) {
                    View details = UserPopUp.this.findViewById(C1283R.id.userDetailsInfo);
                    details.setVisibility(0);
                    details.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(UserPopUp.this.mContext, UserDetailsActivity.class);
                            intent.putExtra(PublicMacros.FriendUserData, friend);
                            UserPopUp.this.mContext.startActivity(intent);
                            UserPopUp.this.hideNow();
                        }
                    });
                    return;
                }
            }
        }
    }

    class C32544 implements OnClickListener {
        C32544() {
        }

        public void onClick(View v) {
            UserMessage.startPrivate(UserPopUp.this.mContext, UserPopUp.this.mData);
        }
    }

    class C32565 implements OnClickListener {

        class C32551 implements IResultCode {
            C32551() {
            }

            public void onResult(int res) {
                if (res >= 0) {
                    UserPopUp.this.hide();
                }
            }
        }

        C32565() {
        }

        public void onClick(View v) {
            NativeManager.getInstance().SendBeepBeep(UserPopUp.this.mData.mLongitude, UserPopUp.this.mData.mLatitude, UserPopUp.this.mData.mAzimuth, UserPopUp.this.mData.mID, new C32551());
        }
    }

    private class DestructionRunnable implements Runnable {
        public boolean canceled;

        private DestructionRunnable() {
            this.canceled = false;
        }

        public void run() {
            if (!(this.canceled || UserPopUp.this.mMapWrapper == null)) {
                UserPopUp.this.bIsInitialized = false;
                UserPopUp.this.mMapWrapper.removeView(UserPopUp.this);
            }
            UserPopUp.this.mDestructionRunnable = null;
        }
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public UserPopUp(ActivityBase context, LayoutManager mgr) {
        super(context, mgr);
        this.mContext = context;
        this.mLayoutManager = mgr;
        init();
    }

    public void setUpButtonsTxt() {
        ((TextView) findViewById(C1283R.id.buttonLeft)).setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_USER_MESSAGE_BUTTON));
        ((TextView) findViewById(C1283R.id.buttonRight)).setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_USER_BEEP_BUTTON));
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.user_popup, this);
        setLayoutParams(new LayoutParams(-2, -2));
        setUpButtonsTxt();
    }

    private void hideNow() {
        this.mIsShown = false;
        this.mLayoutManager.popupsRemove(this);
        setVisibility(8);
        if (this.mDestructionRunnable == null) {
            this.mDestructionRunnable = new DestructionRunnable();
            postDelayed(this.mDestructionRunnable, 1000);
        }
        this.mHiding = false;
    }

    public boolean onBackPressed() {
        if (!this.mIsShown) {
            return false;
        }
        hide();
        return true;
    }

    public void hide() {
        if (this.mHiding) {
            hideNow();
        } else if (this.mIsShown) {
            this.mHiding = true;
            AnimationSet as = new AnimationSet(true);
            as.setDuration(250);
            as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            as.addAnimation(new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 0, (float) this.mX, 0, (float) this.mY));
            as.setInterpolator(new AnticipateInterpolator());
            startAnimation(as);
            as.setAnimationListener(new C32501());
        }
    }

    private void enableButtons(boolean enabled) {
        View left = findViewById(C1283R.id.buttonLeft);
        View right = findViewById(C1283R.id.buttonRight);
        float alpha = enabled ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        left.setEnabled(enabled);
        right.setEnabled(enabled);
        left.setAlpha(alpha);
        right.setAlpha(alpha);
    }

    private void setName(String name) {
        ((TextView) findViewById(C1283R.id.UserPopUpName)).setText(name);
    }

    public Object fetch(String address) throws IOException {
        return new URL(address).getContent();
    }

    public void fillPopUpData() {
        setName(this.mData.mNickName);
        ImageView icon = (ImageView) findViewById(C1283R.id.userDetailsImage);
        icon.setImageDrawable(MoodManager.getBigMoodDrawble(this.mContext, this.mData.mMood));
        icon.setVisibility(0);
        ImageView addon = (ImageView) findViewById(C1283R.id.userDetailsImageAddon);
        if (this.mData.mAddonName == null || this.mData.mAddonName.length() <= 0) {
            addon.setVisibility(8);
        } else {
            addon.setVisibility(0);
            addon.setImageDrawable(MoodManager.getBigMoodDrawble(this.mContext, this.mData.mAddonName));
        }
        if (this.mData.getImage() != null) {
            addon.setVisibility(8);
            VolleyManager.getInstance().loadImageFromUrl(this.mData.getImage(), new C32512());
            DriveToNativeManager.getInstance().getFriendsListData(new C32533());
        }
        TextView PtsLabel = (TextView) findViewById(C1283R.id.PtsLabel);
        String sData = this.mData.mPtsStr;
        if (this.mData.mRank == -1 || this.mData.mPtsStr == null) {
            sData = DisplayStrings.displayString(DisplayStrings.DS_RANK_AND_POINTS_NA);
        } else {
            sData = sData + " " + this.mData.mRankStr;
        }
        PtsLabel.setText(sData);
        TextView JoinedLabel = (TextView) findViewById(C1283R.id.JoinedLabel);
        if (this.mData.mJoinedStr == null || this.mData.mJoinedStr.isEmpty()) {
            JoinedLabel.setVisibility(8);
        } else {
            JoinedLabel.setVisibility(0);
            JoinedLabel.setText(this.mData.mJoinedStr);
        }
        ((TextView) findViewById(C1283R.id.SpeedLabel)).setText(this.mData.mSpeedStr);
        enableButtons(this.mData.mAllowPing);
    }

    public void show(UserData data, final int x, final int y) {
        if (this.mIsShown || this.mHiding) {
            if (data.getID() == this.mData.getID() && this.mHiding) {
                hideNow();
                this.mData = null;
                return;
            }
            this.mShowing = true;
            hideNow();
            this.mShowing = false;
        }
        this.mData = data;
        fillPopUpData();
        if (this.mDestructionRunnable != null) {
            this.mDestructionRunnable.canceled = true;
        }
        if (!this.bIsInitialized) {
            this.mMapWrapper = AppService.getActiveMapViewWrapper();
            if (this.mMapWrapper != null) {
                this.mMapWrapper.addView(this);
            }
            this.bIsInitialized = true;
        }
        setVisibility(0);
        findViewById(C1283R.id.buttonLeft).setOnClickListener(new C32544());
        findViewById(C1283R.id.buttonRight).setOnClickListener(new C32565());
        this.mIsShown = true;
        this.mLayoutManager.popupsAdd(this);
        NativeManager.getInstance().focusCanvasUser(getHeight() / 2);
        final View layout = findViewById(C1283R.id.UserPopUpMainLayout);
        layout.setOnClickListener(null);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                UserPopUp.this.updatePosition(x, y);
            }
        });
        AnimationSet as = new AnimationSet(true);
        as.setDuration(250);
        as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        as.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 0, (float) x, 0, (float) y));
        as.setInterpolator(new OvershootInterpolator());
        startAnimation(as);
    }

    private void updatePosition(int x, int y) {
        animate().translationX((float) (x - (getMeasuredWidth() / 2))).translationY((float) (y - getMeasuredHeight())).setDuration(0).start();
        this.mX = x;
        this.mY = y;
    }

    public void update(int x, int y) {
        if (this.mIsShown) {
            updatePosition(x, y);
        }
    }
}
