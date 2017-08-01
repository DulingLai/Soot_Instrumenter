package com.waze;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Looper;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.Style;
import android.util.Log;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.location.LocationHistory;
import com.waze.location.LocationHistory.LocationHistoryOptInStatus;
import com.waze.messages.QuestionData;
import com.waze.push.Alerter;
import com.waze.push.WazeGcmListenerService;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageUtils;
import com.waze.utils.NotificationsActionsBuilder;
import com.waze.utils.OfflineStats;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class PushCommands {
    private static final long DELTA_WEEK = 604800000;
    private static String ENABLE_LOCATION_HISTORY_URI = "waze://?a=enable_location_history";

    public static class AlarmHandler extends BroadcastReceiver {
        public static final String ALARM_ACTION_NAME = "com.waze.broadcast.ALARM";

        public void onReceive(final Context $r1, Intent $r2) throws  {
            if (ALARM_ACTION_NAME.equals($r2.getAction())) {
                LocationHistory $r3 = new LocationHistory($r1);
                LocationHistory.checkLocationHistoryOptInStatus($r1, new LocationHistoryOptInStatus() {
                    public void onLocationHistoryOptInStatus(boolean $z0, boolean hasOptedOut, boolean $z2) throws  {
                        if ($z0 && !$z2) {
                            PushCommands.showLocationHistoryNotification($r1);
                        }
                    }
                });
            }
        }
    }

    private enum DayType {
        TODAY,
        TOMORROW,
        WEEKDAY
    }

    private enum TimeType {
        NIGHT,
        MORNING,
        DAY,
        AFTERNOON,
        EVENING
    }

    public static void ParseConstructionInstructionCommand(Context $r0, String $r1, String $r2, String $r3, String $r4, boolean $z0) throws  {
        if (NativeManager.IsAppStarted() && NativeManager.getInstance().isNavigatingNTV()) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TRAFFIC_PUSH_FILTERED, "CAUSE|VAUE", "NAVIGATING|TRUE");
            return;
        }
        boolean $z1 = false;
        Alerter.instance();
        Location $r6 = Alerter.getBestLocation($r0);
        if ($r6 != null) {
            int $i0 = $r6.getLongitude() * 1000000.0d;
            long j = $i0;
            int $i02 = (int) $i0;
            $i0 = $r6.getLatitude() * 1000000.0d;
            j = $i0;
            QuestionData $r8 = OfflineNativeManager.getInstance($r0).HandleCommandForPush($r1, $i02, (int) $i0, 0);
            if ($r8 != null) {
                CreatePush($r0, $r8, false, false, $r2, null, $r3, null, $r4, $z0);
                $z1 = true;
            }
        }
        if (!$z1) {
            $r1 = "LOCATION|" + $r2;
            Context context = $r0;
            OfflineStats.SendStats(context, AnalyticsEvents.ANALYTICS_EVENT_TRAFFIC_PUSH_FILTERED, new String[]{"CAUSE|VAUE", $r1});
        }
        if (!NativeManager.IsAppStarted()) {
            Logger.m36d("Stopping service WazeGcmListenerService");
            $r0.stopService(new Intent($r0, WazeGcmListenerService.class));
            System.exit(0);
        }
    }

    public static void ParseCarpoolCommand(Context $r0, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, boolean $z0) throws  {
        if (!NativeManager.IsAppStarted()) {
            QuestionData $r10 = OfflineNativeManager.getInstance($r0).HandleCommandForPush($r1, -1, -1, 1);
            if ($r10 != null) {
                CreatePush($r0, $r10, true, true, $r5, $r2, $r3, $r4, $r6, $z0);
            }
            if (!NativeManager.IsAppStarted()) {
                Log.d(Logger.TAG, "Stopping service WazeGcmListenerService");
                $r0.stopService(new Intent($r0, WazeGcmListenerService.class));
                System.exit(0);
            }
        } else if (CarpoolNativeManager.getInstance().hasCarpoolProfileNTV()) {
            final Context context = $r0;
            final String str = $r1;
            final String str2 = $r5;
            final String str3 = $r2;
            final String str4 = $r3;
            final String str5 = $r4;
            final String str6 = $r6;
            final boolean z = $z0;
            NativeManager.Post(new RunnableUICallback() {
                QuestionData question;

                public void event() throws  {
                    this.question = OfflineNativeManager.getInstance(context).HandleCommandForPush(str, -1, -1, 1);
                }

                public void callback() throws  {
                    if (this.question != null) {
                        Logger.m36d("PushCommands: ParseCarpoolCommand: refreshing ride list");
                        CarpoolNativeManager.getInstance().RefreshListOfRides();
                        PushCommands.CreatePush(AppService.getAppContext(), this.question, true, true, str2, str3, str4, str5, str6, z);
                    }
                }
            });
        }
    }

    public static void ParseInAppMsg(Context $r0, String $r1, String $r2, String $r3, String $r4, String $r5, boolean $z0) throws  {
        if (NativeManager.IsAppStarted()) {
            final Context context = $r0;
            final String str = $r1;
            final String str2 = $r2;
            NativeManager.Post(new RunnableUICallback() {
                QuestionData question;

                public void event() throws  {
                    this.question = OfflineNativeManager.getInstance(context).HandleParamsForPushMsgs(str);
                }

                public void callback() throws  {
                    if (this.question != null) {
                        Logger.m41i("PushCommands:ParseInAppMsg: Sending chat msg from ride id " + this.question.rideId + " msg=" + str2);
                        CarpoolNativeManager.getInstance().onRideMessagePushed(this.question.rideId, str2);
                    }
                }
            });
            return;
        }
        QuestionData $r8 = OfflineNativeManager.getInstance($r0).HandleParamsForPushMsgs($r1);
        $r8.Text = $r2;
        if ($r8 != null) {
            CreatePush($r0, $r8, true, true, "In-app messaging Push", $r2, $r3, $r4, $r5, $z0);
        }
        if (!NativeManager.IsAppStarted()) {
            Logger.m36d("Stopping service WazeGcmListenerService");
            $r0.stopService(new Intent($r0, WazeGcmListenerService.class));
            System.exit(0);
        }
    }

    public static void ParseMsgRead(final Context $r0, final String $r1, String msg, String title, String actionsJson, boolean showNotification) throws  {
        if (NativeManager.IsAppStarted()) {
            NativeManager.Post(new RunnableUICallback() {
                QuestionData question;

                public void event() throws  {
                    this.question = OfflineNativeManager.getInstance($r0).HandleParamsForMsgRead($r1);
                }

                public void callback() throws  {
                }
            });
        }
    }

    private static String centsToString(Context ctx, int $i0, String $r1) throws  {
        Locale $r3 = Locale.getDefault();
        Currency $r4 = null;
        if (!($r1 == null || $r1.isEmpty())) {
            try {
                Locale $r2 = new Locale("", $r1);
                try {
                    $r4 = Currency.getInstance($r2);
                    $r3 = $r2;
                } catch (Exception e) {
                    $r3 = $r2;
                }
            } catch (Exception e2) {
            }
        }
        if ($r4 != null) {
            return NumberFormat.getCurrencyInstance($r3).format(((double) $i0) / Math.pow(10.0d, (double) $r4.getDefaultFractionDigits()));
        }
        return String.format("%.2f", new Object[]{Double.valueOf(((double) $i0) / Math.pow(10.0d, 2.0d))});
    }

    public static void CreatePush(Context $r0, QuestionData $r1, boolean $z0, boolean $z1, String $r2, String $r3, String $r4, String $r5, String $r6, boolean $z2) throws  {
        String $r14;
        String $r7 = $r1.Text;
        final NotificationManager $r13 = (NotificationManager) $r0.getSystemService("notification");
        if ($r4 == null || $r4.isEmpty()) {
            $r4 = "Waze";
        }
        if ($z1) {
            $r14 = "TYPE|RIDE_ID|VAUE";
        } else {
            $r14 = "TYPE|ConstructionInstructionID|VAUE";
        }
        String $r16 = $r1.Text + "|" + $r1.NotificationID + "|" + $r2;
        String $r17 = $r16;
        $r7 = $r1.Text;
        if ($r1.MessageID != null) {
            $r14 = $r14 + "|MESSAGE_ID";
            StringBuilder $r15 = new StringBuilder().append($r16).append("|");
            String str = $r1.MessageID;
            $r17 = str;
            $r17 = $r15.append(str).toString();
        }
        if (AppService.IsAppRunning()) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PUSH_MESSAGE_WHILE_RUNNING, $r14, $r17);
            Logger.m43w("Received push while running (ANALYTICS_EVENT_PUSH_MESSAGE_WHILE_RUNNING): " + $r17);
            Log.w(Logger.TAG, "Received push while running (ANALYTICS_EVENT_PUSH_MESSAGE_WHILE_RUNNING): " + $r17);
        } else {
            OfflineStats.SendStats($r0, AnalyticsEvents.ANALYTICS_EVENT_OFFLINE_PUSH_RECEIVED, new String[]{$r14, $r17});
            Logger.m43w("Received push while offline (ANALYTICS_EVENT_OFFLINE_PUSH_RECEIVED): " + $r17);
            Log.w(Logger.TAG, "Received push while offline running (ANALYTICS_EVENT_PUSH_MESSAGE_WHILE_RUNNING): " + $r17);
        }
        if ($r1.Text.equals("RIDE_OFFER_CANCELED")) {
            $r13.cancel($r1.NotificationID, 2);
        } else if ($r1.Text.equals("ACTIVATE_LOCATION_HISTORY")) {
            HandleLocationHistoryActivation($r0);
        } else {
            if ($r3 != null) {
                if ($r1.AnswerType == 5) {
                    Logger.m43w("wazegcm: nullifying action strings for carpool");
                    $r1.ActionText1 = null;
                    $r1.ActionText2 = null;
                }
                $r1.Text = $r3;
            }
            Intent intent;
            if (VERSION.SDK_INT >= 16) {
                Builder builder = new Builder($r0);
                Style bigTextStyle = new BigTextStyle();
                bigTextStyle.setBigContentTitle($r4);
                bigTextStyle.bigText($r1.Text);
                builder.setStyle(bigTextStyle);
                intent = new Intent($r0, FreeMapAppActivity.class);
                intent.setAction("android.intent.action.MAIN");
                intent.putExtra("PushClicked", $r2);
                intent.putExtra("AnalyticsType", $r7);
                if ($r1.defaultAction != null) {
                    str = $r1.defaultAction;
                    if (!str.isEmpty()) {
                        str = $r1.defaultAction;
                        intent.setData(Uri.parse(str));
                    }
                }
                builder.setContentIntent(PendingIntent.getActivity($r0, 0, intent, 268435456));
                builder.setSmallIcon(C1283R.drawable.notification);
                builder.setColor($r0.getResources().getColor(C1283R.color.notification_circle_bg));
                builder.setContentTitle($r4);
                Uri $r20 = Uri.parse("android.resource://" + $r0.getPackageName() + "/raw/riderequest");
                builder.setSound($r20);
                builder.setContentText($r1.Text);
                builder.setPriority(2);
                builder.setAutoCancel(true);
                Alerter.setVibrationMode(builder, $r0);
                if ($r1.ActionText1 != null) {
                    str = $r1.ActionText1;
                    if (!(str.isEmpty() || $r1.ActionText2 == null)) {
                        str = $r1.ActionText2;
                        if (!str.isEmpty()) {
                            intent = new Intent($r0, ActionIntent.class);
                            str = $r1.ActionText1;
                            Uri $r23 = Uri.parse(str);
                            intent.putExtra("QuestionID", $r1.QuestionID);
                            intent.putExtra("QuestionType", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ALERT);
                            intent.putExtra("Action", $r1.ActionText1);
                            intent.setData($r23);
                            PendingIntent $r21 = PendingIntent.getBroadcast($r0, 1, intent, 268435456);
                            intent = new Intent($r0, ActionIntent.class);
                            str = $r1.ActionText2;
                            intent.setData(Uri.parse(str));
                            intent.putExtra("QuestionID", $r1.QuestionID);
                            intent.putExtra("QuestionType", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ALERT);
                            intent.putExtra("Action", $r1.ActionText2);
                            builder.addAction(C1283R.drawable.x_notify_icon, $r1.SubText2, PendingIntent.getBroadcast($r0, 1, intent, 268435456));
                            builder.addAction(C1283R.drawable.v_notify_icon, $r1.SubText1, $r21);
                        }
                    }
                }
                NotificationsActionsBuilder.buildNotificationActions($r0, $r1, builder, $r6, $r2);
                final Builder builder2 = builder;
                final Uri uri = $r20;
                final QuestionData questionData = $r1;
                C12784 c12784 = new Runnable() {
                    public void run() throws  {
                        Notification $r2 = builder2.build();
                        $r2.flags |= 17;
                        $r2.when = System.currentTimeMillis();
                        $r2.ledARGB = -16711681;
                        $r2.ledOnMS = 15000;
                        $r2.ledOffMS = 15000;
                        $r2.sound = uri;
                        $r2.defaults = 6;
                        $r13.notify(questionData.NotificationID, 2, $r2);
                    }
                };
                if ($r5 != null && !$r5.isEmpty()) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        final String str2 = $r5;
                        final Builder builder3 = builder;
                        final boolean z = $z2;
                        final C12784 c127842 = c12784;
                        new Thread(new Runnable() {
                            public void run() throws  {
                                Bitmap $r2 = ImageUtils.makeBitmap(250, 262144, str2, null, null);
                                if ($r2 != null) {
                                    builder3.setLargeIcon(ImageUtils.getCircleBitmap($r2));
                                }
                                if (z) {
                                    c127842.run();
                                }
                            }
                        }).start();
                        return;
                    }
                    Bitmap $r29 = ImageUtils.makeBitmap(250, 262144, $r5, null, null);
                    if ($r29 != null) {
                        builder.setLargeIcon(ImageUtils.getCircleBitmap($r29));
                    }
                    if ($z2) {
                        c12784.run();
                        return;
                    }
                    return;
                } else if ($z2) {
                    c12784.run();
                    return;
                } else {
                    return;
                }
            }
            intent = new Intent($r0, FreeMapAppActivity.class);
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            Notification $r30 = createNotification($r0, $z0, $r4, $r1, PendingIntent.getActivity($r0, 0, intent, 268435456), $r6, $r2);
            $r30.flags |= 16;
            if ($z2) {
                $r13.notify($r1.NotificationID, 2, $r30);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getPushByParams(boolean r3, boolean r4, com.waze.PushCommands.DayType r5, com.waze.PushCommands.TimeType r6) throws  {
        /*
        if (r3 == 0) goto L_0x0063;
    L_0x0002:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$DayType;
        r1 = r5.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x0011;
            case 2: goto L_0x001d;
            case 3: goto L_0x0029;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x000e;
    L_0x000e:
        r2 = 2258; // 0x8d2 float:3.164E-42 double:1.1156E-320;
        return r2;
    L_0x0011:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x0039;
            case 2: goto L_0x003c;
            case 3: goto L_0x003f;
            case 4: goto L_0x0042;
            case 5: goto L_0x0045;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x001d;
    L_0x001d:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x0048;
            case 2: goto L_0x004b;
            case 3: goto L_0x004e;
            case 4: goto L_0x0051;
            case 5: goto L_0x0054;
            default: goto L_0x0028;
        };
    L_0x0028:
        goto L_0x0029;
    L_0x0029:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x0036;
            case 2: goto L_0x0057;
            case 3: goto L_0x005a;
            case 4: goto L_0x005d;
            case 5: goto L_0x0060;
            default: goto L_0x0034;
        };
    L_0x0034:
        goto L_0x0035;
    L_0x0035:
        goto L_0x000e;
    L_0x0036:
        r2 = 2307; // 0x903 float:3.233E-42 double:1.14E-320;
        return r2;
    L_0x0039:
        r2 = 2297; // 0x8f9 float:3.219E-42 double:1.135E-320;
        return r2;
    L_0x003c:
        r2 = 2298; // 0x8fa float:3.22E-42 double:1.1354E-320;
        return r2;
    L_0x003f:
        r2 = 2299; // 0x8fb float:3.222E-42 double:1.136E-320;
        return r2;
    L_0x0042:
        r2 = 2300; // 0x8fc float:3.223E-42 double:1.1364E-320;
        return r2;
    L_0x0045:
        r2 = 2301; // 0x8fd float:3.224E-42 double:1.137E-320;
        return r2;
    L_0x0048:
        r2 = 2302; // 0x8fe float:3.226E-42 double:1.1373E-320;
        return r2;
    L_0x004b:
        r2 = 2303; // 0x8ff float:3.227E-42 double:1.138E-320;
        return r2;
    L_0x004e:
        r2 = 2304; // 0x900 float:3.229E-42 double:1.1383E-320;
        return r2;
    L_0x0051:
        r2 = 2305; // 0x901 float:3.23E-42 double:1.139E-320;
        return r2;
    L_0x0054:
        r2 = 2306; // 0x902 float:3.231E-42 double:1.1393E-320;
        return r2;
    L_0x0057:
        r2 = 2308; // 0x904 float:3.234E-42 double:1.1403E-320;
        return r2;
    L_0x005a:
        r2 = 2309; // 0x905 float:3.236E-42 double:1.141E-320;
        return r2;
    L_0x005d:
        r2 = 2310; // 0x906 float:3.237E-42 double:1.1413E-320;
        return r2;
    L_0x0060:
        r2 = 2311; // 0x907 float:3.238E-42 double:1.142E-320;
        return r2;
    L_0x0063:
        if (r4 == 0) goto L_0x00c6;
    L_0x0065:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$DayType;
        r1 = r5.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x0074;
            case 2: goto L_0x0080;
            case 3: goto L_0x008c;
            default: goto L_0x0070;
        };
    L_0x0070:
        goto L_0x0071;
    L_0x0071:
        r2 = 2257; // 0x8d1 float:3.163E-42 double:1.115E-320;
        return r2;
    L_0x0074:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x009c;
            case 2: goto L_0x009f;
            case 3: goto L_0x00a2;
            case 4: goto L_0x00a5;
            case 5: goto L_0x00a8;
            default: goto L_0x007f;
        };
    L_0x007f:
        goto L_0x0080;
    L_0x0080:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x00ab;
            case 2: goto L_0x00ae;
            case 3: goto L_0x00b1;
            case 4: goto L_0x00b4;
            case 5: goto L_0x00b7;
            default: goto L_0x008b;
        };
    L_0x008b:
        goto L_0x008c;
    L_0x008c:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x0099;
            case 2: goto L_0x00ba;
            case 3: goto L_0x00bd;
            case 4: goto L_0x00c0;
            case 5: goto L_0x00c3;
            default: goto L_0x0097;
        };
    L_0x0097:
        goto L_0x0098;
    L_0x0098:
        goto L_0x0071;
    L_0x0099:
        r2 = 2292; // 0x8f4 float:3.212E-42 double:1.1324E-320;
        return r2;
    L_0x009c:
        r2 = 2282; // 0x8ea float:3.198E-42 double:1.1275E-320;
        return r2;
    L_0x009f:
        r2 = 2283; // 0x8eb float:3.199E-42 double:1.128E-320;
        return r2;
    L_0x00a2:
        r2 = 2284; // 0x8ec float:3.2E-42 double:1.1284E-320;
        return r2;
    L_0x00a5:
        r2 = 2285; // 0x8ed float:3.202E-42 double:1.129E-320;
        return r2;
    L_0x00a8:
        r2 = 2286; // 0x8ee float:3.203E-42 double:1.1294E-320;
        return r2;
    L_0x00ab:
        r2 = 2287; // 0x8ef float:3.205E-42 double:1.13E-320;
        return r2;
    L_0x00ae:
        r2 = 2288; // 0x8f0 float:3.206E-42 double:1.1304E-320;
        return r2;
    L_0x00b1:
        r2 = 2289; // 0x8f1 float:3.208E-42 double:1.131E-320;
        return r2;
    L_0x00b4:
        r2 = 2290; // 0x8f2 float:3.209E-42 double:1.1314E-320;
        return r2;
    L_0x00b7:
        r2 = 2291; // 0x8f3 float:3.21E-42 double:1.132E-320;
        return r2;
    L_0x00ba:
        r2 = 2293; // 0x8f5 float:3.213E-42 double:1.133E-320;
        return r2;
    L_0x00bd:
        r2 = 2294; // 0x8f6 float:3.215E-42 double:1.1334E-320;
        return r2;
    L_0x00c0:
        r2 = 2295; // 0x8f7 float:3.216E-42 double:1.134E-320;
        return r2;
    L_0x00c3:
        r2 = 2296; // 0x8f8 float:3.217E-42 double:1.1344E-320;
        return r2;
    L_0x00c6:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$DayType;
        r1 = r5.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x00d5;
            case 2: goto L_0x00e1;
            case 3: goto L_0x00ed;
            default: goto L_0x00d1;
        };
    L_0x00d1:
        goto L_0x00d2;
    L_0x00d2:
        r2 = 2256; // 0x8d0 float:3.161E-42 double:1.1146E-320;
        return r2;
    L_0x00d5:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x00fd;
            case 2: goto L_0x0100;
            case 3: goto L_0x0103;
            case 4: goto L_0x0106;
            case 5: goto L_0x0109;
            default: goto L_0x00e0;
        };
    L_0x00e0:
        goto L_0x00e1;
    L_0x00e1:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x010c;
            case 2: goto L_0x010f;
            case 3: goto L_0x0112;
            case 4: goto L_0x0115;
            case 5: goto L_0x0118;
            default: goto L_0x00ec;
        };
    L_0x00ec:
        goto L_0x00ed;
    L_0x00ed:
        r0 = com.waze.PushCommands.C12817.$SwitchMap$com$waze$PushCommands$TimeType;
        r1 = r6.ordinal();
        r1 = r0[r1];
        switch(r1) {
            case 1: goto L_0x00fa;
            case 2: goto L_0x011b;
            case 3: goto L_0x011e;
            case 4: goto L_0x0121;
            case 5: goto L_0x0124;
            default: goto L_0x00f8;
        };
    L_0x00f8:
        goto L_0x00f9;
    L_0x00f9:
        goto L_0x00d2;
    L_0x00fa:
        r2 = 2277; // 0x8e5 float:3.191E-42 double:1.125E-320;
        return r2;
    L_0x00fd:
        r2 = 2267; // 0x8db float:3.177E-42 double:1.12E-320;
        return r2;
    L_0x0100:
        r2 = 2268; // 0x8dc float:3.178E-42 double:1.1205E-320;
        return r2;
    L_0x0103:
        r2 = 2269; // 0x8dd float:3.18E-42 double:1.121E-320;
        return r2;
    L_0x0106:
        r2 = 2270; // 0x8de float:3.181E-42 double:1.1215E-320;
        return r2;
    L_0x0109:
        r2 = 2271; // 0x8df float:3.182E-42 double:1.122E-320;
        return r2;
    L_0x010c:
        r2 = 2272; // 0x8e0 float:3.184E-42 double:1.1225E-320;
        return r2;
    L_0x010f:
        r2 = 2273; // 0x8e1 float:3.185E-42 double:1.123E-320;
        return r2;
    L_0x0112:
        r2 = 2274; // 0x8e2 float:3.187E-42 double:1.1235E-320;
        return r2;
    L_0x0115:
        r2 = 2275; // 0x8e3 float:3.188E-42 double:1.124E-320;
        return r2;
    L_0x0118:
        r2 = 2276; // 0x8e4 float:3.19E-42 double:1.1245E-320;
        return r2;
    L_0x011b:
        r2 = 2278; // 0x8e6 float:3.192E-42 double:1.1255E-320;
        return r2;
    L_0x011e:
        r2 = 2279; // 0x8e7 float:3.194E-42 double:1.126E-320;
        return r2;
    L_0x0121:
        r2 = 2280; // 0x8e8 float:3.195E-42 double:1.1265E-320;
        return r2;
    L_0x0124:
        r2 = 2281; // 0x8e9 float:3.196E-42 double:1.127E-320;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.PushCommands.getPushByParams(boolean, boolean, com.waze.PushCommands$DayType, com.waze.PushCommands$TimeType):int");
    }

    private static Notification createNotification(Context $r0, boolean message, String $r1, QuestionData $r2, PendingIntent $r3, String $r4, String $r5) throws  {
        Builder $r6 = new Builder($r0);
        $r6.setSmallIcon(C1283R.drawable.notification).setColor($r0.getResources().getColor(C1283R.color.notification_circle_bg)).setWhen(System.currentTimeMillis()).setLights(-16711681, 15000, 15000).setContentTitle($r1).setContentText($r2.Text).setContentIntent($r3);
        NotificationsActionsBuilder.buildNotificationActions($r0, $r2, $r6, $r4, $r5);
        Notification $r9 = $r6.build();
        $r9.flags |= 16;
        $r9.flags |= 1;
        Alerter.setVibrationMode($r9, $r0);
        return $r9;
    }

    private static void HandleLocationHistoryActivation(final Context $r0) throws  {
        LocationHistory $r1 = new LocationHistory($r0);
        LocationHistory.checkLocationHistoryOptInStatus($r0, new LocationHistoryOptInStatus() {
            public void onLocationHistoryOptInStatus(boolean $z0, boolean $z1, boolean $z2) throws  {
                if (!$z0) {
                    PendingIntent $r3 = PendingIntent.getBroadcast($r0, 0, new Intent(AlarmHandler.ALARM_ACTION_NAME), 0);
                    Calendar $r4 = Calendar.getInstance();
                    $r4.add(10, 24);
                    ((AlarmManager) $r0.getSystemService("alarm")).set(1, $r4.getTimeInMillis(), $r3);
                } else if (!$z1 && !$z2) {
                    PushCommands.showLocationHistoryNotification($r0);
                }
            }
        });
    }

    private static void showLocationHistoryNotification(Context $r0) throws  {
        String $r4 = OfflineNativeManager.getInstance($r0).getLanguageString((int) DisplayStrings.DS_PUSH_ENABLE_LOCATION_HISTORY_MESSAGE_SHORT);
        String $r5 = OfflineNativeManager.getInstance($r0).getLanguageString((int) DisplayStrings.DS_PUSH_ENABLE_LOCATION_HISTORY_MESSAGE);
        String $r6 = OfflineNativeManager.getInstance($r0).getLanguageString((int) DisplayStrings.DS_PUSH_ENABLE_LOCATION_HISTORY_TITLE);
        Intent $r2 = new Intent($r0, FreeMapAppActivity.class);
        $r2.putExtra("AnalyticsType", "LH");
        $r2.setData(Uri.parse(ENABLE_LOCATION_HISTORY_URI));
        PendingIntent $r9 = PendingIntent.getActivity($r0, 0, $r2, 134217728);
        Notification.Builder $r1 = new Notification.Builder($r0);
        $r1.setContentTitle($r6);
        $r1.setContentText($r4);
        $r1.setSmallIcon(C1283R.drawable.notification);
        $r1.setAutoCancel(true);
        $r1.setDefaults(7);
        if (VERSION.SDK_INT >= 16) {
            $r1.setStyle(new Notification.BigTextStyle().bigText($r5));
        }
        $r1.setContentIntent($r9);
        NotificationManager notificationManager = (NotificationManager) $r0.getSystemService("notification");
        notificationManager.notify("", 2, $r1.build());
    }
}
