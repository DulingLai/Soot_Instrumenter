package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.Validate;

public abstract class AccessTokenTracker {
    private final LocalBroadcastManager broadcastManager;
    private boolean isTracking = false;
    private final BroadcastReceiver receiver;

    private class CurrentAccessTokenBroadcastReceiver extends BroadcastReceiver {
        private CurrentAccessTokenBroadcastReceiver() throws  {
        }

        public void onReceive(Context context, Intent $r2) throws  {
            if ("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED".equals($r2.getAction())) {
                AccessTokenTracker.this.onCurrentAccessTokenChanged((AccessToken) $r2.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN"), (AccessToken) $r2.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN"));
            }
        }
    }

    protected abstract void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) throws ;

    public AccessTokenTracker() throws  {
        Validate.sdkInitialized();
        this.receiver = new CurrentAccessTokenBroadcastReceiver();
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
        $r1.addAction("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED");
        this.broadcastManager.registerReceiver(this.receiver, $r1);
    }
}
