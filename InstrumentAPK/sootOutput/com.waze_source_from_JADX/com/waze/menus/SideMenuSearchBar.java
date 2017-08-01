package com.waze.menus;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeSoundManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.SettingsNativeManager.SettingsSearchLangListener;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class SideMenuSearchBar extends FrameLayout {
    private ImageView mAddButton;
    private View mBackgroundView;
    private boolean mBackgroundVisible;
    private ImageView mCancelButton;
    private ImageView mClearSearchButton;
    private int mDictationMode;
    private boolean mIsDisabled;
    private SearchBarActionListener mListener;
    private ImageView mMagGlassImage;
    private Runnable mOnFocusAction;
    private EditText mSearchBox;
    private RelativeLayout mSearchBoxContainer;
    private ImageButton mSpeechButton;
    private boolean mSpeechButtonVisible;
    private TextWatcher mTextWatcher;
    private long mTouchDownTime;

    public interface SearchBarActionListener {
        void onAddClick() throws ;

        void onCancelClick() throws ;

        void onSearchButtonClick() throws ;

        void onSearchTextChanged(String str) throws ;

        void onSpeechButtonClick() throws ;
    }

    class C19571 implements OnClickListener {
        C19571() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK, "ACTION", "CANCEL");
            if (SideMenuSearchBar.this.mListener != null) {
                SideMenuSearchBar.this.mListener.onCancelClick();
            }
        }
    }

    class C19582 implements OnClickListener {
        C19582() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_CLICK, "ACTION", "VOICE_SEARCH");
            if (SideMenuSearchBar.this.mListener != null) {
                SideMenuSearchBar.this.mListener.onSpeechButtonClick();
            }
            SideMenuSearchBar.this.onSpeechRecognitionClick();
        }
    }

    class C19593 implements OnClickListener {
        C19593() throws  {
        }

        public void onClick(View v) throws  {
            SideMenuSearchBar.this.mSearchBox.setText("");
            SideMenuSearchBar.this.hideClearButton();
        }
    }

    class C19604 implements OnClickListener {
        C19604() throws  {
        }

        public void onClick(View v) throws  {
            if (SideMenuSearchBar.this.mListener != null) {
                SideMenuSearchBar.this.mListener.onAddClick();
            }
        }
    }

    class C19615 implements OnFocusChangeListener {
        C19615() throws  {
        }

        public void onFocusChange(View v, boolean $z0) throws  {
            Log.i("SideMenuSearchBar", "On Focus Change = " + $z0);
            if ($z0 && SideMenuSearchBar.this.mOnFocusAction != null) {
                SideMenuSearchBar.this.mOnFocusAction.run();
            }
        }
    }

    class C19626 implements OnEditorActionListener {
        C19626() throws  {
        }

        public boolean onEditorAction(TextView v, int $i0, KeyEvent $r2) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "SEARCH");
            if ($i0 != 3) {
                if ($r2 == null) {
                    return true;
                }
                if ($r2.getAction() != 1) {
                    return true;
                }
            }
            SideMenuSearchBar.this.mListener.onSearchButtonClick();
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_CLICK).addParam("TYPE", AnalyticsEvents.ANALYTICS_AUTOCOMPLETE_RETURN).addParam("QUERY", SideMenuSearchBar.this.mSearchBox.getText().toString()).send();
            return true;
        }
    }

    class C19637 implements TextWatcher {
        C19637() throws  {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) throws  {
            if (TextUtils.isEmpty(SideMenuSearchBar.this.mSearchBox.getText())) {
                SideMenuSearchBar.this.hideClearButton();
            } else {
                SideMenuSearchBar.this.showClearButton();
            }
            if (SideMenuSearchBar.this.mListener != null) {
                SideMenuSearchBar.this.mListener.onSearchTextChanged(SideMenuSearchBar.this.mSearchBox.getText().toString());
            }
        }

        public void afterTextChanged(Editable s) throws  {
        }
    }

    class C19648 implements Runnable {
        C19648() throws  {
        }

        public void run() throws  {
            SideMenuSearchBar.this.mBackgroundView.setVisibility(8);
        }
    }

    public SideMenuSearchBar(Context $r1) throws  {
        this($r1, null);
    }

    public SideMenuSearchBar(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SideMenuSearchBar(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mDictationMode = 0;
        View $r4 = LayoutInflater.from(getContext()).inflate(C1283R.layout.side_menu_search_bar, null);
        this.mSearchBox = (EditText) $r4.findViewById(C1283R.id.searchBox);
        this.mCancelButton = (ImageView) $r4.findViewById(C1283R.id.btnCancelSearch);
        this.mSpeechButton = (ImageButton) $r4.findViewById(C1283R.id.speechRecognition);
        this.mSearchBoxContainer = (RelativeLayout) $r4.findViewById(C1283R.id.searchBoxContainer);
        this.mClearSearchButton = (ImageView) $r4.findViewById(C1283R.id.btnClearSearch);
        this.mAddButton = (ImageView) $r4.findViewById(C1283R.id.btnAdd);
        this.mMagGlassImage = (ImageView) $r4.findViewById(C1283R.id.imgMagGlass);
        this.mCancelButton.setOnClickListener(new C19571());
        this.mSpeechButton.setOnClickListener(new C19582());
        this.mClearSearchButton.setOnClickListener(new C19593());
        this.mAddButton.setOnClickListener(new C19604());
        this.mSearchBox.setOnFocusChangeListener(new C19615());
        this.mSearchBox.setOnEditorActionListener(new C19626());
        this.mTextWatcher = new C19637();
        this.mSpeechButtonVisible = true;
        this.mBackgroundView = new View(getContext());
        this.mBackgroundView.setLayoutParams(new LayoutParams(-1, -1));
        this.mBackgroundView.setBackgroundResource(C1283R.drawable.middle_idle_nav);
        this.mBackgroundVisible = true;
        addView(this.mBackgroundView);
        addView($r4);
        setPadding(0, 0, 0, 0);
    }

    public void setDictationMode(int $i0) throws  {
        this.mDictationMode = $i0;
    }

    public void showBackground(boolean $z0) throws  {
        this.mBackgroundView.setVisibility(0);
        this.mBackgroundVisible = true;
        if (VERSION.SDK_INT >= 21) {
            this.mSearchBox.setElevation(0.0f);
            this.mSearchBox.setAlpha(1.0f);
        }
        if ($z0) {
            this.mBackgroundView.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mBackgroundView).alpha(1.0f).setListener(null);
        } else {
            this.mBackgroundView.setAlpha(1.0f);
        }
        onDayNightChange();
    }

    public void hideBackground(boolean $z0) throws  {
        C19648 $r1 = new C19648();
        if (VERSION.SDK_INT >= 21) {
            this.mSearchBox.setElevation((float) PixelMeasure.dp(0));
            this.mSearchBox.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
            this.mSearchBox.setAlpha(0.9f);
        }
        this.mBackgroundVisible = false;
        if ($z0) {
            this.mBackgroundView.setAlpha(1.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mBackgroundView).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener($r1));
        } else {
            $r1.run();
        }
        onDayNightChange();
    }

    public void onDayNightChange() throws  {
        final boolean $z0 = this.mBackgroundVisible || DriveToNativeManager.getInstance().isDayMode();
        post(new Runnable() {
            public void run() throws  {
                SideMenuSearchBar.this.mBackgroundView.setBackgroundDrawable(new ColorDrawable(SideMenuSearchBar.this.getResources().getColor($z0 ? C1283R.color.White : C1283R.color.DarkBlue)));
                int $i0 = $z0 ? C1283R.drawable.search_box : C1283R.drawable.search_box_night;
                int $i1 = $z0 ? -4929073 : SideMenuSearchBar.this.getResources().getColor(C1283R.color.Light);
                int $i2 = $z0 ? -16777216 : SideMenuSearchBar.this.getResources().getColor(C1283R.color.PassiveGrey);
                SideMenuSearchBar.this.mSearchBox.setBackgroundResource($i0);
                SideMenuSearchBar.this.mSearchBox.setHintTextColor($i1);
                SideMenuSearchBar.this.mSearchBox.setTextColor($i2);
            }
        });
    }

    public void setSearchBarActionListener(SearchBarActionListener $r1) throws  {
        this.mListener = $r1;
    }

    public void setHint(String $r1) throws  {
        this.mSearchBox.setHint($r1);
    }

    public void setSpeechButtonVisibility(boolean $z0) throws  {
        this.mSpeechButtonVisible = $z0;
        if (!this.mSpeechButtonVisible) {
            this.mSpeechButton.setVisibility(8);
        }
    }

    public void setAddButtonVisibility(boolean $z0) throws  {
        int $i0 = 0;
        this.mAddButton.setVisibility($z0 ? (byte) 0 : (byte) 8);
        RelativeLayout.LayoutParams $r4 = (RelativeLayout.LayoutParams) this.mSearchBoxContainer.getLayoutParams();
        if ($z0) {
            $i0 = PixelMeasure.dp(48);
        }
        $r4.leftMargin = $i0;
        this.mSearchBoxContainer.setLayoutParams($r4);
    }

    public void showCancelButton(long $l0, Interpolator $r1) throws  {
        int $i1 = PixelMeasure.dp(48);
        this.mCancelButton.setVisibility(0);
        this.mCancelButton.setTranslationX((float) (-$i1));
        ViewPropertyAnimatorHelper.initAnimation(this.mCancelButton, $l0, $r1).translationX(0.0f).setListener(null);
        ViewPropertyAnimatorHelper.initAnimation(this.mSpeechButton, $l0, $r1).scaleX(0.7f).scaleY(0.7f);
        if (this.mAddButton.getVisibility() != 0) {
            ValueAnimator $r6 = ValueAnimator.ofInt(new int[]{0, $i1});
            $r6.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator $r1) throws  {
                    RelativeLayout.LayoutParams $r5 = (RelativeLayout.LayoutParams) SideMenuSearchBar.this.mSearchBoxContainer.getLayoutParams();
                    $r5.leftMargin = ((Integer) $r1.getAnimatedValue()).intValue();
                    SideMenuSearchBar.this.mSearchBoxContainer.setLayoutParams($r5);
                }
            });
            $r6.setInterpolator($r1);
            $r6.setDuration($l0);
            $r6.start();
            return;
        }
        ViewPropertyAnimatorHelper.initAnimation(this.mAddButton).translationX((float) (-$i1));
    }

    public void hideCancelButton(long $l0, Interpolator $r1) throws  {
        ViewPropertyAnimatorHelper.initAnimation(this.mCancelButton, $l0, $r1).translationX((float) (-PixelMeasure.dp(48))).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mCancelButton));
        ViewPropertyAnimatorHelper.initAnimation(this.mSpeechButton, $l0, $r1).scaleX(1.0f).scaleY(1.0f);
        if (this.mAddButton.getVisibility() != 0) {
            ValueAnimator $r7 = ValueAnimator.ofInt(new int[]{$i1, 0});
            $r7.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator $r1) throws  {
                    RelativeLayout.LayoutParams $r5 = (RelativeLayout.LayoutParams) SideMenuSearchBar.this.mSearchBoxContainer.getLayoutParams();
                    $r5.leftMargin = ((Integer) $r1.getAnimatedValue()).intValue();
                    SideMenuSearchBar.this.mSearchBoxContainer.setLayoutParams($r5);
                }
            });
            $r7.setInterpolator($r1);
            $r7.setDuration($l0);
            $r7.start();
        } else {
            ViewPropertyAnimatorHelper.initAnimation(this.mAddButton).translationX(0.0f);
        }
        this.mSearchBox.setText("");
        hideClearButton();
    }

    private void showClearButton() throws  {
        if (this.mClearSearchButton.getVisibility() != 0) {
            this.mClearSearchButton.setVisibility(0);
            this.mClearSearchButton.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mClearSearchButton).alpha(1.0f).setListener(null);
            if (this.mSpeechButtonVisible) {
                ViewPropertyAnimatorHelper.initAnimation(this.mSpeechButton).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mSpeechButton));
            }
        }
    }

    private void hideClearButton() throws  {
        if (this.mSpeechButton.getVisibility() != 0) {
            if (this.mSpeechButtonVisible) {
                this.mSpeechButton.setVisibility(0);
                this.mSpeechButton.setAlpha(0.0f);
                ViewPropertyAnimatorHelper.initAnimation(this.mSpeechButton).alpha(1.0f).setListener(null);
            }
            ViewPropertyAnimatorHelper.initAnimation(this.mClearSearchButton).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createInvisibleWhenDoneListener(this.mClearSearchButton));
        }
    }

    public void clearSearchFocus() throws  {
        this.mSearchBox.clearFocus();
        this.mSearchBox.removeTextChangedListener(this.mTextWatcher);
        this.mMagGlassImage.setVisibility(0);
        this.mSearchBox.setPadding(PixelMeasure.dp(48), this.mSearchBox.getPaddingTop(), this.mSearchBox.getPaddingRight(), this.mSearchBox.getPaddingBottom());
        hideKeyboard();
    }

    public void hideKeyboard() throws  {
        if (!isInEditMode()) {
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mSearchBox.getWindowToken(), 0);
        }
    }

    public void obtainSearchFocus(boolean $z0) throws  {
        if (!isInEditMode()) {
            if (!$z0) {
                EditTextUtils.checkTypeLockOnCreate(getContext(), this.mSearchBox);
            }
            this.mSearchBox.addTextChangedListener(this.mTextWatcher);
            this.mMagGlassImage.setVisibility(8);
            this.mSearchBox.setPadding(PixelMeasure.dp(16), this.mSearchBox.getPaddingTop(), this.mSearchBox.getPaddingRight(), this.mSearchBox.getPaddingBottom());
            postDelayed(new Runnable() {
                public void run() throws  {
                    ((InputMethodManager) SideMenuSearchBar.this.getContext().getSystemService("input_method")).toggleSoftInputFromWindow(SideMenuSearchBar.this.mSearchBox.getApplicationWindowToken(), 2, 0);
                    SideMenuSearchBar.this.mSearchBox.requestFocus();
                }
            }, 200);
        }
    }

    public void clearText() throws  {
        this.mSearchBox.setText("");
    }

    public void disableFocus() throws  {
        this.mIsDisabled = true;
        clearSearchFocus();
    }

    public void enableFocus(boolean $z0) throws  {
        this.mIsDisabled = false;
        obtainSearchFocus($z0);
    }

    public boolean onInterceptTouchEvent(MotionEvent $r1) throws  {
        if (!this.mIsDisabled) {
            return super.onInterceptTouchEvent($r1);
        }
        if ($r1.getAction() == 0) {
            this.mTouchDownTime = System.currentTimeMillis();
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        if (this.mIsDisabled && $r1.getAction() == 1 && System.currentTimeMillis() - this.mTouchDownTime <= 300) {
            Rect $r2 = new Rect();
            this.mTouchDownTime = 0;
            if (this.mAddButton.getVisibility() == 0) {
                this.mAddButton.getHitRect($r2);
                if ($r2.contains((int) $r1.getX(), (int) $r1.getY())) {
                    this.mAddButton.performClick();
                    return true;
                }
            }
            if (this.mSpeechButtonVisible) {
                ImageButton $r4 = this.mSpeechButton;
                $r4.getHitRect($r2);
                if ($r2.contains((int) $r1.getX(), (int) $r1.getY())) {
                    if (this.mListener != null) {
                        SearchBarActionListener $r5 = this.mListener;
                        $r5.onSpeechButtonClick();
                    }
                    onSpeechRecognitionClick();
                    return true;
                }
            }
        }
        return super.onTouchEvent($r1);
    }

    public void onSpeechRecognitionClick() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "VOICE_SEARCH");
        if (NativeSoundManager.getInstance().isAsrV2Enabled()) {
            Log.d(Logger.TAG, "Speech recognition button tapped - using app ASR (v2).");
            NativeSoundManager.getInstance().beginAsrDictationSession(this.mDictationMode);
            hideKeyboard();
            return;
        }
        Log.d(Logger.TAG, "Speech recognition button tapped - using native ASR.");
        SettingsNativeManager.getInstance().getSearchLanguageTag(new SettingsSearchLangListener() {
            public void onComplete(String $r3) throws  {
                try {
                    Intent $r2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                    $r2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                    if ($r3 == null || $r3.isEmpty()) {
                        Logger.m38e("SettingsVoiceSearchActivity: Config for search language null or empty. Not setting lang for speech recog");
                    } else {
                        $r2.putExtra("android.speech.extra.LANGUAGE", $r3);
                    }
                    AnalyticsBuilder $r4 = AnalyticsBuilder.analytics("VOICE_SEARCH");
                    if ($r3 == null) {
                        $r3 = "";
                    }
                    $r4.addParam(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_BY_VOICE_LANGUAGE, $r3).addParam(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_BY_VOICE_AUTO_SELECTED, SettingsNativeManager.getInstance().getCurrentSearchVoiceIsAutoNTV() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
                    AppService.getActiveActivity().startActivityForResult($r2, 1234);
                } catch (Exception $r1) {
                    Logger.m39e("onSpeechRecognitionClick exception", $r1);
                    NativeManager $r7 = NativeManager.getInstance();
                    MsgBox.openMessageBox($r7.getLanguageString((int) DisplayStrings.DS_SPEECH_ACTIVITY_NOT_FOUND_TITLE), $r7.getLanguageString((int) DisplayStrings.DS_SPEECH_ACTIVITY_NOT_FOUND_CONTENT), false);
                }
            }
        });
    }

    public void setOnFocusAction(Runnable $r1) throws  {
        this.mOnFocusAction = $r1;
    }

    public String getSearchTerm() throws  {
        return this.mSearchBox.getText().toString();
    }

    public void setSearchTerm(String $r1, boolean isVoiceSearch) throws  {
        this.mSearchBox.setText("");
        this.mSearchBox.append($r1);
    }
}
