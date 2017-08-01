package com.waze.settings;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.audioextension.spotify.SpotifyManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class SettingsSpotifyActivity extends ActivityBase {
    private NativeManager mNativeManager;
    private WazeSettingsView mSpotifyCheckBoxView = null;

    class C27771 implements OnCheckedChangeListener {
        C27771() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SpotifyManager.getInstance().setUserEnable(isChecked);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNativeManager = NativeManager.getInstance();
        setContentView(C1283R.layout.settings_spotify);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_SPOTIFY);
        this.mSpotifyCheckBoxView = (WazeSettingsView) findViewById(C1283R.id.settingsSpotify);
        this.mSpotifyCheckBoxView.setText(this.mNativeManager.getLanguageString(DisplayStrings.DS_SPOTIFY_SETTINGS));
        this.mSpotifyCheckBoxView.setValue(SpotifyManager.getInstance().userEnabled());
        this.mSpotifyCheckBoxView.initToggleCallbackBoolean(435, new C27771());
    }
}
