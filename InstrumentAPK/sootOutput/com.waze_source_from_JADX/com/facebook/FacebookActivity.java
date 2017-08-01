package com.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginFragment;

public class FacebookActivity extends FragmentActivity {
    private static String FRAGMENT_TAG = "SingleFragment";
    public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
    private Fragment singleFragment;

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C0496R.layout.com_facebook_activity_layout);
        Intent $r3 = getIntent();
        if (PASS_THROUGH_CANCEL_ACTION.equals($r3.getAction())) {
            handlePassThroughError();
            return;
        }
        FragmentManager $r6 = getSupportFragmentManager();
        Fragment $r7 = $r6.findFragmentByTag(FRAGMENT_TAG);
        Fragment $r8 = $r7;
        if ($r7 == null) {
            if (FacebookDialogFragment.TAG.equals($r3.getAction())) {
                Fragment $r2 = r10;
                Fragment r10 = new FacebookDialogFragment();
                $r2.setRetainInstance(true);
                $r2.show($r6, FRAGMENT_TAG);
                $r8 = $r2;
            } else {
                $r8 = r11;
                Fragment r11 = new LoginFragment();
                $r8.setRetainInstance(true);
                $r6.beginTransaction().add(C0496R.id.com_facebook_fragment_container, $r8, FRAGMENT_TAG).commit();
            }
        }
        this.singleFragment = $r8;
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        if (this.singleFragment != null) {
            this.singleFragment.onConfigurationChanged($r1);
        }
    }

    private void handlePassThroughError() throws  {
        Intent $r1 = getIntent();
        setResult(0, NativeProtocol.createProtocolResultIntent($r1, null, NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent($r1))));
        finish();
    }
}
