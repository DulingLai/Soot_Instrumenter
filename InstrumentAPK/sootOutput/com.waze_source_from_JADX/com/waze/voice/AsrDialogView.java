package com.waze.voice;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.text.AutoResizeTextView;
import com.waze.voice.AsrSpeechRecognizer.AsrAudioEventListener;
import com.waze.voice.AsrVoiceWaveView.WaveValueProvider;

public class AsrDialogView extends FrameLayout implements AsrAudioEventListener {
    private static final String[] SUGGESTION_STRINGS = new String[]{"Drive home", "Drive to 15 Main Street", "Report a traffic jam", "When will I get there?", "Show more routes", "Cancel"};
    private static final int TOTAL_WAVE_SEGMENTS = 15;
    private boolean mApplyListeningAnimation;
    private Runnable mAutoCloseRunnable;
    private TextView mEllipsisLabel;
    private ImageView mEmoticon;
    private boolean mHasBeenShown;
    private boolean mIsDragging;
    private boolean mIsMinimizedMode;
    private boolean mIsUnavailableMode;
    private boolean mIsUpMotion;
    private int mListeningEllipsisCount;
    private RelativeLayout mMainContainer;
    private int mMinimizedTranslation;
    private int mPreviousHeight;
    private float mPreviousMotionY;
    private TextView mSubTitleLabel;
    private RecyclerView mSuggestionsRecycler;
    private boolean mSwipeToShutMode;
    private TextView mTitleLabel;
    private int mTouchDownY;
    private AsrVoiceWaveView mWaveView;

    class C32851 implements Runnable {
        C32851() {
        }

        public void run() {
            AsrDialogView.this.onStopListening();
        }
    }

    class C32862 implements WaveValueProvider {
        C32862() {
        }

        public float getCurrentValue() {
            return ((float) AsrSpeechRecognizer.getInstance().getAudioWaveLevel()) / AutoResizeTextView.MIN_TEXT_SIZE;
        }

        public void resetSegmentWidth() {
            AsrDialogView.this.mWaveView.setSegmentWidth(AsrDialogView.this.getMeasuredWidth() / 15);
        }
    }

    class C32873 implements OnClickListener {
        C32873() {
        }

        public void onClick(View v) {
            AsrDialogView.this.performCloseSequence("X");
        }
    }

    class C32884 implements Runnable {
        C32884() {
        }

        public void run() {
            if (AsrDialogView.this.mApplyListeningAnimation) {
                AsrDialogView.this.buildEllipsisString();
                AsrDialogView.this.mListeningEllipsisCount = AsrDialogView.this.mListeningEllipsisCount + 1;
                if (AsrDialogView.this.mListeningEllipsisCount > 3) {
                    AsrDialogView.this.mListeningEllipsisCount = 0;
                }
            }
            AsrDialogView.this.applyListeningAnimation();
        }
    }

    class C32906 implements Runnable {
        C32906() {
        }

