package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import com.waze.map.CanvasFont;
import java.lang.ref.WeakReference;

public class PagerTitleStrip extends ViewGroup implements Decor {
    private static final int[] ATTRS = new int[]{16842804, 16842901, 16842904, 16842927};
    private static final PagerTitleStripImpl IMPL;
    private static final float SIDE_ALPHA = 0.6f;
    private static final String TAG = "PagerTitleStrip";
    private static final int[] TEXT_ATTRS = new int[]{16843660};
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage;
    private float mLastKnownPositionOffset;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    private class PageListener extends DataSetObserver implements OnAdapterChangeListener, OnPageChangeListener {
        private int mScrollState;

        private PageListener() throws  {
        }

        public void onPageScrolled(int $i1, float $f0, int positionOffsetPixels) throws  {
            if ($f0 > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
                $i1++;
            }
            PagerTitleStrip.this.updateTextPositions($i1, $f0, false);
        }

        public void onPageSelected(int position) throws  {
            float $f0 = 0.0f;
            if (this.mScrollState == 0) {
                PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
                if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                    $f0 = PagerTitleStrip.this.mLastKnownPositionOffset;
                }
                PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), $f0, true);
            }
        }

        public void onPageScrollStateChanged(int $i0) throws  {
            this.mScrollState = $i0;
        }

        public void onAdapterChanged(PagerAdapter $r1, PagerAdapter $r2) throws  {
            PagerTitleStrip.this.updateAdapter($r1, $r2);
        }

        public void onChanged() throws  {
            float $f0 = 0.0f;
            PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                $f0 = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), $f0, true);
        }
    }

    interface PagerTitleStripImpl {
        void setSingleLineAllCaps(TextView textView) throws ;
    }

    static class PagerTitleStripImplBase implements PagerTitleStripImpl {
        PagerTitleStripImplBase() throws  {
        }

        public void setSingleLineAllCaps(TextView $r1) throws  {
            $r1.setSingleLine();
        }
    }

    static class PagerTitleStripImplIcs implements PagerTitleStripImpl {
        PagerTitleStripImplIcs() throws  {
        }

        public void setSingleLineAllCaps(TextView $r1) throws  {
            PagerTitleStripIcs.setSingleLineAllCaps($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new PagerTitleStripImplIcs();
        } else {
            IMPL = new PagerTitleStripImplBase();
        }
    }

    private static void setSingleLineAllCaps(TextView $r0) throws  {
        IMPL.setSingleLineAllCaps($r0);
    }

    public PagerTitleStrip(Context $r1) throws  {
        this($r1, null);
    }

    public PagerTitleStrip(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mLastKnownCurrentPage = -1;
        this.mLastKnownPositionOffset = -1.0f;
        PagerTitleStrip pagerTitleStrip = this;
        this.mPageListener = new PageListener();
        TextView $r4 = new TextView($r1);
        this.mPrevText = $r4;
        addView($r4);
        $r4 = new TextView($r1);
        this.mCurrText = $r4;
        addView($r4);
        $r4 = new TextView($r1);
        this.mNextText = $r4;
        addView($r4);
        TypedArray $r6 = $r1.obtainStyledAttributes($r2, ATTRS);
        int $i0 = $r6.getResourceId(0, 0);
        if ($i0 != 0) {
            this.mPrevText.setTextAppearance($r1, $i0);
            this.mCurrText.setTextAppearance($r1, $i0);
            this.mNextText.setTextAppearance($r1, $i0);
        }
        int $i1 = $r6.getDimensionPixelSize(1, 0);
        if ($i1 != 0) {
            setTextSize(0, (float) $i1);
        }
        if ($r6.hasValue(2)) {
            $i1 = $r6.getColor(2, 0);
            this.mPrevText.setTextColor($i1);
            this.mCurrText.setTextColor($i1);
            this.mNextText.setTextColor($i1);
        }
        this.mGravity = $r6.getInteger(3, 80);
        $r6.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(0.6f);
        this.mPrevText.setEllipsize(TruncateAt.END);
        this.mCurrText.setEllipsize(TruncateAt.END);
        this.mNextText.setEllipsize(TruncateAt.END);
        boolean $z0 = false;
        if ($i0 != 0) {
            $r6 = $r1.obtainStyledAttributes($i0, TEXT_ATTRS);
            $z0 = $r6.getBoolean(0, false);
            $r6.recycle();
        }
        if ($z0) {
            setSingleLineAllCaps(this.mPrevText);
            setSingleLineAllCaps(this.mCurrText);
            setSingleLineAllCaps(this.mNextText);
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (16.0f * $r1.getResources().getDisplayMetrics().density);
    }

    public void setTextSpacing(int $i0) throws  {
        this.mScaledTextSpacing = $i0;
        requestLayout();
    }

    public int getTextSpacing() throws  {
        return this.mScaledTextSpacing;
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = 1.0d) float $f0) throws  {
        this.mNonPrimaryAlpha = ((int) (255.0f * $f0)) & 255;
        int $i0 = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor($i0);
        this.mNextText.setTextColor($i0);
    }

    public void setTextColor(@ColorInt int $i0) throws  {
        this.mTextColor = $i0;
        this.mCurrText.setTextColor($i0);
        $i0 = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor($i0);
        this.mNextText.setTextColor($i0);
    }

    public void setTextSize(int $i0, float $f0) throws  {
        this.mPrevText.setTextSize($i0, $f0);
        this.mCurrText.setTextSize($i0, $f0);
        this.mNextText.setTextSize($i0, $f0);
    }

    public void setGravity(int $i0) throws  {
        this.mGravity = $i0;
        requestLayout();
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        ViewParent $r1 = getParent();
        if ($r1 instanceof ViewPager) {
            ViewPager $r3 = (ViewPager) $r1;
            PagerAdapter $r4 = $r3.getAdapter();
            $r3.setInternalPageChangeListener(this.mPageListener);
            $r3.setOnAdapterChangeListener(this.mPageListener);
            this.mPager = $r3;
            updateAdapter(this.mWatchingAdapter != null ? (PagerAdapter) this.mWatchingAdapter.get() : null, $r4);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.mPager != null) {
            updateAdapter(this.mPager.getAdapter(), null);
            this.mPager.setInternalPageChangeListener(null);
            this.mPager.setOnAdapterChangeListener(null);
            this.mPager = null;
        }
    }

    void updateText(int $i0, PagerAdapter $r1) throws  {
        int $i2 = $r1 != null ? $r1.getCount() : 0;
        this.mUpdatingText = true;
        CharSequence $r2 = null;
        if ($i0 >= 1 && $r1 != null) {
            $r2 = $r1.getPageTitle($i0 - 1);
        }
        this.mPrevText.setText($r2);
        TextView $r3 = this.mCurrText;
        $r2 = ($r1 == null || $i0 >= $i2) ? null : $r1.getPageTitle($i0);
        $r3.setText($r2);
        $r2 = null;
        if ($i0 + 1 < $i2 && $r1 != null) {
            $r2 = $r1.getPageTitle($i0 + 1);
        }
        this.mNextText.setText($r2);
        $i2 = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR)), Integer.MIN_VALUE);
        int $i1 = MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.mPrevText.measure($i2, $i1);
        this.mCurrText.measure($i2, $i1);
        this.mNextText.measure($i2, $i1);
        this.mLastKnownCurrentPage = $i0;
        if (!this.mUpdatingPositions) {
            updateTextPositions($i0, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    public void requestLayout() throws  {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    void updateAdapter(PagerAdapter $r1, PagerAdapter $r2) throws  {
        if ($r1 != null) {
            $r1.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if ($r2 != null) {
            $r2.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference($r2);
        }
        if (this.mPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            updateText(this.mPager.getCurrentItem(), $r2);
            requestLayout();
        }
    }

    void updateTextPositions(int $i0, float $f0, boolean $z0) throws  {
        if ($i0 != this.mLastKnownCurrentPage) {
            updateText($i0, this.mPager.getAdapter());
        } else if (!$z0 && $f0 == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int $i11 = this.mPrevText.getMeasuredWidth();
        int $i7 = this.mCurrText.getMeasuredWidth();
        $i0 = this.mNextText.getMeasuredWidth();
        int $i6 = $i7 / 2;
        int $i10 = getWidth();
        int $i12 = getHeight();
        int $i13 = getPaddingLeft();
        int $i14 = getPaddingRight();
        int $i4 = getPaddingTop();
        int $i15 = getPaddingBottom();
        int $i5 = $i13 + $i6;
        $i6 = $i14 + $i6;
        $i5 = ($i10 - $i5) - $i6;
        float $f1 = $f0 + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        if ($f1 > 1.0f) {
            $f1 -= 1.0f;
        }
        $i6 = (($i10 - $i6) - ((int) (((float) $i5) * $f1))) - ($i7 / 2);
        $i7 = $i6 + $i7;
        int $i9 = this.mPrevText.getBaseline();
        $i5 = this.mCurrText.getBaseline();
        int $i8 = this.mNextText.getBaseline();
        int $i3 = Math.max(Math.max($i9, $i5), $i8);
        $i9 = $i3 - $i9;
        $i5 = $i3 - $i5;
        $i8 = $i3 - $i8;
        int $i2 = $i8 + this.mNextText.getMeasuredHeight();
        $i3 = Math.max(Math.max($i9 + this.mPrevText.getMeasuredHeight(), $i5 + this.mCurrText.getMeasuredHeight()), $i2);
        switch (this.mGravity & 112) {
            case 16:
                $i4 = ((($i12 - $i4) - $i15) - $i3) / 2;
                $i15 = $i4 + $i9;
                $i12 = $i4 + $i5;
                $i4 += $i8;
                break;
            case 80:
                $i4 = ($i12 - $i15) - $i3;
                $i15 = $i4 + $i9;
                $i12 = $i4 + $i5;
                $i4 += $i8;
                break;
            default:
                $i15 = $i4 + $i9;
                $i12 = $i4 + $i5;
                $i4 += $i8;
                break;
        }
        TextView $r3 = this.mCurrText;
        TextView $r4 = this.mCurrText;
        $r3.layout($i6, $i12, $i7, $r4.getMeasuredHeight() + $i12);
        $i13 = Math.min($i13, ($i6 - this.mScaledTextSpacing) - $i11);
        $r3 = this.mPrevText;
        $i11 = $i13 + $i11;
        $r4 = this.mPrevText;
        $r3.layout($i13, $i15, $i11, $r4.getMeasuredHeight() + $i15);
        $i10 = Math.max(($i10 - $i14) - $i0, this.mScaledTextSpacing + $i7);
        $r3 = this.mNextText;
        $i0 = $i10 + $i0;
        $r4 = this.mNextText;
        $r3.layout($i10, $i4, $i0, $r4.getMeasuredHeight() + $i4);
        this.mLastKnownPositionOffset = $f0;
        this.mUpdatingPositions = false;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        if (MeasureSpec.getMode($i0) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int $i2 = getPaddingTop() + getPaddingBottom();
        int $i5 = getChildMeasureSpec($i1, $i2, -2);
        int $i4 = MeasureSpec.getSize($i0);
        $i0 = getChildMeasureSpec($i0, (int) (((float) $i4) * 0.2f), -2);
        this.mPrevText.measure($i0, $i5);
        this.mCurrText.measure($i0, $i5);
        this.mNextText.measure($i0, $i5);
        if (MeasureSpec.getMode($i1) == 1073741824) {
            $i0 = MeasureSpec.getSize($i1);
        } else {
            $i0 = Math.max(getMinHeight(), this.mCurrText.getMeasuredHeight() + $i2);
        }
        setMeasuredDimension($i4, ViewCompat.resolveSizeAndState($i0, $i1, ViewCompat.getMeasuredState(this.mCurrText) << 16));
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) throws  {
        float $f0 = 0.0f;
        if (this.mPager != null) {
            if (this.mLastKnownPositionOffset >= 0.0f) {
                $f0 = this.mLastKnownPositionOffset;
            }
            updateTextPositions(this.mLastKnownCurrentPage, $f0, true);
        }
    }

    int getMinHeight() throws  {
        Drawable $r1 = getBackground();
        if ($r1 != null) {
            return $r1.getIntrinsicHeight();
        }
        return 0;
    }
}
