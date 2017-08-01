package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.C0192R;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class DialogTitle extends TextView {
    public DialogTitle(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public DialogTitle(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public DialogTitle(Context $r1) throws  {
        super($r1);
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        Layout $r2 = getLayout();
        if ($r2 != null) {
            int $i2 = $r2.getLineCount();
            if ($i2 > 0 && $r2.getEllipsisCount($i2 - 1) > 0) {
                setSingleLine(false);
                setMaxLines(2);
                TypedArray $r4 = getContext().obtainStyledAttributes(null, C0192R.styleable.TextAppearance, 16842817, 16973892);
                $i2 = $r4.getDimensionPixelSize(C0192R.styleable.TextAppearance_android_textSize, 0);
                if ($i2 != 0) {
                    setTextSize(0, (float) $i2);
                }
                $r4.recycle();
                super.onMeasure($i0, $i1);
            }
        }
    }
}
