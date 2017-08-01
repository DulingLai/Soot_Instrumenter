package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class ActivityCompatJB {
    ActivityCompatJB() throws  {
    }

    public static void startActivity(Context $r0, Intent $r1, Bundle $r2) throws  {
        $r0.startActivity($r1, $r2);
    }

    public static void startActivityForResult(Activity $r0, Intent $r1, int $i0, Bundle $r2) throws  {
        $r0.startActivityForResult($r1, $i0, $r2);
    }

    public static void finishAffinity(Activity $r0) throws  {
        $r0.finishAffinity();
    }
}
