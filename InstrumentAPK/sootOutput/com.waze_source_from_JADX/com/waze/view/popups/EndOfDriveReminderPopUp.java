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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.settings.SettingsEndOfDrive;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class EndOfDriveReminderPopUp extends FrameLayout {
    public static final int MODE_ENCOURAGEMENT = 0;
    public static final int MODE_REMINDER = 1;
    private TextView mDescriptionLabel;
    private ImageView mHeaderImage;
    private FrameLayout mLeftButton;
    private TextView mLeftButtonTitle;
    private EndOfDriveReminderPopupListener mListener;
    private LinearLayout mMainContainer;
    private int mMode;
    private FrameLayout mRightButton;
    private TextView mRightButtonTitle;
    private TextView mTitleLabel;

    class C31291 implements OnClickListener {
        C31291() {
        }

        public void onClick(View v) {
        }
    }

    class C31302 implements OnClickListener {
        C31302() {
        }

        public void onClick(View v) {
            if (EndOfDriveReminderPopUp.this.mMode == 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_ENCOURAGEMENT_TAPPED, "ACTION", "NOT_NOW");
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_TAPPED, "ACTION", "GOT_IT");
            }
            EndOfDriveReminderPopUp.this.hide();
        }
    }

    class C31313 implements OnClickListener {
        C31313() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_ENCOURAGEMENT_TAPPED, "ACTION", "NOT_NOW");
            EndOfDriveReminderPopUp.this.hide();
        }
    }

    class C31324 implements OnClickListener {
        C31324() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_ENCOURAGEMENT_TAPPED, "ACTION", "TRY_NOW");
            AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsEndOfDrive.class));
            EndOfDriveReminderPopUp.this.hide();
        }
    }

    class C31335 implements OnClickListener {
        C31335() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_TAPPED, "ACTION", "SETTINGS");
            AppService.getActiveActivity().startActivity(new Intent(AppService.getActiveActivity(), SettingsEndOfDrive.class));
            EndOfDriveReminderPopUp.this.hide();
        }
    }

    class C31346 implements OnClickListener {
        C31346() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_TAPPED, "ACTION", "GOT_IT");
            EndOfDriveReminderPopUp.this.hide();
        }
    }

    class C31357 implements Runnable {
        C31357() {
        }

        public void run() {
            EndOfDriveReminderPopUp.this.setVisibility(8);
            if (EndOfDriveReminderPopUp.this.mListener != null) {
                EndOfDriveReminderPopUp.this.mListener.onReminderPopupDismissed();
            }
        }
    }

    public interface EndOfDriveReminderPopupListener {
        void onReminderPopupDismissed();
    }

    public EndOfDriveReminderPopUp(Context context) {
        this(context, null);
    }

    public EndOfDriveReminderPopUp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndOfDriveReminderPopUp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.end_of_drive_encouragement_popup, null);
        this.mHeaderImage = (ImageView) content.findViewById(C1283R.id.imgHeader);
        this.mTitleLabel = (TextView) content.findViewById(C1283R.id.lblTitle);
        this.mDescriptionLabel = (TextView) content.findViewById(C1283R.id.lblDescription);
        this.mLeftButtonTitle = (TextView) content.findViewById(C1283R.id.lblLeftWhite);
        this.mRightButtonTitle = (TextView) content.findViewById(C1283R.id.lblRightBlue);
        this.mLeftButton = (FrameLayout) content.findViewById(C1283R.id.btnLeftWhite);
        this.mRightButton = (FrameLayout) content.findViewById(C1283R.id.btnRightBlue);
        this.mMainContainer = (LinearLayout) content.findViewById(C1283R.id.mainContainer);
        this.mMainContainer.setOnClickListener(new C31291());
        setOnClickListener(new C31302());
        addView(content);
    }

    public void setMode(int mode) {
        this.mMode = mode;
        setFields();
    }

    public void setListener(EndOfDriveReminderPopupListener listener) {
        this.mListener = listener;
    }

    private void setFields() {
        if (this.mMode == 0) {
            this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_ECOUNRAGEMENT_TITLE));
            this.mDescriptionLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_ECOUNRAGEMENT_DESCRIPTION));
            this.mLeftButtonTitle.setText(DisplayStrings.displayString(DisplayStrings.DS_NO_THANKS));
            this.mRightButtonTitle.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_GET_REMINDERS));
            this.mLeftButton.setOnClickListener(new C31313());
            this.mRightButton.setOnClickListener(new C31324());
            return;
        }
        boolean hasCustomMessage;
        String displayTextReminderMessage;
        this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_REMINDER_TITLE));
        if (TextUtils.isEmpty(ConfigManager.getInstance().getConfigValueString(352))) {
            hasCustomMessage = false;
        } else {
            hasCustomMessage = true;
        }
        if (hasCustomMessage) {
            displayTextReminderMessage = String.format("\"%s\"", new Object[]{reminderMessage});
        } else {
            displayTextReminderMessage = DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_SETTINGS_CUSTOM_MESSAGE_DEFAULT);
        }
        this.mDescriptionLabel.setText(displayTextReminderMessage);
        this.mLeftButtonTitle.setText(DisplayStrings.displayString(90));
        this.mRightButtonTitle.setText(DisplayStrings.displayString(DisplayStrings.DS_END_OF_DRIVE_GOT_IT));
        this.mLeftButton.setOnClickListener(new C31335());
        this.mRightButton.setOnClickListener(new C31346());
    }

    public void show() {
        setVisibility(0);
        setAlpha(0.0f);
        this.mMainContainer.setScaleX(0.0f);
        this.mMainContainer.setScaleY(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this).alpha(1.0f).setListener(null);
        ViewPropertyAnimatorHelper.initAnimation(this.mMainContainer).scaleX(1.0f).scaleY(1.0f).setListener(null);
        Analytics.log(this.mMode == 0 ? AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_ENCOURAGEMENT_SHOWN : AnalyticsEvents.ANALYTICS_EVENT_DRIVE_REMINDER_SHOWN);
    }

    public void hide() {
        ViewPropertyAnimatorHelper.initAnimation(this.mMainContainer).scaleX(0.0f).scaleY(0.0f).setListener(null);
        ViewPropertyAnimatorHelper.initAnimation(this).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C31357()));
    }
}
