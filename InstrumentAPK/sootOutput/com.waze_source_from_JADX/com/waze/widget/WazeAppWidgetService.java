package com.waze.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;
import com.waze.AppService;
import com.waze.MainActivity;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.utils.OfflineStats;
import com.waze.widget.routing.RouteScore;
import com.waze.widget.routing.RoutingResponse;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;

public class WazeAppWidgetService extends Service {
    public static final String APPWIDGET_ACTION_CMD_DRIVE = "AppWidget Action Command Drive";
    public static final String APPWIDGET_ACTION_CMD_ENABLE = "AppWidget Action Command Enable";
    public static final String APPWIDGET_ACTION_CMD_FORCE_REFRESH = "AppWidget Action Command Force Refresh";
    public static final String APPWIDGET_ACTION_CMD_GRAPH = "AppWidget Action Command Graph";
    public static final String APPWIDGET_ACTION_CMD_NODATA = "AppWidget Action Command No Data";
    public static final String APPWIDGET_ACTION_CMD_NONE = "AppWidget Action Command None";
    public static final String APPWIDGET_ACTION_CMD_REFRESH = "AppWidget Action Command Refresh";
    public static final String APPWIDGET_ACTION_CMD_REFRESH_INFO = "AppWidget Action Command Refresh Info";
    public static final String APPWIDGET_ACTION_CMD_REFRESH_TEST = "AppWidget Action Command Refresh Test";
    public static final String APPWIDGET_ACTION_CMD_UPDATE = "AppWidget Action Command Update";
    public static final String APPWIDGET_ACTION_UPDATE = "AppWidget Action Update";
    private static final String INITIAL_DESTINATION = "Home";
    private static final String PREFS_DB = "WAZE WIDGET PREFS";
    private static final long REFRESHING_TIMEOUT = 30000;
    public static final int STATE_CURRENT_DATA_NEED_REFRESH = 3;
    public static final int STATE_CURRENT_DATA_UPTODATE = 2;
    public static final int STATE_NONE = 0;
    public static final int STATE_NO_DATA = 1;
    public static final int STATE_REFRESHING = 4;
    public static final int STATE_REFRESHING_INFO = 5;
    public static Context mApplicationContext = null;
    private static WazeAppWidgetService mInstance = null;
    private static Timer mRefreshMonitor = null;
    private static Timer mSDCardChecker = null;
    private static final StatusData mStatusData = new StatusData();
    private static volatile int mWidgetState = 0;

    public void onCreate() {
        WazeAppWidgetLog.m45d("Widget service instance is created: " + this);
        super.onCreate();
        mInstance = this;
        mApplicationContext = getApplicationContext();
        loadState();
    }

    public void onStart(Intent aIntent, int aStartId) {
        super.onStart(aIntent, aStartId);
        if (aIntent != null) {
            WazeAppWidgetLog.m45d("Widget service instance is started. Intent: " + aIntent.getAction());
            String command = aIntent.getAction();
            SystemClock.sleep(100);
            WidgetManager.init(this);
            if (command.equals(APPWIDGET_ACTION_CMD_ENABLE)) {
                enableCmdHandler();
            }
            if (command.equals(APPWIDGET_ACTION_CMD_UPDATE)) {
                updateCmdHandler();
            }
            if (command.equals(APPWIDGET_ACTION_CMD_REFRESH)) {
                OfflineStats.SendStats(this, AnalyticsEvents.ANALYTICS_EVENT_WIDGET_CLICK, new String[]{"ACTION", "REFRESH"});
                refreshCmdHandler(false);
            }
            if (command.equals(APPWIDGET_ACTION_CMD_REFRESH_INFO)) {
                OfflineStats.SendStats(this, AnalyticsEvents.ANALYTICS_EVENT_WIDGET_CLICK, new String[]{"ACTION", "REFRESH_INFO"});
                WazeAppWidgetLog.m45d("APPWIDGET_ACTION_CMD_REFRESH_INFO command");
                refreshCmdHandler(true);
            }
            if (command.equals(APPWIDGET_ACTION_CMD_NODATA)) {
                if (WidgetManager.hasHomeOrWork().booleanValue()) {
                    refreshCmdHandler(true);
                } else {
                    noDataCmdHandler();
                }
            }
            if (command.equals(APPWIDGET_ACTION_CMD_REFRESH_TEST)) {
                refreshTestCmdHandler();
            }
            if (command.equals(APPWIDGET_ACTION_CMD_DRIVE)) {
                OfflineStats.SendStats(this, AnalyticsEvents.ANALYTICS_EVENT_WIDGET_CLICK, new String[]{"ACTION", "DRIVE"});
                driveCmdHandler();
            }
            if (command.equals(APPWIDGET_ACTION_CMD_GRAPH)) {
                OfflineStats.SendStats(this, AnalyticsEvents.ANALYTICS_EVENT_WIDGET_CLICK, new String[]{"ACTION", "GRAPH"});
                graphCmdHandler();
            }
            if (command.equals(APPWIDGET_ACTION_CMD_NONE)) {
            }
            if (command.equals(APPWIDGET_ACTION_CMD_FORCE_REFRESH)) {
                forceRefreshCmdHandler();
            }
        }
    }

