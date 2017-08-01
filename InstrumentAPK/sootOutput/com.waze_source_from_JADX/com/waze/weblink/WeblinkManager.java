package com.waze.weblink;

import android.app.Application;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import com.abaltatech.mcp.weblink.sdk.WEBLINK;
import com.abaltatech.mcp.weblink.sdk.WEBLINK.EWLUIParam;
import com.abaltatech.mcp.weblink.sdk.WEBLINK.IConnectionListener;
import com.abaltatech.mcp.weblink.sdk.WLDisplayManager;
import com.waze.AppService;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityLifetimeHandler;

public class WeblinkManager implements IConnectionListener {
    private static final int SCALE_FACTOR = 250;
    private static final String TAG = "WeblinkManager";
    public static final String WL_PENDING_INTENT_RESULT = "com.abaltatech.weblink.PendingResut";
    public static final String WL_SPEECH_INTENT = "weblink.voicerecognition.ACTION_RECOGNIZE_SPEECH";
    private static WeblinkManager mInstance = null;

    class C33151 implements Runnable {
        C33151() {
        }

        public void run() {
            NativeManager.getInstance().CloseProgressPopup();
        }
    }

    class C33162 implements Runnable {
        C33162() {
        }

        public void run() {
            if (ActivityLifetimeHandler.isInBackground()) {
                Log.d(WeblinkManager.TAG, "WeblinkManager - onClientDisconnected - In Background");
            } else {
                AppService.getMainActivity().setRequestedOrientation(2);
                AppService.getMainActivity().EnableOrientation();
                Log.d(WeblinkManager.TAG, "WeblinkManager - onClientDisconnected - Recreating activity");
                if (VERSION.SDK_INT >= 11) {
                    AppService.getMainActivity().recreate();
                    ActivityBase activeActivity = AppService.getActiveActivity();
                    if (!(activeActivity == null || activeActivity == AppService.getMainActivity())) {
                        activeActivity.recreate();
                    }
                } else {
                    Intent intent = AppService.getMainActivity().getIntent();
                    intent.addFlags(65536);
                    AppService.getMainActivity().finish();
                    AppService.getMainActivity().overridePendingTransition(0, 0);
                    AppService.getMainActivity().startActivity(intent);
                    AppService.getMainActivity().overridePendingTransition(0, 0);
                }
            }
            NativeManager.getInstance().CloseProgressPopup();
        }
    }

    private WeblinkManager() {
    }

    public static WeblinkManager create() {
        mInstance = new WeblinkManager();
        return mInstance;
    }

    public static WeblinkManager getInstance() {
        create();
        return mInstance;
    }

    public void initialize(Application app) {
        Log.d(TAG, "Initializing WeblinkManager");
        WEBLINK.instance.init(app);
        WEBLINK.instance.setUIParamInt(EWLUIParam.UIP_KEEP_PHONE_DPI, 1);
        WEBLINK.instance.setUIParamInt(EWLUIParam.UIP_SCALE_APP_CANVAS_TO_DPI_MODE, 1);
        WEBLINK.instance.setUIParamInt(EWLUIParam.UIP_MAX_APP_CANVAS_SCALE_FACTOR_PERCENT, 250);
        WEBLINK.instance.setUiMode(3);
        WEBLINK.instance.registerConnectionListener(this);
    }

    public void terminate() {
        Log.d(TAG, "Terminating WeblinkManager");
        WEBLINK.instance.unregisterConnectionListener(this);
        WEBLINK.instance.terminate();
    }

    public void onClientConnected() {
        Log.d(TAG, "WeblinkManager - onClientConnected");
        AppService.getMainActivity().setRequestedOrientation(0);
        AppService.getMainActivity().DisableOrientation();
        if (VERSION.SDK_INT >= 11) {
            Log.d(TAG, "WeblinkManager - onClientConnected - Recreating Activity");
            AppService.getMainActivity().recreate();
            ActivityBase activeActivity = AppService.getActiveActivity();
            if (!(activeActivity == null || (activeActivity instanceof MainActivity))) {
                activeActivity.recreate();
            }
        } else {
            Intent intent = AppService.getMainActivity().getIntent();
            intent.addFlags(65536);
            AppService.getMainActivity().finish();
            AppService.getMainActivity().overridePendingTransition(0, 0);
            AppService.getMainActivity().startActivity(intent);
            AppService.getMainActivity().overridePendingTransition(0, 0);
        }
        new Handler().postDelayed(new C33151(), 500);
    }

    public void onClientDisconnected() {
        Log.d(TAG, "WeblinkManager - onClientDisconnected");
        if (AppService.getMainActivity() != null) {
            new Handler().postDelayed(new C33162(), 300);
        }
    }

    public int getScreenWidthPixels() {
        return (int) (((float) ((WLDisplayManager.getInstance().getLastDisplayWidth() * 250) / 100)) / AppService.getAppContext().getResources().getDisplayMetrics().density);
    }

    public int getScreenHeightPixels() {
        return (int) (((float) ((WLDisplayManager.getInstance().getLastDisplayHeight() * 250) / 100)) / AppService.getAppContext().getResources().getDisplayMetrics().density);
    }

    public void requestActivation() {
        WEBLINK.instance.requestActivation();
    }

    public boolean isConnectedToClient() {
        return WEBLINK.instance.isConnectedToClient();
    }
}
