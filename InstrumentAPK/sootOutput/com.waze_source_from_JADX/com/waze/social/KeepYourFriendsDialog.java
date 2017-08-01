package com.waze.social;

import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.utils.ImageRepository;

public class KeepYourFriendsDialog extends Dialog {
    private ActivityBase mActivity;
    private CompoundButton mCheckBox;
    private FriendUserData mFud;
    private int mImageResource;
    private NativeManager mNatMgr = AppService.getNativeManager();
    private OnClickListener mOnGetStarted;
    private boolean mShowAgain;
    private String mText;
    private String mTitle;

    class C29111 implements OnClickListener {
        C29111() {
        }

        public void onClick(View v) {
            if (KeepYourFriendsDialog.this.mFud == null) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_DRIVE_ETA_TIP_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_GET_STARTED);
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_BACK_ETA_TIP_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SEND_ETA);
            }
            KeepYourFriendsDialog.this.mOnGetStarted.onClick(v);
        }
    }

    class C29122 implements OnClickListener {
        C29122() {
        }

        public void onClick(View v) {
            if (KeepYourFriendsDialog.this.mFud == null) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_DRIVE_ETA_TIP_CLICK, "ACTION", "X");
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_BACK_ETA_TIP_CLICK, "ACTION", "X");
            }
            KeepYourFriendsDialog.this.dismiss();
        }
    }

    class C29133 implements OnClickListener {
        C29133() {
        }

        public void onClick(View v) {
        }
    }

    class C29144 implements OnCheckedChangeListener {
        C29144() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            KeepYourFriendsDialog.this.mShowAgain = !isChecked;
            if (!KeepYourFriendsDialog.this.mShowAgain) {
                if (KeepYourFriendsDialog.this.mFud == null) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_DRIVE_ETA_TIP_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_DONT_SHOW_AGAIN);
                } else {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_BACK_ETA_TIP_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_DONT_SHOW_AGAIN);
                }
                KeepYourFriendsDialog.this.dismiss();
            }
        }
    }

    class C29166 implements AnimationListener {
        C29166() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            super.dismiss();
        }
    }

    public KeepYourFriendsDialog(ActivityBase act, String title, String text, int imageResource, FriendUserData fud, OnClickListener onGetStarted) {
        super(act, C1283R.style.ToolTipDialog);
        this.mActivity = act;
        this.mTitle = title;
        this.mText = text;
        this.mImageResource = imageResource;
        this.mOnGetStarted = onGetStarted;
        this.mFud = fud;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    public void initLayout() {
        boolean z;
        setContentView(C1283R.layout.keep_your_friends_dialog);
        getWindow().setLayout(-1, -1);
        ImageView illustration = (ImageView) findViewById(C1283R.id.keepFriendsIllu);
        ((TextView) findViewById(C1283R.id.keepFriendsTitle)).setText(this.mTitle);
        ((TextView) findViewById(C1283R.id.keepFriendsText)).setText(this.mText);
        illustration.setImageResource(this.mImageResource);
        TextView getStartedButton = (TextView) findViewById(C1283R.id.keepFriendsGetStarted);
        if (this.mFud == null) {
            getStartedButton.setText(this.mNatMgr.getLanguageString(DisplayStrings.DS_GET_STARTED_BUTTON));
        } else {
            getStartedButton.setText(this.mNatMgr.getLanguageString(11));
        }
        getStartedButton.setOnClickListener(new C29111());
        OnClickListener closeClick = new C29122();
        findViewById(C1283R.id.keepFriendsClose).setOnClickListener(closeClick);
        findViewById(C1283R.id.keepFriendsScreen).setOnClickListener(closeClick);
        findViewById(C1283R.id.keepFriendsLayout).setOnClickListener(new C29133());
        this.mCheckBox = (CheckBox) findViewById(C1283R.id.keepFriendsCheckbox);
        this.mCheckBox.setText(this.mNatMgr.getLanguageString(DisplayStrings.DS_DONT_SHOW_AGAIN));
        CompoundButton compoundButton = this.mCheckBox;
        if (this.mShowAgain) {
            z = false;
        } else {
            z = true;
        }
        compoundButton.setChecked(z);
        this.mCheckBox.setOnCheckedChangeListener(new C29144());
        if (this.mFud != null) {
            findViewById(C1283R.id.keepFriendsUser).setVisibility(0);
            ImageRepository.instance.getImage(this.mFud.getImage(), 1, (ImageView) findViewById(C1283R.id.keepFriendsUserPic), null, this.mActivity);
            this.mCheckBox.setVisibility(8);
        }
    }

    public void show(final int x, final int y) {
        super.show();
        if (this.mFud == null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_DRIVE_ETA_TIP_SHOWN);
        } else {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_BACK_ETA_TIP_SHOWN);
        }
        final View dialog = findViewById(C1283R.id.keepFriendsLayout);
        dialog.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                dialog.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                KeepYourFriendsDialog.this.animate(x, y);
            }
        });
    }

    public void animate(int x, int y) {
        View dialog = findViewById(C1283R.id.keepFriendsLayout);
        View bg = findViewById(C1283R.id.keepFriendsScreen);
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(200);
        bg.startAnimation(aa);
        AnimationSet as = new AnimationSet(false);
        ScaleAnimation sa = new ScaleAnimation(0.01f, 1.0f, 0.01f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(300);
        sa.setInterpolator(new OvershootInterpolator());
        as.addAnimation(sa);
        RotateAnimation ra = new RotateAnimation(-3.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        ra.setDuration(300);
        ra.setInterpolator(new AnticipateOvershootInterpolator());
        as.addAnimation(ra);
        if (!(x == 0 && y == 0)) {
            int[] iArr = new int[2];
            iArr = new int[]{0, 0};
            dialog.getLocationOnScreen(iArr);
            iArr[0] = iArr[0] + (dialog.getWidth() / 2);
            iArr[1] = iArr[1] + (dialog.getHeight() / 2);
            TranslateAnimation ta = new TranslateAnimation(0, (float) (x - iArr[0]), 1, 0.0f, 0, (float) (y - iArr[1]), 1, 0.0f);
            ta.setDuration(250);
            ta.setInterpolator(new DecelerateInterpolator());
            as.addAnimation(ta);
        }
        aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(100);
        as.addAnimation(aa);
        dialog.startAnimation(as);
        View close = findViewById(C1283R.id.keepFriendsClose);
        aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(300);
        aa.setStartOffset(500);
        close.startAnimation(aa);
    }

    public void dismissNow() {
        super.dismiss();
    }

    public void dismiss() {
        View bg = findViewById(C1283R.id.keepFriendsScreen);
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setDuration(200);
        aa.setAnimationListener(new C29166());
        bg.startAnimation(aa);
    }

    public boolean shouldShowAgain() {
        return this.mShowAgain;
    }

    public void setShowAgain(boolean bShow) {
        this.mShowAgain = bShow;
        if (this.mCheckBox != null) {
            this.mCheckBox.setChecked(!bShow);
        }
    }
}
