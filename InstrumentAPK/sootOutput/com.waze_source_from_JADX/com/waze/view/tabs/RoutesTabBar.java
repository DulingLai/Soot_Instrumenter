package com.waze.view.tabs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.routes.AlternativeRoute;
import com.waze.view.anim.AnimationUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class RoutesTabBar extends LinearLayout {
    private static final int ANIMATION_STEP_MS = 20;
    private static final int ANIMATION_TIME_MS = 300;
    private static final int CARET_HEIGHT_DP = 6;
    private static final int CARET_WIDTH_DP = 10;
    private static final int SELECTED_DISTANCE_LABEL_COLOR = -1291845632;
    private static final int SELECTED_TIME_LABEL_COLOR = -1;
    private static final int SEPARATOR_WIDTH_DP = 1;
    private static final int SHADOW_COLOR = 1073741824;
    private static final int SHADOW_SIZE_DP = 5;
    private long mAnimationStartTime;
    private Path mBgPath;
    private float mDensity;
    private Paint mIdlePaint;
    private int mIdleTabColor = -854024;
    private AccelerateDecelerateInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private boolean mIsAnimating;
    private int mMargin = 10;
    private TextView mSelectedDistanceLabel;
    private View mSelectedTab = null;
    private int mSelectedTabColor = -10066177;
    private TextView mSelectedTimeLabel;
    private Paint mSelectingPaint;
    private Path mSelectingPath;
    private int mSeparatorColor = -6736897;
    private ArrayList<Line> mSeparatorPaths;
    private Paint mSeparatorsPaint;
    private TabTransitionListener mTabTransitionListener;
    private TextView mUnselectedDistanceLabel = null;
    private View mUnselectedTab = null;
    private int mUnselectedTabColor;
    private TextView mUnselectedTimeLabel = null;
    private Paint mUnselectingPaint;
    private Path mUnselectingPath;

    public interface TabTransitionListener {
        void onUpdate(View view, float f);
    }

    class C32681 implements Runnable {
        C32681() {
        }

        public void run() {
            long deltaT = System.currentTimeMillis() - RoutesTabBar.this.mAnimationStartTime;
            float delta = ((float) deltaT) / 300.0f;
            if (delta > 1.0f) {
                delta = 1.0f;
            }
            float i = RoutesTabBar.this.mInterpolator.getInterpolation(delta);
            if (RoutesTabBar.this.mUnselectedTab != null) {
                RoutesTabBar.this.mUnselectingPath = RoutesTabBar.this.drawSelectionPath(RoutesTabBar.this.mUnselectedTab, 1.0f - i);
                RoutesTabBar.this.mUnselectingPaint.setColor(AnimationUtils.mixColors(RoutesTabBar.this.mIdleTabColor, RoutesTabBar.this.mUnselectedTabColor, i));
                RoutesTabBar.this.mUnselectingPaint.setShadowLayer((RoutesTabBar.this.mDensity * 5.0f) * (1.0f - i), 0.0f, ((RoutesTabBar.this.mDensity * 5.0f) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) * (1.0f - i), RoutesTabBar.SHADOW_COLOR);
                if (RoutesTabBar.this.mTabTransitionListener != null) {
                    RoutesTabBar.this.mTabTransitionListener.onUpdate(RoutesTabBar.this.mUnselectedTab, 1.0f - i);
                }
                RoutesTabBar.this.mUnselectedTimeLabel.setTextColor(AnimationUtils.mixColors(RoutesTabBar.this.mUnselectedTabColor, -1, i));
                RoutesTabBar.this.mUnselectedDistanceLabel.setTextColor(AnimationUtils.mixColors(RoutesTabBar.this.mUnselectedTabColor, RoutesTabBar.SELECTED_DISTANCE_LABEL_COLOR, i));
            }
            RoutesTabBar.this.mSelectingPath = RoutesTabBar.this.drawSelectionPath(RoutesTabBar.this.mSelectedTab, i);
            RoutesTabBar.this.mSelectingPaint.setColor(AnimationUtils.mixColors(RoutesTabBar.this.mSelectedTabColor, RoutesTabBar.this.mIdleTabColor, i));
            RoutesTabBar.this.mSelectingPaint.setShadowLayer((RoutesTabBar.this.mDensity * 5.0f) * i, 0.0f, ((RoutesTabBar.this.mDensity * 5.0f) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) * i, RoutesTabBar.SHADOW_COLOR);
            if (RoutesTabBar.this.mTabTransitionListener != null) {
                RoutesTabBar.this.mTabTransitionListener.onUpdate(RoutesTabBar.this.mSelectedTab, i);
            }
            RoutesTabBar.this.mSelectedTimeLabel.setTextColor(AnimationUtils.mixColors(-1, RoutesTabBar.this.mSelectedTabColor, i));
            RoutesTabBar.this.mSelectedDistanceLabel.setTextColor(AnimationUtils.mixColors(RoutesTabBar.SELECTED_DISTANCE_LABEL_COLOR, RoutesTabBar.this.mSelectedTabColor, i));
            if (deltaT < 300) {
                RoutesTabBar.this.postDelayed(this, 20);
            } else {
                RoutesTabBar.this.mIsAnimating = false;
            }
            RoutesTabBar.this.invalidate();
        }
    }

    class Line {
        float startX;
        float startY;
        float stopX;
        float stopY;

        Line() {
        }
    }

    public RoutesTabBar(Context context) {
        super(context);
        init(context);
    }

    public RoutesTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setSelected(View tab) {
        if (tab != this.mSelectedTab) {
            if (!(this.mUnselectedDistanceLabel == null || this.mUnselectedTimeLabel == null || !this.mIsAnimating)) {
                int routeColor = ((AlternativeRoute) this.mUnselectedTab.getTag()).routeColor;
                this.mUnselectedDistanceLabel.setTextColor(routeColor);
                this.mUnselectedTimeLabel.setTextColor(routeColor);
            }
            this.mIsAnimating = true;
            if (this.mSelectedTab != null) {
                this.mUnselectedTab = this.mSelectedTab;
                this.mUnselectingPath = this.mSelectingPath;
                this.mUnselectedTimeLabel = (TextView) this.mUnselectedTab.findViewById(C1283R.id.routesMapTabTitle);
                this.mUnselectedDistanceLabel = (TextView) this.mUnselectedTab.findViewById(C1283R.id.routesMapTabSub);
                this.mUnselectedTabColor = ((AlternativeRoute) this.mUnselectedTab.getTag()).routeColor;
                Paint temp = this.mUnselectingPaint;
                this.mUnselectingPaint = this.mSelectingPaint;
                this.mSelectingPaint = temp;
            } else {
                this.mUnselectedTimeLabel = null;
                this.mUnselectedDistanceLabel = null;
            }
            this.mSelectingPath = drawSelectionPath(tab, 0.0f);
            this.mSelectedTab = tab;
            this.mSelectedTimeLabel = (TextView) this.mSelectedTab.findViewById(C1283R.id.routesMapTabTitle);
            this.mSelectedDistanceLabel = (TextView) this.mSelectedTab.findViewById(C1283R.id.routesMapTabSub);
            this.mSelectedTabColor = ((AlternativeRoute) this.mSelectedTab.getTag()).routeColor;
            this.mSelectingPaint.setColor(this.mIdleTabColor);
            this.mAnimationStartTime = System.currentTimeMillis();
            postDelayed(new C32681(), 20);
            invalidate();
        }
    }

    public boolean isAnimating() {
        return this.mIsAnimating;
    }

    private void init(Context context) {
        setWillNotDraw(false);
        setLayerType(1, null);
        Resources res = context.getResources();
        this.mDensity = res.getDisplayMetrics().density;
        this.mSeparatorColor = res.getColor(C1283R.color.Light);
        this.mSelectedTabColor = res.getColor(C1283R.color.ElectricBlue);
        this.mMargin = -res.getDimensionPixelSize(C1283R.dimen.routes_tab_bar_bottom_margin);
        if (getOrientation() == 0) {
            setPadding(0, 0, 0, this.mMargin);
        } else {
            setPadding(this.mMargin, 0, 0, 0);
        }
        this.mIdlePaint = new Paint();
        this.mIdlePaint.setStyle(Style.FILL_AND_STROKE);
        this.mIdlePaint.setColor(this.mIdleTabColor);
        this.mIdlePaint.setAntiAlias(true);
        if (getOrientation() == 0) {
            this.mIdlePaint.setShadowLayer(this.mDensity * 5.0f, 0.0f, this.mDensity * 5.0f, SHADOW_COLOR);
        } else {
            this.mIdlePaint.setShadowLayer(this.mDensity * 5.0f, (-this.mDensity) * 5.0f, 0.0f, SHADOW_COLOR);
        }
        this.mSeparatorsPaint = new Paint();
        this.mSeparatorsPaint.setStyle(Style.STROKE);
        this.mSeparatorsPaint.setColor(this.mSeparatorColor);
        this.mSeparatorsPaint.setStrokeWidth((this.mDensity * 1.0f) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        this.mSelectingPaint = new Paint();
        this.mSelectingPaint.setStyle(Style.FILL_AND_STROKE);
        this.mSelectingPaint.setColor(this.mIdleTabColor);
        this.mSelectingPaint.setAntiAlias(true);
        this.mSelectingPaint.setShadowLayer(0.0f, 0.0f, 0.0f, SHADOW_COLOR);
        this.mUnselectingPaint = new Paint();
        this.mUnselectingPaint.setStyle(Style.FILL_AND_STROKE);
        this.mUnselectingPaint.setColor(this.mSelectedTabColor);
        this.mUnselectingPaint.setAntiAlias(true);
        this.mUnselectingPaint.setShadowLayer(this.mDensity * 5.0f, 0.0f, this.mDensity * 5.0f, SHADOW_COLOR);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mBgPath = new Path();
        if (getOrientation() == 0) {
            this.mBgPath.addRect(0.0f, 0.0f, (float) (r - l), (float) ((b - t) - this.mMargin), Direction.CW);
            return;
        }
        this.mBgPath.addRect((float) this.mMargin, 0.0f, (float) (r - l), (float) (b - t), Direction.CW);
    }

    private void calcSeparators() {
        int count = getChildCount();
        this.mSeparatorPaths = new ArrayList(count);
        int i;
        View child;
        if (getOrientation() == 0) {
            int curLeft = 0;
            for (i = 0; i < count; i++) {
                child = getChildAt(i);
                if (curLeft != 0) {
                    Line sep = new Line();
                    sep.startX = (float) curLeft;
                    sep.startY = 0.0f;
                    sep.stopX = (float) curLeft;
                    sep.stopY = (float) child.getMeasuredHeight();
                    this.mSeparatorPaths.add(sep);
                }
                curLeft += child.getMeasuredWidth();
            }
            return;
        }
        int curBottom = 0;
        for (i = 0; i < count; i++) {
            child = getChildAt(i);
            if (curBottom != 0) {
                sep = new Line();
                sep.startX = (float) this.mMargin;
                sep.startY = (float) curBottom;
                sep.stopX = (float) (child.getMeasuredWidth() + this.mMargin);
                sep.stopY = (float) curBottom;
                this.mSeparatorPaths.add(sep);
            }
            curBottom += child.getMeasuredHeight();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calcSeparators();
    }

    private Path drawSelectionPath(View v, float stage) {
        Path p = new Path();
        int left = v.getLeft();
        int top = v.getTop();
        int right = v.getRight();
        int bottom = v.getBottom();
        float caretLeft;
        float caretBottom;
        if (getOrientation() == 0) {
            float centerX = ((float) (left + right)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            float caretWidth = (10.0f * this.mDensity) * stage;
            caretLeft = centerX - caretWidth;
            float caretRight = centerX + caretWidth;
            caretBottom = ((float) bottom) + ((6.0f * this.mDensity) * stage);
            p.moveTo((float) left, (float) top);
            p.lineTo((float) right, (float) top);
            p.lineTo((float) right, (float) bottom);
            p.lineTo(caretRight, (float) bottom);
            p.lineTo(centerX, caretBottom);
            p.lineTo(caretLeft, (float) bottom);
            p.lineTo((float) left, (float) bottom);
            p.lineTo((float) left, (float) top);
        } else {
            float centerY = ((float) (top + bottom)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            float caretHeight = (10.0f * this.mDensity) * stage;
            float caretTop = centerY - caretHeight;
            caretBottom = centerY + caretHeight;
            caretLeft = ((float) left) - ((6.0f * this.mDensity) * stage);
            p.moveTo((float) left, (float) top);
            p.lineTo((float) right, (float) top);
            p.lineTo((float) right, (float) bottom);
            p.lineTo((float) left, (float) bottom);
            p.lineTo((float) left, caretBottom);
            p.lineTo(caretLeft, centerY);
            p.lineTo((float) left, caretTop);
            p.lineTo((float) left, (float) top);
        }
        return p;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mBgPath, this.mIdlePaint);
        Iterator it = this.mSeparatorPaths.iterator();
        while (it.hasNext()) {
            Line sep = (Line) it.next();
            canvas.drawLine(sep.startX, sep.startY, sep.stopX, sep.stopY, this.mSeparatorsPaint);
        }
        if (this.mUnselectingPath != null) {
            canvas.drawPath(this.mUnselectingPath, this.mUnselectingPaint);
        }
        if (this.mSelectingPath != null) {
            canvas.drawPath(this.mSelectingPath, this.mSelectingPaint);
        }
    }

    public void setTabTransitionListener(TabTransitionListener tabTransitionListener) {
        this.mTabTransitionListener = tabTransitionListener;
    }
}
