package com.waze.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.waze.C1283R;
import com.waze.FreeMapAppActivity;
import com.waze.messages.QuestionData;
import com.waze.push.DeleteNotificationReceiver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationsActionsBuilder {
    private static final String ACTION_FIELD_IMAGE = "image_url";
    private static final String ACTION_FIELD_NAME = "id";
    private static final String ACTION_FIELD_TEXT = "text";
    private static final String ACTION_FIELD_URL = "waze_url";
    public static final String EXTRA_BUTTON_NAME = "ButtonName";
    public static final String EXTRA_NOTIFICATION_TYPE = "NotificationType";
    private static final String FIRST_ACTION_NAME = "option0";
    private static final Map<String, Integer> RESOURCE_MAP = new HashMap();
    private static final String SECOND_ACTION_NAME = "option1";

    public static void buildNotificationActions(Context context, QuestionData question, Builder builder, String actionsString, String type) {
        if (!TextUtils.isEmpty(actionsString)) {
            buildDismissHandler(context, builder, type);
            try {
                JSONObject actionsJson = new JSONObject(actionsString);
                List<JSONObject> actionList = new ArrayList();
                if (actionsJson.has(FIRST_ACTION_NAME)) {
                    actionList.add(actionsJson.getJSONObject(FIRST_ACTION_NAME));
                }
                if (actionsJson.has(SECOND_ACTION_NAME)) {
                    actionList.add(actionsJson.getJSONObject(SECOND_ACTION_NAME));
                }
                if (actionList.size() > 0) {
                    builder.mActions.clear();
                    for (JSONObject action : actionList) {
                        buildAction(context, question, builder, action, type);
                    }
                }
            } catch (JSONException ex) {
                Log.e("ActionsBuilder", "Unable to parse push action json: " + ex.getMessage());
            }
        }
    }

    private static void buildDismissHandler(Context context, Builder builder, String type) {
        Intent intent = new Intent(context, DeleteNotificationReceiver.class);
        intent.putExtra(EXTRA_NOTIFICATION_TYPE, type);
        builder.setDeleteIntent(PendingIntent.getBroadcast(context, 0, intent, 0));
    }

    private static void buildAction(Context context, QuestionData question, Builder builder, JSONObject action, String notificationType) throws JSONException {
        String deepLink = getFieldSafe(action, ACTION_FIELD_URL);
        builder.addAction(getIconResId(getFieldSafe(action, ACTION_FIELD_IMAGE)), getFieldSafe(action, ACTION_FIELD_TEXT), getPendingIntentFromDeepLink(context, question, deepLink, getFieldSafe(action, "id"), notificationType));
    }

    private static PendingIntent getPendingIntentFromDeepLink(Context context, QuestionData question, String url, String buttonName, String notificationType) {
        Intent notificationIntent;
        if ("NONE".contentEquals(url)) {
            notificationIntent = DismissNotificationActivity.getDismissIntent(context, 2);
        } else {
            notificationIntent = new Intent(context, FreeMapAppActivity.class);
        }
        notificationIntent.setAction(buttonName);
        if (buttonName != null) {
            notificationIntent.putExtra(EXTRA_BUTTON_NAME, buttonName);
        }
        if (notificationType != null) {
            notificationIntent.putExtra(EXTRA_NOTIFICATION_TYPE, notificationType);
        }
        notificationIntent.setData(Uri.parse(url));
        return PendingIntent.getActivity(context, 0, notificationIntent, 268435456);
    }

    private static int getIconResId(String iconName) {
        if (RESOURCE_MAP.isEmpty()) {
            initializeResourceMap();
        }
        if (RESOURCE_MAP.containsKey(iconName)) {
            return ((Integer) RESOURCE_MAP.get(iconName)).intValue();
        }
        return 0;
    }

    private static void initializeResourceMap() {
        RESOURCE_MAP.put("image1", Integer.valueOf(C1283R.drawable.push_icon_dismiss));
        RESOURCE_MAP.put("map", Integer.valueOf(C1283R.drawable.push_icon_navigate));
        RESOURCE_MAP.put("alarm", Integer.valueOf(C1283R.drawable.push_icon_later));
        RESOURCE_MAP.put("Bell", Integer.valueOf(C1283R.drawable.push_icon_bell));
        RESOURCE_MAP.put("Wheel", Integer.valueOf(C1283R.drawable.push_icon_settings));
        RESOURCE_MAP.put("Block", Integer.valueOf(C1283R.drawable.push_icon_dismiss));
        RESOURCE_MAP.put("Info", Integer.valueOf(C1283R.drawable.push_icon_info));
    }

    private static String getFieldSafe(JSONObject jsonObject, String fieldName) throws JSONException {
        if (jsonObject.has(fieldName)) {
            return jsonObject.getString(fieldName);
        }
        return null;
    }
}
