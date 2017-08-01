package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

class ActionBarBackgroundDrawable extends Drawable {
    final ActionBarContainer mContainer;

    public int getOpacity() throws  {
        return 0;
    }

    public ActionBarBackgroundDrawable(ActionBarContainer $r1) throws  {
        this.mContainer = $r1;
    }

    public void draw(Canvas $r1) throws  {
        if (!this.mContainer.mIsSplit) {
            if (this.mContainer.mBackground != null) {
                this.mContainer.mBackground.draw($r1);
            }
            if (this.mContainer.mStackedBackground != null && this.mContainer.mIsStacked) {
                this.mContainer.mStackedBackground.draw($r1);
            }
        } else if (this.mContainer.mSplitBackground != null) {
            this.mContainer.mSplitBackground.draw($r1);
        }
    }

    public void setAlpha(int alpha) throws  {
    }

    public void setColorFilter(ColorFilter cf) throws  {
    }
}
