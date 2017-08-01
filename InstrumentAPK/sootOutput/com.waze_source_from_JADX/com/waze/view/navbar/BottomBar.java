package com.waze.view.navbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.DriveToNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.bottom.BottomNotification;
import com.waze.view.bottom.BottomNotificationIcon;
import java.util.ArrayList;

public class BottomBar extends FrameLayout {
    private static final String UNITS_SPACE = " ";
    private String LOG_TAG = "BottomBar";
    public ImageView arrowView;
    private boolean bIsShown = false;
    private boolean bIsVisible = false;
    private TextView distanceView;
    private TextView etaView;
    private boolean mCarpoolEnabled = false;
    private ArrayList<Runnable> mDelayedNotifications = new ArrayList(2);
    private boolean mHasTimeToPark;
    BottomNotificationIcon mIconLandscape;
    BottomNotificationIcon mIconPortrait;
    private boolean mIsNotifAnimating;
    private boolean mIsOpened;
    private View mNotif;
    private TextView mNotifBody;
    private TextView mNotifTitle;
    private ArrayList<Runnable> mOnResume = new ArrayList(2);
    private boolean mPaused = false;
    private boolean mStopNotifications;
    private ViewGroup mTimeToParkContainer;
    private TextView mTimeToParkLabel;
    private boolean mTimeToParkStatSent = false;
    private boolean nightMode = false;
    private boolean parkingWalkDisplayed = false;
    private int refCount;
    private View rootView;
    private TextView staticMessageText;
    private View staticMessageView;
    private TextView timeToParkingText;
    private TextView timeView;

    class C30181 implements AnimationListener {
        C30181() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            BottomBar.this.rootView.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class C30269 implements Runnable {
        C30269() {
        }

        public void run() {
            BottomBar.this.closeNotifNow();
        }
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C1283R.layout.bottom_bar, this);
        this.rootView = findViewById(C1283R.id.bottomBarRoot);
        this.etaView = (TextView) findViewById(C1283R.id.bottomBarEta);
        this.timeView = (TextView) findViewById(C1283R.id.bottomBarTimeToDest);
        this.distanceView = (TextView) findViewById(C1283R.id.bottomBarDistanceToDest);
        this.arrowView = (ImageView) findViewById(C1283R.id.bottomBarArrow);
        this.staticMessageView = findViewById(C1283R.id.bottomBarStaticMessage);
        this.staticMessageText = (TextView) findViewById(C1283R.id.bottomBarStaticMessageText);
        this.timeToParkingText = (TextView) findViewById(C1283R.id.bottomBarWalkTimeToParkingText);
        this.mTimeToParkContainer = (ViewGroup) findViewById(C1283R.id.timeToParkContainer);
        this.mTimeToParkLabel = (TextView) findViewById(C1283R.id.lblTimeToPark);
        if (!isInEditMode()) {
            this.etaView.setVisibility(4);
            this.timeView.setVisibility(4);
            this.distanceView.setVisibility(4);
            this.arrowView.setVisibility(4);
        }
        this.mNotif = findViewById(C1283R.id.bottomBarNotif);
        this.mNotifTitle = (TextView) findViewById(C1283R.id.bottomBarNotifTitle);
        this.mNotifBody = (TextView) findViewById(C1283R.id.bottomBarNotifBody);
        this.mIsNotifAnimating = false;
    }

    public void initAsMain(BottomNotificationIcon iconLayoutPortrait) {
        BottomNotification.getInstance().setBottomBar(this);
        this.mIconPortrait = iconLayoutPortrait;
        this.mIconLandscape = (BottomNotificationIcon) findViewById(C1283R.id.mainBottomBarNotificationIconLandscape);
        this.staticMessageView.setVisibility(0);
        this.staticMessageText.setText("");
        this.timeToParkingText.setVisibility(8);
    }

