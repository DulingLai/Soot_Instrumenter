package android.support.v4.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    private static final String DEFAULT_CLASS_NAME = View.class.getName();
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private int mFocusedVirtualViewId = Integer.MIN_VALUE;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private ExploreByTouchNodeProvider mNodeProvider;
    private final int[] mTempGlobalRect = new int[2];
    private final Rect mTempParentRect = new Rect();
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempVisibleRect = new Rect();
    private final View mView;

    private class ExploreByTouchNodeProvider extends AccessibilityNodeProviderCompat {
        private ExploreByTouchNodeProvider() throws  {
        }

        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int $i0) throws  {
            return ExploreByTouchHelper.this.createNode($i0);
        }

        public boolean performAction(int $i0, int $i1, Bundle $r1) throws  {
            return ExploreByTouchHelper.this.performAction($i0, $i1, $r1);
        }
    }

    protected abstract int getVirtualViewAt(float f, float f2) throws ;

    protected abstract void getVisibleVirtualViews(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;)V"}) List<Integer> list) throws ;

    protected abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) throws ;

    protected abstract void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) throws ;

    protected abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) throws ;

    public ExploreByTouchHelper(View $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.mView = $r1;
        this.mManager = (AccessibilityManager) $r1.getContext().getSystemService("accessibility");
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) throws  {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new ExploreByTouchNodeProvider();
        }
        return this.mNodeProvider;
    }

    public boolean dispatchHoverEvent(MotionEvent $r1) throws  {
        boolean $z0 = true;
        if (!this.mManager.isEnabled()) {
            return false;
        }
        if (!AccessibilityManagerCompat.isTouchExplorationEnabled(this.mManager)) {
            return false;
        }
        switch ($r1.getAction()) {
            case 7:
            case 9:
                int $i0 = getVirtualViewAt($r1.getX(), $r1.getY());
                updateHoveredVirtualView($i0);
                if ($i0 == Integer.MIN_VALUE) {
                    $z0 = false;
                }
                return $z0;
            case 8:
                break;
            case 10:
                if (this.mFocusedVirtualViewId == Integer.MIN_VALUE) {
                    return false;
                }
                updateHoveredVirtualView(Integer.MIN_VALUE);
                return true;
            default:
                break;
        }
        return false;
    }

    public boolean sendEventForVirtualView(int $i0, int $i1) throws  {
        if ($i0 == Integer.MIN_VALUE) {
            return false;
        }
        if (!this.mManager.isEnabled()) {
            return false;
        }
        ViewParent $r3 = this.mView.getParent();
        if ($r3 == null) {
            return false;
        }
        return ViewParentCompat.requestSendAccessibilityEvent($r3, this.mView, createEvent($i0, $i1));
    }

    public void invalidateRoot() throws  {
        invalidateVirtualView(-1);
    }

    public void invalidateVirtualView(int $i0) throws  {
        sendEventForVirtualView($i0, 2048);
    }

    public int getFocusedVirtualView() throws  {
        return this.mFocusedVirtualViewId;
    }

    private void updateHoveredVirtualView(int $i0) throws  {
        if (this.mHoveredVirtualViewId != $i0) {
            int $i1 = this.mHoveredVirtualViewId;
            this.mHoveredVirtualViewId = $i0;
            sendEventForVirtualView($i0, 128);
            sendEventForVirtualView($i1, 256);
        }
    }

    private AccessibilityEvent createEvent(int $i0, int $i1) throws  {
        switch ($i0) {
            case -1:
                return createEventForHost($i1);
            default:
                return createEventForChild($i0, $i1);
        }
    }

    private AccessibilityEvent createEventForHost(int $i0) throws  {
        AccessibilityEvent $r2 = AccessibilityEvent.obtain($i0);
        ViewCompat.onInitializeAccessibilityEvent(this.mView, $r2);
        return $r2;
    }

    private AccessibilityEvent createEventForChild(int $i0, int $i1) throws  {
        AccessibilityEvent $r1 = AccessibilityEvent.obtain($i1);
        $r1.setEnabled(true);
        $r1.setClassName(DEFAULT_CLASS_NAME);
        onPopulateEventForVirtualView($i0, $r1);
        if ($r1.getText().isEmpty() && $r1.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        $r1.setPackageName(this.mView.getContext().getPackageName());
        AccessibilityEventCompat.asRecord($r1).setSource(this.mView, $i0);
        return $r1;
    }

    private AccessibilityNodeInfoCompat createNode(int $i0) throws  {
        switch ($i0) {
            case -1:
                return createNodeForHost();
            default:
                return createNodeForChild($i0);
        }
    }

    private AccessibilityNodeInfoCompat createNodeForHost() throws  {
        AccessibilityNodeInfoCompat $r3 = AccessibilityNodeInfoCompat.obtain(this.mView);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mView, $r3);
        onPopulateNodeForHost($r3);
        LinkedList $r1 = new LinkedList();
        getVisibleVirtualViews($r1);
        Iterator $r4 = $r1.iterator();
        while ($r4.hasNext()) {
            $r3.addChild(this.mView, ((Integer) $r4.next()).intValue());
        }
        return $r3;
    }

    private AccessibilityNodeInfoCompat createNodeForChild(int $i0) throws  {
        AccessibilityNodeInfoCompat $r1 = AccessibilityNodeInfoCompat.obtain();
        $r1.setEnabled(true);
        $r1.setClassName(DEFAULT_CLASS_NAME);
        $r1.setBoundsInParent(INVALID_PARENT_BOUNDS);
        onPopulateNodeForVirtualView($i0, $r1);
        if ($r1.getText() == null && $r1.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        $r1.getBoundsInParent(this.mTempParentRect);
        if (this.mTempParentRect.equals(INVALID_PARENT_BOUNDS)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int $i1 = $r1.getActions();
        if (($i1 & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else if (($i1 & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else {
            $r1.setPackageName(this.mView.getContext().getPackageName());
            $r1.setSource(this.mView, $i0);
            $r1.setParent(this.mView);
            if (this.mFocusedVirtualViewId == $i0) {
                $r1.setAccessibilityFocused(true);
                $r1.addAction(128);
            } else {
                $r1.setAccessibilityFocused(false);
                $r1.addAction(64);
            }
            if (intersectVisibleToUser(this.mTempParentRect)) {
                $r1.setVisibleToUser(true);
                $r1.setBoundsInParent(this.mTempParentRect);
            }
            this.mView.getLocationOnScreen(this.mTempGlobalRect);
            $i0 = this.mTempGlobalRect[0];
            $i1 = this.mTempGlobalRect[1];
            this.mTempScreenRect.set(this.mTempParentRect);
            this.mTempScreenRect.offset($i0, $i1);
            $r1.setBoundsInScreen(this.mTempScreenRect);
            return $r1;
        }
    }

    private boolean performAction(int $i0, int $i1, Bundle $r1) throws  {
        switch ($i0) {
            case -1:
                return performActionForHost($i1, $r1);
            default:
                return performActionForChild($i0, $i1, $r1);
        }
    }

    private boolean performActionForHost(int $i0, Bundle $r1) throws  {
        return ViewCompat.performAccessibilityAction(this.mView, $i0, $r1);
    }

    private boolean performActionForChild(int $i0, int $i1, Bundle $r1) throws  {
        switch ($i1) {
            case 64:
            case 128:
                return manageFocusForChild($i0, $i1, $r1);
            default:
                return onPerformActionForVirtualView($i0, $i1, $r1);
        }
    }

    private boolean manageFocusForChild(int $i0, int $i1, Bundle arguments) throws  {
        switch ($i1) {
            case 64:
                return requestAccessibilityFocus($i0);
            case 128:
                return clearAccessibilityFocus($i0);
            default:
                return false;
        }
    }

    private boolean intersectVisibleToUser(Rect $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if ($r1.isEmpty()) {
            return false;
        }
        if (this.mView.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent $r3 = this.mView.getParent();
        while ($r3 instanceof View) {
            View $r2 = (View) $r3;
            if (ViewCompat.getAlpha($r2) <= 0.0f) {
                return false;
            }
            if ($r2.getVisibility() != 0) {
                return false;
            }
            $r3 = $r2.getParent();
        }
        if ($r3 != null) {
            return this.mView.getLocalVisibleRect(this.mTempVisibleRect) ? $r1.intersect(this.mTempVisibleRect) : false;
        } else {
            return false;
        }
    }

    private boolean isAccessibilityFocused(int $i0) throws  {
        return this.mFocusedVirtualViewId == $i0;
    }

    private boolean requestAccessibilityFocus(int $i0) throws  {
        if (!this.mManager.isEnabled()) {
            return false;
        }
        if (!AccessibilityManagerCompat.isTouchExplorationEnabled(this.mManager)) {
            return false;
        }
        if (isAccessibilityFocused($i0)) {
            return false;
        }
        if (this.mFocusedVirtualViewId != Integer.MIN_VALUE) {
            sendEventForVirtualView(this.mFocusedVirtualViewId, 65536);
        }
        this.mFocusedVirtualViewId = $i0;
        this.mView.invalidate();
        sendEventForVirtualView($i0, 32768);
        return true;
    }

    private boolean clearAccessibilityFocus(int $i0) throws  {
        if (!isAccessibilityFocused($i0)) {
            return false;
        }
        this.mFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mView.invalidate();
        sendEventForVirtualView($i0, 65536);
        return true;
    }

    public void onPopulateNodeForHost(AccessibilityNodeInfoCompat node) throws  {
    }
}
