package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.CompoundButton;
import com.waze.map.CanvasFont;

public class SwitchCompat extends CompoundButton {
    private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private final AppCompatDrawableManager mDrawableManager;
    private int mMinFlingVelocity;
    private Layout mOffLayout;
    private Layout mOnLayout;
    private ThumbAnimation mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final Rect mTempRect;
    private ColorStateList mTextColors;
    private CharSequence mTextOff;
    private CharSequence mTextOn;
    private TextPaint mTextPaint;
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDrawable;
    private VelocityTracker mVelocityTracker;

    private class ThumbAnimation extends Animation {
        final float mDiff;
        final float mEndPosition;
        final float mStartPosition;

        private ThumbAnimation(float $f0, float $f1) throws  {
            this.mStartPosition = $f0;
            this.mEndPosition = $f1;
            this.mDiff = $f1 - $f0;
        }

        protected void applyTransformation(float $f0, Transformation t) throws  {
            SwitchCompat.this.setThumbPosition(this.mStartPosition + (this.mDiff * $f0));
        }
    }

    public void setSwitchTypeface(android.graphics.Typeface r1, int r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.SwitchCompat.setSwitchTypeface(android.graphics.Typeface, int):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 5 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SwitchCompat.setSwitchTypeface(android.graphics.Typeface, int):void");
    }

    public SwitchCompat(Context $r1) throws  {
        this($r1, null);
    }

