package com.waze.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Looper;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.facebook.appevents.AppEventsConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.FreeMapAppActivity;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageUtils;
import com.waze.utils.NotificationsActionsBuilder;
import com.waze.utils.OfflineStats;

public final class Alerter {
    private static final int SIGNIFICANT_ACCURACY = 200;
    private static final long SIGNIFICANT_TIME = 300000;
    private static Alerter mInstance = null;
    private final Runnable mPickUpHandler = new C24072();

    class C24072 implements Runnable {
        C24072() {
        }

        public void run() {
            Alerter.instance().showPickUpMessage();
        }
    }

    class C24115 implements OnClickListener {
        C24115() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            final NativeManager nm = NativeManager.getInstance();
            if (which == 1) {
                NativeManager.Post(new Runnable() {
                    public void run() {
                        nm.UrlHandler("waze://?a=pickup");
                        nm.ClearNotifications();
                    }
                });
            } else {
                nm.SetPickUpLater(true);
            }
        }
    }

    public static Alerter instance() {
        if (mInstance == null) {
            mInstance = new Alerter();
        }
        return mInstance;
    }

    private Alerter() {
    }

    public void clear(NotificationManager mgr) {
        Logger.d_("Push Service", "Clearing current notifications");
        mgr.cancel("Push Notifications", 256);
    }

    protected static void onBadge(Context context, int badge) {
    }

    protected static void onAlert(Context context, String alert, String actionUrl, String alertType, String title, String image, String actionsJson, boolean showNotification) {
        Logger.i_("Push Service", "Got new alert: " + alert + ". Type: " + alertType);
        if (alert != null) {
            String analyticsInfoValue;
            Alerter alerter = instance();
            if (alertType == null) {
                analyticsInfoValue = "NONE";
            } else {
                analyticsInfoValue = alertType;
            }
            if (NativeManager.IsAppStarted()) {
                Logger.v_("Push Service", "Showing message for the alert: " + alert);
                if ((alertType.startsWith("RIDER_ARRIVED") || alertType.startsWith("DRIVER_INITIATED_RIDE_CONFIRM")) && showNotification) {
                    alerter.notify(context, alert, actionUrl, analyticsInfoValue, title, image, actionsJson);
                }
                if (actionUrl != null && !actionUrl.isEmpty()) {
                    if (actionUrl.startsWith("?a=pickup")) {
                        if (showNotification) {
                            alerter.notify(context, alert, actionUrl, analyticsInfoValue, title, image, actionsJson);
                        }
                        AppService.Post(alerter.mPickUpHandler);
                    } else {
                        alerter.postAction(actionUrl, alert);
                    }
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PUSH_MESSAGE_WHILE_RUNNING, "TYPE", analyticsInfoValue);
                    return;
                }
                return;
            }
            OfflineStats.SendStats(context, AnalyticsEvents.ANALYTICS_EVENT_OFFLINE_PUSH_RECEIVED, new String[]{AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, analyticsInfoValue});
            if (showNotification) {
                alerter.notify(context, alert, actionUrl, analyticsInfoValue, title, image, actionsJson);
            }
            NativeManager.registerOnAppStartedEvent(new RunnableExecutor() {
                public void event() {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PUSH_MESSAGE_RECEIVED, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, analyticsInfoValue);
                }
            });
        }
    }

    private void notify(final Context context, String alert, String actionUrl, String InfoValue, String title, String image, String actionsJson) {
        final Builder builder = new Builder(context);
        builder.setSmallIcon(C1283R.drawable.notification);
        builder.setColor(context.getResources().getColor(C1283R.color.notification_circle_bg));
        if (title == null || title.isEmpty()) {
            builder.setContentTitle("Waze");
        } else {
            builder.setContentTitle(title);
        }
        builder.setContentText(alert);
        builder.setAutoCancel(true);
        setVibrationMode(builder, context);
        if (VERSION.SDK_INT >= 16) {
            builder.setStyle(new BigTextStyle().bigText(alert));
        }
        Intent notificationIntent = new Intent(context, FreeMapAppActivity.class);
        notificationIntent.setAction("android.intent.action.MAIN");
        notificationIntent.addCategory("android.intent.category.LAUNCHER");
        if (actionUrl != null) {
            notificationIntent.setData(Uri.parse(NativeManager.WAZE_URL_PATTERN + actionUrl));
        }
        notificationIntent.putExtra("PushClicked", InfoValue);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, 268435456));
        NotificationsActionsBuilder.buildNotificationActions(context, null, builder, actionsJson, InfoValue);
        final Runnable buildAndShowRunnable = new Runnable() {
            public void run() {
                ((NotificationManager) context.getSystemService("notification")).notify("Push Notifications", 256, builder.build());
            }
        };
        if (image == null || image.isEmpty()) {
            buildAndShowRunnable.run();
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            final String finalPhotoUrl = image;
            new Thread(new Runnable() {
                public void run() {
                    Bitmap bitmap = ImageUtils.makeBitmap(250, 262144, finalPhotoUrl, null, null);
                    if (bitmap != null) {
                        builder.setLargeIcon(bitmap);
                    }
                    buildAndShowRunnable.run();
                }
            }).start();
            return;
        }
        Bitmap bitmap = ImageUtils.makeBitmap(250, 262144, image, null, null);
        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
        }
        buildAndShowRunnable.run();
    }

    public static void testAlert(Context ctx) {
        instance().notify(ctx, "test", "test", "test", "test-titile", null, null);
    }

    private void showPickUpMessage() {
        NativeManager nm = NativeManager.getInstance();
        String title = nm.getLanguageString(DisplayStrings.DS_POPUP_WHEN_RECEIVING_LOCATION_TITLE);
        String text = nm.getLanguageString(DisplayStrings.DS_POPUP_WHEN_RECEIVING_LOCATION_BODY);
        String textYes = nm.getLanguageString(378);
        String textNo = nm.getLanguageString(448);
        OnClickListener listener = new C24115();
        nm.SetPickUpLater(false);
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(title, text, true, listener, textYes, textNo, -1);
    }

    private void postAction(final String actionUrl, final String alert) {
        NativeManager.Post(new Runnable() {
            public void run() {
                String url = NativeManager.WAZE_URL_PATTERN;
                if (actionUrl != null) {
                    url = url + actionUrl + "&";
                }
                NativeManager.getInstance().UrlHandler(url + "popup_message=" + alert);
            }
        });
    }

    public static Location getBestLocation(Context context) {
        LocationManager locMgr = (LocationManager) context.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
        Location bestLocation = null;
        for (String provider : locMgr.getAllProviders()) {
            Location loc = locMgr.getLastKnownLocation(provider);
            if (isBetterLocation(loc, bestLocation)) {
                bestLocation = loc;
            }
        }
        return bestLocation;
    }

    protected static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }
        if (location == null) {
            return false;
        }
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > SIGNIFICANT_TIME;
        boolean isSignificantlyOlder = timeDelta < -300000;
        boolean isNewer = timeDelta > 0;
        if (isSignificantlyNewer) {
            return true;
        }
        if (isSignificantlyOlder) {
            return false;
        }
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        if (accuracyDelta > 0) {
        }
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        if (isMoreAccurate) {
            return true;
        }
        if (!isNewer || isSignificantlyLessAccurate) {
            return false;
        }
        return true;
    }

    public static void setVibrationMode(Builder builder, Context ctx) {
        int vibrationMode = Integer.parseInt(ctx.getSharedPreferences(SettingsNativeManager.SETTINGS_NOTIFICATION_CONFIG_NAME, 0).getString(SettingsNativeManager.VIBRATE_VALUE, AppEventsConstants.EVENT_PARAM_VALUE_NO));
        int defaults = 5;
        if (vibrationMode == 0) {
            defaults = 5 | 2;
        } else if (vibrationMode == 1) {
            builder.setVibrate(new long[]{500, 200, 200, 200});
        } else if (vibrationMode == -1) {
            builder.setVibrate(new long[0]);
        }
        builder.setDefaults(defaults);
    }

    public static void setVibrationMode(Notification notification, Context ctx) {
        int vibrationMode = Integer.parseInt(ctx.getSharedPreferences(SettingsNativeManager.SETTINGS_NOTIFICATION_CONFIG_NAME, 0).getString(SettingsNativeManager.VIBRATE_VALUE, AppEventsConstants.EVENT_PARAM_VALUE_NO));
        int defaults = 5;
        if (vibrationMode == 0) {
            defaults = 5 | 2;
        } else if (vibrationMode == 1) {
            notification.vibrate = new long[]{500, 200, 200, 200};
        } else if (vibrationMode == -1) {
            notification.vibrate = new long[0];
        }
        notification.defaults = defaults;
    }
}
