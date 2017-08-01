package com.waze.settings;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.ifs.ui.SettingsChoiceDialog;
import java.util.List;

public class SettingsUtils {
    public static void showSubmenu(Context context, String dialogTitle, String[] options, int selectedOption, SettingsDialogListener listener) {
        showSubmenuStyle(context, dialogTitle, options, selectedOption, listener, C1283R.style.CustomPopup);
    }

    public static void showSubmenuStyle(Context context, String dialogTitle, String[] options, int selectedOption, SettingsDialogListener listener, int style) {
        if (options != null) {
            new SettingsChoiceDialog(context, dialogTitle, options, selectedOption, listener).show();
        }
    }

    public static int findValueIndex(String[] values, String value) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                return i;
            }
        }
        return 0;
    }

    public static int findValueIndex(String[] values, String defaultvalue, String value) {
        int i;
        for (i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                return i;
            }
        }
        for (i = 0; i < values.length; i++) {
            if (values[i].equals(defaultvalue)) {
                return i;
            }
        }
        return 0;
    }

    public static int findValueIndex(int[] values, int value) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                return i;
            }
        }
        return 0;
    }

    public static void setCheckboxLanguageString(SettingsCheckboxView checkboxView, int displayStr) {
        checkboxView.setText(NativeManager.getInstance().getLanguageString(displayStr));
    }

    public static void setCheckboxCallback(String screenName, List<ConfigItem> configItems, SettingsCheckboxView checkboxView, int configIndex) {
        setCheckboxCallback(screenName, configItems, checkboxView, configIndex, null);
    }

    public static void setCheckboxCallback(final String screenName, final List<ConfigItem> configItems, SettingsCheckboxView checkboxView, final int configIndex, final OnCheckedChangeListener customCallback) {
        ((CheckBox) checkboxView.findViewById(C1283R.id.settingsCheckboxCheckbox)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigItem configItem = (ConfigItem) configItems.get(configIndex);
                configItem.setBooleanValue(isChecked);
                ConfigManager.getInstance().setConfig(configItem, screenName);
                if (customCallback != null) {
                    customCallback.onCheckedChanged(buttonView, isChecked);
                }
            }
        });
    }
}
