package com.waze.voice;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.voice.CustomPromptManager.PromptDefinition;
import com.waze.voice.CustomPromptManager.RecordListener;
import java.util.Locale;

public class RecordYourselfPopup extends FrameLayout {
    private LinearLayout mButtonContainer;
    private FrameLayout mCancelButton;
    private int mCancelProgressAt;
    private TextView mDurationLabel;
    private TextView mInstructionNameLabel;
    private boolean mIsHidden;
    private RecordYourselfListener mListener;
    private ImageView mPreviewButton;
    private ValueAnimator mProgressAnimator;
    private View mProgressBarMarker;
    private PromptDefinition mPromptDefinition;
    private ImageView mRecordButton;
    private ProgressBar mRecordProgress;
    private FrameLayout mSaveButton;
    private View mStopOverlay;

    public interface RecordYourselfListener {
        void onRecordingComplete();
    }

    class C33021 implements OnClickListener {
        C33021() {
        }

        public void onClick(View v) {
            if (!CustomPromptManager.getInstance().isCurrentlyRecordingPrompt(RecordYourselfPopup.this.mPromptDefinition)) {
                RecordYourselfPopup.this.startRecording();
            }
        }
    }

    class C33052 implements OnClickListener {

        class C33041 implements Runnable {

            class C33031 implements Runnable {
                C33031() {
                }

                public void run() {
                    RecordYourselfPopup.this.setButtonEnabled(RecordYourselfPopup.this.mPreviewButton, true);
                    RecordYourselfPopup.this.setButtonEnabled(RecordYourselfPopup.this.mRecordButton, true);
                    RecordYourselfPopup.this.mProgressAnimator.cancel();
                    RecordYourselfPopup.this.mRecordProgress.setProgress(RecordYourselfPopup.this.mCancelProgressAt);
                }
            }

            C33041() {
            }

            public void run() {
                RecordYourselfPopup.this.post(new C33031());
            }
        }

        C33052() {
        }

