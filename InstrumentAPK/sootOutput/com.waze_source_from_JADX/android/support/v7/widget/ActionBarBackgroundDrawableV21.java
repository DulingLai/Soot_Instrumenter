package android.support.v7.widget;

import android.graphics.Outline;
import android.support.annotation.NonNull;

class ActionBarBackgroundDrawableV21 extends ActionBarBackgroundDrawable {
    public ActionBarBackgroundDrawableV21(ActionBarContainer $r1) throws  {
        super($r1);
    }

    public void getOutline(@NonNull Outline $r1) throws  {
        if (this.mContainer.mIsSplit) {
            if (this.mContainer.mSplitBackground != null) {
                this.mContainer.mSplitBackground.getOutline($r1);
            }
        } else if (this.mContainer.mBackground != null) {
            this.mContainer.mBackground.getOutline($r1);
        }
    }
}
