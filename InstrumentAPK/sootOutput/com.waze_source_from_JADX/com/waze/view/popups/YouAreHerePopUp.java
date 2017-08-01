package com.waze.view.popups;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.PublicMacros;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.CarGasTypeManager;
import com.waze.utils.CarGasTypeManager.GasTypeLoadedListener;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;
import com.waze.view.anim.HeightAnimation;
import com.waze.view.anim.HeightAnimation.HeightChangedListener;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.util.Locale;

public class YouAreHerePopUp extends FrameLayout implements GasTypeLoadedListener {
    private static final long AUTO_CLOSE_TIMEOUT = 8000;
    private static final int MAX_AMOUNT_OF_PROMOTION_SHOWN_COUNT = 2;
    private AddressItem mAddressItem;
    private Runnable mAutoCloseRunnable;
    private ImageView mDetailsCarTypeImage;
    private ImageView mDetailsChevron;
    private RelativeLayout mDetailsContainer;
    private ImageView mDetailsInfoButton;
    private boolean mFirstPositionUpdateCalled;
    private RelativeLayout mGasTypeHeaderContainer;
    private ImageView mHeaderCarTypeImage;
    private TextView mHeaderCarTypeLabel;
    private TextView mHeaderGasTypeLabel;
    private View mHeaderSeparatorView;
    private boolean mIsAnimatingShow;
    private boolean mIsFullyVisible;
    private boolean mIsMinimized;
    private boolean mIsShowing;
    private int mPendingX;
    private int mPendingY;
    private int mPositionX;
    private int mPositionY;
    private boolean mRevealAnimationStarted;
    private FrameLayout mSendLocationButton;
    private TextView mSendLocationLabel;
    private TextView mUserLocationLabel;
    private TextView mYouAreHereLabel;

    class C32591 implements Runnable {
        C32591() {
        }

        public void run() {
            YouAreHerePopUp.this.hide(true);
        }
    }

    class C32602 implements OnClickListener {
        C32602() {
        }

