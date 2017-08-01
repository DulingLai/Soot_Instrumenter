package com.spotify.sdk.android.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AuthCallbackActivity extends Activity {
    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        Intent $r2 = new Intent(this, LoginActivity.class);
        $r2.setData(getIntent().getData());
        $r2.setFlags(603979776);
        startActivity($r2);
        finish();
    }
}
