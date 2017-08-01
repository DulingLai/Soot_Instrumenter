package com.waze.settings;

import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.NativeManager;

public class SettingsSound {
    public static int[] SOUND_ICONS = new int[]{C1283R.drawable.list_icon_sound_on, C1283R.drawable.list_icon_sound_alerts, C1283R.drawable.list_icon_sound_off};
    public static String[] SOUND_OPTIONS;
    public static String[] SOUND_VALUES = new String[]{"yes", "alerts", "no"};

    public static String[] getSoundOptions() {
        if (SOUND_OPTIONS == null) {
            SOUND_OPTIONS = new String[]{NativeManager.getInstance().getLanguageString(6), NativeManager.getInstance().getLanguageString(7), NativeManager.getInstance().getLanguageString(8)};
        }
        return SOUND_OPTIONS;
    }

    public static int getSoundSelectionFromConfig() {
        return ConfigManager.getOptionIndex(SOUND_VALUES, ConfigManager.getInstance().getConfigValueString(126), 0);
    }

    public static int getSoundIconBySelection(int sel) {
        if (sel >= 0 && sel <= SOUND_ICONS.length) {
            return SOUND_ICONS[sel];
        }
        Logger.e("SettingsSound:getSoundIconBySelection received illegal index: " + sel);
        return SOUND_ICONS[0];
    }

    public static String getSoundOptionBySelection(int sel) {
        getSoundOptions();
        if (sel >= 0 && sel <= SOUND_OPTIONS.length) {
            return SOUND_OPTIONS[sel];
        }
        Logger.e("SettingsSound:getSoundOptionBySelection received illegal index: " + sel);
        return SOUND_OPTIONS[0];
    }

    public static String getSoundValueBySelection(int sel) {
        getSoundOptions();
        if (sel >= 0 && sel < SOUND_VALUES.length) {
            return SOUND_VALUES[sel];
        }
        Logger.e("SettingsSound:getSoundValueBySelection received illegal index: " + sel);
        return SOUND_VALUES[0];
    }

    public static void setSoundValInConfig(int sel) {
        int ind = sel;
        if (ind < 0 || ind >= SOUND_VALUES.length) {
            Logger.e("SettingsSound:setSoundValInConfig received illegal index: " + sel);
            ind = 0;
        }
        ConfigManager.getInstance().setConfigValueString(126, SOUND_VALUES[ind]);
    }
}
