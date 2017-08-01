package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private ArrayList<ViewHolder> mAddAnimations = new ArrayList();
    private ArrayList<ArrayList<ViewHolder>> mAdditionsList = new ArrayList();
    private ArrayList<ViewHolder> mChangeAnimations = new ArrayList();
    private ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList();
    private ArrayList<ViewHolder> mMoveAnimations = new ArrayList();
    private ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList();
    private ArrayList<ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<ViewHolder> mPendingRemovals = new ArrayList();
    private ArrayList<ViewHolder> mRemoveAnimations = new ArrayList();

    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        private VpaListenerAdapter() throws  {
        }

        public void onAnimationStart(View view) throws  {
        }

        public void onAnimationEnd(View view) throws  {
        }

        public void onAnimationCancel(View view) throws  {
        }
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public ViewHolder newHolder;
        public ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder $r1, ViewHolder $r2) throws  {
            this.oldHolder = $r1;
            this.newHolder = $r2;
        }

        private ChangeInfo(ViewHolder $r1, ViewHolder $r2, int $i0, int $i1, int $i2, int $i3) throws  {
            this($r1, $r2);
            this.fromX = $i0;
            this.fromY = $i1;
            this.toX = $i2;
            this.toY = $i3;
        }

        public String toString() throws  {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public ViewHolder holder;
        public int toX;
        public int toY;

        private MoveInfo(ViewHolder $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            this.holder = $r1;
            this.fromX = $i0;
            this.fromY = $i1;
            this.toX = $i2;
            this.toY = $i3;
        }
    }

    public void runPendingAnimations() throws  {
        boolean $z3;
        boolean $z0 = !this.mPendingRemovals.isEmpty();
        boolean $z1 = !this.mPendingMoves.isEmpty();
        boolean $z2 = !this.mPendingChanges.isEmpty();
        if (this.mPendingAdditions.isEmpty()) {
            $z3 = false;
        } else {
            $z3 = true;
        }
        if ($z0 || $z1 || $z3 || $z2) {
            final ArrayList $r2;
            ArrayList $r8;
            Iterator $r5 = this.mPendingRemovals.iterator();
            while ($r5.hasNext()) {
                animateRemoveImpl((ViewHolder) $r5.next());
            }
            this.mPendingRemovals.clear();
            if ($z1) {
                $r2 = new ArrayList();
                $r8 = this.mPendingMoves;
                $r2.addAll($r8);
                $r8 = this.mMovesList;
                $r8.add($r2);
                $r8 = this.mPendingMoves;
                $r8.clear();
                C02241 c02241 = new Runnable() {
                    public void run() throws  {
                        Iterator $r2 = $r2.iterator();
                        while ($r2.hasNext()) {
                            MoveInfo $r4 = (MoveInfo) $r2.next();
                            DefaultItemAnimator.this.animateMoveImpl($r4.holder, $r4.fromX, $r4.fromY, $r4.toX, $r4.toY);
                        }
                        $r2.clear();
                        DefaultItemAnimator.this.mMovesList.remove($r2);
                    }
                };
                if ($z0) {
                    ViewCompat.postOnAnimationDelayed(((MoveInfo) $r2.get(0)).holder.itemView, c02241, getRemoveDuration());
                } else {
                    c02241.run();
                }
            }
            if ($z2) {
                $r2 = new ArrayList();
                $r8 = this.mPendingChanges;
                $r2.addAll($r8);
                $r8 = this.mChangesList;
                $r8.add($r2);
                $r8 = this.mPendingChanges;
                $r8.clear();
                C02252 c02252 = new Runnable() {
                    public void run() throws  {
                        Iterator $r2 = $r2.iterator();
                        while ($r2.hasNext()) {
                            DefaultItemAnimator.this.animateChangeImpl((ChangeInfo) $r2.next());
                        }
                        $r2.clear();
                        DefaultItemAnimator.this.mChangesList.remove($r2);
                    }
                };
                if ($z0) {
                    ViewCompat.postOnAnimationDelayed(((ChangeInfo) $r2.get(0)).oldHolder.itemView, c02252, getRemoveDuration());
                } else {
                    c02252.run();
                }
            }
            if ($z3) {
                $r2 = new ArrayList();
                $r8 = this.mPendingAdditions;
                $r2.addAll($r8);
                $r8 = this.mAdditionsList;
                $r8.add($r2);
                $r8 = this.mPendingAdditions;
                $r8.clear();
                C02263 c02263 = new Runnable() {
                    public void run() throws  {
                        Iterator $r2 = $r2.iterator();
                        while ($r2.hasNext()) {
                            DefaultItemAnimator.this.animateAddImpl((ViewHolder) $r2.next());
                        }
                        $r2.clear();
                        DefaultItemAnimator.this.mAdditionsList.remove($r2);
                    }
                };
                if ($z0 || $z1 || $z2) {
                    long removeDuration = ($z0 ? getRemoveDuration() : 0) + Math.max($z1 ? getMoveDuration() : 0, $z2 ? getChangeDuration() : 0);
                    View view = ((ViewHolder) $r2.get(null)).itemView;
                    View $r10 = view;
                    ViewCompat.postOnAnimationDelayed(view, c02263, removeDuration);
                    return;
                }
                c02263.run();
            }
        }
    }

    public boolean animateRemove(ViewHolder $r1) throws  {
        resetAnimation($r1);
        this.mPendingRemovals.add($r1);
        return true;
    }

    private void animateRemoveImpl(final ViewHolder $r1) throws  {
        final ViewPropertyAnimatorCompat $r3 = ViewCompat.animate($r1.itemView);
        this.mRemoveAnimations.add($r1);
        $r3.setDuration(getRemoveDuration()).alpha(0.0f).setListener(new VpaListenerAdapter() {
            public void onAnimationStart(View view) throws  {
                DefaultItemAnimator.this.dispatchRemoveStarting($r1);
            }

            public void onAnimationEnd(View $r1) throws  {
                $r3.setListener(null);
                ViewCompat.setAlpha($r1, 1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished($r1);
                DefaultItemAnimator.this.mRemoveAnimations.remove($r1);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateAdd(ViewHolder $r1) throws  {
        resetAnimation($r1);
        ViewCompat.setAlpha($r1.itemView, 0.0f);
        this.mPendingAdditions.add($r1);
        return true;
    }

    private void animateAddImpl(final ViewHolder $r1) throws  {
        final ViewPropertyAnimatorCompat $r4 = ViewCompat.animate($r1.itemView);
        this.mAddAnimations.add($r1);
        $r4.alpha(1.0f).setDuration(getAddDuration()).setListener(new VpaListenerAdapter() {
            public void onAnimationStart(View view) throws  {
                DefaultItemAnimator.this.dispatchAddStarting($r1);
            }

            public void onAnimationCancel(View $r1) throws  {
                ViewCompat.setAlpha($r1, 1.0f);
            }

            public void onAnimationEnd(View view) throws  {
                $r4.setListener(null);
                DefaultItemAnimator.this.dispatchAddFinished($r1);
                DefaultItemAnimator.this.mAddAnimations.remove($r1);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateMove(ViewHolder $r1, int $i4, int $i5, int $i0, int $i1) throws  {
        View $r2 = $r1.itemView;
        $i4 = (int) (((float) $i4) + ViewCompat.getTranslationX($r1.itemView));
        $i5 = (int) (((float) $i5) + ViewCompat.getTranslationY($r1.itemView));
        resetAnimation($r1);
        int $i2 = $i0 - $i4;
        int $i3 = $i1 - $i5;
        if ($i2 == 0 && $i3 == 0) {
            dispatchMoveFinished($r1);
            return false;
        }
        if ($i2 != 0) {
            ViewCompat.setTranslationX($r2, (float) (-$i2));
        }
        if ($i3 != 0) {
            ViewCompat.setTranslationY($r2, (float) (-$i3));
        }
        this.mPendingMoves.add(new MoveInfo($r1, $i4, $i5, $i0, $i1));
        return true;
    }

    private void animateMoveImpl(ViewHolder $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        View $r2 = $r1.itemView;
        $i0 = $i2 - $i0;
        $i1 = $i3 - $i1;
        if ($i0 != 0) {
            ViewCompat.animate($r2).translationX(0.0f);
        }
        if ($i1 != 0) {
            ViewCompat.animate($r2).translationY(0.0f);
        }
        ViewPropertyAnimatorCompat $r3 = ViewCompat.animate($r2);
        this.mMoveAnimations.add($r1);
        final ViewHolder viewHolder = $r1;
        final int i = $i0;
        final int i2 = $i1;
        final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = $r3;
        $r3.setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() {
            public void onAnimationStart(View view) throws  {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder);
            }

            public void onAnimationCancel(View $r1) throws  {
                if (i != 0) {
                    ViewCompat.setTranslationX($r1, 0.0f);
                }
                if (i2 != 0) {
                    ViewCompat.setTranslationY($r1, 0.0f);
                }
            }

            public void onAnimationEnd(View view) throws  {
                viewPropertyAnimatorCompat.setListener(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder);
                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateChange(ViewHolder $r1, ViewHolder $r2, int $i0, int $i1, int $i2, int $i3) throws  {
        if ($r1 == $r2) {
            return animateMove($r1, $i0, $i1, $i2, $i3);
        }
        float $f0 = ViewCompat.getTranslationX($r1.itemView);
        float $f1 = ViewCompat.getTranslationY($r1.itemView);
        float $f2 = ViewCompat.getAlpha($r1.itemView);
        resetAnimation($r1);
        int $i4 = (int) (((float) ($i2 - $i0)) - $f0);
        int $i5 = (int) (((float) ($i3 - $i1)) - $f1);
        ViewCompat.setTranslationX($r1.itemView, $f0);
        ViewCompat.setTranslationY($r1.itemView, $f1);
        ViewCompat.setAlpha($r1.itemView, $f2);
        if ($r2 != null) {
            resetAnimation($r2);
            ViewCompat.setTranslationX($r2.itemView, (float) (-$i4));
            ViewCompat.setTranslationY($r2.itemView, (float) (-$i5));
            ViewCompat.setAlpha($r2.itemView, 0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo($r1, $r2, $i0, $i1, $i2, $i3));
        return true;
    }

    private void animateChangeImpl(final ChangeInfo $r1) throws  {
        View $r3;
        View $r4;
        ViewHolder $r2 = $r1.oldHolder;
        if ($r2 == null) {
            $r3 = null;
        } else {
            $r3 = $r2.itemView;
        }
        $r2 = $r1.newHolder;
        if ($r2 != null) {
            $r4 = $r2.itemView;
        } else {
            $r4 = null;
        }
        if ($r3 != null) {
            final ViewPropertyAnimatorCompat $r5 = ViewCompat.animate($r3).setDuration(getChangeDuration());
            this.mChangeAnimations.add($r1.oldHolder);
            $r5.translationX((float) ($r1.toX - $r1.fromX));
            $r5.translationY((float) ($r1.toY - $r1.fromY));
            $r5.alpha(0.0f).setListener(new VpaListenerAdapter() {
                public void onAnimationStart(View view) throws  {
                    DefaultItemAnimator.this.dispatchChangeStarting($r1.oldHolder, true);
                }

                public void onAnimationEnd(View $r1) throws  {
                    $r5.setListener(null);
                    ViewCompat.setAlpha($r1, 1.0f);
                    ViewCompat.setTranslationX($r1, 0.0f);
                    ViewCompat.setTranslationY($r1, 0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished($r1.oldHolder, true);
                    DefaultItemAnimator.this.mChangeAnimations.remove($r1.oldHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }
        if ($r4 != null) {
            $r5 = ViewCompat.animate($r4);
            this.mChangeAnimations.add($r1.newHolder);
            $r5.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f).setListener(new VpaListenerAdapter() {
                public void onAnimationStart(View view) throws  {
                    DefaultItemAnimator.this.dispatchChangeStarting($r1.newHolder, false);
                }

                public void onAnimationEnd(View view) throws  {
                    $r5.setListener(null);
                    ViewCompat.setAlpha($r4, 1.0f);
                    ViewCompat.setTranslationX($r4, 0.0f);
                    ViewCompat.setTranslationY($r4, 0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished($r1.newHolder, false);
                    DefaultItemAnimator.this.mChangeAnimations.remove($r1.newHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }
    }

    private void endChangeAnimation(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/DefaultItemAnimator$ChangeInfo;", ">;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ")V"}) List<ChangeInfo> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/DefaultItemAnimator$ChangeInfo;", ">;", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ")V"}) ViewHolder $r2) throws  {
        for (int $i0 = $r1.size() - 1; $i0 >= 0; $i0--) {
            ChangeInfo $r4 = (ChangeInfo) $r1.get($i0);
            if (endChangeAnimationIfNecessary($r4, $r2) && $r4.oldHolder == null && $r4.newHolder == null) {
                $r1.remove($r4);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo $r1) throws  {
        if ($r1.oldHolder != null) {
            endChangeAnimationIfNecessary($r1, $r1.oldHolder);
        }
        if ($r1.newHolder != null) {
            endChangeAnimationIfNecessary($r1, $r1.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo $r1, ViewHolder $r2) throws  {
        boolean $z0 = false;
        if ($r1.newHolder == $r2) {
            $r1.newHolder = null;
        } else if ($r1.oldHolder != $r2) {
            return false;
        } else {
            $r1.oldHolder = null;
            $z0 = true;
        }
        ViewCompat.setAlpha($r2.itemView, 1.0f);
        ViewCompat.setTranslationX($r2.itemView, 0.0f);
        ViewCompat.setTranslationY($r2.itemView, 0.0f);
        dispatchChangeFinished($r2, $z0);
        return true;
    }

    public void endAnimation(ViewHolder $r1) throws  {
        int $i0;
        View $r2 = $r1.itemView;
        ViewCompat.animate($r2).cancel();
        for ($i0 = this.mPendingMoves.size() - 1; $i0 >= 0; $i0--) {
            if (((MoveInfo) this.mPendingMoves.get($i0)).holder == $r1) {
                ViewCompat.setTranslationY($r2, 0.0f);
                ViewCompat.setTranslationX($r2, 0.0f);
                dispatchMoveFinished($r1);
                this.mPendingMoves.remove($i0);
            }
        }
        endChangeAnimation(this.mPendingChanges, $r1);
        if (this.mPendingRemovals.remove($r1)) {
            ViewCompat.setAlpha($r2, 1.0f);
            dispatchRemoveFinished($r1);
        }
        if (this.mPendingAdditions.remove($r1)) {
            ViewCompat.setAlpha($r2, 1.0f);
            dispatchAddFinished($r1);
        }
        for ($i0 = this.mChangesList.size() - 1; $i0 >= 0; $i0--) {
            ArrayList $r4 = (ArrayList) this.mChangesList.get($i0);
            endChangeAnimation($r4, $r1);
            if ($r4.isEmpty()) {
                this.mChangesList.remove($i0);
            }
        }
        for ($i0 = this.mMovesList.size() - 1; $i0 >= 0; $i0--) {
            $r4 = (ArrayList) this.mMovesList.get($i0);
            int $i1 = $r4.size() - 1;
            while ($i1 >= 0) {
                if (((MoveInfo) $r4.get($i1)).holder == $r1) {
                    ViewCompat.setTranslationY($r2, 0.0f);
                    ViewCompat.setTranslationX($r2, 0.0f);
                    dispatchMoveFinished($r1);
                    $r4.remove($i1);
                    if ($r4.isEmpty()) {
                        this.mMovesList.remove($i0);
                    }
                } else {
                    $i1--;
                }
            }
        }
        for ($i0 = this.mAdditionsList.size() - 1; $i0 >= 0; $i0--) {
            $r4 = (ArrayList) this.mAdditionsList.get($i0);
            if ($r4.remove($r1)) {
                ViewCompat.setAlpha($r2, 1.0f);
                dispatchAddFinished($r1);
                if ($r4.isEmpty()) {
                    this.mAdditionsList.remove($i0);
                }
            }
        }
        if (this.mRemoveAnimations.remove($r1)) {
        }
        if (this.mAddAnimations.remove($r1)) {
        }
        if (this.mChangeAnimations.remove($r1)) {
        }
        if (this.mMoveAnimations.remove($r1)) {
            dispatchFinishedWhenDone();
        } else {
            dispatchFinishedWhenDone();
        }
    }

    private void resetAnimation(ViewHolder $r1) throws  {
        AnimatorCompatHelper.clearInterpolator($r1.itemView);
        endAnimation($r1);
    }

    public boolean isRunning() throws  {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    private void dispatchFinishedWhenDone() throws  {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() throws  {
        int $i0;
        DefaultItemAnimator $r1;
        this = this;
        for ($i0 = this.mPendingMoves.size() - 1; $i0 >= 0; $i0--) {
            $r1 = this;
            this = $r1;
            MoveInfo $r3 = (MoveInfo) $r1.mPendingMoves.get($i0);
            View $r5 = $r3.holder.itemView;
            ViewCompat.setTranslationY($r5, 0.0f);
            ViewCompat.setTranslationX($r5, 0.0f);
            dispatchMoveFinished($r3.holder);
            $r1 = this;
            this = $r1;
            $r1.mPendingMoves.remove($i0);
        }
        $r1 = this;
        this = $r1;
        for ($i0 = $r1.mPendingRemovals.size() - 1; $i0 >= 0; $i0--) {
            $r1 = this;
            this = $r1;
            dispatchRemoveFinished((ViewHolder) $r1.mPendingRemovals.get($i0));
            $r1 = this;
            this = $r1;
            $r1.mPendingRemovals.remove($i0);
        }
        $r1 = this;
        this = $r1;
        for ($i0 = $r1.mPendingAdditions.size() - 1; $i0 >= 0; $i0--) {
            $r1 = this;
            this = $r1;
            ViewHolder $r4 = (ViewHolder) $r1.mPendingAdditions.get($i0);
            ViewCompat.setAlpha($r4.itemView, 1.0f);
            dispatchAddFinished($r4);
            $r1 = this;
            this = $r1;
            $r1.mPendingAdditions.remove($i0);
        }
        $r1 = this;
        this = $r1;
        for ($i0 = $r1.mPendingChanges.size() - 1; $i0 >= 0; $i0--) {
            $r1 = this;
            this = $r1;
            endChangeAnimationIfNecessary((ChangeInfo) $r1.mPendingChanges.get($i0));
        }
        $r1 = this;
        this = $r1;
        $r1.mPendingChanges.clear();
        if (isRunning()) {
            ArrayList $r12;
            int $i1;
            ArrayList $r7;
            $r1 = this;
            this = $r1;
            for ($i0 = $r1.mMovesList.size() - 1; $i0 >= 0; $i0--) {
                $r1 = this;
                this = $r1;
                $r12 = (ArrayList) $r1.mMovesList.get($i0);
                for ($i1 = $r12.size() - 1; $i1 >= 0; $i1--) {
                    $r3 = (MoveInfo) $r12.get($i1);
                    $r5 = $r3.holder.itemView;
                    ViewCompat.setTranslationY($r5, 0.0f);
                    ViewCompat.setTranslationX($r5, 0.0f);
                    dispatchMoveFinished($r3.holder);
                    $r12.remove($i1);
                    if ($r12.isEmpty()) {
                        $r7 = this.mMovesList;
                        $r7.remove($r12);
                    }
                }
            }
            $r1 = this;
            this = $r1;
            for ($i0 = $r1.mAdditionsList.size() - 1; $i0 >= 0; $i0--) {
                $r1 = this;
                this = $r1;
                $r12 = (ArrayList) $r1.mAdditionsList.get($i0);
                for ($i1 = $r12.size() - 1; $i1 >= 0; $i1--) {
                    $r4 = (ViewHolder) $r12.get($i1);
                    ViewCompat.setAlpha($r4.itemView, 1.0f);
                    dispatchAddFinished($r4);
                    $r12.remove($i1);
                    if ($r12.isEmpty()) {
                        $r7 = this.mAdditionsList;
                        $r7.remove($r12);
                    }
                }
            }
            $r1 = this;
            this = $r1;
            for ($i0 = $r1.mChangesList.size() - 1; $i0 >= 0; $i0--) {
                $r1 = this;
                this = $r1;
                $r12 = (ArrayList) $r1.mChangesList.get($i0);
                for ($i1 = $r12.size() - 1; $i1 >= 0; $i1--) {
                    endChangeAnimationIfNecessary((ChangeInfo) $r12.get($i1));
                    if ($r12.isEmpty()) {
                        $r7 = this.mChangesList;
                        $r7.remove($r12);
                    }
                }
            }
            $r1 = this;
            this = $r1;
            cancelAll($r1.mRemoveAnimations);
            $r1 = this;
            this = $r1;
            cancelAll($r1.mMoveAnimations);
            $r1 = this;
            this = $r1;
            cancelAll($r1.mAddAnimations);
            $r1 = this;
            this = $r1;
            cancelAll($r1.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    void cancelAll(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", ">;)V"}) List<ViewHolder> $r1) throws  {
        for (int $i0 = $r1.size() - 1; $i0 >= 0; $i0--) {
            ViewCompat.animate(((ViewHolder) $r1.get($i0)).itemView).cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)Z"}) ViewHolder $r1, @NonNull @Signature({"(", "Landroid/support/v7/widget/RecyclerView$ViewHolder;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)Z"}) List<Object> $r2) throws  {
        return !$r2.isEmpty() || super.canReuseUpdatedViewHolder($r1, $r2);
    }
}
