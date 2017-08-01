package android.support.v7.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    final AccessibilityDelegateCompat mItemDelegate = new C02571();
    final RecyclerView mRecyclerView;

    class C02571 extends AccessibilityDelegateCompat {
        C02571() throws  {
        }

        public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            super.onInitializeAccessibilityNodeInfo($r1, $r2);
            if (!RecyclerViewAccessibilityDelegate.this.shouldIgnore() && RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null) {
                RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem($r1, $r2);
            }
        }

        public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
            if (super.performAccessibilityAction($r1, $i0, $r2)) {
                return true;
            }
            return (RecyclerViewAccessibilityDelegate.this.shouldIgnore() || RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() == null) ? false : RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().performAccessibilityActionForItem($r1, $i0, $r2);
        }
    }

    public RecyclerViewAccessibilityDelegate(RecyclerView $r1) throws  {
        this.mRecyclerView = $r1;
    }

    private boolean shouldIgnore() throws  {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }

    public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
        if (super.performAccessibilityAction($r1, $i0, $r2)) {
            return true;
        }
        return (shouldIgnore() || this.mRecyclerView.getLayoutManager() == null) ? false : this.mRecyclerView.getLayoutManager().performAccessibilityAction($i0, $r2);
    }

    public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
        super.onInitializeAccessibilityNodeInfo($r1, $r2);
        $r2.setClassName(RecyclerView.class.getName());
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo($r2);
        }
    }

    public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
        super.onInitializeAccessibilityEvent($r1, $r2);
        $r2.setClassName(RecyclerView.class.getName());
        if (($r1 instanceof RecyclerView) && !shouldIgnore()) {
            RecyclerView $r5 = (RecyclerView) $r1;
            if ($r5.getLayoutManager() != null) {
                $r5.getLayoutManager().onInitializeAccessibilityEvent($r2);
            }
        }
    }

    AccessibilityDelegateCompat getItemDelegate() throws  {
        return this.mItemDelegate;
    }
}
