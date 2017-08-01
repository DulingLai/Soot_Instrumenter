package com.waze;

import android.util.Log;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.ifs.async.RunnableUICallback;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ConfigManager {
    public static final int CounterConfig_CarOptionsBubble = 13;
    public static final int CounterConfig_CarpoolButtonBanner = 6;
    public static final int CounterConfig_DogfoodWarning = 7;
    public static final int CounterConfig_FriendsTip = 4;
    public static final int CounterConfig_LAST = 15;
    public static final int CounterConfig_PopupBank = 11;
    public static final int CounterConfig_PopupCarDetails = 9;
    public static final int CounterConfig_PopupDriverProfile = 8;
    public static final int CounterConfig_PopupMultipaxIntro = 14;
    public static final int CounterConfig_PopupTuneupQuestion = 12;
    public static final int CounterConfig_PopupWorkplace = 10;
    public static final int CounterConfig_PrivacyMessage = 2;
    public static final int CounterConfig_ResidentialConfirmation = 0;
    public static final int CounterConfig_SendLocationButtonBanner = 1;
    public static final int CounterConfig_ShareEtaFriendTip = 5;
    public static final int CounterConfig_ShareEtaTip = 3;
    public static final int SwitchConfig_CarpoolAllRidesBankBannerClosed = 4;
    public static final int SwitchConfig_CarpoolAllRidesCarBannerClosed = 2;
    public static final int SwitchConfig_CarpoolAllRidesLocationBannerClosed = 1;
    public static final int SwitchConfig_CarpoolAllRidesMultipaxIntroClosed = 7;
    public static final int SwitchConfig_CarpoolAllRidesProfileBannerClosed = 5;
    public static final int SwitchConfig_CarpoolAllRidesTuneupClosed = 6;
    public static final int SwitchConfig_CarpoolAllRidesWorkBannerClosed = 3;
    public static final int SwitchConfig_LAST = 8;
    public static final int SwitchConfig_OptOutOfTargetedAds = 0;
    private static ConfigManager mInstance;

    class C11142 implements Runnable {
        C11142() throws  {
        }

        public void run() throws  {
            ConfigManager.this.configSyncAllNTV();
        }
    }

    class C11175 implements Runnable {
        C11175() throws  {
        }

        public void run() throws  {
            ConfigManager.this.sendLogsClickNTV();
        }
    }

    class C11186 implements Runnable {
        C11186() throws  {
        }

        public void run() throws  {
            ConfigManager.this.askQuestionNTV();
        }
    }

    class C11197 implements Runnable {
        C11197() throws  {
        }

        public void run() throws  {
            ConfigManager.this.aboutClickNTV();
        }
    }

    public interface IConfigUpdater {
        void updateConfigItems(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/ConfigItem;", ">;)V"}) List<ConfigItem> list) throws ;
    }

    private native void InitNativeLayerNTV() throws ;

    private native void aboutClickNTV() throws ;

    private native void askQuestionNTV() throws ;

    private native int checkConfigDisplayCounterNTV(int i, boolean z) throws ;

    private native void configSyncAllNTV() throws ;

    private native String getConfigNTV(String str, String str2) throws ;

    private native boolean getConfigSwitchValueNTV(int i) throws ;

    private native void sendLogsClickNTV() throws ;

    private native void setConfigDisplayCounterNTV(int i, int i2) throws ;

    private native void setConfigNTV(String str, String str2) throws ;

    private native void setConfigSwitchValueNTV(int i, boolean z) throws ;

    private native void setMapOrientationNTV(String str) throws ;

    private native void setMapSkinNTV(String str, String str2) throws ;

    public static native void testCounterConfigEnum() throws ;

    private native void toggleConfigSwitchValueNTV(int i) throws ;

    public native boolean configValueStringMatchesNTV(int i, String str) throws ;

    public native boolean getConfigValueBoolNTV(int i) throws ;

    public native int getConfigValueIntNTV(int i) throws ;

    public native long getConfigValueLongNTV(int i) throws ;

    public native String getConfigValueStringNTV(int i) throws ;

    public native void setConfigValueBoolNTV(int i, boolean z) throws ;

    public native void setConfigValueIntNTV(int i, int i2) throws ;

    public native void setConfigValueLongNTV(int i, long j) throws ;

    public native void setConfigValueStringNTV(int i, String str) throws ;

    public native void setSkinSchemeNTV(String str) throws ;

    public void setConfigValueBool(int $i0, boolean $z0) throws  {
        setConfigValueBoolNTV($i0, $z0);
    }

    public int getConfigValueInt(int $i0) throws  {
        return getConfigValueIntNTV($i0);
    }

    public boolean getConfigValueBool(int $i0) throws  {
        return getConfigValueBoolNTV($i0);
    }

    public String getConfigValueString(int $i0) throws  {
        return getConfigValueStringNTV($i0);
    }

    public long getConfigValueLong(int $i0) throws  {
        return getConfigValueLongNTV($i0);
    }

    public void setConfigValueString(int $i0, String $r1) throws  {
        setConfigValueStringNTV($i0, $r1);
    }

    public boolean configValueStringMatches(int $i0, String $r1) throws  {
        return configValueStringMatchesNTV($i0, $r1);
    }

    public void setConfigValueLong(int $i0, long $l1) throws  {
        setConfigValueLongNTV($i0, $l1);
    }

    public static int getOptionIndex(String[] $r0, String $r1, int $i0) throws  {
        if ($r0 == null || $r0.length == 0) {
            return -1;
        }
        for (int $i1 = 0; $i1 < $r0.length; $i1++) {
            if ($r1.equalsIgnoreCase($r0[$i1])) {
                return $i1;
            }
        }
        return $i0;
    }

    public void setMapSkin(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                ConfigManager.this.setMapSkinNTV($r1, $r2);
            }
        });
    }

    public void configSyncAll() throws  {
        NativeManager.Post(new C11142());
    }

    public void setMapOrientation(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                ConfigManager.this.setMapOrientationNTV($r1);
            }
        });
    }

    public static ConfigManager getInstance() throws  {
        if (mInstance == null) {
            mInstance = new ConfigManager();
            mInstance.initNativeLayer();
        }
        return mInstance;
    }

    public void getConfig(@Signature({"(", "Lcom/waze/ConfigManager$IConfigUpdater;", "Ljava/util/List", "<", "Lcom/waze/ConfigItem;", ">;", "Ljava/lang/String;", ")V"}) final IConfigUpdater $r1, @Signature({"(", "Lcom/waze/ConfigManager$IConfigUpdater;", "Ljava/util/List", "<", "Lcom/waze/ConfigItem;", ">;", "Ljava/lang/String;", ")V"}) final List<ConfigItem> $r2, @Signature({"(", "Lcom/waze/ConfigManager$IConfigUpdater;", "Ljava/util/List", "<", "Lcom/waze/ConfigItem;", ">;", "Ljava/lang/String;", ")V"}) final String $r3) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private String mConfigItems;

            public void event() throws  {
                Log.i(Logger.TAG, "getConfig - event");
                this.mConfigItems = ConfigManager.this.getConfigNTV(ConfigManager.this.configItemsToStr($r2), $r3);
            }

            public void callback() throws  {
                Log.i(Logger.TAG, "getConfig - callback");
                $r1.updateConfigItems(ConfigManager.this.StringToConfigItems(this.mConfigItems));
            }
        });
    }

    public void sendLogsClick() throws  {
        NativeManager.Post(new C11175());
    }

    public void helpAskQuestion() throws  {
        NativeManager.Post(new C11186());
    }

    public void aboutClick() throws  {
        NativeManager.Post(new C11197());
    }

    public void setConfig(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/ConfigItem;", ">;", "Ljava/lang/String;", ")V"}) final ArrayList<ConfigItem> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/ConfigItem;", ">;", "Ljava/lang/String;", ")V"}) final String $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                Log.i(Logger.TAG, "setConfig - event");
                ConfigManager.this.setConfigNTV(ConfigManager.this.configItemsToStr($r1).toString(), $r2);
            }

            public void callback() throws  {
            }
        });
    }

    public void setConfig(final ConfigItem $r1, final String $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                ConfigManager.this.setConfigNTV(($r1.getCategory() + FileUploadSession.SEPARATOR + $r1.getName() + ":" + $r1.getStringValue()).toString(), $r2);
            }

            public void callback() throws  {
            }
        });
    }

    private String configItemsToStr(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/ConfigItem;", ">;)", "Ljava/lang/String;"}) List<ConfigItem> $r1) throws  {
        StringBuffer $r2 = new StringBuffer(1024);
        for (ConfigItem $r5 : $r1) {
            if ($r5 == null || $r5.getName() == null || $r5.getName().length() == 0 || $r5.getCategory() == null || $r5.getCategory().length() == 0) {
                Log.w(Logger.TAG, "ConfigItem is not initialized as expected: " + $r5);
            } else {
                $r2.append($r5.getCategory() + FileUploadSession.SEPARATOR + $r5.getName() + ":" + $r5.getStringValue() + "|");
            }
        }
        return $r2.toString();
    }

    private List<ConfigItem> StringToConfigItems(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Lcom/waze/ConfigItem;", ">;"}) String $r1) throws  {
        ArrayList $r2 = new ArrayList();
        StringTokenizer $r4 = new StringTokenizer($r1, "|");
        while ($r4.hasMoreTokens()) {
            String $r5 = $r4.nextToken();
            $r1 = $r5.substring(0, $r5.indexOf(FileUploadSession.SEPARATOR));
            String $r6 = $r5.substring($r5.indexOf(FileUploadSession.SEPARATOR) + 1, $r5.indexOf(":"));
            $r5 = $r5.substring($r5.indexOf(":") + 1);
            ConfigItem $r3 = new ConfigItem();
            $r3.setCategory($r1);
            $r3.setName($r6);
            $r3.setStringValue($r5);
            $r2.add($r3);
        }
        return $r2;
    }

    public int checkConfigDisplayCounter(int $i0, boolean $z0) throws  {
        return checkConfigDisplayCounterNTV($i0, $z0);
    }

    public void setConfigDisplayCounter(int $i0, int $i1) throws  {
        setConfigDisplayCounterNTV($i0, $i1);
    }

    public boolean getConfigSwitchValue(int $i0) throws  {
        return getConfigSwitchValueNTV($i0);
    }

    public void setConfigSwitchValue(int $i0, boolean $z0) throws  {
        setConfigSwitchValueNTV($i0, $z0);
    }

    public void toggleConfigSwitchValue(int $i0) throws  {
        toggleConfigSwitchValueNTV($i0);
    }

    private void initNativeLayer() throws  {
        InitNativeLayerNTV();
    }

    public void setSkinScheme(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                ConfigManager.this.setSkinSchemeNTV($r1);
            }
        });
    }
}
