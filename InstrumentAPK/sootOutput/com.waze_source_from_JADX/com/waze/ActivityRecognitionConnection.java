package com.waze;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.ActivityRecognition;
import com.waze.analytics.AnalyticsEvents;
import com.waze.utils.OfflineStats;

public class ActivityRecognitionConnection implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final String TAG = "ActivityRecognition";
    private static PendingIntent callbackIntent;
    private static GoogleApiClient mActivityRecognitionClient = null;
    private Context context;

    public void startActivityRecognitionConnection(Context $r1) throws  {
        this.context = $r1;
        if (mActivityRecognitionClient == null) {
            mActivityRecognitionClient = new Builder(AppService.getAppContext()).addApi(ActivityRecognition.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            mActivityRecognitionClient.connect();
            Logger.m36d("radius - Activity recognition start");
        }
    }

    public void stopActivityRecognitionConnection() throws  {
        try {
            Logger.m36d("radius - Stop updates activity recognition");
            OfflineStats.SendStats(this.context, AnalyticsEvents.ANALYTICS_EVENT_ACTIVITY_RECOGNITION_OFF, null);
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mActivityRecognitionClient, callbackIntent);
        } catch (IllegalStateException e) {
            Logger.m38e("radius - Exception Stop updates activity recognition");
        }
    }

    public void onConnectionFailed(ConnectionResult result) throws  {
        Logger.m43w("radius - Activity recognition request failed");
    }

    public void onConnected(Bundle connectionHint) throws  {
        callbackIntent = PendingIntent.getService(this.context, 0, new Intent(this.context, ActivityRecognitionService.class), 134217728);
        Logger.m36d("radius - Activity recognition request updates");
        OfflineStats.SendStats(this.context, AnalyticsEvents.ANALYTICS_EVENT_ACTIVITY_RECOGNITION_ON, null);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mActivityRecognitionClient, 0, callbackIntent);
    }

    public void onConnectionSuspended(int arg0) throws  {
    }
}
