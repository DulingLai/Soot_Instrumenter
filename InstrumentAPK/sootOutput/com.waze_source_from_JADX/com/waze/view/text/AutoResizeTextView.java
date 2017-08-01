package com.waze.view.text;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.waze.R;

public class AutoResizeTextView extends WazeTextView {
    public static final float MIN_TEXT_SIZE = 15.0f;
    private static final String mEllipsis = "...";
    private static final Canvas sTextResizeCanvas = new Canvas();
    private boolean mAddEllipsis;
    private float mForcedMax;
    private float mMaxTextSize;
    private float mMinTextSize;
    private boolean mNeedsResize;
    private float mSpacingAdd;
    private float mSpacingMult;
    private OnTextResizeListener mTextResizeListener;
    private float mTextSize;

    public interface OnTextResizeListener {
        void onTextResize(TextView textView, float f, float f2);
    }

    public AutoResizeTextView(Context context) {
        this(context, null);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNeedsResize = false;
        this.mMaxTextSize = 0.0f;
        this.mForcedMax = 0.0f;
        this.mMinTextSize = MIN_TEXT_SIZE;
        this.mSpacingMult = 1.0f;
        this.mSpacingAdd = 0.0f;
        this.mAddEllipsis = true;
        this.mTextSize = getTextSize();
        setMinTextSize(context.obtainStyledAttributes(attrs, R.styleable.AutoResizeTextView).getDimension(0, MIN_TEXT_SIZE));
    }

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mNeedsResize = false;
        this.mMaxTextSize = 0.0f;
        this.mForcedMax = 0.0f;
        this.mMinTextSize = MIN_TEXT_SIZE;
        this.mSpacingMult = 1.0f;
        this.mSpacingAdd = 0.0f;
        this.mAddEllipsis = true;
        this.mTextSize = getTextSize();
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        this.mNeedsResize = true;
        requestLayout();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            resetTextSize();
            this.mNeedsResize = true;
        }
    }

    public void setOnResizeListener(OnTextResizeListener listener) {
        this.mTextResizeListener = listener;
    }

    public void setTextSize(float size) {
        super.setTextSize(size);
        this.mTextSize = getTextSize();
    }

    public void forceTextSize(float maxSize) {
        this.mForcedMax = maxSize;
    }

    public float getCalculatedTextSize() {
        return getPaint().getTextSize();
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        this.mTextSize = getTextSize();
    }

    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        this.mSpacingMult = mult;
        this.mSpacingAdd = add;
    }

    public void setMaxTextSize(float maxTextSize) {
        this.mMaxTextSize = maxTextSize;
        requestLayout();
        invalidate();
    }

    public float getMaxTextSize() {
        return this.mMaxTextSize;
    }

    public void setMinTextSize(float minTextSize) {
        this.mMinTextSize = minTextSize;
        requestLayout();
        invalidate();
    }

    public float getMinTextSize() {
        return this.mMinTextSize;
    }

    public void setAddEllipsis(boolean addEllipsis) {
        this.mAddEllipsis = addEllipsis;
    }

    public boolean getAddEllipsis() {
        return this.mAddEllipsis;
    }

    public void resetTextSize() {
        super.setTextSize(0, this.mTextSize);
        this.mMaxTextSize = this.mTextSize;
        if (this.mForcedMax != 0.0f) {
            this.mMaxTextSize = this.mForcedMax;
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed || this.mNeedsResize) {
            resizeText(((right - left) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), ((bottom - top) - getCompoundPaddingBottom()) - getCompoundPaddingTop());
        }
    }

    public void resizeText() {
        resizeText((getWidth() - getPaddingLeft()) - getPaddingRight(), (getHeight() - getPaddingBottom()) - getPaddingTop());
    }

    public void resizeText(int width, int height) {
        CharSequence text = getText();
        if (text != null && text.length() != 0 && height > 0 && width > 0 && this.mTextSize != 0.0f) {
            float targetTextSize;
            TextPaint textPaint = getPaint();
            float oldTextSize = textPaint.getTextSize();
            if (this.mMaxTextSize > 0.0f) {
                targetTextSize = Math.min(this.mTextSize, this.mMaxTextSize);
            } else {
                targetTextSize = this.mTextSize;
            }
            int textHeight = getTextHeight(text, textPaint, width, targetTextSize);
            while (true) {
                if ((textHeight == -1 || textHeight > height) && targetTextSize > this.mMinTextSize) {
                    targetTextSize = Math.max(targetTextSize - 1.0f, this.mMinTextSize);
                    textHeight = getTextHeight(text, textPaint, width, targetTextSize);
                }
            }
            if (!isInEditMode() && this.mAddEllipsis && targetTextSize == this.mMinTextSize && (textHeight > height || textHeight == -1)) {
                StaticLayout layout = new StaticLayout(text, textPaint, width, Alignment.ALIGN_NORMAL, this.mSpacingMult, this.mSpacingAdd, false);
                layout.draw(sTextResizeCanvas);
                int lastLine = layout.getLineForVertical(height) - 1;
                if (lastLine != -1) {
                    int start = layout.getLineStart(lastLine);
                    int end = layout.getLineEnd(lastLine);
                    float lineWidth = layout.getLineWidth(lastLine);
                    float ellipseWidth = textPaint.measureText(mEllipsis);
                    while (((float) width) < lineWidth + ellipseWidth && start <= end) {
                        end--;
                        lineWidth = textPaint.measureText(text.subSequence(start, end + 1).toString());
                    }
                    if (end < 0) {
                        end = 0;
                    }
                    text = text.subSequence(0, end) + mEllipsis;
                }
            }
            setText(text);
            textPaint.setTextSize(targetTextSize);
            setLineSpacing(this.mSpacingAdd, this.mSpacingMult);
            if (this.mTextResizeListener != null) {
                this.mTextResizeListener.onTextResize(this, oldTextSize, targetTextSize);
            }
            this.mNeedsResize = false;
        }
    }

    private int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
        if (isInEditMode()) {
            return -1;
        }
        paint.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(source, paint, width, Alignment.ALIGN_NORMAL, this.mSpacingMult, this.mSpacingAdd, true);
        layout.draw(sTextResizeCanvas);
        if (layout.getLineCount() > 1) {
            return -1;
        }
        return layout.getHeight();
    }
}
