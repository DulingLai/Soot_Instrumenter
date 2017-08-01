package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.Validate;

public abstract class ProfileTracker {
    private final LocalBroadcastManager broadcastManager;
    private boolean isTracking = false;
    private final BroadcastReceiver receiver;

    private class ProfileBroadcastReceiver extends BroadcastReceiver {
        private ProfileBroadcastReceiver() throws  {
        }

        public void onReceive(Context context, Intent $r2) throws  {
            if ("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED".equals($r2.getAction())) {
                ProfileTracker.this.onCurrentProfileChanged((Profile) $r2.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_PROFILE"), (Profile) $r2.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_PROFILE"));
            }
        }
    }

    protected abstract void onCurrentProfileChanged(Profile profile, Profile profile2) throws ;

    public ProfileTracker() throws  {
        Validate.sdkInitialized();
        this.receiver = new ProfileBroadcastReceiver();
        this.broadcastManager = LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext());
        startTracking();
    }

    public void startTracking() throws  {
        if (!this.isTracking) {
            addBroadcastReceiver();
            this.isTracking = true;
        }
    }

    public void stopTracking() throws  {
        if (this.isTracking) {
            this.broadcastManager.unregisterReceiver(this.receiver);
            this.isTracking = false;
        }
    }

    public boolean isTracking() throws  {
        return this.isTracking;
    }

    private void addBroadcastReceiver() throws  {
        IntentFilter $r1 = new IntentFilter();
        $r1.addAction("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED");
        this.broadcastManager.registerReceiver(this.receiver, $r1);
    }
}
