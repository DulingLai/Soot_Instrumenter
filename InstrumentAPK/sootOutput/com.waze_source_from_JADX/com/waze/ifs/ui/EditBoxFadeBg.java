package com.waze.ifs.ui;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import com.waze.view.text.WazeEditText;

public class EditBoxFadeBg extends WazeEditText {
    private static final int ALPHA_OFF = 89;
    private static final int ALPHA_ON = 255;
    private static final int BASIC_SWITCH_DURATION = 200;
    private boolean mHasFocus;

    class C17131 implements TextWatcher {
        C17131() throws  {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) throws  {
            EditBoxFadeBg.this.setGravity(19);
        }

        public void afterTextChanged(Editable s) throws  {
        }
    }

    class C17142 implements AnimatorUpdateListener {
        C17142() throws  {
        }

        public void onAnimationUpdate(ValueAnimator $r1) throws  {
            EditBoxFadeBg.this.getBackground().setAlpha(((Integer) $r1.getAnimatedValue()).intValue());
        }
    }

    public EditBoxFadeBg(Context $r1) throws  {
        super($r1);
        init();
    }

    public EditBoxFadeBg(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        init();
    }

    public EditBoxFadeBg(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    @TargetApi(21)
    public EditBoxFadeBg(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super($r1, $r2, $i0, $i1);
        init();
    }

    private void init() throws  {
        getBackground().setAlpha(89);
        this.mHasFocus = false;
        setGravity(19);
        addTextChangedListener(new C17131());
    }

    protected void onFocusChanged(boolean $z0, int $i0, Rect $r1) throws  {
        super.onFocusChanged($z0, $i0, $r1);
        if (this.mHasFocus != $z0) {
            this.mHasFocus = $z0;
            if (isShown()) {
                animateSwitch();
            } else {
                getBackground().setAlpha(this.mHasFocus ? (short) 255 : (short) 89);
            }
        }
    }

    private void animateSwitch() throws  {
        ValueAnimator $r1 = new ValueAnimator();
        $r1.setDuration(200);
        $r1.setInterpolator(new LinearInterpolator());
        if (this.mHasFocus) {
            $r1.setIntValues(new int[]{89, 255});
        } else {
            $r1.setIntValues(new int[]{255, 89});
        }
        $r1.addUpdateListener(new C17142());
        $r1.start();
    }
}