    public void setParkingTime(int minutes) {
        if (minutes > 0) {
            this.staticMessageView.setVisibility(0);
            this.timeToParkingText.setVisibility(0);
            this.staticMessageText.setText(DisplayStrings.displayString(DisplayStrings.DS_WALK_TO_CAR_SUBTITLE));
            this.timeToParkingText.setText(DisplayStrings.displayStringF(DisplayStrings.DS_WALK_TO_CAR_MINUTES_PD, Integer.valueOf(minutes)));
            this.parkingWalkDisplayed = true;
        } else if (this.parkingWalkDisplayed) {
            this.timeToParkingText.setVisibility(8);
            this.parkingWalkDisplayed = false;
            setShowDefaultMessage(true);
        }
    }

    public void show() {
        boolean navigating = NativeManager.getInstance().isNavigatingNTV();
        Logger.d_(this.LOG_TAG, "Show request. Visible: " + isVisible() + ". Navigating: " + navigating);
        if (!isVisible() && navigating) {
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(300);
            this.rootView.startAnimation(anim);
            this.rootView.setVisibility(0);
            this.staticMessageView.setVisibility(8);
            this.bIsShown = true;
            this.bIsVisible = true;
        }
    }

    public boolean isVisible() {
        return this.bIsVisible;
    }

    public void setVisible(boolean IsVisible) {
        int i;
        int i2 = 8;
        this.bIsVisible = IsVisible;
        View view = this.rootView;
        if (IsVisible) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
        View view2 = this.staticMessageView;
        if (!IsVisible) {
            i2 = 0;
        }
        view2.setVisibility(i2);
    }

    public void hide(boolean animated) {
        Logger.d_(this.LOG_TAG, "Hide request. Visible: " + isVisible());
        if (this.bIsVisible) {
            this.bIsVisible = false;
            if (animated) {
                Animation anim = new AlphaAnimation(1.0f, 0.0f);
                anim.setDuration(300);
                this.rootView.startAnimation(anim);
                anim.setAnimationListener(new C30181());
            } else {
                this.rootView.setVisibility(8);
            }
            this.staticMessageView.setVisibility(0);
            this.staticMessageText.setText(NativeManager.getInstance().getTimeOfDayGreetingNTV());
        }
    }

    public boolean unhide() {
        boolean navigating = NativeManager.getInstance().isNavigatingNTV();
        Logger.d_(this.LOG_TAG, "Unhide request. Visible: " + isVisible() + ". Navigating: " + navigating);
        if (this.bIsVisible || !navigating || !this.bIsShown) {
            return false;
        }
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(250);
        this.rootView.startAnimation(anim);
        this.rootView.setVisibility(0);
        this.staticMessageView.setVisibility(8);
        this.bIsVisible = true;
        return true;
    }

    public void setToShown() {
        this.bIsShown = true;
    }

    public void setMode(boolean nightMode) {
        if (this.nightMode != nightMode) {
            this.nightMode = nightMode;
        }
    }

    public void onOrientationChanged(int orientation) {
        CharSequence etaStr = this.etaView.getText();
        int etaVisibility = this.etaView.getVisibility();
        float etaTextSize = this.etaView.getTextSize();
        CharSequence timeStr = this.timeView.getText();
        int timeVisibility = this.timeView.getVisibility();
        CharSequence distanceStr = this.distanceView.getText();
        int distanceVisibility = this.distanceView.getVisibility();
        Drawable arrowDrawable = this.arrowView.getDrawable();
        int arrowVisibility = this.arrowView.getVisibility();
        removeAllViews();
        init(getContext());
        this.etaView.setText(etaStr);
        this.etaView.setVisibility(etaVisibility);
        this.etaView.setTextSize(0, etaTextSize);
        this.timeView.setText(timeStr);
        this.timeView.setVisibility(timeVisibility);
        this.distanceView.setText(distanceStr);
        this.distanceView.setVisibility(distanceVisibility);
        this.arrowView.setImageDrawable(arrowDrawable);
        this.arrowView.setVisibility(arrowVisibility);
        if (!this.mCarpoolEnabled) {
            if (orientation == 2) {
                this.arrowView.setVisibility(8);
            } else {
                this.arrowView.setVisibility(0);
            }
        }
        setTimeToPark();
        if (this.mHasTimeToPark && this.mIsOpened) {
            this.mTimeToParkContainer.setVisibility(0);
        } else {
            this.mTimeToParkContainer.setVisibility(8);
        }
        this.mIconLandscape = (BottomNotificationIcon) findViewById(C1283R.id.mainBottomBarNotificationIconLandscape);
    }

