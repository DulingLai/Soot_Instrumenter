package com.waze.referrer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;
import com.spotify.sdk.android.authentication.AuthenticationClient.QueryParams;
import com.waze.AppUUID;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.config.WazePreferences;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ReferrerTracker extends BroadcastReceiver {
    private static final Object LOCK = ReferrerTracker.class;
    public static final String LOG_TAG = "WAZE_REFERRER";
    private static final String REFERRER_COMMAND = "Stats,%s,-1,ANALYTICS_EVENT_REFERRER_RECEIVED,2,REFERRER,%s";
    private static final String REFERRER_FIELD = "referrer";
    private static final String REFERRER_FILE = "referrer";
    private static final String REFERRER_FILE_OLD = "referrer.old";
    private static final int REFERRER_NAME_MAX_SIZE = 4096;
    public static final String REFERRER_UNKNOWN = "Unknown";
    private static final String SERVICE_NAME = "distrib/static";
    private static final String SERVICE_URL_DEFAULT = "http://rt.waze.com/rtserver";

    public void onReceive(final Context context, Intent intent) {
        final String referrer = extractReferrer(intent);
        Log.d(LOG_TAG, "Received referrer broadcast for referrer: " + referrer);
        if (referrer == null) {
            return;
        }
        if (referrer.startsWith("invite_")) {
            NativeManager.mInviteId = referrer.substring(7);
        } else {
            new Thread(new Runnable() {
                public void run() {
                    ReferrerTracker.this.send(context, referrer);
                    ReferrerTracker.saveToPrefs(context, referrer);
                }
            }).start();
        }
    }

    public static void invalidateReferrer(Context context) {
        try {
            saveToPrefs(context, "");
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Failed to invalidate referrer file", ex);
        }
    }

    private String parseReferrer(String referrer) {
        return referrer.replaceAll("&", "|");
    }

    private boolean send(Context context, String referrer) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(getWebService());
            httppost.addHeader("Content-Type", "binary/octet-stream");
            httppost.setEntity(new StringEntity(getCmd(context, referrer)));
            HttpResponse rp = httpclient.execute(httppost);
            if (rp.getStatusLine().getStatusCode() == 200) {
                Log.d(LOG_TAG, "Successfully posted refferer stats. Status: " + rp.getStatusLine() + ". Referrer: " + referrer);
                return true;
            }
            Log.e(LOG_TAG, "Failed sending referrer statistics. Status: " + rp.getStatusLine());
            return false;
        } catch (IOException e) {
            Log.e(LOG_TAG, "routing request Http post error " + e.getMessage());
            return false;
        }
    }

    private String getWebService() {
        return WazePreferences.getProperty("GeoConfig.Web-Service Address", SERVICE_URL_DEFAULT) + "/" + SERVICE_NAME;
    }

    private String getCmd(Context context, String referrer) {
        return String.format(REFERRER_COMMAND, new Object[]{AppUUID.getInstallationUUID(context), referrer});
    }

    private String extractReferrer(Intent intent) {
        try {
            String referrer = intent.getStringExtra(QueryParams.REFERRER);
            if (referrer != null) {
                return parseReferrer(Uri.decode(referrer));
            }
            return referrer;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Referrer extraction error", ex);
            return null;
        }
    }

    public static String getReferrer(Context context) {
        String referrer = null;
        synchronized (LOCK) {
            try {
                referrer = getPrefs(context).getString(QueryParams.REFERRER, "");
                Logger.d_(LOG_TAG, "Loading prefs. Referrer: " + referrer);
                if (referrer.length() == 0) {
                    referrer = null;
                }
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Failed to read referrer file", ex);
            }
        }
        if (referrer == null) {
            Logger.d_(LOG_TAG, "Referrer is empty");
        }
        return referrer;
    }

    private static void saveToPrefs(Context context, String referrer) {
        synchronized (LOCK) {
            try {
                Logger.d_(LOG_TAG, "Saving to prefs. Referrer: " + referrer);
                Editor editor = getPrefs(context).edit();
                editor.putString(QueryParams.REFERRER, referrer);
                editor.commit();
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Failed to update referrer prefs", ex);
            }
        }
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(QueryParams.REFERRER, 0);
    }

    public static void __unit_test(Context context) {
        Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
        intent.putExtra(QueryParams.REFERRER, "AGA_UNIT_TEST_REFERRER");
        context.sendBroadcast(intent);
    }
}
