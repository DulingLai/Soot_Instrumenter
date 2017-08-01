package com.waze.audioextension.spotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SpotifyRunningBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) throws  {
        Log.d("SpotifyManager", "SpotifyRunningBroadcastReceiver - Spotify runnig");
        SpotifyManager.getInstance().onAppBecameActive();
    }
}