    public SwitchCompat(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.switchStyle);
    }

    public SwitchCompat(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mTempRect = new Rect();
        this.mTextPaint = new TextPaint(1);
        Resources $r6 = getResources();
        this.mTextPaint.density = $r6.getDisplayMetrics().density;
        TintTypedArray $r9 = TintTypedArray.obtainStyledAttributes($r1, $r2, C0192R.styleable.SwitchCompat, $i0, 0);
        this.mThumbDrawable = $r9.getDrawable(C0192R.styleable.SwitchCompat_android_thumb);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(this);
        }
        this.mTrackDrawable = $r9.getDrawable(C0192R.styleable.SwitchCompat_track);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this);
        }
        this.mTextOn = $r9.getText(C0192R.styleable.SwitchCompat_android_textOn);
        this.mTextOff = $r9.getText(C0192R.styleable.SwitchCompat_android_textOff);
        this.mShowText = $r9.getBoolean(C0192R.styleable.SwitchCompat_showText, true);
        this.mThumbTextPadding = $r9.getDimensionPixelSize(C0192R.styleable.SwitchCompat_thumbTextPadding, 0);
        this.mSwitchMinWidth = $r9.getDimensionPixelSize(C0192R.styleable.SwitchCompat_switchMinWidth, 0);
        this.mSwitchPadding = $r9.getDimensionPixelSize(C0192R.styleable.SwitchCompat_switchPadding, 0);
        this.mSplitTrack = $r9.getBoolean(C0192R.styleable.SwitchCompat_splitTrack, false);
        $i0 = $r9.getResourceId(C0192R.styleable.SwitchCompat_switchTextAppearance, 0);
        if ($i0 != 0) {
            setSwitchTextAppearance($r1, $i0);
        }
        this.mDrawableManager = AppCompatDrawableManager.get();
        $r9.recycle();
        ViewConfiguration $r13 = ViewConfiguration.get($r1);
        this.mTouchSlop = $r13.getScaledTouchSlop();
        this.mMinFlingVelocity = $r13.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchTextAppearance(Context $r1, int $i0) throws  {
        TypedArray $r3 = $r1.obtainStyledAttributes($i0, C0192R.styleable.TextAppearance);
        ColorStateList $r4 = $r3.getColorStateList(C0192R.styleable.TextAppearance_android_textColor);
        if ($r4 != null) {
            this.mTextColors = $r4;
        } else {
            this.mTextColors = getTextColors();
        }
        $i0 = $r3.getDimensionPixelSize(C0192R.styleable.TextAppearance_android_textSize, 0);
        if (!($i0 == 0 || ((float) $i0) == this.mTextPaint.getTextSize())) {
            this.mTextPaint.setTextSize((float) $i0);
            requestLayout();
        }
        setSwitchTypefaceByIndex($r3.getInt(C0192R.styleable.TextAppearance_android_typeface, -1), $r3.getInt(C0192R.styleable.TextAppearance_android_textStyle, -1));
        if ($r3.getBoolean(C0192R.styleable.TextAppearance_textAllCaps, false)) {
            this.mSwitchTransformationMethod = new AllCapsTransformationMethod(getContext());
        } else {
            this.mSwitchTransformationMethod = null;
        }
        $r3.recycle();
    }

    private void setSwitchTypefaceByIndex(int $i0, int $i1) throws  {
        Typeface $r1 = null;
        switch ($i0) {
            case 1:
                $r1 = Typeface.SANS_SERIF;
                break;
            case 2:
                $r1 = Typeface.SERIF;
                break;
            case 3:
                $r1 = Typeface.MONOSPACE;
                break;
            default:
                break;
        }
        setSwitchTypeface($r1, $i1);
    }

    public void setSwitchTypeface(Typeface $r1) throws  {
        if (this.mTextPaint.getTypeface() != $r1) {
            this.mTextPaint.setTypeface($r1);
            requestLayout();
            invalidate();
        }
    }

    public void setSwitchPadding(int $i0) throws  {
        this.mSwitchPadding = $i0;
        requestLayout();
    }

    public int getSwitchPadding() throws  {
        return this.mSwitchPadding;
    }

    public void setSwitchMinWidth(int $i0) throws  {
        this.mSwitchMinWidth = $i0;
        requestLayout();
    }

    public int getSwitchMinWidth() throws  {
        return this.mSwitchMinWidth;
    }

    public void setThumbTextPadding(int $i0) throws  {
        this.mThumbTextPadding = $i0;
        requestLayout();
    }

    public int getThumbTextPadding() throws  {
        return this.mThumbTextPadding;
    }

    public void setTrackDrawable(Drawable $r1) throws  {
        this.mTrackDrawable = $r1;
        requestLayout();
    }

    public void setTrackResource(int $i0) throws  {
        setTrackDrawable(this.mDrawableManager.getDrawable(getContext(), $i0));
    }

    public Drawable getTrackDrawable() throws  {
        return this.mTrackDrawable;
    }

    public void setThumbDrawable(Drawable $r1) throws  {
        this.mThumbDrawable = $r1;
        requestLayout();
    }

    public void setThumbResource(int $i0) throws  {
        setThumbDrawable(this.mDrawableManager.getDrawable(getContext(), $i0));
    }

    public Drawable getThumbDrawable() throws  {
        return this.mThumbDrawable;
    }

    public void setSplitTrack(boolean $z0) throws  {
        this.mSplitTrack = $z0;
        invalidate();
    }

    public boolean getSplitTrack() throws  {
        return this.mSplitTrack;
    }

    public CharSequence getTextOn() throws  {
        return this.mTextOn;
    }

    public void setTextOn(CharSequence $r1) throws  {
        this.mTextOn = $r1;
        requestLayout();
    }

    public CharSequence getTextOff() throws  {
        return this.mTextOff;
    }

    public void setTextOff(CharSequence $r1) throws  {
        this.mTextOff = $r1;
        requestLayout();
    }

    public void setShowText(boolean $z0) throws  {
        if (this.mShowText != $z0) {
            this.mShowText = $z0;
            requestLayout();
        }
    }

    public boolean getShowText() throws  {
        return this.mShowText;
    }

    public void onMeasure(int $i0, int $i1) throws  {
        int $i3;
        int $i2;
        int $i4;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOff);
            }
        }
        Rect $r1 = this.mTempRect;
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding($r1);
            $i3 = (this.mThumbDrawable.getIntrinsicWidth() - $r1.left) - $r1.right;
            $i2 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            $i3 = 0;
            $i2 = 0;
        }
        if (this.mShowText) {
            $i4 = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + (this.mThumbTextPadding * 2);
        } else {
            $i4 = 0;
        }
        this.mThumbWidth = Math.max($i4, $i3);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding($r1);
            $i3 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            $r1.setEmpty();
            $i3 = 0;
        }
        int $i5 = $r1.left;
        $i4 = $r1.right;
        if (this.mThumbDrawable != null) {
            $r1 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            $i5 = Math.max($i5, $r1.left);
            $i4 = Math.max($i4, $r1.right);
        }
        $i4 = Math.max(this.mSwitchMinWidth, ((this.mThumbWidth * 2) + $i5) + $i4);
        $i2 = Math.max($i3, $i2);
        this.mSwitchWidth = $i4;
        this.mSwitchHeight = $i2;
        super.onMeasure($i0, $i1);
        if (getMeasuredHeight() < $i2) {
            setMeasuredDimension(ViewCompat.getMeasuredWidthAndState(this), $i2);
        }
    }

    @TargetApi(14)
    public void onPopulateAccessibilityEvent(AccessibilityEvent $r1) throws  {
        super.onPopulateAccessibilityEvent($r1);
        CharSequence $r2 = isChecked() ? this.mTextOn : this.mTextOff;
        if ($r2 != null) {
            $r1.getText().add($r2);
        }
    }

    private Layout makeLayout(CharSequence $r1) throws  {
        int $i0;
        if (this.mSwitchTransformationMethod != null) {
            $r1 = this.mSwitchTransformationMethod.getTransformation($r1, this);
        }
        TextPaint $r2 = this.mTextPaint;
        if ($r1 != null) {
            $i0 = (int) Math.ceil((double) Layout.getDesiredWidth($r1, this.mTextPaint));
        } else {
            $i0 = 0;
        }
        return new StaticLayout($r1, $r2, $i0, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    private boolean hitThumb(float $f0, float $f1) throws  {
        if (this.mThumbDrawable == null) {
            return false;
        }
        int $i1 = getThumbOffset();
        this.mThumbDrawable.getPadding(this.mTempRect);
        int $i3 = this.mSwitchTop - this.mTouchSlop;
        $i1 = (this.mSwitchLeft + $i1) - this.mTouchSlop;
        int $i2 = (((this.mThumbWidth + $i1) + this.mTempRect.left) + this.mTempRect.right) + this.mTouchSlop;
        int $i0 = this.mSwitchBottom + this.mTouchSlop;
        if ($f0 <= ((float) $i1)) {
            return false;
        }
        if ($f0 >= ((float) $i2)) {
            return false;
        }
        if ($f1 > ((float) $i3)) {
            return $f1 < ((float) $i0);
        } else {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        this.mVelocityTracker.addMovement($r1);
        float $f1;
        float $f0;
        switch (MotionEventCompat.getActionMasked($r1)) {
            case 0:
                $f1 = $r1.getX();
                $f0 = $r1.getY();
                if (isEnabled() && hitThumb($f1, $f0)) {
                    this.mTouchMode = 1;
                    this.mTouchX = $f1;
                    this.mTouchY = $f0;
                    break;
                }
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    this.mVelocityTracker.clear();
                    break;
                }
                stopDrag($r1);
                super.onTouchEvent($r1);
                return true;
            case 2:
                switch (this.mTouchMode) {
                    case 0:
                        break;
                    case 1:
                        $f1 = $r1.getX();
                        $f0 = $r1.getY();
                        if (Math.abs($f1 - this.mTouchX) > ((float) this.mTouchSlop) || Math.abs($f0 - this.mTouchY) > ((float) this.mTouchSlop)) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = $f1;
                            this.mTouchY = $f0;
                            return true;
                        }
                    case 2:
                        $f1 = $r1.getX();
                        int $i0 = getThumbScrollRange();
                        $f0 = $f1 - this.mTouchX;
                        if ($i0 != 0) {
                            $f0 /= (float) $i0;
                        } else {
                            $f0 = $f0 > 0.0f ? 1.0f : -1.0f;
                        }
                        if (ViewUtils.isLayoutRtl(this)) {
                            $f0 = -$f0;
                        }
                        $f0 = constrain(this.mThumbPosition + $f0, 0.0f, 1.0f);
                        if ($f0 != this.mThumbPosition) {
                            this.mTouchX = $f1;
                            setThumbPosition($f0);
                        }
                        return true;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent($r1);
    }

    private void cancelSuperTouch(MotionEvent $r1) throws  {
        $r1 = MotionEvent.obtain($r1);
        $r1.setAction(3);
        super.onTouchEvent($r1);
        $r1.recycle();
    }

    private void stopDrag(MotionEvent $r1) throws  {
        this.mTouchMode = 0;
        boolean $z1 = $r1.getAction() == 1 && isEnabled();
        boolean $z0 = isChecked();
        if ($z1) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float $f0 = this.mVelocityTracker.getXVelocity();
            $z1 = Math.abs($f0) > ((float) this.mMinFlingVelocity) ? ViewUtils.isLayoutRtl(this) ? $f0 < 0.0f : $f0 > 0.0f : getTargetCheckedState();
        } else {
            $z1 = $z0;
        }
        if ($z1 != $z0) {
            playSoundEffect(0);
        }
        setChecked($z1);
        cancelSuperTouch($r1);
    }

    private void animateThumbToCheckedState(final boolean $z0) throws  {
        if (this.mPositionAnimator != null) {
            cancelPositionAnimator();
        }
        this.mPositionAnimator = new ThumbAnimation(this.mThumbPosition, $z0 ? 1.0f : 0.0f);
        this.mPositionAnimator.setDuration(250);
        this.mPositionAnimator.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation $r1) throws  {
                if (SwitchCompat.this.mPositionAnimator == $r1) {
                    SwitchCompat.this.setThumbPosition($z0 ? 1.0f : 0.0f);
                    SwitchCompat.this.mPositionAnimator = null;
                }
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }
        });
        startAnimation(this.mPositionAnimator);
    }

    private void cancelPositionAnimator() throws  {
        if (this.mPositionAnimator != null) {
            clearAnimation();
            this.mPositionAnimator = null;
        }
    }

    private boolean getTargetCheckedState() throws  {
        return this.mThumbPosition > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
    }

    private void setThumbPosition(float $f0) throws  {
        this.mThumbPosition = $f0;
        invalidate();
    }

    public void toggle() throws  {
        setChecked(!isChecked());
    }

    public void setChecked(boolean $z0) throws  {
        super.setChecked($z0);
        $z0 = isChecked();
        if (getWindowToken() != null && ViewCompat.isLaidOut(this) && isShown()) {
            animateThumbToCheckedState($z0);
            return;
        }
        float $f0;
        cancelPositionAnimator();
        if ($z0) {
            $f0 = 1.0f;
        } else {
            $f0 = 0.0f;
        }
        setThumbPosition($f0);
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        $i2 = 0;
        $i3 = 0;
        if (this.mThumbDrawable != null) {
            Rect $r1 = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding($r1);
            } else {
                $r1.setEmpty();
            }
            Rect $r3 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            $i2 = Math.max(0, $r3.left - $r1.left);
            $i3 = Math.max(0, $r3.right - $r1.right);
        }
        if (ViewUtils.isLayoutRtl(this)) {
            $i0 = getPaddingLeft() + $i2;
            $i1 = ((this.mSwitchWidth + $i0) - $i2) - $i3;
        } else {
            $i1 = (getWidth() - getPaddingRight()) - $i3;
            $i0 = (($i1 - this.mSwitchWidth) + $i2) + $i3;
        }
        switch (getGravity() & 112) {
            case 16:
                $i2 = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2);
                $i3 = $i2 + this.mSwitchHeight;
                break;
            case 80:
                $i3 = getHeight() - getPaddingBottom();
                $i2 = $i3 - this.mSwitchHeight;
                break;
            default:
                $i3 = getPaddingTop();
                $i2 = $i3;
                $i3 += this.mSwitchHeight;
                break;
        }
        this.mSwitchLeft = $i0;
        this.mSwitchTop = $i2;
        this.mSwitchBottom = $i3;
        this.mSwitchRight = $i1;
    }

    public void draw(Canvas $r1) throws  {
        Rect $r4;
        Rect $r2 = this.mTempRect;
        int $i1 = this.mSwitchLeft;
        int $i3 = this.mSwitchTop;
        int $i2 = this.mSwitchRight;
        int $i0 = this.mSwitchBottom;
        int $i4 = $i1 + getThumbOffset();
        if (this.mThumbDrawable != null) {
            $r4 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        } else {
            $r4 = DrawableUtils.INSETS_NONE;
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding($r2);
            $i4 += $r2.left;
            int $i5 = $i1;
            int $i6 = $i3;
            int $i7 = $i2;
            int $i8 = $i0;
            if ($r4 != null) {
                if ($r4.left > $r2.left) {
                    $i5 = $i1 + ($r4.left - $r2.left);
                }
                if ($r4.top > $r2.top) {
                    $i6 = $i3 + ($r4.top - $r2.top);
                }
                if ($r4.right > $r2.right) {
                    $i7 = $i2 - ($r4.right - $r2.right);
                }
                if ($r4.bottom > $r2.bottom) {
                    $i8 = $i0 - ($r4.bottom - $r2.bottom);
                }
            }
            this.mTrackDrawable.setBounds($i5, $i6, $i7, $i8);
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding($r2);
            $i2 = $i4 - $r2.left;
            $i4 = (this.mThumbWidth + $i4) + $r2.right;
            this.mThumbDrawable.setBounds($i2, $i3, $i4, $i0);
            Drawable $r3 = getBackground();
            if ($r3 != null) {
                DrawableCompat.setHotspotBounds($r3, $i2, $i3, $i4, $i0);
            }
        }
        super.draw($r1);
    }

    protected void onDraw(Canvas $r1) throws  {
        int $i3;
        Layout $r7;
        super.onDraw($r1);
        Rect $r2 = this.mTempRect;
        Drawable $r4 = this.mTrackDrawable;
        if ($r4 != null) {
            $r4.getPadding($r2);
        } else {
            $r2.setEmpty();
        }
        int $i2 = this.mSwitchTop;
        $i2 += $r2.top;
        int $i1 = this.mSwitchBottom - $r2.bottom;
        Drawable $r3 = this.mThumbDrawable;
        if ($r4 != null) {
            if (!this.mSplitTrack || $r3 == null) {
                $r4.draw($r1);
            } else {
                Rect $r5 = DrawableUtils.getOpticalBounds($r3);
                $r3.copyBounds($r2);
                $r2.left += $r5.left;
                $r2.right -= $r5.right;
                $i3 = $r1.save();
                $r1.clipRect($r2, Op.DIFFERENCE);
                $r4.draw($r1);
                $r1.restoreToCount($i3);
            }
        }
        $i3 = $r1.save();
        if ($r3 != null) {
            $r3.draw($r1);
        }
        if (getTargetCheckedState()) {
            $r7 = this.mOnLayout;
        } else {
            $r7 = this.mOffLayout;
        }
        if ($r7 != null) {
            int $i0;
            int[] $r8 = getDrawableState();
            if (this.mTextColors != null) {
                this.mTextPaint.setColor(this.mTextColors.getColorForState($r8, 0));
            }
            TextPaint $r10 = this.mTextPaint;
            $r10.drawableState = $r8;
            if ($r3 != null) {
                $r2 = $r3.getBounds();
                $i0 = $r2.left;
                int $i4 = $r2.right;
                $i0 += $i4;
            } else {
                $i0 = getWidth();
            }
            $r1.translate((float) (($i0 / 2) - ($r7.getWidth() / 2)), (float) ((($i2 + $i1) / 2) - ($r7.getHeight() / 2)));
            $r7.draw($r1);
        }
        $r1.restoreToCount($i3);
    }

    public int getCompoundPaddingLeft() throws  {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int $i0 = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        return !TextUtils.isEmpty(getText()) ? $i0 + this.mSwitchPadding : $i0;
    }

    public int getCompoundPaddingRight() throws  {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int $i0 = super.getCompoundPaddingRight() + this.mSwitchWidth;
        return !TextUtils.isEmpty(getText()) ? $i0 + this.mSwitchPadding : $i0;
    }

    private int getThumbOffset() throws  {
        float $f0;
        if (ViewUtils.isLayoutRtl(this)) {
            $f0 = 1.0f - this.mThumbPosition;
        } else {
            $f0 = this.mThumbPosition;
        }
        return (int) ((((float) getThumbScrollRange()) * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
    }

    private int getThumbScrollRange() throws  {
        if (this.mTrackDrawable == null) {
            return 0;
        }
        Rect $r2;
        Rect $r1 = this.mTempRect;
        this.mTrackDrawable.getPadding($r1);
        if (this.mThumbDrawable != null) {
            $r2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        } else {
            $r2 = DrawableUtils.INSETS_NONE;
        }
        return ((((this.mSwitchWidth - this.mThumbWidth) - $r1.left) - $r1.right) - $r2.left) - $r2.right;
    }

    protected int[] onCreateDrawableState(int $i0) throws  {
        int[] $r1 = super.onCreateDrawableState($i0 + 1);
        if (!isChecked()) {
            return $r1;
        }
        mergeDrawableStates($r1, CHECKED_STATE_SET);
        return $r1;
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        int[] $r1 = getDrawableState();
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setState($r1);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setState($r1);
        }
        invalidate();
    }

    public void drawableHotspotChanged(float $f0, float $f1) throws  {
        if (VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged($f0, $f1);
        }
        if (this.mThumbDrawable != null) {
            DrawableCompat.setHotspot(this.mThumbDrawable, $f0, $f1);
        }
        if (this.mTrackDrawable != null) {
            DrawableCompat.setHotspot(this.mTrackDrawable, $f0, $f1);
        }
    }

    protected boolean verifyDrawable(Drawable $r1) throws  {
        return super.verifyDrawable($r1) || $r1 == this.mThumbDrawable || $r1 == this.mTrackDrawable;
    }

    public void jumpDrawablesToCurrentState() throws  {
        if (VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.mThumbDrawable != null) {
                this.mThumbDrawable.jumpToCurrentState();
            }
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.jumpToCurrentState();
            }
            cancelPositionAnimator();
            setThumbPosition(isChecked() ? 1.0f : 0.0f);
        }
    }

    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
        super.onInitializeAccessibilityEvent($r1);
        $r1.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo $r1) throws  {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo($r1);
            $r1.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME);
            CharSequence $r3 = isChecked() ? this.mTextOn : this.mTextOff;
            if (!TextUtils.isEmpty($r3)) {
                CharSequence $r4 = $r1.getText();
                if (TextUtils.isEmpty($r4)) {
                    $r1.setText($r3);
                    return;
                }
                StringBuilder $r2 = new StringBuilder();
                $r2.append($r4).append(' ').append($r3);
                $r1.setText($r2);
            }
        }
    }

    private static float constrain(float $f0, float $f2, float $f1) throws  {
        if ($f0 < $f2) {
            return $f2;
        }
        return $f0 > $f1 ? $f1 : $f0;
    }
}