        public void onClick(View v) {
            if (YouAreHerePopUp.this.mIsMinimized) {
                String paramName = String.format(Locale.US, "%s|%s", new Object[]{"TYPE", "ACTION"});
                Locale locale = Locale.US;
                String str = "%s|%s";
                Object[] objArr = new Object[2];
                objArr[0] = YouAreHerePopUp.this.isFirstTime() ? AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_FIRST_TIME : AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_REGULAR;
                objArr[1] = AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_ACTION_YOU_ARE_HERE;
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TAPPED, paramName, String.format(locale, str, objArr));
                YouAreHerePopUp.this.setMaximizedMode(true);
            }
        }
    }

    class C32613 implements OnClickListener {
        C32613() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TAPPED, String.format(Locale.US, "%s|%s", new Object[]{"TYPE", "ACTION"}), String.format(Locale.US, "%s|%s", new Object[]{AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_EXPANDED, AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_ACTION_SEND_LOCATION}));
            ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareSelection, null, YouAreHerePopUp.this.mAddressItem, null);
            YouAreHerePopUp.this.hide(true);
        }
    }

    class C32624 implements OnClickListener {
        C32624() {
        }

        public void onClick(View v) {
            YouAreHerePopUp.this.hide(true);
            if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr() != null) {
                AppService.getMainActivity().getLayoutMgr().showCarGasSettingsPopup();
                String paramName = String.format(Locale.US, "%s|%s", new Object[]{"TYPE", "ACTION"});
                Locale locale = Locale.US;
                String str = "%s|%s";
                Object[] objArr = new Object[2];
                String str2 = (YouAreHerePopUp.this.isFirstTime() && YouAreHerePopUp.this.mIsMinimized) ? AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_FIRST_TIME : AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_EXPANDED;
                objArr[0] = str2;
                objArr[1] = AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_ACTION_CAR_TYPE;
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TAPPED, paramName, String.format(locale, str, objArr));
                if (YouAreHerePopUp.this.isFirstTime()) {
                    YouAreHerePopUp.this.unsetFirstTime();
                }
            }
        }
    }

    class C32635 implements OnClickListener {
        C32635() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, YouAreHerePopUp.this.mAddressItem);
            if (!(YouAreHerePopUp.this.mAddressItem.VanueID == null || YouAreHerePopUp.this.mAddressItem.VanueID.isEmpty())) {
                intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TAPPED, String.format(Locale.US, "%s|%s", new Object[]{"TYPE", "ACTION"}), String.format(Locale.US, "%s|%s", new Object[]{AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_EXPANDED, AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_ACTION_INFO}));
            AppService.getActiveActivity().startActivityForResult(intent, 1);
            YouAreHerePopUp.this.hide(true);
        }
    }

    class C32646 implements HeightChangedListener {
        C32646() {
        }

        public void onHeightChanged(int currentHeight) {
            YouAreHerePopUp.this.animateToPosition();
        }
    }

    class C32657 implements Runnable {
        C32657() {
        }

        public void run() {
            YouAreHerePopUp.this.animateToPosition();
        }
    }

    class C32668 implements Runnable {
        C32668() {
        }

        public void run() {
            YouAreHerePopUp.this.mIsShowing = false;
            YouAreHerePopUp.this.setVisibility(8);
        }
    }

    class C32679 implements Runnable {
        C32679() {
        }

        public void run() {
            YouAreHerePopUp.this.animateToPosition();
        }
    }

    public YouAreHerePopUp(Context context) {
        this(context, null);
    }

    public YouAreHerePopUp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YouAreHerePopUp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAutoCloseRunnable = new C32591();
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.you_are_here_bubble, null);
        this.mGasTypeHeaderContainer = (RelativeLayout) content.findViewById(C1283R.id.carTypeOptionsContainer);
        this.mHeaderCarTypeImage = (ImageView) content.findViewById(C1283R.id.imgHeaderCarType);
        this.mHeaderCarTypeLabel = (TextView) content.findViewById(C1283R.id.lblHeaderCarType);
        this.mHeaderGasTypeLabel = (TextView) content.findViewById(C1283R.id.lblHeaderGasType);
        this.mHeaderSeparatorView = content.findViewById(C1283R.id.carTypeOptionsSeparator);
        this.mYouAreHereLabel = (TextView) content.findViewById(C1283R.id.lblYouAreHere);
        this.mUserLocationLabel = (TextView) content.findViewById(C1283R.id.lblUserLocation);
        this.mDetailsChevron = (ImageView) content.findViewById(C1283R.id.imgDetailsChevron);
        this.mDetailsCarTypeImage = (ImageView) content.findViewById(C1283R.id.imgDetailsCarType);
        this.mDetailsInfoButton = (ImageView) content.findViewById(C1283R.id.imgInfoButton);
        this.mSendLocationButton = (FrameLayout) content.findViewById(C1283R.id.btnSendLocation);
        this.mSendLocationLabel = (TextView) content.findViewById(C1283R.id.lblSendLocation);
        this.mDetailsContainer = (RelativeLayout) content.findViewById(C1283R.id.detailsContainer);
        this.mDetailsContainer.setOnClickListener(new C32602());
        this.mSendLocationButton.setOnClickListener(new C32613());
        this.mGasTypeHeaderContainer.setOnClickListener(new C32624());
        this.mDetailsInfoButton.setOnClickListener(new C32635());
        addView(content);
    }

    public void show(int x, int y, int offsetY, AddressItem addressItem, int timeoutSec) {
        if (!this.mIsShowing || !CarGasTypeManager.getInstance().isDataLoaded()) {
            this.mFirstPositionUpdateCalled = false;
            this.mPositionX = -1;
            this.mPositionY = -1;
            this.mIsShowing = true;
            this.mIsAnimatingShow = true;
            this.mAddressItem = addressItem;
            this.mYouAreHereLabel.setText(String.format(Locale.US, "%s:", new Object[]{DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_MY_LOCATION_TITLE)}));
            this.mSendLocationLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_SEND_LOCATION));
            setScaleX(0.0f);
            setScaleY(0.0f);
            this.mPendingX = x;
            this.mPendingY = y;
            CarGasTypeManager.getInstance().reloadCarAndGasData(this);
        }
    }

    private boolean isFirstTime() {
        return ConfigManager.getInstance().getConfigValueBool(337);
    }

    private void updatePromotionCounter() {
        int promotionShownCount = ConfigManager.getInstance().getConfigValueInt(338);
        if (promotionShownCount < 2) {
            ConfigManager.getInstance().setConfigValueLong(338, (long) (promotionShownCount + 1));
        } else {
            unsetFirstTime();
        }
    }

    private void unsetFirstTime() {
        ConfigManager.getInstance().setConfigValueBool(337, false);
    }

    private void setMinimizedMode() {
        if (isFirstTime()) {
            updatePromotionCounter();
        }
        if (isFirstTime()) {
            this.mGasTypeHeaderContainer.setVisibility(0);
            this.mDetailsCarTypeImage.setVisibility(8);
            this.mHeaderSeparatorView.setVisibility(0);
        } else {
            this.mGasTypeHeaderContainer.setVisibility(8);
            this.mDetailsCarTypeImage.setVisibility(0);
            this.mHeaderSeparatorView.setVisibility(8);
        }
        this.mDetailsChevron.setVisibility(0);
        this.mDetailsInfoButton.setVisibility(8);
        this.mSendLocationButton.setVisibility(8);
        this.mIsMinimized = true;
    }

    private void setMaximizedMode(boolean animated) {
        if (!this.mIsAnimatingShow) {
            boolean headerWasGone;
            removeCallbacks(this.mAutoCloseRunnable);
            if (this.mGasTypeHeaderContainer.getVisibility() == 8) {
                headerWasGone = true;
            } else {
                headerWasGone = false;
            }
            this.mGasTypeHeaderContainer.setVisibility(0);
            this.mDetailsChevron.setVisibility(8);
            this.mDetailsCarTypeImage.setVisibility(8);
            this.mHeaderSeparatorView.setVisibility(0);
            this.mSendLocationButton.setVisibility(0);
            this.mDetailsInfoButton.setVisibility(0);
            if (animated) {
                this.mSendLocationButton.getLayoutParams().height = 0;
                this.mSendLocationButton.setLayoutParams(this.mSendLocationButton.getLayoutParams());
                HeightAnimation sendLocationHeightAnimation = new HeightAnimation(this.mSendLocationButton, 0, PixelMeasure.dp(72));
                sendLocationHeightAnimation.setHeightChangedListener(new C32646());
                sendLocationHeightAnimation.setDuration(300);
                sendLocationHeightAnimation.setInterpolator(EasingInterpolators.BOUNCE_OUT);
                this.mSendLocationButton.startAnimation(sendLocationHeightAnimation);
                if (headerWasGone) {
                    this.mGasTypeHeaderContainer.getLayoutParams().height = 0;
                    this.mGasTypeHeaderContainer.setLayoutParams(this.mGasTypeHeaderContainer.getLayoutParams());
                    HeightAnimation headerHeightAnimation = new HeightAnimation(this.mGasTypeHeaderContainer, 0, PixelMeasure.dp(72));
                    headerHeightAnimation.setDuration(300);
                    headerHeightAnimation.setInterpolator(EasingInterpolators.BOUNCE_OUT);
                    this.mGasTypeHeaderContainer.startAnimation(headerHeightAnimation);
                }
            }
            this.mIsMinimized = false;
            if (!animated) {
                post(new C32657());
            }
        }
    }

    public void hide(boolean withAnimation) {
        this.mIsShowing = false;
        if (this.mIsFullyVisible) {
            removeCallbacks(this.mAutoCloseRunnable);
            this.mIsFullyVisible = false;
            if (withAnimation) {
                setPivotX((float) (getMeasuredWidth() / 2));
                setPivotY((float) getMeasuredHeight());
                ViewPropertyAnimatorHelper.initAnimation(this, 300, EasingInterpolators.BOUNCE_IN).scaleX(0.0f).scaleY(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C32668()));
            } else {
                setVisibility(8);
                this.mIsShowing = false;
            }
            this.mRevealAnimationStarted = false;
        }
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public boolean isAnimatingShow() {
        return this.mIsAnimatingShow;
    }

    private void setFields() {
        String title;
        if (TextUtils.isEmpty(this.mAddressItem.getAddress())) {
            title = DisplayStrings.displayString(3333);
        } else {
            title = this.mAddressItem.getAddress();
        }
        this.mUserLocationLabel.setText(title);
        this.mDetailsCarTypeImage.setImageResource(CarGasTypeManager.getInstance().getSelecteVehicleResId());
        this.mHeaderCarTypeImage.setImageResource(CarGasTypeManager.getInstance().getSelecteVehicleResId());
        this.mHeaderGasTypeLabel.setText(CarGasTypeManager.getInstance().getSelectedGasDisplayString());
        this.mHeaderCarTypeLabel.setText(CarGasTypeManager.getInstance().getSelectedVehicleDisplayString());
    }

    public synchronized void updatePosition(int x, int y) {
        if (!(x == this.mPositionX && y == this.mPositionY) && this.mFirstPositionUpdateCalled) {
            this.mPositionX = x;
            this.mPositionY = y;
            animateToPosition();
        }
    }

    private void animateToPosition() {
        if (!this.mRevealAnimationStarted) {
            if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) {
                post(new C32679());
                return;
            }
            int centerY = this.mPositionY - getMeasuredHeight();
            setTranslationX((float) (this.mPositionX - (getMeasuredWidth() / 2)));
            setTranslationY((float) centerY);
            if (!this.mRevealAnimationStarted && getScaleX() == 0.0f && getScaleY() == 0.0f) {
                revealPopup();
            }
        }
    }

    private void revealPopup() {
        if (!this.mRevealAnimationStarted) {
            this.mRevealAnimationStarted = true;
            setPivotX((float) (getMeasuredWidth() / 2));
            setPivotY((float) getMeasuredHeight());
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_SHOWN, "TYPE", isFirstTime() ? AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_FIRST_TIME : AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_BUBBLE_TYPE_REGULAR);
            ViewPropertyAnimatorHelper.initAnimation(this, 300, EasingInterpolators.BOUNCE_OUT).scaleX(1.0f).scaleY(1.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new Runnable() {
                public void run() {
                    YouAreHerePopUp.this.mRevealAnimationStarted = false;
                    YouAreHerePopUp.this.mIsAnimatingShow = false;
                    YouAreHerePopUp.this.mIsFullyVisible = true;
                    YouAreHerePopUp.this.animateToPosition();
                    YouAreHerePopUp.this.postDelayed(YouAreHerePopUp.this.mAutoCloseRunnable, YouAreHerePopUp.AUTO_CLOSE_TIMEOUT);
                }
            }));
        }
    }

    public void onGasTypeLoaded() {
        post(new Runnable() {

            class C32581 implements Runnable {
                C32581() {
                }

                public void run() {
                    YouAreHerePopUp.this.mFirstPositionUpdateCalled = true;
                    YouAreHerePopUp.this.updatePosition(YouAreHerePopUp.this.mPendingX, YouAreHerePopUp.this.mPendingY);
                }
            }

            public void run() {
                YouAreHerePopUp.this.setVisibility(0);
                YouAreHerePopUp.this.setMinimizedMode();
                YouAreHerePopUp.this.setFields();
                YouAreHerePopUp.this.postDelayed(new C32581(), 10);
            }
        });
    }
}
