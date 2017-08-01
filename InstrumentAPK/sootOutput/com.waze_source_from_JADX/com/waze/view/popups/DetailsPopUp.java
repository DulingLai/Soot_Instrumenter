package com.waze.view.popups;

import android.animation.LayoutTransition;
import android.animation.LayoutTransition.TransitionListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.map.CanvasFont;
import com.waze.map.MapViewWrapper;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.MyWazeDataHandler;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.NavigateNativeManager;
import com.waze.navigate.PublicMacros;
import com.waze.reports.VenueData;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import com.waze.view.dialogs.ParkingPinsFeedbackDialog;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.text.WazeTextView;

public class DetailsPopUp extends RelativeLayout {
    private static final long GROW_ANIMATION_PHASE1_TIME = 150;
    private static final long GROW_ANIMATION_PHASE2_TIME = 200;
    public static final int TYPE_BONUS = 10;
    public static final int TYPE_HOME = 5;
    public static final int TYPE_MAP_DROP = 2;
    public static final int TYPE_MY_LOCATION = 1;
    public static final int TYPE_MY_PARKING = 7;
    public static final int TYPE_NEW_VENUE = 9;
    public static final int TYPE_ORIGINAL_DEST = 13;
    public static final int TYPE_PARKING_SPOT = 8;
    public static final int TYPE_SEARCH_RESULT = 12;
    public static final int TYPE_SHOW_ON_MAP = 3;
    public static final int TYPE_START_POINT = 4;
    public static final int TYPE_WORK = 6;
    private AutocloseRunnable autoClose;
    private boolean bIsInitialized;
    private DestructionRunnable mDestructionRunnable;
    private boolean mHiding = false;
    private boolean mIsShown = false;
    private int mLogoResource = -1;
    private int mOffsetY;
    private VenueData mParkingForVenue = null;
    private String mTitle;
    private int mType;
    private int mX;
    private int mY;

    class C31136 implements OnClickListener {
        C31136() {
        }

