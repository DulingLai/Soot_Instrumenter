package com.waze.share;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.Logger;
import com.waze.ifs.ui.ActivityBase;
import com.waze.location.Position;
import com.waze.social.facebook.FacebookManager;
import com.waze.view.dialogs.ParkingPinsFeedbackDialog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareFbQueries {
    public static final int FB_FRIENDS_COMPARE_NAME = 0;
    public static final String FB_POST_DLG_URL = "http://www.waze.com";
    public static final int FB_QUERY_RESULT_ERROR_GENERAL = 268435457;
    public static final int FB_QUERY_RESULT_ERROR_INVALID_RESPONSE = 268435460;
    public static final int FB_QUERY_RESULT_ERROR_NO_RESULT = 268435458;
    public static final int FB_QUERY_RESULT_OK = 0;
    public static final int LOCATION_QUERY_RADIUS_METERS = 5000;
    public static final String LOG_TAG = "Facebook queries";
    private static ShareFbQueries mInstance = null;

    public interface IPostCallback {
        void onPostResult(int i, String str);
    }

    public interface IFriendsListCallback {
        void onFriendsListResult(ArrayList<ShareFbFriend> arrayList, int i, String str);
    }

    public interface ILocationsListCallback {
        void onLocationsListResult(ArrayList<ShareFbLocation> arrayList, int i, String str);
    }

    public static ShareFbQueries getInstance() {
        if (mInstance == null) {
            mInstance = new ShareFbQueries();
        }
        return mInstance;
    }

    public static void postEventFeed(ActivityBase context, ShareFbLocation destination, String link, String[] tags, String message, IPostCallback cb, boolean showProgress, String eventId, String desc, String picture) {
        FacebookManager.getInstance().postFeed(destination, link, tags, message, cb, eventId, desc, picture);
    }

    public static void postAction(ActivityBase context, ShareFbLocation destination, String end_time, String[] tags, String message, IPostCallback cb, boolean showProgress) {
        FacebookManager.getInstance().postAction(destination, end_time, tags, message, cb);
    }

    private static String getFriendsQuery(String pattern, int limit) {
        String query = "select name, current_location, uid, pic from user where uid in (select uid2 from friend where uid1=me())";
        if (pattern != null && pattern.length() > 0) {
            query = query + " and strpos( lower(name),'" + pattern.toLowerCase() + "') >= 0";
        }
        query = query + " order by name limit " + limit;
        Logger.d_(LOG_TAG, "Friends query: " + query);
        return query;
    }

    private static ArrayList<ShareFbFriend> parseFriendsListData(JSONObject apiResponse, boolean isFql) {
        try {
            ArrayList<ShareFbFriend> arrayList = new ArrayList();
            JSONArray jsonArray = apiResponse.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                ShareFbFriend friend = new ShareFbFriend();
                friend.id = jsonArray.getJSONObject(i).getLong("uid");
                friend.name = jsonArray.getJSONObject(i).getString("name");
                friend.imageUrl = jsonArray.getJSONObject(i).getString("pic");
                arrayList.add(friend);
                Logger.i_(LOG_TAG, "Friend # " + i + " : " + friend.name);
            }
            return arrayList;
        } catch (JSONException ex) {
            Logger.e_(LOG_TAG, "Got JSON exception: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    private static ArrayList<ShareFbLocation> parseLocationsListData(JSONObject apiResponse, Position center, boolean isFql) {
        try {
            ArrayList<ShareFbLocation> arrayList = new ArrayList();
            JSONArray jsonArray = apiResponse.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                ShareFbLocation entry = new ShareFbLocation();
                entry.id = jsonArray.getJSONObject(i).getString("id");
                entry.name = jsonArray.getJSONObject(i).getString("name");
                JSONObject jsonObjLocation = jsonArray.getJSONObject(i).getJSONObject(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
                entry.address = "";
                if (jsonObjLocation.has("street")) {
                    entry.address += jsonObjLocation.getString("street");
                }
                if (jsonObjLocation.has(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION)) {
                    if (entry.address.length() > 0) {
                        entry.address += ", ";
                    }
                    entry.address += jsonObjLocation.getString(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
                }
                if (jsonObjLocation.has("state")) {
                    if (entry.address.length() > 0) {
                        entry.address += ", ";
                    }
                    entry.address += jsonObjLocation.getString("state");
                }
                entry.latitude = jsonObjLocation.getDouble(WLTypes.VEHICLEDATA_ATTRIBUTE_LATITUDE);
                entry.longitude = jsonObjLocation.getDouble(WLTypes.VEHICLEDATA_ATTRIBUTE_LONGITUDE);
                float[] res = new float[1];
                Location.distanceBetween(entry.latitude, entry.longitude, center.latitude, center.longitude, res);
                if (ShareNativeManager.getInstance().isMetricUnits()) {
                    entry.distance = res[0] * ParkingPinsFeedbackDialog.LAMBDA;
                } else {
                    entry.distance = res[0] * ShareNativeManager.METER_TO_MILES_FACTOR;
                }
                entry.distance = ((float) Math.round(entry.distance * 100.0f)) / 100.0f;
                arrayList.add(entry);
                Logger.i_(LOG_TAG, "Place # " + i + " : " + entry.name);
            }
            return arrayList;
        } catch (JSONException ex) {
            Logger.e_(LOG_TAG, "Got JSON exception: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public static String tagsList(String[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(tags[i]);
        }
        return sb.toString();
    }

    public static void postDialogShow(Activity activity) {
        new Bundle().putString("link", FB_POST_DLG_URL);
    }
}
