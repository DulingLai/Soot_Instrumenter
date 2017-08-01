package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;

class IntentCompatHoneycomb {
    IntentCompatHoneycomb() throws  {
    }

    public static Intent makeMainActivity(ComponentName $r0) throws  {
        return Intent.makeMainActivity($r0);
    }

    public static Intent makeRestartActivityTask(ComponentName $r0) throws  {
        return Intent.makeRestartActivityTask($r0);
    }
}