    public void setDistance(String dist, String unit) {
        if (dist == null) {
            this.distanceView.setVisibility(4);
            return;
        }
        this.distanceView.setVisibility(0);
        this.distanceView.setText(dist + UNITS_SPACE + unit);
    }

    public void setTime(String timeStr, String timeUnits) {
        if (timeStr == null || "----".equals(timeStr)) {
            this.timeView.setVisibility(4);
            return;
        }
        this.timeView.setVisibility(0);
        this.timeView.setText(timeStr + UNITS_SPACE + timeUnits);
    }

    private void setTimeToPark() {
        String timeToParkStr = DriveToNativeManager.getInstance().getTimeToParkStrNTV();
        this.mHasTimeToPark = !TextUtils.isEmpty(timeToParkStr);
        if (this.mHasTimeToPark) {
            this.mTimeToParkLabel.setText(timeToParkStr);
            if (!this.mTimeToParkStatSent) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ETA_SHOWN_TIME_TO_PARK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_TIME_TO_PARK, timeToParkStr).send();
                this.mTimeToParkStatSent = true;
                return;
            }
            return;
        }
        this.mTimeToParkStatSent = false;
    }

    public void setOffline(boolean bOffline) {
        if (bOffline) {
            this.etaView.setTextSize(0, (float) AppService.getAppResources().getDimensionPixelSize(C1283R.dimen.etaBoxEtaSubTextSize));
            this.etaView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ETA_OFFLINE));
            this.etaView.setVisibility(0);
        }
    }

    public void setETA(String eta) {
        if (eta == null) {
            this.etaView.setVisibility(4);
            return;
        }
        float etaBoxEtaTextSize = (float) AppService.getAppResources().getDimensionPixelSize(C1283R.dimen.etaBoxEtaTextSize);
        float etaBoxEtaAmPmTextSize = (float) AppService.getAppResources().getDimensionPixelSize(C1283R.dimen.etaBoxEtaSubTextSize);
        if ("--:--".equals(eta)) {
            this.etaView.setTextSize(0, etaBoxEtaAmPmTextSize);
            this.etaView.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_ETA_OFFLINE));
            this.etaView.setVisibility(0);
            return;
        }
        if (eta.contains(UNITS_SPACE)) {
            String[] strs = eta.split(UNITS_SPACE);
            new SpannableString(strs[0]).setSpan(new AbsoluteSizeSpan((int) etaBoxEtaTextSize), 0, strs[0].length(), 18);
            new SpannableString(strs[1]).setSpan(new AbsoluteSizeSpan((int) etaBoxEtaAmPmTextSize), 0, strs[1].length(), 18);
            this.etaView.setText(TextUtils.concat(new CharSequence[]{time, UNITS_SPACE, ampm}));
        } else {
            this.etaView.setTextSize(0, etaBoxEtaTextSize);
            this.etaView.setText(eta);
        }
        this.etaView.setVisibility(0);
        setTimeToPark();
        if (this.mIsOpened && this.mHasTimeToPark && this.mTimeToParkContainer.getVisibility() == 8) {
            this.mTimeToParkContainer.setVisibility(0);
            this.mTimeToParkContainer.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mTimeToParkContainer).alpha(1.0f).setListener(null);
        }
    }

    private void _append_textsize_span(SpannableStringBuilder builder, String str, float factor) {
        builder.append(str);
        builder.setSpan(new RelativeSizeSpan(factor), builder.length() - str.length(), builder.length(), 33);
    }

    public void setShortMessage(final String message, final int timeout) {
        if (this.mPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.setShortMessage(message, timeout);
                }
            });
            return;
        }
        this.mNotifTitle.setVisibility(8);
        this.mNotifBody.setVisibility(0);
        this.mNotifBody.setText(message);
        openNotif(timeout, 0);
    }

    public void setNearByMessage(String message, String messageBody, int type, int timeout, boolean isRandomUser, boolean isFoursquareEnabled, boolean isClosureEnabled) {
        final String str;
        final String str2;
        final int i;
        final int i2;
        final boolean z;
        final boolean z2;
        final boolean z3;
        if (this.mPaused) {
            str = message;
            str2 = messageBody;
            i = type;
            i2 = timeout;
            z = isRandomUser;
            z2 = isFoursquareEnabled;
            z3 = isClosureEnabled;
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.setNearByMessage(str, str2, i, i2, z, z2, z3);
                }
            });
        } else if (this.refCount > 0) {
            str = message;
            str2 = messageBody;
            i = type;
            i2 = timeout;
            z = isRandomUser;
            z2 = isFoursquareEnabled;
            z3 = isClosureEnabled;
            this.mDelayedNotifications.add(new Runnable() {
                public void run() {
                    BottomBar.this.setNearByMessage(str, str2, i, i2, z, z2, z3);
                }
            });
        } else {
            if (message == null) {
                this.mNotifTitle.setVisibility(8);
            } else {
                this.mNotifTitle.setVisibility(0);
                this.mNotifTitle.setText(message);
            }
            if (messageBody == null) {
                this.mNotifBody.setVisibility(8);
            } else {
                this.mNotifBody.setVisibility(0);
                this.mNotifBody.setText(messageBody);
            }
            openNotif(timeout, type);
        }
    }

    public boolean canHideNotif() {
        return (this.mNotif == null || this.mNotif.getVisibility() == 8) ? false : true;
    }

    public void setShortMessageIcon(final String message, final String icon, final int timeout) {
        if (this.mPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.setShortMessageIcon(message, icon, timeout);
                }
            });
            return;
        }
        this.mNotifTitle.setVisibility(8);
        this.mNotifBody.setVisibility(0);
        this.mNotifBody.setText(message);
        openNotif(timeout, 0);
    }

    public void setLongMessageIcon(String title, String message, String icon, int timeout) {
        if (this.mPaused) {
            final String str = title;
            final String str2 = message;
            final String str3 = icon;
            final int i = timeout;
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.setLongMessageIcon(str, str2, str3, i);
                }
            });
            return;
        }
        this.mNotifTitle.setVisibility(0);
        this.mNotifTitle.setText(title);
        this.mNotifBody.setVisibility(0);
        this.mNotifBody.setText(message);
        openNotif(timeout, 0);
    }

    public void setLongMessagePoints(String title, String message, int points, int timeout) {
        final String str;
        final String str2;
        final int i;
        final int i2;
        if (this.mPaused) {
            str = title;
            str2 = message;
            i = points;
            i2 = timeout;
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.setLongMessagePoints(str, str2, i, i2);
                }
            });
        } else if (this.refCount > 0) {
            str = title;
            str2 = message;
            i = points;
            i2 = timeout;
            this.mDelayedNotifications.add(new Runnable() {
                public void run() {
                    BottomBar.this.setLongMessagePoints(str, str2, i, i2);
                }
            });
        } else {
            this.mNotifTitle.setVisibility(0);
            this.mNotifTitle.setText(title);
            this.mNotifBody.setVisibility(0);
            this.mNotifBody.setText(message);
            BottomNotificationIcon iconLayout = getIconLayout();
            if (iconLayout != null) {
                iconLayout.setImageResourcesAndPoints(getContext(), C1283R.drawable.points_banner_points_green_polygon, points);
            }
            openNotif(timeout, 3);
        }
    }

    private BottomNotificationIcon getIconLayout() {
        BottomNotificationIcon iconLayout = this.mIconLandscape;
        if (iconLayout == null) {
            return this.mIconPortrait;
        }
        this.mIconPortrait.setVisibility(8);
        return iconLayout;
    }

    public void hideNotif(boolean animated) {
        if (this.mPaused) {
            this.mOnResume.add(new C30269());
        } else if (this.mNotif != null && this.mNotif.getVisibility() != 8) {
            closeNotifNow();
        }
    }

    private int getIconResId(String icon) {
        return getResources().getIdentifier("drawable/" + icon.replace("-", "_").replace("+", "_").toLowerCase(), null, getContext().getPackageName());
    }

    private void openNotif(int timeout, int type) {
        if (!this.mStopNotifications) {
            if (this.mIsNotifAnimating) {
                Animation a = this.mNotif.getAnimation();
                if (a != null) {
                    a.setAnimationListener(null);
                }
                this.mNotif.clearAnimation();
                this.mNotif.setVisibility(8);
                this.mIsNotifAnimating = false;
            }
            if (!(this.mNotif == null || this.mNotif.getVisibility() == 0)) {
                animateMessageIn();
            }
            this.refCount++;
            if (timeout <= 0) {
                timeout = 8;
            }
            postDelayed(new Runnable() {
                public void run() {
                    BottomBar.this.closeNotif();
                }
            }, (long) (timeout * 1000));
            BottomNotificationIcon iconLayout = getIconLayout();
            if (type != 0 && iconLayout != null) {
                if (type == 2) {
                    iconLayout.setImageResources(C1283R.drawable.bottom_message_reports_back, C1283R.drawable.bottom_message_reports_shadow, C1283R.drawable.bottom_message_reports_front);
                } else if (type == 1) {
                    iconLayout.setImageResources(C1283R.drawable.bottom_message_wazers_online_back, C1283R.drawable.bottom_message_wazers_online_shadow, C1283R.drawable.bottom_message_wazers_online_front);
                } else if (type == 3) {
                }
                iconLayout.setVisibility(0);
                iconLayout.startOpenAnimation(0, DisplayStrings.DS_FAILED_TO_COMMUNICATE_WITH_ROUTING_SERVER, null);
            }
        }
    }

    private void animateMessageIn() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(250);
        this.mNotif.startAnimation(anim);
        this.mNotif.setVisibility(0);
        int delay = 100;
        if (this.mNotifTitle.getVisibility() == 0) {
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(new TranslateAnimation(0, 0.0f, 0, 0.0f, 1, 1.0f, 0, 0.0f));
            as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            as.setInterpolator(new DecelerateInterpolator());
            as.setDuration(200);
            as.setStartOffset((long) 100);
            as.setFillBefore(true);
            this.mNotifTitle.startAnimation(as);
            delay = 100 + 100;
        }
        if (this.mNotifBody.getVisibility() == 0) {
            as = new AnimationSet(true);
            as.addAnimation(new TranslateAnimation(0, 0.0f, 0, 0.0f, 1, 1.0f, 0, 0.0f));
            as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            as.setInterpolator(new DecelerateInterpolator());
            as.setDuration(200);
            as.setStartOffset((long) delay);
            as.setFillBefore(true);
            this.mNotifBody.startAnimation(as);
            delay += 100;
        }
    }

    private void closeNotifNow() {
        if (!this.mIsNotifAnimating) {
            BottomNotificationIcon iconLayout;
            if (this.mNotif == null || this.mNotif.getVisibility() == 8) {
                iconLayout = getIconLayout();
                if (iconLayout != null) {
                    iconLayout.setVisibility(8);
                }
                onCloseDone();
                return;
            }
            this.mIsNotifAnimating = true;
            animateMessageOut();
            iconLayout = getIconLayout();
            if (iconLayout != null) {
                iconLayout.startCloseAnimation(0, DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT, null);
            }
        }
    }

    private void animateMessageOut() {
        int delay = 0;
        if (this.mNotifBody.getVisibility() == 0) {
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 1, 1.0f));
            as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            as.setInterpolator(new DecelerateInterpolator());
            as.setDuration(200);
            as.setStartOffset((long) null);
            as.setFillBefore(true);
            as.setFillAfter(true);
            this.mNotifBody.startAnimation(as);
            delay = 0 + 100;
        }
        if (this.mNotifTitle.getVisibility() == 0) {
            as = new AnimationSet(true);
            as.addAnimation(new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 1, 1.0f));
            as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            as.setInterpolator(new DecelerateInterpolator());
            as.setDuration(200);
            as.setStartOffset((long) delay);
            as.setFillBefore(true);
            as.setFillAfter(true);
            this.mNotifTitle.startAnimation(as);
            delay += 100;
        }
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(250);
        anim.setFillBefore(true);
        anim.setStartOffset((long) delay);
        anim.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                BottomBar.this.mNotif.setVisibility(8);
                BottomBar.this.mIsNotifAnimating = false;
                BottomBar.this.onCloseDone();
            }
        });
        this.mNotif.startAnimation(anim);
    }

    public void closeNotif() {
        Runnable runnable = new Runnable() {
            public void run() {
                if (BottomBar.this.refCount > 1) {
                    BottomBar.this.refCount = BottomBar.this.refCount - 1;
                } else {
                    BottomBar.this.closeNotifNow();
                }
            }
        };
        if (this.mPaused) {
            this.mOnResume.add(runnable);
        } else {
            AppService.Post(runnable);
        }
    }

    private void onCloseDone() {
        if (this.refCount > 0) {
            this.refCount--;
        }
        if (!this.mDelayedNotifications.isEmpty()) {
            AppService.Post((Runnable) this.mDelayedNotifications.remove(0), 500);
        }
    }

    public void stopMessages() {
        this.mStopNotifications = true;
        if (this.mPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.closeNotifNow();
                }
            });
        } else {
            closeNotifNow();
        }
    }

    public void continueMessages() {
        this.mStopNotifications = false;
    }

    public void setShowDefaultMessage(final boolean show) {
        if (this.mPaused) {
            this.mOnResume.add(new Runnable() {
                public void run() {
                    BottomBar.this.setShowDefaultMessage(show);
                }
            });
        } else if (show) {
            setVisible(false);
            this.staticMessageText.setText(NativeManager.getInstance().getTimeOfDayGreetingNTV());
        } else {
            setVisible(true);
        }
    }

    public void setPaused(boolean paused) {
        this.mPaused = paused;
        if (!this.mPaused) {
            while (!this.mOnResume.isEmpty()) {
                ((Runnable) this.mOnResume.remove(0)).run();
            }
        }
    }

    public void setNavigating(boolean navigating) {
        this.arrowView.setVisibility(navigating ? 0 : 4);
        setTimeToPark();
    }

    public void setOpen(boolean open) {
        this.mIsOpened = open;
        ImageView imageView = this.arrowView;
        int i = !this.mCarpoolEnabled ? C1283R.drawable.navigate_bottom_bar_sep_dot : open ? C1283R.drawable.eta_arrows_disabled : C1283R.drawable.eta_arrows;
        imageView.setImageResource(i);
        setTimeToPark();
        if (open) {
            AppService.getMainActivity().getLayoutMgr().hideSpotifyButton();
        } else {
            AppService.getMainActivity().getLayoutMgr().showSpotifyButton();
        }
        if (this.mHasTimeToPark && open) {
            this.mTimeToParkContainer.setVisibility(0);
            this.mTimeToParkContainer.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mTimeToParkContainer).alpha(1.0f).setListener(null);
            DriveToNativeManager.getInstance().getTimeToParkStrNTV();
        }
    }

    public void closeTimeToParkMessage() {
        ViewPropertyAnimatorHelper.initAnimation(this.mTimeToParkContainer).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mTimeToParkContainer));
    }

    public void setCarpoolEnabled(boolean carpoolEnabled) {
        this.mCarpoolEnabled = carpoolEnabled;
    }
}
