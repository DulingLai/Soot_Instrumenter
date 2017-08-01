package com.waze.widget.rt;

public class AuthenticateRequest {
    private String mPassword;
    private String mUserName;

    AuthenticateRequest(String userName, String passWord) {
        this.mUserName = userName;
        this.mPassword = passWord;
    }

    String buildCmd() {
        return "Authenticate," + RealTimeManager.getProtocol() + "," + this.mUserName + "," + this.mPassword;
    }
}
