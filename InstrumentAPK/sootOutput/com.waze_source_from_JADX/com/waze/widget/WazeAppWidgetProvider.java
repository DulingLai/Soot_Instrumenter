package com.waze.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.SystemClock;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import com.facebook.appevents.AppEventsConstants;
import com.waze.C1283R;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.WazeLang;
import com.waze.utils.OfflineStats;
import com.waze.widget.routing.RouteScoreType;

public class WazeAppWidgetProvider extends AppWidgetProvider {
    public static Context _CONTEXT = null;
    private static boolean mIsEnabled;

    public void onUpdate(Context aContext, AppWidgetManager aAppWidgetManager, int[] aAppWidgetIds) {
        super.onUpdate(aContext, aAppWidgetManager, aAppWidgetIds);
        Intent updateIntent = new Intent(aContext, WazeAppWidgetService.class);
        updateIntent.setAction(WazeAppWidgetService.APPWIDGET_ACTION_CMD_UPDATE);
        aContext.startService(updateIntent);
    }

    public void onEnabled(Context aContext) {
        super.onEnabled(aContext);
        WazeAppWidgetLog.m45d("ON ENABLE");
        OfflineStats.SendStats(aContext, AnalyticsEvents.ANALYTICS_EVENT_WIDGET_INSTALL, null);
        Intent enableIntent = new Intent(aContext, WazeAppWidgetService.class);
        enableIntent.setAction(WazeAppWidgetService.APPWIDGET_ACTION_CMD_ENABLE);
        aContext.startService(enableIntent);
        setAlarm(aContext, false);
        setIsWidgetEnabled(true);
    }

    public void onDisabled(Context aContext) {
        super.onDisabled(aContext);
        WazeAppWidgetLog.m45d("ON DISABLE");
        aContext.stopService(new Intent(aContext, WazeAppWidgetService.class));
        setAlarm(aContext, true);
        setIsWidgetEnabled(false);
    }

    private static void setAlarm(Context aContext, boolean aCancel) {
        PendingIntent refreshTestIntent = getControlIntent(aContext, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH_TEST);
        AlarmManager alarms = (AlarmManager) aContext.getSystemService("alarm");
        if (aCancel) {
            WazeAppWidgetLog.m45d("Disable Alarm");
            alarms.cancel(refreshTestIntent);
            return;
        }
        WazeAppWidgetLog.m45d("Setting Alarm for " + (WazeAppWidgetPreferences.AllowRefreshTimer() / 1000) + " seconds");
        alarms.cancel(refreshTestIntent);
        alarms.setRepeating(3, SystemClock.elapsedRealtime(), WazeAppWidgetPreferences.AllowRefreshTimer(), refreshTestIntent);
    }

    public static PendingIntent getControlIntent(Context aContext, int aAppWidgetId, String aCommand) {
        Intent commandIntent = new Intent(aContext, WazeAppWidgetService.class);
        commandIntent.setAction(aCommand);
        commandIntent.putExtra("appWidgetId", aAppWidgetId);
        commandIntent.setData(Uri.withAppendedPath(Uri.parse("WazeAppWidget://widget/id/#" + aCommand + aAppWidgetId), String.valueOf(aAppWidgetId)));
        return PendingIntent.getService(aContext, 0, commandIntent, 134217728);
    }

    public static PendingIntent getControlIntent(Context aContext, String aCommand) {
        Intent commandIntent = new Intent(aContext, WazeAppWidgetService.class);
        commandIntent.setAction(aCommand);
        return PendingIntent.getService(aContext, 0, commandIntent, 134217728);
    }

