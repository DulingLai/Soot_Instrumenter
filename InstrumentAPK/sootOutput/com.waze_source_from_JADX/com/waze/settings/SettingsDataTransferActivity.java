package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.ConfigManager.IConfigUpdater;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class SettingsDataTransferActivity extends ActivityBase implements IConfigUpdater {
    private static final int DOWNLOAD_TRAFFIC_INDEX = 0;
    public static final String screenName = "SettingsDataTransfer";
    private WazeSettingsView downloadTrafficInfoView;
    private List<ConfigItem> mConfigItems;
    private NativeManager mNativeManager = AppService.getNativeManager();

    class C26791 implements Runnable {
        C26791() {
        }

        public void run() {
            AppService.getNativeManager().refreshMapNTV();
        }
    }

    class C26813 implements OnClickListener {
        C26813() {
        }

        public void onClick(View v) {
            SettingsDataTransferActivity.this.onRefresh();
        }
    }

    public void updateConfigItems(List<ConfigItem> configItems) {
        this.downloadTrafficInfoView.setValue(((ConfigItem) configItems.get(0)).getBooleanValue());
        this.downloadTrafficInfoView.initToggleCallback(screenName, this.mConfigItems, 0, null);
    }

    private void onRefresh() {
        NativeManager.Post(new C26791());
        final TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.setCloseEnabled(false);
        titleBar.postDelayed(new Runnable() {
            public void run() {
                titleBar.setCloseEnabled(true);
            }
        }, 2500);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("waze", "start data transfer activity");
        setContentView(C1283R.layout.settings_data_transfer);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 100);
        this.mConfigItems = new ArrayList();
        this.mConfigItems.add(new ConfigItem("Download", "Download traffic jams", ""));
        ConfigManager.getInstance().getConfig(this, this.mConfigItems, screenName);
        ((WazeSettingsView) findViewById(C1283R.id.settingsRefreshMap)).setText(this.mNativeManager.getLanguageString(101));
        ((TextView) findViewById(C1283R.id.dataTransferDeisclaimer1)).setText(this.mNativeManager.getLanguageString(104));
        ((TextView) findViewById(C1283R.id.dataTransferDeisclaimer2)).setText(this.mNativeManager.getLanguageString(103));
        findViewById(C1283R.id.settingsRefreshMap).setOnClickListener(new C26813());
        this.downloadTrafficInfoView = (WazeSettingsView) findViewById(C1283R.id.settingsDownloadTrafficInfo);
        this.downloadTrafficInfoView.setText(102);
    }
}
