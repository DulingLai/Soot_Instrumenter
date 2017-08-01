package com.waze.auto;

import android.content.Intent;
import android.os.Build.VERSION;
import com.waze.AppService;
import com.waze.pioneer.PioneerManager;
import com.waze.weblink.WeblinkManager;

public class AutoUtils {
    static boolean simulatedAuto = false;

    public static boolean isAutoMode() throws  {
        if (WeblinkManager.getInstance().isConnectedToClient()) {
            return true;
        }
        return !PioneerManager.isActive() ? simulatedAuto : true;
    }

    public static void toggleSimulatedAuto() throws  {
        simulatedAuto = !simulatedAuto;
        if (VERSION.SDK_INT >= 11) {
            AppService.getMainActivity().recreate();
            return;
        }
        Intent $r1 = AppService.getMainActivity().getIntent();
        $r1.addFlags(65536);
        AppService.getMainActivity().finish();
        AppService.getMainActivity().overridePendingTransition(0, 0);
        AppService.getMainActivity().startActivity($r1);
        AppService.getMainActivity().overridePendingTransition(0, 0);
    }
}
