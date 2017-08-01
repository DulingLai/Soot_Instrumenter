package com.abaltatech.mcp.weblink.sdk.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.abaltatech.mcp.weblink.sdk.WEBLINK;

public class WLEditText extends EditText {
    public WLEditText(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
    }

    public boolean onCheckIsTextEditor() throws  {
        if (WEBLINK.instance.isServerSideKeyboard()) {
            return super.onCheckIsTextEditor();
        }
        return !WEBLINK.instance.isConnectedToClient();
    }
}