        public void onClick(View v) {
            if (CustomPromptManager.getInstance().doesFileExist(RecordYourselfPopup.this.mPromptDefinition.getId(), true) && CustomPromptManager.getInstance().previewPrompt(RecordYourselfPopup.this.mPromptDefinition.getId(), true, new C33041())) {
                RecordYourselfPopup.this.setButtonEnabled(RecordYourselfPopup.this.mPreviewButton, false);
                RecordYourselfPopup.this.setButtonEnabled(RecordYourselfPopup.this.mRecordButton, false);
                RecordYourselfPopup.this.startProgressAnimation(((float) RecordYourselfPopup.this.mRecordProgress.getProgress()) / 1000.0f, CustomPromptManager.getInstance().getFileDuration(RecordYourselfPopup.this.mPromptDefinition.getId(), true));
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_PREVIEWED).addParam("ACTION", RecordYourselfPopup.this.mPromptDefinition.getId()).send();
            }
        }
    }

    class C33063 implements AnimatorUpdateListener {
        C33063() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            int progress = (int) (animation.getAnimatedFraction() * 1000.0f);
            RecordYourselfPopup.this.mRecordProgress.setProgress(progress);
            if (RecordYourselfPopup.this.mCancelProgressAt > 0 && progress >= RecordYourselfPopup.this.mCancelProgressAt) {
                RecordYourselfPopup.this.mRecordProgress.setProgress(RecordYourselfPopup.this.mCancelProgressAt);
                RecordYourselfPopup.this.mProgressAnimator.cancel();
            }
        }
    }

    class C33074 implements OnClickListener {
        C33074() {
        }

        public void onClick(View v) {
            CustomPromptManager.getInstance().stopRecording(false);
        }
    }

    class C33085 implements OnClickListener {
        C33085() {
        }

        public void onClick(View v) {
            RecordYourselfPopup.this.hide();
        }
    }

    class C33096 implements OnClickListener {
        C33096() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CUSTOM_PROMPTS_SAVED).addParam("ACTION", RecordYourselfPopup.this.mPromptDefinition.getId()).send();
            CustomPromptManager.getInstance().savePrompt(RecordYourselfPopup.this.mPromptDefinition.getId());
            RecordYourselfPopup.this.hide();
        }
    }

    class C33107 implements OnClickListener {
        C33107() {
        }

        public void onClick(View v) {
            if (CustomPromptManager.getInstance().isCurrentlyRecordingPrompt(RecordYourselfPopup.this.mPromptDefinition)) {
                CustomPromptManager.getInstance().stopRecording(false);
            } else {
                RecordYourselfPopup.this.hide();
            }
        }
    }

    class C33118 implements OnClickListener {
        C33118() {
        }

        public void onClick(View v) {
        }
    }

    class C33129 implements Runnable {
        C33129() {
        }

        public void run() {
            RecordYourselfPopup.this.mStopOverlay.setVisibility(0);
        }
    }

    public RecordYourselfPopup(Context context) {
        this(context, null);
    }

    public RecordYourselfPopup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordYourselfPopup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.recorder_popup, null);
        this.mInstructionNameLabel = (TextView) content.findViewById(C1283R.id.lblInstructionName);
        this.mDurationLabel = (TextView) content.findViewById(C1283R.id.lblDuration);
        this.mRecordButton = (ImageView) content.findViewById(C1283R.id.btnRecord);
        this.mPreviewButton = (ImageView) content.findViewById(C1283R.id.btnPreview);
        this.mButtonContainer = (LinearLayout) content.findViewById(C1283R.id.buttonContainer);
        this.mRecordProgress = (ProgressBar) content.findViewById(C1283R.id.recordingProgress);
        this.mCancelButton = (FrameLayout) content.findViewById(C1283R.id.btnCancel);
        this.mSaveButton = (FrameLayout) content.findViewById(C1283R.id.btnSave);
        this.mStopOverlay = content.findViewById(C1283R.id.stopOverlay);
        this.mProgressBarMarker = content.findViewById(C1283R.id.progressBarMarker);
        this.mRecordButton.setOnClickListener(new C33021());
        this.mPreviewButton.setOnClickListener(new C33052());
        this.mProgressAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mProgressAnimator.addUpdateListener(new C33063());
        this.mProgressAnimator.setInterpolator(new LinearInterpolator());
        this.mStopOverlay.setOnClickListener(new C33074());
        this.mCancelButton.setOnClickListener(new C33085());
        this.mSaveButton.setOnClickListener(new C33096());
        setOnClickListener(new C33107());
        content.findViewById(C1283R.id.popupContainer).setOnClickListener(new C33118());
        setButtonEnabled(this.mSaveButton, false);
        setButtonEnabled(this.mCancelButton, true);
        addView(content);
        setDisplayStrings();
    }

    private void startRecording() {
        postDelayed(new C33129(), 250);
        CustomPromptManager.getInstance().beginRecordingPrompt(this.mPromptDefinition, new RecordListener() {

            class C33011 implements Runnable {
                C33011() {
                }

                public void run() {
                    RecordYourselfPopup.this.adjustPlayStopState();
                    RecordYourselfPopup.this.mStopOverlay.setVisibility(8);
                    RecordYourselfPopup.this.adjustPreviewButton();
                }
            }

            public void onRecordComplete() {
                RecordYourselfPopup.this.post(new C33011());
            }

            public void onRecordStart() {
                RecordYourselfPopup.this.startProgressAnimation();
            }
        });
        adjustPlayStopState();
    }

    private float getProgressRatio() {
        return ((float) this.mRecordProgress.getProgress()) / 1000.0f;
    }

    private void adjustPlayStopState() {
        if (CustomPromptManager.getInstance().isCurrentlyRecordingPrompt(this.mPromptDefinition)) {
            this.mProgressBarMarker.setVisibility(8);
            ViewPropertyAnimatorHelper.initAnimation(this.mButtonContainer).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener(this.mButtonContainer));
            ViewPropertyAnimatorHelper.initAnimation(this.mRecordButton).translationX((float) ((this.mButtonContainer.getWidth() / 2) - (this.mRecordButton.getMeasuredWidth() / 2)));
            ViewPropertyAnimatorHelper.initAnimation(this.mPreviewButton).translationX((float) (((-this.mButtonContainer.getWidth()) / 2) + (this.mRecordButton.getMeasuredWidth() / 2)));
            setButtonEnabled(this.mSaveButton, false);
            setButtonEnabled(this.mCancelButton, false);
            return;
        }
        this.mProgressBarMarker.setTranslationX(getProgressRatio() * ((float) this.mRecordProgress.getMeasuredWidth()));
        this.mProgressBarMarker.setVisibility(0);
        this.mButtonContainer.setVisibility(0);
        ViewPropertyAnimatorHelper.initAnimation(this.mButtonContainer).alpha(1.0f).setListener(null);
        ViewPropertyAnimatorHelper.initAnimation(this.mRecordButton).translationX(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this.mPreviewButton).translationX(0.0f);
        setButtonEnabled(this.mSaveButton, true);
        setButtonEnabled(this.mCancelButton, true);
        this.mProgressAnimator.cancel();
    }

    private void setButtonEnabled(View button, boolean enabled) {
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1.0f : 0.25f);
    }

    private void startProgressAnimation() {
        startProgressAnimation(1.0f, (long) (this.mPromptDefinition.getMaxSeconds() * 1000));
    }

    private void startProgressAnimation(float ratio, long duration) {
        this.mRecordProgress.setProgress(0);
        this.mCancelProgressAt = (int) (1000.0f * ratio);
        this.mProgressAnimator.setDuration((long) ((int) (((float) duration) / ratio)));
        this.mProgressAnimator.start();
    }

    private void setDisplayStrings() {
        ((TextView) this.mCancelButton.findViewById(C1283R.id.lblCancel)).setText(DisplayStrings.displayString(344));
        ((TextView) this.mSaveButton.findViewById(C1283R.id.lblSave)).setText(DisplayStrings.displayString(DisplayStrings.DS_SAVE));
    }

    public void setPromptDefinition(PromptDefinition promptDefinition) {
        this.mPromptDefinition = promptDefinition;
        setFields();
    }

    public void setListener(RecordYourselfListener listener) {
        this.mListener = listener;
    }

    private void setFields() {
        if (this.mPromptDefinition != null) {
            String displayText = this.mPromptDefinition.getDisplayText();
            this.mInstructionNameLabel.setText(String.format(Locale.US, "\"%s\"", new Object[]{displayText}));
            if (displayText != null && displayText.length() > 15) {
                this.mInstructionNameLabel.setTextSize(1, 18.0f);
            }
            this.mDurationLabel.setText(String.format(Locale.US, DisplayStrings.displayString(DisplayStrings.DS_CUSTOM_PROMPTS_X_SECONDS_MAX), new Object[]{Integer.valueOf(this.mPromptDefinition.getMaxSeconds())}));
            this.mRecordProgress.setProgress(0);
            adjustPreviewButton();
        }
    }

    private void adjustPreviewButton() {
        setButtonEnabled(this.mPreviewButton, CustomPromptManager.getInstance().doesFileExist(this.mPromptDefinition.getId(), true));
    }

    public void hide() {
        if (!this.mIsHidden) {
            this.mIsHidden = true;
            if (CustomPromptManager.getInstance().isCurrentlyRecordingPrompt(this.mPromptDefinition)) {
                CustomPromptManager.getInstance().stopRecording(true);
            } else {
                CustomPromptManager.getInstance().deletePrompt(this.mPromptDefinition.getId(), true);
            }
            ViewPropertyAnimatorHelper.initAnimation(this).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new Runnable() {
                public void run() {
                    ((ViewGroup) RecordYourselfPopup.this.getParent()).removeView(RecordYourselfPopup.this);
                    if (RecordYourselfPopup.this.mListener != null) {
                        RecordYourselfPopup.this.mListener.onRecordingComplete();
                    }
                }
            }));
        }
    }

    public static RecordYourselfPopup showRecordYourselfPopup(Activity activity, PromptDefinition promptDefinition, RecordYourselfListener listener) {
        RecordYourselfPopup recordYourselfPopup = new RecordYourselfPopup(activity);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        recordYourselfPopup.setLayoutParams(layoutParams);
        recordYourselfPopup.setListener(listener);
        recordYourselfPopup.setPromptDefinition(promptDefinition);
        recordYourselfPopup.setAlpha(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(recordYourselfPopup).alpha(1.0f);
        activity.addContentView(recordYourselfPopup, layoutParams);
        return recordYourselfPopup;
    }
}
