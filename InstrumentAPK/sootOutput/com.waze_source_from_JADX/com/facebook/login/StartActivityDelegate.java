package com.facebook.login;

import android.app.Activity;
import android.content.Intent;

interface StartActivityDelegate {
    Activity getActivityContext() throws ;

    void startActivityForResult(Intent intent, int i) throws ;
}
