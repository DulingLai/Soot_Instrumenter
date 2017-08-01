package android.support.v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;

abstract class BaseFragmentActivityHoneycomb extends BaseFragmentActivityEclair {
    BaseFragmentActivityHoneycomb() throws  {
    }

    public View onCreateView(View $r1, String $r2, Context $r3, AttributeSet $r4) throws  {
        View $r5 = dispatchFragmentsOnCreateView($r1, $r2, $r3, $r4);
        if ($r5 != null || VERSION.SDK_INT < 11) {
            return $r5;
        }
        return super.onCreateView($r1, $r2, $r3, $r4);
    }
}