        public void onClick(View v) {
            DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
            DetailsPopUp.this.hideNow();
            ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareLocation, null, null, null);
        }
    }

    class C31157 implements OnClickListener {

        class C31141 implements Runnable {
            C31141() {
            }

            public void run() {
                AppService.getNativeManager().wazeUiDetailsPopupNextNTV();
            }
        }

        C31157() {
        }

        public void onClick(View v) {
            DetailsPopUp.this.sendClickStat("MORE");
            NativeManager.Post(new C31141());
        }
    }

    class C31188 implements MyWazeDataHandler {
        C31188() {
        }

        public void onMyWazeDataReceived(final MyWazeData data) {
            DetailsPopUp.this.post(new Runnable() {

                class C31161 implements VolleyManager$ImageRequestListener {
                    C31161() {
                    }

                    public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
                        ((ImageView) DetailsPopUp.this.findViewById(C1283R.id.riderProfileImage)).setImageDrawable(new CircleShaderDrawable(bitmap, 0));
                    }

                    public void onImageLoadFailed(Object token, long duration) {
                    }
                }

                public void run() {
                    if (data.mImageUrl != null && !data.mImageUrl.isEmpty()) {
                        VolleyManager.getInstance().loadImageFromUrl(data.mImageUrl, new C31161());
                    }
                }
            });
        }
    }

    private class AutocloseRunnable implements Runnable {
        public boolean canceled;

        private AutocloseRunnable() {
            this.canceled = false;
        }

        public void run() {
            if (!this.canceled) {
                DetailsPopUp.this.hide(null);
            }
            DetailsPopUp.this.mDestructionRunnable = null;
        }
    }

    private class DestructionRunnable implements Runnable {
        public boolean canceled;

        class C31201 implements Runnable {
            C31201() {
            }

            public void run() {
                AppService.getNativeManager().wazeUiDetailsPopupClosedNTV();
            }
        }

        private DestructionRunnable() {
            this.canceled = false;
        }

        public void run() {
            if (!this.canceled) {
                MapViewWrapper activeMapViewWrapper = AppService.getActiveMapViewWrapper();
                if (activeMapViewWrapper != null) {
                    activeMapViewWrapper.removeView(DetailsPopUp.this);
                    NativeManager.Post(new C31201());
                    DetailsPopUp.this.bIsInitialized = false;
                }
            }
            DetailsPopUp.this.mDestructionRunnable = null;
        }
    }

    private enum displayMode {
        SMALL_OPENABLE,
        SMALL_UNOPENABLE,
        SMALL_MORE,
        BIG
    }

    private String typeString(int type) {
        switch (type) {
            case 1:
                return "LOCATION";
            case 2:
                return "DROPPED_PIN";
            case 3:
                return "SHOW_NO_MAP";
            case 4:
                return "START_POINT";
            case 5:
                return "HOME";
            case 6:
                return "WORK";
            case 7:
                return "MY_PARKING";
            case 8:
                return "PARKING_SPOT";
            case 9:
                return AnalyticsEvents.ANALYTICS_EVENT_VALUE_NEW_VENUE;
            case 10:
                return AnalyticsEvents.ANALYTICS_EVENT_VALUE_BONUS;
            case 12:
                return "SEARCH_RESULT";
            case 13:
                return "ORIGINAL_DESTINATION";
            default:
                return "unknown";
        }
    }

    public DetailsPopUp(Context context) {
        super(context);
        init();
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public void hideNow() {
        setVisibility(8);
        setLayoutTransition(null);
        setupSmall(0);
        this.mHiding = false;
        this.mIsShown = false;
        if (this.mDestructionRunnable == null) {
            this.mDestructionRunnable = new DestructionRunnable();
            postDelayed(this.mDestructionRunnable, 10000);
        }
    }

    public void hide(final Runnable onClose) {
        if (this.mIsShown) {
            this.mHiding = true;
            this.mIsShown = false;
            AnimationSet as = new AnimationSet(true);
            as.setDuration(250);
            as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            as.addAnimation(new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 0, (float) this.mX, 0, (float) this.mY));
            as.setInterpolator(new AnticipateInterpolator());
            startAnimation(as);
            as.setAnimationListener(new AnimationEndListener() {
                public void onAnimationEnd(Animation animation) {
                    DetailsPopUp.this.hideNow();
                    if (onClose != null) {
                        onClose.run();
                    }
                }
            });
            return;
        }
        this.mHiding = false;
    }

    private void onMoreAction() {
        if (this.autoClose != null) {
            this.autoClose.canceled = true;
        }
        findViewById(C1283R.id.DetailsPopupTopSpace).setVisibility(0);
        findViewById(C1283R.id.DetailsPopupButtons).setVisibility(0);
        findViewById(C1283R.id.DetailsPopupButtonsSeparator).setVisibility(0);
        TextView infoView = (TextView) findViewById(C1283R.id.ExtraInfo);
        if (infoView.getText().length() > 0) {
            infoView.setVisibility(0);
        }
        final ViewGroup bubble = (ViewGroup) findViewById(C1283R.id.DetailsBubble);
        bubble.getLayoutTransition().setDuration(GROW_ANIMATION_PHASE1_TIME);
        bubble.getLayoutTransition().addTransitionListener(new TransitionListener() {
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            }

            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                View imageFrame = DetailsPopUp.this.findViewById(C1283R.id.DetailsPopupImageFrame);
                imageFrame.setVisibility(0);
                AnimationSet as = new AnimationSet(true);
                as.setDuration(200);
                as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                as.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                as.setInterpolator(new OvershootInterpolator());
                imageFrame.startAnimation(as);
                bubble.getLayoutTransition().removeTransitionListener(this);
            }
        });
        findViewById(C1283R.id.moreActionIcon).setVisibility(8);
        animateToCenter(C1283R.id.TitleText);
        animateToCenter(C1283R.id.MsgText);
        animateToCenter(C1283R.id.ExtraInfoLayout);
    }

    private void animateToCenter(int resourceId) {
        final View view = findViewById(resourceId);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (VERSION.SDK_INT >= 16) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (!(view instanceof TextView) || ((TextView) view).getLineCount() == 1) {
                    Animation anim = new TranslateAnimation(0.0f, (float) (((DetailsPopUp.this.findViewById(C1283R.id.TextLayout).getWidth() - PixelMeasure.dp(18)) - view.getWidth()) / 2), 0.0f, 0.0f);
                    anim.setFillAfter(true);
                    anim.setDuration(200);
                    view.startAnimation(anim);
                } else if (view instanceof TextView) {
                    ((TextView) view).setGravity(1);
                }
            }
        });
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.details_popup, this);
    }

    private void setTitleText(String title) {
        ((TextView) findViewById(C1283R.id.TitleText)).setText(title);
    }

    private void setMsgText(String msg) {
        TextView textView = (TextView) findViewById(C1283R.id.MsgText);
        textView.setText(msg);
        if (msg == null || msg.isEmpty()) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    private void setInfo(String info, boolean loader) {
        boolean showLayout;
        int i;
        TextView infoView = (TextView) findViewById(C1283R.id.ExtraInfo);
        ProgressAnimation infoLoader = (ProgressAnimation) findViewById(C1283R.id.ExtraInfoLoader);
        if ((info == null || info.isEmpty()) && !loader) {
            showLayout = false;
        } else {
            showLayout = true;
        }
        View findViewById = findViewById(C1283R.id.ExtraInfoLayout);
        if (showLayout) {
            i = 0;
        } else {
            i = 8;
        }
        findViewById.setVisibility(i);
        infoView.setVisibility(8);
        infoLoader.setVisibility(8);
        if (!(info == null || info.isEmpty())) {
            infoView.setText(info);
            infoView.setVisibility(0);
        }
        if (loader) {
            infoLoader.setVisibility(0);
            infoLoader.start();
            return;
        }
        infoLoader.stop();
    }

    private void updatePosition(int x, int y) {
        animate().translationX((float) (x - (getMeasuredWidth() / 2))).translationY((float) ((y - getMeasuredHeight()) + this.mOffsetY)).setDuration(0).start();
        this.mX = x;
        this.mY = this.mOffsetY + y;
    }

    public void update(int x, int y) {
        if (this.mIsShown) {
            updatePosition(x, y);
        }
    }

    public void show(int x, int y, int offsetY, String title, String msg, String info, boolean loader, int type, AddressItem pos, int timeoutSec, VenueData parkingForVenue, int walkingDistance) {
        if (!this.mHiding) {
            if (this.mIsShown) {
                hide(null);
                return;
            }
            offsetY += PixelMeasure.dp(48);
            if (this.mDestructionRunnable != null) {
                this.mDestructionRunnable.canceled = true;
            }
            ViewGroup oldParent = (ViewGroup) getParent();
            MapViewWrapper activeMapViewWrapper = AppService.getActiveMapViewWrapper();
            if (!(oldParent == null || oldParent.equals(activeMapViewWrapper))) {
                oldParent.removeView(this);
                activeMapViewWrapper.addView(this);
            }
            setVisibility(0);
            setTitleText(title);
            setMsgText(msg);
            setInfo(info, loader);
            this.mIsShown = true;
            this.mOffsetY = offsetY;
            this.mTitle = title;
            this.mParkingForVenue = parkingForVenue;
            ImageView ivMore = (ImageView) findViewById(C1283R.id.DetailsPopupTopSpace);
            View rightButton = findViewById(C1283R.id.buttonRight);
            View rightButtonImage = findViewById(C1283R.id.buttonRightImage);
            WazeTextView tvRightButtonText = (WazeTextView) findViewById(C1283R.id.buttonRightText);
            WazeTextView tvLeftButton = (WazeTextView) findViewById(C1283R.id.buttonLeft);
            final View view = rightButton;
            OnClickListener c31114 = new OnClickListener() {
                public void onClick(View v) {
                    view.performClick();
                }
            };
            tvRightButtonText.setOnClickListener(c31114);
            rightButtonImage.setOnClickListener(c31114);
            rightButtonImage.setVisibility(8);
            this.mLogoResource = -1;
            final int i = type;
            final int i2 = walkingDistance;
            final AddressItem addressItem = pos;
            ivMore.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    DetailsPopUp.this.sendClickStat("MORE");
                    NavigateNativeManager.instance().locationPickerCanvasUnset();
                    Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
                    if (i == 8) {
                        intent.putExtra(PublicMacros.PREVIEW_PARKING_MODE, true);
                        intent.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, DetailsPopUp.this.mParkingForVenue);
                        intent.putExtra(PublicMacros.PREVIEW_PARKING_DISTANCE, i2);
                        intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
                    }
                    intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
                    intent.putExtra("logo", DetailsPopUp.this.mLogoResource);
                    AppService.getActiveActivity().startActivity(intent);
                    DetailsPopUp.this.hide(null);
                }
            });
            tvLeftButton.setVisibility(8);
            displayMode mode = displayMode.SMALL_OPENABLE;
            if (type == 1) {
                setLogoBitmap(C1283R.drawable.rs_profilepic_placeholder);
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_MY_LOCATION_SEND_LOCATION_BUTTON));
                rightButton.setOnClickListener(new C31136());
                ivMore.setOnClickListener(new C31157());
                MyWazeNativeManager.getInstance().getMyWazeData(new C31188());
            } else if (type == 2 || type == 3 || type == 9) {
                mode = displayMode.BIG;
                setLogoBitmap(C1283R.drawable.default_location_illustration);
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_DEFAULT_GO_BUTTON));
                r1 = pos;
                rightButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
                        DetailsPopUp.this.hideNow();
                        NavigateNativeManager.instance().locationPickerCanvasUnset();
                        DriveToNativeManager.getInstance().navigate(r1, null);
                    }
                });
                rightButtonImage.setVisibility(0);
                tvLeftButton.setVisibility(0);
                tvLeftButton.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_DEFAULT_SEND_BUTTON));
                r1 = pos;
                tvLeftButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_SECONDARY);
                        DetailsPopUp.this.hideNow();
                        NavigateNativeManager.instance().locationPickerCanvasUnset();
                        ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareSelection, null, r1, null);
                    }
                });
            } else if (type == 4) {
                setLogoBitmap(C1283R.drawable.default_location_illustration);
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_START_POINT_SEARCH_BUTTON));
                rightButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
                        DetailsPopUp.this.hideNow();
                        AppService.getMainActivity().onSearchRequested();
                    }
                });
                tvLeftButton.setVisibility(0);
                tvLeftButton.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_START_POINT_CANCEL_BUTTON));
                tvLeftButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_SECONDARY);
                        DetailsPopUp.this.hideNow();
                        DriveToNativeManager.getInstance().removeStartPoint();
                    }
                });
            } else if (type == 6) {
                mode = displayMode.BIG;
                setLogoBitmap(C1283R.drawable.work_illustration);
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_DEFAULT_GO_BUTTON));
                rightButtonImage.setVisibility(0);
                r1 = rightButton;
                r2 = tvLeftButton;
                DriveToNativeManager.getInstance().getWork(new DriveToGetAddressItemArrayCallback() {
                    public void getAddressItemArrayCallback(final AddressItem[] ai) {
                        if (ai != null && ai.length > 0) {
                            r1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
                                    DetailsPopUp.this.hideNow();
                                    DriveToNativeManager.getInstance().navigate(ai[0], null);
                                }
                            });
                            r2.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_SECONDARY);
                                    DetailsPopUp.this.hideNow();
                                    ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareLocation, null, ai[0], null);
                                }
                            });
                        }
                    }
                });
                tvLeftButton.setVisibility(0);
                tvLeftButton.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_DEFAULT_SEND_BUTTON));
            } else if (type == 5) {
                mode = displayMode.BIG;
                setLogoBitmap(C1283R.drawable.home_illustration);
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_DEFAULT_GO_BUTTON));
                rightButtonImage.setVisibility(0);
                r1 = rightButton;
                r2 = tvLeftButton;
                DriveToNativeManager.getInstance().getHome(new DriveToGetAddressItemArrayCallback() {
                    public void getAddressItemArrayCallback(final AddressItem[] ai) {
                        if (ai != null && ai.length > 0) {
                            r1.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
                                    DetailsPopUp.this.hideNow();
                                    DriveToNativeManager.getInstance().navigate(ai[0], null);
                                }
                            });
                            r2.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_SECONDARY);
                                    DetailsPopUp.this.hideNow();
                                    ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareLocation, null, ai[0], null);
                                }
                            });
                        }
                    }
                });
                tvLeftButton.setVisibility(0);
                tvLeftButton.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_DEFAULT_SEND_BUTTON));
            } else if (type == 7) {
                setLogoBitmap(C1283R.drawable.parked_here_illustration);
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_PARKED_SEND_LOCATION_BUTTON));
                r1 = pos;
                rightButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
                        DetailsPopUp.this.hideNow();
                        ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareLocation, null, r1, null);
                    }
                });
                if (ConfigValues.getBoolValue(236) && ParkingPinsFeedbackDialog.shouldShowParkingPinsFeedback()) {
                    if (!title.equals(ParkingPinsFeedbackDialog.AVAILABLE_TAG)) {
                        if (!title.equals(ParkingPinsFeedbackDialog.GPS_TAG)) {
                            tvLeftButton.setVisibility(0);
                            tvLeftButton.setText(DisplayStrings.displayString(DisplayStrings.DS_PARKING_PINS_FEEDBACK_POPUP_BUTTON));
                            tvLeftButton.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    DetailsPopUp.this.hideNow();
                                    new ParkingPinsFeedbackDialog().show();
                                }
                            });
                        }
                    }
                    hideNow();
                    new ParkingPinsFeedbackDialog().show();
                }
            } else if (type == 8) {
                mode = displayMode.BIG;
                setLogoBitmap(C1283R.drawable.parking_illustration);
                tvLeftButton.setVisibility(0);
                tvLeftButton.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_CAN_PARK_SEND_BUTTON));
                r1 = pos;
                tvLeftButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_SECONDARY);
                        DetailsPopUp.this.hideNow();
                        ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareLocation, null, r1, null);
                    }
                });
                tvRightButtonText.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_POPUP_CAN_PARK_PARK_BUTTON));
                final String str = title;
                final AddressItem addressItem2 = pos;
                rightButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DetailsPopUp.this.sendClickStat(AnalyticsEvents.ANALYTICS_EVENT_VALUE_PRIMARY);
                        DetailsPopUp.this.hideNow();
                        VenueData parkingVenue = new VenueData();
                        parkingVenue.name = str;
                        parkingVenue.longitude = addressItem2.getLocationX().intValue();
                        parkingVenue.latitude = addressItem2.getLocationY().intValue();
                        parkingVenue.id = addressItem2.VanueID;
                        NavigateNativeManager.instance().navigateToParking(parkingVenue, DetailsPopUp.this.mParkingForVenue);
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_TYPE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "PARKING");
                    }
                });
            } else if (type == 10) {
                mode = displayMode.SMALL_UNOPENABLE;
            } else if (type == 12) {
                mode = displayMode.SMALL_MORE;
            } else if (type == 13) {
                mode = displayMode.SMALL_UNOPENABLE;
            } else {
                Logger.e("Details popup called for unknown bubble type - " + type);
                hideNow();
                return;
            }
            TextView tvText = (TextView) findViewById(C1283R.id.TitleText);
            TextView tvMsg = (TextView) findViewById(C1283R.id.MsgText);
            View vExtra = findViewById(C1283R.id.ExtraInfoLayout);
            if (mode == displayMode.BIG) {
                setLayoutTransition(null);
                findViewById(C1283R.id.DetailsPopupTopSpace).setVisibility(0);
                findViewById(C1283R.id.moreActionIcon).setVisibility(8);
                findViewById(C1283R.id.DetailsPopupButtons).setVisibility(0);
                findViewById(C1283R.id.DetailsPopupButtonsSeparator).setVisibility(0);
                findViewById(C1283R.id.DetailsPopupImageFrame).setVisibility(0);
                ((LayoutParams) tvText.getLayoutParams()).gravity = 17;
                ((LayoutParams) tvMsg.getLayoutParams()).gravity = 17;
                ((LayoutParams) vExtra.getLayoutParams()).gravity = 17;
                tvText.setGravity(17);
                tvText.setAnimation(null);
                tvMsg.setGravity(17);
                tvMsg.setAnimation(null);
                vExtra.setAnimation(null);
                findViewById(C1283R.id.DetailsBubble).setOnClickListener(null);
                findViewById(C1283R.id.ExtraInfo).setVisibility(0);
            } else if (mode == displayMode.SMALL_OPENABLE) {
                setupSmall(timeoutSec);
            } else if (mode == displayMode.SMALL_MORE) {
                findViewById(C1283R.id.DetailsBubble).setOnClickListener(null);
                View more = findViewById(C1283R.id.moreActionIcon);
                more.setVisibility(0);
                final ImageView imageView = ivMore;
                more.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        imageView.performClick();
                    }
                });
            } else {
                findViewById(C1283R.id.DetailsBubble).setOnClickListener(null);
                findViewById(C1283R.id.moreActionIcon).setVisibility(8);
            }
            this.mType = type;
            if (this.bIsInitialized) {
                if (this.mType != 1) {
                    NativeManager.getInstance().focusCanvas(getHeight() / 2);
                }
            } else if (activeMapViewWrapper != null && getParent() == null) {
                ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(PixelMeasure.dp(309), PixelMeasure.dp(280));
                setPadding(0, 0, 0, PixelMeasure.dp(20));
                setClipChildren(false);
                setClipToPadding(false);
                setGravity(81);
                activeMapViewWrapper.addView(this, layoutParams);
                i = x;
                i2 = y;
                getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        DetailsPopUp.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        DetailsPopUp.this.updatePosition(i, i2);
                        if (DetailsPopUp.this.mType != 1) {
                            NativeManager.getInstance().focusCanvas(DetailsPopUp.this.getHeight() / 2);
                        }
                    }
                });
                this.bIsInitialized = true;
            }
            AnimationSet as = new AnimationSet(true);
            as.setDuration(250);
            as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            as.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 0, (float) x, 0, (float) y));
            as.setInterpolator(new OvershootInterpolator());
            startAnimation(as);
            updatePosition(x, y);
        }
    }

    private void sendClickStat(String action) {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PIN_POPUP_CLICKED).addParam("TYPE", typeString(this.mType)).addParam("ACTION", action).send();
    }

    private void setLogoBitmap(int resource) {
        this.mLogoResource = resource;
        ((ImageView) findViewById(C1283R.id.riderProfileImage)).setImageDrawable(new CircleShaderDrawable(BitmapFactory.decodeResource(getResources(), this.mLogoResource), 0));
    }

    public void updateContent(int type, String title, String msg) {
        if (this.mType == 1 && type == this.mType) {
            setTitleText(title);
            setMsgText(msg);
        }
    }

    public void updateDetailsPopupInfo(int type, String moreInfo, boolean loader) {
        setInfo(moreInfo, loader);
    }

    private void setupSmall(int timeoutSec) {
        TextView tvText = (TextView) findViewById(C1283R.id.TitleText);
        TextView tvMsg = (TextView) findViewById(C1283R.id.MsgText);
        View vExtra = findViewById(C1283R.id.ExtraInfoLayout);
        findViewById(C1283R.id.DetailsPopupTopSpace).setVisibility(8);
        findViewById(C1283R.id.moreActionIcon).setVisibility(0);
        findViewById(C1283R.id.DetailsPopupImageFrame).setVisibility(8);
        findViewById(C1283R.id.DetailsPopupTopSpace).setVisibility(8);
        findViewById(C1283R.id.DetailsPopupButtons).setVisibility(8);
        findViewById(C1283R.id.DetailsPopupButtonsSeparator).setVisibility(8);
        ((LayoutParams) tvText.getLayoutParams()).gravity = 3;
        ((LayoutParams) tvMsg.getLayoutParams()).gravity = 3;
        ((LayoutParams) vExtra.getLayoutParams()).gravity = 3;
        tvText.setGravity(3);
        tvText.setAnimation(null);
        tvMsg.setGravity(3);
        tvMsg.setAnimation(null);
        vExtra.setAnimation(null);
        this.autoClose = new AutocloseRunnable();
        if (timeoutSec > 0) {
            AppService.getActiveActivity().postDelayed(this.autoClose, (long) (timeoutSec * 1000));
        }
        findViewById(C1283R.id.DetailsBubble).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DetailsPopUp.this.sendClickStat("MORE");
                DetailsPopUp.this.onMoreAction();
                DetailsPopUp.this.findViewById(C1283R.id.DetailsBubble).setOnClickListener(null);
            }
        });
        findViewById(C1283R.id.moreActionIcon).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DetailsPopUp.this.onMoreAction();
            }
        });
    }
}
