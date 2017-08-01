package com.waze.view.map;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.R;

public class ProgressAnimation extends RelativeLayout {
    private LayoutInflater inflater;

    public ProgressAnimation(Context context) {
        super(context);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.progress, this);
    }

    public ProgressAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.progress, this);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressAnimation);
        int srcRes = attrArray.getResourceId(1, 0);
        if (srcRes != 0) {
            ((ImageView) findViewById(C1283R.id.progressCircle)).setImageResource(srcRes);
        }
        int iconRes = attrArray.getResourceId(0, 0);
        if (iconRes != 0) {
            ((ImageView) findViewById(C1283R.id.progressWazer)).setImageResource(iconRes);
        }
    }

    public void start() {
        findViewById(C1283R.id.progressCircle).startAnimation(AnimationUtils.loadAnimation(getContext(), C1283R.anim.rotate_infinite));
    }

    public void stop() {
        findViewById(C1283R.id.progressCircle).clearAnimation();
    }

    public void hideWazer() {
        findViewById(C1283R.id.progressWazer).setVisibility(8);
    }
}
