package com.waze.view.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class SmartGridView extends GridView {
    public SmartGridView(Context context) {
        super(context);
    }

    public SmartGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SmartGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        if (getLayoutParams().height == -2) {
            heightSpec = MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE);
        } else {
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
