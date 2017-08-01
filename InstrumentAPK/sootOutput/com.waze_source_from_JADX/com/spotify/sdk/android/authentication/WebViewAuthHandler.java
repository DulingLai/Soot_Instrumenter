package com.spotify.sdk.android.authentication;

import android.app.Activity;
import android.util.Log;
import com.spotify.sdk.android.authentication.AuthenticationHandler.OnCompleteListener;

class WebViewAuthHandler implements AuthenticationHandler {
    private static String TAG = WebViewAuthHandler.class.getSimpleName();
    private OnCompleteListener mListener;
    private LoginDialog mLoginDialog;

    WebViewAuthHandler() throws  {
    }

    public boolean start(Activity $r1, AuthenticationRequest $r2) throws  {
        Log.d(TAG, "start");
        this.mLoginDialog = new LoginDialog($r1, $r2);
        this.mLoginDialog.setOnCompleteListener(this.mListener);
        this.mLoginDialog.show();
        return true;
    }

    public void stop() throws  {
        Log.d(TAG, "stop");
        if (this.mLoginDialog != null) {
            this.mLoginDialog.close();
            this.mLoginDialog = null;
        }
    }

    public void setOnCompleteListener(OnCompleteListener $r1) throws  {
        this.mListener = $r1;
        if (this.mLoginDialog != null) {
            this.mLoginDialog.setOnCompleteListener($r1);
        }
    }
}
