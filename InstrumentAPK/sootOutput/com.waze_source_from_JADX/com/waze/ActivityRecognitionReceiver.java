package com.waze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ActivityRecognitionReceiver extends BroadcastReceiver {
    public void onReceive(Context $r1, Intent $r2) throws  {
        Bundle $r3 = $r2.getExtras();
        int $i0 = $r3.getInt("Confidence");
        int $i1 = $r3.getInt("Activity");
        Log.d("PARKING", String.format("got activity recognition %d (%s) with confidence %d", new Object[]{Integer.valueOf($i1), ActivityRecognitionService.getNameFromType($i1), Integer.valueOf($i0)}));
        if ($i1 == 2 || $i1 == 7 || $i1 == 8 || $i1 == 6) {
            Log.d("PARKING", "Activity experiment - detected activity as " + $i1 + ", putting a parking pin");
            if (!GeoFencingService.IsRunning()) {
                GeoFencingService.start($r1);
            } else if (!GeoFencingService.CreatePushMessage($i1, $i0)) {
                GeoFencingService.stop(false);
            }
        }
    }
}
