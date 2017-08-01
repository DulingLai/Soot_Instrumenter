package com.waze.widget.routing;

import android.support.v7.widget.ActivityChooserView.ActivityChooserViewAdapter;
import com.spotify.sdk.android.authentication.LoginActivity;
import com.waze.widget.WazeAppWidgetLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoutingResponse {
    private int mAverageTime;
    private JSONObject mJson;
    private int mMax;
    private int mMin;
    private JSONArray mValuesArray;

    public RoutingResponse(String fromString) throws JSONException {
        int total = 0;
        try {
            this.mMin = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.mMax = 0;
            this.mAverageTime = 0;
            this.mJson = new JSONObject(fromString);
            this.mValuesArray = this.mJson.getJSONArray(LoginActivity.RESPONSE_KEY);
            for (int i = 0; i < this.mValuesArray.length(); i++) {
                String val = this.mValuesArray.getString(i);
                total += Integer.parseInt(val);
                if (Integer.parseInt(val) < this.mMin) {
                    this.mMin = Integer.parseInt(val);
                }
                if (Integer.parseInt(val) > this.mMax) {
                    this.mMax = Integer.parseInt(val);
                }
            }
            this.mAverageTime = total / this.mValuesArray.length();
        } catch (JSONException e) {
            WazeAppWidgetLog.m46e("JSONException parsing routing result [" + fromString + "]" + e.getMessage());
            throw e;
        }
    }

    public double[] getList() {
        if (this.mValuesArray == null) {
            return null;
        }
        double[] dArr = new double[this.mValuesArray.length()];
        for (int i = 0; i < this.mValuesArray.length(); i++) {
            try {
                dArr[i] = Double.parseDouble(this.mValuesArray.getString(i)) / 60.0d;
            } catch (JSONException e) {
            }
        }
        return dArr;
    }

    public int getNumResults() {
        return this.mValuesArray.length();
    }

    public int getAveragetTime() {
        return this.mAverageTime;
    }

    public int getMinValue() {
        return this.mMin;
    }

    public int getMaxValue() {
        return this.mMax;
    }

    public String toString() {
        if (this.mJson != null) {
            return this.mJson.toString();
        }
        return null;
    }

    public int getTime() {
        int i = 0;
        try {
            if (this.mValuesArray != null) {
                i = Integer.parseInt(this.mValuesArray.getString(RoutingRequest.getNowLocation()));
            }
        } catch (JSONException e) {
        }
        return i;
    }
}
