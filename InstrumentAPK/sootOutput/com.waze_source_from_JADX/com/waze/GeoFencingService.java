package com.waze;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.ContextCompat;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.ActivityRecognition;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.messages.QuestionData;
import com.waze.push.Alerter;
import com.waze.utils.OfflineStats;
import com.waze.view.dialogs.ParkingPinsFeedbackDialog;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GeoFencingService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final int GPS_REQUEST_TIMEOUT = 30;
    private static boolean IsInitialized = false;
    private static final int LOCATION_TIMEOUT = 30;
    private static boolean bIsInternalStop = false;
    private static boolean bIsStop = false;
    private static PendingIntent callbackIntent;
    private static GoogleApiClient mActivityRecognitionClient = null;
    private static Handler mHandler = new Handler();
    private static GeoFencingService mInstance = null;
    private static QuestionData mQuestion = null;

    class C11461 implements Runnable {
        C11461() throws  {
        }

        public void run() throws  {
            if (GeoFencingService.bIsStop && GeoFencingService.mInstance != null) {
                OfflineStats.SendStats(GeoFencingService.mInstance, "ACTIVITY_RECOGNITION_TIMEOUT", new String[]{"TIME", Long.toString(System.currentTimeMillis())});
                GeoFencingService.stop(false);
            }
        }
    }

    static class CustomLocationListener implements LocationListener {
        public boolean timedOut = false;

        CustomLocationListener() throws  {
        }

        public void onLocationChanged(Location $r1) throws  {
            Logger.m36d("SetParking - onLocationChanged");
            if (!this.timedOut && $r1 != null) {
                GeoFencingService.reportExperimentLocation($r1, ParkingPinsFeedbackDialog.GPS_TAG);
                GeoFencingService.stop(true);
            }
        }

        public void onStatusChanged(String $r1, int $i0, Bundle extras) throws  {
            if (!this.timedOut) {
                Logger.m36d("SetParking - onStatusChanged. Status of " + $r1 + " is now " + $i0);
                if ($i0 != 2) {
                    GeoFencingService.stop(true);
                }
            }
        }

        private void onTimeout() throws  {
            if (!this.timedOut && GeoFencingService.mInstance != null) {
                Logger.m36d("SetParking - onTimeout");
                this.timedOut = true;
                ((LocationManager) GeoFencingService.mInstance.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION)).removeUpdates(this);
                GeoFencingService.stop(true);
            }
        }

        public void onProviderEnabled(String provider) throws  {
            Logger.m36d("SetParking - onProviderEnabled. Awaiting GPS location for parking");
        }

        public void onProviderDisabled(String provider) throws  {
            Logger.m43w("SetParking - onProviderDisabled. no GPS location for parking");
            GeoFencingService.stop(true);
        }
    }

    private static void SendParkingLocation(android.location.Location r48, int r49, int r50) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:35:0x0270 in {2, 7, 17, 20, 23, 27, 29, 32, 34, 36, 39, 43} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = mInstance;
        if (r7 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r9 = 2;
        r8 = new double[r9];
        r0 = r48;
        r10 = r0.getLatitude();
        r9 = 0;
        r8[r9] = r10;
        r0 = r48;
        r10 = r0.getLongitude();
        r9 = 1;
        r8[r9] = r10;
        r9 = 1;
        r10 = r8[r9];
        r12 = java.lang.Double.toString(r10);
        r9 = 0;
        r10 = r8[r9];
        r13 = java.lang.Double.toString(r10);
        r7 = mInstance;
        r14 = r7.getApplicationContext();
        r15 = com.waze.messages.QuestionData.GetLocationData(r14);
        r17 = "dest_lon";
        r0 = r17;
        r16 = r15.get(r0);
        r19 = r16;
        r19 = (java.lang.String) r19;
        r18 = r19;
        r17 = "dest_lat";
        r0 = r17;
        r16 = r15.get(r0);
        r21 = r16;
        r21 = (java.lang.String) r21;
        r20 = r21;
        r17 = "dest_venueId";
        r0 = r17;
        r16 = r15.get(r0);
        r23 = r16;
        r23 = (java.lang.String) r23;
        r22 = r23;
        r24 = java.lang.System.currentTimeMillis();
        r26 = com.waze.NativeManager.IsAppStarted();
        if (r26 == 0) goto L_0x0274;
    L_0x0066:
        r27 = com.waze.NativeManager.getInstance();
        r0 = r27;
        r26 = r0.isNavigatingNTV();
        if (r26 != 0) goto L_0x0274;
    L_0x0072:
        r26 = 1;
    L_0x0074:
        r28 = 0;
        if (r18 == 0) goto L_0x00de;
    L_0x0078:
        r0 = r18;
        r29 = r0.isEmpty();
        if (r29 != 0) goto L_0x00de;
    L_0x0080:
        if (r20 == 0) goto L_0x00de;
    L_0x0082:
        r0 = r20;
        r29 = r0.isEmpty();
        if (r29 != 0) goto L_0x00de;
    L_0x008a:
        r0 = r18;
        r30 = java.lang.Float.parseFloat(r0);
        r31 = 1232348160; // 0x49742400 float:1000000.0 double:6.088608896E-315;
        r30 = r31 * r30;
        r0 = r30;
        r0 = (int) r0;
        r28 = r0;
        r0 = r20;
        r30 = java.lang.Float.parseFloat(r0);
        r31 = 1232348160; // 0x49742400 float:1000000.0 double:6.088608896E-315;
        r30 = r31 * r30;
        r0 = r30;
        r0 = (int) r0;
        r32 = r0;
        r9 = 1;
        r10 = r8[r9];
        r33 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r0 = r33;
        r10 = r10 * r0;
        r0 = (int) r10;
        r35 = r0;
        r9 = 0;
        r10 = r8[r9];
        r33 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r0 = r33;
        r10 = r10 * r0;
        r0 = (int) r10;
        r36 = r0;
        r29 = com.waze.NativeManager.IsAppStarted();
        if (r29 == 0) goto L_0x0277;
    L_0x00cc:
        r27 = com.waze.NativeManager.getInstance();
        r0 = r27;
        r1 = r28;
        r2 = r32;
        r3 = r35;
        r4 = r36;
        r28 = r0.mathDistance(r1, r2, r3, r4);
    L_0x00de:
        r9 = 2;
        r0 = new java.lang.String[r9];
        r37 = r0;
        r9 = 0;
        r17 = "LON|LAT|SPEED|TIME|GPS_TIME|DEST_LON|DEST_LAT|DEST_VENUE_ID|DISTANCE|ACTIVITY|CONFIDENCE|ONLINE|VISIBLE";
        r37[r9] = r17;
        r38 = new java.lang.StringBuilder;
        r0 = r38;
        r0.<init>();
        r0 = r38;
        r38 = r0.append(r12);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r38 = r0.append(r13);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r48;
        r30 = r0.getSpeed();
        r0 = r38;
        r1 = r30;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r1 = r24;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r48;
        r39 = r0.getTime();
        r0 = r38;
        r1 = r39;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r1 = r18;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r1 = r20;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r1 = r22;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r1 = r28;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r49;
        r12 = getActivityName(r0);
        r0 = r38;
        r38 = r0.append(r12);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r0 = r38;
        r1 = r50;
        r38 = r0.append(r1);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        r29 = com.waze.NativeManager.IsAppStarted();
        if (r29 == 0) goto L_0x0290;
    L_0x01d2:
        r12 = "T";
    L_0x01d4:
        r0 = r38;
        r38 = r0.append(r12);
        r17 = "|";
        r0 = r38;
        r1 = r17;
        r38 = r0.append(r1);
        if (r26 == 0) goto L_0x0293;
    L_0x01e7:
        r12 = "TRUE";
    L_0x01e9:
        r0 = r38;
        r38 = r0.append(r12);
        r0 = r38;
        r12 = r0.toString();
        r9 = 1;
        r37[r9] = r12;
        r7 = mInstance;
        r14 = r7.getApplicationContext();
        r17 = "SET_PARKING";
        r9 = 1;
        r0 = r17;
        r1 = r37;
        com.waze.utils.OfflineStats.SendStats(r14, r0, r1, r9);
        r29 = com.waze.NativeManager.IsAppStarted();
        if (r29 == 0) goto L_0x0296;
    L_0x020e:
        r27 = com.waze.NativeManager.getInstance();
        r9 = 1;
        r10 = r8[r9];
        r33 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r0 = r33;
        r10 = r10 * r0;
        r0 = (int) r10;
        r50 = r0;
        r9 = 0;
        r10 = r8[r9];
        r33 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r0 = r33;
        r10 = r10 * r0;
        r0 = (int) r10;
        r28 = r0;
        goto L_0x0232;
    L_0x022f:
        goto L_0x01d4;
    L_0x0232:
        r41 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r24;
        r2 = r41;
        r0 = r0 / r2;
        r24 = r0;
        goto L_0x023f;
    L_0x023c:
        goto L_0x01e9;
    L_0x023f:
        r0 = (int) r0;
        r49 = r0;
        r43 = 0;
        r0 = r27;
        r1 = r50;
        r2 = r28;
        r3 = r49;
        r4 = r43;
        r5 = r26;
        r0.Set_Parking(r1, r2, r3, r4, r5);
        if (r26 == 0) goto L_0x0266;
    L_0x0255:
        r44 = new com.waze.view.dialogs.TimeToParkFeedbackDialog;
        r17 = "SET_PARKING";
        r9 = 1;
        r0 = r44;
        r1 = r17;
        r0.<init>(r1, r9);
        r0 = r44;
        r0.show();
    L_0x0266:
        r7 = mInstance;
        r14 = r7.getApplicationContext();
        com.waze.messages.QuestionData.ResetLocationData(r14);
        return;
        goto L_0x0274;
    L_0x0271:
        goto L_0x0074;
    L_0x0274:
        r26 = 0;
        goto L_0x0271;
    L_0x0277:
        r7 = mInstance;
        r45 = com.waze.OfflineNativeManager.getInstance(r7);
        goto L_0x0281;
    L_0x027e:
        goto L_0x00de;
    L_0x0281:
        r0 = r45;
        r1 = r28;
        r2 = r32;
        r3 = r35;
        r4 = r36;
        r28 = r0.mathDistanceNTV(r1, r2, r3, r4);
        goto L_0x027e;
    L_0x0290:
        r12 = "F";
        goto L_0x022f;
    L_0x0293:
        r12 = "FALSE";
        goto L_0x023c;
    L_0x0296:
        r7 = mInstance;
        r14 = r7.getApplicationContext();
        r9 = 1;
        r46 = r8[r9];
        r9 = 0;
        r10 = r8[r9];
        r41 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r24;
        r2 = r41;
        r0 = r0 / r2;
        r24 = r0;
        r0 = (int) r0;
        r49 = r0;
        r43 = 0;
        r0 = r14;
        r1 = r46;
        r3 = r10;
        r5 = r49;
        r6 = r43;
        com.waze.messages.QuestionData.SetParking(r0, r1, r3, r5, r6);
        goto L_0x0266;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.GeoFencingService.SendParkingLocation(android.location.Location, int, int):void");
    }

    public IBinder onBind(Intent intent) throws  {
        return null;
    }

    public void onCreate() throws  {
        super.onCreate();
        mInstance = this;
        startActivityRecognitionConnection();
        bIsInternalStop = false;
        IsInitialized = true;
        bIsStop = true;
        mHandler.postDelayed(new C11461(), 1800000);
    }

    public void onDestroy() throws  {
        StopServices(getApplicationContext());
        if (!bIsStop) {
            mInstance = null;
            IsInitialized = false;
        }
        super.onDestroy();
    }

    public static boolean SetParking(int $i0, int $i1) throws  {
        if (mInstance == null) {
            return true;
        }
        LocationManager $r4 = (LocationManager) mInstance.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
        if (!locationAllowed()) {
            return false;
        }
        Location $r5 = $r4.getLastKnownLocation("gps");
        if ($r5 != null && $r5.getTime() + 300000 > System.currentTimeMillis()) {
            SendParkingLocation($r5, $i0, $i1);
        }
        if (OfflineNativeManager.getInstance(mInstance).isParkingDetectionExperimentEnabledNTV()) {
            if ($r5 != null) {
                reportExperimentLocation($r5, "default");
            }
            $r5 = $r4.getLastKnownLocation("passive");
            if ($r5 != null) {
                reportExperimentLocation($r5, ParkingPinsFeedbackDialog.AVAILABLE_TAG);
            }
            CustomLocationListener customLocationListener = new CustomLocationListener();
            $r4.requestSingleUpdate("gps", customLocationListener, null);
            final CustomLocationListener customLocationListener2 = customLocationListener;
            new Timer().schedule(new TimerTask() {
                public void run() throws  {
                    customLocationListener2.onTimeout();
                }
            }, 30000);
            return true;
        }
        stop(true);
        return true;
    }

    private static void reportExperimentLocation(Location $r0, String $r1) throws  {
        if (mInstance != null) {
            int $i2;
            Map $r4 = QuestionData.GetLocationData(mInstance.getApplicationContext());
            boolean $z0 = NativeManager.IsAppStarted();
            String $r6 = (String) $r4.get(QuestionData.DESTINATION_LON);
            String str = (String) $r4.get(QuestionData.DESTINATION_LAT);
            String str2 = (String) $r4.get(QuestionData.DESTINATION_VENUE_ID);
            int $i0 = 0;
            if (!($r6 == null || $r6.isEmpty() || str == null || str.isEmpty())) {
                $i0 = (int) (1000000.0f * Float.parseFloat($r6));
                int $i1 = (int) (1000000.0f * Float.parseFloat(str));
                $i2 = $r0.getLongitude() * 1000000.0d;
                long j = $i2;
                int $i22 = (int) $i2;
                $i2 = $r0.getLatitude() * 1000000.0d;
                j = $i2;
                int $i3 = (int) $i2;
                $i0 = NativeManager.IsAppStarted() ? NativeManager.getInstance().mathDistance($i0, $i1, $i22, $i3) : OfflineNativeManager.getInstance(mInstance).mathDistanceNTV($i0, $i1, $i22, $i3);
            }
            AnalyticsBuilder $r10 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SET_PARKING_EXPERIMENT).addParam("LON", $r0.getLongitude()).addParam("LAT", $r0.getLatitude()).addParam("SPEED", $r0.getSpeed()).addParam("ACCURACY", $r0.getAccuracy()).addParam("PROVIDER", $r0.getProvider()).addParam("PROVIDER_TAG", $r1).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_PARKING_GPS_TIME, $r0.getTime()).addParam("TIME", System.currentTimeMillis()).addParam("ONLINE", $z0 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F);
            if ($r6 != null) {
                $r10.addParam("DEST_LON", $r6);
            }
            if (str != null) {
                $r10.addParam("DEST_LAT", str);
            }
            if (str2 != null) {
                $r10.addParam("DEST_VENUE_ID", str2);
            }
            if ($i0 != 0) {
                $r10.addParam(AnalyticsEvents.ANALYTICS_AUTOCOMPLETE_DISTANCE, (long) $i0);
            }
            long j2;
            if ($z0) {
                $r10.send();
                if (ConfigValues.getBoolValue(236)) {
                    $i2 = $r0.getTime() / 1000;
                    j2 = $i2;
                    NativeManager.getInstance().Set_Parking((int) $r0.getLongitude(), (int) $r0.getLatitude(), (int) $i2, $r1, true);
                    return;
                }
                return;
            }
            OfflineStats.SendStats(mInstance.getApplicationContext(), AnalyticsEvents.ANALYTICS_EVENT_SET_PARKING_EXPERIMENT, $r10.getParamsAsStringPair(), true);
            $i2 = $r0.getTime() / 1000;
            j2 = $i2;
            QuestionData.SetParking(mInstance.getApplicationContext(), $r0.getLongitude(), $r0.getLatitude(), (int) $i2, $r1);
        }
    }

    private static boolean locationAllowed() throws  {
        return mInstance != null && ContextCompat.checkSelfPermission(mInstance, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public static boolean CreatePushMessage(int $i0, int $i1) throws  {
        if (mInstance == null) {
            return false;
        }
        String $r5;
        OfflineStats.SendStats(mInstance.getApplicationContext(), AnalyticsEvents.ANALYTICS_EVENT_START_WALKING, new String[]{"TIME", Long.toString(System.currentTimeMillis())});
        OfflineStats.SendAdsStats(mInstance.getApplicationContext(), "ADS_OFFLINE_ARRIVED");
        mQuestion = QuestionData.GetQuestionData(mInstance.getApplicationContext());
        switch (mQuestion.AnswerType) {
            case 0:
                $r5 = "UNKNOWN";
                break;
            case 1:
                $r5 = "NONE";
                break;
            case 2:
                $r5 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_ALERT;
                break;
            case 3:
                $r5 = AnalyticsEvents.ANALYTICS_EVENT_INFO_ANSWER;
                break;
            case 4:
                $r5 = "PARKING";
                break;
            default:
                $r5 = "UNKNOWN";
                break;
        }
        boolean $z0 = SetParking($i0, $i1);
        if (mQuestion.AnswerType == 4) {
            QuestionData.ResetQuestionData(mInstance.getApplicationContext());
            return $z0;
        } else if (mQuestion.Text == null || mQuestion.Text.isEmpty()) {
            return $z0;
        } else {
            OfflineStats.SendStats(mInstance.getApplicationContext(), AnalyticsEvents.ANALYTICS_EVENT_INTERNAL_PUSH_RECEIVED, new String[]{"PUSH_TYPE|ID", $r5 + "|" + mQuestion.QuestionID});
            Intent intent;
            Notification $r15;
            if (VERSION.SDK_INT >= 16) {
                intent = new Intent(mInstance.getApplicationContext(), FreeMapAppActivity.class);
                intent.setData(Uri.parse(mQuestion.ActionText1));
                PendingIntent $r11 = PendingIntent.getActivity(mInstance.getApplicationContext(), 0, intent, 268435456);
                intent = new Intent(mInstance.getApplicationContext(), ActionIntent.class);
                Uri $r10 = Uri.parse(mQuestion.ActionText1);
                intent = intent;
                intent.putExtra("QuestionID", mQuestion.QuestionID);
                intent.putExtra("QuestionType", $r5);
                intent = intent;
                intent.putExtra("Action", mQuestion.ActionText1);
                intent.setData($r10);
                PendingIntent $r12 = PendingIntent.getBroadcast(mInstance.getApplicationContext(), 1, intent, 268435456);
                intent = new Intent(mInstance.getApplicationContext(), ActionIntent.class);
                intent.setData(Uri.parse(mQuestion.ActionText2));
                intent = intent;
                intent.putExtra("QuestionID", mQuestion.QuestionID);
                intent.putExtra("QuestionType", $r5);
                intent = intent;
                intent.putExtra("Action", mQuestion.ActionText2);
                PendingIntent $r13 = PendingIntent.getBroadcast(mInstance.getApplicationContext(), 1, intent, 268435456);
                Builder builder = new Builder(mInstance.getApplicationContext());
                builder.setContentIntent($r11);
                builder.setSmallIcon(C1283R.drawable.notification);
                builder.setColor(mInstance.getApplicationContext().getResources().getColor(C1283R.color.notification_circle_bg));
                builder.setContentTitle("Waze");
                builder.setContentText(mQuestion.Text);
                builder.setPriority(2);
                builder.setAutoCancel(true);
                Alerter.setVibrationMode(builder, mInstance.getApplicationContext());
                builder.addAction(C1283R.drawable.x_notify_icon, mQuestion.SubText2, $r13);
                builder.addAction(C1283R.drawable.v_notify_icon, mQuestion.SubText1, $r12);
                $r15 = builder.build();
                $r15.flags |= 17;
                $r15.when = System.currentTimeMillis();
                $r15.ledARGB = -16711681;
                $r15.ledOnMS = 15000;
                $r15.ledOffMS = 15000;
                ((NotificationManager) mInstance.getApplicationContext().getSystemService("notification")).notify(2, $r15);
                QuestionData.ResetQuestionData(mInstance.getApplicationContext());
                return $z0;
            }
            intent = new Intent(mInstance.getApplicationContext(), FreeMapAppActivity.class);
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setData(Uri.parse(mQuestion.ActionText1));
            String str = "Waze";
            $r15 = createNotification(mInstance.getApplicationContext(), str, mQuestion.Text, PendingIntent.getActivity(mInstance.getApplicationContext(), 0, intent, 268435456));
            $r15.flags |= 34;
            ((NotificationManager) mInstance.getApplicationContext().getSystemService("notification")).notify(2, $r15);
            QuestionData.ResetQuestionData(mInstance.getApplicationContext());
            return $z0;
        }
    }

    private static Notification createNotification(Context $r0, String $r1, String $r2, PendingIntent $r3) throws  {
        Builder $r4 = new Builder($r0);
        $r4.setSmallIcon(C1283R.drawable.notification).setColor($r0.getResources().getColor(C1283R.color.notification_circle_bg)).setWhen(System.currentTimeMillis()).setLights(-16711681, 15000, 15000).setContentTitle($r1).setContentText($r2).setContentIntent($r3);
        Notification $r7 = $r4.build();
        $r7.flags |= 16;
        $r7.flags |= 1;
        Alerter.setVibrationMode($r7, mInstance.getApplicationContext());
        return $r7;
    }

    public static void start(Context $r0) throws  {
        if (IsInitialized) {
            bIsStop = true;
        } else {
            $r0.startService(new Intent($r0, GeoFencingService.class));
        }
    }

    public static void stop(boolean $z0) throws  {
        if (mInstance != null) {
            QuestionData.ResetQuestionData(mInstance.getApplicationContext());
            mInstance.stopSelf();
        }
        bIsInternalStop = $z0;
    }

    public static boolean IsRunning() throws  {
        return mInstance != null;
    }

    private void StopServices(Context $r1) throws  {
        stopActivityRecognitionConnection();
        if (!bIsInternalStop) {
            ((LocationManager) $r1.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION)).removeProximityAlert(PendingIntent.getBroadcast($r1, 0, new Intent("com.android.GEO_FENCING"), 0));
        }
    }

    private void startActivityRecognitionConnection() throws  {
        mActivityRecognitionClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(ActivityRecognition.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mActivityRecognitionClient.connect();
    }

    private void stopActivityRecognitionConnection() throws  {
        try {
            OfflineStats.SendStats(this, AnalyticsEvents.ANALYTICS_EVENT_ACTIVITY_RECOGNITION_OFF, null);
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mActivityRecognitionClient, callbackIntent);
            if (mActivityRecognitionClient.isConnected()) {
                mActivityRecognitionClient.disconnect();
            }
            bIsInternalStop = false;
            bIsStop = false;
            IsInitialized = false;
            mActivityRecognitionClient = null;
        } catch (IllegalStateException e) {
            bIsStop = true;
        }
    }

    public void onConnectionFailed(ConnectionResult result) throws  {
        stop(false);
    }

    public void onConnected(Bundle connectionHint) throws  {
        callbackIntent = PendingIntent.getService(getApplicationContext(), 0, new Intent(this, ActivityRecognitionService.class), 134217728);
        OfflineStats.SendStats(this, AnalyticsEvents.ANALYTICS_EVENT_ACTIVITY_RECOGNITION_ON, null);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mActivityRecognitionClient, 10000, callbackIntent);
    }

    public void onConnectionSuspended(int arg0) throws  {
    }

    public static String getActivityName(int $i0) throws  {
        switch ($i0) {
            case 0:
                return "IN_VEHICLE";
            case 1:
                return "ON_BICYCLE";
            case 2:
                return "ON_FOOT";
            case 3:
                return "STILL";
            case 4:
                return "UNKNOWN";
            case 5:
                return "TILTING";
            case 6:
                return "EXITING_VEHICLE";
            case 7:
                return "WALKING";
            case 8:
                return "RUNNING";
            case 9:
                return "OFF_BODY";
            case 10:
                return "TRUSTED_GAIT";
            case 11:
                return "FLOOR_CHANGE";
            case 12:
                return "ON_STAIRS";
            case 13:
                return "ON_ESCALATOR";
            case 14:
                return "IN_ELEVATOR";
            case 15:
                return "SLEEPING";
            default:
                return "??? - " + $i0;
        }
    }
}
