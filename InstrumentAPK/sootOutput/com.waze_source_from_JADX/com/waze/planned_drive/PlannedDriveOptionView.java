package com.waze.planned_drive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PlannedDriveOptionView extends FrameLayout {
    private static final float FOCUS_SCALE_24_HOUR = 1.5f;
    private static final float FOCUS_SCALE_AM_PM = 1.25f;
    private static PlannedDriveOptionView currentViewShowingDetails = null;
    private Runnable mDelayedGraphReveal;
    private ImageView mDetailsArrow;
    private TextView mDriveDurationLabel;
    private LinearLayout mEtaContainer;
    private boolean mFromTop;
    private boolean mGraphAnimationStarted;
    private long mGraphRevealDelay;
    private PlannedDriveGraphView mGraphView;
    private TextView mLeaveByLabel;
    private PlannedDriveOptionModel mModel;
    private TextView mTimeLabel;

    public PlannedDriveOptionView(Context context) {
        this(context, null);
    }

    public PlannedDriveOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlannedDriveOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.planned_drive_option_view, null);
        this.mTimeLabel = (TextView) content.findViewById(C1283R.id.lblTime);
        this.mGraphView = (PlannedDriveGraphView) content.findViewById(C1283R.id.graphView);
        this.mDriveDurationLabel = (TextView) content.findViewById(C1283R.id.lblDriveDuration);
        this.mLeaveByLabel = (TextView) content.findViewById(C1283R.id.lblLeaveBy);
        this.mDetailsArrow = (ImageView) content.findViewById(C1283R.id.imgArrow);
        this.mEtaContainer = (LinearLayout) content.findViewById(C1283R.id.etaContainer);
        this.mTimeLabel.setTextSize(NativeManager.getInstance().is24HrClock() ? 18.0f : 16.0f);
        addView(content);
    }

    public void setModel(PlannedDriveOptionModel model, boolean fromTop, long graphRevealDelay) {
        this.mModel = model;
        setFields(fromTop, graphRevealDelay);
    }

    public boolean isGraphAnimating() {
        return this.mGraphView.isActuallyAnimating();
    }

    public void cancelPendingGraphAnimation() {
        if (this.mDelayedGraphReveal != null) {
            removeCallbacks(this.mDelayedGraphReveal);
        }
        this.mGraphView.setValues(null);
        this.mGraphAnimationStarted = false;
    }

    public void resumeGraphAnimation() {
        if (!this.mGraphAnimationStarted) {
            animateGraph(this.mFromTop, this.mGraphRevealDelay);
        }
    }

    public void animateGraph(final boolean fromTop, long graphRevealDelay) {
        if (this.mGraphView.getValues() != this.mModel.getGraphValues() || !this.mGraphView.isActuallyAnimating()) {
            this.mGraphView.setValues(null);
            this.mFromTop = fromTop;
            this.mGraphRevealDelay = graphRevealDelay;
            this.mGraphAnimationStarted = false;
            this.mDelayedGraphReveal = null;
            if (!PlannedDriveGraphView.haltAllGraphAnimations) {
                if (graphRevealDelay > 0) {
                    final PlannedDriveOptionModel currentModel = this.mModel;
                    this.mDelayedGraphReveal = new Runnable() {
                        public void run() {
                            if ((PlannedDriveOptionView.this.mGraphView.getValues() != PlannedDriveOptionView.this.mModel.getGraphValues() || !PlannedDriveOptionView.this.mGraphView.isActuallyAnimating()) && currentModel == PlannedDriveOptionView.this.mModel && !PlannedDriveGraphView.haltAllGraphAnimations) {
                                PlannedDriveOptionView.this.mGraphAnimationStarted = true;
                                PlannedDriveOptionView.this.mGraphView.setValues(PlannedDriveOptionView.this.mModel.getGraphValues());
                                PlannedDriveOptionView.this.mGraphView.startAnimating(fromTop);
                            }
                        }
                    };
                    postDelayed(this.mDelayedGraphReveal, graphRevealDelay);
                    return;
                }
                this.mGraphAnimationStarted = true;
                this.mGraphView.setValues(this.mModel.getGraphValues());
                this.mGraphView.startAnimating(fromTop);
            }
        }
    }

    private void setFields(boolean fromTop, long graphRevealDelay) {
        String durationText;
        animateGraph(fromTop, graphRevealDelay);
        SimpleDateFormat formatter = new SimpleDateFormat(NativeManager.getInstance().is24HrClock() ? "HH:mm" : "hh:mm a");
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        symbols.setAmPmStrings(new String[]{"am", "pm"});
        formatter.setDateFormatSymbols(symbols);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.mModel.getTimeMillis());
        this.mTimeLabel.setText(formatter.format(calendar.getTime()));
        calendar.setTimeInMillis(this.mModel.getTimeMillis() - this.mModel.getEtaMillis());
        this.mLeaveByLabel.setText(String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_LEAVE_BY_PS), new Object[]{formatter.format(calendar.getTime())}));
        int minutes = (int) (this.mModel.getEtaMillis() / 60000);
        int hours = minutes / 60;
        int subHourMinutes = minutes - (hours * 60);
        if (hours > 0) {
            String minutesText = subHourMinutes > 9 ? String.valueOf(subHourMinutes) : AppEventsConstants.EVENT_PARAM_VALUE_NO + subHourMinutes;
            durationText = String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_PD_HOUR_DRIVE), new Object[]{hours + ":" + minutesText});
        } else {
            durationText = String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FUTURE_DRIVES_PLAN_PD_MIN_DRIVE), new Object[]{Integer.valueOf(minutes)});
        }
        this.mDriveDurationLabel.setText(durationText);
        if (currentViewShowingDetails == null || currentViewShowingDetails.mModel != this.mModel) {
            this.mTimeLabel.setScaleX(1.0f);
            this.mTimeLabel.setScaleY(1.0f);
            this.mEtaContainer.setVisibility(4);
            this.mDetailsArrow.setVisibility(4);
            return;
        }
        this.mTimeLabel.setScaleX(getFocusScale());
        this.mTimeLabel.setScaleY(getFocusScale());
        this.mEtaContainer.setVisibility(0);
        this.mDetailsArrow.setVisibility(0);
        this.mEtaContainer.setAlpha(1.0f);
        this.mDetailsArrow.setTranslationX(0.0f);
    }

    private float getFocusScale() {
        return NativeManager.getInstance().is24HrClock() ? FOCUS_SCALE_24_HOUR : FOCUS_SCALE_AM_PM;
    }

    public void showDetails() {
        if (currentViewShowingDetails == null || currentViewShowingDetails.mModel != this.mModel) {
            if (currentViewShowingDetails != null) {
                currentViewShowingDetails.hideDetails();
            }
            currentViewShowingDetails = this;
            this.mEtaContainer.animate().cancel();
            this.mDetailsArrow.animate().cancel();
            this.mTimeLabel.animate().cancel();
            this.mEtaContainer.setVisibility(0);
            this.mEtaContainer.setAlpha(0.0f);
            this.mDetailsArrow.setVisibility(0);
            this.mDetailsArrow.setTranslationX((float) (-this.mDetailsArrow.getMeasuredWidth()));
            ViewPropertyAnimatorHelper.initAnimation(this.mEtaContainer).alpha(1.0f).setListener(null);
            ViewPropertyAnimatorHelper.initAnimation(this.mDetailsArrow).translationX(0.0f).setListener(null);
            ViewPropertyAnimatorHelper.initAnimation(this.mTimeLabel).scaleX(getFocusScale()).scaleY(getFocusScale());
        }
    }

    public void hideDetails() {
        this.mEtaContainer.animate().cancel();
        this.mDetailsArrow.animate().cancel();
        this.mTimeLabel.animate().cancel();
        this.mEtaContainer.setVisibility(0);
        this.mEtaContainer.setAlpha(1.0f);
        this.mDetailsArrow.setVisibility(0);
        this.mDetailsArrow.setTranslationX(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this.mEtaContainer).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener(this.mEtaContainer));
        ViewPropertyAnimatorHelper.initAnimation(this.mDetailsArrow).translationX((float) (-this.mDetailsArrow.getMeasuredWidth())).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener(this.mDetailsArrow));
        ViewPropertyAnimatorHelper.initAnimation(this.mTimeLabel).scaleX(1.0f).scaleY(1.0f);
    }
}
