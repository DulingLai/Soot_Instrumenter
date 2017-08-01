package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

public abstract class SimpleItemAnimator extends ItemAnimator {
    private static final boolean DEBUG = false;
    private static final String TAG = "SimpleItemAnimator";
    boolean mSupportsChangeAnimations = true;

    public abstract boolean animateAdd(ViewHolder viewHolder) throws ;

    public abstract boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) throws ;

    public abstract boolean animateMove(ViewHolder viewHolder, int i, int i2, int i3, int i4) throws ;

    public abstract boolean animateRemove(ViewHolder viewHolder) throws ;

    public boolean getSupportsChangeAnimations() throws  {
        return this.mSupportsChangeAnimations;
    }

    public void setSupportsChangeAnimations(boolean $z0) throws  {
        this.mSupportsChangeAnimations = $z0;
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder $r1) throws  {
        return !this.mSupportsChangeAnimations || $r1.isInvalid();
    }

    public boolean animateDisappearance(@NonNull ViewHolder $r1, @NonNull ItemHolderInfo $r2, @Nullable ItemHolderInfo $r3) throws  {
        int $i0 = $r2.left;
        int $i1 = $r2.top;
        View $r4 = $r1.itemView;
        int $i2 = $r3 == null ? $r4.getLeft() : $r3.left;
        int $i3 = $r3 == null ? $r4.getTop() : $r3.top;
        if ($r1.isRemoved() || ($i0 == $i2 && $i1 == $i3)) {
            return animateRemove($r1);
        }
        $r4.layout($i2, $i3, $r4.getWidth() + $i2, $r4.getHeight() + $i3);
        return animateMove($r1, $i0, $i1, $i2, $i3);
    }

    public boolean animateAppearance(@NonNull ViewHolder $r1, @Nullable ItemHolderInfo $r2, @NonNull ItemHolderInfo $r3) throws  {
        if ($r2 == null || ($r2.left == $r3.left && $r2.top == $r3.top)) {
            return animateAdd($r1);
        }
        return animateMove($r1, $r2.left, $r2.top, $r3.left, $r3.top);
    }

    public boolean animatePersistence(@NonNull ViewHolder $r1, @NonNull ItemHolderInfo $r2, @NonNull ItemHolderInfo $r3) throws  {
        if ($r2.left == $r3.left && $r2.top == $r3.top) {
            dispatchMoveFinished($r1);
            return false;
        }
        return animateMove($r1, $r2.left, $r2.top, $r3.left, $r3.top);
    }

    public boolean animateChange(@NonNull ViewHolder $r1, @NonNull ViewHolder $r2, @NonNull ItemHolderInfo $r3, @NonNull ItemHolderInfo $r4) throws  {
        int $i2;
        int $i3;
        int $i0 = $r3.left;
        int $i1 = $r3.top;
        if ($r2.shouldIgnore()) {
            $i2 = $r3.left;
            $i3 = $r3.top;
        } else {
            $i2 = $r4.left;
            $i3 = $r4.top;
        }
        return animateChange($r1, $r2, $i0, $i1, $i2, $i3);
    }

    public final void dispatchRemoveFinished(ViewHolder $r1) throws  {
        onRemoveFinished($r1);
        dispatchAnimationFinished($r1);
    }

    public final void dispatchMoveFinished(ViewHolder $r1) throws  {
        onMoveFinished($r1);
        dispatchAnimationFinished($r1);
    }

    public final void dispatchAddFinished(ViewHolder $r1) throws  {
        onAddFinished($r1);
        dispatchAnimationFinished($r1);
    }

    public final void dispatchChangeFinished(ViewHolder $r1, boolean $z0) throws  {
        onChangeFinished($r1, $z0);
        dispatchAnimationFinished($r1);
    }

    public final void dispatchRemoveStarting(ViewHolder $r1) throws  {
        onRemoveStarting($r1);
    }

    public final void dispatchMoveStarting(ViewHolder $r1) throws  {
        onMoveStarting($r1);
    }

    public final void dispatchAddStarting(ViewHolder $r1) throws  {
        onAddStarting($r1);
    }

    public final void dispatchChangeStarting(ViewHolder $r1, boolean $z0) throws  {
        onChangeStarting($r1, $z0);
    }

    public void onRemoveStarting(ViewHolder item) throws  {
    }

    public void onRemoveFinished(ViewHolder item) throws  {
    }

    public void onAddStarting(ViewHolder item) throws  {
    }

    public void onAddFinished(ViewHolder item) throws  {
    }

    public void onMoveStarting(ViewHolder item) throws  {
    }

    public void onMoveFinished(ViewHolder item) throws  {
    }

    public void onChangeStarting(ViewHolder item, boolean oldItem) throws  {
    }

    public void onChangeFinished(ViewHolder item, boolean oldItem) throws  {
    }
}
