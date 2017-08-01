package com.waze.widget.rt;

import com.waze.widget.WazeAppWidgetLog;

public class AuthenticateSuccessfulResponse {
    private String mCookie;
    private String mSessionId;

    public String getCookie() {
        return this.mCookie;
    }

    public String getSessionId() {
        return this.mSessionId;
    }

    public AuthenticateSuccessfulResponse(String responseStr) {
        String[] lines = responseStr.split("\n");
        String[] params = lines[0].split(",");
        if (params[1].equalsIgnoreCase("200")) {
            WazeAppWidgetLog.m45d("Got AuthenticateSuccessful response [" + lines[1] + "]");
            String[] responseParams = lines[1].split(",");
            if (responseParams[0].equalsIgnoreCase("LoginError")) {
                WazeAppWidgetLog.m46e("Authenticate failed status =" + responseParams[1]);
                return;
            }
            this.mSessionId = responseParams[1];
            this.mCookie = responseParams[2];
            return;
        }
        WazeAppWidgetLog.m46e("Authenticate failed status =" + params[1] + " details=" + params[2]);
    }
}
