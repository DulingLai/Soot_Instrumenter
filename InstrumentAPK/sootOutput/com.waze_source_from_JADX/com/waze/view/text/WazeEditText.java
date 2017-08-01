package com.waze.view.text;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.waze.AppService;
import com.waze.R;
import com.waze.utils.EditTextUtils;
import com.waze.utils.EditTextUtils.OnTouchListenerDoneListener;

public class WazeEditText extends EditText implements OnTouchListenerDoneListener {
    OnTouchListener mKeyboardLockOnTouchListener;
    private OnBackPressedListener mOnBackPressedListener = null;
    OnClickListener mOnClickListener;
    int nType = 0;
    boolean selectionAllowed = true;

    public interface OnBackPressedListener {
        void onBackPressed();
    }

    class C32711 implements TextWatcher {
        C32711() {
        }

        public void onTextChanged(CharSequence aSeq, int aStart, int aBefore, int aCount) {
            if (aSeq.length() == 1) {
                AppService.getNativeManager().EditBoxCheckTypingLock(WazeEditText.this);
            }
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        public void afterTextChanged(Editable arg0) {
        }
    }

    public WazeEditText(Context context) {
        super(context);
        setFont(context);
        init();
    }

    public WazeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.nType = context.obtainStyledAttributes(attrs, R.styleable.WazeTextView).getInteger(0, 1);
        setFont(context);
        init();
    }

    public WazeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.nType = context.obtainStyledAttributes(attrs, R.styleable.WazeTextView).getInteger(0, 1);
        setFont(context);
        init();
    }

    @TargetApi(21)
    public WazeEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.nType = context.obtainStyledAttributes(attrs, R.styleable.WazeTextView).getInteger(0, 1);
        setFont(context);
        init();
    }

    private void init() {
        this.mKeyboardLockOnTouchListener = EditTextUtils.getKeyboardLockOnTouchListener(getContext(), this, this);
        addTextChangedListener(new C32711());
    }

    public void onDone() {
    }

    private void setFont(Context context) {
        int style = 0;
        boolean isLight = false;
        if (getTypeface() != null) {
            if (getTypeface().isBold()) {
                style = 1;
            }
            if (getTypeface().isItalic()) {
                isLight = true;
            }
        }
        Typeface font = getTypeface();
        if (!isInEditMode()) {
            font = WazeTextView.loadTypeface(context, this.nType, isLight, font);
        }
        setTypeface(font, style);
    }

    public void setOnBackPressedListener(OnBackPressedListener mOnBackPressedListener) {
        this.mOnBackPressedListener = mOnBackPressedListener;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (this.mOnBackPressedListener == null || event.getAction() != 0 || keyCode != 4) {
            return super.onKeyPreIme(keyCode, event);
        }
        this.mOnBackPressedListener.onBackPressed();
        return true;
    }

    public void setMyOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0 && this.mOnClickListener != null) {
            this.mOnClickListener.onClick(this);
        }
        this.mKeyboardLockOnTouchListener.onTouch(this, event);
        return super.onTouchEvent(event);
    }

    public void onSelectionChanged(int start, int end) {
        if (!this.selectionAllowed) {
            CharSequence text = getText();
            if (!(text == null || (start == text.length() && end == text.length()))) {
                setSelection(text.length(), text.length());
                return;
            }
        }
        super.onSelectionChanged(start, end);
    }

    public void setSelectionAllowed(boolean allowed) {
        this.selectionAllowed = allowed;
    }
}
