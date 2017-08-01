package com.waze.ifs.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.map.CanvasFont;

public class PointsView extends RelativeLayout {
    private static final int BASIC_SWITCH_DURATION = 200;
    private View mBg;
    private View mCheck;
    private boolean mIgnoreFirst = false;
    private OnStateChangedListener mListener;
    private View mMust;
    private boolean mPointsOn = true;
    private TextView mPointsTextView;
    private boolean mShowPoints = true;
    private boolean mTriangle = false;
    private boolean mValid = false;
    private boolean mWasValid = true;

    public interface OnStateChangedListener {
        void onStateChanged(boolean z, boolean z2) throws ;
    }

    public PointsView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        LayoutInflater $r3 = LayoutInflater.from($r1);
        TypedArray $r5 = $r1.obtainStyledAttributes($r2, C1283R.styleable.PointsView);
        this.mTriangle = $r5.getBoolean(0, false);
        this.mIgnoreFirst = $r5.getBoolean(1, false);
        $r5.recycle();
        if (this.mTriangle) {
            $r3.inflate(C1283R.layout.points_marker_triangle, this);
        } else {
            $r3.inflate(C1283R.layout.points_marker, this);
        }
        initMemebers();
    }

    public void setOnStateChangedListener(OnStateChangedListener $r1) throws  {
        this.mListener = $r1;
    }

    public void setPoints(int $i0) throws  {
        setPoints($i0, false);
    }

    public void setPoints(int $i0, boolean $z0) throws  {
        if ($z0 || $i0 <= 0) {
            this.mShowPoints = false;
            this.mPointsTextView.setVisibility(8);
            return;
        }
        this.mPointsTextView.setText("+" + $i0);
    }

    public void setValid(boolean $z0) throws  {
        this.mValid = $z0;
        if (this.mValid) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        setIsOn($z0, false, true);
    }

    public void setIsOn(boolean $z0, boolean hasContent, boolean $z2) throws  {
        byte $b0 = (byte) 4;
        byte $b1 = (byte) 8;
        if (this.mIgnoreFirst) {
            this.mIgnoreFirst = false;
            return;
        }
        if (!(this.mListener == null || (this.mPointsOn == $z0 && this.mWasValid == this.mValid))) {
            this.mListener.onStateChanged(this.mValid, $z0);
        }
        if (!$z2) {
            this.mPointsOn = $z0;
            if (this.mValid) {
                if (this.mShowPoints) {
                    byte $b2;
                    TextView $r3 = this.mPointsTextView;
                    if (this.mPointsOn) {
                        $b2 = (byte) 8;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r3.setVisibility($b2);
                }
                this.mMust.setVisibility(8);
            } else {
                this.mMust.setVisibility(this.mPointsOn ? (byte) 4 : (byte) 0);
                this.mPointsTextView.setVisibility(8);
            }
            View $r2 = this.mCheck;
            if (this.mPointsOn) {
                $b1 = (byte) 0;
            }
            $r2.setVisibility($b1);
            $r2 = this.mBg;
            if (this.mPointsOn || this.mShowPoints) {
                $b0 = (byte) 0;
            }
            $r2.setVisibility($b0);
        } else if (this.mPointsOn != $z0 || this.mWasValid != this.mValid) {
            animateSwitch($z0);
        }
    }

    public boolean isOn() throws  {
        return this.mPointsOn;
    }

    public boolean isValid() throws  {
        if (this.mIgnoreFirst) {
            boolean $z0;
            this.mIgnoreFirst = false;
            if (this.mValid) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            setIsOn($z0, false, true);
        }
        return this.mValid;
    }

    private void animateSwitch(boolean $z0) throws  {
        View $r2 = null;
        View $r3 = null;
        View $r4 = null;
        View $r5 = null;
        if (!this.mWasValid && this.mValid) {
            $r4 = this.mMust;
        } else if (!$z0 && this.mPointsOn) {
            $r4 = this.mCheck;
            if (!this.mShowPoints) {
                $r5 = this.mBg;
            }
        } else if (this.mShowPoints) {
            $r4 = this.mPointsTextView;
        }
        if (!this.mValid && this.mWasValid) {
            $r2 = this.mMust;
        } else if (!this.mPointsOn && $z0) {
            $r2 = this.mCheck;
            if (!this.mShowPoints) {
                $r3 = this.mBg;
            }
        } else if (this.mShowPoints) {
            $r2 = this.mPointsTextView;
        }
        this.mMust.setVisibility(8);
        this.mCheck.setVisibility(8);
        this.mPointsTextView.setVisibility(8);
        if ($r2 != null) {
            $r2.setVisibility(0);
            $r2.startAnimation(makeAnimation(true));
        }
        if ($r3 != null) {
            $r3.setVisibility(0);
            $r3.startAnimation(makeAnimation(true));
        }
        if ($r4 != null) {
            $r4.setVisibility(0);
            $r4.startAnimation(makeAnimation(false));
        }
        if ($r5 != null) {
            $r5.setVisibility(0);
            $r5.startAnimation(makeAnimation(false));
        }
        this.mWasValid = this.mValid;
        this.mPointsOn = $z0;
        Animation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(2);
        scaleAnimation.setDuration(100);
        startAnimation(scaleAnimation);
    }

    private Animation makeAnimation(boolean $z0) throws  {
        if (this.mTriangle) {
            return makeZoomAnimation($z0);
        }
        return makeTurningAnimation($z0, !this.mValid);
    }

    private Animation makeTurningAnimation(boolean $z0, boolean $z1) throws  {
        AlphaAnimation $r4;
        short $s0;
        short $s1;
        AnimationSet $r2 = new AnimationSet(true);
        $r2.setInterpolator(new AccelerateDecelerateInterpolator());
        $r2.setDuration(200);
        $r2.setFillAfter(true);
        if ($z0) {
            $r4 = new AlphaAnimation(0.0f, 1.0f);
        } else {
            $r4 = new AlphaAnimation(1.0f, 0.0f);
        }
        $r2.addAnimation($r4);
        if ($z0) {
            $s0 = (short) 0;
            $s1 = $z1 ? (short) -180 : (short) 180;
        } else {
            $s1 = (short) 0;
            $s0 = $z1 ? (short) 180 : (short) -180;
        }
        $r2.addAnimation(new RotateAnimation((float) $s1, (float) $s0, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        return $r2;
    }

    private Animation makeZoomAnimation(boolean $z0) throws  {
        AlphaAnimation $r4;
        float $f0;
        float f;
        AnimationSet $r2 = new AnimationSet(true);
        if ($z0) {
            $r2.setInterpolator(new OvershootInterpolator());
        } else {
            $r2.setInterpolator(new AnticipateInterpolator());
        }
        $r2.setDuration(200);
        $r2.setFillAfter(true);
        if ($z0) {
            $r4 = new AlphaAnimation(0.0f, 1.0f);
        } else {
            $r4 = new AlphaAnimation(1.0f, 0.0f);
        }
        $r2.addAnimation($r4);
        if ($z0) {
            $f0 = 1.0f;
            f = 0.0f;
        } else {
            $f0 = 0.0f;
            f = 1.0f;
        }
        $r2.addAnimation(new ScaleAnimation(f, $f0, 1.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        return $r2;
    }

    private void initMemebers() throws  {
        this.mBg = findViewById(C1283R.id.pointsBg);
        this.mCheck = findViewById(C1283R.id.pointsCheck);
        this.mMust = findViewById(C1283R.id.pointsMustFill);
        this.mPointsTextView = (TextView) findViewById(C1283R.id.pointsBadge);
    }

    public ImageView getBg() throws  {
        return (ImageView) this.mBg;
    }

    public ImageView getCheck() throws  {
        return (ImageView) this.mCheck;
    }

    public void setIgnoreFirst(boolean $z0) throws  {
        this.mIgnoreFirst = $z0;
    }
}
