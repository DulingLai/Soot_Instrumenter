package com.waze.view.text;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build.VERSION;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import com.waze.LayoutManager;
import com.waze.utils.PixelMeasure;

public class CharacterPlaceholderEditText extends WazeEditText {
    private int mCharLimit;
    private Paint mPaint;

    public CharacterPlaceholderEditText(Context context) {
        this(context, null);
    }

    public CharacterPlaceholderEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CharacterPlaceholderEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @TargetApi(21)
    public CharacterPlaceholderEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mPaint.setColor(-16777216);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth((float) PixelMeasure.dp(2));
        setWillNotDraw(false);
    }

    public void setCharacterLimit(int characterLimit) {
        this.mCharLimit = characterLimit;
        setFilters(new InputFilter[]{new LengthFilter(characterLimit)});
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (VERSION.SDK_INT >= 21) {
            int currentX = 0;
            int textLength = getText().length();
            float letterLength = getTextSize() * 0.82f;
            float letterSpacing = getLetterSpacing();
            for (int i = 0; i < this.mCharLimit; i++) {
                currentX = (int) (((float) currentX) + ((letterSpacing * letterLength) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
                if (i >= textLength) {
                    canvas.drawLine((float) currentX, (float) getMeasuredHeight(), ((float) currentX) + letterLength, (float) getMeasuredHeight(), this.mPaint);
                }
                currentX = (int) (((float) ((int) (((float) currentX) + letterLength))) + ((letterSpacing * letterLength) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
            }
        }
    }
}
