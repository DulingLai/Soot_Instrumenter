package android.support.v4.app;

import android.app.RemoteInput.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput.Factory;

class RemoteInputCompatApi20 {
    RemoteInputCompatApi20() throws  {
    }

    static RemoteInput[] toCompat(android.app.RemoteInput[] $r0, Factory $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        RemoteInput[] $r3 = $r1.newArray($r0.length);
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            android.app.RemoteInput $r2 = $r0[$i0];
            $r3[$i0] = $r1.build($r2.getResultKey(), $r2.getLabel(), $r2.getChoices(), $r2.getAllowFreeFormInput(), $r2.getExtras());
        }
        return $r3;
    }

    static android.app.RemoteInput[] fromCompat(RemoteInput[] $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        android.app.RemoteInput[] $r2 = new android.app.RemoteInput[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            RemoteInput $r1 = $r0[$i0];
            $r2[$i0] = new Builder($r1.getResultKey()).setLabel($r1.getLabel()).setChoices($r1.getChoices()).setAllowFreeFormInput($r1.getAllowFreeFormInput()).addExtras($r1.getExtras()).build();
        }
        return $r2;
    }

    static Bundle getResultsFromIntent(Intent $r0) throws  {
        return android.app.RemoteInput.getResultsFromIntent($r0);
    }

    static void addResultsToIntent(RemoteInput[] $r0, Intent $r1, Bundle $r2) throws  {
        android.app.RemoteInput.addResultsToIntent(fromCompat($r0), $r1, $r2);
    }
}
