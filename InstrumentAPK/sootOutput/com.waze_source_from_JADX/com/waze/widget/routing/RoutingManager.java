package com.waze.widget.routing;

import android.location.Location;
import com.waze.widget.WazeAppWidgetLog;
import com.waze.widget.WazeAppWidgetPreferences;
import com.waze.widget.WidgetManager;
import com.waze.widget.rt.RealTimeManager;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

public class RoutingManager {
    private String mServerUrl = WazeAppWidgetPreferences.getRoutingServerUrl();

    public void RoutingRequest(Location from, Location to) {
        RealTimeManager rt = RealTimeManager.getInstance();
        RoutingRequest rr = new RoutingRequest(from, to, RoutingUserOptions.routeType(), rt.getSessionId(), rt.getCookie());
        rr.addOption(RoutingOption.AVOID_TOLL_ROADS, RoutingUserOptions.avoidTolls());
        rr.addOption(RoutingOption.AVOID_DANGER_ZONES, RoutingUserOptions.avoidPalestinianRoads());
        rr.addOption(RoutingOption.AVOID_PRIMARIES, RoutingUserOptions.avoidPrimaries());
        rr.addOption(RoutingOption.PREFER_SAME_STREET, RoutingUserOptions.preferSameStreet());
        execute(rr);
    }

    private void execute(RoutingRequest rr) {
        if (this.mServerUrl == null || this.mServerUrl.length() <= 0) {
            WazeAppWidgetLog.m46e("Sending routing request [mServerUrl is null]");
            WidgetManager.executeResponse(null);
        } else if (rr == null || rr.buildCmd() == null) {
            WazeAppWidgetLog.m46e("Sending routing request [RoutingRequest is null]");
            WidgetManager.executeResponse(null);
        } else {
            WazeAppWidgetLog.m46e("Sending routing request [ " + rr.getOriginAndDest() + "]");
            final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost(this.mServerUrl + "/routingRequest" + rr.buildCmd());
            new Thread() {
                public void run() {
                    try {
                        HttpResponse rp = httpclient.execute(httppost);
                        try {
                            if (rp.getStatusLine().getStatusCode() == 200) {
                                String str = EntityUtils.toString(rp.getEntity());
                                WazeAppWidgetLog.m45d("Got routing response [" + str + "]");
                                try {
                                    WidgetManager.executeResponse(new RoutingResponse(str));
                                    return;
                                } catch (JSONException e) {
                                    WidgetManager.executeResponse(null);
                                    return;
                                }
                            }
                            WazeAppWidgetLog.m46e("routing request failed code=" + rp.getStatusLine().getStatusCode());
                            WidgetManager.executeResponse(null);
                        } catch (IOException e2) {
                        }
                    } catch (IOException e3) {
                    }
                }
            }.start();
        }
    }
}
