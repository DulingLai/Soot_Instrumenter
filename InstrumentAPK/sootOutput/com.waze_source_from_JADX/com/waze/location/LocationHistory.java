package com.waze.location;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.reporting.Reporting.ReportingStateResult;
import com.google.android.gms.location.reporting.ReportingServices;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.GoogleSignInActivity;
import com.waze.utils.OfflineStats;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LocationHistory implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final String PREF_KEY_LOCATION_HISTORY_OPT_OUT = "location_history_opt_out";
    private static final String WHITELISTED_PACKAGE = "com.waze";
    private final Account account;
    private final GoogleApiClient client;
    private final Context context;

    public interface LocationHistoryOptInStatus {
        void onLocationHistoryOptInStatus(boolean z, boolean z2, boolean z3) throws ;
    }

    private static class RunnableTask extends AsyncTask<Void, Void, Void> {
        boolean bSuccess = false;
        Context context;
        LocationHistory helper;
        boolean isOptedIn = false;
        LocationHistoryOptInStatus lhois;

        RunnableTask(LocationHistory $r1, LocationHistoryOptInStatus $r2, Context $r3) throws  {
            this.helper = $r1;
            this.lhois = $r2;
            this.context = $r3;
        }

        protected Void doInBackground(Void... voids) throws  {
            try {
                this.isOptedIn = this.helper.isOptedIn();
                this.bSuccess = true;
            } catch (IOException $r2) {
                Logger.m36d("Failed to check location history opt in" + $r2.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void v) throws  {
            String $r3;
            if (AppService.IsAppRunning()) {
                $r3 = this.bSuccess ? this.isOptedIn ? AnalyticsEvents.ANALYTICS_EVENT_ON : AnalyticsEvents.ANALYTICS_EVENT_OFF : AnalyticsEvents.ANALYTICS_EVENT_INFO_UNAVAILABLE;
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_HISTORY_CHECKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, $r3);
            } else {
                Context $r5 = this.context;
                String[] $r2 = new String[2];
                $r2[0] = AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE;
                $r3 = this.bSuccess ? this.isOptedIn ? AnalyticsEvents.ANALYTICS_EVENT_ON : AnalyticsEvents.ANALYTICS_EVENT_OFF : AnalyticsEvents.ANALYTICS_EVENT_INFO_UNAVAILABLE;
                $r2[1] = $r3;
                OfflineStats.SendStats($r5, AnalyticsEvents.ANALYTICS_EVENT_LOCATION_HISTORY_CHECKED, $r2);
            }
            this.lhois.onLocationHistoryOptInStatus(this.bSuccess, false, this.isOptedIn);
        }

        public void run() throws  {
            doInBackground(new Void[0]);
            onPostExecute(null);
        }
    }

    public static boolean isOptedOut(Context context) throws  {
        return false;
    }

    private boolean isSigned() throws  {
        return true;
    }

    public LocationHistory(Context $r1) throws  {
        this.context = $r1;
        this.account = GetSelectedAccount($r1);
        this.client = new Builder($r1).addApi(ReportingServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }

    private boolean shouldOptIn() throws IOException {
        boolean $z0 = false;
        connect();
        ReportingStateResult $r6 = (ReportingStateResult) ReportingServices.ReportingApi.getReportingStateSafe(this.client, this.account).await();
        if ($r6.getStatus().isSuccess()) {
            if ($r6.shouldOptInInsistent()) {
                $z0 = true;
            }
            this.client.disconnect();
            return $z0;
        }
        throw new IOException("Could not get location reporting settings: " + $r6.getStatus());
    }

    private boolean isOptedIn() throws IOException {
        boolean $z0 = false;
        connect();
        ReportingStateResult $r6 = (ReportingStateResult) ReportingServices.ReportingApi.getReportingStateSafe(this.client, this.account).await();
        if ($r6.getStatus().isSuccess()) {
            if ($r6.isOptedIn()) {
                $z0 = true;
            }
            this.client.disconnect();
            return $z0;
        }
        throw new IOException("Could not get location reporting settings: " + $r6.getStatus());
    }

    private void tryOptIn() throws IOException {
        connect();
        Status $r6 = (Status) ReportingServices.ReportingApi.tryOptIn(this.client, this.account).await();
        if ($r6.isSuccess()) {
            this.client.disconnect();
            return;
        }
        throw new IOException("Failed to opt in to location reporting " + $r6);
    }

    private void connect() throws IOException {
        if (isSigned()) {
            ConnectionResult $r4 = this.client.blockingConnect(30, TimeUnit.SECONDS);
            if (!$r4.isSuccess()) {
                throw new IOException("Could not connect to Google Play API. Error code: " + $r4.getErrorCode());
            }
            return;
        }
        throw new IOException("App not properly signed, so not connecting.");
    }

    public static void setOptedOut(Context $r0) throws  {
        $r0.getSharedPreferences("pref", 4).edit().putBoolean(PREF_KEY_LOCATION_HISTORY_OPT_OUT, true).commit();
    }

    private static Account getAccountByName(String $r0, Context $r1) throws  {
        AccountManager $r2 = AccountManager.get($r1);
        if ($r0 != null) {
            for (Account $r4 : $r2.getAccountsByType("com.google")) {
                if ($r0.equals($r4.name)) {
                    return $r4;
                }
            }
        }
        return null;
    }

    private static Account GetSelectedAccount(Context $r0) throws  {
        String $r1 = GoogleSignInActivity.GetStoredAuthorizedAccountName($r0);
        if ($r1 != null) {
            return getAccountByName($r1, $r0);
        }
        return null;
    }

    public static void checkLocationHistoryOptInStatus(Context $r0, LocationHistoryOptInStatus $r1) throws  {
        boolean $z0 = true;
        if (isOptedOut($r0)) {
            if (AppService.IsAppRunning()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_HISTORY_CHECKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, "OPT_OUT");
            } else {
                Context context = $r0;
                OfflineStats.SendStats(context, AnalyticsEvents.ANALYTICS_EVENT_LOCATION_HISTORY_CHECKED, new String[]{AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, "OPT_OUT"});
            }
            $r1.onLocationHistoryOptInStatus(true, true, false);
            return;
        }
        LocationHistory $r2 = new LocationHistory($r0);
        if ($r2.client == null || $r2.account == null) {
            if (AppService.IsAppRunning()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_LOCATION_HISTORY_CHECKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, "NULL");
            } else {
                context = $r0;
                OfflineStats.SendStats(context, AnalyticsEvents.ANALYTICS_EVENT_LOCATION_HISTORY_CHECKED, new String[]{AnalyticsEvents.ANALYTICS_EVENT_INFO_STATE, "NULL"});
            }
            $r1.onLocationHistoryOptInStatus(false, false, false);
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            $z0 = false;
        }
        RunnableTask runnableTask = new RunnableTask($r2, $r1, $r0);
        if ($z0) {
            runnableTask.execute(new Void[0]);
            return;
        }
        runnableTask.run();
    }

    public static void tryToOptIn(Context $r0, final LocationHistoryOptInStatus $r1) throws  {
        final LocationHistory $r2 = new LocationHistory($r0);
        if ($r2.client != null && $r2.account != null) {
            new AsyncTask<Void, Void, Void>() {
                boolean bSuccess = false;
                boolean didOptIn = false;

                protected Void doInBackground(Void... voids) throws  {
                    try {
                        $r2.tryOptIn();
                        this.didOptIn = true;
                        this.bSuccess = true;
                    } catch (IOException $r2) {
                        Logger.m36d("Failed to opt in " + $r2.getMessage());
                    }
                    return null;
                }

                protected void onPostExecute(Void v) throws  {
                    if ($r1 != null) {
                        $r1.onLocationHistoryOptInStatus(this.bSuccess, false, this.didOptIn);
                    }
                }
            }.execute(new Void[0]);
        }
    }

    public static void maybeShowLocationHistoryOptInDialog(final Context $r0) throws  {
        if (!isOptedOut($r0)) {
            final LocationHistory $r1 = new LocationHistory($r0);
            if ($r1.client != null && $r1.account != null) {
                new AsyncTask<Void, Void, Boolean>() {
                    protected Boolean doInBackground(Void... voids) throws  {
                        try {
                            return Boolean.valueOf($r1.shouldOptIn());
                        } catch (IOException $r2) {
                            Logger.m36d("Failed to check location history opt in" + $r2.getMessage());
                            return Boolean.valueOf(false);
                        }
                    }

                    protected void onPostExecute(Boolean $r1) throws  {
                        if ($r1.booleanValue()) {
                            LocationHistory.createAndShowOptInDialog($r0, $r1);
                        }
                    }
                }.execute(new Void[0]);
            }
        }
    }

    public static void createAndShowOptInDialog(Context $r0, LocationHistory $r1) throws  {
        final SharedPreferences $r2 = $r0.getSharedPreferences("pref", 4);
        View $r5 = ((LayoutInflater) $r0.getSystemService("layout_inflater")).inflate(C1283R.layout.location_history_opt_in, null);
        final AlertDialog $r7 = new AlertDialog.Builder($r0).setView($r5).setTitle((CharSequence) "Improve your matches").setCancelable(false).show();
        final LocationHistory locationHistory = $r1;
        $r5.findViewById(C1283R.id.locationHistoryOptIn).setOnClickListener(new OnClickListener() {

            class C18401 extends AsyncTask<Void, Void, Void> {
                C18401() throws  {
                }

                protected Void doInBackground(Void... voids) throws  {
                    try {
                        locationHistory.tryOptIn();
                    } catch (IOException $r2) {
                        Logger.m36d("Failed to opt in " + $r2.getMessage());
                    }
                    return null;
                }

                protected void onPostExecute(Void aVoid) throws  {
                    $r7.dismiss();
                }
            }

            public void onClick(View view) throws  {
                new C18401().execute(new Void[0]);
            }
        });
        $r5.findViewById(C1283R.id.locationHistoryOptOut).setOnClickListener(new OnClickListener() {
            public void onClick(View view) throws  {
                $r7.dismiss();
                $r2.edit().putBoolean(LocationHistory.PREF_KEY_LOCATION_HISTORY_OPT_OUT, true).commit();
            }
        });
        final WebView $r11 = (WebView) $r5.findViewById(C1283R.id.learnMoreText);
        $r11.loadUrl("file:///android_res/raw/learn_more.html");
        $r5.findViewById(C1283R.id.learnMoreLink).setOnClickListener(new OnClickListener() {
            public void onClick(View clickedView) throws  {
                if ($r11.getVisibility() == 8) {
                    $r11.setVisibility(0);
                } else {
                    $r11.setVisibility(8);
                }
            }
        });
        TextView $r13 = (TextView) $r5.findViewById(C1283R.id.accountName);
        String $r15 = $r1.account;
        String $r14 = $r15;
        $r13.setText($r15.name);
    }

    public void onConnected(Bundle bundle) throws  {
    }

    public void onConnectionSuspended(int i) throws  {
    }

    public void onConnectionFailed(ConnectionResult connectionResult) throws  {
    }
}
