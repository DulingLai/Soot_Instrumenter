package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class Space extends View {
    public Space(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        if (getVisibility() == 0) {
            setVisibility(4);
        }
    }

    public Space(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public Space(Context $r1) throws  {
        this($r1, null);
    }

    public void draw(Canvas canvas) throws  {
    }

    private static int getDefaultSize2(int $i0, int $i1) throws  {
        int $i2 = MeasureSpec.getMode($i1);
        $i1 = MeasureSpec.getSize($i1);
        switch ($i2) {
            case Integer.MIN_VALUE:
                return Math.min($i0, $i1);
            case 0:
                return $i0;
            case 1073741824:
                return $i1;
            default:
                return $i0;
        }
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        setMeasuredDimension(getDefaultSize2(getSuggestedMinimumWidth(), $i0), getDefaultSize2(getSuggestedMinimumHeight(), $i1));
    }
}
