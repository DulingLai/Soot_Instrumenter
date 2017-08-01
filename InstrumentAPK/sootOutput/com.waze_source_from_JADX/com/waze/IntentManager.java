package com.waze;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.provider.Contacts.ContactMethods;
import android.provider.ContactsContract.Data;
import android.util.Log;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CommuteModelWeekActivity;
import com.waze.ifs.ui.ActivityLifetimeHandler;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.referrer.ReferrerTracker;

public final class IntentManager {
    private static IntentManager mInstance = null;
    private static boolean mReferrerSubmitted = false;

    private static final class CompatabilityWrapper {
        private CompatabilityWrapper() throws  {
        }

        private static String FetchContactAddress(Uri $r0, Activity $r1) throws  {
            Cursor $r6 = $r1.managedQuery(Data.CONTENT_URI, new String[]{"data1", "data4", "data7", "data9", "data4", "data8", "data10", "data2"}, "_id = " + ContentUris.parseId($r0) + " AND ContactsContract.Data.MIMETYPE = '" + "vnd.android.cursor.item/postal-address_v2" + "'", null, "data10 asc");
            if ($r6 == null) {
                Log.w(Logger.TAG, "No data for uri: " + $r0.toString());
                return null;
            } else if (!$r6.moveToFirst()) {
                return null;
            } else {
                String $r4;
                int $i1 = $r6.getColumnIndex("data1");
                do {
                    $r4 = $r6.getString($i1);
                    if ($r4 != null && $r4.length() > 0) {
                        return $r4;
                    }
                } while ($r6.moveToNext());
                return $r4;
            }
        }
    }

    private static boolean applyIntentHandling(Intent $r0) throws  {
        return ($r0 == null && mReferrerSubmitted) ? false : true;
    }

    static IntentManager Create() throws  {
        mInstance = new IntentManager();
        return mInstance;
    }