    public void onDestroy() {
        WazeAppWidgetLog.m45d("Widget service instance is destroyed: " + this);
        super.onDestroy();
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public static void setState(int aStatusCode, StatusData aStatusData) {
        stateHandler(aStatusCode, aStatusData);
    }

    public static void alertUser(final String str) {
        if (str != null && str.length() > 0) {
            ActivityBase activeActivity = AppService.getActiveActivity();
            if (activeActivity != null) {
                activeActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(WazeAppWidgetService.mInstance.getApplicationContext(), makePrintFriendly(str), 1).show();
                    }

                    private String makePrintFriendly(String str) {
                        String formattedStr = str.replace("{", "").replace("}", "").replace("\"", "");
                        int indexOfPipe = formattedStr.indexOf("|");
                        if (indexOfPipe <= -1 || indexOfPipe >= str.length() - 1) {
                            return formattedStr;
                        }
                        return formattedStr.substring(indexOfPipe + 1);
                    }
                });
            }
        }
    }

    public static void destroy() {
        if (mInstance != null) {
            mInstance.saveState();
            Context context = mInstance.getApplicationContext();
            context.stopService(new Intent(context, WazeAppWidgetService.class));
        }
    }

    public static void requestRefresh(Context aContext) {
        Intent requestRefreshIntent = new Intent(aContext, WazeAppWidgetService.class);
        requestRefreshIntent.setAction(APPWIDGET_ACTION_CMD_REFRESH);
        aContext.startService(requestRefreshIntent);
    }

    private static void startRefresh() {
        if (mRefreshMonitor != null) {
            WazeAppWidgetLog.m45d("startRefresh - Refresh timer is active - cancelling");
            mRefreshMonitor.cancel();
        }
        mRefreshMonitor = new Timer();
        WazeAppWidgetLog.m45d("startRefresh timer");
        mRefreshMonitor.schedule(new TimerTask() {
            public void run() {
                if (WazeAppWidgetService.mInstance != null) {
                    WazeAppWidgetService.mInstance.onRefreshingTimeout();
                }
                if (WazeAppWidgetService.mRefreshMonitor != null) {
                    WazeAppWidgetService.mRefreshMonitor.cancel();
                    WazeAppWidgetService.mRefreshMonitor = null;
                }
            }
        }, REFRESHING_TIMEOUT);
        WazeAppWidgetProvider.setRefreshingState(mInstance.getApplicationContext());
        WazeAppWidgetManager.refreshHandler(mInstance.getApplicationContext());
    }

    public static void stopRefreshMonitor() {
        if (mRefreshMonitor != null) {
            WazeAppWidgetLog.m45d("stopRefreshMonitor - Refresh timer is active - cancelling");
            mRefreshMonitor.cancel();
            mRefreshMonitor = null;
        }
    }

    private void onRefreshingTimeout() {
        WazeAppWidgetLog.m45d("Refresh timeout. Reset state.");
        setState(2097152, null);
    }

    private void enableCmdHandler() {
        WazeAppWidgetLog.m45d("enable command handler");
        final File file = new File(AppService.getExternalStoragePath(mApplicationContext));
        if (file.exists() && file.canWrite()) {
            WazeAppWidgetLog.m45d("SD Card is available. Setting state to the STATUS_NEW_WIDGET.");
            setState(8, null);
        } else if (mSDCardChecker == null) {
            mSDCardChecker = new Timer();
            mSDCardChecker.schedule(new TimerTask() {
                public void run() {
                    if (file.exists() && file.canWrite()) {
                        WazeAppWidgetLog.m45d("SD Card is available. Setting state to the STATUS_NEW_WIDGET. Cancelling the timer.");
                        WazeAppWidgetService.setState(8, null);
                        WazeAppWidgetService.mSDCardChecker.cancel();
                        WidgetManager.loadWazeConfig();
                        WazeAppWidgetService.mWidgetState = 0;
                        WazeAppWidgetService.mStatusData.copy(new StatusData());
                        WazeAppWidgetService.mSDCardChecker = null;
                        return;
                    }
                    WazeAppWidgetLog.m47w("SD Card is not available. Scheduling next check in 30 seconds");
                    WazeAppWidgetService.mSDCardChecker.schedule(this, WazeAppWidgetService.REFRESHING_TIMEOUT);
                }
            }, REFRESHING_TIMEOUT);
        }
    }

    private void updateCmdHandler() {
        WazeAppWidgetProvider.setNoDataState(getApplicationContext(), INITIAL_DESTINATION);
        WazeAppWidgetLog.m45d("Update command handler");
        final File file = new File(AppService.getExternalStoragePath(mApplicationContext));
        if (file.exists() && file.canWrite()) {
            WazeAppWidgetLog.m45d("SD Card is available. Setting state to the STATUS_NEW_WIDGET.");
            setState(8, null);
        } else if (mSDCardChecker == null) {
            mSDCardChecker = new Timer();
            mSDCardChecker.schedule(new TimerTask() {
                public void run() {
                    if (file.exists() && file.canWrite()) {
                        WazeAppWidgetLog.m45d("SD Card is available. Setting state to the STATUS_NEW_WIDGET. Cancelling the timer.");
                        WazeAppWidgetService.setState(8, null);
                        WazeAppWidgetService.mSDCardChecker.cancel();
                        WidgetManager.loadWazeConfig();
                        WazeAppWidgetService.mSDCardChecker = null;
                        return;
                    }
                    WazeAppWidgetLog.m47w("SD Card is not available. Scheduling next check in 30 seconds");
                    WazeAppWidgetService.mSDCardChecker.schedule(this, WazeAppWidgetService.REFRESHING_TIMEOUT);
                }
            }, REFRESHING_TIMEOUT);
        }
    }

    private void refreshCmdHandler(boolean aShowNoDataInfo) {
        WazeAppWidgetLog.m45d("Refresh command handler");
        if (aShowNoDataInfo) {
            setState(64, null);
        } else {
            setState(32, null);
        }
    }

    private void refreshTestCmdHandler() {
        WazeAppWidgetLog.m45d("RefreshTest command handler");
        if (isDataExpired()) {
            setState(16, null);
        }
    }

    private void graphCmdHandler() {
        WazeAppWidgetLog.m45d("Graph command handler");
        if (mWidgetState == 2 || mWidgetState == 3) {
            Intent intent = new WazeAppWidgetChart().execute(this, mStatusData.getRoutingRespnse(), mStatusData.timeStamp());
            intent.setFlags(1342177280);
            getApplicationContext().startActivity(intent);
            return;
        }
        WazeAppWidgetLog.m45d("Graph command handler called but state is =" + mWidgetState);
    }

    private void forceRefreshCmdHandler() {
        WazeAppWidgetLog.m45d("force refresh command handler");
        mWidgetState = 3;
        setState(32, null);
    }

    private void noDataCmdHandler() {
        WazeAppWidgetLog.m45d("NoData command handler");
        Intent intent = new Intent(getApplicationContext(), WazeAppWidgetNoDataActivity.class);
        intent.setFlags(402653184);
        getApplicationContext().startActivity(intent);
    }

    private void driveCmdHandler() {
        WazeAppWidgetLog.m45d("Drive command handler");
        if (mWidgetState == 2 || mWidgetState == 3) {
            WazeAppWidgetLog.m45d("Starting waze waze://?favorite=" + mStatusData.destination());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Uri uri = Uri.parse("waze://?favorite=" + mStatusData.destination());
            intent.setAction("android.intent.action.VIEW");
            intent.setData(uri);
            intent.setFlags(402653184);
            WazeAppWidgetLog.m45d("driveCmdHandler - starting waze");
            getApplicationContext().startActivity(intent);
            return;
        }
        WazeAppWidgetLog.m45d("driveCmdHandler invalid state" + mWidgetState);
    }

    private static void stateHandler(int aStatusCode, StatusData aStatusData) {
        boolean isOldData = true;
        if (aStatusData != null) {
            mStatusData.copy(aStatusData);
        }
        switch (mWidgetState) {
            case 0:
                printDbg("STATE_NONE: ", aStatusCode);
                if (aStatusCode == 8) {
                    mWidgetState = 4;
                    startRefresh();
                    return;
                }
                mWidgetState = 4;
                startRefresh();
                return;
            case 1:
                printDbg("STATE_NO_DATA: ", aStatusCode);
                if (aStatusCode == 32) {
                    mWidgetState = 4;
                    startRefresh();
                }
                if (aStatusCode == 64) {
                    mWidgetState = 5;
                    startRefresh();
                    return;
                }
                return;
            case 2:
                printDbg("STATE_CURRENT_DATA_UPTODATE: ", aStatusCode);
                if (aStatusCode == 8) {
                    WazeAppWidgetProvider.setUptodateState(mInstance.getApplicationContext(), mStatusData.destination(), mStatusData.timeToDest(), mStatusData.score());
                }
                if (aStatusCode == 16) {
                    mWidgetState = 3;
                    if (new Date().getTime() - mStatusData.timeStamp() <= 7200000) {
                        isOldData = false;
                    }
                    WazeAppWidgetProvider.setNeedRefreshState(mInstance.getApplicationContext(), mStatusData.destination(), mStatusData.timeToDest(), isOldData);
                    return;
                }
                return;
            case 3:
                printDbg("STATE_CURRENT_DATA_NEED_REFRESH: ", aStatusCode);
                if (aStatusCode == 32 || aStatusCode == 64) {
                    mWidgetState = 4;
                    startRefresh();
                    return;
                }
                return;
            case 4:
            case 5:
                printDbg("STATE_REFRESHING: ", aStatusCode);
                if (aStatusCode == 1) {
                    mWidgetState = 2;
                    WazeAppWidgetProvider.setUptodateState(mInstance.getApplicationContext(), mStatusData.destination(), mStatusData.timeToDest(), mStatusData.score());
                    if (mInstance != null) {
                        mInstance.saveState();
                        return;
                    }
                    return;
                } else if (aStatusCode == 2097152 || aStatusCode == 1048576 || aStatusCode == 262144) {
                    mWidgetState = 1;
                    WazeAppWidgetProvider.setNoDataState(mInstance.getApplicationContext(), "No Data");
                    return;
                } else if (testStatus(aStatusCode, 524288) || testStatus(aStatusCode, 131072)) {
                    if (mWidgetState == 5) {
                        MainActivity mainActivity = AppService.getMainActivity();
                        if (mainActivity == null || !mainActivity.IsRunning()) {
                            startNoDataActivity();
                        }
                    }
                    mWidgetState = 1;
                    WazeAppWidgetProvider.setNoDataState(mInstance.getApplicationContext(), mStatusData.destination());
                    return;
                } else {
                    if (aStatusCode == 64) {
                        mWidgetState = 5;
                        startRefresh();
                    }
                    WazeAppWidgetLog.m46e("Illegal status for STATE_REFRESHING: " + aStatusCode);
                    return;
                }
            default:
                return;
        }
    }

    private void saveState() {
        WazeAppWidgetLog.m45d("saveState ");
        Editor editor = getApplicationContext().getSharedPreferences(PREFS_DB, 0).edit();
        editor.putInt("State", mWidgetState);
        editor.putString("Destination", mStatusData.destination());
        editor.putInt("TimeToDestination", mStatusData.timeToDest());
        editor.putLong("TimeStamp", mStatusData.timeStamp());
        if (mStatusData.getRoutingRespnse() != null) {
            editor.putString("TimesArray", mStatusData.getRoutingRespnse().toString());
            WazeAppWidgetLog.m45d("Saving last Routing Reposne: " + mStatusData.getRoutingRespnse().toString());
            WazeAppWidgetLog.m45d("Last saved state: " + mWidgetState + " timestamp= " + mStatusData.timeStamp());
        }
        editor.commit();
    }

    private void loadState() {
        WazeAppWidgetLog.m45d("loadState ");
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(PREFS_DB, 0);
        mWidgetState = prefs.getInt("State", mWidgetState);
        String destination = prefs.getString("Destination", mStatusData.destination());
        int timeToDest = prefs.getInt("TimeToDestination", mStatusData.timeToDest());
        long timeStamp = prefs.getLong("TimeStamp", mStatusData.timeStamp());
        String routingResultStr = prefs.getString("TimesArray", "");
        WazeAppWidgetLog.m45d("destination: " + destination + " timeToDest: " + timeToDest);
        if (routingResultStr == null || routingResultStr.length() <= 0) {
            mStatusData.copy(new StatusData(destination, timeToDest, timeStamp));
        } else {
            try {
                RoutingResponse rrsp = new RoutingResponse(routingResultStr);
                mStatusData.copy(new StatusData(destination, timeToDest, RouteScore.getScore(timeToDest, rrsp.getAveragetTime()), rrsp));
                mStatusData.setTimeStamp(timeStamp);
                WazeAppWidgetLog.m45d("Last loaded Routing Reposne: " + rrsp.toString() + " timestamp= " + timeStamp);
            } catch (JSONException e) {
            }
        }
        if (mWidgetState != 2) {
            WazeAppWidgetLog.m45d("Last loaded state was: " + mWidgetState);
            mWidgetState = 2;
            setState(16, null);
        } else if (isDataExpired()) {
            WazeAppWidgetLog.m45d("loaded state: data is expired");
            setState(16, null);
        } else {
            WazeAppWidgetLog.m45d("loaded state: data is not expired");
        }
        WazeAppWidgetLog.m45d("Last loaded state: " + mWidgetState);
    }

    private static void startNoDataActivity() {
        WazeAppWidgetLog.m45d("Request to show NO DATA Activity");
        Context ctx = mInstance.getApplicationContext();
        Intent intent = new Intent(ctx, WazeAppWidgetNoDataActivity.class);
        intent.setFlags(402653184);
        ((AlarmManager) ctx.getSystemService("alarm")).set(3, SystemClock.elapsedRealtime() + 300, PendingIntent.getActivity(ctx, 0, intent, 1073741824));
    }

    private static boolean isDataExpired() {
        return System.currentTimeMillis() - mStatusData.timeStamp() > WazeAppWidgetPreferences.AllowRefreshTimer();
    }

    private static void printDbg(String aStr, int aStatus) {
        WazeAppWidgetLog.m45d("DEBUG PRINT. " + aStr + "(" + mWidgetState + "). " + "Status: " + Integer.toString(aStatus, 16) + ". Current status data: ( " + mStatusData.destination() + " , " + mStatusData.timeToDest() + ", " + mStatusData.score().name() + ")");
    }

    private static boolean testStatus(int aLhs, int aRhs) {
        return (aLhs & aRhs) > 0;
    }
}
