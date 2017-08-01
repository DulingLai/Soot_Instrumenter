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
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.map.CanvasFont;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.view.button.PillButton;
import com.waze.view.popups.EndorseRiderDialog;

public class WazeSwitchView extends RelativeLayout {
    private static final int BASIC_SWITCH_DURATION = 100;
    private static final int BOX_RETURN_FROM_STRETCH_DURATION = 40;
    private static final int BOX_STRETCH_DURATION = 60;
    private static final int TRANSALATE_DP = 34;
    private LayoutInflater inflater;
    private float mDensity;
    private SettingsToggleCallback mListener;
    private ImageView mSwitchBgOff;
    private ImageView mSwitchBgOn;
    private ImageView mSwitchHandle;
    private SwitchModes mSwitchMode;
    private ImageView mSwitchV;

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

    private enum SwitchModes {
        CHECKED,
        UNCHECKED
    }

    public WazeSwitchView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.inflater = LayoutInflater.from($r1);
        this.inflater.inflate(C1283R.layout.waze_switch, this);
        initMemebers();
    }

    public WazeSwitchView(Context $r1) throws  {
        super($r1);
        this.inflater = LayoutInflater.from($r1);
        this.inflater.inflate(C1283R.layout.waze_switch, this);
        initMemebers();
    }

    public void setValue(boolean $z0) throws  {
        SwitchModes $r2;
        if ($z0) {
            $r2 = SwitchModes.CHECKED;
        } else {
            $r2 = SwitchModes.UNCHECKED;
        }
        this.mSwitchMode = $r2;
        setSwitch(this.mSwitchMode);
        this.mSwitchBgOn.clearAnimation();
        this.mSwitchBgOn.setVisibility($z0 ? (byte) 0 : (byte) 4);
    }

    public boolean isChecked() throws  {
        return this.mSwitchMode == SwitchModes.CHECKED;
    }

    public void setOnChecked(SettingsToggleCallback $r1) throws  {
        this.mListener = $r1;
    }

    public void toggle() throws  {
        this.mSwitchMode = isChecked() ? SwitchModes.UNCHECKED : SwitchModes.CHECKED;
        animateSwitch(this.mSwitchMode);
        if (this.mListener != null) {
            this.mListener.onToggle(isChecked());
        }
    }

    private void animateSwitch(SwitchModes $r1) throws  {
        fadeSwitchV($r1);
        slideBox($r1);
        changeBgAlpha($r1);
    }

    private void fadeSwitchV(SwitchModes $r1) throws  {
        AnimationSet $r2 = new AnimationSet(true);
        this.mSwitchV.setVisibility(0);
        switch ($r1) {
            case CHECKED:
                $r2.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                $r2.setStartOffset(100);
                break;
            default:
                $r2.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                $r2.setStartOffset(0);
                break;
        }
        $r2.setDuration(100);
        $r2.setFillAfter(true);
        $r2.setInterpolator(new DecelerateInterpolator());
        this.mSwitchV.startAnimation($r2);
    }

    private void setSwitch(SwitchModes $r1) throws  {
        TranslateAnimation $r3;
        switch ($r1) {
            case CHECKED:
                $r3 = new TranslateAnimation(this.mDensity * 34.0f, this.mDensity * 34.0f, 0.0f, 0.0f);
                this.mSwitchV.setVisibility(0);
                this.mSwitchBgOn.setVisibility(0);
                break;
            default:
                $r3 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
                this.mSwitchV.setVisibility(4);
                this.mSwitchBgOn.setVisibility(4);
                break;
        }
        $r3.setDuration(0);
        $r3.setFillAfter(true);
        this.mSwitchHandle.startAnimation($r3);
    }

    private void changeBgAlpha(SwitchModes $r1) throws  {
        AlphaAnimation $r3;
        switch ($r1) {
            case CHECKED:
                $r3 = new AlphaAnimation(0.0f, 1.0f);
                break;
            default:
                $r3 = new AlphaAnimation(1.0f, 0.0f);
                break;
        }
        $r3.setDuration(100);
        $r3.setFillAfter(true);
        this.mSwitchBgOn.setVisibility(0);
        this.mSwitchBgOn.startAnimation($r3);
    }

    private void slideBox(SwitchModes $r1) throws  {
        AnimationSet $r4 = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, EndorseRiderDialog.SCALE, 1.0f, 0.9f, 1, $r1 == SwitchModes.CHECKED ? 1.0f : 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        scaleAnimation.setDuration(100);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        $r4.addAnimation(scaleAnimation);
        float $f1 = new TranslateAnimation($r1 == SwitchModes.CHECKED ? 0.0f : this.mDensity * 34.0f, $r1 == SwitchModes.CHECKED ? this.mDensity * 34.0f : 0.0f, 0.0f, 0.0f);
        $f1.setDuration(100);
        $f1.setInterpolator(new AccelerateInterpolator(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r4.addAnimation($f1);
        $r4.setFillAfter(true);
        final SwitchModes switchModes = $r1;
        $r4.setAnimationListener(new JustCallNextAnimationListener() {
            void nextStep() throws  {
                WazeSwitchView.this.moveBoxToStretch(switchModes);
            }
        });
        ImageView $r9 = this.mSwitchHandle;
        $r9.startAnimation($r4);
    }

    private void moveBoxToStretch(SwitchModes $r1) throws  {
        float $f0;
        AnimationSet $r4 = new AnimationSet(true);
        $r4.addAnimation(new ScaleAnimation(EndorseRiderDialog.SCALE, PillButton.PRESSED_SCALE_DOWN, 0.9f, 1.0f, 1, $r1 == SwitchModes.CHECKED ? 1.0f : 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        if ($r1 == SwitchModes.CHECKED) {
            $f0 = this.mDensity * 34.0f;
        } else {
            $f0 = 0.0f;
        }
        $r4.addAnimation(new TranslateAnimation($f0, $f0, 0.0f, 0.0f));
        $r4.setFillAfter(true);
        $r4.setDuration(60);
        $r4.setInterpolator(new DecelerateInterpolator(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        final SwitchModes switchModes = $r1;
        $r4.setAnimationListener(new JustCallNextAnimationListener() {
            void nextStep() throws  {
                WazeSwitchView.this.returnBoxAfterStretchMove(switchModes);
            }
        });
        ImageView $r8 = this.mSwitchHandle;
        $r8.setAnimation($r4);
    }

    private void returnBoxAfterStretchMove(SwitchModes $r1) throws  {
        float $f0;
        AnimationSet $r3 = new AnimationSet(true);
        $r3.addAnimation(new ScaleAnimation(PillButton.PRESSED_SCALE_DOWN, 1.0f, 1.0f, 1.0f, 1, $r1 == SwitchModes.CHECKED ? 1.0f : 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        if ($r1 == SwitchModes.CHECKED) {
            $f0 = this.mDensity * 34.0f;
        } else {
            $f0 = 0.0f;
        }
        $r3.addAnimation(new TranslateAnimation($f0, $f0, 0.0f, 0.0f));
        $r3.setFillAfter(true);
        $r3.setDuration(40);
        $r3.setInterpolator(new DecelerateInterpolator(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        ImageView $r7 = this.mSwitchHandle;
        $r7.setAnimation($r3);
    }

    private void initMemebers() throws  {
        this.mSwitchBgOn = (ImageView) findViewById(C1283R.id.switch_bg_on);
        this.mSwitchBgOff = (ImageView) findViewById(C1283R.id.switch_bg_off);
        this.mSwitchHandle = (ImageView) findViewById(C1283R.id.switch_handle);
        this.mSwitchV = (ImageView) findViewById(C1283R.id.switch_v);
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
        int $i0 = (int) (10.0f * this.mDensity);
        setPadding($i0, $i0, $i0, $i0);
        setClipChildren(false);
        setClipToPadding(false);
    }
}
