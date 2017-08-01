package com.waze.reports;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.NearbyStation;
import com.waze.strings.DisplayStrings;

class ReportMenu$2 implements OnClickListener {
    ReportMenu$2() {
    }

    public void onClick(View v) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_CLICK, "TYPE", "GAS");
        if (NativeManager.getInstance().hasNetworkNTV()) {
            final DriveToNativeManager dtnm = DriveToNativeManager.getInstance();
            dtnm.setUpdateHandler(DriveToNativeManager.UH_NEARBY_STATIONS, new Handler() {

                class C25301 implements Runnable {
                    C25301() {
                    }

                    public void run() {
                        NativeManager.getInstance().CloseProgressPopup();
                    }
                }

                public void handleMessage(Message msg) {
                    dtnm.unsetUpdateHandler(DriveToNativeManager.UH_NEARBY_STATIONS, this);
                    NearbyStation[] nearbyStations = (NearbyStation[]) msg.getData().getSerializable(GasPriceReport.NEARBY_STATIONS);
                    if (nearbyStations.length == 0) {
                        NativeManager.getInstance().OpenProgressIconPopup(DisplayStrings.displayString(208), "popup_x_icon");
                        AppService.getMainActivity().postDelayed(new C25301(), 3000);
                        return;
                    }
                    Intent reportGasPrices = new Intent(AppService.getActiveActivity(), GasPriceReport.class);
                    reportGasPrices.putExtra(GasPriceReport.NEARBY_STATIONS, nearbyStations);
                    AppService.getMainActivity().startActivityForResult(reportGasPrices, 4000);
                }
            });
            dtnm.searchNearbyStations();
            return;
        }
        MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_OFFLINE_REPORT_GAS_TITLE), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_OFFLINE_REPORT_GAS_BODY), false);
    }
}
