package com.waze.push;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.waze.Logger;

public class WazeInstanceIDListenerService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        Logger.i("WazeInstanceIDListenerService: received GCM token refresh");
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
