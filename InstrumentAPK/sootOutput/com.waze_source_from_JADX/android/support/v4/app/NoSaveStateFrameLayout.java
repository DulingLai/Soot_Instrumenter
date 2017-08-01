package android.support.v4.app;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import dalvik.annotation.Signature;

class NoSaveStateFrameLayout extends FrameLayout {
    static ViewGroup wrap(View $r0) throws  {
        NoSaveStateFrameLayout $r2 = new NoSaveStateFrameLayout($r0.getContext());
        LayoutParams $r4 = $r0.getLayoutParams();
        if ($r4 != null) {
            $r2.setLayoutParams($r4);
        }
        $r0.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        $r2.addView($r0);
        return $r2;
    }

    public NoSaveStateFrameLayout(Context $r1) throws  {
        super($r1);
    }

    protected void dispatchSaveInstanceState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        dispatchFreezeSelfOnly($r1);
    }

    protected void dispatchRestoreInstanceState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        dispatchThawSelfOnly($r1);
    }
}