    public static void setNeedRefreshState(Context aContext, String aDestDesc, int aTimeToDest, boolean OldData) {
        WazeAppWidgetLog.m45d("setNeedRefreshState isOld=" + OldData);
        RemoteViews views = new RemoteViews(aContext.getPackageName(), C1283R.layout.app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(aContext);
        Resources res = aContext.getResources();
        views.setViewVisibility(C1283R.id.widget_progress, 4);
        views.setViewVisibility(C1283R.id.image_status, 0);
        if (OldData) {
            views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_no_data);
        }
        views.setTextColor(C1283R.id.text_view_action, res.getColor(C1283R.color.solid_white));
        views.setTextViewText(C1283R.id.text_view_action, WazeLang.getLang("Refresh"));
        views.setImageViewResource(C1283R.id.image_action, C1283R.drawable.widget_bt_refresh_idle);
        views.setTextViewText(C1283R.id.text_view_destination, formatDestination(aContext, aDestDesc));
        views.setTextViewText(C1283R.id.text_view_time, formatTime(aContext, aTimeToDest));
        setRootIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH);
        setActionIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH);
        if (OldData) {
            setStatusIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH);
        } else {
            setStatusIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_GRAPH);
        }
        appWidgetManager.updateAppWidget(new ComponentName(aContext, WazeAppWidgetProvider.class), views);
        setAlarm(aContext, true);
    }

    public static void setRefreshingState(Context aContext) {
        WazeAppWidgetLog.m45d("setRefreshingState");
        RemoteViews views = new RemoteViews(aContext.getPackageName(), C1283R.layout.app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(aContext);
        Resources res = aContext.getResources();
        views.setViewVisibility(C1283R.id.widget_progress, 0);
        views.setViewVisibility(C1283R.id.image_status, 0);
        views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_refresh_bg1);
        views.setTextColor(C1283R.id.text_view_action, res.getColor(C1283R.color.disabled_white));
        views.setImageViewResource(C1283R.id.image_action, C1283R.drawable.widget_bt_drive_disabled);
        views.setTextViewText(C1283R.id.text_view_action, WazeLang.getLang("Drive") + "!");
        views.setTextViewText(C1283R.id.text_view_destination, WazeLang.getLang("Please wait..."));
        views.setTextViewText(C1283R.id.text_view_time, formatTime(aContext, 0));
        setAlarm(aContext, false);
        appWidgetManager.updateAppWidget(new ComponentName(aContext, WazeAppWidgetProvider.class), views);
    }

    public static void setUptodateState(Context aContext, String aDestDesc, int aTimeToDest, RouteScoreType score) {
        WazeAppWidgetLog.m45d("setUptodateState " + score.name());
        RemoteViews views = new RemoteViews(aContext.getPackageName(), C1283R.layout.app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(aContext);
        Resources res = aContext.getResources();
        views.setViewVisibility(C1283R.id.widget_progress, 4);
        views.setViewVisibility(C1283R.id.image_status, 0);
        switch (score) {
            case ROUTE_BAD:
                views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_red);
                break;
            case ROUTE_GOOD:
                views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_green);
                break;
            case ROUTE_OK:
                views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_orange);
                break;
            case NONE:
                views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_no_data);
                break;
        }
        views.setTextViewText(C1283R.id.text_view_action, WazeLang.getLang("Drive") + "!");
        views.setTextColor(C1283R.id.text_view_action, res.getColor(C1283R.color.solid_white));
        views.setImageViewResource(C1283R.id.image_action, C1283R.drawable.widget_bt_drive_idle);
        views.setTextViewText(C1283R.id.text_view_destination, formatDestination(aContext, aDestDesc));
        views.setTextViewText(C1283R.id.text_view_time, formatTime(aContext, aTimeToDest));
        setRootIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_NONE);
        setActionIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_DRIVE);
        setStatusIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_GRAPH);
        setAlarm(aContext, false);
        appWidgetManager.updateAppWidget(new ComponentName(aContext, WazeAppWidgetProvider.class), views);
    }

    public static void setNoDataState(Context aContext, String aDestDesc) {
        WazeAppWidgetLog.m45d("setNoDataState");
        RemoteViews views = new RemoteViews(aContext.getPackageName(), C1283R.layout.app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(aContext);
        Resources res = aContext.getResources();
        views.setViewVisibility(C1283R.id.widget_progress, 4);
        views.setViewVisibility(C1283R.id.image_status, 0);
        views.setImageViewResource(C1283R.id.image_status, C1283R.drawable.widget_icon_no_data);
        views.setTextColor(C1283R.id.text_view_action, res.getColor(C1283R.color.solid_white));
        views.setTextViewText(C1283R.id.text_view_action, WazeLang.getLang("Refresh"));
        views.setImageViewResource(C1283R.id.image_action, C1283R.drawable.widget_bt_refresh_idle);
        views.setTextViewText(C1283R.id.text_view_destination, "No Data");
        views.setTextViewText(C1283R.id.text_view_time, formatTime(aContext, 0));
        setRootIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH_INFO);
        setStatusIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH_INFO);
        setActionIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH_INFO);
        setAlarm(aContext, false);
        appWidgetManager.updateAppWidget(new ComponentName(aContext, WazeAppWidgetProvider.class), views);
    }

    private static CharSequence formatDestination(Context aContext, String aDestDesc) {
        return "@ " + WazeLang.getLang(aDestDesc) + " " + WazeLang.getLang("in:");
    }

    private static CharSequence formatTime(Context aContext, int aTimeToDest) {
        TextAppearanceSpan textAppearanceSpan;
        int spanEnd;
        int hours = aTimeToDest / 60;
        int mins = aTimeToDest - (hours * 60);
        String hoursString = "";
        String hoursValue = null;
        String hoursUnit = "h.";
        String hoursSep = " ";
        String sepString = "   ";
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder();
        if (hours != 0 || aTimeToDest == 0) {
            hoursValue = String.valueOf(hours);
            hoursString = hoursValue + hoursSep + hoursUnit + sepString;
        }
        String minsValue = String.valueOf(mins);
        if (aTimeToDest == 0) {
            minsValue = minsValue + AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
        String minsUnit = "min.";
        String minsSep = " ";
        spanBuilder.append(hoursString + (minsValue + minsSep + minsUnit));
        if (hoursString.length() > 0) {
            textAppearanceSpan = new TextAppearanceSpan(aContext, C1283R.style.TextAppearance_TimeValue);
            textAppearanceSpan = new TextAppearanceSpan(aContext, C1283R.style.TextAppearance_TimeUnit);
            spanEnd = hoursValue.length();
            spanBuilder.setSpan(textAppearanceSpan, 0, spanEnd, 17);
            spanBuilder.setSpan(textAppearanceSpan, spanEnd, hoursString.length(), 33);
        }
        textAppearanceSpan = new TextAppearanceSpan(aContext, C1283R.style.TextAppearance_TimeValue);
        textAppearanceSpan = new TextAppearanceSpan(aContext, C1283R.style.TextAppearance_TimeUnit);
        int spanStart = hoursString.length();
        spanEnd = spanStart + minsValue.length();
        spanBuilder.setSpan(textAppearanceSpan, spanStart, spanEnd, 33);
        spanStart = spanEnd;
        spanBuilder.setSpan(textAppearanceSpan, spanStart, (minsSep.length() + spanStart) + minsUnit.length(), 33);
        return spanBuilder;
    }

    static void updateCallbacks(Context aContext) {
        RemoteViews views = new RemoteViews(aContext.getPackageName(), C1283R.layout.app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(aContext);
        setRootIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_REFRESH);
        setActionIntent(aContext, views, WazeAppWidgetService.APPWIDGET_ACTION_CMD_DRIVE);
        appWidgetManager.updateAppWidget(new ComponentName(aContext, WazeAppWidgetProvider.class), views);
    }

    private static void setRootIntent(Context aContext, RemoteViews aViews, String aAction) {
        aViews.setOnClickPendingIntent(C1283R.id.app_widget_root, getControlIntent(aContext, aAction));
    }

    private static void setActionIntent(Context aContext, RemoteViews aViews, String aAction) {
        aViews.setOnClickPendingIntent(C1283R.id.layout_action, getControlIntent(aContext, aAction));
    }

    private static void setStatusIntent(Context aContext, RemoteViews aViews, String aAction) {
        aViews.setOnClickPendingIntent(C1283R.id.layout_status_image, getControlIntent(aContext, aAction));
    }

    public static boolean isWidgetEnabled(Context aContext) {
        AppWidgetManager manager = null;
        ComponentName widgetComponent = null;
        if (!mIsEnabled) {
            if (null == null || null == null) {
                widgetComponent = new ComponentName(aContext, WazeAppWidgetProvider.class);
                manager = AppWidgetManager.getInstance(aContext);
            }
            mIsEnabled = manager.getAppWidgetIds(widgetComponent) != null;
        }
        return mIsEnabled;
    }

    public static void setIsWidgetEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
    }
}
