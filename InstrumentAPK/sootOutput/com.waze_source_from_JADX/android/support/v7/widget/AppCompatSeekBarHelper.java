package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
    private static final int[] TINT_ATTRS = new int[]{16843074};
    private final SeekBar mView;

    AppCompatSeekBarHelper(SeekBar $r1, AppCompatDrawableManager $r2) throws  {
        super($r1, $r2);
        this.mView = $r1;
    }

    void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        super.loadFromAttributes($r1, $i0);
        TintTypedArray $r5 = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), $r1, TINT_ATTRS, $i0, 0);
        Drawable $r6 = $r5.getDrawableIfKnown(0);
        if ($r6 != null) {
            this.mView.setThumb($r6);
        }
        $r5.recycle();
    }
}
