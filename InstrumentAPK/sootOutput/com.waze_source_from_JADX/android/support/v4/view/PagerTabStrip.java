package android.support.v4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

public class PagerTabStrip extends PagerTitleStrip {
    private static final int FULL_UNDERLINE_HEIGHT = 1;
    private static final int INDICATOR_HEIGHT = 3;
    private static final int MIN_PADDING_BOTTOM = 6;
    private static final int MIN_STRIP_HEIGHT = 32;
    private static final int MIN_TEXT_SPACING = 64;
    private static final int TAB_PADDING = 16;
    private static final int TAB_SPACING = 32;
    private static final String TAG = "PagerTabStrip";
    private boolean mDrawFullUnderline;
    private boolean mDrawFullUnderlineSet;
    private int mFullUnderlineHeight;
    private boolean mIgnoreTap;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mMinPaddingBottom;
    private int mMinStripHeight;
    private int mMinTextSpacing;
    private int mTabAlpha;
    private int mTabPadding;
    private final Paint mTabPaint;
    private final Rect mTempRect;
    private int mTouchSlop;

    class C01161 implements OnClickListener {
        C01161() throws  {
        }

        public void onClick(View v) throws  {
            PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() - 1);
        }
    }

    class C01172 implements OnClickListener {
        C01172() throws  {
        }

        public void onClick(View v) throws  {
            PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() + 1);
        }
    }

    public PagerTabStrip(Context $r1) throws  {
        this($r1, null);
    }

    public PagerTabStrip(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mTabPaint = new Paint();
        this.mTempRect = new Rect();
        this.mTabAlpha = 255;
        this.mDrawFullUnderline = false;
        this.mDrawFullUnderlineSet = false;
        this.mIndicatorColor = this.mTextColor;
        this.mTabPaint.setColor(this.mIndicatorColor);
        float $f0 = $r1.getResources().getDisplayMetrics().density;
        this.mIndicatorHeight = (int) ((3.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mMinPaddingBottom = (int) ((6.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mMinTextSpacing = (int) (64.0f * $f0);
        this.mTabPadding = (int) ((16.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mFullUnderlineHeight = (int) ((1.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mMinStripHeight = (int) ((32.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mTouchSlop = ViewConfiguration.get($r1).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.mPrevText.setFocusable(true);
        this.mPrevText.setOnClickListener(new C01161());
        this.mNextText.setFocusable(true);
        this.mNextText.setOnClickListener(new C01172());
        if (getBackground() == null) {
            this.mDrawFullUnderline = true;
        }
    }

    public void setTabIndicatorColor(@ColorInt int $i0) throws  {
        this.mIndicatorColor = $i0;
        this.mTabPaint.setColor(this.mIndicatorColor);
        invalidate();
    }

    public void setTabIndicatorColorResource(@ColorRes int $i0) throws  {
        setTabIndicatorColor(getContext().getResources().getColor($i0));
    }

    @ColorInt
    public int getTabIndicatorColor() throws  {
        return this.mIndicatorColor;
    }

    public void setPadding(int $i0, int $i1, int $i2, int $i4) throws  {
        if ($i4 < this.mMinPaddingBottom) {
            $i4 = this.mMinPaddingBottom;
        }
        super.setPadding($i0, $i1, $i2, $i4);
    }

    public void setTextSpacing(int $i1) throws  {
        if ($i1 < this.mMinTextSpacing) {
            $i1 = this.mMinTextSpacing;
        }
        super.setTextSpacing($i1);
    }

    public void setBackgroundDrawable(Drawable $r1) throws  {
        super.setBackgroundDrawable($r1);
        if (!this.mDrawFullUnderlineSet) {
            boolean $z0;
            if ($r1 == null) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            this.mDrawFullUnderline = $z0;
        }
    }

    public void setBackgroundColor(@ColorInt int $i0) throws  {
        super.setBackgroundColor($i0);
        if (!this.mDrawFullUnderlineSet) {
            boolean $z0;
            if ((-16777216 & $i0) == 0) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            this.mDrawFullUnderline = $z0;
        }
    }

    public void setBackgroundResource(@DrawableRes int $i0) throws  {
        super.setBackgroundResource($i0);
        if (!this.mDrawFullUnderlineSet) {
            boolean $z0;
            if ($i0 == 0) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            this.mDrawFullUnderline = $z0;
        }
    }

    public void setDrawFullUnderline(boolean $z0) throws  {
        this.mDrawFullUnderline = $z0;
        this.mDrawFullUnderlineSet = true;
        invalidate();
    }

    public boolean getDrawFullUnderline() throws  {
        return this.mDrawFullUnderline;
    }

    int getMinHeight() throws  {
        return Math.max(super.getMinHeight(), this.mMinStripHeight);
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        int $i0 = $r1.getAction();
        if ($i0 != 0 && this.mIgnoreTap) {
            return false;
        }
        float $f0 = $r1.getX();
        float $f1 = $r1.getY();
        switch ($i0) {
            case 0:
                this.mInitialMotionX = $f0;
                this.mInitialMotionY = $f1;
                this.mIgnoreTap = false;
                break;
            case 1:
                if ($f0 >= ((float) (this.mCurrText.getLeft() - this.mTabPadding))) {
                    if ($f0 > ((float) (this.mCurrText.getRight() + this.mTabPadding))) {
                        this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1);
                        break;
                    }
                }
                this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
                break;
                break;
            case 2:
                if (Math.abs($f0 - this.mInitialMotionX) > ((float) this.mTouchSlop) || Math.abs($f1 - this.mInitialMotionY) > ((float) this.mTouchSlop)) {
                    this.mIgnoreTap = true;
                    break;
                }
            default:
                break;
        }
        return true;
    }

    protected void onDraw(Canvas $r1) throws  {
        super.onDraw($r1);
        int $i3 = getHeight();
        int $i0 = this.mCurrText.getLeft() - this.mTabPadding;
        int $i1 = this.mCurrText.getRight() + this.mTabPadding;
        int $i2 = $i3 - this.mIndicatorHeight;
        this.mTabPaint.setColor((this.mTabAlpha << 24) | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK));
        $r1.drawRect((float) $i0, (float) $i2, (float) $i1, (float) $i3, this.mTabPaint);
        if (this.mDrawFullUnderline) {
            this.mTabPaint.setColor(-16777216 | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK));
            $r1.drawRect((float) getPaddingLeft(), (float) ($i3 - this.mFullUnderlineHeight), (float) (getWidth() - getPaddingRight()), (float) $i3, this.mTabPaint);
        }
    }

    void updateTextPositions(int $i0, float $f0, boolean $z0) throws  {
        Rect $r1 = this.mTempRect;
        int $i2 = getHeight();
        int $i1 = $i2 - this.mIndicatorHeight;
        $r1.set(this.mCurrText.getLeft() - this.mTabPadding, $i1, this.mCurrText.getRight() + this.mTabPadding, $i2);
        super.updateTextPositions($i0, $f0, $z0);
        this.mTabAlpha = (int) ((Math.abs($f0 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) * 255.0f);
        $r1.union(this.mCurrText.getLeft() - this.mTabPadding, $i1, this.mCurrText.getRight() + this.mTabPadding, $i2);
        invalidate($r1);
    }
}
