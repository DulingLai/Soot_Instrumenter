package com.waze.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DismissNotificationActivity extends Activity {
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NotificationManager) getSystemService("notification")).cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        finish();
    }

    public static Intent getDismissIntent(Context context, int notificationId) {
        Intent intent = new Intent(context, DismissNotificationActivity.class);
        intent.setFlags(268468224);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        return intent;
    }
}
