package com.waze.settings;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import java.util.List;

public class SettingsUtilsMultipleConfig {
    public static void createSettingsSelectionView(Context context, String screenName, List<ConfigItem> configItems, WazeSettingsView selectionView, int displayStr, String[] options, String[] values, int[] configIndex) {
        final String langDisplayStr = NativeManager.getInstance().getLanguageString(displayStr);
        selectionView.setKeyText(langDisplayStr);
        final String[] strArr = options;
        final Context context2 = context;
        final List<ConfigItem> list = configItems;
        final int[] iArr = configIndex;
        final String str = screenName;
        final WazeSettingsView wazeSettingsView = selectionView;
        final String[] strArr2 = values;
        selectionView.setOnClickListener(new OnClickListener() {

            class C27791 implements SettingsDialogListener {
                C27791() {
                }

                public void onComplete(int position) {
                    ConfigItem configItem;
                    if (position != 1) {
                        configItem = (ConfigItem) list.get(iArr[1]);
                        configItem.setBooleanValue(false);
                        ConfigManager.getInstance().setConfig(configItem, str);
                    }
                    configItem = (ConfigItem) list.get(iArr[position]);
                    wazeSettingsView.setValueText(NativeManager.getInstance().getLanguageString(strArr[position]));
                    configItem.setStringValue(strArr2[position]);
                    ConfigManager.getInstance().setConfig(configItem, str);
                }
            }

            public void onClick(View v) {
                String[] displayOptions = new String[strArr.length];
                int i = 0;
                for (String option : strArr) {
                    displayOptions[i] = NativeManager.getInstance().getLanguageString(option);
                    i++;
                }
                SettingsUtilsMultipleConfig.showSubmenu(context2, langDisplayStr, displayOptions, new C27791());
            }
        });
    }

    public static void showSubmenu(Context context, String dialogTitle, String[] options, final SettingsDialogListener listener) {
        if (options != null) {
            Builder builder = new Builder(context, C1283R.style.CustomPopup);
            if (dialogTitle != null) {
                builder.setTitle(dialogTitle);
            }
            builder.setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    listener.onComplete(item);
                }
            });
            builder.create().show();
        }
    }

    public static void createFakeSettingsSelectionView(final Context context, WazeSettingsView selectionView, int displayStr, final String[] options, final SettingsDialogListener listener) {
        final String langDisplayStr = NativeManager.getInstance().getLanguageString(displayStr);
        selectionView.setKeyText(langDisplayStr);
        selectionView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String[] displayOptions = new String[options.length];
                int i = 0;
                for (String option : options) {
                    displayOptions[i] = NativeManager.getInstance().getLanguageString(option);
                    i++;
                }
                SettingsUtilsMultipleConfig.showSubmenu(context, langDisplayStr, displayOptions, listener);
            }
        });
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

    public static void setCheckboxCallback(final String screenName, final List<ConfigItem> configItems, SettingsCheckboxView checkboxView, final int configIndex) {
        ((CheckBox) checkboxView.findViewById(C1283R.id.settingsCheckboxCheckbox)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigItem configItem = (ConfigItem) configItems.get(configIndex);
                configItem.setBooleanValue(isChecked);
                ConfigManager.getInstance().setConfig(configItem, screenName);
            }
        });
    }
}
