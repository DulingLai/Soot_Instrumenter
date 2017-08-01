package com.facebook.share.internal;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.C0496R;
import com.facebook.FacebookButtonBase;
import com.facebook.internal.AnalyticsEvents;

public class LikeButton extends FacebookButtonBase {
    protected int getDefaultRequestCode() throws  {
        return 0;
    }

    public LikeButton(Context $r1, boolean $z0) throws  {
        super($r1, null, 0, 0, AnalyticsEvents.EVENT_LIKE_BUTTON_CREATE, AnalyticsEvents.EVENT_LIKE_BUTTON_DID_TAP);
        setSelected($z0);
    }

    public void setSelected(boolean $z0) throws  {
        super.setSelected($z0);
        updateForLikeStatus();
    }

    protected void configureButton(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super.configureButton($r1, $r2, $i0, $i1);
        updateForLikeStatus();
    }

    protected int getDefaultStyleResource() throws  {
        return C0496R.style.com_facebook_button_like;
    }

    private void updateForLikeStatus() throws  {
        if (isSelected()) {
            setCompoundDrawablesWithIntrinsicBounds(C0496R.drawable.com_facebook_button_like_icon_selected, 0, 0, 0);
            setText(getResources().getString(C0496R.string.com_facebook_like_button_liked));
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(C0496R.drawable.com_facebook_button_icon, 0, 0, 0);
        setText(getResources().getString(C0496R.string.com_facebook_like_button_not_liked));
    }
}