        public void run() {
            AsrDialogView.this.mApplyListeningAnimation = false;
            AsrDialogView.this.mEllipsisLabel.setText("");
            AsrDialogView.this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ASR_THINKING));
            ViewPropertyAnimatorHelper.initAnimation(AsrDialogView.this.mSubTitleLabel).alpha(0.0f);
        }
    }

    class C32917 implements Runnable {
        C32917() {
        }

        public void run() {
            AsrDialogView.this.mApplyListeningAnimation = true;
            AsrDialogView.this.mListeningEllipsisCount = 0;
            AsrDialogView.this.buildEllipsisString();
            ViewPropertyAnimatorHelper.initAnimation(AsrDialogView.this.mSubTitleLabel).alpha(1.0f);
        }
    }

    class C32928 implements Runnable {
        C32928() {
        }

        public void run() {
            ((ViewGroup) AsrDialogView.this.getParent()).removeView(AsrDialogView.this);
        }
    }

    private class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(View itemView) {
            super(itemView);
            LayoutParams params = new LayoutParams(-1, PixelMeasure.dp(1));
            params.leftMargin = PixelMeasure.dp(16);
            params.rightMargin = PixelMeasure.dp(16);
            itemView.setLayoutParams(params);
            itemView.setBackgroundColor(956301311);
        }
    }

    private class SuggestionViewHolder extends ViewHolder {
        public SuggestionViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dp(64)));
        }

        public void bind(String text) {
            ((TextView) this.itemView.findViewById(C1283R.id.lblSuggestion)).setText(text);
        }
    }

    private class SuggestionsAdapter extends Adapter<ViewHolder> {
        private static final int VIEW_TYPE_SEP = 1;
        private static final int VIEW_TYPE_TEXT = 0;

        private SuggestionsAdapter() {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new SuggestionViewHolder(LayoutInflater.from(AsrDialogView.this.getContext()).inflate(C1283R.layout.asr_suggestion_view, null));
            }
            return new SeparatorViewHolder(new View(AsrDialogView.this.getContext()));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof SuggestionViewHolder) {
                ((SuggestionViewHolder) holder).bind(AsrDialogView.SUGGESTION_STRINGS[position / 2]);
            }
        }

        public int getItemCount() {
            return (AsrDialogView.SUGGESTION_STRINGS.length * 2) - 1;
        }

        public int getItemViewType(int position) {
            return position % 2 == 0 ? 0 : 1;
        }
    }

    public AsrDialogView(Context context) {
        this(context, null);
    }

    public AsrDialogView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsrDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAutoCloseRunnable = new C32851();
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.asr_dialog, null);
        this.mMainContainer = (RelativeLayout) content.findViewById(C1283R.id.asrMainContainer);
        this.mEmoticon = (ImageView) content.findViewById(C1283R.id.imgAsrEmoticon);
        this.mWaveView = (AsrVoiceWaveView) content.findViewById(C1283R.id.asrWaveView);
        this.mTitleLabel = (TextView) content.findViewById(C1283R.id.lblAsrTitle);
        this.mSubTitleLabel = (TextView) content.findViewById(C1283R.id.lblAsrSubTitle);
        this.mEllipsisLabel = (TextView) content.findViewById(C1283R.id.lblEllipsis);
        this.mSuggestionsRecycler = (RecyclerView) content.findViewById(C1283R.id.suggestionsRecycler);
        ImageView closeButton = (ImageView) content.findViewById(C1283R.id.btnAsrClose);
        this.mSuggestionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mSuggestionsRecycler.setAdapter(new SuggestionsAdapter());
        this.mWaveView.setWaveValueProvider(new C32862());
        this.mWaveView.setColor(getResources().getColor(C1283R.color.BlueDeepLight));
        this.mWaveView.setAnimationSpeed(100);
        closeButton.setSoundEffectsEnabled(false);
        closeButton.setOnClickListener(new C32873());
        this.mIsMinimizedMode = NativeManager.getInstance().isNavigatingNTV();
        this.mEmoticon.setPivotY(0.0f);
        AsrSpeechRecognizer.getInstance().setListener(this);
        AppService.getMainActivity().getLayoutMgr().setAsrDialogView(this);
        setDisplayStrings();
        this.mApplyListeningAnimation = true;
        applyListeningAnimation();
        addView(content);
    }

    private void setDisplayStrings() {
        if (this.mIsUnavailableMode) {
            this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ASR_UNAVAILABLE_TITLE));
            this.mSubTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ASR_UNAVAILABLE_DESCRIPTION));
            this.mTitleLabel.setTextColor(getResources().getColor(C1283R.color.RedSweet));
            this.mSubTitleLabel.setTextColor(getResources().getColor(C1283R.color.RedSweet));
            this.mEmoticon.setVisibility(8);
            return;
        }
        this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ASR_LISTENING));
        this.mSubTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ASR_GO_AHEAD));
        this.mTitleLabel.setTextColor(getResources().getColor(C1283R.color.White));
        this.mSubTitleLabel.setTextColor(getResources().getColor(C1283R.color.White));
        this.mEmoticon.setVisibility(0);
    }

    private void scheduleAutoClose() {
        postDelayed(this.mAutoCloseRunnable, 5000);
    }

    private void applyListeningAnimation() {
        if (!this.mIsUnavailableMode) {
            postDelayed(new C32884(), 500);
        }
    }

    private void buildEllipsisString() {
        StringBuilder listeningBuilder = new StringBuilder();
        for (int i = 0; i < this.mListeningEllipsisCount; i++) {
            listeningBuilder.append(FileUploadSession.SEPARATOR);
        }
        this.mEllipsisLabel.setText(listeningBuilder.toString());
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean touchIntercepted = super.dispatchTouchEvent(ev);
        if (touchIntercepted || ev.getAction() != 0 || ev.getY() < this.mMainContainer.getTranslationY()) {
            return touchIntercepted;
        }
        this.mTouchDownY = (int) ev.getY();
        this.mPreviousMotionY = (float) this.mTouchDownY;
        this.mIsDragging = true;
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mIsDragging) {
            if (event.getAction() == 2) {
                boolean z;
                int moveDelta = ((int) event.getY()) - this.mTouchDownY;
                if (event.getY() < this.mPreviousMotionY) {
                    z = true;
                } else {
                    z = false;
                }
                this.mIsUpMotion = z;
                this.mPreviousMotionY = event.getY();
                if (!this.mIsUnavailableMode && this.mIsMinimizedMode && moveDelta < 0 && this.mMinimizedTranslation + moveDelta > 0) {
                    this.mMainContainer.setTranslationY((float) (this.mMinimizedTranslation + moveDelta));
                    adjustViewPositions(this.mMinimizedTranslation + moveDelta);
                    this.mSwipeToShutMode = false;
                    return true;
                } else if (this.mIsMinimizedMode && moveDelta > 0) {
                    this.mSwipeToShutMode = true;
                    return true;
                } else if (this.mIsMinimizedMode || moveDelta <= 0 || moveDelta >= this.mMinimizedTranslation) {
                    return true;
                } else {
                    this.mMainContainer.setTranslationY((float) moveDelta);
                    adjustViewPositions(moveDelta);
                    return true;
                }
            } else if (event.getAction() == 1 || event.getAction() == 3) {
                this.mIsDragging = false;
                if (this.mSwipeToShutMode) {
                    performCloseSequence(AnalyticsEvents.f78x9b41f7d7);
                } else if (!this.mIsUnavailableMode) {
                    finalizeTouchAnimation(this.mIsUpMotion);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void performCloseSequence(String analyticsEvent) {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ASR_LISTENING_CLICKED).addParam("ACTION", analyticsEvent).send();
        if (this.mIsUnavailableMode) {
            removeCallbacks(this.mAutoCloseRunnable);
            onStopListening();
            return;
        }
        AsrSpeechRecognizer.getInstance().endSpeechSession(AnalyticsEvents.f50xedcf1877);
    }

    private void finalizeTouchAnimation(boolean isUp) {
        boolean z;
        boolean z2 = true;
        if (isUp) {
            z = false;
        } else {
            z = true;
        }
        this.mIsMinimizedMode = z;
        final float initialTranslation = this.mMainContainer.getTranslationY();
        final float targetTranslation = isUp ? 0.0f : (float) this.mMinimizedTranslation;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentTranslation = initialTranslation + ((targetTranslation - initialTranslation) * animation.getAnimatedFraction());
                AsrDialogView.this.mMainContainer.setTranslationY(currentTranslation);
                AsrDialogView.this.adjustViewPositions((int) currentTranslation);
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        animator.start();
        if (!this.mIsMinimizedMode) {
            RecyclerView recyclerView = this.mSuggestionsRecycler;
            if (isRecyclerScrollable()) {
                z2 = false;
            }
            recyclerView.requestDisallowInterceptTouchEvent(z2);
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ASR_LISTENING_CLICKED).addParam("ACTION", this.mIsMinimizedMode ? "COLLAPSE" : "EXPAND").send();
    }

    public void handleBackClick() {
        if (this.mIsMinimizedMode) {
            performCloseSequence("BACK");
        } else {
            finalizeTouchAnimation(false);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int translationTarget = 0;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mPreviousHeight != getMeasuredHeight()) {
            this.mPreviousHeight = getMeasuredHeight();
            this.mMinimizedTranslation = getMeasuredHeight() - PixelMeasure.dimension(C1283R.dimen.asrDialogMinimizedHeight);
            if (!this.mHasBeenShown) {
                this.mHasBeenShown = true;
                this.mMainContainer.setTranslationY((float) getMeasuredHeight());
                if (this.mIsMinimizedMode) {
                    translationTarget = this.mMinimizedTranslation;
                }
                ViewPropertyAnimatorHelper.initAnimation(this.mMainContainer).translationY((float) translationTarget);
                adjustViewPositions(translationTarget);
                this.mWaveView.setSegmentWidth(getMeasuredWidth() / 15);
                this.mWaveView.start();
            } else if (this.mIsMinimizedMode) {
                this.mMainContainer.setTranslationY((float) this.mMinimizedTranslation);
                adjustViewPositions(this.mMinimizedTranslation);
            } else {
                this.mMainContainer.setTranslationY(0.0f);
                adjustViewPositions(0);
            }
            this.mEmoticon.setPivotX((float) (this.mEmoticon.getMeasuredWidth() / 2));
        }
    }

    private void adjustViewPositions(int currentTranslation) {
        this.mWaveView.setTranslationY((float) ((this.mMainContainer.getMeasuredHeight() - currentTranslation) - this.mWaveView.getMeasuredHeight()));
        float ratio = ((float) (this.mMinimizedTranslation - currentTranslation)) / ((float) this.mMinimizedTranslation);
        float ratioSquare = ratio * ratio;
        float ratioInverseSin = 1.0f - ((float) Math.sin((((double) (1.0f - ratio)) * 3.141592653589793d) / 2.0d));
        float scale = 1.0f + ratioInverseSin;
        float labelY = (((float) this.mEmoticon.getMeasuredHeight()) * ((float) Math.sin((((double) ratio) * 3.141592653589793d) / 2.0d))) * scale;
        float recyclerY = (ratio - 1.0f) * ((float) PixelMeasure.dp(80));
        this.mEmoticon.setTranslationX(((1.0f - ratio) * ((float) PixelMeasure.dp(16))) + (((float) ((getMeasuredWidth() / 2) - (this.mEmoticon.getMeasuredWidth() / 2))) * ratioInverseSin));
        this.mEmoticon.setScaleX(scale);
        this.mEmoticon.setScaleY(scale);
        this.mTitleLabel.setTranslationY(labelY);
        this.mSubTitleLabel.setTranslationY(labelY);
        this.mEllipsisLabel.setTranslationY(labelY);
        this.mSuggestionsRecycler.setTranslationY(recyclerY);
        for (int i = 0; i < this.mSuggestionsRecycler.getLayoutManager().getChildCount(); i++) {
            View itemView = this.mSuggestionsRecycler.getLayoutManager().getChildAt(i);
            itemView.setTranslationY(((1.0f - ratioSquare) * ((float) PixelMeasure.dp(32))) * ((float) i));
            itemView.setAlpha(0.25f + (0.75f * ratio));
        }
    }

    private boolean isRecyclerScrollable() {
        return ((LinearLayoutManager) this.mSuggestionsRecycler.getLayoutManager()).findLastCompletelyVisibleItemPosition() < this.mSuggestionsRecycler.getAdapter().getItemCount() + -1;
    }

    public static AsrDialogView showAsrDialog(Activity activity) {
        AsrDialogView asrDialogView = new AsrDialogView(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        asrDialogView.setLayoutParams(layoutParams);
        activity.addContentView(asrDialogView, layoutParams);
        return asrDialogView;
    }

    public static AsrDialogView showAsrUnavailableDialog(Activity activity) {
        AsrDialogView asrDialogView = new AsrDialogView(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        asrDialogView.setLayoutParams(layoutParams);
        asrDialogView.mIsUnavailableMode = true;
        asrDialogView.mWaveView.setVisibility(8);
        asrDialogView.setDisplayStrings();
        asrDialogView.scheduleAutoClose();
        activity.addContentView(asrDialogView, layoutParams);
        return asrDialogView;
    }

    public void onPauseListening() {
        post(new C32906());
    }

    public void onResumeListening() {
        post(new C32917());
    }

    public void onStopListening() {
        AsrSpeechRecognizer.getInstance().setListener(null);
        AppService.getMainActivity().getLayoutMgr().setAsrDialogView(null);
        ViewPropertyAnimatorHelper.initAnimation(this.mMainContainer).translationY((float) getMeasuredHeight()).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C32928()));
    }
}
