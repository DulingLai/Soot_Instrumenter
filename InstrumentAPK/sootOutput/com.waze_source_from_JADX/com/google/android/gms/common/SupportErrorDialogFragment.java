package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class SupportErrorDialogFragment extends DialogFragment {
    private OnCancelListener BD = null;
    private Dialog mDialog = null;

    public static SupportErrorDialogFragment newInstance(Dialog $r0) throws  {
        return newInstance($r0, null);
    }

    public static SupportErrorDialogFragment newInstance(Dialog $r0, OnCancelListener $r1) throws  {
        SupportErrorDialogFragment $r2 = new SupportErrorDialogFragment();
        $r0 = (Dialog) zzab.zzb((Object) $r0, (Object) "Cannot display null dialog");
        $r0.setOnCancelListener(null);
        $r0.setOnDismissListener(null);
        $r2.mDialog = $r0;
        if ($r1 == null) {
            return $r2;
        }
        $r2.BD = $r1;
        return $r2;
    }

    public void onCancel(DialogInterface $r1) throws  {
        if (this.BD != null) {
            this.BD.onCancel($r1);
        }
    }

    public Dialog onCreateDialog(Bundle bundle) throws  {
        if (this.mDialog == null) {
            setShowsDialog(false);
        }
        return this.mDialog;
    }

    public void show(FragmentManager $r1, String $r2) throws  {
        super.show($r1, $r2);
    }
}