    public static void HandleIntent(Activity $r0, boolean $z0) throws  {
        Uri $r1 = null;
        if ($r0 != null && applyIntentHandling($r0.getIntent())) {
            Intent $r2 = $r0.getIntent();
            if ($r2 != null) {
                Uri $r3 = $r2.getData();
                $r1 = $r3;
                if ($r3 != null && $r3.toString().contains("?a=addplace")) {
                    MainActivity.sQuestionID = $r0.getIntent().getStringExtra("QuestionID");
                }
                String $r6 = $r2.getStringExtra("PushClicked");
                String $r7 = $r2.getStringExtra("AnalyticsType");
                if (!($r6 == null || $r6.isEmpty())) {
                    String $r4 = AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE;
                    String $r8 = $r6;
                    if (!($r7 == null || $r7.isEmpty())) {
                        $r4 = AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE + "|TYPE";
                        $r8 = $r6 + "|" + $r7;
                    }
                    if ($r3 != null) {
                        $r6 = $r3.getQueryParameter("carpool_ride");
                        if ($r6 != null) {
                            $r4 = $r4 + "|RIDE_ID";
                            $r8 = $r8 + "|" + $r6;
                        }
                    }
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PUSH_MESSAGE_LAUNCHED, $r4, $r8);
                }
            }
            if ($z0) {
                $r0.setIntent(null);
            }
            Logger.m41i("IntentManager: Processing deep link. Data = " + $r1);
            HandleUri($r1, $r0);
        }
    }

    public static void ParseIntentDataFlags(Activity $r0) throws  {
        Uri $r1 = null;
        if ($r0 != null && applyIntentHandling($r0.getIntent())) {
            if ($r0.getIntent() != null) {
                $r1 = $r0.getIntent().getData();
            }
            if ($r1 != null && $r1.getScheme().equals("waze")) {
                NativeManager.getInstance().bToForceLoginWithSocial = true;
            }
        }
    }

    private static void HandleUri(Uri $r0, Activity $r1) throws  {
        String $r4;
        NativeManager $r2 = AppService.getNativeManager();
        String $r3 = null;
        boolean $z0 = false;
        if ($r0 != null) {
            $r4 = $r0.getScheme();
            String $r5 = Uri.decode($r0.toString());
            if ($r5.contains("waze.com/ul")) {
                $r3 = $r5.substring($r5.indexOf("waze.com/ul") + 11, $r5.length());
            } else if ($r4.equals("http") || $r4.equals("https")) {
                boolean $z1 = $r5.matches(".*daddr=[0-9]+\\.[0-9]+,[0-9]+\\.[0-9]+.*");
                int $i0 = $r5.indexOf("daddr");
                if ($i0 == -1) {
                    $i0 = $r5.indexOf("?q");
                    if ($i0 == -1) {
                        $r5.substring(34, $r5.length() - 5);
                        $r3 = "waze://?ll=" + $r5.substring(34, $r5.length() - 4);
                    } else {
                        int $i1 = $r5.indexOf("@");
                        if ($i1 == -1) {
                            $i1 = $r5.indexOf("loc:");
                            if ($i1 == -1) {
                                $r3 = "waze://?q=" + $r5.substring($i0 + 1, $r5.length());
                            } else {
                                $r3 = "waze://?ll=" + $r5.substring($i1 + 4, $r5.length());
                            }
                        } else {
                            $r3 = "waze://?ll=" + $r5.substring($i1 + 1, $r5.length());
                        }
                    }
                } else {
                    $r3 = $r5.substring($i0 + 6, $r5.length());
                    $i0 = $r3.indexOf("&");
                    if ($i0 != -1) {
                        $r3 = $r3.substring(0, $i0);
                        if ($z1) {
                            $r3 = "waze://?ll=" + $r3;
                        } else {
                            $i0 = $r3.indexOf("@");
                            if ($i0 == -1) {
                                $r3 = "waze://?q=" + $r3;
                            } else {
                                $r3 = "waze://?ll=" + $r3.substring($i0 + 1, $r3.length());
                            }
                        }
                    } else if ($z1) {
                        $r3 = "waze://?ll=" + $r3;
                    } else {
                        $r3 = "waze://?q=" + $r3;
                    }
                }
            }
            if ($r4.equals("WazeUrl")) {
                $r3 = $r5.substring($r4.length() + 3, $r5.length());
            }
            if ($r4.equals("waze")) {
                $r2.setIsAllowTripDialog(false);
                $r3 = $r0.toString();
                $z0 = true;
            }
            if ($r4.equals("geo")) {
                $r3 = ConvertGeoUri($r5) + "&navigate=yes";
            }
            if ($r4.equals("content")) {
                $r4 = FetchContactAddress($r0, $r1);
                if ($r4 != null) {
                    $r3 = "waze://?q=" + $r4 + "&navigate=yes";
                }
            }
        }
        $r4 = ReferrerTracker.getReferrer(AppService.getAppContext());
        if ($r4 != null) {
            if ($r3 == null) {
                $r3 = "waze://?ref=" + $r4;
            } else {
                $r3 = $r3 + "&ref=" + $r4;
            }
            mReferrerSubmitted = true;
            ReferrerTracker.invalidateReferrer(AppService.getAppContext());
        }
        if ($r3 != null) {
            if (!NativeManager.IsAppStarted()) {
                $r3 = $r3 + "&startup=yes";
            } else if (ActivityLifetimeHandler.isInBackground()) {
                $r3 = $r3 + "&background=yes";
            }
        }
        $r2.UrlHandler($r3, $z0);
    }

    public static void RequestRestart(Context $r0) throws  {
        ((AlarmManager) $r0.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + 5000, PendingIntent.getBroadcast($r0, 0, new Intent($r0, MainActivity.class), 0));
    }

    private IntentManager() throws  {
    }

    private static String FetchContactAddress(Uri $r0, Activity $r1) throws  {
        if (Integer.parseInt(VERSION.SDK) >= 5) {
            return CompatabilityWrapper.FetchContactAddress($r0, $r1);
        }
        Cursor $r5 = $r1.managedQuery(ContactMethods.CONTENT_URI, new String[]{"_id", "kind", "data"}, null, null, null);
        if ($r5 == null) {
            Log.w(Logger.TAG, "No data for uri: " + $r0.toString());
            return null;
        } else if (!$r5.moveToFirst()) {
            return null;
        } else {
            long $l1 = ContentUris.parseId($r0);
            int $i0 = $r5.getColumnIndex("_id");
            int $i2 = $r5.getColumnIndex("data");
            int $i3 = $r5.getColumnIndex("kind");
            do {
                long $l4 = $r5.getLong($i0);
                int $i5 = $r5.getInt($i3);
                if ($l4 == $l1 && $i5 == 2) {
                    return $r5.getString($i2);
                }
            } while ($r5.moveToNext());
            return null;
        }
    }

    private static String ConvertGeoUri(String $r0) throws  {
        String $r2 = "geo:";
        String $r1 = "0,0?";
        if (!$r0.startsWith("geo:")) {
            return null;
        }
        $r0 = $r0.substring($r2.length());
        if ($r0.startsWith("0,0?")) {
            return "waze://?" + $r0.substring($r1.length());
        }
        return "waze://?" + "ll=" + $r0.replace('?', '&');
    }

    public static void OpenCommuteModelActivity(Activity $r0) throws  {
        CarpoolUserData $r2 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        if ($r2 == null) {
            Log.w(Logger.TAG, "OpenCommuteModelActivity called while there is no CarpoolUserData");
            return;
        }
        Intent $r4;
        if (!$r2.inferred_work.isValid() || !$r2.inferred_home.isValid() || $r2.inferredHomeConflict() || $r2.inferredWorkConflict()) {
            $r4 = new Intent($r0, AddHomeWorkActivity.class);
            $r4.putExtra("carpool", true);
        } else {
            $r4 = new Intent($r0, CommuteModelWeekActivity.class);
        }
        $r0.startActivityForResult($r4, 0);
    }
}
