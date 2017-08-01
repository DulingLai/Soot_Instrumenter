package com.waze.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.OfflineNativeManager;
import com.waze.PushCommands;

public class WazeGcmListenerService extends GcmListenerService {
    public void onMessageReceived(String from, Bundle data) {
        Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:bundle: " + data);
        Context context = getApplicationContext();
        if (!handleOfflineAtRequest(data)) {
            String alert = data.getString("alert");
            String badgeStr = data.getString("badge");
            String image = data.getString("icon");
            String alertType = data.getString("type");
            if (alertType != null) {
                alertType = alertType.replaceAll("\\|", "\\^");
            }
            String payload = data.getString("gcmPayload");
            String title = data.getString("title");
            String messageType = data.getString("messageType");
            String actionsJson = data.getString("actions");
            boolean showNotification = data.getBoolean("show", true);
            if (payload == null || payload.isEmpty()) {
                if (messageType != null) {
                    if (messageType.equalsIgnoreCase("RIDEWITH_IN_APP_MESSAGE")) {
                        Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:in app messaging");
                        PushCommands.ParseInAppMsg(context, data.getString("messageParams"), alert, title, image, actionsJson, showNotification);
                        return;
                    }
                }
                if (messageType != null) {
                    if (messageType.equalsIgnoreCase("MESSAGE_READ")) {
                        Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:message read");
                        PushCommands.ParseMsgRead(context, data.getString("messageParams"), alert, title, actionsJson, false);
                        return;
                    }
                }
                int badge = badgeStr == null ? -1 : Integer.valueOf(badgeStr).intValue();
                String alertConstruction = data.getString("constructionInstructions");
                if (!(alertConstruction == null || alertConstruction.isEmpty())) {
                    Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:alertConstruct: " + alertConstruction);
                    PushCommands.ParseConstructionInstructionCommand(context, alertConstruction, alertType, title, actionsJson, showNotification);
                }
                Logger.d_("Push Service", "Badge: " + badge);
                if (alert != null) {
                    String alertActionUrl = data.getString("WazeUrl");
                    Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:alert action url: " + alertActionUrl);
                    Alerter.onAlert(context, alert, alertActionUrl, alertType, title, image, actionsJson, showNotification);
                }
                if (badge != -1) {
                    Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:Alerter.onBadge: " + badge);
                    Alerter.onBadge(context, badge);
                    return;
                }
                return;
            }
            Log.d(Logger.TAG, "WazeGcmListenerService:onMessageReceived:carpool: " + payload);
            PushCommands.ParseCarpoolCommand(context, payload, alert, title, image, alertType, actionsJson, showNotification);
        }
    }

    boolean handleOfflineAtRequest(Bundle data) {
        String url = data.getString("WazeUrl");
        if (url == null || !url.equals("?a=send_location")) {
            return false;
        }
        if (NativeManager.IsAppStarted()) {
            Log.d(Logger.TAG, "WazeGcmListenerService.handleOfflineAtRequest - ignoring a request while the app is running");
            return false;
        }
        Log.d(Logger.TAG, "WazeGcmListenerService.handleOfflineAtRequest - about to sendOfflineLocation");
        Context context = getApplicationContext();
        OfflineNativeManager.getInstance(context).sendOfflineLocation();
        context.stopService(new Intent(context, WazeGcmListenerService.class));
        System.exit(0);
        return true;
    }
}
