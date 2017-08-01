package com.waze.settings;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import com.waze.voice.VoiceData;
import java.io.Serializable;
import java.util.HashMap;

public class SettingsNativeManager {
    public static final String ADDRESS_BOOK_IMPL_CONTENT_RESOLVER = "Res";
    public static final String ADDRESS_BOOK_IMPL_GMS_CORE = "Gms";
    public static final String ADDRESS_BOOK_IMPL_OLD = "Old";
    public static final String ADDRESS_BOOK_IMPL_VALUE = "AddressBookImpl";
    public static final String SETTINGS_NOTIFICATION_CONFIG_NAME = "com.waze.settingNotifications";
    public static final int UH_NOTIFICATION_PREFERENCES = TicketRoller.get(Type.Handler);
    public static final String VIBRATE_VALUE = "SettingsVibrateMode";
    private static SettingsNativeManager mInstance;
    private UpdateHandlers handlers = new UpdateHandlers();

    public interface SettingsVoiceDataValuesListener {
        void onComplete(VoiceData[] voiceDataArr);
    }

    public interface SettingsValuesListener {
        void onComplete(SettingsValue[] settingsValueArr);
    }

    public static class NotificationCategory implements Parcelable {
        public static final Creator<NotificationCategory> CREATOR = new C27321();
        boolean bEnabled;
        String sLangDescription;
        String sLangName;
        String sName;

        static class C27321 implements Creator<NotificationCategory> {
            C27321() {
            }

            public NotificationCategory createFromParcel(Parcel in) {
                return new NotificationCategory(in);
            }

            public NotificationCategory[] newArray(int size) {
                return new NotificationCategory[size];
            }
        }

        protected NotificationCategory(Parcel in) {
            this.sName = in.readString();
            this.bEnabled = in.readInt() == 0;
            this.sLangName = in.readString();
            this.sLangDescription = in.readString();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.sName);
            dest.writeInt(this.bEnabled ? 0 : 1);
            dest.writeString(this.sLangName);
            dest.writeString(this.sLangDescription);
        }

