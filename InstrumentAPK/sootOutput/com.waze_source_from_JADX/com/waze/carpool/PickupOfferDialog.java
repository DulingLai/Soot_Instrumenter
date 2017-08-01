package com.waze.carpool;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.timer.TimerView;

public class PickupOfferDialog extends Dialog {
    public static final int RT_RIDE_DETAILS_REQUEST = 5715;
    private ActivityBase mActivity;
    private String mDetour;
    private final CarpoolDrive mDrive;
    private String mImageUrl;
    private String mName;
    private NativeManager mNatMgr = AppService.getNativeManager();
    Runnable mOnCancel;
    private String mReward;
    private String mRideId;
    private TimerView mTimer;

    class C16721 implements OnClickListener {
        C16721() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "TAP|" + PickupOfferDialog.this.mRideId);
            if (PickupOfferDialog.this.mDrive == null) {
                Logger.m38e("PickupOfferDialog: ride is null! cannot view ride details");
                MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
                PickupOfferDialog.this.dismiss();
                return;
            }
            Intent $r2 = new Intent(PickupOfferDialog.this.mActivity, CarpoolRideDetailsActivity.class);
            $r2.putExtra(CarpoolDrive.class.getSimpleName(), PickupOfferDialog.this.mDrive);
            PickupOfferDialog.this.mActivity.startActivityForResult($r2, PickupOfferDialog.RT_RIDE_DETAILS_REQUEST);
            PickupOfferDialog.this.dismiss();
        }
    }

    class C16732 implements OnClickListener {
        C16732() throws  {
        }

        public void onClick(View v) throws  {
            if (((TimerView) PickupOfferDialog.this.findViewById(C1283R.id.pickupOfferButtonNoTimer)).hasExpired()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "TO|" + PickupOfferDialog.this.mRideId);
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "CLOSE|" + PickupOfferDialog.this.mRideId);
            }
            PickupOfferDialog.this.mOnCancel.run();
            PickupOfferDialog.this.dismiss();
        }
    }

    class C16743 implements OnClickListener {
        C16743() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "DONT_ASK|" + PickupOfferDialog.this.mRideId);
            CarpoolNativeManager.getInstance().setRwRtDontShowAgainValue(true);
            PickupOfferDialog.this.mOnCancel.run();
            PickupOfferDialog.this.dismiss();
        }
    }

    class C16754 implements OnCancelListener {
        C16754() throws  {
        }

        public void onCancel(DialogInterface dialog) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "CLOSE|" + PickupOfferDialog.this.mRideId);
        }
    }

    class C16776 implements AnimationListener {
        C16776() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            super.dismiss();
        }
    }

    public PickupOfferDialog(ActivityBase $r1, CarpoolDrive $r2, Runnable $r3) throws  {
        super($r1, C1283R.style.ToolTipDialog);
        this.mActivity = $r1;
        this.mName = $r2.getRider().getName();
        this.mReward = $r2.getRewardString($r1);
        this.mDetour = $r2.getDetourString(DisplayStrings.DS_RIDE_REQ_EXTRA_TIME_PD);
        this.mImageUrl = $r2.getRider().getImage();
        this.mDrive = $r2;
        this.mOnCancel = $r3;
        if (this.mDrive != null) {
            this.mRideId = this.mDrive.getRide().getId();
        } else {
            this.mRideId = "UNKNOWN";
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        initLayout();
    }

    public void initLayout() throws  {
        setContentView(C1283R.layout.pickup_offer_dialog);
        getWindow().setLayout(-1, -1);
        ImageView $r4 = (ImageView) findViewById(C1283R.id.pickupOfferImage);
        ImageRepository.instance.getImage(this.mImageUrl, 2, $r4, null, this.mActivity);
        ((TextView) findViewById(C1283R.id.pickupOfferTextTop)).setText(String.format(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_TITLE_PS), new Object[]{this.mName}));
        ((TextView) findViewById(C1283R.id.pickupOfferTextReward)).setText(String.format(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_REWARD_PS), new Object[]{this.mReward}));
        ((TextView) findViewById(C1283R.id.pickupOfferTextDetour)).setText(this.mDetour);
        ((TextView) findViewById(C1283R.id.pickupOfferTextDontShow)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_DONT_SHOW));
        ((TextView) findViewById(C1283R.id.pickupOfferButtonYesText)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_BUTTON_YES));
        ((TextView) findViewById(C1283R.id.pickupOfferButtonNoText)).setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_RT_RIDE_POPUP_BUTTON_NO));
        findViewById(C1283R.id.pickupOfferButtonYes).setOnClickListener(new C16721());
        findViewById(C1283R.id.pickupOfferButtonNo).setOnClickListener(new C16732());
        findViewById(C1283R.id.pickupOfferTextDontShow).setOnClickListener(new C16743());
        this.mTimer = (TimerView) findViewById(C1283R.id.pickupOfferButtonNoTimer);
        int $i0 = ConfigValues.getIntValue(10);
        TimerView $r14 = this.mTimer;
        TimerView timerView = $r14;
        $r14.reset().setTime($i0).start();
        setOnCancelListener(new C16754());
    }

    public void show(final int $i0, final int $i1) throws  {
        super.show();
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_SHOWN, "TYPE|RIDE_ID|STATUS", "DIALOG|" + this.mRideId + "|" + CarpoolUtils.getBoardedState());
        final View $r3 = findViewById(C1283R.id.pickupOfferBg);
        $r3.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() throws  {
                $r3.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                PickupOfferDialog.this.animate($i0, $i1);
            }
        });
    }

    public void animate(int $i0, int $i1) throws  {
        View $r5 = findViewById(C1283R.id.pickupOfferBg);
        AnimationSet $r3 = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.01f, 1.0f, 0.01f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        $r3.addAnimation(scaleAnimation);
        Animation rotateAnimation = new RotateAnimation(-3.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        rotateAnimation.setDuration(300);
        rotateAnimation.setInterpolator(new AnticipateOvershootInterpolator());
        $r3.addAnimation(rotateAnimation);
        if (!($i0 == 0 && $i1 == 0)) {
            int[] $r4 = new int[]{0, 0};
            $r5.getLocationOnScreen($r4);
            $r4[0] = $r4[0] + ($r5.getWidth() / 2);
            $r4[1] = $r4[1] + ($r5.getHeight() / 2);
            float $f0 = $i0 - $r4[0];
            float f = $f0;
            rotateAnimation = new TranslateAnimation(0, (float) $f0, 1, 0.0f, 0, (float) ($i1 - $r4[1]), 1, 0.0f);
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            $r3.addAnimation(rotateAnimation);
        }
        rotateAnimation = new AlphaAnimation(0.0f, 1.0f);
        rotateAnimation.setDuration(100);
        $r3.addAnimation(rotateAnimation);
        $r5.startAnimation($r3);
    }

    public void dismissNow() throws  {
        super.dismiss();
    }

    public void dismiss() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RT_RIDE_OFFER_CLICK, "ACTION|RIDE_ID", "CLOSE|" + this.mRideId);
        View $r4 = findViewById(C1283R.id.pickupOfferBg);
        AlphaAnimation $r1 = new AlphaAnimation(1.0f, 0.0f);
        $r1.setDuration(200);
        $r1.setAnimationListener(new C16776());
        $r4.startAnimation($r1);
    }
}
