package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import java.io.File;

class ContextCompatHoneycomb {
    ContextCompatHoneycomb() throws  {
    }

    static void startActivities(Context $r0, Intent[] $r1) throws  {
        $r0.startActivities($r1);
    }

    public static File getObbDir(Context $r0) throws  {
        return $r0.getObbDir();
    }
}
