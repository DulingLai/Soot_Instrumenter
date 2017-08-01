package com.spotify.sdk.android.authentication;

import android.app.Activity;

public interface AuthenticationHandler {

    public interface OnCompleteListener {
        void onCancel() throws ;

        void onComplete(AuthenticationResponse authenticationResponse) throws ;

        void onError(Throwable th) throws ;
    }

    void setOnCompleteListener(OnCompleteListener onCompleteListener) throws ;

    boolean start(Activity activity, AuthenticationRequest authenticationRequest) throws ;

    void stop() throws ;
}
