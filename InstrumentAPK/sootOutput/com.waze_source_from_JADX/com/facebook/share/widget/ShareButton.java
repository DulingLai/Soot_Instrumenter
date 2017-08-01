package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.C0496R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;

public final class ShareButton extends ShareButtonBase {

    class C06351 implements OnClickListener {
        C06351() throws  {
        }

        public void onClick(View $r1) throws  {
            ShareDialog $r4;
            ShareButton.this.callExternalOnClickListener($r1);
            if (ShareButton.this.getFragment() != null) {
                $r4 = new ShareDialog(ShareButton.this.getFragment(), ShareButton.this.getRequestCode());
            } else {
                $r4 = new ShareDialog(ShareButton.this.getActivity(), ShareButton.this.getRequestCode());
            }
            $r4.show(ShareButton.this.getShareContent());
        }
    }

    public ShareButton(Context $r1) throws  {
        super($r1, null, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    public ShareButton(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    public ShareButton(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    protected int getDefaultStyleResource() throws  {
        return C0496R.style.com_facebook_button_share;
    }

    protected OnClickListener getShareOnClickListener() throws  {
        return new C06351();
    }

    protected int getDefaultRequestCode() throws  {
        return RequestCodeOffset.Share.toRequestCode();
    }
}
