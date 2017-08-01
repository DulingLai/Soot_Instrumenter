package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookWebFallbackDialog extends WebDialog {
    private static final int OS_BACK_BUTTON_RESPONSE_TIMEOUT_MILLISECONDS = 1500;
    private static final String TAG = FacebookWebFallbackDialog.class.getName();
    private boolean waitingForDialogToClose;

    class C05261 implements Runnable {
        C05261() throws  {
        }

        public void run() throws  {
            super.cancel();
        }
    }

    public FacebookWebFallbackDialog(Context $r1, String $r2, String $r3) throws  {
        super($r1, $r2);
        setExpectedRedirectUrl($r3);
    }

    protected Bundle parseResponseUri(String $r1) throws  {
        Bundle $r4 = Utility.parseUrlQueryString(Uri.parse($r1).getQuery());
        $r1 = $r4.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS);
        $r4.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS);
        if (!Utility.isNullOrEmpty($r1)) {
            try {
                $r4.putBundle(NativeProtocol.EXTRA_PROTOCOL_BRIDGE_ARGS, BundleJSONConverter.convertToBundle(new JSONObject($r1)));
            } catch (JSONException $r7) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", $r7);
            }
        }
        $r1 = $r4.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_RESULTS);
        String $r6 = $r1;
        $r4.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_RESULTS);
        if (!Utility.isNullOrEmpty($r1)) {
            if (Utility.isNullOrEmpty($r1)) {
                $r6 = "{}";
            }
            try {
                $r4.putBundle(NativeProtocol.EXTRA_PROTOCOL_METHOD_RESULTS, BundleJSONConverter.convertToBundle(new JSONObject($r6)));
            } catch (JSONException $r8) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", $r8);
            }
        }
        $r4.remove("version");
        $r4.putInt(NativeProtocol.EXTRA_PROTOCOL_VERSION, NativeProtocol.getLatestKnownVersion());
        return $r4;
    }

    public void cancel() throws  {
        WebView $r2 = getWebView();
        if (!isPageFinished() || isListenerCalled() || $r2 == null || !$r2.isShown()) {
            super.cancel();
        } else if (!this.waitingForDialogToClose) {
            this.waitingForDialogToClose = true;
            $r2.loadUrl("javascript:" + "(function() {  var event = document.createEvent('Event');  event.initEvent('fbPlatformDialogMustClose',true,true);  document.dispatchEvent(event);})();");
            new Handler(Looper.getMainLooper()).postDelayed(new C05261(), 1500);
        }
    }
}