        public int describeContents() {
            return 0;
        }
    }

    public interface SettingsExistsListener {
        void onComplete(boolean z);
    }

    public interface SettingsSearchLangListener {
        void onComplete(String str);
    }

    public interface SettingsSearchVoiceDataValuesListener {
        void onComplete(StringsToHashMap stringsToHashMap);
    }

    public static class StringsToHashMap implements Serializable {
        public String[] keys;
        public String[] values;

        public HashMap<String, String> get() {
            HashMap<String, String> res = new HashMap();
            if (this.keys == null || this.values == null || this.keys.length != this.values.length) {
                Logger.e("NativeManager:SearchLanguages: keys or values are null or arrays not the same size");
            } else {
                for (int i = 0; i < this.keys.length; i++) {
                    res.put(this.keys[i], this.values[i]);
                }
            }
            return res;
        }
    }

    private native String getCurrentSearchVoiceCaptionNTV(boolean z);

    private native String getCurrentSearchVoiceNTV();

    private native String getFallbackLocaleNTV();

    private native SettingsValue[] getGasStationsNTV();

    private native SettingsValue[] getGasTypesNTV();

    private native SettingsValue[] getLanguagesNTV();

    private native SettingsValue[] getNavigationGuidanceNTV();

    private native SettingsValue[] getNavigationGuidanceTypesNTV();

    private native int getNotificationPreferencesNTV();

    private native String getVoiceSearchLangNTV();

    private native VoiceData[] getVoicesNTV();

    private native void initNativeLayerNTV();

    private native void setLanguageNTV(String str);

    private native void setNavigationGuidanceNTV(String str);

    private native boolean setNavigationGuidanceTypeNTV(String str, String str2);

    private native int setNotificationPreferencesNTV(String str, boolean z);

    private native void setPreferredStationNTV(int i);

    private native void setPreferredTypeNTV(int i);

    private native void setSearchVoiceNTV(String str);

    private native void setVoiceNTV(int i);

    public native boolean getCurrentSearchVoiceIsAutoNTV();

    public native String getLanguagesLocaleNTV();

    public native int getVibrationConfigNTV();

    public native StringsToHashMap getVoiceSearchLanguageMapNTV();

    public static SettingsNativeManager getInstance() {
        if (mInstance == null) {
            mInstance = new SettingsNativeManager();
        }
        return mInstance;
    }

    private SettingsNativeManager() {
        initNativeLayerNTV();
    }

    public void setUpdateHandler(int id, Handler h) {
        this.handlers.setUpdateHandler(id, h);
    }

    public void unsetUpdateHandler(int id, Handler h) {
        this.handlers.unsetUpdateHandler(id, h);
    }

    public void getLanguages(final SettingsValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            SettingsValue[] languages = null;

            public void event() {
                this.languages = SettingsNativeManager.this.getLanguagesNTV();
            }

            public void callback() {
                listener.onComplete(this.languages);
            }
        });
    }

    public void getVoices(final SettingsVoiceDataValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            VoiceData[] languages = null;

            public void event() {
                this.languages = SettingsNativeManager.this.getVoicesNTV();
            }

            public void callback() {
                if (listener != null) {
                    listener.onComplete(this.languages);
                }
            }
        });
    }

    public void getSearchLanguagesOptions(final SettingsSearchVoiceDataValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            StringsToHashMap languages = null;

            public void event() {
                this.languages = SettingsNativeManager.this.getVoiceSearchLanguageMapNTV();
                if (this.languages == null) {
                    Logger.e("SettingsNativeManager:getConfigSearchLanguages: null returned from NTV");
                    this.languages = new StringsToHashMap();
                }
            }

            public void callback() {
                listener.onComplete(this.languages);
            }
        });
    }

    public void getDefaultSearchLanguage(final SettingsSearchLangListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            String language = null;

            public void event() {
                this.language = SettingsNativeManager.this.getFallbackLocaleNTV();
            }

            public void callback() {
                listener.onComplete(this.language);
            }
        });
    }

    public void getSearchLanguage(final SettingsSearchLangListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            String language = null;

            public void event() {
                this.language = SettingsNativeManager.this.getVoiceSearchLangNTV();
            }

            public void callback() {
                listener.onComplete(this.language);
            }
        });
    }

    public void getSearchLanguageCaption(final boolean withAutoCaption, final SettingsSearchLangListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            String caption = null;

            public void event() {
                this.caption = SettingsNativeManager.this.getCurrentSearchVoiceCaptionNTV(withAutoCaption);
            }

            public void callback() {
                listener.onComplete(this.caption);
            }
        });
    }

    public void getSearchLanguageTag(final SettingsSearchLangListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            String tag = null;

            public void event() {
                this.tag = SettingsNativeManager.this.getCurrentSearchVoiceNTV();
            }

            public void callback() {
                listener.onComplete(this.tag);
            }
        });
    }

    public void setLanguage(final String name) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setLanguageNTV(name);
            }
        });
    }

    public void setVoice(final int position) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setVoiceNTV(position);
            }
        });
    }

    public void setSearchVoice(final String key) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setSearchVoiceNTV(key);
            }
        });
    }

    public void getNavigationGuidance(final SettingsValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            SettingsValue[] values = null;

            public void event() {
                this.values = SettingsNativeManager.this.getNavigationGuidanceNTV();
            }

            public void callback() {
                listener.onComplete(this.values);
            }
        });
    }

    public void setNavigationGuidance(final String value) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setNavigationGuidanceNTV(value);
            }
        });
    }

    public void getNavigationGuidanceTypes(final SettingsValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            SettingsValue[] values = null;

            public void event() {
                this.values = SettingsNativeManager.this.getNavigationGuidanceTypesNTV();
            }

            public void callback() {
                listener.onComplete(this.values);
            }
        });
    }

    public void setNavigationGuidanceType(final String name, final String display, final SettingsExistsListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            private boolean exists;

            public void event() {
                this.exists = SettingsNativeManager.this.setNavigationGuidanceTypeNTV(name, display);
            }

            public void callback() {
                listener.onComplete(this.exists);
            }
        });
    }

    public void getPreferredGasType(final SettingsValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            SettingsValue[] gasTypes = null;

            public void event() {
                this.gasTypes = SettingsNativeManager.this.getGasTypesNTV();
            }

            public void callback() {
                listener.onComplete(this.gasTypes);
            }
        });
    }

    public void getPreferredGasStations(final SettingsValuesListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            SettingsValue[] gasStations = null;

            public void event() {
                this.gasStations = SettingsNativeManager.this.getGasStationsNTV();
            }

            public void callback() {
                listener.onComplete(this.gasStations);
            }
        });
    }

    public void setPreferredStation(final int index) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setPreferredStationNTV(index);
            }
        });
    }

    public void setPreferredType(final int index) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setPreferredTypeNTV(index);
            }
        });
    }

    public void ntv_on_get_push_prefs_result(NotificationCategory[] notCatArr) {
        Bundle b = new Bundle();
        b.putParcelableArray("notification_categories", notCatArr);
        this.handlers.sendUpdateMessage(UH_NOTIFICATION_PREFERENCES, b);
    }

    public void getNotificationPreferences() {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.getNotificationPreferencesNTV();
            }
        });
    }

    public void setNotificationPreferences(final String id, final boolean value) {
        NativeManager.Post(new Runnable() {
            public void run() {
                SettingsNativeManager.this.setNotificationPreferencesNTV(id, value);
            }
        });
    }
}
