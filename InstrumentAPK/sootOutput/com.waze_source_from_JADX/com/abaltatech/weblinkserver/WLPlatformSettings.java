package com.abaltatech.weblinkserver;

import android.os.Build;
import android.os.Build.VERSION;

public class WLPlatformSettings {
    private static boolean m_isInitialized = false;
    private static WLSettingNameValuePairList m_settings;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void init() throws  {
        /*
        r0 = com.abaltatech.weblinkserver.WLPlatformSettings.class;
        monitor-enter(r0);
        r1 = m_isInitialized;	 Catch:{ Throwable -> 0x002a }
        if (r1 == 0) goto L_0x000b;
    L_0x0007:
        r0 = com.abaltatech.weblinkserver.WLPlatformSettings.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002a }
        return;
    L_0x000b:
        r2 = new com.abaltatech.weblinkserver.WLSettingNameValuePairList;	 Catch:{ Throwable -> 0x002a }
        r2.<init>();	 Catch:{ Throwable -> 0x002a }
        m_settings = r2;	 Catch:{ Throwable -> 0x002a }
        r1 = checkNexus7WithAndroid19();	 Catch:{ Throwable -> 0x002a }
        if (r1 == 0) goto L_0x0026;
    L_0x0018:
        r3 = new com.abaltatech.weblinkserver.WLSettingNameValuePair;	 Catch:{ Throwable -> 0x002a }
        r4 = "enc.h264.useSurface";
        r5 = "false";
        r3.<init>(r4, r5);	 Catch:{ Throwable -> 0x002a }
        r2 = m_settings;	 Catch:{ Throwable -> 0x002a }
        r2.add(r3);	 Catch:{ Throwable -> 0x002a }
    L_0x0026:
        r0 = com.abaltatech.weblinkserver.WLPlatformSettings.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002a }
        return;
    L_0x002a:
        r6 = move-exception;
        r0 = com.abaltatech.weblinkserver.WLPlatformSettings.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002a }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLPlatformSettings.init():void");
    }

    private static boolean checkNexus7WithAndroid19() throws  {
        if (VERSION.SDK_INT != 19) {
            return false;
        }
        return Build.MODEL.compareToIgnoreCase("Nexus 7") == 0 || Build.MANUFACTURER.compareToIgnoreCase("Nexus 7") == 0;
    }

    public static WLSettingNameValuePairList getCurrentPlatformSettings() throws  {
        init();
        return new WLSettingNameValuePairList(m_settings);
    }
}
