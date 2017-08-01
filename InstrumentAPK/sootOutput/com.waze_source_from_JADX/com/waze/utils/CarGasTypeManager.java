package com.waze.utils;

import android.util.Log;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.NativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.SettingsNativeManager.SettingsValuesListener;
import com.waze.settings.SettingsValue;
import java.util.ArrayList;
import java.util.List;

public class CarGasTypeManager {
    public static final int PRIVATE_CAR_INDEX = 0;
    public static final int TAXI_INDEX = 1;
    private static CarGasTypeManager _instance;
    private int mCarSelectedIndex;
    private String[] mGasCodes = null;
    private String[] mGasDisplayStrings = null;
    private int mGasSelectedIndex;
    private boolean mIsLoadInProgress;
    private List<GasTypeLoadedListener> mListeners = new ArrayList();
    private String[] mVehicleCodes = null;
    private String[] mVehicleDisplayStrings = null;
    private int[] mVehicleResIds = new int[]{C1283R.drawable.list_icon_private_car, C1283R.drawable.list_icon_taxi};
    private final Object reloadDataMutex = new Object();

    public interface GasTypeLoadedListener {
        void onGasTypeLoaded();
    }

    public static synchronized CarGasTypeManager getInstance() {
        CarGasTypeManager carGasTypeManager;
        synchronized (CarGasTypeManager.class) {
            if (_instance == null) {
                _instance = new CarGasTypeManager();
            }
            carGasTypeManager = _instance;
        }
        return carGasTypeManager;
    }

    private CarGasTypeManager() {
    }

    public void reloadCarAndGasData(GasTypeLoadedListener listener) {
        synchronized (this.reloadDataMutex) {
            if (listener != null) {
                if (!this.mListeners.contains(listener)) {
                    this.mListeners.add(listener);
                }
            }
            this.mIsLoadInProgress = true;
        }
        final boolean isDataLoaded = isDataLoaded();
        if (!isDataLoaded) {
            String[] configGetVehicleTypes = DriveToNativeManager.getInstance().configGetVehicleTypesNTV();
            if (configGetVehicleTypes == null || configGetVehicleTypes.length < 2) {
                this.mIsLoadInProgress = false;
                Log.i("CarGasManager", "vehicle types was empty or invalid: " + configGetVehicleTypes);
                return;
            }
            this.mVehicleDisplayStrings = new String[(configGetVehicleTypes.length / 2)];
            this.mVehicleCodes = new String[(configGetVehicleTypes.length / 2)];
            for (int i = 1; i < configGetVehicleTypes.length; i += 2) {
                this.mVehicleCodes[i / 2] = configGetVehicleTypes[i];
                this.mVehicleDisplayStrings[i / 2] = configGetVehicleTypes[i - 1];
            }
        }
        SettingsNativeManager.getInstance().getPreferredGasType(new SettingsValuesListener() {
            public void onComplete(SettingsValue[] values) {
                if (values == null || values.length == 0) {
                    CarGasTypeManager.this.mIsLoadInProgress = false;
                    return;
                }
                if (!isDataLoaded) {
                    CarGasTypeManager.this.mGasCodes = new String[values.length];
                    CarGasTypeManager.this.mGasDisplayStrings = new String[values.length];
                }
                for (int i = 0; i < values.length; i++) {
                    if (!isDataLoaded) {
                        CarGasTypeManager.this.mGasCodes[i] = values[i].value;
                        CarGasTypeManager.this.mGasDisplayStrings[i] = NativeManager.getInstance().getLanguageString(values[i].value);
                    }
                    if (values[i].isSelected) {
                        CarGasTypeManager.this.mGasSelectedIndex = i;
                    }
                }
                String selectedCarCode = CarGasTypeManager.this.findSelectedCarIndex();
                String permanentCarCode = ConfigManager.getInstance().getConfigValueString(336);
                if (CarGasTypeManager.this.mVehicleCodes != null && CarGasTypeManager.this.mVehicleCodes.length > 0) {
                    if (permanentCarCode.equals("UNDEFINED")) {
                        ConfigManager.getInstance().setConfigValueString(336, CarGasTypeManager.this.mVehicleCodes[CarGasTypeManager.this.mCarSelectedIndex]);
                    } else if (!(isDataLoaded || permanentCarCode.equals(selectedCarCode))) {
                        ConfigManager.getInstance().setConfigValueString(140, permanentCarCode);
                        CarGasTypeManager.this.findSelectedCarIndex();
                    }
                }
                synchronized (CarGasTypeManager.this.reloadDataMutex) {
                    CarGasTypeManager.this.mIsLoadInProgress = false;
                    for (GasTypeLoadedListener uiListener : CarGasTypeManager.this.mListeners) {
                        uiListener.onGasTypeLoaded();
                    }
                    CarGasTypeManager.this.mListeners.clear();
                }
            }
        });
    }

    private String findSelectedCarIndex() {
        String selectedCarCode = ConfigManager.getInstance().getConfigValueString(140);
        for (int i = 0; i < this.mVehicleCodes.length; i++) {
            if (this.mVehicleCodes[i].equals(selectedCarCode)) {
                this.mCarSelectedIndex = i;
                break;
            }
        }
        return selectedCarCode;
    }

    public int getTotalGasTypes() {
        return this.mGasCodes.length;
    }

    public boolean isCarTypePermanent(String carCode) {
        return carCode.equals(ConfigManager.getInstance().getConfigValueString(336));
    }

    public boolean isSelectedCarTypePermanent() {
        return isCarTypePermanent(this.mVehicleCodes[this.mCarSelectedIndex]);
    }

    public String getVehicleDisplayString(int index) {
        return NativeManager.getInstance().getLanguageString(this.mVehicleDisplayStrings[index]);
    }

    public String getVehicleCode(int index) {
        return this.mVehicleCodes[index];
    }

    public int getVehicleResId(int index) {
        return this.mVehicleResIds[index];
    }

    public String getSelectedVehicleDisplayString() {
        return getVehicleDisplayString(this.mCarSelectedIndex);
    }

    public String getSelectedVehicleCode() {
        return getVehicleCode(this.mCarSelectedIndex);
    }

    public int getSelecteVehicleResId() {
        return getVehicleResId(this.mCarSelectedIndex);
    }

    public String getGasDisplayString(int index) {
        return this.mGasDisplayStrings[index];
    }

    public String getGasCode(int index) {
        return this.mGasCodes[index];
    }

    public String getSelectedGasDisplayString() {
        return getGasDisplayString(this.mGasSelectedIndex);
    }

    public String getSelectedGasCode() {
        return getGasCode(this.mGasSelectedIndex);
    }

    public int getSelectedVehicleIndex() {
        return this.mCarSelectedIndex;
    }

    public int getSelectedGasIndex() {
        return this.mGasSelectedIndex;
    }

    public boolean isDataLoaded() {
        return this.mGasDisplayStrings != null && this.mGasDisplayStrings.length > 0 && this.mVehicleCodes != null && this.mVehicleCodes.length > 0;
    }
}
