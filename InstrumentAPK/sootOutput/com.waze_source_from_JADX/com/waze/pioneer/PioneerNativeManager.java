package com.waze.pioneer;

public final class PioneerNativeManager {
    private static PioneerNativeManager mInstance = null;

    private native void InitNativeLayerNTV();

    private native void onPioneerConnectedNTV();

    public native void LocationCallbackNTV(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static PioneerNativeManager create() {
        if (mInstance == null) {
            mInstance = new PioneerNativeManager();
            mInstance.InitNativeLayerNTV();
        }
        return mInstance;
    }

    public static PioneerNativeManager getInstance() {
        create();
        return mInstance;
    }

    public static void onPioneerConnected() {
        create();
        mInstance.onPioneerConnectedNTV();
    }
}
