package com.waze.view.tabs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.map.CanvasFont;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.text.WazeTextView;
import java.util.ArrayList;
import java.util.List;

public class SlidingTabBar extends HorizontalScrollView {
    public static final long TAB_ANIMATION_DURATION = 200;
    private long mAnimationStartTime;
    private LinearLayout mContent;
    private int mCurrentDrawX;
    private Paint mPaint;
    private SlidingTabBarProvider mProvider;
    private TextView mSelectedTab;
    private int mStartDrawX;
    private Paint mTabPaint;
    private List<TextView> mTabs;
    private int mTargetDrawX;

    public interface SlidingTabBarProvider {
        String getTitleForTab(int i);

        int getTotalTabs();

        void onTabClick(int i);
    }

    public SlidingTabBar(Context context) {
        this(context, null);
    }

    public SlidingTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mContent = new LinearLayout(getContext());
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mContent.setOrientation(0);
        this.mContent.setLayoutParams(new LayoutParams(-2, -1));
        this.mContent.setGravity(16);
        addView(this.mContent);
        setWillNotDraw(false);
        this.mPaint = new Paint();
        this.mPaint.setColor(getResources().getColor(C1283R.color.BlueDeep));
        this.mPaint.setStyle(Style.FILL);
        PixelMeasure.setResourceIfUnset(getResources());
        this.mTabPaint = new Paint();
        this.mTabPaint.setColor(getResources().getColor(C1283R.color.BlueDeep));
        this.mTabPaint.setStyle(Style.STROKE);
        this.mTabPaint.setStrokeWidth((float) PixelMeasure.dp(1));
        this.mTabs = new ArrayList();
    }

    public void setProvider(SlidingTabBarProvider provider) {
        this.mProvider = provider;
        initTabs();
    }

    private void initTabs() {
        int totalTabs = this.mProvider.getTotalTabs();
        for (int i = 0; i < totalTabs; i++) {
            createTab(i);
        }
        if (this.mContent.getChildCount() > 0) {
            this.mCurrentDrawX = 0;
            this.mTargetDrawX = 0;
            this.mStartDrawX = 0;
            onTabClick((TextView) this.mTabs.get(0), 0);
        }
    }

    private void createTab(final int index) {
        WazeTextView tabView = new WazeTextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PixelMeasure.dp(104), -2);
        params.leftMargin = PixelMeasure.dp(8);
        params.rightMargin = PixelMeasure.dp(8);
        tabView.setLayoutParams(params);
        tabView.setTextSize(16.0f);
        tabView.setTextColor(getResources().getColor(C1283R.color.BlueDeep));
        tabView.setGravity(17);
        tabView.setFont(11, 1);
        tabView.setText(this.mProvider.getTitleForTab(index));
        tabView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SlidingTabBar.this.onTabClick((TextView) v, index);
            }
        });
        if (this.mContent.getChildCount() > 0) {
            addSeparator();
        }
        this.mContent.addView(tabView);
        this.mTabs.add(tabView);
    }

    private void addSeparator() {
        View separator = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PixelMeasure.dp(1), -1);
        params.topMargin = PixelMeasure.dp(16);
        params.bottomMargin = PixelMeasure.dp(16);
        separator.setLayoutParams(params);
        separator.setBackgroundColor(getResources().getColor(C1283R.color.BlueDeepLight));
        separator.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mContent.addView(separator);
    }

    private void onTabClick(TextView tab, int index) {
        if (this.mSelectedTab != null) {
            applyClickedState(this.mSelectedTab, false);
            if (this.mSelectedTab != tab) {
                this.mStartDrawX = this.mTabs.indexOf(this.mSelectedTab) * PixelMeasure.dp(121);
                this.mTargetDrawX = PixelMeasure.dp(121) * index;
                this.mAnimationStartTime = System.currentTimeMillis();
                postInvalidate();
            }
        }
        this.mProvider.onTabClick(index);
        this.mSelectedTab = tab;
        smoothScrollTo(((PixelMeasure.dp(121) * index) - (getMeasuredWidth() / 2)) + PixelMeasure.dp(60), 0);
        applyClickedState(this.mSelectedTab, true);
    }

    public void setSelectedIndex(int index) {
        onTabClick((TextView) this.mTabs.get(index), index);
    }

    private void applyClickedState(TextView clickedTab, boolean clicked) {
        clickedTab.setTextColor(getResources().getColor(clicked ? C1283R.color.White : C1283R.color.BlueDeep));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mCurrentDrawX != this.mTargetDrawX) {
            float timeRatio = ((float) (System.currentTimeMillis() - this.mAnimationStartTime)) / 200.0f;
            float animationRatio = ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR.getInterpolation(timeRatio);
            if (timeRatio >= 1.0f) {
                this.mCurrentDrawX = this.mTargetDrawX;
            } else {
                this.mCurrentDrawX = (int) (((float) this.mStartDrawX) + (((float) (this.mTargetDrawX - this.mStartDrawX)) * animationRatio));
                postInvalidate();
            }
        }
        canvas.drawRect((float) this.mCurrentDrawX, (float) PixelMeasure.dp(52), (float) (this.mCurrentDrawX + PixelMeasure.dp(120)), (float) PixelMeasure.dp(56), this.mPaint);
    }
}
