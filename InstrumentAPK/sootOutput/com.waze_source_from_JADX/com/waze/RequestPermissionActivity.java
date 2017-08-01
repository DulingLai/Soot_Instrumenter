package com.waze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public final class RequestPermissionActivity extends Activity {
    public static final String INT_NEEDED_PERMISSIONS = "needed_permissions";
    public static final String INT_ON_GRANTED = "on_granted";
    public static final String INT_ON_REJECTED = "on_rejected";
    private static final int MY_PERMISSIONS_REQUEST = 1001;
    private static final int REAL_INTENT = 1002;
    private String[] mNeededPermissions;
    private Intent mOnGranted;
    private Intent mOnRejected;

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNeededPermissions = getIntent().getStringArrayExtra(INT_NEEDED_PERMISSIONS);
        this.mOnGranted = (Intent) getIntent().getParcelableExtra(INT_ON_GRANTED);
        this.mOnRejected = (Intent) getIntent().getParcelableExtra(INT_ON_REJECTED);
        if (!requestPermissionNeeded()) {
            onGranted();
        }
    }

    public boolean requestPermissionNeeded() throws  {
        boolean $z0 = false;
        for (String $r1 : this.mNeededPermissions) {
            if (ContextCompat.checkSelfPermission(this, $r1) != 0) {
                $z0 = true;
                break;
            }
        }
        if (!$z0) {
            return false;
        }
        ActivityCompat.requestPermissions(this, this.mNeededPermissions, 1001);
        return true;
    }

    public void onRequestPermissionsResult(int $i0, String[] permissions, int[] $r2) throws  {
        switch ($i0) {
            case 1001:
                if ($r2.length <= 0 || $r2[0] != 0) {
                    onRejected();
                    return;
                } else {
                    onGranted();
                    return;
                }
            default:
                return;
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        if ($i0 == 1002) {
            setResult($i1, $r1);
            finish();
        }
    }

    public void onGranted() throws  {
        if (this.mOnGranted != null) {
            startActivityForResult(this.mOnGranted, 1002);
        }
        setResult(-1);
        finish();
    }

    public void onRejected() throws  {
        if (this.mOnRejected != null) {
            startActivityForResult(this.mOnRejected, 1002);
        }
        setResult(0);
        finish();
    }
}
