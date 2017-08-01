package com.waze.ifs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.waze.C1283R;
import com.waze.map.CanvasFont;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.view.button.PillButton;

public class WazeRadioView extends RelativeLayout {
    private static final int BASIC_ANIM_DURATION = 200;
    private LayoutInflater inflater;
    private float mDensity;
    private SettingsToggleCallback mListener;
    private ImageView mRadioBgOff;
    private ImageView mRadioBgOn;
    private RadioModes mRadioMode;

    private enum RadioModes {
        SELECTED,
        UNSELECTED
    }

    public WazeRadioView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.inflater = LayoutInflater.from($r1);
        this.inflater.inflate(C1283R.layout.waze_radio, this);
        initMemebers();
    }

    public WazeRadioView(Context $r1) throws  {
        super($r1);
        this.inflater = LayoutInflater.from($r1);
        this.inflater.inflate(C1283R.layout.waze_radio, this);
        initMemebers();
    }

    public void setValue(boolean $z0) throws  {
        RadioModes $r2;
        byte $b1;
        byte $b0 = (byte) 0;
        if ($z0) {
            $r2 = RadioModes.SELECTED;
        } else {
            $r2 = RadioModes.UNSELECTED;
        }
        this.mRadioMode = $r2;
        ImageView $r1 = this.mRadioBgOff;
        if ($z0) {
            $b1 = (byte) 4;
        } else {
            $b1 = (byte) 0;
        }
        $r1.setVisibility($b1);
        this.mRadioBgOff.clearAnimation();
        $r1 = this.mRadioBgOn;
        if (!$z0) {
            $b0 = (byte) 4;
        }
        $r1.setVisibility($b0);
        this.mRadioBgOn.clearAnimation();
    }

    public void setValueAnimated(boolean $z0) throws  {
        RadioModes $r1;
        if ($z0) {
            $r1 = RadioModes.SELECTED;
        } else {
            $r1 = RadioModes.UNSELECTED;
        }
        this.mRadioMode = $r1;
        animateMode(this.mRadioMode);
    }

    public boolean isSelected() throws  {
        return this.mRadioMode == RadioModes.SELECTED;
    }

    public void setOnChecked(SettingsToggleCallback $r1) throws  {
        this.mListener = $r1;
    }

    public void toggle() throws  {
        this.mRadioMode = isSelected() ? RadioModes.UNSELECTED : RadioModes.SELECTED;
        animateMode(this.mRadioMode);
        if (this.mListener != null) {
            this.mListener.onToggle(isSelected());
        }
    }

    private void animateMode(RadioModes $r1) throws  {
        fadeOffBg($r1);
        anmateOnBg($r1);
    }

    private void fadeOffBg(RadioModes $r1) throws  {
        AnimationSet $r2 = new AnimationSet(true);
        this.mRadioBgOff.setVisibility(0);
        switch ($r1) {
            case SELECTED:
                $r2.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                break;
            default:
                $r2.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                break;
        }
        $r2.setDuration(100);
        $r2.setFillAfter(true);
        this.mRadioBgOff.startAnimation($r2);
    }

    private void anmateOnBg(RadioModes $r1) throws  {
        AnimationSet $r2 = new AnimationSet(true);
        this.mRadioBgOn.setVisibility(0);
        switch ($r1) {
            case SELECTED:
                $r2.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                $r2.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                break;
            default:
                $r2.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                $r2.addAnimation(new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                break;
        }
        $r2.setDuration(200);
        $r2.setInterpolator(new OvershootInterpolator(PillButton.PRESSED_SCALE_DOWN));
        $r2.setFillAfter(true);
        this.mRadioBgOn.startAnimation($r2);
    }

    private void initMemebers() throws  {
        this.mRadioBgOn = (ImageView) findViewById(C1283R.id.radio_bg_on);
        this.mRadioBgOff = (ImageView) findViewById(C1283R.id.radio_bg_off);
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
        int $i0 = (int) (this.mDensity * 3.0f);
        setPadding($i0, $i0, $i0, $i0);
        setClipChildren(false);
        setClipToPadding(false);
    }
}
