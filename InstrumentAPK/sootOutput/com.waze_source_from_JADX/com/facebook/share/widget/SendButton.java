package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.C0496R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;

public final class SendButton extends ShareButtonBase {

    class C06341 implements OnClickListener {
        C06341() throws  {
        }

        public void onClick(View $r1) throws  {
            MessageDialog $r4;
            SendButton.this.callExternalOnClickListener($r1);
            if (SendButton.this.getFragment() != null) {
                $r4 = new MessageDialog(SendButton.this.getFragment(), SendButton.this.getRequestCode());
            } else {
                $r4 = new MessageDialog(SendButton.this.getActivity(), SendButton.this.getRequestCode());
            }
            $r4.show(SendButton.this.getShareContent());
        }
    }

    public SendButton(Context $r1) throws  {
        super($r1, null, 0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, AnalyticsEvents.EVENT_SEND_BUTTON_DID_TAP);
    }

    public SendButton(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2, 0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, AnalyticsEvents.EVENT_SEND_BUTTON_DID_TAP);
    }

    public SendButton(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, AnalyticsEvents.EVENT_SEND_BUTTON_DID_TAP);
    }

    protected int getDefaultStyleResource() throws  {
        return C0496R.style.com_facebook_button_send;
    }

    protected OnClickListener getShareOnClickListener() throws  {
        return new C06341();
    }

    protected int getDefaultRequestCode() throws  {
        return RequestCodeOffset.Message.toRequestCode();
    }
}
