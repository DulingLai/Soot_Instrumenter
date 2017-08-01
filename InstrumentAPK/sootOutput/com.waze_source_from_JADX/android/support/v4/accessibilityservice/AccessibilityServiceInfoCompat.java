package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;

public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    private static final AccessibilityServiceInfoVersionImpl IMPL;

    interface AccessibilityServiceInfoVersionImpl {
        boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo) throws ;

        int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) throws ;

        String getDescription(AccessibilityServiceInfo accessibilityServiceInfo) throws ;

        String getId(AccessibilityServiceInfo accessibilityServiceInfo) throws ;

        ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws ;

        String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo) throws ;
    }

    static class AccessibilityServiceInfoStubImpl implements AccessibilityServiceInfoVersionImpl {
        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) throws  {
            return false;
        }

        public int getCapabilities(AccessibilityServiceInfo info) throws  {
            return 0;
        }

        public String getDescription(AccessibilityServiceInfo info) throws  {
            return null;
        }

        public String getId(AccessibilityServiceInfo info) throws  {
            return null;
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo info) throws  {
            return null;
        }

        public String getSettingsActivityName(AccessibilityServiceInfo info) throws  {
            return null;
        }

        AccessibilityServiceInfoStubImpl() throws  {
        }
    }

    static class AccessibilityServiceInfoIcsImpl extends AccessibilityServiceInfoStubImpl {
        AccessibilityServiceInfoIcsImpl() throws  {
        }

        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo $r1) throws  {
            return AccessibilityServiceInfoCompatIcs.getCanRetrieveWindowContent($r1);
        }

        public String getDescription(AccessibilityServiceInfo $r1) throws  {
            return AccessibilityServiceInfoCompatIcs.getDescription($r1);
        }

        public String getId(AccessibilityServiceInfo $r1) throws  {
            return AccessibilityServiceInfoCompatIcs.getId($r1);
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo $r1) throws  {
            return AccessibilityServiceInfoCompatIcs.getResolveInfo($r1);
        }

        public String getSettingsActivityName(AccessibilityServiceInfo $r1) throws  {
            return AccessibilityServiceInfoCompatIcs.getSettingsActivityName($r1);
        }

        public int getCapabilities(AccessibilityServiceInfo $r1) throws  {
            return getCanRetrieveWindowContent($r1) ? 1 : 0;
        }
    }

    static class AccessibilityServiceInfoJellyBeanMr2 extends AccessibilityServiceInfoIcsImpl {
        AccessibilityServiceInfoJellyBeanMr2() throws  {
        }

        public int getCapabilities(AccessibilityServiceInfo $r1) throws  {
            return AccessibilityServiceInfoCompatJellyBeanMr2.getCapabilities($r1);
        }
    }

    public static String capabilityToString(int $i0) throws  {
        switch ($i0) {
            case 1:
                return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
            case 2:
                return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
            case 3:
            case 5:
            case 6:
            case 7:
                break;
            case 4:
                return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case 8:
                return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
            default:
                break;
        }
        return "UNKNOWN";
    }

    public static java.lang.String feedbackTypeToString(int r1) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.feedbackTypeToString(int):java.lang.String
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 5 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.feedbackTypeToString(int):java.lang.String");
    }

    public static String flagToString(int $i0) throws  {
        switch ($i0) {
            case 1:
                return "DEFAULT";
            case 2:
                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
            case 4:
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
            case 8:
                return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case 16:
                return "FLAG_REPORT_VIEW_IDS";
            case 32:
                return "FLAG_REQUEST_FILTER_KEY_EVENTS";
            default:
                return null;
        }
    }

    static {
        if (VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityServiceInfoJellyBeanMr2();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityServiceInfoIcsImpl();
        } else {
            IMPL = new AccessibilityServiceInfoStubImpl();
        }
    }

    private AccessibilityServiceInfoCompat() throws  {
    }

    public static String getId(AccessibilityServiceInfo $r0) throws  {
        return IMPL.getId($r0);
    }

    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo $r0) throws  {
        return IMPL.getResolveInfo($r0);
    }

    public static String getSettingsActivityName(AccessibilityServiceInfo $r0) throws  {
        return IMPL.getSettingsActivityName($r0);
    }

    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo $r0) throws  {
        return IMPL.getCanRetrieveWindowContent($r0);
    }

    public static String getDescription(AccessibilityServiceInfo $r0) throws  {
        return IMPL.getDescription($r0);
    }

    public static int getCapabilities(AccessibilityServiceInfo $r0) throws  {
        return IMPL.getCapabilities($r0);
    }
}
