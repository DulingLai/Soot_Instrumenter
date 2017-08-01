package com.waze.share;

import android.app.Activity;
import android.util.Log;
import com.waze.AppService;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.ConfigManager.IConfigUpdater;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import java.util.ArrayList;
import java.util.List;

public final class ShareNativeManager implements IConfigUpdater {
    public static final float METER_TO_MILES_FACTOR = 6.21371E-4f;
    private static ShareNativeManager mInstance = null;
    private boolean mFacebookFeatureEnabled = false;
    private boolean mFoursquareFeatureEnabled = false;
    private volatile boolean mMetricUnits = true;
    private String mShareUserPrerfixUrl = null;
    private boolean mTwitterFeatureEnabled = false;

    public interface IShareFbMainDataHandler {
        void onFbMainData(ShareFbMainData shareFbMainData);
    }

    class C28371 implements Runnable {
        C28371() {
        }

        public void run() {
            ShareNativeManager.this.InitNativeLayerNTV();
        }
    }

    class C28393 implements Runnable {
        C28393() {
        }

        public void run() {
            Activity context = AppService.getActiveActivity();
            if (context != null) {
                ShareFbQueries.postDialogShow(context);
            }
        }
    }

    public interface IShareFbLocationDataHandler {
        void onFbLocationData(String str);
    }

    public interface IShareFbWithDataHandler {
        void onFbWithData(String str, String str2);
    }

    private native void InitNativeLayerNTV();

    private native String getDestinationNameNTV();

    private native String getLocationNTV(boolean z);

    private native String getNickNameNTV();

    public native String getEtaNTV();

    public native FriendUserData getFriendFromMeeting(String str);

    public native FriendUserData getReceivedLocationSender();

    public native boolean isMetricUnitsNTV();

    public native boolean isOkToShowShareDriveTip();

    public native void makeItOkToShowShareDriveTip();

    public native void shownReceivedLocationShareTip();

    public native void shownShareDriveTip();

    public static ShareNativeManager getInstance() {
        create();
        return mInstance;
    }

    public static ShareNativeManager create() {
        if (mInstance == null) {
            mInstance = new ShareNativeManager();
            mInstance.initNativeLayer();
            mInstance.initConfig();
        }
        return mInstance;
    }

    private void initNativeLayer() {
        NativeManager.Post(new C28371());
    }

    public void updateConfigItems(List<ConfigItem> configItems) {
        this.mFacebookFeatureEnabled = ((ConfigItem) configItems.get(0)).getBooleanValue();
        this.mTwitterFeatureEnabled = ((ConfigItem) configItems.get(1)).getBooleanValue();
        this.mFoursquareFeatureEnabled = ((ConfigItem) configItems.get(2)).getBooleanValue();
        this.mShareUserPrerfixUrl = ((ConfigItem) configItems.get(3)).getStringValue();
        Log.d(Logger.TAG, "Got ShareConfig values: FB: " + this.mFacebookFeatureEnabled);
    }

    public void getShareFbMainData(final IShareFbMainDataHandler dataHandler, final boolean shareDestination) {
        NativeManager.Post(new RunnableUICallback() {
            final ShareFbMainData mData = new ShareFbMainData();

            public void event() {
                NativeManager mgr = NativeManager.getInstance();
                this.mData.title = mgr.getLanguageString(DisplayStrings.DS_SHARE);
                this.mData.hint = mgr.getLanguageString(DisplayStrings.DS_SAY_ANYTHING___);
                this.mData.post_to_fb = mgr.getLanguageString(DisplayStrings.DS_POST_TO_FACEBOOK);
                this.mData.postMsgBoxSuccessTitle = mgr.getLanguageString(DisplayStrings.DS_GREAT_X);
                this.mData.postMsgBoxSuccessText = mgr.getLanguageString(DisplayStrings.DS_THE_POST_WAS_PUBLISHED);
                this.mData.postMsgBoxFailureTitle = mgr.getLanguageString(DisplayStrings.DS_UHHOHE);
                this.mData.postMsgBoxFailureText = mgr.getLanguageString(DisplayStrings.DS_THE_POST_COULD_NOT_BE_PUBLISHED);
                this.mData.isNavigating = mgr.isNavigatingNTV();
                this.mData.location = ShareNativeManager.this.getLocationNTV(shareDestination);
                this.mData.nickname = ShareNativeManager.this.getNickNameNTV();
                this.mData.eta = ShareNativeManager.this.getEtaNTV();
                ShareNativeManager.this.mMetricUnits = ShareNativeManager.this.isMetricUnitsNTV();
            }

            public void callback() {
                if (dataHandler != null) {
                    dataHandler.onFbMainData(this.mData);
                }
            }
        });
    }

    public void shareFbDialogShow() {
        AppService.Post(new C28393());
    }

    public void getShareFbWithData(final IShareFbWithDataHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            String mHint;
            String mTitle;

            public void event() {
                NativeManager mgr = NativeManager.getInstance();
                this.mTitle = mgr.getLanguageString(DisplayStrings.DS_WITH);
                this.mHint = mgr.getLanguageString(DisplayStrings.DS_SEARCH_FRIENDS);
            }

            public void callback() {
                if (dataHandler != null) {
                    dataHandler.onFbWithData(this.mTitle, this.mHint);
                }
            }
        });
    }

    public void getShareFbLocationData(final IShareFbLocationDataHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            String mTitle;

            public void event() {
                this.mTitle = NativeManager.getInstance().getLanguageString(DisplayStrings.DS_LOCATION);
            }

            public void callback() {
                if (dataHandler != null) {
                    dataHandler.onFbLocationData(this.mTitle);
                }
            }
        });
    }

    public boolean getFBFeatureEnabled() {
        return this.mFacebookFeatureEnabled;
    }

    public boolean getTwitterFeatureEnabled() {
        return this.mTwitterFeatureEnabled;
    }

    public boolean getFsqFeatureEnabled() {
        return this.mFoursquareFeatureEnabled;
    }

    public String getShareUserPrerfixUrl() {
        return this.mShareUserPrerfixUrl;
    }

    public boolean isMetricUnits() {
        return this.mMetricUnits;
    }

    private void initConfig() {
        RunnableExecutor configEvent = new RunnableExecutor(AppService.getInstance()) {
            public void event() {
                List<ConfigItem> configItems = new ArrayList();
                configItems.add(0, ShareConstants.CFG_ITEM_SHARE_FB_FEATURE_ENABLED);
                configItems.add(1, ShareConstants.CFG_ITEM_SHARE_TWITTER_FEATURE_ENABLED);
                configItems.add(2, ShareConstants.CFG_ITEM_SHARE_FOURSQUARE_FEATURE_ENABLED);
                configItems.add(3, ShareConstants.CFG_ITEM_SHARE_FB_USER_SHARE_PREFIX);
                ConfigManager.getInstance().getConfig(ShareNativeManager.this, configItems, "");
            }
        };
        if (NativeManager.IsAppStarted()) {
            configEvent.run();
        } else {
            NativeManager.registerOnAppStartedEvent(configEvent);
        }
    }
}
