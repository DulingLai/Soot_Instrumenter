package com.waze.share;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.MyWazeDataHandler;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.text.WazeEditText;
import com.waze.view.text.WazeEditText.OnBackPressedListener;

public class NameYourselfView extends FrameLayout {
    private boolean mAdjustForKeyboard;
    private TextView mDescriptionLabel;
    private EditText mFirstNameEditText;
    private boolean mHasFocus;
    private boolean mIsHiding;
    private boolean mIsValid;
    private EditText mLastNameEditText;
    private NameYourselfViewListener mListener;
    private View mMainContent;
    private boolean mNameWasChanged;
    private FrameLayout mNextButton;
    private TextView mNextLabel;
    private TextView mTitleLabel;

    public interface NameYourselfViewListener {
        void onNameYourselfClosed(boolean z);
    }

    class C28131 implements TextWatcher {
        C28131() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            NameYourselfView.this.validateTextFields();
        }
    }

    class C28152 implements OnFocusChangeListener {

        class C28141 implements Runnable {
            C28141() {
            }

            public void run() {
                NameYourselfView.this.onKeyboardShown(NameYourselfView.this.mHasFocus);
            }
        }

        C28152() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            NameYourselfView.this.mHasFocus = hasFocus;
            NameYourselfView.this.postDelayed(new C28141(), 20);
        }
    }

    class C28163 implements OnEditorActionListener {
        C28163() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == 4) {
                NameYourselfView.this.onNextClick();
                return true;
            } else if (actionId != 5 || !NameYourselfView.this.mIsValid) {
                return false;
            } else {
                NameYourselfView.this.mFirstNameEditText.clearFocus();
                NameYourselfView.this.mLastNameEditText.clearFocus();
                ((InputMethodManager) NameYourselfView.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(NameYourselfView.this.mFirstNameEditText.getWindowToken(), 0);
                NameYourselfView.this.onKeyboardShown(false);
                return false;
            }
        }
    }

    class C28174 implements OnBackPressedListener {
        C28174() {
        }

        public void onBackPressed() {
            NameYourselfView.this.reactToBackButton();
        }
    }

    class C28185 implements OnClickListener {
        C28185() {
        }

        public void onClick(View v) {
        }
    }

    class C28196 implements OnClickListener {
        C28196() {
        }

        public void onClick(View v) {
            NameYourselfView.this.close();
        }
    }

    class C28207 implements OnClickListener {
        C28207() {
        }

        public void onClick(View v) {
            NameYourselfView.this.close();
        }
    }

    class C28218 implements OnClickListener {
        C28218() {
        }

        public void onClick(View v) {
            NameYourselfView.this.onNextClick();
        }
    }

    public NameYourselfView(Context context) {
        this(context, null);
    }

    public NameYourselfView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NameYourselfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.name_yourself_layout, null);
        this.mTitleLabel = (TextView) content.findViewById(C1283R.id.lblTitle);
        this.mDescriptionLabel = (TextView) content.findViewById(C1283R.id.lblDescription);
        this.mNextLabel = (TextView) content.findViewById(C1283R.id.lblNext);
        this.mFirstNameEditText = (EditText) content.findViewById(C1283R.id.editTextFirstName);
        this.mLastNameEditText = (EditText) content.findViewById(C1283R.id.editTextLastName);
        this.mNextButton = (FrameLayout) content.findViewById(C1283R.id.btnNext);
        this.mMainContent = content.findViewById(C1283R.id.mainContent);
        this.mNameWasChanged = false;
        TextWatcher textWatcher = new C28131();
        OnFocusChangeListener focusChangeListener = new C28152();
        OnEditorActionListener editorActionListener = new C28163();
        OnBackPressedListener backPressedListener = new C28174();
        this.mMainContent.setOnClickListener(new C28185());
        setOnClickListener(new C28196());
        content.findViewById(C1283R.id.btnClose).setOnClickListener(new C28207());
        this.mFirstNameEditText.addTextChangedListener(textWatcher);
        this.mLastNameEditText.addTextChangedListener(textWatcher);
        this.mFirstNameEditText.setOnFocusChangeListener(focusChangeListener);
        this.mLastNameEditText.setOnFocusChangeListener(focusChangeListener);
        this.mFirstNameEditText.setOnEditorActionListener(editorActionListener);
        this.mLastNameEditText.setOnEditorActionListener(editorActionListener);
        ((WazeEditText) this.mFirstNameEditText).setOnBackPressedListener(backPressedListener);
        ((WazeEditText) this.mLastNameEditText).setOnBackPressedListener(backPressedListener);
        this.mNextButton.setOnClickListener(new C28218());
        LayoutParams params = (LayoutParams) this.mMainContent.getLayoutParams();
        params.width = Math.min(PixelMeasure.dp(304), getResources().getDisplayMetrics().widthPixels - PixelMeasure.dp(64));
        this.mMainContent.setLayoutParams(params);
        addView(content);
        validateTextFields();
        setDisplayStrings();
    }

    public void adjustForKeyboard(boolean adjustForKeyboard) {
        this.mAdjustForKeyboard = adjustForKeyboard;
    }

    private void onKeyboardShown(boolean isShown) {
        if (this.mAdjustForKeyboard) {
            ViewPropertyAnimatorHelper.initAnimation(this.mMainContent).translationY((float) (isShown ? (-getMeasuredHeight()) / 3 : 0));
        }
    }

    public void setFields(String firstName, String lastName) {
        if (!TextUtils.isEmpty(firstName)) {
            this.mFirstNameEditText.setText(firstName);
        }
        if (!TextUtils.isEmpty(lastName)) {
            this.mLastNameEditText.setText(lastName);
        }
        validateTextFields();
    }

    public void setListener(NameYourselfViewListener listener) {
        this.mListener = listener;
    }

    private void onNextClick() {
        if (this.mNextButton.isEnabled() && !this.mIsHiding) {
            final String firstName = this.mFirstNameEditText.getText().toString();
            final String lastName = this.mLastNameEditText.getText().toString();
            NativeManager.Post(new Runnable() {
                public void run() {
                    MyWazeNativeManager.getInstance().setFirstLastNamesNTV(firstName, lastName);
                }
            });
            this.mNameWasChanged = true;
            close();
        }
    }

    public void reactToBackButton() {
        if (this.mFirstNameEditText.hasFocus() || this.mLastNameEditText.hasFocus()) {
            this.mFirstNameEditText.clearFocus();
            this.mLastNameEditText.clearFocus();
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mFirstNameEditText.getWindowToken(), 0);
            onKeyboardShown(false);
            return;
        }
        close();
    }

    public void close() {
        if (!this.mIsHiding) {
            this.mIsHiding = true;
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mFirstNameEditText.getWindowToken(), 0);
            ViewPropertyAnimatorHelper.initAnimation(this).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new Runnable() {
                public void run() {
                    ((ViewGroup) NameYourselfView.this.getParent()).removeView(NameYourselfView.this);
                    if (NameYourselfView.this.mListener != null) {
                        NameYourselfView.this.mListener.onNameYourselfClosed(NameYourselfView.this.mNameWasChanged);
                    }
                }
            }));
        }
    }

    private void validateTextFields() {
        boolean isValid = (TextUtils.isEmpty(this.mFirstNameEditText.getText().toString()) || TextUtils.isEmpty(this.mLastNameEditText.getText().toString())) ? false : true;
        this.mNextButton.setAlpha(isValid ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mNextButton.setEnabled(isValid);
        this.mIsValid = isValid;
    }

    private void setDisplayStrings() {
        this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_LET_YOUR_FRIEND_KNOW_ITS_YOU));
        this.mDescriptionLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_TO_CONTINUE_ENTER_NAME));
        this.mNextLabel.setText(DisplayStrings.displayString(357));
        this.mFirstNameEditText.setHint(DisplayStrings.displayString(DisplayStrings.DS_FIRST_NAME));
        this.mLastNameEditText.setHint(DisplayStrings.displayString(DisplayStrings.DS_LAST_NAME));
    }

    public static NameYourselfView showNameYourselfView(Activity activity, String existingFirstName, String existingLastName, NameYourselfViewListener listener) {
        NameYourselfView nameYourselfView = new NameYourselfView(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        nameYourselfView.setLayoutParams(layoutParams);
        nameYourselfView.setListener(listener);
        nameYourselfView.setFields(existingFirstName, existingLastName);
        activity.addContentView(nameYourselfView, layoutParams);
        nameYourselfView.setAlpha(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(nameYourselfView).alpha(1.0f);
        return nameYourselfView;
    }

    public static NameYourselfView showNameYourselfView(Activity activity, String existingFirstName, String existingLastName, final Runnable onSuccessAction) {
        return showNameYourselfView(activity, existingFirstName, existingLastName, new NameYourselfViewListener() {
            public void onNameYourselfClosed(boolean nameWasChanged) {
                if (nameWasChanged && onSuccessAction != null) {
                    onSuccessAction.run();
                }
            }
        });
    }

    public static void showNameYourselfViewIfNeeded(final Activity activity, final NameYourselfView[] viewRef, final NameYourselfViewListener listener, final Runnable onReferenceCreatedAction) {
        MyWazeNativeManager.getInstance().getMyWazeData(new MyWazeDataHandler() {
            public void onMyWazeDataReceived(MyWazeData data) {
                String firstName = data.mFirstName;
                String lastName = data.mLastName;
                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                    NameYourselfView view = NameYourselfView.showNameYourselfView(activity, firstName, lastName, listener);
                    if (viewRef != null && viewRef.length > 0) {
                        viewRef[0] = view;
                        if (onReferenceCreatedAction != null) {
                            onReferenceCreatedAction.run();
                        }
                    }
                } else if (listener != null) {
                    listener.onNameYourselfClosed(true);
                }
            }
        });
    }
}
