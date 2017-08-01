package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ResolveInfo;

class AccessibilityServiceInfoCompatIcs {
    AccessibilityServiceInfoCompatIcs() throws  {
    }

    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo $r0) throws  {
        return $r0.getCanRetrieveWindowContent();
    }

    public static String getDescription(AccessibilityServiceInfo $r0) throws  {
        return $r0.getDescription();
    }

    public static String getId(AccessibilityServiceInfo $r0) throws  {
        return $r0.getId();
    }

    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo $r0) throws  {
        return $r0.getResolveInfo();
    }

    public static String getSettingsActivityName(AccessibilityServiceInfo $r0) throws  {
        return $r0.getSettingsActivityName();
    }
}
