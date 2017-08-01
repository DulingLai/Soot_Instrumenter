package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.internal.zzra;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleApiActivity extends Activity implements OnCancelListener {
    protected int Cv = 0;

    public static PendingIntent zza(Context $r0, PendingIntent $r1, int $i0) throws  {
        return zza($r0, $r1, $i0, true);
    }

    public static PendingIntent zza(Context $r0, PendingIntent $r1, int $i0, boolean $z0) throws  {
        return PendingIntent.getActivity($r0, 0, zzb($r0, $r1, $i0, $z0), 134217728);
    }

    private void zza(int $i0, zzra $r1) throws  {
        switch ($i0) {
            case -1:
                $r1.zzasf();
                return;
            case 0:
                $r1.zza(new ConnectionResult(13, null), getIntent().getIntExtra("failing_client_id", -1));
                return;
            default:
                return;
        }
    }

    private void zzarv() throws  {
        Bundle $r2 = getIntent().getExtras();
        if ($r2 == null) {
            Log.e("GoogleApiActivity", "Activity started without extras");
            finish();
            return;
        }
        PendingIntent $r4 = (PendingIntent) $r2.get("pending_intent");
        Integer $r5 = (Integer) $r2.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
        if ($r4 == null && $r5 == null) {
            Log.e("GoogleApiActivity", "Activity started without resolution");
            finish();
        } else if ($r4 != null) {
            try {
                startIntentSenderForResult($r4.getIntentSender(), 1, null, 0, 0, 0);
                this.Cv = 1;
            } catch (Throwable $r7) {
                Log.e("GoogleApiActivity", "Failed to launch pendingIntent", $r7);
                finish();
            }
        } else {
            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, $r5.intValue(), 2, this);
            this.Cv = 1;
        }
    }

    public static Intent zzb(Context $r0, PendingIntent $r1, int $i0, boolean $z0) throws  {
        Intent $r2 = new Intent($r0, GoogleApiActivity.class);
        $r2.putExtra("pending_intent", $r1);
        $r2.putExtra("failing_client_id", $i0);
        $r2.putExtra("notify_manager", $z0);
        return $r2;
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        if ($i0 == 1) {
            boolean $z0 = getIntent().getBooleanExtra("notify_manager", true);
            this.Cv = 0;
            zzra $r2 = zzra.zzatu();
            setResultCode($i1);
            if ($z0) {
                zza($i1, $r2);
            }
        } else if ($i0 == 2) {
            this.Cv = 0;
            setResultCode($i1);
        }
        finish();
    }

    public void onCancel(DialogInterface dialogInterface) throws  {
        this.Cv = 0;
        setResult(0);
        finish();
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if ($r1 != null) {
            this.Cv = $r1.getInt("resolution");
        }
        if (this.Cv != 1) {
            zzarv();
        }
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        $r1.putInt("resolution", this.Cv);
        super.onSaveInstanceState($r1);
    }

    protected void setResultCode(int $i0) throws  {
        setResult($i0);
    }
}
