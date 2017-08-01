package com.waze.ifs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.map.CanvasFont;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;

public class WazeCheckBoxView extends RelativeLayout {
    private static final int CHECK_MARK_DURATION = 70;
    private static final int MORPH_DURATION = 180;
    private LayoutInflater inflater;
    private ImageView mCheckBoxBgOff;
    private ImageView mCheckBoxBgOn;
    private ImageView mCheckBoxFiller;
    private CheckBoxModes mCheckBoxMode;
    private ImageView mCheckBoxV;
    private float mDensity;
    SettingsToggleCallback mListener;

    private enum CheckBoxModes {
        CHECKED,
        UNCHECKED
    }

    abstract class JustCallNextAnimationListener implements AnimationListener {
        abstract void nextStep() throws ;

        JustCallNextAnimationListener() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            nextStep();
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    public WazeCheckBoxView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.inflater = LayoutInflater.from($r1);
        this.inflater.inflate(C1283R.layout.waze_checkbox, this);
        initMemebers();
    }

    public WazeCheckBoxView(Context $r1) throws  {
        super($r1);
        this.inflater = LayoutInflater.from($r1);
        this.inflater.inflate(C1283R.layout.waze_checkbox, this);
        initMemebers();
    }

    public void setValue(boolean $z0) throws  {
        setValue($z0, false);
    }

    public void setValue(boolean $z0, boolean $z1) throws  {
        CheckBoxModes $r1;
        if ($z0) {
            $r1 = CheckBoxModes.CHECKED;
        } else {
            $r1 = CheckBoxModes.UNCHECKED;
        }
        if (this.mCheckBoxMode == $r1) {
            setCheckBox(this.mCheckBoxMode);
            return;
        }
        this.mCheckBoxMode = $r1;
        if ($z1) {
            animateCheckBox(this.mCheckBoxMode);
        } else {
            setCheckBox(this.mCheckBoxMode);
        }
    }

    public boolean isChecked() throws  {
        return this.mCheckBoxMode == CheckBoxModes.CHECKED;
    }

    public void setOnChecked(SettingsToggleCallback $r1) throws  {
        this.mListener = $r1;
    }

    public void toggle() throws  {
        this.mCheckBoxMode = isChecked() ? CheckBoxModes.UNCHECKED : CheckBoxModes.CHECKED;
        animateCheckBox(this.mCheckBoxMode);
        if (this.mListener != null) {
            this.mListener.onToggle(isChecked());
        }
    }

    private void setCheckBox(CheckBoxModes $r1) throws  {
        this.mCheckBoxV.clearAnimation();
        this.mCheckBoxBgOn.clearAnimation();
        this.mCheckBoxFiller.clearAnimation();
        this.mCheckBoxFiller.setVisibility(4);
        switch ($r1) {
            case CHECKED:
                this.mCheckBoxV.setVisibility(0);
                this.mCheckBoxBgOn.setVisibility(0);
                return;
            default:
                this.mCheckBoxV.setVisibility(4);
                this.mCheckBoxBgOn.setVisibility(4);
                return;
        }
    }

    private void animateCheckBox(CheckBoxModes $r1) throws  {
        changeBgAlpha($r1);
        shrinkBox($r1);
        fadeCheckBoxV($r1);
    }

    private void fadeCheckBoxV(CheckBoxModes $r1) throws  {
        AnimationSet $r2 = new AnimationSet(true);
        this.mCheckBoxV.setVisibility(0);
        switch ($r1) {
            case CHECKED:
                $r2.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                $r2.setStartOffset(180);
                break;
            default:
                $r2.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                $r2.setStartOffset(0);
                break;
        }
        $r2.setDuration(70);
        $r2.setFillAfter(true);
        $r2.setInterpolator(new DecelerateInterpolator());
        this.mCheckBoxV.startAnimation($r2);
    }

    private void changeBgAlpha(CheckBoxModes $r1) throws  {
        AlphaAnimation $r3;
        switch ($r1) {
            case CHECKED:
                $r3 = new AlphaAnimation(0.0f, 1.0f);
                $r3.setStartOffset(0);
                break;
            default:
                $r3 = new AlphaAnimation(1.0f, 0.0f);
                $r3.setStartOffset(70);
                break;
        }
        $r3.setDuration(180);
        $r3.setFillAfter(true);
        this.mCheckBoxBgOn.setVisibility(0);
        this.mCheckBoxBgOn.startAnimation($r3);
    }

    private void shrinkBox(CheckBoxModes $r1) throws  {
        AnimationSet $r2 = new AnimationSet(true);
        switch ($r1) {
            case CHECKED:
                $r2.addAnimation(new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                $r2.setInterpolator(new AccelerateInterpolator());
                break;
            default:
                $r2.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                $r2.setStartOffset(70);
                $r2.setInterpolator(new DecelerateInterpolator());
                break;
        }
        $r2.setDuration(180);
        $r2.setFillAfter(true);
        ImageView $r6 = this.mCheckBoxFiller;
        $r6.startAnimation($r2);
    }

    private void initMemebers() throws  {
        this.mCheckBoxBgOn = (ImageView) findViewById(C1283R.id.checkbox_bg_on);
        this.mCheckBoxBgOff = (ImageView) findViewById(C1283R.id.checkbox_bg_off);
        this.mCheckBoxFiller = (ImageView) findViewById(C1283R.id.checkbox_filler);
        this.mCheckBoxV = (ImageView) findViewById(C1283R.id.checkbox_v);
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
    }
}
