package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLoggerUtility {
    private static final Map<GraphAPIActivityType, String> API_ACTIVITY_TYPE_TO_STRING = new C05101();

    static class C05101 extends HashMap<GraphAPIActivityType, String> {
        C05101() throws  {
            put(GraphAPIActivityType.MOBILE_INSTALL_EVENT, "MOBILE_APP_INSTALL");
            put(GraphAPIActivityType.CUSTOM_APP_EVENTS, "CUSTOM_APP_EVENTS");
        }
    }

    public enum GraphAPIActivityType {
        MOBILE_INSTALL_EVENT,
        CUSTOM_APP_EVENTS
    }

    public static JSONObject getJSONObjectForGraphAPICall(GraphAPIActivityType $r0, AttributionIdentifiers $r1, String $r2, boolean $z0, Context $r3) throws JSONException {
        JSONObject $r5 = new JSONObject();
        $r5.put("event", API_ACTIVITY_TYPE_TO_STRING.get($r0));
        Utility.setAppEventAttributionParameters($r5, $r1, $r2, $z0);
        try {
            Utility.setAppEventExtendedDeviceInfoParameters($r5, $r3);
        } catch (Exception $r4) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Fetching extended device info parameters failed: '%s'", $r4.toString());
        }
        $r5.put("application_package_name", $r3.getPackageName());
        return $r5;
    }
}
