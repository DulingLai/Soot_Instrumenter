package com.facebook.internal;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog.Builder;
import com.facebook.internal.WebDialog.OnCompleteListener;

public class FacebookDialogFragment extends DialogFragment {
    public static final String TAG = "FacebookDialogFragment";
    private Dialog dialog;

    class C05211 implements OnCompleteListener {
        C05211() throws  {
        }

        public void onComplete(Bundle $r1, FacebookException $r2) throws  {
            FacebookDialogFragment.this.onCompleteWebDialog($r1, $r2);
        }
    }

    class C05222 implements OnCompleteListener {
        C05222() throws  {
        }

        public void onComplete(Bundle $r1, FacebookException error) throws  {
            FacebookDialogFragment.this.onCompleteWebFallbackDialog($r1);
        }
    }

    public void setDialog(Dialog $r1) throws  {
        this.dialog = $r1;
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if (this.dialog == null) {
            WebDialog $r8;
            FragmentActivity $r3 = getActivity();
            $r1 = NativeProtocol.getMethodArgumentsFromIntent($r3.getIntent());
            String $r5;
            if ($r1.getBoolean(NativeProtocol.WEB_DIALOG_IS_FALLBACK, false)) {
                $r5 = $r1.getString("url");
                if (Utility.isNullOrEmpty($r5)) {
                    Utility.logd(TAG, "Cannot start a fallback WebDialog with an empty/missing 'url'");
                    $r3.finish();
                    return;
                }
                $r8 = r0;
                WebDialog facebookWebFallbackDialog = new FacebookWebFallbackDialog($r3, $r5, String.format("fb%s://bridge/", new Object[]{FacebookSdk.getApplicationId()}));
                $r8.setOnCompleteListener(new C05222());
            } else {
                $r5 = $r1.getString(NativeProtocol.WEB_DIALOG_ACTION);
                $r1 = $r1.getBundle(NativeProtocol.WEB_DIALOG_PARAMS);
                if (Utility.isNullOrEmpty($r5)) {
                    Utility.logd(TAG, "Cannot start a WebDialog with an empty/missing 'actionName'");
                    $r3.finish();
                    return;
                }
                $r8 = new Builder($r3, $r5, $r1).setOnCompleteListener(new C05211()).build();
            }
            this.dialog = $r8;
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) throws  {
        if (this.dialog == null) {
            onCompleteWebDialog(null, null);
            setShowsDialog(false);
        }
        return this.dialog;
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        if (this.dialog instanceof WebDialog) {
            ((WebDialog) this.dialog).resize();
        }
    }

    public void onDestroyView() throws  {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private void onCompleteWebDialog(Bundle $r1, FacebookException $r2) throws  {
        byte $b0;
        FragmentActivity $r3 = getActivity();
        Intent $r4 = NativeProtocol.createProtocolResultIntent($r3.getIntent(), $r1, $r2);
        if ($r2 == null) {
            $b0 = (byte) -1;
        } else {
            $b0 = (byte) 0;
        }
        $r3.setResult($b0, $r4);
        $r3.finish();
    }

    private void onCompleteWebFallbackDialog(Bundle $r3) throws  {
        FragmentActivity $r2 = getActivity();
        Intent $r1 = new Intent();
        if ($r3 == null) {
            $r3 = new Bundle();
        }
        $r1.putExtras($r3);
        $r2.setResult(-1, $r1);
        $r2.finish();
    }
}
