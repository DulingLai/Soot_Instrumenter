package com.waze;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.location.ActivityRecognitionResult;

public class ActivityRecognitionService extends IntentService {
    private static final String TAG = "ActivityRecognition";

    public static String getNameFromType(int $i0) throws  {
        switch ($i0) {
            case 0:
                return "in_vehicle";
            case 1:
                return "on_bicycle";
            case 2:
                return "on_foot";
            case 3:
                return "still";
            case 4:
            case 6:
                break;
            case 5:
                return "tilting";
            case 7:
                return "walking";
            case 8:
                return "running";
            default:
                break;
        }
        return "unknown";
    }

    public ActivityRecognitionService() throws  {
        super("ActivityRecognitionService");
    }

    protected void onHandleIntent(Intent $r1) throws  {
        Log.d("radius", "Activity recognition Service Get data");
        if (ActivityRecognitionResult.hasResult($r1)) {
            ActivityRecognitionResult $r2 = ActivityRecognitionResult.extractResult($r1);
            if ($r2 == null) {
                Log.w("radius", "Could not get ActivityRecognitionResult");
                return;
            }
            $r1 = new Intent("com.android.ACTIVITY_RECOGNITION");
            $r1.putExtra("Activity", $r2.getMostProbableActivity().getType());
            $r1.putExtra("Confidence", $r2.getMostProbableActivity().getConfidence());
            sendBroadcast($r1);
        }
    }
}
