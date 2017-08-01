package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

public class ListViewCompat extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    private static final int[] STATE_SET_NOTHING = new int[]{0};
    private Field mIsChildViewEnabled;
    protected int mMotionPosition;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    private GateKeeperDrawable mSelector;
    final Rect mSelectorRect;

    private static class GateKeeperDrawable extends DrawableWrapper {
        private boolean mEnabled = true;

        public GateKeeperDrawable(Drawable $r1) throws  {
            super($r1);
        }

        void setEnabled(boolean $z0) throws  {
            this.mEnabled = $z0;
        }

        public boolean setState(int[] $r1) throws  {
            if (this.mEnabled) {
                return super.setState($r1);
            }
            return false;
        }

        public void draw(Canvas $r1) throws  {
            if (this.mEnabled) {
                super.draw($r1);
            }
        }

        public void setHotspot(float $f0, float $f1) throws  {
            if (this.mEnabled) {
                super.setHotspot($f0, $f1);
            }
        }

        public void setHotspotBounds(int $i0, int $i1, int $i2, int $i3) throws  {
            if (this.mEnabled) {
                super.setHotspotBounds($i0, $i1, $i2, $i3);
            }
        }

        public boolean setVisible(boolean $z0, boolean $z1) throws  {
            if (this.mEnabled) {
                return super.setVisible($z0, $z1);
            }
            return false;
        }
    }

    protected boolean touchModeDrawsInPressedStateCompat() throws  {
        return false;
    }

    public ListViewCompat(Context $r1) throws  {
        this($r1, null);
    }

    public ListViewCompat(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public ListViewCompat(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mSelectorRect = new Rect();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled.setAccessible(true);
        } catch (NoSuchFieldException $r3) {
            $r3.printStackTrace();
        }
    }

    public void setSelector(Drawable $r1) throws  {
        this.mSelector = $r1 != null ? new GateKeeperDrawable($r1) : null;
        super.setSelector(this.mSelector);
        Rect $r2 = new Rect();
        if ($r1 != null) {
            $r1.getPadding($r2);
        }
        this.mSelectionLeftPadding = $r2.left;
        this.mSelectionTopPadding = $r2.top;
        this.mSelectionRightPadding = $r2.right;
        this.mSelectionBottomPadding = $r2.bottom;
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        setSelectorEnabled(true);
        updateSelectorStateCompat();
    }

    protected void dispatchDraw(Canvas $r1) throws  {
        drawSelectorCompat($r1);
        super.dispatchDraw($r1);
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        switch ($r1.getAction()) {
            case 0:
                this.mMotionPosition = pointToPosition((int) $r1.getX(), (int) $r1.getY());
                break;
            default:
                break;
        }
        return super.onTouchEvent($r1);
    }

    protected void updateSelectorStateCompat() throws  {
        Drawable $r1 = getSelector();
        if ($r1 != null && shouldShowSelectorCompat()) {
            $r1.setState(getDrawableState());
        }
    }

    protected boolean shouldShowSelectorCompat() throws  {
        return touchModeDrawsInPressedStateCompat() && isPressed();
    }

    protected void drawSelectorCompat(Canvas $r1) throws  {
        if (!this.mSelectorRect.isEmpty()) {
            Drawable $r3 = getSelector();
            if ($r3 != null) {
                $r3.setBounds(this.mSelectorRect);
                $r3.draw($r1);
            }
        }
    }

    public int lookForSelectablePosition(int $i0, boolean $z0) throws  {
        ListAdapter $r1 = getAdapter();
        if ($r1 == null) {
            return -1;
        }
        if (isInTouchMode()) {
            return -1;
        }
        int $i1 = $r1.getCount();
        if (!getAdapter().areAllItemsEnabled()) {
            if ($z0) {
                $i0 = Math.max(0, $i0);
                while ($i0 < $i1 && !$r1.isEnabled($i0)) {
                    $i0++;
                }
            } else {
                $i0 = Math.min($i0, $i1 - 1);
                while ($i0 >= 0 && !$r1.isEnabled($i0)) {
                    $i0--;
                }
            }
            if ($i0 >= 0) {
                return $i0 < $i1 ? $i0 : -1;
            } else {
                return -1;
            }
        } else if ($i0 >= 0) {
            return $i0 < $i1 ? $i0 : -1;
        } else {
            return -1;
        }
    }

    protected void positionSelectorLikeTouchCompat(int $i0, View $r1, float $f0, float $f1) throws  {
        positionSelectorLikeFocusCompat($i0, $r1);
        Drawable $r2 = getSelector();
        if ($r2 != null && $i0 != -1) {
            DrawableCompat.setHotspot($r2, $f0, $f1);
        }
    }

    protected void positionSelectorLikeFocusCompat(int $i0, View $r1) throws  {
        boolean $z1;
        boolean $z0 = true;
        Drawable $r3 = getSelector();
        if ($r3 == null || $i0 == -1) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        if ($z1) {
            $r3.setVisible(false, false);
        }
        positionSelectorCompat($i0, $r1);
        if ($z1) {
            Rect $r2 = this.mSelectorRect;
            float $f0 = $r2.exactCenterX();
            float $f1 = $r2.exactCenterY();
            if (getVisibility() != 0) {
                $z0 = false;
            }
            $r3.setVisible($z0, false);
            DrawableCompat.setHotspot($r3, $f0, $f1);
        }
    }

    protected void positionSelectorCompat(int $i0, View $r1) throws  {
        Rect $r3 = this.mSelectorRect;
        $r3.set($r1.getLeft(), $r1.getTop(), $r1.getRight(), $r1.getBottom());
        $r3.left -= this.mSelectionLeftPadding;
        $r3.top -= this.mSelectionTopPadding;
        $r3.right += this.mSelectionRightPadding;
        $r3.bottom += this.mSelectionBottomPadding;
        try {
            boolean $z0 = this.mIsChildViewEnabled.getBoolean(this);
            if ($r1.isEnabled() != $z0) {
                this.mIsChildViewEnabled.set(this, Boolean.valueOf(!$z0));
                if ($i0 != -1) {
                    refreshDrawableState();
                }
            }
        } catch (IllegalAccessException $r2) {
            $r2.printStackTrace();
        }
    }

    public int measureHeightOfChildrenCompat(int $i0, int startPosition, int endPosition, int $i3, int $i4) throws  {
        startPosition = getListPaddingTop();
        int $i5 = getListPaddingBottom();
        getListPaddingLeft();
        getListPaddingRight();
        endPosition = getDividerHeight();
        Drawable $r1 = getDivider();
        ListAdapter $r2 = getAdapter();
        if ($r2 == null) {
            return startPosition + $i5;
        }
        startPosition += $i5;
        if (endPosition <= 0 || $r1 == null) {
            $i5 = 0;
        } else {
            $i5 = endPosition;
        }
        endPosition = 0;
        View $r3 = null;
        int $i6 = 0;
        int $i7 = $r2.getCount();
        int $i8 = 0;
        while ($i8 < $i7) {
            int $i9 = $r2.getItemViewType($i8);
            if ($i9 != $i6) {
                $r3 = null;
                $i6 = $i9;
            }
            View $r4 = $r2.getView($i8, $r3, this);
            $r3 = $r4;
            LayoutParams $r5 = $r4.getLayoutParams();
            LayoutParams $r6 = $r5;
            if ($r5 == null) {
                $r5 = generateDefaultLayoutParams();
                $r6 = $r5;
                $r4.setLayoutParams($r5);
            }
            if ($r6.height > 0) {
                $i9 = MeasureSpec.makeMeasureSpec($r6.height, 1073741824);
            } else {
                $i9 = MeasureSpec.makeMeasureSpec(0, 0);
            }
            $r4.measure($i0, $i9);
            $r4.forceLayout();
            if ($i8 > 0) {
                startPosition += $i5;
            }
            int $i1 = startPosition + $r4.getMeasuredHeight();
            startPosition = $i1;
            if ($i1 < $i3) {
                if ($i4 >= 0 && $i8 >= $i4) {
                    endPosition = startPosition;
                }
                $i8++;
            } else if ($i4 < 0 || $i8 <= $i4 || endPosition <= 0 || startPosition == $i3) {
                return $i3;
            } else {
                return endPosition;
            }
        }
        return startPosition;
    }

    protected void setSelectorEnabled(boolean $z0) throws  {
        if (this.mSelector != null) {
            this.mSelector.setEnabled($z0);
        }
    }
}
